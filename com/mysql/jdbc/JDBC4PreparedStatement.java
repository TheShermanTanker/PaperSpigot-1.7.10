/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.sql.NClob;
/*    */ import java.sql.RowId;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.SQLXML;
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
/*    */ public class JDBC4PreparedStatement
/*    */   extends PreparedStatement
/*    */ {
/*    */   public JDBC4PreparedStatement(MySQLConnection conn, String catalog) throws SQLException {
/* 43 */     super(conn, catalog);
/*    */   }
/*    */ 
/*    */   
/*    */   public JDBC4PreparedStatement(MySQLConnection conn, String sql, String catalog) throws SQLException {
/* 48 */     super(conn, sql, catalog);
/*    */   }
/*    */ 
/*    */   
/*    */   public JDBC4PreparedStatement(MySQLConnection conn, String sql, String catalog, PreparedStatement.ParseInfo cachedParseInfo) throws SQLException {
/* 53 */     super(conn, sql, catalog, cachedParseInfo);
/*    */   }
/*    */   
/*    */   public void setRowId(int parameterIndex, RowId x) throws SQLException {
/* 57 */     JDBC4PreparedStatementHelper.setRowId(this, parameterIndex, x);
/*    */   }
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
/*    */   public void setNClob(int parameterIndex, NClob value) throws SQLException {
/* 72 */     JDBC4PreparedStatementHelper.setNClob(this, parameterIndex, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
/* 77 */     JDBC4PreparedStatementHelper.setSQLXML(this, parameterIndex, xmlObject);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\JDBC4PreparedStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */