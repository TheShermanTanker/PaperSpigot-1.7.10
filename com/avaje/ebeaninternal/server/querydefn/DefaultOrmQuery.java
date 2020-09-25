/*      */ package com.avaje.ebeaninternal.server.querydefn;
/*      */ 
/*      */ import com.avaje.ebean.EbeanServer;
/*      */ import com.avaje.ebean.Expression;
/*      */ import com.avaje.ebean.ExpressionFactory;
/*      */ import com.avaje.ebean.ExpressionList;
/*      */ import com.avaje.ebean.FetchConfig;
/*      */ import com.avaje.ebean.FutureIds;
/*      */ import com.avaje.ebean.FutureList;
/*      */ import com.avaje.ebean.FutureRowCount;
/*      */ import com.avaje.ebean.OrderBy;
/*      */ import com.avaje.ebean.PagingList;
/*      */ import com.avaje.ebean.Query;
/*      */ import com.avaje.ebean.QueryIterator;
/*      */ import com.avaje.ebean.QueryListener;
/*      */ import com.avaje.ebean.QueryResultVisitor;
/*      */ import com.avaje.ebean.RawSql;
/*      */ import com.avaje.ebean.bean.BeanCollectionTouched;
/*      */ import com.avaje.ebean.bean.CallStack;
/*      */ import com.avaje.ebean.bean.EntityBean;
/*      */ import com.avaje.ebean.bean.ObjectGraphNode;
/*      */ import com.avaje.ebean.bean.ObjectGraphOrigin;
/*      */ import com.avaje.ebean.bean.PersistenceContext;
/*      */ import com.avaje.ebean.event.BeanQueryRequest;
/*      */ import com.avaje.ebean.meta.MetaAutoFetchStatistic;
/*      */ import com.avaje.ebeaninternal.api.BindParams;
/*      */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*      */ import com.avaje.ebeaninternal.api.SpiExpression;
/*      */ import com.avaje.ebeaninternal.api.SpiExpressionList;
/*      */ import com.avaje.ebeaninternal.api.SpiQuery;
/*      */ import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*      */ import com.avaje.ebeaninternal.server.deploy.DRawSqlSelect;
/*      */ import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
/*      */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
/*      */ import com.avaje.ebeaninternal.server.expression.SimpleExpression;
/*      */ import com.avaje.ebeaninternal.server.query.CancelableQuery;
/*      */ import com.avaje.ebeaninternal.util.DefaultExpressionList;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.persistence.PersistenceException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DefaultOrmQuery<T>
/*      */   implements SpiQuery<T>
/*      */ {
/*      */   private static final long serialVersionUID = 6838006264714672460L;
/*      */   private final Class<T> beanType;
/*      */   private final transient EbeanServer server;
/*      */   private transient BeanCollectionTouched beanCollectionTouched;
/*      */   private final transient ExpressionFactory expressionFactory;
/*      */   private transient ArrayList<EntityBean> contextAdditions;
/*      */   private transient QueryListener<T> queryListener;
/*      */   private transient TableJoin includeTableJoin;
/*      */   private transient AutoFetchManager autoFetchManager;
/*      */   private transient BeanDescriptor<?> beanDescriptor;
/*      */   private boolean cancelled;
/*      */   private transient CancelableQuery cancelableQuery;
/*      */   private String name;
/*      */   private Query.UseIndex useIndex;
/*      */   private SpiQuery.Type type;
/*   94 */   private SpiQuery.Mode mode = SpiQuery.Mode.NORMAL;
/*      */ 
/*      */   
/*      */   private OrmQueryDetail detail;
/*      */ 
/*      */   
/*      */   private int maxRows;
/*      */ 
/*      */   
/*      */   private int firstRow;
/*      */ 
/*      */   
/*      */   private int totalHits;
/*      */ 
/*      */   
/*      */   private String rawWhereClause;
/*      */ 
/*      */   
/*      */   private OrderBy<T> orderBy;
/*      */ 
/*      */   
/*      */   private String loadMode;
/*      */ 
/*      */   
/*      */   private String loadDescription;
/*      */ 
/*      */   
/*      */   private String generatedSql;
/*      */ 
/*      */   
/*      */   private String query;
/*      */ 
/*      */   
/*      */   private String additionalWhere;
/*      */ 
/*      */   
/*      */   private String additionalHaving;
/*      */ 
/*      */   
/*      */   private String lazyLoadProperty;
/*      */ 
/*      */   
/*      */   private String lazyLoadManyPath;
/*      */ 
/*      */   
/*      */   private Boolean vanillaMode;
/*      */ 
/*      */   
/*      */   private boolean distinct;
/*      */ 
/*      */   
/*      */   private boolean futureFetch;
/*      */ 
/*      */   
/*      */   private List<Object> partialIds;
/*      */ 
/*      */   
/*      */   private int backgroundFetchAfter;
/*      */ 
/*      */   
/*  154 */   private int timeout = -1;
/*      */ 
/*      */ 
/*      */   
/*      */   private String mapKey;
/*      */ 
/*      */ 
/*      */   
/*      */   private Object id;
/*      */ 
/*      */ 
/*      */   
/*      */   private BindParams bindParams;
/*      */ 
/*      */   
/*      */   private DefaultExpressionList<T> whereExpressions;
/*      */ 
/*      */   
/*      */   private DefaultExpressionList<T> havingExpressions;
/*      */ 
/*      */   
/*      */   private int bufferFetchSizeHint;
/*      */ 
/*      */   
/*      */   private boolean usageProfiling = true;
/*      */ 
/*      */   
/*      */   private boolean loadBeanCache;
/*      */ 
/*      */   
/*      */   private Boolean useBeanCache;
/*      */ 
/*      */   
/*      */   private Boolean useQueryCache;
/*      */ 
/*      */   
/*      */   private Boolean readOnly;
/*      */ 
/*      */   
/*      */   private boolean sqlSelect;
/*      */ 
/*      */   
/*      */   private Boolean autoFetch;
/*      */ 
/*      */   
/*      */   private Boolean forUpdate;
/*      */ 
/*      */   
/*      */   private boolean autoFetchTuned;
/*      */ 
/*      */   
/*      */   private ObjectGraphNode parentNode;
/*      */ 
/*      */   
/*      */   private int queryPlanHash;
/*      */ 
/*      */   
/*      */   private transient PersistenceContext persistenceContext;
/*      */ 
/*      */   
/*      */   private ManyWhereJoins manyWhereJoins;
/*      */ 
/*      */   
/*      */   private RawSql rawSql;
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery(Class<T> beanType, EbeanServer server, ExpressionFactory expressionFactory, String query) {
/*  222 */     this.beanType = beanType;
/*  223 */     this.server = server;
/*  224 */     this.expressionFactory = expressionFactory;
/*  225 */     this.detail = new OrmQueryDetail();
/*  226 */     this.name = "";
/*  227 */     if (query != null) {
/*  228 */       setQuery(query);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery(Class<T> beanType, EbeanServer server, ExpressionFactory expressionFactory, DeployNamedQuery namedQuery) throws PersistenceException {
/*  238 */     this.beanType = beanType;
/*  239 */     this.server = server;
/*  240 */     this.expressionFactory = expressionFactory;
/*  241 */     this.detail = new OrmQueryDetail();
/*  242 */     if (namedQuery == null) {
/*  243 */       this.name = "";
/*      */     } else {
/*  245 */       this.name = namedQuery.getName();
/*  246 */       this.sqlSelect = namedQuery.isSqlSelect();
/*  247 */       if (this.sqlSelect) {
/*      */         
/*  249 */         DRawSqlSelect sqlSelect = namedQuery.getSqlSelect();
/*  250 */         this.additionalWhere = sqlSelect.getWhereClause();
/*  251 */         this.additionalHaving = sqlSelect.getHavingClause();
/*  252 */       } else if (namedQuery.isRawSql()) {
/*  253 */         this.rawSql = namedQuery.getRawSql();
/*      */       }
/*      */       else {
/*      */         
/*  257 */         setQuery(namedQuery.getQuery());
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getTotalHits() {
/*  263 */     return this.totalHits;
/*      */   }
/*      */   
/*      */   public void setTotalHits(int totalHits) {
/*  267 */     this.totalHits = totalHits;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBeanDescriptor(BeanDescriptor<?> beanDescriptor) {
/*  274 */     this.beanDescriptor = beanDescriptor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean selectAllForLazyLoadProperty() {
/*  282 */     if (this.lazyLoadProperty != null && 
/*  283 */       !this.detail.containsProperty(this.lazyLoadProperty)) {
/*  284 */       this.detail.select("*");
/*  285 */       return true;
/*      */     } 
/*      */     
/*  288 */     return false;
/*      */   }
/*      */   
/*      */   public RawSql getRawSql() {
/*  292 */     return this.rawSql;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setRawSql(RawSql rawSql) {
/*  296 */     this.rawSql = rawSql;
/*  297 */     return this;
/*      */   }
/*      */   
/*      */   public String getLazyLoadProperty() {
/*  301 */     return this.lazyLoadProperty;
/*      */   }
/*      */   
/*      */   public void setLazyLoadProperty(String lazyLoadProperty) {
/*  305 */     this.lazyLoadProperty = lazyLoadProperty;
/*      */   }
/*      */   
/*      */   public String getLazyLoadManyPath() {
/*  309 */     return this.lazyLoadManyPath;
/*      */   }
/*      */   
/*      */   public ExpressionFactory getExpressionFactory() {
/*  313 */     return this.expressionFactory;
/*      */   }
/*      */   
/*      */   public MetaAutoFetchStatistic getMetaAutoFetchStatistic() {
/*  317 */     if (this.parentNode != null && this.server != null) {
/*  318 */       ObjectGraphOrigin origin = this.parentNode.getOriginQueryPoint();
/*  319 */       return (MetaAutoFetchStatistic)this.server.find(MetaAutoFetchStatistic.class, origin.getKey());
/*      */     } 
/*  321 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean initManyWhereJoins() {
/*  328 */     this.manyWhereJoins = new ManyWhereJoins();
/*  329 */     if (this.whereExpressions != null) {
/*  330 */       this.whereExpressions.containsMany(this.beanDescriptor, this.manyWhereJoins);
/*      */     }
/*  332 */     return !this.manyWhereJoins.isEmpty();
/*      */   }
/*      */   
/*      */   public ManyWhereJoins getManyWhereJoins() {
/*  336 */     return this.manyWhereJoins;
/*      */   }
/*      */   
/*      */   public List<OrmQueryProperties> removeQueryJoins() {
/*  340 */     List<OrmQueryProperties> queryJoins = this.detail.removeSecondaryQueries();
/*  341 */     if (queryJoins != null && 
/*  342 */       this.orderBy != null)
/*      */     {
/*      */       
/*  345 */       for (int i = 0; i < queryJoins.size(); i++) {
/*  346 */         OrmQueryProperties joinPath = queryJoins.get(i);
/*      */ 
/*      */ 
/*      */         
/*  350 */         List<OrderBy.Property> properties = this.orderBy.getProperties();
/*  351 */         Iterator<OrderBy.Property> it = properties.iterator();
/*  352 */         while (it.hasNext()) {
/*  353 */           OrderBy.Property property = it.next();
/*  354 */           if (property.getProperty().startsWith(joinPath.getPath())) {
/*      */ 
/*      */             
/*  357 */             it.remove();
/*  358 */             joinPath.addSecJoinOrderProperty(property);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*  364 */     return queryJoins;
/*      */   }
/*      */   
/*      */   public List<OrmQueryProperties> removeLazyJoins() {
/*  368 */     return this.detail.removeSecondaryLazyQueries();
/*      */   }
/*      */   
/*      */   public void setLazyLoadManyPath(String lazyLoadManyPath) {
/*  372 */     this.lazyLoadManyPath = lazyLoadManyPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void convertManyFetchJoinsToQueryJoins(boolean allowOne, int queryBatch) {
/*  379 */     this.detail.convertManyFetchJoinsToQueryJoins(this.beanDescriptor, this.lazyLoadManyPath, allowOne, queryBatch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectId() {
/*  387 */     this.detail.clear();
/*      */     
/*  389 */     select(this.beanDescriptor.getIdBinder().getIdProperty());
/*      */   }
/*      */   
/*      */   public void convertWhereNaturalKeyToId(Object idValue) {
/*  393 */     this.whereExpressions = new DefaultExpressionList((Query)this, null);
/*  394 */     setId(idValue);
/*      */   }
/*      */   
/*      */   public NaturalKeyBindParam getNaturalKeyBindParam() {
/*  398 */     NaturalKeyBindParam namedBind = null;
/*  399 */     if (this.bindParams != null) {
/*  400 */       namedBind = this.bindParams.getNaturalKeyBindParam();
/*  401 */       if (namedBind == null) {
/*  402 */         return null;
/*      */       }
/*      */     } 
/*      */     
/*  406 */     if (this.whereExpressions != null) {
/*  407 */       List<SpiExpression> exprList = this.whereExpressions.internalList();
/*  408 */       if (exprList.size() > 1)
/*  409 */         return null; 
/*  410 */       if (exprList.size() == 0) {
/*  411 */         return namedBind;
/*      */       }
/*  413 */       if (namedBind != null) {
/*  414 */         return null;
/*      */       }
/*  416 */       SpiExpression se = exprList.get(0);
/*  417 */       if (se instanceof SimpleExpression) {
/*  418 */         SimpleExpression e = (SimpleExpression)se;
/*  419 */         if (e.isOpEquals()) {
/*  420 */           return new NaturalKeyBindParam(e.getPropertyName(), e.getValue());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  425 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> copy() {
/*  437 */     DefaultOrmQuery<T> copy = new DefaultOrmQuery(this.beanType, this.server, this.expressionFactory, (String)null);
/*  438 */     copy.name = this.name;
/*  439 */     copy.includeTableJoin = this.includeTableJoin;
/*  440 */     copy.autoFetchManager = this.autoFetchManager;
/*      */     
/*  442 */     copy.query = this.query;
/*  443 */     copy.additionalWhere = this.additionalWhere;
/*  444 */     copy.additionalHaving = this.additionalHaving;
/*  445 */     copy.distinct = this.distinct;
/*  446 */     copy.backgroundFetchAfter = this.backgroundFetchAfter;
/*  447 */     copy.timeout = this.timeout;
/*  448 */     copy.mapKey = this.mapKey;
/*  449 */     copy.id = this.id;
/*  450 */     copy.vanillaMode = this.vanillaMode;
/*  451 */     copy.loadBeanCache = this.loadBeanCache;
/*  452 */     copy.useBeanCache = this.useBeanCache;
/*  453 */     copy.useQueryCache = this.useQueryCache;
/*  454 */     copy.readOnly = this.readOnly;
/*  455 */     copy.sqlSelect = this.sqlSelect;
/*  456 */     if (this.detail != null) {
/*  457 */       copy.detail = this.detail.copy();
/*      */     }
/*      */     
/*  460 */     copy.firstRow = this.firstRow;
/*  461 */     copy.maxRows = this.maxRows;
/*  462 */     copy.rawWhereClause = this.rawWhereClause;
/*  463 */     if (this.orderBy != null) {
/*  464 */       copy.orderBy = this.orderBy.copy();
/*      */     }
/*  466 */     if (this.bindParams != null) {
/*  467 */       copy.bindParams = this.bindParams.copy();
/*      */     }
/*  469 */     if (this.whereExpressions != null) {
/*  470 */       copy.whereExpressions = this.whereExpressions.copy((Query)copy);
/*      */     }
/*  472 */     if (this.havingExpressions != null) {
/*  473 */       copy.havingExpressions = this.havingExpressions.copy((Query)copy);
/*      */     }
/*  475 */     copy.usageProfiling = this.usageProfiling;
/*  476 */     copy.autoFetch = this.autoFetch;
/*  477 */     copy.parentNode = this.parentNode;
/*  478 */     copy.forUpdate = this.forUpdate;
/*  479 */     copy.rawSql = this.rawSql;
/*  480 */     copy.rawWhereClause = this.rawWhereClause;
/*  481 */     return copy;
/*      */   }
/*      */   
/*      */   public SpiQuery.Type getType() {
/*  485 */     return this.type;
/*      */   }
/*      */   
/*      */   public void setType(SpiQuery.Type type) {
/*  489 */     this.type = type;
/*      */   }
/*      */   
/*      */   public Query.UseIndex getUseIndex() {
/*  493 */     return this.useIndex;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setUseIndex(Query.UseIndex useIndex) {
/*  497 */     this.useIndex = useIndex;
/*  498 */     return this;
/*      */   }
/*      */   
/*      */   public String getLoadDescription() {
/*  502 */     return this.loadDescription;
/*      */   }
/*      */   
/*      */   public String getLoadMode() {
/*  506 */     return this.loadMode;
/*      */   }
/*      */   
/*      */   public void setLoadDescription(String loadMode, String loadDescription) {
/*  510 */     this.loadMode = loadMode;
/*  511 */     this.loadDescription = loadDescription;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PersistenceContext getPersistenceContext() {
/*  522 */     return this.persistenceContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPersistenceContext(PersistenceContext persistenceContext) {
/*  533 */     this.persistenceContext = persistenceContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDetailEmpty() {
/*  540 */     return this.detail.isEmpty();
/*      */   }
/*      */   
/*      */   public boolean isAutofetchTuned() {
/*  544 */     return this.autoFetchTuned;
/*      */   }
/*      */   
/*      */   public void setAutoFetchTuned(boolean autoFetchTuned) {
/*  548 */     this.autoFetchTuned = autoFetchTuned;
/*      */   }
/*      */   
/*      */   public Boolean isAutofetch() {
/*  552 */     return this.sqlSelect ? Boolean.FALSE : this.autoFetch;
/*      */   }
/*      */   
/*      */   public Boolean isForUpdate() {
/*  556 */     return this.forUpdate;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setAutoFetch(boolean autoFetch) {
/*  560 */     return setAutofetch(autoFetch);
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setAutofetch(boolean autoFetch) {
/*  564 */     this.autoFetch = Boolean.valueOf(autoFetch);
/*  565 */     return this;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setForUpdate(boolean forUpdate) {
/*  569 */     this.forUpdate = Boolean.valueOf(forUpdate);
/*  570 */     return this;
/*      */   }
/*      */   
/*      */   public AutoFetchManager getAutoFetchManager() {
/*  574 */     return this.autoFetchManager;
/*      */   }
/*      */   
/*      */   public void setAutoFetchManager(AutoFetchManager autoFetchManager) {
/*  578 */     this.autoFetchManager = autoFetchManager;
/*      */   }
/*      */   
/*      */   public SpiQuery.Mode getMode() {
/*  582 */     return this.mode;
/*      */   }
/*      */   
/*      */   public void setMode(SpiQuery.Mode mode) {
/*  586 */     this.mode = mode;
/*      */   }
/*      */   
/*      */   public boolean isUsageProfiling() {
/*  590 */     return this.usageProfiling;
/*      */   }
/*      */   
/*      */   public void setUsageProfiling(boolean usageProfiling) {
/*  594 */     this.usageProfiling = usageProfiling;
/*      */   }
/*      */   
/*      */   public void setParentNode(ObjectGraphNode parentNode) {
/*  598 */     this.parentNode = parentNode;
/*      */   }
/*      */   
/*      */   public ObjectGraphNode getParentNode() {
/*  602 */     return this.parentNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectGraphNode setOrigin(CallStack callStack) {
/*  609 */     ObjectGraphOrigin o = new ObjectGraphOrigin(calculateOriginQueryHash(), callStack, this.beanType.getName());
/*  610 */     this.parentNode = new ObjectGraphNode(o, null);
/*  611 */     return this.parentNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int calculateOriginQueryHash() {
/*  626 */     int hc = this.beanType.getName().hashCode();
/*  627 */     hc = hc * 31 + ((this.type == null) ? 0 : this.type.ordinal());
/*  628 */     return hc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int calculateHash(BeanQueryRequest<?> request) {
/*  641 */     int hc = this.beanType.getName().hashCode();
/*      */     
/*  643 */     hc = hc * 31 + ((this.type == null) ? 0 : this.type.ordinal());
/*  644 */     hc = hc * 31 + ((this.useIndex == null) ? 0 : this.useIndex.hashCode());
/*      */     
/*  646 */     hc = hc * 31 + ((this.rawSql == null) ? 0 : this.rawSql.queryHash());
/*      */     
/*  648 */     hc = hc * 31 + (this.autoFetchTuned ? 31 : 0);
/*  649 */     hc = hc * 31 + (this.distinct ? 31 : 0);
/*  650 */     hc = hc * 31 + ((this.query == null) ? 0 : this.query.hashCode());
/*  651 */     hc = hc * 31 + this.detail.queryPlanHash(request);
/*      */     
/*  653 */     hc = hc * 31 + ((this.firstRow == 0) ? 0 : this.firstRow);
/*  654 */     hc = hc * 31 + ((this.maxRows == 0) ? 0 : this.maxRows);
/*  655 */     hc = hc * 31 + ((this.orderBy == null) ? 0 : this.orderBy.hash());
/*  656 */     hc = hc * 31 + ((this.rawWhereClause == null) ? 0 : this.rawWhereClause.hashCode());
/*      */     
/*  658 */     hc = hc * 31 + ((this.additionalWhere == null) ? 0 : this.additionalWhere.hashCode());
/*  659 */     hc = hc * 31 + ((this.additionalHaving == null) ? 0 : this.additionalHaving.hashCode());
/*  660 */     hc = hc * 31 + ((this.mapKey == null) ? 0 : this.mapKey.hashCode());
/*  661 */     hc = hc * 31 + ((this.id == null) ? 0 : 1);
/*      */     
/*  663 */     if (this.bindParams != null) {
/*  664 */       hc = hc * 31 + this.bindParams.getQueryPlanHash();
/*      */     }
/*      */     
/*  667 */     if (request == null) {
/*      */       
/*  669 */       hc = hc * 31 + ((this.whereExpressions == null) ? 0 : this.whereExpressions.queryAutoFetchHash());
/*  670 */       hc = hc * 31 + ((this.havingExpressions == null) ? 0 : this.havingExpressions.queryAutoFetchHash());
/*      */     }
/*      */     else {
/*      */       
/*  674 */       hc = hc * 31 + ((this.whereExpressions == null) ? 0 : this.whereExpressions.queryPlanHash(request));
/*  675 */       hc = hc * 31 + ((this.havingExpressions == null) ? 0 : this.havingExpressions.queryPlanHash(request));
/*      */     } 
/*      */     
/*  678 */     hc = hc * 31 + ((this.forUpdate == null) ? 0 : this.forUpdate.hashCode());
/*      */     
/*  680 */     return hc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int queryAutofetchHash() {
/*  689 */     return calculateHash(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int queryPlanHash(BeanQueryRequest<?> request) {
/*  704 */     this.queryPlanHash = calculateHash(request);
/*  705 */     return this.queryPlanHash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int queryBindHash() {
/*  715 */     int hc = (this.id == null) ? 0 : this.id.hashCode();
/*  716 */     hc = hc * 31 + ((this.whereExpressions == null) ? 0 : this.whereExpressions.queryBindHash());
/*  717 */     hc = hc * 31 + ((this.havingExpressions == null) ? 0 : this.havingExpressions.queryBindHash());
/*  718 */     hc = hc * 31 + ((this.bindParams == null) ? 0 : this.bindParams.queryBindHash());
/*  719 */     hc = hc * 31 + ((this.contextAdditions == null) ? 0 : this.contextAdditions.hashCode());
/*      */     
/*  721 */     return hc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int queryHash() {
/*  734 */     int hc = this.queryPlanHash;
/*  735 */     hc = hc * 31 + queryBindHash();
/*  736 */     return hc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  743 */     return this.name;
/*      */   }
/*      */   
/*      */   public boolean isSqlSelect() {
/*  747 */     return this.sqlSelect;
/*      */   }
/*      */   
/*      */   public boolean isRawSql() {
/*  751 */     return (this.rawSql != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAdditionalWhere() {
/*  758 */     return this.additionalWhere;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTimeout() {
/*  765 */     return this.timeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAdditionalHaving() {
/*  772 */     return this.additionalHaving;
/*      */   }
/*      */   
/*      */   public boolean hasMaxRowsOrFirstRow() {
/*  776 */     return (this.maxRows > 0 || this.firstRow > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isVanillaMode(boolean defaultVanillaMode) {
/*  781 */     if (this.vanillaMode != null) {
/*  782 */       return this.vanillaMode.booleanValue();
/*      */     }
/*  784 */     return defaultVanillaMode;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setVanillaMode(boolean vanillaMode) {
/*  788 */     this.vanillaMode = Boolean.valueOf(vanillaMode);
/*  789 */     return this;
/*      */   }
/*      */   
/*      */   public Boolean isReadOnly() {
/*  793 */     return this.readOnly;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setReadOnly(boolean readOnly) {
/*  797 */     this.readOnly = Boolean.valueOf(readOnly);
/*  798 */     return this;
/*      */   }
/*      */   
/*      */   public Boolean isUseBeanCache() {
/*  802 */     return this.useBeanCache;
/*      */   }
/*      */   
/*      */   public boolean isUseQueryCache() {
/*  806 */     return Boolean.TRUE.equals(this.useQueryCache);
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setUseCache(boolean useBeanCache) {
/*  810 */     this.useBeanCache = Boolean.valueOf(useBeanCache);
/*  811 */     return this;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setUseQueryCache(boolean useQueryCache) {
/*  815 */     this.useQueryCache = Boolean.valueOf(useQueryCache);
/*  816 */     return this;
/*      */   }
/*      */   
/*      */   public boolean isLoadBeanCache() {
/*  820 */     return this.loadBeanCache;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setLoadBeanCache(boolean loadBeanCache) {
/*  824 */     this.loadBeanCache = loadBeanCache;
/*  825 */     return this;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setTimeout(int secs) {
/*  829 */     this.timeout = secs;
/*  830 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setQuery(String queryString) throws PersistenceException {
/*  835 */     this.query = queryString;
/*      */     
/*  837 */     OrmQueryDetailParser parser = new OrmQueryDetailParser(queryString);
/*  838 */     parser.parse();
/*  839 */     parser.assign(this);
/*      */     
/*  841 */     return this;
/*      */   }
/*      */   
/*      */   protected void setOrmQueryDetail(OrmQueryDetail detail) {
/*  845 */     this.detail = detail;
/*      */   }
/*      */   protected void setRawWhereClause(String rawWhereClause) {
/*  848 */     this.rawWhereClause = rawWhereClause;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setProperties(String columns) {
/*  852 */     return select(columns);
/*      */   }
/*      */   
/*      */   public void setDefaultSelectClause() {
/*  856 */     this.detail.setDefaultSelectClause(this.beanDescriptor);
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> select(String columns) {
/*  860 */     this.detail.select(columns);
/*  861 */     return this;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> fetch(String property) {
/*  865 */     return fetch(property, (String)null, (FetchConfig)null);
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> fetch(String property, FetchConfig joinConfig) {
/*  869 */     return fetch(property, (String)null, joinConfig);
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> fetch(String property, String columns) {
/*  873 */     return fetch(property, columns, (FetchConfig)null);
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> fetch(String property, String columns, FetchConfig config) {
/*  877 */     this.detail.addFetch(property, columns, config);
/*  878 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Object> findIds() {
/*  885 */     return this.server.findIds((Query)this, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int findRowCount() {
/*  892 */     return this.server.findRowCount((Query)this, null);
/*      */   }
/*      */   
/*      */   public void findVisit(QueryResultVisitor<T> visitor) {
/*  896 */     this.server.findVisit((Query)this, visitor, null);
/*      */   }
/*      */   
/*      */   public QueryIterator<T> findIterate() {
/*  900 */     return this.server.findIterate((Query)this, null);
/*      */   }
/*      */   
/*      */   public List<T> findList() {
/*  904 */     return this.server.findList((Query)this, null);
/*      */   }
/*      */   
/*      */   public Set<T> findSet() {
/*  908 */     return this.server.findSet((Query)this, null);
/*      */   }
/*      */   
/*      */   public Map<?, T> findMap() {
/*  912 */     return this.server.findMap((Query)this, null);
/*      */   }
/*      */ 
/*      */   
/*      */   public <K> Map<K, T> findMap(String keyProperty, Class<K> keyType) {
/*  917 */     setMapKey(keyProperty);
/*  918 */     return (Map)findMap();
/*      */   }
/*      */   
/*      */   public T findUnique() {
/*  922 */     return (T)this.server.findUnique((Query)this, null);
/*      */   }
/*      */   
/*      */   public FutureIds<T> findFutureIds() {
/*  926 */     return this.server.findFutureIds((Query)this, null);
/*      */   }
/*      */   
/*      */   public FutureList<T> findFutureList() {
/*  930 */     return this.server.findFutureList((Query)this, null);
/*      */   }
/*      */   
/*      */   public FutureRowCount<T> findFutureRowCount() {
/*  934 */     return this.server.findFutureRowCount((Query)this, null);
/*      */   }
/*      */   
/*      */   public PagingList<T> findPagingList(int pageSize) {
/*  938 */     return this.server.findPagingList((Query)this, null, pageSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setParameter(int position, Object value) {
/*  947 */     if (this.bindParams == null) {
/*  948 */       this.bindParams = new BindParams();
/*      */     }
/*  950 */     this.bindParams.setParameter(position, value);
/*  951 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setParameter(String name, Object value) {
/*  959 */     if (this.bindParams == null) {
/*  960 */       this.bindParams = new BindParams();
/*      */     }
/*  962 */     this.bindParams.setParameter(name, value);
/*  963 */     return this;
/*      */   }
/*      */   
/*      */   public OrderBy<T> getOrderBy() {
/*  967 */     return this.orderBy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRawWhereClause() {
/*  974 */     return this.rawWhereClause;
/*      */   }
/*      */   
/*      */   public OrderBy<T> orderBy() {
/*  978 */     return order();
/*      */   }
/*      */   
/*      */   public OrderBy<T> order() {
/*  982 */     if (this.orderBy == null) {
/*  983 */       this.orderBy = new OrderBy((Query)this, null);
/*      */     }
/*  985 */     return this.orderBy;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setOrderBy(String orderByClause) {
/*  989 */     return order(orderByClause);
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> orderBy(String orderByClause) {
/*  993 */     return order(orderByClause);
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> order(String orderByClause) {
/*  997 */     if (orderByClause == null || orderByClause.trim().length() == 0) {
/*  998 */       this.orderBy = null;
/*      */     } else {
/* 1000 */       this.orderBy = new OrderBy((Query)this, orderByClause);
/*      */     } 
/* 1002 */     return this;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setOrderBy(OrderBy<T> orderBy) {
/* 1006 */     return setOrder(orderBy);
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setOrder(OrderBy<T> orderBy) {
/* 1010 */     this.orderBy = orderBy;
/* 1011 */     if (orderBy != null) {
/* 1012 */       orderBy.setQuery((Query)this);
/*      */     }
/* 1014 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDistinct() {
/* 1022 */     return this.distinct;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setDistinct(boolean isDistinct) {
/* 1029 */     this.distinct = isDistinct;
/* 1030 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QueryListener<T> getListener() {
/* 1037 */     return this.queryListener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setListener(QueryListener<T> queryListener) {
/* 1049 */     this.queryListener = queryListener;
/* 1050 */     return this;
/*      */   }
/*      */   
/*      */   public Class<T> getBeanType() {
/* 1054 */     return this.beanType;
/*      */   }
/*      */   
/*      */   public void setDetail(OrmQueryDetail detail) {
/* 1058 */     this.detail = detail;
/*      */   }
/*      */   
/*      */   public boolean tuneFetchProperties(OrmQueryDetail tunedDetail) {
/* 1062 */     return this.detail.tuneFetchProperties(tunedDetail);
/*      */   }
/*      */   
/*      */   public OrmQueryDetail getDetail() {
/* 1066 */     return this.detail;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final ArrayList<EntityBean> getContextAdditions() {
/* 1074 */     return this.contextAdditions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void contextAdd(EntityBean bean) {
/* 1084 */     if (this.contextAdditions == null) {
/* 1085 */       this.contextAdditions = new ArrayList<EntityBean>();
/*      */     }
/* 1087 */     this.contextAdditions.add(bean);
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1091 */     return "Query [" + this.whereExpressions + "]";
/*      */   }
/*      */   
/*      */   public TableJoin getIncludeTableJoin() {
/* 1095 */     return this.includeTableJoin;
/*      */   }
/*      */   
/*      */   public void setIncludeTableJoin(TableJoin includeTableJoin) {
/* 1099 */     this.includeTableJoin = includeTableJoin;
/*      */   }
/*      */   
/*      */   public int getFirstRow() {
/* 1103 */     return this.firstRow;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setFirstRow(int firstRow) {
/* 1107 */     this.firstRow = firstRow;
/* 1108 */     return this;
/*      */   }
/*      */   
/*      */   public int getMaxRows() {
/* 1112 */     return this.maxRows;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setMaxRows(int maxRows) {
/* 1116 */     this.maxRows = maxRows;
/* 1117 */     return this;
/*      */   }
/*      */   
/*      */   public String getMapKey() {
/* 1121 */     return this.mapKey;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setMapKey(String mapKey) {
/* 1125 */     this.mapKey = mapKey;
/* 1126 */     return this;
/*      */   }
/*      */   
/*      */   public int getBackgroundFetchAfter() {
/* 1130 */     return this.backgroundFetchAfter;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setBackgroundFetchAfter(int backgroundFetchAfter) {
/* 1134 */     this.backgroundFetchAfter = backgroundFetchAfter;
/* 1135 */     return this;
/*      */   }
/*      */   
/*      */   public Object getId() {
/* 1139 */     return this.id;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setId(Object id) {
/* 1143 */     this.id = id;
/* 1144 */     return this;
/*      */   }
/*      */   
/*      */   public BindParams getBindParams() {
/* 1148 */     return this.bindParams;
/*      */   }
/*      */   
/*      */   public String getQuery() {
/* 1152 */     return this.query;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> addWhere(String addToWhereClause) {
/* 1156 */     return where(addToWhereClause);
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> addWhere(Expression expression) {
/* 1160 */     return where(expression);
/*      */   }
/*      */   
/*      */   public ExpressionList<T> addWhere() {
/* 1164 */     return where();
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> where(String addToWhereClause) {
/* 1168 */     if (this.additionalWhere == null) {
/* 1169 */       this.additionalWhere = addToWhereClause;
/*      */     } else {
/* 1171 */       this.additionalWhere += " " + addToWhereClause;
/*      */     } 
/* 1173 */     return this;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> where(Expression expression) {
/* 1177 */     if (this.whereExpressions == null) {
/* 1178 */       this.whereExpressions = new DefaultExpressionList((Query)this, null);
/*      */     }
/* 1180 */     this.whereExpressions.add(expression);
/* 1181 */     return this;
/*      */   }
/*      */   
/*      */   public ExpressionList<T> where() {
/* 1185 */     if (this.whereExpressions == null) {
/* 1186 */       this.whereExpressions = new DefaultExpressionList((Query)this, null);
/*      */     }
/* 1188 */     return (ExpressionList<T>)this.whereExpressions;
/*      */   }
/*      */ 
/*      */   
/*      */   public ExpressionList<T> filterMany(String prop) {
/* 1193 */     OrmQueryProperties chunk = this.detail.getChunk(prop, true);
/* 1194 */     return (ExpressionList<T>)chunk.filterMany((Query<T>)this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFilterMany(String prop, ExpressionList<?> filterMany) {
/* 1199 */     if (filterMany != null) {
/* 1200 */       OrmQueryProperties chunk = this.detail.getChunk(prop, true);
/* 1201 */       chunk.setFilterMany((SpiExpressionList)filterMany);
/*      */     } 
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> addHaving(String addToHavingClause) {
/* 1206 */     return having(addToHavingClause);
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> addHaving(Expression expression) {
/* 1210 */     return having(expression);
/*      */   }
/*      */   
/*      */   public ExpressionList<T> addHaving() {
/* 1214 */     return having();
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> having(String addToHavingClause) {
/* 1218 */     if (this.additionalHaving == null) {
/* 1219 */       this.additionalHaving = addToHavingClause;
/*      */     } else {
/* 1221 */       this.additionalHaving += " " + addToHavingClause;
/*      */     } 
/* 1223 */     return this;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> having(Expression expression) {
/* 1227 */     if (this.havingExpressions == null) {
/* 1228 */       this.havingExpressions = new DefaultExpressionList((Query)this, null);
/*      */     }
/* 1230 */     this.havingExpressions.add(expression);
/* 1231 */     return this;
/*      */   }
/*      */   
/*      */   public ExpressionList<T> having() {
/* 1235 */     if (this.havingExpressions == null) {
/* 1236 */       this.havingExpressions = new DefaultExpressionList((Query)this, null);
/*      */     }
/* 1238 */     return (ExpressionList<T>)this.havingExpressions;
/*      */   }
/*      */   
/*      */   public SpiExpressionList<T> getHavingExpressions() {
/* 1242 */     return (SpiExpressionList<T>)this.havingExpressions;
/*      */   }
/*      */   
/*      */   public SpiExpressionList<T> getWhereExpressions() {
/* 1246 */     return (SpiExpressionList<T>)this.whereExpressions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean createOwnTransaction() {
/* 1253 */     if (this.futureFetch)
/*      */     {
/*      */       
/* 1256 */       return false;
/*      */     }
/* 1258 */     if (this.backgroundFetchAfter > 0 || this.queryListener != null)
/*      */     {
/*      */       
/* 1261 */       return true;
/*      */     }
/* 1263 */     return false;
/*      */   }
/*      */   
/*      */   public String getGeneratedSql() {
/* 1267 */     return this.generatedSql;
/*      */   }
/*      */   
/*      */   public void setGeneratedSql(String generatedSql) {
/* 1271 */     this.generatedSql = generatedSql;
/*      */   }
/*      */   
/*      */   public Query<T> setBufferFetchSizeHint(int bufferFetchSizeHint) {
/* 1275 */     this.bufferFetchSizeHint = bufferFetchSizeHint;
/* 1276 */     return (Query<T>)this;
/*      */   }
/*      */   
/*      */   public int getBufferFetchSizeHint() {
/* 1280 */     return this.bufferFetchSizeHint;
/*      */   }
/*      */   
/*      */   public void setBeanCollectionTouched(BeanCollectionTouched notify) {
/* 1284 */     this.beanCollectionTouched = notify;
/*      */   }
/*      */   
/*      */   public BeanCollectionTouched getBeanCollectionTouched() {
/* 1288 */     return this.beanCollectionTouched;
/*      */   }
/*      */   
/*      */   public List<Object> getIdList() {
/* 1292 */     return this.partialIds;
/*      */   }
/*      */   
/*      */   public void setIdList(List<Object> partialIds) {
/* 1296 */     this.partialIds = partialIds;
/*      */   }
/*      */   
/*      */   public boolean isFutureFetch() {
/* 1300 */     return this.futureFetch;
/*      */   }
/*      */   
/*      */   public void setFutureFetch(boolean backgroundFetch) {
/* 1304 */     this.futureFetch = backgroundFetch;
/*      */   }
/*      */   
/*      */   public void setCancelableQuery(CancelableQuery cancelableQuery) {
/* 1308 */     synchronized (this) {
/* 1309 */       this.cancelableQuery = cancelableQuery;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void cancel() {
/* 1314 */     synchronized (this) {
/* 1315 */       this.cancelled = true;
/* 1316 */       if (this.cancelableQuery != null) {
/* 1317 */         this.cancelableQuery.cancel();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isCancelled() {
/* 1323 */     synchronized (this) {
/* 1324 */       return this.cancelled;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\querydefn\DefaultOrmQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */