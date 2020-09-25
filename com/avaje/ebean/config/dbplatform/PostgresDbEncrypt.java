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
/*    */ public class PostgresDbEncrypt
/*    */   extends AbstractDbEncrypt
/*    */ {
/*    */   private static class PgVarcharFunction
/*    */     implements DbEncryptFunction
/*    */   {
/*    */     private PgVarcharFunction() {}
/*    */     
/*    */     public String getDecryptSql(String columnWithTableAlias) {
/* 39 */       return "pgp_sym_decrypt(" + columnWithTableAlias + ",?)";
/*    */     }
/*    */     
/*    */     public String getEncryptBindSql() {
/* 43 */       return "pgp_sym_encrypt(?,?)";
/*    */     } }
/*    */   
/*    */   private static class PgDateFunction implements DbEncryptFunction {
/*    */     private PgDateFunction() {}
/*    */     
/*    */     public String getDecryptSql(String columnWithTableAlias) {
/* 50 */       return "to_date(pgp_sym_decrypt(" + columnWithTableAlias + ",?),'YYYYMMDD')";
/*    */     }
/*    */     
/*    */     public String getEncryptBindSql() {
/* 54 */       return "pgp_sym_encrypt(to_char(?::date,'YYYYMMDD'),?)";
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\PostgresDbEncrypt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */