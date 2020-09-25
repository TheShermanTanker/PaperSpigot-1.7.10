/*     */ package net.minecraft.util.io.netty.handler.timeout;
/*     */ 
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelInboundHandlerAdapter;
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
/*     */ public class ReadTimeoutHandler
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   private final long timeoutMillis;
/*     */   private volatile ScheduledFuture<?> timeout;
/*     */   private volatile long lastReadTime;
/*     */   private volatile int state;
/*     */   private boolean closed;
/*     */   
/*     */   public ReadTimeoutHandler(int timeoutSeconds) {
/*  82 */     this(timeoutSeconds, TimeUnit.SECONDS);
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
/*     */   public ReadTimeoutHandler(long timeout, TimeUnit unit) {
/*  94 */     if (unit == null) {
/*  95 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/*  98 */     if (timeout <= 0L) {
/*  99 */       this.timeoutMillis = 0L;
/*     */     } else {
/* 101 */       this.timeoutMillis = Math.max(unit.toMillis(timeout), 1L);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 107 */     if (ctx.channel().isActive() && ctx.channel().isRegistered())
/*     */     {
/*     */       
/* 110 */       initialize(ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 119 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
/* 125 */     if (ctx.channel().isActive()) {
/* 126 */       initialize(ctx);
/*     */     }
/* 128 */     super.channelRegistered(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/* 136 */     initialize(ctx);
/* 137 */     super.channelActive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 142 */     destroy();
/* 143 */     super.channelInactive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 148 */     this.lastReadTime = System.currentTimeMillis();
/* 149 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize(ChannelHandlerContext ctx) {
/* 155 */     switch (this.state) {
/*     */       case 1:
/*     */       case 2:
/*     */         return;
/*     */     } 
/*     */     
/* 161 */     this.state = 1;
/*     */     
/* 163 */     this.lastReadTime = System.currentTimeMillis();
/* 164 */     if (this.timeoutMillis > 0L) {
/* 165 */       this.timeout = (ScheduledFuture<?>)ctx.executor().schedule(new ReadTimeoutTask(ctx), this.timeoutMillis, TimeUnit.MILLISECONDS);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void destroy() {
/* 172 */     this.state = 2;
/*     */     
/* 174 */     if (this.timeout != null) {
/* 175 */       this.timeout.cancel(false);
/* 176 */       this.timeout = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readTimedOut(ChannelHandlerContext ctx) throws Exception {
/* 184 */     if (!this.closed) {
/* 185 */       ctx.fireExceptionCaught((Throwable)ReadTimeoutException.INSTANCE);
/* 186 */       ctx.close();
/* 187 */       this.closed = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private final class ReadTimeoutTask
/*     */     implements Runnable {
/*     */     private final ChannelHandlerContext ctx;
/*     */     
/*     */     ReadTimeoutTask(ChannelHandlerContext ctx) {
/* 196 */       this.ctx = ctx;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 201 */       if (!this.ctx.channel().isOpen()) {
/*     */         return;
/*     */       }
/*     */       
/* 205 */       long currentTime = System.currentTimeMillis();
/* 206 */       long nextDelay = ReadTimeoutHandler.this.timeoutMillis - currentTime - ReadTimeoutHandler.this.lastReadTime;
/* 207 */       if (nextDelay <= 0L) {
/*     */         
/* 209 */         ReadTimeoutHandler.this.timeout = (ScheduledFuture<?>)this.ctx.executor().schedule(this, ReadTimeoutHandler.this.timeoutMillis, TimeUnit.MILLISECONDS);
/*     */         try {
/* 211 */           ReadTimeoutHandler.this.readTimedOut(this.ctx);
/* 212 */         } catch (Throwable t) {
/* 213 */           this.ctx.fireExceptionCaught(t);
/*     */         } 
/*     */       } else {
/*     */         
/* 217 */         ReadTimeoutHandler.this.timeout = (ScheduledFuture<?>)this.ctx.executor().schedule(this, nextDelay, TimeUnit.MILLISECONDS);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\timeout\ReadTimeoutHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */