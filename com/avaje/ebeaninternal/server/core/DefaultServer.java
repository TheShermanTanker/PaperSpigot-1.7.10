/*      */ package com.avaje.ebeaninternal.server.core;
/*      */ 
/*      */ import com.avaje.ebean.AdminAutofetch;
/*      */ import com.avaje.ebean.AdminLogging;
/*      */ import com.avaje.ebean.BackgroundExecutor;
/*      */ import com.avaje.ebean.BeanState;
/*      */ import com.avaje.ebean.CallableSql;
/*      */ import com.avaje.ebean.Ebean;
/*      */ import com.avaje.ebean.EbeanServer;
/*      */ import com.avaje.ebean.ExpressionFactory;
/*      */ import com.avaje.ebean.Filter;
/*      */ import com.avaje.ebean.FutureIds;
/*      */ import com.avaje.ebean.FutureList;
/*      */ import com.avaje.ebean.FutureRowCount;
/*      */ import com.avaje.ebean.InvalidValue;
/*      */ import com.avaje.ebean.PagingList;
/*      */ import com.avaje.ebean.Query;
/*      */ import com.avaje.ebean.QueryIterator;
/*      */ import com.avaje.ebean.QueryResultVisitor;
/*      */ import com.avaje.ebean.SqlFutureList;
/*      */ import com.avaje.ebean.SqlQuery;
/*      */ import com.avaje.ebean.SqlRow;
/*      */ import com.avaje.ebean.SqlUpdate;
/*      */ import com.avaje.ebean.Transaction;
/*      */ import com.avaje.ebean.TxCallable;
/*      */ import com.avaje.ebean.TxIsolation;
/*      */ import com.avaje.ebean.TxRunnable;
/*      */ import com.avaje.ebean.TxScope;
/*      */ import com.avaje.ebean.TxType;
/*      */ import com.avaje.ebean.Update;
/*      */ import com.avaje.ebean.ValuePair;
/*      */ import com.avaje.ebean.bean.BeanCollection;
/*      */ import com.avaje.ebean.bean.CallStack;
/*      */ import com.avaje.ebean.bean.EntityBean;
/*      */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*      */ import com.avaje.ebean.bean.PersistenceContext;
/*      */ import com.avaje.ebean.cache.ServerCacheManager;
/*      */ import com.avaje.ebean.config.EncryptKeyManager;
/*      */ import com.avaje.ebean.config.GlobalProperties;
/*      */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*      */ import com.avaje.ebean.config.ldap.LdapConfig;
/*      */ import com.avaje.ebean.event.BeanPersistController;
/*      */ import com.avaje.ebean.event.BeanQueryAdapter;
/*      */ import com.avaje.ebean.text.csv.CsvReader;
/*      */ import com.avaje.ebean.text.json.JsonContext;
/*      */ import com.avaje.ebean.text.json.JsonElement;
/*      */ import com.avaje.ebeaninternal.api.LoadBeanRequest;
/*      */ import com.avaje.ebeaninternal.api.LoadManyRequest;
/*      */ import com.avaje.ebeaninternal.api.ScopeTrans;
/*      */ import com.avaje.ebeaninternal.api.SpiBackgroundExecutor;
/*      */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*      */ import com.avaje.ebeaninternal.api.SpiQuery;
/*      */ import com.avaje.ebeaninternal.api.SpiSqlQuery;
/*      */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*      */ import com.avaje.ebeaninternal.api.SpiTransactionScopeManager;
/*      */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*      */ import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
/*      */ import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanManager;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*      */ import com.avaje.ebeaninternal.server.deploy.DNativeQuery;
/*      */ import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
/*      */ import com.avaje.ebeaninternal.server.deploy.DeployNamedUpdate;
/*      */ import com.avaje.ebeaninternal.server.deploy.InheritInfo;
/*      */ import com.avaje.ebeaninternal.server.el.ElFilter;
/*      */ import com.avaje.ebeaninternal.server.jmx.MAdminAutofetch;
/*      */ import com.avaje.ebeaninternal.server.ldap.DefaultLdapOrmQuery;
/*      */ import com.avaje.ebeaninternal.server.ldap.LdapOrmQueryEngine;
/*      */ import com.avaje.ebeaninternal.server.ldap.LdapOrmQueryRequest;
/*      */ import com.avaje.ebeaninternal.server.ldap.expression.LdapExpressionFactory;
/*      */ import com.avaje.ebeaninternal.server.lib.ShutdownManager;
/*      */ import com.avaje.ebeaninternal.server.loadcontext.DLoadContext;
/*      */ import com.avaje.ebeaninternal.server.query.CQuery;
/*      */ import com.avaje.ebeaninternal.server.query.CQueryEngine;
/*      */ import com.avaje.ebeaninternal.server.query.CallableQueryIds;
/*      */ import com.avaje.ebeaninternal.server.query.CallableQueryList;
/*      */ import com.avaje.ebeaninternal.server.query.CallableQueryRowCount;
/*      */ import com.avaje.ebeaninternal.server.query.CallableSqlQueryList;
/*      */ import com.avaje.ebeaninternal.server.query.LimitOffsetPagingQuery;
/*      */ import com.avaje.ebeaninternal.server.query.QueryFutureIds;
/*      */ import com.avaje.ebeaninternal.server.query.QueryFutureList;
/*      */ import com.avaje.ebeaninternal.server.query.QueryFutureRowCount;
/*      */ import com.avaje.ebeaninternal.server.query.SqlQueryFutureList;
/*      */ import com.avaje.ebeaninternal.server.querydefn.DefaultOrmQuery;
/*      */ import com.avaje.ebeaninternal.server.querydefn.DefaultOrmUpdate;
/*      */ import com.avaje.ebeaninternal.server.querydefn.DefaultRelationalQuery;
/*      */ import com.avaje.ebeaninternal.server.querydefn.NaturalKeyBindParam;
/*      */ import com.avaje.ebeaninternal.server.text.csv.TCsvReader;
/*      */ import com.avaje.ebeaninternal.server.transaction.DefaultPersistenceContext;
/*      */ import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
/*      */ import com.avaje.ebeaninternal.server.transaction.TransactionManager;
/*      */ import com.avaje.ebeaninternal.server.transaction.TransactionScopeManager;
/*      */ import com.avaje.ebeaninternal.util.ParamTypeHelper;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.lang.reflect.Type;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.FutureTask;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.management.InstanceAlreadyExistsException;
/*      */ import javax.management.MBeanServer;
/*      */ import javax.management.ObjectName;
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
/*      */ public final class DefaultServer
/*      */   implements SpiEbeanServer
/*      */ {
/*  142 */   private static final Logger logger = Logger.getLogger(DefaultServer.class.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  147 */   private static final InvalidValue[] EMPTY_INVALID_VALUES = new InvalidValue[0];
/*      */ 
/*      */   
/*      */   private final String serverName;
/*      */ 
/*      */   
/*      */   private final DatabasePlatform databasePlatform;
/*      */ 
/*      */   
/*      */   private final AdminLogging adminLogging;
/*      */ 
/*      */   
/*      */   private final AdminAutofetch adminAutofetch;
/*      */ 
/*      */   
/*      */   private final TransactionManager transactionManager;
/*      */ 
/*      */   
/*      */   private final TransactionScopeManager transactionScopeManager;
/*      */ 
/*      */   
/*      */   private final int maxCallStack;
/*      */ 
/*      */   
/*      */   private final boolean rollbackOnChecked;
/*      */   
/*      */   private final boolean defaultDeleteMissingChildren;
/*      */   
/*      */   private final boolean defaultUpdateNullProperties;
/*      */   
/*      */   private final boolean vanillaMode;
/*      */   
/*      */   private final boolean vanillaRefMode;
/*      */   
/*      */   private final LdapOrmQueryEngine ldapQueryEngine;
/*      */   
/*      */   private final Persister persister;
/*      */   
/*      */   private final OrmQueryEngine queryEngine;
/*      */   
/*      */   private final RelationalQueryEngine relationalQueryEngine;
/*      */   
/*      */   private final ServerCacheManager serverCacheManager;
/*      */   
/*      */   private final BeanDescriptorManager beanDescriptorManager;
/*      */   
/*  193 */   private final DiffHelp diffHelp = new DiffHelp();
/*      */   
/*      */   private final AutoFetchManager autoFetchManager;
/*      */   
/*      */   private final CQueryEngine cqueryEngine;
/*      */   
/*      */   private final DdlGenerator ddlGenerator;
/*      */   
/*  201 */   private final ExpressionFactory ldapExpressionFactory = (ExpressionFactory)new LdapExpressionFactory();
/*      */ 
/*      */ 
/*      */   
/*      */   private final ExpressionFactory expressionFactory;
/*      */ 
/*      */   
/*      */   private final SpiBackgroundExecutor backgroundExecutor;
/*      */ 
/*      */   
/*      */   private final DefaultBeanLoader beanLoader;
/*      */ 
/*      */   
/*      */   private final EncryptKeyManager encryptKeyManager;
/*      */ 
/*      */   
/*      */   private final JsonContext jsonContext;
/*      */ 
/*      */   
/*      */   private String mbeanName;
/*      */ 
/*      */   
/*      */   private MBeanServer mbeanServer;
/*      */ 
/*      */   
/*      */   private int lazyLoadBatchSize;
/*      */ 
/*      */   
/*      */   private int queryBatchSize;
/*      */ 
/*      */   
/*      */   private PstmtBatch pstmtBatch;
/*      */ 
/*      */   
/*      */   private static final int IGNORE_LEADING_ELEMENTS = 5;
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultServer(InternalConfiguration config, ServerCacheManager cache) {
/*  240 */     this.vanillaMode = config.getServerConfig().isVanillaMode();
/*  241 */     this.vanillaRefMode = config.getServerConfig().isVanillaRefMode();
/*      */     
/*  243 */     this.serverCacheManager = cache;
/*  244 */     this.pstmtBatch = config.getPstmtBatch();
/*  245 */     this.databasePlatform = config.getDatabasePlatform();
/*  246 */     this.backgroundExecutor = config.getBackgroundExecutor();
/*  247 */     this.serverName = config.getServerConfig().getName();
/*  248 */     this.lazyLoadBatchSize = config.getServerConfig().getLazyLoadBatchSize();
/*  249 */     this.queryBatchSize = config.getServerConfig().getQueryBatchSize();
/*  250 */     this.cqueryEngine = config.getCQueryEngine();
/*  251 */     this.expressionFactory = config.getExpressionFactory();
/*  252 */     this.adminLogging = (AdminLogging)config.getLogControl();
/*  253 */     this.encryptKeyManager = config.getServerConfig().getEncryptKeyManager();
/*      */     
/*  255 */     this.beanDescriptorManager = config.getBeanDescriptorManager();
/*  256 */     this.beanDescriptorManager.setEbeanServer(this);
/*      */     
/*  258 */     this.maxCallStack = GlobalProperties.getInt("ebean.maxCallStack", 5);
/*      */     
/*  260 */     this.defaultUpdateNullProperties = "true".equalsIgnoreCase(config.getServerConfig().getProperty("defaultUpdateNullProperties", "false"));
/*      */     
/*  262 */     this.defaultDeleteMissingChildren = "true".equalsIgnoreCase(config.getServerConfig().getProperty("defaultDeleteMissingChildren", "true"));
/*      */ 
/*      */     
/*  265 */     this.rollbackOnChecked = GlobalProperties.getBoolean("ebean.transaction.rollbackOnChecked", true);
/*  266 */     this.transactionManager = config.getTransactionManager();
/*  267 */     this.transactionScopeManager = config.getTransactionScopeManager();
/*      */     
/*  269 */     this.persister = config.createPersister(this);
/*  270 */     this.queryEngine = config.createOrmQueryEngine();
/*  271 */     this.relationalQueryEngine = config.createRelationalQueryEngine();
/*      */     
/*  273 */     this.autoFetchManager = config.createAutoFetchManager(this);
/*  274 */     this.adminAutofetch = (AdminAutofetch)new MAdminAutofetch(this.autoFetchManager);
/*      */     
/*  276 */     this.ddlGenerator = new DdlGenerator(this, config.getDatabasePlatform(), config.getServerConfig());
/*  277 */     this.beanLoader = new DefaultBeanLoader(this, config.getDebugLazyLoad());
/*  278 */     this.jsonContext = config.createJsonContext(this);
/*      */     
/*  280 */     LdapConfig ldapConfig = config.getServerConfig().getLdapConfig();
/*  281 */     if (ldapConfig == null) {
/*  282 */       this.ldapQueryEngine = null;
/*      */     } else {
/*  284 */       this.ldapQueryEngine = new LdapOrmQueryEngine(ldapConfig.isVanillaMode(), ldapConfig.getContextFactory());
/*      */     } 
/*      */     
/*  287 */     ShutdownManager.register(new Shutdown());
/*      */   }
/*      */   
/*      */   public boolean isDefaultDeleteMissingChildren() {
/*  291 */     return this.defaultDeleteMissingChildren;
/*      */   }
/*      */   
/*      */   public boolean isDefaultUpdateNullProperties() {
/*  295 */     return this.defaultUpdateNullProperties;
/*      */   }
/*      */   
/*      */   public boolean isVanillaMode() {
/*  299 */     return this.vanillaMode;
/*      */   }
/*      */   
/*      */   public int getLazyLoadBatchSize() {
/*  303 */     return this.lazyLoadBatchSize;
/*      */   }
/*      */   
/*      */   public PstmtBatch getPstmtBatch() {
/*  307 */     return this.pstmtBatch;
/*      */   }
/*      */   
/*      */   public DatabasePlatform getDatabasePlatform() {
/*  311 */     return this.databasePlatform;
/*      */   }
/*      */   
/*      */   public BackgroundExecutor getBackgroundExecutor() {
/*  315 */     return (BackgroundExecutor)this.backgroundExecutor;
/*      */   }
/*      */   
/*      */   public ExpressionFactory getExpressionFactory() {
/*  319 */     return this.expressionFactory;
/*      */   }
/*      */   
/*      */   public DdlGenerator getDdlGenerator() {
/*  323 */     return this.ddlGenerator;
/*      */   }
/*      */   
/*      */   public AdminLogging getAdminLogging() {
/*  327 */     return this.adminLogging;
/*      */   }
/*      */   
/*      */   public AdminAutofetch getAdminAutofetch() {
/*  331 */     return this.adminAutofetch;
/*      */   }
/*      */   
/*      */   public AutoFetchManager getAutoFetchManager() {
/*  335 */     return this.autoFetchManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initialise() {
/*  342 */     if (this.encryptKeyManager != null) {
/*  343 */       this.encryptKeyManager.initialise();
/*      */     }
/*  345 */     List<BeanDescriptor<?>> list = this.beanDescriptorManager.getBeanDescriptorList();
/*  346 */     for (int i = 0; i < list.size(); i++) {
/*  347 */       ((BeanDescriptor)list.get(i)).cacheInitialise();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void start() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerMBeans(MBeanServer mbeanServer, int uniqueServerId) {
/*      */     ObjectName adminName, autofethcName;
/*  360 */     this.mbeanServer = mbeanServer;
/*  361 */     this.mbeanName = "Ebean:server=" + this.serverName + uniqueServerId;
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  366 */       adminName = new ObjectName(this.mbeanName + ",function=Logging");
/*  367 */       autofethcName = new ObjectName(this.mbeanName + ",key=AutoFetch");
/*  368 */     } catch (Exception e) {
/*  369 */       String msg = "Failed to register the JMX beans for Ebean server [" + this.serverName + "].";
/*  370 */       logger.log(Level.SEVERE, msg, e);
/*      */       
/*      */       return;
/*      */     } 
/*      */     try {
/*  375 */       mbeanServer.registerMBean(this.adminLogging, adminName);
/*  376 */       mbeanServer.registerMBean(this.adminAutofetch, autofethcName);
/*      */     }
/*  378 */     catch (InstanceAlreadyExistsException e) {
/*      */       
/*  380 */       String msg = "JMX beans for Ebean server [" + this.serverName + "] already registered. Will try unregister/register" + e.getMessage();
/*  381 */       logger.log(Level.WARNING, msg);
/*      */       try {
/*  383 */         mbeanServer.unregisterMBean(adminName);
/*  384 */         mbeanServer.unregisterMBean(autofethcName);
/*      */         
/*  386 */         mbeanServer.registerMBean(this.adminLogging, adminName);
/*  387 */         mbeanServer.registerMBean(this.adminAutofetch, autofethcName);
/*      */       }
/*  389 */       catch (Exception ae) {
/*  390 */         String amsg = "Unable to unregister/register the JMX beans for Ebean server [" + this.serverName + "].";
/*  391 */         logger.log(Level.SEVERE, amsg, ae);
/*      */       } 
/*  393 */     } catch (Exception e) {
/*  394 */       String msg = "Error registering MBean[" + this.mbeanName + "]";
/*  395 */       logger.log(Level.SEVERE, msg, e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private final class Shutdown implements Runnable {
/*      */     public void run() {
/*      */       try {
/*  402 */         if (DefaultServer.this.mbeanServer != null) {
/*  403 */           DefaultServer.this.mbeanServer.unregisterMBean(new ObjectName(DefaultServer.this.mbeanName + ",function=Logging"));
/*  404 */           DefaultServer.this.mbeanServer.unregisterMBean(new ObjectName(DefaultServer.this.mbeanName + ",key=AutoFetch"));
/*      */         } 
/*  406 */       } catch (Exception e) {
/*  407 */         String msg = "Error unregistering Ebean " + DefaultServer.this.mbeanName;
/*  408 */         DefaultServer.logger.log(Level.SEVERE, msg, e);
/*      */       } 
/*      */ 
/*      */       
/*  412 */       DefaultServer.this.transactionManager.shutdown();
/*  413 */       DefaultServer.this.autoFetchManager.shutdown();
/*  414 */       DefaultServer.this.backgroundExecutor.shutdown();
/*      */     }
/*      */ 
/*      */     
/*      */     private Shutdown() {}
/*      */   }
/*      */   
/*      */   public String getName() {
/*  422 */     return this.serverName;
/*      */   }
/*      */   
/*      */   public BeanState getBeanState(Object bean) {
/*  426 */     if (bean instanceof EntityBean) {
/*  427 */       return new DefaultBeanState((EntityBean)bean);
/*      */     }
/*      */ 
/*      */     
/*  431 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void runCacheWarming() {
/*  438 */     List<BeanDescriptor<?>> descList = this.beanDescriptorManager.getBeanDescriptorList();
/*  439 */     for (int i = 0; i < descList.size(); i++) {
/*  440 */       ((BeanDescriptor)descList.get(i)).runCacheWarming();
/*      */     }
/*      */   }
/*      */   
/*      */   public void runCacheWarming(Class<?> beanType) {
/*  445 */     BeanDescriptor<?> desc = this.beanDescriptorManager.getBeanDescriptor(beanType);
/*  446 */     if (desc == null) {
/*  447 */       String msg = "Is " + beanType + " an entity? Could not find a BeanDescriptor";
/*  448 */       throw new PersistenceException(msg);
/*      */     } 
/*  450 */     desc.runCacheWarming();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> CQuery<T> compileQuery(Query<T> query, Transaction t) {
/*  458 */     SpiOrmQueryRequest<T> qr = createQueryRequest(SpiQuery.Type.SUBQUERY, query, t);
/*  459 */     OrmQueryRequest<T> orm = (OrmQueryRequest<T>)qr;
/*  460 */     return this.cqueryEngine.buildQuery(orm);
/*      */   }
/*      */   
/*      */   public CQueryEngine getQueryEngine() {
/*  464 */     return this.cqueryEngine;
/*      */   }
/*      */   
/*      */   public ServerCacheManager getServerCacheManager() {
/*  468 */     return this.serverCacheManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AutoFetchManager getProfileListener() {
/*  475 */     return this.autoFetchManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RelationalQueryEngine getRelationalQueryEngine() {
/*  482 */     return this.relationalQueryEngine;
/*      */   }
/*      */ 
/*      */   
/*      */   public void refreshMany(Object parentBean, String propertyName, Transaction t) {
/*  487 */     this.beanLoader.refreshMany(parentBean, propertyName, t);
/*      */   }
/*      */ 
/*      */   
/*      */   public void refreshMany(Object parentBean, String propertyName) {
/*  492 */     this.beanLoader.refreshMany(parentBean, propertyName);
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadMany(LoadManyRequest loadRequest) {
/*  497 */     this.beanLoader.loadMany(loadRequest);
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadMany(BeanCollection<?> bc, boolean onlyIds) {
/*  502 */     this.beanLoader.loadMany(bc, null, onlyIds);
/*      */   }
/*      */ 
/*      */   
/*      */   public void refresh(Object bean) {
/*  507 */     this.beanLoader.refresh(bean);
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadBean(LoadBeanRequest loadRequest) {
/*  512 */     this.beanLoader.loadBean(loadRequest);
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadBean(EntityBeanIntercept ebi) {
/*  517 */     this.beanLoader.loadBean(ebi);
/*      */   }
/*      */   
/*      */   public InvalidValue validate(Object bean) {
/*  521 */     if (bean == null) {
/*  522 */       return null;
/*      */     }
/*  524 */     BeanDescriptor<?> beanDescriptor = getBeanDescriptor(bean.getClass());
/*  525 */     return beanDescriptor.validate(true, bean);
/*      */   }
/*      */   
/*      */   public InvalidValue[] validate(Object bean, String propertyName, Object value) {
/*  529 */     if (bean == null) {
/*  530 */       return null;
/*      */     }
/*  532 */     BeanDescriptor<?> beanDescriptor = getBeanDescriptor(bean.getClass());
/*  533 */     BeanProperty prop = beanDescriptor.getBeanProperty(propertyName);
/*  534 */     if (prop == null) {
/*  535 */       String msg = "property " + propertyName + " was not found?";
/*  536 */       throw new PersistenceException(msg);
/*      */     } 
/*  538 */     if (value == null) {
/*  539 */       value = prop.getValue(bean);
/*      */     }
/*  541 */     List<InvalidValue> errors = prop.validate(true, value);
/*  542 */     if (errors == null) {
/*  543 */       return EMPTY_INVALID_VALUES;
/*      */     }
/*  545 */     return InvalidValue.toArray(errors);
/*      */   }
/*      */ 
/*      */   
/*      */   public Map<String, ValuePair> diff(Object a, Object b) {
/*  550 */     if (a == null) {
/*  551 */       return null;
/*      */     }
/*      */     
/*  554 */     BeanDescriptor<?> desc = getBeanDescriptor(a.getClass());
/*  555 */     return this.diffHelp.diff(a, b, desc);
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
/*      */   public void externalModification(TransactionEventTable tableEvent) {
/*  568 */     SpiTransaction t = this.transactionScopeManager.get();
/*  569 */     if (t != null) {
/*  570 */       t.getEvent().add(tableEvent);
/*      */     } else {
/*  572 */       this.transactionManager.externalModification(tableEvent);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void externalModification(String tableName, boolean inserts, boolean updates, boolean deletes) {
/*  582 */     TransactionEventTable evt = new TransactionEventTable();
/*  583 */     evt.add(tableName, inserts, updates, deletes);
/*      */     
/*  585 */     externalModification(evt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearQueryStatistics() {
/*  592 */     for (BeanDescriptor<?> desc : getBeanDescriptors()) {
/*  593 */       desc.clearQueryStatistics();
/*      */     }
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
/*      */   public <T> T createEntityBean(Class<T> type) {
/*  607 */     BeanDescriptor<T> desc = getBeanDescriptor(type);
/*  608 */     return (T)desc.createEntityBean();
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectInputStream createProxyObjectInputStream(InputStream is) {
/*      */     try {
/*  614 */       return new ProxyBeanObjectInputStream(is, (EbeanServer)this);
/*  615 */     } catch (IOException e) {
/*  616 */       throw new PersistenceException(e);
/*      */     } 
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
/*      */   public <T> T getReference(Class<T> type, Object id) {
/*  631 */     if (id == null) {
/*  632 */       throw new NullPointerException("The id is null");
/*      */     }
/*      */     
/*  635 */     BeanDescriptor<T> desc = getBeanDescriptor(type);
/*      */     
/*  637 */     id = desc.convertId(id);
/*      */     
/*  639 */     Object ref = null;
/*  640 */     PersistenceContext ctx = null;
/*      */     
/*  642 */     SpiTransaction t = this.transactionScopeManager.get();
/*  643 */     if (t != null) {
/*      */       
/*  645 */       ctx = t.getPersistenceContext();
/*  646 */       ref = ctx.get(type, id);
/*      */     } 
/*      */     
/*  649 */     if (ref == null) {
/*  650 */       InheritInfo inheritInfo = desc.getInheritInfo();
/*  651 */       if (inheritInfo != null) {
/*      */         String idNames;
/*      */ 
/*      */         
/*  655 */         BeanProperty[] idProps = desc.propertiesId();
/*      */         
/*  657 */         switch (idProps.length) {
/*      */           case 0:
/*  659 */             throw new PersistenceException("No ID properties for this type? " + desc);
/*      */           case 1:
/*  661 */             idNames = idProps[0].getName();
/*      */             break;
/*      */           default:
/*  664 */             idNames = Arrays.toString((Object[])idProps);
/*  665 */             idNames = idNames.substring(1, idNames.length() - 1);
/*      */             break;
/*      */         } 
/*      */ 
/*      */         
/*  670 */         Query<T> query = createQuery(type);
/*  671 */         query.select(idNames).setId(id);
/*      */         
/*  673 */         ref = query.findUnique();
/*      */       }
/*      */       else {
/*      */         
/*  677 */         ref = desc.createReference(this.vanillaRefMode, null, id, null);
/*      */       } 
/*      */       
/*  680 */       if (ctx != null && ref instanceof EntityBean)
/*      */       {
/*  682 */         ctx.put(id, ref);
/*      */       }
/*      */     } 
/*  685 */     return (T)ref;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Transaction createTransaction() {
/*  694 */     return (Transaction)this.transactionManager.createTransaction(true, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Transaction createTransaction(TxIsolation isolation) {
/*  705 */     return (Transaction)this.transactionManager.createTransaction(true, isolation.getLevel());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void logComment(String msg) {
/*  712 */     SpiTransaction spiTransaction = this.transactionScopeManager.get();
/*  713 */     if (spiTransaction != null) {
/*  714 */       spiTransaction.log(msg);
/*      */     }
/*      */   }
/*      */   
/*      */   public <T> T execute(TxCallable<T> c) {
/*  719 */     return execute((TxScope)null, c);
/*      */   }
/*      */   
/*      */   public <T> T execute(TxScope scope, TxCallable<T> c) {
/*  723 */     ScopeTrans scopeTrans = createScopeTrans(scope);
/*      */     try {
/*  725 */       return (T)c.call();
/*      */     }
/*  727 */     catch (Error e) {
/*  728 */       throw scopeTrans.caughtError(e);
/*      */     }
/*  730 */     catch (RuntimeException e) {
/*  731 */       throw (RuntimeException)scopeTrans.caughtThrowable(e);
/*      */     } finally {
/*      */       
/*  734 */       scopeTrans.onFinally();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void execute(TxRunnable r) {
/*  739 */     execute((TxScope)null, r);
/*      */   }
/*      */   
/*      */   public void execute(TxScope scope, TxRunnable r) {
/*  743 */     ScopeTrans scopeTrans = createScopeTrans(scope);
/*      */     try {
/*  745 */       r.run();
/*      */     }
/*  747 */     catch (Error e) {
/*  748 */       throw scopeTrans.caughtError(e);
/*      */     }
/*  750 */     catch (RuntimeException e) {
/*  751 */       throw (RuntimeException)scopeTrans.caughtThrowable(e);
/*      */     } finally {
/*      */       
/*  754 */       scopeTrans.onFinally();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean createNewTransaction(SpiTransaction t, TxScope scope) {
/*  766 */     TxType type = scope.getType();
/*  767 */     switch (type) {
/*      */       case SQL:
/*  769 */         return (t == null);
/*      */       
/*      */       case LDAP:
/*  772 */         return true;
/*      */       
/*      */       case null:
/*  775 */         if (t == null) {
/*  776 */           throw new PersistenceException("Transaction missing when MANDATORY");
/*      */         }
/*  778 */         return true;
/*      */       
/*      */       case null:
/*  781 */         if (t != null) {
/*  782 */           throw new PersistenceException("Transaction exists for Transactional NEVER");
/*      */         }
/*  784 */         return false;
/*      */       
/*      */       case null:
/*  787 */         return false;
/*      */       
/*      */       case null:
/*  790 */         throw new RuntimeException("NOT_SUPPORTED should already be handled?");
/*      */     } 
/*      */     
/*  793 */     throw new RuntimeException("Should never get here?");
/*      */   }
/*      */ 
/*      */   
/*      */   public ScopeTrans createScopeTrans(TxScope txScope) {
/*      */     boolean newTransaction;
/*  799 */     if (txScope == null)
/*      */     {
/*  801 */       txScope = new TxScope();
/*      */     }
/*      */     
/*  804 */     SpiTransaction suspended = null;
/*      */ 
/*      */     
/*  807 */     SpiTransaction t = this.transactionScopeManager.get();
/*      */ 
/*      */     
/*  810 */     if (txScope.getType().equals(TxType.NOT_SUPPORTED)) {
/*      */ 
/*      */       
/*  813 */       newTransaction = false;
/*  814 */       suspended = t;
/*  815 */       t = null;
/*      */     }
/*      */     else {
/*      */       
/*  819 */       newTransaction = createNewTransaction(t, txScope);
/*      */       
/*  821 */       if (newTransaction) {
/*      */         
/*  823 */         suspended = t;
/*      */ 
/*      */         
/*  826 */         int isoLevel = -1;
/*  827 */         TxIsolation isolation = txScope.getIsolation();
/*  828 */         if (isolation != null) {
/*  829 */           isoLevel = isolation.getLevel();
/*      */         }
/*  831 */         t = this.transactionManager.createTransaction(true, isoLevel);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  837 */     this.transactionScopeManager.replace(t);
/*      */     
/*  839 */     return new ScopeTrans(this.rollbackOnChecked, newTransaction, t, txScope, suspended, (SpiTransactionScopeManager)this.transactionScopeManager);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SpiTransaction getCurrentServerTransaction() {
/*  846 */     return this.transactionScopeManager.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Transaction beginTransaction() {
/*  857 */     SpiTransaction t = this.transactionManager.createTransaction(true, -1);
/*  858 */     this.transactionScopeManager.set(t);
/*  859 */     return (Transaction)t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Transaction beginTransaction(TxIsolation isolation) {
/*  870 */     SpiTransaction t = this.transactionManager.createTransaction(true, isolation.getLevel());
/*  871 */     this.transactionScopeManager.set(t);
/*  872 */     return (Transaction)t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Transaction currentTransaction() {
/*  880 */     return (Transaction)this.transactionScopeManager.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void commitTransaction() {
/*  887 */     this.transactionScopeManager.commit();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rollbackTransaction() {
/*  894 */     this.transactionScopeManager.rollback();
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
/*      */   public void endTransaction() {
/*  926 */     this.transactionScopeManager.end();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object nextId(Class<?> beanType) {
/*  937 */     BeanDescriptor<?> desc = getBeanDescriptor(beanType);
/*  938 */     return desc.nextId(null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> void sort(List<T> list, String sortByClause) {
/*  944 */     if (list == null) {
/*  945 */       throw new NullPointerException("list is null");
/*      */     }
/*  947 */     if (sortByClause == null) {
/*  948 */       throw new NullPointerException("sortByClause is null");
/*      */     }
/*  950 */     if (list.size() == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  955 */     Class<T> beanType = (Class)list.get(0).getClass();
/*  956 */     BeanDescriptor<T> beanDescriptor = getBeanDescriptor(beanType);
/*  957 */     if (beanDescriptor == null) {
/*  958 */       String m = "BeanDescriptor not found, is [" + beanType + "] an entity bean?";
/*  959 */       throw new PersistenceException(m);
/*      */     } 
/*  961 */     beanDescriptor.sort(list, sortByClause);
/*      */   }
/*      */   
/*      */   public <T> Query<T> createQuery(Class<T> beanType) throws PersistenceException {
/*  965 */     return createQuery(beanType, null);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> Query<T> createNamedQuery(Class<T> beanType, String namedQuery) throws PersistenceException {
/*  970 */     BeanDescriptor<?> desc = getBeanDescriptor(beanType);
/*  971 */     if (desc == null) {
/*  972 */       throw new PersistenceException("Is " + beanType.getName() + " an Entity Bean? BeanDescriptor not found?");
/*      */     }
/*  974 */     DeployNamedQuery deployQuery = desc.getNamedQuery(namedQuery);
/*  975 */     if (deployQuery == null) {
/*  976 */       throw new PersistenceException("named query " + namedQuery + " was not found for " + desc.getFullName());
/*      */     }
/*      */ 
/*      */     
/*  980 */     return (Query<T>)new DefaultOrmQuery(beanType, (EbeanServer)this, this.expressionFactory, deployQuery);
/*      */   }
/*      */   
/*      */   public <T> Filter<T> filter(Class<T> beanType) {
/*  984 */     BeanDescriptor<T> desc = getBeanDescriptor(beanType);
/*  985 */     if (desc == null) {
/*  986 */       String m = beanType.getName() + " is NOT an Entity Bean registered with this server?";
/*  987 */       throw new PersistenceException(m);
/*      */     } 
/*  989 */     return (Filter<T>)new ElFilter(desc);
/*      */   }
/*      */   
/*      */   public <T> CsvReader<T> createCsvReader(Class<T> beanType) {
/*  993 */     BeanDescriptor<T> descriptor = getBeanDescriptor(beanType);
/*  994 */     if (descriptor == null) {
/*  995 */       throw new NullPointerException("BeanDescriptor for " + beanType.getName() + " not found");
/*      */     }
/*  997 */     return (CsvReader<T>)new TCsvReader((EbeanServer)this, descriptor);
/*      */   }
/*      */   
/*      */   public <T> Query<T> find(Class<T> beanType) {
/* 1001 */     return createQuery(beanType);
/*      */   }
/*      */   public <T> Query<T> createQuery(Class<T> beanType, String query) {
/*      */     DeployNamedQuery defaultSqlSelect;
/* 1005 */     BeanDescriptor<?> desc = getBeanDescriptor(beanType);
/* 1006 */     if (desc == null) {
/* 1007 */       String m = beanType.getName() + " is NOT an Entity Bean registered with this server?";
/* 1008 */       throw new PersistenceException(m);
/*      */     } 
/* 1010 */     switch (desc.getEntityType()) {
/*      */       case SQL:
/* 1012 */         if (query != null) {
/* 1013 */           throw new PersistenceException("You must used Named queries for this Entity " + desc.getFullName());
/*      */         }
/*      */         
/* 1016 */         defaultSqlSelect = desc.getNamedQuery("default");
/* 1017 */         return (Query<T>)new DefaultOrmQuery(beanType, (EbeanServer)this, this.expressionFactory, defaultSqlSelect);
/*      */       
/*      */       case LDAP:
/* 1020 */         return (Query<T>)new DefaultLdapOrmQuery(beanType, (EbeanServer)this, this.ldapExpressionFactory, query);
/*      */     } 
/*      */     
/* 1023 */     return (Query<T>)new DefaultOrmQuery(beanType, (EbeanServer)this, this.expressionFactory, query);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> Update<T> createNamedUpdate(Class<T> beanType, String namedUpdate) {
/* 1028 */     BeanDescriptor<?> desc = getBeanDescriptor(beanType);
/* 1029 */     if (desc == null) {
/* 1030 */       String m = beanType.getName() + " is NOT an Entity Bean registered with this server?";
/* 1031 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/* 1034 */     DeployNamedUpdate deployUpdate = desc.getNamedUpdate(namedUpdate);
/* 1035 */     if (deployUpdate == null) {
/* 1036 */       throw new PersistenceException("named update " + namedUpdate + " was not found for " + desc.getFullName());
/*      */     }
/*      */     
/* 1039 */     return (Update<T>)new DefaultOrmUpdate(beanType, (EbeanServer)this, desc.getBaseTable(), deployUpdate);
/*      */   }
/*      */   
/*      */   public <T> Update<T> createUpdate(Class<T> beanType, String ormUpdate) {
/* 1043 */     BeanDescriptor<?> desc = getBeanDescriptor(beanType);
/* 1044 */     if (desc == null) {
/* 1045 */       String m = beanType.getName() + " is NOT an Entity Bean registered with this server?";
/* 1046 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/* 1049 */     return (Update<T>)new DefaultOrmUpdate(beanType, (EbeanServer)this, desc.getBaseTable(), ormUpdate);
/*      */   }
/*      */   
/*      */   public SqlQuery createSqlQuery(String sql) {
/* 1053 */     return (SqlQuery)new DefaultRelationalQuery((EbeanServer)this, sql);
/*      */   }
/*      */   
/*      */   public SqlQuery createNamedSqlQuery(String namedQuery) {
/* 1057 */     DNativeQuery nq = this.beanDescriptorManager.getNativeQuery(namedQuery);
/* 1058 */     if (nq == null) {
/* 1059 */       throw new PersistenceException("SqlQuery " + namedQuery + " not found.");
/*      */     }
/* 1061 */     return (SqlQuery)new DefaultRelationalQuery((EbeanServer)this, nq.getQuery());
/*      */   }
/*      */   
/*      */   public SqlUpdate createSqlUpdate(String sql) {
/* 1065 */     return (SqlUpdate)new DefaultSqlUpdate((EbeanServer)this, sql);
/*      */   }
/*      */   
/*      */   public CallableSql createCallableSql(String sql) {
/* 1069 */     return (CallableSql)new DefaultCallableSql((EbeanServer)this, sql);
/*      */   }
/*      */   
/*      */   public SqlUpdate createNamedSqlUpdate(String namedQuery) {
/* 1073 */     DNativeQuery nq = this.beanDescriptorManager.getNativeQuery(namedQuery);
/* 1074 */     if (nq == null) {
/* 1075 */       throw new PersistenceException("SqlUpdate " + namedQuery + " not found.");
/*      */     }
/* 1077 */     return (SqlUpdate)new DefaultSqlUpdate((EbeanServer)this, nq.getQuery());
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> T find(Class<T> beanType, Object uid) {
/* 1082 */     return find(beanType, uid, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T find(Class<T> beanType, Object id, Transaction t) {
/* 1090 */     if (id == null) {
/* 1091 */       throw new NullPointerException("The id is null");
/*      */     }
/*      */     
/* 1094 */     Query<T> query = createQuery(beanType).setId(id);
/* 1095 */     return findId(query, t);
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> SpiOrmQueryRequest<T> createQueryRequest(SpiQuery.Type type, Query<T> query, Transaction t) {
/* 1100 */     SpiQuery<T> spiQuery = (SpiQuery<T>)query;
/* 1101 */     spiQuery.setType(type);
/*      */     
/* 1103 */     BeanDescriptor<T> desc = this.beanDescriptorManager.getBeanDescriptor(spiQuery.getBeanType());
/* 1104 */     spiQuery.setBeanDescriptor(desc);
/*      */     
/* 1106 */     return createQueryRequest(desc, spiQuery, t);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> SpiOrmQueryRequest<T> createQueryRequest(BeanDescriptor<T> desc, SpiQuery<T> query, Transaction t) {
/* 1111 */     if (desc.isLdapEntityType()) {
/* 1112 */       return (SpiOrmQueryRequest<T>)new LdapOrmQueryRequest(query, desc, this.ldapQueryEngine);
/*      */     }
/*      */     
/* 1115 */     if (desc.isAutoFetchTunable() && !query.isSqlSelect())
/*      */     {
/* 1117 */       if (!this.autoFetchManager.tuneQuery(query))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1122 */         query.setDefaultSelectClause();
/*      */       }
/*      */     }
/*      */     
/* 1126 */     if (query.selectAllForLazyLoadProperty())
/*      */     {
/*      */       
/* 1129 */       if (logger.isLoggable(Level.FINE)) {
/* 1130 */         logger.log(Level.FINE, "Using selectAllForLazyLoadProperty");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1136 */     if (query.getParentNode() == null) {
/* 1137 */       CallStack callStack = createCallStack();
/* 1138 */       query.setOrigin(callStack);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1144 */     if (query.initManyWhereJoins())
/*      */     {
/* 1146 */       query.setDistinct(true);
/*      */     }
/*      */     
/* 1149 */     boolean allowOneManyFetch = true;
/* 1150 */     if (SpiQuery.Mode.LAZYLOAD_MANY.equals(query.getMode())) {
/* 1151 */       allowOneManyFetch = false;
/*      */     }
/* 1153 */     else if (query.hasMaxRowsOrFirstRow() && !query.isRawSql() && !query.isSqlSelect() && query.getBackgroundFetchAfter() == 0) {
/*      */ 
/*      */       
/* 1156 */       allowOneManyFetch = false;
/*      */     } 
/*      */     
/* 1159 */     query.convertManyFetchJoinsToQueryJoins(allowOneManyFetch, this.queryBatchSize);
/*      */     
/* 1161 */     SpiTransaction serverTrans = (SpiTransaction)t;
/* 1162 */     OrmQueryRequest<T> request = new OrmQueryRequest<T>(this, this.queryEngine, query, desc, serverTrans);
/*      */     
/* 1164 */     BeanQueryAdapter queryAdapter = desc.getQueryAdapter();
/* 1165 */     if (queryAdapter != null)
/*      */     {
/*      */       
/* 1168 */       queryAdapter.preQuery(request);
/*      */     }
/*      */ 
/*      */     
/* 1172 */     request.calculateQueryPlanHash();
/*      */     
/* 1174 */     return request;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> T findIdCheckPersistenceContextAndCache(Transaction transaction, BeanDescriptor<T> beanDescriptor, SpiQuery<T> query) {
/* 1183 */     SpiTransaction t = (SpiTransaction)transaction;
/* 1184 */     if (t == null) {
/* 1185 */       t = getCurrentServerTransaction();
/*      */     }
/* 1187 */     PersistenceContext context = null;
/* 1188 */     if (t != null) {
/*      */       
/* 1190 */       context = t.getPersistenceContext();
/* 1191 */       if (context != null) {
/* 1192 */         Object o = context.get(beanDescriptor.getBeanType(), query.getId());
/* 1193 */         if (o != null) {
/* 1194 */           return (T)o;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1199 */     if (!beanDescriptor.calculateUseCache(query.isUseBeanCache()))
/*      */     {
/* 1201 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1205 */     boolean vanilla = query.isVanillaMode(this.vanillaMode);
/* 1206 */     Object cachedBean = beanDescriptor.cacheGetBean(query.getId(), vanilla, query.isReadOnly());
/* 1207 */     if (cachedBean != null) {
/* 1208 */       DefaultPersistenceContext defaultPersistenceContext; if (context == null) {
/* 1209 */         defaultPersistenceContext = new DefaultPersistenceContext();
/*      */       }
/*      */       
/* 1212 */       defaultPersistenceContext.put(query.getId(), cachedBean);
/* 1213 */       if (!vanilla) {
/*      */         
/* 1215 */         DLoadContext loadContext = new DLoadContext(this, beanDescriptor, query.isReadOnly(), false, null, false);
/* 1216 */         loadContext.setPersistenceContext((PersistenceContext)defaultPersistenceContext);
/*      */         
/* 1218 */         EntityBeanIntercept ebi = ((EntityBean)cachedBean)._ebean_getIntercept();
/* 1219 */         ebi.setPersistenceContext((PersistenceContext)defaultPersistenceContext);
/* 1220 */         loadContext.register(null, ebi);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1225 */     return (T)cachedBean;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> T findId(Query<T> query, Transaction t) {
/* 1231 */     SpiQuery<T> spiQuery = (SpiQuery<T>)query;
/* 1232 */     spiQuery.setType(SpiQuery.Type.BEAN);
/*      */     
/* 1234 */     BeanDescriptor<T> desc = this.beanDescriptorManager.getBeanDescriptor(spiQuery.getBeanType());
/* 1235 */     spiQuery.setBeanDescriptor(desc);
/*      */     
/* 1237 */     if (SpiQuery.Mode.NORMAL.equals(spiQuery.getMode()) && !spiQuery.isLoadBeanCache()) {
/*      */ 
/*      */       
/* 1240 */       T bean = findIdCheckPersistenceContextAndCache(t, desc, spiQuery);
/* 1241 */       if (bean != null) {
/* 1242 */         return bean;
/*      */       }
/*      */     } 
/*      */     
/* 1246 */     SpiOrmQueryRequest<T> request = createQueryRequest(desc, spiQuery, t);
/*      */     
/*      */     try {
/* 1249 */       request.initTransIfRequired();
/*      */       
/* 1251 */       T bean = (T)request.findId();
/* 1252 */       request.endTransIfRequired();
/*      */       
/* 1254 */       return bean;
/*      */     }
/* 1256 */     catch (RuntimeException ex) {
/* 1257 */       request.rollbackTransIfRequired();
/* 1258 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T findUnique(Query<T> query, Transaction t) {
/* 1266 */     SpiQuery<T> q = (SpiQuery<T>)query;
/* 1267 */     Object id = q.getId();
/* 1268 */     if (id != null) {
/* 1269 */       return findId(query, t);
/*      */     }
/*      */     
/* 1272 */     BeanDescriptor<T> desc = this.beanDescriptorManager.getBeanDescriptor(q.getBeanType());
/*      */     
/* 1274 */     if (desc.calculateUseNaturalKeyCache(q.isUseBeanCache())) {
/*      */       
/* 1276 */       NaturalKeyBindParam keyBindParam = q.getNaturalKeyBindParam();
/* 1277 */       if (keyBindParam != null && desc.cacheIsNaturalKey(keyBindParam.getName())) {
/* 1278 */         Object id2 = desc.cacheGetNaturalKeyId(keyBindParam.getValue());
/* 1279 */         if (id2 != null) {
/* 1280 */           SpiQuery<T> copy = q.copy();
/* 1281 */           copy.convertWhereNaturalKeyToId(id2);
/* 1282 */           return findId((Query<T>)copy, t);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1288 */     List<T> list = findList(query, t);
/*      */     
/* 1290 */     if (list.size() == 0)
/* 1291 */       return null; 
/* 1292 */     if (list.size() > 1) {
/* 1293 */       String m = "Unique expecting 0 or 1 rows but got [" + list.size() + "]";
/* 1294 */       throw new PersistenceException(m);
/*      */     } 
/* 1296 */     return list.get(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> Set<T> findSet(Query<T> query, Transaction t) {
/* 1303 */     SpiOrmQueryRequest<T> request = createQueryRequest(SpiQuery.Type.SET, query, t);
/*      */     
/* 1305 */     Object<T> result = (Object<T>)request.getFromQueryCache();
/* 1306 */     if (result != null) {
/* 1307 */       return (Set<T>)result;
/*      */     }
/*      */     
/*      */     try {
/* 1311 */       request.initTransIfRequired();
/* 1312 */       Set<T> set = (Set)request.findSet();
/* 1313 */       request.endTransIfRequired();
/*      */       
/* 1315 */       return set;
/*      */     }
/* 1317 */     catch (RuntimeException ex) {
/*      */       
/* 1319 */       request.rollbackTransIfRequired();
/* 1320 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> Map<?, T> findMap(Query<T> query, Transaction t) {
/* 1327 */     SpiOrmQueryRequest<T> request = createQueryRequest(SpiQuery.Type.MAP, query, t);
/*      */     
/* 1329 */     Object<T> result = (Object<T>)request.getFromQueryCache();
/* 1330 */     if (result != null) {
/* 1331 */       return (Map)result;
/*      */     }
/*      */     
/*      */     try {
/* 1335 */       request.initTransIfRequired();
/* 1336 */       Map<?, T> map = (Map)request.findMap();
/* 1337 */       request.endTransIfRequired();
/*      */       
/* 1339 */       return map;
/*      */     }
/* 1341 */     catch (RuntimeException ex) {
/*      */       
/* 1343 */       request.rollbackTransIfRequired();
/* 1344 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> int findRowCount(Query<T> query, Transaction t) {
/* 1350 */     SpiQuery<T> copy = ((SpiQuery)query).copy();
/* 1351 */     return findRowCountWithCopy((Query<T>)copy, t);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> int findRowCountWithCopy(Query<T> query, Transaction t) {
/* 1356 */     SpiOrmQueryRequest<T> request = createQueryRequest(SpiQuery.Type.ROWCOUNT, query, t);
/*      */     try {
/* 1358 */       request.initTransIfRequired();
/* 1359 */       int rowCount = request.findRowCount();
/* 1360 */       request.endTransIfRequired();
/*      */       
/* 1362 */       return rowCount;
/*      */     }
/* 1364 */     catch (RuntimeException ex) {
/* 1365 */       request.rollbackTransIfRequired();
/* 1366 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> List<Object> findIds(Query<T> query, Transaction t) {
/* 1372 */     SpiQuery<T> copy = ((SpiQuery)query).copy();
/*      */     
/* 1374 */     return findIdsWithCopy((Query<T>)copy, t);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> List<Object> findIdsWithCopy(Query<T> query, Transaction t) {
/* 1379 */     SpiOrmQueryRequest<T> request = createQueryRequest(SpiQuery.Type.ID_LIST, query, t);
/*      */     try {
/* 1381 */       request.initTransIfRequired();
/* 1382 */       List<Object> list = request.findIds();
/* 1383 */       request.endTransIfRequired();
/*      */       
/* 1385 */       return list;
/*      */     }
/* 1387 */     catch (RuntimeException ex) {
/* 1388 */       request.rollbackTransIfRequired();
/* 1389 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> FutureRowCount<T> findFutureRowCount(Query<T> q, Transaction t) {
/* 1395 */     SpiQuery<T> copy = ((SpiQuery)q).copy();
/* 1396 */     copy.setFutureFetch(true);
/*      */     
/* 1398 */     Transaction newTxn = createTransaction();
/*      */     
/* 1400 */     CallableQueryRowCount<T> call = new CallableQueryRowCount(this, (Query)copy, newTxn);
/* 1401 */     FutureTask<Integer> futureTask = new FutureTask<Integer>((Callable)call);
/*      */     
/* 1403 */     QueryFutureRowCount<T> queryFuture = new QueryFutureRowCount((Query)copy, futureTask);
/* 1404 */     this.backgroundExecutor.execute(futureTask);
/*      */     
/* 1406 */     return (FutureRowCount<T>)queryFuture;
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> FutureIds<T> findFutureIds(Query<T> query, Transaction t) {
/* 1411 */     SpiQuery<T> copy = ((SpiQuery)query).copy();
/* 1412 */     copy.setFutureFetch(true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1417 */     List<Object> idList = Collections.synchronizedList(new ArrayList());
/* 1418 */     copy.setIdList(idList);
/*      */     
/* 1420 */     Transaction newTxn = createTransaction();
/*      */     
/* 1422 */     CallableQueryIds<T> call = new CallableQueryIds(this, (Query)copy, newTxn);
/* 1423 */     FutureTask<List<Object>> futureTask = new FutureTask<List<Object>>((Callable)call);
/*      */     
/* 1425 */     QueryFutureIds<T> queryFuture = new QueryFutureIds(copy, futureTask);
/*      */     
/* 1427 */     this.backgroundExecutor.execute(futureTask);
/*      */     
/* 1429 */     return (FutureIds<T>)queryFuture;
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> FutureList<T> findFutureList(Query<T> query, Transaction t) {
/* 1434 */     SpiQuery<T> spiQuery = (SpiQuery<T>)query;
/* 1435 */     spiQuery.setFutureFetch(true);
/*      */     
/* 1437 */     if (spiQuery.getPersistenceContext() == null) {
/* 1438 */       if (t != null) {
/* 1439 */         spiQuery.setPersistenceContext(((SpiTransaction)t).getPersistenceContext());
/*      */       } else {
/* 1441 */         SpiTransaction st = getCurrentServerTransaction();
/* 1442 */         if (st != null) {
/* 1443 */           spiQuery.setPersistenceContext(st.getPersistenceContext());
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1448 */     Transaction newTxn = createTransaction();
/* 1449 */     CallableQueryList<T> call = new CallableQueryList(this, query, newTxn);
/*      */     
/* 1451 */     FutureTask<List<T>> futureTask = new FutureTask<List<T>>((Callable)call);
/*      */     
/* 1453 */     this.backgroundExecutor.execute(futureTask);
/*      */     
/* 1455 */     return (FutureList<T>)new QueryFutureList(query, futureTask);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> PagingList<T> findPagingList(Query<T> query, Transaction t, int pageSize) {
/* 1460 */     SpiQuery<T> spiQuery = (SpiQuery<T>)query;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1465 */     PersistenceContext pc = spiQuery.getPersistenceContext();
/* 1466 */     if (pc == null) {
/* 1467 */       DefaultPersistenceContext defaultPersistenceContext; SpiTransaction currentTransaction = getCurrentServerTransaction();
/* 1468 */       if (currentTransaction != null) {
/* 1469 */         pc = currentTransaction.getPersistenceContext();
/*      */       }
/* 1471 */       if (pc == null) {
/* 1472 */         defaultPersistenceContext = new DefaultPersistenceContext();
/*      */       }
/* 1474 */       spiQuery.setPersistenceContext((PersistenceContext)defaultPersistenceContext);
/*      */     } 
/*      */     
/* 1477 */     return (PagingList<T>)new LimitOffsetPagingQuery((EbeanServer)this, spiQuery, pageSize);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> void findVisit(Query<T> query, QueryResultVisitor<T> visitor, Transaction t) {
/* 1482 */     SpiOrmQueryRequest<T> request = createQueryRequest(SpiQuery.Type.LIST, query, t);
/*      */     
/*      */     try {
/* 1485 */       request.initTransIfRequired();
/* 1486 */       request.findVisit(visitor);
/*      */     }
/* 1488 */     catch (RuntimeException ex) {
/* 1489 */       request.rollbackTransIfRequired();
/* 1490 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> QueryIterator<T> findIterate(Query<T> query, Transaction t) {
/* 1496 */     SpiOrmQueryRequest<T> request = createQueryRequest(SpiQuery.Type.LIST, query, t);
/*      */     
/*      */     try {
/* 1499 */       request.initTransIfRequired();
/* 1500 */       return request.findIterate();
/*      */     
/*      */     }
/* 1503 */     catch (RuntimeException ex) {
/* 1504 */       request.rollbackTransIfRequired();
/* 1505 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> List<T> findList(Query<T> query, Transaction t) {
/* 1512 */     SpiOrmQueryRequest<T> request = createQueryRequest(SpiQuery.Type.LIST, query, t);
/*      */     
/* 1514 */     Object<T> result = (Object<T>)request.getFromQueryCache();
/* 1515 */     if (result != null) {
/* 1516 */       return (List<T>)result;
/*      */     }
/*      */     
/*      */     try {
/* 1520 */       request.initTransIfRequired();
/* 1521 */       List<T> list = request.findList();
/* 1522 */       request.endTransIfRequired();
/*      */       
/* 1524 */       return list;
/*      */     }
/* 1526 */     catch (RuntimeException ex) {
/* 1527 */       request.rollbackTransIfRequired();
/* 1528 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SqlRow findUnique(SqlQuery query, Transaction t) {
/* 1536 */     List<SqlRow> list = findList(query, t);
/*      */     
/* 1538 */     if (list.size() == 0) {
/* 1539 */       return null;
/*      */     }
/* 1541 */     if (list.size() > 1) {
/* 1542 */       String m = "Unique expecting 0 or 1 rows but got [" + list.size() + "]";
/* 1543 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/* 1546 */     return list.get(0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public SqlFutureList findFutureList(SqlQuery query, Transaction t) {
/* 1552 */     SpiSqlQuery spiQuery = (SpiSqlQuery)query;
/* 1553 */     spiQuery.setFutureFetch(true);
/*      */     
/* 1555 */     Transaction newTxn = createTransaction();
/* 1556 */     CallableSqlQueryList call = new CallableSqlQueryList((EbeanServer)this, query, newTxn);
/*      */     
/* 1558 */     FutureTask<List<SqlRow>> futureTask = new FutureTask<List<SqlRow>>((Callable<List<SqlRow>>)call);
/*      */     
/* 1560 */     this.backgroundExecutor.execute(futureTask);
/*      */     
/* 1562 */     return (SqlFutureList)new SqlQueryFutureList(query, futureTask);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<SqlRow> findList(SqlQuery query, Transaction t) {
/* 1567 */     RelationalQueryRequest request = new RelationalQueryRequest(this, this.relationalQueryEngine, query, t);
/*      */     
/*      */     try {
/* 1570 */       request.initTransIfRequired();
/* 1571 */       List<SqlRow> list = request.findList();
/* 1572 */       request.endTransIfRequired();
/*      */       
/* 1574 */       return list;
/*      */     }
/* 1576 */     catch (RuntimeException ex) {
/* 1577 */       request.rollbackTransIfRequired();
/* 1578 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Set<SqlRow> findSet(SqlQuery query, Transaction t) {
/* 1584 */     RelationalQueryRequest request = new RelationalQueryRequest(this, this.relationalQueryEngine, query, t);
/*      */     
/*      */     try {
/* 1587 */       request.initTransIfRequired();
/* 1588 */       Set<SqlRow> set = request.findSet();
/* 1589 */       request.endTransIfRequired();
/*      */       
/* 1591 */       return set;
/*      */     }
/* 1593 */     catch (RuntimeException ex) {
/* 1594 */       request.rollbackTransIfRequired();
/* 1595 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Map<?, SqlRow> findMap(SqlQuery query, Transaction t) {
/* 1601 */     RelationalQueryRequest request = new RelationalQueryRequest(this, this.relationalQueryEngine, query, t);
/*      */     try {
/* 1603 */       request.initTransIfRequired();
/* 1604 */       Map<?, SqlRow> map = request.findMap();
/* 1605 */       request.endTransIfRequired();
/*      */       
/* 1607 */       return map;
/*      */     }
/* 1609 */     catch (RuntimeException ex) {
/* 1610 */       request.rollbackTransIfRequired();
/* 1611 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(Object bean) {
/* 1619 */     save(bean, (Transaction)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(Object bean, Transaction t) {
/* 1626 */     if (bean == null) {
/* 1627 */       throw new NullPointerException(Message.msg("bean.isnull"));
/*      */     }
/* 1629 */     this.persister.save(bean, t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update(Object bean) {
/* 1636 */     update(bean, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update(Object bean, Set<String> updateProps) {
/* 1644 */     update(bean, updateProps, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update(Object bean, Transaction t) {
/* 1651 */     update(bean, null, t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update(Object bean, Set<String> updateProps, Transaction t) {
/* 1659 */     update(bean, updateProps, t, this.defaultDeleteMissingChildren, this.defaultUpdateNullProperties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update(Object bean, Set<String> updateProps, Transaction t, boolean deleteMissingChildren, boolean updateNullProperties) {
/* 1667 */     if (bean == null) {
/* 1668 */       throw new NullPointerException(Message.msg("bean.isnull"));
/*      */     }
/* 1670 */     this.persister.forceUpdate(bean, updateProps, t, deleteMissingChildren, updateNullProperties);
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
/*      */   public void insert(Object bean) {
/* 1683 */     insert(bean, null);
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
/*      */   public void insert(Object bean, Transaction t) {
/* 1696 */     if (bean == null) {
/* 1697 */       throw new NullPointerException(Message.msg("bean.isnull"));
/*      */     }
/* 1699 */     this.persister.forceInsert(bean, t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int deleteManyToManyAssociations(Object ownerBean, String propertyName) {
/* 1710 */     return deleteManyToManyAssociations(ownerBean, propertyName, null);
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
/*      */   public int deleteManyToManyAssociations(Object ownerBean, String propertyName, Transaction t) {
/* 1722 */     TransWrapper wrap = initTransIfRequired(t);
/*      */     try {
/* 1724 */       SpiTransaction trans = wrap.transaction;
/* 1725 */       int rc = this.persister.deleteManyToManyAssociations(ownerBean, propertyName, (Transaction)trans);
/* 1726 */       wrap.commitIfCreated();
/* 1727 */       return rc;
/*      */     }
/* 1729 */     catch (RuntimeException e) {
/* 1730 */       wrap.rollbackIfCreated();
/* 1731 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveManyToManyAssociations(Object ownerBean, String propertyName) {
/* 1740 */     saveManyToManyAssociations(ownerBean, propertyName, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveManyToManyAssociations(Object ownerBean, String propertyName, Transaction t) {
/* 1749 */     TransWrapper wrap = initTransIfRequired(t);
/*      */     try {
/* 1751 */       SpiTransaction trans = wrap.transaction;
/*      */       
/* 1753 */       this.persister.saveManyToManyAssociations(ownerBean, propertyName, (Transaction)trans);
/*      */       
/* 1755 */       wrap.commitIfCreated();
/*      */     }
/* 1757 */     catch (RuntimeException e) {
/* 1758 */       wrap.rollbackIfCreated();
/* 1759 */       throw e;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void saveAssociation(Object ownerBean, String propertyName) {
/* 1764 */     saveAssociation(ownerBean, propertyName, null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void saveAssociation(Object ownerBean, String propertyName, Transaction t) {
/* 1769 */     if (ownerBean instanceof EntityBean) {
/* 1770 */       Set<String> loadedProps = ((EntityBean)ownerBean)._ebean_getIntercept().getLoadedProps();
/* 1771 */       if (loadedProps != null && !loadedProps.contains(propertyName)) {
/*      */ 
/*      */         
/* 1774 */         logger.fine("Skip saveAssociation as property " + propertyName + " is not loaded");
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 1779 */     TransWrapper wrap = initTransIfRequired(t);
/*      */     try {
/* 1781 */       SpiTransaction trans = wrap.transaction;
/*      */       
/* 1783 */       this.persister.saveAssociation(ownerBean, propertyName, (Transaction)trans);
/*      */       
/* 1785 */       wrap.commitIfCreated();
/*      */     }
/* 1787 */     catch (RuntimeException e) {
/* 1788 */       wrap.rollbackIfCreated();
/* 1789 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int save(Iterator<?> it) {
/* 1798 */     return save(it, (Transaction)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int save(Collection<?> c) {
/* 1806 */     return save(c.iterator(), (Transaction)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int save(Iterator<?> it, Transaction t) {
/* 1814 */     TransWrapper wrap = initTransIfRequired(t);
/*      */     try {
/* 1816 */       SpiTransaction trans = wrap.transaction;
/* 1817 */       int saveCount = 0;
/* 1818 */       while (it.hasNext()) {
/* 1819 */         Object bean = it.next();
/* 1820 */         this.persister.save(bean, (Transaction)trans);
/* 1821 */         saveCount++;
/*      */       } 
/*      */       
/* 1824 */       wrap.commitIfCreated();
/*      */       
/* 1826 */       return saveCount;
/*      */     }
/* 1828 */     catch (RuntimeException e) {
/* 1829 */       wrap.rollbackIfCreated();
/* 1830 */       throw e;
/*      */     } 
/*      */   }
/*      */   
/*      */   public int delete(Class<?> beanType, Object id) {
/* 1835 */     return delete(beanType, id, (Transaction)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int delete(Class<?> beanType, Object id, Transaction t) {
/* 1840 */     TransWrapper wrap = initTransIfRequired(t);
/*      */     try {
/* 1842 */       SpiTransaction trans = wrap.transaction;
/* 1843 */       int rowCount = this.persister.delete(beanType, id, (Transaction)trans);
/* 1844 */       wrap.commitIfCreated();
/*      */       
/* 1846 */       return rowCount;
/*      */     }
/* 1848 */     catch (RuntimeException e) {
/* 1849 */       wrap.rollbackIfCreated();
/* 1850 */       throw e;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void delete(Class<?> beanType, Collection<?> ids) {
/* 1855 */     delete(beanType, ids, (Transaction)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void delete(Class<?> beanType, Collection<?> ids, Transaction t) {
/* 1860 */     TransWrapper wrap = initTransIfRequired(t);
/*      */     try {
/* 1862 */       SpiTransaction trans = wrap.transaction;
/* 1863 */       this.persister.deleteMany(beanType, ids, (Transaction)trans);
/* 1864 */       wrap.commitIfCreated();
/*      */     }
/* 1866 */     catch (RuntimeException e) {
/* 1867 */       wrap.rollbackIfCreated();
/* 1868 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void delete(Object bean) {
/* 1876 */     delete(bean, (Transaction)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void delete(Object bean, Transaction t) {
/* 1883 */     if (bean == null) {
/* 1884 */       throw new NullPointerException(Message.msg("bean.isnull"));
/*      */     }
/* 1886 */     this.persister.delete(bean, t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int delete(Iterator<?> it) {
/* 1893 */     return delete(it, (Transaction)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int delete(Collection<?> c) {
/* 1900 */     return delete(c.iterator(), (Transaction)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int delete(Iterator<?> it, Transaction t) {
/* 1908 */     TransWrapper wrap = initTransIfRequired(t);
/*      */     
/*      */     try {
/* 1911 */       SpiTransaction trans = wrap.transaction;
/* 1912 */       int deleteCount = 0;
/* 1913 */       while (it.hasNext()) {
/* 1914 */         Object bean = it.next();
/* 1915 */         this.persister.delete(bean, (Transaction)trans);
/* 1916 */         deleteCount++;
/*      */       } 
/*      */       
/* 1919 */       wrap.commitIfCreated();
/*      */       
/* 1921 */       return deleteCount;
/*      */     }
/* 1923 */     catch (RuntimeException e) {
/* 1924 */       wrap.rollbackIfCreated();
/* 1925 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int execute(CallableSql callSql, Transaction t) {
/* 1933 */     return this.persister.executeCallable(callSql, t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int execute(CallableSql callSql) {
/* 1940 */     return execute(callSql, (Transaction)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int execute(SqlUpdate updSql, Transaction t) {
/* 1947 */     return this.persister.executeSqlUpdate(updSql, t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int execute(SqlUpdate updSql) {
/* 1954 */     return execute(updSql, (Transaction)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int execute(Update<?> update, Transaction t) {
/* 1961 */     return this.persister.executeOrmUpdate(update, t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int execute(Update<?> update) {
/* 1968 */     return execute(update, (Transaction)null);
/*      */   }
/*      */   
/*      */   public <T> BeanManager<T> getBeanManager(Class<T> beanClass) {
/* 1972 */     return this.beanDescriptorManager.getBeanManager(beanClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<BeanDescriptor<?>> getBeanDescriptors() {
/* 1979 */     return this.beanDescriptorManager.getBeanDescriptorList();
/*      */   }
/*      */   
/*      */   public void register(BeanPersistController c) {
/* 1983 */     List<BeanDescriptor<?>> list = this.beanDescriptorManager.getBeanDescriptorList();
/* 1984 */     for (int i = 0; i < list.size(); i++) {
/* 1985 */       ((BeanDescriptor)list.get(i)).register(c);
/*      */     }
/*      */   }
/*      */   
/*      */   public void deregister(BeanPersistController c) {
/* 1990 */     List<BeanDescriptor<?>> list = this.beanDescriptorManager.getBeanDescriptorList();
/* 1991 */     for (int i = 0; i < list.size(); i++) {
/* 1992 */       ((BeanDescriptor)list.get(i)).deregister(c);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSupportedType(Type genericType) {
/* 1998 */     ParamTypeHelper.TypeInfo typeInfo = ParamTypeHelper.getTypeInfo(genericType);
/* 1999 */     if (typeInfo == null) {
/* 2000 */       return false;
/*      */     }
/* 2002 */     Class<?> beanType = typeInfo.getBeanType();
/* 2003 */     if (JsonElement.class.isAssignableFrom(beanType)) {
/* 2004 */       return true;
/*      */     }
/* 2006 */     return (getBeanDescriptor(typeInfo.getBeanType()) != null);
/*      */   }
/*      */   
/*      */   public Object getBeanId(Object bean) {
/* 2010 */     BeanDescriptor<?> desc = getBeanDescriptor(bean.getClass());
/* 2011 */     if (desc == null) {
/* 2012 */       String m = bean.getClass().getName() + " is NOT an Entity Bean registered with this server?";
/* 2013 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/* 2016 */     return desc.getId(bean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> BeanDescriptor<T> getBeanDescriptor(Class<T> beanClass) {
/* 2023 */     return this.beanDescriptorManager.getBeanDescriptor(beanClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<BeanDescriptor<?>> getBeanDescriptors(String tableName) {
/* 2030 */     return this.beanDescriptorManager.getBeanDescriptors(tableName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanDescriptor<?> getBeanDescriptorById(String descriptorId) {
/* 2037 */     return this.beanDescriptorManager.getBeanDescriptorById(descriptorId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void remoteTransactionEvent(RemoteTransactionEvent event) {
/* 2046 */     this.transactionManager.remoteTransactionEvent(event);
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
/*      */   TransWrapper initTransIfRequired(Transaction t) {
/* 2060 */     if (t != null) {
/* 2061 */       return new TransWrapper((SpiTransaction)t, false);
/*      */     }
/*      */     
/* 2064 */     boolean wasCreated = false;
/* 2065 */     SpiTransaction trans = this.transactionScopeManager.get();
/* 2066 */     if (trans == null) {
/*      */       
/* 2068 */       trans = this.transactionManager.createTransaction(false, -1);
/* 2069 */       wasCreated = true;
/*      */     } 
/* 2071 */     return new TransWrapper(trans, wasCreated);
/*      */   }
/*      */   
/*      */   public SpiTransaction createServerTransaction(boolean isExplicit, int isolationLevel) {
/* 2075 */     return this.transactionManager.createTransaction(isExplicit, isolationLevel);
/*      */   }
/*      */   
/*      */   public SpiTransaction createQueryTransaction() {
/* 2079 */     return this.transactionManager.createQueryTransaction();
/*      */   }
/*      */ 
/*      */   
/* 2083 */   private static final String AVAJE_EBEAN = Ebean.class.getName().substring(0, 15);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CallStack createCallStack() {
/* 2094 */     StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
/*      */ 
/*      */     
/* 2097 */     int startIndex = 5;
/*      */ 
/*      */     
/* 2100 */     for (; startIndex < stackTrace.length && 
/* 2101 */       stackTrace[startIndex].getClassName().startsWith(AVAJE_EBEAN); startIndex++);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2106 */     int stackLength = stackTrace.length - startIndex;
/* 2107 */     if (stackLength > this.maxCallStack)
/*      */     {
/* 2109 */       stackLength = this.maxCallStack;
/*      */     }
/*      */ 
/*      */     
/* 2113 */     StackTraceElement[] finalTrace = new StackTraceElement[stackLength];
/* 2114 */     for (int i = 0; i < stackLength; i++) {
/* 2115 */       finalTrace[i] = stackTrace[i + startIndex];
/*      */     }
/*      */     
/* 2118 */     if (stackLength < 1)
/*      */     {
/* 2120 */       throw new RuntimeException("StackTraceElement size 0?  stack: " + Arrays.toString(stackTrace));
/*      */     }
/*      */     
/* 2123 */     return new CallStack(finalTrace);
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonContext createJsonContext() {
/* 2128 */     return this.jsonContext;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\DefaultServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */