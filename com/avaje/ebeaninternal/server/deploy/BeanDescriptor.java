/*      */ package com.avaje.ebeaninternal.server.deploy;
/*      */ 
/*      */ import com.avaje.ebean.InvalidValue;
/*      */ import com.avaje.ebean.Query;
/*      */ import com.avaje.ebean.SqlUpdate;
/*      */ import com.avaje.ebean.Transaction;
/*      */ import com.avaje.ebean.bean.BeanCollection;
/*      */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*      */ import com.avaje.ebean.bean.EntityBean;
/*      */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*      */ import com.avaje.ebean.bean.PersistenceContext;
/*      */ import com.avaje.ebean.cache.ServerCache;
/*      */ import com.avaje.ebean.cache.ServerCacheManager;
/*      */ import com.avaje.ebean.config.EncryptKey;
/*      */ import com.avaje.ebean.config.dbplatform.IdGenerator;
/*      */ import com.avaje.ebean.config.dbplatform.IdType;
/*      */ import com.avaje.ebean.event.BeanFinder;
/*      */ import com.avaje.ebean.event.BeanPersistController;
/*      */ import com.avaje.ebean.event.BeanPersistListener;
/*      */ import com.avaje.ebean.event.BeanQueryAdapter;
/*      */ import com.avaje.ebean.text.TextException;
/*      */ import com.avaje.ebean.text.json.JsonWriteBeanVisitor;
/*      */ import com.avaje.ebean.text.json.JsonWriter;
/*      */ import com.avaje.ebean.validation.factory.Validator;
/*      */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*      */ import com.avaje.ebeaninternal.api.SpiQuery;
/*      */ import com.avaje.ebeaninternal.api.SpiUpdatePlan;
/*      */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*      */ import com.avaje.ebeaninternal.server.cache.CachedBeanData;
/*      */ import com.avaje.ebeaninternal.server.cache.CachedBeanDataFromBean;
/*      */ import com.avaje.ebeaninternal.server.cache.CachedBeanDataToBean;
/*      */ import com.avaje.ebeaninternal.server.cache.CachedBeanDataUpdate;
/*      */ import com.avaje.ebeaninternal.server.cache.CachedManyIds;
/*      */ import com.avaje.ebeaninternal.server.core.CacheOptions;
/*      */ import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
/*      */ import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
/*      */ import com.avaje.ebeaninternal.server.core.InternString;
/*      */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*      */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyLists;
/*      */ import com.avaje.ebeaninternal.server.el.ElComparator;
/*      */ import com.avaje.ebeaninternal.server.el.ElComparatorCompound;
/*      */ import com.avaje.ebeaninternal.server.el.ElComparatorProperty;
/*      */ import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
/*      */ import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
/*      */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*      */ import com.avaje.ebeaninternal.server.ldap.LdapPersistenceException;
/*      */ import com.avaje.ebeaninternal.server.persist.DmlUtil;
/*      */ import com.avaje.ebeaninternal.server.query.CQueryPlan;
/*      */ import com.avaje.ebeaninternal.server.query.SplitName;
/*      */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
/*      */ import com.avaje.ebeaninternal.server.reflect.BeanReflect;
/*      */ import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
/*      */ import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
/*      */ import com.avaje.ebeaninternal.server.type.DataBind;
/*      */ import com.avaje.ebeaninternal.server.type.TypeManager;
/*      */ import com.avaje.ebeaninternal.util.SortByClause;
/*      */ import com.avaje.ebeaninternal.util.SortByClauseParser;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.naming.InvalidNameException;
/*      */ import javax.naming.directory.Attributes;
/*      */ import javax.naming.directory.BasicAttribute;
/*      */ import javax.naming.directory.BasicAttributes;
/*      */ import javax.naming.ldap.LdapName;
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
/*      */ public class BeanDescriptor<T>
/*      */ {
/*  109 */   private static final Logger logger = Logger.getLogger(BeanDescriptor.class.getName());
/*      */   
/*  111 */   private final ConcurrentHashMap<Integer, SpiUpdatePlan> updatePlanCache = new ConcurrentHashMap<Integer, SpiUpdatePlan>();
/*      */   
/*  113 */   private final ConcurrentHashMap<Integer, CQueryPlan> queryPlanCache = new ConcurrentHashMap<Integer, CQueryPlan>();
/*      */   
/*  115 */   private final ConcurrentHashMap<String, ElPropertyValue> elGetCache = new ConcurrentHashMap<String, ElPropertyValue>(); private final String serverName; private final EntityType entityType; private final IdType idType; private final IdGenerator idGenerator; private final String sequenceName; private final String ldapBaseDn; private final String[] ldapObjectclasses; private final String selectLastInsertedId; private final boolean autoFetchTunable; private final boolean cacheSharableBeans; private final String lazyFetchIncludes; private final ConcurrencyMode concurrencyMode; private final String[] dependantTables; private final CompoundUniqueContraint[] compoundUniqueConstraints; private final Map<String, String> extraAttrMap; private final String baseTable; private final BeanReflect beanReflect; private final LinkedHashMap<String, BeanProperty> propMap; private final LinkedHashMap<String, BeanProperty> propMapByDbColumn; private final Class<T> beanType; private final BeanDescriptorMap owner;
/*      */   private final Class<?> factoryType;
/*  117 */   private final ConcurrentHashMap<String, ElComparator<T>> comparatorCache = new ConcurrentHashMap<String, ElComparator<T>>(); private final boolean enhancedBean; private volatile BeanPersistController persistController; private volatile BeanPersistListener<T> persistListener; private volatile BeanQueryAdapter queryAdapter; private final BeanFinder<T> beanFinder; private final TableJoin[] derivedTableJoins; private final InheritInfo inheritInfo; private final BeanProperty[] propertiesId; private final BeanProperty[] propertiesVersion; private final BeanProperty propertiesNaturalKey; private final BeanProperty[] propertiesLocal; private final BeanPropertyAssocOne<?> unidirectional; private final int namesOfManyPropsHash; private final Set<String> namesOfManyProps; private final BeanProperty[] propertiesNonMany; private final BeanPropertyAssocMany<?>[] propertiesMany; private final BeanPropertyAssocMany<?>[] propertiesManySave; private final BeanPropertyAssocMany<?>[] propertiesManyDelete; private final BeanPropertyAssocMany<?>[] propertiesManyToMany; private final BeanPropertyAssocOne<?>[] propertiesOne; private final BeanPropertyAssocOne<?>[] propertiesOneImported; private final BeanPropertyAssocOne<?>[] propertiesOneImportedSave; private final BeanPropertyAssocOne<?>[] propertiesOneImportedDelete; private final BeanPropertyAssocOne<?>[] propertiesOneExported; private final BeanPropertyAssocOne<?>[] propertiesOneExportedSave; private final BeanPropertyAssocOne<?>[] propertiesOneExportedDelete; private final BeanPropertyAssocOne<?>[] propertiesEmbedded; private final BeanProperty[] propertiesBaseScalar; private final BeanPropertyCompound[] propertiesBaseCompound; private final BeanProperty[] propertiesTransient; final BeanProperty[] propertiesNonTransient; private final BeanProperty propertyFirstVersion; private final BeanProperty propertySingleId; private final String fullName; private final Map<String, DeployNamedQuery> namedQueries; private final Map<String, DeployNamedUpdate> namedUpdates; private final boolean hasLocalValidation; private final boolean hasCascadeValidation; private final BeanProperty[] propertiesValidationLocal; private final BeanProperty[] propertiesValidationCascade; private final Validator[] beanValidators;
/*      */   private boolean saveRecurseSkippable;
/*  119 */   private final ConcurrentHashMap<String, BeanFkeyProperty> fkeyMap = new ConcurrentHashMap<String, BeanFkeyProperty>(); private boolean deleteRecurseSkippable; private final TypeManager typeManager; private final IdBinder idBinder; private String idBinderInLHSSql; private String idBinderIdSql; private String deleteByIdSql; private String deleteByIdInSql; private final String name; private final String baseTableAlias; private final boolean updateChangesOnly; private final ServerCacheManager cacheManager; private final CacheOptions cacheOptions; private final String defaultSelectClause; private final Set<String> defaultSelectClauseSet; private final String[] defaultSelectDbArray; private final String descriptorId; private final Query.UseIndex useIndex; private SpiEbeanServer ebeanServer; private ServerCache beanCache; private ServerCache naturalKeyCache;
/*      */   private ServerCache queryCache;
/*      */   
/*  122 */   public enum EntityType { ORM, EMBEDDED, SQL, META, LDAP, XMLELEMENT; }
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
/*      */   public BeanDescriptor(BeanDescriptorMap owner, TypeManager typeManager, DeployBeanDescriptor<T> deploy, String descriptorId) {
/*  413 */     this.owner = owner;
/*  414 */     this.cacheManager = owner.getCacheManager();
/*  415 */     this.serverName = owner.getServerName();
/*  416 */     this.entityType = deploy.getEntityType();
/*  417 */     this.name = InternString.intern(deploy.getName());
/*  418 */     this.baseTableAlias = "t0";
/*  419 */     this.fullName = InternString.intern(deploy.getFullName());
/*  420 */     this.descriptorId = descriptorId;
/*      */     
/*  422 */     this.useIndex = deploy.getUseIndex();
/*  423 */     this.typeManager = typeManager;
/*  424 */     this.beanType = deploy.getBeanType();
/*  425 */     this.factoryType = deploy.getFactoryType();
/*  426 */     this.enhancedBean = this.beanType.equals(this.factoryType);
/*  427 */     this.namedQueries = deploy.getNamedQueries();
/*  428 */     this.namedUpdates = deploy.getNamedUpdates();
/*      */     
/*  430 */     this.inheritInfo = deploy.getInheritInfo();
/*      */     
/*  432 */     this.beanFinder = deploy.getBeanFinder();
/*  433 */     this.persistController = deploy.getPersistController();
/*  434 */     this.persistListener = deploy.getPersistListener();
/*  435 */     this.queryAdapter = deploy.getQueryAdapter();
/*  436 */     this.cacheOptions = deploy.getCacheOptions();
/*      */     
/*  438 */     this.defaultSelectClause = deploy.getDefaultSelectClause();
/*  439 */     this.defaultSelectClauseSet = deploy.parseDefaultSelectClause(this.defaultSelectClause);
/*  440 */     this.defaultSelectDbArray = deploy.getDefaultSelectDbArray(this.defaultSelectClauseSet);
/*      */     
/*  442 */     this.idType = deploy.getIdType();
/*  443 */     this.idGenerator = deploy.getIdGenerator();
/*  444 */     this.ldapBaseDn = deploy.getLdapBaseDn();
/*  445 */     this.ldapObjectclasses = deploy.getLdapObjectclasses();
/*  446 */     this.sequenceName = deploy.getSequenceName();
/*  447 */     this.selectLastInsertedId = deploy.getSelectLastInsertedId();
/*  448 */     this.lazyFetchIncludes = InternString.intern(deploy.getLazyFetchIncludes());
/*  449 */     this.concurrencyMode = deploy.getConcurrencyMode();
/*  450 */     this.updateChangesOnly = deploy.isUpdateChangesOnly();
/*      */     
/*  452 */     this.dependantTables = deploy.getDependantTables();
/*  453 */     this.compoundUniqueConstraints = deploy.getCompoundUniqueConstraints();
/*      */     
/*  455 */     this.extraAttrMap = deploy.getExtraAttributeMap();
/*      */     
/*  457 */     this.baseTable = InternString.intern(deploy.getBaseTable());
/*      */     
/*  459 */     this.beanReflect = deploy.getBeanReflect();
/*      */     
/*  461 */     this.autoFetchTunable = (EntityType.ORM.equals(this.entityType) && this.beanFinder == null);
/*      */ 
/*      */     
/*  464 */     DeployBeanPropertyLists listHelper = new DeployBeanPropertyLists(owner, this, deploy);
/*      */     
/*  466 */     this.propMap = listHelper.getPropertyMap();
/*  467 */     this.propMapByDbColumn = getReverseMap(this.propMap);
/*  468 */     this.propertiesTransient = listHelper.getTransients();
/*  469 */     this.propertiesNonTransient = listHelper.getNonTransients();
/*  470 */     this.propertiesBaseScalar = listHelper.getBaseScalar();
/*  471 */     this.propertiesBaseCompound = listHelper.getBaseCompound();
/*  472 */     this.propertiesId = listHelper.getId();
/*  473 */     this.propertiesNaturalKey = listHelper.getNaturalKey();
/*  474 */     this.propertiesVersion = listHelper.getVersion();
/*  475 */     this.propertiesEmbedded = (BeanPropertyAssocOne<?>[])listHelper.getEmbedded();
/*  476 */     this.propertiesLocal = listHelper.getLocal();
/*  477 */     this.unidirectional = listHelper.getUnidirectional();
/*  478 */     this.propertiesOne = (BeanPropertyAssocOne<?>[])listHelper.getOnes();
/*  479 */     this.propertiesOneExported = (BeanPropertyAssocOne<?>[])listHelper.getOneExported();
/*  480 */     this.propertiesOneExportedSave = (BeanPropertyAssocOne<?>[])listHelper.getOneExportedSave();
/*  481 */     this.propertiesOneExportedDelete = (BeanPropertyAssocOne<?>[])listHelper.getOneExportedDelete();
/*  482 */     this.propertiesOneImported = (BeanPropertyAssocOne<?>[])listHelper.getOneImported();
/*  483 */     this.propertiesOneImportedSave = (BeanPropertyAssocOne<?>[])listHelper.getOneImportedSave();
/*  484 */     this.propertiesOneImportedDelete = (BeanPropertyAssocOne<?>[])listHelper.getOneImportedDelete();
/*      */     
/*  486 */     this.propertiesMany = (BeanPropertyAssocMany<?>[])listHelper.getMany();
/*  487 */     this.propertiesNonMany = listHelper.getNonMany();
/*  488 */     this.propertiesManySave = (BeanPropertyAssocMany<?>[])listHelper.getManySave();
/*  489 */     this.propertiesManyDelete = (BeanPropertyAssocMany<?>[])listHelper.getManyDelete();
/*  490 */     this.propertiesManyToMany = (BeanPropertyAssocMany<?>[])listHelper.getManyToMany();
/*  491 */     boolean noRelationships = (this.propertiesOne.length + this.propertiesMany.length == 0);
/*  492 */     this.cacheSharableBeans = (noRelationships && this.cacheOptions.isReadOnly());
/*      */     
/*  494 */     this.namesOfManyProps = deriveManyPropNames();
/*  495 */     this.namesOfManyPropsHash = this.namesOfManyProps.hashCode();
/*      */     
/*  497 */     this.derivedTableJoins = listHelper.getTableJoin();
/*  498 */     this.propertyFirstVersion = listHelper.getFirstVersion();
/*      */     
/*  500 */     if (this.propertiesId.length == 1) {
/*  501 */       this.propertySingleId = this.propertiesId[0];
/*      */     } else {
/*  503 */       this.propertySingleId = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  511 */     this.saveRecurseSkippable = (0 == this.propertiesOneExportedSave.length + this.propertiesOneImportedSave.length + this.propertiesManySave.length);
/*      */ 
/*      */ 
/*      */     
/*  515 */     this.deleteRecurseSkippable = (0 == this.propertiesOneExportedDelete.length + this.propertiesOneImportedDelete.length + this.propertiesManyDelete.length);
/*      */     
/*  517 */     this.propertiesValidationLocal = listHelper.getPropertiesWithValidators(false);
/*  518 */     this.propertiesValidationCascade = listHelper.getPropertiesWithValidators(true);
/*  519 */     this.beanValidators = listHelper.getBeanValidators();
/*  520 */     this.hasLocalValidation = (this.propertiesValidationLocal.length > 0 || this.beanValidators.length > 0);
/*  521 */     this.hasCascadeValidation = (this.propertiesValidationCascade.length > 0 || this.beanValidators.length > 0);
/*      */ 
/*      */     
/*  524 */     this.idBinder = owner.createIdBinder(this.propertiesId);
/*      */   }
/*      */ 
/*      */   
/*      */   private LinkedHashMap<String, BeanProperty> getReverseMap(LinkedHashMap<String, BeanProperty> propMap) {
/*  529 */     LinkedHashMap<String, BeanProperty> revMap = new LinkedHashMap<String, BeanProperty>(propMap.size() * 2);
/*      */     
/*  531 */     for (BeanProperty prop : propMap.values()) {
/*  532 */       if (prop.getDbColumn() != null) {
/*  533 */         revMap.put(prop.getDbColumn(), prop);
/*      */       }
/*      */     } 
/*      */     
/*  537 */     return revMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEbeanServer(SpiEbeanServer ebeanServer) {
/*  544 */     this.ebeanServer = ebeanServer;
/*  545 */     for (int i = 0; i < this.propertiesMany.length; i++)
/*      */     {
/*  547 */       this.propertiesMany[i].setLoader((BeanCollectionLoader)ebeanServer);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConcurrencyMode determineConcurrencyMode(Object bean) {
/*  557 */     if (this.propertyFirstVersion == null) {
/*  558 */       return ConcurrencyMode.NONE;
/*      */     }
/*  560 */     Object v = this.propertyFirstVersion.getValue(bean);
/*  561 */     return (v == null) ? ConcurrencyMode.NONE : ConcurrencyMode.VERSION;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getDirtyEmbeddedProperties(Object bean) {
/*  569 */     HashSet<String> dirtyProperties = null;
/*      */     
/*  571 */     for (int i = 0; i < this.propertiesEmbedded.length; i++) {
/*  572 */       Object embValue = this.propertiesEmbedded[i].getValue(bean);
/*  573 */       if (embValue instanceof EntityBean) {
/*  574 */         if (((EntityBean)embValue)._ebean_getIntercept().isDirty()) {
/*      */           
/*  576 */           if (dirtyProperties == null) {
/*  577 */             dirtyProperties = new HashSet<String>();
/*      */           }
/*  579 */           dirtyProperties.add(this.propertiesEmbedded[i].getName());
/*      */         } 
/*      */       } else {
/*      */         
/*  583 */         if (dirtyProperties == null) {
/*  584 */           dirtyProperties = new HashSet<String>();
/*      */         }
/*  586 */         dirtyProperties.add(this.propertiesEmbedded[i].getName());
/*      */       } 
/*      */     } 
/*      */     
/*  590 */     return dirtyProperties;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> determineLoadedProperties(Object bean) {
/*  598 */     HashSet<String> nonNullProps = new HashSet<String>();
/*      */     
/*  600 */     for (int j = 0; j < this.propertiesId.length; j++) {
/*  601 */       if (this.propertiesId[j].getValue(bean) != null) {
/*  602 */         nonNullProps.add(this.propertiesId[j].getName());
/*      */       }
/*      */     } 
/*  605 */     for (int i = 0; i < this.propertiesNonTransient.length; i++) {
/*  606 */       if (this.propertiesNonTransient[i].getValue(bean) != null) {
/*  607 */         nonNullProps.add(this.propertiesNonTransient[i].getName());
/*      */       }
/*      */     } 
/*  610 */     return nonNullProps;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SpiEbeanServer getEbeanServer() {
/*  617 */     return this.ebeanServer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityType getEntityType() {
/*  624 */     return this.entityType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Query.UseIndex getUseIndex() {
/*  632 */     return this.useIndex;
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
/*      */   public void initialiseId() {
/*  644 */     if (logger.isLoggable(Level.FINER)) {
/*  645 */       logger.finer("BeanDescriptor initialise " + this.fullName);
/*      */     }
/*      */     
/*  648 */     if (this.inheritInfo != null) {
/*  649 */       this.inheritInfo.setDescriptor(this);
/*      */     }
/*      */     
/*  652 */     if (isEmbedded()) {
/*      */       
/*  654 */       Iterator<BeanProperty> it = propertiesAll();
/*  655 */       while (it.hasNext()) {
/*  656 */         BeanProperty prop = it.next();
/*  657 */         prop.initialise();
/*      */       } 
/*      */     } else {
/*      */       
/*  661 */       BeanProperty[] idProps = propertiesId();
/*  662 */       for (int i = 0; i < idProps.length; i++) {
/*  663 */         idProps[i].initialise();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initialiseOther() {
/*  673 */     if (!isEmbedded()) {
/*      */       
/*  675 */       Iterator<BeanProperty> it = propertiesAll();
/*  676 */       while (it.hasNext()) {
/*  677 */         BeanProperty prop = it.next();
/*  678 */         if (!prop.isId()) {
/*  679 */           prop.initialise();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  684 */     if (this.unidirectional != null) {
/*  685 */       this.unidirectional.initialise();
/*      */     }
/*      */     
/*  688 */     this.idBinder.initialise();
/*  689 */     this.idBinderInLHSSql = this.idBinder.getBindIdInSql(this.baseTableAlias);
/*  690 */     this.idBinderIdSql = this.idBinder.getBindIdSql(this.baseTableAlias);
/*  691 */     String idBinderInLHSSqlNoAlias = this.idBinder.getBindIdInSql(null);
/*  692 */     String idEqualsSql = this.idBinder.getBindIdSql(null);
/*      */     
/*  694 */     this.deleteByIdSql = "delete from " + this.baseTable + " where " + idEqualsSql;
/*  695 */     this.deleteByIdInSql = "delete from " + this.baseTable + " where " + idBinderInLHSSqlNoAlias + " ";
/*      */     
/*  697 */     if (!isEmbedded())
/*      */     {
/*  699 */       for (DeployNamedUpdate namedUpdate : this.namedUpdates.values()) {
/*  700 */         DeployUpdateParser parser = new DeployUpdateParser(this);
/*  701 */         namedUpdate.initialise(parser);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void initInheritInfo() {
/*  708 */     if (this.inheritInfo != null) {
/*      */       
/*  710 */       if (this.saveRecurseSkippable) {
/*  711 */         this.saveRecurseSkippable = this.inheritInfo.isSaveRecurseSkippable();
/*      */       }
/*  713 */       if (this.deleteRecurseSkippable) {
/*  714 */         this.deleteRecurseSkippable = this.inheritInfo.isDeleteRecurseSkippable();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cacheInitialise() {
/*  723 */     if (this.cacheOptions.isUseNaturalKeyCache()) {
/*  724 */       this.naturalKeyCache = this.cacheManager.getNaturalKeyCache(this.beanType);
/*      */     }
/*  726 */     if (this.cacheOptions.isUseCache()) {
/*  727 */       this.beanCache = this.cacheManager.getBeanCache(this.beanType);
/*      */     }
/*      */   }
/*      */   
/*      */   protected boolean hasInheritance() {
/*  732 */     return (this.inheritInfo != null);
/*      */   }
/*      */   
/*      */   protected boolean isDynamicSubclass() {
/*  736 */     return !this.beanType.equals(this.factoryType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLdapObjectClasses(Attributes attributes) {
/*  744 */     if (this.ldapObjectclasses != null) {
/*  745 */       BasicAttribute ocAttrs = new BasicAttribute("objectclass");
/*  746 */       for (int i = 0; i < this.ldapObjectclasses.length; i++) {
/*  747 */         ocAttrs.add(this.ldapObjectclasses[i]);
/*      */       }
/*  749 */       attributes.put(ocAttrs);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Attributes createAttributes() {
/*  758 */     Attributes attrs = new BasicAttributes(true);
/*  759 */     setLdapObjectClasses(attrs);
/*  760 */     return attrs;
/*      */   }
/*      */   
/*      */   public String getLdapBaseDn() {
/*  764 */     return this.ldapBaseDn;
/*      */   }
/*      */ 
/*      */   
/*      */   public LdapName createLdapNameById(Object id) throws InvalidNameException {
/*  769 */     LdapName baseDn = new LdapName(this.ldapBaseDn);
/*  770 */     this.idBinder.createLdapNameById(baseDn, id);
/*  771 */     return baseDn;
/*      */   }
/*      */ 
/*      */   
/*      */   public LdapName createLdapName(Object bean) {
/*      */     try {
/*  777 */       LdapName name = new LdapName(this.ldapBaseDn);
/*  778 */       if (bean != null) {
/*  779 */         this.idBinder.createLdapNameByBean(name, bean);
/*      */       }
/*  781 */       return name;
/*      */     }
/*  783 */     catch (InvalidNameException e) {
/*  784 */       throw new LdapPersistenceException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public SqlUpdate deleteById(Object id, List<Object> idList) {
/*  789 */     if (id != null) {
/*  790 */       return deleteById(id);
/*      */     }
/*  792 */     return deleteByIdList(idList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SqlUpdate deleteByIdList(List<Object> idList) {
/*  802 */     StringBuilder sb = new StringBuilder(this.deleteByIdInSql);
/*  803 */     String inClause = this.idBinder.getIdInValueExprDelete(idList.size());
/*  804 */     sb.append(inClause);
/*      */     
/*  806 */     DefaultSqlUpdate delete = new DefaultSqlUpdate(sb.toString());
/*  807 */     for (int i = 0; i < idList.size(); i++) {
/*  808 */       this.idBinder.bindId(delete, idList.get(i));
/*      */     }
/*  810 */     return (SqlUpdate)delete;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SqlUpdate deleteById(Object id) {
/*  819 */     DefaultSqlUpdate sqlDelete = new DefaultSqlUpdate(this.deleteByIdSql);
/*      */     
/*  821 */     Object[] bindValues = this.idBinder.getBindValues(id);
/*  822 */     for (int i = 0; i < bindValues.length; i++) {
/*  823 */       sqlDelete.addParameter(bindValues[i]);
/*      */     }
/*      */     
/*  826 */     return (SqlUpdate)sqlDelete;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(BeanFkeyProperty fkey) {
/*  834 */     this.fkeyMap.put(fkey.getName(), fkey);
/*      */   }
/*      */   
/*      */   public void initialiseFkeys() {
/*  838 */     for (int i = 0; i < this.propertiesOneImported.length; i++) {
/*  839 */       this.propertiesOneImported[i].addFkey();
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean calculateUseCache(Boolean queryUseCache) {
/*  844 */     return (queryUseCache != null) ? queryUseCache.booleanValue() : isBeanCaching();
/*      */   }
/*      */   
/*      */   public boolean calculateUseNaturalKeyCache(Boolean queryUseCache) {
/*  848 */     return (queryUseCache != null) ? queryUseCache.booleanValue() : isBeanCaching();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CacheOptions getCacheOptions() {
/*  855 */     return this.cacheOptions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EncryptKey getEncryptKey(BeanProperty p) {
/*  862 */     return this.owner.getEncryptKey(this.baseTable, p.getDbColumn());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EncryptKey getEncryptKey(String tableName, String columnName) {
/*  869 */     return this.owner.getEncryptKey(tableName, columnName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void runCacheWarming() {
/*  876 */     if (this.cacheOptions == null) {
/*      */       return;
/*      */     }
/*  879 */     String warmingQuery = this.cacheOptions.getWarmingQuery();
/*  880 */     if (warmingQuery != null && warmingQuery.trim().length() > 0) {
/*  881 */       Query<T> query = this.ebeanServer.createQuery(this.beanType, warmingQuery);
/*  882 */       query.setUseCache(true);
/*  883 */       query.setReadOnly(true);
/*  884 */       query.setLoadBeanCache(true);
/*  885 */       List<T> list = query.findList();
/*  886 */       if (logger.isLoggable(Level.INFO)) {
/*  887 */         String msg = "Loaded " + this.beanType + " cache with [" + list.size() + "] beans";
/*  888 */         logger.info(msg);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasDefaultSelectClause() {
/*  898 */     return (this.defaultSelectClause != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultSelectClause() {
/*  905 */     return this.defaultSelectClause;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getDefaultSelectClauseSet() {
/*  912 */     return this.defaultSelectClauseSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getDefaultSelectDbArray() {
/*  919 */     return this.defaultSelectDbArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInheritanceRoot() {
/*  927 */     return (this.inheritInfo == null || this.inheritInfo.isRoot());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isQueryCaching() {
/*  934 */     return (this.queryCache != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBeanCaching() {
/*  941 */     return (this.beanCache != null);
/*      */   }
/*      */   
/*      */   public boolean cacheIsUseManyId() {
/*  945 */     return isBeanCaching();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCacheNotify() {
/*  953 */     if (isBeanCaching() || isQueryCaching()) {
/*  954 */       return true;
/*      */     }
/*  956 */     for (int i = 0; i < this.propertiesOneImported.length; i++) {
/*  957 */       if (this.propertiesOneImported[i].getTargetDescriptor().isBeanCaching()) {
/*  958 */         return true;
/*      */       }
/*      */     } 
/*  961 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUsingL2Cache() {
/*  969 */     return isBeanCaching();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cacheNotify(TransactionEventTable.TableIUD tableIUD) {
/*  977 */     if (tableIUD.isUpdateOrDelete()) {
/*  978 */       cacheClear();
/*      */     }
/*      */     
/*  981 */     queryCacheClear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void queryCacheClear() {
/*  988 */     if (this.queryCache != null) {
/*  989 */       this.queryCache.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanCollection<T> queryCacheGet(Object id) {
/*  998 */     if (this.queryCache == null) {
/*  999 */       return null;
/*      */     }
/* 1001 */     return (BeanCollection<T>)this.queryCache.get(id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void queryCachePut(Object id, BeanCollection<T> query) {
/* 1009 */     if (this.queryCache == null) {
/* 1010 */       this.queryCache = this.cacheManager.getQueryCache(this.beanType);
/*      */     }
/* 1012 */     this.queryCache.put(id, query);
/*      */   }
/*      */   
/*      */   private ServerCache getBeanCache() {
/* 1016 */     if (this.beanCache == null) {
/* 1017 */       this.beanCache = this.cacheManager.getBeanCache(this.beanType);
/*      */     }
/* 1019 */     return this.beanCache;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cacheClear() {
/* 1026 */     if (this.beanCache != null) {
/* 1027 */       this.beanCache.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cachePutBeanData(Object bean) {
/* 1036 */     CachedBeanData beanData = CachedBeanDataFromBean.extract(this, bean);
/*      */     
/* 1038 */     Object id = getId(bean);
/* 1039 */     getBeanCache().put(id, beanData);
/* 1040 */     if (beanData.isNaturalKeyUpdate() && this.naturalKeyCache != null) {
/* 1041 */       Object naturalKey = beanData.getNaturalKey();
/* 1042 */       if (naturalKey != null) {
/* 1043 */         this.naturalKeyCache.put(naturalKey, id);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean cacheLoadMany(BeanPropertyAssocMany<?> many, BeanCollection<?> bc, Object parentId, Boolean readOnly, boolean vanilla) {
/* 1050 */     CachedManyIds ids = cacheGetCachedManyIds(parentId, many.getName());
/* 1051 */     if (ids == null) {
/* 1052 */       return false;
/*      */     }
/*      */     
/* 1055 */     Object ownerBean = bc.getOwnerBean();
/* 1056 */     EntityBeanIntercept ebi = ((EntityBean)ownerBean)._ebean_getIntercept();
/* 1057 */     PersistenceContext persistenceContext = ebi.getPersistenceContext();
/*      */     
/* 1059 */     BeanDescriptor<?> targetDescriptor = many.getTargetDescriptor();
/*      */     
/* 1061 */     List<Object> idList = ids.getIdList();
/* 1062 */     bc.checkEmptyLazyLoad();
/* 1063 */     for (int i = 0; i < idList.size(); i++) {
/* 1064 */       Object id = idList.get(i);
/* 1065 */       Object refBean = targetDescriptor.createReference(vanilla, readOnly, id, null);
/* 1066 */       EntityBeanIntercept refEbi = ((EntityBean)refBean)._ebean_getIntercept();
/*      */       
/* 1068 */       many.add(bc, refBean);
/* 1069 */       persistenceContext.put(id, refBean);
/* 1070 */       refEbi.setPersistenceContext(persistenceContext);
/*      */     } 
/* 1072 */     return true;
/*      */   }
/*      */   
/*      */   public void cachePutMany(BeanPropertyAssocMany<?> many, BeanCollection<?> bc, Object parentId) {
/* 1076 */     BeanDescriptor<?> targetDescriptor = many.getTargetDescriptor();
/* 1077 */     Collection<?> actualDetails = bc.getActualDetails();
/* 1078 */     ArrayList<Object> idList = new ArrayList();
/* 1079 */     for (Object bean : actualDetails) {
/* 1080 */       Object id = targetDescriptor.getId(bean);
/* 1081 */       idList.add(id);
/*      */     } 
/* 1083 */     CachedManyIds ids = new CachedManyIds(idList);
/* 1084 */     cachePutCachedManyIds(parentId, many.getName(), ids);
/*      */   }
/*      */   
/*      */   public void cacheRemoveCachedManyIds(Object parentId, String propertyName) {
/* 1088 */     ServerCache collectionIdsCache = this.cacheManager.getCollectionIdsCache(this.beanType, propertyName);
/* 1089 */     collectionIdsCache.remove(parentId);
/*      */   }
/*      */   
/*      */   public void cacheClearCachedManyIds(String propertyName) {
/* 1093 */     ServerCache collectionIdsCache = this.cacheManager.getCollectionIdsCache(this.beanType, propertyName);
/* 1094 */     collectionIdsCache.clear();
/*      */   }
/*      */   
/*      */   public CachedManyIds cacheGetCachedManyIds(Object parentId, String propertyName) {
/* 1098 */     ServerCache collectionIdsCache = this.cacheManager.getCollectionIdsCache(this.beanType, propertyName);
/* 1099 */     return (CachedManyIds)collectionIdsCache.get(parentId);
/*      */   }
/*      */   
/*      */   public void cachePutCachedManyIds(Object parentId, String propertyName, CachedManyIds ids) {
/* 1103 */     ServerCache collectionIdsCache = this.cacheManager.getCollectionIdsCache(this.beanType, propertyName);
/* 1104 */     collectionIdsCache.put(parentId, ids);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T cacheGetBean(Object id, boolean vanilla, Boolean readOnly) {
/* 1113 */     CachedBeanData d = (CachedBeanData)getBeanCache().get(id);
/* 1114 */     if (d == null) {
/* 1115 */       return null;
/*      */     }
/* 1117 */     if (this.cacheSharableBeans && !vanilla && !Boolean.FALSE.equals(readOnly)) {
/* 1118 */       Object object = d.getSharableBean();
/* 1119 */       if (object != null) {
/* 1120 */         return (T)object;
/*      */       }
/*      */     } 
/*      */     
/* 1124 */     T bean = (T)createBean(vanilla);
/* 1125 */     convertSetId(id, bean);
/* 1126 */     if (!vanilla && Boolean.TRUE.equals(readOnly)) {
/* 1127 */       ((EntityBean)bean)._ebean_getIntercept().setReadOnly(true);
/*      */     }
/*      */     
/* 1130 */     CachedBeanDataToBean.load(this, bean, d);
/* 1131 */     return bean;
/*      */   }
/*      */   
/*      */   public boolean cacheIsNaturalKey(String propName) {
/* 1135 */     return (propName != null && propName.equals(this.cacheOptions.getNaturalKey()));
/*      */   }
/*      */   
/*      */   public Object cacheGetNaturalKeyId(Object uniqueKeyValue) {
/* 1139 */     if (this.naturalKeyCache != null) {
/* 1140 */       return this.naturalKeyCache.get(uniqueKeyValue);
/*      */     }
/* 1142 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cacheRemove(Object id) {
/* 1149 */     if (this.beanCache != null) {
/* 1150 */       this.beanCache.remove(id);
/*      */     }
/* 1152 */     for (int i = 0; i < this.propertiesOneImported.length; i++) {
/* 1153 */       this.propertiesOneImported[i].cacheClear();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cacheDelete(Object id, PersistRequestBean<T> deleteRequest) {
/* 1161 */     if (this.beanCache != null) {
/* 1162 */       this.beanCache.remove(id);
/*      */     }
/* 1164 */     for (int i = 0; i < this.propertiesOneImported.length; i++) {
/* 1165 */       BeanPropertyAssocMany<?> many = this.propertiesOneImported[i].getRelationshipProperty();
/* 1166 */       if (many != null) {
/* 1167 */         this.propertiesOneImported[i].cacheDelete(true, deleteRequest);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void cacheInsert(Object id, PersistRequestBean<T> insertRequest) {
/* 1173 */     if (this.queryCache != null) {
/* 1174 */       this.queryCache.clear();
/*      */     }
/* 1176 */     for (int i = 0; i < this.propertiesOneImported.length; i++) {
/* 1177 */       this.propertiesOneImported[i].cacheDelete(false, insertRequest.getBean());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cacheUpdate(Object id, PersistRequestBean<T> updateRequest) {
/* 1186 */     ServerCache cache = getBeanCache();
/* 1187 */     CachedBeanData cd = (CachedBeanData)cache.get(id);
/* 1188 */     if (cd != null) {
/* 1189 */       CachedBeanData newCd = CachedBeanDataUpdate.update(this, cd, updateRequest);
/* 1190 */       cache.put(id, newCd);
/* 1191 */       if (newCd.isNaturalKeyUpdate() && this.naturalKeyCache != null) {
/* 1192 */         Object oldKey = this.propertiesNaturalKey.getValue(updateRequest.getOldValues());
/* 1193 */         Object newKey = this.propertiesNaturalKey.getValue(updateRequest.getBean());
/* 1194 */         if (oldKey != null) {
/* 1195 */           this.naturalKeyCache.remove(oldKey);
/*      */         }
/* 1197 */         if (newKey != null) {
/* 1198 */           this.naturalKeyCache.put(newKey, id);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getBaseTableAlias() {
/* 1209 */     return this.baseTableAlias;
/*      */   }
/*      */   
/*      */   public boolean loadFromCache(EntityBeanIntercept ebi) {
/* 1213 */     Object bean = ebi.getOwner();
/* 1214 */     Object id = getId(bean);
/*      */     
/* 1216 */     return loadFromCache(bean, ebi, id);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean loadFromCache(Object bean, EntityBeanIntercept ebi, Object id) {
/* 1221 */     CachedBeanData cacheData = (CachedBeanData)getBeanCache().get(id);
/* 1222 */     if (cacheData == null) {
/* 1223 */       return false;
/*      */     }
/* 1225 */     String lazyLoadProperty = ebi.getLazyLoadProperty();
/* 1226 */     if (lazyLoadProperty != null && !cacheData.containsProperty(lazyLoadProperty)) {
/* 1227 */       return false;
/*      */     }
/*      */     
/* 1230 */     CachedBeanDataToBean.load(this, bean, ebi, cacheData);
/* 1231 */     return true;
/*      */   }
/*      */   
/*      */   public void preAllocateIds(int batchSize) {
/* 1235 */     if (this.idGenerator != null) {
/* 1236 */       this.idGenerator.preAllocateIds(batchSize);
/*      */     }
/*      */   }
/*      */   
/*      */   public Object nextId(Transaction t) {
/* 1241 */     if (this.idGenerator != null) {
/* 1242 */       return this.idGenerator.nextId(t);
/*      */     }
/* 1244 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public DeployPropertyParser createDeployPropertyParser() {
/* 1249 */     return new DeployPropertyParser(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String convertOrmUpdateToSql(String ormUpdateStatement) {
/* 1257 */     return (new DeployUpdateParser(this)).parse(ormUpdateStatement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearQueryStatistics() {
/* 1264 */     Iterator<CQueryPlan> it = this.queryPlanCache.values().iterator();
/* 1265 */     while (it.hasNext()) {
/* 1266 */       CQueryPlan queryPlan = it.next();
/* 1267 */       queryPlan.resetStatistics();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void postLoad(Object bean, Set<String> includedProperties) {
/* 1276 */     BeanPersistController c = this.persistController;
/* 1277 */     if (c != null) {
/* 1278 */       c.postLoad(bean, includedProperties);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator<CQueryPlan> queryPlans() {
/* 1286 */     return this.queryPlanCache.values().iterator();
/*      */   }
/*      */   
/*      */   public CQueryPlan getQueryPlan(Integer key) {
/* 1290 */     return this.queryPlanCache.get(key);
/*      */   }
/*      */   
/*      */   public void putQueryPlan(Integer key, CQueryPlan plan) {
/* 1294 */     this.queryPlanCache.put(key, plan);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SpiUpdatePlan getUpdatePlan(Integer key) {
/* 1301 */     return this.updatePlanCache.get(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void putUpdatePlan(Integer key, SpiUpdatePlan plan) {
/* 1308 */     this.updatePlanCache.put(key, plan);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeManager getTypeManager() {
/* 1315 */     return this.typeManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUpdateChangesOnly() {
/* 1323 */     return this.updateChangesOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSaveRecurseSkippable() {
/* 1331 */     return this.saveRecurseSkippable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDeleteRecurseSkippable() {
/* 1339 */     return this.deleteRecurseSkippable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasLocalValidation() {
/* 1346 */     return this.hasLocalValidation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasCascadeValidation() {
/* 1353 */     return this.hasCascadeValidation;
/*      */   }
/*      */ 
/*      */   
/*      */   public InvalidValue validate(boolean cascade, Object bean) {
/* 1358 */     if (!this.hasCascadeValidation)
/*      */     {
/* 1360 */       return null;
/*      */     }
/*      */     
/* 1363 */     List<InvalidValue> errList = null;
/*      */     
/* 1365 */     Set<String> loadedProps = null;
/* 1366 */     if (bean instanceof EntityBean) {
/* 1367 */       EntityBeanIntercept ebi = ((EntityBean)bean)._ebean_getIntercept();
/* 1368 */       loadedProps = ebi.getLoadedProps();
/*      */     } 
/* 1370 */     if (loadedProps != null) {
/*      */       
/* 1372 */       Iterator<String> propIt = loadedProps.iterator();
/* 1373 */       while (propIt.hasNext()) {
/* 1374 */         String propName = propIt.next();
/* 1375 */         BeanProperty property = getBeanProperty(propName);
/*      */ 
/*      */         
/* 1378 */         if (property != null && property.hasValidationRules(cascade)) {
/* 1379 */           Object value = property.getValue(bean);
/* 1380 */           List<InvalidValue> errs = property.validate(cascade, value);
/* 1381 */           if (errs != null) {
/* 1382 */             if (errList == null) {
/* 1383 */               errList = new ArrayList<InvalidValue>();
/*      */             }
/* 1385 */             errList.addAll(errs);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1391 */       BeanProperty[] props = cascade ? this.propertiesValidationCascade : this.propertiesValidationLocal;
/*      */ 
/*      */       
/* 1394 */       for (int j = 0; j < props.length; j++) {
/* 1395 */         BeanProperty prop = props[j];
/* 1396 */         Object value = prop.getValue(bean);
/* 1397 */         List<InvalidValue> errs = prop.validate(cascade, value);
/* 1398 */         if (errs != null) {
/* 1399 */           if (errList == null) {
/* 1400 */             errList = new ArrayList<InvalidValue>();
/*      */           }
/* 1402 */           errList.addAll(errs);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1407 */     for (int i = 0; i < this.beanValidators.length; i++) {
/* 1408 */       if (!this.beanValidators[i].isValid(bean)) {
/* 1409 */         if (errList == null) {
/* 1410 */           errList = new ArrayList<InvalidValue>();
/*      */         }
/* 1412 */         Validator v = this.beanValidators[i];
/* 1413 */         errList.add(new InvalidValue(v.getKey(), v.getAttributes(), getFullName(), null, bean));
/*      */       } 
/*      */     } 
/*      */     
/* 1417 */     if (errList == null) {
/* 1418 */       return null;
/*      */     }
/*      */     
/* 1421 */     return new InvalidValue(null, getFullName(), bean, InvalidValue.toArray(errList));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPropertyAssocMany<?> getManyProperty(SpiQuery<?> query) {
/* 1429 */     OrmQueryDetail detail = query.getDetail();
/* 1430 */     for (int i = 0; i < this.propertiesMany.length; i++) {
/* 1431 */       if (detail.includes(this.propertiesMany[i].getName())) {
/* 1432 */         return this.propertiesMany[i];
/*      */       }
/*      */     } 
/*      */     
/* 1436 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IdBinder getIdBinder() {
/* 1443 */     return this.idBinder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getIdBinderIdSql() {
/* 1451 */     return this.idBinderIdSql;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getIdBinderInLHSSql() {
/* 1458 */     return this.idBinderInLHSSql;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void bindId(DataBind dataBind, Object idValue) throws SQLException {
/* 1468 */     this.idBinder.bindId(dataBind, idValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object[] getBindIdValues(Object idValue) {
/* 1478 */     return this.idBinder.getBindValues(idValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DeployNamedQuery getNamedQuery(String name) {
/* 1485 */     return this.namedQueries.get(name);
/*      */   }
/*      */   
/*      */   public DeployNamedQuery addNamedQuery(DeployNamedQuery deployNamedQuery) {
/* 1489 */     return this.namedQueries.put(deployNamedQuery.getName(), deployNamedQuery);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DeployNamedUpdate getNamedUpdate(String name) {
/* 1496 */     return this.namedUpdates.get(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object createBean(boolean vanillaMode) {
/* 1503 */     return vanillaMode ? createVanillaBean() : createEntityBean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object createVanillaBean() {
/* 1513 */     return this.beanReflect.createVanillaBean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityBean createEntityBean() {
/*      */     try {
/* 1522 */       EntityBean eb = (EntityBean)this.beanReflect.createEntityBean();
/*      */       
/* 1524 */       return eb;
/*      */     }
/* 1526 */     catch (Exception ex) {
/* 1527 */       throw new PersistenceException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T createReference(boolean vanillaMode, Boolean readOnly, Object id, Object parent) {
/* 1537 */     if (this.cacheSharableBeans && !vanillaMode && !Boolean.FALSE.equals(readOnly)) {
/* 1538 */       CachedBeanData d = (CachedBeanData)getBeanCache().get(id);
/* 1539 */       if (d != null) {
/* 1540 */         Object shareableBean = d.getSharableBean();
/* 1541 */         if (shareableBean != null) {
/* 1542 */           return (T)shareableBean;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     try {
/* 1547 */       Object bean = createBean(vanillaMode);
/*      */       
/* 1549 */       convertSetId(id, bean);
/*      */       
/* 1551 */       if (!vanillaMode) {
/* 1552 */         EntityBean eb = (EntityBean)bean;
/*      */         
/* 1554 */         EntityBeanIntercept ebi = eb._ebean_getIntercept();
/* 1555 */         ebi.setBeanLoaderByServerName(this.ebeanServer.getName());
/*      */         
/* 1557 */         if (parent != null)
/*      */         {
/*      */           
/* 1560 */           ebi.setParentBean(parent);
/*      */         }
/*      */ 
/*      */         
/* 1564 */         ebi.setReference();
/*      */       } 
/*      */       
/* 1567 */       return (T)bean;
/*      */     }
/* 1569 */     catch (Exception ex) {
/* 1570 */       throw new PersistenceException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanProperty getBeanPropertyFromDbColumn(String dbColumn) {
/* 1578 */     return this.propMapByDbColumn.get(dbColumn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanProperty getBeanPropertyFromPath(String path) {
/* 1587 */     String[] split = SplitName.splitBegin(path);
/* 1588 */     if (split[1] == null) {
/* 1589 */       return _findBeanProperty(split[0]);
/*      */     }
/* 1591 */     BeanPropertyAssoc<?> assocProp = (BeanPropertyAssoc)_findBeanProperty(split[0]);
/* 1592 */     BeanDescriptor<?> targetDesc = assocProp.getTargetDescriptor();
/*      */     
/* 1594 */     return targetDesc.getBeanPropertyFromPath(split[1]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanDescriptor<?> getBeanDescriptor(String path) {
/* 1601 */     if (path == null) {
/* 1602 */       return this;
/*      */     }
/* 1604 */     String[] splitBegin = SplitName.splitBegin(path);
/*      */     
/* 1606 */     BeanProperty beanProperty = this.propMap.get(splitBegin[0]);
/* 1607 */     if (beanProperty instanceof BeanPropertyAssoc) {
/* 1608 */       BeanPropertyAssoc<?> assocProp = (BeanPropertyAssoc)beanProperty;
/* 1609 */       return assocProp.getTargetDescriptor().getBeanDescriptor(splitBegin[1]);
/*      */     } 
/*      */     
/* 1612 */     throw new RuntimeException("Error getting BeanDescriptor for path " + path + " from " + getFullName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <U> BeanDescriptor<U> getBeanDescriptor(Class<U> otherType) {
/* 1620 */     return this.owner.getBeanDescriptor(otherType);
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
/*      */   public BeanPropertyAssocOne<?> getUnidirectional() {
/* 1632 */     if (this.unidirectional != null) {
/* 1633 */       return this.unidirectional;
/*      */     }
/* 1635 */     if (this.inheritInfo != null && !this.inheritInfo.isRoot()) {
/* 1636 */       return this.inheritInfo.getParent().getBeanDescriptor().getUnidirectional();
/*      */     }
/* 1638 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getValue(Object bean, String property) {
/* 1645 */     return getBeanProperty(property).getValue(bean);
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
/* 1656 */     return (this.idGenerator != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescriptorId() {
/* 1664 */     return this.descriptorId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<T> getBeanType() {
/* 1671 */     return this.beanType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?> getFactoryType() {
/* 1678 */     return this.factoryType;
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
/* 1689 */     return this.fullName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/* 1696 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1703 */     return this.fullName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getId(Object bean) {
/* 1714 */     if (this.propertySingleId != null) {
/* 1715 */       if (this.inheritInfo != null && !this.enhancedBean)
/*      */       {
/* 1717 */         return this.propertySingleId.getValueViaReflection(bean);
/*      */       }
/*      */       
/* 1720 */       return this.propertySingleId.getValue(bean);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1726 */     LinkedHashMap<String, Object> idMap = new LinkedHashMap<String, Object>();
/* 1727 */     for (int i = 0; i < this.propertiesId.length; i++) {
/*      */       
/* 1729 */       Object value = this.propertiesId[i].getValue(bean);
/* 1730 */       idMap.put(this.propertiesId[i].getName(), value);
/*      */     } 
/* 1732 */     return idMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isComplexId() {
/* 1740 */     return this.idBinder.isComplexId();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultOrderBy() {
/* 1748 */     return this.idBinder.getDefaultOrderBy();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object convertId(Object idValue) {
/* 1755 */     return this.idBinder.convertSetId(idValue, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object convertSetId(Object idValue, Object bean) {
/* 1766 */     return this.idBinder.convertSetId(idValue, bean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanProperty getBeanProperty(String propName) {
/* 1773 */     return this.propMap.get(propName);
/*      */   }
/*      */ 
/*      */   
/*      */   public void sort(List<T> list, String sortByClause) {
/* 1778 */     ElComparator<T> comparator = getElComparator(sortByClause);
/* 1779 */     Collections.sort(list, (Comparator<? super T>)comparator);
/*      */   }
/*      */   
/*      */   public ElComparator<T> getElComparator(String propNameOrSortBy) {
/* 1783 */     ElComparator<T> c = this.comparatorCache.get(propNameOrSortBy);
/* 1784 */     if (c == null) {
/* 1785 */       c = createComparator(propNameOrSortBy);
/* 1786 */       this.comparatorCache.put(propNameOrSortBy, c);
/*      */     } 
/* 1788 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean lazyLoadMany(EntityBeanIntercept ebi) {
/* 1797 */     String lazyLoadProperty = ebi.getLazyLoadProperty();
/* 1798 */     BeanProperty lazyLoadBeanProp = getBeanProperty(lazyLoadProperty);
/*      */     
/* 1800 */     if (lazyLoadBeanProp instanceof BeanPropertyAssocMany) {
/* 1801 */       BeanPropertyAssocMany<?> manyProp = (BeanPropertyAssocMany)lazyLoadBeanProp;
/* 1802 */       manyProp.createReference(ebi.getOwner());
/* 1803 */       Set<String> loadedProps = ebi.getLoadedProps();
/* 1804 */       HashSet<String> newLoadedProps = new HashSet<String>();
/* 1805 */       if (loadedProps != null) {
/* 1806 */         newLoadedProps.addAll(loadedProps);
/*      */       }
/* 1808 */       newLoadedProps.add(lazyLoadProperty);
/* 1809 */       ebi.setLoadedProps(newLoadedProps);
/* 1810 */       ebi.setLoadedLazy();
/* 1811 */       return true;
/*      */     } 
/*      */     
/* 1814 */     return false;
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
/*      */   private ElComparator<T> createComparator(String sortByClause) {
/* 1826 */     SortByClause sortBy = SortByClauseParser.parse(sortByClause);
/* 1827 */     if (sortBy.size() == 1)
/*      */     {
/* 1829 */       return createPropertyComparator(sortBy.getProperties().get(0));
/*      */     }
/*      */ 
/*      */     
/* 1833 */     ElComparator[] arrayOfElComparator = new ElComparator[sortBy.size()];
/*      */     
/* 1835 */     List<SortByClause.Property> sortProps = sortBy.getProperties();
/* 1836 */     for (int i = 0; i < sortProps.size(); i++) {
/* 1837 */       SortByClause.Property sortProperty = sortProps.get(i);
/* 1838 */       arrayOfElComparator[i] = createPropertyComparator(sortProperty);
/*      */     } 
/*      */     
/* 1841 */     return (ElComparator<T>)new ElComparatorCompound(arrayOfElComparator);
/*      */   }
/*      */ 
/*      */   
/*      */   private ElComparator<T> createPropertyComparator(SortByClause.Property sortProp) {
/* 1846 */     ElPropertyValue elGetValue = getElGetValue(sortProp.getName());
/*      */     
/* 1848 */     Boolean nullsHigh = sortProp.getNullsHigh();
/* 1849 */     if (nullsHigh == null) {
/* 1850 */       nullsHigh = Boolean.TRUE;
/*      */     }
/* 1852 */     return (ElComparator<T>)new ElComparatorProperty(elGetValue, sortProp.isAscending(), nullsHigh.booleanValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ElPropertyValue getElGetValue(String propName) {
/* 1859 */     return getElPropertyValue(propName, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ElPropertyDeploy getElPropertyDeploy(String propName) {
/* 1869 */     ElPropertyDeploy fk = (ElPropertyDeploy)this.fkeyMap.get(propName);
/* 1870 */     if (fk != null) {
/* 1871 */       return fk;
/*      */     }
/* 1873 */     return (ElPropertyDeploy)getElPropertyValue(propName, true);
/*      */   }
/*      */   
/*      */   private ElPropertyValue getElPropertyValue(String propName, boolean propertyDeploy) {
/* 1877 */     ElPropertyValue elGetValue = this.elGetCache.get(propName);
/* 1878 */     if (elGetValue == null) {
/*      */       
/* 1880 */       elGetValue = buildElGetValue(propName, null, propertyDeploy);
/* 1881 */       if (elGetValue == null) {
/* 1882 */         return null;
/*      */       }
/* 1884 */       if (elGetValue instanceof BeanFkeyProperty) {
/* 1885 */         this.fkeyMap.put(propName, (BeanFkeyProperty)elGetValue);
/*      */       } else {
/* 1887 */         this.elGetCache.put(propName, elGetValue);
/*      */       } 
/*      */     } 
/* 1890 */     return elGetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   protected ElPropertyValue buildElGetValue(String propName, ElPropertyChainBuilder chain, boolean propertyDeploy) {
/* 1895 */     if (propertyDeploy && chain != null) {
/* 1896 */       BeanFkeyProperty fk = this.fkeyMap.get(propName);
/* 1897 */       if (fk != null) {
/* 1898 */         return fk.create(chain.getExpression());
/*      */       }
/*      */     } 
/*      */     
/* 1902 */     int basePos = propName.indexOf('.');
/* 1903 */     if (basePos > -1) {
/*      */       
/* 1905 */       String baseName = propName.substring(0, basePos);
/* 1906 */       String remainder = propName.substring(basePos + 1);
/*      */       
/* 1908 */       BeanProperty assocProp = _findBeanProperty(baseName);
/* 1909 */       if (assocProp == null) {
/* 1910 */         return null;
/*      */       }
/* 1912 */       return assocProp.buildElPropertyValue(propName, remainder, chain, propertyDeploy);
/*      */     } 
/*      */     
/* 1915 */     BeanProperty property = _findBeanProperty(propName);
/* 1916 */     if (chain == null) {
/* 1917 */       return property;
/*      */     }
/* 1919 */     if (property == null) {
/* 1920 */       throw new PersistenceException("No property found for [" + propName + "] in expression " + chain.getExpression());
/*      */     }
/* 1922 */     if (property.containsMany()) {
/* 1923 */       chain.setContainsMany(true);
/*      */     }
/* 1925 */     return (ElPropertyValue)chain.add(property).build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanProperty findBeanProperty(String propName) {
/* 1936 */     int basePos = propName.indexOf('.');
/* 1937 */     if (basePos > -1) {
/*      */       
/* 1939 */       String baseName = propName.substring(0, basePos);
/* 1940 */       return _findBeanProperty(baseName);
/*      */     } 
/*      */     
/* 1943 */     return _findBeanProperty(propName);
/*      */   }
/*      */   
/*      */   private BeanProperty _findBeanProperty(String propName) {
/* 1947 */     BeanProperty prop = this.propMap.get(propName);
/* 1948 */     if (prop == null && this.inheritInfo != null)
/*      */     {
/* 1950 */       return this.inheritInfo.findSubTypeProperty(propName);
/*      */     }
/* 1952 */     return prop;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Object getBeanPropertyWithInheritance(Object bean, String propName) {
/* 1957 */     BeanDescriptor<?> desc = getBeanDescriptor(bean.getClass());
/* 1958 */     BeanProperty beanProperty = desc.findBeanProperty(propName);
/* 1959 */     return beanProperty.getValue(bean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getServerName() {
/* 1966 */     return this.serverName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCacheSharableBeans() {
/* 1977 */     return this.cacheSharableBeans;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAutoFetchTunable() {
/* 1984 */     return this.autoFetchTunable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InheritInfo getInheritInfo() {
/* 1992 */     return this.inheritInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmbedded() {
/* 1999 */     return EntityType.EMBEDDED.equals(this.entityType);
/*      */   }
/*      */   
/*      */   public boolean isBaseTableType() {
/* 2003 */     return EntityType.ORM.equals(this.entityType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConcurrencyMode getConcurrencyMode() {
/* 2010 */     return this.concurrencyMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getDependantTables() {
/* 2018 */     return this.dependantTables;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompoundUniqueContraint[] getCompoundUniqueConstraints() {
/* 2025 */     return this.compoundUniqueConstraints;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPersistListener<T> getPersistListener() {
/* 2032 */     return this.persistListener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanFinder<T> getBeanFinder() {
/* 2039 */     return this.beanFinder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanQueryAdapter getQueryAdapter() {
/* 2046 */     return this.queryAdapter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deregister(BeanPersistListener<?> listener) {
/* 2055 */     BeanPersistListener<T> currListener = this.persistListener;
/* 2056 */     if (currListener != null) {
/*      */ 
/*      */       
/* 2059 */       BeanPersistListener<T> deregListener = (BeanPersistListener)listener;
/* 2060 */       if (currListener instanceof ChainedBeanPersistListener) {
/*      */         
/* 2062 */         this.persistListener = ((ChainedBeanPersistListener<T>)currListener).deregister(deregListener);
/* 2063 */       } else if (currListener.equals(deregListener)) {
/* 2064 */         this.persistListener = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deregister(BeanPersistController controller) {
/* 2074 */     BeanPersistController c = this.persistController;
/* 2075 */     if (c != null)
/*      */     {
/*      */       
/* 2078 */       if (c instanceof ChainedBeanPersistController) {
/*      */         
/* 2080 */         this.persistController = ((ChainedBeanPersistController)c).deregister(controller);
/* 2081 */       } else if (c.equals(controller)) {
/* 2082 */         this.persistController = null;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void register(BeanPersistListener<?> newPersistListener) {
/* 2093 */     if (PersistListenerManager.isRegisterFor(this.beanType, newPersistListener)) {
/*      */ 
/*      */       
/* 2096 */       BeanPersistListener<T> newListener = (BeanPersistListener)newPersistListener;
/*      */       
/* 2098 */       BeanPersistListener<T> currListener = this.persistListener;
/* 2099 */       if (currListener == null) {
/* 2100 */         this.persistListener = newListener;
/*      */       }
/* 2102 */       else if (currListener instanceof ChainedBeanPersistListener) {
/*      */         
/* 2104 */         this.persistListener = ((ChainedBeanPersistListener<T>)currListener).register(newListener);
/*      */       } else {
/*      */         
/* 2107 */         this.persistListener = new ChainedBeanPersistListener<T>(currListener, newListener);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void register(BeanPersistController newController) {
/* 2118 */     if (newController.isRegisterFor(this.beanType)) {
/*      */ 
/*      */ 
/*      */       
/* 2122 */       BeanPersistController c = this.persistController;
/* 2123 */       if (c == null) {
/* 2124 */         this.persistController = newController;
/*      */       }
/* 2126 */       else if (c instanceof ChainedBeanPersistController) {
/*      */         
/* 2128 */         this.persistController = ((ChainedBeanPersistController)c).register(newController);
/*      */       } else {
/*      */         
/* 2131 */         this.persistController = new ChainedBeanPersistController(c, newController);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPersistController getPersistController() {
/* 2141 */     return this.persistController;
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
/*      */   public boolean isSqlSelectBased() {
/* 2153 */     return EntityType.SQL.equals(this.entityType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLdapEntityType() {
/* 2160 */     return EntityType.LDAP.equals(this.entityType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getBaseTable() {
/* 2168 */     return this.baseTable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getExtraAttribute(String key) {
/* 2175 */     return this.extraAttrMap.get(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IdType getIdType() {
/* 2182 */     return this.idType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSequenceName() {
/* 2189 */     return this.sequenceName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSelectLastInsertedId() {
/* 2200 */     return this.selectLastInsertedId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IdGenerator getIdGenerator() {
/* 2207 */     return this.idGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLazyFetchIncludes() {
/* 2214 */     return this.lazyFetchIncludes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TableJoin[] tableJoins() {
/* 2224 */     return this.derivedTableJoins;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator<BeanProperty> propertiesAll() {
/* 2231 */     return this.propMap.values().iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanProperty[] propertiesId() {
/* 2242 */     return this.propertiesId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanProperty[] propertiesNonTransient() {
/* 2249 */     return this.propertiesNonTransient;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanProperty[] propertiesTransient() {
/* 2256 */     return this.propertiesTransient;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanProperty getSingleIdProperty() {
/* 2264 */     return this.propertySingleId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPropertyAssocOne<?>[] propertiesEmbedded() {
/* 2272 */     return this.propertiesEmbedded;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPropertyAssocOne<?>[] propertiesOne() {
/* 2280 */     return this.propertiesOne;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPropertyAssocOne<?>[] propertiesOneImported() {
/* 2290 */     return this.propertiesOneImported;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPropertyAssocOne<?>[] propertiesOneImportedSave() {
/* 2297 */     return this.propertiesOneImportedSave;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPropertyAssocOne<?>[] propertiesOneImportedDelete() {
/* 2304 */     return this.propertiesOneImportedDelete;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPropertyAssocOne<?>[] propertiesOneExported() {
/* 2314 */     return this.propertiesOneExported;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPropertyAssocOne<?>[] propertiesOneExportedSave() {
/* 2321 */     return this.propertiesOneExportedSave;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPropertyAssocOne<?>[] propertiesOneExportedDelete() {
/* 2328 */     return this.propertiesOneExportedDelete;
/*      */   }
/*      */ 
/*      */   
/*      */   private Set<String> deriveManyPropNames() {
/* 2333 */     LinkedHashSet<String> names = new LinkedHashSet<String>();
/* 2334 */     for (int i = 0; i < this.propertiesMany.length; i++) {
/* 2335 */       names.add(this.propertiesMany[i].getName());
/*      */     }
/*      */     
/* 2338 */     return Collections.unmodifiableSet(names);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNamesOfManyPropsHash() {
/* 2347 */     return this.namesOfManyPropsHash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getNamesOfManyProps() {
/* 2354 */     return this.namesOfManyProps;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanProperty[] propertiesNonMany() {
/* 2361 */     return this.propertiesNonMany;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPropertyAssocMany<?>[] propertiesMany() {
/* 2368 */     return this.propertiesMany;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPropertyAssocMany<?>[] propertiesManySave() {
/* 2375 */     return this.propertiesManySave;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPropertyAssocMany<?>[] propertiesManyDelete() {
/* 2382 */     return this.propertiesManyDelete;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPropertyAssocMany<?>[] propertiesManyToMany() {
/* 2389 */     return this.propertiesManyToMany;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanProperty firstVersionProperty() {
/* 2400 */     return this.propertyFirstVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isVanillaInsert(Object bean) {
/* 2407 */     if (this.propertyFirstVersion == null) {
/* 2408 */       return true;
/*      */     }
/* 2410 */     Object versionValue = this.propertyFirstVersion.getValue(bean);
/* 2411 */     return DmlUtil.isNullOrZero(versionValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isStatelessUpdate(Object bean) {
/* 2419 */     if (this.propertyFirstVersion == null) {
/* 2420 */       Object object = getId(bean);
/* 2421 */       return !DmlUtil.isNullOrZero(object);
/*      */     } 
/* 2423 */     Object versionValue = this.propertyFirstVersion.getValue(bean);
/* 2424 */     return !DmlUtil.isNullOrZero(versionValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanProperty[] propertiesVersion() {
/* 2434 */     return this.propertiesVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanProperty[] propertiesBaseScalar() {
/* 2441 */     return this.propertiesBaseScalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPropertyCompound[] propertiesBaseCompound() {
/* 2451 */     return this.propertiesBaseCompound;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanProperty[] propertiesLocal() {
/* 2458 */     return this.propertiesLocal;
/*      */   }
/*      */ 
/*      */   
/*      */   public void jsonWrite(WriteJsonContext ctx, Object bean) {
/* 2463 */     if (bean != null) {
/*      */       
/* 2465 */       ctx.appendObjectBegin();
/* 2466 */       WriteJsonContext.WriteBeanState prevState = ctx.pushBeanState(bean);
/*      */       
/* 2468 */       if (this.inheritInfo != null) {
/* 2469 */         InheritInfo localInheritInfo = this.inheritInfo.readType(bean.getClass());
/* 2470 */         String discValue = localInheritInfo.getDiscriminatorStringValue();
/* 2471 */         String discColumn = localInheritInfo.getDiscriminatorColumn();
/* 2472 */         ctx.appendDiscriminator(discColumn, discValue);
/*      */         
/* 2474 */         BeanDescriptor<?> localDescriptor = localInheritInfo.getBeanDescriptor();
/* 2475 */         localDescriptor.jsonWriteProperties(ctx, bean);
/*      */       } else {
/*      */         
/* 2478 */         jsonWriteProperties(ctx, bean);
/*      */       } 
/*      */       
/* 2481 */       ctx.pushPreviousState(prevState);
/* 2482 */       ctx.appendObjectEnd();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void jsonWriteProperties(WriteJsonContext ctx, Object bean) {
/* 2489 */     boolean explicitAllProps, referenceBean = ctx.isReferenceBean();
/*      */     
/* 2491 */     JsonWriteBeanVisitor<T> beanVisitor = ctx.getBeanVisitor();
/*      */     
/* 2493 */     Set<String> props = ctx.getIncludeProperties();
/*      */ 
/*      */     
/* 2496 */     if (props == null) {
/* 2497 */       explicitAllProps = false;
/*      */     } else {
/* 2499 */       explicitAllProps = props.contains("*");
/* 2500 */       if (explicitAllProps || props.isEmpty()) {
/* 2501 */         props = null;
/*      */       }
/*      */     } 
/*      */     
/* 2505 */     for (int i = 0; i < this.propertiesId.length; i++) {
/* 2506 */       Object idValue = this.propertiesId[i].getValue(bean);
/* 2507 */       if (idValue != null && (
/* 2508 */         props == null || props.contains(this.propertiesId[i].getName()))) {
/* 2509 */         this.propertiesId[i].jsonWrite(ctx, bean);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2514 */     if (!explicitAllProps && props == null)
/*      */     {
/* 2516 */       props = ctx.getLoadedProps();
/*      */     }
/* 2518 */     if (props != null) {
/*      */       
/* 2520 */       for (String prop : props) {
/* 2521 */         BeanProperty p = getBeanProperty(prop);
/* 2522 */         if (p != null && !p.isId()) {
/* 2523 */           p.jsonWrite(ctx, bean);
/*      */         }
/*      */       }
/*      */     
/* 2527 */     } else if (explicitAllProps || !referenceBean) {
/*      */       
/* 2529 */       for (int j = 0; j < this.propertiesNonTransient.length; j++) {
/* 2530 */         this.propertiesNonTransient[j].jsonWrite(ctx, bean);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2535 */     if (beanVisitor != null) {
/* 2536 */       beanVisitor.visit(bean, (JsonWriter)ctx);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public T jsonReadBean(ReadJsonContext ctx, String path) {
/* 2542 */     ReadJsonContext.ReadBeanState beanState = jsonRead(ctx, path);
/* 2543 */     if (beanState == null) {
/* 2544 */       return null;
/*      */     }
/* 2546 */     beanState.setLoadedState();
/* 2547 */     return (T)beanState.getBean();
/*      */   }
/*      */ 
/*      */   
/*      */   public ReadJsonContext.ReadBeanState jsonRead(ReadJsonContext ctx, String path) {
/* 2552 */     if (!ctx.readObjectBegin())
/*      */     {
/* 2554 */       return null;
/*      */     }
/*      */     
/* 2557 */     if (this.inheritInfo == null) {
/* 2558 */       return jsonReadObject(ctx, path);
/*      */     }
/*      */ 
/*      */     
/* 2562 */     String discColumn = this.inheritInfo.getRoot().getDiscriminatorColumn();
/*      */     
/* 2564 */     if (!ctx.readKeyNext()) {
/* 2565 */       String msg = "Error reading inheritance discriminator - expected [" + discColumn + "] but no json key?";
/* 2566 */       throw new TextException(msg);
/*      */     } 
/* 2568 */     String propName = ctx.getTokenKey();
/*      */     
/* 2570 */     if (!propName.equalsIgnoreCase(discColumn)) {
/* 2571 */       String msg = "Error reading inheritance discriminator - expected [" + discColumn + "] but read [" + propName + "]";
/* 2572 */       throw new TextException(msg);
/*      */     } 
/*      */     
/* 2575 */     String discValue = ctx.readScalarValue();
/* 2576 */     if (!ctx.readValueNext()) {
/* 2577 */       String msg = "Error reading inheritance discriminator [" + discColumn + "]. Expected more json name values?";
/* 2578 */       throw new TextException(msg);
/*      */     } 
/*      */ 
/*      */     
/* 2582 */     InheritInfo localInheritInfo = this.inheritInfo.readType(discValue);
/* 2583 */     BeanDescriptor<?> localDescriptor = localInheritInfo.getBeanDescriptor();
/* 2584 */     return localDescriptor.jsonReadObject(ctx, path);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ReadJsonContext.ReadBeanState jsonReadObject(ReadJsonContext ctx, String path) {
/* 2591 */     EntityBean entityBean = createEntityBean();
/* 2592 */     ctx.pushBean(entityBean, path, this);
/*      */ 
/*      */     
/* 2595 */     while (ctx.readKeyNext()) {
/*      */ 
/*      */ 
/*      */       
/* 2599 */       String propName = ctx.getTokenKey();
/* 2600 */       BeanProperty p = getBeanProperty(propName);
/* 2601 */       if (p != null) {
/* 2602 */         p.jsonRead(ctx, entityBean);
/* 2603 */         ctx.setProperty(propName);
/*      */       } else {
/*      */         
/* 2606 */         ctx.readUnmappedJson(propName);
/*      */       } 
/*      */       
/* 2609 */       if (!ctx.readValueNext()) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2615 */     return ctx.popBeanState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoadedProps(EntityBeanIntercept ebi, Set<String> loadedProps) {
/* 2623 */     if (isLoadedReference(loadedProps)) {
/* 2624 */       ebi.setReference();
/*      */     } else {
/* 2626 */       ebi.setLoadedProps(loadedProps);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLoadedReference(Set<String> loadedProps) {
/* 2636 */     if (loadedProps != null && 
/* 2637 */       loadedProps.size() == this.propertiesId.length) {
/* 2638 */       for (int i = 0; i < this.propertiesId.length; i++) {
/* 2639 */         if (!loadedProps.contains(this.propertiesId[i].getName())) {
/* 2640 */           return false;
/*      */         }
/*      */       } 
/* 2643 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2647 */     return false;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanDescriptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */