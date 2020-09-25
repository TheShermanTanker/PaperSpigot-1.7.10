/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
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
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ public abstract class ImmutableMap<K, V>
/*     */   implements Map<K, V>, Serializable
/*     */ {
/*     */   public static <K, V> ImmutableMap<K, V> of() {
/*  65 */     return EmptyImmutableMap.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableMap<K, V> of(K k1, V v1) {
/*  75 */     return new SingletonImmutableMap<K, V>((K)Preconditions.checkNotNull(k1), (V)Preconditions.checkNotNull(v1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2) {
/*  85 */     return new RegularImmutableMap<K, V>((Map.Entry<?, ?>[])new Map.Entry[] { entryOf(k1, v1), entryOf(k2, v2) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
/*  95 */     return new RegularImmutableMap<K, V>((Map.Entry<?, ?>[])new Map.Entry[] { entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
/* 106 */     return new RegularImmutableMap<K, V>((Map.Entry<?, ?>[])new Map.Entry[] { entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
/* 117 */     return new RegularImmutableMap<K, V>((Map.Entry<?, ?>[])new Map.Entry[] { entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4), entryOf(k5, v5) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Builder<K, V> builder() {
/* 128 */     return new Builder<K, V>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <K, V> Map.Entry<K, V> entryOf(K key, V value) {
/* 139 */     return Maps.immutableEntry((K)Preconditions.checkNotNull(key, "null key"), (V)Preconditions.checkNotNull(value, "null value"));
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
/*     */   public static class Builder<K, V>
/*     */   {
/* 165 */     final ArrayList<Map.Entry<K, V>> entries = Lists.newArrayList();
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
/*     */     public Builder<K, V> put(K key, V value) {
/* 178 */       this.entries.add(ImmutableMap.entryOf(key, value));
/* 179 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
/* 189 */       this.entries.ensureCapacity(this.entries.size() + map.size());
/* 190 */       for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
/* 191 */         put(entry.getKey(), entry.getValue());
/*     */       }
/* 193 */       return this;
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
/*     */     
/*     */     public ImmutableMap<K, V> build() {
/* 207 */       return fromEntryList(this.entries);
/*     */     }
/*     */ 
/*     */     
/*     */     private static <K, V> ImmutableMap<K, V> fromEntryList(List<Map.Entry<K, V>> entries) {
/* 212 */       int size = entries.size();
/* 213 */       switch (size) {
/*     */         case 0:
/* 215 */           return ImmutableMap.of();
/*     */         case 1:
/* 217 */           return new SingletonImmutableMap<K, V>(Iterables.<Map.Entry<K, V>>getOnlyElement(entries));
/*     */       } 
/* 219 */       Map.Entry[] arrayOfEntry = entries.<Map.Entry>toArray(new Map.Entry[entries.size()]);
/*     */       
/* 221 */       return new RegularImmutableMap<K, V>((Map.Entry<?, ?>[])arrayOfEntry);
/*     */     }
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
/*     */   public static <K, V> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
/* 240 */     if (map instanceof ImmutableMap && !(map instanceof ImmutableSortedMap)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 245 */       ImmutableMap<K, V> kvMap = (ImmutableMap)map;
/* 246 */       if (!kvMap.isPartialView()) {
/* 247 */         return kvMap;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 252 */     Map.Entry[] arrayOfEntry = (Map.Entry[])map.entrySet().toArray((Object[])new Map.Entry[0]);
/* 253 */     switch (arrayOfEntry.length) {
/*     */       case 0:
/* 255 */         return of();
/*     */       case 1:
/* 257 */         return new SingletonImmutableMap<K, V>(entryOf(arrayOfEntry[0].getKey(), arrayOfEntry[0].getValue()));
/*     */     } 
/*     */     
/* 260 */     for (int i = 0; i < arrayOfEntry.length; i++) {
/* 261 */       K k = arrayOfEntry[i].getKey();
/* 262 */       V v = arrayOfEntry[i].getValue();
/* 263 */       arrayOfEntry[i] = entryOf(k, v);
/*     */     } 
/* 265 */     return new RegularImmutableMap<K, V>((Map.Entry<?, ?>[])arrayOfEntry);
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
/*     */   public final V put(K k, V v) {
/* 278 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final V remove(Object o) {
/* 288 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void putAll(Map<? extends K, ? extends V> map) {
/* 298 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clear() {
/* 308 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 313 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(@Nullable Object key) {
/* 318 */     return (get(key) != null);
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
/*     */   public boolean equals(@Nullable Object object) {
/* 351 */     if (object == this) {
/* 352 */       return true;
/*     */     }
/* 354 */     if (object instanceof Map) {
/* 355 */       Map<?, ?> that = (Map<?, ?>)object;
/* 356 */       return entrySet().equals(that.entrySet());
/*     */     } 
/* 358 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 366 */     return entrySet().hashCode();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 370 */     return Maps.toStringImpl(this);
/*     */   }
/*     */ 
/*     */   
/*     */   static class SerializedForm
/*     */     implements Serializable
/*     */   {
/*     */     private final Object[] keys;
/*     */     private final Object[] values;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     SerializedForm(ImmutableMap<?, ?> map) {
/* 382 */       this.keys = new Object[map.size()];
/* 383 */       this.values = new Object[map.size()];
/* 384 */       int i = 0;
/* 385 */       for (Map.Entry<?, ?> entry : map.entrySet()) {
/* 386 */         this.keys[i] = entry.getKey();
/* 387 */         this.values[i] = entry.getValue();
/* 388 */         i++;
/*     */       } 
/*     */     }
/*     */     Object readResolve() {
/* 392 */       ImmutableMap.Builder<Object, Object> builder = new ImmutableMap.Builder<Object, Object>();
/* 393 */       return createMap(builder);
/*     */     }
/*     */     Object createMap(ImmutableMap.Builder<Object, Object> builder) {
/* 396 */       for (int i = 0; i < this.keys.length; i++) {
/* 397 */         builder.put(this.keys[i], this.values[i]);
/*     */       }
/* 399 */       return builder.build();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   Object writeReplace() {
/* 405 */     return new SerializedForm(this);
/*     */   }
/*     */   
/*     */   public abstract boolean containsValue(@Nullable Object paramObject);
/*     */   
/*     */   public abstract V get(@Nullable Object paramObject);
/*     */   
/*     */   public abstract ImmutableSet<Map.Entry<K, V>> entrySet();
/*     */   
/*     */   public abstract ImmutableSet<K> keySet();
/*     */   
/*     */   public abstract ImmutableCollection<V> values();
/*     */   
/*     */   abstract boolean isPartialView();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ImmutableMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */