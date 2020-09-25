/*    */ package com.google.common.cache;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.google.common.base.Supplier;
/*    */ import java.io.Serializable;
/*    */ import java.util.concurrent.ConcurrentMap;
/*    */ import java.util.concurrent.ExecutionException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ComputingCache<K, V>
/*    */   extends AbstractCache<K, V>
/*    */   implements Serializable
/*    */ {
/*    */   final CustomConcurrentHashMap<K, V> map;
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   ComputingCache(CacheBuilder<? super K, ? super V> builder, Supplier<? extends AbstractCache.StatsCounter> statsCounterSupplier, CacheLoader<? super K, V> loader) {
/* 39 */     this.map = new CustomConcurrentHashMap<K, V>(builder, statsCounterSupplier, loader);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public V get(K key) throws ExecutionException {
/* 46 */     return this.map.getOrCompute(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public void invalidate(Object key) {
/* 51 */     Preconditions.checkNotNull(key);
/* 52 */     this.map.remove(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public void invalidateAll() {
/* 57 */     this.map.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public long size() {
/* 62 */     return this.map.longSize();
/*    */   }
/*    */ 
/*    */   
/*    */   public ConcurrentMap<K, V> asMap() {
/* 67 */     return this.map;
/*    */   }
/*    */ 
/*    */   
/*    */   public CacheStats stats() {
/* 72 */     AbstractCache.SimpleStatsCounter aggregator = new AbstractCache.SimpleStatsCounter();
/* 73 */     for (CustomConcurrentHashMap.Segment<K, V> segment : this.map.segments) {
/* 74 */       aggregator.incrementBy(segment.statsCounter);
/*    */     }
/* 76 */     return aggregator.snapshot();
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 81 */     this.map.cleanUp();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   Object writeReplace() {
/* 89 */     return this.map.cacheSerializationProxy();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\cache\ComputingCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */