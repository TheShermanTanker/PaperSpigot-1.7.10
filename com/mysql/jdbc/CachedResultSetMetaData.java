/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.sql.ResultSetMetaData;
/*    */ import java.util.Map;
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
/*    */ public class CachedResultSetMetaData
/*    */ {
/* 32 */   Map columnNameToIndex = null;
/*    */ 
/*    */   
/*    */   Field[] fields;
/*    */ 
/*    */   
/* 38 */   Map fullColumnNameToIndex = null;
/*    */   
/*    */   ResultSetMetaData metadata;
/*    */ 
/*    */   
/*    */   public Map getColumnNameToIndex() {
/* 44 */     return this.columnNameToIndex;
/*    */   }
/*    */   
/*    */   public Field[] getFields() {
/* 48 */     return this.fields;
/*    */   }
/*    */   
/*    */   public Map getFullColumnNameToIndex() {
/* 52 */     return this.fullColumnNameToIndex;
/*    */   }
/*    */   
/*    */   public ResultSetMetaData getMetadata() {
/* 56 */     return this.metadata;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\CachedResultSetMetaData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */