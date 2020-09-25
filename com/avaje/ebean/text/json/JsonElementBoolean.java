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
/*    */ public class JsonElementBoolean
/*    */   implements JsonElement
/*    */ {
/* 37 */   public static final JsonElementBoolean TRUE = new JsonElementBoolean(Boolean.valueOf(true));
/*    */   
/* 39 */   public static final JsonElementBoolean FALSE = new JsonElementBoolean(Boolean.valueOf(false));
/*    */   
/*    */   private final Boolean value;
/*    */   
/*    */   private JsonElementBoolean(Boolean value) {
/* 44 */     this.value = value;
/*    */   }
/*    */   
/*    */   public Boolean getValue() {
/* 48 */     return this.value;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 52 */     return Boolean.toString(this.value.booleanValue());
/*    */   }
/*    */   
/*    */   public boolean isPrimitive() {
/* 56 */     return true;
/*    */   }
/*    */   
/*    */   public String toPrimitiveString() {
/* 60 */     return this.value.toString();
/*    */   }
/*    */   
/*    */   public Object eval(String exp) {
/* 64 */     if (exp != null) {
/* 65 */       throw new IllegalArgumentException("expression [" + exp + "] not allowed on boolean");
/*    */     }
/* 67 */     return this.value;
/*    */   }
/*    */   
/*    */   public int evalInt(String exp) {
/* 71 */     return this.value.booleanValue() ? 1 : 0;
/*    */   }
/*    */   
/*    */   public String evalString(String exp) {
/* 75 */     return toString();
/*    */   }
/*    */   
/*    */   public boolean evalBoolean(String exp) {
/* 79 */     return this.value.booleanValue();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\json\JsonElementBoolean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */