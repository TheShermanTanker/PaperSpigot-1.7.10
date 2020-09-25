/*    */ package com.google.common.util.concurrent;
/*    */ 
/*    */ import com.google.common.annotations.Beta;
/*    */ import com.google.common.collect.ForwardingObject;
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
/*    */ public abstract class ForwardingService
/*    */   extends ForwardingObject
/*    */   implements Service
/*    */ {
/*    */   public ListenableFuture<Service.State> start() {
/* 38 */     return delegate().start();
/*    */   }
/*    */   
/*    */   public Service.State state() {
/* 42 */     return delegate().state();
/*    */   }
/*    */   
/*    */   public ListenableFuture<Service.State> stop() {
/* 46 */     return delegate().stop();
/*    */   }
/*    */   
/*    */   public Service.State startAndWait() {
/* 50 */     return delegate().startAndWait();
/*    */   }
/*    */   
/*    */   public Service.State stopAndWait() {
/* 54 */     return delegate().stopAndWait();
/*    */   }
/*    */   
/*    */   public boolean isRunning() {
/* 58 */     return delegate().isRunning();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Service.State standardStartAndWait() {
/* 68 */     return Futures.<Service.State>getUnchecked(start());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Service.State standardStopAndWait() {
/* 78 */     return Futures.<Service.State>getUnchecked(stop());
/*    */   }
/*    */   
/*    */   protected abstract Service delegate();
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\commo\\util\concurrent\ForwardingService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */