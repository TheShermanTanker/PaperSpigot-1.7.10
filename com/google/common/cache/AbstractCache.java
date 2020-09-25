/*     */ package com.google.common.cache;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.util.concurrent.UncheckedExecutionException;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.atomic.AtomicLong;
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
/*     */ @Beta
/*     */ public abstract class AbstractCache<K, V>
/*     */   implements Cache<K, V>
/*     */ {
/*     */   public V getUnchecked(K key) {
/*     */     try {
/*  49 */       return get(key);
/*  50 */     } catch (ExecutionException e) {
/*  51 */       throw new UncheckedExecutionException(e.getCause());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final V apply(K key) {
/*  57 */     return getUnchecked(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {}
/*     */ 
/*     */   
/*     */   public long size() {
/*  65 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidate(Object key) {
/*  70 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidateAll() {
/*  75 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public CacheStats stats() {
/*  80 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ConcurrentMap<K, V> asMap() {
/*  85 */     throw new UnsupportedOperationException();
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
/*     */   @Beta
/*     */   public static class SimpleStatsCounter
/*     */     implements StatsCounter
/*     */   {
/* 153 */     private final AtomicLong hitCount = new AtomicLong();
/* 154 */     private final AtomicLong missCount = new AtomicLong();
/* 155 */     private final AtomicLong loadSuccessCount = new AtomicLong();
/* 156 */     private final AtomicLong loadExceptionCount = new AtomicLong();
/* 157 */     private final AtomicLong totalLoadTime = new AtomicLong();
/* 158 */     private final AtomicLong evictionCount = new AtomicLong();
/*     */ 
/*     */     
/*     */     public void recordHit() {
/* 162 */       this.hitCount.incrementAndGet();
/*     */     }
/*     */ 
/*     */     
/*     */     public void recordLoadSuccess(long loadTime) {
/* 167 */       this.missCount.incrementAndGet();
/* 168 */       this.loadSuccessCount.incrementAndGet();
/* 169 */       this.totalLoadTime.addAndGet(loadTime);
/*     */     }
/*     */ 
/*     */     
/*     */     public void recordLoadException(long loadTime) {
/* 174 */       this.missCount.incrementAndGet();
/* 175 */       this.loadExceptionCount.incrementAndGet();
/* 176 */       this.totalLoadTime.addAndGet(loadTime);
/*     */     }
/*     */ 
/*     */     
/*     */     public void recordConcurrentMiss() {
/* 181 */       this.missCount.incrementAndGet();
/*     */     }
/*     */ 
/*     */     
/*     */     public void recordEviction() {
/* 186 */       this.evictionCount.incrementAndGet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CacheStats snapshot() {
/* 191 */       return new CacheStats(this.hitCount.get(), this.missCount.get(), this.loadSuccessCount.get(), this.loadExceptionCount.get(), this.totalLoadTime.get(), this.evictionCount.get());
/*     */     }
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
/*     */     public void incrementBy(AbstractCache.StatsCounter other) {
/* 204 */       CacheStats otherStats = other.snapshot();
/* 205 */       this.hitCount.addAndGet(otherStats.hitCount());
/* 206 */       this.missCount.addAndGet(otherStats.missCount());
/* 207 */       this.loadSuccessCount.addAndGet(otherStats.loadSuccessCount());
/* 208 */       this.loadExceptionCount.addAndGet(otherStats.loadExceptionCount());
/* 209 */       this.totalLoadTime.addAndGet(otherStats.totalLoadTime());
/* 210 */       this.evictionCount.addAndGet(otherStats.evictionCount());
/*     */     }
/*     */   }
/*     */   
/*     */   @Beta
/*     */   public static interface StatsCounter {
/*     */     void recordHit();
/*     */     
/*     */     void recordLoadSuccess(long param1Long);
/*     */     
/*     */     void recordLoadException(long param1Long);
/*     */     
/*     */     void recordConcurrentMiss();
/*     */     
/*     */     void recordEviction();
/*     */     
/*     */     CacheStats snapshot();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\cache\AbstractCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */