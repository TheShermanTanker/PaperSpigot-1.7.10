/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DRawSqlColumnInfo
/*    */ {
/*    */   final String name;
/*    */   final String label;
/*    */   final String propertyName;
/*    */   final boolean scalarProperty;
/*    */   
/*    */   public DRawSqlColumnInfo(String name, String label, String propertyName, boolean scalarProperty) {
/* 17 */     this.name = name;
/* 18 */     this.label = label;
/* 19 */     this.propertyName = propertyName;
/* 20 */     this.scalarProperty = scalarProperty;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 24 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getLabel() {
/* 28 */     return this.label;
/*    */   }
/*    */   
/*    */   public String getPropertyName() {
/* 32 */     return this.propertyName;
/*    */   }
/*    */   
/*    */   public boolean isScalarProperty() {
/* 36 */     return this.scalarProperty;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 40 */     return "name:" + this.name + " label:" + this.label + " prop:" + this.propertyName;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\DRawSqlColumnInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */