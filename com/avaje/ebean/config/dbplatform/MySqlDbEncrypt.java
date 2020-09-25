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
/*    */ public class MySqlDbEncrypt
/*    */   extends AbstractDbEncrypt
/*    */ {
/*    */   private static class MyVarcharFunction
/*    */     implements DbEncryptFunction
/*    */   {
/*    */     private MyVarcharFunction() {}
/*    */     
/*    */     public String getDecryptSql(String columnWithTableAlias) {
/* 38 */       return "CONVERT(AES_DECRYPT(" + columnWithTableAlias + ",?) USING UTF8)";
/*    */     }
/*    */     
/*    */     public String getEncryptBindSql() {
/* 42 */       return "AES_ENCRYPT(?,?)";
/*    */     } }
/*    */   
/*    */   private static class MyDateFunction implements DbEncryptFunction {
/*    */     private MyDateFunction() {}
/*    */     
/*    */     public String getDecryptSql(String columnWithTableAlias) {
/* 49 */       return "STR_TO_DATE(AES_DECRYPT(" + columnWithTableAlias + ",?),'%Y%d%m')";
/*    */     }
/*    */     
/*    */     public String getEncryptBindSql() {
/* 53 */       return "AES_ENCRYPT(DATE_FORMAT(?,'%Y%d%m'),?)";
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\MySqlDbEncrypt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */