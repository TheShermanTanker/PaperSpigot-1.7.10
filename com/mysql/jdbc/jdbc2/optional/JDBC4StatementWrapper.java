/*     */ package com.mysql.jdbc.jdbc2.optional;
/*     */ 
/*     */ import com.mysql.jdbc.SQLError;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JDBC4StatementWrapper
/*     */   extends StatementWrapper
/*     */ {
/*     */   public JDBC4StatementWrapper(ConnectionWrapper c, MysqlPooledConnection conn, Statement toWrap) {
/*  61 */     super(c, conn, toWrap);
/*     */   }
/*     */   
/*     */   public void close() throws SQLException {
/*     */     try {
/*  66 */       super.close();
/*     */     } finally {
/*  68 */       this.unwrappedInterfaces = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isClosed() throws SQLException {
/*     */     try {
/*  74 */       if (this.wrappedStmt != null) {
/*  75 */         return this.wrappedStmt.isClosed();
/*     */       }
/*  77 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     
/*     */     }
/*  80 */     catch (SQLException sqlEx) {
/*  81 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/*  84 */       return false;
/*     */     } 
/*     */   }
/*     */   public void setPoolable(boolean poolable) throws SQLException {
/*     */     try {
/*  89 */       if (this.wrappedStmt != null) {
/*  90 */         this.wrappedStmt.setPoolable(poolable);
/*     */       } else {
/*  92 */         throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */       }
/*     */     
/*  95 */     } catch (SQLException sqlEx) {
/*  96 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isPoolable() throws SQLException {
/*     */     try {
/* 102 */       if (this.wrappedStmt != null) {
/* 103 */         return this.wrappedStmt.isPoolable();
/*     */       }
/* 105 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     
/*     */     }
/* 108 */     catch (SQLException sqlEx) {
/* 109 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 112 */       return false;
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
/*     */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/* 139 */     boolean isInstance = iface.isInstance(this);
/*     */     
/* 141 */     if (isInstance) {
/* 142 */       return true;
/*     */     }
/*     */     
/* 145 */     String interfaceClassName = iface.getName();
/*     */     
/* 147 */     return (interfaceClassName.equals("com.mysql.jdbc.Statement") || interfaceClassName.equals("java.sql.Statement") || interfaceClassName.equals("java.sql.Wrapper"));
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
/*     */   public synchronized <T> T unwrap(Class<T> iface) throws SQLException {
/*     */     try {
/* 174 */       if ("java.sql.Statement".equals(iface.getName()) || "java.sql.Wrapper.class".equals(iface.getName()))
/*     */       {
/* 176 */         return iface.cast(this);
/*     */       }
/*     */       
/* 179 */       if (this.unwrappedInterfaces == null) {
/* 180 */         this.unwrappedInterfaces = new HashMap<Object, Object>();
/*     */       }
/*     */       
/* 183 */       Object cachedUnwrapped = this.unwrappedInterfaces.get(iface);
/*     */       
/* 185 */       if (cachedUnwrapped == null) {
/* 186 */         cachedUnwrapped = Proxy.newProxyInstance(this.wrappedStmt.getClass().getClassLoader(), new Class[] { iface }, new WrapperBase.ConnectionErrorFiringInvocationHandler(this, this.wrappedStmt));
/*     */ 
/*     */ 
/*     */         
/* 190 */         this.unwrappedInterfaces.put(iface, cachedUnwrapped);
/*     */       } 
/*     */       
/* 193 */       return iface.cast(cachedUnwrapped);
/* 194 */     } catch (ClassCastException cce) {
/* 195 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.exceptionInterceptor);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\jdbc2\optional\JDBC4StatementWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */