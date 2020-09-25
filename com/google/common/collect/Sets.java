/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import com.google.common.annotations.GwtIncompatible;
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Objects;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.base.Predicates;
/*      */ import com.google.common.primitives.Ints;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.EnumSet;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
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
/*      */ public final class Sets
/*      */ {
/*      */   @GwtCompatible(serializable = true)
/*      */   public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(E anElement, E... otherElements) {
/*   81 */     return new ImmutableEnumSet<E>(EnumSet.of(anElement, otherElements));
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
/*      */   @GwtCompatible(serializable = true)
/*      */   public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(Iterable<E> elements) {
/*   99 */     Iterator<E> iterator = elements.iterator();
/*  100 */     if (!iterator.hasNext()) {
/*  101 */       return ImmutableSet.of();
/*      */     }
/*  103 */     if (elements instanceof EnumSet) {
/*  104 */       EnumSet<E> enumSetClone = EnumSet.copyOf((EnumSet<E>)elements);
/*  105 */       return new ImmutableEnumSet<E>(enumSetClone);
/*      */     } 
/*  107 */     Enum enum_ = (Enum)iterator.next();
/*  108 */     EnumSet<E> set = EnumSet.of((E)enum_);
/*  109 */     while (iterator.hasNext()) {
/*  110 */       set.add(iterator.next());
/*      */     }
/*  112 */     return new ImmutableEnumSet<E>(set);
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
/*      */   public static <E extends Enum<E>> EnumSet<E> newEnumSet(Iterable<E> iterable, Class<E> elementType) {
/*  136 */     Preconditions.checkNotNull(iterable);
/*  137 */     EnumSet<E> set = EnumSet.noneOf(elementType);
/*  138 */     Iterables.addAll(set, iterable);
/*  139 */     return set;
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
/*      */   public static <E> HashSet<E> newHashSet() {
/*  156 */     return new HashSet<E>();
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
/*      */   public static <E> HashSet<E> newHashSet(E... elements) {
/*  174 */     HashSet<E> set = newHashSetWithExpectedSize(elements.length);
/*  175 */     Collections.addAll(set, elements);
/*  176 */     return set;
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
/*      */   public static <E> HashSet<E> newHashSetWithExpectedSize(int expectedSize) {
/*  193 */     return new HashSet<E>(Maps.capacity(expectedSize));
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
/*      */   public static <E> HashSet<E> newHashSet(Iterable<? extends E> elements) {
/*  210 */     return (elements instanceof Collection) ? new HashSet<E>(Collections2.cast(elements)) : newHashSet(elements.iterator());
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
/*      */   public static <E> HashSet<E> newHashSet(Iterator<? extends E> elements) {
/*  229 */     HashSet<E> set = newHashSet();
/*  230 */     while (elements.hasNext()) {
/*  231 */       set.add(elements.next());
/*      */     }
/*  233 */     return set;
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
/*      */   public static <E> LinkedHashSet<E> newLinkedHashSet() {
/*  247 */     return new LinkedHashSet<E>();
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
/*      */   public static <E> LinkedHashSet<E> newLinkedHashSet(Iterable<? extends E> elements) {
/*  263 */     if (elements instanceof Collection) {
/*  264 */       return new LinkedHashSet<E>(Collections2.cast(elements));
/*      */     }
/*  266 */     LinkedHashSet<E> set = newLinkedHashSet();
/*  267 */     for (E element : elements) {
/*  268 */       set.add(element);
/*      */     }
/*  270 */     return set;
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
/*      */   public static <E extends Comparable> TreeSet<E> newTreeSet() {
/*  285 */     return new TreeSet<E>();
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
/*      */   public static <E extends Comparable> TreeSet<E> newTreeSet(Iterable<? extends E> elements) {
/*  305 */     TreeSet<E> set = newTreeSet();
/*  306 */     for (Comparable comparable : elements) {
/*  307 */       set.add((E)comparable);
/*      */     }
/*  309 */     return set;
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
/*      */   public static <E> TreeSet<E> newTreeSet(Comparator<? super E> comparator) {
/*  324 */     return new TreeSet<E>((Comparator<? super E>)Preconditions.checkNotNull(comparator));
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
/*      */   public static <E> Set<E> newIdentityHashSet() {
/*  338 */     return newSetFromMap(Maps.newIdentityHashMap());
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
/*      */   public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection) {
/*  358 */     if (collection instanceof EnumSet) {
/*  359 */       return EnumSet.complementOf((EnumSet<E>)collection);
/*      */     }
/*  361 */     Preconditions.checkArgument(!collection.isEmpty(), "collection is empty; use the other version of this method");
/*      */     
/*  363 */     Class<E> type = ((Enum<E>)collection.iterator().next()).getDeclaringClass();
/*  364 */     return makeComplementByHand(collection, type);
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
/*      */   public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection, Class<E> type) {
/*  381 */     Preconditions.checkNotNull(collection);
/*  382 */     return (collection instanceof EnumSet) ? EnumSet.<E>complementOf((EnumSet<E>)collection) : makeComplementByHand(collection, type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <E extends Enum<E>> EnumSet<E> makeComplementByHand(Collection<E> collection, Class<E> type) {
/*  389 */     EnumSet<E> result = EnumSet.allOf(type);
/*  390 */     result.removeAll(collection);
/*  391 */     return result;
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
/*      */   public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
/*  434 */     return new SetFromMap<E>(map);
/*      */   }
/*      */   
/*      */   private static class SetFromMap<E>
/*      */     extends AbstractSet<E>
/*      */     implements Set<E>, Serializable {
/*      */     private final Map<E, Boolean> m;
/*      */     
/*      */     SetFromMap(Map<E, Boolean> map) {
/*  443 */       Preconditions.checkArgument(map.isEmpty(), "Map is non-empty");
/*  444 */       this.m = map;
/*  445 */       this.s = map.keySet();
/*      */     } private transient Set<E> s; @GwtIncompatible("not needed in emulated source")
/*      */     private static final long serialVersionUID = 0L;
/*      */     public void clear() {
/*  449 */       this.m.clear();
/*      */     }
/*      */     public int size() {
/*  452 */       return this.m.size();
/*      */     }
/*      */     public boolean isEmpty() {
/*  455 */       return this.m.isEmpty();
/*      */     }
/*      */     public boolean contains(Object o) {
/*  458 */       return this.m.containsKey(o);
/*      */     }
/*      */     public boolean remove(Object o) {
/*  461 */       return (this.m.remove(o) != null);
/*      */     }
/*      */     public boolean add(E e) {
/*  464 */       return (this.m.put(e, Boolean.TRUE) == null);
/*      */     }
/*      */     public Iterator<E> iterator() {
/*  467 */       return this.s.iterator();
/*      */     }
/*      */     public Object[] toArray() {
/*  470 */       return this.s.toArray();
/*      */     }
/*      */     public <T> T[] toArray(T[] a) {
/*  473 */       return this.s.toArray(a);
/*      */     }
/*      */     public String toString() {
/*  476 */       return this.s.toString();
/*      */     }
/*      */     public int hashCode() {
/*  479 */       return this.s.hashCode();
/*      */     }
/*      */     public boolean equals(@Nullable Object object) {
/*  482 */       return (this == object || this.s.equals(object));
/*      */     }
/*      */     public boolean containsAll(Collection<?> c) {
/*  485 */       return this.s.containsAll(c);
/*      */     }
/*      */     public boolean removeAll(Collection<?> c) {
/*  488 */       return this.s.removeAll(c);
/*      */     }
/*      */     public boolean retainAll(Collection<?> c) {
/*  491 */       return this.s.retainAll(c);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GwtIncompatible("java.io.ObjectInputStream")
/*      */     private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/*  501 */       stream.defaultReadObject();
/*  502 */       this.s = this.m.keySet();
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
/*      */   public static abstract class SetView<E>
/*      */     extends AbstractSet<E>
/*      */   {
/*      */     private SetView() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ImmutableSet<E> immutableCopy() {
/*  529 */       return ImmutableSet.copyOf(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public <S extends Set<E>> S copyInto(S set) {
/*  542 */       set.addAll(this);
/*  543 */       return set;
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
/*      */   public static <E> SetView<E> union(final Set<? extends E> set1, final Set<? extends E> set2) {
/*  564 */     Preconditions.checkNotNull(set1, "set1");
/*  565 */     Preconditions.checkNotNull(set2, "set2");
/*      */     
/*  567 */     final Set<? extends E> set2minus1 = difference(set2, set1);
/*      */     
/*  569 */     return new SetView<E>() {
/*      */         public int size() {
/*  571 */           return set1.size() + set2minus1.size();
/*      */         }
/*      */         public boolean isEmpty() {
/*  574 */           return (set1.isEmpty() && set2.isEmpty());
/*      */         }
/*      */         public Iterator<E> iterator() {
/*  577 */           return Iterators.unmodifiableIterator(Iterators.concat(set1.iterator(), set2minus1.iterator()));
/*      */         }
/*      */         
/*      */         public boolean contains(Object object) {
/*  581 */           return (set1.contains(object) || set2.contains(object));
/*      */         }
/*      */         public <S extends Set<E>> S copyInto(S set) {
/*  584 */           set.addAll(set1);
/*  585 */           set.addAll(set2);
/*  586 */           return set;
/*      */         }
/*      */         public ImmutableSet<E> immutableCopy() {
/*  589 */           return (new ImmutableSet.Builder<E>()).addAll(set1).addAll(set2).build();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <E> SetView<E> intersection(final Set<E> set1, final Set<?> set2) {
/*  623 */     Preconditions.checkNotNull(set1, "set1");
/*  624 */     Preconditions.checkNotNull(set2, "set2");
/*      */     
/*  626 */     final Predicate<Object> inSet2 = Predicates.in(set2);
/*  627 */     return new SetView<E>() {
/*      */         public Iterator<E> iterator() {
/*  629 */           return Iterators.filter(set1.iterator(), inSet2);
/*      */         }
/*      */         public int size() {
/*  632 */           return Iterators.size(iterator());
/*      */         }
/*      */         public boolean isEmpty() {
/*  635 */           return !iterator().hasNext();
/*      */         }
/*      */         public boolean contains(Object object) {
/*  638 */           return (set1.contains(object) && set2.contains(object));
/*      */         }
/*      */         public boolean containsAll(Collection<?> collection) {
/*  641 */           return (set1.containsAll(collection) && set2.containsAll(collection));
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
/*      */   public static <E> SetView<E> difference(final Set<E> set1, final Set<?> set2) {
/*  660 */     Preconditions.checkNotNull(set1, "set1");
/*  661 */     Preconditions.checkNotNull(set2, "set2");
/*      */     
/*  663 */     final Predicate<Object> notInSet2 = Predicates.not(Predicates.in(set2));
/*  664 */     return new SetView<E>() {
/*      */         public Iterator<E> iterator() {
/*  666 */           return Iterators.filter(set1.iterator(), notInSet2);
/*      */         }
/*      */         public int size() {
/*  669 */           return Iterators.size(iterator());
/*      */         }
/*      */         public boolean isEmpty() {
/*  672 */           return set2.containsAll(set1);
/*      */         }
/*      */         public boolean contains(Object element) {
/*  675 */           return (set1.contains(element) && !set2.contains(element));
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
/*      */   public static <E> SetView<E> symmetricDifference(Set<? extends E> set1, Set<? extends E> set2) {
/*  694 */     Preconditions.checkNotNull(set1, "set1");
/*  695 */     Preconditions.checkNotNull(set2, "set2");
/*      */ 
/*      */     
/*  698 */     return difference(union(set1, set2), intersection(set1, set2));
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
/*      */   public static <E> Set<E> filter(Set<E> unfiltered, Predicate<? super E> predicate) {
/*  730 */     if (unfiltered instanceof FilteredSet) {
/*      */ 
/*      */       
/*  733 */       FilteredSet<E> filtered = (FilteredSet<E>)unfiltered;
/*  734 */       Predicate<E> combinedPredicate = Predicates.and(filtered.predicate, predicate);
/*      */       
/*  736 */       return new FilteredSet<E>((Set<E>)filtered.unfiltered, combinedPredicate);
/*      */     } 
/*      */ 
/*      */     
/*  740 */     return new FilteredSet<E>((Set<E>)Preconditions.checkNotNull(unfiltered), (Predicate<? super E>)Preconditions.checkNotNull(predicate));
/*      */   }
/*      */   
/*      */   private static class FilteredSet<E>
/*      */     extends Collections2.FilteredCollection<E>
/*      */     implements Set<E> {
/*      */     FilteredSet(Set<E> unfiltered, Predicate<? super E> predicate) {
/*  747 */       super(unfiltered, predicate);
/*      */     }
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/*  751 */       return Sets.equalsImpl(this, object);
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  755 */       return Sets.hashCodeImpl(this);
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
/*      */   public static <B> Set<List<B>> cartesianProduct(List<? extends Set<? extends B>> sets) {
/*  806 */     for (Set<? extends B> set : sets) {
/*  807 */       if (set.isEmpty()) {
/*  808 */         return ImmutableSet.of();
/*      */       }
/*      */     } 
/*  811 */     CartesianSet<B> cartesianSet = new CartesianSet<B>(sets);
/*  812 */     return cartesianSet;
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
/*      */   public static <B> Set<List<B>> cartesianProduct(Set<? extends B>... sets) {
/*  862 */     return cartesianProduct(Arrays.asList(sets));
/*      */   }
/*      */   
/*      */   private static class CartesianSet<B> extends AbstractSet<List<B>> {
/*      */     final ImmutableList<Axis> axes;
/*      */     final int size;
/*      */     
/*      */     CartesianSet(List<? extends Set<? extends B>> sets) {
/*  870 */       long dividend = 1L;
/*  871 */       ImmutableList.Builder<Axis> builder = ImmutableList.builder();
/*  872 */       for (Set<? extends B> set : sets) {
/*  873 */         Axis axis = new Axis(set, (int)dividend);
/*  874 */         builder.add(axis);
/*  875 */         dividend *= axis.size();
/*  876 */         Preconditions.checkArgument((dividend <= 2147483647L), "cartesian product is too big");
/*      */       } 
/*      */       
/*  879 */       this.axes = builder.build();
/*  880 */       this.size = Ints.checkedCast(dividend);
/*      */     }
/*      */     
/*      */     public int size() {
/*  884 */       return this.size;
/*      */     }
/*      */     
/*      */     public UnmodifiableIterator<List<B>> iterator() {
/*  888 */       return new UnmodifiableIterator<List<B>>()
/*      */         {
/*      */           int index;
/*      */           
/*      */           public boolean hasNext() {
/*  893 */             return (this.index < Sets.CartesianSet.this.size);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public List<B> next() {
/*      */             // Byte code:
/*      */             //   0: aload_0
/*      */             //   1: invokevirtual hasNext : ()Z
/*      */             //   4: ifne -> 15
/*      */             //   7: new java/util/NoSuchElementException
/*      */             //   10: dup
/*      */             //   11: invokespecial <init> : ()V
/*      */             //   14: athrow
/*      */             //   15: aload_0
/*      */             //   16: getfield this$0 : Lcom/google/common/collect/Sets$CartesianSet;
/*      */             //   19: getfield axes : Lcom/google/common/collect/ImmutableList;
/*      */             //   22: invokevirtual size : ()I
/*      */             //   25: anewarray java/lang/Object
/*      */             //   28: astore_1
/*      */             //   29: iconst_0
/*      */             //   30: istore_2
/*      */             //   31: iload_2
/*      */             //   32: aload_1
/*      */             //   33: arraylength
/*      */             //   34: if_icmpge -> 67
/*      */             //   37: aload_1
/*      */             //   38: iload_2
/*      */             //   39: aload_0
/*      */             //   40: getfield this$0 : Lcom/google/common/collect/Sets$CartesianSet;
/*      */             //   43: getfield axes : Lcom/google/common/collect/ImmutableList;
/*      */             //   46: iload_2
/*      */             //   47: invokevirtual get : (I)Ljava/lang/Object;
/*      */             //   50: checkcast com/google/common/collect/Sets$CartesianSet$Axis
/*      */             //   53: aload_0
/*      */             //   54: getfield index : I
/*      */             //   57: invokevirtual getForIndex : (I)Ljava/lang/Object;
/*      */             //   60: aastore
/*      */             //   61: iinc #2, 1
/*      */             //   64: goto -> 31
/*      */             //   67: aload_0
/*      */             //   68: dup
/*      */             //   69: getfield index : I
/*      */             //   72: iconst_1
/*      */             //   73: iadd
/*      */             //   74: putfield index : I
/*      */             //   77: aload_1
/*      */             //   78: invokestatic copyOf : ([Ljava/lang/Object;)Lcom/google/common/collect/ImmutableList;
/*      */             //   81: astore_2
/*      */             //   82: aload_2
/*      */             //   83: areturn
/*      */             // Line number table:
/*      */             //   Java source line number -> byte code offset
/*      */             //   #898	-> 0
/*      */             //   #899	-> 7
/*      */             //   #902	-> 15
/*      */             //   #903	-> 29
/*      */             //   #904	-> 37
/*      */             //   #903	-> 61
/*      */             //   #906	-> 67
/*      */             //   #909	-> 77
/*      */             //   #910	-> 82
/*      */             // Local variable table:
/*      */             //   start	length	slot	name	descriptor
/*      */             //   31	36	2	i	I
/*      */             //   0	84	0	this	Lcom/google/common/collect/Sets$CartesianSet$1;
/*      */             //   29	55	1	tuple	[Ljava/lang/Object;
/*      */             //   82	2	2	result	Ljava/util/List;
/*      */             // Local variable type table:
/*      */             //   start	length	slot	name	signature
/*      */             //   0	84	0	this	Lcom/google/common/collect/Sets$CartesianSet.1;
/*      */             //   82	2	2	result	Ljava/util/List<TB;>;
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object element) {
/*  916 */       if (!(element instanceof List)) {
/*  917 */         return false;
/*      */       }
/*  919 */       List<?> tuple = (List)element;
/*  920 */       int dimensions = this.axes.size();
/*  921 */       if (tuple.size() != dimensions) {
/*  922 */         return false;
/*      */       }
/*  924 */       for (int i = 0; i < dimensions; i++) {
/*  925 */         if (!((Axis)this.axes.get(i)).contains(tuple.get(i))) {
/*  926 */           return false;
/*      */         }
/*      */       } 
/*  929 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/*  935 */       if (object instanceof CartesianSet) {
/*  936 */         CartesianSet<?> that = (CartesianSet)object;
/*  937 */         return this.axes.equals(that.axes);
/*      */       } 
/*  939 */       return super.equals(object);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  947 */       int adjust = this.size - 1;
/*  948 */       for (int i = 0; i < this.axes.size(); i++) {
/*  949 */         adjust *= 31;
/*      */       }
/*  951 */       return this.axes.hashCode() + adjust;
/*      */     }
/*      */     
/*      */     private class Axis {
/*      */       final ImmutableSet<? extends B> choices;
/*      */       final ImmutableList<? extends B> choicesList;
/*      */       final int dividend;
/*      */       
/*      */       Axis(Set<? extends B> set, int dividend) {
/*  960 */         this.choices = ImmutableSet.copyOf(set);
/*  961 */         this.choicesList = this.choices.asList();
/*  962 */         this.dividend = dividend;
/*      */       }
/*      */       
/*      */       int size() {
/*  966 */         return this.choices.size();
/*      */       }
/*      */       
/*      */       B getForIndex(int index) {
/*  970 */         return this.choicesList.get(index / this.dividend % size());
/*      */       }
/*      */       
/*      */       boolean contains(Object target) {
/*  974 */         return this.choices.contains(target);
/*      */       }
/*      */       
/*      */       public boolean equals(Object obj) {
/*  978 */         if (obj instanceof Axis) {
/*  979 */           Axis that = (Axis)obj;
/*  980 */           return this.choices.equals(that.choices);
/*      */         } 
/*      */         
/*  983 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int hashCode() {
/*  990 */         return Sets.CartesianSet.this.size / this.choices.size() * this.choices.hashCode();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtCompatible(serializable = false)
/*      */   public static <E> Set<Set<E>> powerSet(Set<E> set) {
/* 1026 */     ImmutableSet<E> input = ImmutableSet.copyOf(set);
/* 1027 */     Preconditions.checkArgument((input.size() <= 30), "Too many elements to create power set: %s > 30", new Object[] { Integer.valueOf(input.size()) });
/*      */     
/* 1029 */     return new PowerSet<E>(input);
/*      */   }
/*      */   
/*      */   private static final class PowerSet<E> extends AbstractSet<Set<E>> {
/*      */     final ImmutableSet<E> inputSet;
/*      */     final ImmutableList<E> inputList;
/*      */     final int powerSetSize;
/*      */     
/*      */     PowerSet(ImmutableSet<E> input) {
/* 1038 */       this.inputSet = input;
/* 1039 */       this.inputList = input.asList();
/* 1040 */       this.powerSetSize = 1 << input.size();
/*      */     }
/*      */     
/*      */     public int size() {
/* 1044 */       return this.powerSetSize;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1048 */       return false;
/*      */     }
/*      */     
/*      */     public Iterator<Set<E>> iterator() {
/* 1052 */       return (Iterator)new AbstractIndexedListIterator<Set<Set<E>>>(this.powerSetSize) {
/*      */           protected Set<E> get(final int setBits) {
/* 1054 */             return new AbstractSet<E>() {
/*      */                 public int size() {
/* 1056 */                   return Integer.bitCount(setBits);
/*      */                 }
/*      */                 public Iterator<E> iterator() {
/* 1059 */                   return new Sets.PowerSet.BitFilteredSetIterator<E>(Sets.PowerSet.this.inputList, setBits);
/*      */                 }
/*      */               };
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     private static final class BitFilteredSetIterator<E>
/*      */       extends UnmodifiableIterator<E> {
/*      */       final ImmutableList<E> input;
/*      */       int remainingSetBits;
/*      */       
/*      */       BitFilteredSetIterator(ImmutableList<E> input, int allSetBits) {
/* 1072 */         this.input = input;
/* 1073 */         this.remainingSetBits = allSetBits;
/*      */       }
/*      */       
/*      */       public boolean hasNext() {
/* 1077 */         return (this.remainingSetBits != 0);
/*      */       }
/*      */       
/*      */       public E next() {
/* 1081 */         int index = Integer.numberOfTrailingZeros(this.remainingSetBits);
/* 1082 */         if (index == 32) {
/* 1083 */           throw new NoSuchElementException();
/*      */         }
/*      */         
/* 1086 */         int currentElementMask = 1 << index;
/* 1087 */         this.remainingSetBits &= currentElementMask ^ 0xFFFFFFFF;
/* 1088 */         return this.input.get(index);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean contains(@Nullable Object obj) {
/* 1093 */       if (obj instanceof Set) {
/* 1094 */         Set<?> set = (Set)obj;
/* 1095 */         return this.inputSet.containsAll(set);
/*      */       } 
/* 1097 */       return false;
/*      */     }
/*      */     
/*      */     public boolean equals(@Nullable Object obj) {
/* 1101 */       if (obj instanceof PowerSet) {
/* 1102 */         PowerSet<?> that = (PowerSet)obj;
/* 1103 */         return this.inputSet.equals(that.inputSet);
/*      */       } 
/* 1105 */       return super.equals(obj);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1114 */       return this.inputSet.hashCode() << this.inputSet.size() - 1;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1118 */       return "powerSet(" + this.inputSet + ")";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int hashCodeImpl(Set<?> s) {
/* 1126 */     int hashCode = 0;
/* 1127 */     for (Object o : s) {
/* 1128 */       hashCode += (o != null) ? o.hashCode() : 0;
/*      */     }
/* 1130 */     return hashCode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean equalsImpl(Set<?> s, @Nullable Object object) {
/* 1137 */     if (s == object) {
/* 1138 */       return true;
/*      */     }
/* 1140 */     if (object instanceof Set) {
/* 1141 */       Set<?> o = (Set)object;
/*      */       
/*      */       try {
/* 1144 */         return (s.size() == o.size() && s.containsAll(o));
/* 1145 */       } catch (NullPointerException ignored) {
/* 1146 */         return false;
/* 1147 */       } catch (ClassCastException ignored) {
/* 1148 */         return false;
/*      */       } 
/*      */     } 
/* 1151 */     return false;
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
/*      */   static <A, B> Set<B> transform(Set<A> set, InvertibleFunction<A, B> bijection) {
/* 1168 */     return new TransformedSet<Object, B>((Set)Preconditions.checkNotNull(set, "set"), (InvertibleFunction<?, B>)Preconditions.checkNotNull(bijection, "bijection"));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class InvertibleFunction<A, B>
/*      */     implements Function<A, B>
/*      */   {
/*      */     abstract A invert(B param1B);
/*      */ 
/*      */ 
/*      */     
/*      */     public InvertibleFunction<B, A> inverse() {
/* 1181 */       return new InvertibleFunction<B, A>() {
/*      */           public A apply(B b) {
/* 1183 */             return Sets.InvertibleFunction.this.invert(b);
/*      */           }
/*      */           
/*      */           B invert(A a) {
/* 1187 */             return (B)Sets.InvertibleFunction.this.apply(a);
/*      */           }
/*      */ 
/*      */           
/*      */           public Sets.InvertibleFunction<A, B> inverse() {
/* 1192 */             return Sets.InvertibleFunction.this;
/*      */           }
/*      */         };
/*      */     }
/*      */   }
/*      */   
/*      */   private static class TransformedSet<A, B> extends AbstractSet<B> {
/*      */     final Set<A> delegate;
/*      */     final Sets.InvertibleFunction<A, B> bijection;
/*      */     
/*      */     TransformedSet(Set<A> delegate, Sets.InvertibleFunction<A, B> bijection) {
/* 1203 */       this.delegate = delegate;
/* 1204 */       this.bijection = bijection;
/*      */     }
/*      */     
/*      */     public Iterator<B> iterator() {
/* 1208 */       return Iterators.transform(this.delegate.iterator(), this.bijection);
/*      */     }
/*      */     
/*      */     public int size() {
/* 1212 */       return this.delegate.size();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1217 */       B b = (B)o;
/* 1218 */       A a = this.bijection.invert(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1240 */       return (this.delegate.contains(a) && Objects.equal(this.bijection.apply(a), o));
/*      */     }
/*      */     
/*      */     public boolean add(B b) {
/* 1244 */       return this.delegate.add(this.bijection.invert(b));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1249 */       return (contains(o) && this.delegate.remove(this.bijection.invert((B)o)));
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1253 */       this.delegate.clear();
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Sets.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */