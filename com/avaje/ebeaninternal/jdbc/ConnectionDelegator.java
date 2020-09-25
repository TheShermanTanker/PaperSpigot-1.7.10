/*     */ package com.avaje.ebeaninternal.jdbc;
/*     */ 
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.NClob;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLClientInfoException;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.sql.SQLXML;
/*     */ import java.sql.Savepoint;
/*     */ import java.sql.Statement;
/*     */ import java.sql.Struct;
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
/*     */ public class ConnectionDelegator
/*     */   implements Connection
/*     */ {
/*     */   private final Connection delegate;
/*     */   
/*     */   public ConnectionDelegator(Connection delegate) {
/*  44 */     this.delegate = delegate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Statement createStatement() throws SQLException {
/*  50 */     return this.delegate.createStatement();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PreparedStatement prepareStatement(String sql) throws SQLException {
/*  56 */     return this.delegate.prepareStatement(sql);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CallableStatement prepareCall(String sql) throws SQLException {
/*  62 */     return this.delegate.prepareCall(sql);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String nativeSQL(String sql) throws SQLException {
/*  68 */     return this.delegate.nativeSQL(sql);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAutoCommit(boolean autoCommit) throws SQLException {
/*  74 */     this.delegate.setAutoCommit(autoCommit);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAutoCommit() throws SQLException {
/*  80 */     return this.delegate.getAutoCommit();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void commit() throws SQLException {
/*  86 */     this.delegate.commit();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void rollback() throws SQLException {
/*  92 */     this.delegate.rollback();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws SQLException {
/*  98 */     this.delegate.close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClosed() throws SQLException {
/* 104 */     return this.delegate.isClosed();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DatabaseMetaData getMetaData() throws SQLException {
/* 110 */     return this.delegate.getMetaData();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReadOnly(boolean readOnly) throws SQLException {
/* 116 */     this.delegate.setReadOnly(readOnly);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReadOnly() throws SQLException {
/* 122 */     return this.delegate.isReadOnly();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCatalog(String catalog) throws SQLException {
/* 128 */     this.delegate.setCatalog(catalog);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCatalog() throws SQLException {
/* 134 */     return this.delegate.getCatalog();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransactionIsolation(int level) throws SQLException {
/* 140 */     this.delegate.setTransactionIsolation(level);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTransactionIsolation() throws SQLException {
/* 146 */     return this.delegate.getTransactionIsolation();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SQLWarning getWarnings() throws SQLException {
/* 152 */     return this.delegate.getWarnings();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearWarnings() throws SQLException {
/* 158 */     this.delegate.clearWarnings();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
/* 164 */     return this.delegate.createStatement(resultSetType, resultSetConcurrency);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/* 170 */     return this.delegate.prepareStatement(sql, resultSetType, resultSetConcurrency);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/* 176 */     return this.delegate.prepareCall(sql, resultSetType, resultSetConcurrency);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Class<?>> getTypeMap() throws SQLException {
/* 182 */     return this.delegate.getTypeMap();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
/* 188 */     this.delegate.setTypeMap(map);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHoldability(int holdability) throws SQLException {
/* 194 */     this.delegate.setHoldability(holdability);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHoldability() throws SQLException {
/* 200 */     return this.delegate.getHoldability();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Savepoint setSavepoint() throws SQLException {
/* 206 */     return this.delegate.setSavepoint();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Savepoint setSavepoint(String name) throws SQLException {
/* 212 */     return this.delegate.setSavepoint(name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void rollback(Savepoint savepoint) throws SQLException {
/* 218 */     this.delegate.rollback(savepoint);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseSavepoint(Savepoint savepoint) throws SQLException {
/* 224 */     this.delegate.releaseSavepoint(savepoint);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/* 230 */     return this.delegate.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/* 236 */     return this.delegate.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/* 242 */     return this.delegate.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
/* 248 */     return this.delegate.prepareStatement(sql, autoGeneratedKeys);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
/* 254 */     return this.delegate.prepareStatement(sql, columnIndexes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
/* 260 */     return this.delegate.prepareStatement(sql, columnNames);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Clob createClob() throws SQLException {
/* 266 */     return this.delegate.createClob();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Blob createBlob() throws SQLException {
/* 272 */     return this.delegate.createBlob();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NClob createNClob() throws SQLException {
/* 278 */     return this.delegate.createNClob();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SQLXML createSQLXML() throws SQLException {
/* 284 */     return this.delegate.createSQLXML();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(int timeout) throws SQLException {
/* 290 */     return this.delegate.isValid(timeout);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClientInfo(String name, String value) throws SQLClientInfoException {
/* 296 */     this.delegate.setClientInfo(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClientInfo(Properties properties) throws SQLClientInfoException {
/* 302 */     this.delegate.setClientInfo(properties);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClientInfo(String name) throws SQLException {
/* 308 */     return this.delegate.getClientInfo(name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Properties getClientInfo() throws SQLException {
/* 314 */     return this.delegate.getClientInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
/* 320 */     return this.delegate.createArrayOf(typeName, elements);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
/* 326 */     return this.delegate.createStruct(typeName, attributes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T unwrap(Class<T> iface) throws SQLException {
/* 332 */     return this.delegate.unwrap(iface);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/* 338 */     return this.delegate.isWrapperFor(iface);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\jdbc\ConnectionDelegator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */