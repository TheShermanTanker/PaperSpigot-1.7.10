/*     */ package net.minecraft.util.io.netty.channel;
/*     */ 
/*     */ import java.net.SocketAddress;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.util.DefaultAttributeMap;
/*     */ import net.minecraft.util.io.netty.util.Recycler;
/*     */ import net.minecraft.util.io.netty.util.concurrent.EventExecutor;
/*     */ import net.minecraft.util.io.netty.util.concurrent.EventExecutorGroup;
/*     */ import net.minecraft.util.io.netty.util.internal.StringUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DefaultChannelHandlerContext
/*     */   extends DefaultAttributeMap
/*     */   implements ChannelHandlerContext
/*     */ {
/*     */   volatile DefaultChannelHandlerContext next;
/*     */   volatile DefaultChannelHandlerContext prev;
/*     */   private final boolean inbound;
/*     */   private final boolean outbound;
/*     */   private final AbstractChannel channel;
/*     */   private final DefaultChannelPipeline pipeline;
/*     */   private final String name;
/*     */   private final ChannelHandler handler;
/*     */   private boolean removed;
/*     */   final EventExecutor executor;
/*     */   private ChannelFuture succeededFuture;
/*     */   private Runnable invokeChannelReadCompleteTask;
/*     */   private Runnable invokeReadTask;
/*     */   private Runnable invokeFlushTask;
/*     */   private Runnable invokeChannelWritableStateChangedTask;
/*     */   
/*     */   DefaultChannelHandlerContext(DefaultChannelPipeline pipeline, EventExecutorGroup group, String name, ChannelHandler handler) {
/*  55 */     if (name == null) {
/*  56 */       throw new NullPointerException("name");
/*     */     }
/*  58 */     if (handler == null) {
/*  59 */       throw new NullPointerException("handler");
/*     */     }
/*     */     
/*  62 */     this.channel = pipeline.channel;
/*  63 */     this.pipeline = pipeline;
/*  64 */     this.name = name;
/*  65 */     this.handler = handler;
/*     */     
/*  67 */     if (group != null) {
/*     */ 
/*     */       
/*  70 */       EventExecutor childExecutor = pipeline.childExecutors.get(group);
/*  71 */       if (childExecutor == null) {
/*  72 */         childExecutor = group.next();
/*  73 */         pipeline.childExecutors.put(group, childExecutor);
/*     */       } 
/*  75 */       this.executor = childExecutor;
/*     */     } else {
/*  77 */       this.executor = null;
/*     */     } 
/*     */     
/*  80 */     this.inbound = handler instanceof ChannelInboundHandler;
/*  81 */     this.outbound = handler instanceof ChannelOutboundHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   void teardown() {
/*  86 */     EventExecutor executor = executor();
/*  87 */     if (executor.inEventLoop()) {
/*  88 */       teardown0();
/*     */     } else {
/*  90 */       executor.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/*  93 */               DefaultChannelHandlerContext.this.teardown0();
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   private void teardown0() {
/* 100 */     DefaultChannelHandlerContext prev = this.prev;
/* 101 */     if (prev != null) {
/* 102 */       synchronized (this.pipeline) {
/* 103 */         this.pipeline.remove0(this);
/*     */       } 
/* 105 */       prev.teardown();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Channel channel() {
/* 111 */     return this.channel;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelPipeline pipeline() {
/* 116 */     return this.pipeline;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator alloc() {
/* 121 */     return channel().config().getAllocator();
/*     */   }
/*     */ 
/*     */   
/*     */   public EventExecutor executor() {
/* 126 */     if (this.executor == null) {
/* 127 */       return channel().eventLoop();
/*     */     }
/* 129 */     return this.executor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelHandler handler() {
/* 135 */     return this.handler;
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/* 140 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelRegistered() {
/* 145 */     final DefaultChannelHandlerContext next = findContextInbound();
/* 146 */     EventExecutor executor = next.executor();
/* 147 */     if (executor.inEventLoop()) {
/* 148 */       next.invokeChannelRegistered();
/*     */     } else {
/* 150 */       executor.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 153 */               next.invokeChannelRegistered();
/*     */             }
/*     */           });
/*     */     } 
/* 157 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelRegistered() {
/*     */     try {
/* 162 */       ((ChannelInboundHandler)this.handler).channelRegistered(this);
/* 163 */     } catch (Throwable t) {
/* 164 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelUnregistered() {
/* 170 */     final DefaultChannelHandlerContext next = findContextInbound();
/* 171 */     EventExecutor executor = next.executor();
/* 172 */     if (executor.inEventLoop()) {
/* 173 */       next.invokeChannelUnregistered();
/*     */     } else {
/* 175 */       executor.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 178 */               next.invokeChannelUnregistered();
/*     */             }
/*     */           });
/*     */     } 
/* 182 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelUnregistered() {
/*     */     try {
/* 187 */       ((ChannelInboundHandler)this.handler).channelUnregistered(this);
/* 188 */     } catch (Throwable t) {
/* 189 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelActive() {
/* 195 */     final DefaultChannelHandlerContext next = findContextInbound();
/* 196 */     EventExecutor executor = next.executor();
/* 197 */     if (executor.inEventLoop()) {
/* 198 */       next.invokeChannelActive();
/*     */     } else {
/* 200 */       executor.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 203 */               next.invokeChannelActive();
/*     */             }
/*     */           });
/*     */     } 
/* 207 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelActive() {
/*     */     try {
/* 212 */       ((ChannelInboundHandler)this.handler).channelActive(this);
/* 213 */     } catch (Throwable t) {
/* 214 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelInactive() {
/* 220 */     final DefaultChannelHandlerContext next = findContextInbound();
/* 221 */     EventExecutor executor = next.executor();
/* 222 */     if (executor.inEventLoop()) {
/* 223 */       next.invokeChannelInactive();
/*     */     } else {
/* 225 */       executor.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 228 */               next.invokeChannelInactive();
/*     */             }
/*     */           });
/*     */     } 
/* 232 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelInactive() {
/*     */     try {
/* 237 */       ((ChannelInboundHandler)this.handler).channelInactive(this);
/* 238 */     } catch (Throwable t) {
/* 239 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireExceptionCaught(final Throwable cause) {
/* 245 */     if (cause == null) {
/* 246 */       throw new NullPointerException("cause");
/*     */     }
/*     */     
/* 249 */     final DefaultChannelHandlerContext next = this.next;
/*     */     
/* 251 */     EventExecutor executor = next.executor();
/* 252 */     if (executor.inEventLoop()) {
/* 253 */       next.invokeExceptionCaught(cause);
/*     */     } else {
/*     */       try {
/* 256 */         executor.execute(new Runnable()
/*     */             {
/*     */               public void run() {
/* 259 */                 next.invokeExceptionCaught(cause);
/*     */               }
/*     */             });
/* 262 */       } catch (Throwable t) {
/* 263 */         if (DefaultChannelPipeline.logger.isWarnEnabled()) {
/* 264 */           DefaultChannelPipeline.logger.warn("Failed to submit an exceptionCaught() event.", t);
/* 265 */           DefaultChannelPipeline.logger.warn("The exceptionCaught() event that was failed to submit was:", cause);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 270 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeExceptionCaught(Throwable cause) {
/*     */     try {
/* 275 */       this.handler.exceptionCaught(this, cause);
/* 276 */     } catch (Throwable t) {
/* 277 */       if (DefaultChannelPipeline.logger.isWarnEnabled()) {
/* 278 */         DefaultChannelPipeline.logger.warn("An exception was thrown by a user handler's exceptionCaught() method while handling the following exception:", cause);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireUserEventTriggered(final Object event) {
/* 287 */     if (event == null) {
/* 288 */       throw new NullPointerException("event");
/*     */     }
/*     */     
/* 291 */     final DefaultChannelHandlerContext next = findContextInbound();
/* 292 */     EventExecutor executor = next.executor();
/* 293 */     if (executor.inEventLoop()) {
/* 294 */       next.invokeUserEventTriggered(event);
/*     */     } else {
/* 296 */       executor.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 299 */               next.invokeUserEventTriggered(event);
/*     */             }
/*     */           });
/*     */     } 
/* 303 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeUserEventTriggered(Object event) {
/*     */     try {
/* 308 */       ((ChannelInboundHandler)this.handler).userEventTriggered(this, event);
/* 309 */     } catch (Throwable t) {
/* 310 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelRead(final Object msg) {
/* 316 */     if (msg == null) {
/* 317 */       throw new NullPointerException("msg");
/*     */     }
/*     */     
/* 320 */     final DefaultChannelHandlerContext next = findContextInbound();
/* 321 */     EventExecutor executor = next.executor();
/* 322 */     if (executor.inEventLoop()) {
/* 323 */       next.invokeChannelRead(msg);
/*     */     } else {
/* 325 */       executor.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 328 */               next.invokeChannelRead(msg);
/*     */             }
/*     */           });
/*     */     } 
/* 332 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelRead(Object msg) {
/*     */     try {
/* 337 */       ((ChannelInboundHandler)this.handler).channelRead(this, msg);
/* 338 */     } catch (Throwable t) {
/* 339 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelReadComplete() {
/* 345 */     final DefaultChannelHandlerContext next = findContextInbound();
/* 346 */     EventExecutor executor = next.executor();
/* 347 */     if (executor.inEventLoop()) {
/* 348 */       next.invokeChannelReadComplete();
/*     */     } else {
/* 350 */       Runnable task = next.invokeChannelReadCompleteTask;
/* 351 */       if (task == null) {
/* 352 */         next.invokeChannelReadCompleteTask = task = new Runnable()
/*     */           {
/*     */             public void run() {
/* 355 */               next.invokeChannelReadComplete();
/*     */             }
/*     */           };
/*     */       }
/* 359 */       executor.execute(task);
/*     */     } 
/* 361 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelReadComplete() {
/*     */     try {
/* 366 */       ((ChannelInboundHandler)this.handler).channelReadComplete(this);
/* 367 */     } catch (Throwable t) {
/* 368 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelWritabilityChanged() {
/* 374 */     final DefaultChannelHandlerContext next = findContextInbound();
/* 375 */     EventExecutor executor = next.executor();
/* 376 */     if (executor.inEventLoop()) {
/* 377 */       next.invokeChannelWritabilityChanged();
/*     */     } else {
/* 379 */       Runnable task = next.invokeChannelWritableStateChangedTask;
/* 380 */       if (task == null) {
/* 381 */         next.invokeChannelWritableStateChangedTask = task = new Runnable()
/*     */           {
/*     */             public void run() {
/* 384 */               next.invokeChannelWritabilityChanged();
/*     */             }
/*     */           };
/*     */       }
/* 388 */       executor.execute(task);
/*     */     } 
/* 390 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelWritabilityChanged() {
/*     */     try {
/* 395 */       ((ChannelInboundHandler)this.handler).channelWritabilityChanged(this);
/* 396 */     } catch (Throwable t) {
/* 397 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(SocketAddress localAddress) {
/* 403 */     return bind(localAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress) {
/* 408 */     return connect(remoteAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
/* 413 */     return connect(remoteAddress, localAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture disconnect() {
/* 418 */     return disconnect(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close() {
/* 423 */     return close(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture deregister() {
/* 428 */     return deregister(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(final SocketAddress localAddress, final ChannelPromise promise) {
/* 433 */     if (localAddress == null) {
/* 434 */       throw new NullPointerException("localAddress");
/*     */     }
/* 436 */     validatePromise(promise, false);
/*     */     
/* 438 */     final DefaultChannelHandlerContext next = findContextOutbound();
/* 439 */     EventExecutor executor = next.executor();
/* 440 */     if (executor.inEventLoop()) {
/* 441 */       next.invokeBind(localAddress, promise);
/*     */     } else {
/* 443 */       executor.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 446 */               next.invokeBind(localAddress, promise);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 451 */     return promise;
/*     */   }
/*     */   
/*     */   private void invokeBind(SocketAddress localAddress, ChannelPromise promise) {
/*     */     try {
/* 456 */       ((ChannelOutboundHandler)this.handler).bind(this, localAddress, promise);
/* 457 */     } catch (Throwable t) {
/* 458 */       notifyOutboundHandlerException(t, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
/* 464 */     return connect(remoteAddress, null, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
/* 471 */     if (remoteAddress == null) {
/* 472 */       throw new NullPointerException("remoteAddress");
/*     */     }
/* 474 */     validatePromise(promise, false);
/*     */     
/* 476 */     final DefaultChannelHandlerContext next = findContextOutbound();
/* 477 */     EventExecutor executor = next.executor();
/* 478 */     if (executor.inEventLoop()) {
/* 479 */       next.invokeConnect(remoteAddress, localAddress, promise);
/*     */     } else {
/* 481 */       executor.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 484 */               next.invokeConnect(remoteAddress, localAddress, promise);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 489 */     return promise;
/*     */   }
/*     */   
/*     */   private void invokeConnect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/*     */     try {
/* 494 */       ((ChannelOutboundHandler)this.handler).connect(this, remoteAddress, localAddress, promise);
/* 495 */     } catch (Throwable t) {
/* 496 */       notifyOutboundHandlerException(t, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture disconnect(final ChannelPromise promise) {
/* 502 */     validatePromise(promise, false);
/*     */     
/* 504 */     final DefaultChannelHandlerContext next = findContextOutbound();
/* 505 */     EventExecutor executor = next.executor();
/* 506 */     if (executor.inEventLoop()) {
/*     */ 
/*     */       
/* 509 */       if (!channel().metadata().hasDisconnect()) {
/* 510 */         next.invokeClose(promise);
/*     */       } else {
/* 512 */         next.invokeDisconnect(promise);
/*     */       } 
/*     */     } else {
/* 515 */       executor.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 518 */               if (!DefaultChannelHandlerContext.this.channel().metadata().hasDisconnect()) {
/* 519 */                 next.invokeClose(promise);
/*     */               } else {
/* 521 */                 next.invokeDisconnect(promise);
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 527 */     return promise;
/*     */   }
/*     */   
/*     */   private void invokeDisconnect(ChannelPromise promise) {
/*     */     try {
/* 532 */       ((ChannelOutboundHandler)this.handler).disconnect(this, promise);
/* 533 */     } catch (Throwable t) {
/* 534 */       notifyOutboundHandlerException(t, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close(final ChannelPromise promise) {
/* 540 */     validatePromise(promise, false);
/*     */     
/* 542 */     final DefaultChannelHandlerContext next = findContextOutbound();
/* 543 */     EventExecutor executor = next.executor();
/* 544 */     if (executor.inEventLoop()) {
/* 545 */       next.invokeClose(promise);
/*     */     } else {
/* 547 */       executor.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 550 */               next.invokeClose(promise);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 555 */     return promise;
/*     */   }
/*     */   
/*     */   private void invokeClose(ChannelPromise promise) {
/*     */     try {
/* 560 */       ((ChannelOutboundHandler)this.handler).close(this, promise);
/* 561 */     } catch (Throwable t) {
/* 562 */       notifyOutboundHandlerException(t, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture deregister(final ChannelPromise promise) {
/* 568 */     validatePromise(promise, false);
/*     */     
/* 570 */     final DefaultChannelHandlerContext next = findContextOutbound();
/* 571 */     EventExecutor executor = next.executor();
/* 572 */     if (executor.inEventLoop()) {
/* 573 */       next.invokeDeregister(promise);
/*     */     } else {
/* 575 */       executor.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 578 */               next.invokeDeregister(promise);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 583 */     return promise;
/*     */   }
/*     */   
/*     */   private void invokeDeregister(ChannelPromise promise) {
/*     */     try {
/* 588 */       ((ChannelOutboundHandler)this.handler).deregister(this, promise);
/* 589 */     } catch (Throwable t) {
/* 590 */       notifyOutboundHandlerException(t, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext read() {
/* 596 */     final DefaultChannelHandlerContext next = findContextOutbound();
/* 597 */     EventExecutor executor = next.executor();
/* 598 */     if (executor.inEventLoop()) {
/* 599 */       next.invokeRead();
/*     */     } else {
/* 601 */       Runnable task = next.invokeReadTask;
/* 602 */       if (task == null) {
/* 603 */         next.invokeReadTask = task = new Runnable()
/*     */           {
/*     */             public void run() {
/* 606 */               next.invokeRead();
/*     */             }
/*     */           };
/*     */       }
/* 610 */       executor.execute(task);
/*     */     } 
/*     */     
/* 613 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeRead() {
/*     */     try {
/* 618 */       ((ChannelOutboundHandler)this.handler).read(this);
/* 619 */     } catch (Throwable t) {
/* 620 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture write(Object msg) {
/* 626 */     return write(msg, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture write(Object msg, ChannelPromise promise) {
/* 631 */     if (msg == null) {
/* 632 */       throw new NullPointerException("msg");
/*     */     }
/*     */     
/* 635 */     validatePromise(promise, true);
/*     */     
/* 637 */     write(msg, false, promise);
/*     */     
/* 639 */     return promise;
/*     */   }
/*     */   
/*     */   private void invokeWrite(Object msg, ChannelPromise promise) {
/*     */     try {
/* 644 */       ((ChannelOutboundHandler)this.handler).write(this, msg, promise);
/* 645 */     } catch (Throwable t) {
/* 646 */       notifyOutboundHandlerException(t, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext flush() {
/* 652 */     final DefaultChannelHandlerContext next = findContextOutbound();
/* 653 */     EventExecutor executor = next.executor();
/* 654 */     if (executor.inEventLoop()) {
/* 655 */       next.invokeFlush();
/*     */     } else {
/* 657 */       Runnable task = next.invokeFlushTask;
/* 658 */       if (task == null) {
/* 659 */         next.invokeFlushTask = task = new Runnable()
/*     */           {
/*     */             public void run() {
/* 662 */               next.invokeFlush();
/*     */             }
/*     */           };
/*     */       }
/* 666 */       executor.execute(task);
/*     */     } 
/*     */     
/* 669 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeFlush() {
/*     */     try {
/* 674 */       ((ChannelOutboundHandler)this.handler).flush(this);
/* 675 */     } catch (Throwable t) {
/* 676 */       notifyHandlerException(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
/* 682 */     if (msg == null) {
/* 683 */       throw new NullPointerException("msg");
/*     */     }
/*     */     
/* 686 */     validatePromise(promise, true);
/*     */     
/* 688 */     write(msg, true, promise);
/*     */     
/* 690 */     return promise;
/*     */   }
/*     */ 
/*     */   
/*     */   private void write(Object msg, boolean flush, ChannelPromise promise) {
/* 695 */     DefaultChannelHandlerContext next = findContextOutbound();
/* 696 */     EventExecutor executor = next.executor();
/* 697 */     if (executor.inEventLoop()) {
/* 698 */       next.invokeWrite(msg, promise);
/* 699 */       if (flush) {
/* 700 */         next.invokeFlush();
/*     */       }
/*     */     } else {
/* 703 */       int size = this.channel.estimatorHandle().size(msg);
/* 704 */       if (size > 0) {
/* 705 */         ChannelOutboundBuffer buffer = this.channel.unsafe().outboundBuffer();
/*     */         
/* 707 */         if (buffer != null) {
/* 708 */           buffer.incrementPendingOutboundBytes(size);
/*     */         }
/*     */       } 
/* 711 */       executor.execute(WriteTask.newInstance(next, msg, size, flush, promise));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture writeAndFlush(Object msg) {
/* 717 */     return writeAndFlush(msg, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void notifyOutboundHandlerException(Throwable cause, ChannelPromise promise) {
/* 723 */     if (promise instanceof VoidChannelPromise) {
/*     */       return;
/*     */     }
/*     */     
/* 727 */     if (!promise.tryFailure(cause) && 
/* 728 */       DefaultChannelPipeline.logger.isWarnEnabled()) {
/* 729 */       DefaultChannelPipeline.logger.warn("Failed to fail the promise because it's done already: {}", promise, cause);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void notifyHandlerException(Throwable cause) {
/* 735 */     if (inExceptionCaught(cause)) {
/* 736 */       if (DefaultChannelPipeline.logger.isWarnEnabled()) {
/* 737 */         DefaultChannelPipeline.logger.warn("An exception was thrown by a user handler while handling an exceptionCaught event", cause);
/*     */       }
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 744 */     invokeExceptionCaught(cause);
/*     */   }
/*     */   
/*     */   private static boolean inExceptionCaught(Throwable cause) {
/*     */     do {
/* 749 */       StackTraceElement[] trace = cause.getStackTrace();
/* 750 */       if (trace != null) {
/* 751 */         for (StackTraceElement t : trace) {
/* 752 */           if (t == null) {
/*     */             break;
/*     */           }
/* 755 */           if ("exceptionCaught".equals(t.getMethodName())) {
/* 756 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 761 */       cause = cause.getCause();
/* 762 */     } while (cause != null);
/*     */     
/* 764 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelPromise newPromise() {
/* 769 */     return new DefaultChannelPromise(channel(), executor());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelProgressivePromise newProgressivePromise() {
/* 774 */     return new DefaultChannelProgressivePromise(channel(), executor());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture newSucceededFuture() {
/* 779 */     ChannelFuture succeededFuture = this.succeededFuture;
/* 780 */     if (succeededFuture == null) {
/* 781 */       this.succeededFuture = succeededFuture = new SucceededChannelFuture(channel(), executor());
/*     */     }
/* 783 */     return succeededFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture newFailedFuture(Throwable cause) {
/* 788 */     return new FailedChannelFuture(channel(), executor(), cause);
/*     */   }
/*     */   
/*     */   private void validatePromise(ChannelPromise promise, boolean allowVoidPromise) {
/* 792 */     if (promise == null) {
/* 793 */       throw new NullPointerException("promise");
/*     */     }
/*     */     
/* 796 */     if (promise.isDone()) {
/* 797 */       throw new IllegalArgumentException("promise already done: " + promise);
/*     */     }
/*     */     
/* 800 */     if (promise.channel() != channel()) {
/* 801 */       throw new IllegalArgumentException(String.format("promise.channel does not match: %s (expected: %s)", new Object[] { promise.channel(), channel() }));
/*     */     }
/*     */ 
/*     */     
/* 805 */     if (promise.getClass() == DefaultChannelPromise.class) {
/*     */       return;
/*     */     }
/*     */     
/* 809 */     if (!allowVoidPromise && promise instanceof VoidChannelPromise) {
/* 810 */       throw new IllegalArgumentException(StringUtil.simpleClassName(VoidChannelPromise.class) + " not allowed for this operation");
/*     */     }
/*     */ 
/*     */     
/* 814 */     if (promise instanceof AbstractChannel.CloseFuture) {
/* 815 */       throw new IllegalArgumentException(StringUtil.simpleClassName(AbstractChannel.CloseFuture.class) + " not allowed in a pipeline");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private DefaultChannelHandlerContext findContextInbound() {
/* 821 */     DefaultChannelHandlerContext ctx = this;
/*     */     while (true) {
/* 823 */       ctx = ctx.next;
/* 824 */       if (ctx.inbound)
/* 825 */         return ctx; 
/*     */     } 
/*     */   }
/*     */   private DefaultChannelHandlerContext findContextOutbound() {
/* 829 */     DefaultChannelHandlerContext ctx = this;
/*     */     while (true) {
/* 831 */       ctx = ctx.prev;
/* 832 */       if (ctx.outbound)
/* 833 */         return ctx; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ChannelPromise voidPromise() {
/* 838 */     return this.channel.voidPromise();
/*     */   }
/*     */   
/*     */   void setRemoved() {
/* 842 */     this.removed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRemoved() {
/* 847 */     return this.removed;
/*     */   }
/*     */   
/*     */   static final class WriteTask implements Runnable {
/*     */     private DefaultChannelHandlerContext ctx;
/*     */     private Object msg;
/*     */     private ChannelPromise promise;
/*     */     private int size;
/*     */     private boolean flush;
/*     */     
/* 857 */     private static final Recycler<WriteTask> RECYCLER = new Recycler<WriteTask>()
/*     */       {
/*     */         protected DefaultChannelHandlerContext.WriteTask newObject(Recycler.Handle handle) {
/* 860 */           return new DefaultChannelHandlerContext.WriteTask(handle);
/*     */         }
/*     */       };
/*     */     private final Recycler.Handle handle;
/*     */     
/*     */     private static WriteTask newInstance(DefaultChannelHandlerContext ctx, Object msg, int size, boolean flush, ChannelPromise promise) {
/* 866 */       WriteTask task = (WriteTask)RECYCLER.get();
/* 867 */       task.ctx = ctx;
/* 868 */       task.msg = msg;
/* 869 */       task.promise = promise;
/* 870 */       task.size = size;
/* 871 */       task.flush = flush;
/* 872 */       return task;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private WriteTask(Recycler.Handle handle) {
/* 878 */       this.handle = handle;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/* 884 */         if (this.size > 0) {
/* 885 */           ChannelOutboundBuffer buffer = this.ctx.channel.unsafe().outboundBuffer();
/*     */           
/* 887 */           if (buffer != null) {
/* 888 */             buffer.decrementPendingOutboundBytes(this.size);
/*     */           }
/*     */         } 
/* 891 */         this.ctx.invokeWrite(this.msg, this.promise);
/* 892 */         if (this.flush) {
/* 893 */           this.ctx.invokeFlush();
/*     */         }
/*     */       } finally {
/*     */         
/* 897 */         this.ctx = null;
/* 898 */         this.msg = null;
/* 899 */         this.promise = null;
/*     */         
/* 901 */         RECYCLER.recycle(this, this.handle);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\DefaultChannelHandlerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */