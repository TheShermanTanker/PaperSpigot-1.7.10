/*    */ package com.avaje.ebeaninternal.server.querydefn;
/*    */ 
/*    */ 
/*    */ public class NaturalKeyBindParam
/*    */ {
/*    */   private final String name;
/*    */   private final Object value;
/*    */   
/*    */   public NaturalKeyBindParam(String name, Object value) {
/* 10 */     this.name = name;
/* 11 */     this.value = value;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 15 */     return this.name;
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 19 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\querydefn\NaturalKeyBindParam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */