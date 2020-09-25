/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.math.BigDecimal;
/*     */ import java.net.URL;
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Date;
/*     */ import java.sql.ParameterMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.Ref;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
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
/*     */ public class ExtendedPreparedStatement
/*     */   extends ExtendedStatement
/*     */   implements PreparedStatement
/*     */ {
/*     */   final String sql;
/*     */   final String cacheKey;
/*     */   
/*     */   public ExtendedPreparedStatement(PooledConnection pooledConnection, PreparedStatement pstmt, String sql, String cacheKey) {
/*  63 */     super(pooledConnection, pstmt);
/*  64 */     this.sql = sql;
/*  65 */     this.cacheKey = cacheKey;
/*     */   }
/*     */   
/*     */   public PreparedStatement getDelegate() {
/*  69 */     return this.pstmt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCacheKey() {
/*  76 */     return this.cacheKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSql() {
/*  83 */     return this.sql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeDestroy() throws SQLException {
/*  91 */     this.pstmt.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws SQLException {
/* 100 */     this.pooledConnection.returnPreparedStatement(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBatch() throws SQLException {
/*     */     try {
/* 108 */       this.pstmt.addBatch();
/* 109 */     } catch (SQLException e) {
/*     */ 
/*     */       
/* 112 */       this.pooledConnection.addError(e);
/* 113 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearParameters() throws SQLException {
/*     */     try {
/* 122 */       this.pstmt.clearParameters();
/* 123 */     } catch (SQLException e) {
/*     */ 
/*     */       
/* 126 */       this.pooledConnection.addError(e);
/* 127 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute() throws SQLException {
/*     */     try {
/* 136 */       return this.pstmt.execute();
/* 137 */     } catch (SQLException e) {
/*     */ 
/*     */       
/* 140 */       this.pooledConnection.addError(e);
/* 141 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet executeQuery() throws SQLException {
/*     */     try {
/* 150 */       return this.pstmt.executeQuery();
/* 151 */     } catch (SQLException e) {
/*     */ 
/*     */       
/* 154 */       this.pooledConnection.addError(e);
/* 155 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate() throws SQLException {
/*     */     try {
/* 164 */       return this.pstmt.executeUpdate();
/* 165 */     } catch (SQLException e) {
/*     */ 
/*     */       
/* 168 */       this.pooledConnection.addError(e);
/* 169 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSetMetaData getMetaData() throws SQLException {
/*     */     try {
/* 178 */       return this.pstmt.getMetaData();
/* 179 */     } catch (SQLException e) {
/*     */ 
/*     */       
/* 182 */       this.pooledConnection.addError(e);
/* 183 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterMetaData getParameterMetaData() throws SQLException {
/* 191 */     return this.pstmt.getParameterMetaData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setArray(int i, Array x) throws SQLException {
/* 198 */     this.pstmt.setArray(i, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
/* 205 */     this.pstmt.setAsciiStream(parameterIndex, x, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
/* 212 */     this.pstmt.setBigDecimal(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
/* 219 */     this.pstmt.setBinaryStream(parameterIndex, x, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlob(int i, Blob x) throws SQLException {
/* 226 */     this.pstmt.setBlob(i, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBoolean(int parameterIndex, boolean x) throws SQLException {
/* 233 */     this.pstmt.setBoolean(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByte(int parameterIndex, byte x) throws SQLException {
/* 240 */     this.pstmt.setByte(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBytes(int parameterIndex, byte[] x) throws SQLException {
/* 247 */     this.pstmt.setBytes(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
/* 255 */     this.pstmt.setCharacterStream(parameterIndex, reader, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClob(int i, Clob x) throws SQLException {
/* 262 */     this.pstmt.setClob(i, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDate(int parameterIndex, Date x) throws SQLException {
/* 269 */     this.pstmt.setDate(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
/* 276 */     this.pstmt.setDate(parameterIndex, x, cal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDouble(int parameterIndex, double x) throws SQLException {
/* 283 */     this.pstmt.setDouble(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFloat(int parameterIndex, float x) throws SQLException {
/* 290 */     this.pstmt.setFloat(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInt(int parameterIndex, int x) throws SQLException {
/* 297 */     this.pstmt.setInt(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLong(int parameterIndex, long x) throws SQLException {
/* 304 */     this.pstmt.setLong(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNull(int parameterIndex, int sqlType) throws SQLException {
/* 311 */     this.pstmt.setNull(parameterIndex, sqlType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException {
/* 318 */     this.pstmt.setNull(paramIndex, sqlType, typeName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObject(int parameterIndex, Object x) throws SQLException {
/* 325 */     this.pstmt.setObject(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
/* 332 */     this.pstmt.setObject(parameterIndex, x, targetSqlType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
/* 340 */     this.pstmt.setObject(parameterIndex, x, targetSqlType, scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRef(int i, Ref x) throws SQLException {
/* 347 */     this.pstmt.setRef(i, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShort(int parameterIndex, short x) throws SQLException {
/* 354 */     this.pstmt.setShort(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(int parameterIndex, String x) throws SQLException {
/* 361 */     this.pstmt.setString(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTime(int parameterIndex, Time x) throws SQLException {
/* 368 */     this.pstmt.setTime(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
/* 375 */     this.pstmt.setTime(parameterIndex, x, cal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
/* 382 */     this.pstmt.setTimestamp(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
/* 389 */     this.pstmt.setTimestamp(parameterIndex, x, cal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
/* 397 */     this.pstmt.setUnicodeStream(parameterIndex, x, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setURL(int parameterIndex, URL x) throws SQLException {
/* 404 */     this.pstmt.setURL(parameterIndex, x);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\ExtendedPreparedStatement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */