/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.annotations.Beta;
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import com.google.common.annotations.GwtIncompatible;
/*      */ import com.google.common.base.Equivalence;
/*      */ import com.google.common.base.Equivalences;
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Joiner;
/*      */ import com.google.common.base.Objects;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.base.Predicates;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractCollection;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.AbstractSet;
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
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.TreeMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <K, V> HashMap<K, V> newHashMap() {
/*   85 */     return new HashMap<K, V>();
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
/*  103 */     return new HashMap<K, V>(capacity(expectedSize));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int capacity(int expectedSize) {
/*  112 */     if (expectedSize < 3) {
/*  113 */       Preconditions.checkArgument((expectedSize >= 0));
/*  114 */       return expectedSize + 1;
/*      */     } 
/*  116 */     if (expectedSize < 1073741824) {
/*  117 */       return expectedSize + expectedSize / 3;
/*      */     }
/*  119 */     return Integer.MAX_VALUE;
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
/*  138 */     return new HashMap<K, V>(map);
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
/*  151 */     return new LinkedHashMap<K, V>();
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
/*  167 */     return new LinkedHashMap<K, V>(map);
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
/*  186 */     return (new MapMaker()).makeMap();
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
/*  199 */     return new TreeMap<K, V>();
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
/*  215 */     return new TreeMap<K, V>(map);
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
/*  235 */     return new TreeMap<K, V>(comparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> type) {
/*  245 */     return new EnumMap<K, V>((Class<K>)Preconditions.checkNotNull(type));
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
/*  259 */     return new EnumMap<K, V>(map);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
/*  268 */     return new IdentityHashMap<K, V>();
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
/*      */   public static <K, V> BiMap<K, V> synchronizedBiMap(BiMap<K, V> bimap) {
/*  300 */     return Synchronized.biMap(bimap, null);
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
/*      */   public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> left, Map<? extends K, ? extends V> right) {
/*  321 */     return difference(left, right, Equivalences.equals());
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
/*  347 */     Preconditions.checkNotNull(valueEquivalence);
/*      */     
/*  349 */     Map<K, V> onlyOnLeft = newHashMap();
/*  350 */     Map<K, V> onlyOnRight = new HashMap<K, V>(right);
/*  351 */     Map<K, V> onBoth = newHashMap();
/*  352 */     Map<K, MapDifference.ValueDifference<V>> differences = newHashMap();
/*  353 */     boolean eq = true;
/*      */     
/*  355 */     for (Map.Entry<? extends K, ? extends V> entry : left.entrySet()) {
/*  356 */       K leftKey = entry.getKey();
/*  357 */       V leftValue = entry.getValue();
/*  358 */       if (right.containsKey(leftKey)) {
/*  359 */         V rightValue = onlyOnRight.remove(leftKey);
/*  360 */         if (valueEquivalence.equivalent(leftValue, rightValue)) {
/*  361 */           onBoth.put(leftKey, leftValue); continue;
/*      */         } 
/*  363 */         eq = false;
/*  364 */         differences.put(leftKey, ValueDifferenceImpl.create(leftValue, rightValue));
/*      */         
/*      */         continue;
/*      */       } 
/*  368 */       eq = false;
/*  369 */       onlyOnLeft.put(leftKey, leftValue);
/*      */     } 
/*      */ 
/*      */     
/*  373 */     boolean areEqual = (eq && onlyOnRight.isEmpty());
/*  374 */     return mapDifference(areEqual, onlyOnLeft, onlyOnRight, onBoth, differences);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <K, V> MapDifference<K, V> mapDifference(boolean areEqual, Map<K, V> onlyOnLeft, Map<K, V> onlyOnRight, Map<K, V> onBoth, Map<K, MapDifference.ValueDifference<V>> differences) {
/*  381 */     return new MapDifferenceImpl<K, V>(areEqual, Collections.unmodifiableMap(onlyOnLeft), Collections.unmodifiableMap(onlyOnRight), Collections.unmodifiableMap(onBoth), Collections.unmodifiableMap(differences));
/*      */   }
/*      */ 
/*      */   
/*      */   static class MapDifferenceImpl<K, V>
/*      */     implements MapDifference<K, V>
/*      */   {
/*      */     final boolean areEqual;
/*      */     
/*      */     final Map<K, V> onlyOnLeft;
/*      */     
/*      */     final Map<K, V> onlyOnRight;
/*      */     
/*      */     final Map<K, V> onBoth;
/*      */     final Map<K, MapDifference.ValueDifference<V>> differences;
/*      */     
/*      */     MapDifferenceImpl(boolean areEqual, Map<K, V> onlyOnLeft, Map<K, V> onlyOnRight, Map<K, V> onBoth, Map<K, MapDifference.ValueDifference<V>> differences) {
/*  398 */       this.areEqual = areEqual;
/*  399 */       this.onlyOnLeft = onlyOnLeft;
/*  400 */       this.onlyOnRight = onlyOnRight;
/*  401 */       this.onBoth = onBoth;
/*  402 */       this.differences = differences;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean areEqual() {
/*  407 */       return this.areEqual;
/*      */     }
/*      */ 
/*      */     
/*      */     public Map<K, V> entriesOnlyOnLeft() {
/*  412 */       return this.onlyOnLeft;
/*      */     }
/*      */ 
/*      */     
/*      */     public Map<K, V> entriesOnlyOnRight() {
/*  417 */       return this.onlyOnRight;
/*      */     }
/*      */ 
/*      */     
/*      */     public Map<K, V> entriesInCommon() {
/*  422 */       return this.onBoth;
/*      */     }
/*      */ 
/*      */     
/*      */     public Map<K, MapDifference.ValueDifference<V>> entriesDiffering() {
/*  427 */       return this.differences;
/*      */     }
/*      */     
/*      */     public boolean equals(Object object) {
/*  431 */       if (object == this) {
/*  432 */         return true;
/*      */       }
/*  434 */       if (object instanceof MapDifference) {
/*  435 */         MapDifference<?, ?> other = (MapDifference<?, ?>)object;
/*  436 */         return (entriesOnlyOnLeft().equals(other.entriesOnlyOnLeft()) && entriesOnlyOnRight().equals(other.entriesOnlyOnRight()) && entriesInCommon().equals(other.entriesInCommon()) && entriesDiffering().equals(other.entriesDiffering()));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  441 */       return false;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  445 */       return Objects.hashCode(new Object[] { entriesOnlyOnLeft(), entriesOnlyOnRight(), entriesInCommon(), entriesDiffering() });
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  450 */       if (this.areEqual) {
/*  451 */         return "equal";
/*      */       }
/*      */       
/*  454 */       StringBuilder result = new StringBuilder("not equal");
/*  455 */       if (!this.onlyOnLeft.isEmpty()) {
/*  456 */         result.append(": only on left=").append(this.onlyOnLeft);
/*      */       }
/*  458 */       if (!this.onlyOnRight.isEmpty()) {
/*  459 */         result.append(": only on right=").append(this.onlyOnRight);
/*      */       }
/*  461 */       if (!this.differences.isEmpty()) {
/*  462 */         result.append(": value differences=").append(this.differences);
/*      */       }
/*  464 */       return result.toString();
/*      */     }
/*      */   }
/*      */   
/*      */   static class ValueDifferenceImpl<V>
/*      */     implements MapDifference.ValueDifference<V> {
/*      */     private final V left;
/*      */     private final V right;
/*      */     
/*      */     static <V> MapDifference.ValueDifference<V> create(@Nullable V left, @Nullable V right) {
/*  474 */       return new ValueDifferenceImpl<V>(left, right);
/*      */     }
/*      */     
/*      */     private ValueDifferenceImpl(@Nullable V left, @Nullable V right) {
/*  478 */       this.left = left;
/*  479 */       this.right = right;
/*      */     }
/*      */ 
/*      */     
/*      */     public V leftValue() {
/*  484 */       return this.left;
/*      */     }
/*      */ 
/*      */     
/*      */     public V rightValue() {
/*  489 */       return this.right;
/*      */     }
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/*  493 */       if (object instanceof MapDifference.ValueDifference) {
/*  494 */         MapDifference.ValueDifference<?> that = (MapDifference.ValueDifference)object;
/*      */         
/*  496 */         return (Objects.equal(this.left, that.leftValue()) && Objects.equal(this.right, that.rightValue()));
/*      */       } 
/*      */       
/*  499 */       return false;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  503 */       return Objects.hashCode(new Object[] { this.left, this.right });
/*      */     }
/*      */     
/*      */     public String toString() {
/*  507 */       return "(" + this.left + ", " + this.right + ")";
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
/*      */   public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterable<V> values, Function<? super V, K> keyFunction) {
/*  527 */     return uniqueIndex(values.iterator(), keyFunction);
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
/*      */   @Deprecated
/*      */   @Beta
/*      */   public static <K, V, I extends Iterable<V> & Iterator<V>> ImmutableMap<K, V> uniqueIndex(I values, Function<? super V, K> keyFunction) {
/*  544 */     Iterable<V> valuesIterable = (Iterable<V>)Preconditions.checkNotNull(values);
/*  545 */     return uniqueIndex(valuesIterable, keyFunction);
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
/*  565 */     Preconditions.checkNotNull(keyFunction);
/*  566 */     ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
/*  567 */     while (values.hasNext()) {
/*  568 */       V value = values.next();
/*  569 */       builder.put((K)keyFunction.apply(value), value);
/*      */     } 
/*  571 */     return builder.build();
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
/*  590 */     ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
/*      */     
/*  592 */     for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements(); ) {
/*  593 */       String key = (String)e.nextElement();
/*  594 */       builder.put(key, properties.getProperty(key));
/*      */     } 
/*      */     
/*  597 */     return builder.build();
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
/*  612 */     return new ImmutableEntry<K, V>(key, value);
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
/*  625 */     return new UnmodifiableEntrySet<K, V>(Collections.unmodifiableSet(entrySet));
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
/*      */   static <K, V> Map.Entry<K, V> unmodifiableEntry(final Map.Entry<K, V> entry) {
/*  639 */     Preconditions.checkNotNull(entry);
/*  640 */     return new AbstractMapEntry<K, V>() {
/*      */         public K getKey() {
/*  642 */           return (K)entry.getKey();
/*      */         }
/*      */         
/*      */         public V getValue() {
/*  646 */           return (V)entry.getValue();
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
/*  657 */       this.entries = entries;
/*      */     }
/*      */     
/*      */     protected Collection<Map.Entry<K, V>> delegate() {
/*  661 */       return this.entries;
/*      */     }
/*      */     
/*      */     public Iterator<Map.Entry<K, V>> iterator() {
/*  665 */       final Iterator<Map.Entry<K, V>> delegate = super.iterator();
/*  666 */       return new ForwardingIterator<Map.Entry<K, V>>() {
/*      */           public Map.Entry<K, V> next() {
/*  668 */             return Maps.unmodifiableEntry(super.next());
/*      */           }
/*      */           
/*      */           public void remove() {
/*  672 */             throw new UnsupportedOperationException();
/*      */           }
/*      */           
/*      */           protected Iterator<Map.Entry<K, V>> delegate() {
/*  676 */             return delegate;
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean add(Map.Entry<K, V> element) {
/*  684 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
/*  689 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void clear() {
/*  693 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean remove(Object object) {
/*  697 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> collection) {
/*  701 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> collection) {
/*  705 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object[] toArray() {
/*  709 */       return standardToArray();
/*      */     }
/*      */     
/*      */     public <T> T[] toArray(T[] array) {
/*  713 */       return (T[])standardToArray((Object[])array);
/*      */     }
/*      */   }
/*      */   
/*      */   static class UnmodifiableEntrySet<K, V>
/*      */     extends UnmodifiableEntries<K, V>
/*      */     implements Set<Map.Entry<K, V>> {
/*      */     UnmodifiableEntrySet(Set<Map.Entry<K, V>> entries) {
/*  721 */       super(entries);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/*  727 */       return Sets.equalsImpl(this, object);
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  731 */       return Sets.hashCodeImpl(this);
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
/*      */   public static <K, V> BiMap<K, V> unmodifiableBiMap(BiMap<? extends K, ? extends V> bimap) {
/*  750 */     return new UnmodifiableBiMap<K, V>(bimap, null);
/*      */   }
/*      */   
/*      */   private static class UnmodifiableBiMap<K, V>
/*      */     extends ForwardingMap<K, V>
/*      */     implements BiMap<K, V>, Serializable {
/*      */     final Map<K, V> unmodifiableMap;
/*      */     final BiMap<? extends K, ? extends V> delegate;
/*      */     transient BiMap<V, K> inverse;
/*      */     transient Set<V> values;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     UnmodifiableBiMap(BiMap<? extends K, ? extends V> delegate, @Nullable BiMap<V, K> inverse) {
/*  763 */       this.unmodifiableMap = Collections.unmodifiableMap(delegate);
/*  764 */       this.delegate = delegate;
/*  765 */       this.inverse = inverse;
/*      */     }
/*      */     
/*      */     protected Map<K, V> delegate() {
/*  769 */       return this.unmodifiableMap;
/*      */     }
/*      */ 
/*      */     
/*      */     public V forcePut(K key, V value) {
/*  774 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public BiMap<V, K> inverse() {
/*  779 */       BiMap<V, K> result = this.inverse;
/*  780 */       return (result == null) ? (this.inverse = new UnmodifiableBiMap(this.delegate.inverse(), this)) : result;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<V> values() {
/*  786 */       Set<V> result = this.values;
/*  787 */       return (result == null) ? (this.values = Collections.unmodifiableSet(this.delegate.values())) : result;
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
/*      */   public static <K, V1, V2> Map<K, V2> transformValues(Map<K, V1> fromMap, final Function<? super V1, V2> function) {
/*  833 */     Preconditions.checkNotNull(function);
/*  834 */     EntryTransformer<K, V1, V2> transformer = new EntryTransformer<K, V1, V2>()
/*      */       {
/*      */         public V2 transformEntry(K key, V1 value)
/*      */         {
/*  838 */           return (V2)function.apply(value);
/*      */         }
/*      */       };
/*  841 */     return transformEntries(fromMap, transformer);
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
/*  898 */     return new TransformedEntriesMap<K, V1, V2>(fromMap, transformer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class TransformedEntriesMap<K, V1, V2>
/*      */     extends AbstractMap<K, V2>
/*      */   {
/*      */     final Map<K, V1> fromMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final Maps.EntryTransformer<? super K, ? super V1, V2> transformer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Set<Map.Entry<K, V2>> entrySet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Collection<V2> values;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TransformedEntriesMap(Map<K, V1> fromMap, Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
/*  940 */       this.fromMap = (Map<K, V1>)Preconditions.checkNotNull(fromMap);
/*  941 */       this.transformer = (Maps.EntryTransformer<? super K, ? super V1, V2>)Preconditions.checkNotNull(transformer);
/*      */     }
/*      */     
/*      */     public int size() {
/*  945 */       return this.fromMap.size();
/*      */     }
/*      */     
/*      */     public boolean containsKey(Object key) {
/*  949 */       return this.fromMap.containsKey(key);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V2 get(Object key) {
/*  955 */       V1 value = this.fromMap.get(key);
/*  956 */       return (value != null || this.fromMap.containsKey(key)) ? this.transformer.transformEntry((K)key, value) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V2 remove(Object key) {
/*  964 */       return this.fromMap.containsKey(key) ? this.transformer.transformEntry((K)key, this.fromMap.remove(key)) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void clear() {
/*  970 */       this.fromMap.clear();
/*      */     }
/*      */     
/*      */     public Set<K> keySet() {
/*  974 */       return this.fromMap.keySet();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, V2>> entrySet() {
/*  980 */       Set<Map.Entry<K, V2>> result = this.entrySet;
/*  981 */       if (result == null) {
/*  982 */         this.entrySet = result = new Maps.EntrySet<K, V2>() {
/*      */             Map<K, V2> map() {
/*  984 */               return Maps.TransformedEntriesMap.this;
/*      */             }
/*      */             
/*      */             public Iterator<Map.Entry<K, V2>> iterator() {
/*  988 */               Iterator<Map.Entry<K, V1>> backingIterator = Maps.TransformedEntriesMap.this.fromMap.entrySet().iterator();
/*      */               
/*  990 */               return Iterators.transform(backingIterator, new Function<Map.Entry<K, V1>, Map.Entry<K, V2>>()
/*      */                   {
/*      */                     public Map.Entry<K, V2> apply(Map.Entry<K, V1> entry) {
/*  993 */                       return Maps.immutableEntry(entry.getKey(), (V2)Maps.TransformedEntriesMap.this.transformer.transformEntry(entry.getKey(), entry.getValue()));
/*      */                     }
/*      */                   });
/*      */             }
/*      */           };
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1002 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Collection<V2> values() {
/* 1008 */       Collection<V2> result = this.values;
/* 1009 */       if (result == null) {
/* 1010 */         return this.values = new Maps.Values<K, V2>() {
/*      */             Map<K, V2> map() {
/* 1012 */               return Maps.TransformedEntriesMap.this;
/*      */             }
/*      */           };
/*      */       }
/* 1016 */       return result;
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
/*      */   public static <K, V> Map<K, V> filterKeys(Map<K, V> unfiltered, final Predicate<? super K> keyPredicate) {
/* 1050 */     Preconditions.checkNotNull(keyPredicate);
/* 1051 */     Predicate<Map.Entry<K, V>> entryPredicate = new Predicate<Map.Entry<K, V>>()
/*      */       {
/*      */         public boolean apply(Map.Entry<K, V> input)
/*      */         {
/* 1055 */           return keyPredicate.apply(input.getKey());
/*      */         }
/*      */       };
/* 1058 */     return (unfiltered instanceof AbstractFilteredMap) ? filterFiltered((AbstractFilteredMap<K, V>)unfiltered, entryPredicate) : new FilteredKeyMap<K, V>((Map<K, V>)Preconditions.checkNotNull(unfiltered), keyPredicate, entryPredicate);
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
/*      */   public static <K, V> Map<K, V> filterValues(Map<K, V> unfiltered, final Predicate<? super V> valuePredicate) {
/* 1095 */     Preconditions.checkNotNull(valuePredicate);
/* 1096 */     Predicate<Map.Entry<K, V>> entryPredicate = new Predicate<Map.Entry<K, V>>()
/*      */       {
/*      */         public boolean apply(Map.Entry<K, V> input)
/*      */         {
/* 1100 */           return valuePredicate.apply(input.getValue());
/*      */         }
/*      */       };
/* 1103 */     return filterEntries(unfiltered, entryPredicate);
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
/* 1137 */     Preconditions.checkNotNull(entryPredicate);
/* 1138 */     return (unfiltered instanceof AbstractFilteredMap) ? filterFiltered((AbstractFilteredMap<K, V>)unfiltered, entryPredicate) : new FilteredEntryMap<K, V>((Map<K, V>)Preconditions.checkNotNull(unfiltered), entryPredicate);
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
/* 1149 */     Predicate<Map.Entry<K, V>> predicate = Predicates.and(map.predicate, entryPredicate);
/*      */     
/* 1151 */     return new FilteredEntryMap<K, V>(map.unfiltered, predicate);
/*      */   }
/*      */   
/*      */   private static abstract class AbstractFilteredMap<K, V>
/*      */     extends AbstractMap<K, V> {
/*      */     final Map<K, V> unfiltered;
/*      */     final Predicate<? super Map.Entry<K, V>> predicate;
/*      */     Collection<V> values;
/*      */     
/*      */     AbstractFilteredMap(Map<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> predicate) {
/* 1161 */       this.unfiltered = unfiltered;
/* 1162 */       this.predicate = predicate;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean apply(Object key, V value) {
/* 1169 */       K k = (K)key;
/* 1170 */       return this.predicate.apply(Maps.immutableEntry(k, value));
/*      */     }
/*      */     
/*      */     public V put(K key, V value) {
/* 1174 */       Preconditions.checkArgument(apply(key, value));
/* 1175 */       return this.unfiltered.put(key, value);
/*      */     }
/*      */     
/*      */     public void putAll(Map<? extends K, ? extends V> map) {
/* 1179 */       for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
/* 1180 */         Preconditions.checkArgument(apply(entry.getKey(), entry.getValue()));
/*      */       }
/* 1182 */       this.unfiltered.putAll(map);
/*      */     }
/*      */     
/*      */     public boolean containsKey(Object key) {
/* 1186 */       return (this.unfiltered.containsKey(key) && apply(key, this.unfiltered.get(key)));
/*      */     }
/*      */     
/*      */     public V get(Object key) {
/* 1190 */       V value = this.unfiltered.get(key);
/* 1191 */       return (value != null && apply(key, value)) ? value : null;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1195 */       return entrySet().isEmpty();
/*      */     }
/*      */     
/*      */     public V remove(Object key) {
/* 1199 */       return containsKey(key) ? this.unfiltered.remove(key) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Collection<V> values() {
/* 1205 */       Collection<V> result = this.values;
/* 1206 */       return (result == null) ? (this.values = new Values()) : result;
/*      */     }
/*      */     
/*      */     class Values extends AbstractCollection<V> {
/*      */       public Iterator<V> iterator() {
/* 1211 */         final Iterator<Map.Entry<K, V>> entryIterator = Maps.AbstractFilteredMap.this.entrySet().iterator();
/* 1212 */         return new UnmodifiableIterator<V>()
/*      */           {
/*      */             public boolean hasNext() {
/* 1215 */               return entryIterator.hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public V next() {
/* 1220 */               return (V)((Map.Entry)entryIterator.next()).getValue();
/*      */             }
/*      */           };
/*      */       }
/*      */       
/*      */       public int size() {
/* 1226 */         return Maps.AbstractFilteredMap.this.entrySet().size();
/*      */       }
/*      */       
/*      */       public void clear() {
/* 1230 */         Maps.AbstractFilteredMap.this.entrySet().clear();
/*      */       }
/*      */       
/*      */       public boolean isEmpty() {
/* 1234 */         return Maps.AbstractFilteredMap.this.entrySet().isEmpty();
/*      */       }
/*      */       
/*      */       public boolean remove(Object o) {
/* 1238 */         Iterator<Map.Entry<K, V>> iterator = Maps.AbstractFilteredMap.this.unfiltered.entrySet().iterator();
/* 1239 */         while (iterator.hasNext()) {
/* 1240 */           Map.Entry<K, V> entry = iterator.next();
/* 1241 */           if (Objects.equal(o, entry.getValue()) && Maps.AbstractFilteredMap.this.predicate.apply(entry)) {
/* 1242 */             iterator.remove();
/* 1243 */             return true;
/*      */           } 
/*      */         } 
/* 1246 */         return false;
/*      */       }
/*      */       
/*      */       public boolean removeAll(Collection<?> collection) {
/* 1250 */         Preconditions.checkNotNull(collection);
/* 1251 */         boolean changed = false;
/* 1252 */         Iterator<Map.Entry<K, V>> iterator = Maps.AbstractFilteredMap.this.unfiltered.entrySet().iterator();
/* 1253 */         while (iterator.hasNext()) {
/* 1254 */           Map.Entry<K, V> entry = iterator.next();
/* 1255 */           if (collection.contains(entry.getValue()) && Maps.AbstractFilteredMap.this.predicate.apply(entry)) {
/* 1256 */             iterator.remove();
/* 1257 */             changed = true;
/*      */           } 
/*      */         } 
/* 1260 */         return changed;
/*      */       }
/*      */       
/*      */       public boolean retainAll(Collection<?> collection) {
/* 1264 */         Preconditions.checkNotNull(collection);
/* 1265 */         boolean changed = false;
/* 1266 */         Iterator<Map.Entry<K, V>> iterator = Maps.AbstractFilteredMap.this.unfiltered.entrySet().iterator();
/* 1267 */         while (iterator.hasNext()) {
/* 1268 */           Map.Entry<K, V> entry = iterator.next();
/* 1269 */           if (!collection.contains(entry.getValue()) && Maps.AbstractFilteredMap.this.predicate.apply(entry)) {
/*      */             
/* 1271 */             iterator.remove();
/* 1272 */             changed = true;
/*      */           } 
/*      */         } 
/* 1275 */         return changed;
/*      */       }
/*      */ 
/*      */       
/*      */       public Object[] toArray() {
/* 1280 */         return Lists.<V>newArrayList(iterator()).toArray();
/*      */       }
/*      */       
/*      */       public <T> T[] toArray(T[] array) {
/* 1284 */         return (T[])Lists.<V>newArrayList(iterator()).toArray((Object[])array);
/*      */       } }
/*      */   }
/*      */   
/*      */   private static class FilteredKeyMap<K, V> extends AbstractFilteredMap<K, V> {
/*      */     Predicate<? super K> keyPredicate;
/*      */     Set<Map.Entry<K, V>> entrySet;
/*      */     Set<K> keySet;
/*      */     
/*      */     FilteredKeyMap(Map<K, V> unfiltered, Predicate<? super K> keyPredicate, Predicate<Map.Entry<K, V>> entryPredicate) {
/* 1294 */       super(unfiltered, entryPredicate);
/* 1295 */       this.keyPredicate = keyPredicate;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 1301 */       Set<Map.Entry<K, V>> result = this.entrySet;
/* 1302 */       return (result == null) ? (this.entrySet = Sets.filter(this.unfiltered.entrySet(), this.predicate)) : result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 1310 */       Set<K> result = this.keySet;
/* 1311 */       return (result == null) ? (this.keySet = Sets.filter(this.unfiltered.keySet(), this.keyPredicate)) : result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object key) {
/* 1321 */       return (this.unfiltered.containsKey(key) && this.keyPredicate.apply(key));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class FilteredEntryMap<K, V>
/*      */     extends AbstractFilteredMap<K, V>
/*      */   {
/*      */     final Set<Map.Entry<K, V>> filteredEntrySet;
/*      */     Set<Map.Entry<K, V>> entrySet;
/*      */     Set<K> keySet;
/*      */     
/*      */     FilteredEntryMap(Map<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 1334 */       super(unfiltered, entryPredicate);
/* 1335 */       this.filteredEntrySet = Sets.filter(unfiltered.entrySet(), this.predicate);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 1341 */       Set<Map.Entry<K, V>> result = this.entrySet;
/* 1342 */       return (result == null) ? (this.entrySet = new EntrySet()) : result;
/*      */     }
/*      */     
/*      */     private class EntrySet extends ForwardingSet<Map.Entry<K, V>> {
/*      */       protected Set<Map.Entry<K, V>> delegate() {
/* 1347 */         return Maps.FilteredEntryMap.this.filteredEntrySet;
/*      */       }
/*      */       private EntrySet() {}
/*      */       public Iterator<Map.Entry<K, V>> iterator() {
/* 1351 */         final Iterator<Map.Entry<K, V>> iterator = Maps.FilteredEntryMap.this.filteredEntrySet.iterator();
/* 1352 */         return new UnmodifiableIterator<Map.Entry<K, V>>()
/*      */           {
/*      */             public boolean hasNext() {
/* 1355 */               return iterator.hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public Map.Entry<K, V> next() {
/* 1360 */               final Map.Entry<K, V> entry = iterator.next();
/* 1361 */               return new ForwardingMapEntry<K, V>() {
/*      */                   protected Map.Entry<K, V> delegate() {
/* 1363 */                     return entry;
/*      */                   }
/*      */                   
/*      */                   public V setValue(V value) {
/* 1367 */                     Preconditions.checkArgument(Maps.FilteredEntryMap.this.apply(entry.getKey(), value));
/* 1368 */                     return super.setValue(value);
/*      */                   }
/*      */                 };
/*      */             }
/*      */           };
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 1379 */       Set<K> result = this.keySet;
/* 1380 */       return (result == null) ? (this.keySet = new KeySet()) : result;
/*      */     }
/*      */     private class KeySet extends AbstractSet<K> { private KeySet() {}
/*      */       
/*      */       public Iterator<K> iterator() {
/* 1385 */         final Iterator<Map.Entry<K, V>> iterator = Maps.FilteredEntryMap.this.filteredEntrySet.iterator();
/* 1386 */         return new UnmodifiableIterator<K>()
/*      */           {
/*      */             public boolean hasNext() {
/* 1389 */               return iterator.hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public K next() {
/* 1394 */               return (K)((Map.Entry)iterator.next()).getKey();
/*      */             }
/*      */           };
/*      */       }
/*      */       
/*      */       public int size() {
/* 1400 */         return Maps.FilteredEntryMap.this.filteredEntrySet.size();
/*      */       }
/*      */       
/*      */       public void clear() {
/* 1404 */         Maps.FilteredEntryMap.this.filteredEntrySet.clear();
/*      */       }
/*      */       
/*      */       public boolean contains(Object o) {
/* 1408 */         return Maps.FilteredEntryMap.this.containsKey(o);
/*      */       }
/*      */       
/*      */       public boolean remove(Object o) {
/* 1412 */         if (Maps.FilteredEntryMap.this.containsKey(o)) {
/* 1413 */           Maps.FilteredEntryMap.this.unfiltered.remove(o);
/* 1414 */           return true;
/*      */         } 
/* 1416 */         return false;
/*      */       }
/*      */       
/*      */       public boolean removeAll(Collection<?> collection) {
/* 1420 */         Preconditions.checkNotNull(collection);
/* 1421 */         boolean changed = false;
/* 1422 */         for (Object obj : collection) {
/* 1423 */           changed |= remove(obj);
/*      */         }
/* 1425 */         return changed;
/*      */       }
/*      */       
/*      */       public boolean retainAll(Collection<?> collection) {
/* 1429 */         Preconditions.checkNotNull(collection);
/* 1430 */         boolean changed = false;
/* 1431 */         Iterator<Map.Entry<K, V>> iterator = Maps.FilteredEntryMap.this.unfiltered.entrySet().iterator();
/* 1432 */         while (iterator.hasNext()) {
/* 1433 */           Map.Entry<K, V> entry = iterator.next();
/* 1434 */           if (!collection.contains(entry.getKey()) && Maps.FilteredEntryMap.this.predicate.apply(entry)) {
/* 1435 */             iterator.remove();
/* 1436 */             changed = true;
/*      */           } 
/*      */         } 
/* 1439 */         return changed;
/*      */       }
/*      */ 
/*      */       
/*      */       public Object[] toArray() {
/* 1444 */         return Lists.<K>newArrayList(iterator()).toArray();
/*      */       }
/*      */       
/*      */       public <T> T[] toArray(T[] array) {
/* 1448 */         return (T[])Lists.<K>newArrayList(iterator()).toArray((Object[])array);
/*      */       } }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtCompatible
/*      */   static abstract class ImprovedAbstractMap<K, V>
/*      */     extends AbstractMap<K, V>
/*      */   {
/*      */     private Set<Map.Entry<K, V>> entrySet;
/*      */ 
/*      */     
/*      */     private Set<K> keySet;
/*      */ 
/*      */     
/*      */     private Collection<V> values;
/*      */ 
/*      */     
/*      */     protected abstract Set<Map.Entry<K, V>> createEntrySet();
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 1472 */       Set<Map.Entry<K, V>> result = this.entrySet;
/* 1473 */       if (result == null) {
/* 1474 */         this.entrySet = result = createEntrySet();
/*      */       }
/* 1476 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 1482 */       Set<K> result = this.keySet;
/* 1483 */       if (result == null) {
/* 1484 */         return this.keySet = new Maps.KeySet<K, V>() {
/*      */             Map<K, V> map() {
/* 1486 */               return Maps.ImprovedAbstractMap.this;
/*      */             }
/*      */           };
/*      */       }
/* 1490 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Collection<V> values() {
/* 1496 */       Collection<V> result = this.values;
/* 1497 */       if (result == null) {
/* 1498 */         return this.values = new Maps.Values<K, V>() {
/*      */             Map<K, V> map() {
/* 1500 */               return Maps.ImprovedAbstractMap.this;
/*      */             }
/*      */           };
/*      */       }
/* 1504 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1515 */       return entrySet().isEmpty();
/*      */     }
/*      */   }
/*      */   
/* 1519 */   static final Joiner.MapJoiner STANDARD_JOINER = Collections2.STANDARD_JOINER.withKeyValueSeparator("=");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <V> V safeGet(Map<?, V> map, Object key) {
/*      */     try {
/* 1528 */       return map.get(key);
/* 1529 */     } catch (ClassCastException e) {
/* 1530 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean safeContainsKey(Map<?, ?> map, Object key) {
/*      */     try {
/* 1540 */       return map.containsKey(key);
/* 1541 */     } catch (ClassCastException e) {
/* 1542 */       return false;
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
/*      */   static <K, V> boolean containsEntryImpl(Collection<Map.Entry<K, V>> c, Object o) {
/* 1560 */     if (!(o instanceof Map.Entry)) {
/* 1561 */       return false;
/*      */     }
/* 1563 */     return c.contains(unmodifiableEntry((Map.Entry<?, ?>)o));
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
/* 1580 */     if (!(o instanceof Map.Entry)) {
/* 1581 */       return false;
/*      */     }
/* 1583 */     return c.remove(unmodifiableEntry((Map.Entry<?, ?>)o));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean equalsImpl(Map<?, ?> map, Object object) {
/* 1590 */     if (map == object) {
/* 1591 */       return true;
/*      */     }
/* 1593 */     if (object instanceof Map) {
/* 1594 */       Map<?, ?> o = (Map<?, ?>)object;
/* 1595 */       return map.entrySet().equals(o.entrySet());
/*      */     } 
/* 1597 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int hashCodeImpl(Map<?, ?> map) {
/* 1604 */     return Sets.hashCodeImpl(map.entrySet());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String toStringImpl(Map<?, ?> map) {
/* 1611 */     StringBuilder sb = Collections2.newStringBuilderForCollection(map.size()).append('{');
/*      */     
/* 1613 */     STANDARD_JOINER.appendTo(sb, map);
/* 1614 */     return sb.append('}').toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> void putAllImpl(Map<K, V> self, Map<? extends K, ? extends V> map) {
/* 1622 */     for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
/* 1623 */       self.put(entry.getKey(), entry.getValue());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean containsKeyImpl(Map<?, ?> map, @Nullable Object key) {
/* 1631 */     for (Map.Entry<?, ?> entry : map.entrySet()) {
/* 1632 */       if (Objects.equal(entry.getKey(), key)) {
/* 1633 */         return true;
/*      */       }
/*      */     } 
/* 1636 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean containsValueImpl(Map<?, ?> map, @Nullable Object value) {
/* 1643 */     for (Map.Entry<?, ?> entry : map.entrySet()) {
/* 1644 */       if (Objects.equal(entry.getValue(), value)) {
/* 1645 */         return true;
/*      */       }
/*      */     } 
/* 1648 */     return false;
/*      */   }
/*      */   
/*      */   static abstract class KeySet<K, V> extends AbstractSet<K> {
/*      */     abstract Map<K, V> map();
/*      */     
/*      */     public Iterator<K> iterator() {
/* 1655 */       return Iterators.transform(map().entrySet().iterator(), new Function<Map.Entry<K, V>, K>()
/*      */           {
/*      */             public K apply(Map.Entry<K, V> entry) {
/* 1658 */               return entry.getKey();
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*      */     public int size() {
/* 1664 */       return map().size();
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1668 */       return map().isEmpty();
/*      */     }
/*      */     
/*      */     public boolean contains(Object o) {
/* 1672 */       return map().containsKey(o);
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1676 */       if (contains(o)) {
/* 1677 */         map().remove(o);
/* 1678 */         return true;
/*      */       } 
/* 1680 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/* 1686 */       return super.removeAll((Collection)Preconditions.checkNotNull(c));
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1690 */       map().clear();
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class Values<K, V> extends AbstractCollection<V> {
/*      */     abstract Map<K, V> map();
/*      */     
/*      */     public Iterator<V> iterator() {
/* 1698 */       return Iterators.transform(map().entrySet().iterator(), new Function<Map.Entry<K, V>, V>()
/*      */           {
/*      */             public V apply(Map.Entry<K, V> entry) {
/* 1701 */               return entry.getValue();
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*      */       try {
/* 1708 */         return super.remove(o);
/* 1709 */       } catch (UnsupportedOperationException e) {
/* 1710 */         for (Map.Entry<K, V> entry : map().entrySet()) {
/* 1711 */           if (Objects.equal(o, entry.getValue())) {
/* 1712 */             map().remove(entry.getKey());
/* 1713 */             return true;
/*      */           } 
/*      */         } 
/* 1716 */         return false;
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/*      */       try {
/* 1722 */         return super.removeAll((Collection)Preconditions.checkNotNull(c));
/* 1723 */       } catch (UnsupportedOperationException e) {
/* 1724 */         Set<K> toRemove = Sets.newHashSet();
/* 1725 */         for (Map.Entry<K, V> entry : map().entrySet()) {
/* 1726 */           if (c.contains(entry.getValue())) {
/* 1727 */             toRemove.add(entry.getKey());
/*      */           }
/*      */         } 
/* 1730 */         return map().keySet().removeAll(toRemove);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/*      */       try {
/* 1736 */         return super.retainAll((Collection)Preconditions.checkNotNull(c));
/* 1737 */       } catch (UnsupportedOperationException e) {
/* 1738 */         Set<K> toRetain = Sets.newHashSet();
/* 1739 */         for (Map.Entry<K, V> entry : map().entrySet()) {
/* 1740 */           if (c.contains(entry.getValue())) {
/* 1741 */             toRetain.add(entry.getKey());
/*      */           }
/*      */         } 
/* 1744 */         return map().keySet().retainAll(toRetain);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1749 */       return map().size();
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1753 */       return map().isEmpty();
/*      */     }
/*      */     
/*      */     public boolean contains(@Nullable Object o) {
/* 1757 */       return map().containsValue(o);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1761 */       map().clear();
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class EntrySet<K, V> extends AbstractSet<Map.Entry<K, V>> {
/*      */     abstract Map<K, V> map();
/*      */     
/*      */     public int size() {
/* 1769 */       return map().size();
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1773 */       map().clear();
/*      */     }
/*      */     
/*      */     public boolean contains(Object o) {
/* 1777 */       if (o instanceof Map.Entry) {
/* 1778 */         Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
/* 1779 */         Object key = entry.getKey();
/* 1780 */         V value = map().get(key);
/* 1781 */         return (Objects.equal(value, entry.getValue()) && (value != null || map().containsKey(key)));
/*      */       } 
/*      */       
/* 1784 */       return false;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1788 */       return map().isEmpty();
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1792 */       if (contains(o)) {
/* 1793 */         Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
/* 1794 */         return map().keySet().remove(entry.getKey());
/*      */       } 
/* 1796 */       return false;
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/*      */       try {
/* 1801 */         return super.removeAll((Collection)Preconditions.checkNotNull(c));
/* 1802 */       } catch (UnsupportedOperationException e) {
/*      */         
/* 1804 */         boolean changed = true;
/* 1805 */         for (Object o : c) {
/* 1806 */           changed |= remove(o);
/*      */         }
/* 1808 */         return changed;
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/*      */       try {
/* 1814 */         return super.retainAll((Collection)Preconditions.checkNotNull(c));
/* 1815 */       } catch (UnsupportedOperationException e) {
/*      */         
/* 1817 */         Set<Object> keys = Sets.newHashSetWithExpectedSize(c.size());
/* 1818 */         for (Object o : c) {
/* 1819 */           if (contains(o)) {
/* 1820 */             Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
/* 1821 */             keys.add(entry.getKey());
/*      */           } 
/*      */         } 
/* 1824 */         return map().keySet().retainAll(keys);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static interface EntryTransformer<K, V1, V2> {
/*      */     V2 transformEntry(@Nullable K param1K, @Nullable V1 param1V1);
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Maps.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */