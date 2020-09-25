/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.annotation.SqlSelect;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DRawSqlMeta
/*     */ {
/*     */   private String name;
/*     */   private String tableAlias;
/*     */   private String extend;
/*     */   private String query;
/*     */   private boolean debug;
/*     */   private String where;
/*     */   private String having;
/*     */   private String columnMapping;
/*     */   
/*     */   public DRawSqlMeta(SqlSelect sqlSelect) {
/*  24 */     this.debug = sqlSelect.debug();
/*  25 */     this.name = sqlSelect.name();
/*  26 */     this.tableAlias = toNull(sqlSelect.tableAlias());
/*  27 */     this.extend = toNull(sqlSelect.extend());
/*  28 */     this.having = toNull(sqlSelect.having());
/*  29 */     this.where = toNull(sqlSelect.where());
/*  30 */     this.columnMapping = toNull(sqlSelect.columnMapping());
/*  31 */     this.query = toNull(sqlSelect.query());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DRawSqlMeta(String name, String extend, String query, boolean debug, String where, String having, String columnMapping) {
/*  37 */     this.name = name;
/*  38 */     this.extend = extend;
/*  39 */     this.query = query;
/*  40 */     this.debug = debug;
/*  41 */     this.having = having;
/*  42 */     this.where = where;
/*  43 */     this.columnMapping = columnMapping;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  47 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  51 */     this.name = name;
/*     */   }
/*     */   
/*     */   public void setTableAlias(String tableAlias) {
/*  55 */     this.tableAlias = tableAlias;
/*     */   }
/*     */   
/*     */   public String getTableAlias() {
/*  59 */     return this.tableAlias;
/*     */   }
/*     */   
/*     */   public String getExtend() {
/*  63 */     return this.extend;
/*     */   }
/*     */   
/*     */   public void setExtend(String extend) {
/*  67 */     this.extend = extend;
/*     */   }
/*     */   
/*     */   public String getQuery() {
/*  71 */     return this.query;
/*     */   }
/*     */   
/*     */   public void setQuery(String query) {
/*  75 */     this.query = query;
/*     */   }
/*     */   
/*     */   public boolean isDebug() {
/*  79 */     return this.debug;
/*     */   }
/*     */   
/*     */   public void setDebug(boolean debug) {
/*  83 */     this.debug = debug;
/*     */   }
/*     */   
/*     */   public String getWhere() {
/*  87 */     return this.where;
/*     */   }
/*     */   
/*     */   public void setWhere(String where) {
/*  91 */     this.where = where;
/*     */   }
/*     */   
/*     */   public String getHaving() {
/*  95 */     return this.having;
/*     */   }
/*     */   
/*     */   public void setHaving(String having) {
/*  99 */     this.having = having;
/*     */   }
/*     */   
/*     */   public String getColumnMapping() {
/* 103 */     return this.columnMapping;
/*     */   }
/*     */   
/*     */   public void setColumnMapping(String columnMapping) {
/* 107 */     this.columnMapping = columnMapping;
/*     */   }
/*     */   
/*     */   public void extend(DRawSqlMeta parentQuery) {
/* 111 */     extendQuery(parentQuery.getQuery());
/* 112 */     extendColumnMapping(parentQuery.getColumnMapping());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void extendQuery(String parentSql) {
/* 119 */     if (this.query == null) {
/* 120 */       this.query = parentSql;
/*     */     } else {
/* 122 */       this.query = parentSql + " " + this.query;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void extendColumnMapping(String parentColumnMapping) {
/* 127 */     if (this.columnMapping == null) {
/* 128 */       this.columnMapping = parentColumnMapping;
/*     */     }
/*     */   }
/*     */   
/*     */   private static String toNull(String s) {
/* 133 */     if (s != null && s.equals("")) {
/* 134 */       return null;
/*     */     }
/* 136 */     return s;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\DRawSqlMeta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */