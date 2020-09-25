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
/*    */ public class H2SequenceIdGenerator
/*    */   extends SequenceIdGenerator
/*    */ {
/*    */   private final String baseSql;
/*    */   private final String unionBaseSql;
/*    */   
/*    */   public H2SequenceIdGenerator(BackgroundExecutor be, DataSource ds, String seqName, int batchSize) {
/* 19 */     super(be, ds, seqName, batchSize);
/* 20 */     this.baseSql = "select " + seqName + ".nextval";
/* 21 */     this.unionBaseSql = " union " + this.baseSql;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSql(int batchSize) {
/* 26 */     StringBuilder sb = new StringBuilder();
/* 27 */     sb.append(this.baseSql);
/* 28 */     for (int i = 1; i < batchSize; i++) {
/* 29 */       sb.append(this.unionBaseSql);
/*    */     }
/* 31 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\H2SequenceIdGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */