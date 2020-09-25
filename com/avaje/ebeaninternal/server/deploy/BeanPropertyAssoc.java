/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebeaninternal.server.core.InternString;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.ImportedIdEmbedded;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.ImportedIdMultiple;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.ImportedIdSimple;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssoc;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import java.util.ArrayList;
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
/*     */ public abstract class BeanPropertyAssoc<T>
/*     */   extends BeanProperty
/*     */ {
/*  44 */   private static final Logger logger = Logger.getLogger(BeanPropertyAssoc.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   BeanDescriptor<T> targetDescriptor;
/*     */ 
/*     */ 
/*     */   
/*     */   IdBinder targetIdBinder;
/*     */ 
/*     */ 
/*     */   
/*     */   InheritInfo targetInheritInfo;
/*     */ 
/*     */ 
/*     */   
/*     */   String targetIdProperty;
/*     */ 
/*     */ 
/*     */   
/*     */   final BeanCascadeInfo cascadeInfo;
/*     */ 
/*     */ 
/*     */   
/*     */   final TableJoin tableJoin;
/*     */ 
/*     */ 
/*     */   
/*     */   final Class<T> targetType;
/*     */ 
/*     */   
/*     */   final BeanTable beanTable;
/*     */ 
/*     */   
/*     */   final String mappedBy;
/*     */ 
/*     */   
/*     */   final boolean isOuterJoin;
/*     */ 
/*     */   
/*     */   String extraWhere;
/*     */ 
/*     */   
/*     */   boolean saveRecurseSkippable;
/*     */ 
/*     */   
/*     */   boolean deleteRecurseSkippable;
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyAssoc(BeanDescriptorMap owner, BeanDescriptor<?> descriptor, DeployBeanPropertyAssoc<T> deploy) {
/*  95 */     super(owner, descriptor, (DeployBeanProperty)deploy);
/*  96 */     this.extraWhere = InternString.intern(deploy.getExtraWhere());
/*  97 */     this.isOuterJoin = deploy.isOuterJoin();
/*  98 */     this.beanTable = deploy.getBeanTable();
/*  99 */     this.mappedBy = InternString.intern(deploy.getMappedBy());
/*     */     
/* 101 */     this.tableJoin = new TableJoin(deploy.getTableJoin(), null);
/*     */     
/* 103 */     this.targetType = deploy.getTargetType();
/* 104 */     this.cascadeInfo = deploy.getCascadeInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialise() {
/* 114 */     if (!this.isTransient) {
/* 115 */       this.targetDescriptor = this.descriptor.getBeanDescriptor(this.targetType);
/* 116 */       this.targetIdBinder = this.targetDescriptor.getIdBinder();
/* 117 */       this.targetInheritInfo = this.targetDescriptor.getInheritInfo();
/*     */       
/* 119 */       this.saveRecurseSkippable = this.targetDescriptor.isSaveRecurseSkippable();
/* 120 */       this.deleteRecurseSkippable = this.targetDescriptor.isDeleteRecurseSkippable();
/*     */       
/* 122 */       this.cascadeValidate = this.cascadeInfo.isValidate();
/*     */       
/* 124 */       if (!this.targetIdBinder.isComplexId()) {
/* 125 */         this.targetIdProperty = this.targetIdBinder.getIdProperty();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ElPropertyValue createElPropertyValue(String propName, String remainder, ElPropertyChainBuilder chain, boolean propertyDeploy) {
/* 136 */     BeanDescriptor<?> embDesc = getTargetDescriptor();
/*     */     
/* 138 */     if (chain == null) {
/* 139 */       chain = new ElPropertyChainBuilder(isEmbedded(), propName);
/*     */     }
/* 141 */     chain.add(this);
/* 142 */     if (containsMany()) {
/* 143 */       chain.setContainsMany(true);
/*     */     }
/* 145 */     return embDesc.buildElGetValue(remainder, chain, propertyDeploy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addJoin(boolean forceOuterJoin, String prefix, DbSqlContext ctx) {
/* 152 */     return this.tableJoin.addJoin(forceOuterJoin, prefix, ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addJoin(boolean forceOuterJoin, String a1, String a2, DbSqlContext ctx) {
/* 159 */     return this.tableJoin.addJoin(forceOuterJoin, a1, a2, ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInnerJoin(String a1, String a2, DbSqlContext ctx) {
/* 166 */     this.tableJoin.addInnerJoin(a1, a2, ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isScalar() {
/* 173 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMappedBy() {
/* 181 */     return this.mappedBy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTargetIdProperty() {
/* 191 */     return this.targetIdProperty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanDescriptor<T> getTargetDescriptor() {
/* 198 */     return this.targetDescriptor;
/*     */   }
/*     */   
/*     */   public boolean isSaveRecurseSkippable(Object bean) {
/* 202 */     if (!this.saveRecurseSkippable)
/*     */     {
/*     */       
/* 205 */       return false;
/*     */     }
/* 207 */     if (bean instanceof EntityBean) {
/* 208 */       return !((EntityBean)bean)._ebean_getIntercept().isNewOrDirty();
/*     */     }
/*     */     
/* 211 */     return false;
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
/*     */   public boolean isSaveRecurseSkippable() {
/* 225 */     return this.saveRecurseSkippable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDeleteRecurseSkippable() {
/* 232 */     return this.deleteRecurseSkippable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasId(Object bean) {
/* 240 */     BeanDescriptor<?> targetDesc = getTargetDescriptor();
/*     */     
/* 242 */     BeanProperty[] uids = targetDesc.propertiesId();
/* 243 */     for (int i = 0; i < uids.length; i++) {
/*     */       
/* 245 */       Object value = uids[i].getValue(bean);
/* 246 */       if (value == null) {
/* 247 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 251 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getTargetType() {
/* 262 */     return this.targetType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExtraWhere() {
/* 270 */     return this.extraWhere;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOuterJoin() {
/* 277 */     return this.isOuterJoin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUpdateable() {
/* 284 */     if ((this.tableJoin.columns()).length > 0) {
/* 285 */       return this.tableJoin.columns()[0].isUpdateable();
/*     */     }
/*     */     
/* 288 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInsertable() {
/* 295 */     if ((this.tableJoin.columns()).length > 0) {
/* 296 */       return this.tableJoin.columns()[0].isInsertable();
/*     */     }
/*     */     
/* 299 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableJoin getTableJoin() {
/* 306 */     return this.tableJoin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanTable getBeanTable() {
/* 317 */     return this.beanTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCascadeInfo getCascadeInfo() {
/* 324 */     return this.cascadeInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ImportedId createImportedId(BeanPropertyAssoc<?> owner, BeanDescriptor<?> target, TableJoin join) {
/* 333 */     BeanProperty[] props = target.propertiesId();
/* 334 */     BeanProperty[] others = target.propertiesBaseScalar();
/*     */     
/* 336 */     if (this.descriptor.isSqlSelectBased()) {
/* 337 */       String dbColumn = owner.getDbColumn();
/* 338 */       return (ImportedId)new ImportedIdSimple(owner, dbColumn, props[0], 0);
/*     */     } 
/*     */     
/* 341 */     TableJoinColumn[] cols = join.columns();
/*     */     
/* 343 */     if (props.length == 1) {
/* 344 */       if (!props[0].isEmbedded()) {
/*     */         
/* 346 */         if (cols.length != 1) {
/* 347 */           String msg = "No Imported Id column for [" + props[0] + "] in table [" + join.getTable() + "]";
/* 348 */           logger.log(Level.SEVERE, msg);
/* 349 */           return null;
/*     */         } 
/* 351 */         return (ImportedId)createImportedScalar(owner, cols[0], props, others);
/*     */       } 
/*     */ 
/*     */       
/* 355 */       BeanPropertyAssocOne<?> embProp = (BeanPropertyAssocOne)props[0];
/* 356 */       BeanProperty[] embBaseProps = embProp.getTargetDescriptor().propertiesBaseScalar();
/* 357 */       ImportedIdSimple[] arrayOfImportedIdSimple = createImportedList(owner, cols, embBaseProps, others);
/*     */       
/* 359 */       return (ImportedId)new ImportedIdEmbedded(owner, embProp, arrayOfImportedIdSimple);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 364 */     ImportedIdSimple[] scalars = createImportedList(owner, cols, props, others);
/* 365 */     return (ImportedId)new ImportedIdMultiple(owner, scalars);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ImportedIdSimple[] createImportedList(BeanPropertyAssoc<?> owner, TableJoinColumn[] cols, BeanProperty[] props, BeanProperty[] others) {
/* 371 */     ArrayList<ImportedIdSimple> list = new ArrayList<ImportedIdSimple>();
/*     */     
/* 373 */     for (int i = 0; i < cols.length; i++) {
/* 374 */       list.add(createImportedScalar(owner, cols[i], props, others));
/*     */     }
/*     */     
/* 377 */     return ImportedIdSimple.sort(list);
/*     */   }
/*     */ 
/*     */   
/*     */   private ImportedIdSimple createImportedScalar(BeanPropertyAssoc<?> owner, TableJoinColumn col, BeanProperty[] props, BeanProperty[] others) {
/* 382 */     String matchColumn = col.getForeignDbColumn();
/* 383 */     String localColumn = col.getLocalDbColumn();
/*     */     int j;
/* 385 */     for (j = 0; j < props.length; j++) {
/* 386 */       if (props[j].getDbColumn().equalsIgnoreCase(matchColumn)) {
/* 387 */         return new ImportedIdSimple(owner, localColumn, props[j], j);
/*     */       }
/*     */     } 
/*     */     
/* 391 */     for (j = 0; j < others.length; j++) {
/* 392 */       if (others[j].getDbColumn().equalsIgnoreCase(matchColumn)) {
/* 393 */         return new ImportedIdSimple(owner, localColumn, others[j], j + props.length);
/*     */       }
/*     */     } 
/*     */     
/* 397 */     String msg = "Error with the Join on [" + getFullBeanName() + "]. Could not find the local match for [" + matchColumn + "] " + " Perhaps an error in a @JoinColumn";
/*     */ 
/*     */     
/* 400 */     throw new PersistenceException(msg);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanPropertyAssoc.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */