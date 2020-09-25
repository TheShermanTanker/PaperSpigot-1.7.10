/*    */ package com.avaje.ebean.text.json;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JsonElementNumber
/*    */   implements JsonElement
/*    */ {
/*    */   private final String value;
/*    */   
/*    */   public JsonElementNumber(String value) {
/* 40 */     this.value = value;
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 44 */     return this.value;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 48 */     return this.value;
/*    */   }
/*    */   
/*    */   public boolean isPrimitive() {
/* 52 */     return true;
/*    */   }
/*    */   
/*    */   public String toPrimitiveString() {
/* 56 */     return this.value;
/*    */   }
/*    */   
/*    */   public Object eval(String exp) {
/* 60 */     if (exp != null) {
/* 61 */       throw new IllegalArgumentException("expression [" + exp + "] not allowed on number");
/*    */     }
/* 63 */     return Double.valueOf(Double.parseDouble(this.value));
/*    */   }
/*    */   
/*    */   public int evalInt(String exp) {
/* 67 */     if (exp != null) {
/* 68 */       throw new IllegalArgumentException("expression [" + exp + "] not allowed on number");
/*    */     }
/* 70 */     return Integer.parseInt(this.value);
/*    */   }
/*    */   
/*    */   public String evalString(String exp) {
/* 74 */     if (exp != null) {
/* 75 */       throw new IllegalArgumentException("expression [" + exp + "] not allowed on number");
/*    */     }
/* 77 */     return this.value;
/*    */   }
/*    */   
/*    */   public boolean evalBoolean(String exp) {
/* 81 */     if (exp != null) {
/* 82 */       throw new IllegalArgumentException("expression [" + exp + "] not allowed on number");
/*    */     }
/* 84 */     return Boolean.parseBoolean(this.value);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\json\JsonElementNumber.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */