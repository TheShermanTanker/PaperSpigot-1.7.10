/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.RawSql;
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimitRequest;
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimiter;
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryLimitRequest;
/*     */ import com.avaje.ebeaninternal.server.util.BindParamsParser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CQueryBuilderRawSql
/*     */   implements Constants
/*     */ {
/*     */   private final SqlLimiter sqlLimiter;
/*     */   private final DatabasePlatform dbPlatform;
/*     */   
/*     */   CQueryBuilderRawSql(SqlLimiter sqlLimiter, DatabasePlatform dbPlatform) {
/*  39 */     this.sqlLimiter = sqlLimiter;
/*  40 */     this.dbPlatform = dbPlatform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SqlLimitResponse buildSql(OrmQueryRequest<?> request, CQueryPredicates predicates, RawSql.Sql rsql) {
/*  48 */     if (!rsql.isParsed()) {
/*  49 */       String str = rsql.getUnparsedSql();
/*  50 */       BindParams bindParams = request.getQuery().getBindParams();
/*  51 */       if (bindParams != null && bindParams.requiresNamedParamsPrepare())
/*     */       {
/*  53 */         str = BindParamsParser.parse(bindParams, str);
/*     */       }
/*     */       
/*  56 */       return new SqlLimitResponse(str, false);
/*     */     } 
/*     */     
/*  59 */     String orderBy = getOrderBy(predicates, rsql);
/*     */ 
/*     */     
/*  62 */     String sql = buildMainQuery(orderBy, request, predicates, rsql);
/*     */     
/*  64 */     SpiQuery<?> query = request.getQuery();
/*  65 */     if (query.hasMaxRowsOrFirstRow() && this.sqlLimiter != null)
/*     */     {
/*  67 */       return this.sqlLimiter.limit((SqlLimitRequest)new OrmQueryLimitRequest(sql, orderBy, query, this.dbPlatform));
/*     */     }
/*     */ 
/*     */     
/*  71 */     String prefix = "select " + (rsql.isDistinct() ? "distinct " : "");
/*  72 */     sql = prefix + sql;
/*  73 */     return new SqlLimitResponse(sql, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String buildMainQuery(String orderBy, OrmQueryRequest<?> request, CQueryPredicates predicates, RawSql.Sql sql) {
/*  79 */     StringBuilder sb = new StringBuilder();
/*  80 */     sb.append(sql.getPreFrom());
/*  81 */     sb.append(" ");
/*  82 */     sb.append('\n');
/*     */     
/*  84 */     String s = sql.getPreWhere();
/*  85 */     BindParams bindParams = request.getQuery().getBindParams();
/*  86 */     if (bindParams != null && bindParams.requiresNamedParamsPrepare())
/*     */     {
/*     */ 
/*     */       
/*  90 */       s = BindParamsParser.parse(bindParams, s);
/*     */     }
/*  92 */     sb.append(s);
/*  93 */     sb.append(" ");
/*     */     
/*  95 */     String dynamicWhere = null;
/*  96 */     if (request.getQuery().getId() != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 101 */       BeanDescriptor<?> descriptor = request.getBeanDescriptor();
/*     */ 
/*     */       
/* 104 */       dynamicWhere = descriptor.getIdBinderIdSql();
/*     */     } 
/*     */     
/* 107 */     String dbWhere = predicates.getDbWhere();
/* 108 */     if (!isEmpty(dbWhere)) {
/* 109 */       if (dynamicWhere == null) {
/* 110 */         dynamicWhere = dbWhere;
/*     */       } else {
/* 112 */         dynamicWhere = dynamicWhere + " and " + dbWhere;
/*     */       } 
/*     */     }
/*     */     
/* 116 */     if (!isEmpty(dynamicWhere)) {
/* 117 */       sb.append('\n');
/* 118 */       if (sql.isAndWhereExpr()) {
/* 119 */         sb.append("and ");
/*     */       } else {
/* 121 */         sb.append("where ");
/*     */       } 
/* 123 */       sb.append(dynamicWhere);
/* 124 */       sb.append(" ");
/*     */     } 
/*     */     
/* 127 */     String preHaving = sql.getPreHaving();
/* 128 */     if (!isEmpty(preHaving)) {
/* 129 */       sb.append('\n');
/* 130 */       sb.append(preHaving);
/* 131 */       sb.append(" ");
/*     */     } 
/*     */     
/* 134 */     String dbHaving = predicates.getDbHaving();
/* 135 */     if (!isEmpty(dbHaving)) {
/* 136 */       sb.append(" ");
/* 137 */       sb.append('\n');
/* 138 */       if (sql.isAndHavingExpr()) {
/* 139 */         sb.append("and ");
/*     */       } else {
/* 141 */         sb.append("having ");
/*     */       } 
/* 143 */       sb.append(dbHaving);
/* 144 */       sb.append(" ");
/*     */     } 
/*     */     
/* 147 */     if (!isEmpty(orderBy)) {
/* 148 */       sb.append('\n');
/* 149 */       sb.append(" order by ").append(orderBy);
/*     */     } 
/*     */     
/* 152 */     return sb.toString().trim();
/*     */   }
/*     */   
/*     */   private boolean isEmpty(String s) {
/* 156 */     return (s == null || s.length() == 0);
/*     */   }
/*     */   
/*     */   private String getOrderBy(CQueryPredicates predicates, RawSql.Sql sql) {
/* 160 */     String orderBy = predicates.getDbOrderBy();
/* 161 */     if (orderBy != null) {
/* 162 */       return orderBy;
/*     */     }
/* 164 */     return sql.getOrderBy();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\CQueryBuilderRawSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */