/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.ExpressionList;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.bean.ObjectGraphNode;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebeaninternal.api.LoadBeanContext;
/*     */ import com.avaje.ebeaninternal.api.LoadBeanRequest;
/*     */ import com.avaje.ebeaninternal.api.LoadManyContext;
/*     */ import com.avaje.ebeaninternal.api.LoadManyRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.transaction.DefaultPersistenceContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.EntityNotFoundException;
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
/*     */ public class DefaultBeanLoader
/*     */ {
/*  53 */   private static final Logger logger = Logger.getLogger(DefaultBeanLoader.class.getName());
/*     */   
/*     */   private final DebugLazyLoad debugLazyLoad;
/*     */   
/*     */   private final DefaultServer server;
/*     */   
/*     */   protected DefaultBeanLoader(DefaultServer server, DebugLazyLoad debugLazyLoad) {
/*  60 */     this.server = server;
/*  61 */     this.debugLazyLoad = debugLazyLoad;
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
/*     */   private int getBatchSize(int batchListSize, int requestedBatchSize) {
/*  75 */     if (batchListSize == requestedBatchSize) {
/*  76 */       return batchListSize;
/*     */     }
/*  78 */     if (batchListSize == 1)
/*     */     {
/*  80 */       return 1;
/*     */     }
/*  82 */     if (requestedBatchSize <= 5)
/*     */     {
/*  84 */       return 5;
/*     */     }
/*  86 */     if (batchListSize <= 10 || requestedBatchSize <= 10)
/*     */     {
/*     */       
/*  89 */       return 10;
/*     */     }
/*  91 */     if (batchListSize <= 20 || requestedBatchSize <= 20)
/*     */     {
/*     */       
/*  94 */       return 20;
/*     */     }
/*  96 */     if (batchListSize <= 50) {
/*  97 */       return 50;
/*     */     }
/*  99 */     return requestedBatchSize;
/*     */   }
/*     */   
/*     */   public void refreshMany(Object parentBean, String propertyName) {
/* 103 */     refreshMany(parentBean, propertyName, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadMany(LoadManyRequest loadRequest) {
/* 108 */     List<BeanCollection<?>> batch = loadRequest.getBatch();
/*     */     
/* 110 */     int batchSize = getBatchSize(batch.size(), loadRequest.getBatchSize());
/*     */     
/* 112 */     LoadManyContext ctx = loadRequest.getLoadContext();
/* 113 */     BeanPropertyAssocMany<?> many = ctx.getBeanProperty();
/*     */     
/* 115 */     PersistenceContext pc = ctx.getPersistenceContext();
/*     */     
/* 117 */     ArrayList<Object> idList = new ArrayList(batchSize);
/*     */     
/* 119 */     for (int i = 0; i < batch.size(); i++) {
/* 120 */       BeanCollection<?> bc = batch.get(i);
/* 121 */       Object ownerBean = bc.getOwnerBean();
/* 122 */       Object id = many.getParentId(ownerBean);
/* 123 */       idList.add(id);
/*     */     } 
/* 125 */     int extraIds = batchSize - batch.size();
/* 126 */     if (extraIds > 0) {
/* 127 */       Object firstId = idList.get(0);
/* 128 */       for (int k = 0; k < extraIds; k++) {
/* 129 */         idList.add(firstId);
/*     */       }
/*     */     } 
/*     */     
/* 133 */     BeanDescriptor<?> desc = ctx.getBeanDescriptor();
/*     */     
/* 135 */     String idProperty = desc.getIdBinder().getIdProperty();
/*     */     
/* 137 */     SpiQuery<?> query = (SpiQuery)this.server.createQuery(desc.getBeanType());
/* 138 */     query.setMode(SpiQuery.Mode.LAZYLOAD_MANY);
/* 139 */     query.setLazyLoadManyPath(many.getName());
/* 140 */     query.setPersistenceContext(pc);
/* 141 */     query.select(idProperty);
/* 142 */     query.fetch(many.getName());
/*     */     
/* 144 */     if (idList.size() == 1) {
/* 145 */       query.where().idEq(idList.get(0));
/*     */     } else {
/* 147 */       query.where().idIn(idList);
/*     */     } 
/*     */     
/* 150 */     String mode = loadRequest.isLazy() ? "+lazy" : "+query";
/* 151 */     query.setLoadDescription(mode, loadRequest.getDescription());
/*     */ 
/*     */     
/* 154 */     ctx.configureQuery(query);
/*     */     
/* 156 */     if (loadRequest.isOnlyIds())
/*     */     {
/* 158 */       query.fetch(many.getName(), many.getTargetIdProperty());
/*     */     }
/*     */     
/* 161 */     this.server.findList((Query<?>)query, loadRequest.getTransaction());
/*     */ 
/*     */ 
/*     */     
/* 165 */     for (int j = 0; j < batch.size(); j++) {
/* 166 */       BeanCollection<?> bc = batch.get(j);
/* 167 */       if (bc.checkEmptyLazyLoad()) {
/* 168 */         if (logger.isLoggable(Level.FINE)) {
/* 169 */           logger.fine("BeanCollection after load was empty. Owner:" + ((BeanCollection)batch.get(j)).getOwnerBean());
/*     */         }
/* 171 */       } else if (loadRequest.isLoadCache()) {
/* 172 */         Object parentId = desc.getId(bc.getOwnerBean());
/* 173 */         desc.cachePutMany(many, bc, parentId);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadMany(BeanCollection<?> bc, LoadManyContext ctx, boolean onlyIds) {
/* 180 */     Object parentBean = bc.getOwnerBean();
/* 181 */     String propertyName = bc.getPropertyName();
/*     */     
/* 183 */     ObjectGraphNode node = (ctx == null) ? null : ctx.getObjectGraphNode();
/*     */     
/* 185 */     loadManyInternal(parentBean, propertyName, null, false, node, onlyIds);
/*     */     
/* 187 */     if (this.server.getAdminLogging().isDebugLazyLoad()) {
/*     */       
/* 189 */       Class<?> cls = parentBean.getClass();
/* 190 */       BeanDescriptor<?> desc = this.server.getBeanDescriptor(cls);
/* 191 */       BeanPropertyAssocMany<?> many = (BeanPropertyAssocMany)desc.getBeanProperty(propertyName);
/*     */       
/* 193 */       StackTraceElement cause = this.debugLazyLoad.getStackTraceElement(cls);
/*     */       
/* 195 */       String msg = "debug.lazyLoad " + many.getManyType() + " [" + desc + "][" + propertyName + "]";
/* 196 */       if (cause != null) {
/* 197 */         msg = msg + " at: " + cause;
/*     */       }
/* 199 */       System.err.println(msg);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void refreshMany(Object parentBean, String propertyName, Transaction t) {
/* 204 */     loadManyInternal(parentBean, propertyName, t, true, null, false);
/*     */   }
/*     */   
/*     */   private void loadManyInternal(Object parentBean, String propertyName, Transaction t, boolean refresh, ObjectGraphNode node, boolean onlyIds) {
/*     */     DefaultPersistenceContext defaultPersistenceContext;
/* 209 */     boolean vanilla = !(parentBean instanceof EntityBean);
/*     */     
/* 211 */     EntityBeanIntercept ebi = null;
/* 212 */     PersistenceContext pc = null;
/* 213 */     BeanCollection<?> beanCollection = null;
/* 214 */     ExpressionList<?> filterMany = null;
/*     */     
/* 216 */     if (!vanilla) {
/* 217 */       ebi = ((EntityBean)parentBean)._ebean_getIntercept();
/* 218 */       pc = ebi.getPersistenceContext();
/*     */     } 
/*     */     
/* 221 */     BeanDescriptor<?> parentDesc = this.server.getBeanDescriptor(parentBean.getClass());
/* 222 */     BeanPropertyAssocMany<?> many = (BeanPropertyAssocMany)parentDesc.getBeanProperty(propertyName);
/*     */     
/* 224 */     Object currentValue = many.getValueUnderlying(parentBean);
/* 225 */     if (currentValue instanceof BeanCollection) {
/* 226 */       beanCollection = (BeanCollection)currentValue;
/* 227 */       filterMany = beanCollection.getFilterMany();
/*     */     } 
/*     */     
/* 230 */     Object parentId = parentDesc.getId(parentBean);
/*     */     
/* 232 */     if (pc == null) {
/* 233 */       defaultPersistenceContext = new DefaultPersistenceContext();
/* 234 */       defaultPersistenceContext.put(parentId, parentBean);
/*     */     } 
/*     */     
/* 237 */     boolean useManyIdCache = (!vanilla && beanCollection != null && parentDesc.cacheIsUseManyId());
/* 238 */     if (useManyIdCache) {
/* 239 */       Boolean readOnly = null;
/* 240 */       if (ebi != null && ebi.isReadOnly()) {
/* 241 */         readOnly = Boolean.TRUE;
/*     */       }
/* 243 */       if (parentDesc.cacheLoadMany(many, beanCollection, parentId, readOnly, false)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 248 */     SpiQuery<?> query = (SpiQuery)this.server.createQuery(parentDesc.getBeanType());
/*     */     
/* 250 */     if (refresh) {
/*     */       
/* 252 */       Object emptyCollection = many.createEmpty(vanilla);
/* 253 */       many.setValue(parentBean, emptyCollection);
/* 254 */       query.setLoadDescription("+refresh", null);
/*     */     } else {
/* 256 */       query.setLoadDescription("+lazy", null);
/*     */     } 
/*     */     
/* 259 */     if (node != null)
/*     */     {
/* 261 */       query.setParentNode(node);
/*     */     }
/*     */     
/* 264 */     String idProperty = parentDesc.getIdBinder().getIdProperty();
/* 265 */     query.select(idProperty);
/*     */     
/* 267 */     if (onlyIds) {
/* 268 */       query.fetch(many.getName(), many.getTargetIdProperty());
/*     */     } else {
/* 270 */       query.fetch(many.getName());
/*     */     } 
/* 272 */     if (filterMany != null) {
/* 273 */       query.setFilterMany(many.getName(), filterMany);
/*     */     }
/*     */     
/* 276 */     query.where().idEq(parentId);
/* 277 */     query.setUseCache(false);
/* 278 */     query.setMode(SpiQuery.Mode.LAZYLOAD_MANY);
/* 279 */     query.setLazyLoadManyPath(many.getName());
/* 280 */     query.setPersistenceContext((PersistenceContext)defaultPersistenceContext);
/* 281 */     query.setVanillaMode(vanilla);
/*     */     
/* 283 */     if (ebi != null && 
/* 284 */       ebi.isReadOnly()) {
/* 285 */       query.setReadOnly(true);
/*     */     }
/*     */ 
/*     */     
/* 289 */     this.server.findUnique((Query<?>)query, t);
/*     */     
/* 291 */     if (beanCollection != null) {
/* 292 */       if (beanCollection.checkEmptyLazyLoad()) {
/* 293 */         if (logger.isLoggable(Level.FINE)) {
/* 294 */           logger.fine("BeanCollection after load was empty. Owner:" + beanCollection.getOwnerBean());
/*     */         }
/* 296 */       } else if (useManyIdCache) {
/* 297 */         parentDesc.cachePutMany(many, beanCollection, parentId);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadBean(LoadBeanRequest loadRequest) {
/* 308 */     List<EntityBeanIntercept> batch = loadRequest.getBatch();
/*     */     
/* 310 */     if (batch.isEmpty()) {
/* 311 */       throw new RuntimeException("Nothing in batch?");
/*     */     }
/*     */     
/* 314 */     int batchSize = getBatchSize(batch.size(), loadRequest.getBatchSize());
/*     */     
/* 316 */     LoadBeanContext ctx = loadRequest.getLoadContext();
/* 317 */     BeanDescriptor<?> desc = ctx.getBeanDescriptor();
/*     */     
/* 319 */     Class<?> beanType = desc.getBeanType();
/*     */     
/* 321 */     EntityBeanIntercept[] ebis = batch.<EntityBeanIntercept>toArray(new EntityBeanIntercept[batch.size()]);
/* 322 */     ArrayList<Object> idList = new ArrayList(batchSize);
/*     */     
/* 324 */     for (int i = 0; i < batch.size(); i++) {
/* 325 */       EntityBeanIntercept ebi = batch.get(i);
/* 326 */       Object bean = ebi.getOwner();
/* 327 */       Object id = desc.getId(bean);
/* 328 */       idList.add(id);
/*     */     } 
/*     */     
/* 331 */     if (idList.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 336 */     int extraIds = batchSize - batch.size();
/* 337 */     if (extraIds > 0) {
/*     */ 
/*     */       
/* 340 */       Object firstId = idList.get(0);
/* 341 */       for (int m = 0; m < extraIds; m++)
/*     */       {
/* 343 */         idList.add(firstId);
/*     */       }
/*     */     } 
/*     */     
/* 347 */     PersistenceContext persistenceContext = ctx.getPersistenceContext();
/*     */ 
/*     */     
/* 350 */     for (int j = 0; j < ebis.length; j++) {
/* 351 */       Object parentBean = ebis[j].getParentBean();
/* 352 */       if (parentBean != null) {
/*     */         
/* 354 */         BeanDescriptor<?> parentDesc = this.server.getBeanDescriptor(parentBean.getClass());
/* 355 */         Object parentId = parentDesc.getId(parentBean);
/* 356 */         persistenceContext.put(parentId, parentBean);
/*     */       } 
/*     */     } 
/*     */     
/* 360 */     SpiQuery<?> query = (SpiQuery)this.server.createQuery(beanType);
/*     */     
/* 362 */     query.setMode(SpiQuery.Mode.LAZYLOAD_BEAN);
/* 363 */     query.setPersistenceContext(persistenceContext);
/*     */     
/* 365 */     String mode = loadRequest.isLazy() ? "+lazy" : "+query";
/* 366 */     query.setLoadDescription(mode, loadRequest.getDescription());
/*     */     
/* 368 */     ctx.configureQuery(query, loadRequest.getLazyLoadProperty());
/*     */ 
/*     */ 
/*     */     
/* 372 */     if (idList.size() == 1) {
/* 373 */       query.where().idEq(idList.get(0));
/*     */     } else {
/* 375 */       query.where().idIn(idList);
/*     */     } 
/*     */     
/* 378 */     List<?> list = this.server.findList((Query<?>)query, loadRequest.getTransaction());
/*     */     
/* 380 */     if (loadRequest.isLoadCache()) {
/* 381 */       for (int m = 0; m < list.size(); m++) {
/* 382 */         desc.cachePutBeanData(list.get(m));
/*     */       }
/*     */     }
/*     */     
/* 386 */     for (int k = 0; k < ebis.length; k++) {
/* 387 */       if (ebis[k].isReference())
/*     */       {
/*     */         
/* 390 */         ebis[k].setLazyLoadFailure();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh(Object bean) {
/* 397 */     refreshBeanInternal(bean, SpiQuery.Mode.REFRESH_BEAN);
/*     */   }
/*     */   
/*     */   public void loadBean(EntityBeanIntercept ebi) {
/* 401 */     refreshBeanInternal(ebi.getOwner(), SpiQuery.Mode.LAZYLOAD_BEAN);
/*     */   }
/*     */   
/*     */   private void refreshBeanInternal(Object bean, SpiQuery.Mode mode) {
/*     */     DefaultPersistenceContext defaultPersistenceContext;
/* 406 */     boolean vanilla = !(bean instanceof EntityBean);
/*     */     
/* 408 */     EntityBeanIntercept ebi = null;
/* 409 */     PersistenceContext pc = null;
/*     */     
/* 411 */     if (!vanilla) {
/* 412 */       ebi = ((EntityBean)bean)._ebean_getIntercept();
/* 413 */       pc = ebi.getPersistenceContext();
/*     */     } 
/*     */     
/* 416 */     BeanDescriptor<?> desc = this.server.getBeanDescriptor(bean.getClass());
/* 417 */     Object id = desc.getId(bean);
/*     */     
/* 419 */     if (pc == null) {
/*     */       
/* 421 */       defaultPersistenceContext = new DefaultPersistenceContext();
/* 422 */       defaultPersistenceContext.put(id, bean);
/* 423 */       if (ebi != null) {
/* 424 */         ebi.setPersistenceContext((PersistenceContext)defaultPersistenceContext);
/*     */       }
/*     */     } 
/*     */     
/* 428 */     if (ebi != null) {
/* 429 */       if (SpiQuery.Mode.LAZYLOAD_BEAN.equals(mode) && desc.isBeanCaching())
/*     */       {
/* 431 */         if (desc.loadFromCache(bean, ebi, id)) {
/*     */           return;
/*     */         }
/*     */       }
/* 435 */       if (desc.lazyLoadMany(ebi)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 440 */     SpiQuery<?> query = (SpiQuery)this.server.createQuery(desc.getBeanType());
/* 441 */     if (ebi != null) {
/* 442 */       Object parentBean = ebi.getParentBean();
/* 443 */       if (parentBean != null) {
/*     */         
/* 445 */         BeanDescriptor<?> parentDesc = this.server.getBeanDescriptor(parentBean.getClass());
/* 446 */         Object parentId = parentDesc.getId(parentBean);
/* 447 */         defaultPersistenceContext.putIfAbsent(parentId, parentBean);
/*     */       } 
/*     */       
/* 450 */       query.setLazyLoadProperty(ebi.getLazyLoadProperty());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 456 */     query.setUsageProfiling(false);
/* 457 */     query.setPersistenceContext((PersistenceContext)defaultPersistenceContext);
/*     */     
/* 459 */     query.setMode(mode);
/* 460 */     query.setId(id);
/*     */     
/* 462 */     if (mode.equals(SpiQuery.Mode.REFRESH_BEAN)) {
/* 463 */       query.setUseCache(false);
/*     */     }
/* 465 */     query.setVanillaMode(vanilla);
/*     */     
/* 467 */     if (ebi != null && ebi.isReadOnly()) {
/* 468 */       query.setReadOnly(true);
/*     */     }
/*     */     
/* 471 */     Object dbBean = query.findUnique();
/* 472 */     if (dbBean == null) {
/* 473 */       String msg = "Bean not found during lazy load or refresh. id[" + id + "] type[" + desc.getBeanType() + "]";
/* 474 */       throw new EntityNotFoundException(msg);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\DefaultBeanLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */