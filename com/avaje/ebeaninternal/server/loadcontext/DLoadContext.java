/*     */ package com.avaje.ebeaninternal.server.loadcontext;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.bean.ObjectGraphNode;
/*     */ import com.avaje.ebean.bean.ObjectGraphOrigin;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.api.LoadContext;
/*     */ import com.avaje.ebeaninternal.api.LoadSecondaryQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class DLoadContext
/*     */   implements LoadContext
/*     */ {
/*     */   private final SpiEbeanServer ebeanServer;
/*     */   private final BeanDescriptor<?> rootDescriptor;
/*  55 */   private final Map<String, DLoadBeanContext> beanMap = new HashMap<String, DLoadBeanContext>();
/*  56 */   private final Map<String, DLoadManyContext> manyMap = new HashMap<String, DLoadManyContext>();
/*     */   
/*     */   private final DLoadBeanContext rootBeanContext;
/*     */   
/*     */   private final Boolean readOnly;
/*     */   
/*     */   private final boolean excludeBeanCache;
/*     */   
/*     */   private final int defaultBatchSize;
/*     */   
/*     */   private final String relativePath;
/*     */   
/*     */   private final ObjectGraphOrigin origin;
/*     */   
/*     */   private final boolean useAutofetchManager;
/*     */   private final boolean hardRefs;
/*  72 */   private final Map<String, ObjectGraphNode> nodePathMap = new HashMap<String, ObjectGraphNode>();
/*     */   
/*     */   private PersistenceContext persistenceContext;
/*     */   private List<OrmQueryProperties> secQuery;
/*     */   
/*     */   public DLoadContext(SpiEbeanServer ebeanServer, BeanDescriptor<?> rootDescriptor, Boolean readOnly, SpiQuery<?> query) {
/*  78 */     this(ebeanServer, rootDescriptor, readOnly, Boolean.FALSE.equals(query.isUseBeanCache()), query.getParentNode(), (query.getAutoFetchManager() != null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DLoadContext(SpiEbeanServer ebeanServer, BeanDescriptor<?> rootDescriptor, Boolean readOnly, boolean excludeBeanCache, ObjectGraphNode parentNode, boolean useAutofetchManager) {
/*  87 */     this.ebeanServer = ebeanServer;
/*  88 */     this.hardRefs = GlobalProperties.getBoolean("ebean.hardrefs", false);
/*  89 */     this.defaultBatchSize = ebeanServer.getLazyLoadBatchSize();
/*  90 */     this.rootDescriptor = rootDescriptor;
/*  91 */     this.rootBeanContext = new DLoadBeanContext(this, rootDescriptor, null, this.defaultBatchSize, null, createBeanLoadList());
/*  92 */     this.readOnly = readOnly;
/*  93 */     this.excludeBeanCache = excludeBeanCache;
/*  94 */     this.useAutofetchManager = useAutofetchManager;
/*     */     
/*  96 */     if (parentNode != null) {
/*  97 */       this.origin = parentNode.getOriginQueryPoint();
/*  98 */       this.relativePath = parentNode.getPath();
/*     */     } else {
/* 100 */       this.origin = null;
/* 101 */       this.relativePath = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isExcludeBeanCache() {
/* 106 */     return this.excludeBeanCache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSecondaryQueriesMinBatchSize(OrmQueryRequest<?> parentRequest, int defaultQueryBatch) {
/* 114 */     if (this.secQuery == null) {
/* 115 */       return -1;
/*     */     }
/*     */     
/* 118 */     int maxBatch = 0;
/* 119 */     for (int i = 0; i < this.secQuery.size(); i++) {
/* 120 */       int batchSize = ((OrmQueryProperties)this.secQuery.get(i)).getQueryFetchBatch();
/* 121 */       if (batchSize == 0) {
/* 122 */         batchSize = defaultQueryBatch;
/*     */       }
/* 124 */       maxBatch = Math.max(maxBatch, batchSize);
/*     */     } 
/* 126 */     return maxBatch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeSecondaryQueries(OrmQueryRequest<?> parentRequest, int defaultQueryBatch) {
/* 134 */     if (this.secQuery != null) {
/* 135 */       for (int i = 0; i < this.secQuery.size(); i++) {
/* 136 */         OrmQueryProperties properties = this.secQuery.get(i);
/*     */         
/* 138 */         int batchSize = properties.getQueryFetchBatch();
/* 139 */         if (batchSize == 0) {
/* 140 */           batchSize = defaultQueryBatch;
/*     */         }
/* 142 */         LoadSecondaryQuery load = getLoadSecondaryQuery(properties.getPath());
/* 143 */         load.loadSecondaryQuery(parentRequest, batchSize, properties.isQueryFetchAll());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LoadSecondaryQuery getLoadSecondaryQuery(String path) {
/* 152 */     LoadSecondaryQuery beanLoad = (LoadSecondaryQuery)this.beanMap.get(path);
/* 153 */     if (beanLoad == null) {
/* 154 */       beanLoad = (LoadSecondaryQuery)this.manyMap.get(path);
/*     */     }
/* 156 */     return beanLoad;
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
/*     */   
/*     */   public void registerSecondaryQueries(SpiQuery<?> query) {
/* 171 */     this.secQuery = query.removeQueryJoins();
/* 172 */     if (this.secQuery != null) {
/* 173 */       for (int i = 0; i < this.secQuery.size(); i++) {
/* 174 */         OrmQueryProperties props = this.secQuery.get(i);
/* 175 */         registerSecondaryQuery(props);
/*     */       } 
/*     */     }
/*     */     
/* 179 */     List<OrmQueryProperties> lazyQueries = query.removeLazyJoins();
/* 180 */     if (lazyQueries != null) {
/* 181 */       for (int i = 0; i < lazyQueries.size(); i++) {
/* 182 */         OrmQueryProperties lazyProps = lazyQueries.get(i);
/* 183 */         registerSecondaryQuery(lazyProps);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerSecondaryQuery(OrmQueryProperties props) {
/* 194 */     String propName = props.getPath();
/* 195 */     ElPropertyValue elGetValue = this.rootDescriptor.getElGetValue(propName);
/*     */     
/* 197 */     boolean many = elGetValue.getBeanProperty().containsMany();
/* 198 */     registerSecondaryNode(many, props);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectGraphNode getObjectGraphNode(String path) {
/* 204 */     ObjectGraphNode node = this.nodePathMap.get(path);
/* 205 */     if (node == null) {
/* 206 */       node = createObjectGraphNode(path);
/* 207 */       this.nodePathMap.put(path, node);
/*     */     } 
/*     */     
/* 210 */     return node;
/*     */   }
/*     */ 
/*     */   
/*     */   private ObjectGraphNode createObjectGraphNode(String path) {
/* 215 */     if (this.relativePath != null) {
/* 216 */       if (path == null) {
/* 217 */         path = this.relativePath;
/*     */       } else {
/* 219 */         path = this.relativePath + "." + path;
/*     */       } 
/*     */     }
/* 222 */     return new ObjectGraphNode(this.origin, path);
/*     */   }
/*     */   
/*     */   public boolean isUseAutofetchManager() {
/* 226 */     return this.useAutofetchManager;
/*     */   }
/*     */   
/*     */   public String getRelativePath() {
/* 230 */     return this.relativePath;
/*     */   }
/*     */   
/*     */   protected SpiEbeanServer getEbeanServer() {
/* 234 */     return this.ebeanServer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Boolean isReadOnly() {
/* 242 */     return this.readOnly;
/*     */   }
/*     */   
/*     */   public PersistenceContext getPersistenceContext() {
/* 246 */     return this.persistenceContext;
/*     */   }
/*     */   
/*     */   public void setPersistenceContext(PersistenceContext persistenceContext) {
/* 250 */     this.persistenceContext = persistenceContext;
/*     */   }
/*     */   
/*     */   public void register(String path, EntityBeanIntercept ebi) {
/* 254 */     getBeanContext(path).register(ebi);
/*     */   }
/*     */   
/*     */   public void register(String path, BeanCollection<?> bc) {
/* 258 */     getManyContext(path).register(bc);
/*     */   }
/*     */   
/*     */   private DLoadBeanContext getBeanContext(String path) {
/* 262 */     if (path == null) {
/* 263 */       return this.rootBeanContext;
/*     */     }
/* 265 */     DLoadBeanContext beanContext = this.beanMap.get(path);
/* 266 */     if (beanContext == null) {
/* 267 */       beanContext = createBeanContext(path, this.defaultBatchSize, null);
/* 268 */       this.beanMap.put(path, beanContext);
/*     */     } 
/* 270 */     return beanContext;
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerSecondaryNode(boolean many, OrmQueryProperties props) {
/* 275 */     String path = props.getPath();
/* 276 */     int lazyJoinBatch = props.getLazyFetchBatch();
/* 277 */     int batchSize = (lazyJoinBatch > 0) ? lazyJoinBatch : this.defaultBatchSize;
/*     */     
/* 279 */     if (many) {
/* 280 */       DLoadManyContext manyContext = createManyContext(path, batchSize, props);
/* 281 */       this.manyMap.put(path, manyContext);
/*     */     } else {
/* 283 */       DLoadBeanContext beanContext = createBeanContext(path, batchSize, props);
/* 284 */       this.beanMap.put(path, beanContext);
/*     */     } 
/*     */   }
/*     */   
/*     */   private DLoadManyContext getManyContext(String path) {
/* 289 */     if (path == null) {
/* 290 */       throw new RuntimeException("path is null?");
/*     */     }
/* 292 */     DLoadManyContext ctx = this.manyMap.get(path);
/* 293 */     if (ctx == null) {
/* 294 */       ctx = createManyContext(path, this.defaultBatchSize, null);
/* 295 */       this.manyMap.put(path, ctx);
/*     */     } 
/* 297 */     return ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   private DLoadManyContext createManyContext(String path, int batchSize, OrmQueryProperties queryProps) {
/* 302 */     BeanPropertyAssocMany<?> p = (BeanPropertyAssocMany)getBeanProperty(this.rootDescriptor, path);
/*     */     
/* 304 */     return new DLoadManyContext(this, p, path, batchSize, queryProps, createBeanCollectionLoadList());
/*     */   }
/*     */ 
/*     */   
/*     */   private DLoadList<EntityBeanIntercept> createBeanLoadList() {
/* 309 */     if (this.hardRefs) {
/* 310 */       return new DLoadHardList<EntityBeanIntercept>();
/*     */     }
/* 312 */     return new DLoadWeakList<EntityBeanIntercept>();
/*     */   }
/*     */ 
/*     */   
/*     */   private DLoadList<BeanCollection<?>> createBeanCollectionLoadList() {
/* 317 */     if (this.hardRefs) {
/* 318 */       return new DLoadHardList<BeanCollection<?>>();
/*     */     }
/* 320 */     return new DLoadWeakList<BeanCollection<?>>();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private DLoadBeanContext createBeanContext(String path, int batchSize, OrmQueryProperties queryProps) {
/* 326 */     BeanPropertyAssoc<?> p = (BeanPropertyAssoc)getBeanProperty(this.rootDescriptor, path);
/* 327 */     BeanDescriptor<?> targetDescriptor = p.getTargetDescriptor();
/*     */     
/* 329 */     return new DLoadBeanContext(this, targetDescriptor, path, batchSize, queryProps, createBeanLoadList());
/*     */   }
/*     */ 
/*     */   
/*     */   private BeanProperty getBeanProperty(BeanDescriptor<?> desc, String path) {
/* 334 */     return desc.getBeanPropertyFromPath(path);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\loadcontext\DLoadContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */