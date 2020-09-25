/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @GwtCompatible
/*     */ public final class SortedMaps
/*     */ {
/*     */   public static <K, V1, V2> SortedMap<K, V2> transformValues(SortedMap<K, V1> fromMap, final Function<? super V1, V2> function) {
/*  92 */     Preconditions.checkNotNull(function);
/*  93 */     Maps.EntryTransformer<K, V1, V2> transformer = new Maps.EntryTransformer<K, V1, V2>()
/*     */       {
/*     */         public V2 transformEntry(K key, V1 value)
/*     */         {
/*  97 */           return (V2)function.apply(value);
/*     */         }
/*     */       };
/* 100 */     return transformEntries(fromMap, transformer);
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
/*     */   public static <K, V1, V2> SortedMap<K, V2> transformEntries(SortedMap<K, V1> fromMap, Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 156 */     return new TransformedEntriesSortedMap<K, V1, V2>(fromMap, transformer);
/*     */   }
/*     */   
/*     */   static class TransformedEntriesSortedMap<K, V1, V2>
/*     */     extends Maps.TransformedEntriesMap<K, V1, V2>
/*     */     implements SortedMap<K, V2> {
/*     */     protected SortedMap<K, V1> fromMap() {
/* 163 */       return (SortedMap<K, V1>)this.fromMap;
/*     */     }
/*     */ 
/*     */     
/*     */     TransformedEntriesSortedMap(SortedMap<K, V1> fromMap, Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 168 */       super(fromMap, transformer);
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 172 */       return fromMap().comparator();
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 176 */       return fromMap().firstKey();
/*     */     }
/*     */     
/*     */     public SortedMap<K, V2> headMap(K toKey) {
/* 180 */       return SortedMaps.transformEntries(fromMap().headMap(toKey), this.transformer);
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 184 */       return fromMap().lastKey();
/*     */     }
/*     */     
/*     */     public SortedMap<K, V2> subMap(K fromKey, K toKey) {
/* 188 */       return SortedMaps.transformEntries(fromMap().subMap(fromKey, toKey), this.transformer);
/*     */     }
/*     */ 
/*     */     
/*     */     public SortedMap<K, V2> tailMap(K fromKey) {
/* 193 */       return SortedMaps.transformEntries(fromMap().tailMap(fromKey), this.transformer);
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
/*     */   public static <K, V> SortedMapDifference<K, V> difference(SortedMap<K, ? extends V> left, Map<? extends K, ? extends V> right) {
/* 218 */     Comparator<? super K> comparator = orNaturalOrder(left.comparator());
/* 219 */     SortedMap<K, V> onlyOnLeft = Maps.newTreeMap(comparator);
/* 220 */     SortedMap<K, V> onlyOnRight = Maps.newTreeMap(comparator);
/* 221 */     onlyOnRight.putAll(right);
/* 222 */     SortedMap<K, V> onBoth = Maps.newTreeMap(comparator);
/* 223 */     SortedMap<K, MapDifference.ValueDifference<V>> differences = Maps.newTreeMap(comparator);
/*     */     
/* 225 */     boolean eq = true;
/*     */     
/* 227 */     for (Map.Entry<? extends K, ? extends V> entry : left.entrySet()) {
/* 228 */       K leftKey = entry.getKey();
/* 229 */       V leftValue = entry.getValue();
/* 230 */       if (right.containsKey(leftKey)) {
/* 231 */         V rightValue = onlyOnRight.remove(leftKey);
/* 232 */         if (Objects.equal(leftValue, rightValue)) {
/* 233 */           onBoth.put(leftKey, leftValue); continue;
/*     */         } 
/* 235 */         eq = false;
/* 236 */         differences.put(leftKey, Maps.ValueDifferenceImpl.create(leftValue, rightValue));
/*     */         
/*     */         continue;
/*     */       } 
/* 240 */       eq = false;
/* 241 */       onlyOnLeft.put(leftKey, leftValue);
/*     */     } 
/*     */ 
/*     */     
/* 245 */     boolean areEqual = (eq && onlyOnRight.isEmpty());
/* 246 */     return sortedMapDifference(areEqual, onlyOnLeft, onlyOnRight, onBoth, differences);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <K, V> SortedMapDifference<K, V> sortedMapDifference(boolean areEqual, SortedMap<K, V> onlyOnLeft, SortedMap<K, V> onlyOnRight, SortedMap<K, V> onBoth, SortedMap<K, MapDifference.ValueDifference<V>> differences) {
/* 253 */     return new SortedMapDifferenceImpl<K, V>(areEqual, Collections.unmodifiableSortedMap(onlyOnLeft), Collections.unmodifiableSortedMap(onlyOnRight), Collections.unmodifiableSortedMap(onBoth), Collections.unmodifiableSortedMap(differences));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class SortedMapDifferenceImpl<K, V>
/*     */     extends Maps.MapDifferenceImpl<K, V>
/*     */     implements SortedMapDifference<K, V>
/*     */   {
/*     */     SortedMapDifferenceImpl(boolean areEqual, SortedMap<K, V> onlyOnLeft, SortedMap<K, V> onlyOnRight, SortedMap<K, V> onBoth, SortedMap<K, MapDifference.ValueDifference<V>> differences) {
/* 265 */       super(areEqual, onlyOnLeft, onlyOnRight, onBoth, differences);
/*     */     }
/*     */     
/*     */     public SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering() {
/* 269 */       return (SortedMap<K, MapDifference.ValueDifference<V>>)super.entriesDiffering();
/*     */     }
/*     */     
/*     */     public SortedMap<K, V> entriesInCommon() {
/* 273 */       return (SortedMap<K, V>)super.entriesInCommon();
/*     */     }
/*     */     
/*     */     public SortedMap<K, V> entriesOnlyOnLeft() {
/* 277 */       return (SortedMap<K, V>)super.entriesOnlyOnLeft();
/*     */     }
/*     */     
/*     */     public SortedMap<K, V> entriesOnlyOnRight() {
/* 281 */       return (SortedMap<K, V>)super.entriesOnlyOnRight();
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
/*     */   static <E> Comparator<? super E> orNaturalOrder(@Nullable Comparator<? super E> comparator) {
/* 293 */     if (comparator != null) {
/* 294 */       return comparator;
/*     */     }
/* 296 */     return Ordering.natural();
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
/*     */   @GwtIncompatible("untested")
/*     */   public static <K, V> SortedMap<K, V> filterKeys(SortedMap<K, V> unfiltered, final Predicate<? super K> keyPredicate) {
/* 332 */     Preconditions.checkNotNull(keyPredicate);
/* 333 */     Predicate<Map.Entry<K, V>> entryPredicate = new Predicate<Map.Entry<K, V>>()
/*     */       {
/*     */         public boolean apply(Map.Entry<K, V> input) {
/* 336 */           return keyPredicate.apply(input.getKey());
/*     */         }
/*     */       };
/* 339 */     return filterEntries(unfiltered, entryPredicate);
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
/*     */   @GwtIncompatible("untested")
/*     */   public static <K, V> SortedMap<K, V> filterValues(SortedMap<K, V> unfiltered, final Predicate<? super V> valuePredicate) {
/* 374 */     Preconditions.checkNotNull(valuePredicate);
/* 375 */     Predicate<Map.Entry<K, V>> entryPredicate = new Predicate<Map.Entry<K, V>>()
/*     */       {
/*     */         public boolean apply(Map.Entry<K, V> input)
/*     */         {
/* 379 */           return valuePredicate.apply(input.getValue());
/*     */         }
/*     */       };
/* 382 */     return filterEntries(unfiltered, entryPredicate);
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
/*     */   @GwtIncompatible("untested")
/*     */   public static <K, V> SortedMap<K, V> filterEntries(SortedMap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 418 */     Preconditions.checkNotNull(entryPredicate);
/* 419 */     return (unfiltered instanceof FilteredSortedMap) ? filterFiltered((FilteredSortedMap<K, V>)unfiltered, entryPredicate) : new FilteredSortedMap<K, V>((SortedMap<K, V>)Preconditions.checkNotNull(unfiltered), entryPredicate);
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
/*     */   private static <K, V> SortedMap<K, V> filterFiltered(FilteredSortedMap<K, V> map, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 431 */     Predicate<Map.Entry<K, V>> predicate = Predicates.and(map.predicate, entryPredicate);
/*     */     
/* 433 */     return new FilteredSortedMap<K, V>(map.sortedMap(), predicate);
/*     */   }
/*     */   
/*     */   private static class FilteredSortedMap<K, V>
/*     */     extends Maps.FilteredEntryMap<K, V>
/*     */     implements SortedMap<K, V>
/*     */   {
/*     */     FilteredSortedMap(SortedMap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 441 */       super(unfiltered, entryPredicate);
/*     */     }
/*     */     
/*     */     SortedMap<K, V> sortedMap() {
/* 445 */       return (SortedMap<K, V>)this.unfiltered;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 449 */       return sortedMap().comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public K firstKey() {
/* 454 */       return keySet().iterator().next();
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 458 */       SortedMap<K, V> headMap = sortedMap();
/*     */       
/*     */       while (true) {
/* 461 */         K key = headMap.lastKey();
/* 462 */         if (apply(key, this.unfiltered.get(key))) {
/* 463 */           return key;
/*     */         }
/* 465 */         headMap = sortedMap().headMap(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public SortedMap<K, V> headMap(K toKey) {
/* 470 */       return new FilteredSortedMap(sortedMap().headMap(toKey), this.predicate);
/*     */     }
/*     */     
/*     */     public SortedMap<K, V> subMap(K fromKey, K toKey) {
/* 474 */       return new FilteredSortedMap(sortedMap().subMap(fromKey, toKey), this.predicate);
/*     */     }
/*     */ 
/*     */     
/*     */     public SortedMap<K, V> tailMap(K fromKey) {
/* 479 */       return new FilteredSortedMap(sortedMap().tailMap(fromKey), this.predicate);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\SortedMaps.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */