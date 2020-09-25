/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.BackgroundExecutor;
/*     */ import com.avaje.ebean.RawSql;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimitRequest;
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimiter;
/*     */ import com.avaje.ebean.text.PathProperties;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.persist.Binder;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryLimitRequest;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CQueryBuilder
/*     */   implements Constants
/*     */ {
/*     */   private final String tableAliasPlaceHolder;
/*     */   private final String columnAliasPrefix;
/*     */   private final SqlLimiter sqlLimiter;
/*     */   private final RawSqlSelectClauseBuilder sqlSelectBuilder;
/*     */   private final CQueryBuilderRawSql rawSqlHandler;
/*     */   private final Binder binder;
/*     */   private final BackgroundExecutor backgroundExecutor;
/*     */   private final boolean selectCountWithAlias;
/*     */   private DatabasePlatform dbPlatform;
/*     */   
/*     */   public CQueryBuilder(BackgroundExecutor backgroundExecutor, DatabasePlatform dbPlatform, Binder binder) {
/*  76 */     this.backgroundExecutor = backgroundExecutor;
/*  77 */     this.binder = binder;
/*  78 */     this.tableAliasPlaceHolder = GlobalProperties.get("ebean.tableAliasPlaceHolder", "${ta}");
/*  79 */     this.columnAliasPrefix = GlobalProperties.get("ebean.columnAliasPrefix", "c");
/*  80 */     this.sqlSelectBuilder = new RawSqlSelectClauseBuilder(dbPlatform, binder);
/*     */     
/*  82 */     this.sqlLimiter = dbPlatform.getSqlLimiter();
/*  83 */     this.rawSqlHandler = new CQueryBuilderRawSql(this.sqlLimiter, dbPlatform);
/*     */     
/*  85 */     this.selectCountWithAlias = dbPlatform.isSelectCountWithAlias();
/*     */     
/*  87 */     this.dbPlatform = dbPlatform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String prefixOrderByFields(String name, String orderBy) {
/*  95 */     StringBuilder sb = new StringBuilder();
/*  96 */     for (String token : orderBy.split(",")) {
/*  97 */       if (sb.length() > 0) {
/*  98 */         sb.append(", ");
/*     */       }
/*     */       
/* 101 */       sb.append(name);
/* 102 */       sb.append(".");
/* 103 */       sb.append(token.trim());
/*     */     } 
/*     */     
/* 106 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> CQueryFetchIds buildFetchIdsQuery(OrmQueryRequest<T> request) {
/* 114 */     SpiQuery<T> query = request.getQuery();
/*     */     
/* 116 */     query.setSelectId();
/*     */     
/* 118 */     CQueryPredicates predicates = new CQueryPredicates(this.binder, request);
/* 119 */     CQueryPlan queryPlan = request.getQueryPlan();
/* 120 */     if (queryPlan != null) {
/*     */       
/* 122 */       predicates.prepare(false);
/* 123 */       String str = queryPlan.getSql();
/* 124 */       return new CQueryFetchIds(request, predicates, str, this.backgroundExecutor);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 129 */     predicates.prepare(true);
/*     */     
/* 131 */     SqlTree sqlTree = createSqlTree(request, predicates);
/* 132 */     SqlLimitResponse s = buildSql(null, request, predicates, sqlTree);
/* 133 */     String sql = s.getSql();
/*     */ 
/*     */     
/* 136 */     queryPlan = new CQueryPlan(sql, sqlTree, false, s.isIncludesRowNumberColumn(), predicates.getLogWhereSql());
/*     */     
/* 138 */     request.putQueryPlan(queryPlan);
/* 139 */     return new CQueryFetchIds(request, predicates, sql, this.backgroundExecutor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> CQueryRowCount buildRowCountQuery(OrmQueryRequest<T> request) {
/* 147 */     SpiQuery<T> query = request.getQuery();
/*     */ 
/*     */     
/* 150 */     query.setOrder(null);
/*     */     
/* 152 */     boolean hasMany = !query.getManyWhereJoins().isEmpty();
/*     */     
/* 154 */     query.setSelectId();
/*     */     
/* 156 */     String sqlSelect = "select count(*)";
/* 157 */     if (hasMany) {
/*     */       
/* 159 */       query.setDistinct(true);
/* 160 */       sqlSelect = null;
/*     */     } 
/*     */     
/* 163 */     CQueryPredicates predicates = new CQueryPredicates(this.binder, request);
/* 164 */     CQueryPlan queryPlan = request.getQueryPlan();
/* 165 */     if (queryPlan != null) {
/*     */       
/* 167 */       predicates.prepare(false);
/* 168 */       String str = queryPlan.getSql();
/* 169 */       return new CQueryRowCount(request, predicates, str);
/*     */     } 
/*     */     
/* 172 */     predicates.prepare(true);
/*     */     
/* 174 */     SqlTree sqlTree = createSqlTree(request, predicates);
/* 175 */     SqlLimitResponse s = buildSql(sqlSelect, request, predicates, sqlTree);
/* 176 */     String sql = s.getSql();
/* 177 */     if (hasMany) {
/* 178 */       sql = "select count(*) from ( " + sql + ")";
/* 179 */       if (this.selectCountWithAlias) {
/* 180 */         sql = sql + " as c";
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 185 */     queryPlan = new CQueryPlan(sql, sqlTree, false, s.isIncludesRowNumberColumn(), predicates.getLogWhereSql());
/* 186 */     request.putQueryPlan(queryPlan);
/*     */     
/* 188 */     return new CQueryRowCount(request, predicates, sql);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> CQuery<T> buildQuery(OrmQueryRequest<T> request) {
/* 197 */     if (request.isSqlSelect()) {
/* 198 */       return this.sqlSelectBuilder.build(request);
/*     */     }
/*     */     
/* 201 */     CQueryPredicates predicates = new CQueryPredicates(this.binder, request);
/*     */     
/* 203 */     CQueryPlan queryPlan = request.getQueryPlan();
/* 204 */     if (queryPlan != null) {
/*     */ 
/*     */       
/* 207 */       predicates.prepare(false);
/* 208 */       return new CQuery<T>(request, predicates, queryPlan);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     predicates.prepare(true);
/*     */ 
/*     */     
/* 222 */     SqlTree sqlTree = createSqlTree(request, predicates);
/* 223 */     SqlLimitResponse res = buildSql(null, request, predicates, sqlTree);
/*     */     
/* 225 */     boolean rawSql = request.isRawSql();
/* 226 */     if (rawSql) {
/* 227 */       queryPlan = new CQueryPlanRawSql(request, res, sqlTree, predicates.getLogWhereSql());
/*     */     } else {
/*     */       
/* 230 */       queryPlan = new CQueryPlan(request, res, sqlTree, rawSql, predicates.getLogWhereSql(), null);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 235 */     request.putQueryPlan(queryPlan);
/*     */     
/* 237 */     return new CQuery<T>(request, predicates, queryPlan);
/*     */   }
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
/*     */   private SqlTree createSqlTree(OrmQueryRequest<?> request, CQueryPredicates predicates) {
/* 253 */     if (request.isRawSql()) {
/* 254 */       return createRawSqlSqlTree(request, predicates);
/*     */     }
/*     */     
/* 257 */     return (new SqlTreeBuilder(this.tableAliasPlaceHolder, this.columnAliasPrefix, request, predicates)).build();
/*     */   }
/*     */ 
/*     */   
/*     */   private SqlTree createRawSqlSqlTree(OrmQueryRequest<?> request, CQueryPredicates predicates) {
/* 262 */     BeanDescriptor<?> descriptor = request.getBeanDescriptor();
/* 263 */     RawSql.ColumnMapping columnMapping = request.getQuery().getRawSql().getColumnMapping();
/*     */     
/* 265 */     PathProperties pathProps = new PathProperties();
/*     */ 
/*     */     
/* 268 */     Iterator<RawSql.ColumnMapping.Column> it = columnMapping.getColumns();
/* 269 */     while (it.hasNext()) {
/* 270 */       RawSql.ColumnMapping.Column column = it.next();
/* 271 */       String propertyName = column.getPropertyName();
/* 272 */       if (!"$$_IGNORE_COLUMN_$$".equals(propertyName)) {
/*     */         
/* 274 */         ElPropertyValue el = descriptor.getElGetValue(propertyName);
/* 275 */         if (el == null) {
/* 276 */           String msg = "Property [" + propertyName + "] not found on " + descriptor.getFullName();
/* 277 */           throw new PersistenceException(msg);
/*     */         } 
/* 279 */         BeanProperty beanProperty = el.getBeanProperty();
/* 280 */         if (beanProperty.isId()) {
/*     */           
/* 282 */           propertyName = SplitName.parent(propertyName);
/* 283 */         } else if (beanProperty instanceof com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne) {
/* 284 */           String msg = "Column [" + column.getDbColumn() + "] mapped to complex Property[" + propertyName + "]";
/* 285 */           msg = msg + ". It should be mapped to a simple property (proably the Id property). ";
/* 286 */           throw new PersistenceException(msg);
/*     */         } 
/* 288 */         if (propertyName != null) {
/* 289 */           String[] pathProp = SplitName.split(propertyName);
/* 290 */           pathProps.addToPath(pathProp[0], pathProp[1]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 295 */     OrmQueryDetail detail = new OrmQueryDetail();
/*     */ 
/*     */     
/* 298 */     Iterator<String> pathIt = pathProps.getPaths().iterator();
/* 299 */     while (pathIt.hasNext()) {
/* 300 */       String path = pathIt.next();
/* 301 */       Set<String> props = pathProps.get(path);
/* 302 */       detail.getChunk(path, true).setDefaultProperties(null, props);
/*     */     } 
/*     */ 
/*     */     
/* 306 */     return (new SqlTreeBuilder(request, predicates, detail)).build();
/*     */   }
/*     */ 
/*     */   
/*     */   private SqlLimitResponse buildSql(String selectClause, OrmQueryRequest<?> request, CQueryPredicates predicates, SqlTree select) {
/* 311 */     SpiQuery<?> query = request.getQuery();
/*     */     
/* 313 */     RawSql rawSql = query.getRawSql();
/* 314 */     if (rawSql != null) {
/* 315 */       return this.rawSqlHandler.buildSql(request, predicates, rawSql.getSql());
/*     */     }
/*     */     
/* 318 */     BeanPropertyAssocMany<?> manyProp = select.getManyProperty();
/*     */     
/* 320 */     boolean useSqlLimiter = false;
/*     */     
/* 322 */     StringBuilder sb = new StringBuilder(500);
/*     */     
/* 324 */     if (selectClause != null) {
/* 325 */       sb.append(selectClause);
/*     */     }
/*     */     else {
/*     */       
/* 329 */       useSqlLimiter = (query.hasMaxRowsOrFirstRow() && manyProp == null);
/*     */       
/* 331 */       if (!useSqlLimiter) {
/* 332 */         sb.append("select ");
/* 333 */         if (query.isDistinct()) {
/* 334 */           sb.append("distinct ");
/*     */         }
/*     */       } 
/*     */       
/* 338 */       sb.append(select.getSelectSql());
/*     */     } 
/*     */     
/* 341 */     sb.append(" ").append('\n');
/* 342 */     sb.append("from ");
/*     */ 
/*     */ 
/*     */     
/* 346 */     sb.append(select.getFromSql());
/*     */     
/* 348 */     String inheritanceWhere = select.getInheritanceWhereSql();
/*     */     
/* 350 */     boolean hasWhere = false;
/* 351 */     if (inheritanceWhere.length() > 0) {
/* 352 */       sb.append(" ").append('\n').append("where");
/* 353 */       sb.append(inheritanceWhere);
/* 354 */       hasWhere = true;
/*     */     } 
/*     */     
/* 357 */     if (request.isFindById() || query.getId() != null) {
/* 358 */       if (hasWhere) {
/* 359 */         sb.append(" and ");
/*     */       } else {
/* 361 */         sb.append('\n').append("where ");
/*     */       } 
/*     */       
/* 364 */       BeanDescriptor<?> desc = request.getBeanDescriptor();
/* 365 */       String idSql = desc.getIdBinderIdSql();
/* 366 */       if (idSql.isEmpty()) {
/* 367 */         throw new IllegalStateException("Executing FindById query on entity bean " + desc.getName() + " that doesn't have an @Id property??");
/*     */       }
/*     */       
/* 370 */       sb.append(idSql).append(" ");
/* 371 */       hasWhere = true;
/*     */     } 
/*     */     
/* 374 */     String dbWhere = predicates.getDbWhere();
/* 375 */     if (!isEmpty(dbWhere)) {
/* 376 */       if (!hasWhere) {
/* 377 */         hasWhere = true;
/* 378 */         sb.append(" ").append('\n').append("where ");
/*     */       } else {
/* 380 */         sb.append("and ");
/*     */       } 
/* 382 */       sb.append(dbWhere);
/*     */     } 
/*     */     
/* 385 */     String dbFilterMany = predicates.getDbFilterMany();
/* 386 */     if (!isEmpty(dbFilterMany)) {
/* 387 */       if (!hasWhere) {
/* 388 */         sb.append(" ").append('\n').append("where ");
/*     */       } else {
/* 390 */         sb.append("and ");
/*     */       } 
/* 392 */       sb.append(dbFilterMany);
/*     */     } 
/*     */     
/* 395 */     String dbOrderBy = predicates.getDbOrderBy();
/* 396 */     if (dbOrderBy != null) {
/* 397 */       sb.append(" ").append('\n');
/* 398 */       sb.append("order by ").append(dbOrderBy);
/*     */     } 
/*     */     
/* 401 */     if (useSqlLimiter) {
/*     */       
/* 403 */       OrmQueryLimitRequest ormQueryLimitRequest = new OrmQueryLimitRequest(sb.toString(), dbOrderBy, query, this.dbPlatform);
/* 404 */       return this.sqlLimiter.limit((SqlLimitRequest)ormQueryLimitRequest);
/*     */     } 
/*     */ 
/*     */     
/* 408 */     return new SqlLimitResponse(this.dbPlatform.completeSql(sb.toString(), query), false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isEmpty(String s) {
/* 414 */     return (s == null || s.length() == 0);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\CQueryBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */