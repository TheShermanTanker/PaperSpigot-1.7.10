/*      */ package net.minecraft.util.com.google.common.collect;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractCollection;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.EnumMap;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Map;
/*      */ import java.util.NavigableMap;
/*      */ import java.util.NavigableSet;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.SortedSet;
/*      */ import java.util.TreeMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.util.com.google.common.annotations.Beta;
/*      */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*      */ import net.minecraft.util.com.google.common.annotations.GwtIncompatible;
/*      */ import net.minecraft.util.com.google.common.base.Converter;
/*      */ import net.minecraft.util.com.google.common.base.Equivalence;
/*      */ import net.minecraft.util.com.google.common.base.Function;
/*      */ import net.minecraft.util.com.google.common.base.Joiner;
/*      */ import net.minecraft.util.com.google.common.base.Objects;
/*      */ import net.minecraft.util.com.google.common.base.Preconditions;
/*      */ import net.minecraft.util.com.google.common.base.Predicate;
/*      */ import net.minecraft.util.com.google.common.base.Predicates;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @GwtCompatible(emulated = true)
/*      */ public final class Maps
/*      */ {
/*      */   private enum EntryFunction
/*      */     implements Function<Map.Entry<?, ?>, Object>
/*      */   {
/*   86 */     KEY
/*      */     {
/*      */       @Nullable
/*      */       public Object apply(Map.Entry<?, ?> entry) {
/*   90 */         return entry.getKey();
/*      */       }
/*      */     },
/*   93 */     VALUE
/*      */     {
/*      */       @Nullable
/*      */       public Object apply(Map.Entry<?, ?> entry) {
/*   97 */         return entry.getValue();
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   
/*      */   static <K> Function<Map.Entry<K, ?>, K> keyFunction() {
/*  104 */     return EntryFunction.KEY;
/*      */   }
/*      */ 
/*      */   
/*      */   static <V> Function<Map.Entry<?, V>, V> valueFunction() {
/*  109 */     return EntryFunction.VALUE;
/*      */   }
/*      */   
/*      */   static <K, V> Iterator<K> keyIterator(Iterator<Map.Entry<K, V>> entryIterator) {
/*  113 */     return Iterators.transform(entryIterator, (Function)keyFunction());
/*      */   }
/*      */   
/*      */   static <K, V> Iterator<V> valueIterator(Iterator<Map.Entry<K, V>> entryIterator) {
/*  117 */     return Iterators.transform(entryIterator, (Function)valueFunction());
/*      */   }
/*      */ 
/*      */   
/*      */   static <K, V> UnmodifiableIterator<V> valueIterator(final UnmodifiableIterator<Map.Entry<K, V>> entryIterator) {
/*  122 */     return new UnmodifiableIterator<V>()
/*      */       {
/*      */         public boolean hasNext() {
/*  125 */           return entryIterator.hasNext();
/*      */         }
/*      */ 
/*      */         
/*      */         public V next() {
/*  130 */           return (V)((Map.Entry)entryIterator.next()).getValue();
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtCompatible(serializable = true)
/*      */   @Beta
/*      */   public static <K extends Enum<K>, V> ImmutableMap<K, V> immutableEnumMap(Map<K, ? extends V> map) {
/*  150 */     if (map instanceof ImmutableEnumMap) {
/*      */       
/*  152 */       ImmutableEnumMap<K, V> result = (ImmutableEnumMap)map;
/*  153 */       return result;
/*  154 */     }  if (map.isEmpty()) {
/*  155 */       return ImmutableMap.of();
/*      */     }
/*  157 */     for (Map.Entry<K, ? extends V> entry : map.entrySet()) {
/*  158 */       Preconditions.checkNotNull(entry.getKey());
/*  159 */       Preconditions.checkNotNull(entry.getValue());
/*      */     } 
/*  161 */     return ImmutableEnumMap.asImmutable(new EnumMap<K, V>(map));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> HashMap<K, V> newHashMap() {
/*  177 */     return new HashMap<K, V>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) {
/*  195 */     return new HashMap<K, V>(capacity(expectedSize));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int capacity(int expectedSize) {
/*  204 */     if (expectedSize < 3) {
/*  205 */       CollectPreconditions.checkNonnegative(expectedSize, "expectedSize");
/*  206 */       return expectedSize + 1;
/*      */     } 
/*  208 */     if (expectedSize < 1073741824) {
/*  209 */       return expectedSize + expectedSize / 3;
/*      */     }
/*  211 */     return Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
/*  230 */     return new HashMap<K, V>(map);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
/*  243 */     return new LinkedHashMap<K, V>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> map) {
/*  259 */     return new LinkedHashMap<K, V>(map);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
/*  278 */     return (new MapMaker()).makeMap();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
/*  291 */     return new TreeMap<K, V>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> map) {
/*  307 */     return new TreeMap<K, V>(map);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <C, K extends C, V> TreeMap<K, V> newTreeMap(@Nullable Comparator<C> comparator) {
/*  327 */     return new TreeMap<K, V>(comparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> type) {
/*  337 */     return new EnumMap<K, V>((Class<K>)Preconditions.checkNotNull(type));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Map<K, ? extends V> map) {
/*  351 */     return new EnumMap<K, V>(map);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
/*  360 */     return new IdentityHashMap<K, V>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> left, Map<? extends K, ? extends V> right) {
/*  382 */     if (left instanceof SortedMap) {
/*  383 */       SortedMap<K, ? extends V> sortedLeft = (SortedMap)left;
/*  384 */       SortedMapDifference<K, V> result = difference(sortedLeft, right);
/*  385 */       return result;
/*      */     } 
/*  387 */     return difference(left, right, Equivalence.equals());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> left, Map<? extends K, ? extends V> right, Equivalence<? super V> valueEquivalence) {
/*  413 */     Preconditions.checkNotNull(valueEquivalence);
/*      */     
/*  415 */     Map<K, V> onlyOnLeft = newHashMap();
/*  416 */     Map<K, V> onlyOnRight = new HashMap<K, V>(right);
/*  417 */     Map<K, V> onBoth = newHashMap();
/*  418 */     Map<K, MapDifference.ValueDifference<V>> differences = newHashMap();
/*  419 */     doDifference(left, right, valueEquivalence, onlyOnLeft, onlyOnRight, onBoth, differences);
/*  420 */     return new MapDifferenceImpl<K, V>(onlyOnLeft, onlyOnRight, onBoth, differences);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <K, V> void doDifference(Map<? extends K, ? extends V> left, Map<? extends K, ? extends V> right, Equivalence<? super V> valueEquivalence, Map<K, V> onlyOnLeft, Map<K, V> onlyOnRight, Map<K, V> onBoth, Map<K, MapDifference.ValueDifference<V>> differences) {
/*  428 */     for (Map.Entry<? extends K, ? extends V> entry : left.entrySet()) {
/*  429 */       K leftKey = entry.getKey();
/*  430 */       V leftValue = entry.getValue();
/*  431 */       if (right.containsKey(leftKey)) {
/*  432 */         V rightValue = onlyOnRight.remove(leftKey);
/*  433 */         if (valueEquivalence.equivalent(leftValue, rightValue)) {
/*  434 */           onBoth.put(leftKey, leftValue); continue;
/*      */         } 
/*  436 */         differences.put(leftKey, ValueDifferenceImpl.create(leftValue, rightValue));
/*      */         
/*      */         continue;
/*      */       } 
/*  440 */       onlyOnLeft.put(leftKey, leftValue);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static <K, V> Map<K, V> unmodifiableMap(Map<K, V> map) {
/*  446 */     if (map instanceof SortedMap) {
/*  447 */       return Collections.unmodifiableSortedMap((SortedMap<K, ? extends V>)map);
/*      */     }
/*  449 */     return Collections.unmodifiableMap(map);
/*      */   }
/*      */ 
/*      */   
/*      */   static class MapDifferenceImpl<K, V>
/*      */     implements MapDifference<K, V>
/*      */   {
/*      */     final Map<K, V> onlyOnLeft;
/*      */     final Map<K, V> onlyOnRight;
/*      */     final Map<K, V> onBoth;
/*      */     final Map<K, MapDifference.ValueDifference<V>> differences;
/*      */     
/*      */     MapDifferenceImpl(Map<K, V> onlyOnLeft, Map<K, V> onlyOnRight, Map<K, V> onBoth, Map<K, MapDifference.ValueDifference<V>> differences) {
/*  462 */       this.onlyOnLeft = Maps.unmodifiableMap(onlyOnLeft);
/*  463 */       this.onlyOnRight = Maps.unmodifiableMap(onlyOnRight);
/*  464 */       this.onBoth = Maps.unmodifiableMap(onBoth);
/*  465 */       this.differences = (Map)Maps.unmodifiableMap((Map)differences);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean areEqual() {
/*  470 */       return (this.onlyOnLeft.isEmpty() && this.onlyOnRight.isEmpty() && this.differences.isEmpty());
/*      */     }
/*      */ 
/*      */     
/*      */     public Map<K, V> entriesOnlyOnLeft() {
/*  475 */       return this.onlyOnLeft;
/*      */     }
/*      */ 
/*      */     
/*      */     public Map<K, V> entriesOnlyOnRight() {
/*  480 */       return this.onlyOnRight;
/*      */     }
/*      */ 
/*      */     
/*      */     public Map<K, V> entriesInCommon() {
/*  485 */       return this.onBoth;
/*      */     }
/*      */ 
/*      */     
/*      */     public Map<K, MapDifference.ValueDifference<V>> entriesDiffering() {
/*  490 */       return this.differences;
/*      */     }
/*      */     
/*      */     public boolean equals(Object object) {
/*  494 */       if (object == this) {
/*  495 */         return true;
/*      */       }
/*  497 */       if (object instanceof MapDifference) {
/*  498 */         MapDifference<?, ?> other = (MapDifference<?, ?>)object;
/*  499 */         return (entriesOnlyOnLeft().equals(other.entriesOnlyOnLeft()) && entriesOnlyOnRight().equals(other.entriesOnlyOnRight()) && entriesInCommon().equals(other.entriesInCommon()) && entriesDiffering().equals(other.entriesDiffering()));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  504 */       return false;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  508 */       return Objects.hashCode(new Object[] { entriesOnlyOnLeft(), entriesOnlyOnRight(), entriesInCommon(), entriesDiffering() });
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  513 */       if (areEqual()) {
/*  514 */         return "equal";
/*      */       }
/*      */       
/*  517 */       StringBuilder result = new StringBuilder("not equal");
/*  518 */       if (!this.onlyOnLeft.isEmpty()) {
/*  519 */         result.append(": only on left=").append(this.onlyOnLeft);
/*      */       }
/*  521 */       if (!this.onlyOnRight.isEmpty()) {
/*  522 */         result.append(": only on right=").append(this.onlyOnRight);
/*      */       }
/*  524 */       if (!this.differences.isEmpty()) {
/*  525 */         result.append(": value differences=").append(this.differences);
/*      */       }
/*  527 */       return result.toString();
/*      */     }
/*      */   }
/*      */   
/*      */   static class ValueDifferenceImpl<V>
/*      */     implements MapDifference.ValueDifference<V> {
/*      */     private final V left;
/*      */     private final V right;
/*      */     
/*      */     static <V> MapDifference.ValueDifference<V> create(@Nullable V left, @Nullable V right) {
/*  537 */       return new ValueDifferenceImpl<V>(left, right);
/*      */     }
/*      */     
/*      */     private ValueDifferenceImpl(@Nullable V left, @Nullable V right) {
/*  541 */       this.left = left;
/*  542 */       this.right = right;
/*      */     }
/*      */ 
/*      */     
/*      */     public V leftValue() {
/*  547 */       return this.left;
/*      */     }
/*      */ 
/*      */     
/*      */     public V rightValue() {
/*  552 */       return this.right;
/*      */     }
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/*  556 */       if (object instanceof MapDifference.ValueDifference) {
/*  557 */         MapDifference.ValueDifference<?> that = (MapDifference.ValueDifference)object;
/*      */         
/*  559 */         return (Objects.equal(this.left, that.leftValue()) && Objects.equal(this.right, that.rightValue()));
/*      */       } 
/*      */       
/*  562 */       return false;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  566 */       return Objects.hashCode(new Object[] { this.left, this.right });
/*      */     }
/*      */     
/*      */     public String toString() {
/*  570 */       return "(" + this.left + ", " + this.right + ")";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> SortedMapDifference<K, V> difference(SortedMap<K, ? extends V> left, Map<? extends K, ? extends V> right) {
/*  595 */     Preconditions.checkNotNull(left);
/*  596 */     Preconditions.checkNotNull(right);
/*  597 */     Comparator<? super K> comparator = orNaturalOrder(left.comparator());
/*  598 */     SortedMap<K, V> onlyOnLeft = newTreeMap(comparator);
/*  599 */     SortedMap<K, V> onlyOnRight = newTreeMap(comparator);
/*  600 */     onlyOnRight.putAll(right);
/*  601 */     SortedMap<K, V> onBoth = newTreeMap(comparator);
/*  602 */     SortedMap<K, MapDifference.ValueDifference<V>> differences = newTreeMap(comparator);
/*      */     
/*  604 */     doDifference(left, right, Equivalence.equals(), onlyOnLeft, onlyOnRight, onBoth, differences);
/*  605 */     return new SortedMapDifferenceImpl<K, V>(onlyOnLeft, onlyOnRight, onBoth, differences);
/*      */   }
/*      */   
/*      */   static class SortedMapDifferenceImpl<K, V>
/*      */     extends MapDifferenceImpl<K, V>
/*      */     implements SortedMapDifference<K, V>
/*      */   {
/*      */     SortedMapDifferenceImpl(SortedMap<K, V> onlyOnLeft, SortedMap<K, V> onlyOnRight, SortedMap<K, V> onBoth, SortedMap<K, MapDifference.ValueDifference<V>> differences) {
/*  613 */       super(onlyOnLeft, onlyOnRight, onBoth, differences);
/*      */     }
/*      */     
/*      */     public SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering() {
/*  617 */       return (SortedMap<K, MapDifference.ValueDifference<V>>)super.entriesDiffering();
/*      */     }
/*      */     
/*      */     public SortedMap<K, V> entriesInCommon() {
/*  621 */       return (SortedMap<K, V>)super.entriesInCommon();
/*      */     }
/*      */     
/*      */     public SortedMap<K, V> entriesOnlyOnLeft() {
/*  625 */       return (SortedMap<K, V>)super.entriesOnlyOnLeft();
/*      */     }
/*      */     
/*      */     public SortedMap<K, V> entriesOnlyOnRight() {
/*  629 */       return (SortedMap<K, V>)super.entriesOnlyOnRight();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <E> Comparator<? super E> orNaturalOrder(@Nullable Comparator<? super E> comparator) {
/*  641 */     if (comparator != null) {
/*  642 */       return comparator;
/*      */     }
/*  644 */     return Ordering.natural();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <K, V> Map<K, V> asMap(Set<K> set, Function<? super K, V> function) {
/*  677 */     if (set instanceof SortedSet) {
/*  678 */       return asMap((SortedSet<K>)set, function);
/*      */     }
/*  680 */     return new AsMapView<K, V>(set, function);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <K, V> SortedMap<K, V> asMap(SortedSet<K> set, Function<? super K, V> function) {
/*  713 */     return Platform.mapsAsMapSortedSet(set, function);
/*      */   }
/*      */ 
/*      */   
/*      */   static <K, V> SortedMap<K, V> asMapSortedIgnoreNavigable(SortedSet<K> set, Function<? super K, V> function) {
/*  718 */     return new SortedAsMapView<K, V>(set, function);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   @GwtIncompatible("NavigableMap")
/*      */   public static <K, V> NavigableMap<K, V> asMap(NavigableSet<K> set, Function<? super K, V> function) {
/*  751 */     return new NavigableAsMapView<K, V>(set, function);
/*      */   }
/*      */   
/*      */   private static class AsMapView<K, V>
/*      */     extends ImprovedAbstractMap<K, V> {
/*      */     private final Set<K> set;
/*      */     final Function<? super K, V> function;
/*      */     
/*      */     Set<K> backingSet() {
/*  760 */       return this.set;
/*      */     }
/*      */     
/*      */     AsMapView(Set<K> set, Function<? super K, V> function) {
/*  764 */       this.set = (Set<K>)Preconditions.checkNotNull(set);
/*  765 */       this.function = (Function<? super K, V>)Preconditions.checkNotNull(function);
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<K> createKeySet() {
/*  770 */       return Maps.removeOnlySet(backingSet());
/*      */     }
/*      */ 
/*      */     
/*      */     Collection<V> createValues() {
/*  775 */       return Collections2.transform(this.set, this.function);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  780 */       return backingSet().size();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(@Nullable Object key) {
/*  785 */       return backingSet().contains(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public V get(@Nullable Object key) {
/*  790 */       if (Collections2.safeContains(backingSet(), key)) {
/*      */         
/*  792 */         K k = (K)key;
/*  793 */         return (V)this.function.apply(k);
/*      */       } 
/*  795 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V remove(@Nullable Object key) {
/*  801 */       if (backingSet().remove(key)) {
/*      */         
/*  803 */         K k = (K)key;
/*  804 */         return (V)this.function.apply(k);
/*      */       } 
/*  806 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void clear() {
/*  812 */       backingSet().clear();
/*      */     }
/*      */ 
/*      */     
/*      */     protected Set<Map.Entry<K, V>> createEntrySet() {
/*  817 */       return new Maps.EntrySet<K, V>()
/*      */         {
/*      */           Map<K, V> map() {
/*  820 */             return Maps.AsMapView.this;
/*      */           }
/*      */ 
/*      */           
/*      */           public Iterator<Map.Entry<K, V>> iterator() {
/*  825 */             return Maps.asMapEntryIterator(Maps.AsMapView.this.backingSet(), Maps.AsMapView.this.function);
/*      */           }
/*      */         };
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static <K, V> Iterator<Map.Entry<K, V>> asMapEntryIterator(Set<K> set, final Function<? super K, V> function) {
/*  833 */     return new TransformedIterator<K, Map.Entry<K, V>>(set.iterator())
/*      */       {
/*      */         Map.Entry<K, V> transform(K key) {
/*  836 */           return Maps.immutableEntry(key, (V)function.apply(key));
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private static class SortedAsMapView<K, V>
/*      */     extends AsMapView<K, V>
/*      */     implements SortedMap<K, V> {
/*      */     SortedAsMapView(SortedSet<K> set, Function<? super K, V> function) {
/*  845 */       super(set, function);
/*      */     }
/*      */ 
/*      */     
/*      */     SortedSet<K> backingSet() {
/*  850 */       return (SortedSet<K>)super.backingSet();
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/*  855 */       return backingSet().comparator();
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/*  860 */       return Maps.removeOnlySortedSet(backingSet());
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedMap<K, V> subMap(K fromKey, K toKey) {
/*  865 */       return Maps.asMap(backingSet().subSet(fromKey, toKey), this.function);
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedMap<K, V> headMap(K toKey) {
/*  870 */       return Maps.asMap(backingSet().headSet(toKey), this.function);
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedMap<K, V> tailMap(K fromKey) {
/*  875 */       return Maps.asMap(backingSet().tailSet(fromKey), this.function);
/*      */     }
/*      */ 
/*      */     
/*      */     public K firstKey() {
/*  880 */       return backingSet().first();
/*      */     }
/*      */ 
/*      */     
/*      */     public K lastKey() {
/*  885 */       return backingSet().last();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("NavigableMap")
/*      */   private static final class NavigableAsMapView<K, V>
/*      */     extends AbstractNavigableMap<K, V>
/*      */   {
/*      */     private final NavigableSet<K> set;
/*      */     
/*      */     private final Function<? super K, V> function;
/*      */ 
/*      */     
/*      */     NavigableAsMapView(NavigableSet<K> ks, Function<? super K, V> vFunction) {
/*  901 */       this.set = (NavigableSet<K>)Preconditions.checkNotNull(ks);
/*  902 */       this.function = (Function<? super K, V>)Preconditions.checkNotNull(vFunction);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
/*  908 */       return Maps.asMap(this.set.subSet(fromKey, fromInclusive, toKey, toInclusive), this.function);
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
/*  913 */       return Maps.asMap(this.set.headSet(toKey, inclusive), this.function);
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
/*  918 */       return Maps.asMap(this.set.tailSet(fromKey, inclusive), this.function);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/*  923 */       return this.set.comparator();
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public V get(@Nullable Object key) {
/*  929 */       if (Collections2.safeContains(this.set, key)) {
/*      */         
/*  931 */         K k = (K)key;
/*  932 */         return (V)this.function.apply(k);
/*      */       } 
/*  934 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void clear() {
/*  940 */       this.set.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     Iterator<Map.Entry<K, V>> entryIterator() {
/*  945 */       return Maps.asMapEntryIterator(this.set, this.function);
/*      */     }
/*      */ 
/*      */     
/*      */     Iterator<Map.Entry<K, V>> descendingEntryIterator() {
/*  950 */       return descendingMap().entrySet().iterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableSet<K> navigableKeySet() {
/*  955 */       return Maps.removeOnlyNavigableSet(this.set);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  960 */       return this.set.size();
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> descendingMap() {
/*  965 */       return Maps.asMap(this.set.descendingSet(), this.function);
/*      */     }
/*      */   }
/*      */   
/*      */   private static <E> Set<E> removeOnlySet(final Set<E> set) {
/*  970 */     return new ForwardingSet<E>()
/*      */       {
/*      */         protected Set<E> delegate() {
/*  973 */           return set;
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean add(E element) {
/*  978 */           throw new UnsupportedOperationException();
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean addAll(Collection<? extends E> es) {
/*  983 */           throw new UnsupportedOperationException();
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private static <E> SortedSet<E> removeOnlySortedSet(final SortedSet<E> set) {
/*  989 */     return new ForwardingSortedSet<E>()
/*      */       {
/*      */         protected SortedSet<E> delegate() {
/*  992 */           return set;
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean add(E element) {
/*  997 */           throw new UnsupportedOperationException();
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean addAll(Collection<? extends E> es) {
/* 1002 */           throw new UnsupportedOperationException();
/*      */         }
/*      */ 
/*      */         
/*      */         public SortedSet<E> headSet(E toElement) {
/* 1007 */           return Maps.removeOnlySortedSet(super.headSet(toElement));
/*      */         }
/*      */ 
/*      */         
/*      */         public SortedSet<E> subSet(E fromElement, E toElement) {
/* 1012 */           return Maps.removeOnlySortedSet(super.subSet(fromElement, toElement));
/*      */         }
/*      */ 
/*      */         
/*      */         public SortedSet<E> tailSet(E fromElement) {
/* 1017 */           return Maps.removeOnlySortedSet(super.tailSet(fromElement));
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   @GwtIncompatible("NavigableSet")
/*      */   private static <E> NavigableSet<E> removeOnlyNavigableSet(final NavigableSet<E> set) {
/* 1024 */     return new ForwardingNavigableSet<E>()
/*      */       {
/*      */         protected NavigableSet<E> delegate() {
/* 1027 */           return set;
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean add(E element) {
/* 1032 */           throw new UnsupportedOperationException();
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean addAll(Collection<? extends E> es) {
/* 1037 */           throw new UnsupportedOperationException();
/*      */         }
/*      */ 
/*      */         
/*      */         public SortedSet<E> headSet(E toElement) {
/* 1042 */           return Maps.removeOnlySortedSet(super.headSet(toElement));
/*      */         }
/*      */ 
/*      */         
/*      */         public SortedSet<E> subSet(E fromElement, E toElement) {
/* 1047 */           return Maps.removeOnlySortedSet(super.subSet(fromElement, toElement));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public SortedSet<E> tailSet(E fromElement) {
/* 1053 */           return Maps.removeOnlySortedSet(super.tailSet(fromElement));
/*      */         }
/*      */ 
/*      */         
/*      */         public NavigableSet<E> headSet(E toElement, boolean inclusive) {
/* 1058 */           return Maps.removeOnlyNavigableSet(super.headSet(toElement, inclusive));
/*      */         }
/*      */ 
/*      */         
/*      */         public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
/* 1063 */           return Maps.removeOnlyNavigableSet(super.tailSet(fromElement, inclusive));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
/* 1069 */           return Maps.removeOnlyNavigableSet(super.subSet(fromElement, fromInclusive, toElement, toInclusive));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public NavigableSet<E> descendingSet() {
/* 1075 */           return Maps.removeOnlyNavigableSet(super.descendingSet());
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <K, V> ImmutableMap<K, V> toMap(Iterable<K> keys, Function<? super K, V> valueFunction) {
/* 1097 */     return toMap(keys.iterator(), valueFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <K, V> ImmutableMap<K, V> toMap(Iterator<K> keys, Function<? super K, V> valueFunction) {
/* 1114 */     Preconditions.checkNotNull(valueFunction);
/*      */     
/* 1116 */     Map<K, V> builder = newLinkedHashMap();
/* 1117 */     while (keys.hasNext()) {
/* 1118 */       K key = keys.next();
/* 1119 */       builder.put(key, (V)valueFunction.apply(key));
/*      */     } 
/* 1121 */     return ImmutableMap.copyOf(builder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterable<V> values, Function<? super V, K> keyFunction) {
/* 1140 */     return uniqueIndex(values.iterator(), keyFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterator<V> values, Function<? super V, K> keyFunction) {
/* 1160 */     Preconditions.checkNotNull(keyFunction);
/* 1161 */     ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
/* 1162 */     while (values.hasNext()) {
/* 1163 */       V value = values.next();
/* 1164 */       builder.put((K)keyFunction.apply(value), value);
/*      */     } 
/* 1166 */     return builder.build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("java.util.Properties")
/*      */   public static ImmutableMap<String, String> fromProperties(Properties properties) {
/* 1185 */     ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
/*      */     
/* 1187 */     for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements(); ) {
/* 1188 */       String key = (String)e.nextElement();
/* 1189 */       builder.put(key, properties.getProperty(key));
/*      */     } 
/*      */     
/* 1192 */     return builder.build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtCompatible(serializable = true)
/*      */   public static <K, V> Map.Entry<K, V> immutableEntry(@Nullable K key, @Nullable V value) {
/* 1207 */     return new ImmutableEntry<K, V>(key, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> Set<Map.Entry<K, V>> unmodifiableEntrySet(Set<Map.Entry<K, V>> entrySet) {
/* 1220 */     return new UnmodifiableEntrySet<K, V>(Collections.unmodifiableSet(entrySet));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> Map.Entry<K, V> unmodifiableEntry(final Map.Entry<? extends K, ? extends V> entry) {
/* 1234 */     Preconditions.checkNotNull(entry);
/* 1235 */     return new AbstractMapEntry<K, V>() {
/*      */         public K getKey() {
/* 1237 */           return (K)entry.getKey();
/*      */         }
/*      */         
/*      */         public V getValue() {
/* 1241 */           return (V)entry.getValue();
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   static class UnmodifiableEntries<K, V>
/*      */     extends ForwardingCollection<Map.Entry<K, V>>
/*      */   {
/*      */     private final Collection<Map.Entry<K, V>> entries;
/*      */     
/*      */     UnmodifiableEntries(Collection<Map.Entry<K, V>> entries) {
/* 1252 */       this.entries = entries;
/*      */     }
/*      */     
/*      */     protected Collection<Map.Entry<K, V>> delegate() {
/* 1256 */       return this.entries;
/*      */     }
/*      */     
/*      */     public Iterator<Map.Entry<K, V>> iterator() {
/* 1260 */       final Iterator<Map.Entry<K, V>> delegate = super.iterator();
/* 1261 */       return new UnmodifiableIterator<Map.Entry<K, V>>()
/*      */         {
/*      */           public boolean hasNext() {
/* 1264 */             return delegate.hasNext();
/*      */           }
/*      */           
/*      */           public Map.Entry<K, V> next() {
/* 1268 */             return Maps.unmodifiableEntry(delegate.next());
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Object[] toArray() {
/* 1276 */       return standardToArray();
/*      */     }
/*      */     
/*      */     public <T> T[] toArray(T[] array) {
/* 1280 */       return (T[])standardToArray((Object[])array);
/*      */     }
/*      */   }
/*      */   
/*      */   static class UnmodifiableEntrySet<K, V>
/*      */     extends UnmodifiableEntries<K, V>
/*      */     implements Set<Map.Entry<K, V>> {
/*      */     UnmodifiableEntrySet(Set<Map.Entry<K, V>> entries) {
/* 1288 */       super(entries);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/* 1294 */       return Sets.equalsImpl(this, object);
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1298 */       return Sets.hashCodeImpl(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <A, B> Converter<A, B> asConverter(BiMap<A, B> bimap) {
/* 1313 */     return new BiMapConverter<A, B>(bimap);
/*      */   }
/*      */   
/*      */   private static final class BiMapConverter<A, B> extends Converter<A, B> implements Serializable { private final BiMap<A, B> bimap;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     BiMapConverter(BiMap<A, B> bimap) {
/* 1320 */       this.bimap = (BiMap<A, B>)Preconditions.checkNotNull(bimap);
/*      */     }
/*      */ 
/*      */     
/*      */     protected B doForward(A a) {
/* 1325 */       return convert(this.bimap, a);
/*      */     }
/*      */ 
/*      */     
/*      */     protected A doBackward(B b) {
/* 1330 */       return convert(this.bimap.inverse(), b);
/*      */     }
/*      */     
/*      */     private static <X, Y> Y convert(BiMap<X, Y> bimap, X input) {
/* 1334 */       Y output = bimap.get(input);
/* 1335 */       Preconditions.checkArgument((output != null), "No non-null mapping present for input: %s", new Object[] { input });
/* 1336 */       return output;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/* 1341 */       if (object instanceof BiMapConverter) {
/* 1342 */         BiMapConverter<?, ?> that = (BiMapConverter<?, ?>)object;
/* 1343 */         return this.bimap.equals(that.bimap);
/*      */       } 
/* 1345 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1350 */       return this.bimap.hashCode();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1356 */       return "Maps.asConverter(" + this.bimap + ")";
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> BiMap<K, V> synchronizedBiMap(BiMap<K, V> bimap) {
/* 1391 */     return Synchronized.biMap(bimap, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> BiMap<K, V> unmodifiableBiMap(BiMap<? extends K, ? extends V> bimap) {
/* 1409 */     return new UnmodifiableBiMap<K, V>(bimap, null);
/*      */   }
/*      */   
/*      */   private static class UnmodifiableBiMap<K, V>
/*      */     extends ForwardingMap<K, V>
/*      */     implements BiMap<K, V>, Serializable {
/*      */     final Map<K, V> unmodifiableMap;
/*      */     final BiMap<? extends K, ? extends V> delegate;
/*      */     BiMap<V, K> inverse;
/*      */     transient Set<V> values;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     UnmodifiableBiMap(BiMap<? extends K, ? extends V> delegate, @Nullable BiMap<V, K> inverse) {
/* 1422 */       this.unmodifiableMap = Collections.unmodifiableMap(delegate);
/* 1423 */       this.delegate = delegate;
/* 1424 */       this.inverse = inverse;
/*      */     }
/*      */     
/*      */     protected Map<K, V> delegate() {
/* 1428 */       return this.unmodifiableMap;
/*      */     }
/*      */ 
/*      */     
/*      */     public V forcePut(K key, V value) {
/* 1433 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public BiMap<V, K> inverse() {
/* 1438 */       BiMap<V, K> result = this.inverse;
/* 1439 */       return (result == null) ? (this.inverse = new UnmodifiableBiMap(this.delegate.inverse(), this)) : result;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<V> values() {
/* 1445 */       Set<V> result = this.values;
/* 1446 */       return (result == null) ? (this.values = Collections.unmodifiableSet(this.delegate.values())) : result;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V1, V2> Map<K, V2> transformValues(Map<K, V1> fromMap, Function<? super V1, V2> function) {
/* 1492 */     return transformEntries(fromMap, asEntryTransformer(function));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V1, V2> SortedMap<K, V2> transformValues(SortedMap<K, V1> fromMap, Function<? super V1, V2> function) {
/* 1536 */     return transformEntries(fromMap, asEntryTransformer(function));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("NavigableMap")
/*      */   public static <K, V1, V2> NavigableMap<K, V2> transformValues(NavigableMap<K, V1> fromMap, Function<? super V1, V2> function) {
/* 1583 */     return transformEntries(fromMap, asEntryTransformer(function));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V1, V2> Map<K, V2> transformEntries(Map<K, V1> fromMap, EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 1640 */     if (fromMap instanceof SortedMap) {
/* 1641 */       return transformEntries((SortedMap<K, V1>)fromMap, transformer);
/*      */     }
/* 1643 */     return new TransformedEntriesMap<K, V1, V2>(fromMap, transformer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V1, V2> SortedMap<K, V2> transformEntries(SortedMap<K, V1> fromMap, EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 1701 */     return Platform.mapsTransformEntriesSortedMap(fromMap, transformer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("NavigableMap")
/*      */   public static <K, V1, V2> NavigableMap<K, V2> transformEntries(NavigableMap<K, V1> fromMap, EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 1761 */     return new TransformedEntriesNavigableMap<K, V1, V2>(fromMap, transformer);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V1, V2> SortedMap<K, V2> transformEntriesIgnoreNavigable(SortedMap<K, V1> fromMap, EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 1767 */     return new TransformedEntriesSortedMap<K, V1, V2>(fromMap, transformer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V1, V2> EntryTransformer<K, V1, V2> asEntryTransformer(final Function<? super V1, V2> function) {
/* 1806 */     Preconditions.checkNotNull(function);
/* 1807 */     return new EntryTransformer<K, V1, V2>()
/*      */       {
/*      */         public V2 transformEntry(K key, V1 value) {
/* 1810 */           return (V2)function.apply(value);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   static <K, V1, V2> Function<V1, V2> asValueToValueFunction(final EntryTransformer<? super K, V1, V2> transformer, final K key) {
/* 1817 */     Preconditions.checkNotNull(transformer);
/* 1818 */     return new Function<V1, V2>()
/*      */       {
/*      */         public V2 apply(@Nullable V1 v1) {
/* 1821 */           return transformer.transformEntry(key, v1);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V1, V2> Function<Map.Entry<K, V1>, V2> asEntryToValueFunction(final EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 1831 */     Preconditions.checkNotNull(transformer);
/* 1832 */     return new Function<Map.Entry<K, V1>, V2>()
/*      */       {
/*      */         public V2 apply(Map.Entry<K, V1> entry) {
/* 1835 */           return (V2)transformer.transformEntry(entry.getKey(), entry.getValue());
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <V2, K, V1> Map.Entry<K, V2> transformEntry(final EntryTransformer<? super K, ? super V1, V2> transformer, final Map.Entry<K, V1> entry) {
/* 1845 */     Preconditions.checkNotNull(transformer);
/* 1846 */     Preconditions.checkNotNull(entry);
/* 1847 */     return new AbstractMapEntry<K, V2>()
/*      */       {
/*      */         public K getKey() {
/* 1850 */           return (K)entry.getKey();
/*      */         }
/*      */ 
/*      */         
/*      */         public V2 getValue() {
/* 1855 */           return (V2)transformer.transformEntry(entry.getKey(), entry.getValue());
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V1, V2> Function<Map.Entry<K, V1>, Map.Entry<K, V2>> asEntryToEntryFunction(final EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 1865 */     Preconditions.checkNotNull(transformer);
/* 1866 */     return new Function<Map.Entry<K, V1>, Map.Entry<K, V2>>()
/*      */       {
/*      */         public Map.Entry<K, V2> apply(Map.Entry<K, V1> entry) {
/* 1869 */           return Maps.transformEntry(transformer, entry);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   static class TransformedEntriesMap<K, V1, V2>
/*      */     extends ImprovedAbstractMap<K, V2>
/*      */   {
/*      */     final Map<K, V1> fromMap;
/*      */     final Maps.EntryTransformer<? super K, ? super V1, V2> transformer;
/*      */     
/*      */     TransformedEntriesMap(Map<K, V1> fromMap, Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 1882 */       this.fromMap = (Map<K, V1>)Preconditions.checkNotNull(fromMap);
/* 1883 */       this.transformer = (Maps.EntryTransformer<? super K, ? super V1, V2>)Preconditions.checkNotNull(transformer);
/*      */     }
/*      */     
/*      */     public int size() {
/* 1887 */       return this.fromMap.size();
/*      */     }
/*      */     
/*      */     public boolean containsKey(Object key) {
/* 1891 */       return this.fromMap.containsKey(key);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V2 get(Object key) {
/* 1897 */       V1 value = this.fromMap.get(key);
/* 1898 */       return (value != null || this.fromMap.containsKey(key)) ? this.transformer.transformEntry((K)key, value) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V2 remove(Object key) {
/* 1906 */       return this.fromMap.containsKey(key) ? this.transformer.transformEntry((K)key, this.fromMap.remove(key)) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1912 */       this.fromMap.clear();
/*      */     }
/*      */     
/*      */     public Set<K> keySet() {
/* 1916 */       return this.fromMap.keySet();
/*      */     }
/*      */ 
/*      */     
/*      */     protected Set<Map.Entry<K, V2>> createEntrySet() {
/* 1921 */       return new Maps.EntrySet<K, V2>() {
/*      */           Map<K, V2> map() {
/* 1923 */             return Maps.TransformedEntriesMap.this;
/*      */           }
/*      */           
/*      */           public Iterator<Map.Entry<K, V2>> iterator() {
/* 1927 */             return Iterators.transform(Maps.TransformedEntriesMap.this.fromMap.entrySet().iterator(), (Function)Maps.asEntryToEntryFunction(Maps.TransformedEntriesMap.this.transformer));
/*      */           }
/*      */         };
/*      */     }
/*      */   }
/*      */   
/*      */   static class TransformedEntriesSortedMap<K, V1, V2>
/*      */     extends TransformedEntriesMap<K, V1, V2>
/*      */     implements SortedMap<K, V2>
/*      */   {
/*      */     protected SortedMap<K, V1> fromMap() {
/* 1938 */       return (SortedMap<K, V1>)this.fromMap;
/*      */     }
/*      */ 
/*      */     
/*      */     TransformedEntriesSortedMap(SortedMap<K, V1> fromMap, Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 1943 */       super(fromMap, transformer);
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1947 */       return fromMap().comparator();
/*      */     }
/*      */     
/*      */     public K firstKey() {
/* 1951 */       return fromMap().firstKey();
/*      */     }
/*      */     
/*      */     public SortedMap<K, V2> headMap(K toKey) {
/* 1955 */       return Maps.transformEntries(fromMap().headMap(toKey), this.transformer);
/*      */     }
/*      */     
/*      */     public K lastKey() {
/* 1959 */       return fromMap().lastKey();
/*      */     }
/*      */     
/*      */     public SortedMap<K, V2> subMap(K fromKey, K toKey) {
/* 1963 */       return Maps.transformEntries(fromMap().subMap(fromKey, toKey), this.transformer);
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedMap<K, V2> tailMap(K fromKey) {
/* 1968 */       return Maps.transformEntries(fromMap().tailMap(fromKey), this.transformer);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @GwtIncompatible("NavigableMap")
/*      */   private static class TransformedEntriesNavigableMap<K, V1, V2>
/*      */     extends TransformedEntriesSortedMap<K, V1, V2>
/*      */     implements NavigableMap<K, V2>
/*      */   {
/*      */     TransformedEntriesNavigableMap(NavigableMap<K, V1> fromMap, Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 1979 */       super(fromMap, transformer);
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V2> ceilingEntry(K key) {
/* 1983 */       return transformEntry(fromMap().ceilingEntry(key));
/*      */     }
/*      */     
/*      */     public K ceilingKey(K key) {
/* 1987 */       return fromMap().ceilingKey(key);
/*      */     }
/*      */     
/*      */     public NavigableSet<K> descendingKeySet() {
/* 1991 */       return fromMap().descendingKeySet();
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V2> descendingMap() {
/* 1995 */       return Maps.transformEntries(fromMap().descendingMap(), this.transformer);
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V2> firstEntry() {
/* 1999 */       return transformEntry(fromMap().firstEntry());
/*      */     }
/*      */     public Map.Entry<K, V2> floorEntry(K key) {
/* 2002 */       return transformEntry(fromMap().floorEntry(key));
/*      */     }
/*      */     
/*      */     public K floorKey(K key) {
/* 2006 */       return fromMap().floorKey(key);
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V2> headMap(K toKey) {
/* 2010 */       return headMap(toKey, false);
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V2> headMap(K toKey, boolean inclusive) {
/* 2014 */       return Maps.transformEntries(fromMap().headMap(toKey, inclusive), this.transformer);
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V2> higherEntry(K key) {
/* 2019 */       return transformEntry(fromMap().higherEntry(key));
/*      */     }
/*      */     
/*      */     public K higherKey(K key) {
/* 2023 */       return fromMap().higherKey(key);
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V2> lastEntry() {
/* 2027 */       return transformEntry(fromMap().lastEntry());
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V2> lowerEntry(K key) {
/* 2031 */       return transformEntry(fromMap().lowerEntry(key));
/*      */     }
/*      */     
/*      */     public K lowerKey(K key) {
/* 2035 */       return fromMap().lowerKey(key);
/*      */     }
/*      */     
/*      */     public NavigableSet<K> navigableKeySet() {
/* 2039 */       return fromMap().navigableKeySet();
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V2> pollFirstEntry() {
/* 2043 */       return transformEntry(fromMap().pollFirstEntry());
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V2> pollLastEntry() {
/* 2047 */       return transformEntry(fromMap().pollLastEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V2> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
/* 2052 */       return Maps.transformEntries(fromMap().subMap(fromKey, fromInclusive, toKey, toInclusive), this.transformer);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V2> subMap(K fromKey, K toKey) {
/* 2058 */       return subMap(fromKey, true, toKey, false);
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V2> tailMap(K fromKey) {
/* 2062 */       return tailMap(fromKey, true);
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V2> tailMap(K fromKey, boolean inclusive) {
/* 2066 */       return Maps.transformEntries(fromMap().tailMap(fromKey, inclusive), this.transformer);
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     private Map.Entry<K, V2> transformEntry(@Nullable Map.Entry<K, V1> entry) {
/* 2072 */       return (entry == null) ? null : Maps.<V2, K, V1>transformEntry(this.transformer, entry);
/*      */     }
/*      */     
/*      */     protected NavigableMap<K, V1> fromMap() {
/* 2076 */       return (NavigableMap<K, V1>)super.fromMap();
/*      */     }
/*      */   }
/*      */   
/*      */   static <K> Predicate<Map.Entry<K, ?>> keyPredicateOnEntries(Predicate<? super K> keyPredicate) {
/* 2081 */     return Predicates.compose(keyPredicate, keyFunction());
/*      */   }
/*      */   
/*      */   static <V> Predicate<Map.Entry<?, V>> valuePredicateOnEntries(Predicate<? super V> valuePredicate) {
/* 2085 */     return Predicates.compose(valuePredicate, valueFunction());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> Map<K, V> filterKeys(Map<K, V> unfiltered, Predicate<? super K> keyPredicate) {
/* 2118 */     if (unfiltered instanceof SortedMap)
/* 2119 */       return filterKeys((SortedMap<K, V>)unfiltered, keyPredicate); 
/* 2120 */     if (unfiltered instanceof BiMap) {
/* 2121 */       return filterKeys((BiMap<K, V>)unfiltered, keyPredicate);
/*      */     }
/* 2123 */     Preconditions.checkNotNull(keyPredicate);
/* 2124 */     Predicate<Map.Entry<K, ?>> entryPredicate = keyPredicateOnEntries(keyPredicate);
/* 2125 */     return (unfiltered instanceof AbstractFilteredMap) ? filterFiltered((AbstractFilteredMap<K, V>)unfiltered, (Predicate)entryPredicate) : new FilteredKeyMap<K, V>((Map<K, V>)Preconditions.checkNotNull(unfiltered), keyPredicate, (Predicate)entryPredicate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> SortedMap<K, V> filterKeys(SortedMap<K, V> unfiltered, Predicate<? super K> keyPredicate) {
/* 2165 */     return filterEntries(unfiltered, (Predicate)keyPredicateOnEntries(keyPredicate));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("NavigableMap")
/*      */   public static <K, V> NavigableMap<K, V> filterKeys(NavigableMap<K, V> unfiltered, Predicate<? super K> keyPredicate) {
/* 2203 */     return filterEntries(unfiltered, (Predicate)keyPredicateOnEntries(keyPredicate));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> BiMap<K, V> filterKeys(BiMap<K, V> unfiltered, Predicate<? super K> keyPredicate) {
/* 2233 */     Preconditions.checkNotNull(keyPredicate);
/* 2234 */     return filterEntries(unfiltered, (Predicate)keyPredicateOnEntries(keyPredicate));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> Map<K, V> filterValues(Map<K, V> unfiltered, Predicate<? super V> valuePredicate) {
/* 2268 */     if (unfiltered instanceof SortedMap)
/* 2269 */       return filterValues((SortedMap<K, V>)unfiltered, valuePredicate); 
/* 2270 */     if (unfiltered instanceof BiMap) {
/* 2271 */       return filterValues((BiMap<K, V>)unfiltered, valuePredicate);
/*      */     }
/* 2273 */     return filterEntries(unfiltered, (Predicate)valuePredicateOnEntries(valuePredicate));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> SortedMap<K, V> filterValues(SortedMap<K, V> unfiltered, Predicate<? super V> valuePredicate) {
/* 2309 */     return filterEntries(unfiltered, (Predicate)valuePredicateOnEntries(valuePredicate));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("NavigableMap")
/*      */   public static <K, V> NavigableMap<K, V> filterValues(NavigableMap<K, V> unfiltered, Predicate<? super V> valuePredicate) {
/* 2346 */     return filterEntries(unfiltered, (Predicate)valuePredicateOnEntries(valuePredicate));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> BiMap<K, V> filterValues(BiMap<K, V> unfiltered, Predicate<? super V> valuePredicate) {
/* 2379 */     return filterEntries(unfiltered, (Predicate)valuePredicateOnEntries(valuePredicate));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> Map<K, V> filterEntries(Map<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 2413 */     if (unfiltered instanceof SortedMap)
/* 2414 */       return filterEntries((SortedMap<K, V>)unfiltered, entryPredicate); 
/* 2415 */     if (unfiltered instanceof BiMap) {
/* 2416 */       return filterEntries((BiMap<K, V>)unfiltered, entryPredicate);
/*      */     }
/* 2418 */     Preconditions.checkNotNull(entryPredicate);
/* 2419 */     return (unfiltered instanceof AbstractFilteredMap) ? filterFiltered((AbstractFilteredMap<K, V>)unfiltered, entryPredicate) : new FilteredEntryMap<K, V>((Map<K, V>)Preconditions.checkNotNull(unfiltered), entryPredicate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> SortedMap<K, V> filterEntries(SortedMap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 2458 */     return Platform.mapsFilterSortedMap(unfiltered, entryPredicate);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> SortedMap<K, V> filterSortedIgnoreNavigable(SortedMap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 2464 */     Preconditions.checkNotNull(entryPredicate);
/* 2465 */     return (unfiltered instanceof FilteredEntrySortedMap) ? filterFiltered((FilteredEntrySortedMap<K, V>)unfiltered, entryPredicate) : new FilteredEntrySortedMap<K, V>((SortedMap<K, V>)Preconditions.checkNotNull(unfiltered), entryPredicate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("NavigableMap")
/*      */   public static <K, V> NavigableMap<K, V> filterEntries(NavigableMap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 2505 */     Preconditions.checkNotNull(entryPredicate);
/* 2506 */     return (unfiltered instanceof FilteredEntryNavigableMap) ? filterFiltered((FilteredEntryNavigableMap<K, V>)unfiltered, entryPredicate) : new FilteredEntryNavigableMap<K, V>((NavigableMap<K, V>)Preconditions.checkNotNull(unfiltered), entryPredicate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> BiMap<K, V> filterEntries(BiMap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 2540 */     Preconditions.checkNotNull(unfiltered);
/* 2541 */     Preconditions.checkNotNull(entryPredicate);
/* 2542 */     return (unfiltered instanceof FilteredEntryBiMap) ? filterFiltered((FilteredEntryBiMap<K, V>)unfiltered, entryPredicate) : new FilteredEntryBiMap<K, V>(unfiltered, entryPredicate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <K, V> Map<K, V> filterFiltered(AbstractFilteredMap<K, V> map, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 2553 */     return new FilteredEntryMap<K, V>(map.unfiltered, Predicates.and(map.predicate, entryPredicate));
/*      */   }
/*      */ 
/*      */   
/*      */   private static abstract class AbstractFilteredMap<K, V>
/*      */     extends ImprovedAbstractMap<K, V>
/*      */   {
/*      */     final Map<K, V> unfiltered;
/*      */     final Predicate<? super Map.Entry<K, V>> predicate;
/*      */     
/*      */     AbstractFilteredMap(Map<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> predicate) {
/* 2564 */       this.unfiltered = unfiltered;
/* 2565 */       this.predicate = predicate;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean apply(@Nullable Object key, @Nullable V value) {
/* 2572 */       K k = (K)key;
/* 2573 */       return this.predicate.apply(Maps.immutableEntry(k, value));
/*      */     }
/*      */     
/*      */     public V put(K key, V value) {
/* 2577 */       Preconditions.checkArgument(apply(key, value));
/* 2578 */       return this.unfiltered.put(key, value);
/*      */     }
/*      */     
/*      */     public void putAll(Map<? extends K, ? extends V> map) {
/* 2582 */       for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
/* 2583 */         Preconditions.checkArgument(apply(entry.getKey(), entry.getValue()));
/*      */       }
/* 2585 */       this.unfiltered.putAll(map);
/*      */     }
/*      */     
/*      */     public boolean containsKey(Object key) {
/* 2589 */       return (this.unfiltered.containsKey(key) && apply(key, this.unfiltered.get(key)));
/*      */     }
/*      */     
/*      */     public V get(Object key) {
/* 2593 */       V value = this.unfiltered.get(key);
/* 2594 */       return (value != null && apply(key, value)) ? value : null;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 2598 */       return entrySet().isEmpty();
/*      */     }
/*      */     
/*      */     public V remove(Object key) {
/* 2602 */       return containsKey(key) ? this.unfiltered.remove(key) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     Collection<V> createValues() {
/* 2607 */       return new Maps.FilteredMapValues<K, V>(this, this.unfiltered, this.predicate);
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class FilteredMapValues<K, V>
/*      */     extends Values<K, V> {
/*      */     Map<K, V> unfiltered;
/*      */     Predicate<? super Map.Entry<K, V>> predicate;
/*      */     
/*      */     FilteredMapValues(Map<K, V> filteredMap, Map<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> predicate) {
/* 2617 */       super(filteredMap);
/* 2618 */       this.unfiltered = unfiltered;
/* 2619 */       this.predicate = predicate;
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 2623 */       return (Iterables.removeFirstMatching(this.unfiltered.entrySet(), Predicates.and(this.predicate, Maps.valuePredicateOnEntries(Predicates.equalTo(o)))) != null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean removeIf(Predicate<? super V> valuePredicate) {
/* 2629 */       return Iterables.removeIf(this.unfiltered.entrySet(), Predicates.and(this.predicate, Maps.valuePredicateOnEntries(valuePredicate)));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(Collection<?> collection) {
/* 2634 */       return removeIf(Predicates.in(collection));
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> collection) {
/* 2638 */       return removeIf(Predicates.not(Predicates.in(collection)));
/*      */     }
/*      */ 
/*      */     
/*      */     public Object[] toArray() {
/* 2643 */       return Lists.<V>newArrayList(iterator()).toArray();
/*      */     }
/*      */     
/*      */     public <T> T[] toArray(T[] array) {
/* 2647 */       return (T[])Lists.<V>newArrayList(iterator()).toArray((Object[])array);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class FilteredKeyMap<K, V>
/*      */     extends AbstractFilteredMap<K, V> {
/*      */     Predicate<? super K> keyPredicate;
/*      */     
/*      */     FilteredKeyMap(Map<K, V> unfiltered, Predicate<? super K> keyPredicate, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 2656 */       super(unfiltered, entryPredicate);
/* 2657 */       this.keyPredicate = keyPredicate;
/*      */     }
/*      */ 
/*      */     
/*      */     protected Set<Map.Entry<K, V>> createEntrySet() {
/* 2662 */       return Sets.filter(this.unfiltered.entrySet(), this.predicate);
/*      */     }
/*      */ 
/*      */     
/*      */     Set<K> createKeySet() {
/* 2667 */       return Sets.filter(this.unfiltered.keySet(), this.keyPredicate);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object key) {
/* 2675 */       return (this.unfiltered.containsKey(key) && this.keyPredicate.apply(key));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class FilteredEntryMap<K, V>
/*      */     extends AbstractFilteredMap<K, V>
/*      */   {
/*      */     final Set<Map.Entry<K, V>> filteredEntrySet;
/*      */ 
/*      */     
/*      */     FilteredEntryMap(Map<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 2688 */       super(unfiltered, entryPredicate);
/* 2689 */       this.filteredEntrySet = Sets.filter(unfiltered.entrySet(), this.predicate);
/*      */     }
/*      */ 
/*      */     
/*      */     protected Set<Map.Entry<K, V>> createEntrySet() {
/* 2694 */       return new EntrySet();
/*      */     }
/*      */     
/*      */     private class EntrySet extends ForwardingSet<Map.Entry<K, V>> {
/*      */       protected Set<Map.Entry<K, V>> delegate() {
/* 2699 */         return Maps.FilteredEntryMap.this.filteredEntrySet;
/*      */       }
/*      */       private EntrySet() {}
/*      */       public Iterator<Map.Entry<K, V>> iterator() {
/* 2703 */         return new TransformedIterator<Map.Entry<K, V>, Map.Entry<K, V>>(Maps.FilteredEntryMap.this.filteredEntrySet.iterator())
/*      */           {
/*      */             Map.Entry<K, V> transform(final Map.Entry<K, V> entry) {
/* 2706 */               return new ForwardingMapEntry<K, V>()
/*      */                 {
/*      */                   protected Map.Entry<K, V> delegate() {
/* 2709 */                     return entry;
/*      */                   }
/*      */ 
/*      */                   
/*      */                   public V setValue(V newValue) {
/* 2714 */                     Preconditions.checkArgument(Maps.FilteredEntryMap.this.apply(getKey(), newValue));
/* 2715 */                     return super.setValue(newValue);
/*      */                   }
/*      */                 };
/*      */             }
/*      */           };
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     Set<K> createKeySet() {
/* 2725 */       return new KeySet();
/*      */     }
/*      */     
/*      */     class KeySet extends Maps.KeySet<K, V> {
/*      */       KeySet() {
/* 2730 */         super(Maps.FilteredEntryMap.this);
/*      */       }
/*      */       
/*      */       public boolean remove(Object o) {
/* 2734 */         if (Maps.FilteredEntryMap.this.containsKey(o)) {
/* 2735 */           Maps.FilteredEntryMap.this.unfiltered.remove(o);
/* 2736 */           return true;
/*      */         } 
/* 2738 */         return false;
/*      */       }
/*      */       
/*      */       private boolean removeIf(Predicate<? super K> keyPredicate) {
/* 2742 */         return Iterables.removeIf(Maps.FilteredEntryMap.this.unfiltered.entrySet(), Predicates.and(Maps.FilteredEntryMap.this.predicate, Maps.keyPredicateOnEntries(keyPredicate)));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean removeAll(Collection<?> c) {
/* 2748 */         return removeIf(Predicates.in(c));
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean retainAll(Collection<?> c) {
/* 2753 */         return removeIf(Predicates.not(Predicates.in(c)));
/*      */       }
/*      */ 
/*      */       
/*      */       public Object[] toArray() {
/* 2758 */         return Lists.<K>newArrayList(iterator()).toArray();
/*      */       }
/*      */       
/*      */       public <T> T[] toArray(T[] array) {
/* 2762 */         return (T[])Lists.<K>newArrayList(iterator()).toArray((Object[])array);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <K, V> SortedMap<K, V> filterFiltered(FilteredEntrySortedMap<K, V> map, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 2774 */     Predicate<Map.Entry<K, V>> predicate = Predicates.and(map.predicate, entryPredicate);
/*      */     
/* 2776 */     return new FilteredEntrySortedMap<K, V>(map.sortedMap(), predicate);
/*      */   }
/*      */   
/*      */   private static class FilteredEntrySortedMap<K, V>
/*      */     extends FilteredEntryMap<K, V>
/*      */     implements SortedMap<K, V>
/*      */   {
/*      */     FilteredEntrySortedMap(SortedMap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 2784 */       super(unfiltered, entryPredicate);
/*      */     }
/*      */     
/*      */     SortedMap<K, V> sortedMap() {
/* 2788 */       return (SortedMap<K, V>)this.unfiltered;
/*      */     }
/*      */     
/*      */     public SortedSet<K> keySet() {
/* 2792 */       return (SortedSet<K>)super.keySet();
/*      */     }
/*      */ 
/*      */     
/*      */     SortedSet<K> createKeySet() {
/* 2797 */       return new SortedKeySet();
/*      */     }
/*      */     
/*      */     class SortedKeySet
/*      */       extends Maps.FilteredEntryMap<K, V>.KeySet implements SortedSet<K> {
/*      */       public Comparator<? super K> comparator() {
/* 2803 */         return Maps.FilteredEntrySortedMap.this.sortedMap().comparator();
/*      */       }
/*      */ 
/*      */       
/*      */       public SortedSet<K> subSet(K fromElement, K toElement) {
/* 2808 */         return (SortedSet<K>)Maps.FilteredEntrySortedMap.this.subMap(fromElement, toElement).keySet();
/*      */       }
/*      */ 
/*      */       
/*      */       public SortedSet<K> headSet(K toElement) {
/* 2813 */         return (SortedSet<K>)Maps.FilteredEntrySortedMap.this.headMap(toElement).keySet();
/*      */       }
/*      */ 
/*      */       
/*      */       public SortedSet<K> tailSet(K fromElement) {
/* 2818 */         return (SortedSet<K>)Maps.FilteredEntrySortedMap.this.tailMap(fromElement).keySet();
/*      */       }
/*      */ 
/*      */       
/*      */       public K first() {
/* 2823 */         return (K)Maps.FilteredEntrySortedMap.this.firstKey();
/*      */       }
/*      */ 
/*      */       
/*      */       public K last() {
/* 2828 */         return (K)Maps.FilteredEntrySortedMap.this.lastKey();
/*      */       }
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 2833 */       return sortedMap().comparator();
/*      */     }
/*      */ 
/*      */     
/*      */     public K firstKey() {
/* 2838 */       return keySet().iterator().next();
/*      */     }
/*      */     
/*      */     public K lastKey() {
/* 2842 */       SortedMap<K, V> headMap = sortedMap();
/*      */       
/*      */       while (true) {
/* 2845 */         K key = headMap.lastKey();
/* 2846 */         if (apply(key, this.unfiltered.get(key))) {
/* 2847 */           return key;
/*      */         }
/* 2849 */         headMap = sortedMap().headMap(key);
/*      */       } 
/*      */     }
/*      */     
/*      */     public SortedMap<K, V> headMap(K toKey) {
/* 2854 */       return new FilteredEntrySortedMap(sortedMap().headMap(toKey), this.predicate);
/*      */     }
/*      */     
/*      */     public SortedMap<K, V> subMap(K fromKey, K toKey) {
/* 2858 */       return new FilteredEntrySortedMap(sortedMap().subMap(fromKey, toKey), this.predicate);
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedMap<K, V> tailMap(K fromKey) {
/* 2863 */       return new FilteredEntrySortedMap(sortedMap().tailMap(fromKey), this.predicate);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("NavigableMap")
/*      */   private static <K, V> NavigableMap<K, V> filterFiltered(FilteredEntryNavigableMap<K, V> map, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 2876 */     Predicate<Map.Entry<K, V>> predicate = Predicates.and(map.entryPredicate, entryPredicate);
/*      */     
/* 2878 */     return new FilteredEntryNavigableMap<K, V>(map.unfiltered, predicate);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("NavigableMap")
/*      */   private static class FilteredEntryNavigableMap<K, V>
/*      */     extends AbstractNavigableMap<K, V>
/*      */   {
/*      */     private final NavigableMap<K, V> unfiltered;
/*      */     
/*      */     private final Predicate<? super Map.Entry<K, V>> entryPredicate;
/*      */     
/*      */     private final Map<K, V> filteredDelegate;
/*      */ 
/*      */     
/*      */     FilteredEntryNavigableMap(NavigableMap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 2895 */       this.unfiltered = (NavigableMap<K, V>)Preconditions.checkNotNull(unfiltered);
/* 2896 */       this.entryPredicate = entryPredicate;
/* 2897 */       this.filteredDelegate = new Maps.FilteredEntryMap<K, V>(unfiltered, entryPredicate);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 2902 */       return this.unfiltered.comparator();
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableSet<K> navigableKeySet() {
/* 2907 */       return new Maps.NavigableKeySet<K, V>(this)
/*      */         {
/*      */           public boolean removeAll(Collection<?> c) {
/* 2910 */             return Iterators.removeIf(Maps.FilteredEntryNavigableMap.this.unfiltered.entrySet().iterator(), Predicates.and(Maps.FilteredEntryNavigableMap.this.entryPredicate, Maps.keyPredicateOnEntries(Predicates.in(c))));
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean retainAll(Collection<?> c) {
/* 2916 */             return Iterators.removeIf(Maps.FilteredEntryNavigableMap.this.unfiltered.entrySet().iterator(), Predicates.and(Maps.FilteredEntryNavigableMap.this.entryPredicate, Maps.keyPredicateOnEntries(Predicates.not(Predicates.in(c)))));
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Collection<V> values() {
/* 2924 */       return new Maps.FilteredMapValues<K, V>(this, this.unfiltered, this.entryPredicate);
/*      */     }
/*      */ 
/*      */     
/*      */     Iterator<Map.Entry<K, V>> entryIterator() {
/* 2929 */       return Iterators.filter(this.unfiltered.entrySet().iterator(), this.entryPredicate);
/*      */     }
/*      */ 
/*      */     
/*      */     Iterator<Map.Entry<K, V>> descendingEntryIterator() {
/* 2934 */       return Iterators.filter(this.unfiltered.descendingMap().entrySet().iterator(), this.entryPredicate);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 2939 */       return this.filteredDelegate.size();
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public V get(@Nullable Object key) {
/* 2945 */       return this.filteredDelegate.get(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(@Nullable Object key) {
/* 2950 */       return this.filteredDelegate.containsKey(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public V put(K key, V value) {
/* 2955 */       return this.filteredDelegate.put(key, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public V remove(@Nullable Object key) {
/* 2960 */       return this.filteredDelegate.remove(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public void putAll(Map<? extends K, ? extends V> m) {
/* 2965 */       this.filteredDelegate.putAll(m);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 2970 */       this.filteredDelegate.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 2975 */       return this.filteredDelegate.entrySet();
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> pollFirstEntry() {
/* 2980 */       return Iterables.<Map.Entry<K, V>>removeFirstMatching(this.unfiltered.entrySet(), this.entryPredicate);
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> pollLastEntry() {
/* 2985 */       return Iterables.<Map.Entry<K, V>>removeFirstMatching(this.unfiltered.descendingMap().entrySet(), this.entryPredicate);
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> descendingMap() {
/* 2990 */       return Maps.filterEntries(this.unfiltered.descendingMap(), this.entryPredicate);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
/* 2996 */       return Maps.filterEntries(this.unfiltered.subMap(fromKey, fromInclusive, toKey, toInclusive), this.entryPredicate);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
/* 3002 */       return Maps.filterEntries(this.unfiltered.headMap(toKey, inclusive), this.entryPredicate);
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
/* 3007 */       return Maps.filterEntries(this.unfiltered.tailMap(fromKey, inclusive), this.entryPredicate);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <K, V> BiMap<K, V> filterFiltered(FilteredEntryBiMap<K, V> map, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 3017 */     Predicate<Map.Entry<K, V>> predicate = Predicates.and(map.predicate, entryPredicate);
/* 3018 */     return new FilteredEntryBiMap<K, V>(map.unfiltered(), predicate);
/*      */   }
/*      */   
/*      */   static final class FilteredEntryBiMap<K, V>
/*      */     extends FilteredEntryMap<K, V>
/*      */     implements BiMap<K, V> {
/*      */     private final BiMap<V, K> inverse;
/*      */     
/*      */     private static <K, V> Predicate<Map.Entry<V, K>> inversePredicate(final Predicate<? super Map.Entry<K, V>> forwardPredicate) {
/* 3027 */       return new Predicate<Map.Entry<V, K>>()
/*      */         {
/*      */           public boolean apply(Map.Entry<V, K> input) {
/* 3030 */             return forwardPredicate.apply(Maps.immutableEntry(input.getValue(), input.getKey()));
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     FilteredEntryBiMap(BiMap<K, V> delegate, Predicate<? super Map.Entry<K, V>> predicate) {
/* 3038 */       super(delegate, predicate);
/* 3039 */       this.inverse = new FilteredEntryBiMap(delegate.inverse(), inversePredicate(predicate), this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private FilteredEntryBiMap(BiMap<K, V> delegate, Predicate<? super Map.Entry<K, V>> predicate, BiMap<V, K> inverse) {
/* 3046 */       super(delegate, predicate);
/* 3047 */       this.inverse = inverse;
/*      */     }
/*      */     
/*      */     BiMap<K, V> unfiltered() {
/* 3051 */       return (BiMap<K, V>)this.unfiltered;
/*      */     }
/*      */ 
/*      */     
/*      */     public V forcePut(@Nullable K key, @Nullable V value) {
/* 3056 */       Preconditions.checkArgument(apply(key, value));
/* 3057 */       return unfiltered().forcePut(key, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public BiMap<V, K> inverse() {
/* 3062 */       return this.inverse;
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<V> values() {
/* 3067 */       return this.inverse.keySet();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("NavigableMap")
/*      */   public static <K, V> NavigableMap<K, V> unmodifiableNavigableMap(NavigableMap<K, V> map) {
/* 3085 */     Preconditions.checkNotNull(map);
/* 3086 */     if (map instanceof UnmodifiableNavigableMap) {
/* 3087 */       return map;
/*      */     }
/* 3089 */     return new UnmodifiableNavigableMap<K, V>(map);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private static <K, V> Map.Entry<K, V> unmodifiableOrNull(@Nullable Map.Entry<K, V> entry) {
/* 3094 */     return (entry == null) ? null : unmodifiableEntry(entry);
/*      */   }
/*      */   
/*      */   @GwtIncompatible("NavigableMap")
/*      */   static class UnmodifiableNavigableMap<K, V> extends ForwardingSortedMap<K, V> implements NavigableMap<K, V>, Serializable {
/*      */     private final NavigableMap<K, V> delegate;
/*      */     private transient UnmodifiableNavigableMap<K, V> descendingMap;
/*      */     
/*      */     UnmodifiableNavigableMap(NavigableMap<K, V> delegate) {
/* 3103 */       this.delegate = delegate;
/*      */     }
/*      */ 
/*      */     
/*      */     UnmodifiableNavigableMap(NavigableMap<K, V> delegate, UnmodifiableNavigableMap<K, V> descendingMap) {
/* 3108 */       this.delegate = delegate;
/* 3109 */       this.descendingMap = descendingMap;
/*      */     }
/*      */ 
/*      */     
/*      */     protected SortedMap<K, V> delegate() {
/* 3114 */       return Collections.unmodifiableSortedMap(this.delegate);
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> lowerEntry(K key) {
/* 3119 */       return Maps.unmodifiableOrNull(this.delegate.lowerEntry(key));
/*      */     }
/*      */ 
/*      */     
/*      */     public K lowerKey(K key) {
/* 3124 */       return this.delegate.lowerKey(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> floorEntry(K key) {
/* 3129 */       return Maps.unmodifiableOrNull(this.delegate.floorEntry(key));
/*      */     }
/*      */ 
/*      */     
/*      */     public K floorKey(K key) {
/* 3134 */       return this.delegate.floorKey(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> ceilingEntry(K key) {
/* 3139 */       return Maps.unmodifiableOrNull(this.delegate.ceilingEntry(key));
/*      */     }
/*      */ 
/*      */     
/*      */     public K ceilingKey(K key) {
/* 3144 */       return this.delegate.ceilingKey(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> higherEntry(K key) {
/* 3149 */       return Maps.unmodifiableOrNull(this.delegate.higherEntry(key));
/*      */     }
/*      */ 
/*      */     
/*      */     public K higherKey(K key) {
/* 3154 */       return this.delegate.higherKey(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> firstEntry() {
/* 3159 */       return Maps.unmodifiableOrNull(this.delegate.firstEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> lastEntry() {
/* 3164 */       return Maps.unmodifiableOrNull(this.delegate.lastEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public final Map.Entry<K, V> pollFirstEntry() {
/* 3169 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public final Map.Entry<K, V> pollLastEntry() {
/* 3174 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> descendingMap() {
/* 3181 */       UnmodifiableNavigableMap<K, V> result = this.descendingMap;
/* 3182 */       return (result == null) ? (this.descendingMap = new UnmodifiableNavigableMap(this.delegate.descendingMap(), this)) : result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 3189 */       return navigableKeySet();
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableSet<K> navigableKeySet() {
/* 3194 */       return Sets.unmodifiableNavigableSet(this.delegate.navigableKeySet());
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableSet<K> descendingKeySet() {
/* 3199 */       return Sets.unmodifiableNavigableSet(this.delegate.descendingKeySet());
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedMap<K, V> subMap(K fromKey, K toKey) {
/* 3204 */       return subMap(fromKey, true, toKey, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedMap<K, V> headMap(K toKey) {
/* 3209 */       return headMap(toKey, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedMap<K, V> tailMap(K fromKey) {
/* 3214 */       return tailMap(fromKey, true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
/* 3221 */       return Maps.unmodifiableNavigableMap(this.delegate.subMap(fromKey, fromInclusive, toKey, toInclusive));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
/* 3230 */       return Maps.unmodifiableNavigableMap(this.delegate.headMap(toKey, inclusive));
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
/* 3235 */       return Maps.unmodifiableNavigableMap(this.delegate.tailMap(fromKey, inclusive));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("NavigableMap")
/*      */   public static <K, V> NavigableMap<K, V> synchronizedNavigableMap(NavigableMap<K, V> navigableMap) {
/* 3290 */     return Synchronized.navigableMap(navigableMap);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtCompatible
/*      */   static abstract class ImprovedAbstractMap<K, V>
/*      */     extends AbstractMap<K, V>
/*      */   {
/*      */     private transient Set<Map.Entry<K, V>> entrySet;
/*      */ 
/*      */     
/*      */     private transient Set<K> keySet;
/*      */ 
/*      */     
/*      */     private transient Collection<V> values;
/*      */ 
/*      */     
/*      */     abstract Set<Map.Entry<K, V>> createEntrySet();
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 3312 */       Set<Map.Entry<K, V>> result = this.entrySet;
/* 3313 */       return (result == null) ? (this.entrySet = createEntrySet()) : result;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 3319 */       Set<K> result = this.keySet;
/* 3320 */       return (result == null) ? (this.keySet = createKeySet()) : result;
/*      */     }
/*      */     
/*      */     Set<K> createKeySet() {
/* 3324 */       return new Maps.KeySet<K, V>(this);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Collection<V> values() {
/* 3330 */       Collection<V> result = this.values;
/* 3331 */       return (result == null) ? (this.values = createValues()) : result;
/*      */     }
/*      */     
/*      */     Collection<V> createValues() {
/* 3335 */       return new Maps.Values<K, V>(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <V> V safeGet(Map<?, V> map, @Nullable Object key) {
/* 3344 */     Preconditions.checkNotNull(map);
/*      */     try {
/* 3346 */       return map.get(key);
/* 3347 */     } catch (ClassCastException e) {
/* 3348 */       return null;
/* 3349 */     } catch (NullPointerException e) {
/* 3350 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean safeContainsKey(Map<?, ?> map, Object key) {
/* 3359 */     Preconditions.checkNotNull(map);
/*      */     try {
/* 3361 */       return map.containsKey(key);
/* 3362 */     } catch (ClassCastException e) {
/* 3363 */       return false;
/* 3364 */     } catch (NullPointerException e) {
/* 3365 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <V> V safeRemove(Map<?, V> map, Object key) {
/* 3374 */     Preconditions.checkNotNull(map);
/*      */     try {
/* 3376 */       return map.remove(key);
/* 3377 */     } catch (ClassCastException e) {
/* 3378 */       return null;
/* 3379 */     } catch (NullPointerException e) {
/* 3380 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean containsKeyImpl(Map<?, ?> map, @Nullable Object key) {
/* 3388 */     return Iterators.contains(keyIterator(map.entrySet().iterator()), key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean containsValueImpl(Map<?, ?> map, @Nullable Object value) {
/* 3395 */     return Iterators.contains(valueIterator(map.entrySet().iterator()), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> boolean containsEntryImpl(Collection<Map.Entry<K, V>> c, Object o) {
/* 3412 */     if (!(o instanceof Map.Entry)) {
/* 3413 */       return false;
/*      */     }
/* 3415 */     return c.contains(unmodifiableEntry((Map.Entry<?, ?>)o));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> boolean removeEntryImpl(Collection<Map.Entry<K, V>> c, Object o) {
/* 3432 */     if (!(o instanceof Map.Entry)) {
/* 3433 */       return false;
/*      */     }
/* 3435 */     return c.remove(unmodifiableEntry((Map.Entry<?, ?>)o));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean equalsImpl(Map<?, ?> map, Object object) {
/* 3442 */     if (map == object)
/* 3443 */       return true; 
/* 3444 */     if (object instanceof Map) {
/* 3445 */       Map<?, ?> o = (Map<?, ?>)object;
/* 3446 */       return map.entrySet().equals(o.entrySet());
/*      */     } 
/* 3448 */     return false;
/*      */   }
/*      */   
/* 3451 */   static final Joiner.MapJoiner STANDARD_JOINER = Collections2.STANDARD_JOINER.withKeyValueSeparator("=");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String toStringImpl(Map<?, ?> map) {
/* 3458 */     StringBuilder sb = Collections2.newStringBuilderForCollection(map.size()).append('{');
/*      */     
/* 3460 */     STANDARD_JOINER.appendTo(sb, map);
/* 3461 */     return sb.append('}').toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> void putAllImpl(Map<K, V> self, Map<? extends K, ? extends V> map) {
/* 3469 */     for (Map.Entry<? extends K, ? extends V> entry : map.entrySet())
/* 3470 */       self.put(entry.getKey(), entry.getValue()); 
/*      */   }
/*      */   
/*      */   static class KeySet<K, V>
/*      */     extends Sets.ImprovedAbstractSet<K> {
/*      */     final Map<K, V> map;
/*      */     
/*      */     KeySet(Map<K, V> map) {
/* 3478 */       this.map = (Map<K, V>)Preconditions.checkNotNull(map);
/*      */     }
/*      */     
/*      */     Map<K, V> map() {
/* 3482 */       return this.map;
/*      */     }
/*      */     
/*      */     public Iterator<K> iterator() {
/* 3486 */       return Maps.keyIterator(map().entrySet().iterator());
/*      */     }
/*      */     
/*      */     public int size() {
/* 3490 */       return map().size();
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 3494 */       return map().isEmpty();
/*      */     }
/*      */     
/*      */     public boolean contains(Object o) {
/* 3498 */       return map().containsKey(o);
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 3502 */       if (contains(o)) {
/* 3503 */         map().remove(o);
/* 3504 */         return true;
/*      */       } 
/* 3506 */       return false;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 3510 */       map().clear();
/*      */     }
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   static <K> K keyOrNull(@Nullable Map.Entry<K, ?> entry) {
/* 3516 */     return (entry == null) ? null : entry.getKey();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   static <V> V valueOrNull(@Nullable Map.Entry<?, V> entry) {
/* 3521 */     return (entry == null) ? null : entry.getValue();
/*      */   }
/*      */   
/*      */   static class SortedKeySet<K, V> extends KeySet<K, V> implements SortedSet<K> {
/*      */     SortedKeySet(SortedMap<K, V> map) {
/* 3526 */       super(map);
/*      */     }
/*      */ 
/*      */     
/*      */     SortedMap<K, V> map() {
/* 3531 */       return (SortedMap<K, V>)super.map();
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 3536 */       return map().comparator();
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedSet<K> subSet(K fromElement, K toElement) {
/* 3541 */       return new SortedKeySet(map().subMap(fromElement, toElement));
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedSet<K> headSet(K toElement) {
/* 3546 */       return new SortedKeySet(map().headMap(toElement));
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedSet<K> tailSet(K fromElement) {
/* 3551 */       return new SortedKeySet(map().tailMap(fromElement));
/*      */     }
/*      */ 
/*      */     
/*      */     public K first() {
/* 3556 */       return map().firstKey();
/*      */     }
/*      */ 
/*      */     
/*      */     public K last() {
/* 3561 */       return map().lastKey();
/*      */     }
/*      */   }
/*      */   
/*      */   @GwtIncompatible("NavigableMap")
/*      */   static class NavigableKeySet<K, V> extends SortedKeySet<K, V> implements NavigableSet<K> {
/*      */     NavigableKeySet(NavigableMap<K, V> map) {
/* 3568 */       super(map);
/*      */     }
/*      */ 
/*      */     
/*      */     NavigableMap<K, V> map() {
/* 3573 */       return (NavigableMap<K, V>)this.map;
/*      */     }
/*      */ 
/*      */     
/*      */     public K lower(K e) {
/* 3578 */       return map().lowerKey(e);
/*      */     }
/*      */ 
/*      */     
/*      */     public K floor(K e) {
/* 3583 */       return map().floorKey(e);
/*      */     }
/*      */ 
/*      */     
/*      */     public K ceiling(K e) {
/* 3588 */       return map().ceilingKey(e);
/*      */     }
/*      */ 
/*      */     
/*      */     public K higher(K e) {
/* 3593 */       return map().higherKey(e);
/*      */     }
/*      */ 
/*      */     
/*      */     public K pollFirst() {
/* 3598 */       return Maps.keyOrNull(map().pollFirstEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public K pollLast() {
/* 3603 */       return Maps.keyOrNull(map().pollLastEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableSet<K> descendingSet() {
/* 3608 */       return map().descendingKeySet();
/*      */     }
/*      */ 
/*      */     
/*      */     public Iterator<K> descendingIterator() {
/* 3613 */       return descendingSet().iterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableSet<K> subSet(K fromElement, boolean fromInclusive, K toElement, boolean toInclusive) {
/* 3622 */       return map().subMap(fromElement, fromInclusive, toElement, toInclusive).navigableKeySet();
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableSet<K> headSet(K toElement, boolean inclusive) {
/* 3627 */       return map().headMap(toElement, inclusive).navigableKeySet();
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableSet<K> tailSet(K fromElement, boolean inclusive) {
/* 3632 */       return map().tailMap(fromElement, inclusive).navigableKeySet();
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedSet<K> subSet(K fromElement, K toElement) {
/* 3637 */       return subSet(fromElement, true, toElement, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedSet<K> headSet(K toElement) {
/* 3642 */       return headSet(toElement, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedSet<K> tailSet(K fromElement) {
/* 3647 */       return tailSet(fromElement, true);
/*      */     }
/*      */   }
/*      */   
/*      */   static class Values<K, V> extends AbstractCollection<V> {
/*      */     final Map<K, V> map;
/*      */     
/*      */     Values(Map<K, V> map) {
/* 3655 */       this.map = (Map<K, V>)Preconditions.checkNotNull(map);
/*      */     }
/*      */     
/*      */     final Map<K, V> map() {
/* 3659 */       return this.map;
/*      */     }
/*      */     
/*      */     public Iterator<V> iterator() {
/* 3663 */       return Maps.valueIterator(map().entrySet().iterator());
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*      */       try {
/* 3668 */         return super.remove(o);
/* 3669 */       } catch (UnsupportedOperationException e) {
/* 3670 */         for (Map.Entry<K, V> entry : map().entrySet()) {
/* 3671 */           if (Objects.equal(o, entry.getValue())) {
/* 3672 */             map().remove(entry.getKey());
/* 3673 */             return true;
/*      */           } 
/*      */         } 
/* 3676 */         return false;
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/*      */       try {
/* 3682 */         return super.removeAll((Collection)Preconditions.checkNotNull(c));
/* 3683 */       } catch (UnsupportedOperationException e) {
/* 3684 */         Set<K> toRemove = Sets.newHashSet();
/* 3685 */         for (Map.Entry<K, V> entry : map().entrySet()) {
/* 3686 */           if (c.contains(entry.getValue())) {
/* 3687 */             toRemove.add(entry.getKey());
/*      */           }
/*      */         } 
/* 3690 */         return map().keySet().removeAll(toRemove);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/*      */       try {
/* 3696 */         return super.retainAll((Collection)Preconditions.checkNotNull(c));
/* 3697 */       } catch (UnsupportedOperationException e) {
/* 3698 */         Set<K> toRetain = Sets.newHashSet();
/* 3699 */         for (Map.Entry<K, V> entry : map().entrySet()) {
/* 3700 */           if (c.contains(entry.getValue())) {
/* 3701 */             toRetain.add(entry.getKey());
/*      */           }
/*      */         } 
/* 3704 */         return map().keySet().retainAll(toRetain);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 3709 */       return map().size();
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 3713 */       return map().isEmpty();
/*      */     }
/*      */     
/*      */     public boolean contains(@Nullable Object o) {
/* 3717 */       return map().containsValue(o);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 3721 */       map().clear();
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class EntrySet<K, V>
/*      */     extends Sets.ImprovedAbstractSet<Map.Entry<K, V>> {
/*      */     abstract Map<K, V> map();
/*      */     
/*      */     public int size() {
/* 3730 */       return map().size();
/*      */     }
/*      */     
/*      */     public void clear() {
/* 3734 */       map().clear();
/*      */     }
/*      */     
/*      */     public boolean contains(Object o) {
/* 3738 */       if (o instanceof Map.Entry) {
/* 3739 */         Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
/* 3740 */         Object key = entry.getKey();
/* 3741 */         V value = Maps.safeGet(map(), key);
/* 3742 */         return (Objects.equal(value, entry.getValue()) && (value != null || map().containsKey(key)));
/*      */       } 
/*      */       
/* 3745 */       return false;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 3749 */       return map().isEmpty();
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 3753 */       if (contains(o)) {
/* 3754 */         Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
/* 3755 */         return map().keySet().remove(entry.getKey());
/*      */       } 
/* 3757 */       return false;
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/*      */       try {
/* 3762 */         return super.removeAll((Collection)Preconditions.checkNotNull(c));
/* 3763 */       } catch (UnsupportedOperationException e) {
/*      */         
/* 3765 */         return Sets.removeAllImpl(this, c.iterator());
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/*      */       try {
/* 3771 */         return super.retainAll((Collection)Preconditions.checkNotNull(c));
/* 3772 */       } catch (UnsupportedOperationException e) {
/*      */         
/* 3774 */         Set<Object> keys = Sets.newHashSetWithExpectedSize(c.size());
/* 3775 */         for (Object o : c) {
/* 3776 */           if (contains(o)) {
/* 3777 */             Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
/* 3778 */             keys.add(entry.getKey());
/*      */           } 
/*      */         } 
/* 3781 */         return map().keySet().retainAll(keys);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   @GwtIncompatible("NavigableMap")
/*      */   static abstract class DescendingMap<K, V>
/*      */     extends ForwardingMap<K, V> implements NavigableMap<K, V> {
/*      */     private transient Comparator<? super K> comparator;
/*      */     private transient Set<Map.Entry<K, V>> entrySet;
/*      */     private transient NavigableSet<K> navigableKeySet;
/*      */     
/*      */     protected final Map<K, V> delegate() {
/* 3794 */       return forward();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 3802 */       Comparator<? super K> result = this.comparator;
/* 3803 */       if (result == null) {
/* 3804 */         Comparator<? super K> forwardCmp = forward().comparator();
/* 3805 */         if (forwardCmp == null) {
/* 3806 */           forwardCmp = Ordering.natural();
/*      */         }
/* 3808 */         result = this.comparator = reverse(forwardCmp);
/*      */       } 
/* 3810 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     private static <T> Ordering<T> reverse(Comparator<T> forward) {
/* 3815 */       return Ordering.<T>from(forward).reverse();
/*      */     }
/*      */ 
/*      */     
/*      */     public K firstKey() {
/* 3820 */       return forward().lastKey();
/*      */     }
/*      */ 
/*      */     
/*      */     public K lastKey() {
/* 3825 */       return forward().firstKey();
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> lowerEntry(K key) {
/* 3830 */       return forward().higherEntry(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public K lowerKey(K key) {
/* 3835 */       return forward().higherKey(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> floorEntry(K key) {
/* 3840 */       return forward().ceilingEntry(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public K floorKey(K key) {
/* 3845 */       return forward().ceilingKey(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> ceilingEntry(K key) {
/* 3850 */       return forward().floorEntry(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public K ceilingKey(K key) {
/* 3855 */       return forward().floorKey(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> higherEntry(K key) {
/* 3860 */       return forward().lowerEntry(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public K higherKey(K key) {
/* 3865 */       return forward().lowerKey(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> firstEntry() {
/* 3870 */       return forward().lastEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> lastEntry() {
/* 3875 */       return forward().firstEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> pollFirstEntry() {
/* 3880 */       return forward().pollLastEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> pollLastEntry() {
/* 3885 */       return forward().pollFirstEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> descendingMap() {
/* 3890 */       return forward();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 3897 */       Set<Map.Entry<K, V>> result = this.entrySet;
/* 3898 */       return (result == null) ? (this.entrySet = createEntrySet()) : result;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     Set<Map.Entry<K, V>> createEntrySet() {
/* 3904 */       return new Maps.EntrySet<K, V>()
/*      */         {
/*      */           Map<K, V> map() {
/* 3907 */             return Maps.DescendingMap.this;
/*      */           }
/*      */ 
/*      */           
/*      */           public Iterator<Map.Entry<K, V>> iterator() {
/* 3912 */             return Maps.DescendingMap.this.entryIterator();
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 3919 */       return navigableKeySet();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableSet<K> navigableKeySet() {
/* 3926 */       NavigableSet<K> result = this.navigableKeySet;
/* 3927 */       return (result == null) ? (this.navigableKeySet = new Maps.NavigableKeySet<K, V>(this)) : result;
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableSet<K> descendingKeySet() {
/* 3932 */       return forward().navigableKeySet();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
/* 3939 */       return forward().subMap(toKey, toInclusive, fromKey, fromInclusive).descendingMap();
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
/* 3944 */       return forward().tailMap(toKey, inclusive).descendingMap();
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
/* 3949 */       return forward().headMap(fromKey, inclusive).descendingMap();
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedMap<K, V> subMap(K fromKey, K toKey) {
/* 3954 */       return subMap(fromKey, true, toKey, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedMap<K, V> headMap(K toKey) {
/* 3959 */       return headMap(toKey, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedMap<K, V> tailMap(K fromKey) {
/* 3964 */       return tailMap(fromKey, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public Collection<V> values() {
/* 3969 */       return new Maps.Values<K, V>(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 3974 */       return standardToString();
/*      */     }
/*      */     
/*      */     abstract NavigableMap<K, V> forward();
/*      */     
/*      */     abstract Iterator<Map.Entry<K, V>> entryIterator();
/*      */   }
/*      */   
/*      */   public static interface EntryTransformer<K, V1, V2> {
/*      */     V2 transformEntry(@Nullable K param1K, @Nullable V1 param1V1);
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\Maps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */