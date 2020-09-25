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
/*    */ public abstract class AbstractDbEncrypt
/*    */   implements DbEncrypt
/*    */ {
/*    */   protected DbEncryptFunction varcharEncryptFunction;
/*    */   protected DbEncryptFunction dateEncryptFunction;
/*    */   protected DbEncryptFunction timestampEncryptFunction;
/*    */   
/*    */   public DbEncryptFunction getDbEncryptFunction(int jdbcType) {
/* 62 */     switch (jdbcType) {
/*    */       case 12:
/* 64 */         return this.varcharEncryptFunction;
/*    */       case 2005:
/* 66 */         return this.varcharEncryptFunction;
/*    */       case 1:
/* 68 */         return this.varcharEncryptFunction;
/*    */       case -1:
/* 70 */         return this.varcharEncryptFunction;
/*    */       
/*    */       case 91:
/* 73 */         return this.dateEncryptFunction;
/*    */       
/*    */       case 93:
/* 76 */         return this.timestampEncryptFunction;
/*    */     } 
/*    */     
/* 79 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEncryptDbType() {
/* 87 */     return -3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isBindEncryptDataFirst() {
/* 94 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\AbstractDbEncrypt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */