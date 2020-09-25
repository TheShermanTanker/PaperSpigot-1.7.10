/*     */ package com.google.common.cache;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.ForwardingObject;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import javax.annotation.Nullable;
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
/*     */ public abstract class ForwardingCache<K, V>
/*     */   extends ForwardingObject
/*     */   implements Cache<K, V>
/*     */ {
/*     */   @Nullable
/*     */   public V get(@Nullable K key) throws ExecutionException {
/*  51 */     return delegate().get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public V getUnchecked(@Nullable K key) {
/*  57 */     return delegate().getUnchecked(key);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @Nullable
/*     */   public V apply(@Nullable K key) {
/*  64 */     return delegate().apply(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidate(@Nullable Object key) {
/*  69 */     delegate().invalidate(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidateAll() {
/*  74 */     delegate().invalidateAll();
/*     */   }
/*     */ 
/*     */   
/*     */   public long size() {
/*  79 */     return delegate().size();
/*     */   }
/*     */ 
/*     */   
/*     */   public CacheStats stats() {
/*  84 */     return delegate().stats();
/*     */   }
/*     */ 
/*     */   
/*     */   public ConcurrentMap<K, V> asMap() {
/*  89 */     return delegate().asMap();
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/*  94 */     delegate().cleanUp();
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract Cache<K, V> delegate();
/*     */ 
/*     */   
/*     */   @Beta
/*     */   public static abstract class SimpleForwardingCache<K, V>
/*     */     extends ForwardingCache<K, V>
/*     */   {
/*     */     private final Cache<K, V> delegate;
/*     */     
/*     */     protected SimpleForwardingCache(Cache<K, V> delegate) {
/* 108 */       this.delegate = (Cache<K, V>)Preconditions.checkNotNull(delegate);
/*     */     }
/*     */ 
/*     */     
/*     */     protected final Cache<K, V> delegate() {
/* 113 */       return this.delegate;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\cache\ForwardingCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */