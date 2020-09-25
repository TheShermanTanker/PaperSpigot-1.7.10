/*     */ package com.avaje.ebeaninternal.server.loadcontext;
/*     */ 
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.BeanLoader;
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.bean.ObjectGraphNode;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebeaninternal.api.LoadBeanContext;
/*     */ import com.avaje.ebeaninternal.api.LoadBeanRequest;
/*     */ import com.avaje.ebeaninternal.api.LoadContext;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class DLoadBeanContext
/*     */   implements LoadBeanContext, BeanLoader
/*     */ {
/*  46 */   private static final Logger logger = Logger.getLogger(DLoadBeanContext.class.getName());
/*     */ 
/*     */   
/*     */   protected final DLoadContext parent;
/*     */   
/*     */   protected final BeanDescriptor<?> desc;
/*     */   
/*     */   protected final String path;
/*     */   
/*     */   protected final String fullPath;
/*     */   
/*     */   private final DLoadList<EntityBeanIntercept> weakList;
/*     */   
/*     */   private final OrmQueryProperties queryProps;
/*     */   
/*     */   private int batchSize;
/*     */ 
/*     */   
/*     */   public DLoadBeanContext(DLoadContext parent, BeanDescriptor<?> desc, String path, int batchSize, OrmQueryProperties queryProps, DLoadList<EntityBeanIntercept> weakList) {
/*  65 */     this.parent = parent;
/*  66 */     this.desc = desc;
/*  67 */     this.path = path;
/*  68 */     this.batchSize = batchSize;
/*  69 */     this.queryProps = queryProps;
/*  70 */     this.weakList = weakList;
/*     */     
/*  72 */     if (parent.getRelativePath() == null) {
/*  73 */       this.fullPath = path;
/*     */     } else {
/*  75 */       this.fullPath = parent.getRelativePath() + "." + path;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void configureQuery(SpiQuery<?> query, String lazyLoadProperty) {
/*  82 */     if (this.parent.isReadOnly() != null) {
/*  83 */       query.setReadOnly(this.parent.isReadOnly().booleanValue());
/*     */     }
/*  85 */     query.setParentNode(getObjectGraphNode());
/*  86 */     query.setLazyLoadProperty(lazyLoadProperty);
/*     */     
/*  88 */     if (this.queryProps != null) {
/*  89 */       this.queryProps.configureBeanQuery(query);
/*     */     }
/*  91 */     if (this.parent.isUseAutofetchManager()) {
/*  92 */       query.setAutofetch(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getFullPath() {
/*  97 */     return this.fullPath;
/*     */   }
/*     */   
/*     */   public PersistenceContext getPersistenceContext() {
/* 101 */     return this.parent.getPersistenceContext();
/*     */   }
/*     */   
/*     */   public OrmQueryProperties getQueryProps() {
/* 105 */     return this.queryProps;
/*     */   }
/*     */   
/*     */   public ObjectGraphNode getObjectGraphNode() {
/* 109 */     return this.parent.getObjectGraphNode(this.path);
/*     */   }
/*     */   
/*     */   public String getPath() {
/* 113 */     return this.path;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 117 */     return this.parent.getEbeanServer().getName();
/*     */   }
/*     */   
/*     */   public int getBatchSize() {
/* 121 */     return this.batchSize;
/*     */   }
/*     */   
/*     */   public void setBatchSize(int batchSize) {
/* 125 */     this.batchSize = batchSize;
/*     */   }
/*     */   
/*     */   public BeanDescriptor<?> getBeanDescriptor() {
/* 129 */     return this.desc;
/*     */   }
/*     */   
/*     */   public LoadContext getGraphContext() {
/* 133 */     return this.parent;
/*     */   }
/*     */   
/*     */   public void register(EntityBeanIntercept ebi) {
/* 137 */     int pos = this.weakList.add(ebi);
/* 138 */     ebi.setBeanLoader(pos, this, this.parent.getPersistenceContext());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean loadBeanFromCache(EntityBeanIntercept ebi, int position) {
/* 146 */     if (!this.desc.loadFromCache(ebi)) {
/* 147 */       return false;
/*     */     }
/*     */     
/* 150 */     this.weakList.removeEntry(position);
/* 151 */     if (logger.isLoggable(Level.FINEST)) {
/* 152 */       logger.log(Level.FINEST, "Loading path:" + this.fullPath + " - bean loaded from L2 cache, position[" + position + "]");
/*     */     }
/* 154 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadBean(EntityBeanIntercept ebi) {
/* 164 */     if (this.desc.lazyLoadMany(ebi)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 169 */     int position = ebi.getBeanLoaderIndex();
/* 170 */     boolean hitCache = (!this.parent.isExcludeBeanCache() && this.desc.isBeanCaching());
/*     */     
/* 172 */     if (hitCache && loadBeanFromCache(ebi, position)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 178 */     List<EntityBeanIntercept> batch = null;
/*     */     try {
/* 180 */       batch = this.weakList.getLoadBatch(position, this.batchSize);
/* 181 */     } catch (IllegalStateException e) {
/* 182 */       logger.log(Level.SEVERE, "type[" + this.desc.getFullName() + "] fullPath[" + this.fullPath + "] batchSize[" + this.batchSize + "]", e);
/*     */     } 
/*     */     
/* 185 */     if (hitCache && this.batchSize > 1)
/*     */     {
/*     */       
/* 188 */       batch = loadBeanCheckBatch(batch);
/*     */     }
/*     */     
/* 191 */     if (logger.isLoggable(Level.FINER)) {
/* 192 */       for (int i = 0; i < batch.size(); i++) {
/*     */         
/* 194 */         EntityBeanIntercept entityBeanIntercept = batch.get(i);
/* 195 */         EntityBean owner = entityBeanIntercept.getOwner();
/* 196 */         Object id = this.desc.getId(owner);
/*     */         
/* 198 */         logger.finer("LoadBean type[" + owner.getClass().getName() + "] fullPath[" + this.fullPath + "] id[" + id + "] batchIndex[" + i + "] beanLoaderIndex[" + entityBeanIntercept.getBeanLoaderIndex() + "]");
/*     */       } 
/*     */     }
/*     */     
/* 202 */     LoadBeanRequest req = new LoadBeanRequest(this, batch, null, this.batchSize, true, ebi.getLazyLoadProperty(), hitCache);
/* 203 */     this.parent.getEbeanServer().loadBean(req);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<EntityBeanIntercept> loadBeanCheckBatch(List<EntityBeanIntercept> batch) {
/* 214 */     List<EntityBeanIntercept> actualLoadBatch = new ArrayList<EntityBeanIntercept>(this.batchSize);
/* 215 */     List<EntityBeanIntercept> batchToCheck = batch;
/*     */     
/* 217 */     int loadedFromCache = 0;
/*     */ 
/*     */     
/*     */     while (true) {
/* 221 */       for (int i = 0; i < batchToCheck.size(); i++) {
/* 222 */         if (!this.desc.loadFromCache(batchToCheck.get(i))) {
/* 223 */           actualLoadBatch.add(batchToCheck.get(i));
/*     */         } else {
/* 225 */           loadedFromCache++;
/* 226 */           if (logger.isLoggable(Level.FINEST)) {
/* 227 */             logger.log(Level.FINEST, "Loading path:" + this.fullPath + " - bean loaded from L2 cache(batch)");
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 232 */       if (batchToCheck.isEmpty()) {
/*     */         break;
/*     */       }
/*     */       
/* 236 */       int more = this.batchSize - actualLoadBatch.size();
/* 237 */       if (more <= 0 || loadedFromCache > 500) {
/*     */         break;
/*     */       }
/*     */       
/* 241 */       batchToCheck = this.weakList.getNextBatch(more);
/*     */     } 
/* 243 */     return actualLoadBatch;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSecondaryQuery(OrmQueryRequest<?> parentRequest, int requestedBatchSize, boolean all) {
/* 248 */     synchronized (this) {
/*     */       do {
/* 250 */         List<EntityBeanIntercept> batch = this.weakList.getNextBatch(requestedBatchSize);
/* 251 */         if (batch.size() == 0) {
/*     */           
/* 253 */           if (logger.isLoggable(Level.FINEST)) {
/* 254 */             logger.log(Level.FINEST, "Loading path:" + this.fullPath + " - no more beans to load");
/*     */           }
/*     */           return;
/*     */         } 
/* 258 */         boolean loadCache = false;
/* 259 */         LoadBeanRequest req = new LoadBeanRequest(this, batch, (Transaction)parentRequest.getTransaction(), requestedBatchSize, false, null, loadCache);
/*     */         
/* 261 */         if (logger.isLoggable(Level.FINEST)) {
/* 262 */           logger.log(Level.FINEST, "Loading path:" + this.fullPath + " - secondary query batch load [" + batch.size() + "] beans");
/*     */         }
/*     */         
/* 265 */         this.parent.getEbeanServer().loadBean(req);
/* 266 */       } while (all);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\loadcontext\DLoadBeanContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */