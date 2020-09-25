/*     */ package com.google.common.util.concurrent;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
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
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ public interface Service
/*     */ {
/*     */   ListenableFuture<State> start();
/*     */   
/*     */   State startAndWait();
/*     */   
/*     */   boolean isRunning();
/*     */   
/*     */   State state();
/*     */   
/*     */   ListenableFuture<State> stop();
/*     */   
/*     */   State stopAndWait();
/*     */   
/*     */   @Beta
/*     */   public enum State
/*     */   {
/* 131 */     NEW,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     STARTING,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     RUNNING,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     STOPPING,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     TERMINATED,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     FAILED;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\commo\\util\concurrent\Service.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */