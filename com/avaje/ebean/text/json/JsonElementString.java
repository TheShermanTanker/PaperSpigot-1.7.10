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
/*    */ public class JsonElementString
/*    */   implements JsonElement
/*    */ {
/*    */   private final String value;
/*    */   
/*    */   public JsonElementString(String value) {
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
/* 63 */     return this.value;
/*    */   }
/*    */   
/*    */   public int evalInt(String exp) {
/* 67 */     if (exp != null) {
/* 68 */       throw new IllegalArgumentException("expression [" + exp + "] not allowed on number");
/*    */     }
/*    */     try {
/* 71 */       return Integer.parseInt(this.value);
/* 72 */     } catch (NumberFormatException e) {
/* 73 */       return 0;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String evalString(String exp) {
/* 78 */     if (exp != null) {
/* 79 */       throw new IllegalArgumentException("expression [" + exp + "] not allowed on number");
/*    */     }
/* 81 */     return this.value;
/*    */   }
/*    */   
/*    */   public boolean evalBoolean(String exp) {
/* 85 */     if (exp != null) {
/* 86 */       throw new IllegalArgumentException("expression [" + exp + "] not allowed on number");
/*    */     }
/* 88 */     return Boolean.parseBoolean(exp);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\json\JsonElementString.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */