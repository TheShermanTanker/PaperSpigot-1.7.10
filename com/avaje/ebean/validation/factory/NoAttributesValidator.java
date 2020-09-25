/*    */ package com.avaje.ebean.validation.factory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class NoAttributesValidator
/*    */   implements Validator
/*    */ {
/*  8 */   private static final Object[] EMPTY = new Object[0];
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object[] getAttributes() {
/* 14 */     return EMPTY;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\factory\NoAttributesValidator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */