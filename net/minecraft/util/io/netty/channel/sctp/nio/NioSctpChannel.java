/*     */ package net.minecraft.util.io.netty.channel.sctp.nio;
/*     */ 
/*     */ import com.sun.nio.sctp.Association;
/*     */ import com.sun.nio.sctp.MessageInfo;
/*     */ import com.sun.nio.sctp.NotificationHandler;
/*     */ import com.sun.nio.sctp.SctpChannel;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelException;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelMetadata;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOutboundBuffer;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.channel.RecvByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.channel.nio.AbstractNioMessageChannel;
/*     */ import net.minecraft.util.io.netty.channel.sctp.DefaultSctpChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.sctp.SctpChannel;
/*     */ import net.minecraft.util.io.netty.channel.sctp.SctpChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.sctp.SctpMessage;
/*     */ import net.minecraft.util.io.netty.channel.sctp.SctpNotificationHandler;
/*     */ import net.minecraft.util.io.netty.channel.sctp.SctpServerChannel;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NioSctpChannel
/*     */   extends AbstractNioMessageChannel
/*     */   implements SctpChannel
/*     */ {
/*  62 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   
/*  64 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioSctpChannel.class);
/*     */   
/*     */   private final SctpChannelConfig config;
/*     */   
/*     */   private final NotificationHandler<?> notificationHandler;
/*     */   
/*     */   private RecvByteBufAllocator.Handle allocHandle;
/*     */   
/*     */   private static SctpChannel newSctpChannel() {
/*     */     try {
/*  74 */       return SctpChannel.open();
/*  75 */     } catch (IOException e) {
/*  76 */       throw new ChannelException("Failed to open a sctp channel.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSctpChannel() {
/*  84 */     this(newSctpChannel());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSctpChannel(SctpChannel sctpChannel) {
/*  91 */     this((Channel)null, sctpChannel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSctpChannel(Channel parent, SctpChannel sctpChannel) {
/* 102 */     super(parent, sctpChannel, 1);
/*     */     try {
/* 104 */       sctpChannel.configureBlocking(false);
/* 105 */       this.config = (SctpChannelConfig)new DefaultSctpChannelConfig(this, sctpChannel);
/* 106 */       this.notificationHandler = (NotificationHandler<?>)new SctpNotificationHandler(this);
/* 107 */     } catch (IOException e) {
/*     */       try {
/* 109 */         sctpChannel.close();
/* 110 */       } catch (IOException e2) {
/* 111 */         if (logger.isWarnEnabled()) {
/* 112 */           logger.warn("Failed to close a partially initialized sctp channel.", e2);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 117 */       throw new ChannelException("Failed to enter non-blocking mode.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 123 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 128 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannel parent() {
/* 133 */     return (SctpServerChannel)super.parent();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 138 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public Association association() {
/*     */     try {
/* 144 */       return javaChannel().association();
/* 145 */     } catch (IOException e) {
/* 146 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<InetSocketAddress> allLocalAddresses() {
/*     */     try {
/* 153 */       Set<SocketAddress> allLocalAddresses = javaChannel().getAllLocalAddresses();
/* 154 */       Set<InetSocketAddress> addresses = new LinkedHashSet<InetSocketAddress>(allLocalAddresses.size());
/* 155 */       for (SocketAddress socketAddress : allLocalAddresses) {
/* 156 */         addresses.add((InetSocketAddress)socketAddress);
/*     */       }
/* 158 */       return addresses;
/* 159 */     } catch (Throwable t) {
/* 160 */       return Collections.emptySet();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig config() {
/* 166 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<InetSocketAddress> allRemoteAddresses() {
/*     */     try {
/* 172 */       Set<SocketAddress> allLocalAddresses = javaChannel().getRemoteAddresses();
/* 173 */       Set<InetSocketAddress> addresses = new HashSet<InetSocketAddress>(allLocalAddresses.size());
/* 174 */       for (SocketAddress socketAddress : allLocalAddresses) {
/* 175 */         addresses.add((InetSocketAddress)socketAddress);
/*     */       }
/* 177 */       return addresses;
/* 178 */     } catch (Throwable t) {
/* 179 */       return Collections.emptySet();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected SctpChannel javaChannel() {
/* 185 */     return (SctpChannel)super.javaChannel();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 190 */     SctpChannel ch = javaChannel();
/* 191 */     return (ch.isOpen() && association() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/*     */     try {
/* 197 */       Iterator<SocketAddress> i = javaChannel().getAllLocalAddresses().iterator();
/* 198 */       if (i.hasNext()) {
/* 199 */         return i.next();
/*     */       }
/* 201 */     } catch (IOException e) {}
/*     */ 
/*     */     
/* 204 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/*     */     try {
/* 210 */       Iterator<SocketAddress> i = javaChannel().getRemoteAddresses().iterator();
/* 211 */       if (i.hasNext()) {
/* 212 */         return i.next();
/*     */       }
/* 214 */     } catch (IOException e) {}
/*     */ 
/*     */     
/* 217 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 222 */     javaChannel().bind(localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 227 */     if (localAddress != null) {
/* 228 */       javaChannel().bind(localAddress);
/*     */     }
/*     */     
/* 231 */     boolean success = false;
/*     */     try {
/* 233 */       boolean connected = javaChannel().connect(remoteAddress);
/* 234 */       if (!connected) {
/* 235 */         selectionKey().interestOps(8);
/*     */       }
/* 237 */       success = true;
/* 238 */       return connected;
/*     */     } finally {
/* 240 */       if (!success) {
/* 241 */         doClose();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doFinishConnect() throws Exception {
/* 248 */     if (!javaChannel().finishConnect()) {
/* 249 */       throw new Error();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 255 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 260 */     javaChannel().close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadMessages(List<Object> buf) throws Exception {
/* 265 */     SctpChannel ch = javaChannel();
/*     */     
/* 267 */     RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
/* 268 */     if (allocHandle == null) {
/* 269 */       this.allocHandle = allocHandle = config().getRecvByteBufAllocator().newHandle();
/*     */     }
/* 271 */     ByteBuf buffer = allocHandle.allocate(config().getAllocator());
/* 272 */     boolean free = true;
/*     */     try {
/* 274 */       ByteBuffer data = buffer.nioBuffer(buffer.writerIndex(), buffer.writableBytes());
/* 275 */       MessageInfo messageInfo = ch.receive(data, (Object)null, this.notificationHandler);
/* 276 */       if (messageInfo == null) {
/* 277 */         return 0;
/*     */       }
/*     */       
/* 280 */       data.flip();
/* 281 */       buf.add(new SctpMessage(messageInfo, buffer.writerIndex(buffer.writerIndex() + data.remaining())));
/* 282 */       free = false;
/* 283 */       return 1;
/* 284 */     } catch (Throwable cause) {
/* 285 */       PlatformDependent.throwException(cause);
/* 286 */       return -1;
/*     */     } finally {
/* 288 */       int bytesRead = buffer.readableBytes();
/* 289 */       allocHandle.record(bytesRead);
/* 290 */       if (free) {
/* 291 */         buffer.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
/*     */     ByteBuffer nioData;
/* 298 */     SctpMessage packet = (SctpMessage)msg;
/* 299 */     ByteBuf data = packet.content();
/* 300 */     int dataLen = data.readableBytes();
/* 301 */     if (dataLen == 0) {
/* 302 */       return true;
/*     */     }
/*     */     
/* 305 */     ByteBufAllocator alloc = alloc();
/* 306 */     boolean needsCopy = (data.nioBufferCount() != 1);
/* 307 */     if (!needsCopy && 
/* 308 */       !data.isDirect() && alloc.isDirectBufferPooled()) {
/* 309 */       needsCopy = true;
/*     */     }
/*     */ 
/*     */     
/* 313 */     if (!needsCopy) {
/* 314 */       nioData = data.nioBuffer();
/*     */     } else {
/* 316 */       data = alloc.directBuffer(dataLen).writeBytes(data);
/* 317 */       nioData = data.nioBuffer();
/*     */     } 
/*     */     
/* 320 */     MessageInfo mi = MessageInfo.createOutgoing(association(), null, packet.streamIdentifier());
/* 321 */     mi.payloadProtocolID(packet.protocolIdentifier());
/* 322 */     mi.streamNumber(packet.streamIdentifier());
/*     */     
/* 324 */     int writtenBytes = javaChannel().send(nioData, mi);
/*     */     
/* 326 */     boolean done = (writtenBytes > 0);
/* 327 */     if (needsCopy) {
/* 328 */       if (!done) {
/* 329 */         in.current(new SctpMessage(mi, data));
/*     */       } else {
/* 331 */         in.current(data);
/*     */       } 
/*     */     }
/* 334 */     return done;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bindAddress(InetAddress localAddress) {
/* 339 */     return bindAddress(localAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bindAddress(final InetAddress localAddress, final ChannelPromise promise) {
/* 344 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 346 */         javaChannel().bindAddress(localAddress);
/* 347 */         promise.setSuccess();
/* 348 */       } catch (Throwable t) {
/* 349 */         promise.setFailure(t);
/*     */       } 
/*     */     } else {
/* 352 */       eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 355 */               NioSctpChannel.this.bindAddress(localAddress, promise);
/*     */             }
/*     */           });
/*     */     } 
/* 359 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture unbindAddress(InetAddress localAddress) {
/* 364 */     return unbindAddress(localAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture unbindAddress(final InetAddress localAddress, final ChannelPromise promise) {
/* 369 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 371 */         javaChannel().unbindAddress(localAddress);
/* 372 */         promise.setSuccess();
/* 373 */       } catch (Throwable t) {
/* 374 */         promise.setFailure(t);
/*     */       } 
/*     */     } else {
/* 377 */       eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 380 */               NioSctpChannel.this.unbindAddress(localAddress, promise);
/*     */             }
/*     */           });
/*     */     } 
/* 384 */     return (ChannelFuture)promise;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\sctp\nio\NioSctpChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */