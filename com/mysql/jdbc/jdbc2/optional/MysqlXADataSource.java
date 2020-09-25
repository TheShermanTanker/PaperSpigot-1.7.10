/*    */ package com.mysql.jdbc.jdbc2.optional;
/*    */ 
/*    */ import com.mysql.jdbc.Connection;
/*    */ import com.mysql.jdbc.ConnectionImpl;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import javax.sql.XAConnection;
/*    */ import javax.sql.XADataSource;
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
/*    */ 
/*    */ 
/*    */ public class MysqlXADataSource
/*    */   extends MysqlDataSource
/*    */   implements XADataSource
/*    */ {
/*    */   public XAConnection getXAConnection() throws SQLException {
/* 48 */     Connection conn = getConnection();
/*    */     
/* 50 */     return wrapConnection(conn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XAConnection getXAConnection(String u, String p) throws SQLException {
/* 59 */     Connection conn = getConnection(u, p);
/*    */     
/* 61 */     return wrapConnection(conn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private XAConnection wrapConnection(Connection conn) throws SQLException {
/* 69 */     if (getPinGlobalTxToPhysicalConnection() || ((Connection)conn).getPinGlobalTxToPhysicalConnection())
/*    */     {
/* 71 */       return SuspendableXAConnection.getInstance((ConnectionImpl)conn);
/*    */     }
/*    */     
/* 74 */     return MysqlXAConnection.getInstance((ConnectionImpl)conn, getLogXaCommands());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\jdbc2\optional\MysqlXADataSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */