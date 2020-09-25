/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebean.config.ExternalTransactionManager;
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
/*    */ public class ExternalTransactionScopeManager
/*    */   extends TransactionScopeManager
/*    */ {
/*    */   final ExternalTransactionManager externalManager;
/*    */   
/*    */   public ExternalTransactionScopeManager(TransactionManager transactionManager, ExternalTransactionManager externalManager) {
/* 38 */     super(transactionManager);
/* 39 */     this.externalManager = externalManager;
/*    */   }
/*    */   
/*    */   public void commit() {
/* 43 */     DefaultTransactionThreadLocal.commit(this.serverName);
/*    */   }
/*    */ 
/*    */   
/*    */   public void end() {
/* 48 */     DefaultTransactionThreadLocal.end(this.serverName);
/*    */   }
/*    */ 
/*    */   
/*    */   public SpiTransaction get() {
/* 53 */     return (SpiTransaction)this.externalManager.getCurrentTransaction();
/*    */   }
/*    */   
/*    */   public void replace(SpiTransaction trans) {
/* 57 */     DefaultTransactionThreadLocal.replace(this.serverName, trans);
/*    */   }
/*    */   
/*    */   public void rollback() {
/* 61 */     DefaultTransactionThreadLocal.rollback(this.serverName);
/*    */   }
/*    */   
/*    */   public void set(SpiTransaction trans) {
/* 65 */     DefaultTransactionThreadLocal.set(this.serverName, trans);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\ExternalTransactionScopeManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */