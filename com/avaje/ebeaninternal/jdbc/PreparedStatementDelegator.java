/*     */ package com.avaje.ebeaninternal.jdbc;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.math.BigDecimal;
/*     */ import java.net.URL;
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.NClob;
/*     */ import java.sql.ParameterMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.Ref;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.RowId;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.sql.SQLXML;
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
/*     */ public class PreparedStatementDelegator
/*     */   implements PreparedStatement
/*     */ {
/*     */   private final PreparedStatement delegate;
/*     */   
/*     */   public PreparedStatementDelegator(PreparedStatement delegate) {
/*  49 */     this.delegate = delegate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet executeQuery() throws SQLException {
/*  55 */     return this.delegate.executeQuery();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate() throws SQLException {
/*  61 */     return this.delegate.executeUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNull(int parameterIndex, int sqlType) throws SQLException {
/*  67 */     this.delegate.setNull(parameterIndex, sqlType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBoolean(int parameterIndex, boolean x) throws SQLException {
/*  73 */     this.delegate.setBoolean(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByte(int parameterIndex, byte x) throws SQLException {
/*  79 */     this.delegate.setByte(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShort(int parameterIndex, short x) throws SQLException {
/*  85 */     this.delegate.setShort(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInt(int parameterIndex, int x) throws SQLException {
/*  91 */     this.delegate.setInt(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLong(int parameterIndex, long x) throws SQLException {
/*  97 */     this.delegate.setLong(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFloat(int parameterIndex, float x) throws SQLException {
/* 103 */     this.delegate.setFloat(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDouble(int parameterIndex, double x) throws SQLException {
/* 109 */     this.delegate.setDouble(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
/* 115 */     this.delegate.setBigDecimal(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(int parameterIndex, String x) throws SQLException {
/* 121 */     this.delegate.setString(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBytes(int parameterIndex, byte[] x) throws SQLException {
/* 127 */     this.delegate.setBytes(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDate(int parameterIndex, Date x) throws SQLException {
/* 133 */     this.delegate.setDate(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTime(int parameterIndex, Time x) throws SQLException {
/* 139 */     this.delegate.setTime(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
/* 145 */     this.delegate.setTimestamp(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
/* 151 */     this.delegate.setAsciiStream(parameterIndex, x, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
/* 158 */     this.delegate.setUnicodeStream(parameterIndex, x, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
/* 164 */     this.delegate.setBinaryStream(parameterIndex, x, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearParameters() throws SQLException {
/* 170 */     this.delegate.clearParameters();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
/* 176 */     this.delegate.setObject(parameterIndex, x, targetSqlType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObject(int parameterIndex, Object x) throws SQLException {
/* 182 */     this.delegate.setObject(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute() throws SQLException {
/* 188 */     return this.delegate.execute();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBatch() throws SQLException {
/* 194 */     this.delegate.addBatch();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
/* 200 */     this.delegate.setCharacterStream(parameterIndex, reader, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRef(int parameterIndex, Ref x) throws SQLException {
/* 206 */     this.delegate.setRef(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlob(int parameterIndex, Blob x) throws SQLException {
/* 212 */     this.delegate.setBlob(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClob(int parameterIndex, Clob x) throws SQLException {
/* 218 */     this.delegate.setClob(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setArray(int parameterIndex, Array x) throws SQLException {
/* 224 */     this.delegate.setArray(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSetMetaData getMetaData() throws SQLException {
/* 230 */     return this.delegate.getMetaData();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
/* 236 */     this.delegate.setDate(parameterIndex, x, cal);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
/* 242 */     this.delegate.setTime(parameterIndex, x, cal);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
/* 248 */     this.delegate.setTimestamp(parameterIndex, x, cal);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
/* 254 */     this.delegate.setNull(parameterIndex, sqlType, typeName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setURL(int parameterIndex, URL x) throws SQLException {
/* 260 */     this.delegate.setURL(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterMetaData getParameterMetaData() throws SQLException {
/* 266 */     return this.delegate.getParameterMetaData();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRowId(int parameterIndex, RowId x) throws SQLException {
/* 272 */     this.delegate.setRowId(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNString(int parameterIndex, String value) throws SQLException {
/* 278 */     this.delegate.setNString(parameterIndex, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
/* 284 */     this.delegate.setNCharacterStream(parameterIndex, value, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNClob(int parameterIndex, NClob value) throws SQLException {
/* 290 */     this.delegate.setNClob(parameterIndex, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
/* 296 */     this.delegate.setClob(parameterIndex, reader, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
/* 302 */     this.delegate.setBlob(parameterIndex, inputStream, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
/* 308 */     this.delegate.setNClob(parameterIndex, reader, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
/* 314 */     this.delegate.setSQLXML(parameterIndex, xmlObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
/* 320 */     this.delegate.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
/* 326 */     this.delegate.setAsciiStream(parameterIndex, x, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
/* 332 */     this.delegate.setBinaryStream(parameterIndex, x, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
/* 338 */     this.delegate.setCharacterStream(parameterIndex, reader, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
/* 344 */     this.delegate.setAsciiStream(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
/* 350 */     this.delegate.setBinaryStream(parameterIndex, x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
/* 356 */     this.delegate.setCharacterStream(parameterIndex, reader);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
/* 362 */     this.delegate.setNCharacterStream(parameterIndex, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClob(int parameterIndex, Reader reader) throws SQLException {
/* 368 */     this.delegate.setClob(parameterIndex, reader);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
/* 374 */     this.delegate.setBlob(parameterIndex, inputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNClob(int parameterIndex, Reader reader) throws SQLException {
/* 380 */     this.delegate.setNClob(parameterIndex, reader);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet executeQuery(String sql) throws SQLException {
/* 386 */     return this.delegate.executeQuery(sql);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate(String sql) throws SQLException {
/* 392 */     return this.delegate.executeUpdate(sql);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws SQLException {
/* 398 */     this.delegate.close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxFieldSize() throws SQLException {
/* 404 */     return this.delegate.getMaxFieldSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxFieldSize(int max) throws SQLException {
/* 410 */     this.delegate.setMaxFieldSize(max);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxRows() throws SQLException {
/* 416 */     return this.delegate.getMaxRows();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxRows(int max) throws SQLException {
/* 422 */     this.delegate.setMaxRows(max);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEscapeProcessing(boolean enable) throws SQLException {
/* 428 */     this.delegate.setEscapeProcessing(enable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getQueryTimeout() throws SQLException {
/* 434 */     return this.delegate.getQueryTimeout();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQueryTimeout(int seconds) throws SQLException {
/* 440 */     this.delegate.setQueryTimeout(seconds);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancel() throws SQLException {
/* 446 */     this.delegate.cancel();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SQLWarning getWarnings() throws SQLException {
/* 452 */     return this.delegate.getWarnings();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearWarnings() throws SQLException {
/* 458 */     this.delegate.clearWarnings();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorName(String name) throws SQLException {
/* 464 */     this.delegate.setCursorName(name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String sql) throws SQLException {
/* 470 */     return this.delegate.execute(sql);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet getResultSet() throws SQLException {
/* 476 */     return this.delegate.getResultSet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUpdateCount() throws SQLException {
/* 482 */     return this.delegate.getUpdateCount();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getMoreResults() throws SQLException {
/* 488 */     return this.delegate.getMoreResults();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFetchDirection(int direction) throws SQLException {
/* 494 */     this.delegate.setFetchDirection(direction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFetchDirection() throws SQLException {
/* 500 */     return this.delegate.getFetchDirection();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFetchSize(int rows) throws SQLException {
/* 506 */     this.delegate.setFetchSize(rows);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFetchSize() throws SQLException {
/* 512 */     return this.delegate.getFetchSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResultSetConcurrency() throws SQLException {
/* 518 */     return this.delegate.getResultSetConcurrency();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResultSetType() throws SQLException {
/* 524 */     return this.delegate.getResultSetType();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBatch(String sql) throws SQLException {
/* 530 */     this.delegate.addBatch(sql);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearBatch() throws SQLException {
/* 536 */     this.delegate.clearBatch();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] executeBatch() throws SQLException {
/* 542 */     return this.delegate.executeBatch();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() throws SQLException {
/* 548 */     return this.delegate.getConnection();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getMoreResults(int current) throws SQLException {
/* 554 */     return this.delegate.getMoreResults(current);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet getGeneratedKeys() throws SQLException {
/* 560 */     return this.delegate.getGeneratedKeys();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
/* 566 */     return this.delegate.executeUpdate(sql, autoGeneratedKeys);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
/* 572 */     return this.delegate.executeUpdate(sql, columnIndexes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate(String sql, String[] columnNames) throws SQLException {
/* 578 */     return this.delegate.executeUpdate(sql, columnNames);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
/* 584 */     return this.delegate.execute(sql, autoGeneratedKeys);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String sql, int[] columnIndexes) throws SQLException {
/* 590 */     return this.delegate.execute(sql, columnIndexes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String sql, String[] columnNames) throws SQLException {
/* 596 */     return this.delegate.execute(sql, columnNames);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResultSetHoldability() throws SQLException {
/* 602 */     return this.delegate.getResultSetHoldability();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClosed() throws SQLException {
/* 608 */     return this.delegate.isClosed();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPoolable(boolean poolable) throws SQLException {
/* 614 */     this.delegate.setPoolable(poolable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPoolable() throws SQLException {
/* 620 */     return this.delegate.isPoolable();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T unwrap(Class<T> iface) throws SQLException {
/* 626 */     return this.delegate.unwrap(iface);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/* 632 */     return this.delegate.isWrapperFor(iface);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\jdbc\PreparedStatementDelegator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */