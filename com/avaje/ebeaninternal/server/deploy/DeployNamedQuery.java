/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebean.RawSql;
/*    */ import javax.persistence.NamedQuery;
/*    */ import javax.persistence.QueryHint;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeployNamedQuery
/*    */ {
/*    */   private final String name;
/*    */   private final String query;
/*    */   private final QueryHint[] hints;
/*    */   private final DRawSqlSelect sqlSelect;
/*    */   private final RawSql rawSql;
/*    */   
/*    */   public DeployNamedQuery(NamedQuery namedQuery) {
/* 21 */     this.name = namedQuery.name();
/* 22 */     this.query = namedQuery.query();
/* 23 */     this.hints = namedQuery.hints();
/* 24 */     this.sqlSelect = null;
/* 25 */     this.rawSql = null;
/*    */   }
/*    */   
/*    */   public DeployNamedQuery(String name, String query, QueryHint[] hints) {
/* 29 */     this.name = name;
/* 30 */     this.query = query;
/* 31 */     this.hints = hints;
/* 32 */     this.sqlSelect = null;
/* 33 */     this.rawSql = null;
/*    */   }
/*    */   
/*    */   public DeployNamedQuery(String name, RawSql rawSql) {
/* 37 */     this.name = name;
/* 38 */     this.query = null;
/* 39 */     this.hints = null;
/* 40 */     this.sqlSelect = null;
/* 41 */     this.rawSql = rawSql;
/*    */   }
/*    */   
/*    */   public DeployNamedQuery(DRawSqlSelect sqlSelect) {
/* 45 */     this.name = sqlSelect.getName();
/* 46 */     this.query = null;
/* 47 */     this.hints = null;
/* 48 */     this.sqlSelect = sqlSelect;
/* 49 */     this.rawSql = null;
/*    */   }
/*    */   
/*    */   public boolean isRawSql() {
/* 53 */     return (this.rawSql != null);
/*    */   }
/*    */   
/*    */   public boolean isSqlSelect() {
/* 57 */     return (this.sqlSelect != null);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 61 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getQuery() {
/* 65 */     return this.query;
/*    */   }
/*    */   
/*    */   public QueryHint[] getHints() {
/* 69 */     return this.hints;
/*    */   }
/*    */   
/*    */   public RawSql getRawSql() {
/* 73 */     return this.rawSql;
/*    */   }
/*    */   
/*    */   public DRawSqlSelect getSqlSelect() {
/* 77 */     return this.sqlSelect;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\DeployNamedQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */