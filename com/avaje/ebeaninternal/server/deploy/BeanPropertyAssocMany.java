/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.Expression;
/*     */ import com.avaje.ebean.InvalidValue;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.SqlUpdate;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionAdd;
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssoc;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
/*     */ import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ public class BeanPropertyAssocMany<T>
/*     */   extends BeanPropertyAssoc<T>
/*     */ {
/*     */   final TableJoin intersectionJoin;
/*     */   final TableJoin inverseJoin;
/*     */   final boolean unidirectional;
/*     */   final boolean manyToMany;
/*     */   final String fetchOrderBy;
/*     */   final String mapKey;
/*     */   final ManyType manyType;
/*     */   final String serverName;
/*     */   final BeanCollection.ModifyListenMode modifyListenMode;
/*     */   BeanProperty mapKeyProperty;
/*     */   ExportedProperty[] exportedProperties;
/*     */   BeanPropertyAssocOne<?> childMasterProperty;
/*     */   boolean embeddedExportedProperties;
/*     */   BeanCollectionHelp<T> help;
/*     */   ImportedId importedId;
/*     */   String deleteByParentIdSql;
/*     */   String deleteByParentIdInSql;
/*     */   final CollectionTypeConverter typeConverter;
/*     */   
/*     */   public BeanPropertyAssocMany(BeanDescriptorMap owner, BeanDescriptor<?> descriptor, DeployBeanPropertyAssocMany<T> deploy) {
/* 116 */     super(owner, descriptor, (DeployBeanPropertyAssoc<T>)deploy);
/* 117 */     this.unidirectional = deploy.isUnidirectional();
/* 118 */     this.manyToMany = deploy.isManyToMany();
/* 119 */     this.serverName = descriptor.getServerName();
/* 120 */     this.manyType = deploy.getManyType();
/* 121 */     this.typeConverter = this.manyType.getTypeConverter();
/* 122 */     this.mapKey = deploy.getMapKey();
/* 123 */     this.fetchOrderBy = deploy.getFetchOrderBy();
/*     */     
/* 125 */     this.intersectionJoin = deploy.createIntersectionTableJoin();
/* 126 */     this.inverseJoin = deploy.createInverseTableJoin();
/* 127 */     this.modifyListenMode = deploy.getModifyListenMode();
/*     */   }
/*     */   
/*     */   public void initialise() {
/* 131 */     super.initialise();
/*     */     
/* 133 */     if (!this.isTransient) {
/* 134 */       String delStmt; this.help = BeanCollectionHelpFactory.create(this);
/*     */       
/* 136 */       if (this.manyToMany) {
/*     */         
/* 138 */         this.importedId = createImportedId(this, this.targetDescriptor, this.tableJoin);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 143 */         this.childMasterProperty = initChildMasterProperty();
/* 144 */         if (this.childMasterProperty != null) {
/* 145 */           this.childMasterProperty.setRelationshipProperty(this);
/*     */         }
/*     */       } 
/*     */       
/* 149 */       if (this.mapKey != null) {
/* 150 */         this.mapKeyProperty = initMapKeyProperty();
/*     */       }
/*     */       
/* 153 */       this.exportedProperties = createExported();
/* 154 */       if (this.exportedProperties.length > 0) {
/* 155 */         this.embeddedExportedProperties = this.exportedProperties[0].isEmbedded();
/*     */       }
/*     */ 
/*     */       
/* 159 */       if (this.manyToMany) {
/* 160 */         delStmt = "delete from " + this.inverseJoin.getTable() + " where ";
/*     */       } else {
/* 162 */         delStmt = "delete from " + this.targetDescriptor.getBaseTable() + " where ";
/*     */       } 
/* 164 */       this.deleteByParentIdSql = delStmt + deriveWhereParentIdSql(false);
/* 165 */       this.deleteByParentIdInSql = delStmt + deriveWhereParentIdSql(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValueUnderlying(Object bean) {
/* 175 */     Object value = getValue(bean);
/* 176 */     if (this.typeConverter != null) {
/* 177 */       value = this.typeConverter.toUnderlying(value);
/*     */     }
/* 179 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValue(Object bean) {
/* 184 */     return super.getValue(bean);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValueIntercept(Object bean) {
/* 189 */     return super.getValueIntercept(bean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(Object bean, Object value) {
/* 194 */     if (this.typeConverter != null) {
/* 195 */       value = this.typeConverter.toWrapped(value);
/*     */     }
/* 197 */     super.setValue(bean, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueIntercept(Object bean, Object value) {
/* 202 */     if (this.typeConverter != null) {
/* 203 */       value = this.typeConverter.toWrapped(value);
/*     */     }
/* 205 */     super.setValueIntercept(bean, value);
/*     */   }
/*     */   
/*     */   public ElPropertyValue buildElPropertyValue(String propName, String remainder, ElPropertyChainBuilder chain, boolean propertyDeploy) {
/* 209 */     return createElPropertyValue(propName, remainder, chain, propertyDeploy);
/*     */   }
/*     */   
/*     */   public SqlUpdate deleteByParentId(Object parentId, List<Object> parentIdist) {
/* 213 */     if (parentId != null) {
/* 214 */       return deleteByParentId(parentId);
/*     */     }
/* 216 */     return deleteByParentIdList(parentIdist);
/*     */   }
/*     */ 
/*     */   
/*     */   private SqlUpdate deleteByParentId(Object parentId) {
/* 221 */     DefaultSqlUpdate sqlDelete = new DefaultSqlUpdate(this.deleteByParentIdSql);
/* 222 */     bindWhereParendId(sqlDelete, parentId);
/* 223 */     return (SqlUpdate)sqlDelete;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Object> findIdsByParentId(Object parentId, List<Object> parentIdist, Transaction t, ArrayList<Object> excludeDetailIds) {
/* 230 */     if (parentId != null) {
/* 231 */       return findIdsByParentId(parentId, t, excludeDetailIds);
/*     */     }
/* 233 */     return findIdsByParentIdList(parentIdist, t, excludeDetailIds);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Object> findIdsByParentId(Object parentId, Transaction t, ArrayList<Object> excludeDetailIds) {
/* 239 */     String rawWhere = deriveWhereParentIdSql(false);
/*     */     
/* 241 */     SpiEbeanServer spiEbeanServer = getBeanDescriptor().getEbeanServer();
/* 242 */     Query<?> q = spiEbeanServer.find(getPropertyType()).where().raw(rawWhere).query();
/*     */ 
/*     */     
/* 245 */     bindWhereParendId(1, q, parentId);
/*     */     
/* 247 */     if (excludeDetailIds != null && !excludeDetailIds.isEmpty()) {
/* 248 */       Expression idIn = q.getExpressionFactory().idIn(excludeDetailIds);
/* 249 */       q.where().not(idIn);
/*     */     } 
/*     */     
/* 252 */     return spiEbeanServer.findIds(q, t);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Object> findIdsByParentIdList(List<Object> parentIdist, Transaction t, ArrayList<Object> excludeDetailIds) {
/* 257 */     String rawWhere = deriveWhereParentIdSql(true);
/* 258 */     String inClause = this.targetIdBinder.getIdInValueExpr(parentIdist.size());
/*     */     
/* 260 */     String expr = rawWhere + inClause;
/*     */     
/* 262 */     SpiEbeanServer spiEbeanServer = getBeanDescriptor().getEbeanServer();
/* 263 */     Query<?> q = spiEbeanServer.find(getPropertyType()).where().raw(expr).query();
/*     */ 
/*     */     
/* 266 */     int pos = 1;
/* 267 */     for (int i = 0; i < parentIdist.size(); i++) {
/* 268 */       pos = bindWhereParendId(pos, q, parentIdist.get(i));
/*     */     }
/*     */     
/* 271 */     if (excludeDetailIds != null && !excludeDetailIds.isEmpty()) {
/* 272 */       Expression idIn = q.getExpressionFactory().idIn(excludeDetailIds);
/* 273 */       q.where().not(idIn);
/*     */     } 
/*     */     
/* 276 */     return spiEbeanServer.findIds(q, t);
/*     */   }
/*     */ 
/*     */   
/*     */   private SqlUpdate deleteByParentIdList(List<Object> parentIdist) {
/* 281 */     StringBuilder sb = new StringBuilder(100);
/* 282 */     sb.append(this.deleteByParentIdInSql);
/*     */     
/* 284 */     String inClause = this.targetIdBinder.getIdInValueExpr(parentIdist.size());
/* 285 */     sb.append(inClause);
/*     */     
/* 287 */     DefaultSqlUpdate delete = new DefaultSqlUpdate(sb.toString());
/* 288 */     for (int i = 0; i < parentIdist.size(); i++) {
/* 289 */       bindWhereParendId(delete, parentIdist.get(i));
/*     */     }
/*     */     
/* 292 */     return (SqlUpdate)delete;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoader(BeanCollectionLoader loader) {
/* 300 */     if (this.help != null) {
/* 301 */       this.help.setLoader(loader);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCollection.ModifyListenMode getModifyListenMode() {
/* 310 */     return this.modifyListenMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasChanged(Object bean, Object oldValues) {
/* 317 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendSelect(DbSqlContext ctx, boolean subQuery) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadIgnore(DbReadContext ctx) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(SqlBeanLoad sqlBeanLoad) throws SQLException {
/* 331 */     sqlBeanLoad.loadAssocMany(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object readSet(DbReadContext ctx, Object bean, Class<?> type) throws SQLException {
/* 336 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object read(DbReadContext ctx) throws SQLException {
/* 341 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValueLoaded(Object value) {
/* 347 */     if (value instanceof BeanCollection) {
/* 348 */       return ((BeanCollection)value).isPopulated();
/*     */     }
/* 350 */     return true;
/*     */   }
/*     */   
/*     */   public void add(BeanCollection<?> collection, Object bean) {
/* 354 */     this.help.add(collection, bean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InvalidValue validateCascade(Object manyValue) {
/* 360 */     ArrayList<InvalidValue> errs = this.help.validate(manyValue);
/*     */     
/* 362 */     if (errs == null) {
/* 363 */       return null;
/*     */     }
/* 365 */     return new InvalidValue("recurse.many", this.targetDescriptor.getFullName(), manyValue, InvalidValue.toArray(errs));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refresh(EbeanServer server, Query<?> query, Transaction t, Object parentBean) {
/* 373 */     this.help.refresh(server, query, t, parentBean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refresh(BeanCollection<?> bc, Object parentBean) {
/* 380 */     this.help.refresh(bc, parentBean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getAssocOneIdValues(Object bean) {
/* 388 */     return this.targetDescriptor.getIdBinder().getIdValues(bean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAssocOneIdExpr(String prefix, String operator) {
/* 395 */     return this.targetDescriptor.getIdBinder().getAssocOneIdExpr(prefix, operator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAssocIdInValueExpr(int size) {
/* 403 */     return this.targetDescriptor.getIdBinder().getIdInValueExpr(size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAssocIdInExpr(String prefix) {
/* 411 */     return this.targetDescriptor.getIdBinder().getAssocIdInExpr(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAssocId() {
/* 417 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAssocProperty() {
/* 422 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsMany() {
/* 430 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ManyType getManyType() {
/* 437 */     return this.manyType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isManyToMany() {
/* 444 */     return this.manyToMany;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableJoin getIntersectionTableJoin() {
/* 451 */     return this.intersectionJoin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJoinValuesToChild(Object parent, Object child, Object mapKeyValue) {
/* 460 */     if (this.mapKeyProperty != null) {
/* 461 */       this.mapKeyProperty.setValue(child, mapKeyValue);
/*     */     }
/*     */     
/* 464 */     if (!this.manyToMany && 
/* 465 */       this.childMasterProperty != null)
/*     */     {
/*     */       
/* 468 */       this.childMasterProperty.setValue(child, parent);
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
/*     */   public String getFetchOrderBy() {
/* 481 */     return this.fetchOrderBy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMapKey() {
/* 488 */     return this.mapKey;
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanCollection<?> createReferenceIfNull(Object parentBean) {
/* 493 */     Object v = getValue(parentBean);
/* 494 */     if (v instanceof BeanCollection) {
/* 495 */       BeanCollection<?> bc = (BeanCollection)v;
/* 496 */       return bc.isReference() ? bc : null;
/*     */     } 
/* 498 */     return createReference(parentBean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCollection<?> createReference(Object parentBean) {
/* 504 */     BeanCollection<?> ref = this.help.createReference(parentBean, this.name);
/* 505 */     setValue(parentBean, ref);
/* 506 */     return ref;
/*     */   }
/*     */   
/*     */   public Object createEmpty(boolean vanilla) {
/* 510 */     return this.help.createEmpty(vanilla);
/*     */   }
/*     */   
/*     */   public BeanCollectionAdd getBeanCollectionAdd(Object bc, String mapKey) {
/* 514 */     return this.help.getBeanCollectionAdd(bc, mapKey);
/*     */   }
/*     */   
/*     */   public Object getParentId(Object parentBean) {
/* 518 */     return this.descriptor.getId(parentBean);
/*     */   }
/*     */ 
/*     */   
/*     */   private void bindWhereParendId(DefaultSqlUpdate sqlUpd, Object parentId) {
/* 523 */     if (this.exportedProperties.length == 1) {
/* 524 */       sqlUpd.addParameter(parentId);
/*     */       return;
/*     */     } 
/* 527 */     for (int i = 0; i < this.exportedProperties.length; i++) {
/* 528 */       Object embVal = this.exportedProperties[i].getValue(parentId);
/* 529 */       sqlUpd.addParameter(embVal);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int bindWhereParendId(int pos, Query<?> q, Object parentId) {
/* 535 */     if (this.exportedProperties.length == 1) {
/* 536 */       q.setParameter(pos++, parentId);
/*     */     }
/*     */     else {
/*     */       
/* 540 */       for (int i = 0; i < this.exportedProperties.length; i++) {
/* 541 */         Object embVal = this.exportedProperties[i].getValue(parentId);
/* 542 */         q.setParameter(pos++, embVal);
/*     */       } 
/*     */     } 
/* 545 */     return pos;
/*     */   }
/*     */ 
/*     */   
/*     */   private String deriveWhereParentIdSql(boolean inClause) {
/* 550 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 552 */     if (inClause) {
/* 553 */       sb.append("(");
/*     */     }
/* 555 */     for (int i = 0; i < this.exportedProperties.length; i++) {
/* 556 */       String fkColumn = this.exportedProperties[i].getForeignDbColumn();
/* 557 */       if (i > 0) {
/* 558 */         String s = inClause ? "," : " and ";
/* 559 */         sb.append(s);
/*     */       } 
/* 561 */       sb.append(fkColumn);
/* 562 */       if (!inClause) {
/* 563 */         sb.append("=? ");
/*     */       }
/*     */     } 
/* 566 */     if (inClause) {
/* 567 */       sb.append(")");
/*     */     }
/* 569 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPredicates(SpiQuery<?> query, Object parentBean) {
/* 574 */     if (this.manyToMany)
/*     */     {
/*     */ 
/*     */       
/* 578 */       query.setIncludeTableJoin(this.inverseJoin);
/*     */     }
/*     */     
/* 581 */     if (this.embeddedExportedProperties) {
/*     */       
/* 583 */       BeanProperty[] uids = this.descriptor.propertiesId();
/* 584 */       parentBean = uids[0].getValue(parentBean);
/*     */     } 
/*     */     
/* 587 */     for (int i = 0; i < this.exportedProperties.length; i++) {
/* 588 */       Object val = this.exportedProperties[i].getValue(parentBean);
/* 589 */       String fkColumn = this.exportedProperties[i].getForeignDbColumn();
/* 590 */       if (!this.manyToMany) {
/* 591 */         fkColumn = this.targetDescriptor.getBaseTableAlias() + "." + fkColumn;
/*     */       } else {
/*     */         
/* 594 */         fkColumn = "int_." + fkColumn;
/*     */       } 
/* 596 */       query.where().eq(fkColumn, val);
/*     */     } 
/*     */     
/* 599 */     if (this.extraWhere != null) {
/*     */       
/* 601 */       String ta = this.targetDescriptor.getBaseTableAlias();
/* 602 */       String where = StringHelper.replaceString(this.extraWhere, "${ta}", ta);
/* 603 */       query.where().raw(where);
/*     */     } 
/*     */     
/* 606 */     if (this.fetchOrderBy != null) {
/* 607 */       query.order(this.fetchOrderBy);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ExportedProperty[] createExported() {
/* 616 */     BeanProperty[] uids = this.descriptor.propertiesId();
/*     */     
/* 618 */     ArrayList<ExportedProperty> list = new ArrayList<ExportedProperty>();
/*     */     
/* 620 */     if (uids.length == 1 && uids[0].isEmbedded()) {
/*     */       
/* 622 */       BeanPropertyAssocOne<?> one = (BeanPropertyAssocOne)uids[0];
/* 623 */       BeanDescriptor<?> targetDesc = one.getTargetDescriptor();
/* 624 */       BeanProperty[] emIds = targetDesc.propertiesBaseScalar();
/*     */       try {
/* 626 */         for (int i = 0; i < emIds.length; i++) {
/* 627 */           ExportedProperty expProp = findMatch(true, emIds[i]);
/* 628 */           list.add(expProp);
/*     */         } 
/* 630 */       } catch (PersistenceException e) {
/*     */         
/* 632 */         e.printStackTrace();
/*     */       } 
/*     */     } else {
/*     */       
/* 636 */       for (int i = 0; i < uids.length; i++) {
/* 637 */         ExportedProperty expProp = findMatch(false, uids[i]);
/* 638 */         list.add(expProp);
/*     */       } 
/*     */     } 
/*     */     
/* 642 */     return list.<ExportedProperty>toArray(new ExportedProperty[list.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ExportedProperty findMatch(boolean embedded, BeanProperty prop) {
/*     */     TableJoinColumn[] columns;
/* 650 */     String searchTable, matchColumn = prop.getDbColumn();
/*     */ 
/*     */ 
/*     */     
/* 654 */     if (this.manyToMany) {
/*     */       
/* 656 */       columns = this.intersectionJoin.columns();
/* 657 */       searchTable = this.intersectionJoin.getTable();
/*     */     } else {
/*     */       
/* 660 */       columns = this.tableJoin.columns();
/* 661 */       searchTable = this.tableJoin.getTable();
/*     */     } 
/* 663 */     for (int i = 0; i < columns.length; i++) {
/* 664 */       String matchTo = columns[i].getLocalDbColumn();
/*     */       
/* 666 */       if (matchColumn.equalsIgnoreCase(matchTo)) {
/* 667 */         String foreignCol = columns[i].getForeignDbColumn();
/* 668 */         return new ExportedProperty(embedded, foreignCol, prop);
/*     */       } 
/*     */     } 
/*     */     
/* 672 */     String msg = "Error with the Join on [" + getFullBeanName() + "]. Could not find the matching foreign key for [" + matchColumn + "] in table[" + searchTable + "]?" + " Perhaps using a @JoinColumn with the name/referencedColumnName attributes swapped?";
/*     */ 
/*     */     
/* 675 */     throw new PersistenceException(msg);
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
/*     */   private BeanPropertyAssocOne<?> initChildMasterProperty() {
/* 687 */     if (this.unidirectional) {
/* 688 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 692 */     Class<?> beanType = this.descriptor.getBeanType();
/* 693 */     BeanDescriptor<?> targetDesc = getTargetDescriptor();
/*     */     
/* 695 */     BeanPropertyAssocOne[] arrayOfBeanPropertyAssocOne = (BeanPropertyAssocOne[])targetDesc.propertiesOne();
/* 696 */     for (int i = 0; i < arrayOfBeanPropertyAssocOne.length; i++) {
/* 697 */       BeanPropertyAssocOne<?> prop = arrayOfBeanPropertyAssocOne[i];
/* 698 */       if (this.mappedBy != null) {
/*     */         
/* 700 */         if (this.mappedBy.equalsIgnoreCase(prop.getName())) {
/* 701 */           return prop;
/*     */         
/*     */         }
/*     */       }
/* 705 */       else if (prop.getTargetType().equals(beanType)) {
/*     */         
/* 707 */         return prop;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 712 */     String msg = "Can not find Master [" + beanType + "] in Child[" + targetDesc + "]";
/* 713 */     throw new RuntimeException(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BeanProperty initMapKeyProperty() {
/* 723 */     BeanDescriptor<?> targetDesc = getTargetDescriptor();
/*     */     
/* 725 */     Iterator<BeanProperty> it = targetDesc.propertiesAll();
/* 726 */     while (it.hasNext()) {
/* 727 */       BeanProperty prop = it.next();
/* 728 */       if (this.mapKey.equalsIgnoreCase(prop.getName())) {
/* 729 */         return prop;
/*     */       }
/*     */     } 
/*     */     
/* 733 */     String from = this.descriptor.getFullName();
/* 734 */     String to = targetDesc.getFullName();
/* 735 */     String msg = from + ": Could not find mapKey property [" + this.mapKey + "] on [" + to + "]";
/* 736 */     throw new PersistenceException(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public IntersectionRow buildManyDeleteChildren(Object parentBean, ArrayList<Object> excludeDetailIds) {
/* 741 */     IntersectionRow row = new IntersectionRow(this.tableJoin.getTable());
/* 742 */     if (excludeDetailIds != null && !excludeDetailIds.isEmpty()) {
/* 743 */       row.setExcludeIds(excludeDetailIds, getTargetDescriptor());
/*     */     }
/* 745 */     buildExport(row, parentBean);
/* 746 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntersectionRow buildManyToManyDeleteChildren(Object parentBean) {
/* 751 */     IntersectionRow row = new IntersectionRow(this.intersectionJoin.getTable());
/* 752 */     buildExport(row, parentBean);
/* 753 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntersectionRow buildManyToManyMapBean(Object parent, Object other) {
/* 758 */     IntersectionRow row = new IntersectionRow(this.intersectionJoin.getTable());
/*     */     
/* 760 */     buildExport(row, parent);
/* 761 */     buildImport(row, other);
/* 762 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildExport(IntersectionRow row, Object parentBean) {
/* 767 */     if (this.embeddedExportedProperties) {
/* 768 */       BeanProperty[] uids = this.descriptor.propertiesId();
/* 769 */       parentBean = uids[0].getValue(parentBean);
/*     */     } 
/* 771 */     for (int i = 0; i < this.exportedProperties.length; i++) {
/* 772 */       Object val = this.exportedProperties[i].getValue(parentBean);
/* 773 */       String fkColumn = this.exportedProperties[i].getForeignDbColumn();
/*     */       
/* 775 */       row.put(fkColumn, val);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildImport(IntersectionRow row, Object otherBean) {
/* 785 */     this.importedId.buildImport(row, otherBean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasImportedId(Object otherBean) {
/* 793 */     return (null != this.targetDescriptor.getId(otherBean));
/*     */   }
/*     */ 
/*     */   
/*     */   public void jsonWrite(WriteJsonContext ctx, Object bean) {
/* 798 */     Boolean include = ctx.includeMany(this.name);
/* 799 */     if (Boolean.FALSE.equals(include)) {
/*     */       return;
/*     */     }
/*     */     
/* 803 */     Object value = getValueIntercept(bean);
/* 804 */     if (value != null) {
/* 805 */       ctx.pushParentBeanMany(bean);
/* 806 */       this.help.jsonWrite(ctx, this.name, value, (include != null));
/* 807 */       ctx.popParentBeanMany();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void jsonRead(ReadJsonContext ctx, Object bean) {
/* 813 */     if (!ctx.readArrayBegin()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 818 */     Object collection = this.help.createEmpty(false);
/* 819 */     BeanCollectionAdd add = getBeanCollectionAdd(collection, (String)null);
/*     */     do {
/* 821 */       ReadJsonContext.ReadBeanState detailBeanState = this.targetDescriptor.jsonRead(ctx, this.name);
/* 822 */       if (detailBeanState == null) {
/*     */         break;
/*     */       }
/*     */       
/* 826 */       Object detailBean = detailBeanState.getBean();
/* 827 */       add.addBean(detailBean);
/*     */       
/* 829 */       if (bean != null && this.childMasterProperty != null) {
/*     */         
/* 831 */         this.childMasterProperty.setValue(detailBean, bean);
/* 832 */         detailBeanState.setLoaded(this.childMasterProperty.getName());
/*     */       } 
/*     */       
/* 835 */       detailBeanState.setLoadedState();
/*     */     }
/* 837 */     while (ctx.readArrayNext());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 842 */     setValue(bean, collection);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanPropertyAssocMany.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */