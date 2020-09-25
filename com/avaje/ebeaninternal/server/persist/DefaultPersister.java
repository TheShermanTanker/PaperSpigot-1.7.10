/*      */ package com.avaje.ebeaninternal.server.persist;
/*      */ 
/*      */ import com.avaje.ebean.CallableSql;
/*      */ import com.avaje.ebean.EbeanServer;
/*      */ import com.avaje.ebean.Query;
/*      */ import com.avaje.ebean.SqlUpdate;
/*      */ import com.avaje.ebean.Transaction;
/*      */ import com.avaje.ebean.Update;
/*      */ import com.avaje.ebean.bean.BeanCollection;
/*      */ import com.avaje.ebean.bean.EntityBean;
/*      */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*      */ import com.avaje.ebean.config.ldap.LdapContextFactory;
/*      */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*      */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*      */ import com.avaje.ebeaninternal.api.SpiUpdate;
/*      */ import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
/*      */ import com.avaje.ebeaninternal.server.core.Message;
/*      */ import com.avaje.ebeaninternal.server.core.PersistRequest;
/*      */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*      */ import com.avaje.ebeaninternal.server.core.PersistRequestCallableSql;
/*      */ import com.avaje.ebeaninternal.server.core.PersistRequestOrmUpdate;
/*      */ import com.avaje.ebeaninternal.server.core.PersistRequestUpdateSql;
/*      */ import com.avaje.ebeaninternal.server.core.Persister;
/*      */ import com.avaje.ebeaninternal.server.core.PstmtBatch;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanManager;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*      */ import com.avaje.ebeaninternal.server.deploy.IntersectionRow;
/*      */ import com.avaje.ebeaninternal.server.deploy.ManyType;
/*      */ import com.avaje.ebeaninternal.server.ldap.DefaultLdapPersister;
/*      */ import com.avaje.ebeaninternal.server.ldap.LdapPersistBeanRequest;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class DefaultPersister
/*      */   implements Persister
/*      */ {
/*   84 */   private static final Logger logger = Logger.getLogger(DefaultPersister.class.getName());
/*      */ 
/*      */   
/*      */   private final PersistExecute persistExecute;
/*      */ 
/*      */   
/*      */   private final DefaultLdapPersister ldapPersister;
/*      */ 
/*      */   
/*      */   private final SpiEbeanServer server;
/*      */   
/*      */   private final BeanDescriptorManager beanDescriptorManager;
/*      */   
/*      */   private final boolean defaultUpdateNullProperties;
/*      */   
/*      */   private final boolean defaultDeleteMissingChildren;
/*      */ 
/*      */   
/*      */   public DefaultPersister(SpiEbeanServer server, boolean validate, Binder binder, BeanDescriptorManager descMgr, PstmtBatch pstmtBatch, LdapContextFactory contextFactory) {
/*  103 */     this.server = server;
/*  104 */     this.beanDescriptorManager = descMgr;
/*      */     
/*  106 */     this.persistExecute = new DefaultPersistExecute(validate, binder, pstmtBatch);
/*  107 */     this.ldapPersister = new DefaultLdapPersister(contextFactory);
/*      */     
/*  109 */     this.defaultUpdateNullProperties = server.isDefaultUpdateNullProperties();
/*  110 */     this.defaultDeleteMissingChildren = server.isDefaultDeleteMissingChildren();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int executeCallable(CallableSql callSql, Transaction t) {
/*  118 */     PersistRequestCallableSql request = new PersistRequestCallableSql(this.server, callSql, (SpiTransaction)t, this.persistExecute);
/*      */     try {
/*  120 */       request.initTransIfRequired();
/*  121 */       int rc = request.executeOrQueue();
/*  122 */       request.commitTransIfRequired();
/*  123 */       return rc;
/*      */     }
/*  125 */     catch (RuntimeException e) {
/*  126 */       request.rollbackTransIfRequired();
/*  127 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int executeOrmUpdate(Update<?> update, Transaction t) {
/*  136 */     SpiUpdate<?> ormUpdate = (SpiUpdate)update;
/*      */     
/*  138 */     BeanManager<?> mgr = this.beanDescriptorManager.getBeanManager(ormUpdate.getBeanType());
/*      */     
/*  140 */     if (mgr == null) {
/*  141 */       String msg = "No BeanManager found for type [" + ormUpdate.getBeanType() + "]. Is it an entity?";
/*  142 */       throw new PersistenceException(msg);
/*      */     } 
/*      */     
/*  145 */     PersistRequestOrmUpdate request = new PersistRequestOrmUpdate(this.server, mgr, ormUpdate, (SpiTransaction)t, this.persistExecute);
/*      */     try {
/*  147 */       request.initTransIfRequired();
/*  148 */       int rc = request.executeOrQueue();
/*  149 */       request.commitTransIfRequired();
/*  150 */       return rc;
/*      */     }
/*  152 */     catch (RuntimeException e) {
/*  153 */       request.rollbackTransIfRequired();
/*  154 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int executeSqlUpdate(SqlUpdate updSql, Transaction t) {
/*  163 */     PersistRequestUpdateSql request = new PersistRequestUpdateSql(this.server, updSql, (SpiTransaction)t, this.persistExecute);
/*      */     try {
/*  165 */       request.initTransIfRequired();
/*  166 */       int rc = request.executeOrQueue();
/*  167 */       request.commitTransIfRequired();
/*  168 */       return rc;
/*      */     }
/*  170 */     catch (RuntimeException e) {
/*  171 */       request.rollbackTransIfRequired();
/*  172 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deleteRecurse(Object detailBean, Transaction t) {
/*  181 */     this.server.delete(detailBean, t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void forceUpdate(Object bean, Set<String> updateProps, Transaction t, boolean deleteMissingChildren, boolean updateNullProperties) {
/*  189 */     if (bean == null) {
/*  190 */       throw new NullPointerException(Message.msg("bean.isnull"));
/*      */     }
/*      */     
/*  193 */     if (updateProps == null)
/*      */     {
/*  195 */       if (bean instanceof EntityBean) {
/*  196 */         EntityBeanIntercept ebi = ((EntityBean)bean)._ebean_getIntercept();
/*  197 */         if (ebi.isDirty() || ebi.isLoaded()) {
/*      */ 
/*      */           
/*  200 */           PersistRequestBean<?> req = createRequest(bean, t, null);
/*      */           try {
/*  202 */             req.initTransIfRequired();
/*  203 */             update(req);
/*  204 */             req.commitTransIfRequired();
/*      */ 
/*      */             
/*      */             return;
/*  208 */           } catch (RuntimeException ex) {
/*  209 */             req.rollbackTransIfRequired();
/*  210 */             throw ex;
/*      */           } 
/*  212 */         }  if (ebi.isReference()) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  218 */         updateProps = ebi.getLoadedProps();
/*      */       } 
/*      */     }
/*      */     
/*  222 */     BeanManager<?> mgr = getBeanManager(bean);
/*  223 */     if (mgr == null) {
/*  224 */       throw new PersistenceException(errNotRegistered(bean.getClass()));
/*      */     }
/*      */     
/*  227 */     forceUpdateStateless(bean, t, null, mgr, updateProps, deleteMissingChildren, updateNullProperties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void forceUpdateStateless(Object bean, Transaction t, Object parentBean, BeanManager<?> mgr, Set<String> updateProps, boolean deleteMissingChildren, boolean updateNullProperties) {
/*      */     PersistRequestBean<?> req;
/*  237 */     BeanDescriptor<?> descriptor = mgr.getBeanDescriptor();
/*      */ 
/*      */     
/*  240 */     ConcurrencyMode mode = descriptor.determineConcurrencyMode(bean);
/*      */     
/*  242 */     if (updateProps == null) {
/*      */       
/*  244 */       updateProps = updateNullProperties ? null : descriptor.determineLoadedProperties(bean);
/*      */     }
/*  246 */     else if (updateProps.isEmpty()) {
/*      */       
/*  248 */       updateProps = null;
/*      */     }
/*  250 */     else if (ConcurrencyMode.VERSION.equals(mode)) {
/*      */       
/*  252 */       String verName = descriptor.firstVersionProperty().getName();
/*  253 */       if (!updateProps.contains(verName)) {
/*      */         
/*  255 */         updateProps = new HashSet<String>(updateProps);
/*  256 */         updateProps.add(verName);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  261 */     if (descriptor.isLdapEntityType()) {
/*  262 */       LdapPersistBeanRequest ldapPersistBeanRequest = new LdapPersistBeanRequest(this.server, bean, parentBean, mgr, this.ldapPersister, updateProps, mode);
/*      */     }
/*      */     else {
/*      */       
/*  266 */       req = new PersistRequestBean(this.server, bean, parentBean, mgr, (SpiTransaction)t, this.persistExecute, updateProps, mode);
/*  267 */       req.setStatelessUpdate(true, deleteMissingChildren, updateNullProperties);
/*      */     } 
/*      */     
/*      */     try {
/*  271 */       req.initTransIfRequired();
/*  272 */       update(req);
/*  273 */       req.commitTransIfRequired();
/*      */     }
/*  275 */     catch (RuntimeException ex) {
/*  276 */       req.rollbackTransIfRequired();
/*  277 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void save(Object bean, Transaction t) {
/*  282 */     saveRecurse(bean, t, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void forceInsert(Object bean, Transaction t) {
/*  290 */     PersistRequestBean<?> req = createRequest(bean, t, null);
/*      */     try {
/*  292 */       req.initTransIfRequired();
/*  293 */       insert(req);
/*  294 */       req.commitTransIfRequired();
/*      */     }
/*  296 */     catch (RuntimeException ex) {
/*  297 */       req.rollbackTransIfRequired();
/*  298 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void saveRecurse(Object bean, Transaction t, Object parentBean) {
/*  303 */     if (bean == null) {
/*  304 */       throw new NullPointerException(Message.msg("bean.isnull"));
/*      */     }
/*      */     
/*  307 */     if (!(bean instanceof EntityBean)) {
/*  308 */       saveVanillaRecurse(bean, t, parentBean);
/*      */       
/*      */       return;
/*      */     } 
/*  312 */     PersistRequestBean<?> req = createRequest(bean, t, parentBean);
/*      */     try {
/*  314 */       req.initTransIfRequired();
/*  315 */       saveEnhanced(req);
/*  316 */       req.commitTransIfRequired();
/*      */     }
/*  318 */     catch (RuntimeException ex) {
/*  319 */       req.rollbackTransIfRequired();
/*  320 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void saveEnhanced(PersistRequestBean<?> request) {
/*  329 */     EntityBeanIntercept intercept = request.getEntityBeanIntercept();
/*      */     
/*  331 */     if (intercept.isReference()) {
/*      */       
/*  333 */       if (request.isPersistCascade())
/*      */       {
/*  335 */         intercept.setLoaded();
/*  336 */         saveAssocMany(false, request);
/*  337 */         intercept.setReference();
/*      */       }
/*      */     
/*      */     }
/*  341 */     else if (intercept.isLoaded()) {
/*      */       
/*  343 */       update(request);
/*      */     } else {
/*  345 */       insert(request);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void saveVanillaRecurse(Object bean, Transaction t, Object parentBean) {
/*  355 */     BeanManager<?> mgr = getBeanManager(bean);
/*  356 */     if (mgr == null) {
/*  357 */       throw new RuntimeException("No Mgr found for " + bean + " " + bean.getClass());
/*      */     }
/*      */     
/*  360 */     if (mgr.getBeanDescriptor().isVanillaInsert(bean)) {
/*  361 */       saveVanillaInsert(bean, t, parentBean, mgr);
/*      */     }
/*      */     else {
/*      */       
/*  365 */       forceUpdateStateless(bean, t, parentBean, mgr, null, this.defaultDeleteMissingChildren, this.defaultUpdateNullProperties);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void saveVanillaInsert(Object bean, Transaction t, Object parentBean, BeanManager<?> mgr) {
/*  374 */     PersistRequestBean<?> req = createRequest(bean, t, parentBean, mgr);
/*      */     try {
/*  376 */       req.initTransIfRequired();
/*  377 */       insert(req);
/*  378 */       req.commitTransIfRequired();
/*      */     }
/*  380 */     catch (RuntimeException ex) {
/*  381 */       req.rollbackTransIfRequired();
/*  382 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void insert(PersistRequestBean<?> request) {
/*  391 */     if (request.isRegisteredBean()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  397 */       request.setType(PersistRequest.Type.INSERT);
/*      */       
/*  399 */       if (request.isPersistCascade())
/*      */       {
/*  401 */         saveAssocOne(request);
/*      */       }
/*      */ 
/*      */       
/*  405 */       setIdGenValue(request);
/*  406 */       request.executeOrQueue();
/*      */       
/*  408 */       if (request.isPersistCascade())
/*      */       {
/*  410 */         saveAssocMany(true, request);
/*      */       }
/*      */     } finally {
/*  413 */       request.unRegisterBean();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void update(PersistRequestBean<?> request) {
/*  422 */     if (request.isRegisteredBean()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  429 */       request.setType(PersistRequest.Type.UPDATE);
/*  430 */       if (request.isPersistCascade())
/*      */       {
/*  432 */         saveAssocOne(request);
/*      */       }
/*      */       
/*  435 */       if (request.isDirty()) {
/*  436 */         request.executeOrQueue();
/*      */ 
/*      */       
/*      */       }
/*  440 */       else if (logger.isLoggable(Level.FINE)) {
/*  441 */         logger.fine(Message.msg("persist.update.skipped", request.getBean()));
/*      */       } 
/*      */ 
/*      */       
/*  445 */       if (request.isPersistCascade())
/*      */       {
/*  447 */         saveAssocMany(false, request);
/*      */       }
/*      */     } finally {
/*  450 */       request.unRegisterBean();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void delete(Object bean, Transaction t) {
/*  459 */     PersistRequestBean<?> req = createRequest(bean, t, null);
/*  460 */     if (req.isRegisteredForDeleteBean()) {
/*      */ 
/*      */       
/*  463 */       if (logger.isLoggable(Level.FINE)) {
/*  464 */         logger.fine("skipping delete on alreadyRegistered " + bean);
/*      */       }
/*      */       return;
/*      */     } 
/*  468 */     req.setType(PersistRequest.Type.DELETE);
/*      */     try {
/*  470 */       req.initTransIfRequired();
/*  471 */       delete(req);
/*  472 */       req.commitTransIfRequired();
/*      */     }
/*  474 */     catch (RuntimeException ex) {
/*  475 */       req.rollbackTransIfRequired();
/*  476 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void deleteList(List<?> beanList, Transaction t) {
/*  481 */     for (int i = 0; i < beanList.size(); i++) {
/*  482 */       Object bean = beanList.get(i);
/*  483 */       delete(bean, t);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteMany(Class<?> beanType, Collection<?> ids, Transaction transaction) {
/*  492 */     if (ids == null || ids.size() == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  496 */     BeanDescriptor<?> descriptor = this.beanDescriptorManager.getBeanDescriptor(beanType);
/*      */     
/*  498 */     ArrayList<Object> idList = new ArrayList(ids.size());
/*  499 */     for (Object id : ids)
/*      */     {
/*  501 */       idList.add(descriptor.convertId(id));
/*      */     }
/*      */     
/*  504 */     delete(descriptor, null, idList, transaction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int delete(Class<?> beanType, Object id, Transaction transaction) {
/*  512 */     BeanDescriptor<?> descriptor = this.beanDescriptorManager.getBeanDescriptor(beanType);
/*      */ 
/*      */     
/*  515 */     id = descriptor.convertId(id);
/*  516 */     return delete(descriptor, id, null, transaction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int delete(BeanDescriptor<?> descriptor, Object id, List<Object> idList, Transaction transaction) {
/*  524 */     SpiTransaction t = (SpiTransaction)transaction;
/*  525 */     if (t.isPersistCascade()) {
/*  526 */       BeanPropertyAssocOne[] arrayOfBeanPropertyAssocOne = descriptor.propertiesOneImportedDelete();
/*  527 */       if (arrayOfBeanPropertyAssocOne.length > 0) {
/*      */ 
/*      */ 
/*      */         
/*  531 */         Query<?> q = deleteRequiresQuery(descriptor, (BeanPropertyAssocOne<?>[])arrayOfBeanPropertyAssocOne);
/*  532 */         if (idList != null) {
/*  533 */           q.where().idIn(idList);
/*  534 */           if (t.isLogSummary()) {
/*  535 */             t.logInternal("-- DeleteById of " + descriptor.getName() + " ids[" + idList + "] requires fetch of foreign key values");
/*      */           }
/*  537 */           List<?> beanList = this.server.findList(q, (Transaction)t);
/*  538 */           deleteList(beanList, (Transaction)t);
/*  539 */           return beanList.size();
/*      */         } 
/*      */         
/*  542 */         q.where().idEq(id);
/*  543 */         if (t.isLogSummary()) {
/*  544 */           t.logInternal("-- DeleteById of " + descriptor.getName() + " id[" + id + "] requires fetch of foreign key values");
/*      */         }
/*  546 */         Object bean = this.server.findUnique(q, (Transaction)t);
/*  547 */         if (bean == null) {
/*  548 */           return 0;
/*      */         }
/*  550 */         delete(bean, (Transaction)t);
/*  551 */         return 1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  557 */     if (t.isPersistCascade()) {
/*      */       
/*  559 */       BeanPropertyAssocOne[] arrayOfBeanPropertyAssocOne = descriptor.propertiesOneExportedDelete();
/*  560 */       for (int j = 0; j < arrayOfBeanPropertyAssocOne.length; j++) {
/*  561 */         BeanDescriptor<?> targetDesc = arrayOfBeanPropertyAssocOne[j].getTargetDescriptor();
/*  562 */         if (targetDesc.isDeleteRecurseSkippable() && !targetDesc.isUsingL2Cache()) {
/*  563 */           SqlUpdate sqlDelete = arrayOfBeanPropertyAssocOne[j].deleteByParentId(id, idList);
/*  564 */           executeSqlUpdate(sqlDelete, (Transaction)t);
/*      */         } else {
/*  566 */           List<Object> childIds = arrayOfBeanPropertyAssocOne[j].findIdsByParentId(id, idList, (Transaction)t);
/*  567 */           delete(targetDesc, null, childIds, (Transaction)t);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  572 */       BeanPropertyAssocMany[] arrayOfBeanPropertyAssocMany1 = descriptor.propertiesManyDelete();
/*  573 */       for (int k = 0; k < arrayOfBeanPropertyAssocMany1.length; k++) {
/*  574 */         BeanDescriptor<?> targetDesc = arrayOfBeanPropertyAssocMany1[k].getTargetDescriptor();
/*  575 */         if (targetDesc.isDeleteRecurseSkippable() && !targetDesc.isUsingL2Cache()) {
/*      */           
/*  577 */           SqlUpdate sqlDelete = arrayOfBeanPropertyAssocMany1[k].deleteByParentId(id, idList);
/*  578 */           executeSqlUpdate(sqlDelete, (Transaction)t);
/*      */         } else {
/*      */           
/*  581 */           List<Object> childIds = arrayOfBeanPropertyAssocMany1[k].findIdsByParentId(id, idList, (Transaction)t, null);
/*  582 */           delete(targetDesc, null, childIds, (Transaction)t);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  588 */     BeanPropertyAssocMany[] arrayOfBeanPropertyAssocMany = descriptor.propertiesManyToMany();
/*  589 */     for (int i = 0; i < arrayOfBeanPropertyAssocMany.length; i++) {
/*  590 */       SqlUpdate sqlDelete = arrayOfBeanPropertyAssocMany[i].deleteByParentId(id, idList);
/*  591 */       if (t.isLogSummary()) {
/*  592 */         t.logInternal("-- Deleting intersection table entries: " + arrayOfBeanPropertyAssocMany[i].getFullBeanName());
/*      */       }
/*  594 */       executeSqlUpdate(sqlDelete, (Transaction)t);
/*      */     } 
/*      */ 
/*      */     
/*  598 */     SqlUpdate deleteById = descriptor.deleteById(id, idList);
/*  599 */     if (t.isLogSummary()) {
/*  600 */       t.logInternal("-- Deleting " + descriptor.getName() + " Ids" + idList);
/*      */     }
/*      */ 
/*      */     
/*  604 */     deleteById.setAutoTableMod(false);
/*  605 */     if (idList != null) {
/*  606 */       t.getEvent().addDeleteByIdList(descriptor, idList);
/*      */     } else {
/*  608 */       t.getEvent().addDeleteById(descriptor, id);
/*      */     } 
/*  610 */     return executeSqlUpdate(deleteById, (Transaction)t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Query<?> deleteRequiresQuery(BeanDescriptor<?> desc, BeanPropertyAssocOne<?>[] propImportDelete) {
/*  619 */     Query<?> q = this.server.createQuery(desc.getBeanType());
/*  620 */     StringBuilder sb = new StringBuilder(30);
/*  621 */     for (int i = 0; i < propImportDelete.length; i++) {
/*  622 */       sb.append(propImportDelete[i].getName()).append(",");
/*      */     }
/*  624 */     q.setAutofetch(false);
/*  625 */     q.select(sb.toString());
/*  626 */     return q;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void delete(PersistRequestBean<?> request) {
/*  637 */     DeleteUnloadedForeignKeys unloadedForeignKeys = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  646 */     if (request.isPersistCascade()) {
/*      */ 
/*      */       
/*  649 */       request.registerDeleteBean();
/*  650 */       deleteAssocMany(request);
/*  651 */       request.unregisterDeleteBean();
/*      */       
/*  653 */       unloadedForeignKeys = getDeleteUnloadedForeignKeys(request);
/*  654 */       if (unloadedForeignKeys != null)
/*      */       {
/*      */         
/*  657 */         unloadedForeignKeys.queryForeignKeys();
/*      */       }
/*      */     } 
/*      */     
/*  661 */     request.executeOrQueue();
/*      */     
/*  663 */     if (request.isPersistCascade()) {
/*  664 */       deleteAssocOne(request);
/*      */       
/*  666 */       if (unloadedForeignKeys != null) {
/*  667 */         unloadedForeignKeys.deleteCascade();
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
/*      */   private void saveAssocMany(boolean insertedParent, PersistRequestBean<?> request) {
/*  684 */     Object parentBean = request.getBean();
/*  685 */     BeanDescriptor<?> desc = request.getBeanDescriptor();
/*  686 */     SpiTransaction t = request.getTransaction();
/*      */ 
/*      */     
/*  689 */     BeanPropertyAssocOne[] arrayOfBeanPropertyAssocOne = desc.propertiesOneExportedSave();
/*  690 */     for (int i = 0; i < arrayOfBeanPropertyAssocOne.length; i++) {
/*  691 */       BeanPropertyAssocOne<?> prop = arrayOfBeanPropertyAssocOne[i];
/*      */ 
/*      */       
/*  694 */       if (request.isLoadedProperty((BeanProperty)prop)) {
/*  695 */         Object detailBean = prop.getValue(parentBean);
/*  696 */         if (detailBean != null && 
/*  697 */           !prop.isSaveRecurseSkippable(detailBean)) {
/*      */ 
/*      */           
/*  700 */           t.depth(1);
/*  701 */           saveRecurse(detailBean, (Transaction)t, parentBean);
/*  702 */           t.depth(-1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  709 */     BeanPropertyAssocMany[] arrayOfBeanPropertyAssocMany = desc.propertiesManySave();
/*  710 */     for (int j = 0; j < arrayOfBeanPropertyAssocMany.length; j++) {
/*  711 */       saveMany(new SaveManyPropRequest(insertedParent, arrayOfBeanPropertyAssocMany[j], parentBean, request));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SaveManyPropRequest
/*      */   {
/*      */     private final boolean insertedParent;
/*      */     
/*      */     private final BeanPropertyAssocMany<?> many;
/*      */     
/*      */     private final Object parentBean;
/*      */     private final SpiTransaction t;
/*      */     private final boolean cascade;
/*      */     private final boolean statelessUpdate;
/*      */     private final boolean deleteMissingChildren;
/*      */     private final boolean updateNullProperties;
/*      */     
/*      */     private SaveManyPropRequest(boolean insertedParent, BeanPropertyAssocMany<?> many, Object parentBean, PersistRequestBean<?> request) {
/*  730 */       this.insertedParent = insertedParent;
/*  731 */       this.many = many;
/*  732 */       this.cascade = many.getCascadeInfo().isSave();
/*  733 */       this.parentBean = parentBean;
/*  734 */       this.t = request.getTransaction();
/*  735 */       this.statelessUpdate = request.isStatelessUpdate();
/*  736 */       this.deleteMissingChildren = request.isDeleteMissingChildren();
/*  737 */       this.updateNullProperties = request.isUpdateNullProperties();
/*      */     }
/*      */     
/*      */     private SaveManyPropRequest(BeanPropertyAssocMany<?> many, Object parentBean, SpiTransaction t) {
/*  741 */       this.insertedParent = false;
/*  742 */       this.many = many;
/*  743 */       this.parentBean = parentBean;
/*  744 */       this.t = t;
/*  745 */       this.cascade = true;
/*  746 */       this.statelessUpdate = false;
/*  747 */       this.deleteMissingChildren = false;
/*  748 */       this.updateNullProperties = false;
/*      */     }
/*      */     
/*      */     private Object getValueUnderlying() {
/*  752 */       return this.many.getValueUnderlying(this.parentBean);
/*      */     }
/*      */     
/*      */     private boolean isModifyListenMode() {
/*  756 */       return BeanCollection.ModifyListenMode.REMOVALS.equals(this.many.getModifyListenMode());
/*      */     }
/*      */     
/*      */     private boolean isStatelessUpdate() {
/*  760 */       return this.statelessUpdate;
/*      */     }
/*      */     
/*      */     private boolean isDeleteMissingChildren() {
/*  764 */       return this.deleteMissingChildren;
/*      */     }
/*      */     
/*      */     private boolean isUpdateNullProperties() {
/*  768 */       return this.updateNullProperties;
/*      */     }
/*      */     
/*      */     private boolean isInsertedParent() {
/*  772 */       return this.insertedParent;
/*      */     }
/*      */     
/*      */     private BeanPropertyAssocMany<?> getMany() {
/*  776 */       return this.many;
/*      */     }
/*      */     
/*      */     private Object getParentBean() {
/*  780 */       return this.parentBean;
/*      */     }
/*      */     
/*      */     private SpiTransaction getTransaction() {
/*  784 */       return this.t;
/*      */     }
/*      */     
/*      */     private boolean isCascade() {
/*  788 */       return this.cascade;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void saveMany(SaveManyPropRequest saveMany) {
/*  794 */     if (saveMany.getMany().isManyToMany()) {
/*      */       
/*  796 */       if (saveMany.isCascade()) {
/*      */         
/*  798 */         saveAssocManyDetails(saveMany, false, saveMany.isUpdateNullProperties());
/*      */ 
/*      */         
/*  801 */         saveAssocManyIntersection(saveMany, saveMany.isDeleteMissingChildren());
/*      */       } 
/*      */     } else {
/*      */       
/*  805 */       if (saveMany.isCascade()) {
/*  806 */         saveAssocManyDetails(saveMany, saveMany.isDeleteMissingChildren(), saveMany.isUpdateNullProperties());
/*      */       }
/*  808 */       if (saveMany.isModifyListenMode()) {
/*  809 */         removeAssocManyPrivateOwned(saveMany);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void removeAssocManyPrivateOwned(SaveManyPropRequest saveMany) {
/*  816 */     Object details = saveMany.getValueUnderlying();
/*      */ 
/*      */ 
/*      */     
/*  820 */     if (details instanceof BeanCollection) {
/*      */       
/*  822 */       BeanCollection<?> c = (BeanCollection)details;
/*  823 */       Set<?> modifyRemovals = c.getModifyRemovals();
/*  824 */       if (modifyRemovals != null && !modifyRemovals.isEmpty()) {
/*      */         
/*  826 */         SpiTransaction t = saveMany.getTransaction();
/*      */         
/*  828 */         t.depth(1);
/*  829 */         for (Object removedBean : modifyRemovals) {
/*  830 */           deleteRecurse(removedBean, (Transaction)t);
/*      */         }
/*  832 */         t.depth(-1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void saveAssocManyDetails(SaveManyPropRequest saveMany, boolean deleteMissingChildren, boolean updateNullProperties) {
/*  842 */     BeanPropertyAssocMany<?> prop = saveMany.getMany();
/*      */     
/*  844 */     Object details = saveMany.getValueUnderlying();
/*      */ 
/*      */ 
/*      */     
/*  848 */     Collection<?> collection = getDetailsIterator(details);
/*      */     
/*  850 */     if (collection == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  855 */     if (saveMany.isInsertedParent())
/*      */     {
/*  857 */       prop.getTargetDescriptor().preAllocateIds(collection.size());
/*      */     }
/*      */     
/*  860 */     BeanDescriptor<?> targetDescriptor = prop.getTargetDescriptor();
/*  861 */     ArrayList<Object> detailIds = null;
/*  862 */     if (deleteMissingChildren)
/*      */     {
/*  864 */       detailIds = new ArrayList();
/*      */     }
/*      */ 
/*      */     
/*  868 */     SpiTransaction t = saveMany.getTransaction();
/*  869 */     t.depth(1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  874 */     boolean isMap = ManyType.JAVA_MAP.equals(prop.getManyType());
/*  875 */     Object parentBean = saveMany.getParentBean();
/*  876 */     Object mapKeyValue = null;
/*      */     
/*  878 */     boolean saveSkippable = prop.isSaveRecurseSkippable();
/*  879 */     boolean skipSavingThisBean = false;
/*      */     
/*  881 */     for (Object detailBean : collection) {
/*  882 */       if (isMap) {
/*      */         
/*  884 */         Map.Entry<?, ?> entry = (Map.Entry<?, ?>)detailBean;
/*  885 */         mapKeyValue = entry.getKey();
/*  886 */         detailBean = entry.getValue();
/*      */       } 
/*      */       
/*  889 */       if (prop.isManyToMany()) {
/*  890 */         if (detailBean instanceof EntityBean) {
/*  891 */           skipSavingThisBean = ((EntityBean)detailBean)._ebean_getIntercept().isReference();
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  896 */       else if (detailBean instanceof EntityBean) {
/*  897 */         EntityBeanIntercept ebi = ((EntityBean)detailBean)._ebean_getIntercept();
/*  898 */         if (ebi.isNewOrDirty()) {
/*      */           
/*  900 */           prop.setJoinValuesToChild(parentBean, detailBean, mapKeyValue);
/*  901 */         } else if (ebi.isReference()) {
/*      */           
/*  903 */           skipSavingThisBean = true;
/*      */         }
/*      */         else {
/*      */           
/*  907 */           skipSavingThisBean = saveSkippable;
/*      */         } 
/*      */       } else {
/*      */         
/*  911 */         prop.setJoinValuesToChild(parentBean, detailBean, mapKeyValue);
/*      */       } 
/*      */ 
/*      */       
/*  915 */       if (skipSavingThisBean) {
/*      */ 
/*      */ 
/*      */         
/*  919 */         skipSavingThisBean = false;
/*      */       }
/*  921 */       else if (!saveMany.isStatelessUpdate()) {
/*      */         
/*  923 */         saveRecurse(detailBean, (Transaction)t, parentBean);
/*      */       
/*      */       }
/*  926 */       else if (targetDescriptor.isStatelessUpdate(detailBean)) {
/*      */ 
/*      */         
/*  929 */         forceUpdate(detailBean, null, (Transaction)t, deleteMissingChildren, updateNullProperties);
/*      */       } else {
/*      */         
/*  932 */         forceInsert(detailBean, (Transaction)t);
/*      */       } 
/*      */ 
/*      */       
/*  936 */       if (detailIds != null) {
/*      */         
/*  938 */         Object id = targetDescriptor.getId(detailBean);
/*  939 */         if (!DmlUtil.isNullOrZero(id)) {
/*  940 */           detailIds.add(id);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  945 */     if (detailIds != null) {
/*  946 */       deleteManyDetails(t, prop.getBeanDescriptor(), parentBean, prop, detailIds);
/*      */     }
/*      */     
/*  949 */     t.depth(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int deleteManyToManyAssociations(Object ownerBean, String propertyName, Transaction t) {
/*  955 */     BeanDescriptor<?> descriptor = this.beanDescriptorManager.getBeanDescriptor(ownerBean.getClass());
/*  956 */     BeanPropertyAssocMany<?> prop = (BeanPropertyAssocMany)descriptor.getBeanProperty(propertyName);
/*  957 */     return deleteAssocManyIntersection(ownerBean, prop, t);
/*      */   }
/*      */ 
/*      */   
/*      */   public void saveManyToManyAssociations(Object ownerBean, String propertyName, Transaction t) {
/*  962 */     BeanDescriptor<?> descriptor = this.beanDescriptorManager.getBeanDescriptor(ownerBean.getClass());
/*  963 */     BeanPropertyAssocMany<?> prop = (BeanPropertyAssocMany)descriptor.getBeanProperty(propertyName);
/*      */     
/*  965 */     saveAssocManyIntersection(new SaveManyPropRequest(prop, ownerBean, (SpiTransaction)t), false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void saveAssociation(Object parentBean, String propertyName, Transaction t) {
/*  970 */     BeanDescriptor<?> descriptor = this.beanDescriptorManager.getBeanDescriptor(parentBean.getClass());
/*  971 */     SpiTransaction trans = (SpiTransaction)t;
/*      */     
/*  973 */     BeanProperty prop = descriptor.getBeanProperty(propertyName);
/*  974 */     if (prop == null) {
/*  975 */       String msg = "Could not find property [" + propertyName + "] on bean " + parentBean.getClass();
/*  976 */       throw new PersistenceException(msg);
/*      */     } 
/*      */     
/*  979 */     if (prop instanceof BeanPropertyAssocMany) {
/*  980 */       BeanPropertyAssocMany<?> manyProp = (BeanPropertyAssocMany)prop;
/*  981 */       saveMany(new SaveManyPropRequest(manyProp, parentBean, (SpiTransaction)t));
/*      */     }
/*  983 */     else if (prop instanceof BeanPropertyAssocOne) {
/*  984 */       BeanPropertyAssocOne<?> oneProp = (BeanPropertyAssocOne)prop;
/*  985 */       Object assocBean = oneProp.getValue(parentBean);
/*      */       
/*  987 */       int depth = oneProp.isOneToOneExported() ? 1 : -1;
/*  988 */       int revertDepth = -1 * depth;
/*      */       
/*  990 */       trans.depth(depth);
/*  991 */       saveRecurse(assocBean, t, parentBean);
/*  992 */       trans.depth(revertDepth);
/*      */     } else {
/*      */       
/*  995 */       String msg = "Expecting [" + prop.getFullBeanName() + "] to be a OneToMany, OneToOne, ManyToOne or ManyToMany property?";
/*  996 */       throw new PersistenceException(msg);
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
/*      */   private void saveAssocManyIntersection(SaveManyPropRequest saveManyPropRequest, boolean deleteMissingChildren) {
/* 1010 */     BeanPropertyAssocMany<?> prop = saveManyPropRequest.getMany();
/* 1011 */     Object value = prop.getValueUnderlying(saveManyPropRequest.getParentBean());
/* 1012 */     if (value == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1016 */     SpiTransaction t = saveManyPropRequest.getTransaction();
/* 1017 */     Collection<?> additions = null;
/* 1018 */     Collection<?> deletions = null;
/*      */     
/* 1020 */     boolean vanillaCollection = !(value instanceof BeanCollection);
/*      */     
/* 1022 */     if (vanillaCollection || deleteMissingChildren)
/*      */     {
/*      */       
/* 1025 */       deleteAssocManyIntersection(saveManyPropRequest.getParentBean(), prop, (Transaction)t);
/*      */     }
/*      */     
/* 1028 */     if (saveManyPropRequest.isInsertedParent() || vanillaCollection || deleteMissingChildren) {
/*      */       
/* 1030 */       if (value instanceof Map) {
/* 1031 */         additions = ((Map)value).values();
/* 1032 */       } else if (value instanceof Collection) {
/* 1033 */         additions = (Collection)value;
/*      */       } else {
/* 1035 */         String msg = "Unhandled ManyToMany type " + value.getClass().getName() + " for " + prop.getFullBeanName();
/* 1036 */         throw new PersistenceException(msg);
/*      */       } 
/* 1038 */       if (!vanillaCollection) {
/* 1039 */         ((BeanCollection)value).modifyReset();
/*      */       }
/*      */     } else {
/*      */       
/* 1043 */       BeanCollection<?> manyValue = (BeanCollection)value;
/* 1044 */       additions = manyValue.getModifyAdditions();
/* 1045 */       deletions = manyValue.getModifyRemovals();
/*      */       
/* 1047 */       manyValue.modifyReset();
/*      */     } 
/*      */     
/* 1050 */     t.depth(1);
/*      */     
/* 1052 */     if (additions != null && !additions.isEmpty()) {
/* 1053 */       for (Object otherBean : additions) {
/*      */         
/* 1055 */         if (deletions != null && deletions.remove(otherBean)) {
/* 1056 */           String m = "Inserting and Deleting same object? " + otherBean;
/* 1057 */           if (t.isLogSummary()) {
/* 1058 */             t.logInternal(m);
/*      */           }
/* 1060 */           logger.log(Level.WARNING, m);
/*      */           continue;
/*      */         } 
/* 1063 */         if (!prop.hasImportedId(otherBean)) {
/* 1064 */           String msg = "ManyToMany bean " + otherBean + " does not have an Id value.";
/* 1065 */           throw new PersistenceException(msg);
/*      */         } 
/*      */ 
/*      */         
/* 1069 */         IntersectionRow intRow = prop.buildManyToManyMapBean(saveManyPropRequest.getParentBean(), otherBean);
/* 1070 */         SqlUpdate sqlInsert = intRow.createInsert((EbeanServer)this.server);
/* 1071 */         executeSqlUpdate(sqlInsert, (Transaction)t);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1076 */     if (deletions != null && !deletions.isEmpty()) {
/* 1077 */       for (Object otherDelete : deletions) {
/*      */ 
/*      */         
/* 1080 */         IntersectionRow intRow = prop.buildManyToManyMapBean(saveManyPropRequest.getParentBean(), otherDelete);
/* 1081 */         SqlUpdate sqlDelete = intRow.createDelete((EbeanServer)this.server);
/* 1082 */         executeSqlUpdate(sqlDelete, (Transaction)t);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1087 */     t.depth(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int deleteAssocManyIntersection(Object bean, BeanPropertyAssocMany<?> many, Transaction t) {
/* 1093 */     IntersectionRow intRow = many.buildManyToManyDeleteChildren(bean);
/* 1094 */     SqlUpdate sqlDelete = intRow.createDeleteChildren((EbeanServer)this.server);
/*      */     
/* 1096 */     return executeSqlUpdate(sqlDelete, t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deleteAssocMany(PersistRequestBean<?> request) {
/* 1107 */     SpiTransaction t = request.getTransaction();
/* 1108 */     t.depth(-1);
/*      */     
/* 1110 */     BeanDescriptor<?> desc = request.getBeanDescriptor();
/* 1111 */     Object parentBean = request.getBean();
/*      */     
/* 1113 */     BeanPropertyAssocOne[] arrayOfBeanPropertyAssocOne = desc.propertiesOneExportedDelete();
/* 1114 */     if (arrayOfBeanPropertyAssocOne.length > 0) {
/*      */       
/* 1116 */       DeleteUnloadedForeignKeys unloaded = null;
/* 1117 */       for (int j = 0; j < arrayOfBeanPropertyAssocOne.length; j++) {
/* 1118 */         BeanPropertyAssocOne<?> prop = arrayOfBeanPropertyAssocOne[j];
/* 1119 */         if (request.isLoadedProperty((BeanProperty)prop)) {
/* 1120 */           Object detailBean = prop.getValue(parentBean);
/* 1121 */           if (detailBean != null) {
/* 1122 */             deleteRecurse(detailBean, (Transaction)t);
/*      */           }
/*      */         } else {
/* 1125 */           if (unloaded == null) {
/* 1126 */             unloaded = new DeleteUnloadedForeignKeys(this.server, request);
/*      */           }
/* 1128 */           unloaded.add(prop);
/*      */         } 
/*      */       } 
/* 1131 */       if (unloaded != null) {
/* 1132 */         unloaded.queryForeignKeys();
/* 1133 */         unloaded.deleteCascade();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1138 */     BeanPropertyAssocMany[] arrayOfBeanPropertyAssocMany = desc.propertiesManyDelete();
/* 1139 */     for (int i = 0; i < arrayOfBeanPropertyAssocMany.length; i++) {
/* 1140 */       if (arrayOfBeanPropertyAssocMany[i].isManyToMany()) {
/*      */         
/* 1142 */         deleteAssocManyIntersection(parentBean, arrayOfBeanPropertyAssocMany[i], (Transaction)t);
/*      */       }
/*      */       else {
/*      */         
/* 1146 */         if (BeanCollection.ModifyListenMode.REMOVALS.equals(arrayOfBeanPropertyAssocMany[i].getModifyListenMode())) {
/*      */           
/* 1148 */           Object details = arrayOfBeanPropertyAssocMany[i].getValueUnderlying(parentBean);
/* 1149 */           if (details instanceof BeanCollection) {
/* 1150 */             Set<?> modifyRemovals = ((BeanCollection)details).getModifyRemovals();
/* 1151 */             if (modifyRemovals != null && !modifyRemovals.isEmpty())
/*      */             {
/*      */               
/* 1154 */               for (Object detailBean : modifyRemovals) {
/* 1155 */                 if (arrayOfBeanPropertyAssocMany[i].hasId(detailBean)) {
/* 1156 */                   deleteRecurse(detailBean, (Transaction)t);
/*      */                 }
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 1163 */         deleteManyDetails(t, desc, parentBean, arrayOfBeanPropertyAssocMany[i], null);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1168 */     t.depth(1);
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
/*      */   private void deleteManyDetails(SpiTransaction t, BeanDescriptor<?> desc, Object parentBean, BeanPropertyAssocMany<?> many, ArrayList<Object> excludeDetailIds) {
/* 1182 */     if (many.getCascadeInfo().isDelete()) {
/*      */       
/* 1184 */       BeanDescriptor<?> targetDesc = many.getTargetDescriptor();
/* 1185 */       if (targetDesc.isDeleteRecurseSkippable() && !targetDesc.isUsingL2Cache()) {
/*      */         
/* 1187 */         IntersectionRow intRow = many.buildManyDeleteChildren(parentBean, excludeDetailIds);
/* 1188 */         SqlUpdate sqlDelete = intRow.createDelete((EbeanServer)this.server);
/* 1189 */         executeSqlUpdate(sqlDelete, (Transaction)t);
/*      */       }
/*      */       else {
/*      */         
/* 1193 */         Object parentId = desc.getId(parentBean);
/* 1194 */         List<Object> idsByParentId = many.findIdsByParentId(parentId, null, (Transaction)t, excludeDetailIds);
/* 1195 */         if (!idsByParentId.isEmpty()) {
/* 1196 */           delete(targetDesc, null, idsByParentId, (Transaction)t);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void saveAssocOne(PersistRequestBean<?> request) {
/* 1207 */     BeanDescriptor<?> desc = request.getBeanDescriptor();
/*      */ 
/*      */     
/* 1210 */     BeanPropertyAssocOne[] arrayOfBeanPropertyAssocOne = desc.propertiesOneImportedSave();
/*      */     
/* 1212 */     for (int i = 0; i < arrayOfBeanPropertyAssocOne.length; i++) {
/* 1213 */       BeanPropertyAssocOne<?> prop = arrayOfBeanPropertyAssocOne[i];
/*      */ 
/*      */       
/* 1216 */       if (request.isLoadedProperty((BeanProperty)prop)) {
/* 1217 */         Object detailBean = prop.getValue(request.getBean());
/* 1218 */         if (detailBean != null && 
/* 1219 */           !isReference(detailBean))
/*      */         {
/* 1221 */           if (!request.isParent(detailBean))
/*      */           {
/* 1223 */             if (!prop.isSaveRecurseSkippable(detailBean)) {
/*      */ 
/*      */ 
/*      */               
/* 1227 */               SpiTransaction t = request.getTransaction();
/* 1228 */               t.depth(-1);
/* 1229 */               saveRecurse(detailBean, (Transaction)t, null);
/* 1230 */               t.depth(1);
/*      */             } 
/*      */           }
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isReference(Object bean) {
/* 1241 */     return (bean instanceof EntityBean && ((EntityBean)bean)._ebean_getIntercept().isReference());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DeleteUnloadedForeignKeys getDeleteUnloadedForeignKeys(PersistRequestBean<?> request) {
/* 1250 */     DeleteUnloadedForeignKeys fkeys = null;
/*      */     
/* 1252 */     BeanPropertyAssocOne[] arrayOfBeanPropertyAssocOne = request.getBeanDescriptor().propertiesOneImportedDelete();
/* 1253 */     for (int i = 0; i < arrayOfBeanPropertyAssocOne.length; i++) {
/* 1254 */       if (!request.isLoadedProperty((BeanProperty)arrayOfBeanPropertyAssocOne[i])) {
/*      */ 
/*      */         
/* 1257 */         if (fkeys == null) {
/* 1258 */           fkeys = new DeleteUnloadedForeignKeys(this.server, request);
/*      */         }
/* 1260 */         fkeys.add(arrayOfBeanPropertyAssocOne[i]);
/*      */       } 
/*      */     } 
/*      */     
/* 1264 */     return fkeys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deleteAssocOne(PersistRequestBean<?> request) {
/* 1272 */     BeanDescriptor<?> desc = request.getBeanDescriptor();
/* 1273 */     BeanPropertyAssocOne[] arrayOfBeanPropertyAssocOne = desc.propertiesOneImportedDelete();
/*      */     
/* 1275 */     for (int i = 0; i < arrayOfBeanPropertyAssocOne.length; i++) {
/* 1276 */       BeanPropertyAssocOne<?> prop = arrayOfBeanPropertyAssocOne[i];
/* 1277 */       if (request.isLoadedProperty((BeanProperty)prop)) {
/*      */ 
/*      */ 
/*      */         
/* 1281 */         Object detailBean = prop.getValue(request.getBean());
/* 1282 */         if (detailBean != null && prop.hasId(detailBean)) {
/* 1283 */           deleteRecurse(detailBean, (Transaction)request.getTransaction());
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setIdGenValue(PersistRequestBean<?> request) {
/* 1294 */     BeanDescriptor<?> desc = request.getBeanDescriptor();
/* 1295 */     if (!desc.isUseIdGenerator()) {
/*      */       return;
/*      */     }
/*      */     
/* 1299 */     BeanProperty idProp = desc.getSingleIdProperty();
/* 1300 */     if (idProp == null || idProp.isEmbedded()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1305 */     Object bean = request.getBean();
/* 1306 */     Object uid = idProp.getValue(bean);
/*      */     
/* 1308 */     if (DmlUtil.isNullOrZero(uid)) {
/*      */ 
/*      */       
/* 1311 */       Object nextId = desc.nextId((Transaction)request.getTransaction());
/*      */ 
/*      */       
/* 1314 */       desc.convertSetId(nextId, bean);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Collection<?> getDetailsIterator(Object o) {
/* 1323 */     if (o == null) {
/* 1324 */       return null;
/*      */     }
/* 1326 */     if (o instanceof BeanCollection) {
/* 1327 */       BeanCollection<?> bc = (BeanCollection)o;
/* 1328 */       if (!bc.isPopulated()) {
/* 1329 */         return null;
/*      */       }
/* 1331 */       return bc.getActualDetails();
/*      */     } 
/*      */     
/* 1334 */     if (o instanceof Map)
/*      */     {
/* 1336 */       return ((Map)o).entrySet();
/*      */     }
/* 1338 */     if (o instanceof Collection) {
/* 1339 */       return (Collection)o;
/*      */     }
/* 1341 */     String m = "expecting a Map or Collection but got [" + o.getClass().getName() + "]";
/* 1342 */     throw new PersistenceException(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> PersistRequestBean<T> createRequest(T bean, Transaction t, Object parentBean) {
/* 1351 */     BeanManager<T> mgr = getBeanManager(bean);
/* 1352 */     if (mgr == null) {
/* 1353 */       throw new PersistenceException(errNotRegistered(bean.getClass()));
/*      */     }
/* 1355 */     return (PersistRequestBean)createRequest(bean, t, parentBean, mgr);
/*      */   }
/*      */   
/*      */   private String errNotRegistered(Class<?> beanClass) {
/* 1359 */     String msg = "The type [" + beanClass + "] is not a registered entity?";
/* 1360 */     msg = msg + " If you don't explicitly list the entity classes to use Ebean will search for them in the classpath.";
/* 1361 */     msg = msg + " If the entity is in a Jar check the ebean.search.jars property in ebean.properties file or check ServerConfig.addJar().";
/* 1362 */     return msg;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PersistRequestBean<?> createRequest(Object bean, Transaction t, Object parentBean, BeanManager<?> mgr) {
/* 1372 */     if (mgr.isLdapEntityType()) {
/* 1373 */       return (PersistRequestBean<?>)new LdapPersistBeanRequest(this.server, bean, parentBean, mgr, this.ldapPersister);
/*      */     }
/* 1375 */     return new PersistRequestBean(this.server, bean, parentBean, mgr, (SpiTransaction)t, this.persistExecute);
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
/*      */   private <T> BeanManager<T> getBeanManager(T bean) {
/* 1388 */     return this.beanDescriptorManager.getBeanManager(bean.getClass());
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\DefaultPersister.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */