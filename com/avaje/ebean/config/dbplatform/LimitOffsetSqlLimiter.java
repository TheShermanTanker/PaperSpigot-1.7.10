/*    */ package com.avaje.ebean.config.dbplatform;
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
/*    */ public class LimitOffsetSqlLimiter
/*    */   implements SqlLimiter
/*    */ {
/*    */   private static final String LIMIT = "limit";
/*    */   private static final String OFFSET = "offset";
/*    */   
/*    */   public SqlLimitResponse limit(SqlLimitRequest request) {
/* 22 */     StringBuilder sb = new StringBuilder(512);
/* 23 */     sb.append("select ");
/* 24 */     if (request.isDistinct()) {
/* 25 */       sb.append("distinct ");
/*    */     }
/*    */     
/* 28 */     sb.append(request.getDbSql());
/*    */     
/* 30 */     int firstRow = request.getFirstRow();
/* 31 */     int maxRows = request.getMaxRows();
/* 32 */     if (maxRows > 0) {
/* 33 */       maxRows++;
/*    */     }
/*    */     
/* 36 */     sb.append(" ").append('\n').append("limit").append(" ");
/* 37 */     if (maxRows > 0) {
/* 38 */       sb.append(maxRows);
/*    */     }
/* 40 */     if (firstRow > 0) {
/* 41 */       sb.append(" ").append("offset").append(" ");
/* 42 */       sb.append(firstRow);
/*    */     } 
/*    */     
/* 45 */     String sql = request.getDbPlatform().completeSql(sb.toString(), request.getOrmQuery());
/*    */     
/* 47 */     return new SqlLimitResponse(sql, false);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\LimitOffsetSqlLimiter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */