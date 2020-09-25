/*    */ package com.mysql.jdbc.jdbc2.optional;
/*    */ 
/*    */ import com.mysql.jdbc.ConnectionImpl;
/*    */ import java.sql.SQLException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.sql.StatementEvent;
/*    */ import javax.sql.StatementEventListener;
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
/*    */ public class JDBC4MysqlXAConnection
/*    */   extends MysqlXAConnection
/*    */ {
/*    */   private Map<StatementEventListener, StatementEventListener> statementEventListeners;
/*    */   
/*    */   public JDBC4MysqlXAConnection(ConnectionImpl connection, boolean logXaCommands) throws SQLException {
/* 43 */     super(connection, logXaCommands);
/*    */     
/* 45 */     this.statementEventListeners = new HashMap<StatementEventListener, StatementEventListener>();
/*    */   }
/*    */   
/*    */   public synchronized void close() throws SQLException {
/* 49 */     super.close();
/*    */     
/* 51 */     if (this.statementEventListeners != null) {
/* 52 */       this.statementEventListeners.clear();
/*    */       
/* 54 */       this.statementEventListeners = null;
/*    */     } 
/*    */   }
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
/*    */   public void addStatementEventListener(StatementEventListener listener) {
/* 71 */     synchronized (this.statementEventListeners) {
/* 72 */       this.statementEventListeners.put(listener, listener);
/*    */     } 
/*    */   }
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
/*    */   public void removeStatementEventListener(StatementEventListener listener) {
/* 88 */     synchronized (this.statementEventListeners) {
/* 89 */       this.statementEventListeners.remove(listener);
/*    */     } 
/*    */   }
/*    */   
/*    */   void fireStatementEvent(StatementEvent event) throws SQLException {
/* 94 */     synchronized (this.statementEventListeners) {
/* 95 */       for (StatementEventListener listener : this.statementEventListeners.keySet())
/* 96 */         listener.statementClosed(event); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\jdbc2\optional\JDBC4MysqlXAConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */