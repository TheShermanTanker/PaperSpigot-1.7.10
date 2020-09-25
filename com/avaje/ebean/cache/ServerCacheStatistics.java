/*     */ package com.avaje.ebean.cache;
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
/*     */ public class ServerCacheStatistics
/*     */ {
/*     */   protected String cacheName;
/*     */   protected int maxSize;
/*     */   protected int size;
/*     */   protected int hitCount;
/*     */   protected int missCount;
/*     */   
/*     */   public String toString() {
/*  44 */     StringBuilder sb = new StringBuilder();
/*  45 */     sb.append(this.cacheName);
/*  46 */     sb.append(" size:").append(this.size);
/*  47 */     sb.append(" hitRatio:").append(getHitRatio());
/*  48 */     sb.append(" hitCount:").append(this.hitCount);
/*  49 */     sb.append(" missCount:").append(this.missCount);
/*  50 */     sb.append(" maxSize:").append(this.maxSize);
/*  51 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCacheName() {
/*  58 */     return this.cacheName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCacheName(String cacheName) {
/*  65 */     this.cacheName = cacheName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHitCount() {
/*  72 */     return this.hitCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHitCount(int hitCount) {
/*  79 */     this.hitCount = hitCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMissCount() {
/*  86 */     return this.missCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMissCount(int missCount) {
/*  93 */     this.missCount = missCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 100 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSize(int size) {
/* 107 */     this.size = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxSize() {
/* 118 */     return this.maxSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxSize(int maxSize) {
/* 125 */     this.maxSize = maxSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHitRatio() {
/* 136 */     int totalCount = this.hitCount + this.missCount;
/* 137 */     if (totalCount == 0) {
/* 138 */       return 0;
/*     */     }
/* 140 */     return this.hitCount * 100 / totalCount;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\cache\ServerCacheStatistics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */