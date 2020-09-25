/*    */ package com.avaje.ebeaninternal.api;
/*    */ 
/*    */ public final class DerivedRelationshipData
/*    */ {
/*    */   private final Object assocBean;
/*    */   private final String logicalName;
/*    */   private final Object bean;
/*    */   
/*    */   public DerivedRelationshipData(Object assocBean, String logicalName, Object bean) {
/* 10 */     this.assocBean = assocBean;
/* 11 */     this.logicalName = logicalName;
/* 12 */     this.bean = bean;
/*    */   }
/*    */   
/*    */   public Object getAssocBean() {
/* 16 */     return this.assocBean;
/*    */   }
/*    */   
/*    */   public String getLogicalName() {
/* 20 */     return this.logicalName;
/*    */   }
/*    */   
/*    */   public Object getBean() {
/* 24 */     return this.bean;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\DerivedRelationshipData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */