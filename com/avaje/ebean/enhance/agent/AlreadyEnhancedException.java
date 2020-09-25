/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AlreadyEnhancedException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -831705721822834774L;
/*    */   final String className;
/*    */   
/*    */   public AlreadyEnhancedException(String className) {
/* 16 */     this.className = className;
/*    */   }
/*    */   
/*    */   public String getClassName() {
/* 20 */     return this.className;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\agent\AlreadyEnhancedException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */