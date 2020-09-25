/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebean.LogLevel;
/*    */ import java.sql.Connection;
/*    */ import javax.persistence.PersistenceException;
/*    */ import javax.persistence.RollbackException;
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
/*    */ public class ExternalJdbcTransaction
/*    */   extends JdbcTransaction
/*    */ {
/*    */   public ExternalJdbcTransaction(Connection connection) {
/* 32 */     super(null, true, LogLevel.NONE, connection, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ExternalJdbcTransaction(String id, boolean explicit, Connection connection, TransactionManager manager) {
/* 39 */     super(id, explicit, manager.getTransactionLogLevel(), connection, manager);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ExternalJdbcTransaction(String id, boolean explicit, LogLevel logLevel, Connection connection, TransactionManager manager) {
/* 46 */     super(id, explicit, logLevel, connection, manager);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void commit() throws RollbackException {
/* 57 */     throw new PersistenceException("This is an external transaction so must be committed externally");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void end() throws PersistenceException {
/* 68 */     throw new PersistenceException("This is an external transaction so must be committed externally");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void rollback() throws PersistenceException {
/* 79 */     throw new PersistenceException("This is an external transaction so must be rolled back externally");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void rollback(Throwable e) throws PersistenceException {
/* 90 */     throw new PersistenceException("This is an external transaction so must be rolled back externally");
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\ExternalJdbcTransaction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */