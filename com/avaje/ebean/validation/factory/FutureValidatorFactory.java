/*    */ package com.avaje.ebean.validation.factory;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FutureValidatorFactory
/*    */   implements ValidatorFactory
/*    */ {
/* 12 */   Validator DATE = new DateValidator();
/*    */   
/* 14 */   Validator CALENDAR = new CalendarValidator();
/*    */   
/*    */   public Validator create(Annotation annotation, Class<?> type) {
/* 17 */     if (Date.class.isAssignableFrom(type)) {
/* 18 */       return this.DATE;
/*    */     }
/* 20 */     if (Calendar.class.isAssignableFrom(type)) {
/* 21 */       return this.CALENDAR;
/*    */     }
/* 23 */     String msg = "Can not use @Future on type " + type;
/* 24 */     throw new RuntimeException(msg);
/*    */   }
/*    */   
/*    */   private static class DateValidator
/*    */     extends NoAttributesValidator {
/*    */     public String getKey() {
/* 30 */       return "future";
/*    */     }
/*    */     private DateValidator() {}
/*    */     public boolean isValid(Object value) {
/* 34 */       if (value == null) {
/* 35 */         return true;
/*    */       }
/*    */       
/* 38 */       Date date = (Date)value;
/* 39 */       return date.after(new Date());
/*    */     } }
/*    */   
/*    */   private static class CalendarValidator extends NoAttributesValidator {
/*    */     private CalendarValidator() {}
/*    */     
/*    */     public String getKey() {
/* 46 */       return "future";
/*    */     }
/*    */     
/*    */     public boolean isValid(Object value) {
/* 50 */       if (value == null) {
/* 51 */         return true;
/*    */       }
/*    */       
/* 54 */       Calendar cal = (Calendar)value;
/* 55 */       return cal.after(Calendar.getInstance());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\factory\FutureValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */