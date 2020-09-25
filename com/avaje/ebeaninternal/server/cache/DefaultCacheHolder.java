/*     */ package com.avaje.ebeaninternal.server.cache;
/*     */ 
/*     */ import com.avaje.ebean.annotation.CacheTuning;
/*     */ import com.avaje.ebean.cache.ServerCache;
/*     */ import com.avaje.ebean.cache.ServerCacheFactory;
/*     */ import com.avaje.ebean.cache.ServerCacheOptions;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultCacheHolder
/*     */ {
/*  17 */   private final ConcurrentHashMap<String, ServerCache> concMap = new ConcurrentHashMap<String, ServerCache>();
/*     */   
/*  19 */   private final HashMap<String, ServerCache> synchMap = new HashMap<String, ServerCache>();
/*     */   
/*  21 */   private final Object monitor = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ServerCacheFactory cacheFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ServerCacheOptions defaultOptions;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean useBeanTuning;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultCacheHolder(ServerCacheFactory cacheFactory, ServerCacheOptions defaultOptions, boolean useBeanTuning) {
/*  43 */     this.cacheFactory = cacheFactory;
/*  44 */     this.defaultOptions = defaultOptions;
/*  45 */     this.useBeanTuning = useBeanTuning;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerCacheOptions getDefaultOptions() {
/*  52 */     return this.defaultOptions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerCache getCache(String cacheKey) {
/*  60 */     ServerCache cache = this.concMap.get(cacheKey);
/*  61 */     if (cache != null) {
/*  62 */       return cache;
/*     */     }
/*  64 */     synchronized (this.monitor) {
/*  65 */       cache = this.synchMap.get(cacheKey);
/*  66 */       if (cache == null) {
/*  67 */         ServerCacheOptions options = getCacheOptions(cacheKey);
/*  68 */         cache = this.cacheFactory.createCache(cacheKey, options);
/*  69 */         this.synchMap.put(cacheKey, cache);
/*  70 */         this.concMap.put(cacheKey, cache);
/*     */       } 
/*  72 */       return cache;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearCache(String cacheKey) {
/*  78 */     ServerCache cache = this.concMap.get(cacheKey);
/*  79 */     if (cache != null) {
/*  80 */       cache.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCaching(String beanType) {
/*  88 */     return this.concMap.containsKey(beanType);
/*     */   }
/*     */   
/*     */   public void clearAll() {
/*  92 */     Iterator<ServerCache> it = this.concMap.values().iterator();
/*  93 */     while (it.hasNext()) {
/*  94 */       ServerCache serverCache = it.next();
/*  95 */       serverCache.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerCacheOptions getCacheOptions(String beanType) {
/* 104 */     if (this.useBeanTuning) {
/*     */       
/*     */       try {
/* 107 */         Class<?> cls = Class.forName(beanType);
/* 108 */         CacheTuning cacheTuning = cls.<CacheTuning>getAnnotation(CacheTuning.class);
/* 109 */         if (cacheTuning != null) {
/* 110 */           ServerCacheOptions o = new ServerCacheOptions(cacheTuning);
/* 111 */           o.applyDefaults(this.defaultOptions);
/* 112 */           return o;
/*     */         } 
/* 114 */       } catch (ClassNotFoundException e) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 119 */     return this.defaultOptions.copy();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cache\DefaultCacheHolder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */