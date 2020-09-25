/*     */ package com.avaje.ebeaninternal.server.cache;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.cache.ServerCache;
/*     */ import com.avaje.ebean.cache.ServerCacheFactory;
/*     */ import com.avaje.ebean.cache.ServerCacheManager;
/*     */ import com.avaje.ebean.cache.ServerCacheOptions;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
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
/*     */ public class DefaultServerCacheManager
/*     */   implements ServerCacheManager
/*     */ {
/*     */   private final DefaultCacheHolder beanCache;
/*     */   private final DefaultCacheHolder queryCache;
/*     */   private final DefaultCacheHolder naturalKeyCache;
/*     */   private final DefaultCacheHolder collectionIdsCache;
/*     */   private final ServerCacheFactory cacheFactory;
/*     */   private SpiEbeanServer ebeanServer;
/*     */   
/*     */   public DefaultServerCacheManager(ServerCacheFactory cacheFactory, ServerCacheOptions defaultBeanOptions, ServerCacheOptions defaultQueryOptions) {
/*  32 */     this.cacheFactory = cacheFactory;
/*  33 */     this.beanCache = new DefaultCacheHolder(cacheFactory, defaultBeanOptions, true);
/*  34 */     this.queryCache = new DefaultCacheHolder(cacheFactory, defaultQueryOptions, false);
/*  35 */     this.naturalKeyCache = new DefaultCacheHolder(cacheFactory, defaultQueryOptions, false);
/*  36 */     this.collectionIdsCache = new DefaultCacheHolder(cacheFactory, defaultQueryOptions, false);
/*     */   }
/*     */   
/*     */   public void init(EbeanServer server) {
/*  40 */     this.cacheFactory.init(server);
/*  41 */     this.ebeanServer = (SpiEbeanServer)server;
/*     */   }
/*     */   
/*     */   public void setCaching(Class<?> beanType, boolean useCache) {
/*  45 */     this.ebeanServer.getBeanDescriptor(beanType).getCacheOptions().setUseCache(useCache);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear(Class<?> beanType) {
/*  53 */     String beanName = beanType.getName();
/*  54 */     this.beanCache.clearCache(beanName);
/*  55 */     this.naturalKeyCache.clearCache(beanName);
/*  56 */     this.collectionIdsCache.clearCache(beanName);
/*  57 */     this.queryCache.clearCache(beanName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearAll() {
/*  62 */     this.beanCache.clearAll();
/*  63 */     this.queryCache.clearAll();
/*  64 */     this.naturalKeyCache.clearAll();
/*  65 */     this.collectionIdsCache.clearAll();
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerCache getCollectionIdsCache(Class<?> beanType, String propertyName) {
/*  70 */     return this.collectionIdsCache.getCache(beanType.getName() + "." + propertyName);
/*     */   }
/*     */   
/*     */   public boolean isCollectionIdsCaching(Class<?> beanType) {
/*  74 */     return this.collectionIdsCache.isCaching(beanType.getName());
/*     */   }
/*     */   
/*     */   public ServerCache getNaturalKeyCache(Class<?> beanType) {
/*  78 */     return this.naturalKeyCache.getCache(beanType.getName());
/*     */   }
/*     */   
/*     */   public boolean isNaturalKeyCaching(Class<?> beanType) {
/*  82 */     return this.naturalKeyCache.isCaching(beanType.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerCache getQueryCache(Class<?> beanType) {
/*  89 */     return this.queryCache.getCache(beanType.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerCache getBeanCache(Class<?> beanType) {
/*  96 */     return this.beanCache.getCache(beanType.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBeanCaching(Class<?> beanType) {
/* 103 */     return this.beanCache.isCaching(beanType.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isQueryCaching(Class<?> beanType) {
/* 108 */     return this.queryCache.isCaching(beanType.getName());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cache\DefaultServerCacheManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */