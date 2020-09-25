/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.SqlUpdate;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiSqlUpdate;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.persist.PersistExecute;
/*     */ import java.sql.SQLException;
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
/*     */ public final class PersistRequestUpdateSql
/*     */   extends PersistRequest
/*     */ {
/*     */   private final SpiSqlUpdate updateSql;
/*     */   private int rowCount;
/*     */   private String bindLog;
/*     */   private SqlType sqlType;
/*     */   private String tableName;
/*     */   private String description;
/*     */   
/*     */   public enum SqlType
/*     */   {
/*  36 */     SQL_UPDATE, SQL_DELETE, SQL_INSERT, SQL_UNKNOWN;
/*     */   }
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
/*     */   public PersistRequestUpdateSql(SpiEbeanServer server, SqlUpdate updateSql, SpiTransaction t, PersistExecute persistExecute) {
/*  56 */     super(server, t, persistExecute);
/*  57 */     this.type = PersistRequest.Type.UPDATESQL;
/*  58 */     this.updateSql = (SpiSqlUpdate)updateSql;
/*     */   }
/*     */ 
/*     */   
/*     */   public int executeNow() {
/*  63 */     return this.persistExecute.executeSqlUpdate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int executeOrQueue() {
/*  68 */     return executeStatement();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiSqlUpdate getUpdateSql() {
/*  75 */     return this.updateSql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkRowCount(int count) throws SQLException {
/*  82 */     this.rowCount = count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useGeneratedKeys() {
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGeneratedKey(Object idValue) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(SqlType sqlType, String tableName, String description) {
/* 103 */     this.sqlType = sqlType;
/* 104 */     this.tableName = tableName;
/* 105 */     this.description = description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBindLog(String bindLog) {
/* 112 */     this.bindLog = bindLog;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postExecute() throws SQLException {
/* 120 */     if (this.transaction.isLogSummary()) {
/* 121 */       String m = this.description + " table[" + this.tableName + "] rows[" + this.rowCount + "] bind[" + this.bindLog + "]";
/* 122 */       this.transaction.logInternal(m);
/*     */     } 
/*     */     
/* 125 */     if (this.updateSql.isAutoTableMod())
/*     */     {
/*     */       
/* 128 */       switch (this.sqlType) {
/*     */         case SQL_INSERT:
/* 130 */           this.transaction.getEvent().add(this.tableName, true, false, false);
/*     */           break;
/*     */         case SQL_UPDATE:
/* 133 */           this.transaction.getEvent().add(this.tableName, false, true, false);
/*     */           break;
/*     */         case SQL_DELETE:
/* 136 */           this.transaction.getEvent().add(this.tableName, false, false, true);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\PersistRequestUpdateSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */