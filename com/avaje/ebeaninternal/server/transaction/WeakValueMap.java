/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ public class WeakValueMap<K, V>
/*     */ {
/*  39 */   protected final ReferenceQueue<V> refQueue = new ReferenceQueue<V>();
/*     */ 
/*     */   
/*     */   private final Map<K, WeakReferenceWithKey<K, V>> backing;
/*     */ 
/*     */ 
/*     */   
/*     */   private static class WeakReferenceWithKey<K, V>
/*     */     extends WeakReference<V>
/*     */   {
/*     */     private final K key;
/*     */ 
/*     */ 
/*     */     
/*     */     public WeakReferenceWithKey(K key, V referent, ReferenceQueue<? super V> q) {
/*  54 */       super(referent, q);
/*  55 */       this.key = key;
/*     */     }
/*     */     
/*     */     public K getKey() {
/*  59 */       return this.key;
/*     */     }
/*     */   }
/*     */   
/*     */   public WeakValueMap() {
/*  64 */     this.backing = new HashMap<K, WeakReferenceWithKey<K, V>>();
/*     */   }
/*     */   
/*     */   private WeakReferenceWithKey<K, V> createReference(K key, V value) {
/*  68 */     return new WeakReferenceWithKey<K, V>(key, value, this.refQueue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void expunge() {
/*     */     Reference<? extends V> ref;
/*  76 */     while ((ref = this.refQueue.poll()) != null) {
/*  77 */       this.backing.remove(((WeakReferenceWithKey)ref).getKey());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object putIfAbsent(K key, V value) {
/*  86 */     expunge();
/*     */     
/*  88 */     Reference<V> ref = this.backing.get(key);
/*  89 */     if (ref != null) {
/*  90 */       V existingValue = ref.get();
/*  91 */       if (existingValue != null)
/*     */       {
/*  93 */         return existingValue;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  98 */     this.backing.put(key, createReference(key, value));
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   public void put(K key, V value) {
/* 103 */     expunge();
/*     */     
/* 105 */     this.backing.put(key, createReference(key, value));
/*     */   }
/*     */   
/*     */   public V get(K key) {
/* 109 */     expunge();
/*     */     
/* 111 */     Reference<V> v = this.backing.get(key);
/* 112 */     return (v == null) ? null : v.get();
/*     */   }
/*     */   
/*     */   public int size() {
/* 116 */     expunge();
/*     */     
/* 118 */     return this.backing.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 122 */     expunge();
/*     */     
/* 124 */     return this.backing.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 128 */     expunge();
/*     */     
/* 130 */     return this.backing.containsKey(key);
/*     */   }
/*     */   
/*     */   public V remove(K key) {
/* 134 */     expunge();
/*     */     
/* 136 */     Reference<V> v = this.backing.remove(key);
/* 137 */     return (v == null) ? null : v.get();
/*     */   }
/*     */   
/*     */   public void clear() {
/* 141 */     expunge();
/* 142 */     this.backing.clear();
/* 143 */     expunge();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 147 */     expunge();
/*     */     
/* 149 */     return this.backing.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\WeakValueMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */