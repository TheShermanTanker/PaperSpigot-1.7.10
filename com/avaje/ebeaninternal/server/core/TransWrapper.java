/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiTransaction;
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
/*    */ final class TransWrapper
/*    */ {
/*    */   final SpiTransaction transaction;
/*    */   private final boolean wasCreated;
/*    */   
/*    */   TransWrapper(SpiTransaction t, boolean created) {
/* 22 */     this.transaction = t;
/* 23 */     this.wasCreated = created;
/*    */   }
/*    */   
/*    */   void commitIfCreated() {
/* 27 */     if (this.wasCreated) {
/* 28 */       this.transaction.commit();
/*    */     }
/*    */   }
/*    */   
/*    */   void rollbackIfCreated() {
/* 33 */     if (this.wasCreated) {
/* 34 */       this.transaction.rollback();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean wasCreated() {
/* 43 */     return this.wasCreated;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\TransWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */