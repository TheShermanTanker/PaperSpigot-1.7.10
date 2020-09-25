/*    */ package com.avaje.ebean.config.dbplatform;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SqlAnywhereLimiter
/*    */   implements SqlLimiter
/*    */ {
/*    */   public SqlLimitResponse limit(SqlLimitRequest request) {
/* 11 */     StringBuilder sb = new StringBuilder(500);
/*    */     
/* 13 */     int firstRow = request.getFirstRow();
/* 14 */     int maxRows = request.getMaxRows();
/* 15 */     if (maxRows > 0)
/*    */     {
/*    */       
/* 18 */       maxRows++;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 24 */     sb.append("select ");
/* 25 */     if (request.isDistinct()) {
/* 26 */       sb.append("distinct ");
/*    */     }
/* 28 */     if (maxRows > 0) {
/* 29 */       sb.append("top ").append(maxRows).append(" ");
/*    */     }
/* 31 */     if (firstRow > 0) {
/* 32 */       sb.append("start at ").append(firstRow + 1).append(" ");
/*    */     }
/* 34 */     sb.append(request.getDbSql());
/*    */     
/* 36 */     String sql = request.getDbPlatform().completeSql(sb.toString(), request.getOrmQuery());
/*    */     
/* 38 */     return new SqlLimitResponse(sql, false);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\SqlAnywhereLimiter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */