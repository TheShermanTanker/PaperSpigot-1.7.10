/*    */ package net.minecraft.util.io.netty.channel;
/*    */ 
/*    */ import net.minecraft.util.io.netty.util.concurrent.EventExecutor;
/*    */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*    */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class FailedChannelFuture
/*    */   extends CompleteChannelFuture
/*    */ {
/*    */   private final Throwable cause;
/*    */   
/*    */   public FailedChannelFuture(Channel channel, EventExecutor executor, Throwable cause) {
/* 37 */     super(channel, executor);
/* 38 */     if (cause == null) {
/* 39 */       throw new NullPointerException("cause");
/*    */     }
/* 41 */     this.cause = cause;
/*    */   }
/*    */ 
/*    */   
/*    */   public Throwable cause() {
/* 46 */     return this.cause;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSuccess() {
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelFuture sync() {
/* 56 */     PlatformDependent.throwException(this.cause);
/* 57 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelFuture syncUninterruptibly() {
/* 62 */     PlatformDependent.throwException(this.cause);
/* 63 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\FailedChannelFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */