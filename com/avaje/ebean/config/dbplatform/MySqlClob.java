/*    */ package com.avaje.ebean.config.dbplatform;
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
/*    */ public class MySqlClob
/*    */   extends DbType
/*    */ {
/*    */   private static final int POWER_2_16 = 65536;
/*    */   private static final int POWER_2_24 = 16777216;
/*    */   
/*    */   public MySqlClob() {
/* 36 */     super("text");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String renderType(int deployLength, int deployScale) {
/* 42 */     if (deployLength >= 16777216) {
/* 43 */       return "longtext";
/*    */     }
/* 45 */     if (deployLength >= 65536) {
/* 46 */       return "mediumtext";
/*    */     }
/* 48 */     if (deployLength < 1)
/*    */     {
/* 50 */       return "longtext";
/*    */     }
/* 52 */     return "text";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\MySqlClob.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */