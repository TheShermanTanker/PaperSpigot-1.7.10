/*    */ package com.google.common.util.concurrent;
/*    */ 
/*    */ import com.google.common.annotations.VisibleForTesting;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class UncaughtExceptionHandlers
/*    */ {
/*    */   public static Thread.UncaughtExceptionHandler systemExit() {
/* 47 */     return new Exiter(Runtime.getRuntime());
/*    */   }
/*    */   
/*    */   @VisibleForTesting
/* 51 */   static final class Exiter implements Thread.UncaughtExceptionHandler { private static final Logger logger = Logger.getLogger(Exiter.class.getName());
/*    */     
/*    */     private final Runtime runtime;
/*    */     
/*    */     Exiter(Runtime runtime) {
/* 56 */       this.runtime = runtime;
/*    */     }
/*    */ 
/*    */     
/*    */     public void uncaughtException(Thread t, Throwable e) {
/* 61 */       logger.log(Level.SEVERE, String.format("Caught an exception in %s.  Shutting down.", new Object[] { t }), e);
/* 62 */       this.runtime.exit(1);
/*    */     } }
/*    */ 
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\commo\\util\concurrent\UncaughtExceptionHandlers.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */