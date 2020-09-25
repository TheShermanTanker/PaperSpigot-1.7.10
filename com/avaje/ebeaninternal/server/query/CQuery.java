/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.ExpressionList;
/*     */ import com.avaje.ebean.QueryIterator;
/*     */ import com.avaje.ebean.QueryListener;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionAdd;
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.bean.NodeUsageCollector;
/*     */ import com.avaje.ebean.bean.NodeUsageListener;
/*     */ import com.avaje.ebean.bean.ObjectGraphNode;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebeaninternal.api.LoadContext;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionList;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
/*     */ import com.avaje.ebeaninternal.server.core.Message;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanCollectionHelp;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanCollectionHelpFactory;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
/*     */ import com.avaje.ebeaninternal.server.transaction.DefaultPersistenceContext;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import com.avaje.ebeaninternal.server.type.DataReader;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class CQuery<T>
/*     */   implements DbReadContext, CancelableQuery
/*     */ {
/*  78 */   private static final Logger logger = Logger.getLogger(CQuery.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int GLOBAL_ROW_LIMIT = 1000000;
/*     */ 
/*     */ 
/*     */   
/*     */   private int rowCount;
/*     */ 
/*     */ 
/*     */   
/*     */   private int loadedBeanCount;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean noMoreRows;
/*     */ 
/*     */ 
/*     */   
/*     */   private Object loadedBeanId;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean loadedBeanChanged;
/*     */ 
/*     */ 
/*     */   
/*     */   private Object loadedBean;
/*     */ 
/*     */ 
/*     */   
/*     */   private Object prevLoadedBean;
/*     */ 
/*     */ 
/*     */   
/*     */   private Object loadedManyBean;
/*     */ 
/*     */ 
/*     */   
/*     */   private Object prevDetailCollection;
/*     */ 
/*     */ 
/*     */   
/*     */   private Object currentDetailCollection;
/*     */ 
/*     */ 
/*     */   
/*     */   private final BeanCollection<T> collection;
/*     */ 
/*     */ 
/*     */   
/*     */   private final BeanCollectionHelp<T> help;
/*     */ 
/*     */ 
/*     */   
/*     */   private final OrmQueryRequest<T> request;
/*     */ 
/*     */ 
/*     */   
/*     */   private final BeanDescriptor<T> desc;
/*     */ 
/*     */ 
/*     */   
/*     */   private final SpiQuery<T> query;
/*     */ 
/*     */ 
/*     */   
/*     */   private final QueryListener<T> queryListener;
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, String> currentPathMap;
/*     */ 
/*     */ 
/*     */   
/*     */   private String currentPrefix;
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean manyIncluded;
/*     */ 
/*     */ 
/*     */   
/*     */   private final CQueryPredicates predicates;
/*     */ 
/*     */   
/*     */   private final SqlTree sqlTree;
/*     */ 
/*     */   
/*     */   private final boolean rawSql;
/*     */ 
/*     */   
/*     */   private final String sql;
/*     */ 
/*     */   
/*     */   private final String logWhereSql;
/*     */ 
/*     */   
/*     */   private final boolean rowNumberIncluded;
/*     */ 
/*     */   
/*     */   private final SqlTreeNode rootNode;
/*     */ 
/*     */   
/*     */   private final BeanPropertyAssocMany<?> manyProperty;
/*     */ 
/*     */   
/*     */   private final ElPropertyValue manyPropertyEl;
/*     */ 
/*     */   
/*     */   private final int backgroundFetchAfter;
/*     */ 
/*     */   
/*     */   private final int maxRowsLimit;
/*     */ 
/*     */   
/*     */   private boolean hasHitBackgroundFetchAfter;
/*     */ 
/*     */   
/*     */   private final PersistenceContext persistenceContext;
/*     */ 
/*     */   
/*     */   private DataReader dataReader;
/*     */ 
/*     */   
/*     */   private PreparedStatement pstmt;
/*     */ 
/*     */   
/*     */   private boolean cancelled;
/*     */ 
/*     */   
/*     */   private String bindLog;
/*     */ 
/*     */   
/*     */   private final CQueryPlan queryPlan;
/*     */ 
/*     */   
/*     */   private long startNano;
/*     */ 
/*     */   
/*     */   private final SpiQuery.Mode queryMode;
/*     */ 
/*     */   
/*     */   private final boolean autoFetchProfiling;
/*     */ 
/*     */   
/*     */   private final ObjectGraphNode autoFetchParentNode;
/*     */ 
/*     */   
/*     */   private final AutoFetchManager autoFetchManager;
/*     */ 
/*     */   
/*     */   private final WeakReference<NodeUsageListener> autoFetchManagerRef;
/*     */ 
/*     */   
/*     */   private int executionTimeMicros;
/*     */ 
/*     */   
/*     */   private final Boolean readOnly;
/*     */ 
/*     */   
/*     */   private final SpiExpressionList<?> filterMany;
/*     */ 
/*     */   
/*     */   private BeanCollectionAdd currentDetailAdd;
/*     */ 
/*     */ 
/*     */   
/*     */   public CQuery(OrmQueryRequest<T> request, CQueryPredicates predicates, CQueryPlan queryPlan) {
/* 248 */     this.request = request;
/* 249 */     this.queryPlan = queryPlan;
/* 250 */     this.query = request.getQuery();
/* 251 */     this.queryMode = this.query.getMode();
/*     */     
/* 253 */     this.readOnly = request.isReadOnly();
/*     */     
/* 255 */     this.autoFetchManager = this.query.getAutoFetchManager();
/* 256 */     this.autoFetchProfiling = (this.autoFetchManager != null);
/* 257 */     this.autoFetchParentNode = this.autoFetchProfiling ? this.query.getParentNode() : null;
/* 258 */     this.autoFetchManagerRef = this.autoFetchProfiling ? new WeakReference(this.autoFetchManager) : null;
/*     */ 
/*     */ 
/*     */     
/* 262 */     this.query.setGeneratedSql(queryPlan.getSql());
/*     */     
/* 264 */     this.sqlTree = queryPlan.getSqlTree();
/* 265 */     this.rootNode = this.sqlTree.getRootNode();
/*     */     
/* 267 */     this.manyProperty = this.sqlTree.getManyProperty();
/* 268 */     this.manyPropertyEl = this.sqlTree.getManyPropertyEl();
/* 269 */     this.manyIncluded = this.sqlTree.isManyIncluded();
/* 270 */     if (this.manyIncluded) {
/*     */       
/* 272 */       String manyPropertyName = this.sqlTree.getManyPropertyName();
/* 273 */       OrmQueryProperties chunk = this.query.getDetail().getChunk(manyPropertyName, false);
/* 274 */       this.filterMany = chunk.getFilterMany();
/*     */     } else {
/* 276 */       this.filterMany = null;
/*     */     } 
/*     */     
/* 279 */     this.sql = queryPlan.getSql();
/* 280 */     this.rawSql = queryPlan.isRawSql();
/* 281 */     this.rowNumberIncluded = queryPlan.isRowNumberIncluded();
/* 282 */     this.logWhereSql = queryPlan.getLogWhereSql();
/* 283 */     this.desc = request.getBeanDescriptor();
/* 284 */     this.predicates = predicates;
/*     */     
/* 286 */     this.queryListener = this.query.getListener();
/* 287 */     if (this.queryListener == null) {
/*     */       
/* 289 */       this.persistenceContext = request.getPersistenceContext();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 294 */       this.persistenceContext = (PersistenceContext)new DefaultPersistenceContext();
/*     */     } 
/*     */     
/* 297 */     this.maxRowsLimit = (this.query.getMaxRows() > 0) ? this.query.getMaxRows() : 1000000;
/* 298 */     this.backgroundFetchAfter = (this.query.getBackgroundFetchAfter() > 0) ? this.query.getBackgroundFetchAfter() : Integer.MAX_VALUE;
/*     */     
/* 300 */     this.help = createHelp(request);
/* 301 */     this.collection = (this.help != null) ? (BeanCollection<T>)this.help.createEmpty(false) : null;
/*     */   }
/*     */   
/*     */   private BeanCollectionHelp<T> createHelp(OrmQueryRequest<T> request) {
/* 305 */     if (request.isFindById()) {
/* 306 */       return null;
/*     */     }
/* 308 */     SpiQuery.Type manyType = request.getQuery().getType();
/* 309 */     if (manyType == null)
/*     */     {
/* 311 */       return null;
/*     */     }
/* 313 */     return BeanCollectionHelpFactory.create(request);
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean isReadOnly() {
/* 318 */     return this.readOnly;
/*     */   }
/*     */   
/*     */   public void propagateState(Object e) {
/* 322 */     if (Boolean.TRUE.equals(this.readOnly) && 
/* 323 */       e instanceof EntityBean) {
/* 324 */       ((EntityBean)e)._ebean_getIntercept().setReadOnly(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public DataReader getDataReader() {
/* 330 */     return this.dataReader;
/*     */   }
/*     */   
/*     */   public SpiQuery.Mode getQueryMode() {
/* 334 */     return this.queryMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVanillaMode() {
/* 341 */     return this.request.isVanillaMode();
/*     */   }
/*     */   
/*     */   public CQueryPredicates getPredicates() {
/* 345 */     return this.predicates;
/*     */   }
/*     */   
/*     */   public LoadContext getGraphContext() {
/* 349 */     return this.request.getGraphContext();
/*     */   }
/*     */   
/*     */   public SpiOrmQueryRequest<?> getQueryRequest() {
/* 353 */     return (SpiOrmQueryRequest<?>)this.request;
/*     */   }
/*     */   
/*     */   public void cancel() {
/* 357 */     synchronized (this) {
/* 358 */       this.cancelled = true;
/* 359 */       if (this.pstmt != null) {
/*     */         try {
/* 361 */           this.pstmt.cancel();
/* 362 */         } catch (SQLException e) {
/* 363 */           String msg = "Error cancelling query";
/* 364 */           throw new PersistenceException(msg, e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean prepareBindExecuteQuery() throws SQLException {
/* 372 */     synchronized (this) {
/* 373 */       if (this.cancelled || this.query.isCancelled()) {
/*     */         
/* 375 */         this.cancelled = true;
/* 376 */         return false;
/*     */       } 
/*     */       
/* 379 */       this.startNano = System.nanoTime();
/*     */ 
/*     */       
/* 382 */       SpiTransaction t = this.request.getTransaction();
/* 383 */       Connection conn = t.getInternalConnection();
/* 384 */       this.pstmt = conn.prepareStatement(this.sql);
/*     */       
/* 386 */       if (this.query.getTimeout() > 0) {
/* 387 */         this.pstmt.setQueryTimeout(this.query.getTimeout());
/*     */       }
/* 389 */       if (this.query.getBufferFetchSizeHint() > 0) {
/* 390 */         this.pstmt.setFetchSize(this.query.getBufferFetchSizeHint());
/*     */       }
/*     */       
/* 393 */       DataBind dataBind = new DataBind(this.pstmt);
/*     */ 
/*     */       
/* 396 */       this.queryPlan.bindEncryptedProperties(dataBind);
/*     */       
/* 398 */       this.bindLog = this.predicates.bind(dataBind);
/*     */ 
/*     */       
/* 401 */       ResultSet rset = this.pstmt.executeQuery();
/* 402 */       this.dataReader = this.queryPlan.createDataReader(rset);
/*     */       
/* 404 */       return true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*     */     try {
/* 417 */       if (this.dataReader != null) {
/* 418 */         this.dataReader.close();
/* 419 */         this.dataReader = null;
/*     */       } 
/* 421 */     } catch (SQLException e) {
/* 422 */       logger.log(Level.SEVERE, (String)null, e);
/*     */     } 
/*     */     try {
/* 425 */       if (this.pstmt != null) {
/* 426 */         this.pstmt.close();
/* 427 */         this.pstmt = null;
/*     */       } 
/* 429 */     } catch (SQLException e) {
/* 430 */       logger.log(Level.SEVERE, (String)null, e);
/*     */     } 
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
/*     */   public PersistenceContext getPersistenceContext() {
/* 464 */     return this.persistenceContext;
/*     */   }
/*     */   
/*     */   public void setLoadedBean(Object bean, Object id) {
/* 468 */     if (id == null || !id.equals(this.loadedBeanId)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 474 */       if (this.manyIncluded) {
/* 475 */         if (this.rowCount > 1) {
/* 476 */           this.loadedBeanChanged = true;
/*     */         }
/* 478 */         this.prevLoadedBean = this.loadedBean;
/* 479 */         this.loadedBeanId = id;
/*     */       } 
/* 481 */       this.loadedBean = bean;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setLoadedManyBean(Object manyValue) {
/* 486 */     this.loadedManyBean = manyValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getLoadedBean() {
/* 494 */     if (this.manyIncluded) {
/* 495 */       if (this.prevDetailCollection instanceof BeanCollection) {
/* 496 */         ((BeanCollection)this.prevDetailCollection).setModifyListening(this.manyProperty.getModifyListenMode());
/*     */       }
/* 498 */       else if (this.currentDetailCollection instanceof BeanCollection) {
/* 499 */         ((BeanCollection)this.currentDetailCollection).setModifyListening(this.manyProperty.getModifyListenMode());
/*     */       } 
/*     */     }
/*     */     
/* 503 */     if (this.prevLoadedBean != null) {
/* 504 */       return (T)this.prevLoadedBean;
/*     */     }
/* 506 */     return (T)this.loadedBean;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasMoreRows() throws SQLException {
/* 511 */     synchronized (this) {
/* 512 */       if (this.cancelled) {
/* 513 */         return false;
/*     */       }
/* 515 */       return this.dataReader.next();
/*     */     } 
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
/*     */   private boolean readRow() throws SQLException {
/* 528 */     synchronized (this) {
/* 529 */       if (this.cancelled) {
/* 530 */         return false;
/*     */       }
/*     */       
/* 533 */       if (!this.dataReader.next()) {
/* 534 */         return false;
/*     */       }
/*     */       
/* 537 */       this.rowCount++;
/* 538 */       this.dataReader.resetColumnPosition();
/*     */       
/* 540 */       if (this.rowNumberIncluded)
/*     */       {
/* 542 */         this.dataReader.incrementPos(1);
/*     */       }
/*     */       
/* 545 */       this.rootNode.load(this, null);
/*     */       
/* 547 */       return true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getQueryExecutionTimeMicros() {
/* 552 */     return this.executionTimeMicros;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean readBean() throws SQLException {
/* 557 */     boolean result = readBeanInternal(true);
/*     */     
/* 559 */     updateExecutionStatistics();
/*     */     
/* 561 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean readBeanInternal(boolean inForeground) throws SQLException {
/* 566 */     if (this.loadedBeanCount >= this.maxRowsLimit) {
/* 567 */       this.collection.setHasMoreRows(hasMoreRows());
/* 568 */       return false;
/*     */     } 
/*     */     
/* 571 */     if (inForeground && this.loadedBeanCount >= this.backgroundFetchAfter) {
/* 572 */       this.hasHitBackgroundFetchAfter = true;
/* 573 */       this.collection.setFinishedFetch(false);
/* 574 */       return false;
/*     */     } 
/*     */     
/* 577 */     if (!this.manyIncluded)
/*     */     {
/* 579 */       return readRow();
/*     */     }
/*     */     
/* 582 */     if (this.noMoreRows) {
/* 583 */       return false;
/*     */     }
/*     */     
/* 586 */     if (this.rowCount == 0) {
/* 587 */       if (!readRow())
/*     */       {
/* 589 */         return false;
/*     */       }
/* 591 */       createNewDetailCollection();
/*     */     } 
/*     */ 
/*     */     
/* 595 */     if (readIntoCurrentDetailCollection()) {
/* 596 */       createNewDetailCollection();
/*     */       
/* 598 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 602 */     this.prevDetailCollection = null;
/* 603 */     this.prevLoadedBean = null;
/* 604 */     this.noMoreRows = true;
/* 605 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean readIntoCurrentDetailCollection() throws SQLException {
/* 610 */     while (readRow()) {
/* 611 */       if (this.loadedBeanChanged) {
/* 612 */         this.loadedBeanChanged = false;
/* 613 */         return true;
/*     */       } 
/* 615 */       addToCurrentDetailCollection();
/*     */     } 
/*     */     
/* 618 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void createNewDetailCollection() {
/* 624 */     this.prevDetailCollection = this.currentDetailCollection;
/* 625 */     if (this.queryMode.equals(SpiQuery.Mode.LAZYLOAD_MANY)) {
/*     */       
/* 627 */       this.currentDetailCollection = this.manyPropertyEl.elGetValue(this.loadedBean);
/*     */     } else {
/*     */       
/* 630 */       this.currentDetailCollection = this.manyProperty.createEmpty(this.request.isVanillaMode());
/* 631 */       this.manyPropertyEl.elSetValue(this.loadedBean, this.currentDetailCollection, false, false);
/*     */     } 
/*     */     
/* 634 */     if (this.filterMany != null && !this.request.isVanillaMode())
/*     */     {
/* 636 */       ((BeanCollection)this.currentDetailCollection).setFilterMany((ExpressionList)this.filterMany);
/*     */     }
/*     */ 
/*     */     
/* 640 */     this.currentDetailAdd = this.manyProperty.getBeanCollectionAdd(this.currentDetailCollection, null);
/* 641 */     addToCurrentDetailCollection();
/*     */   }
/*     */   
/*     */   private void addToCurrentDetailCollection() {
/* 645 */     if (this.loadedManyBean != null) {
/* 646 */       this.currentDetailAdd.addBean(this.loadedManyBean);
/*     */     }
/*     */   }
/*     */   
/*     */   public BeanCollection<T> continueFetchingInBackground() throws SQLException {
/* 651 */     readTheRows(false);
/* 652 */     this.collection.setFinishedFetch(true);
/* 653 */     return this.collection;
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanCollection<T> readCollection() throws SQLException {
/* 658 */     readTheRows(true);
/*     */     
/* 660 */     updateExecutionStatistics();
/*     */     
/* 662 */     return this.collection;
/*     */   }
/*     */   
/*     */   protected void updateExecutionStatistics() {
/*     */     try {
/* 667 */       long exeNano = System.nanoTime() - this.startNano;
/* 668 */       this.executionTimeMicros = (int)exeNano / 1000;
/*     */       
/* 670 */       if (this.autoFetchProfiling) {
/* 671 */         this.autoFetchManager.collectQueryInfo(this.autoFetchParentNode, this.loadedBeanCount, this.executionTimeMicros);
/*     */       }
/* 673 */       this.queryPlan.executionTime(this.loadedBeanCount, this.executionTimeMicros);
/*     */     }
/* 675 */     catch (Exception e) {
/* 676 */       logger.log(Level.SEVERE, (String)null, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public QueryIterator<T> readIterate(int bufferSize, OrmQueryRequest<T> request) {
/* 682 */     if (bufferSize > 0) {
/* 683 */       return new CQueryIteratorWithBuffer<T>(this, request, bufferSize);
/*     */     }
/*     */     
/* 686 */     return new CQueryIteratorSimple<T>(this, request);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readTheRows(boolean inForeground) throws SQLException {
/* 691 */     while (hasNextBean(inForeground)) {
/* 692 */       if (this.queryListener != null) {
/* 693 */         this.queryListener.process(getLoadedBean());
/*     */         
/*     */         continue;
/*     */       } 
/* 697 */       this.help.add(this.collection, getLoadedBean());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasNextBean(boolean inForeground) throws SQLException {
/* 705 */     if (!readBeanInternal(inForeground)) {
/* 706 */       return false;
/*     */     }
/*     */     
/* 709 */     this.loadedBeanCount++;
/* 710 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLoadedRowDetail() {
/* 715 */     if (!this.manyIncluded) {
/* 716 */       return String.valueOf(this.rowCount);
/*     */     }
/* 718 */     return this.loadedBeanCount + ":" + this.rowCount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(String path, EntityBeanIntercept ebi) {
/* 724 */     path = getPath(path);
/* 725 */     this.request.getGraphContext().register(path, ebi);
/*     */   }
/*     */ 
/*     */   
/*     */   public void register(String path, BeanCollection<?> bc) {
/* 730 */     path = getPath(path);
/* 731 */     this.request.getGraphContext().register(path, bc);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useBackgroundToContinueFetch() {
/* 736 */     return this.hasHitBackgroundFetchAfter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 743 */     return this.query.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRawSql() {
/* 750 */     return this.rawSql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLogWhereSql() {
/* 757 */     return this.logWhereSql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyAssocMany<?> getManyProperty() {
/* 765 */     return this.manyProperty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSummary() {
/* 772 */     return this.sqlTree.getSummary();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SqlTree getSqlTree() {
/* 780 */     return this.sqlTree;
/*     */   }
/*     */   
/*     */   public String getBindLog() {
/* 784 */     return this.bindLog;
/*     */   }
/*     */   
/*     */   public SpiTransaction getTransaction() {
/* 788 */     return this.request.getTransaction();
/*     */   }
/*     */   
/*     */   public String getBeanType() {
/* 792 */     return this.desc.getFullName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBeanName() {
/* 799 */     return this.desc.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGeneratedSql() {
/* 806 */     return this.sql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistenceException createPersistenceException(SQLException e) {
/* 814 */     return createPersistenceException(e, getTransaction(), this.bindLog, this.sql);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PersistenceException createPersistenceException(SQLException e, SpiTransaction t, String bindLog, String sql) {
/* 822 */     if (t.isLogSummary()) {
/*     */       
/* 824 */       String errMsg = StringHelper.replaceStringMulti(e.getMessage(), new String[] { "\r", "\n" }, "\\n ");
/* 825 */       String msg = "ERROR executing query:   bindLog[" + bindLog + "] error[" + errMsg + "]";
/* 826 */       t.logInternal(msg);
/*     */     } 
/*     */ 
/*     */     
/* 830 */     t.getConnection();
/*     */ 
/*     */     
/* 833 */     String m = Message.msg("fetch.sqlerror", e.getMessage(), bindLog, sql);
/* 834 */     return new PersistenceException(m, e);
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
/*     */   public boolean isAutoFetchProfiling() {
/* 847 */     return (this.autoFetchProfiling && this.query.isUsageProfiling());
/*     */   }
/*     */ 
/*     */   
/*     */   private String getPath(String propertyName) {
/* 852 */     if (this.currentPrefix == null)
/* 853 */       return propertyName; 
/* 854 */     if (propertyName == null) {
/* 855 */       return this.currentPrefix;
/*     */     }
/*     */     
/* 858 */     String path = this.currentPathMap.get(propertyName);
/* 859 */     if (path != null) {
/* 860 */       return path;
/*     */     }
/* 862 */     return this.currentPrefix + "." + propertyName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void profileBean(EntityBeanIntercept ebi, String prefix) {
/* 869 */     ObjectGraphNode node = this.request.getGraphContext().getObjectGraphNode(prefix);
/*     */     
/* 871 */     ebi.setNodeUsageCollector(new NodeUsageCollector(node, this.autoFetchManagerRef));
/*     */   }
/*     */   
/*     */   public void setCurrentPrefix(String currentPrefix, Map<String, String> currentPathMap) {
/* 875 */     this.currentPrefix = currentPrefix;
/* 876 */     this.currentPathMap = currentPathMap;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\CQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */