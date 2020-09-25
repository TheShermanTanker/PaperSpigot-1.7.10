/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.NClob;
/*     */ import java.sql.SQLClientInfoException;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLXML;
/*     */ import java.sql.Struct;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JDBC4LoadBalancedMySQLConnection
/*     */   extends LoadBalancedMySQLConnection
/*     */   implements JDBC4MySQLConnection
/*     */ {
/*     */   public JDBC4LoadBalancedMySQLConnection(LoadBalancingConnectionProxy proxy) throws SQLException {
/*  48 */     super(proxy);
/*     */   }
/*     */   
/*     */   private JDBC4Connection getJDBC4Connection() {
/*  52 */     return (JDBC4Connection)this.proxy.currentConn;
/*     */   }
/*     */   public SQLXML createSQLXML() throws SQLException {
/*  55 */     return getJDBC4Connection().createSQLXML();
/*     */   }
/*     */   
/*     */   public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
/*  59 */     return getJDBC4Connection().createArrayOf(typeName, elements);
/*     */   }
/*     */   
/*     */   public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
/*  63 */     return getJDBC4Connection().createStruct(typeName, attributes);
/*     */   }
/*     */   
/*     */   public Properties getClientInfo() throws SQLException {
/*  67 */     return getJDBC4Connection().getClientInfo();
/*     */   }
/*     */   
/*     */   public String getClientInfo(String name) throws SQLException {
/*  71 */     return getJDBC4Connection().getClientInfo(name);
/*     */   }
/*     */   
/*     */   public synchronized boolean isValid(int timeout) throws SQLException {
/*  75 */     return getJDBC4Connection().isValid(timeout);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClientInfo(Properties properties) throws SQLClientInfoException {
/*  80 */     getJDBC4Connection().setClientInfo(properties);
/*     */   }
/*     */   
/*     */   public void setClientInfo(String name, String value) throws SQLClientInfoException {
/*  84 */     getJDBC4Connection().setClientInfo(name, value);
/*     */   }
/*     */   
/*     */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/*  88 */     checkClosed();
/*     */ 
/*     */ 
/*     */     
/*  92 */     return iface.isInstance(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T unwrap(Class<T> iface) throws SQLException {
/*     */     try {
/* 100 */       return iface.cast(this);
/* 101 */     } catch (ClassCastException cce) {
/* 102 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", getExceptionInterceptor());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Blob createBlob() {
/* 111 */     return getJDBC4Connection().createBlob();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Clob createClob() {
/* 118 */     return getJDBC4Connection().createClob();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NClob createNClob() {
/* 125 */     return getJDBC4Connection().createNClob();
/*     */   }
/*     */   
/*     */   protected synchronized JDBC4ClientInfoProvider getClientInfoProviderImpl() throws SQLException {
/* 129 */     return getJDBC4Connection().getClientInfoProviderImpl();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\JDBC4LoadBalancedMySQLConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */