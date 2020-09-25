/*     */ package net.minecraft.util.io.netty.handler.timeout;
/*     */ 
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.util.io.netty.channel.ChannelDuplexHandler;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.util.concurrent.EventExecutor;
/*     */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*     */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
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
/*     */ public class IdleStateHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*     */   private final long readerIdleTimeMillis;
/*     */   private final long writerIdleTimeMillis;
/*     */   private final long allIdleTimeMillis;
/*     */   volatile ScheduledFuture<?> readerIdleTimeout;
/*     */   volatile long lastReadTime;
/*     */   private boolean firstReaderIdleEvent = true;
/*     */   volatile ScheduledFuture<?> writerIdleTimeout;
/*     */   volatile long lastWriteTime;
/*     */   private boolean firstWriterIdleEvent = true;
/*     */   volatile ScheduledFuture<?> allIdleTimeout;
/*     */   private boolean firstAllIdleEvent = true;
/*     */   private volatile int state;
/*     */   
/*     */   public IdleStateHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
/* 137 */     this(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds, TimeUnit.SECONDS);
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
/*     */   public IdleStateHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
/* 163 */     if (unit == null) {
/* 164 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/* 167 */     if (readerIdleTime <= 0L) {
/* 168 */       this.readerIdleTimeMillis = 0L;
/*     */     } else {
/* 170 */       this.readerIdleTimeMillis = Math.max(unit.toMillis(readerIdleTime), 1L);
/*     */     } 
/* 172 */     if (writerIdleTime <= 0L) {
/* 173 */       this.writerIdleTimeMillis = 0L;
/*     */     } else {
/* 175 */       this.writerIdleTimeMillis = Math.max(unit.toMillis(writerIdleTime), 1L);
/*     */     } 
/* 177 */     if (allIdleTime <= 0L) {
/* 178 */       this.allIdleTimeMillis = 0L;
/*     */     } else {
/* 180 */       this.allIdleTimeMillis = Math.max(unit.toMillis(allIdleTime), 1L);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getReaderIdleTimeInMillis() {
/* 189 */     return this.readerIdleTimeMillis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getWriterIdleTimeInMillis() {
/* 197 */     return this.writerIdleTimeMillis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getAllIdleTimeInMillis() {
/* 205 */     return this.allIdleTimeMillis;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 210 */     if ((ctx.channel().isActive() & ctx.channel().isRegistered()) != 0)
/*     */     {
/*     */       
/* 213 */       initialize(ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 222 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
/* 228 */     if (ctx.channel().isActive()) {
/* 229 */       initialize(ctx);
/*     */     }
/* 231 */     super.channelRegistered(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/* 239 */     initialize(ctx);
/* 240 */     super.channelActive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 245 */     destroy();
/* 246 */     super.channelInactive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 251 */     this.lastReadTime = System.currentTimeMillis();
/* 252 */     this.firstReaderIdleEvent = this.firstAllIdleEvent = true;
/* 253 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 258 */     promise.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture future) throws Exception {
/* 261 */             IdleStateHandler.this.lastWriteTime = System.currentTimeMillis();
/* 262 */             IdleStateHandler.this.firstWriterIdleEvent = IdleStateHandler.this.firstAllIdleEvent = true;
/*     */           }
/*     */         });
/* 265 */     ctx.write(msg, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize(ChannelHandlerContext ctx) {
/* 271 */     switch (this.state) {
/*     */       case 1:
/*     */       case 2:
/*     */         return;
/*     */     } 
/*     */     
/* 277 */     this.state = 1;
/*     */     
/* 279 */     EventExecutor loop = ctx.executor();
/*     */     
/* 281 */     this.lastReadTime = this.lastWriteTime = System.currentTimeMillis();
/* 282 */     if (this.readerIdleTimeMillis > 0L) {
/* 283 */       this.readerIdleTimeout = (ScheduledFuture<?>)loop.schedule(new ReaderIdleTimeoutTask(ctx), this.readerIdleTimeMillis, TimeUnit.MILLISECONDS);
/*     */     }
/*     */ 
/*     */     
/* 287 */     if (this.writerIdleTimeMillis > 0L) {
/* 288 */       this.writerIdleTimeout = (ScheduledFuture<?>)loop.schedule(new WriterIdleTimeoutTask(ctx), this.writerIdleTimeMillis, TimeUnit.MILLISECONDS);
/*     */     }
/*     */ 
/*     */     
/* 292 */     if (this.allIdleTimeMillis > 0L) {
/* 293 */       this.allIdleTimeout = (ScheduledFuture<?>)loop.schedule(new AllIdleTimeoutTask(ctx), this.allIdleTimeMillis, TimeUnit.MILLISECONDS);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void destroy() {
/* 300 */     this.state = 2;
/*     */     
/* 302 */     if (this.readerIdleTimeout != null) {
/* 303 */       this.readerIdleTimeout.cancel(false);
/* 304 */       this.readerIdleTimeout = null;
/*     */     } 
/* 306 */     if (this.writerIdleTimeout != null) {
/* 307 */       this.writerIdleTimeout.cancel(false);
/* 308 */       this.writerIdleTimeout = null;
/*     */     } 
/* 310 */     if (this.allIdleTimeout != null) {
/* 311 */       this.allIdleTimeout.cancel(false);
/* 312 */       this.allIdleTimeout = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
/* 321 */     ctx.fireUserEventTriggered(evt);
/*     */   }
/*     */   
/*     */   private final class ReaderIdleTimeoutTask
/*     */     implements Runnable {
/*     */     private final ChannelHandlerContext ctx;
/*     */     
/*     */     ReaderIdleTimeoutTask(ChannelHandlerContext ctx) {
/* 329 */       this.ctx = ctx;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 334 */       if (!this.ctx.channel().isOpen()) {
/*     */         return;
/*     */       }
/*     */       
/* 338 */       long currentTime = System.currentTimeMillis();
/* 339 */       long lastReadTime = IdleStateHandler.this.lastReadTime;
/* 340 */       long nextDelay = IdleStateHandler.this.readerIdleTimeMillis - currentTime - lastReadTime;
/* 341 */       if (nextDelay <= 0L) {
/*     */         
/* 343 */         IdleStateHandler.this.readerIdleTimeout = (ScheduledFuture<?>)this.ctx.executor().schedule(this, IdleStateHandler.this.readerIdleTimeMillis, TimeUnit.MILLISECONDS);
/*     */         
/*     */         try {
/*     */           IdleStateEvent event;
/* 347 */           if (IdleStateHandler.this.firstReaderIdleEvent) {
/* 348 */             IdleStateHandler.this.firstReaderIdleEvent = false;
/* 349 */             event = IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT;
/*     */           } else {
/* 351 */             event = IdleStateEvent.READER_IDLE_STATE_EVENT;
/*     */           } 
/* 353 */           IdleStateHandler.this.channelIdle(this.ctx, event);
/* 354 */         } catch (Throwable t) {
/* 355 */           this.ctx.fireExceptionCaught(t);
/*     */         } 
/*     */       } else {
/*     */         
/* 359 */         IdleStateHandler.this.readerIdleTimeout = (ScheduledFuture<?>)this.ctx.executor().schedule(this, nextDelay, TimeUnit.MILLISECONDS);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private final class WriterIdleTimeoutTask
/*     */     implements Runnable {
/*     */     private final ChannelHandlerContext ctx;
/*     */     
/*     */     WriterIdleTimeoutTask(ChannelHandlerContext ctx) {
/* 369 */       this.ctx = ctx;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 374 */       if (!this.ctx.channel().isOpen()) {
/*     */         return;
/*     */       }
/*     */       
/* 378 */       long currentTime = System.currentTimeMillis();
/* 379 */       long lastWriteTime = IdleStateHandler.this.lastWriteTime;
/* 380 */       long nextDelay = IdleStateHandler.this.writerIdleTimeMillis - currentTime - lastWriteTime;
/* 381 */       if (nextDelay <= 0L) {
/*     */         
/* 383 */         IdleStateHandler.this.writerIdleTimeout = (ScheduledFuture<?>)this.ctx.executor().schedule(this, IdleStateHandler.this.writerIdleTimeMillis, TimeUnit.MILLISECONDS);
/*     */         
/*     */         try {
/*     */           IdleStateEvent event;
/* 387 */           if (IdleStateHandler.this.firstWriterIdleEvent) {
/* 388 */             IdleStateHandler.this.firstWriterIdleEvent = false;
/* 389 */             event = IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT;
/*     */           } else {
/* 391 */             event = IdleStateEvent.WRITER_IDLE_STATE_EVENT;
/*     */           } 
/* 393 */           IdleStateHandler.this.channelIdle(this.ctx, event);
/* 394 */         } catch (Throwable t) {
/* 395 */           this.ctx.fireExceptionCaught(t);
/*     */         } 
/*     */       } else {
/*     */         
/* 399 */         IdleStateHandler.this.writerIdleTimeout = (ScheduledFuture<?>)this.ctx.executor().schedule(this, nextDelay, TimeUnit.MILLISECONDS);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private final class AllIdleTimeoutTask
/*     */     implements Runnable {
/*     */     private final ChannelHandlerContext ctx;
/*     */     
/*     */     AllIdleTimeoutTask(ChannelHandlerContext ctx) {
/* 409 */       this.ctx = ctx;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 414 */       if (!this.ctx.channel().isOpen()) {
/*     */         return;
/*     */       }
/*     */       
/* 418 */       long currentTime = System.currentTimeMillis();
/* 419 */       long lastIoTime = Math.max(IdleStateHandler.this.lastReadTime, IdleStateHandler.this.lastWriteTime);
/* 420 */       long nextDelay = IdleStateHandler.this.allIdleTimeMillis - currentTime - lastIoTime;
/* 421 */       if (nextDelay <= 0L) {
/*     */ 
/*     */         
/* 424 */         IdleStateHandler.this.allIdleTimeout = (ScheduledFuture<?>)this.ctx.executor().schedule(this, IdleStateHandler.this.allIdleTimeMillis, TimeUnit.MILLISECONDS);
/*     */         
/*     */         try {
/*     */           IdleStateEvent event;
/* 428 */           if (IdleStateHandler.this.firstAllIdleEvent) {
/* 429 */             IdleStateHandler.this.firstAllIdleEvent = false;
/* 430 */             event = IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT;
/*     */           } else {
/* 432 */             event = IdleStateEvent.ALL_IDLE_STATE_EVENT;
/*     */           } 
/* 434 */           IdleStateHandler.this.channelIdle(this.ctx, event);
/* 435 */         } catch (Throwable t) {
/* 436 */           this.ctx.fireExceptionCaught(t);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 441 */         IdleStateHandler.this.allIdleTimeout = (ScheduledFuture<?>)this.ctx.executor().schedule(this, nextDelay, TimeUnit.MILLISECONDS);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\timeout\IdleStateHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */