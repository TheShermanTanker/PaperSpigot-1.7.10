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
/*    */ public class Oracle10Platform
/*    */   extends DatabasePlatform
/*    */ {
/*    */   public Oracle10Platform() {
/* 34 */     this.name = "oracle";
/* 35 */     this.dbEncrypt = new Oracle10DbEncrypt();
/*    */     
/* 37 */     this.sqlLimiter = new RownumSqlLimiter();
/*    */ 
/*    */ 
/*    */     
/* 41 */     this.dbIdentity.setSupportsGetGeneratedKeys(false);
/* 42 */     this.dbIdentity.setIdType(IdType.SEQUENCE);
/* 43 */     this.dbIdentity.setSupportsSequence(true);
/*    */     
/* 45 */     this.treatEmptyStringsAsNull = true;
/*    */     
/* 47 */     this.openQuote = "\"";
/* 48 */     this.closeQuote = "\"";
/*    */     
/* 50 */     this.booleanDbType = 4;
/* 51 */     this.dbTypeMap.put(16, new DbType("number(1) default 0"));
/*    */     
/* 53 */     this.dbTypeMap.put(4, new DbType("number", 10));
/* 54 */     this.dbTypeMap.put(-5, new DbType("number", 19));
/* 55 */     this.dbTypeMap.put(7, new DbType("number", 19, 4));
/* 56 */     this.dbTypeMap.put(8, new DbType("number", 19, 4));
/* 57 */     this.dbTypeMap.put(5, new DbType("number", 5));
/* 58 */     this.dbTypeMap.put(-6, new DbType("number", 3));
/* 59 */     this.dbTypeMap.put(3, new DbType("number", 38));
/*    */     
/* 61 */     this.dbTypeMap.put(12, new DbType("varchar2", 255));
/*    */     
/* 63 */     this.dbTypeMap.put(-4, new DbType("blob"));
/* 64 */     this.dbTypeMap.put(-1, new DbType("clob"));
/* 65 */     this.dbTypeMap.put(-3, new DbType("raw", 255));
/* 66 */     this.dbTypeMap.put(-2, new DbType("raw", 255));
/*    */     
/* 68 */     this.dbTypeMap.put(92, new DbType("timestamp"));
/*    */     
/* 70 */     this.dbDdlSyntax.setDropTableCascade("cascade constraints purge");
/* 71 */     this.dbDdlSyntax.setIdentity(null);
/* 72 */     this.dbDdlSyntax.setMaxConstraintNameLength(30);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IdGenerator createSequenceIdGenerator(BackgroundExecutor be, DataSource ds, String seqName, int batchSize) {
/* 78 */     return new OracleSequenceIdGenerator(be, ds, seqName, batchSize);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String withForUpdate(String sql) {
/* 83 */     return sql + " for update";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\Oracle10Platform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */