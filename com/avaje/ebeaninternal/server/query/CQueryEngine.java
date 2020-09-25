/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.BackgroundExecutor;
/*     */ import com.avaje.ebean.QueryIterator;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionTouched;
/*     */ import com.avaje.ebean.bean.ObjectGraphNode;
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebeaninternal.api.BeanIdList;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.jmx.MAdminLogging;
/*     */ import com.avaje.ebeaninternal.server.persist.Binder;
/*     */ import java.sql.SQLException;
/*     */ import java.util.concurrent.FutureTask;
/*     */ import java.util.logging.Logger;
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
/*     */ public class CQueryEngine
/*     */ {
/*  43 */   private static final Logger logger = Logger.getLogger(CQueryEngine.class.getName());
/*     */   
/*     */   private final CQueryBuilder queryBuilder;
/*     */   
/*     */   private final MAdminLogging logControl;
/*     */   
/*     */   private final BackgroundExecutor backgroundExecutor;
/*     */   
/*  51 */   private final int defaultSecondaryQueryBatchSize = 100;
/*     */ 
/*     */   
/*     */   public CQueryEngine(DatabasePlatform dbPlatform, MAdminLogging logControl, Binder binder, BackgroundExecutor backgroundExecutor) {
/*  55 */     this.logControl = logControl;
/*  56 */     this.backgroundExecutor = backgroundExecutor;
/*  57 */     this.queryBuilder = new CQueryBuilder(backgroundExecutor, dbPlatform, binder);
/*     */   }
/*     */   
/*     */   public <T> CQuery<T> buildQuery(OrmQueryRequest<T> request) {
/*  61 */     return this.queryBuilder.buildQuery(request);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> BeanIdList findIds(OrmQueryRequest<T> request) {
/*  69 */     CQueryFetchIds rcQuery = this.queryBuilder.buildFetchIdsQuery(request);
/*     */     
/*     */     try {
/*  72 */       String sql = rcQuery.getGeneratedSql();
/*  73 */       sql = sql.replace('\n', ' ');
/*     */       
/*  75 */       if (this.logControl.isDebugGeneratedSql()) {
/*  76 */         System.out.println(sql);
/*     */       }
/*  78 */       request.logSql(sql);
/*     */       
/*  80 */       BeanIdList list = rcQuery.findIds();
/*     */       
/*  82 */       if (request.isLogSummary()) {
/*  83 */         request.getTransaction().logInternal(rcQuery.getSummary());
/*     */       }
/*     */       
/*  86 */       if (!list.isFetchingInBackground() && request.getQuery().isFutureFetch()) {
/*     */         
/*  88 */         logger.fine("Future findIds completed!");
/*  89 */         request.getTransaction().end();
/*     */       } 
/*     */       
/*  92 */       return list;
/*     */     }
/*  94 */     catch (SQLException e) {
/*  95 */       throw CQuery.createPersistenceException(e, request.getTransaction(), rcQuery.getBindLog(), rcQuery.getGeneratedSql());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> int findRowCount(OrmQueryRequest<T> request) {
/* 104 */     CQueryRowCount rcQuery = this.queryBuilder.buildRowCountQuery(request);
/*     */     
/*     */     try {
/* 107 */       String sql = rcQuery.getGeneratedSql();
/* 108 */       sql = sql.replace('\n', ' ');
/*     */       
/* 110 */       if (this.logControl.isDebugGeneratedSql()) {
/* 111 */         System.out.println(sql);
/*     */       }
/* 113 */       request.logSql(sql);
/*     */       
/* 115 */       int rowCount = rcQuery.findRowCount();
/*     */       
/* 117 */       if (request.isLogSummary()) {
/* 118 */         request.getTransaction().logInternal(rcQuery.getSummary());
/*     */       }
/*     */       
/* 121 */       if (request.getQuery().isFutureFetch()) {
/* 122 */         logger.fine("Future findRowCount completed!");
/* 123 */         request.getTransaction().end();
/*     */       } 
/*     */       
/* 126 */       return rowCount;
/*     */     }
/* 128 */     catch (SQLException e) {
/* 129 */       throw CQuery.createPersistenceException(e, request.getTransaction(), rcQuery.getBindLog(), rcQuery.getGeneratedSql());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> QueryIterator<T> findIterate(OrmQueryRequest<T> request) {
/* 139 */     CQuery<T> cquery = this.queryBuilder.buildQuery(request);
/* 140 */     request.setCancelableQuery(cquery);
/*     */ 
/*     */     
/*     */     try {
/* 144 */       if (this.logControl.isDebugGeneratedSql()) {
/* 145 */         logSqlToConsole(cquery);
/*     */       }
/*     */       
/* 148 */       if (request.isLogSql()) {
/* 149 */         logSql(cquery);
/*     */       }
/*     */       
/* 152 */       if (!cquery.prepareBindExecuteQuery()) {
/*     */         
/* 154 */         logger.finest("Future fetch already cancelled");
/* 155 */         return null;
/*     */       } 
/*     */       
/* 158 */       int iterateBufferSize = request.getSecondaryQueriesMinBatchSize(100);
/*     */       
/* 160 */       QueryIterator<T> readIterate = cquery.readIterate(iterateBufferSize, request);
/*     */       
/* 162 */       if (request.isLogSummary()) {
/* 163 */         logFindManySummary(cquery);
/*     */       }
/*     */       
/* 166 */       return readIterate;
/*     */     }
/* 168 */     catch (SQLException e) {
/* 169 */       throw cquery.createPersistenceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> BeanCollection<T> findMany(OrmQueryRequest<T> request) {
/* 179 */     boolean useBackgroundToContinueFetch = false;
/*     */     
/* 181 */     CQuery<T> cquery = this.queryBuilder.buildQuery(request);
/* 182 */     request.setCancelableQuery(cquery);
/*     */ 
/*     */     
/*     */     try {
/* 186 */       if (this.logControl.isDebugGeneratedSql()) {
/* 187 */         logSqlToConsole(cquery);
/*     */       }
/* 189 */       if (request.isLogSql()) {
/* 190 */         logSql(cquery);
/*     */       }
/*     */       
/* 193 */       if (!cquery.prepareBindExecuteQuery()) {
/*     */         
/* 195 */         logger.finest("Future fetch already cancelled");
/* 196 */         return null;
/*     */       } 
/*     */       
/* 199 */       BeanCollection<T> beanCollection = cquery.readCollection();
/*     */       
/* 201 */       BeanCollectionTouched collectionTouched = request.getQuery().getBeanCollectionTouched();
/* 202 */       if (collectionTouched != null)
/*     */       {
/*     */         
/* 205 */         beanCollection.setBeanCollectionTouched(collectionTouched);
/*     */       }
/*     */       
/* 208 */       if (cquery.useBackgroundToContinueFetch()) {
/*     */ 
/*     */         
/* 211 */         request.setBackgroundFetching();
/* 212 */         useBackgroundToContinueFetch = true;
/* 213 */         BackgroundFetch fetch = new BackgroundFetch(cquery);
/*     */         
/* 215 */         FutureTask<Integer> future = new FutureTask<Integer>(fetch);
/* 216 */         beanCollection.setBackgroundFetch(future);
/* 217 */         this.backgroundExecutor.execute(future);
/*     */       } 
/*     */       
/* 220 */       if (request.isLogSummary()) {
/* 221 */         logFindManySummary(cquery);
/*     */       }
/*     */       
/* 224 */       request.executeSecondaryQueries(100);
/*     */       
/* 226 */       return beanCollection;
/*     */     }
/* 228 */     catch (SQLException e) {
/* 229 */       throw cquery.createPersistenceException(e);
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 234 */       if (!useBackgroundToContinueFetch) {
/*     */ 
/*     */         
/* 237 */         if (cquery != null) {
/* 238 */           cquery.close();
/*     */         }
/* 240 */         if (request.getQuery().isFutureFetch()) {
/*     */ 
/*     */           
/* 243 */           logger.fine("Future fetch completed!");
/* 244 */           request.getTransaction().end();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T find(OrmQueryRequest<T> request) {
/* 255 */     T bean = null;
/*     */     
/* 257 */     CQuery<T> cquery = this.queryBuilder.buildQuery(request);
/*     */     
/*     */     try {
/* 260 */       if (this.logControl.isDebugGeneratedSql()) {
/* 261 */         logSqlToConsole(cquery);
/*     */       }
/* 263 */       if (request.isLogSql()) {
/* 264 */         logSql(cquery);
/*     */       }
/*     */       
/* 267 */       cquery.prepareBindExecuteQuery();
/*     */       
/* 269 */       if (cquery.readBean()) {
/* 270 */         bean = cquery.getLoadedBean();
/*     */       }
/*     */       
/* 273 */       if (request.isLogSummary()) {
/* 274 */         logFindBeanSummary(cquery);
/*     */       }
/*     */       
/* 277 */       request.executeSecondaryQueries(100);
/*     */       
/* 279 */       return bean;
/*     */     }
/* 281 */     catch (SQLException e) {
/* 282 */       throw cquery.createPersistenceException(e);
/*     */     } finally {
/*     */       
/* 285 */       cquery.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logSqlToConsole(CQuery<?> cquery) {
/* 294 */     SpiQuery<?> query = cquery.getQueryRequest().getQuery();
/* 295 */     String loadMode = query.getLoadMode();
/* 296 */     String loadDesc = query.getLoadDescription();
/*     */     
/* 298 */     String sql = cquery.getGeneratedSql();
/* 299 */     String summary = cquery.getSummary();
/*     */     
/* 301 */     StringBuilder sb = new StringBuilder(1000);
/* 302 */     sb.append("<sql ");
/* 303 */     if (query.isAutofetchTuned()) {
/* 304 */       sb.append("tuned='true' ");
/*     */     }
/* 306 */     if (loadMode != null) {
/* 307 */       sb.append("mode='").append(loadMode).append("' ");
/*     */     }
/* 309 */     sb.append("summary='").append(summary);
/* 310 */     if (loadDesc != null) {
/* 311 */       sb.append("' load='").append(loadDesc);
/*     */     }
/* 313 */     sb.append("' >");
/* 314 */     sb.append('\n');
/* 315 */     sb.append(sql);
/* 316 */     sb.append('\n').append("</sql>");
/*     */     
/* 318 */     System.out.println(sb.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logSql(CQuery<?> query) {
/* 326 */     String sql = query.getGeneratedSql();
/* 327 */     sql = sql.replace('\n', ' ');
/* 328 */     query.getTransaction().logInternal(sql);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logFindBeanSummary(CQuery<?> q) {
/*     */     String originKey;
/* 336 */     SpiQuery<?> query = q.getQueryRequest().getQuery();
/* 337 */     String loadMode = query.getLoadMode();
/* 338 */     String loadDesc = query.getLoadDescription();
/* 339 */     String lazyLoadProp = query.getLazyLoadProperty();
/* 340 */     ObjectGraphNode node = query.getParentNode();
/*     */     
/* 342 */     if (node == null || node.getOriginQueryPoint() == null) {
/* 343 */       originKey = null;
/*     */     } else {
/* 345 */       originKey = node.getOriginQueryPoint().getKey();
/*     */     } 
/*     */     
/* 348 */     StringBuilder msg = new StringBuilder(200);
/* 349 */     msg.append("FindBean ");
/* 350 */     if (loadMode != null) {
/* 351 */       msg.append("mode[").append(loadMode).append("] ");
/*     */     }
/* 353 */     msg.append("type[").append(q.getBeanName()).append("] ");
/* 354 */     if (query.isAutofetchTuned()) {
/* 355 */       msg.append("tuned[true] ");
/*     */     }
/* 357 */     if (originKey != null) {
/* 358 */       msg.append("origin[").append(originKey).append("] ");
/*     */     }
/* 360 */     if (lazyLoadProp != null) {
/* 361 */       msg.append("lazyLoadProp[").append(lazyLoadProp).append("] ");
/*     */     }
/* 363 */     if (loadDesc != null) {
/* 364 */       msg.append("load[").append(loadDesc).append("] ");
/*     */     }
/* 366 */     msg.append("exeMicros[").append(q.getQueryExecutionTimeMicros());
/* 367 */     msg.append("] rows[").append(q.getLoadedRowDetail());
/* 368 */     msg.append("] bind[").append(q.getBindLog()).append("]");
/*     */     
/* 370 */     q.getTransaction().logInternal(msg.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logFindManySummary(CQuery<?> q) {
/*     */     String originKey;
/* 378 */     SpiQuery<?> query = q.getQueryRequest().getQuery();
/* 379 */     String loadMode = query.getLoadMode();
/* 380 */     String loadDesc = query.getLoadDescription();
/* 381 */     String lazyLoadProp = query.getLazyLoadProperty();
/* 382 */     ObjectGraphNode node = query.getParentNode();
/*     */ 
/*     */     
/* 385 */     if (node == null || node.getOriginQueryPoint() == null) {
/* 386 */       originKey = null;
/*     */     } else {
/* 388 */       originKey = node.getOriginQueryPoint().getKey();
/*     */     } 
/*     */     
/* 391 */     StringBuilder msg = new StringBuilder(200);
/* 392 */     msg.append("FindMany ");
/* 393 */     if (loadMode != null) {
/* 394 */       msg.append("mode[").append(loadMode).append("] ");
/*     */     }
/* 396 */     msg.append("type[").append(q.getBeanName()).append("] ");
/* 397 */     if (query.isAutofetchTuned()) {
/* 398 */       msg.append("tuned[true] ");
/*     */     }
/* 400 */     if (originKey != null) {
/* 401 */       msg.append("origin[").append(originKey).append("] ");
/*     */     }
/* 403 */     if (lazyLoadProp != null) {
/* 404 */       msg.append("lazyLoadProp[").append(lazyLoadProp).append("] ");
/*     */     }
/* 406 */     if (loadDesc != null) {
/* 407 */       msg.append("load[").append(loadDesc).append("] ");
/*     */     }
/* 409 */     msg.append("exeMicros[").append(q.getQueryExecutionTimeMicros());
/* 410 */     msg.append("] rows[").append(q.getLoadedRowDetail());
/* 411 */     msg.append("] name[").append(q.getName());
/* 412 */     msg.append("] predicates[").append(q.getLogWhereSql());
/* 413 */     msg.append("] bind[").append(q.getBindLog()).append("]");
/*     */     
/* 415 */     q.getTransaction().logInternal(msg.toString());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\CQueryEngine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */