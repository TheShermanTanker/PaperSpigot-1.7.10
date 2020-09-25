/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.Reader;
/*     */ import java.sql.NClob;
/*     */ import java.sql.RowId;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLXML;
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
/*     */ public class JDBC4ServerPreparedStatement
/*     */   extends ServerPreparedStatement
/*     */ {
/*     */   public JDBC4ServerPreparedStatement(MySQLConnection conn, String sql, String catalog, int resultSetType, int resultSetConcurrency) throws SQLException {
/*  44 */     super(conn, sql, catalog, resultSetType, resultSetConcurrency);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
/*  55 */     if (!this.charEncoding.equalsIgnoreCase("UTF-8") && !this.charEncoding.equalsIgnoreCase("utf8"))
/*     */     {
/*  57 */       throw SQLError.createSQLException("Can not call setNCharacterStream() when connection character set isn't UTF-8", getExceptionInterceptor());
/*     */     }
/*     */ 
/*     */     
/*  61 */     checkClosed();
/*     */     
/*  63 */     if (reader == null) {
/*  64 */       setNull(parameterIndex, -2);
/*     */     } else {
/*  66 */       ServerPreparedStatement.BindValue binding = getBinding(parameterIndex, true);
/*  67 */       setType(binding, 252);
/*     */       
/*  69 */       binding.value = reader;
/*  70 */       binding.isNull = false;
/*  71 */       binding.isLongData = true;
/*     */       
/*  73 */       if (this.connection.getUseStreamLengthsInPrepStmts()) {
/*  74 */         binding.bindLength = length;
/*     */       } else {
/*  76 */         binding.bindLength = -1L;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNClob(int parameterIndex, NClob x) throws SQLException {
/*  85 */     setNClob(parameterIndex, x.getCharacterStream(), this.connection.getUseStreamLengthsInPrepStmts() ? x.length() : -1L);
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
/*     */   public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
/* 105 */     if (!this.charEncoding.equalsIgnoreCase("UTF-8") && !this.charEncoding.equalsIgnoreCase("utf8"))
/*     */     {
/* 107 */       throw SQLError.createSQLException("Can not call setNClob() when connection character set isn't UTF-8", getExceptionInterceptor());
/*     */     }
/*     */ 
/*     */     
/* 111 */     checkClosed();
/*     */     
/* 113 */     if (reader == null) {
/* 114 */       setNull(parameterIndex, 2011);
/*     */     } else {
/* 116 */       ServerPreparedStatement.BindValue binding = getBinding(parameterIndex, true);
/* 117 */       setType(binding, 252);
/*     */       
/* 119 */       binding.value = reader;
/* 120 */       binding.isNull = false;
/* 121 */       binding.isLongData = true;
/*     */       
/* 123 */       if (this.connection.getUseStreamLengthsInPrepStmts()) {
/* 124 */         binding.bindLength = length;
/*     */       } else {
/* 126 */         binding.bindLength = -1L;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNString(int parameterIndex, String x) throws SQLException {
/* 135 */     if (this.charEncoding.equalsIgnoreCase("UTF-8") || this.charEncoding.equalsIgnoreCase("utf8")) {
/*     */       
/* 137 */       setString(parameterIndex, x);
/*     */     } else {
/* 139 */       throw SQLError.createSQLException("Can not call setNString() when connection character set isn't UTF-8", getExceptionInterceptor());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRowId(int parameterIndex, RowId x) throws SQLException {
/* 145 */     JDBC4PreparedStatementHelper.setRowId(this, parameterIndex, x);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
/* 150 */     JDBC4PreparedStatementHelper.setSQLXML(this, parameterIndex, xmlObject);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\JDBC4ServerPreparedStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */