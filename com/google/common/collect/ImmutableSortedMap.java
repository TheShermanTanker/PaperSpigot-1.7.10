/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
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
/*     */ 
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ public class ImmutableSortedMap<K, V>
/*     */   extends ImmutableSortedMapFauxverideShim<K, V>
/*     */   implements SortedMap<K, V>
/*     */ {
/*  71 */   private static final Comparator<Comparable> NATURAL_ORDER = Ordering.natural();
/*     */ 
/*     */ 
/*     */   
/*  75 */   private static final ImmutableSortedMap<Comparable, Object> NATURAL_EMPTY_MAP = new ImmutableSortedMap(ImmutableList.of(), (Comparator)NATURAL_ORDER);
/*     */   
/*     */   final transient ImmutableList<Map.Entry<K, V>> entries;
/*     */   
/*     */   private final transient Comparator<? super K> comparator;
/*     */   private transient ImmutableSet<Map.Entry<K, V>> entrySet;
/*     */   private transient ImmutableSortedSet<K> keySet;
/*     */   private transient ImmutableCollection<V> values;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   public static <K, V> ImmutableSortedMap<K, V> of() {
/*  86 */     return (ImmutableSortedMap)NATURAL_EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <K, V> ImmutableSortedMap<K, V> emptyMap(Comparator<? super K> comparator) {
/*  92 */     if (NATURAL_ORDER.equals(comparator)) {
/*  93 */       return (ImmutableSortedMap)NATURAL_EMPTY_MAP;
/*     */     }
/*  95 */     return new ImmutableSortedMap<K, V>(ImmutableList.of(), comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1) {
/* 105 */     return new ImmutableSortedMap<K, V>(ImmutableList.of(entryOf(k1, v1)), Ordering.natural());
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
/*     */   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1, K k2, V v2) {
/* 118 */     return (new Builder<K, V>(Ordering.natural())).put(k1, v1).put(k2, v2).build();
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
/*     */   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
/* 131 */     return (new Builder<K, V>(Ordering.natural())).put(k1, v1).put(k2, v2).put(k3, v3).build();
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
/*     */   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
/* 144 */     return (new Builder<K, V>(Ordering.natural())).put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).build();
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
/*     */   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
/* 157 */     return (new Builder<K, V>(Ordering.natural())).put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).build();
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
/*     */   public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
/* 183 */     Ordering<K> naturalOrder = Ordering.natural();
/* 184 */     return copyOfInternal(map, naturalOrder);
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
/*     */   public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
/* 201 */     return copyOfInternal(map, (Comparator<? super K>)Preconditions.checkNotNull(comparator));
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
/*     */   public static <K, V> ImmutableSortedMap<K, V> copyOfSorted(SortedMap<K, ? extends V> map) {
/*     */     Comparator<Comparable> comparator1;
/* 217 */     Comparator<? super K> comparator = map.comparator();
/* 218 */     if (comparator == null)
/*     */     {
/*     */       
/* 221 */       comparator1 = NATURAL_ORDER;
/*     */     }
/* 223 */     return copyOfInternal(map, (Comparator)comparator1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <K, V> ImmutableSortedMap<K, V> copyOfInternal(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
/* 228 */     boolean sameComparator = false;
/* 229 */     if (map instanceof SortedMap) {
/* 230 */       SortedMap<?, ?> sortedMap = (SortedMap<?, ?>)map;
/* 231 */       Comparator<?> comparator2 = sortedMap.comparator();
/* 232 */       sameComparator = (comparator2 == null) ? ((comparator == NATURAL_ORDER)) : comparator.equals(comparator2);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 237 */     if (sameComparator && map instanceof ImmutableSortedMap) {
/*     */ 
/*     */ 
/*     */       
/* 241 */       ImmutableSortedMap<K, V> kvMap = (ImmutableSortedMap)map;
/* 242 */       if (!kvMap.isPartialView()) {
/* 243 */         return kvMap;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 251 */     Map.Entry[] arrayOfEntry = (Map.Entry[])map.entrySet().toArray((Object[])new Map.Entry[0]);
/*     */     
/* 253 */     for (int i = 0; i < arrayOfEntry.length; i++) {
/* 254 */       Map.Entry<K, V> entry = arrayOfEntry[i];
/* 255 */       arrayOfEntry[i] = entryOf(entry.getKey(), entry.getValue());
/*     */     } 
/*     */     
/* 258 */     List<Map.Entry<K, V>> list = Arrays.asList((Map.Entry<K, V>[])arrayOfEntry);
/*     */     
/* 260 */     if (!sameComparator) {
/* 261 */       sortEntries(list, comparator);
/* 262 */       validateEntries(list, comparator);
/*     */     } 
/*     */     
/* 265 */     return new ImmutableSortedMap<K, V>(ImmutableList.copyOf(list), comparator);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <K, V> void sortEntries(List<Map.Entry<K, V>> entries, final Comparator<? super K> comparator) {
/* 270 */     Comparator<Map.Entry<K, V>> entryComparator = new Comparator<Map.Entry<K, V>>()
/*     */       {
/*     */         public int compare(Map.Entry<K, V> entry1, Map.Entry<K, V> entry2) {
/* 273 */           return comparator.compare(entry1.getKey(), entry2.getKey());
/*     */         }
/*     */       };
/*     */     
/* 277 */     Collections.sort(entries, entryComparator);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <K, V> void validateEntries(List<Map.Entry<K, V>> entries, Comparator<? super K> comparator) {
/* 282 */     for (int i = 1; i < entries.size(); i++) {
/* 283 */       if (comparator.compare((K)((Map.Entry)entries.get(i - 1)).getKey(), (K)((Map.Entry)entries.get(i)).getKey()) == 0)
/*     */       {
/* 285 */         throw new IllegalArgumentException("Duplicate keys in mappings " + entries.get(i - 1) + " and " + entries.get(i));
/*     */       }
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
/*     */   public static <K extends Comparable<K>, V> Builder<K, V> naturalOrder() {
/* 303 */     return new Builder<K, V>(Ordering.natural());
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
/*     */   public static <K, V> Builder<K, V> orderedBy(Comparator<K> comparator) {
/* 315 */     return new Builder<K, V>(comparator);
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
/*     */   public static <K extends Comparable<K>, V> Builder<K, V> reverseOrder() {
/* 328 */     return new Builder<K, V>(Ordering.<Comparable>natural().reverse());
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
/*     */   public static class Builder<K, V>
/*     */     extends ImmutableMap.Builder<K, V>
/*     */   {
/*     */     private final Comparator<? super K> comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder(Comparator<? super K> comparator) {
/* 359 */       this.comparator = (Comparator<? super K>)Preconditions.checkNotNull(comparator);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> put(K key, V value) {
/* 368 */       this.entries.add(ImmutableMap.entryOf(key, value));
/* 369 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
/* 380 */       for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
/* 381 */         put(entry.getKey(), entry.getValue());
/*     */       }
/* 383 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ImmutableSortedMap<K, V> build() {
/* 393 */       ImmutableSortedMap.sortEntries(this.entries, this.comparator);
/* 394 */       ImmutableSortedMap.validateEntries(this.entries, this.comparator);
/* 395 */       return new ImmutableSortedMap<K, V>(ImmutableList.copyOf(this.entries), this.comparator);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ImmutableSortedMap(ImmutableList<Map.Entry<K, V>> entries, Comparator<? super K> comparator) {
/* 405 */     this.entries = entries;
/* 406 */     this.comparator = comparator;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 411 */     return this.entries.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Comparator<Object> unsafeComparator() {
/* 419 */     return (Comparator)this.comparator;
/*     */   }
/*     */   public V get(@Nullable Object key) {
/*     */     int i;
/* 423 */     if (key == null) {
/* 424 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 428 */       i = index(key, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.INVERTED_INSERTION_INDEX);
/* 429 */     } catch (ClassCastException e) {
/* 430 */       return null;
/*     */     } 
/* 432 */     return (i >= 0) ? (V)((Map.Entry)this.entries.get(i)).getValue() : null;
/*     */   }
/*     */   
/*     */   public boolean containsValue(@Nullable Object value) {
/* 436 */     if (value == null) {
/* 437 */       return false;
/*     */     }
/* 439 */     return Iterators.contains(valueIterator(), value);
/*     */   }
/*     */   
/*     */   boolean isPartialView() {
/* 443 */     return this.entries.isPartialView();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSet<Map.Entry<K, V>> entrySet() {
/* 453 */     ImmutableSet<Map.Entry<K, V>> es = this.entrySet;
/* 454 */     return (es == null) ? (this.entrySet = createEntrySet()) : es;
/*     */   }
/*     */   
/*     */   private ImmutableSet<Map.Entry<K, V>> createEntrySet() {
/* 458 */     return isEmpty() ? ImmutableSet.<Map.Entry<K, V>>of() : new EntrySet<K, V>(this);
/*     */   }
/*     */   
/*     */   private static class EntrySet<K, V>
/*     */     extends ImmutableSet<Map.Entry<K, V>>
/*     */   {
/*     */     final transient ImmutableSortedMap<K, V> map;
/*     */     
/*     */     EntrySet(ImmutableSortedMap<K, V> map) {
/* 467 */       this.map = map;
/*     */     }
/*     */     
/*     */     boolean isPartialView() {
/* 471 */       return this.map.isPartialView();
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 476 */       return this.map.size();
/*     */     }
/*     */     
/*     */     public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
/* 480 */       return this.map.entries.iterator();
/*     */     }
/*     */     
/*     */     public boolean contains(Object target) {
/* 484 */       if (target instanceof Map.Entry) {
/* 485 */         Map.Entry<?, ?> entry = (Map.Entry<?, ?>)target;
/* 486 */         V mappedValue = this.map.get(entry.getKey());
/* 487 */         return (mappedValue != null && mappedValue.equals(entry.getValue()));
/*     */       } 
/* 489 */       return false;
/*     */     }
/*     */     
/*     */     Object writeReplace() {
/* 493 */       return new ImmutableSortedMap.EntrySetSerializedForm<K, V>(this.map);
/*     */     } }
/*     */   
/*     */   private static class EntrySetSerializedForm<K, V> implements Serializable {
/*     */     final ImmutableSortedMap<K, V> map;
/*     */     
/*     */     EntrySetSerializedForm(ImmutableSortedMap<K, V> map) {
/* 500 */       this.map = map;
/*     */     } private static final long serialVersionUID = 0L;
/*     */     Object readResolve() {
/* 503 */       return this.map.entrySet();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSortedSet<K> keySet() {
/* 514 */     ImmutableSortedSet<K> ks = this.keySet;
/* 515 */     return (ks == null) ? (this.keySet = createKeySet()) : ks;
/*     */   }
/*     */ 
/*     */   
/*     */   private ImmutableSortedSet<K> createKeySet() {
/* 520 */     if (isEmpty()) {
/* 521 */       return ImmutableSortedSet.emptySet(this.comparator);
/*     */     }
/*     */     
/* 524 */     return new RegularImmutableSortedSet<K>(new TransformedImmutableList<Map.Entry<K, V>, K>(this.entries)
/*     */         {
/*     */           K transform(Map.Entry<K, V> entry)
/*     */           {
/* 528 */             return entry.getKey();
/*     */           }
/*     */         }this.comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableCollection<V> values() {
/* 540 */     ImmutableCollection<V> v = this.values;
/* 541 */     return (v == null) ? (this.values = new Values<V>(this)) : v;
/*     */   }
/*     */   
/*     */   UnmodifiableIterator<V> valueIterator() {
/* 545 */     final UnmodifiableIterator<Map.Entry<K, V>> entryIterator = this.entries.iterator();
/* 546 */     return new UnmodifiableIterator<V>()
/*     */       {
/*     */         public boolean hasNext() {
/* 549 */           return entryIterator.hasNext();
/*     */         }
/*     */         
/*     */         public V next() {
/* 553 */           return (V)((Map.Entry)entryIterator.next()).getValue();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private static class Values<V>
/*     */     extends ImmutableCollection<V> {
/*     */     private final ImmutableSortedMap<?, V> map;
/*     */     
/*     */     Values(ImmutableSortedMap<?, V> map) {
/* 563 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 568 */       return this.map.size();
/*     */     }
/*     */     
/*     */     public UnmodifiableIterator<V> iterator() {
/* 572 */       return this.map.valueIterator();
/*     */     }
/*     */     
/*     */     public boolean contains(Object target) {
/* 576 */       return this.map.containsValue(target);
/*     */     }
/*     */     
/*     */     boolean isPartialView() {
/* 580 */       return true;
/*     */     }
/*     */     
/*     */     Object writeReplace() {
/* 584 */       return new ImmutableSortedMap.ValuesSerializedForm<V>(this.map);
/*     */     } }
/*     */   
/*     */   private static class ValuesSerializedForm<V> implements Serializable { final ImmutableSortedMap<?, V> map;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     ValuesSerializedForm(ImmutableSortedMap<?, V> map) {
/* 591 */       this.map = map;
/*     */     }
/*     */     Object readResolve() {
/* 594 */       return this.map.values();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparator<? super K> comparator() {
/* 607 */     return this.comparator;
/*     */   }
/*     */ 
/*     */   
/*     */   public K firstKey() {
/* 612 */     if (isEmpty()) {
/* 613 */       throw new NoSuchElementException();
/*     */     }
/* 615 */     return (K)((Map.Entry)this.entries.get(0)).getKey();
/*     */   }
/*     */ 
/*     */   
/*     */   public K lastKey() {
/* 620 */     if (isEmpty()) {
/* 621 */       throw new NoSuchElementException();
/*     */     }
/* 623 */     return (K)((Map.Entry)this.entries.get(size() - 1)).getKey();
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
/*     */   public ImmutableSortedMap<K, V> headMap(K toKey) {
/* 638 */     return headMap(toKey, false);
/*     */   }
/*     */   
/*     */   ImmutableSortedMap<K, V> headMap(K toKey, boolean inclusive) {
/*     */     int index;
/* 643 */     if (inclusive) {
/* 644 */       index = index(toKey, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER) + 1;
/*     */     } else {
/* 646 */       index = index(toKey, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
/*     */     } 
/* 648 */     return createSubmap(0, index);
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
/*     */   public ImmutableSortedMap<K, V> subMap(K fromKey, K toKey) {
/* 666 */     return subMap(fromKey, true, toKey, false);
/*     */   }
/*     */ 
/*     */   
/*     */   ImmutableSortedMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
/* 671 */     Preconditions.checkNotNull(fromKey);
/* 672 */     Preconditions.checkNotNull(toKey);
/* 673 */     Preconditions.checkArgument((this.comparator.compare(fromKey, toKey) <= 0));
/* 674 */     return tailMap(fromKey, fromInclusive).headMap(toKey, toInclusive);
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
/*     */   public ImmutableSortedMap<K, V> tailMap(K fromKey) {
/* 689 */     return tailMap(fromKey, true);
/*     */   }
/*     */   
/*     */   ImmutableSortedMap<K, V> tailMap(K fromKey, boolean inclusive) {
/*     */     int index;
/* 694 */     if (inclusive) {
/* 695 */       index = index(fromKey, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
/*     */     } else {
/* 697 */       index = index(fromKey, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER) + 1;
/*     */     } 
/* 699 */     return createSubmap(index, size());
/*     */   }
/*     */   
/*     */   private ImmutableList<K> keyList() {
/* 703 */     return new TransformedImmutableList<Map.Entry<K, V>, K>(this.entries)
/*     */       {
/*     */         K transform(Map.Entry<K, V> entry) {
/* 706 */           return entry.getKey();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private int index(Object key, SortedLists.KeyPresentBehavior presentBehavior, SortedLists.KeyAbsentBehavior absentBehavior) {
/* 713 */     return SortedLists.binarySearch(keyList(), (K)Preconditions.checkNotNull(key), (Comparator)unsafeComparator(), presentBehavior, absentBehavior);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ImmutableSortedMap<K, V> createSubmap(int newFromIndex, int newToIndex) {
/* 719 */     if (newFromIndex < newToIndex) {
/* 720 */       return new ImmutableSortedMap(this.entries.subList(newFromIndex, newToIndex), this.comparator);
/*     */     }
/*     */     
/* 723 */     return emptyMap(this.comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SerializedForm
/*     */     extends ImmutableMap.SerializedForm
/*     */   {
/*     */     private final Comparator<Object> comparator;
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */     
/*     */     SerializedForm(ImmutableSortedMap<?, ?> sortedMap) {
/* 737 */       super(sortedMap);
/* 738 */       this.comparator = (Comparator)sortedMap.comparator();
/*     */     }
/*     */     Object readResolve() {
/* 741 */       ImmutableSortedMap.Builder<Object, Object> builder = new ImmutableSortedMap.Builder<Object, Object>(this.comparator);
/* 742 */       return createMap(builder);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   Object writeReplace() {
/* 748 */     return new SerializedForm(this);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ImmutableSortedMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */