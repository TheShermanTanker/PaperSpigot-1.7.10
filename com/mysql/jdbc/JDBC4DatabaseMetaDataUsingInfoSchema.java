/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.sql.RowIdLifetime;
/*    */ import java.sql.SQLException;
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
/*    */ public class JDBC4DatabaseMetaDataUsingInfoSchema
/*    */   extends DatabaseMetaDataUsingInfoSchema
/*    */ {
/*    */   public JDBC4DatabaseMetaDataUsingInfoSchema(MySQLConnection connToSet, String databaseToSet) throws SQLException {
/* 37 */     super(connToSet, databaseToSet);
/*    */   }
/*    */   
/*    */   public RowIdLifetime getRowIdLifetime() throws SQLException {
/* 41 */     return RowIdLifetime.ROWID_UNSUPPORTED;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/* 62 */     return iface.isInstance(this);
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> T unwrap(Class<T> iface) throws SQLException {
/*    */     try {
/* 83 */       return iface.cast(this);
/* 84 */     } catch (ClassCastException cce) {
/* 85 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.conn.getExceptionInterceptor());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getJDBC4FunctionNoTableConstant() {
/* 91 */     return 1;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\JDBC4DatabaseMetaDataUsingInfoSchema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */