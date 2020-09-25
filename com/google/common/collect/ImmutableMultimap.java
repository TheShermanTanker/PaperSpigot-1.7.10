/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
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
/*     */ @GwtCompatible(emulated = true)
/*     */ public abstract class ImmutableMultimap<K, V>
/*     */   implements Multimap<K, V>, Serializable
/*     */ {
/*     */   final transient ImmutableMap<K, ? extends ImmutableCollection<V>> map;
/*     */   final transient int size;
/*     */   private transient ImmutableCollection<Map.Entry<K, V>> entries;
/*     */   private transient ImmutableMultiset<K> keys;
/*     */   private transient ImmutableCollection<V> values;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   public static <K, V> ImmutableMultimap<K, V> of() {
/*  62 */     return ImmutableListMultimap.of();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableMultimap<K, V> of(K k1, V v1) {
/*  69 */     return ImmutableListMultimap.of(k1, v1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableMultimap<K, V> of(K k1, V v1, K k2, V v2) {
/*  76 */     return ImmutableListMultimap.of(k1, v1, k2, v2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
/*  84 */     return ImmutableListMultimap.of(k1, v1, k2, v2, k3, v3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
/*  92 */     return ImmutableListMultimap.of(k1, v1, k2, v2, k3, v3, k4, v4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
/* 100 */     return ImmutableListMultimap.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Builder<K, V> builder() {
/* 110 */     return new Builder<K, V>();
/*     */   }
/*     */ 
/*     */   
/*     */   private static class BuilderMultimap<K, V>
/*     */     extends AbstractMultimap<K, V>
/*     */   {
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     BuilderMultimap() {
/* 120 */       super(new LinkedHashMap<K, Collection<V>>());
/*     */     }
/*     */     Collection<V> createCollection() {
/* 123 */       return Lists.newArrayList();
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
/* 136 */       super(new TreeMap<K, Collection<V>>(keyComparator));
/* 137 */       putAll(multimap);
/*     */     }
/*     */     Collection<V> createCollection() {
/* 140 */       return Lists.newArrayList();
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
/*     */   public static class Builder<K, V>
/*     */   {
/* 164 */     Multimap<K, V> builderMultimap = new ImmutableMultimap.BuilderMultimap<K, V>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Comparator<? super V> valueComparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> put(K key, V value) {
/* 177 */       this.builderMultimap.put((K)Preconditions.checkNotNull(key), (V)Preconditions.checkNotNull(value));
/* 178 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> putAll(K key, Iterable<? extends V> values) {
/* 189 */       Collection<V> valueList = this.builderMultimap.get((K)Preconditions.checkNotNull(key));
/* 190 */       for (V value : values) {
/* 191 */         valueList.add((V)Preconditions.checkNotNull(value));
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
/*     */     public Builder<K, V> putAll(K key, V... values) {
/* 203 */       return putAll(key, Arrays.asList(values));
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
/*     */     public Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
/* 217 */       for (Map.Entry<? extends K, ? extends Collection<? extends V>> entry : (Iterable<Map.Entry<? extends K, ? extends Collection<? extends V>>>)multimap.asMap().entrySet()) {
/* 218 */         putAll(entry.getKey(), entry.getValue());
/*     */       }
/* 220 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Beta
/*     */     public Builder<K, V> orderKeysBy(Comparator<? super K> keyComparator) {
/* 230 */       this.builderMultimap = new ImmutableMultimap.SortedKeyBuilderMultimap<K, V>((Comparator<? super K>)Preconditions.checkNotNull(keyComparator), this.builderMultimap);
/*     */       
/* 232 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Beta
/*     */     public Builder<K, V> orderValuesBy(Comparator<? super V> valueComparator) {
/* 242 */       this.valueComparator = (Comparator<? super V>)Preconditions.checkNotNull(valueComparator);
/* 243 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ImmutableMultimap<K, V> build() {
/* 250 */       if (this.valueComparator != null) {
/* 251 */         for (Collection<V> values : (Iterable<Collection<V>>)this.builderMultimap.asMap().values()) {
/* 252 */           List<V> list = (List<V>)values;
/* 253 */           Collections.sort(list, this.valueComparator);
/*     */         } 
/*     */       }
/* 256 */       return ImmutableMultimap.copyOf(this.builderMultimap);
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
/*     */   public static <K, V> ImmutableMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
/* 274 */     if (multimap instanceof ImmutableMultimap) {
/*     */       
/* 276 */       ImmutableMultimap<K, V> kvMultimap = (ImmutableMultimap)multimap;
/*     */       
/* 278 */       if (!kvMultimap.isPartialView()) {
/* 279 */         return kvMultimap;
/*     */       }
/*     */     } 
/* 282 */     return ImmutableListMultimap.copyOf(multimap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java serialization is not supported")
/*     */   static class FieldSettersHolder
/*     */   {
/* 294 */     static final Serialization.FieldSetter<ImmutableMultimap> MAP_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "map");
/*     */ 
/*     */     
/* 297 */     static final Serialization.FieldSetter<ImmutableMultimap> SIZE_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "size");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   ImmutableMultimap(ImmutableMap<K, ? extends ImmutableCollection<V>> map, int size) {
/* 303 */     this.map = map;
/* 304 */     this.size = size;
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
/*     */   public ImmutableCollection<V> removeAll(Object key) {
/* 316 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableCollection<V> replaceValues(K key, Iterable<? extends V> values) {
/* 327 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 337 */     throw new UnsupportedOperationException();
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
/*     */   public boolean put(K key, V value) {
/* 356 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean putAll(K key, Iterable<? extends V> values) {
/* 366 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
/* 376 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Object key, Object value) {
/* 386 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   boolean isPartialView() {
/* 390 */     return this.map.isPartialView();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsEntry(@Nullable Object key, @Nullable Object value) {
/* 397 */     Collection<V> values = this.map.get(key);
/* 398 */     return (values != null && values.contains(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(@Nullable Object key) {
/* 403 */     return this.map.containsKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(@Nullable Object value) {
/* 408 */     for (Collection<V> valueCollection : this.map.values()) {
/* 409 */       if (valueCollection.contains(value)) {
/* 410 */         return true;
/*     */       }
/*     */     } 
/* 413 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 418 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 423 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/* 427 */     if (object instanceof Multimap) {
/* 428 */       Multimap<?, ?> that = (Multimap<?, ?>)object;
/* 429 */       return this.map.equals(that.asMap());
/*     */     } 
/* 431 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 435 */     return this.map.hashCode();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 439 */     return this.map.toString();
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
/*     */   public ImmutableSet<K> keySet() {
/* 451 */     return this.map.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableMap<K, Collection<V>> asMap() {
/* 461 */     return (ImmutableMap)this.map;
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
/*     */   public ImmutableCollection<Map.Entry<K, V>> entries() {
/* 473 */     ImmutableCollection<Map.Entry<K, V>> result = this.entries;
/* 474 */     return (result == null) ? (this.entries = new EntryCollection<K, V>(this)) : result;
/*     */   }
/*     */   
/*     */   private static class EntryCollection<K, V>
/*     */     extends ImmutableCollection<Map.Entry<K, V>> {
/*     */     final ImmutableMultimap<K, V> multimap;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     EntryCollection(ImmutableMultimap<K, V> multimap) {
/* 483 */       this.multimap = multimap;
/*     */     }
/*     */ 
/*     */     
/*     */     public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
/* 488 */       final Iterator<? extends Map.Entry<K, ? extends ImmutableCollection<V>>> mapIterator = this.multimap.map.entrySet().iterator();
/*     */       
/* 490 */       return new UnmodifiableIterator<Map.Entry<K, V>>()
/*     */         {
/*     */           K key;
/*     */           Iterator<V> valueIterator;
/*     */           
/*     */           public boolean hasNext() {
/* 496 */             return ((this.key != null && this.valueIterator.hasNext()) || mapIterator.hasNext());
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Map.Entry<K, V> next() {
/* 502 */             if (this.key == null || !this.valueIterator.hasNext()) {
/* 503 */               Map.Entry<K, ? extends ImmutableCollection<V>> entry = mapIterator.next();
/*     */               
/* 505 */               this.key = entry.getKey();
/* 506 */               this.valueIterator = ((ImmutableCollection<V>)entry.getValue()).iterator();
/*     */             } 
/* 508 */             return Maps.immutableEntry(this.key, this.valueIterator.next());
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     boolean isPartialView() {
/* 514 */       return this.multimap.isPartialView();
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 519 */       return this.multimap.size();
/*     */     }
/*     */     
/*     */     public boolean contains(Object object) {
/* 523 */       if (object instanceof Map.Entry) {
/* 524 */         Map.Entry<?, ?> entry = (Map.Entry<?, ?>)object;
/* 525 */         return this.multimap.containsEntry(entry.getKey(), entry.getValue());
/*     */       } 
/* 527 */       return false;
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
/*     */   public ImmutableMultiset<K> keys() {
/* 543 */     ImmutableMultiset<K> result = this.keys;
/* 544 */     return (result == null) ? (this.keys = createKeys()) : result;
/*     */   }
/*     */   
/*     */   private ImmutableMultiset<K> createKeys() {
/* 548 */     ImmutableMultiset.Builder<K> builder = ImmutableMultiset.builder();
/*     */     
/* 550 */     for (Map.Entry<K, ? extends ImmutableCollection<V>> entry : this.map.entrySet()) {
/* 551 */       builder.addCopies(entry.getKey(), ((ImmutableCollection)entry.getValue()).size());
/*     */     }
/* 553 */     return builder.build();
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
/*     */   public ImmutableCollection<V> values() {
/* 565 */     ImmutableCollection<V> result = this.values;
/* 566 */     return (result == null) ? (this.values = new Values<V>(this)) : result;
/*     */   }
/*     */   public abstract ImmutableCollection<V> get(K paramK);
/*     */   
/*     */   private static class Values<V> extends ImmutableCollection<V> { final ImmutableMultimap<?, V> multimap;
/*     */     
/*     */     Values(ImmutableMultimap<?, V> multimap) {
/* 573 */       this.multimap = multimap;
/*     */     }
/*     */     private static final long serialVersionUID = 0L;
/*     */     public UnmodifiableIterator<V> iterator() {
/* 577 */       final Iterator<? extends Map.Entry<?, V>> entryIterator = this.multimap.entries().iterator();
/*     */       
/* 579 */       return new UnmodifiableIterator<V>()
/*     */         {
/*     */           public boolean hasNext() {
/* 582 */             return entryIterator.hasNext();
/*     */           }
/*     */           
/*     */           public V next() {
/* 586 */             return (V)((Map.Entry)entryIterator.next()).getValue();
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 593 */       return this.multimap.size();
/*     */     }
/*     */     
/*     */     boolean isPartialView() {
/* 597 */       return true;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ImmutableMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */