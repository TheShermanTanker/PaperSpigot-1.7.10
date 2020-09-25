/*    */ package com.avaje.ebean.config.dbplatform;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RownumSqlLimiter
/*    */   implements SqlLimiter
/*    */ {
/*    */   private final String rnum;
/*    */   private final boolean useFirstRowsHint;
/*    */   
/*    */   public RownumSqlLimiter() {
/* 16 */     this("rn_", true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RownumSqlLimiter(String rnum, boolean useFirstRowsHint) {
/* 24 */     this.rnum = rnum;
/* 25 */     this.useFirstRowsHint = useFirstRowsHint;
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
/*    */   public SqlLimitResponse limit(SqlLimitRequest request) {
/* 38 */     StringBuilder sb = new StringBuilder(500);
/*    */     
/* 40 */     int firstRow = request.getFirstRow();
/*    */     
/* 42 */     int lastRow = request.getMaxRows();
/* 43 */     if (lastRow > 0) {
/* 44 */       lastRow = lastRow + firstRow + 1;
/*    */     }
/*    */     
/* 47 */     sb.append("select * ").append('\n').append("from ( ");
/*    */     
/* 49 */     sb.append("select ");
/* 50 */     if (this.useFirstRowsHint && request.getMaxRows() > 0) {
/* 51 */       sb.append("/*+ FIRST_ROWS(").append(request.getMaxRows() + 1).append(") */ ");
/*    */     }
/*    */     
/* 54 */     sb.append("rownum ").append(this.rnum).append(", a.* ").append('\n');
/* 55 */     sb.append("       from (");
/*    */     
/* 57 */     sb.append(" select ");
/* 58 */     if (request.isDistinct()) {
/* 59 */       sb.append("distinct ");
/*    */     }
/* 61 */     sb.append(request.getDbSql());
/*    */     
/* 63 */     sb.append('\n').append("            ) a ");
/* 64 */     if (lastRow > 0) {
/* 65 */       sb.append('\n').append("       where rownum <= ").append(lastRow);
/*    */     }
/* 67 */     sb.append('\n').append("     ) ");
/* 68 */     if (firstRow > 0) {
/* 69 */       sb.append('\n').append("where ");
/* 70 */       sb.append(this.rnum).append(" > ").append(firstRow);
/*    */     } 
/*    */     
/* 73 */     String sql = request.getDbPlatform().completeSql(sb.toString(), request.getOrmQuery());
/*    */     
/* 75 */     return new SqlLimitResponse(sql, true);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\RownumSqlLimiter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */