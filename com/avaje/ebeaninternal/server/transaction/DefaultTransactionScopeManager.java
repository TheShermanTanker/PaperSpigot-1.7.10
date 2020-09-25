/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultTransactionScopeManager
/*    */   extends TransactionScopeManager
/*    */ {
/*    */   public DefaultTransactionScopeManager(TransactionManager transactionManager) {
/* 12 */     super(transactionManager);
/*    */   }
/*    */   
/*    */   public void commit() {
/* 16 */     DefaultTransactionThreadLocal.commit(this.serverName);
/*    */   }
/*    */   
/*    */   public void end() {
/* 20 */     DefaultTransactionThreadLocal.end(this.serverName);
/*    */   }
/*    */   
/*    */   public SpiTransaction get() {
/* 24 */     SpiTransaction t = DefaultTransactionThreadLocal.get(this.serverName);
/* 25 */     if (t == null || !t.isActive()) {
/* 26 */       return null;
/*    */     }
/* 28 */     return t;
/*    */   }
/*    */ 
/*    */   
/*    */   public void replace(SpiTransaction trans) {
/* 33 */     DefaultTransactionThreadLocal.replace(this.serverName, trans);
/*    */   }
/*    */   
/*    */   public void rollback() {
/* 37 */     DefaultTransactionThreadLocal.rollback(this.serverName);
/*    */   }
/*    */   
/*    */   public void set(SpiTransaction trans) {
/* 41 */     DefaultTransactionThreadLocal.set(this.serverName, trans);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\DefaultTransactionScopeManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */