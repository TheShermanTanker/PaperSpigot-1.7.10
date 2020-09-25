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
/*    */ 
/*    */ public class RowNumberSqlLimiter
/*    */   implements SqlLimiter
/*    */ {
/*    */   private static final String ROW_NUMBER_OVER = "row_number() over (order by ";
/*    */   private static final String ROW_NUMBER_AS = ") as rn, ";
/*    */   final String rowNumberWindowAlias;
/*    */   
/*    */   public RowNumberSqlLimiter(String rowNumberWindowAlias) {
/* 24 */     this.rowNumberWindowAlias = rowNumberWindowAlias;
/*    */   }
/*    */   
/*    */   public RowNumberSqlLimiter() {
/* 28 */     this("as limitresult");
/*    */   }
/*    */ 
/*    */   
/*    */   public SqlLimitResponse limit(SqlLimitRequest request) {
/* 33 */     StringBuilder sb = new StringBuilder(500);
/*    */     
/* 35 */     int firstRow = request.getFirstRow();
/*    */     
/* 37 */     int lastRow = request.getMaxRows();
/* 38 */     if (lastRow > 0) {
/* 39 */       lastRow = lastRow + firstRow + 1;
/*    */     }
/*    */     
/* 42 */     sb.append("select * from (").append('\n');
/*    */     
/* 44 */     sb.append("select ");
/* 45 */     if (request.isDistinct()) {
/* 46 */       sb.append("distinct ");
/*    */     }
/*    */     
/* 49 */     sb.append("row_number() over (order by ");
/* 50 */     sb.append(request.getDbOrderBy());
/* 51 */     sb.append(") as rn, ");
/*    */     
/* 53 */     sb.append(request.getDbSql());
/*    */     
/* 55 */     sb.append('\n').append(") ");
/* 56 */     sb.append(this.rowNumberWindowAlias);
/* 57 */     sb.append(" where ");
/* 58 */     if (firstRow > 0) {
/* 59 */       sb.append(" rn > ").append(firstRow);
/* 60 */       if (lastRow > 0) {
/* 61 */         sb.append(" and ");
/*    */       }
/*    */     } 
/* 64 */     if (lastRow > 0) {
/* 65 */       sb.append(" rn <= ").append(lastRow);
/*    */     }
/*    */     
/* 68 */     String sql = request.getDbPlatform().completeSql(sb.toString(), request.getOrmQuery());
/*    */     
/* 70 */     return new SqlLimitResponse(sql, true);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\RowNumberSqlLimiter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */