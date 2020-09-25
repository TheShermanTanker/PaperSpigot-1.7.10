/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Properties;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReflectiveStatementInterceptorAdapter
/*    */   implements StatementInterceptorV2
/*    */ {
/*    */   private final StatementInterceptor toProxy;
/*    */   final Method v2PostProcessMethod;
/*    */   
/*    */   public ReflectiveStatementInterceptorAdapter(StatementInterceptor toProxy) {
/* 35 */     this.toProxy = toProxy;
/* 36 */     this.v2PostProcessMethod = getV2PostProcessMethod(toProxy.getClass());
/*    */   }
/*    */   
/*    */   public void destroy() {
/* 40 */     this.toProxy.destroy();
/*    */   }
/*    */   
/*    */   public boolean executeTopLevelOnly() {
/* 44 */     return this.toProxy.executeTopLevelOnly();
/*    */   }
/*    */   
/*    */   public void init(Connection conn, Properties props) throws SQLException {
/* 48 */     this.toProxy.init(conn, props);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResultSetInternalMethods postProcess(String sql, Statement interceptedStatement, ResultSetInternalMethods originalResultSet, Connection connection, int warningCount, boolean noIndexUsed, boolean noGoodIndexUsed, SQLException statementException) throws SQLException {
/*    */     try {
/* 58 */       return (ResultSetInternalMethods)this.v2PostProcessMethod.invoke(this.toProxy, new Object[] { sql, interceptedStatement, originalResultSet, connection, new Integer(warningCount), noIndexUsed ? Boolean.TRUE : Boolean.FALSE, noGoodIndexUsed ? Boolean.TRUE : Boolean.FALSE, statementException });
/*    */ 
/*    */ 
/*    */     
/*    */     }
/* 63 */     catch (IllegalArgumentException e) {
/* 64 */       SQLException sqlEx = new SQLException("Unable to reflectively invoke interceptor");
/* 65 */       sqlEx.initCause(e);
/*    */       
/* 67 */       throw sqlEx;
/* 68 */     } catch (IllegalAccessException e) {
/* 69 */       SQLException sqlEx = new SQLException("Unable to reflectively invoke interceptor");
/* 70 */       sqlEx.initCause(e);
/*    */       
/* 72 */       throw sqlEx;
/* 73 */     } catch (InvocationTargetException e) {
/* 74 */       SQLException sqlEx = new SQLException("Unable to reflectively invoke interceptor");
/* 75 */       sqlEx.initCause(e);
/*    */       
/* 77 */       throw sqlEx;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ResultSetInternalMethods preProcess(String sql, Statement interceptedStatement, Connection connection) throws SQLException {
/* 84 */     return this.toProxy.preProcess(sql, interceptedStatement, connection);
/*    */   }
/*    */   
/*    */   public static final Method getV2PostProcessMethod(Class toProxyClass) {
/*    */     try {
/* 89 */       Method postProcessMethod = toProxyClass.getMethod("postProcess", new Class[] { String.class, Statement.class, ResultSetInternalMethods.class, Connection.class, int.class, boolean.class, boolean.class, SQLException.class });
/*    */ 
/*    */ 
/*    */       
/* 93 */       return postProcessMethod;
/* 94 */     } catch (SecurityException e) {
/* 95 */       return null;
/* 96 */     } catch (NoSuchMethodException e) {
/* 97 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\ReflectiveStatementInterceptorAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */