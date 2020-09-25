/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*    */ import com.avaje.ebeaninternal.api.SpiTransactionScopeManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TransactionScopeManager
/*    */   implements SpiTransactionScopeManager
/*    */ {
/*    */   protected final TransactionManager transactionManager;
/*    */   protected final String serverName;
/*    */   
/*    */   public TransactionScopeManager(TransactionManager transactionManager) {
/* 16 */     this.transactionManager = transactionManager;
/* 17 */     this.serverName = transactionManager.getServerName();
/*    */   }
/*    */   
/*    */   public abstract SpiTransaction get();
/*    */   
/*    */   public abstract void set(SpiTransaction paramSpiTransaction);
/*    */   
/*    */   public abstract void commit();
/*    */   
/*    */   public abstract void rollback();
/*    */   
/*    */   public abstract void end();
/*    */   
/*    */   public abstract void replace(SpiTransaction paramSpiTransaction);
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\TransactionScopeManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */