/*     */ package net.minecraft.util.io.netty.channel;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.NotYetConnectedException;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.util.DefaultAttributeMap;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCountUtil;
/*     */ import net.minecraft.util.io.netty.util.concurrent.Promise;
/*     */ import net.minecraft.util.io.netty.util.internal.EmptyArrays;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
/*     */ import net.minecraft.util.io.netty.util.internal.ThreadLocalRandom;
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
/*     */ public abstract class AbstractChannel
/*     */   extends DefaultAttributeMap
/*     */   implements Channel
/*     */ {
/*  39 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractChannel.class);
/*     */   
/*  41 */   static final ClosedChannelException CLOSED_CHANNEL_EXCEPTION = new ClosedChannelException();
/*  42 */   static final NotYetConnectedException NOT_YET_CONNECTED_EXCEPTION = new NotYetConnectedException();
/*     */   
/*     */   static {
/*  45 */     CLOSED_CHANNEL_EXCEPTION.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*  46 */     NOT_YET_CONNECTED_EXCEPTION.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*     */   }
/*     */ 
/*     */   
/*     */   private MessageSizeEstimator.Handle estimatorHandle;
/*     */   private final Channel parent;
/*  52 */   private final long hashCode = ThreadLocalRandom.current().nextLong();
/*     */   private final Channel.Unsafe unsafe;
/*     */   private final DefaultChannelPipeline pipeline;
/*  55 */   private final ChannelFuture succeededFuture = new SucceededChannelFuture(this, null);
/*  56 */   private final VoidChannelPromise voidPromise = new VoidChannelPromise(this, true);
/*  57 */   private final VoidChannelPromise unsafeVoidPromise = new VoidChannelPromise(this, false);
/*  58 */   private final CloseFuture closeFuture = new CloseFuture(this);
/*     */ 
/*     */   
/*     */   private volatile SocketAddress localAddress;
/*     */ 
/*     */   
/*     */   private volatile SocketAddress remoteAddress;
/*     */   
/*     */   private volatile EventLoop eventLoop;
/*     */   
/*     */   private volatile boolean registered;
/*     */   
/*     */   private boolean strValActive;
/*     */   
/*     */   private String strVal;
/*     */ 
/*     */   
/*     */   protected AbstractChannel(Channel parent) {
/*  76 */     this.parent = parent;
/*  77 */     this.unsafe = newUnsafe();
/*  78 */     this.pipeline = new DefaultChannelPipeline(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWritable() {
/*  83 */     ChannelOutboundBuffer buf = this.unsafe.outboundBuffer();
/*  84 */     return (buf != null && buf.getWritable());
/*     */   }
/*     */ 
/*     */   
/*     */   public Channel parent() {
/*  89 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelPipeline pipeline() {
/*  94 */     return this.pipeline;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator alloc() {
/*  99 */     return config().getAllocator();
/*     */   }
/*     */ 
/*     */   
/*     */   public EventLoop eventLoop() {
/* 104 */     EventLoop eventLoop = this.eventLoop;
/* 105 */     if (eventLoop == null) {
/* 106 */       throw new IllegalStateException("channel not registered to an event loop");
/*     */     }
/* 108 */     return eventLoop;
/*     */   }
/*     */ 
/*     */   
/*     */   public SocketAddress localAddress() {
/* 113 */     SocketAddress localAddress = this.localAddress;
/* 114 */     if (localAddress == null) {
/*     */       try {
/* 116 */         this.localAddress = localAddress = unsafe().localAddress();
/* 117 */       } catch (Throwable t) {
/*     */         
/* 119 */         return null;
/*     */       } 
/*     */     }
/* 122 */     return localAddress;
/*     */   }
/*     */   
/*     */   protected void invalidateLocalAddress() {
/* 126 */     this.localAddress = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public SocketAddress remoteAddress() {
/* 131 */     SocketAddress remoteAddress = this.remoteAddress;
/* 132 */     if (remoteAddress == null) {
/*     */       try {
/* 134 */         this.remoteAddress = remoteAddress = unsafe().remoteAddress();
/* 135 */       } catch (Throwable t) {
/*     */         
/* 137 */         return null;
/*     */       } 
/*     */     }
/* 140 */     return remoteAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void invalidateRemoteAddress() {
/* 147 */     this.remoteAddress = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRegistered() {
/* 152 */     return this.registered;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(SocketAddress localAddress) {
/* 157 */     return this.pipeline.bind(localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress) {
/* 162 */     return this.pipeline.connect(remoteAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
/* 167 */     return this.pipeline.connect(remoteAddress, localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture disconnect() {
/* 172 */     return this.pipeline.disconnect();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close() {
/* 177 */     return this.pipeline.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture deregister() {
/* 182 */     return this.pipeline.deregister();
/*     */   }
/*     */ 
/*     */   
/*     */   public Channel flush() {
/* 187 */     this.pipeline.flush();
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
/* 193 */     return this.pipeline.bind(localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
/* 198 */     return this.pipeline.connect(remoteAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 203 */     return this.pipeline.connect(remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture disconnect(ChannelPromise promise) {
/* 208 */     return this.pipeline.disconnect(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close(ChannelPromise promise) {
/* 213 */     return this.pipeline.close(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture deregister(ChannelPromise promise) {
/* 218 */     return this.pipeline.deregister(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public Channel read() {
/* 223 */     this.pipeline.read();
/* 224 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture write(Object msg) {
/* 229 */     return this.pipeline.write(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture write(Object msg, ChannelPromise promise) {
/* 234 */     return this.pipeline.write(msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture writeAndFlush(Object msg) {
/* 239 */     return this.pipeline.writeAndFlush(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
/* 244 */     return this.pipeline.writeAndFlush(msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelPromise newPromise() {
/* 249 */     return new DefaultChannelPromise(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelProgressivePromise newProgressivePromise() {
/* 254 */     return new DefaultChannelProgressivePromise(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture newSucceededFuture() {
/* 259 */     return this.succeededFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture newFailedFuture(Throwable cause) {
/* 264 */     return new FailedChannelFuture(this, null, cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture closeFuture() {
/* 269 */     return this.closeFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public Channel.Unsafe unsafe() {
/* 274 */     return this.unsafe;
/*     */   }
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
/*     */   public final int hashCode() {
/* 287 */     return (int)this.hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean equals(Object o) {
/* 296 */     return (this == o);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int compareTo(Channel o) {
/* 301 */     if (this == o) {
/* 302 */       return 0;
/*     */     }
/*     */     
/* 305 */     long ret = this.hashCode - o.hashCode();
/* 306 */     if (ret > 0L) {
/* 307 */       return 1;
/*     */     }
/* 309 */     if (ret < 0L) {
/* 310 */       return -1;
/*     */     }
/*     */     
/* 313 */     ret = (System.identityHashCode(this) - System.identityHashCode(o));
/* 314 */     if (ret != 0L) {
/* 315 */       return (int)ret;
/*     */     }
/*     */ 
/*     */     
/* 319 */     throw new Error();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 330 */     boolean active = isActive();
/* 331 */     if (this.strValActive == active && this.strVal != null) {
/* 332 */       return this.strVal;
/*     */     }
/*     */     
/* 335 */     SocketAddress remoteAddr = remoteAddress();
/* 336 */     SocketAddress localAddr = localAddress();
/* 337 */     if (remoteAddr != null) {
/*     */       SocketAddress srcAddr, dstAddr;
/*     */       
/* 340 */       if (this.parent == null) {
/* 341 */         srcAddr = localAddr;
/* 342 */         dstAddr = remoteAddr;
/*     */       } else {
/* 344 */         srcAddr = remoteAddr;
/* 345 */         dstAddr = localAddr;
/*     */       } 
/* 347 */       this.strVal = String.format("[id: 0x%08x, %s %s %s]", new Object[] { Integer.valueOf((int)this.hashCode), srcAddr, active ? "=>" : ":>", dstAddr });
/* 348 */     } else if (localAddr != null) {
/* 349 */       this.strVal = String.format("[id: 0x%08x, %s]", new Object[] { Integer.valueOf((int)this.hashCode), localAddr });
/*     */     } else {
/* 351 */       this.strVal = String.format("[id: 0x%08x]", new Object[] { Integer.valueOf((int)this.hashCode) });
/*     */     } 
/*     */     
/* 354 */     this.strValActive = active;
/* 355 */     return this.strVal;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ChannelPromise voidPromise() {
/* 360 */     return this.voidPromise;
/*     */   }
/*     */   
/*     */   final MessageSizeEstimator.Handle estimatorHandle() {
/* 364 */     if (this.estimatorHandle == null) {
/* 365 */       this.estimatorHandle = config().getMessageSizeEstimator().newHandle();
/*     */     }
/* 367 */     return this.estimatorHandle;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract class AbstractUnsafe
/*     */     implements Channel.Unsafe
/*     */   {
/* 375 */     private ChannelOutboundBuffer outboundBuffer = ChannelOutboundBuffer.newInstance(AbstractChannel.this);
/*     */     
/*     */     private boolean inFlush0;
/*     */     
/*     */     public final ChannelOutboundBuffer outboundBuffer() {
/* 380 */       return this.outboundBuffer;
/*     */     }
/*     */ 
/*     */     
/*     */     public final SocketAddress localAddress() {
/* 385 */       return AbstractChannel.this.localAddress0();
/*     */     }
/*     */ 
/*     */     
/*     */     public final SocketAddress remoteAddress() {
/* 390 */       return AbstractChannel.this.remoteAddress0();
/*     */     }
/*     */ 
/*     */     
/*     */     public final void register(EventLoop eventLoop, final ChannelPromise promise) {
/* 395 */       if (eventLoop == null) {
/* 396 */         throw new NullPointerException("eventLoop");
/*     */       }
/* 398 */       if (AbstractChannel.this.isRegistered()) {
/* 399 */         promise.setFailure(new IllegalStateException("registered to an event loop already"));
/*     */         return;
/*     */       } 
/* 402 */       if (!AbstractChannel.this.isCompatible(eventLoop)) {
/* 403 */         promise.setFailure(new IllegalStateException("incompatible event loop type: " + eventLoop.getClass().getName()));
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 408 */       AbstractChannel.this.eventLoop = eventLoop;
/*     */       
/* 410 */       if (eventLoop.inEventLoop()) {
/* 411 */         register0(promise);
/*     */       } else {
/*     */         try {
/* 414 */           eventLoop.execute(new Runnable()
/*     */               {
/*     */                 public void run() {
/* 417 */                   AbstractChannel.AbstractUnsafe.this.register0(promise);
/*     */                 }
/*     */               });
/* 420 */         } catch (Throwable t) {
/* 421 */           AbstractChannel.logger.warn("Force-closing a channel whose registration task was not accepted by an event loop: {}", AbstractChannel.this, t);
/*     */ 
/*     */           
/* 424 */           closeForcibly();
/* 425 */           AbstractChannel.this.closeFuture.setClosed();
/* 426 */           promise.setFailure(t);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void register0(ChannelPromise promise) {
/*     */       try {
/* 435 */         if (!ensureOpen(promise)) {
/*     */           return;
/*     */         }
/* 438 */         AbstractChannel.this.doRegister();
/* 439 */         AbstractChannel.this.registered = true;
/* 440 */         promise.setSuccess();
/* 441 */         AbstractChannel.this.pipeline.fireChannelRegistered();
/* 442 */         if (AbstractChannel.this.isActive()) {
/* 443 */           AbstractChannel.this.pipeline.fireChannelActive();
/*     */         }
/* 445 */       } catch (Throwable t) {
/*     */         
/* 447 */         closeForcibly();
/* 448 */         AbstractChannel.this.closeFuture.setClosed();
/* 449 */         if (!promise.tryFailure(t)) {
/* 450 */           AbstractChannel.logger.warn("Tried to fail the registration promise, but it is complete already. Swallowing the cause of the registration failure:", t);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void bind(SocketAddress localAddress, ChannelPromise promise) {
/* 459 */       if (!ensureOpen(promise)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 464 */       if (!PlatformDependent.isWindows() && !PlatformDependent.isRoot() && Boolean.TRUE.equals(AbstractChannel.this.config().getOption(ChannelOption.SO_BROADCAST)) && localAddress instanceof InetSocketAddress && !((InetSocketAddress)localAddress).getAddress().isAnyLocalAddress())
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 470 */         AbstractChannel.logger.warn("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; binding to a non-wildcard address (" + localAddress + ") anyway as requested.");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 476 */       boolean wasActive = AbstractChannel.this.isActive();
/*     */       try {
/* 478 */         AbstractChannel.this.doBind(localAddress);
/* 479 */       } catch (Throwable t) {
/* 480 */         closeIfClosed();
/* 481 */         promise.setFailure(t);
/*     */         return;
/*     */       } 
/* 484 */       if (!wasActive && AbstractChannel.this.isActive()) {
/* 485 */         invokeLater(new Runnable()
/*     */             {
/*     */               public void run() {
/* 488 */                 AbstractChannel.this.pipeline.fireChannelActive();
/*     */               }
/*     */             });
/*     */       }
/* 492 */       promise.setSuccess();
/*     */     }
/*     */ 
/*     */     
/*     */     public final void disconnect(ChannelPromise promise) {
/* 497 */       boolean wasActive = AbstractChannel.this.isActive();
/*     */       try {
/* 499 */         AbstractChannel.this.doDisconnect();
/* 500 */       } catch (Throwable t) {
/* 501 */         closeIfClosed();
/* 502 */         promise.setFailure(t);
/*     */         return;
/*     */       } 
/* 505 */       if (wasActive && !AbstractChannel.this.isActive()) {
/* 506 */         invokeLater(new Runnable()
/*     */             {
/*     */               public void run() {
/* 509 */                 AbstractChannel.this.pipeline.fireChannelInactive();
/*     */               }
/*     */             });
/*     */       }
/* 513 */       closeIfClosed();
/* 514 */       promise.setSuccess();
/*     */     }
/*     */ 
/*     */     
/*     */     public final void close(final ChannelPromise promise) {
/* 519 */       if (this.inFlush0) {
/* 520 */         invokeLater(new Runnable()
/*     */             {
/*     */               public void run() {
/* 523 */                 AbstractChannel.AbstractUnsafe.this.close(promise);
/*     */               }
/*     */             });
/*     */         
/*     */         return;
/*     */       } 
/* 529 */       if (AbstractChannel.this.closeFuture.isDone()) {
/*     */         
/* 531 */         promise.setSuccess();
/*     */         
/*     */         return;
/*     */       } 
/* 535 */       boolean wasActive = AbstractChannel.this.isActive();
/* 536 */       ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
/* 537 */       this.outboundBuffer = null;
/*     */       
/*     */       try {
/* 540 */         AbstractChannel.this.doClose();
/* 541 */         AbstractChannel.this.closeFuture.setClosed();
/* 542 */         promise.setSuccess();
/* 543 */       } catch (Throwable t) {
/* 544 */         AbstractChannel.this.closeFuture.setClosed();
/* 545 */         promise.setFailure(t);
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 550 */         outboundBuffer.failFlushed(AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
/* 551 */         outboundBuffer.close(AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
/*     */       } finally {
/*     */         
/* 554 */         if (wasActive && !AbstractChannel.this.isActive()) {
/* 555 */           invokeLater(new Runnable()
/*     */               {
/*     */                 public void run() {
/* 558 */                   AbstractChannel.this.pipeline.fireChannelInactive();
/*     */                 }
/*     */               });
/*     */         }
/*     */         
/* 563 */         deregister(voidPromise());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public final void closeForcibly() {
/*     */       try {
/* 570 */         AbstractChannel.this.doClose();
/* 571 */       } catch (Exception e) {
/* 572 */         AbstractChannel.logger.warn("Failed to close a channel.", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public final void deregister(ChannelPromise promise) {
/* 578 */       if (!AbstractChannel.this.registered) {
/* 579 */         promise.setSuccess();
/*     */         
/*     */         return;
/*     */       } 
/*     */       try {
/* 584 */         AbstractChannel.this.doDeregister();
/* 585 */       } catch (Throwable t) {
/* 586 */         AbstractChannel.logger.warn("Unexpected exception occurred while deregistering a channel.", t);
/*     */       } finally {
/* 588 */         if (AbstractChannel.this.registered) {
/* 589 */           AbstractChannel.this.registered = false;
/* 590 */           promise.setSuccess();
/* 591 */           invokeLater(new Runnable()
/*     */               {
/*     */                 public void run() {
/* 594 */                   AbstractChannel.this.pipeline.fireChannelUnregistered();
/*     */                 }
/*     */               });
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 601 */           promise.setSuccess();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void beginRead() {
/* 608 */       if (!AbstractChannel.this.isActive()) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 613 */         AbstractChannel.this.doBeginRead();
/* 614 */       } catch (Exception e) {
/* 615 */         invokeLater(new Runnable()
/*     */             {
/*     */               public void run() {
/* 618 */                 AbstractChannel.this.pipeline.fireExceptionCaught(e);
/*     */               }
/*     */             });
/* 621 */         close(voidPromise());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(Object msg, ChannelPromise promise) {
/* 627 */       if (!AbstractChannel.this.isActive()) {
/*     */         
/* 629 */         if (AbstractChannel.this.isOpen()) {
/* 630 */           promise.tryFailure(AbstractChannel.NOT_YET_CONNECTED_EXCEPTION);
/*     */         } else {
/* 632 */           promise.tryFailure(AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
/*     */         } 
/*     */         
/* 635 */         ReferenceCountUtil.release(msg);
/*     */       } else {
/* 637 */         this.outboundBuffer.addMessage(msg, promise);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void flush() {
/* 643 */       ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
/* 644 */       if (outboundBuffer == null) {
/*     */         return;
/*     */       }
/*     */       
/* 648 */       outboundBuffer.addFlush();
/* 649 */       flush0();
/*     */     }
/*     */     
/*     */     protected void flush0() {
/* 653 */       if (this.inFlush0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 658 */       ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
/* 659 */       if (outboundBuffer == null || outboundBuffer.isEmpty()) {
/*     */         return;
/*     */       }
/*     */       
/* 663 */       this.inFlush0 = true;
/*     */ 
/*     */       
/* 666 */       if (!AbstractChannel.this.isActive()) {
/*     */         try {
/* 668 */           if (AbstractChannel.this.isOpen()) {
/* 669 */             outboundBuffer.failFlushed(AbstractChannel.NOT_YET_CONNECTED_EXCEPTION);
/*     */           } else {
/* 671 */             outboundBuffer.failFlushed(AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
/*     */           } 
/*     */         } finally {
/* 674 */           this.inFlush0 = false;
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */       try {
/* 680 */         AbstractChannel.this.doWrite(outboundBuffer);
/* 681 */       } catch (Throwable t) {
/* 682 */         outboundBuffer.failFlushed(t);
/* 683 */         if (t instanceof IOException) {
/* 684 */           close(voidPromise());
/*     */         }
/*     */       } finally {
/* 687 */         this.inFlush0 = false;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelPromise voidPromise() {
/* 693 */       return AbstractChannel.this.unsafeVoidPromise;
/*     */     }
/*     */     
/*     */     protected final boolean ensureOpen(ChannelPromise promise) {
/* 697 */       if (AbstractChannel.this.isOpen()) {
/* 698 */         return true;
/*     */       }
/*     */       
/* 701 */       promise.setFailure(AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
/* 702 */       return false;
/*     */     }
/*     */     
/*     */     protected final void closeIfClosed() {
/* 706 */       if (AbstractChannel.this.isOpen()) {
/*     */         return;
/*     */       }
/* 709 */       close(voidPromise());
/*     */     }
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
/*     */     private void invokeLater(Runnable task) {
/* 724 */       AbstractChannel.this.eventLoop().execute(task);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doRegister() throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDeregister() throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract AbstractUnsafe newUnsafe();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isCompatible(EventLoop paramEventLoop);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract SocketAddress localAddress0();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract SocketAddress remoteAddress0();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doBind(SocketAddress paramSocketAddress) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doDisconnect() throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doClose() throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void checkEOF(FileRegion region) throws IOException {
/* 787 */     if (region.transfered() < region.count())
/* 788 */       throw new EOFException("Expected to be able to write " + region.count() + " bytes, but only wrote " + region.transfered()); 
/*     */   }
/*     */   
/*     */   protected abstract void doBeginRead() throws Exception;
/*     */   
/*     */   protected abstract void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception;
/*     */   
/*     */   static final class CloseFuture extends DefaultChannelPromise {
/*     */     CloseFuture(AbstractChannel ch) {
/* 797 */       super(ch);
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelPromise setSuccess() {
/* 802 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelPromise setFailure(Throwable cause) {
/* 807 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean trySuccess() {
/* 812 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryFailure(Throwable cause) {
/* 817 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */     boolean setClosed() {
/* 821 */       return super.trySuccess();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\AbstractChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */