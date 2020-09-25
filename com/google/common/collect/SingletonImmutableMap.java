/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ final class SingletonImmutableMap<K, V>
/*     */   extends ImmutableMap<K, V>
/*     */ {
/*     */   final transient K singleKey;
/*     */   final transient V singleValue;
/*     */   private transient Map.Entry<K, V> entry;
/*     */   private transient ImmutableSet<Map.Entry<K, V>> entrySet;
/*     */   private transient ImmutableSet<K> keySet;
/*     */   private transient ImmutableCollection<V> values;
/*     */   
/*     */   SingletonImmutableMap(K singleKey, V singleValue) {
/*  41 */     this.singleKey = singleKey;
/*  42 */     this.singleValue = singleValue;
/*     */   }
/*     */   
/*     */   SingletonImmutableMap(Map.Entry<K, V> entry) {
/*  46 */     this.entry = entry;
/*  47 */     this.singleKey = entry.getKey();
/*  48 */     this.singleValue = entry.getValue();
/*     */   }
/*     */   
/*     */   private Map.Entry<K, V> entry() {
/*  52 */     Map.Entry<K, V> e = this.entry;
/*  53 */     return (e == null) ? (this.entry = Maps.<K, V>immutableEntry(this.singleKey, this.singleValue)) : e;
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(@Nullable Object key) {
/*  58 */     return this.singleKey.equals(key) ? this.singleValue : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  63 */     return 1;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  67 */     return false;
/*     */   }
/*     */   
/*     */   public boolean containsKey(@Nullable Object key) {
/*  71 */     return this.singleKey.equals(key);
/*     */   }
/*     */   
/*     */   public boolean containsValue(@Nullable Object value) {
/*  75 */     return this.singleValue.equals(value);
/*     */   }
/*     */   
/*     */   boolean isPartialView() {
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSet<Map.Entry<K, V>> entrySet() {
/*  85 */     ImmutableSet<Map.Entry<K, V>> es = this.entrySet;
/*  86 */     return (es == null) ? (this.entrySet = ImmutableSet.of(entry())) : es;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSet<K> keySet() {
/*  92 */     ImmutableSet<K> ks = this.keySet;
/*  93 */     return (ks == null) ? (this.keySet = ImmutableSet.of(this.singleKey)) : ks;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableCollection<V> values() {
/*  99 */     ImmutableCollection<V> v = this.values;
/* 100 */     return (v == null) ? (this.values = new Values<V>(this.singleValue)) : v;
/*     */   }
/*     */   
/*     */   private static class Values<V>
/*     */     extends ImmutableCollection<V> {
/*     */     final V singleValue;
/*     */     
/*     */     Values(V singleValue) {
/* 108 */       this.singleValue = singleValue;
/*     */     }
/*     */     
/*     */     public boolean contains(Object object) {
/* 112 */       return this.singleValue.equals(object);
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 116 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 121 */       return 1;
/*     */     }
/*     */     
/*     */     public UnmodifiableIterator<V> iterator() {
/* 125 */       return Iterators.singletonIterator(this.singleValue);
/*     */     }
/*     */     
/*     */     boolean isPartialView() {
/* 129 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/* 134 */     if (object == this) {
/* 135 */       return true;
/*     */     }
/* 137 */     if (object instanceof Map) {
/* 138 */       Map<?, ?> that = (Map<?, ?>)object;
/* 139 */       if (that.size() != 1) {
/* 140 */         return false;
/*     */       }
/* 142 */       Map.Entry<?, ?> entry = that.entrySet().iterator().next();
/* 143 */       return (this.singleKey.equals(entry.getKey()) && this.singleValue.equals(entry.getValue()));
/*     */     } 
/*     */     
/* 146 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 150 */     return this.singleKey.hashCode() ^ this.singleValue.hashCode();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 154 */     return '{' + this.singleKey.toString() + '=' + this.singleValue.toString() + '}';
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\SingletonImmutableMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */