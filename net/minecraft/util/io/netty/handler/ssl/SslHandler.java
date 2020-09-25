/*      */ package net.minecraft.util.io.netty.handler.ssl;
/*      */ 
/*      */ import java.net.SocketAddress;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.nio.channels.DatagramChannel;
/*      */ import java.nio.channels.SocketChannel;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.Deque;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.ScheduledFuture;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.net.ssl.SSLEngine;
/*      */ import javax.net.ssl.SSLEngineResult;
/*      */ import javax.net.ssl.SSLException;
/*      */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*      */ import net.minecraft.util.io.netty.buffer.ByteBufUtil;
/*      */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*      */ import net.minecraft.util.io.netty.channel.Channel;
/*      */ import net.minecraft.util.io.netty.channel.ChannelException;
/*      */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*      */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*      */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*      */ import net.minecraft.util.io.netty.channel.ChannelOutboundHandler;
/*      */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*      */ import net.minecraft.util.io.netty.handler.codec.ByteToMessageDecoder;
/*      */ import net.minecraft.util.io.netty.util.concurrent.DefaultPromise;
/*      */ import net.minecraft.util.io.netty.util.concurrent.EventExecutor;
/*      */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*      */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
/*      */ import net.minecraft.util.io.netty.util.concurrent.ImmediateExecutor;
/*      */ import net.minecraft.util.io.netty.util.concurrent.Promise;
/*      */ import net.minecraft.util.io.netty.util.concurrent.ScheduledFuture;
/*      */ import net.minecraft.util.io.netty.util.internal.EmptyArrays;
/*      */ import net.minecraft.util.io.netty.util.internal.PendingWrite;
/*      */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
/*      */ import net.minecraft.util.io.netty.util.internal.RecyclableArrayList;
/*      */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*      */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SslHandler
/*      */   extends ByteToMessageDecoder
/*      */   implements ChannelOutboundHandler
/*      */ {
/*  158 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SslHandler.class);
/*      */ 
/*      */   
/*  161 */   private static final Pattern IGNORABLE_CLASS_IN_STACK = Pattern.compile("^.*(?:Socket|Datagram|Sctp|Udt)Channel.*$");
/*      */   
/*  163 */   private static final Pattern IGNORABLE_ERROR_MESSAGE = Pattern.compile("^.*(?:connection.*(?:reset|closed|abort|broken)|broken.*pipe).*$", 2);
/*      */ 
/*      */   
/*  166 */   private static final SSLException SSLENGINE_CLOSED = new SSLException("SSLEngine closed already");
/*  167 */   private static final SSLException HANDSHAKE_TIMED_OUT = new SSLException("handshake timed out");
/*  168 */   private static final ClosedChannelException CHANNEL_CLOSED = new ClosedChannelException(); private volatile ChannelHandlerContext ctx; private final SSLEngine engine;
/*      */   
/*      */   static {
/*  171 */     SSLENGINE_CLOSED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*  172 */     HANDSHAKE_TIMED_OUT.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*  173 */     CHANNEL_CLOSED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*      */   }
/*      */ 
/*      */   
/*      */   private final Executor delegatedTaskExecutor;
/*      */   
/*      */   private final boolean startTls;
/*      */   
/*      */   private boolean sentFirstMessage;
/*      */   
/*  183 */   private final LazyChannelPromise handshakePromise = new LazyChannelPromise();
/*  184 */   private final LazyChannelPromise sslCloseFuture = new LazyChannelPromise();
/*  185 */   private final Deque<PendingWrite> pendingUnencryptedWrites = new ArrayDeque<PendingWrite>();
/*      */   
/*      */   private int packetLength;
/*      */   
/*      */   private ByteBuf decodeOut;
/*  190 */   private volatile long handshakeTimeoutMillis = 10000L;
/*  191 */   private volatile long closeNotifyTimeoutMillis = 3000L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SslHandler(SSLEngine engine) {
/*  199 */     this(engine, (Executor)ImmediateExecutor.INSTANCE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SslHandler(SSLEngine engine, boolean startTls) {
/*  210 */     this(engine, startTls, (Executor)ImmediateExecutor.INSTANCE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SslHandler(SSLEngine engine, Executor delegatedTaskExecutor) {
/*  223 */     this(engine, false, delegatedTaskExecutor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SslHandler(SSLEngine engine, boolean startTls, Executor delegatedTaskExecutor) {
/*  239 */     if (engine == null) {
/*  240 */       throw new NullPointerException("engine");
/*      */     }
/*  242 */     if (delegatedTaskExecutor == null) {
/*  243 */       throw new NullPointerException("delegatedTaskExecutor");
/*      */     }
/*  245 */     this.engine = engine;
/*  246 */     this.delegatedTaskExecutor = delegatedTaskExecutor;
/*  247 */     this.startTls = startTls;
/*      */   }
/*      */   
/*      */   public long getHandshakeTimeoutMillis() {
/*  251 */     return this.handshakeTimeoutMillis;
/*      */   }
/*      */   
/*      */   public void setHandshakeTimeout(long handshakeTimeout, TimeUnit unit) {
/*  255 */     if (unit == null) {
/*  256 */       throw new NullPointerException("unit");
/*      */     }
/*      */     
/*  259 */     setHandshakeTimeoutMillis(unit.toMillis(handshakeTimeout));
/*      */   }
/*      */   
/*      */   public void setHandshakeTimeoutMillis(long handshakeTimeoutMillis) {
/*  263 */     if (handshakeTimeoutMillis < 0L) {
/*  264 */       throw new IllegalArgumentException("handshakeTimeoutMillis: " + handshakeTimeoutMillis + " (expected: >= 0)");
/*      */     }
/*      */     
/*  267 */     this.handshakeTimeoutMillis = handshakeTimeoutMillis;
/*      */   }
/*      */   
/*      */   public long getCloseNotifyTimeoutMillis() {
/*  271 */     return this.closeNotifyTimeoutMillis;
/*      */   }
/*      */   
/*      */   public void setCloseNotifyTimeout(long closeNotifyTimeout, TimeUnit unit) {
/*  275 */     if (unit == null) {
/*  276 */       throw new NullPointerException("unit");
/*      */     }
/*      */     
/*  279 */     setCloseNotifyTimeoutMillis(unit.toMillis(closeNotifyTimeout));
/*      */   }
/*      */   
/*      */   public void setCloseNotifyTimeoutMillis(long closeNotifyTimeoutMillis) {
/*  283 */     if (closeNotifyTimeoutMillis < 0L) {
/*  284 */       throw new IllegalArgumentException("closeNotifyTimeoutMillis: " + closeNotifyTimeoutMillis + " (expected: >= 0)");
/*      */     }
/*      */     
/*  287 */     this.closeNotifyTimeoutMillis = closeNotifyTimeoutMillis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SSLEngine engine() {
/*  294 */     return this.engine;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Future<Channel> handshakeFuture() {
/*  301 */     return (Future<Channel>)this.handshakePromise;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelFuture close() {
/*  309 */     return close(this.ctx.newPromise());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelFuture close(final ChannelPromise future) {
/*  316 */     final ChannelHandlerContext ctx = this.ctx;
/*  317 */     ctx.executor().execute(new Runnable()
/*      */         {
/*      */           public void run() {
/*  320 */             SslHandler.this.engine.closeOutbound();
/*      */             try {
/*  322 */               SslHandler.this.write(ctx, Unpooled.EMPTY_BUFFER, future);
/*  323 */               SslHandler.this.flush(ctx);
/*  324 */             } catch (Exception e) {
/*  325 */               if (!future.tryFailure(e)) {
/*  326 */                 SslHandler.logger.warn("flush() raised a masked exception.", e);
/*      */               }
/*      */             } 
/*      */           }
/*      */         });
/*      */     
/*  332 */     return (ChannelFuture)future;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Future<Channel> sslCloseFuture() {
/*  344 */     return (Future<Channel>)this.sslCloseFuture;
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
/*  349 */     if (this.decodeOut != null) {
/*  350 */       this.decodeOut.release();
/*  351 */       this.decodeOut = null;
/*      */     } 
/*      */     while (true) {
/*  354 */       PendingWrite write = this.pendingUnencryptedWrites.poll();
/*  355 */       if (write == null) {
/*      */         break;
/*      */       }
/*  358 */       write.failAndRecycle((Throwable)new ChannelException("Pending write on removal of SslHandler"));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/*  364 */     ctx.bind(localAddress, promise);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/*  370 */     ctx.connect(remoteAddress, localAddress, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/*  375 */     ctx.deregister(promise);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/*  381 */     closeOutboundAndChannel(ctx, promise, true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/*  387 */     closeOutboundAndChannel(ctx, promise, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void read(ChannelHandlerContext ctx) {
/*  392 */     ctx.read();
/*      */   }
/*      */ 
/*      */   
/*      */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/*  397 */     this.pendingUnencryptedWrites.add(PendingWrite.newInstance(msg, (Promise)promise));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flush(ChannelHandlerContext ctx) throws Exception {
/*  404 */     if (this.startTls && !this.sentFirstMessage) {
/*  405 */       this.sentFirstMessage = true;
/*      */       while (true) {
/*  407 */         PendingWrite pendingWrite = this.pendingUnencryptedWrites.poll();
/*  408 */         if (pendingWrite == null) {
/*      */           break;
/*      */         }
/*  411 */         ctx.write(pendingWrite.msg(), (ChannelPromise)pendingWrite.recycleAndGet());
/*      */       } 
/*  413 */       ctx.flush();
/*      */       return;
/*      */     } 
/*  416 */     if (this.pendingUnencryptedWrites.isEmpty()) {
/*  417 */       this.pendingUnencryptedWrites.add(PendingWrite.newInstance(Unpooled.EMPTY_BUFFER, null));
/*      */     }
/*  419 */     flush0(ctx);
/*      */   }
/*      */   
/*      */   private void flush0(ChannelHandlerContext ctx) throws SSLException {
/*  423 */     ByteBuf out = null;
/*  424 */     ChannelPromise promise = null;
/*      */     try {
/*      */       while (true) {
/*  427 */         PendingWrite pending = this.pendingUnencryptedWrites.peek();
/*  428 */         if (pending == null) {
/*      */           break;
/*      */         }
/*  431 */         if (out == null) {
/*  432 */           out = ctx.alloc().buffer();
/*      */         }
/*  434 */         ByteBuf buf = (ByteBuf)pending.msg();
/*  435 */         SSLEngineResult result = wrap(this.engine, buf, out);
/*      */         
/*  437 */         if (!buf.isReadable()) {
/*  438 */           buf.release();
/*  439 */           promise = (ChannelPromise)pending.recycleAndGet();
/*  440 */           this.pendingUnencryptedWrites.remove();
/*      */         } else {
/*  442 */           promise = null;
/*      */         } 
/*      */         
/*  445 */         if (result.getStatus() == SSLEngineResult.Status.CLOSED) {
/*      */           
/*      */           while (true) {
/*      */             
/*  449 */             PendingWrite w = this.pendingUnencryptedWrites.poll();
/*  450 */             if (w == null) {
/*      */               break;
/*      */             }
/*  453 */             w.failAndRecycle(SSLENGINE_CLOSED);
/*      */           } 
/*      */           return;
/*      */         } 
/*  457 */         switch (result.getHandshakeStatus()) {
/*      */           case CLOSED:
/*  459 */             runDelegatedTasks();
/*      */             continue;
/*      */           case BUFFER_UNDERFLOW:
/*  462 */             setHandshakeSuccess();
/*      */           
/*      */           case BUFFER_OVERFLOW:
/*      */           case null:
/*  466 */             if (promise != null) {
/*  467 */               ctx.writeAndFlush(out, promise);
/*  468 */               promise = null;
/*      */             } else {
/*  470 */               ctx.writeAndFlush(out);
/*      */             } 
/*  472 */             out = ctx.alloc().buffer();
/*      */             continue;
/*      */           case null:
/*      */             return;
/*      */         } 
/*  477 */         throw new IllegalStateException("Unknown handshake status: " + result.getHandshakeStatus());
/*      */       }
/*      */     
/*      */     }
/*  481 */     catch (SSLException e) {
/*  482 */       setHandshakeFailure(e);
/*  483 */       throw e;
/*      */     } finally {
/*  485 */       if (out != null && out.isReadable()) {
/*  486 */         if (promise != null) {
/*  487 */           ctx.writeAndFlush(out, promise);
/*      */         } else {
/*  489 */           ctx.writeAndFlush(out);
/*      */         } 
/*  491 */         out = null;
/*  492 */       } else if (promise != null) {
/*  493 */         promise.trySuccess();
/*      */       } 
/*  495 */       if (out != null) {
/*  496 */         out.release();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void flushNonAppData0(ChannelHandlerContext ctx, boolean inUnwrap) throws SSLException {
/*  502 */     ByteBuf out = null; try {
/*      */       SSLEngineResult result;
/*      */       do {
/*  505 */         if (out == null) {
/*  506 */           out = ctx.alloc().buffer();
/*      */         }
/*  508 */         result = wrap(this.engine, Unpooled.EMPTY_BUFFER, out);
/*      */         
/*  510 */         if (result.bytesProduced() > 0) {
/*  511 */           ctx.writeAndFlush(out);
/*  512 */           out = null;
/*      */         } 
/*      */         
/*  515 */         switch (result.getHandshakeStatus()) {
/*      */           case BUFFER_UNDERFLOW:
/*  517 */             setHandshakeSuccess();
/*      */             break;
/*      */           case CLOSED:
/*  520 */             runDelegatedTasks();
/*      */             break;
/*      */           case null:
/*  523 */             if (!inUnwrap) {
/*  524 */               unwrap(ctx);
/*      */             }
/*      */             break;
/*      */           
/*      */           case null:
/*      */             break;
/*      */           
/*      */           case BUFFER_OVERFLOW:
/*  532 */             if (!inUnwrap) {
/*  533 */               unwrap(ctx);
/*      */             }
/*      */             break;
/*      */           default:
/*  537 */             throw new IllegalStateException("Unknown handshake status: " + result.getHandshakeStatus());
/*      */         } 
/*      */       
/*  540 */       } while (result.bytesProduced() != 0);
/*      */ 
/*      */     
/*      */     }
/*  544 */     catch (SSLException e) {
/*  545 */       setHandshakeFailure(e);
/*  546 */       throw e;
/*      */     } finally {
/*  548 */       if (out != null)
/*  549 */         out.release(); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static SSLEngineResult wrap(SSLEngine engine, ByteBuf in, ByteBuf out) throws SSLException {
/*      */     SSLEngineResult result;
/*  555 */     ByteBuffer in0 = in.nioBuffer();
/*      */     while (true) {
/*  557 */       ByteBuffer out0 = out.nioBuffer(out.writerIndex(), out.writableBytes());
/*  558 */       result = engine.wrap(in0, out0);
/*  559 */       in.skipBytes(result.bytesConsumed());
/*  560 */       out.writerIndex(out.writerIndex() + result.bytesProduced());
/*  561 */       if (result.getStatus() == SSLEngineResult.Status.BUFFER_OVERFLOW) {
/*  562 */         out.ensureWritable(engine.getSession().getPacketBufferSize()); continue;
/*      */       }  break;
/*  564 */     }  return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/*  573 */     setHandshakeFailure(CHANNEL_CLOSED);
/*  574 */     super.channelInactive(ctx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/*  579 */     if (ignoreException(cause)) {
/*      */ 
/*      */       
/*  582 */       if (logger.isDebugEnabled()) {
/*  583 */         logger.debug("Swallowing a harmless 'connection reset by peer / broken pipe' error that occurred while writing close_notify in response to the peer's close_notify", cause);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  590 */       if (ctx.channel().isActive()) {
/*  591 */         ctx.close();
/*      */       }
/*      */     } else {
/*  594 */       ctx.fireExceptionCaught(cause);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ignoreException(Throwable t) {
/*  608 */     if (!(t instanceof SSLException) && t instanceof java.io.IOException && this.sslCloseFuture.isDone()) {
/*  609 */       String message = String.valueOf(t.getMessage()).toLowerCase();
/*      */ 
/*      */ 
/*      */       
/*  613 */       if (IGNORABLE_ERROR_MESSAGE.matcher(message).matches()) {
/*  614 */         return true;
/*      */       }
/*      */ 
/*      */       
/*  618 */       StackTraceElement[] elements = t.getStackTrace();
/*  619 */       for (StackTraceElement element : elements) {
/*  620 */         String classname = element.getClassName();
/*  621 */         String methodname = element.getMethodName();
/*      */ 
/*      */         
/*  624 */         if (!classname.startsWith("io.netty."))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  629 */           if ("read".equals(methodname)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  635 */             if (IGNORABLE_CLASS_IN_STACK.matcher(classname).matches()) {
/*  636 */               return true;
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/*  643 */               Class<?> clazz = getClass().getClassLoader().loadClass(classname);
/*      */               
/*  645 */               if (SocketChannel.class.isAssignableFrom(clazz) || DatagramChannel.class.isAssignableFrom(clazz))
/*      */               {
/*  647 */                 return true;
/*      */               }
/*      */ 
/*      */               
/*  651 */               if (PlatformDependent.javaVersion() >= 7 && "com.sun.nio.sctp.SctpChannel".equals(clazz.getSuperclass().getName()))
/*      */               {
/*  653 */                 return true;
/*      */               }
/*  655 */             } catch (ClassNotFoundException e) {}
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  661 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEncrypted(ByteBuf buffer) {
/*  677 */     if (buffer.readableBytes() < 5) {
/*  678 */       throw new IllegalArgumentException("buffer must have at least 5 readable bytes");
/*      */     }
/*  680 */     return (getEncryptedPacketLength(buffer) != -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getEncryptedPacketLength(ByteBuf buffer) {
/*      */     boolean tls;
/*  697 */     int first = buffer.readerIndex();
/*  698 */     int packetLength = 0;
/*      */ 
/*      */ 
/*      */     
/*  702 */     switch (buffer.getUnsignedByte(first)) {
/*      */       case 20:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*  707 */         tls = true;
/*      */         break;
/*      */       
/*      */       default:
/*  711 */         tls = false;
/*      */         break;
/*      */     } 
/*  714 */     if (tls) {
/*      */       
/*  716 */       int majorVersion = buffer.getUnsignedByte(first + 1);
/*  717 */       if (majorVersion == 3) {
/*      */         
/*  719 */         packetLength = buffer.getUnsignedShort(first + 3) + 5;
/*  720 */         if (packetLength <= 5)
/*      */         {
/*  722 */           tls = false;
/*      */         }
/*      */       } else {
/*      */         
/*  726 */         tls = false;
/*      */       } 
/*      */     } 
/*      */     
/*  730 */     if (!tls) {
/*      */       
/*  732 */       boolean sslv2 = true;
/*  733 */       int headerLength = ((buffer.getUnsignedByte(first) & 0x80) != 0) ? 2 : 3;
/*  734 */       int majorVersion = buffer.getUnsignedByte(first + headerLength + 1);
/*  735 */       if (majorVersion == 2 || majorVersion == 3) {
/*      */         
/*  737 */         if (headerLength == 2) {
/*  738 */           packetLength = (buffer.getShort(first) & Short.MAX_VALUE) + 2;
/*      */         } else {
/*  740 */           packetLength = (buffer.getShort(first) & 0x3FFF) + 3;
/*      */         } 
/*  742 */         if (packetLength <= headerLength) {
/*  743 */           sslv2 = false;
/*      */         }
/*      */       } else {
/*  746 */         sslv2 = false;
/*      */       } 
/*      */       
/*  749 */       if (!sslv2) {
/*  750 */         return -1;
/*      */       }
/*      */     } 
/*  753 */     return packetLength;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws SSLException {
/*  758 */     int packetLength = this.packetLength;
/*  759 */     int readableBytes = in.readableBytes();
/*      */     
/*  761 */     if (packetLength == 0) {
/*      */       
/*  763 */       if (readableBytes < 5) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  768 */       packetLength = getEncryptedPacketLength(in);
/*  769 */       if (packetLength == -1) {
/*      */         
/*  771 */         NotSslRecordException e = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(in));
/*      */         
/*  773 */         in.skipBytes(readableBytes);
/*  774 */         ctx.fireExceptionCaught(e);
/*  775 */         setHandshakeFailure(e);
/*      */         
/*      */         return;
/*      */       } 
/*  779 */       assert packetLength > 0;
/*  780 */       this.packetLength = packetLength;
/*      */     } 
/*      */     
/*  783 */     if (readableBytes < packetLength) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  797 */     int readerIndex = in.readerIndex();
/*  798 */     in.skipBytes(packetLength);
/*  799 */     ByteBuffer buffer = in.nioBuffer(readerIndex, packetLength);
/*  800 */     this.packetLength = 0;
/*      */     
/*  802 */     unwrap(ctx, buffer, out);
/*      */   }
/*      */   
/*      */   private void unwrap(ChannelHandlerContext ctx) throws SSLException {
/*  806 */     RecyclableArrayList out = RecyclableArrayList.newInstance();
/*      */     try {
/*  808 */       unwrap(ctx, Unpooled.EMPTY_BUFFER.nioBuffer(), (List<Object>)out);
/*  809 */       int size = out.size();
/*  810 */       for (int i = 0; i < size; i++) {
/*  811 */         ctx.fireChannelRead(out.get(i));
/*      */       }
/*      */     } finally {
/*  814 */       out.recycle();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void unwrap(ChannelHandlerContext ctx, ByteBuffer packet, List<Object> out) throws SSLException {
/*  819 */     boolean wrapLater = false;
/*  820 */     int bytesProduced = 0;
/*      */     
/*      */     try {
/*      */       while (true) {
/*  824 */         if (this.decodeOut == null) {
/*  825 */           this.decodeOut = ctx.alloc().buffer();
/*      */         }
/*  827 */         SSLEngineResult result = unwrap(this.engine, packet, this.decodeOut);
/*  828 */         bytesProduced += result.bytesProduced();
/*  829 */         switch (result.getStatus()) {
/*      */           
/*      */           case CLOSED:
/*  832 */             this.sslCloseFuture.trySuccess(ctx.channel());
/*      */             break;
/*      */           
/*      */           case BUFFER_UNDERFLOW:
/*      */             break;
/*      */         } 
/*  838 */         switch (result.getHandshakeStatus()) {
/*      */           case null:
/*      */             break;
/*      */           case null:
/*  842 */             flushNonAppData0(ctx, true);
/*      */             break;
/*      */           case CLOSED:
/*  845 */             runDelegatedTasks();
/*      */             break;
/*      */           case BUFFER_UNDERFLOW:
/*  848 */             setHandshakeSuccess();
/*  849 */             wrapLater = true;
/*      */             continue;
/*      */           case BUFFER_OVERFLOW:
/*      */             break;
/*      */           default:
/*  854 */             throw new IllegalStateException("Unknown handshake status: " + result.getHandshakeStatus());
/*      */         } 
/*      */ 
/*      */         
/*  858 */         if (result.bytesConsumed() == 0 && result.bytesProduced() == 0) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */       
/*  863 */       if (wrapLater) {
/*  864 */         flush0(ctx);
/*      */       }
/*  866 */     } catch (SSLException e) {
/*  867 */       setHandshakeFailure(e);
/*  868 */       throw e;
/*      */     } finally {
/*  870 */       if (bytesProduced > 0) {
/*  871 */         ByteBuf decodeOut = this.decodeOut;
/*  872 */         this.decodeOut = null;
/*  873 */         out.add(decodeOut);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static SSLEngineResult unwrap(SSLEngine engine, ByteBuffer in, ByteBuf out) throws SSLException {
/*      */     SSLEngineResult result;
/*      */     while (true) {
/*  880 */       ByteBuffer out0 = out.nioBuffer(out.writerIndex(), out.writableBytes());
/*  881 */       result = engine.unwrap(in, out0);
/*  882 */       out.writerIndex(out.writerIndex() + result.bytesProduced());
/*  883 */       switch (result.getStatus()) {
/*      */         case BUFFER_OVERFLOW:
/*  885 */           out.ensureWritable(engine.getSession().getApplicationBufferSize()); continue;
/*      */       }  break;
/*      */     } 
/*  888 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void runDelegatedTasks() {
/*      */     while (true) {
/*  895 */       Runnable task = this.engine.getDelegatedTask();
/*  896 */       if (task == null) {
/*      */         break;
/*      */       }
/*      */       
/*  900 */       this.delegatedTaskExecutor.execute(task);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setHandshakeSuccess() {
/*  908 */     if (this.handshakePromise.trySuccess(this.ctx.channel())) {
/*  909 */       this.ctx.fireUserEventTriggered(SslHandshakeCompletionEvent.SUCCESS);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setHandshakeFailure(Throwable cause) {
/*  919 */     this.engine.closeOutbound();
/*      */     
/*      */     try {
/*  922 */       this.engine.closeInbound();
/*  923 */     } catch (SSLException e) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  928 */       String msg = e.getMessage();
/*  929 */       if (msg == null || !msg.contains("possible truncation attack")) {
/*  930 */         logger.debug("SSLEngine.closeInbound() raised an exception.", e);
/*      */       }
/*      */     } 
/*  933 */     notifyHandshakeFailure(cause);
/*      */     while (true) {
/*  935 */       PendingWrite write = this.pendingUnencryptedWrites.poll();
/*  936 */       if (write == null) {
/*      */         break;
/*      */       }
/*  939 */       write.failAndRecycle(cause);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void notifyHandshakeFailure(Throwable cause) {
/*  944 */     if (this.handshakePromise.tryFailure(cause)) {
/*  945 */       this.ctx.fireUserEventTriggered(new SslHandshakeCompletionEvent(cause));
/*  946 */       this.ctx.close();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void closeOutboundAndChannel(ChannelHandlerContext ctx, ChannelPromise promise, boolean disconnect) throws Exception {
/*  952 */     if (!ctx.channel().isActive()) {
/*  953 */       if (disconnect) {
/*  954 */         ctx.disconnect(promise);
/*      */       } else {
/*  956 */         ctx.close(promise);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*  961 */     this.engine.closeOutbound();
/*      */     
/*  963 */     ChannelPromise closeNotifyFuture = ctx.newPromise();
/*  964 */     write(ctx, Unpooled.EMPTY_BUFFER, closeNotifyFuture);
/*  965 */     flush(ctx);
/*  966 */     safeClose(ctx, (ChannelFuture)closeNotifyFuture, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/*  971 */     this.ctx = ctx;
/*      */     
/*  973 */     if (ctx.channel().isActive())
/*      */     {
/*      */       
/*  976 */       handshake0();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Future<Channel> handshake0() {
/*      */     final ScheduledFuture<?> timeoutFuture;
/*  985 */     if (this.handshakeTimeoutMillis > 0L) {
/*  986 */       ScheduledFuture scheduledFuture = this.ctx.executor().schedule(new Runnable()
/*      */           {
/*      */             public void run() {
/*  989 */               if (SslHandler.this.handshakePromise.isDone()) {
/*      */                 return;
/*      */               }
/*  992 */               SslHandler.this.notifyHandshakeFailure(SslHandler.HANDSHAKE_TIMED_OUT);
/*      */             }
/*      */           },  this.handshakeTimeoutMillis, TimeUnit.MILLISECONDS);
/*      */     } else {
/*  996 */       timeoutFuture = null;
/*      */     } 
/*      */     
/*  999 */     this.handshakePromise.addListener(new GenericFutureListener<Future<Channel>>()
/*      */         {
/*      */           public void operationComplete(Future<Channel> f) throws Exception {
/* 1002 */             if (timeoutFuture != null) {
/* 1003 */               timeoutFuture.cancel(false);
/*      */             }
/*      */           }
/*      */         });
/*      */     try {
/* 1008 */       this.engine.beginHandshake();
/* 1009 */       flushNonAppData0(this.ctx, false);
/* 1010 */     } catch (Exception e) {
/* 1011 */       notifyHandshakeFailure(e);
/*      */     } 
/* 1013 */     return (Future<Channel>)this.handshakePromise;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void channelActive(final ChannelHandlerContext ctx) throws Exception {
/* 1021 */     if (!this.startTls && this.engine.getUseClientMode())
/*      */     {
/*      */       
/* 1024 */       handshake0().addListener(new GenericFutureListener<Future<Channel>>()
/*      */           {
/*      */             public void operationComplete(Future<Channel> future) throws Exception {
/* 1027 */               if (!future.isSuccess()) {
/* 1028 */                 future.cause().printStackTrace();
/* 1029 */                 ctx.close();
/*      */               } 
/*      */             }
/*      */           });
/*      */     }
/* 1034 */     ctx.fireChannelActive();
/*      */   }
/*      */   
/*      */   private void safeClose(final ChannelHandlerContext ctx, ChannelFuture flushFuture, final ChannelPromise promise) {
/*      */     final ScheduledFuture<?> timeoutFuture;
/* 1039 */     if (!ctx.channel().isActive()) {
/* 1040 */       ctx.close(promise);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1045 */     if (this.closeNotifyTimeoutMillis > 0L) {
/*      */       
/* 1047 */       ScheduledFuture scheduledFuture = ctx.executor().schedule(new Runnable()
/*      */           {
/*      */             public void run() {
/* 1050 */               SslHandler.logger.warn(ctx.channel() + " last write attempt timed out." + " Force-closing the connection.");
/*      */ 
/*      */               
/* 1053 */               ctx.close(promise);
/*      */             }
/*      */           },  this.closeNotifyTimeoutMillis, TimeUnit.MILLISECONDS);
/*      */     } else {
/* 1057 */       timeoutFuture = null;
/*      */     } 
/*      */ 
/*      */     
/* 1061 */     flushFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*      */         {
/*      */           public void operationComplete(ChannelFuture f) throws Exception
/*      */           {
/* 1065 */             if (timeoutFuture != null) {
/* 1066 */               timeoutFuture.cancel(false);
/*      */             }
/* 1068 */             if (ctx.channel().isActive())
/* 1069 */               ctx.close(promise); 
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   private final class LazyChannelPromise
/*      */     extends DefaultPromise<Channel> {
/*      */     private LazyChannelPromise() {}
/*      */     
/*      */     protected EventExecutor executor() {
/* 1079 */       if (SslHandler.this.ctx == null) {
/* 1080 */         throw new IllegalStateException();
/*      */       }
/* 1082 */       return SslHandler.this.ctx.executor();
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\ssl\SslHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */