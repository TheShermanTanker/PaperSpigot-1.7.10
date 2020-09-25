/*     */ package com.avaje.ebeaninternal.server.loadcontext;
/*     */ 
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebean.bean.ObjectGraphNode;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebeaninternal.api.LoadManyContext;
/*     */ import com.avaje.ebeaninternal.api.LoadManyRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
/*     */ import java.util.List;
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
/*     */ public class DLoadManyContext
/*     */   implements LoadManyContext, BeanCollectionLoader
/*     */ {
/*     */   protected final DLoadContext parent;
/*     */   protected final String fullPath;
/*     */   private final BeanDescriptor<?> desc;
/*     */   private final BeanPropertyAssocMany<?> property;
/*     */   private final String path;
/*     */   private final int batchSize;
/*     */   private final OrmQueryProperties queryProps;
/*     */   private final DLoadList<BeanCollection<?>> weakList;
/*     */   
/*     */   public DLoadManyContext(DLoadContext parent, BeanPropertyAssocMany<?> p, String path, int batchSize, OrmQueryProperties queryProps, DLoadList<BeanCollection<?>> weakList) {
/*  57 */     this.parent = parent;
/*  58 */     this.property = p;
/*  59 */     this.desc = p.getBeanDescriptor();
/*  60 */     this.path = path;
/*  61 */     this.batchSize = batchSize;
/*  62 */     this.queryProps = queryProps;
/*  63 */     this.weakList = weakList;
/*     */     
/*  65 */     if (parent.getRelativePath() == null) {
/*  66 */       this.fullPath = path;
/*     */     } else {
/*  68 */       this.fullPath = parent.getRelativePath() + "." + path;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configureQuery(SpiQuery<?> query) {
/*  76 */     if (this.parent.isReadOnly() != null) {
/*  77 */       query.setReadOnly(this.parent.isReadOnly().booleanValue());
/*     */     }
/*  79 */     query.setParentNode(getObjectGraphNode());
/*     */     
/*  81 */     if (this.queryProps != null) {
/*  82 */       this.queryProps.configureManyQuery(query);
/*     */     }
/*     */     
/*  85 */     if (this.parent.isUseAutofetchManager()) {
/*  86 */       query.setAutofetch(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectGraphNode getObjectGraphNode() {
/*  95 */     int pos = this.path.lastIndexOf('.');
/*  96 */     if (pos == -1) {
/*  97 */       return this.parent.getObjectGraphNode(null);
/*     */     }
/*  99 */     String parentPath = this.path.substring(0, pos);
/* 100 */     return this.parent.getObjectGraphNode(parentPath);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFullPath() {
/* 105 */     return this.fullPath;
/*     */   }
/*     */   
/*     */   public PersistenceContext getPersistenceContext() {
/* 109 */     return this.parent.getPersistenceContext();
/*     */   }
/*     */   
/*     */   public int getBatchSize() {
/* 113 */     return this.batchSize;
/*     */   }
/*     */   
/*     */   public BeanPropertyAssocMany<?> getBeanProperty() {
/* 117 */     return this.property;
/*     */   }
/*     */   
/*     */   public BeanDescriptor<?> getBeanDescriptor() {
/* 121 */     return this.desc;
/*     */   }
/*     */   
/*     */   public String getPath() {
/* 125 */     return this.path;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 129 */     return this.parent.getEbeanServer().getName();
/*     */   }
/*     */   
/*     */   public void register(BeanCollection<?> bc) {
/* 133 */     int pos = this.weakList.add(bc);
/* 134 */     bc.setLoader(pos, this);
/*     */   }
/*     */   
/*     */   public void loadMany(BeanCollection<?> bc, boolean onlyIds) {
/*     */     LoadManyRequest req;
/* 139 */     int position = bc.getLoaderIndex();
/*     */ 
/*     */     
/* 142 */     synchronized (this.weakList) {
/* 143 */       boolean hitCache = (this.desc.isBeanCaching() && !onlyIds && !this.parent.isExcludeBeanCache());
/* 144 */       if (hitCache) {
/* 145 */         Object ownerBean = bc.getOwnerBean();
/* 146 */         BeanDescriptor<? extends Object> parentDesc = this.desc.getBeanDescriptor(ownerBean.getClass());
/* 147 */         Object parentId = parentDesc.getId(ownerBean);
/* 148 */         if (parentDesc.cacheLoadMany(this.property, bc, parentId, this.parent.isReadOnly(), false)) {
/*     */           
/* 150 */           this.weakList.removeEntry(position);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 155 */       List<BeanCollection<?>> loadBatch = this.weakList.getLoadBatch(position, this.batchSize);
/* 156 */       req = new LoadManyRequest(this, loadBatch, null, this.batchSize, true, onlyIds, hitCache);
/*     */     } 
/* 158 */     this.parent.getEbeanServer().loadMany(req);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSecondaryQuery(OrmQueryRequest<?> parentRequest, int requestedBatchSize, boolean all) {
/*     */     do {
/*     */       LoadManyRequest req;
/* 165 */       synchronized (this.weakList) {
/* 166 */         List<BeanCollection<?>> batch = this.weakList.getNextBatch(requestedBatchSize);
/* 167 */         if (batch.size() == 0) {
/*     */           return;
/*     */         }
/* 170 */         req = new LoadManyRequest(this, batch, (Transaction)parentRequest.getTransaction(), requestedBatchSize, false, false, false);
/*     */       } 
/* 172 */       this.parent.getEbeanServer().loadMany(req);
/* 173 */     } while (all);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\loadcontext\DLoadManyContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */