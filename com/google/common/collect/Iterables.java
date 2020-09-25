/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import com.google.common.annotations.GwtIncompatible;
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Objects;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.base.Predicate;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Queue;
/*      */ import java.util.Set;
/*      */ import java.util.SortedSet;
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
/*      */ @GwtCompatible(emulated = true)
/*      */ public final class Iterables
/*      */ {
/*      */   public static <T> Iterable<T> unmodifiableIterable(Iterable<T> iterable) {
/*   63 */     Preconditions.checkNotNull(iterable);
/*   64 */     if (iterable instanceof UnmodifiableIterable || iterable instanceof ImmutableCollection)
/*      */     {
/*   66 */       return iterable;
/*      */     }
/*   68 */     return new UnmodifiableIterable<T>(iterable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <E> Iterable<E> unmodifiableIterable(ImmutableCollection<E> iterable) {
/*   79 */     return (Iterable<E>)Preconditions.checkNotNull(iterable);
/*      */   }
/*      */   
/*      */   private static final class UnmodifiableIterable<T> implements Iterable<T> {
/*      */     private final Iterable<T> iterable;
/*      */     
/*      */     private UnmodifiableIterable(Iterable<T> iterable) {
/*   86 */       this.iterable = iterable;
/*      */     }
/*      */ 
/*      */     
/*      */     public Iterator<T> iterator() {
/*   91 */       return Iterators.unmodifiableIterator(this.iterable.iterator());
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*   96 */       return this.iterable.toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int size(Iterable<?> iterable) {
/*  105 */     return (iterable instanceof Collection) ? ((Collection)iterable).size() : Iterators.size(iterable.iterator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean contains(Iterable<?> iterable, @Nullable Object element) {
/*  116 */     if (iterable instanceof Collection) {
/*  117 */       Collection<?> collection = (Collection)iterable;
/*      */       try {
/*  119 */         return collection.contains(element);
/*  120 */       } catch (NullPointerException e) {
/*  121 */         return false;
/*  122 */       } catch (ClassCastException e) {
/*  123 */         return false;
/*      */       } 
/*      */     } 
/*  126 */     return Iterators.contains(iterable.iterator(), element);
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
/*      */   public static boolean removeAll(Iterable<?> removeFrom, Collection<?> elementsToRemove) {
/*  142 */     return (removeFrom instanceof Collection) ? ((Collection)removeFrom).removeAll((Collection)Preconditions.checkNotNull(elementsToRemove)) : Iterators.removeAll(removeFrom.iterator(), elementsToRemove);
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
/*      */   public static boolean retainAll(Iterable<?> removeFrom, Collection<?> elementsToRetain) {
/*  160 */     return (removeFrom instanceof Collection) ? ((Collection)removeFrom).retainAll((Collection)Preconditions.checkNotNull(elementsToRetain)) : Iterators.retainAll(removeFrom.iterator(), elementsToRetain);
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
/*      */   public static <T> boolean removeIf(Iterable<T> removeFrom, Predicate<? super T> predicate) {
/*  180 */     if (removeFrom instanceof java.util.RandomAccess && removeFrom instanceof List) {
/*  181 */       return removeIfFromRandomAccessList((List)removeFrom, (Predicate)Preconditions.checkNotNull(predicate));
/*      */     }
/*      */     
/*  184 */     return Iterators.removeIf(removeFrom.iterator(), predicate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> boolean removeIfFromRandomAccessList(List<T> list, Predicate<? super T> predicate) {
/*  191 */     int from = 0;
/*  192 */     int to = 0;
/*      */     
/*  194 */     for (; from < list.size(); from++) {
/*  195 */       T element = list.get(from);
/*  196 */       if (!predicate.apply(element)) {
/*  197 */         if (from > to) {
/*      */           try {
/*  199 */             list.set(to, element);
/*  200 */           } catch (UnsupportedOperationException e) {
/*  201 */             slowRemoveIfForRemainingElements(list, predicate, to, from);
/*  202 */             return true;
/*      */           } 
/*      */         }
/*  205 */         to++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  210 */     list.subList(to, list.size()).clear();
/*  211 */     return (from != to);
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
/*      */   private static <T> void slowRemoveIfForRemainingElements(List<T> list, Predicate<? super T> predicate, int to, int from) {
/*      */     int n;
/*  226 */     for (n = list.size() - 1; n > from; n--) {
/*  227 */       if (predicate.apply(list.get(n))) {
/*  228 */         list.remove(n);
/*      */       }
/*      */     } 
/*      */     
/*  232 */     for (n = from - 1; n >= to; n--) {
/*  233 */       list.remove(n);
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
/*      */   public static boolean elementsEqual(Iterable<?> iterable1, Iterable<?> iterable2) {
/*  246 */     return Iterators.elementsEqual(iterable1.iterator(), iterable2.iterator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(Iterable<?> iterable) {
/*  254 */     return Iterators.toString(iterable.iterator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T getOnlyElement(Iterable<T> iterable) {
/*  265 */     return Iterators.getOnlyElement(iterable.iterator());
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
/*      */   public static <T> T getOnlyElement(Iterable<T> iterable, @Nullable T defaultValue) {
/*  277 */     return Iterators.getOnlyElement(iterable.iterator(), defaultValue);
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
/*      */   @GwtIncompatible("Array.newInstance(Class, int)")
/*      */   public static <T> T[] toArray(Iterable<? extends T> iterable, Class<T> type) {
/*  290 */     Collection<? extends T> collection = toCollection(iterable);
/*  291 */     T[] array = ObjectArrays.newArray(type, collection.size());
/*  292 */     return collection.toArray(array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Object[] toArray(Iterable<?> iterable) {
/*  303 */     return toCollection(iterable).toArray();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <E> Collection<E> toCollection(Iterable<E> iterable) {
/*  312 */     return (iterable instanceof Collection) ? (Collection<E>)iterable : Lists.<E>newArrayList(iterable.iterator());
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
/*      */   public static <T> boolean addAll(Collection<T> addTo, Iterable<? extends T> elementsToAdd) {
/*  325 */     if (elementsToAdd instanceof Collection) {
/*  326 */       Collection<? extends T> c = Collections2.cast(elementsToAdd);
/*  327 */       return addTo.addAll(c);
/*      */     } 
/*  329 */     return Iterators.addAll(addTo, elementsToAdd.iterator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int frequency(Iterable<?> iterable, @Nullable Object element) {
/*  340 */     if (iterable instanceof Multiset) {
/*  341 */       return ((Multiset)iterable).count(element);
/*      */     }
/*  343 */     if (iterable instanceof Set) {
/*  344 */       return ((Set)iterable).contains(element) ? 1 : 0;
/*      */     }
/*  346 */     return Iterators.frequency(iterable.iterator(), element);
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
/*      */   public static <T> Iterable<T> cycle(final Iterable<T> iterable) {
/*  367 */     Preconditions.checkNotNull(iterable);
/*  368 */     return new Iterable<T>()
/*      */       {
/*      */         public Iterator<T> iterator() {
/*  371 */           return Iterators.cycle(iterable);
/*      */         }
/*      */         public String toString() {
/*  374 */           return iterable.toString() + " (cycled)";
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
/*      */   public static <T> Iterable<T> cycle(T... elements) {
/*  398 */     return cycle(Lists.newArrayList(elements));
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
/*      */   public static <T> Iterable<T> concat(Iterable<? extends T> a, Iterable<? extends T> b) {
/*  412 */     Preconditions.checkNotNull(a);
/*  413 */     Preconditions.checkNotNull(b);
/*  414 */     return concat(Arrays.asList((Iterable<? extends T>[])new Iterable[] { a, b }));
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
/*      */   public static <T> Iterable<T> concat(Iterable<? extends T> a, Iterable<? extends T> b, Iterable<? extends T> c) {
/*  429 */     Preconditions.checkNotNull(a);
/*  430 */     Preconditions.checkNotNull(b);
/*  431 */     Preconditions.checkNotNull(c);
/*  432 */     return concat(Arrays.asList((Iterable<? extends T>[])new Iterable[] { a, b, c }));
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
/*      */   public static <T> Iterable<T> concat(Iterable<? extends T> a, Iterable<? extends T> b, Iterable<? extends T> c, Iterable<? extends T> d) {
/*  449 */     Preconditions.checkNotNull(a);
/*  450 */     Preconditions.checkNotNull(b);
/*  451 */     Preconditions.checkNotNull(c);
/*  452 */     Preconditions.checkNotNull(d);
/*  453 */     return concat(Arrays.asList((Iterable<? extends T>[])new Iterable[] { a, b, c, d }));
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
/*      */   public static <T> Iterable<T> concat(Iterable<? extends T>... inputs) {
/*  467 */     return concat(ImmutableList.copyOf(inputs));
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
/*      */   public static <T> Iterable<T> concat(final Iterable<? extends Iterable<? extends T>> inputs) {
/*  482 */     Preconditions.checkNotNull(inputs);
/*  483 */     return new IterableWithToString<T>()
/*      */       {
/*      */         public Iterator<T> iterator() {
/*  486 */           return Iterators.concat(Iterables.iterators(inputs));
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> UnmodifiableIterator<Iterator<? extends T>> iterators(Iterable<? extends Iterable<? extends T>> iterables) {
/*  496 */     final Iterator<? extends Iterable<? extends T>> iterableIterator = iterables.iterator();
/*      */     
/*  498 */     return new UnmodifiableIterator<Iterator<? extends T>>()
/*      */       {
/*      */         public boolean hasNext() {
/*  501 */           return iterableIterator.hasNext();
/*      */         }
/*      */         
/*      */         public Iterator<? extends T> next() {
/*  505 */           return ((Iterable<? extends T>)iterableIterator.next()).iterator();
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
/*      */   public static <T> Iterable<List<T>> partition(final Iterable<T> iterable, final int size) {
/*  532 */     Preconditions.checkNotNull(iterable);
/*  533 */     Preconditions.checkArgument((size > 0));
/*  534 */     return new IterableWithToString<List<T>>()
/*      */       {
/*      */         public Iterator<List<T>> iterator() {
/*  537 */           return Iterators.partition(iterable.iterator(), size);
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
/*      */   public static <T> Iterable<List<T>> paddedPartition(final Iterable<T> iterable, final int size) {
/*  561 */     Preconditions.checkNotNull(iterable);
/*  562 */     Preconditions.checkArgument((size > 0));
/*  563 */     return new IterableWithToString<List<T>>()
/*      */       {
/*      */         public Iterator<List<T>> iterator() {
/*  566 */           return Iterators.paddedPartition(iterable.iterator(), size);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Iterable<T> filter(final Iterable<T> unfiltered, final Predicate<? super T> predicate) {
/*  577 */     Preconditions.checkNotNull(unfiltered);
/*  578 */     Preconditions.checkNotNull(predicate);
/*  579 */     return new IterableWithToString<T>()
/*      */       {
/*      */         public Iterator<T> iterator() {
/*  582 */           return Iterators.filter(unfiltered.iterator(), predicate);
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
/*      */   @GwtIncompatible("Class.isInstance")
/*      */   public static <T> Iterable<T> filter(final Iterable<?> unfiltered, final Class<T> type) {
/*  601 */     Preconditions.checkNotNull(unfiltered);
/*  602 */     Preconditions.checkNotNull(type);
/*  603 */     return new IterableWithToString<T>()
/*      */       {
/*      */         public Iterator<T> iterator() {
/*  606 */           return Iterators.filter(unfiltered.iterator(), type);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> boolean any(Iterable<T> iterable, Predicate<? super T> predicate) {
/*  617 */     return Iterators.any(iterable.iterator(), predicate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> boolean all(Iterable<T> iterable, Predicate<? super T> predicate) {
/*  626 */     return Iterators.all(iterable.iterator(), predicate);
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
/*      */   public static <T> T find(Iterable<T> iterable, Predicate<? super T> predicate) {
/*  638 */     return Iterators.find(iterable.iterator(), predicate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T find(Iterable<T> iterable, Predicate<? super T> predicate, @Nullable T defaultValue) {
/*  649 */     return Iterators.find(iterable.iterator(), predicate, defaultValue);
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
/*      */   public static <T> int indexOf(Iterable<T> iterable, Predicate<? super T> predicate) {
/*  665 */     return Iterators.indexOf(iterable.iterator(), predicate);
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
/*      */   public static <F, T> Iterable<T> transform(final Iterable<F> fromIterable, final Function<? super F, ? extends T> function) {
/*  682 */     Preconditions.checkNotNull(fromIterable);
/*  683 */     Preconditions.checkNotNull(function);
/*  684 */     return new IterableWithToString<T>()
/*      */       {
/*      */         public Iterator<T> iterator() {
/*  687 */           return Iterators.transform(fromIterable.iterator(), function);
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
/*      */   public static <T> T get(Iterable<T> iterable, int position) {
/*  701 */     Preconditions.checkNotNull(iterable);
/*  702 */     if (iterable instanceof List) {
/*  703 */       return ((List<T>)iterable).get(position);
/*      */     }
/*      */     
/*  706 */     if (iterable instanceof Collection) {
/*      */       
/*  708 */       Collection<T> collection = (Collection<T>)iterable;
/*  709 */       Preconditions.checkElementIndex(position, collection.size());
/*      */     } else {
/*      */       
/*  712 */       checkNonnegativeIndex(position);
/*      */     } 
/*  714 */     return Iterators.get(iterable.iterator(), position);
/*      */   }
/*      */   
/*      */   private static void checkNonnegativeIndex(int position) {
/*  718 */     if (position < 0) {
/*  719 */       throw new IndexOutOfBoundsException("position cannot be negative: " + position);
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
/*      */   public static <T> T get(Iterable<T> iterable, int position, @Nullable T defaultValue) {
/*  739 */     Preconditions.checkNotNull(iterable);
/*  740 */     checkNonnegativeIndex(position);
/*      */     
/*      */     try {
/*  743 */       return get(iterable, position);
/*  744 */     } catch (IndexOutOfBoundsException e) {
/*  745 */       return defaultValue;
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
/*      */   public static <T> T getFirst(Iterable<T> iterable, @Nullable T defaultValue) {
/*  759 */     return Iterators.getNext(iterable.iterator(), defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T getLast(Iterable<T> iterable) {
/*  770 */     if (iterable instanceof List) {
/*  771 */       List<T> list = (List<T>)iterable;
/*  772 */       if (list.isEmpty()) {
/*  773 */         throw new NoSuchElementException();
/*      */       }
/*  775 */       return getLastInNonemptyList(list);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  783 */     if (iterable instanceof SortedSet) {
/*  784 */       SortedSet<T> sortedSet = (SortedSet<T>)iterable;
/*  785 */       return sortedSet.last();
/*      */     } 
/*      */     
/*  788 */     return Iterators.getLast(iterable.iterator());
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
/*      */   public static <T> T getLast(Iterable<T> iterable, @Nullable T defaultValue) {
/*  800 */     if (iterable instanceof Collection) {
/*  801 */       Collection<T> collection = (Collection<T>)iterable;
/*  802 */       if (collection.isEmpty()) {
/*  803 */         return defaultValue;
/*      */       }
/*      */     } 
/*      */     
/*  807 */     if (iterable instanceof List) {
/*  808 */       List<T> list = (List<T>)iterable;
/*  809 */       return getLastInNonemptyList(list);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  817 */     if (iterable instanceof SortedSet) {
/*  818 */       SortedSet<T> sortedSet = (SortedSet<T>)iterable;
/*  819 */       return sortedSet.last();
/*      */     } 
/*      */     
/*  822 */     return Iterators.getLast(iterable.iterator(), defaultValue);
/*      */   }
/*      */   
/*      */   private static <T> T getLastInNonemptyList(List<T> list) {
/*  826 */     return list.get(list.size() - 1);
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
/*      */   public static <T> Iterable<T> skip(final Iterable<T> iterable, final int numberToSkip) {
/*  851 */     Preconditions.checkNotNull(iterable);
/*  852 */     Preconditions.checkArgument((numberToSkip >= 0), "number to skip cannot be negative");
/*      */     
/*  854 */     if (iterable instanceof List) {
/*  855 */       final List<T> list = (List<T>)iterable;
/*  856 */       return new IterableWithToString<T>()
/*      */         {
/*      */           public Iterator<T> iterator()
/*      */           {
/*  860 */             return (numberToSkip >= list.size()) ? Iterators.<T>emptyIterator() : list.subList(numberToSkip, list.size()).iterator();
/*      */           }
/*      */         };
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  867 */     return new IterableWithToString<T>()
/*      */       {
/*      */         public Iterator<T> iterator() {
/*  870 */           final Iterator<T> iterator = iterable.iterator();
/*      */           
/*  872 */           Iterators.skip(iterator, numberToSkip);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  879 */           return new Iterator()
/*      */             {
/*      */               boolean atStart = true;
/*      */               
/*      */               public boolean hasNext() {
/*  884 */                 return iterator.hasNext();
/*      */               }
/*      */ 
/*      */               
/*      */               public T next() {
/*  889 */                 if (!hasNext()) {
/*  890 */                   throw new NoSuchElementException();
/*      */                 }
/*      */                 
/*      */                 try {
/*  894 */                   return (T)iterator.next();
/*      */                 } finally {
/*  896 */                   this.atStart = false;
/*      */                 } 
/*      */               }
/*      */ 
/*      */               
/*      */               public void remove() {
/*  902 */                 if (this.atStart) {
/*  903 */                   throw new IllegalStateException();
/*      */                 }
/*  905 */                 iterator.remove();
/*      */               }
/*      */             };
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
/*      */   public static <T> Iterable<T> limit(final Iterable<T> iterable, final int limitSize) {
/*  926 */     Preconditions.checkNotNull(iterable);
/*  927 */     Preconditions.checkArgument((limitSize >= 0), "limit is negative");
/*  928 */     return new IterableWithToString<T>()
/*      */       {
/*      */         public Iterator<T> iterator() {
/*  931 */           return Iterators.limit(iterable.iterator(), limitSize);
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
/*      */   public static <T> Iterable<T> consumingIterable(final Iterable<T> iterable) {
/*  956 */     if (iterable instanceof Queue) {
/*  957 */       return new Iterable<T>()
/*      */         {
/*      */           public Iterator<T> iterator() {
/*  960 */             return new Iterables.ConsumingQueueIterator<T>((Queue)iterable);
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*  965 */     Preconditions.checkNotNull(iterable);
/*      */     
/*  967 */     return new Iterable<T>()
/*      */       {
/*      */         public Iterator<T> iterator() {
/*  970 */           return Iterators.consumingIterator(iterable.iterator());
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private static class ConsumingQueueIterator<T> extends AbstractIterator<T> {
/*      */     private final Queue<T> queue;
/*      */     
/*      */     private ConsumingQueueIterator(Queue<T> queue) {
/*  979 */       this.queue = queue;
/*      */     }
/*      */     
/*      */     public T computeNext() {
/*      */       try {
/*  984 */         return this.queue.remove();
/*  985 */       } catch (NoSuchElementException e) {
/*  986 */         return endOfData();
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
/*      */   @Deprecated
/*      */   public static <T> Iterable<T> reverse(List<T> list) {
/* 1014 */     return Lists.reverse(list);
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
/*      */   public static <T> boolean isEmpty(Iterable<T> iterable) {
/* 1027 */     return !iterable.iterator().hasNext();
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
/*      */   static boolean remove(Iterable<?> iterable, @Nullable Object o) {
/* 1051 */     Iterator<?> i = iterable.iterator();
/* 1052 */     while (i.hasNext()) {
/* 1053 */       if (Objects.equal(i.next(), o)) {
/* 1054 */         i.remove();
/* 1055 */         return true;
/*      */       } 
/*      */     } 
/* 1058 */     return false;
/*      */   }
/*      */   
/*      */   static abstract class IterableWithToString<E> implements Iterable<E> {
/*      */     public String toString() {
/* 1063 */       return Iterables.toString(this);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Iterables.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */