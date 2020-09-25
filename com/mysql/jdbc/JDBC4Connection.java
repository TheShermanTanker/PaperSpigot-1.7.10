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
/*     */ public class JDBC4Connection
/*     */   extends ConnectionImpl
/*     */ {
/*     */   private JDBC4ClientInfoProvider infoProvider;
/*     */   
/*     */   public JDBC4Connection(String hostToConnectTo, int portToConnectTo, Properties info, String databaseToConnectTo, String url) throws SQLException {
/*  47 */     super(hostToConnectTo, portToConnectTo, info, databaseToConnectTo, url);
/*     */   }
/*     */ 
/*     */   
/*     */   public SQLXML createSQLXML() throws SQLException {
/*  52 */     return new JDBC4MysqlSQLXML(getExceptionInterceptor());
/*     */   }
/*     */   
/*     */   public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
/*  56 */     throw SQLError.notImplemented();
/*     */   }
/*     */   
/*     */   public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
/*  60 */     throw SQLError.notImplemented();
/*     */   }
/*     */   
/*     */   public Properties getClientInfo() throws SQLException {
/*  64 */     return getClientInfoProviderImpl().getClientInfo(this);
/*     */   }
/*     */   
/*     */   public String getClientInfo(String name) throws SQLException {
/*  68 */     return getClientInfoProviderImpl().getClientInfo(this, name);
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
/*     */   public synchronized boolean isValid(int timeout) throws SQLException {
/*  93 */     if (isClosed()) {
/*  94 */       return false;
/*     */     }
/*     */     
/*     */     try {
/*  98 */       synchronized (getMutex()) {
/*     */         try {
/* 100 */           pingInternal(false, timeout * 1000);
/* 101 */         } catch (Throwable t) {
/*     */           try {
/* 103 */             abortInternal();
/* 104 */           } catch (Throwable ignoreThrown) {}
/*     */ 
/*     */ 
/*     */           
/* 108 */           return false;
/*     */         } 
/*     */       } 
/* 111 */     } catch (Throwable t) {
/* 112 */       return false;
/*     */     } 
/*     */     
/* 115 */     return true;
/*     */   }
/*     */   
/*     */   public void setClientInfo(Properties properties) throws SQLClientInfoException {
/*     */     try {
/* 120 */       getClientInfoProviderImpl().setClientInfo(this, properties);
/* 121 */     } catch (SQLClientInfoException ciEx) {
/* 122 */       throw ciEx;
/* 123 */     } catch (SQLException sqlEx) {
/* 124 */       SQLClientInfoException clientInfoEx = new SQLClientInfoException();
/* 125 */       clientInfoEx.initCause(sqlEx);
/*     */       
/* 127 */       throw clientInfoEx;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setClientInfo(String name, String value) throws SQLClientInfoException {
/*     */     try {
/* 133 */       getClientInfoProviderImpl().setClientInfo(this, name, value);
/* 134 */     } catch (SQLClientInfoException ciEx) {
/* 135 */       throw ciEx;
/* 136 */     } catch (SQLException sqlEx) {
/* 137 */       SQLClientInfoException clientInfoEx = new SQLClientInfoException();
/* 138 */       clientInfoEx.initCause(sqlEx);
/*     */       
/* 140 */       throw clientInfoEx;
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
/*     */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/* 160 */     checkClosed();
/*     */ 
/*     */ 
/*     */     
/* 164 */     return iface.isInstance(this);
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
/* 185 */       return iface.cast(this);
/* 186 */     } catch (ClassCastException cce) {
/* 187 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", getExceptionInterceptor());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Blob createBlob() {
/* 196 */     return new Blob(getExceptionInterceptor());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Clob createClob() {
/* 203 */     return new Clob(getExceptionInterceptor());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NClob createNClob() {
/* 210 */     return new JDBC4NClob(getExceptionInterceptor());
/*     */   }
/*     */   
/*     */   protected synchronized JDBC4ClientInfoProvider getClientInfoProviderImpl() throws SQLException {
/* 214 */     if (this.infoProvider == null) {
/*     */       try {
/*     */         try {
/* 217 */           this.infoProvider = (JDBC4ClientInfoProvider)Util.getInstance(getClientInfoProvider(), new Class[0], new Object[0], getExceptionInterceptor());
/*     */         }
/* 219 */         catch (SQLException sqlEx) {
/* 220 */           if (sqlEx.getCause() instanceof ClassCastException)
/*     */           {
/* 222 */             this.infoProvider = (JDBC4ClientInfoProvider)Util.getInstance("com.mysql.jdbc." + getClientInfoProvider(), new Class[0], new Object[0], getExceptionInterceptor());
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 227 */       catch (ClassCastException cce) {
/* 228 */         throw SQLError.createSQLException(Messages.getString("JDBC4Connection.ClientInfoNotImplemented", new Object[] { getClientInfoProvider() }), "S1009", getExceptionInterceptor());
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 233 */       this.infoProvider.initialize(this, this.props);
/*     */     } 
/*     */     
/* 236 */     return this.infoProvider;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\JDBC4Connection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */