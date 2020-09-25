/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.CallableSql;
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.SpiCallableSql;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*     */ import com.avaje.ebeaninternal.server.persist.PersistExecute;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
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
/*     */ public final class PersistRequestCallableSql
/*     */   extends PersistRequest
/*     */ {
/*     */   private final SpiCallableSql callableSql;
/*     */   private int rowCount;
/*     */   private String bindLog;
/*     */   private CallableStatement cstmt;
/*     */   private BindParams bindParam;
/*     */   
/*     */   public PersistRequestCallableSql(SpiEbeanServer server, CallableSql cs, SpiTransaction t, PersistExecute persistExecute) {
/*  56 */     super(server, t, persistExecute);
/*  57 */     this.type = PersistRequest.Type.CALLABLESQL;
/*  58 */     this.callableSql = (SpiCallableSql)cs;
/*     */   }
/*     */ 
/*     */   
/*     */   public int executeOrQueue() {
/*  63 */     return executeStatement();
/*     */   }
/*     */ 
/*     */   
/*     */   public int executeNow() {
/*  68 */     return this.persistExecute.executeSqlCallable(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiCallableSql getCallableSql() {
/*  75 */     return this.callableSql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBindLog(String bindLog) {
/*  82 */     this.bindLog = bindLog;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkRowCount(int count) throws SQLException {
/*  89 */     this.rowCount = count;
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
/*     */   public boolean useGeneratedKeys() {
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postExecute() throws SQLException {
/* 110 */     if (this.transaction.isLogSummary()) {
/* 111 */       String m = "CallableSql label[" + this.callableSql.getLabel() + "]" + " rows[" + this.rowCount + "]" + " bind[" + this.bindLog + "]";
/* 112 */       this.transaction.logInternal(m);
/*     */     } 
/*     */ 
/*     */     
/* 116 */     TransactionEventTable tableEvents = this.callableSql.getTransactionEventTable();
/*     */     
/* 118 */     if (tableEvents != null && !tableEvents.isEmpty()) {
/* 119 */       this.transaction.getEvent().add(tableEvents);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBound(BindParams bindParam, CallableStatement cstmt) {
/* 130 */     this.bindParam = bindParam;
/* 131 */     this.cstmt = cstmt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate() throws SQLException {
/* 141 */     if (this.callableSql.executeOverride(this.cstmt)) {
/* 142 */       return -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     this.rowCount = this.cstmt.executeUpdate();
/*     */ 
/*     */     
/* 151 */     readOutParams();
/*     */     
/* 153 */     return this.rowCount;
/*     */   }
/*     */ 
/*     */   
/*     */   private void readOutParams() throws SQLException {
/* 158 */     List<BindParams.Param> list = this.bindParam.positionedParameters();
/* 159 */     int pos = 0;
/*     */     
/* 161 */     for (int i = 0; i < list.size(); i++) {
/* 162 */       pos++;
/* 163 */       BindParams.Param param = list.get(i);
/* 164 */       if (param.isOutParam()) {
/* 165 */         Object outValue = this.cstmt.getObject(pos);
/* 166 */         param.setOutValue(outValue);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\PersistRequestCallableSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */