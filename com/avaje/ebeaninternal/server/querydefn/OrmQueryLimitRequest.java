/*    */ package com.avaje.ebeaninternal.server.querydefn;
/*    */ 
/*    */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*    */ import com.avaje.ebean.config.dbplatform.SqlLimitRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiQuery;
/*    */ 
/*    */ 
/*    */ public class OrmQueryLimitRequest
/*    */   implements SqlLimitRequest
/*    */ {
/*    */   final SpiQuery<?> ormQuery;
/*    */   final DatabasePlatform dbPlatform;
/*    */   final String sql;
/*    */   final String sqlOrderBy;
/*    */   
/*    */   public OrmQueryLimitRequest(String sql, String sqlOrderBy, SpiQuery<?> ormQuery, DatabasePlatform dbPlatform) {
/* 17 */     this.sql = sql;
/* 18 */     this.sqlOrderBy = sqlOrderBy;
/* 19 */     this.ormQuery = ormQuery;
/* 20 */     this.dbPlatform = dbPlatform;
/*    */   }
/*    */   
/*    */   public String getDbOrderBy() {
/* 24 */     return this.sqlOrderBy;
/*    */   }
/*    */   
/*    */   public String getDbSql() {
/* 28 */     return this.sql;
/*    */   }
/*    */   
/*    */   public int getFirstRow() {
/* 32 */     return this.ormQuery.getFirstRow();
/*    */   }
/*    */   
/*    */   public int getMaxRows() {
/* 36 */     return this.ormQuery.getMaxRows();
/*    */   }
/*    */   
/*    */   public boolean isDistinct() {
/* 40 */     return this.ormQuery.isDistinct();
/*    */   }
/*    */   
/*    */   public SpiQuery<?> getOrmQuery() {
/* 44 */     return this.ormQuery;
/*    */   }
/*    */   
/*    */   public DatabasePlatform getDbPlatform() {
/* 48 */     return this.dbPlatform;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\querydefn\OrmQueryLimitRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */