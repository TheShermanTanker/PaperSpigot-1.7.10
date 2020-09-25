/*    */ package com.avaje.ebeaninternal.server.deploy;
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
/*    */ public class CompoundUniqueContraint
/*    */ {
/*    */   private final String[] columns;
/*    */   
/*    */   public CompoundUniqueContraint(String[] columns) {
/* 30 */     this.columns = columns;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getColumns() {
/* 37 */     return this.columns;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\CompoundUniqueContraint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */