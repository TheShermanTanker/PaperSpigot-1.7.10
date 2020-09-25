/*    */ package com.avaje.ebean.config.dbplatform;
/*    */ 
/*    */ import com.avaje.ebean.BackgroundExecutor;
/*    */ import com.avaje.ebean.config.GlobalProperties;
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
/*    */ public class PostgresPlatform
/*    */   extends DatabasePlatform
/*    */ {
/*    */   public PostgresPlatform() {
/* 38 */     this.name = "postgres";
/* 39 */     this.selectCountWithAlias = true;
/* 40 */     this.blobDbType = -4;
/* 41 */     this.clobDbType = 12;
/*    */     
/* 43 */     this.dbEncrypt = new PostgresDbEncrypt();
/*    */     
/* 45 */     this.dbIdentity.setSupportsGetGeneratedKeys(false);
/* 46 */     this.dbIdentity.setIdType(IdType.SEQUENCE);
/* 47 */     this.dbIdentity.setSupportsSequence(true);
/*    */     
/* 49 */     String colAlias = GlobalProperties.get("ebean.columnAliasPrefix", null);
/* 50 */     if (colAlias == null)
/*    */     {
/* 52 */       GlobalProperties.put("ebean.columnAliasPrefix", "as c");
/*    */     }
/*    */     
/* 55 */     this.openQuote = "\"";
/* 56 */     this.closeQuote = "\"";
/*    */ 
/*    */ 
/*    */     
/* 60 */     this.dbTypeMap.put(4, new DbType("integer", false));
/* 61 */     this.dbTypeMap.put(8, new DbType("float"));
/* 62 */     this.dbTypeMap.put(-6, new DbType("smallint"));
/* 63 */     this.dbTypeMap.put(3, new DbType("decimal", 38));
/*    */     
/* 65 */     this.dbTypeMap.put(-2, new DbType("bytea", false));
/* 66 */     this.dbTypeMap.put(-3, new DbType("bytea", false));
/*    */     
/* 68 */     this.dbTypeMap.put(2004, new DbType("bytea", false));
/* 69 */     this.dbTypeMap.put(2005, new DbType("text"));
/* 70 */     this.dbTypeMap.put(-4, new DbType("bytea", false));
/* 71 */     this.dbTypeMap.put(-1, new DbType("text"));
/*    */     
/* 73 */     this.dbDdlSyntax.setDropTableCascade("cascade");
/* 74 */     this.dbDdlSyntax.setDropIfExists("if exists");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IdGenerator createSequenceIdGenerator(BackgroundExecutor be, DataSource ds, String seqName, int batchSize) {
/* 84 */     return new PostgresSequenceIdGenerator(be, ds, seqName, batchSize);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String withForUpdate(String sql) {
/* 89 */     return sql + " for update";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\PostgresPlatform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */