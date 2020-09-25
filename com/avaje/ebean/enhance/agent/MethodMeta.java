/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ 
/*    */ public class MethodMeta
/*    */ {
/*    */   final int access;
/*    */   final String name;
/*    */   final String desc;
/*    */   final AnnotationInfo annotationInfo;
/*    */   
/*    */   public MethodMeta(AnnotationInfo classAnnotationInfo, int access, String name, String desc) {
/* 12 */     this.annotationInfo = new AnnotationInfo(classAnnotationInfo);
/* 13 */     this.access = access;
/* 14 */     this.name = name;
/* 15 */     this.desc = desc;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 19 */     return this.name + " " + this.desc;
/*    */   }
/*    */   
/*    */   public boolean isMatch(String methodName, String methodDesc) {
/* 23 */     if (this.name.equals(methodName) && this.desc.equals(methodDesc)) {
/* 24 */       return true;
/*    */     }
/* 26 */     return false;
/*    */   }
/*    */   
/*    */   public AnnotationInfo getAnnotationInfo() {
/* 30 */     return this.annotationInfo;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\agent\MethodMeta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */