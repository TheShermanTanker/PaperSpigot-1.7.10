/*     */ package net.minecraft.util.com.google.common.cache;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.collect.ForwardingObject;
/*     */ import net.minecraft.util.com.google.common.collect.ImmutableMap;
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
/*     */   public V getIfPresent(Object key) {
/*  54 */     return delegate().getIfPresent(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V get(K key, Callable<? extends V> valueLoader) throws ExecutionException {
/*  62 */     return delegate().get(key, valueLoader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
/*  70 */     return delegate().getAllPresent(keys);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(K key, V value) {
/*  78 */     delegate().put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends V> m) {
/*  86 */     delegate().putAll(m);
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidate(Object key) {
/*  91 */     delegate().invalidate(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidateAll(Iterable<?> keys) {
/*  99 */     delegate().invalidateAll(keys);
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidateAll() {
/* 104 */     delegate().invalidateAll();
/*     */   }
/*     */ 
/*     */   
/*     */   public long size() {
/* 109 */     return delegate().size();
/*     */   }
/*     */ 
/*     */   
/*     */   public CacheStats stats() {
/* 114 */     return delegate().stats();
/*     */   }
/*     */ 
/*     */   
/*     */   public ConcurrentMap<K, V> asMap() {
/* 119 */     return delegate().asMap();
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/* 124 */     delegate().cleanUp();
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
/* 138 */       this.delegate = (Cache<K, V>)Preconditions.checkNotNull(delegate);
/*     */     }
/*     */ 
/*     */     
/*     */     protected final Cache<K, V> delegate() {
/* 143 */       return this.delegate;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\cache\ForwardingCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */