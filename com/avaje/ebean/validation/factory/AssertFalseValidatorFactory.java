/*    */ package com.avaje.ebean.validation.factory;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssertFalseValidatorFactory
/*    */   implements ValidatorFactory
/*    */ {
/* 15 */   public static final Validator ASSERT_FALSE = new AssertFalseValidator();
/*    */   
/*    */   public Validator create(Annotation annotation, Class<?> type) {
/* 18 */     return ASSERT_FALSE;
/*    */   }
/*    */   
/*    */   public static class AssertFalseValidator
/*    */     extends NoAttributesValidator {
/*    */     public String getKey() {
/* 24 */       return "assertfalse";
/*    */     }
/*    */     
/*    */     public boolean isValid(Object value) {
/* 28 */       if (value == null) {
/* 29 */         return true;
/*    */       }
/* 31 */       return ((Boolean)value).booleanValue();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\factory\AssertFalseValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */