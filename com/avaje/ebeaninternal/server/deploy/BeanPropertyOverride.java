/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.InternString;
/*    */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
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
/*    */ public class BeanPropertyOverride
/*    */ {
/*    */   private final String dbColumn;
/*    */   private final String sqlFormulaSelect;
/*    */   private final String sqlFormulaJoin;
/*    */   
/*    */   public BeanPropertyOverride(String dbColumn) {
/* 40 */     this(dbColumn, null, null);
/*    */   }
/*    */   
/*    */   public BeanPropertyOverride(String dbColumn, String sqlFormulaSelect, String sqlFormulaJoin) {
/* 44 */     this.dbColumn = InternString.intern(dbColumn);
/* 45 */     this.sqlFormulaSelect = InternString.intern(sqlFormulaSelect);
/* 46 */     this.sqlFormulaJoin = InternString.intern(sqlFormulaJoin);
/*    */   }
/*    */   
/*    */   public String getDbColumn() {
/* 50 */     return this.dbColumn;
/*    */   }
/*    */   
/*    */   public String getSqlFormulaSelect() {
/* 54 */     return this.sqlFormulaSelect;
/*    */   }
/*    */   
/*    */   public String getSqlFormulaJoin() {
/* 58 */     return this.sqlFormulaJoin;
/*    */   }
/*    */   
/*    */   public String replace(String src, String srcDbColumn) {
/* 62 */     return StringHelper.replaceString(src, srcDbColumn, this.dbColumn);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanPropertyOverride.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */