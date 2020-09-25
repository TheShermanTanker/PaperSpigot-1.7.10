/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DeployPropertyParserRawSql
/*    */   extends DeployParser
/*    */ {
/*    */   private final DRawSqlSelect rawSqlSelect;
/*    */   
/*    */   public DeployPropertyParserRawSql(DRawSqlSelect rawSqlSelect) {
/* 16 */     this.rawSqlSelect = rawSqlSelect;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<String> getIncludes() {
/* 23 */     return null;
/*    */   }
/*    */   
/*    */   public String convertWord() {
/* 27 */     String r = getDeployWord(this.word);
/* 28 */     return (r == null) ? this.word : r;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDeployWord(String expression) {
/* 33 */     DRawSqlColumnInfo columnInfo = this.rawSqlSelect.getRawSqlColumnInfo(expression);
/* 34 */     if (columnInfo == null) {
/* 35 */       return null;
/*    */     }
/* 37 */     return columnInfo.getName();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\DeployPropertyParserRawSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */