/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
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
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ public class ImmutableSetMultimap<K, V>
/*     */   extends ImmutableMultimap<K, V>
/*     */   implements SetMultimap<K, V>
/*     */ {
/*     */   private final transient ImmutableSortedSet<V> emptySet;
/*     */   private transient ImmutableSet<Map.Entry<K, V>> entries;
/*     */   @GwtIncompatible("not needed in emulated source.")
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   public static <K, V> ImmutableSetMultimap<K, V> of() {
/*  66 */     return EmptyImmutableSetMultimap.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1) {
/*  73 */     Builder<K, V> builder = builder();
/*  74 */     builder.put(k1, v1);
/*  75 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1, K k2, V v2) {
/*  84 */     Builder<K, V> builder = builder();
/*  85 */     builder.put(k1, v1);
/*  86 */     builder.put(k2, v2);
/*  87 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
/*  97 */     Builder<K, V> builder = builder();
/*  98 */     builder.put(k1, v1);
/*  99 */     builder.put(k2, v2);
/* 100 */     builder.put(k3, v3);
/* 101 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
/* 111 */     Builder<K, V> builder = builder();
/* 112 */     builder.put(k1, v1);
/* 113 */     builder.put(k2, v2);
/* 114 */     builder.put(k3, v3);
/* 115 */     builder.put(k4, v4);
/* 116 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
/* 126 */     Builder<K, V> builder = builder();
/* 127 */     builder.put(k1, v1);
/* 128 */     builder.put(k2, v2);
/* 129 */     builder.put(k3, v3);
/* 130 */     builder.put(k4, v4);
/* 131 */     builder.put(k5, v5);
/* 132 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Builder<K, V> builder() {
/* 141 */     return new Builder<K, V>();
/*     */   }
/*     */   
/*     */   private static class BuilderMultimap<K, V>
/*     */     extends AbstractMultimap<K, V>
/*     */   {
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     BuilderMultimap() {
/* 150 */       super(new LinkedHashMap<K, Collection<V>>());
/*     */     }
/*     */     Collection<V> createCollection() {
/* 153 */       return Sets.newLinkedHashSet();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SortedKeyBuilderMultimap<K, V>
/*     */     extends AbstractMultimap<K, V>
/*     */   {
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */     
/*     */     SortedKeyBuilderMultimap(Comparator<? super K> keyComparator, Multimap<K, V> multimap) {
/* 166 */       super(new TreeMap<K, Collection<V>>(keyComparator));
/* 167 */       putAll(multimap);
/*     */     }
/*     */     Collection<V> createCollection() {
/* 170 */       return Sets.newLinkedHashSet();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder<K, V>
/*     */     extends ImmutableMultimap.Builder<K, V>
/*     */   {
/*     */     public Builder<K, V> put(K key, V value) {
/* 208 */       this.builderMultimap.put((K)Preconditions.checkNotNull(key), (V)Preconditions.checkNotNull(value));
/* 209 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<K, V> putAll(K key, Iterable<? extends V> values) {
/* 213 */       Collection<V> collection = this.builderMultimap.get((K)Preconditions.checkNotNull(key));
/* 214 */       for (V value : values) {
/* 215 */         collection.add((V)Preconditions.checkNotNull(value));
/*     */       }
/* 217 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<K, V> putAll(K key, V... values) {
/* 221 */       return putAll(key, Arrays.asList(values));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
/* 227 */       for (Map.Entry<? extends K, ? extends Collection<? extends V>> entry : (Iterable<Map.Entry<? extends K, ? extends Collection<? extends V>>>)multimap.asMap().entrySet()) {
/* 228 */         putAll(entry.getKey(), entry.getValue());
/*     */       }
/* 230 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Beta
/*     */     public Builder<K, V> orderKeysBy(Comparator<? super K> keyComparator) {
/* 240 */       this.builderMultimap = new ImmutableSetMultimap.SortedKeyBuilderMultimap<K, V>((Comparator<? super K>)Preconditions.checkNotNull(keyComparator), this.builderMultimap);
/*     */       
/* 242 */       return this;
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
/*     */ 
/*     */     
/*     */     @Beta
/*     */     public Builder<K, V> orderValuesBy(Comparator<? super V> valueComparator) {
/* 259 */       super.orderValuesBy(valueComparator);
/* 260 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ImmutableSetMultimap<K, V> build() {
/* 267 */       return ImmutableSetMultimap.copyOf(this.builderMultimap, this.valueComparator);
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
/*     */   
/*     */   public static <K, V> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
/* 287 */     return copyOf(multimap, (Comparator<? super V>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <K, V> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap, Comparator<? super V> valueComparator) {
/* 293 */     Preconditions.checkNotNull(multimap);
/* 294 */     if (multimap.isEmpty() && valueComparator == null) {
/* 295 */       return of();
/*     */     }
/*     */     
/* 298 */     if (multimap instanceof ImmutableSetMultimap) {
/*     */       
/* 300 */       ImmutableSetMultimap<K, V> kvMultimap = (ImmutableSetMultimap)multimap;
/*     */       
/* 302 */       if (!kvMultimap.isPartialView()) {
/* 303 */         return kvMultimap;
/*     */       }
/*     */     } 
/*     */     
/* 307 */     ImmutableMap.Builder<K, ImmutableSet<V>> builder = ImmutableMap.builder();
/* 308 */     int size = 0;
/*     */ 
/*     */     
/* 311 */     for (Map.Entry<? extends K, ? extends Collection<? extends V>> entry : (Iterable<Map.Entry<? extends K, ? extends Collection<? extends V>>>)multimap.asMap().entrySet()) {
/* 312 */       K key = entry.getKey();
/* 313 */       Collection<? extends V> values = entry.getValue();
/* 314 */       ImmutableSet<V> set = (valueComparator == null) ? ImmutableSet.<V>copyOf(values) : ImmutableSortedSet.<V>copyOf(valueComparator, values);
/*     */ 
/*     */       
/* 317 */       if (!set.isEmpty()) {
/* 318 */         builder.put(key, set);
/* 319 */         size += set.size();
/*     */       } 
/*     */     } 
/*     */     
/* 323 */     return new ImmutableSetMultimap<K, V>(builder.build(), size, valueComparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ImmutableSetMultimap(ImmutableMap<K, ImmutableSet<V>> map, int size, @Nullable Comparator<? super V> valueComparator) {
/* 332 */     super((ImmutableMap)map, size);
/* 333 */     this.emptySet = (valueComparator == null) ? null : ImmutableSortedSet.<V>emptySet(valueComparator);
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
/*     */   public ImmutableSet<V> get(@Nullable K key) {
/* 347 */     ImmutableSet<V> set = (ImmutableSet<V>)this.map.get(key);
/* 348 */     if (set != null)
/* 349 */       return set; 
/* 350 */     if (this.emptySet != null) {
/* 351 */       return this.emptySet;
/*     */     }
/* 353 */     return ImmutableSet.of();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSet<V> removeAll(Object key) {
/* 363 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSet<V> replaceValues(K key, Iterable<? extends V> values) {
/* 373 */     throw new UnsupportedOperationException();
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
/*     */   public ImmutableSet<Map.Entry<K, V>> entries() {
/* 385 */     ImmutableSet<Map.Entry<K, V>> result = this.entries;
/* 386 */     return (result == null) ? (this.entries = ImmutableSet.copyOf(super.entries())) : result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectOutputStream")
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 397 */     stream.defaultWriteObject();
/* 398 */     Serialization.writeMultimap(this, stream);
/*     */   }
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectInputStream")
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/*     */     ImmutableMap<Object, ImmutableSet<Object>> tmpMap;
/* 404 */     stream.defaultReadObject();
/* 405 */     int keyCount = stream.readInt();
/* 406 */     if (keyCount < 0) {
/* 407 */       throw new InvalidObjectException("Invalid key count " + keyCount);
/*     */     }
/* 409 */     ImmutableMap.Builder<Object, ImmutableSet<Object>> builder = ImmutableMap.builder();
/*     */     
/* 411 */     int tmpSize = 0;
/*     */     
/* 413 */     for (int i = 0; i < keyCount; i++) {
/* 414 */       Object key = stream.readObject();
/* 415 */       int valueCount = stream.readInt();
/* 416 */       if (valueCount <= 0) {
/* 417 */         throw new InvalidObjectException("Invalid value count " + valueCount);
/*     */       }
/*     */       
/* 420 */       Object[] array = new Object[valueCount];
/* 421 */       for (int j = 0; j < valueCount; j++) {
/* 422 */         array[j] = stream.readObject();
/*     */       }
/* 424 */       ImmutableSet<Object> valueSet = ImmutableSet.copyOf(array);
/* 425 */       if (valueSet.size() != array.length) {
/* 426 */         throw new InvalidObjectException("Duplicate key-value pairs exist for key " + key);
/*     */       }
/*     */       
/* 429 */       builder.put(key, valueSet);
/* 430 */       tmpSize += valueCount;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 435 */       tmpMap = builder.build();
/* 436 */     } catch (IllegalArgumentException e) {
/* 437 */       throw (InvalidObjectException)(new InvalidObjectException(e.getMessage())).initCause(e);
/*     */     } 
/*     */ 
/*     */     
/* 441 */     ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set(this, tmpMap);
/* 442 */     ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set(this, tmpSize);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ImmutableSetMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */