/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.InvalidValue;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.ValidationException;
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.event.BeanPersistController;
/*     */ import com.avaje.ebean.event.BeanPersistListener;
/*     */ import com.avaje.ebean.event.BeanPersistRequest;
/*     */ import com.avaje.ebeaninternal.api.DerivedRelationshipData;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.api.TransactionEvent;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanManager;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.persist.BatchControl;
/*     */ import com.avaje.ebeaninternal.server.persist.PersistExecute;
/*     */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*     */ import com.avaje.ebeaninternal.server.transaction.BeanDelta;
/*     */ import com.avaje.ebeaninternal.server.transaction.BeanPersistIdMap;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.persistence.OptimisticLockException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PersistRequestBean<T>
/*     */   extends PersistRequest
/*     */   implements BeanPersistRequest<T>
/*     */ {
/*     */   protected final BeanManager<T> beanManager;
/*     */   protected final BeanDescriptor<T> beanDescriptor;
/*     */   protected final BeanPersistListener<T> beanPersistListener;
/*     */   protected final BeanPersistController controller;
/*     */   protected final EntityBeanIntercept intercept;
/*     */   protected final Object parentBean;
/*     */   protected final boolean isDirty;
/*     */   protected final boolean vanilla;
/*     */   protected final T bean;
/*     */   protected T oldValues;
/*     */   protected ConcurrencyMode concurrencyMode;
/*     */   protected final Set<String> loadedProps;
/*     */   protected Object idValue;
/*     */   protected Integer beanHash;
/*     */   protected Integer beanIdentityHash;
/*     */   protected final Set<String> changedProps;
/*     */   protected boolean notifyCache;
/*     */   private boolean statelessUpdate;
/*     */   private boolean deleteMissingChildren;
/*     */   private boolean updateNullProperties;
/*     */   
/*     */   public PersistRequestBean(SpiEbeanServer server, T bean, Object parentBean, BeanManager<T> mgr, SpiTransaction t, PersistExecute persistExecute, Set<String> updateProps, ConcurrencyMode concurrencyMode) {
/* 123 */     super(server, t, persistExecute);
/* 124 */     this.beanManager = mgr;
/* 125 */     this.beanDescriptor = mgr.getBeanDescriptor();
/* 126 */     this.beanPersistListener = this.beanDescriptor.getPersistListener();
/* 127 */     this.bean = bean;
/* 128 */     this.parentBean = parentBean;
/*     */     
/* 130 */     this.controller = this.beanDescriptor.getPersistController();
/* 131 */     this.concurrencyMode = this.beanDescriptor.getConcurrencyMode();
/*     */     
/* 133 */     this.concurrencyMode = concurrencyMode;
/* 134 */     this.loadedProps = updateProps;
/* 135 */     this.changedProps = updateProps;
/*     */     
/* 137 */     this.vanilla = true;
/* 138 */     this.isDirty = true;
/* 139 */     this.oldValues = bean;
/* 140 */     if (bean instanceof EntityBean) {
/* 141 */       this.intercept = ((EntityBean)bean)._ebean_getIntercept();
/*     */     } else {
/* 143 */       this.intercept = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistRequestBean(SpiEbeanServer server, T bean, Object parentBean, BeanManager<T> mgr, SpiTransaction t, PersistExecute persistExecute) {
/* 151 */     super(server, t, persistExecute);
/* 152 */     this.beanManager = mgr;
/* 153 */     this.beanDescriptor = mgr.getBeanDescriptor();
/* 154 */     this.beanPersistListener = this.beanDescriptor.getPersistListener();
/* 155 */     this.bean = bean;
/* 156 */     this.parentBean = parentBean;
/*     */     
/* 158 */     this.controller = this.beanDescriptor.getPersistController();
/* 159 */     this.concurrencyMode = this.beanDescriptor.getConcurrencyMode();
/*     */     
/* 161 */     if (bean instanceof EntityBean) {
/* 162 */       this.intercept = ((EntityBean)bean)._ebean_getIntercept();
/* 163 */       if (this.intercept.isReference())
/*     */       {
/*     */         
/* 166 */         this.concurrencyMode = ConcurrencyMode.NONE;
/*     */       }
/*     */       
/* 169 */       this.isDirty = this.intercept.isDirty();
/* 170 */       if (!this.isDirty) {
/* 171 */         this.changedProps = this.intercept.getChangedProps();
/*     */       } else {
/*     */         
/* 174 */         Set<String> beanChangedProps = this.intercept.getChangedProps();
/* 175 */         Set<String> dirtyEmbedded = this.beanDescriptor.getDirtyEmbeddedProperties(bean);
/* 176 */         this.changedProps = mergeChangedProperties(beanChangedProps, dirtyEmbedded);
/*     */       } 
/* 178 */       this.loadedProps = this.intercept.getLoadedProps();
/* 179 */       this.oldValues = (T)this.intercept.getOldValues();
/* 180 */       this.vanilla = false;
/*     */     }
/*     */     else {
/*     */       
/* 184 */       this.vanilla = true;
/* 185 */       this.isDirty = true;
/* 186 */       this.loadedProps = null;
/* 187 */       this.changedProps = null;
/* 188 */       this.intercept = null;
/*     */ 
/*     */       
/* 191 */       if (this.concurrencyMode.equals(ConcurrencyMode.ALL)) {
/* 192 */         this.concurrencyMode = ConcurrencyMode.NONE;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<String> mergeChangedProperties(Set<String> beanChangedProps, Set<String> embChanged) {
/* 201 */     if (embChanged == null)
/* 202 */       return beanChangedProps; 
/* 203 */     if (beanChangedProps == null) {
/* 204 */       return embChanged;
/*     */     }
/* 206 */     beanChangedProps.addAll(embChanged);
/* 207 */     return beanChangedProps;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNotify(TransactionEvent txnEvent) {
/* 212 */     return (this.notifyCache || isNotifyPersistListener());
/*     */   }
/*     */   
/*     */   public boolean isNotifyCache() {
/* 216 */     return this.notifyCache;
/*     */   }
/*     */   
/*     */   public boolean isNotifyPersistListener() {
/* 220 */     return (this.beanPersistListener != null);
/*     */   }
/*     */   
/*     */   public void notifyCache() {
/* 224 */     if (this.notifyCache) {
/* 225 */       switch (this.type) {
/*     */         case INSERT:
/* 227 */           this.beanDescriptor.cacheInsert(this.idValue, this);
/*     */           return;
/*     */         case UPDATE:
/* 230 */           this.beanDescriptor.cacheUpdate(this.idValue, this);
/*     */           return;
/*     */         case DELETE:
/* 233 */           this.beanDescriptor.cacheDelete(this.idValue, this);
/*     */           return;
/*     */       } 
/* 236 */       throw new IllegalStateException("Invalid type " + this.type);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToPersistMap(BeanPersistIdMap beanPersistMap) {
/* 243 */     beanPersistMap.add(this.beanDescriptor, this.type, this.idValue);
/*     */   }
/*     */   
/*     */   public boolean notifyLocalPersistListener() {
/* 247 */     if (this.beanPersistListener == null) {
/* 248 */       return false;
/*     */     }
/*     */     
/* 251 */     switch (this.type) {
/*     */       case INSERT:
/* 253 */         return this.beanPersistListener.inserted(this.bean);
/*     */       
/*     */       case UPDATE:
/* 256 */         return this.beanPersistListener.updated(this.bean, getUpdatedProperties());
/*     */       
/*     */       case DELETE:
/* 259 */         return this.beanPersistListener.deleted(this.bean);
/*     */     } 
/*     */     
/* 262 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isParent(Object o) {
/* 268 */     return (o == this.parentBean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRegisteredBean() {
/* 276 */     return this.transaction.isRegisteredBean(this.bean);
/*     */   }
/*     */   
/*     */   public void unRegisterBean() {
/* 280 */     this.transaction.unregisterBean(this.bean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Integer getBeanHash() {
/* 290 */     if (this.beanHash == null) {
/* 291 */       Object id = this.beanDescriptor.getId(this.bean);
/* 292 */       int hc = 31 * this.bean.getClass().getName().hashCode();
/* 293 */       if (id != null) {
/* 294 */         hc += id.hashCode();
/*     */       }
/* 296 */       this.beanHash = Integer.valueOf(hc);
/*     */     } 
/* 298 */     return this.beanHash;
/*     */   }
/*     */   
/*     */   public void registerDeleteBean() {
/* 302 */     Integer hash = getBeanHash();
/* 303 */     this.transaction.registerDeleteBean(hash);
/*     */   }
/*     */   
/*     */   public void unregisterDeleteBean() {
/* 307 */     Integer hash = getBeanHash();
/* 308 */     this.transaction.unregisterDeleteBean(hash);
/*     */   }
/*     */   
/*     */   public boolean isRegisteredForDeleteBean() {
/* 312 */     if (this.transaction == null) {
/* 313 */       return false;
/*     */     }
/* 315 */     Integer hash = getBeanHash();
/* 316 */     return this.transaction.isRegisteredDeleteBean(hash);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(PersistRequest.Type type) {
/* 326 */     this.type = type;
/* 327 */     this.notifyCache = this.beanDescriptor.isCacheNotify();
/* 328 */     if ((type == PersistRequest.Type.DELETE || type == PersistRequest.Type.UPDATE) && 
/* 329 */       this.oldValues == null) {
/* 330 */       this.oldValues = this.bean;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanManager<T> getBeanManager() {
/* 336 */     return this.beanManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanDescriptor<T> getBeanDescriptor() {
/* 343 */     return this.beanDescriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStatelessUpdate() {
/* 350 */     return this.statelessUpdate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDeleteMissingChildren() {
/* 358 */     return this.deleteMissingChildren;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUpdateNullProperties() {
/* 366 */     return this.updateNullProperties;
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
/*     */   public void setStatelessUpdate(boolean statelessUpdate, boolean deleteMissingChildren, boolean updateNullProperties) {
/* 378 */     this.statelessUpdate = statelessUpdate;
/* 379 */     this.deleteMissingChildren = deleteMissingChildren;
/* 380 */     this.updateNullProperties = updateNullProperties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDirty() {
/* 388 */     return this.isDirty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConcurrencyMode getConcurrencyMode() {
/* 395 */     return this.concurrencyMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoadedProps(Set<String> additionalProps) {
/* 403 */     if (this.intercept != null) {
/* 404 */       this.intercept.setLoadedProps(additionalProps);
/*     */     }
/*     */   }
/*     */   
/*     */   public Set<String> getLoadedProperties() {
/* 409 */     return this.loadedProps;
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
/*     */   public String getFullName() {
/* 421 */     return this.beanDescriptor.getFullName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getBean() {
/* 428 */     return this.bean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getBeanId() {
/* 435 */     return this.beanDescriptor.getId(this.bean);
/*     */   }
/*     */   
/*     */   public BeanDelta createDeltaBean() {
/* 439 */     return new BeanDelta(this.beanDescriptor, getBeanId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getOldValues() {
/* 447 */     return this.oldValues;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getParentBean() {
/* 455 */     return this.parentBean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPersistController getBeanController() {
/* 463 */     return this.controller;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityBeanIntercept getEntityBeanIntercept() {
/* 470 */     return this.intercept;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {
/* 478 */     InvalidValue errs = this.beanDescriptor.validate(false, this.bean);
/* 479 */     if (errs != null) {
/* 480 */       throw new ValidationException(errs);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLoadedProperty(BeanProperty prop) {
/* 489 */     if (this.loadedProps == null) {
/* 490 */       return true;
/*     */     }
/* 492 */     return this.loadedProps.contains(prop.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeNow() {
/* 498 */     switch (this.type) {
/*     */       case INSERT:
/* 500 */         this.persistExecute.executeInsertBean(this);
/* 501 */         return -1;
/*     */       
/*     */       case UPDATE:
/* 504 */         this.persistExecute.executeUpdateBean(this);
/* 505 */         return -1;
/*     */       
/*     */       case DELETE:
/* 508 */         this.persistExecute.executeDeleteBean(this);
/* 509 */         return -1;
/*     */     } 
/*     */     
/* 512 */     throw new RuntimeException("Invalid type " + this.type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeOrQueue() {
/* 519 */     boolean batch = this.transaction.isBatchThisRequest();
/*     */     
/* 521 */     BatchControl control = this.transaction.getBatchControl();
/* 522 */     if (control != null) {
/* 523 */       return control.executeOrQueue(this, batch);
/*     */     }
/* 525 */     if (batch) {
/* 526 */       control = this.persistExecute.createBatchControl(this.transaction);
/* 527 */       return control.executeOrQueue(this, batch);
/*     */     } 
/*     */     
/* 530 */     return executeNow();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGeneratedKey(Object idValue) {
/* 539 */     if (idValue != null) {
/*     */ 
/*     */ 
/*     */       
/* 543 */       idValue = this.beanDescriptor.convertSetId(idValue, this.bean);
/*     */ 
/*     */       
/* 546 */       this.idValue = idValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBoundId(Object idValue) {
/* 555 */     this.idValue = idValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void checkRowCount(int rowCount) throws SQLException {
/* 562 */     if (rowCount != 1) {
/* 563 */       String m = Message.msg("persist.conc2", "" + rowCount);
/* 564 */       throw new OptimisticLockException(m, null, this.bean);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postExecute() throws SQLException {
/* 573 */     if (this.controller != null) {
/* 574 */       controllerPost();
/*     */     }
/*     */     
/* 577 */     if (this.intercept != null)
/*     */     {
/* 579 */       this.intercept.setLoaded();
/*     */     }
/*     */     
/* 582 */     addEvent();
/*     */     
/* 584 */     if (isLogSummary()) {
/* 585 */       logSummary();
/*     */     }
/*     */   }
/*     */   
/*     */   private void controllerPost() {
/* 590 */     switch (this.type) {
/*     */       case INSERT:
/* 592 */         this.controller.postInsert(this);
/*     */         break;
/*     */       case UPDATE:
/* 595 */         this.controller.postUpdate(this);
/*     */         break;
/*     */       case DELETE:
/* 598 */         this.controller.postDelete(this);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logSummary() {
/* 607 */     String name = this.beanDescriptor.getName();
/* 608 */     switch (this.type) {
/*     */       case INSERT:
/* 610 */         this.transaction.logInternal("Inserted [" + name + "] [" + this.idValue + "]");
/*     */         break;
/*     */       case UPDATE:
/* 613 */         this.transaction.logInternal("Updated [" + name + "] [" + this.idValue + "]");
/*     */         break;
/*     */       case DELETE:
/* 616 */         this.transaction.logInternal("Deleted [" + name + "] [" + this.idValue + "]");
/*     */         break;
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
/*     */   private void addEvent() {
/* 629 */     TransactionEvent event = this.transaction.getEvent();
/* 630 */     if (event != null) {
/* 631 */       event.add(this);
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
/*     */   public ConcurrencyMode determineConcurrencyMode() {
/* 644 */     if (this.loadedProps != null)
/*     */     {
/* 646 */       if (this.concurrencyMode.equals(ConcurrencyMode.VERSION)) {
/*     */         
/* 648 */         BeanProperty prop = this.beanDescriptor.firstVersionProperty();
/* 649 */         if (prop == null || !this.loadedProps.contains(prop.getName()))
/*     */         {
/*     */           
/* 652 */           this.concurrencyMode = ConcurrencyMode.ALL;
/*     */         }
/*     */       } 
/*     */     }
/* 656 */     return this.concurrencyMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDynamicUpdateSql() {
/* 666 */     return ((!this.vanilla && this.beanDescriptor.isUpdateChangesOnly()) || this.loadedProps != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GenerateDmlRequest createGenerateDmlRequest(boolean emptyStringAsNull) {
/* 677 */     if (this.beanDescriptor.isUpdateChangesOnly()) {
/* 678 */       return new GenerateDmlRequest(emptyStringAsNull, this.changedProps, this.loadedProps, this.oldValues);
/*     */     }
/* 680 */     return new GenerateDmlRequest(emptyStringAsNull, this.loadedProps, this.loadedProps, this.oldValues);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getUpdatedProperties() {
/* 689 */     if (this.changedProps != null) {
/* 690 */       return this.changedProps;
/*     */     }
/* 692 */     return this.loadedProps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasChanged(BeanProperty prop) {
/* 701 */     return this.changedProps.contains(prop.getName());
/*     */   }
/*     */   
/*     */   public List<DerivedRelationshipData> getDerivedRelationships() {
/* 705 */     return this.transaction.getDerivedRelationship(this.bean);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\PersistRequestBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */