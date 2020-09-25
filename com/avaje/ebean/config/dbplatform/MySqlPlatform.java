/*    */ package com.avaje.ebean.config.dbplatform;
/*    */ 
/*    */ import com.avaje.ebean.BackgroundExecutor;
/*    */ import javax.sql.DataSource;
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
/*    */ public class MySqlPlatform
/*    */   extends DatabasePlatform
/*    */ {
/*    */   public MySqlPlatform() {
/* 44 */     this.name = "mysql";
/* 45 */     this.selectCountWithAlias = true;
/* 46 */     this.dbEncrypt = new MySqlDbEncrypt();
/*    */     
/* 48 */     this.dbIdentity.setIdType(IdType.IDENTITY);
/* 49 */     this.dbIdentity.setSupportsGetGeneratedKeys(true);
/* 50 */     this.dbIdentity.setSupportsIdentity(true);
/* 51 */     this.dbIdentity.setSupportsSequence(false);
/*    */     
/* 53 */     this.openQuote = "`";
/* 54 */     this.closeQuote = "`";
/*    */     
/* 56 */     this.booleanDbType = -7;
/*    */     
/* 58 */     this.dbTypeMap.put(-7, new DbType("tinyint(1) default 0"));
/* 59 */     this.dbTypeMap.put(16, new DbType("tinyint(1) default 0"));
/* 60 */     this.dbTypeMap.put(93, new DbType("datetime"));
/* 61 */     this.dbTypeMap.put(2005, new MySqlClob());
/* 62 */     this.dbTypeMap.put(2004, new MySqlBlob());
/* 63 */     this.dbTypeMap.put(-2, new DbType("binary", 255));
/* 64 */     this.dbTypeMap.put(-3, new DbType("varbinary", 255));
/*    */     
/* 66 */     this.dbDdlSyntax.setMaxConstraintNameLength(64);
/* 67 */     this.dbDdlSyntax.setDisableReferentialIntegrity("SET FOREIGN_KEY_CHECKS=0");
/* 68 */     this.dbDdlSyntax.setEnableReferentialIntegrity("SET FOREIGN_KEY_CHECKS=1");
/* 69 */     this.dbDdlSyntax.setForeignKeySuffix("on delete restrict on update restrict");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IdGenerator createSequenceIdGenerator(BackgroundExecutor be, DataSource ds, String seqName, int batchSize) {
/* 80 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String withForUpdate(String sql) {
/* 85 */     return sql + " for update";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\MySqlPlatform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */