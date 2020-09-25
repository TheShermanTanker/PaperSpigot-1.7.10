/*     */ package com.avaje.ebean.cache;
/*     */ 
/*     */ import com.avaje.ebean.annotation.CacheTuning;
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
/*     */ public class ServerCacheOptions
/*     */ {
/*     */   private int maxSize;
/*     */   private int maxIdleSecs;
/*     */   private int maxSecsToLive;
/*     */   
/*     */   public ServerCacheOptions() {}
/*     */   
/*     */   public ServerCacheOptions(CacheTuning cacheTuning) {
/*  44 */     this.maxSize = cacheTuning.maxSize();
/*  45 */     this.maxIdleSecs = cacheTuning.maxIdleSecs();
/*  46 */     this.maxSecsToLive = cacheTuning.maxSecsToLive();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerCacheOptions(ServerCacheOptions d) {
/*  53 */     this.maxSize = d.getMaxSize();
/*  54 */     this.maxIdleSecs = d.getMaxIdleSecs();
/*  55 */     this.maxSecsToLive = d.getMaxIdleSecs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyDefaults(ServerCacheOptions defaults) {
/*  63 */     if (this.maxSize == 0) {
/*  64 */       this.maxSize = defaults.getMaxSize();
/*     */     }
/*  66 */     if (this.maxIdleSecs == 0) {
/*  67 */       this.maxIdleSecs = defaults.getMaxIdleSecs();
/*     */     }
/*  69 */     if (this.maxSecsToLive == 0) {
/*  70 */       this.maxSecsToLive = defaults.getMaxSecsToLive();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerCacheOptions copy() {
/*  79 */     ServerCacheOptions copy = new ServerCacheOptions();
/*  80 */     copy.maxSize = this.maxSize;
/*  81 */     copy.maxIdleSecs = this.maxIdleSecs;
/*  82 */     copy.maxSecsToLive = this.maxSecsToLive;
/*     */     
/*  84 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxSize() {
/*  91 */     return this.maxSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxSize(int maxSize) {
/*  98 */     this.maxSize = maxSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxIdleSecs() {
/* 105 */     return this.maxIdleSecs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxIdleSecs(int maxIdleSecs) {
/* 112 */     this.maxIdleSecs = maxIdleSecs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxSecsToLive() {
/* 119 */     return this.maxSecsToLive;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxSecsToLive(int maxSecsToLive) {
/* 126 */     this.maxSecsToLive = maxSecsToLive;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\cache\ServerCacheOptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */