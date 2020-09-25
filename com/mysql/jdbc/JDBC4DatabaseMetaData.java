/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.RowIdLifetime;
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
/*     */ public class JDBC4DatabaseMetaData
/*     */   extends DatabaseMetaData
/*     */ {
/*     */   public JDBC4DatabaseMetaData(MySQLConnection connToSet, String databaseToSet) {
/*  39 */     super(connToSet, databaseToSet);
/*     */   }
/*     */   
/*     */   public RowIdLifetime getRowIdLifetime() throws SQLException {
/*  43 */     return RowIdLifetime.ROWID_UNSUPPORTED;
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
/*     */   
/*     */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/*  64 */     return iface.isInstance(this);
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
/*     */   public <T> T unwrap(Class<T> iface) throws SQLException {
/*     */     try {
/*  85 */       return iface.cast(this);
/*  86 */     } catch (ClassCastException cce) {
/*  87 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.conn.getExceptionInterceptor());
/*     */     } 
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
/*     */   public ResultSet getClientInfoProperties() throws SQLException {
/* 119 */     Field[] fields = new Field[4];
/* 120 */     fields[0] = new Field("", "NAME", 12, 255);
/* 121 */     fields[1] = new Field("", "MAX_LEN", 4, 10);
/* 122 */     fields[2] = new Field("", "DEFAULT_VALUE", 12, 255);
/* 123 */     fields[3] = new Field("", "DESCRIPTION", 12, 255);
/*     */     
/* 125 */     ArrayList tuples = new ArrayList();
/*     */     
/* 127 */     return buildResultSet(fields, tuples, this.conn);
/*     */   }
/*     */   
/*     */   public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
/* 131 */     return false;
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
/*     */   public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
/* 184 */     Field[] fields = new Field[6];
/*     */     
/* 186 */     fields[0] = new Field("", "FUNCTION_CAT", 1, 255);
/* 187 */     fields[1] = new Field("", "FUNCTION_SCHEM", 1, 255);
/* 188 */     fields[2] = new Field("", "FUNCTION_NAME", 1, 255);
/* 189 */     fields[3] = new Field("", "REMARKS", 1, 255);
/* 190 */     fields[4] = new Field("", "FUNCTION_TYPE", 5, 6);
/* 191 */     fields[5] = new Field("", "SPECIFIC_NAME", 1, 255);
/*     */     
/* 193 */     return getProceduresAndOrFunctions(fields, catalog, schemaPattern, functionNamePattern, false, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getJDBC4FunctionNoTableConstant() {
/* 203 */     return 1;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\JDBC4DatabaseMetaData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */