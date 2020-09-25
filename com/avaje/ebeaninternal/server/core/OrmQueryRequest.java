/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.QueryIterator;
/*     */ import com.avaje.ebean.QueryResultVisitor;
/*     */ import com.avaje.ebean.RawSql;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebean.event.BeanFinder;
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.BeanIdList;
/*     */ import com.avaje.ebeaninternal.api.LoadContext;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployParser;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployPropertyParserMap;
/*     */ import com.avaje.ebeaninternal.server.loadcontext.DLoadContext;
/*     */ import com.avaje.ebeaninternal.server.query.CQueryPlan;
/*     */ import com.avaje.ebeaninternal.server.query.CancelableQuery;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OrmQueryRequest<T>
/*     */   extends BeanRequest
/*     */   implements BeanQueryRequest<T>, SpiOrmQueryRequest<T>
/*     */ {
/*     */   private final BeanDescriptor<T> beanDescriptor;
/*     */   private final OrmQueryEngine queryEngine;
/*     */   private final SpiQuery<T> query;
/*     */   private final boolean vanillaMode;
/*     */   private final BeanFinder<T> finder;
/*     */   private final LoadContext graphContext;
/*     */   private final Boolean readOnly;
/*     */   private final RawSql rawSql;
/*     */   private PersistenceContext persistenceContext;
/*     */   private Integer cacheKey;
/*     */   private int queryPlanHash;
/*     */   private boolean backgroundFetching;
/*     */   
/*     */   public OrmQueryRequest(SpiEbeanServer server, OrmQueryEngine queryEngine, SpiQuery<T> query, BeanDescriptor<T> desc, SpiTransaction t) {
/*  89 */     super(server, t);
/*     */     
/*  91 */     this.beanDescriptor = desc;
/*  92 */     this.rawSql = query.getRawSql();
/*  93 */     this.finder = this.beanDescriptor.getBeanFinder();
/*  94 */     this.queryEngine = queryEngine;
/*  95 */     this.query = query;
/*  96 */     this.vanillaMode = query.isVanillaMode(server.isVanillaMode());
/*  97 */     this.readOnly = query.isReadOnly();
/*     */     
/*  99 */     this.graphContext = (LoadContext)new DLoadContext(this.ebeanServer, this.beanDescriptor, this.readOnly, query);
/* 100 */     this.graphContext.registerSecondaryQueries(query);
/*     */   }
/*     */   
/*     */   public void setTotalHits(int totalHits) {
/* 104 */     this.query.setTotalHits(totalHits);
/*     */   }
/*     */   
/*     */   public void executeSecondaryQueries(int defaultQueryBatch) {
/* 108 */     this.graphContext.executeSecondaryQueries(this, defaultQueryBatch);
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
/*     */   public int getSecondaryQueriesMinBatchSize(int defaultQueryBatch) {
/* 120 */     return this.graphContext.getSecondaryQueriesMinBatchSize(this, defaultQueryBatch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isReadOnly() {
/* 127 */     return this.readOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanDescriptor<T> getBeanDescriptor() {
/* 134 */     return this.beanDescriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoadContext getGraphContext() {
/* 141 */     return this.graphContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void calculateQueryPlanHash() {
/* 148 */     this.queryPlanHash = this.query.queryPlanHash(this);
/*     */   }
/*     */   
/*     */   public boolean isRawSql() {
/* 152 */     return (this.rawSql != null);
/*     */   }
/*     */   
/*     */   public DeployParser createDeployParser() {
/* 156 */     if (this.rawSql != null) {
/* 157 */       return (DeployParser)new DeployPropertyParserMap(this.rawSql.getColumnMapping().getMapping());
/*     */     }
/* 159 */     return (DeployParser)this.beanDescriptor.createDeployPropertyParser();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSqlSelect() {
/* 168 */     return (this.query.isSqlSelect() && this.query.getRawSql() == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistenceContext getPersistenceContext() {
/* 175 */     return this.persistenceContext;
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
/*     */   public void initTransIfRequired() {
/* 190 */     if (this.query.createOwnTransaction()) {
/*     */       
/* 192 */       this.transaction = this.ebeanServer.createQueryTransaction();
/* 193 */       this.createdTransaction = true;
/*     */     }
/* 195 */     else if (this.transaction == null) {
/*     */       
/* 197 */       this.transaction = this.ebeanServer.getCurrentServerTransaction();
/* 198 */       if (this.transaction == null) {
/*     */         
/* 200 */         this.transaction = this.ebeanServer.createQueryTransaction();
/* 201 */         this.createdTransaction = true;
/*     */       } 
/*     */     } 
/* 204 */     this.persistenceContext = getPersistenceContext(this.query, this.transaction);
/* 205 */     this.graphContext.setPersistenceContext(this.persistenceContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PersistenceContext getPersistenceContext(SpiQuery<?> query, SpiTransaction t) {
/* 214 */     PersistenceContext ctx = query.getPersistenceContext();
/* 215 */     if (ctx == null) {
/* 216 */       ctx = t.getPersistenceContext();
/*     */     }
/* 218 */     return ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endTransIfRequired() {
/* 229 */     if (this.createdTransaction && !this.backgroundFetching)
/*     */     {
/* 231 */       this.transaction.rollback();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackgroundFetching() {
/* 239 */     this.backgroundFetching = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFindById() {
/* 246 */     return (this.query.getType() == SpiQuery.Type.BEAN);
/*     */   }
/*     */   
/*     */   public boolean isVanillaMode() {
/* 250 */     return this.vanillaMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object findId() {
/* 257 */     return this.queryEngine.findId(this);
/*     */   }
/*     */   
/*     */   public int findRowCount() {
/* 261 */     return this.queryEngine.findRowCount(this);
/*     */   }
/*     */   
/*     */   public List<Object> findIds() {
/* 265 */     BeanIdList idList = this.queryEngine.findIds(this);
/* 266 */     return idList.getIdList();
/*     */   }
/*     */   
/*     */   public void findVisit(QueryResultVisitor<T> visitor) {
/* 270 */     QueryIterator<T> it = this.queryEngine.findIterate(this); try { do {
/*     */       
/* 272 */       } while (it.hasNext() && 
/* 273 */         visitor.accept(it.next()));
/*     */        }
/*     */     
/*     */     finally
/*     */     
/* 278 */     { it.close(); }
/*     */   
/*     */   }
/*     */   
/*     */   public QueryIterator<T> findIterate() {
/* 283 */     return this.queryEngine.findIterate(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<T> findList() {
/* 291 */     BeanCollection<T> bc = this.queryEngine.findMany(this);
/* 292 */     return this.vanillaMode ? (List<T>)bc.getActualCollection() : (List<T>)bc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<?> findSet() {
/* 300 */     BeanCollection<T> bc = this.queryEngine.findMany(this);
/* 301 */     return this.vanillaMode ? (Set)bc.getActualCollection() : (Set)bc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<?, ?> findMap() {
/* 308 */     String mapKey = this.query.getMapKey();
/* 309 */     if (mapKey == null) {
/* 310 */       BeanProperty[] ids = this.beanDescriptor.propertiesId();
/* 311 */       if (ids.length == 1) {
/* 312 */         this.query.setMapKey(ids[0].getName());
/*     */       } else {
/* 314 */         String msg = "No mapKey specified for query";
/* 315 */         throw new PersistenceException(msg);
/*     */       } 
/*     */     } 
/* 318 */     BeanCollection<T> bc = this.queryEngine.findMany(this);
/* 319 */     return this.vanillaMode ? (Map<?, ?>)bc.getActualCollection() : (Map)bc;
/*     */   }
/*     */   
/*     */   public SpiQuery.Type getQueryType() {
/* 323 */     return this.query.getType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanFinder<T> getBeanFinder() {
/* 330 */     return this.finder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiQuery<T> getQuery() {
/* 337 */     return this.query;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyAssocMany<?> getManyProperty() {
/* 345 */     return this.beanDescriptor.getManyProperty(this.query);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CQueryPlan getQueryPlan() {
/* 353 */     return this.beanDescriptor.getQueryPlan(Integer.valueOf(this.queryPlanHash));
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
/*     */   public int getQueryPlanHash() {
/* 365 */     return this.queryPlanHash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putQueryPlan(CQueryPlan queryPlan) {
/* 372 */     this.beanDescriptor.putQueryPlan(Integer.valueOf(this.queryPlanHash), queryPlan);
/*     */   }
/*     */   
/*     */   public boolean isUseBeanCache() {
/* 376 */     return this.beanDescriptor.calculateUseCache(this.query.isUseBeanCache());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCollection<T> getFromQueryCache() {
/* 384 */     if (!this.query.isUseQueryCache()) {
/* 385 */       return null;
/*     */     }
/*     */     
/* 388 */     if (this.query.getType() == null) {
/*     */       
/* 390 */       this.cacheKey = Integer.valueOf(this.query.queryHash());
/*     */     }
/*     */     else {
/*     */       
/* 394 */       this.cacheKey = Integer.valueOf(31 * this.query.queryHash() + this.query.getType().hashCode());
/*     */     } 
/*     */ 
/*     */     
/* 398 */     return null;
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
/*     */   public void putToQueryCache(BeanCollection<T> queryResult) {
/* 410 */     this.beanDescriptor.queryCachePut(this.cacheKey, queryResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCancelableQuery(CancelableQuery cancelableQuery) {
/* 417 */     this.query.setCancelableQuery(cancelableQuery);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logSql(String sql) {
/* 424 */     if (this.transaction.isLogSql())
/* 425 */       this.transaction.logInternal(sql); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\OrmQueryRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */