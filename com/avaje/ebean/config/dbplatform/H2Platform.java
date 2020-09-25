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
/*    */ 
/*    */ public class H2Platform
/*    */   extends DatabasePlatform
/*    */ {
/*    */   public H2Platform() {
/* 39 */     boolean useIdentity = GlobalProperties.getBoolean("ebean.h2platform.useIdentity", false);
/*    */     
/* 41 */     IdType idType = useIdentity ? IdType.IDENTITY : IdType.SEQUENCE;
/* 42 */     this.dbIdentity.setIdType(idType);
/*    */     
/* 44 */     this.dbIdentity.setSupportsGetGeneratedKeys(true);
/* 45 */     this.dbIdentity.setSupportsSequence(true);
/* 46 */     this.dbIdentity.setSupportsIdentity(true);
/*    */     
/* 48 */     this.openQuote = "\"";
/* 49 */     this.closeQuote = "\"";
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 54 */     this.dbDdlSyntax.setDropIfExists("if exists");
/* 55 */     this.dbDdlSyntax.setDisableReferentialIntegrity("SET REFERENTIAL_INTEGRITY FALSE");
/* 56 */     this.dbDdlSyntax.setEnableReferentialIntegrity("SET REFERENTIAL_INTEGRITY TRUE");
/* 57 */     this.dbDdlSyntax.setForeignKeySuffix("on delete restrict on update restrict");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IdGenerator createSequenceIdGenerator(BackgroundExecutor be, DataSource ds, String seqName, int batchSize) {
/* 67 */     return new H2SequenceIdGenerator(be, ds, seqName, batchSize);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String withForUpdate(String sql) {
/* 72 */     return sql + " for update";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\H2Platform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */