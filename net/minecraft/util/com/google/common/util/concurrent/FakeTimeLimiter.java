/*    */ package net.minecraft.util.com.google.common.util.concurrent;
/*    */ 
/*    */ import java.util.concurrent.Callable;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import net.minecraft.util.com.google.common.annotations.Beta;
/*    */ import net.minecraft.util.com.google.common.base.Preconditions;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Beta
/*    */ public final class FakeTimeLimiter
/*    */   implements TimeLimiter
/*    */ {
/*    */   public <T> T newProxy(T target, Class<T> interfaceType, long timeoutDuration, TimeUnit timeoutUnit) {
/* 41 */     Preconditions.checkNotNull(target);
/* 42 */     Preconditions.checkNotNull(interfaceType);
/* 43 */     Preconditions.checkNotNull(timeoutUnit);
/* 44 */     return target;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> T callWithTimeout(Callable<T> callable, long timeoutDuration, TimeUnit timeoutUnit, boolean amInterruptible) throws Exception {
/* 50 */     Preconditions.checkNotNull(timeoutUnit);
/* 51 */     return callable.call();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\commo\\util\concurrent\FakeTimeLimiter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */