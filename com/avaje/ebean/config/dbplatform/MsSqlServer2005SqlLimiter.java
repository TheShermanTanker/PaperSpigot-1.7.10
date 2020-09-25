/*    */ package com.avaje.ebean.config.dbplatform;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MsSqlServer2005SqlLimiter
/*    */   implements SqlLimiter
/*    */ {
/*    */   final String rowNumberWindowAlias;
/*    */   
/*    */   public MsSqlServer2005SqlLimiter(String rowNumberWindowAlias) {
/* 14 */     this.rowNumberWindowAlias = rowNumberWindowAlias;
/*    */   }
/*    */   
/*    */   public MsSqlServer2005SqlLimiter() {
/* 18 */     this("as limitresult");
/*    */   }
/*    */ 
/*    */   
/*    */   public SqlLimitResponse limit(SqlLimitRequest request) {
/* 23 */     StringBuilder sb = new StringBuilder(500);
/*    */     
/* 25 */     int firstRow = request.getFirstRow();
/*    */     
/* 27 */     int lastRow = request.getMaxRows();
/* 28 */     if (lastRow > 0)
/*    */     {
/*    */       
/* 31 */       lastRow = lastRow + firstRow + 1;
/*    */     }
/*    */     
/* 34 */     if (firstRow < 1) {
/*    */       
/* 36 */       sb.append(" select top ").append(lastRow).append(" ");
/* 37 */       if (request.isDistinct()) {
/* 38 */         sb.append("distinct ");
/*    */       }
/* 40 */       sb.append(request.getDbSql());
/* 41 */       return new SqlLimitResponse(sb.toString(), false);
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 52 */     sb.append("select * ").append('\n').append("from ( ");
/*    */     
/* 54 */     sb.append("select ");
/* 55 */     if (request.isDistinct()) {
/* 56 */       sb.append("distinct ");
/*    */     }
/* 58 */     sb.append("top ").append(lastRow);
/* 59 */     sb.append(" row_number() over (order by ");
/* 60 */     sb.append(request.getDbOrderBy());
/* 61 */     sb.append(") as rn, ");
/* 62 */     sb.append(request.getDbSql());
/*    */     
/* 64 */     sb.append('\n').append(") ");
/* 65 */     sb.append(this.rowNumberWindowAlias);
/* 66 */     sb.append(" where ");
/* 67 */     if (firstRow > 0) {
/* 68 */       sb.append(" rn > ").append(firstRow);
/* 69 */       if (lastRow > 0) {
/* 70 */         sb.append(" and ");
/*    */       }
/*    */     } 
/* 73 */     if (lastRow > 0) {
/* 74 */       sb.append(" rn <= ").append(lastRow);
/*    */     }
/*    */     
/* 77 */     String sql = request.getDbPlatform().completeSql(sb.toString(), request.getOrmQuery());
/*    */     
/* 79 */     return new SqlLimitResponse(sql, true);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\MsSqlServer2005SqlLimiter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */