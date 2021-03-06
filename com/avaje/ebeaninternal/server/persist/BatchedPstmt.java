/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.PstmtBatch;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BatchedPstmt
/*     */ {
/*     */   private PreparedStatement pstmt;
/*     */   private final boolean isGenKeys;
/*  51 */   private final ArrayList<BatchPostExecute> list = new ArrayList<BatchPostExecute>();
/*     */ 
/*     */ 
/*     */   
/*     */   private final String sql;
/*     */ 
/*     */   
/*     */   private final PstmtBatch pstmtBatch;
/*     */ 
/*     */   
/*     */   private final boolean occCheck;
/*     */ 
/*     */ 
/*     */   
/*     */   public BatchedPstmt(PreparedStatement pstmt, boolean isGenKeys, String sql, PstmtBatch pstmtBatch, boolean occCheck) {
/*  66 */     this.pstmt = pstmt;
/*  67 */     this.isGenKeys = isGenKeys;
/*  68 */     this.sql = sql;
/*  69 */     this.pstmtBatch = pstmtBatch;
/*  70 */     this.occCheck = occCheck;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  77 */     return this.list.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSql() {
/*  84 */     return this.sql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PreparedStatement getStatement() {
/*  91 */     return this.pstmt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(BatchPostExecute batchExecute) {
/*  98 */     this.list.add(batchExecute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeBatch(boolean getGeneratedKeys) throws SQLException {
/* 107 */     executeAndCheckRowCounts();
/* 108 */     if (this.isGenKeys && getGeneratedKeys) {
/* 109 */       getGeneratedKeys();
/*     */     }
/* 111 */     postExecute();
/* 112 */     close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws SQLException {
/* 119 */     if (this.pstmt != null) {
/* 120 */       this.pstmt.close();
/* 121 */       this.pstmt = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void postExecute() throws SQLException {
/* 126 */     for (int i = 0; i < this.list.size(); i++) {
/* 127 */       ((BatchPostExecute)this.list.get(i)).postExecute();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void executeAndCheckRowCounts() throws SQLException {
/* 133 */     if (this.pstmtBatch != null) {
/*     */       
/* 135 */       int rc = this.pstmtBatch.executeBatch(this.pstmt, this.list.size(), this.sql, this.occCheck);
/* 136 */       if (this.list.size() == 1) {
/* 137 */         ((BatchPostExecute)this.list.get(0)).checkRowCount(rc);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 146 */     int[] results = this.pstmt.executeBatch();
/*     */     
/* 148 */     if (results.length != this.list.size()) {
/* 149 */       String s = "results array error " + results.length + " " + this.list.size();
/* 150 */       throw new SQLException(s);
/*     */     } 
/*     */ 
/*     */     
/* 154 */     for (int i = 0; i < results.length; i++) {
/* 155 */       ((BatchPostExecute)this.list.get(i)).checkRowCount(results[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void getGeneratedKeys() throws SQLException {
/* 161 */     int index = 0;
/* 162 */     ResultSet rset = this.pstmt.getGeneratedKeys();
/*     */     try {
/* 164 */       while (rset.next()) {
/* 165 */         Object idValue = rset.getObject(1);
/* 166 */         ((BatchPostExecute)this.list.get(index)).setGeneratedKey(idValue);
/* 167 */         index++;
/*     */       } 
/*     */     } finally {
/* 170 */       if (rset != null)
/* 171 */         rset.close(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\BatchedPstmt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */