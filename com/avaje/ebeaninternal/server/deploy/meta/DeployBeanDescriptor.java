/*      */ package com.avaje.ebeaninternal.server.deploy.meta;
/*      */ 
/*      */ import com.avaje.ebean.Query;
/*      */ import com.avaje.ebean.config.TableName;
/*      */ import com.avaje.ebean.config.dbplatform.IdGenerator;
/*      */ import com.avaje.ebean.config.dbplatform.IdType;
/*      */ import com.avaje.ebean.event.BeanFinder;
/*      */ import com.avaje.ebean.event.BeanPersistController;
/*      */ import com.avaje.ebean.event.BeanPersistListener;
/*      */ import com.avaje.ebean.event.BeanQueryAdapter;
/*      */ import com.avaje.ebean.meta.MetaAutoFetchStatistic;
/*      */ import com.avaje.ebeaninternal.server.core.CacheOptions;
/*      */ import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*      */ import com.avaje.ebeaninternal.server.deploy.ChainedBeanPersistController;
/*      */ import com.avaje.ebeaninternal.server.deploy.ChainedBeanPersistListener;
/*      */ import com.avaje.ebeaninternal.server.deploy.ChainedBeanQueryAdapter;
/*      */ import com.avaje.ebeaninternal.server.deploy.CompoundUniqueContraint;
/*      */ import com.avaje.ebeaninternal.server.deploy.DRawSqlMeta;
/*      */ import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
/*      */ import com.avaje.ebeaninternal.server.deploy.DeployNamedUpdate;
/*      */ import com.avaje.ebeaninternal.server.deploy.InheritInfo;
/*      */ import com.avaje.ebeaninternal.server.reflect.BeanReflect;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
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
/*      */ public class DeployBeanDescriptor<T>
/*      */ {
/*      */   static class PropOrder
/*      */     implements Comparator<DeployBeanProperty>
/*      */   {
/*      */     public int compare(DeployBeanProperty o1, DeployBeanProperty o2) {
/*   68 */       int v2 = o1.getSortOrder();
/*   69 */       int v1 = o2.getSortOrder();
/*   70 */       return (v1 < v2) ? -1 : ((v1 == v2) ? 0 : 1);
/*      */     }
/*      */   }
/*      */   
/*   74 */   private static final PropOrder PROP_ORDER = new PropOrder();
/*      */   
/*      */   private static final String I_SCALAOBJECT = "scala.ScalaObject";
/*      */   
/*   78 */   private static final Logger logger = Logger.getLogger(DeployBeanDescriptor.class.getName());
/*      */   
/*   80 */   private static final String META_BEAN_PREFIX = MetaAutoFetchStatistic.class.getName().substring(0, 20);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   85 */   private LinkedHashMap<String, DeployBeanProperty> propMap = new LinkedHashMap<String, DeployBeanProperty>();
/*      */ 
/*      */   
/*      */   private final Class<T> beanType;
/*      */ 
/*      */   
/*      */   private BeanDescriptor.EntityType entityType;
/*      */ 
/*      */   
/*   94 */   private final Map<String, DeployNamedQuery> namedQueries = new LinkedHashMap<String, DeployNamedQuery>();
/*      */   
/*   96 */   private final Map<String, DeployNamedUpdate> namedUpdates = new LinkedHashMap<String, DeployNamedUpdate>();
/*      */   
/*   98 */   private final Map<String, DRawSqlMeta> rawSqlMetas = new LinkedHashMap<String, DRawSqlMeta>();
/*      */ 
/*      */ 
/*      */   
/*      */   private DeployBeanPropertyAssocOne<?> unidirectional;
/*      */ 
/*      */ 
/*      */   
/*      */   private IdType idType;
/*      */ 
/*      */ 
/*      */   
/*      */   private String idGeneratorName;
/*      */ 
/*      */ 
/*      */   
/*      */   private IdGenerator idGenerator;
/*      */ 
/*      */ 
/*      */   
/*      */   private String sequenceName;
/*      */ 
/*      */   
/*      */   private String ldapBaseDn;
/*      */ 
/*      */   
/*      */   private String[] ldapObjectclasses;
/*      */ 
/*      */   
/*      */   private String selectLastInsertedId;
/*      */ 
/*      */   
/*      */   private String lazyFetchIncludes;
/*      */ 
/*      */   
/*  133 */   private ConcurrencyMode concurrencyMode = ConcurrencyMode.ALL;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean updateChangesOnly;
/*      */ 
/*      */ 
/*      */   
/*      */   private String[] dependantTables;
/*      */ 
/*      */   
/*      */   private List<CompoundUniqueContraint> compoundUniqueConstraints;
/*      */ 
/*      */   
/*  147 */   private HashMap<String, String> extraAttrMap = new HashMap<String, String>();
/*      */ 
/*      */ 
/*      */   
/*      */   private String baseTable;
/*      */ 
/*      */ 
/*      */   
/*      */   private TableName baseTableFull;
/*      */ 
/*      */ 
/*      */   
/*      */   private BeanReflect beanReflect;
/*      */ 
/*      */ 
/*      */   
/*      */   private Class<?> factoryType;
/*      */ 
/*      */   
/*  166 */   private List<BeanPersistController> persistControllers = new ArrayList<BeanPersistController>();
/*  167 */   private List<BeanPersistListener<T>> persistListeners = new ArrayList<BeanPersistListener<T>>();
/*  168 */   private List<BeanQueryAdapter> queryAdapters = new ArrayList<BeanQueryAdapter>();
/*      */   
/*  170 */   private CacheOptions cacheOptions = new CacheOptions();
/*      */ 
/*      */ 
/*      */   
/*      */   private BeanFinder<T> beanFinder;
/*      */ 
/*      */ 
/*      */   
/*      */   private Query.UseIndex useIndex;
/*      */ 
/*      */ 
/*      */   
/*  182 */   private ArrayList<DeployTableJoin> tableJoinList = new ArrayList<DeployTableJoin>();
/*      */ 
/*      */ 
/*      */   
/*      */   private InheritInfo inheritInfo;
/*      */ 
/*      */   
/*      */   private String name;
/*      */ 
/*      */   
/*      */   private boolean processedRawSqlExtend;
/*      */ 
/*      */ 
/*      */   
/*      */   public DeployBeanDescriptor(Class<T> beanType) {
/*  197 */     this.beanType = beanType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAbstract() {
/*  204 */     return Modifier.isAbstract(this.beanType.getModifiers());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Query.UseIndex getUseIndex() {
/*  211 */     return this.useIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseIndex(Query.UseIndex useIndex) {
/*  218 */     this.useIndex = useIndex;
/*      */   }
/*      */   
/*      */   public boolean isScalaObject() {
/*  222 */     Class<?>[] interfaces = this.beanType.getInterfaces();
/*  223 */     for (int i = 0; i < interfaces.length; i++) {
/*  224 */       String iname = interfaces[i].getName();
/*  225 */       if ("scala.ScalaObject".equals(iname)) {
/*  226 */         return true;
/*      */       }
/*      */     } 
/*  229 */     return false;
/*      */   }
/*      */   
/*      */   public Collection<DRawSqlMeta> getRawSqlMeta() {
/*  233 */     if (!this.processedRawSqlExtend) {
/*  234 */       rawSqlProcessExtend();
/*  235 */       this.processedRawSqlExtend = true;
/*      */     } 
/*  237 */     return this.rawSqlMetas.values();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void rawSqlProcessExtend() {
/*  246 */     for (DRawSqlMeta rawSqlMeta : this.rawSqlMetas.values()) {
/*  247 */       String extend = rawSqlMeta.getExtend();
/*  248 */       if (extend != null) {
/*  249 */         DRawSqlMeta parentQuery = this.rawSqlMetas.get(extend);
/*  250 */         if (parentQuery == null) {
/*  251 */           throw new RuntimeException("parent query [" + extend + "] not found for sql-select " + rawSqlMeta.getName());
/*      */         }
/*  253 */         rawSqlMeta.extend(parentQuery);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public DeployBeanTable createDeployBeanTable() {
/*  260 */     DeployBeanTable beanTable = new DeployBeanTable(getBeanType());
/*  261 */     beanTable.setBaseTable(this.baseTable);
/*  262 */     beanTable.setIdProperties(propertiesId());
/*      */     
/*  264 */     return beanTable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkReadAndWriteMethods() {
/*  273 */     if (isMeta()) {
/*  274 */       return true;
/*      */     }
/*  276 */     boolean missingMethods = false;
/*      */     
/*  278 */     Iterator<DeployBeanProperty> it = this.propMap.values().iterator();
/*  279 */     while (it.hasNext()) {
/*  280 */       DeployBeanProperty prop = it.next();
/*  281 */       if (!prop.isTransient()) {
/*  282 */         String m = "";
/*  283 */         if (prop.getReadMethod() == null) {
/*  284 */           m = m + " missing readMethod ";
/*      */         }
/*  286 */         if (prop.getWriteMethod() == null) {
/*  287 */           m = m + " missing writeMethod ";
/*      */         }
/*  289 */         if (!"".equals(m)) {
/*  290 */           m = m + ". Should it be transient?";
/*  291 */           String msg = "Bean property " + getFullName() + "." + prop.getName() + " has " + m;
/*  292 */           logger.log(Level.SEVERE, msg);
/*  293 */           missingMethods = true;
/*      */         } 
/*      */       } 
/*      */     } 
/*  297 */     return !missingMethods;
/*      */   }
/*      */   
/*      */   public void setEntityType(BeanDescriptor.EntityType entityType) {
/*  301 */     this.entityType = entityType;
/*      */   }
/*      */   
/*      */   public boolean isEmbedded() {
/*  305 */     return BeanDescriptor.EntityType.EMBEDDED.equals(this.entityType);
/*      */   }
/*      */   
/*      */   public boolean isBaseTableType() {
/*  309 */     BeanDescriptor.EntityType et = getEntityType();
/*  310 */     return BeanDescriptor.EntityType.ORM.equals(et);
/*      */   }
/*      */   
/*      */   public BeanDescriptor.EntityType getEntityType() {
/*  314 */     if (this.entityType == null) {
/*  315 */       this.entityType = isMeta() ? BeanDescriptor.EntityType.META : BeanDescriptor.EntityType.ORM;
/*      */     }
/*  317 */     return this.entityType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isMeta() {
/*  328 */     return this.beanType.getName().startsWith(META_BEAN_PREFIX);
/*      */   }
/*      */   
/*      */   public void add(DRawSqlMeta rawSqlMeta) {
/*  332 */     this.rawSqlMetas.put(rawSqlMeta.getName(), rawSqlMeta);
/*  333 */     if ("default".equals(rawSqlMeta.getName())) {
/*  334 */       setEntityType(BeanDescriptor.EntityType.SQL);
/*      */     }
/*      */   }
/*      */   
/*      */   public void add(DeployNamedUpdate namedUpdate) {
/*  339 */     this.namedUpdates.put(namedUpdate.getName(), namedUpdate);
/*      */   }
/*      */   
/*      */   public void add(DeployNamedQuery namedQuery) {
/*  343 */     this.namedQueries.put(namedQuery.getName(), namedQuery);
/*  344 */     if ("default".equals(namedQuery.getName())) {
/*  345 */       setEntityType(BeanDescriptor.EntityType.SQL);
/*      */     }
/*      */   }
/*      */   
/*      */   public Map<String, DeployNamedQuery> getNamedQueries() {
/*  350 */     return this.namedQueries;
/*      */   }
/*      */   
/*      */   public Map<String, DeployNamedUpdate> getNamedUpdates() {
/*  354 */     return this.namedUpdates;
/*      */   }
/*      */   
/*      */   public BeanReflect getBeanReflect() {
/*  358 */     return this.beanReflect;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<T> getBeanType() {
/*  365 */     return this.beanType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?> getFactoryType() {
/*  372 */     return this.factoryType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFactoryType(Class<?> factoryType) {
/*  382 */     this.factoryType = factoryType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBeanReflect(BeanReflect beanReflect) {
/*  390 */     this.beanReflect = beanReflect;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InheritInfo getInheritInfo() {
/*  398 */     return this.inheritInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInheritInfo(InheritInfo inheritInfo) {
/*  405 */     this.inheritInfo = inheritInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CacheOptions getCacheOptions() {
/*  412 */     return this.cacheOptions;
/*      */   }
/*      */   
/*      */   public boolean isNaturalKeyProperty(String name) {
/*  416 */     return name.equals(this.cacheOptions.getNaturalKey());
/*      */   }
/*      */   
/*      */   public DeployBeanPropertyAssocOne<?> getUnidirectional() {
/*  420 */     return this.unidirectional;
/*      */   }
/*      */   
/*      */   public void setUnidirectional(DeployBeanPropertyAssocOne<?> unidirectional) {
/*  424 */     this.unidirectional = unidirectional;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConcurrencyMode getConcurrencyMode() {
/*  431 */     return this.concurrencyMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConcurrencyMode(ConcurrencyMode concurrencyMode) {
/*  438 */     this.concurrencyMode = concurrencyMode;
/*      */   }
/*      */   
/*      */   public String getLdapBaseDn() {
/*  442 */     return this.ldapBaseDn;
/*      */   }
/*      */   
/*      */   public void setLdapBaseDn(String ldapBaseDn) {
/*  446 */     this.ldapBaseDn = ldapBaseDn;
/*      */   }
/*      */   
/*      */   public String[] getLdapObjectclasses() {
/*  450 */     return this.ldapObjectclasses;
/*      */   }
/*      */   
/*      */   public void setLdapObjectclasses(String[] ldapObjectclasses) {
/*  454 */     this.ldapObjectclasses = ldapObjectclasses;
/*      */   }
/*      */   
/*      */   public boolean isUpdateChangesOnly() {
/*  458 */     return this.updateChangesOnly;
/*      */   }
/*      */   
/*      */   public void setUpdateChangesOnly(boolean updateChangesOnly) {
/*  462 */     this.updateChangesOnly = updateChangesOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getDependantTables() {
/*  470 */     return this.dependantTables;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addCompoundUniqueConstraint(CompoundUniqueContraint c) {
/*  477 */     if (this.compoundUniqueConstraints == null) {
/*  478 */       this.compoundUniqueConstraints = new ArrayList<CompoundUniqueContraint>();
/*      */     }
/*  480 */     this.compoundUniqueConstraints.add(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompoundUniqueContraint[] getCompoundUniqueConstraints() {
/*  487 */     if (this.compoundUniqueConstraints == null) {
/*  488 */       return null;
/*      */     }
/*  490 */     return this.compoundUniqueConstraints.<CompoundUniqueContraint>toArray(new CompoundUniqueContraint[this.compoundUniqueConstraints.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDependantTables(String[] dependantTables) {
/*  499 */     this.dependantTables = dependantTables;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanFinder<T> getBeanFinder() {
/*  506 */     return this.beanFinder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBeanFinder(BeanFinder<T> beanFinder) {
/*  514 */     this.beanFinder = beanFinder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPersistController getPersistController() {
/*  521 */     if (this.persistControllers.size() == 0)
/*  522 */       return null; 
/*  523 */     if (this.persistControllers.size() == 1) {
/*  524 */       return this.persistControllers.get(0);
/*      */     }
/*  526 */     return (BeanPersistController)new ChainedBeanPersistController(this.persistControllers);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPersistListener<T> getPersistListener() {
/*  534 */     if (this.persistListeners.size() == 0)
/*  535 */       return null; 
/*  536 */     if (this.persistListeners.size() == 1) {
/*  537 */       return this.persistListeners.get(0);
/*      */     }
/*  539 */     return (BeanPersistListener<T>)new ChainedBeanPersistListener(this.persistListeners);
/*      */   }
/*      */ 
/*      */   
/*      */   public BeanQueryAdapter getQueryAdapter() {
/*  544 */     if (this.queryAdapters.size() == 0)
/*  545 */       return null; 
/*  546 */     if (this.queryAdapters.size() == 1) {
/*  547 */       return this.queryAdapters.get(0);
/*      */     }
/*  549 */     return (BeanQueryAdapter)new ChainedBeanQueryAdapter(this.queryAdapters);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPersistController(BeanPersistController controller) {
/*  557 */     this.persistControllers.add(controller);
/*      */   }
/*      */   
/*      */   public void addPersistListener(BeanPersistListener<T> listener) {
/*  561 */     this.persistListeners.add(listener);
/*      */   }
/*      */   
/*      */   public void addQueryAdapter(BeanQueryAdapter queryAdapter) {
/*  565 */     this.queryAdapters.add(queryAdapter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUseIdGenerator() {
/*  576 */     return (this.idType == IdType.GENERATOR);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getBaseTable() {
/*  584 */     return this.baseTable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TableName getBaseTableFull() {
/*  591 */     return this.baseTableFull;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseTable(TableName baseTableFull) {
/*  599 */     this.baseTableFull = baseTableFull;
/*  600 */     this.baseTable = (baseTableFull == null) ? null : baseTableFull.getQualifiedName();
/*      */   }
/*      */ 
/*      */   
/*      */   public void sortProperties() {
/*  605 */     ArrayList<DeployBeanProperty> list = new ArrayList<DeployBeanProperty>();
/*  606 */     list.addAll(this.propMap.values());
/*      */     
/*  608 */     Collections.sort(list, PROP_ORDER);
/*      */     
/*  610 */     this.propMap = new LinkedHashMap<String, DeployBeanProperty>(list.size());
/*  611 */     for (int i = 0; i < list.size(); i++) {
/*  612 */       addBeanProperty(list.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DeployBeanProperty addBeanProperty(DeployBeanProperty prop) {
/*  620 */     return this.propMap.put(prop.getName(), prop);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DeployBeanProperty getBeanProperty(String propName) {
/*  627 */     return this.propMap.get(propName);
/*      */   }
/*      */   
/*      */   public Map<String, String> getExtraAttributeMap() {
/*  631 */     return this.extraAttrMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getExtraAttribute(String key) {
/*  638 */     return this.extraAttrMap.get(key);
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
/*      */   public void setExtraAttribute(String key, String value) {
/*  650 */     this.extraAttrMap.put(key, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFullName() {
/*  661 */     return this.beanType.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  668 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setName(String name) {
/*  675 */     this.name = name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IdType getIdType() {
/*  682 */     return this.idType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIdType(IdType idType) {
/*  689 */     this.idType = idType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSequenceName() {
/*  696 */     return this.sequenceName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSequenceName(String sequenceName) {
/*  703 */     this.sequenceName = sequenceName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSelectLastInsertedId() {
/*  713 */     return this.selectLastInsertedId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectLastInsertedId(String selectLastInsertedId) {
/*  720 */     this.selectLastInsertedId = selectLastInsertedId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getIdGeneratorName() {
/*  728 */     return this.idGeneratorName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIdGeneratorName(String idGeneratorName) {
/*  735 */     this.idGeneratorName = idGeneratorName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IdGenerator getIdGenerator() {
/*  742 */     return this.idGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIdGenerator(IdGenerator idGenerator) {
/*  749 */     this.idGenerator = idGenerator;
/*  750 */     if (idGenerator != null && idGenerator.isDbSequence()) {
/*  751 */       setSequenceName(idGenerator.getName());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLazyFetchIncludes() {
/*  759 */     return this.lazyFetchIncludes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLazyFetchIncludes(String lazyFetchIncludes) {
/*  768 */     if (lazyFetchIncludes != null && lazyFetchIncludes.length() > 0) {
/*  769 */       this.lazyFetchIncludes = lazyFetchIncludes;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  777 */     return getFullName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTableJoin(DeployTableJoin join) {
/*  784 */     this.tableJoinList.add(join);
/*      */   }
/*      */   
/*      */   public List<DeployTableJoin> getTableJoins() {
/*  788 */     return this.tableJoinList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator<DeployBeanProperty> propertiesAll() {
/*  795 */     return this.propMap.values().iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultSelectClause() {
/*  803 */     StringBuilder sb = new StringBuilder();
/*      */     
/*  805 */     boolean hasLazyFetch = false;
/*      */     
/*  807 */     Iterator<DeployBeanProperty> it = this.propMap.values().iterator();
/*  808 */     while (it.hasNext()) {
/*  809 */       DeployBeanProperty prop = it.next();
/*  810 */       if (prop.isTransient())
/*      */         continue; 
/*  812 */       if (prop instanceof DeployBeanPropertyAssocMany) {
/*      */         continue;
/*      */       }
/*  815 */       if (prop.isFetchEager()) {
/*  816 */         sb.append(prop.getName()).append(","); continue;
/*      */       } 
/*  818 */       hasLazyFetch = true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  823 */     if (!hasLazyFetch) {
/*  824 */       return null;
/*      */     }
/*  826 */     String selectClause = sb.toString();
/*  827 */     return selectClause.substring(0, selectClause.length() - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getDefaultSelectDbArray(Set<String> defaultSelect) {
/*  835 */     ArrayList<String> list = new ArrayList<String>();
/*  836 */     for (DeployBeanProperty p : this.propMap.values()) {
/*  837 */       if (defaultSelect != null) {
/*  838 */         if (defaultSelect.contains(p.getName()))
/*      */         {
/*  840 */           list.add(p.getDbColumn()); }  continue;
/*      */       } 
/*  842 */       if (!p.isTransient() && p.isDbRead())
/*      */       {
/*  844 */         list.add(p.getDbColumn());
/*      */       }
/*      */     } 
/*  847 */     return list.<String>toArray(new String[list.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> parseDefaultSelectClause(String rawList) {
/*  855 */     if (rawList == null) {
/*  856 */       return null;
/*      */     }
/*      */     
/*  859 */     String[] res = rawList.split(",");
/*      */     
/*  861 */     LinkedHashSet<String> set = new LinkedHashSet<String>(res.length + 3);
/*      */     
/*  863 */     String temp = null;
/*  864 */     for (int i = 0; i < res.length; i++) {
/*  865 */       temp = res[i].trim();
/*  866 */       if (temp.length() > 0) {
/*  867 */         set.add(temp);
/*      */       }
/*      */     } 
/*  870 */     return Collections.unmodifiableSet(set);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSinglePrimaryKeyColumn() {
/*  878 */     List<DeployBeanProperty> ids = propertiesId();
/*  879 */     if (ids.size() == 1) {
/*  880 */       DeployBeanProperty p = ids.get(0);
/*  881 */       if (p instanceof DeployBeanPropertyAssoc)
/*      */       {
/*  883 */         return null;
/*      */       }
/*  885 */       return p.getDbColumn();
/*      */     } 
/*      */     
/*  888 */     return null;
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
/*      */   public List<DeployBeanProperty> propertiesId() {
/*  900 */     ArrayList<DeployBeanProperty> list = new ArrayList<DeployBeanProperty>(2);
/*      */     
/*  902 */     Iterator<DeployBeanProperty> it = this.propMap.values().iterator();
/*  903 */     while (it.hasNext()) {
/*  904 */       DeployBeanProperty prop = it.next();
/*  905 */       if (prop.isId()) {
/*  906 */         list.add(prop);
/*      */       }
/*      */     } 
/*      */     
/*  910 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public DeployBeanPropertyAssocOne<?> findJoinToTable(String tableName) {
/*  915 */     List<DeployBeanPropertyAssocOne<?>> assocOne = propertiesAssocOne();
/*  916 */     for (DeployBeanPropertyAssocOne<?> prop : assocOne) {
/*  917 */       DeployTableJoin tableJoin = prop.getTableJoin();
/*  918 */       if (tableJoin != null && tableJoin.getTable().equalsIgnoreCase(tableName)) {
/*  919 */         return prop;
/*      */       }
/*      */     } 
/*  922 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DeployBeanPropertyAssocOne<?>> propertiesAssocOne() {
/*  931 */     ArrayList<DeployBeanPropertyAssocOne<?>> list = new ArrayList<DeployBeanPropertyAssocOne<?>>();
/*      */     
/*  933 */     Iterator<DeployBeanProperty> it = this.propMap.values().iterator();
/*  934 */     while (it.hasNext()) {
/*  935 */       DeployBeanProperty prop = it.next();
/*  936 */       if (prop instanceof DeployBeanPropertyAssocOne && 
/*  937 */         !prop.isEmbedded()) {
/*  938 */         list.add((DeployBeanPropertyAssocOne)prop);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  943 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DeployBeanPropertyAssocMany<?>> propertiesAssocMany() {
/*  952 */     ArrayList<DeployBeanPropertyAssocMany<?>> list = new ArrayList<DeployBeanPropertyAssocMany<?>>();
/*      */     
/*  954 */     Iterator<DeployBeanProperty> it = this.propMap.values().iterator();
/*  955 */     while (it.hasNext()) {
/*  956 */       DeployBeanProperty prop = it.next();
/*  957 */       if (prop instanceof DeployBeanPropertyAssocMany) {
/*  958 */         list.add((DeployBeanPropertyAssocMany)prop);
/*      */       }
/*      */     } 
/*      */     
/*  962 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DeployBeanProperty> propertiesVersion() {
/*  972 */     ArrayList<DeployBeanProperty> list = new ArrayList<DeployBeanProperty>();
/*      */     
/*  974 */     Iterator<DeployBeanProperty> it = this.propMap.values().iterator();
/*  975 */     while (it.hasNext()) {
/*  976 */       DeployBeanProperty prop = it.next();
/*      */       
/*  978 */       if (prop instanceof DeployBeanPropertyAssoc) {
/*      */         continue;
/*      */       }
/*  981 */       if (!prop.isId() && prop.isVersionColumn()) {
/*  982 */         list.add(prop);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  987 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DeployBeanProperty> propertiesBase() {
/*  995 */     ArrayList<DeployBeanProperty> list = new ArrayList<DeployBeanProperty>();
/*      */     
/*  997 */     Iterator<DeployBeanProperty> it = this.propMap.values().iterator();
/*  998 */     while (it.hasNext()) {
/*  999 */       DeployBeanProperty prop = it.next();
/*      */       
/* 1001 */       if (prop instanceof DeployBeanPropertyAssoc) {
/*      */         continue;
/*      */       }
/* 1004 */       if (!prop.isId()) {
/* 1005 */         list.add(prop);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1010 */     return list;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanDescriptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */