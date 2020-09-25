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
/*    */ public class JsonElementNull
/*    */   implements JsonElement
/*    */ {
/* 36 */   public static final JsonElementNull NULL = new JsonElementNull();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getValue() {
/* 42 */     return "null";
/*    */   }
/*    */   
/*    */   public String toString() {
/* 46 */     return "json null";
/*    */   }
/*    */   
/*    */   public boolean isPrimitive() {
/* 50 */     return true;
/*    */   }
/*    */   
/*    */   public String toPrimitiveString() {
/* 54 */     return null;
/*    */   }
/*    */   
/*    */   public Object eval(String exp) {
/* 58 */     if (exp != null) {
/* 59 */       throw new IllegalArgumentException("expression [" + exp + "] not allowed on null");
/*    */     }
/* 61 */     return null;
/*    */   }
/*    */   
/*    */   public int evalInt(String exp) {
/* 65 */     return 0;
/*    */   }
/*    */   
/*    */   public String evalString(String exp) {
/* 69 */     return null;
/*    */   }
/*    */   
/*    */   public boolean evalBoolean(String exp) {
/* 73 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\json\JsonElementNull.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */