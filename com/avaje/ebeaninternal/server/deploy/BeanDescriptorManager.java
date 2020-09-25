/*      */ package com.avaje.ebeaninternal.server.deploy;
/*      */ 
/*      */ import com.avaje.ebean.BackgroundExecutor;
/*      */ import com.avaje.ebean.RawSql;
/*      */ import com.avaje.ebean.RawSqlBuilder;
/*      */ import com.avaje.ebean.bean.EntityBean;
/*      */ import com.avaje.ebean.cache.ServerCacheManager;
/*      */ import com.avaje.ebean.config.EncryptKey;
/*      */ import com.avaje.ebean.config.EncryptKeyManager;
/*      */ import com.avaje.ebean.config.GlobalProperties;
/*      */ import com.avaje.ebean.config.NamingConvention;
/*      */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*      */ import com.avaje.ebean.config.dbplatform.DbIdentity;
/*      */ import com.avaje.ebean.config.dbplatform.IdGenerator;
/*      */ import com.avaje.ebean.config.dbplatform.IdType;
/*      */ import com.avaje.ebean.event.BeanFinder;
/*      */ import com.avaje.ebean.validation.factory.LengthValidatorFactory;
/*      */ import com.avaje.ebean.validation.factory.NotNullValidatorFactory;
/*      */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*      */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*      */ import com.avaje.ebeaninternal.server.core.BootupClasses;
/*      */ import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
/*      */ import com.avaje.ebeaninternal.server.core.InternString;
/*      */ import com.avaje.ebeaninternal.server.core.InternalConfiguration;
/*      */ import com.avaje.ebeaninternal.server.core.Message;
/*      */ import com.avaje.ebeaninternal.server.core.XmlConfig;
/*      */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*      */ import com.avaje.ebeaninternal.server.deploy.id.IdBinderEmbedded;
/*      */ import com.avaje.ebeaninternal.server.deploy.id.IdBinderFactory;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssoc;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanTable;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;
/*      */ import com.avaje.ebeaninternal.server.deploy.parse.DeployBeanInfo;
/*      */ import com.avaje.ebeaninternal.server.deploy.parse.DeployCreateProperties;
/*      */ import com.avaje.ebeaninternal.server.deploy.parse.DeployInherit;
/*      */ import com.avaje.ebeaninternal.server.deploy.parse.DeployUtil;
/*      */ import com.avaje.ebeaninternal.server.deploy.parse.ReadAnnotations;
/*      */ import com.avaje.ebeaninternal.server.deploy.parse.TransientProperties;
/*      */ import com.avaje.ebeaninternal.server.idgen.UuidIdGenerator;
/*      */ import com.avaje.ebeaninternal.server.lib.util.Dnode;
/*      */ import com.avaje.ebeaninternal.server.reflect.BeanReflect;
/*      */ import com.avaje.ebeaninternal.server.reflect.BeanReflectFactory;
/*      */ import com.avaje.ebeaninternal.server.reflect.BeanReflectGetter;
/*      */ import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;
/*      */ import com.avaje.ebeaninternal.server.reflect.EnhanceBeanReflectFactory;
/*      */ import com.avaje.ebeaninternal.server.subclass.SubClassManager;
/*      */ import com.avaje.ebeaninternal.server.subclass.SubClassUtil;
/*      */ import com.avaje.ebeaninternal.server.type.TypeManager;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.persistence.PersistenceException;
/*      */ import javax.sql.DataSource;
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
/*      */ public class BeanDescriptorManager
/*      */   implements BeanDescriptorMap
/*      */ {
/*   95 */   private static final Logger logger = Logger.getLogger(BeanDescriptorManager.class.getName());
/*      */   
/*   97 */   private static final BeanDescComparator beanDescComparator = new BeanDescComparator();
/*      */   
/*   99 */   private final ReadAnnotations readAnnotations = new ReadAnnotations();
/*      */ 
/*      */   
/*      */   private final TransientProperties transientProperties;
/*      */   
/*      */   private final DeployInherit deplyInherit;
/*      */   
/*      */   private final BeanReflectFactory reflectFactory;
/*      */   
/*      */   private final DeployUtil deployUtil;
/*      */   
/*      */   private final TypeManager typeManager;
/*      */   
/*      */   private final PersistControllerManager persistControllerManager;
/*      */   
/*      */   private final BeanFinderManager beanFinderManager;
/*      */   
/*      */   private final PersistListenerManager persistListenerManager;
/*      */   
/*      */   private final BeanQueryAdapterManager beanQueryAdapterManager;
/*      */   
/*      */   private final SubClassManager subClassManager;
/*      */   
/*      */   private final NamingConvention namingConvention;
/*      */   
/*      */   private final DeployCreateProperties createProperties;
/*      */   
/*      */   private final DeployOrmXml deployOrmXml;
/*      */   
/*      */   private final BeanManagerFactory beanManagerFactory;
/*      */   
/*      */   private int enhancedClassCount;
/*      */   
/*      */   private int subclassClassCount;
/*      */   
/*  134 */   private final HashSet<String> subclassedEntities = new HashSet<String>();
/*      */   
/*      */   private final boolean updateChangesOnly;
/*      */   
/*      */   private final BootupClasses bootupClasses;
/*      */   
/*      */   private final String serverName;
/*      */   
/*  142 */   private Map<Class<?>, DeployBeanInfo<?>> deplyInfoMap = new HashMap<Class<?>, DeployBeanInfo<?>>();
/*      */   
/*  144 */   private final Map<Class<?>, BeanTable> beanTableMap = new HashMap<Class<?>, BeanTable>();
/*      */   
/*  146 */   private final Map<String, BeanDescriptor<?>> descMap = new HashMap<String, BeanDescriptor<?>>();
/*  147 */   private final Map<String, BeanDescriptor<?>> idDescMap = new HashMap<String, BeanDescriptor<?>>();
/*      */   
/*  149 */   private final Map<String, BeanManager<?>> beanManagerMap = new HashMap<String, BeanManager<?>>();
/*      */   
/*  151 */   private final Map<String, List<BeanDescriptor<?>>> tableToDescMap = new HashMap<String, List<BeanDescriptor<?>>>();
/*      */   
/*      */   private List<BeanDescriptor<?>> immutableDescriptorList;
/*      */   
/*  155 */   private final Set<Integer> descriptorUniqueIds = new HashSet<Integer>();
/*      */   
/*      */   private final DbIdentity dbIdentity;
/*      */   
/*      */   private final DataSource dataSource;
/*      */   
/*      */   private final DatabasePlatform databasePlatform;
/*      */   
/*  163 */   private final UuidIdGenerator uuidIdGenerator = new UuidIdGenerator();
/*      */ 
/*      */   
/*      */   private final ServerCacheManager cacheManager;
/*      */ 
/*      */   
/*      */   private final BackgroundExecutor backgroundExecutor;
/*      */ 
/*      */   
/*      */   private final int dbSequenceBatchSize;
/*      */   
/*      */   private final EncryptKeyManager encryptKeyManager;
/*      */   
/*      */   private final IdBinderFactory idBinderFactory;
/*      */   
/*      */   private final XmlConfig xmlConfig;
/*      */   
/*      */   private final boolean allowSubclassing;
/*      */ 
/*      */   
/*      */   public BeanDescriptorManager(InternalConfiguration config) {
/*  184 */     this.serverName = InternString.intern(config.getServerConfig().getName());
/*  185 */     this.cacheManager = config.getCacheManager();
/*  186 */     this.xmlConfig = config.getXmlConfig();
/*  187 */     this.dbSequenceBatchSize = config.getServerConfig().getDatabaseSequenceBatchSize();
/*  188 */     this.backgroundExecutor = (BackgroundExecutor)config.getBackgroundExecutor();
/*  189 */     this.dataSource = config.getServerConfig().getDataSource();
/*  190 */     this.encryptKeyManager = config.getServerConfig().getEncryptKeyManager();
/*  191 */     this.databasePlatform = config.getServerConfig().getDatabasePlatform();
/*  192 */     this.idBinderFactory = new IdBinderFactory(this.databasePlatform.isIdInExpandedForm());
/*      */     
/*  194 */     this.bootupClasses = config.getBootupClasses();
/*  195 */     this.createProperties = config.getDeployCreateProperties();
/*  196 */     this.subClassManager = config.getSubClassManager();
/*  197 */     this.typeManager = config.getTypeManager();
/*  198 */     this.namingConvention = config.getServerConfig().getNamingConvention();
/*  199 */     this.dbIdentity = config.getDatabasePlatform().getDbIdentity();
/*  200 */     this.deplyInherit = config.getDeployInherit();
/*  201 */     this.deployOrmXml = config.getDeployOrmXml();
/*  202 */     this.deployUtil = config.getDeployUtil();
/*      */     
/*  204 */     this.beanManagerFactory = new BeanManagerFactory(config.getServerConfig(), config.getDatabasePlatform());
/*      */     
/*  206 */     this.updateChangesOnly = config.getServerConfig().isUpdateChangesOnly();
/*      */     
/*  208 */     this.persistControllerManager = new PersistControllerManager(this.bootupClasses);
/*  209 */     this.persistListenerManager = new PersistListenerManager(this.bootupClasses);
/*  210 */     this.beanQueryAdapterManager = new BeanQueryAdapterManager(this.bootupClasses);
/*      */     
/*  212 */     this.beanFinderManager = new DefaultBeanFinderManager();
/*      */     
/*  214 */     this.reflectFactory = createReflectionFactory();
/*  215 */     this.transientProperties = new TransientProperties();
/*  216 */     this.allowSubclassing = config.getServerConfig().isAllowSubclassing();
/*      */   }
/*      */   
/*      */   public BeanDescriptor<?> getBeanDescriptorById(String descriptorId) {
/*  220 */     return this.idDescMap.get(descriptorId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> BeanDescriptor<T> getBeanDescriptor(Class<T> entityType) {
/*  227 */     String className = SubClassUtil.getSuperClassName(entityType.getName());
/*  228 */     return (BeanDescriptor<T>)this.descMap.get(className);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> BeanDescriptor<T> getBeanDescriptor(String entityClassName) {
/*  235 */     entityClassName = SubClassUtil.getSuperClassName(entityClassName);
/*  236 */     return (BeanDescriptor<T>)this.descMap.get(entityClassName);
/*      */   }
/*      */   
/*      */   public String getServerName() {
/*  240 */     return this.serverName;
/*      */   }
/*      */   
/*      */   public ServerCacheManager getCacheManager() {
/*  244 */     return this.cacheManager;
/*      */   }
/*      */   
/*      */   public NamingConvention getNamingConvention() {
/*  248 */     return this.namingConvention;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEbeanServer(SpiEbeanServer internalEbean) {
/*  255 */     for (BeanDescriptor<?> desc : this.immutableDescriptorList) {
/*  256 */       desc.setEbeanServer(internalEbean);
/*      */     }
/*      */   }
/*      */   
/*      */   public IdBinder createIdBinder(BeanProperty[] uids) {
/*  261 */     return this.idBinderFactory.createIdBinder(uids);
/*      */   }
/*      */ 
/*      */   
/*      */   public void deploy() {
/*      */     try {
/*  267 */       createListeners();
/*  268 */       readEmbeddedDeployment();
/*  269 */       readEntityDeploymentInitial();
/*  270 */       readEntityBeanTable();
/*  271 */       readEntityDeploymentAssociations();
/*  272 */       readInheritedIdGenerators();
/*      */ 
/*      */       
/*  275 */       readEntityRelationships();
/*  276 */       readRawSqlQueries();
/*      */       
/*  278 */       List<BeanDescriptor<?>> list = new ArrayList<BeanDescriptor<?>>(this.descMap.values());
/*  279 */       Collections.sort(list, beanDescComparator);
/*  280 */       this.immutableDescriptorList = Collections.unmodifiableList(list);
/*      */ 
/*      */       
/*  283 */       for (BeanDescriptor<?> d : list) {
/*  284 */         this.idDescMap.put(d.getDescriptorId(), d);
/*      */       }
/*      */       
/*  287 */       initialiseAll();
/*  288 */       readForeignKeys();
/*      */       
/*  290 */       readTableToDescriptor();
/*      */       
/*  292 */       logStatus();
/*      */       
/*  294 */       this.deplyInfoMap.clear();
/*  295 */       this.deplyInfoMap = null;
/*  296 */     } catch (RuntimeException e) {
/*  297 */       String msg = "Error in deployment";
/*  298 */       logger.log(Level.SEVERE, msg, e);
/*  299 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EncryptKey getEncryptKey(String tableName, String columnName) {
/*  307 */     return this.encryptKeyManager.getEncryptKey(tableName, columnName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cacheNotify(TransactionEventTable.TableIUD tableIUD) {
/*  316 */     List<BeanDescriptor<?>> list = getBeanDescriptors(tableIUD.getTableName());
/*  317 */     if (list != null) {
/*  318 */       for (int i = 0; i < list.size(); i++) {
/*  319 */         ((BeanDescriptor)list.get(i)).cacheNotify(tableIUD);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<BeanDescriptor<?>> getBeanDescriptors(String tableName) {
/*  328 */     return this.tableToDescMap.get(tableName.toLowerCase());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readTableToDescriptor() {
/*  339 */     for (BeanDescriptor<?> desc : this.descMap.values()) {
/*  340 */       String baseTable = desc.getBaseTable();
/*  341 */       if (baseTable == null) {
/*      */         continue;
/*      */       }
/*  344 */       baseTable = baseTable.toLowerCase();
/*      */       
/*  346 */       List<BeanDescriptor<?>> list = this.tableToDescMap.get(baseTable);
/*  347 */       if (list == null) {
/*  348 */         list = new ArrayList<BeanDescriptor<?>>(1);
/*  349 */         this.tableToDescMap.put(baseTable, list);
/*      */       } 
/*  351 */       list.add(desc);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readForeignKeys() {
/*  358 */     for (BeanDescriptor<?> d : this.descMap.values()) {
/*  359 */       d.initialiseFkeys();
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
/*      */   private void initialiseAll() {
/*  384 */     for (BeanDescriptor<?> d : this.descMap.values()) {
/*  385 */       d.initialiseId();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  390 */     for (BeanDescriptor<?> d : this.descMap.values()) {
/*  391 */       d.initInheritInfo();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  396 */     for (BeanDescriptor<?> d : this.descMap.values()) {
/*  397 */       d.initialiseOther();
/*      */     }
/*      */ 
/*      */     
/*  401 */     for (BeanDescriptor<?> d : this.descMap.values()) {
/*  402 */       if (!d.isEmbedded()) {
/*  403 */         BeanManager<?> m = this.beanManagerFactory.create(d);
/*  404 */         this.beanManagerMap.put(d.getFullName(), m);
/*      */         
/*  406 */         checkForValidEmbeddedId(d);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkForValidEmbeddedId(BeanDescriptor<?> d) {
/*  412 */     IdBinder idBinder = d.getIdBinder();
/*  413 */     if (idBinder != null && idBinder instanceof IdBinderEmbedded) {
/*  414 */       IdBinderEmbedded embId = (IdBinderEmbedded)idBinder;
/*  415 */       BeanDescriptor<?> idBeanDescriptor = embId.getIdBeanDescriptor();
/*  416 */       Class<?> idType = idBeanDescriptor.getBeanType();
/*      */       try {
/*  418 */         idType.getDeclaredMethod("hashCode", new Class[0]);
/*  419 */         idType.getDeclaredMethod("equals", new Class[] { Object.class });
/*  420 */       } catch (NoSuchMethodException e) {
/*  421 */         checkMissingHashCodeOrEquals(e, idType, d.getBeanType());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkMissingHashCodeOrEquals(Exception source, Class<?> idType, Class<?> beanType) {
/*  428 */     String msg = "SERIOUS ERROR: The hashCode() and equals() methods *MUST* be implemented ";
/*  429 */     msg = msg + "on Embedded bean " + idType + " as it is used as an Id for " + beanType;
/*      */     
/*  431 */     if (GlobalProperties.getBoolean("ebean.strict", true)) {
/*  432 */       throw new PersistenceException(msg, source);
/*      */     }
/*  434 */     logger.log(Level.SEVERE, msg, source);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<BeanDescriptor<?>> getBeanDescriptorList() {
/*  442 */     return this.immutableDescriptorList;
/*      */   }
/*      */   
/*      */   public Map<Class<?>, BeanTable> getBeanTables() {
/*  446 */     return this.beanTableMap;
/*      */   }
/*      */   
/*      */   public BeanTable getBeanTable(Class<?> type) {
/*  450 */     return this.beanTableMap.get(type);
/*      */   }
/*      */   
/*      */   public Map<String, BeanDescriptor<?>> getBeanDescriptors() {
/*  454 */     return this.descMap;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> BeanManager<T> getBeanManager(Class<T> entityType) {
/*  460 */     return (BeanManager)getBeanManager(entityType.getName());
/*      */   }
/*      */ 
/*      */   
/*      */   public BeanManager<?> getBeanManager(String beanClassName) {
/*  465 */     beanClassName = SubClassUtil.getSuperClassName(beanClassName);
/*  466 */     return this.beanManagerMap.get(beanClassName);
/*      */   }
/*      */   
/*      */   public DNativeQuery getNativeQuery(String name) {
/*  470 */     return this.deployOrmXml.getNativeQuery(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createListeners() {
/*  478 */     int qa = this.beanQueryAdapterManager.getRegisterCount();
/*  479 */     int cc = this.persistControllerManager.getRegisterCount();
/*  480 */     int lc = this.persistListenerManager.getRegisterCount();
/*  481 */     int fc = this.beanFinderManager.createBeanFinders(this.bootupClasses.getBeanFinders());
/*      */     
/*  483 */     logger.fine("BeanPersistControllers[" + cc + "] BeanFinders[" + fc + "] BeanPersistListeners[" + lc + "] BeanQueryAdapters[" + qa + "]");
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
/*      */   private void logStatus() {
/*  496 */     String msg = "Entities enhanced[" + this.enhancedClassCount + "] subclassed[" + this.subclassClassCount + "]";
/*  497 */     logger.info(msg);
/*      */     
/*  499 */     if (this.enhancedClassCount > 0 && 
/*  500 */       this.subclassClassCount > 0) {
/*  501 */       String subclassEntityNames = this.subclassedEntities.toString();
/*      */       
/*  503 */       String m = "Mixing enhanced and subclassed entities. Subclassed classes:" + subclassEntityNames;
/*  504 */       logger.warning(m);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> BeanDescriptor<T> createEmbedded(Class<T> beanClass) {
/*  511 */     DeployBeanInfo<T> info = createDeployBeanInfo(beanClass);
/*  512 */     readDeployAssociations(info);
/*      */     
/*  514 */     Integer key = getUniqueHash(info.getDescriptor());
/*      */     
/*  516 */     return new BeanDescriptor<T>(this, this.typeManager, info.getDescriptor(), key.toString());
/*      */   }
/*      */   
/*      */   private void registerBeanDescriptor(BeanDescriptor<?> desc) {
/*  520 */     this.descMap.put(desc.getBeanType().getName(), desc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readEmbeddedDeployment() {
/*  528 */     ArrayList<Class<?>> embeddedClasses = this.bootupClasses.getEmbeddables();
/*  529 */     for (int i = 0; i < embeddedClasses.size(); i++) {
/*  530 */       Class<?> cls = embeddedClasses.get(i);
/*  531 */       if (logger.isLoggable(Level.FINER)) {
/*  532 */         String msg = "load deployinfo for embeddable:" + cls.getName();
/*  533 */         logger.finer(msg);
/*      */       } 
/*  535 */       BeanDescriptor<?> embDesc = createEmbedded(cls);
/*  536 */       registerBeanDescriptor(embDesc);
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
/*      */   private void readEntityDeploymentInitial() {
/*  549 */     ArrayList<Class<?>> entityClasses = this.bootupClasses.getEntities();
/*      */     
/*  551 */     for (Class<?> entityClass : entityClasses) {
/*  552 */       DeployBeanInfo<?> info = createDeployBeanInfo(entityClass);
/*  553 */       this.deplyInfoMap.put(entityClass, info);
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
/*      */   private void readEntityBeanTable() {
/*  565 */     Iterator<DeployBeanInfo<?>> it = this.deplyInfoMap.values().iterator();
/*  566 */     while (it.hasNext()) {
/*  567 */       DeployBeanInfo<?> info = it.next();
/*  568 */       BeanTable beanTable = createBeanTable(info);
/*  569 */       this.beanTableMap.put(beanTable.getBeanType(), beanTable);
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
/*      */   private void readEntityDeploymentAssociations() {
/*  581 */     Iterator<DeployBeanInfo<?>> it = this.deplyInfoMap.values().iterator();
/*  582 */     while (it.hasNext()) {
/*  583 */       DeployBeanInfo<?> info = it.next();
/*  584 */       readDeployAssociations(info);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readInheritedIdGenerators() {
/*  590 */     Iterator<DeployBeanInfo<?>> it = this.deplyInfoMap.values().iterator();
/*  591 */     while (it.hasNext()) {
/*  592 */       DeployBeanInfo<?> info = it.next();
/*  593 */       DeployBeanDescriptor<?> descriptor = info.getDescriptor();
/*  594 */       InheritInfo inheritInfo = descriptor.getInheritInfo();
/*  595 */       if (inheritInfo != null && !inheritInfo.isRoot()) {
/*  596 */         DeployBeanInfo<?> rootBeanInfo = this.deplyInfoMap.get(inheritInfo.getRoot().getType());
/*  597 */         IdGenerator rootIdGen = rootBeanInfo.getDescriptor().getIdGenerator();
/*  598 */         if (rootIdGen != null) {
/*  599 */           descriptor.setIdGenerator(rootIdGen);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BeanTable createBeanTable(DeployBeanInfo<?> info) {
/*  610 */     DeployBeanDescriptor<?> deployDescriptor = info.getDescriptor();
/*  611 */     DeployBeanTable beanTable = deployDescriptor.createDeployBeanTable();
/*  612 */     return new BeanTable(beanTable, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readRawSqlQueries() {
/*  620 */     for (DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
/*      */       
/*  622 */       DeployBeanDescriptor<?> deployDesc = info.getDescriptor();
/*  623 */       BeanDescriptor<?> desc = getBeanDescriptor(deployDesc.getBeanType());
/*      */       
/*  625 */       for (DRawSqlMeta rawSqlMeta : deployDesc.getRawSqlMeta()) {
/*  626 */         if (rawSqlMeta.getQuery() == null) {
/*      */           continue;
/*      */         }
/*  629 */         DeployNamedQuery nq = (new DRawSqlSelectBuilder(this.namingConvention, desc, rawSqlMeta)).parse();
/*  630 */         desc.addNamedQuery(nq);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readEntityRelationships() {
/*  642 */     for (DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
/*  643 */       checkMappedBy(info);
/*      */     }
/*      */     
/*  646 */     for (DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
/*  647 */       secondaryPropsJoins(info);
/*      */     }
/*      */     
/*  650 */     for (DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
/*  651 */       DeployBeanDescriptor<?> deployBeanDescriptor = info.getDescriptor();
/*  652 */       Integer key = getUniqueHash(deployBeanDescriptor);
/*  653 */       registerBeanDescriptor(new BeanDescriptor(this, this.typeManager, info.getDescriptor(), key.toString()));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Integer getUniqueHash(DeployBeanDescriptor<?> deployBeanDescriptor) {
/*  659 */     int hashCode = deployBeanDescriptor.getFullName().hashCode();
/*      */     
/*  661 */     for (int i = 0; i < 100000; i++) {
/*  662 */       Integer key = Integer.valueOf(hashCode + i);
/*  663 */       if (!this.descriptorUniqueIds.contains(key)) {
/*  664 */         return key;
/*      */       }
/*      */     } 
/*  667 */     throw new RuntimeException("Failed to generate a unique hash for " + deployBeanDescriptor.getFullName());
/*      */   }
/*      */ 
/*      */   
/*      */   private void secondaryPropsJoins(DeployBeanInfo<?> info) {
/*  672 */     DeployBeanDescriptor<?> descriptor = info.getDescriptor();
/*  673 */     for (DeployBeanProperty prop : descriptor.propertiesBase()) {
/*  674 */       if (prop.isSecondaryTable()) {
/*  675 */         String tableName = prop.getSecondaryTable();
/*      */         
/*  677 */         DeployBeanPropertyAssocOne<?> assocOne = descriptor.findJoinToTable(tableName);
/*  678 */         if (assocOne == null) {
/*  679 */           String msg = "Error with property " + prop.getFullBeanName() + ". Could not find a Relationship to table " + tableName + ". Perhaps you could use a @JoinColumn instead.";
/*      */           
/*  681 */           throw new RuntimeException(msg);
/*      */         } 
/*  683 */         DeployTableJoin tableJoin = assocOne.getTableJoin();
/*  684 */         prop.setSecondaryTableJoin(tableJoin, assocOne.getName());
/*      */       } 
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
/*      */   private void checkMappedBy(DeployBeanInfo<?> info) {
/*  699 */     for (DeployBeanPropertyAssocOne<?> oneProp : (Iterable<DeployBeanPropertyAssocOne<?>>)info.getDescriptor().propertiesAssocOne()) {
/*  700 */       if (!oneProp.isTransient() && 
/*  701 */         oneProp.getMappedBy() != null) {
/*  702 */         checkMappedByOneToOne(info, oneProp);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  707 */     for (DeployBeanPropertyAssocMany<?> manyProp : (Iterable<DeployBeanPropertyAssocMany<?>>)info.getDescriptor().propertiesAssocMany()) {
/*  708 */       if (!manyProp.isTransient()) {
/*  709 */         if (manyProp.isManyToMany()) {
/*  710 */           checkMappedByManyToMany(info, manyProp); continue;
/*      */         } 
/*  712 */         checkMappedByOneToMany(info, manyProp);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private DeployBeanDescriptor<?> getTargetDescriptor(DeployBeanPropertyAssoc<?> prop) {
/*  720 */     Class<?> targetType = prop.getTargetType();
/*  721 */     DeployBeanInfo<?> info = this.deplyInfoMap.get(targetType);
/*  722 */     if (info == null) {
/*  723 */       String msg = "Can not find descriptor [" + targetType + "] for " + prop.getFullBeanName();
/*  724 */       throw new PersistenceException(msg);
/*      */     } 
/*      */     
/*  727 */     return info.getDescriptor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean findMappedBy(DeployBeanPropertyAssocMany<?> prop) {
/*  737 */     Class<?> owningType = prop.getOwningType();
/*      */     
/*  739 */     Set<String> matchSet = new HashSet<String>();
/*      */ 
/*      */     
/*  742 */     DeployBeanDescriptor<?> targetDesc = getTargetDescriptor((DeployBeanPropertyAssoc<?>)prop);
/*  743 */     List<DeployBeanPropertyAssocOne<?>> ones = targetDesc.propertiesAssocOne();
/*  744 */     for (DeployBeanPropertyAssocOne<?> possibleMappedBy : ones) {
/*  745 */       Class<?> possibleMappedByType = possibleMappedBy.getTargetType();
/*  746 */       if (possibleMappedByType.equals(owningType)) {
/*  747 */         prop.setMappedBy(possibleMappedBy.getName());
/*  748 */         matchSet.add(possibleMappedBy.getName());
/*      */       } 
/*      */     } 
/*      */     
/*  752 */     if (matchSet.size() == 0)
/*      */     {
/*      */       
/*  755 */       return false;
/*      */     }
/*  757 */     if (matchSet.size() == 1)
/*      */     {
/*  759 */       return true;
/*      */     }
/*  761 */     if (matchSet.size() == 2) {
/*      */ 
/*      */       
/*  764 */       String name = prop.getName();
/*      */ 
/*      */       
/*  767 */       String targetType = prop.getTargetType().getName();
/*  768 */       String shortTypeName = targetType.substring(targetType.lastIndexOf(".") + 1);
/*      */ 
/*      */       
/*  771 */       int p = name.indexOf(shortTypeName);
/*  772 */       if (p > 1) {
/*      */ 
/*      */         
/*  775 */         String searchName = name.substring(0, p).toLowerCase();
/*      */ 
/*      */         
/*  778 */         Iterator<String> it = matchSet.iterator();
/*  779 */         while (it.hasNext()) {
/*  780 */           String possibleMappedBy = it.next();
/*  781 */           String possibleLower = possibleMappedBy.toLowerCase();
/*  782 */           if (possibleLower.indexOf(searchName) > -1) {
/*      */             
/*  784 */             prop.setMappedBy(possibleMappedBy);
/*      */             
/*  786 */             String m = "Implicitly found mappedBy for " + targetDesc + "." + prop;
/*  787 */             m = m + " by searching for [" + searchName + "] against " + matchSet;
/*  788 */             logger.fine(m);
/*      */             
/*  790 */             return true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  797 */     String msg = "Error on " + prop.getFullBeanName() + " missing mappedBy.";
/*  798 */     msg = msg + " There are [" + matchSet.size() + "] possible properties in " + targetDesc;
/*  799 */     msg = msg + " that this association could be mapped to. Please specify one using ";
/*  800 */     msg = msg + "the mappedBy attribute on @OneToMany.";
/*  801 */     throw new PersistenceException(msg);
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
/*      */   private void makeUnidirectional(DeployBeanInfo<?> info, DeployBeanPropertyAssocMany<?> oneToMany) {
/*  819 */     DeployBeanDescriptor<?> targetDesc = getTargetDescriptor((DeployBeanPropertyAssoc<?>)oneToMany);
/*      */     
/*  821 */     Class<?> owningType = oneToMany.getOwningType();
/*      */     
/*  823 */     if (!oneToMany.getCascadeInfo().isSave()) {
/*      */ 
/*      */       
/*  826 */       Class<?> targetType = oneToMany.getTargetType();
/*  827 */       String msg = "Error on " + oneToMany.getFullBeanName() + ". @OneToMany MUST have ";
/*  828 */       msg = msg + "Cascade.PERSIST or Cascade.ALL because this is a unidirectional ";
/*  829 */       msg = msg + "relationship. That is, there is no property of type " + owningType + " on " + targetType;
/*      */       
/*  831 */       throw new PersistenceException(msg);
/*      */     } 
/*      */ 
/*      */     
/*  835 */     oneToMany.setUnidirectional(true);
/*      */ 
/*      */ 
/*      */     
/*  839 */     DeployBeanPropertyAssocOne<?> unidirectional = new DeployBeanPropertyAssocOne(targetDesc, owningType);
/*  840 */     unidirectional.setUndirectionalShadow(true);
/*  841 */     unidirectional.setNullable(false);
/*  842 */     unidirectional.setDbRead(true);
/*  843 */     unidirectional.setDbInsertable(true);
/*  844 */     unidirectional.setDbUpdateable(false);
/*      */     
/*  846 */     targetDesc.setUnidirectional(unidirectional);
/*      */ 
/*      */     
/*  849 */     BeanTable beanTable = getBeanTable(owningType);
/*  850 */     unidirectional.setBeanTable(beanTable);
/*  851 */     unidirectional.setName(beanTable.getBaseTable());
/*      */     
/*  853 */     info.setBeanJoinType(unidirectional, true);
/*      */ 
/*      */     
/*  856 */     DeployTableJoin oneToManyJoin = oneToMany.getTableJoin();
/*  857 */     if (!oneToManyJoin.hasJoinColumns()) {
/*  858 */       throw new RuntimeException("No join columns");
/*      */     }
/*      */ 
/*      */     
/*  862 */     DeployTableJoin unidirectionalJoin = unidirectional.getTableJoin();
/*  863 */     unidirectionalJoin.setColumns(oneToManyJoin.columns(), true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkMappedByOneToOne(DeployBeanInfo<?> info, DeployBeanPropertyAssocOne<?> prop) {
/*  871 */     String mappedBy = prop.getMappedBy();
/*      */ 
/*      */     
/*  874 */     DeployBeanDescriptor<?> targetDesc = getTargetDescriptor((DeployBeanPropertyAssoc<?>)prop);
/*  875 */     DeployBeanProperty mappedProp = targetDesc.getBeanProperty(mappedBy);
/*  876 */     if (mappedProp == null) {
/*  877 */       String m = "Error on " + prop.getFullBeanName();
/*  878 */       m = m + "  Can not find mappedBy property [" + targetDesc + "." + mappedBy + "] ";
/*  879 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/*  882 */     if (!(mappedProp instanceof DeployBeanPropertyAssocOne)) {
/*  883 */       String m = "Error on " + prop.getFullBeanName();
/*  884 */       m = m + ". mappedBy property [" + targetDesc + "." + mappedBy + "]is not a OneToOne?";
/*  885 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/*  888 */     DeployBeanPropertyAssocOne<?> mappedAssocOne = (DeployBeanPropertyAssocOne)mappedProp;
/*      */     
/*  890 */     if (!mappedAssocOne.isOneToOne()) {
/*  891 */       String m = "Error on " + prop.getFullBeanName();
/*  892 */       m = m + ". mappedBy property [" + targetDesc + "." + mappedBy + "]is not a OneToOne?";
/*  893 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/*  896 */     DeployTableJoin tableJoin = prop.getTableJoin();
/*  897 */     if (!tableJoin.hasJoinColumns()) {
/*      */       
/*  899 */       DeployTableJoin otherTableJoin = mappedAssocOne.getTableJoin();
/*  900 */       otherTableJoin.copyTo(tableJoin, true, tableJoin.getTable());
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
/*      */   
/*      */   private void checkMappedByOneToMany(DeployBeanInfo<?> info, DeployBeanPropertyAssocMany<?> prop) {
/*  916 */     if (prop.getMappedBy() == null && 
/*  917 */       !findMappedBy(prop)) {
/*  918 */       makeUnidirectional(info, prop);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  925 */     String mappedBy = prop.getMappedBy();
/*      */ 
/*      */     
/*  928 */     DeployBeanDescriptor<?> targetDesc = getTargetDescriptor((DeployBeanPropertyAssoc<?>)prop);
/*  929 */     DeployBeanProperty mappedProp = targetDesc.getBeanProperty(mappedBy);
/*  930 */     if (mappedProp == null) {
/*      */       
/*  932 */       String m = "Error on " + prop.getFullBeanName();
/*  933 */       m = m + "  Can not find mappedBy property [" + mappedBy + "] ";
/*  934 */       m = m + "in [" + targetDesc + "]";
/*  935 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/*  938 */     if (!(mappedProp instanceof DeployBeanPropertyAssocOne)) {
/*  939 */       String m = "Error on " + prop.getFullBeanName();
/*  940 */       m = m + ". mappedBy property [" + mappedBy + "]is not a ManyToOne?";
/*  941 */       m = m + "in [" + targetDesc + "]";
/*  942 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/*  945 */     DeployBeanPropertyAssocOne<?> mappedAssocOne = (DeployBeanPropertyAssocOne)mappedProp;
/*      */     
/*  947 */     DeployTableJoin tableJoin = prop.getTableJoin();
/*  948 */     if (!tableJoin.hasJoinColumns()) {
/*      */       
/*  950 */       DeployTableJoin otherTableJoin = mappedAssocOne.getTableJoin();
/*  951 */       otherTableJoin.copyTo(tableJoin, true, tableJoin.getTable());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkMappedByManyToMany(DeployBeanInfo<?> info, DeployBeanPropertyAssocMany<?> prop) {
/*  962 */     String mappedBy = prop.getMappedBy();
/*  963 */     if (mappedBy == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  968 */     DeployBeanDescriptor<?> targetDesc = getTargetDescriptor((DeployBeanPropertyAssoc<?>)prop);
/*  969 */     DeployBeanProperty mappedProp = targetDesc.getBeanProperty(mappedBy);
/*      */     
/*  971 */     if (mappedProp == null) {
/*  972 */       String m = "Error on " + prop.getFullBeanName();
/*  973 */       m = m + "  Can not find mappedBy property [" + mappedBy + "] ";
/*  974 */       m = m + "in [" + targetDesc + "]";
/*  975 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/*  978 */     if (!(mappedProp instanceof DeployBeanPropertyAssocMany)) {
/*  979 */       String m = "Error on " + prop.getFullBeanName();
/*  980 */       m = m + ". mappedBy property [" + targetDesc + "." + mappedBy + "] is not a ManyToMany?";
/*  981 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/*  984 */     DeployBeanPropertyAssocMany<?> mappedAssocMany = (DeployBeanPropertyAssocMany)mappedProp;
/*      */     
/*  986 */     if (!mappedAssocMany.isManyToMany()) {
/*  987 */       String m = "Error on " + prop.getFullBeanName();
/*  988 */       m = m + ". mappedBy property [" + targetDesc + "." + mappedBy + "] is not a ManyToMany?";
/*  989 */       throw new PersistenceException(m);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  996 */     DeployTableJoin mappedIntJoin = mappedAssocMany.getIntersectionJoin();
/*  997 */     DeployTableJoin mappendInverseJoin = mappedAssocMany.getInverseJoin();
/*      */     
/*  999 */     String intTableName = mappedIntJoin.getTable();
/*      */     
/* 1001 */     DeployTableJoin tableJoin = prop.getTableJoin();
/* 1002 */     mappedIntJoin.copyTo(tableJoin, true, targetDesc.getBaseTable());
/*      */     
/* 1004 */     DeployTableJoin intJoin = new DeployTableJoin();
/* 1005 */     mappendInverseJoin.copyTo(intJoin, false, intTableName);
/* 1006 */     prop.setIntersectionJoin(intJoin);
/*      */     
/* 1008 */     DeployTableJoin inverseJoin = new DeployTableJoin();
/* 1009 */     mappedIntJoin.copyTo(inverseJoin, false, intTableName);
/* 1010 */     prop.setInverseJoin(inverseJoin);
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> void setBeanControllerFinderListener(DeployBeanDescriptor<T> descriptor) {
/* 1015 */     Class<T> beanType = descriptor.getBeanType();
/*      */     
/* 1017 */     this.persistControllerManager.addPersistControllers(descriptor);
/* 1018 */     this.persistListenerManager.addPersistListeners(descriptor);
/* 1019 */     this.beanQueryAdapterManager.addQueryAdapter(descriptor);
/*      */     
/* 1021 */     BeanFinder<T> beanFinder = this.beanFinderManager.getBeanFinder(beanType);
/* 1022 */     if (beanFinder != null) {
/* 1023 */       descriptor.setBeanFinder(beanFinder);
/* 1024 */       logger.fine("BeanFinder on[" + descriptor.getFullName() + "] " + beanFinder.getClass().getName());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> DeployBeanInfo<T> createDeployBeanInfo(Class<T> beanClass) {
/* 1033 */     DeployBeanDescriptor<T> desc = new DeployBeanDescriptor(beanClass);
/*      */     
/* 1035 */     desc.setUpdateChangesOnly(this.updateChangesOnly);
/*      */ 
/*      */     
/* 1038 */     setBeanControllerFinderListener(desc);
/* 1039 */     this.deplyInherit.process(desc);
/*      */     
/* 1041 */     this.createProperties.createProperties(desc);
/*      */     
/* 1043 */     DeployBeanInfo<T> info = new DeployBeanInfo(this.deployUtil, desc);
/*      */     
/* 1045 */     this.readAnnotations.readInitial(info);
/* 1046 */     return info;
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> void readDeployAssociations(DeployBeanInfo<T> info) {
/* 1051 */     DeployBeanDescriptor<T> desc = info.getDescriptor();
/*      */     
/* 1053 */     this.readAnnotations.readAssociations(info, this);
/*      */     
/* 1055 */     readXml(desc);
/*      */     
/* 1057 */     if (!BeanDescriptor.EntityType.ORM.equals(desc.getEntityType()))
/*      */     {
/* 1059 */       desc.setBaseTable(null);
/*      */     }
/*      */ 
/*      */     
/* 1063 */     this.transientProperties.process(desc);
/* 1064 */     setScalarType(desc);
/*      */     
/* 1066 */     if (!desc.isEmbedded()) {
/*      */       
/* 1068 */       setIdGeneration(desc);
/*      */ 
/*      */       
/* 1071 */       setConcurrencyMode(desc);
/*      */     } 
/*      */     
/* 1074 */     autoAddValidators(desc);
/*      */ 
/*      */     
/* 1077 */     createByteCode(desc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> IdType setIdGeneration(DeployBeanDescriptor<T> desc) {
/* 1085 */     if (desc.propertiesId().size() == 0) {
/*      */       
/* 1087 */       if (desc.isBaseTableType() && desc.getBeanFinder() == null)
/*      */       {
/*      */ 
/*      */         
/* 1091 */         logger.warning(Message.msg("deploy.nouid", desc.getFullName()));
/*      */       }
/* 1093 */       return null;
/*      */     } 
/*      */     
/* 1096 */     if (IdType.SEQUENCE.equals(desc.getIdType()) && !this.dbIdentity.isSupportsSequence()) {
/*      */       
/* 1098 */       logger.info("Explicit sequence on " + desc.getFullName() + " but not supported by DB Platform - ignored");
/* 1099 */       desc.setIdType(null);
/*      */     } 
/* 1101 */     if (IdType.IDENTITY.equals(desc.getIdType()) && !this.dbIdentity.isSupportsIdentity()) {
/*      */       
/* 1103 */       logger.info("Explicit Identity on " + desc.getFullName() + " but not supported by DB Platform - ignored");
/* 1104 */       desc.setIdType(null);
/*      */     } 
/*      */     
/* 1107 */     if (desc.getIdType() == null)
/*      */     {
/* 1109 */       desc.setIdType(this.dbIdentity.getIdType());
/*      */     }
/*      */     
/* 1112 */     if (IdType.GENERATOR.equals(desc.getIdType())) {
/* 1113 */       String genName = desc.getIdGeneratorName();
/* 1114 */       if ("auto.uuid".equals(genName)) {
/* 1115 */         desc.setIdGenerator((IdGenerator)this.uuidIdGenerator);
/* 1116 */         return IdType.GENERATOR;
/*      */       } 
/*      */     } 
/*      */     
/* 1120 */     if (desc.getBaseTable() == null)
/*      */     {
/*      */       
/* 1123 */       return null;
/*      */     }
/*      */     
/* 1126 */     if (IdType.IDENTITY.equals(desc.getIdType())) {
/*      */       
/* 1128 */       String selectLastInsertedId = this.dbIdentity.getSelectLastInsertedId(desc.getBaseTable());
/* 1129 */       desc.setSelectLastInsertedId(selectLastInsertedId);
/* 1130 */       return IdType.IDENTITY;
/*      */     } 
/*      */     
/* 1133 */     String seqName = desc.getIdGeneratorName();
/* 1134 */     if (seqName != null) {
/* 1135 */       logger.fine("explicit sequence " + seqName + " on " + desc.getFullName());
/*      */     } else {
/* 1137 */       String primaryKeyColumn = desc.getSinglePrimaryKeyColumn();
/*      */       
/* 1139 */       seqName = this.namingConvention.getSequenceName(desc.getBaseTable(), primaryKeyColumn);
/*      */     } 
/*      */ 
/*      */     
/* 1143 */     IdGenerator seqIdGen = createSequenceIdGenerator(seqName);
/* 1144 */     desc.setIdGenerator(seqIdGen);
/*      */     
/* 1146 */     return IdType.SEQUENCE;
/*      */   }
/*      */   
/*      */   private IdGenerator createSequenceIdGenerator(String seqName) {
/* 1150 */     return this.databasePlatform.createSequenceIdGenerator(this.backgroundExecutor, this.dataSource, seqName, this.dbSequenceBatchSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createByteCode(DeployBeanDescriptor<?> deploy) {
/* 1157 */     setEntityBeanClass(deploy);
/*      */ 
/*      */ 
/*      */     
/* 1161 */     setBeanReflect(deploy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void autoAddValidators(DeployBeanDescriptor<?> deployDesc) {
/* 1169 */     for (DeployBeanProperty prop : deployDesc.propertiesBase()) {
/* 1170 */       autoAddValidators(prop);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void autoAddValidators(DeployBeanProperty prop) {
/* 1179 */     if (String.class.equals(prop.getPropertyType()) && prop.getDbLength() > 0)
/*      */     {
/* 1181 */       if (!prop.containsValidatorType(LengthValidatorFactory.LengthValidator.class)) {
/* 1182 */         prop.addValidator(LengthValidatorFactory.create(0, prop.getDbLength()));
/*      */       }
/*      */     }
/* 1185 */     if (!prop.isNullable() && !prop.isId() && !prop.isGenerated())
/*      */     {
/* 1187 */       if (!prop.containsValidatorType(NotNullValidatorFactory.NotNullValidator.class)) {
/* 1188 */         prop.addValidator(NotNullValidatorFactory.NOT_NULL);
/*      */       }
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
/*      */   
/*      */   private void setScalarType(DeployBeanDescriptor<?> deployDesc) {
/* 1205 */     Iterator<DeployBeanProperty> it = deployDesc.propertiesAll();
/* 1206 */     while (it.hasNext()) {
/* 1207 */       DeployBeanProperty prop = it.next();
/* 1208 */       if (prop instanceof DeployBeanPropertyAssoc) {
/*      */         continue;
/*      */       }
/* 1211 */       this.deployUtil.setScalarType(prop);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readXml(DeployBeanDescriptor<?> deployDesc) {
/* 1218 */     List<Dnode> eXml = this.xmlConfig.findEntityXml(deployDesc.getFullName());
/* 1219 */     readXmlRawSql(deployDesc, eXml);
/*      */     
/* 1221 */     Dnode entityXml = this.deployOrmXml.findEntityDeploymentXml(deployDesc.getFullName());
/*      */     
/* 1223 */     if (entityXml != null) {
/* 1224 */       readXmlNamedQueries(deployDesc, entityXml);
/* 1225 */       readXmlSql(deployDesc, entityXml);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readXmlSql(DeployBeanDescriptor<?> deployDesc, Dnode entityXml) {
/* 1235 */     List<Dnode> sqlSelectList = entityXml.findAll("sql-select", entityXml.getLevel() + 1);
/* 1236 */     for (int i = 0; i < sqlSelectList.size(); i++) {
/* 1237 */       Dnode sqlSelect = sqlSelectList.get(i);
/* 1238 */       readSqlSelect(deployDesc, sqlSelect);
/*      */     } 
/*      */   }
/*      */   
/*      */   private String findContent(Dnode node, String nodeName) {
/* 1243 */     Dnode found = node.find(nodeName);
/* 1244 */     if (found != null) {
/* 1245 */       return found.getNodeContent();
/*      */     }
/* 1247 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readSqlSelect(DeployBeanDescriptor<?> deployDesc, Dnode sqlSelect) {
/* 1253 */     String name = sqlSelect.getStringAttr("name", "default");
/* 1254 */     String extend = sqlSelect.getStringAttr("extend", null);
/* 1255 */     String queryDebug = sqlSelect.getStringAttr("debug", null);
/* 1256 */     boolean debug = (queryDebug != null && queryDebug.equalsIgnoreCase("true"));
/*      */ 
/*      */     
/* 1259 */     String query = findContent(sqlSelect, "query");
/* 1260 */     String where = findContent(sqlSelect, "where");
/* 1261 */     String having = findContent(sqlSelect, "having");
/* 1262 */     String columnMapping = findContent(sqlSelect, "columnMapping");
/*      */     
/* 1264 */     DRawSqlMeta m = new DRawSqlMeta(name, extend, query, debug, where, having, columnMapping);
/*      */     
/* 1266 */     deployDesc.add(m);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readXmlRawSql(DeployBeanDescriptor<?> deployDesc, List<Dnode> entityXml) {
/* 1272 */     List<Dnode> rawSqlQueries = this.xmlConfig.find(entityXml, "raw-sql");
/* 1273 */     for (int i = 0; i < rawSqlQueries.size(); i++) {
/* 1274 */       Dnode rawSqlDnode = rawSqlQueries.get(i);
/* 1275 */       String name = rawSqlDnode.getAttribute("name");
/* 1276 */       if (isEmpty(name)) {
/* 1277 */         throw new IllegalStateException("raw-sql for " + deployDesc.getFullName() + " missing name attribute");
/*      */       }
/* 1279 */       Dnode queryNode = rawSqlDnode.find("query");
/* 1280 */       if (queryNode == null) {
/* 1281 */         throw new IllegalStateException("raw-sql for " + deployDesc.getFullName() + " missing query element");
/*      */       }
/* 1283 */       String sql = queryNode.getNodeContent();
/* 1284 */       if (isEmpty(sql)) {
/* 1285 */         throw new IllegalStateException("raw-sql for " + deployDesc.getFullName() + " has empty sql in the query element?");
/*      */       }
/*      */       
/* 1288 */       List<Dnode> columnMappings = rawSqlDnode.findAll("columnMapping", 1);
/*      */       
/* 1290 */       RawSqlBuilder rawSqlBuilder = RawSqlBuilder.parse(sql);
/* 1291 */       for (int j = 0; j < columnMappings.size(); j++) {
/* 1292 */         Dnode cm = columnMappings.get(j);
/* 1293 */         String column = cm.getAttribute("column");
/* 1294 */         String property = cm.getAttribute("property");
/* 1295 */         rawSqlBuilder.columnMapping(column, property);
/*      */       } 
/* 1297 */       RawSql rawSql = rawSqlBuilder.create();
/*      */       
/* 1299 */       DeployNamedQuery namedQuery = new DeployNamedQuery(name, rawSql);
/* 1300 */       deployDesc.add(namedQuery);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isEmpty(String s) {
/* 1305 */     return (s == null || s.trim().length() == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readXmlNamedQueries(DeployBeanDescriptor<?> deployDesc, Dnode entityXml) {
/* 1314 */     List<Dnode> namedQueries = entityXml.findAll("named-query", 1);
/*      */     
/* 1316 */     for (Dnode namedQueryXml : namedQueries) {
/*      */       
/* 1318 */       String name = namedQueryXml.getAttribute("name");
/* 1319 */       Dnode query = namedQueryXml.find("query");
/* 1320 */       if (query == null) {
/* 1321 */         logger.warning("orm.xml " + deployDesc.getFullName() + " named-query missing query element?");
/*      */         continue;
/*      */       } 
/* 1324 */       String oql = query.getNodeContent();
/*      */       
/* 1326 */       if (name == null || oql == null) {
/* 1327 */         logger.warning("orm.xml " + deployDesc.getFullName() + " named-query has no query content?");
/*      */         continue;
/*      */       } 
/* 1330 */       DeployNamedQuery q = new DeployNamedQuery(name, oql, null);
/* 1331 */       deployDesc.add(q);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BeanReflectFactory createReflectionFactory() {
/* 1339 */     return (BeanReflectFactory)new EnhanceBeanReflectFactory();
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
/*      */   private void setBeanReflect(DeployBeanDescriptor<?> desc) {
/* 1356 */     Class<?> beanType = desc.getBeanType();
/* 1357 */     Class<?> factType = desc.getFactoryType();
/*      */     
/* 1359 */     BeanReflect beanReflect = this.reflectFactory.create(beanType, factType);
/* 1360 */     desc.setBeanReflect(beanReflect);
/*      */     
/*      */     try {
/* 1363 */       Iterator<DeployBeanProperty> it = desc.propertiesAll();
/* 1364 */       while (it.hasNext()) {
/* 1365 */         DeployBeanProperty prop = it.next();
/* 1366 */         String propName = prop.getName();
/*      */         
/* 1368 */         if (desc.isAbstract() || beanReflect.isVanillaOnly()) {
/*      */ 
/*      */ 
/*      */           
/* 1372 */           prop.setGetter(ReflectGetter.create(prop));
/* 1373 */           prop.setSetter(ReflectSetter.create(prop));
/*      */           
/*      */           continue;
/*      */         } 
/* 1377 */         BeanReflectGetter getter = beanReflect.getGetter(propName);
/* 1378 */         BeanReflectSetter setter = beanReflect.getSetter(propName);
/* 1379 */         prop.setGetter(getter);
/* 1380 */         prop.setSetter(setter);
/* 1381 */         if (getter == null)
/*      */         {
/* 1383 */           String m = "BeanReflectGetter for " + prop.getFullBeanName() + " was not found?";
/* 1384 */           throw new RuntimeException(m);
/*      */         }
/*      */       
/*      */       }
/*      */     
/* 1389 */     } catch (IllegalArgumentException e) {
/* 1390 */       Class<?> superClass = desc.getBeanType().getSuperclass();
/* 1391 */       String msg = "Error with [" + desc.getFullName() + "] I believe it is not enhanced but it's superClass [" + superClass + "] is?" + " (You are not allowed to mix enhancement in a single inheritance hierarchy)";
/*      */       
/* 1393 */       throw new PersistenceException(msg, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setConcurrencyMode(DeployBeanDescriptor<?> desc) {
/* 1404 */     if (!desc.getConcurrencyMode().equals(ConcurrencyMode.ALL)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1409 */     if (checkForVersionProperties(desc)) {
/* 1410 */       desc.setConcurrencyMode(ConcurrencyMode.VERSION);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkForVersionProperties(DeployBeanDescriptor<?> desc) {
/* 1419 */     boolean hasVersionProperty = false;
/*      */     
/* 1421 */     List<DeployBeanProperty> props = desc.propertiesBase();
/* 1422 */     for (int i = 0; i < props.size(); i++) {
/* 1423 */       if (((DeployBeanProperty)props.get(i)).isVersionColumn()) {
/* 1424 */         hasVersionProperty = true;
/*      */       }
/*      */     } 
/*      */     
/* 1428 */     return hasVersionProperty;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean hasEntityBeanInterface(Class<?> beanClass) {
/* 1433 */     Class<?>[] interfaces = beanClass.getInterfaces();
/* 1434 */     for (int i = 0; i < interfaces.length; i++) {
/* 1435 */       if (interfaces[i].equals(EntityBean.class)) {
/* 1436 */         return true;
/*      */       }
/*      */     } 
/* 1439 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setEntityBeanClass(DeployBeanDescriptor<?> desc) {
/* 1447 */     Class<?> beanClass = desc.getBeanType();
/*      */     
/* 1449 */     if (desc.isAbstract()) {
/* 1450 */       if (hasEntityBeanInterface(beanClass)) {
/* 1451 */         checkEnhanced(desc, beanClass);
/*      */       } else {
/* 1453 */         checkSubclass(desc, beanClass);
/*      */       } 
/*      */       return;
/*      */     } 
/*      */     try {
/* 1458 */       Object testBean = null;
/*      */       try {
/* 1460 */         testBean = beanClass.newInstance();
/* 1461 */       } catch (InstantiationException e) {
/*      */         
/* 1463 */         logger.fine("no default constructor on " + beanClass + " e:" + e);
/* 1464 */       } catch (IllegalAccessException e) {
/*      */         
/* 1466 */         logger.fine("no default constructor on " + beanClass + " e:" + e);
/*      */       } 
/* 1468 */       if (!(testBean instanceof EntityBean)) {
/* 1469 */         checkSubclass(desc, beanClass);
/*      */       } else {
/*      */         
/* 1472 */         String className = beanClass.getName();
/*      */ 
/*      */         
/*      */         try {
/* 1476 */           String marker = ((EntityBean)testBean)._ebean_getMarker();
/* 1477 */           if (!marker.equals(className)) {
/* 1478 */             String msg = "Error with [" + desc.getFullName() + "] It has not been enhanced but it's superClass [" + beanClass.getSuperclass() + "] is?" + " (You are not allowed to mix enhancement in a single inheritance hierarchy)" + " marker[" + marker + "] className[" + className + "]";
/*      */ 
/*      */             
/* 1481 */             throw new PersistenceException(msg);
/*      */           } 
/* 1483 */         } catch (AbstractMethodError e) {
/* 1484 */           throw new PersistenceException("Old Ebean v1.0 enhancement detected in Ebean v1.1 - please do a clean enhancement.", e);
/*      */         } 
/*      */         
/* 1487 */         checkEnhanced(desc, beanClass);
/*      */       }
/*      */     
/* 1490 */     } catch (PersistenceException ex) {
/* 1491 */       throw ex;
/*      */     }
/* 1493 */     catch (Exception ex) {
/* 1494 */       throw new PersistenceException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkEnhanced(DeployBeanDescriptor<?> desc, Class<?> beanClass) {
/* 1500 */     checkInheritedClasses(true, beanClass);
/*      */     
/* 1502 */     desc.setFactoryType(beanClass);
/* 1503 */     if (!beanClass.getName().startsWith("com.avaje.ebean.meta")) {
/* 1504 */       this.enhancedClassCount++;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkSubclass(DeployBeanDescriptor<?> desc, Class<?> beanClass) {
/* 1510 */     checkInheritedClasses(false, beanClass);
/* 1511 */     desc.checkReadAndWriteMethods();
/*      */     
/* 1513 */     BeanDescriptor.EntityType entityType = desc.getEntityType();
/* 1514 */     if (BeanDescriptor.EntityType.XMLELEMENT.equals(entityType)) {
/* 1515 */       desc.setFactoryType(beanClass);
/*      */     } else {
/*      */       
/* 1518 */       if (!this.allowSubclassing) {
/* 1519 */         throw new PersistenceException("This configuration does not allow entity subclassing [" + beanClass + "]");
/*      */       }
/* 1521 */       this.subclassClassCount++;
/* 1522 */       Class<?> subClass = this.subClassManager.resolve(beanClass.getName());
/* 1523 */       desc.setFactoryType(subClass);
/* 1524 */       this.subclassedEntities.add(desc.getName());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkInheritedClasses(boolean ensureEnhanced, Class<?> beanClass) {
/* 1533 */     Class<?> superclass = beanClass.getSuperclass();
/* 1534 */     if (Object.class.equals(superclass)) {
/*      */       return;
/*      */     }
/*      */     
/* 1538 */     boolean isClassEnhanced = EntityBean.class.isAssignableFrom(superclass);
/*      */     
/* 1540 */     if (ensureEnhanced != isClassEnhanced) {
/*      */       String msg;
/* 1542 */       if (ensureEnhanced) {
/* 1543 */         msg = "Class [" + superclass + "] is not enhanced and [" + beanClass + "] is - (you can not mix!!)";
/*      */       } else {
/* 1545 */         msg = "Class [" + superclass + "] is enhanced and [" + beanClass + "] is not - (you can not mix!!)";
/*      */       } 
/* 1547 */       throw new IllegalStateException(msg);
/*      */     } 
/*      */ 
/*      */     
/* 1551 */     checkInheritedClasses(ensureEnhanced, superclass);
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class BeanDescComparator
/*      */     implements Comparator<BeanDescriptor<?>>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     private BeanDescComparator() {}
/*      */     
/*      */     public int compare(BeanDescriptor<?> o1, BeanDescriptor<?> o2) {
/* 1563 */       return o1.getName().compareTo(o2.getName());
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanDescriptorManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */