/*     */ package com.mysql.jdbc.interceptors;
/*     */ 
/*     */ import com.mysql.jdbc.Connection;
/*     */ import com.mysql.jdbc.ResultSetInternalMethods;
/*     */ import com.mysql.jdbc.Statement;
/*     */ import com.mysql.jdbc.StatementInterceptor;
/*     */ import com.mysql.jdbc.Util;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
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
/*     */ public class ServerStatusDiffInterceptor
/*     */   implements StatementInterceptor
/*     */ {
/*  40 */   private Map preExecuteValues = new HashMap<Object, Object>();
/*     */   
/*  42 */   private Map postExecuteValues = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(Connection conn, Properties props) throws SQLException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSetInternalMethods postProcess(String sql, Statement interceptedStatement, ResultSetInternalMethods originalResultSet, Connection connection) throws SQLException {
/*  53 */     if (connection.versionMeetsMinimum(5, 0, 2)) {
/*  54 */       populateMapWithSessionStatusValues(connection, this.postExecuteValues);
/*     */       
/*  56 */       connection.getLog().logInfo("Server status change for statement:\n" + Util.calculateDifferences(this.preExecuteValues, this.postExecuteValues));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateMapWithSessionStatusValues(Connection connection, Map toPopulate) throws SQLException {
/*  68 */     Statement stmt = null;
/*  69 */     ResultSet rs = null;
/*     */     
/*     */     try {
/*  72 */       toPopulate.clear();
/*     */       
/*  74 */       stmt = connection.createStatement();
/*  75 */       rs = stmt.executeQuery("SHOW SESSION STATUS");
/*  76 */       Util.resultSetToMap(toPopulate, rs);
/*     */     } finally {
/*  78 */       if (rs != null) {
/*  79 */         rs.close();
/*     */       }
/*     */       
/*  82 */       if (stmt != null) {
/*  83 */         stmt.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSetInternalMethods preProcess(String sql, Statement interceptedStatement, Connection connection) throws SQLException {
/*  92 */     if (connection.versionMeetsMinimum(5, 0, 2)) {
/*  93 */       populateMapWithSessionStatusValues(connection, this.preExecuteValues);
/*     */     }
/*     */ 
/*     */     
/*  97 */     return null;
/*     */   }
/*     */   
/*     */   public boolean executeTopLevelOnly() {
/* 101 */     return true;
/*     */   }
/*     */   
/*     */   public void destroy() {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\interceptors\ServerStatusDiffInterceptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */