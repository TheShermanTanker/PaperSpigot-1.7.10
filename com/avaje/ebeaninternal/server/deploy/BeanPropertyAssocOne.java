/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.InvalidValue;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.SqlUpdate;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssoc;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.query.SplitName;
/*     */ import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
/*     */ import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ public class BeanPropertyAssocOne<T>
/*     */   extends BeanPropertyAssoc<T>
/*     */ {
/*     */   private final boolean oneToOne;
/*     */   private final boolean oneToOneExported;
/*     */   private final boolean embeddedVersion;
/*     */   private final boolean importedPrimaryKey;
/*     */   private final LocalHelp localHelp;
/*     */   private final BeanProperty[] embeddedProps;
/*     */   private final HashMap<String, BeanProperty> embeddedPropsMap;
/*     */   private ImportedId importedId;
/*     */   private ExportedProperty[] exportedProperties;
/*     */   private String deleteByParentIdSql;
/*     */   private String deleteByParentIdInSql;
/*     */   BeanPropertyAssocMany<?> relationshipProperty;
/*     */   
/*     */   public BeanPropertyAssocOne(BeanDescriptorMap owner, DeployBeanPropertyAssocOne<T> deploy) {
/*  82 */     this(owner, (BeanDescriptor<?>)null, deploy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyAssocOne(BeanDescriptorMap owner, BeanDescriptor<?> descriptor, DeployBeanPropertyAssocOne<T> deploy) {
/*  91 */     super(owner, descriptor, (DeployBeanPropertyAssoc<T>)deploy);
/*     */     
/*  93 */     this.importedPrimaryKey = deploy.isImportedPrimaryKey();
/*  94 */     this.oneToOne = deploy.isOneToOne();
/*  95 */     this.oneToOneExported = deploy.isOneToOneExported();
/*     */     
/*  97 */     if (this.embedded) {
/*     */       
/*  99 */       BeanEmbeddedMeta overrideMeta = BeanEmbeddedMetaFactory.create(owner, deploy, descriptor);
/* 100 */       this.embeddedProps = overrideMeta.getProperties();
/* 101 */       if (this.id) {
/* 102 */         this.embeddedVersion = false;
/*     */       } else {
/* 104 */         this.embeddedVersion = overrideMeta.isEmbeddedVersion();
/*     */       } 
/* 106 */       this.embeddedPropsMap = new HashMap<String, BeanProperty>();
/* 107 */       for (int i = 0; i < this.embeddedProps.length; i++) {
/* 108 */         this.embeddedPropsMap.put(this.embeddedProps[i].getName(), this.embeddedProps[i]);
/*     */       }
/*     */     } else {
/*     */       
/* 112 */       this.embeddedProps = null;
/* 113 */       this.embeddedPropsMap = null;
/* 114 */       this.embeddedVersion = false;
/*     */     } 
/* 116 */     this.localHelp = createHelp(this.embedded, this.oneToOneExported);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialise() {
/* 121 */     super.initialise();
/* 122 */     if (!this.isTransient && 
/* 123 */       !this.embedded)
/*     */     {
/* 125 */       if (!this.oneToOneExported) {
/* 126 */         this.importedId = createImportedId(this, this.targetDescriptor, this.tableJoin);
/*     */       } else {
/* 128 */         this.exportedProperties = createExported();
/*     */         
/* 130 */         String delStmt = "delete from " + this.targetDescriptor.getBaseTable() + " where ";
/*     */         
/* 132 */         this.deleteByParentIdSql = delStmt + deriveWhereParentIdSql(false);
/* 133 */         this.deleteByParentIdInSql = delStmt + deriveWhereParentIdSql(true);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRelationshipProperty(BeanPropertyAssocMany<?> relationshipProperty) {
/* 140 */     this.relationshipProperty = relationshipProperty;
/*     */   }
/*     */   
/*     */   public BeanPropertyAssocMany<?> getRelationshipProperty() {
/* 144 */     return this.relationshipProperty;
/*     */   }
/*     */   
/*     */   public void cacheClear() {
/* 148 */     if (this.targetDescriptor.isBeanCaching() && this.relationshipProperty != null) {
/* 149 */       this.targetDescriptor.cacheClearCachedManyIds(this.relationshipProperty.getName());
/*     */     }
/*     */   }
/*     */   
/*     */   public void cacheDelete(boolean clearOnNull, Object bean) {
/* 154 */     if (this.targetDescriptor.isBeanCaching() && this.relationshipProperty != null) {
/* 155 */       Object assocBean = getValue(bean);
/* 156 */       if (assocBean != null) {
/* 157 */         Object parentId = this.targetDescriptor.getId(assocBean);
/* 158 */         if (parentId != null) {
/* 159 */           this.targetDescriptor.cacheRemoveCachedManyIds(parentId, this.relationshipProperty.getName());
/*     */           return;
/*     */         } 
/*     */       } 
/* 163 */       if (clearOnNull) {
/* 164 */         this.targetDescriptor.cacheClearCachedManyIds(this.relationshipProperty.getName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ElPropertyValue buildElPropertyValue(String propName, String remainder, ElPropertyChainBuilder chain, boolean propertyDeploy) {
/* 171 */     if (this.embedded) {
/* 172 */       BeanProperty embProp = this.embeddedPropsMap.get(remainder);
/* 173 */       if (embProp == null) {
/* 174 */         String msg = "Embedded Property " + remainder + " not found in " + getFullBeanName();
/* 175 */         throw new PersistenceException(msg);
/*     */       } 
/* 177 */       if (chain == null) {
/* 178 */         chain = new ElPropertyChainBuilder(true, propName);
/*     */       }
/* 180 */       chain.add(this);
/* 181 */       return (ElPropertyValue)chain.add(embProp).build();
/*     */     } 
/*     */     
/* 184 */     return createElPropertyValue(propName, remainder, chain, propertyDeploy);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getElPlaceholder(boolean encrypted) {
/* 189 */     return encrypted ? this.elPlaceHolderEncrypted : this.elPlaceHolder;
/*     */   }
/*     */   
/*     */   public SqlUpdate deleteByParentId(Object parentId, List<Object> parentIdist) {
/* 193 */     if (parentId != null) {
/* 194 */       return deleteByParentId(parentId);
/*     */     }
/* 196 */     return deleteByParentIdList(parentIdist);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private SqlUpdate deleteByParentIdList(List<Object> parentIdist) {
/* 202 */     StringBuilder sb = new StringBuilder(100);
/* 203 */     sb.append(this.deleteByParentIdInSql);
/*     */     
/* 205 */     String inClause = this.targetIdBinder.getIdInValueExpr(parentIdist.size());
/* 206 */     sb.append(inClause);
/*     */     
/* 208 */     DefaultSqlUpdate delete = new DefaultSqlUpdate(sb.toString());
/* 209 */     for (int i = 0; i < parentIdist.size(); i++) {
/* 210 */       this.targetIdBinder.bindId(delete, parentIdist.get(i));
/*     */     }
/*     */     
/* 213 */     return (SqlUpdate)delete;
/*     */   }
/*     */ 
/*     */   
/*     */   private SqlUpdate deleteByParentId(Object parentId) {
/* 218 */     DefaultSqlUpdate delete = new DefaultSqlUpdate(this.deleteByParentIdSql);
/* 219 */     if (this.exportedProperties.length == 1) {
/* 220 */       delete.addParameter(parentId);
/*     */     } else {
/* 222 */       this.targetDescriptor.getIdBinder().bindId(delete, parentId);
/*     */     } 
/* 224 */     return (SqlUpdate)delete;
/*     */   }
/*     */   
/*     */   public List<Object> findIdsByParentId(Object parentId, List<Object> parentIdist, Transaction t) {
/* 228 */     if (parentId != null) {
/* 229 */       return findIdsByParentId(parentId, t);
/*     */     }
/* 231 */     return findIdsByParentIdList(parentIdist, t);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Object> findIdsByParentId(Object parentId, Transaction t) {
/* 237 */     String rawWhere = deriveWhereParentIdSql(false);
/*     */     
/* 239 */     SpiEbeanServer spiEbeanServer = getBeanDescriptor().getEbeanServer();
/* 240 */     Query<?> q = spiEbeanServer.find(getPropertyType()).where().raw(rawWhere).query();
/*     */ 
/*     */     
/* 243 */     bindWhereParendId(q, parentId);
/* 244 */     return spiEbeanServer.findIds(q, t);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Object> findIdsByParentIdList(List<Object> parentIdist, Transaction t) {
/* 249 */     String rawWhere = deriveWhereParentIdSql(true);
/* 250 */     String inClause = this.targetIdBinder.getIdInValueExpr(parentIdist.size());
/*     */     
/* 252 */     String expr = rawWhere + inClause;
/*     */     
/* 254 */     SpiEbeanServer spiEbeanServer = getBeanDescriptor().getEbeanServer();
/* 255 */     Query<?> q = (Query)spiEbeanServer.find(getPropertyType()).where().raw(expr);
/*     */ 
/*     */     
/* 258 */     for (int i = 0; i < parentIdist.size(); i++) {
/* 259 */       bindWhereParendId(q, parentIdist.get(i));
/*     */     }
/*     */     
/* 262 */     return spiEbeanServer.findIds(q, t);
/*     */   }
/*     */ 
/*     */   
/*     */   private void bindWhereParendId(Query<?> q, Object parentId) {
/* 267 */     if (this.exportedProperties.length == 1) {
/* 268 */       q.setParameter(1, parentId);
/*     */     } else {
/*     */       
/* 271 */       int pos = 1;
/* 272 */       for (int i = 0; i < this.exportedProperties.length; i++) {
/* 273 */         Object embVal = this.exportedProperties[i].getValue(parentId);
/* 274 */         q.setParameter(pos++, embVal);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addFkey() {
/* 280 */     if (this.importedId != null) {
/* 281 */       this.importedId.addFkeys(this.name);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValueLoaded(Object value) {
/* 287 */     if (value instanceof EntityBean) {
/* 288 */       return ((EntityBean)value)._ebean_getIntercept().isLoaded();
/*     */     }
/* 290 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InvalidValue validateCascade(Object value) {
/* 296 */     BeanDescriptor<?> target = getTargetDescriptor();
/* 297 */     return target.validate(true, value);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasChangedEmbedded(Object bean, Object oldValues) {
/* 302 */     Object embValue = getValue(oldValues);
/* 303 */     if (embValue instanceof EntityBean)
/*     */     {
/* 305 */       return ((EntityBean)embValue)._ebean_getIntercept().isNewOrDirty();
/*     */     }
/* 307 */     if (embValue == null) {
/* 308 */       return (getValue(bean) != null);
/*     */     }
/* 310 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasChanged(Object bean, Object oldValues) {
/* 316 */     if (this.embedded) {
/* 317 */       return hasChangedEmbedded(bean, oldValues);
/*     */     }
/* 319 */     Object value = getValue(bean);
/* 320 */     Object oldVal = getValue(oldValues);
/* 321 */     if (this.oneToOneExported)
/*     */     {
/* 323 */       return false;
/*     */     }
/* 325 */     if (value == null)
/* 326 */       return (oldVal != null); 
/* 327 */     if (oldValues == null) {
/* 328 */       return true;
/*     */     }
/*     */     
/* 331 */     return this.importedId.hasChanged(value, oldVal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanProperty[] getProperties() {
/* 340 */     return this.embeddedProps;
/*     */   }
/*     */ 
/*     */   
/*     */   public void buildSelectExpressionChain(String prefix, List<String> selectChain) {
/* 345 */     prefix = SplitName.add(prefix, this.name);
/*     */     
/* 347 */     if (!this.embedded) {
/* 348 */       this.targetIdBinder.buildSelectExpressionChain(prefix, selectChain);
/*     */     } else {
/*     */       
/* 351 */       for (int i = 0; i < this.embeddedProps.length; i++) {
/* 352 */         this.embeddedProps[i].buildSelectExpressionChain(prefix, selectChain);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOneToOne() {
/* 362 */     return this.oneToOne;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOneToOneExported() {
/* 369 */     return this.oneToOneExported;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmbeddedVersion() {
/* 376 */     return this.embeddedVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isImportedPrimaryKey() {
/* 383 */     return this.importedPrimaryKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getTargetType() {
/* 391 */     return getPropertyType();
/*     */   }
/*     */   
/*     */   public Object getCacheDataValue(Object bean) {
/* 395 */     if (this.embedded) {
/* 396 */       throw new RuntimeException();
/*     */     }
/* 398 */     Object ap = getValue(bean);
/* 399 */     if (ap == null) {
/* 400 */       return null;
/*     */     }
/* 402 */     return this.targetDescriptor.getId(ap);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCacheDataValue(Object bean, Object cacheData, Object oldValues, boolean readOnly) {
/* 408 */     if (cacheData != null) {
/* 409 */       if (this.embedded) {
/* 410 */         throw new RuntimeException();
/*     */       }
/* 412 */       boolean vanillaMode = false;
/* 413 */       T ref = this.targetDescriptor.createReference(vanillaMode, Boolean.FALSE, cacheData, null);
/* 414 */       setValue(bean, ref);
/* 415 */       if (oldValues != null) {
/* 416 */         setValue(oldValues, ref);
/*     */       }
/* 418 */       if (readOnly && !vanillaMode) {
/* 419 */         ((EntityBean)ref)._ebean_intercept().setReadOnly(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getAssocOneIdValues(Object bean) {
/* 430 */     return this.targetDescriptor.getIdBinder().getIdValues(bean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAssocOneIdExpr(String prefix, String operator) {
/* 437 */     return this.targetDescriptor.getIdBinder().getAssocOneIdExpr(prefix, operator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAssocIdInValueExpr(int size) {
/* 445 */     return this.targetDescriptor.getIdBinder().getIdInValueExpr(size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAssocIdInExpr(String prefix) {
/* 453 */     return this.targetDescriptor.getIdBinder().getAssocIdInExpr(prefix);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAssocId() {
/* 458 */     return !this.embedded;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAssocProperty() {
/* 463 */     return !this.embedded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createEmbeddedId() {
/* 472 */     return getTargetDescriptor().createVanillaBean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createEmptyReference() {
/* 479 */     return this.targetDescriptor.createEntityBean();
/*     */   }
/*     */   
/*     */   public void elSetReference(Object bean) {
/* 483 */     Object value = getValueIntercept(bean);
/* 484 */     if (value != null) {
/* 485 */       ((EntityBean)value)._ebean_getIntercept().setReference();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Object elGetReference(Object bean) {
/* 491 */     Object value = getValueIntercept(bean);
/* 492 */     if (value == null) {
/* 493 */       value = this.targetDescriptor.createEntityBean();
/* 494 */       setValueIntercept(bean, value);
/*     */     } 
/* 496 */     return value;
/*     */   }
/*     */   
/*     */   public ImportedId getImportedId() {
/* 500 */     return this.importedId;
/*     */   }
/*     */ 
/*     */   
/*     */   private String deriveWhereParentIdSql(boolean inClause) {
/* 505 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 507 */     for (int i = 0; i < this.exportedProperties.length; i++) {
/* 508 */       String fkColumn = this.exportedProperties[i].getForeignDbColumn();
/* 509 */       if (i > 0) {
/* 510 */         String s = inClause ? "," : " and ";
/* 511 */         sb.append(s);
/*     */       } 
/* 513 */       sb.append(fkColumn);
/* 514 */       if (!inClause) {
/* 515 */         sb.append("=? ");
/*     */       }
/*     */     } 
/* 518 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ExportedProperty[] createExported() {
/* 526 */     BeanProperty[] uids = this.descriptor.propertiesId();
/*     */     
/* 528 */     ArrayList<ExportedProperty> list = new ArrayList<ExportedProperty>();
/*     */     
/* 530 */     if (uids.length == 1 && uids[0].isEmbedded()) {
/*     */       
/* 532 */       BeanPropertyAssocOne<?> one = (BeanPropertyAssocOne)uids[0];
/* 533 */       BeanDescriptor<?> targetDesc = one.getTargetDescriptor();
/* 534 */       BeanProperty[] emIds = targetDesc.propertiesBaseScalar();
/*     */       try {
/* 536 */         for (int i = 0; i < emIds.length; i++) {
/* 537 */           ExportedProperty expProp = findMatch(true, emIds[i]);
/* 538 */           list.add(expProp);
/*     */         } 
/* 540 */       } catch (PersistenceException e) {
/*     */         
/* 542 */         e.printStackTrace();
/*     */       } 
/*     */     } else {
/*     */       
/* 546 */       for (int i = 0; i < uids.length; i++) {
/* 547 */         ExportedProperty expProp = findMatch(false, uids[i]);
/* 548 */         list.add(expProp);
/*     */       } 
/*     */     } 
/*     */     
/* 552 */     return list.<ExportedProperty>toArray(new ExportedProperty[list.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ExportedProperty findMatch(boolean embeddedProp, BeanProperty prop) {
/* 560 */     String matchColumn = prop.getDbColumn();
/*     */     
/* 562 */     String searchTable = this.tableJoin.getTable();
/* 563 */     TableJoinColumn[] columns = this.tableJoin.columns();
/*     */     
/* 565 */     for (int i = 0; i < columns.length; i++) {
/* 566 */       String matchTo = columns[i].getLocalDbColumn();
/*     */       
/* 568 */       if (matchColumn.equalsIgnoreCase(matchTo)) {
/* 569 */         String foreignCol = columns[i].getForeignDbColumn();
/* 570 */         return new ExportedProperty(embeddedProp, foreignCol, prop);
/*     */       } 
/*     */     } 
/*     */     
/* 574 */     String msg = "Error with the Join on [" + getFullBeanName() + "]. Could not find the matching foreign key for [" + matchColumn + "] in table[" + searchTable + "]?" + " Perhaps using a @JoinColumn with the name/referencedColumnName attributes swapped?";
/*     */ 
/*     */     
/* 577 */     throw new PersistenceException(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendSelect(DbSqlContext ctx, boolean subQuery) {
/* 583 */     if (!this.isTransient) {
/* 584 */       this.localHelp.appendSelect(ctx, subQuery);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
/* 590 */     if (!this.isTransient) {
/* 591 */       this.localHelp.appendFrom(ctx, forceOuterJoin);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Object readSet(DbReadContext ctx, Object bean, Class<?> type) throws SQLException {
/* 597 */     boolean assignable = (type == null || this.owningType.isAssignableFrom(type));
/* 598 */     return this.localHelp.readSet(ctx, bean, assignable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object read(DbReadContext ctx) throws SQLException {
/* 608 */     return this.localHelp.read(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadIgnore(DbReadContext ctx) {
/* 613 */     this.localHelp.loadIgnore(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(SqlBeanLoad sqlBeanLoad) throws SQLException {
/* 618 */     Object dbVal = sqlBeanLoad.load(this);
/* 619 */     if (this.embedded && sqlBeanLoad.isLazyLoad() && 
/* 620 */       dbVal instanceof EntityBean) {
/* 621 */       ((EntityBean)dbVal)._ebean_getIntercept().setLoaded();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private LocalHelp createHelp(boolean embedded, boolean oneToOneExported) {
/* 627 */     if (embedded)
/* 628 */       return new Embedded(); 
/* 629 */     if (oneToOneExported) {
/* 630 */       return new ReferenceExported();
/*     */     }
/* 632 */     return new Reference(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private abstract class LocalHelp
/*     */   {
/*     */     private LocalHelp() {}
/*     */     
/*     */     abstract void loadIgnore(DbReadContext param1DbReadContext);
/*     */     
/*     */     abstract Object read(DbReadContext param1DbReadContext) throws SQLException;
/*     */     
/*     */     abstract Object readSet(DbReadContext param1DbReadContext, Object param1Object, boolean param1Boolean) throws SQLException;
/*     */     
/*     */     abstract void appendSelect(DbSqlContext param1DbSqlContext, boolean param1Boolean);
/*     */     
/*     */     abstract void appendFrom(DbSqlContext param1DbSqlContext, boolean param1Boolean);
/*     */   }
/*     */   
/*     */   private final class Embedded
/*     */     extends LocalHelp
/*     */   {
/*     */     private Embedded() {}
/*     */     
/*     */     void loadIgnore(DbReadContext ctx) {
/* 657 */       for (int i = 0; i < BeanPropertyAssocOne.this.embeddedProps.length; i++) {
/* 658 */         BeanPropertyAssocOne.this.embeddedProps[i].loadIgnore(ctx);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     Object readSet(DbReadContext ctx, Object bean, boolean assignable) throws SQLException {
/* 664 */       Object dbVal = read(ctx);
/* 665 */       if (bean != null && assignable) {
/*     */         
/* 667 */         BeanPropertyAssocOne.this.setValue(bean, dbVal);
/* 668 */         ctx.propagateState(dbVal);
/* 669 */         return dbVal;
/*     */       } 
/*     */       
/* 672 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Object read(DbReadContext ctx) throws SQLException {
/* 678 */       EntityBean embeddedBean = BeanPropertyAssocOne.this.targetDescriptor.createEntityBean();
/*     */       
/* 680 */       boolean notNull = false;
/* 681 */       for (int i = 0; i < BeanPropertyAssocOne.this.embeddedProps.length; i++) {
/* 682 */         Object value = BeanPropertyAssocOne.this.embeddedProps[i].readSet(ctx, embeddedBean, null);
/* 683 */         if (value != null) {
/* 684 */           notNull = true;
/*     */         }
/*     */       } 
/* 687 */       if (notNull) {
/* 688 */         ctx.propagateState(embeddedBean);
/* 689 */         return embeddedBean;
/*     */       } 
/* 691 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {}
/*     */ 
/*     */ 
/*     */     
/*     */     void appendSelect(DbSqlContext ctx, boolean subQuery) {
/* 701 */       for (int i = 0; i < BeanPropertyAssocOne.this.embeddedProps.length; i++) {
/* 702 */         BeanPropertyAssocOne.this.embeddedProps[i].appendSelect(ctx, subQuery);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class Reference
/*     */     extends LocalHelp
/*     */   {
/*     */     Reference(BeanPropertyAssocOne<?> beanProp) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void loadIgnore(DbReadContext ctx) {
/* 719 */       BeanPropertyAssocOne.this.targetIdBinder.loadIgnore(ctx);
/* 720 */       if (BeanPropertyAssocOne.this.targetInheritInfo != null) {
/* 721 */         ctx.getDataReader().incrementPos(1);
/*     */       }
/*     */     }
/*     */     
/*     */     Object readSet(DbReadContext ctx, Object bean, boolean assignable) throws SQLException {
/* 726 */       Object val = read(ctx);
/* 727 */       if (bean != null && assignable) {
/* 728 */         BeanPropertyAssocOne.this.setValue(bean, val);
/* 729 */         ctx.propagateState(val);
/*     */       } 
/* 731 */       return val;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object read(DbReadContext ctx) throws SQLException {
/*     */       Object ref;
/* 740 */       BeanDescriptor<?> rowDescriptor = null;
/* 741 */       Class<?> rowType = BeanPropertyAssocOne.this.targetType;
/* 742 */       if (BeanPropertyAssocOne.this.targetInheritInfo != null) {
/*     */         
/* 744 */         InheritInfo rowInheritInfo = BeanPropertyAssocOne.this.targetInheritInfo.readType(ctx);
/* 745 */         if (rowInheritInfo != null) {
/* 746 */           rowType = rowInheritInfo.getType();
/* 747 */           rowDescriptor = rowInheritInfo.getBeanDescriptor();
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 752 */       Object id = BeanPropertyAssocOne.this.targetIdBinder.read(ctx);
/* 753 */       if (id == null) {
/* 754 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 758 */       Object existing = ctx.getPersistenceContext().get(rowType, id);
/*     */       
/* 760 */       if (existing != null) {
/* 761 */         return existing;
/*     */       }
/*     */ 
/*     */       
/* 765 */       Object parent = null;
/* 766 */       boolean vanillaMode = ctx.isVanillaMode();
/*     */ 
/*     */       
/* 769 */       Boolean readOnly = ctx.isReadOnly();
/*     */       
/* 771 */       if (BeanPropertyAssocOne.this.targetInheritInfo != null) {
/*     */         
/* 773 */         ref = rowDescriptor.createReference(vanillaMode, readOnly, id, parent);
/*     */       } else {
/* 775 */         ref = BeanPropertyAssocOne.this.targetDescriptor.createReference(vanillaMode, readOnly, id, parent);
/*     */       } 
/*     */       
/* 778 */       Object existingBean = ctx.getPersistenceContext().putIfAbsent(id, ref);
/* 779 */       if (existingBean != null) {
/*     */ 
/*     */ 
/*     */         
/* 783 */         ref = existingBean;
/*     */       }
/* 785 */       else if (!vanillaMode) {
/* 786 */         EntityBeanIntercept ebi = ((EntityBean)ref)._ebean_getIntercept();
/* 787 */         if (Boolean.TRUE.equals(ctx.isReadOnly())) {
/* 788 */           ebi.setReadOnly(true);
/*     */         }
/* 790 */         ctx.register(BeanPropertyAssocOne.this.name, ebi);
/*     */       } 
/*     */       
/* 793 */       return ref;
/*     */     }
/*     */ 
/*     */     
/*     */     void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
/* 798 */       if (BeanPropertyAssocOne.this.targetInheritInfo != null) {
/*     */         
/* 800 */         String relativePrefix = ctx.getRelativePrefix(BeanPropertyAssocOne.this.name);
/* 801 */         BeanPropertyAssocOne.this.tableJoin.addJoin(forceOuterJoin, relativePrefix, ctx);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void appendSelect(DbSqlContext ctx, boolean subQuery) {
/* 811 */       if (!subQuery && BeanPropertyAssocOne.this.targetInheritInfo != null) {
/*     */         
/* 813 */         String relativePrefix = ctx.getRelativePrefix(BeanPropertyAssocOne.this.getName());
/* 814 */         String tableAlias = ctx.getTableAlias(relativePrefix);
/* 815 */         ctx.appendColumn(tableAlias, BeanPropertyAssocOne.this.targetInheritInfo.getDiscriminatorColumn());
/*     */       } 
/* 817 */       BeanPropertyAssocOne.this.importedId.sqlAppend(ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private final class ReferenceExported
/*     */     extends LocalHelp
/*     */   {
/*     */     private ReferenceExported() {}
/*     */     
/*     */     void loadIgnore(DbReadContext ctx) {
/* 828 */       BeanPropertyAssocOne.this.targetDescriptor.getIdBinder().loadIgnore(ctx);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object readSet(DbReadContext ctx, Object bean, boolean assignable) throws SQLException {
/* 837 */       Object dbVal = read(ctx);
/* 838 */       if (bean != null && assignable) {
/* 839 */         BeanPropertyAssocOne.this.setValue(bean, dbVal);
/* 840 */         ctx.propagateState(dbVal);
/*     */       } 
/* 842 */       return dbVal;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object read(DbReadContext ctx) throws SQLException {
/* 849 */       IdBinder idBinder = BeanPropertyAssocOne.this.targetDescriptor.getIdBinder();
/* 850 */       Object id = idBinder.read(ctx);
/* 851 */       if (id == null) {
/* 852 */         return null;
/*     */       }
/*     */       
/* 855 */       PersistenceContext persistCtx = ctx.getPersistenceContext();
/* 856 */       Object existing = persistCtx.get(BeanPropertyAssocOne.this.targetType, id);
/*     */       
/* 858 */       if (existing != null) {
/* 859 */         return existing;
/*     */       }
/* 861 */       boolean vanillaMode = ctx.isVanillaMode();
/* 862 */       Object parent = null;
/* 863 */       Object ref = BeanPropertyAssocOne.this.targetDescriptor.createReference(vanillaMode, ctx.isReadOnly(), id, parent);
/*     */       
/* 865 */       if (!vanillaMode) {
/* 866 */         EntityBeanIntercept ebi = ((EntityBean)ref)._ebean_getIntercept();
/* 867 */         if (Boolean.TRUE.equals(ctx.isReadOnly())) {
/* 868 */           ebi.setReadOnly(true);
/*     */         }
/* 870 */         persistCtx.put(id, ref);
/* 871 */         ctx.register(BeanPropertyAssocOne.this.name, ebi);
/*     */       } 
/* 873 */       return ref;
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
/*     */     void appendSelect(DbSqlContext ctx, boolean subQuery) {
/* 885 */       String relativePrefix = ctx.getRelativePrefix(BeanPropertyAssocOne.this.getName());
/* 886 */       ctx.pushTableAlias(relativePrefix);
/*     */       
/* 888 */       IdBinder idBinder = BeanPropertyAssocOne.this.targetDescriptor.getIdBinder();
/* 889 */       idBinder.appendSelect(ctx, subQuery);
/*     */       
/* 891 */       ctx.popTableAlias();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
/* 897 */       String relativePrefix = ctx.getRelativePrefix(BeanPropertyAssocOne.this.getName());
/* 898 */       BeanPropertyAssocOne.this.tableJoin.addJoin(forceOuterJoin, relativePrefix, ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void jsonWrite(WriteJsonContext ctx, Object bean) {
/* 905 */     Object value = getValueIntercept(bean);
/* 906 */     if (value == null) {
/* 907 */       ctx.beginAssocOneIsNull(this.name);
/*     */     
/*     */     }
/* 910 */     else if (!ctx.isParentBean(value)) {
/*     */ 
/*     */ 
/*     */       
/* 914 */       ctx.pushParentBean(bean);
/* 915 */       ctx.beginAssocOne(this.name);
/* 916 */       BeanDescriptor<?> refDesc = this.descriptor.getBeanDescriptor(value.getClass());
/* 917 */       refDesc.jsonWrite(ctx, value);
/* 918 */       ctx.endAssocOne();
/* 919 */       ctx.popParentBean();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void jsonRead(ReadJsonContext ctx, Object bean) {
/* 927 */     T assocBean = this.targetDescriptor.jsonReadBean(ctx, this.name);
/* 928 */     setValue(bean, assocBean);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanPropertyAssocOne.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */