/*    */ package com.avaje.ebeaninternal.api;
/*    */ 
/*    */ import com.avaje.ebean.Transaction;
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
/*    */ public abstract class LoadRequest
/*    */ {
/*    */   protected final boolean lazy;
/*    */   protected final int batchSize;
/*    */   protected final Transaction transaction;
/*    */   
/*    */   public LoadRequest(Transaction transaction, int batchSize, boolean lazy) {
/* 37 */     this.transaction = transaction;
/* 38 */     this.batchSize = batchSize;
/* 39 */     this.lazy = lazy;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isLazy() {
/* 47 */     return this.lazy;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getBatchSize() {
/* 54 */     return this.batchSize;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Transaction getTransaction() {
/* 64 */     return this.transaction;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\LoadRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */