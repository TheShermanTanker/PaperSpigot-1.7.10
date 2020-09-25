/*    */ package com.avaje.ebeaninternal.server.jmx;
/*    */ 
/*    */ import com.avaje.ebean.AdminLogging;
/*    */ import com.avaje.ebean.LogLevel;
/*    */ import com.avaje.ebean.config.ServerConfig;
/*    */ import com.avaje.ebeaninternal.server.transaction.TransactionManager;
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
/*    */ public class MAdminLogging
/*    */   implements MAdminLoggingMBean, AdminLogging
/*    */ {
/*    */   private final TransactionManager transactionManager;
/*    */   private boolean debugSql;
/*    */   private boolean debugLazyLoad;
/*    */   
/*    */   public MAdminLogging(ServerConfig serverConfig, TransactionManager txManager) {
/* 46 */     this.transactionManager = txManager;
/* 47 */     this.debugSql = serverConfig.isDebugSql();
/* 48 */     this.debugLazyLoad = serverConfig.isDebugLazyLoad();
/*    */   }
/*    */   
/*    */   public void setLogLevel(LogLevel logLevel) {
/* 52 */     this.transactionManager.setTransactionLogLevel(logLevel);
/*    */   }
/*    */   
/*    */   public LogLevel getLogLevel() {
/* 56 */     return this.transactionManager.getTransactionLogLevel();
/*    */   }
/*    */   
/*    */   public boolean isDebugGeneratedSql() {
/* 60 */     return this.debugSql;
/*    */   }
/*    */   
/*    */   public void setDebugGeneratedSql(boolean debugSql) {
/* 64 */     this.debugSql = debugSql;
/*    */   }
/*    */   
/*    */   public boolean isDebugLazyLoad() {
/* 68 */     return this.debugLazyLoad;
/*    */   }
/*    */   
/*    */   public void setDebugLazyLoad(boolean debugLazyLoad) {
/* 72 */     this.debugLazyLoad = debugLazyLoad;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\jmx\MAdminLogging.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */