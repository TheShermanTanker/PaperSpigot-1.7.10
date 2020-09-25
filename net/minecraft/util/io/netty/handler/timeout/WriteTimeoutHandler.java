/*     */ package net.minecraft.util.io.netty.handler.timeout;
/*     */ 
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOutboundHandlerAdapter;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*     */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
/*     */ import net.minecraft.util.io.netty.util.concurrent.ScheduledFuture;
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
/*     */ public class WriteTimeoutHandler
/*     */   extends ChannelOutboundHandlerAdapter
/*     */ {
/*     */   private final long timeoutMillis;
/*     */   private boolean closed;
/*     */   
/*     */   public WriteTimeoutHandler(int timeoutSeconds) {
/*  80 */     this(timeoutSeconds, TimeUnit.SECONDS);
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
/*     */   public WriteTimeoutHandler(long timeout, TimeUnit unit) {
/*  92 */     if (unit == null) {
/*  93 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/*  96 */     if (timeout <= 0L) {
/*  97 */       this.timeoutMillis = 0L;
/*     */     } else {
/*  99 */       this.timeoutMillis = Math.max(unit.toMillis(timeout), 1L);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 105 */     scheduleTimeout(ctx, promise);
/* 106 */     ctx.write(msg, promise);
/*     */   }
/*     */   
/*     */   private void scheduleTimeout(final ChannelHandlerContext ctx, final ChannelPromise future) {
/* 110 */     if (this.timeoutMillis > 0L) {
/*     */       
/* 112 */       final ScheduledFuture sf = ctx.executor().schedule(new Runnable()
/*     */           {
/*     */             public void run() {
/* 115 */               if (future.tryFailure((Throwable)WriteTimeoutException.INSTANCE)) {
/*     */                 
/*     */                 try {
/* 118 */                   WriteTimeoutHandler.this.writeTimedOut(ctx);
/* 119 */                 } catch (Throwable t) {
/* 120 */                   ctx.fireExceptionCaught(t);
/*     */                 } 
/*     */               }
/*     */             }
/*     */           },  this.timeoutMillis, TimeUnit.MILLISECONDS);
/*     */ 
/*     */       
/* 127 */       future.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture future) throws Exception {
/* 130 */               sf.cancel(false);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeTimedOut(ChannelHandlerContext ctx) throws Exception {
/* 140 */     if (!this.closed) {
/* 141 */       ctx.fireExceptionCaught((Throwable)WriteTimeoutException.INSTANCE);
/* 142 */       ctx.close();
/* 143 */       this.closed = true;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\timeout\WriteTimeoutHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */