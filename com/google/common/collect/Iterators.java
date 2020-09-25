/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.annotations.Beta;
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import com.google.common.annotations.GwtIncompatible;
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Objects;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.base.Predicates;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.NoSuchElementException;
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
/*      */ @GwtCompatible(emulated = true)
/*      */ public final class Iterators
/*      */ {
/*   59 */   static final UnmodifiableIterator<Object> EMPTY_ITERATOR = new UnmodifiableIterator()
/*      */     {
/*      */       public boolean hasNext()
/*      */       {
/*   63 */         return false;
/*      */       }
/*      */       
/*      */       public Object next() {
/*   67 */         throw new NoSuchElementException();
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> UnmodifiableIterator<T> emptyIterator() {
/*   80 */     return (UnmodifiableIterator)EMPTY_ITERATOR;
/*      */   }
/*      */   
/*   83 */   private static final Iterator<Object> EMPTY_MODIFIABLE_ITERATOR = new Iterator()
/*      */     {
/*      */       public boolean hasNext() {
/*   86 */         return false;
/*      */       }
/*      */       
/*      */       public Object next() {
/*   90 */         throw new NoSuchElementException();
/*      */       }
/*      */       
/*      */       public void remove() {
/*   94 */         throw new IllegalStateException();
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> Iterator<T> emptyModifiableIterator() {
/*  107 */     return (Iterator)EMPTY_MODIFIABLE_ITERATOR;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> UnmodifiableIterator<T> unmodifiableIterator(final Iterator<T> iterator) {
/*  113 */     Preconditions.checkNotNull(iterator);
/*  114 */     if (iterator instanceof UnmodifiableIterator) {
/*  115 */       return (UnmodifiableIterator<T>)iterator;
/*      */     }
/*  117 */     return new UnmodifiableIterator<T>()
/*      */       {
/*      */         public boolean hasNext() {
/*  120 */           return iterator.hasNext();
/*      */         }
/*      */         
/*      */         public T next() {
/*  124 */           return iterator.next();
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
/*      */   @Deprecated
/*      */   public static <T> UnmodifiableIterator<T> unmodifiableIterator(UnmodifiableIterator<T> iterator) {
/*  137 */     return (UnmodifiableIterator<T>)Preconditions.checkNotNull(iterator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int size(Iterator<?> iterator) {
/*  146 */     int count = 0;
/*  147 */     while (iterator.hasNext()) {
/*  148 */       iterator.next();
/*  149 */       count++;
/*      */     } 
/*  151 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean contains(Iterator<?> iterator, @Nullable Object element) {
/*  159 */     if (element == null) {
/*  160 */       while (iterator.hasNext()) {
/*  161 */         if (iterator.next() == null) {
/*  162 */           return true;
/*      */         }
/*      */       } 
/*      */     } else {
/*  166 */       while (iterator.hasNext()) {
/*  167 */         if (element.equals(iterator.next())) {
/*  168 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*  172 */     return false;
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
/*      */   public static boolean removeAll(Iterator<?> removeFrom, Collection<?> elementsToRemove) {
/*  186 */     Preconditions.checkNotNull(elementsToRemove);
/*  187 */     boolean modified = false;
/*  188 */     while (removeFrom.hasNext()) {
/*  189 */       if (elementsToRemove.contains(removeFrom.next())) {
/*  190 */         removeFrom.remove();
/*  191 */         modified = true;
/*      */       } 
/*      */     } 
/*  194 */     return modified;
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
/*      */   public static <T> boolean removeIf(Iterator<T> removeFrom, Predicate<? super T> predicate) {
/*  210 */     Preconditions.checkNotNull(predicate);
/*  211 */     boolean modified = false;
/*  212 */     while (removeFrom.hasNext()) {
/*  213 */       if (predicate.apply(removeFrom.next())) {
/*  214 */         removeFrom.remove();
/*  215 */         modified = true;
/*      */       } 
/*      */     } 
/*  218 */     return modified;
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
/*      */   public static boolean retainAll(Iterator<?> removeFrom, Collection<?> elementsToRetain) {
/*  232 */     Preconditions.checkNotNull(elementsToRetain);
/*  233 */     boolean modified = false;
/*  234 */     while (removeFrom.hasNext()) {
/*  235 */       if (!elementsToRetain.contains(removeFrom.next())) {
/*  236 */         removeFrom.remove();
/*  237 */         modified = true;
/*      */       } 
/*      */     } 
/*  240 */     return modified;
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
/*      */   public static boolean elementsEqual(Iterator<?> iterator1, Iterator<?> iterator2) {
/*  255 */     while (iterator1.hasNext()) {
/*  256 */       if (!iterator2.hasNext()) {
/*  257 */         return false;
/*      */       }
/*  259 */       Object o1 = iterator1.next();
/*  260 */       Object o2 = iterator2.next();
/*  261 */       if (!Objects.equal(o1, o2)) {
/*  262 */         return false;
/*      */       }
/*      */     } 
/*  265 */     return !iterator2.hasNext();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(Iterator<?> iterator) {
/*  274 */     if (!iterator.hasNext()) {
/*  275 */       return "[]";
/*      */     }
/*  277 */     StringBuilder builder = new StringBuilder();
/*  278 */     builder.append('[').append(iterator.next());
/*  279 */     while (iterator.hasNext()) {
/*  280 */       builder.append(", ").append(iterator.next());
/*      */     }
/*  282 */     return builder.append(']').toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T getOnlyElement(Iterator<T> iterator) {
/*  293 */     T first = iterator.next();
/*  294 */     if (!iterator.hasNext()) {
/*  295 */       return first;
/*      */     }
/*      */     
/*  298 */     StringBuilder sb = new StringBuilder();
/*  299 */     sb.append("expected one element but was: <" + first);
/*  300 */     for (int i = 0; i < 4 && iterator.hasNext(); i++) {
/*  301 */       sb.append(", " + iterator.next());
/*      */     }
/*  303 */     if (iterator.hasNext()) {
/*  304 */       sb.append(", ...");
/*      */     }
/*  306 */     sb.append('>');
/*      */     
/*  308 */     throw new IllegalArgumentException(sb.toString());
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
/*      */   public static <T> T getOnlyElement(Iterator<T> iterator, @Nullable T defaultValue) {
/*  320 */     return iterator.hasNext() ? getOnlyElement(iterator) : defaultValue;
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
/*      */   @GwtIncompatible("Array.newInstance(Class, int)")
/*      */   public static <T> T[] toArray(Iterator<? extends T> iterator, Class<T> type) {
/*  335 */     List<T> list = Lists.newArrayList(iterator);
/*  336 */     return Iterables.toArray(list, type);
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
/*      */   public static <T> boolean addAll(Collection<T> addTo, Iterator<? extends T> iterator) {
/*  349 */     Preconditions.checkNotNull(addTo);
/*  350 */     boolean wasModified = false;
/*  351 */     while (iterator.hasNext()) {
/*  352 */       wasModified |= addTo.add(iterator.next());
/*      */     }
/*  354 */     return wasModified;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int frequency(Iterator<?> iterator, @Nullable Object element) {
/*  365 */     int result = 0;
/*  366 */     if (element == null) {
/*  367 */       while (iterator.hasNext()) {
/*  368 */         if (iterator.next() == null) {
/*  369 */           result++;
/*      */         }
/*      */       } 
/*      */     } else {
/*  373 */       while (iterator.hasNext()) {
/*  374 */         if (element.equals(iterator.next())) {
/*  375 */           result++;
/*      */         }
/*      */       } 
/*      */     } 
/*  379 */     return result;
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
/*      */   public static <T> Iterator<T> cycle(final Iterable<T> iterable) {
/*  397 */     Preconditions.checkNotNull(iterable);
/*  398 */     return new Iterator<T>() {
/*  399 */         Iterator<T> iterator = Iterators.emptyIterator();
/*      */         
/*      */         Iterator<T> removeFrom;
/*      */         
/*      */         public boolean hasNext() {
/*  404 */           if (!this.iterator.hasNext()) {
/*  405 */             this.iterator = iterable.iterator();
/*      */           }
/*  407 */           return this.iterator.hasNext();
/*      */         }
/*      */         
/*      */         public T next() {
/*  411 */           if (!hasNext()) {
/*  412 */             throw new NoSuchElementException();
/*      */           }
/*  414 */           this.removeFrom = this.iterator;
/*  415 */           return this.iterator.next();
/*      */         }
/*      */         
/*      */         public void remove() {
/*  419 */           Preconditions.checkState((this.removeFrom != null), "no calls to next() since last call to remove()");
/*      */           
/*  421 */           this.removeFrom.remove();
/*  422 */           this.removeFrom = null;
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
/*      */   public static <T> Iterator<T> cycle(T... elements) {
/*  441 */     return cycle(Lists.newArrayList(elements));
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
/*      */   public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b) {
/*  455 */     Preconditions.checkNotNull(a);
/*  456 */     Preconditions.checkNotNull(b);
/*  457 */     return concat(Arrays.<Iterator<? extends T>>asList((Iterator<? extends T>[])new Iterator[] { a, b }).iterator());
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
/*      */   public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b, Iterator<? extends T> c) {
/*  472 */     Preconditions.checkNotNull(a);
/*  473 */     Preconditions.checkNotNull(b);
/*  474 */     Preconditions.checkNotNull(c);
/*  475 */     return concat(Arrays.<Iterator<? extends T>>asList((Iterator<? extends T>[])new Iterator[] { a, b, c }).iterator());
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
/*      */   public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b, Iterator<? extends T> c, Iterator<? extends T> d) {
/*  491 */     Preconditions.checkNotNull(a);
/*  492 */     Preconditions.checkNotNull(b);
/*  493 */     Preconditions.checkNotNull(c);
/*  494 */     Preconditions.checkNotNull(d);
/*  495 */     return concat(Arrays.<Iterator<? extends T>>asList((Iterator<? extends T>[])new Iterator[] { a, b, c, d }).iterator());
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
/*      */   public static <T> Iterator<T> concat(Iterator<? extends T>... inputs) {
/*  509 */     return concat(ImmutableList.<Iterator<? extends T>>copyOf(inputs).iterator());
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
/*      */   public static <T> Iterator<T> concat(final Iterator<? extends Iterator<? extends T>> inputs) {
/*  523 */     Preconditions.checkNotNull(inputs);
/*  524 */     return new Iterator<T>() {
/*  525 */         Iterator<? extends T> current = Iterators.emptyIterator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         Iterator<? extends T> removeFrom;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*      */           boolean currentHasNext;
/*  539 */           while (!(currentHasNext = ((Iterator)Preconditions.checkNotNull(this.current)).hasNext()) && inputs.hasNext()) {
/*  540 */             this.current = inputs.next();
/*      */           }
/*  542 */           return currentHasNext;
/*      */         }
/*      */         
/*      */         public T next() {
/*  546 */           if (!hasNext()) {
/*  547 */             throw new NoSuchElementException();
/*      */           }
/*  549 */           this.removeFrom = this.current;
/*  550 */           return this.current.next();
/*      */         }
/*      */         
/*      */         public void remove() {
/*  554 */           Preconditions.checkState((this.removeFrom != null), "no calls to next() since last call to remove()");
/*      */           
/*  556 */           this.removeFrom.remove();
/*  557 */           this.removeFrom = null;
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
/*      */   public static <T> UnmodifiableIterator<List<T>> partition(Iterator<T> iterator, int size) {
/*  579 */     return partitionImpl(iterator, size, false);
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
/*      */   public static <T> UnmodifiableIterator<List<T>> paddedPartition(Iterator<T> iterator, int size) {
/*  600 */     return partitionImpl(iterator, size, true);
/*      */   }
/*      */ 
/*      */   
/*      */   private static <T> UnmodifiableIterator<List<T>> partitionImpl(final Iterator<T> iterator, final int size, final boolean pad) {
/*  605 */     Preconditions.checkNotNull(iterator);
/*  606 */     Preconditions.checkArgument((size > 0));
/*  607 */     return new UnmodifiableIterator<List<T>>()
/*      */       {
/*      */         public boolean hasNext() {
/*  610 */           return iterator.hasNext();
/*      */         }
/*      */         
/*      */         public List<T> next() {
/*  614 */           if (!hasNext()) {
/*  615 */             throw new NoSuchElementException();
/*      */           }
/*  617 */           Object[] array = new Object[size];
/*  618 */           int count = 0;
/*  619 */           for (; count < size && iterator.hasNext(); count++) {
/*  620 */             array[count] = iterator.next();
/*      */           }
/*  622 */           for (int i = count; i < size; i++) {
/*  623 */             array[i] = null;
/*      */           }
/*      */ 
/*      */           
/*  627 */           List<T> list = Collections.unmodifiableList(Arrays.asList((T[])array));
/*      */           
/*  629 */           return (pad || count == size) ? list : list.subList(0, count);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> UnmodifiableIterator<T> filter(final Iterator<T> unfiltered, final Predicate<? super T> predicate) {
/*  639 */     Preconditions.checkNotNull(unfiltered);
/*  640 */     Preconditions.checkNotNull(predicate);
/*  641 */     return new AbstractIterator<T>() {
/*      */         protected T computeNext() {
/*  643 */           while (unfiltered.hasNext()) {
/*  644 */             T element = unfiltered.next();
/*  645 */             if (predicate.apply(element)) {
/*  646 */               return element;
/*      */             }
/*      */           } 
/*  649 */           return endOfData();
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
/*      */   public static <T> UnmodifiableIterator<T> filter(Iterator<?> unfiltered, Class<T> type) {
/*  668 */     return filter((Iterator)unfiltered, Predicates.instanceOf(type));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> boolean any(Iterator<T> iterator, Predicate<? super T> predicate) {
/*  678 */     Preconditions.checkNotNull(predicate);
/*  679 */     while (iterator.hasNext()) {
/*  680 */       T element = iterator.next();
/*  681 */       if (predicate.apply(element)) {
/*  682 */         return true;
/*      */       }
/*      */     } 
/*  685 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> boolean all(Iterator<T> iterator, Predicate<? super T> predicate) {
/*  695 */     Preconditions.checkNotNull(predicate);
/*  696 */     while (iterator.hasNext()) {
/*  697 */       T element = iterator.next();
/*  698 */       if (!predicate.apply(element)) {
/*  699 */         return false;
/*      */       }
/*      */     } 
/*  702 */     return true;
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
/*      */   public static <T> T find(Iterator<T> iterator, Predicate<? super T> predicate) {
/*  715 */     return filter(iterator, predicate).next();
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
/*      */   public static <T> T find(Iterator<T> iterator, Predicate<? super T> predicate, @Nullable T defaultValue) {
/*  728 */     UnmodifiableIterator<T> filteredIterator = filter(iterator, predicate);
/*  729 */     return filteredIterator.hasNext() ? filteredIterator.next() : defaultValue;
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
/*      */   public static <T> int indexOf(Iterator<T> iterator, Predicate<? super T> predicate) {
/*  750 */     Preconditions.checkNotNull(predicate, "predicate");
/*  751 */     int i = 0;
/*  752 */     while (iterator.hasNext()) {
/*  753 */       T current = iterator.next();
/*  754 */       if (predicate.apply(current)) {
/*  755 */         return i;
/*      */       }
/*  757 */       i++;
/*      */     } 
/*  759 */     return -1;
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
/*      */   public static <F, T> Iterator<T> transform(final Iterator<F> fromIterator, final Function<? super F, ? extends T> function) {
/*  772 */     Preconditions.checkNotNull(fromIterator);
/*  773 */     Preconditions.checkNotNull(function);
/*  774 */     return new Iterator<T>()
/*      */       {
/*      */         public boolean hasNext() {
/*  777 */           return fromIterator.hasNext();
/*      */         }
/*      */         
/*      */         public T next() {
/*  781 */           F from = fromIterator.next();
/*  782 */           return (T)function.apply(from);
/*      */         }
/*      */         
/*      */         public void remove() {
/*  786 */           fromIterator.remove();
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
/*      */   public static <T> T get(Iterator<T> iterator, int position) {
/*  802 */     checkNonnegative(position);
/*      */     
/*  804 */     int skipped = 0;
/*  805 */     while (iterator.hasNext()) {
/*  806 */       T t = iterator.next();
/*  807 */       if (skipped++ == position) {
/*  808 */         return t;
/*      */       }
/*      */     } 
/*      */     
/*  812 */     throw new IndexOutOfBoundsException("position (" + position + ") must be less than the number of elements that remained (" + skipped + ")");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkNonnegative(int position) {
/*  818 */     if (position < 0) {
/*  819 */       throw new IndexOutOfBoundsException("position (" + position + ") must not be negative");
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
/*      */   public static <T> T get(Iterator<T> iterator, int position, @Nullable T defaultValue) {
/*  841 */     checkNonnegative(position);
/*      */     
/*      */     try {
/*  844 */       return get(iterator, position);
/*  845 */     } catch (IndexOutOfBoundsException e) {
/*  846 */       return defaultValue;
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
/*      */   public static <T> T getNext(Iterator<T> iterator, @Nullable T defaultValue) {
/*  860 */     return iterator.hasNext() ? iterator.next() : defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T getLast(Iterator<T> iterator) {
/*      */     while (true) {
/*  871 */       T current = iterator.next();
/*  872 */       if (!iterator.hasNext()) {
/*  873 */         return current;
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
/*      */   public static <T> T getLast(Iterator<T> iterator, @Nullable T defaultValue) {
/*  887 */     return iterator.hasNext() ? getLast(iterator) : defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <T> int skip(Iterator<T> iterator, int numberToSkip) {
/*  899 */     Preconditions.checkNotNull(iterator);
/*  900 */     Preconditions.checkArgument((numberToSkip >= 0), "number to skip cannot be negative");
/*      */     
/*      */     int i;
/*  903 */     for (i = 0; i < numberToSkip && iterator.hasNext(); i++) {
/*  904 */       iterator.next();
/*      */     }
/*  906 */     return i;
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
/*      */   public static <T> Iterator<T> limit(final Iterator<T> iterator, final int limitSize) {
/*  923 */     Preconditions.checkNotNull(iterator);
/*  924 */     Preconditions.checkArgument((limitSize >= 0), "limit is negative");
/*  925 */     return new Iterator<T>()
/*      */       {
/*      */         private int count;
/*      */         
/*      */         public boolean hasNext() {
/*  930 */           return (this.count < limitSize && iterator.hasNext());
/*      */         }
/*      */ 
/*      */         
/*      */         public T next() {
/*  935 */           if (!hasNext()) {
/*  936 */             throw new NoSuchElementException();
/*      */           }
/*  938 */           this.count++;
/*  939 */           return iterator.next();
/*      */         }
/*      */ 
/*      */         
/*      */         public void remove() {
/*  944 */           iterator.remove();
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
/*      */   public static <T> Iterator<T> consumingIterator(final Iterator<T> iterator) {
/*  963 */     Preconditions.checkNotNull(iterator);
/*  964 */     return new UnmodifiableIterator<T>()
/*      */       {
/*      */         public boolean hasNext() {
/*  967 */           return iterator.hasNext();
/*      */         }
/*      */ 
/*      */         
/*      */         public T next() {
/*  972 */           T next = iterator.next();
/*  973 */           iterator.remove();
/*  974 */           return next;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void clear(Iterator<?> iterator) {
/*  985 */     Preconditions.checkNotNull(iterator);
/*  986 */     while (iterator.hasNext()) {
/*  987 */       iterator.next();
/*  988 */       iterator.remove();
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
/*      */   public static <T> UnmodifiableIterator<T> forArray(T... array) {
/* 1007 */     Preconditions.checkNotNull(array);
/* 1008 */     return new AbstractIndexedListIterator<T>(array.length) {
/*      */         protected T get(int index) {
/* 1010 */           return (T)array[index];
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
/*      */   static <T> UnmodifiableIterator<T> forArray(final T[] array, final int offset, int length) {
/* 1031 */     Preconditions.checkArgument((length >= 0));
/* 1032 */     int end = offset + length;
/*      */ 
/*      */     
/* 1035 */     Preconditions.checkPositionIndexes(offset, end, array.length);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1042 */     return new AbstractIndexedListIterator<T>(length) {
/*      */         protected T get(int index) {
/* 1044 */           return (T)array[offset + index];
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
/*      */   public static <T> UnmodifiableIterator<T> singletonIterator(@Nullable final T value) {
/* 1057 */     return new UnmodifiableIterator<T>() {
/*      */         boolean done;
/*      */         
/*      */         public boolean hasNext() {
/* 1061 */           return !this.done;
/*      */         }
/*      */         
/*      */         public T next() {
/* 1065 */           if (this.done) {
/* 1066 */             throw new NoSuchElementException();
/*      */           }
/* 1068 */           this.done = true;
/* 1069 */           return (T)value;
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
/*      */   public static <T> UnmodifiableIterator<T> forEnumeration(final Enumeration<T> enumeration) {
/* 1084 */     Preconditions.checkNotNull(enumeration);
/* 1085 */     return new UnmodifiableIterator<T>()
/*      */       {
/*      */         public boolean hasNext() {
/* 1088 */           return enumeration.hasMoreElements();
/*      */         }
/*      */         
/*      */         public T next() {
/* 1092 */           return enumeration.nextElement();
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
/*      */   public static <T> Enumeration<T> asEnumeration(final Iterator<T> iterator) {
/* 1105 */     Preconditions.checkNotNull(iterator);
/* 1106 */     return new Enumeration<T>()
/*      */       {
/*      */         public boolean hasMoreElements() {
/* 1109 */           return iterator.hasNext();
/*      */         }
/*      */         
/*      */         public T nextElement() {
/* 1113 */           return iterator.next();
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   private static class PeekingImpl<E>
/*      */     implements PeekingIterator<E>
/*      */   {
/*      */     private final Iterator<? extends E> iterator;
/*      */     
/*      */     private boolean hasPeeked;
/*      */     private E peekedElement;
/*      */     
/*      */     public PeekingImpl(Iterator<? extends E> iterator) {
/* 1128 */       this.iterator = (Iterator<? extends E>)Preconditions.checkNotNull(iterator);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1133 */       return (this.hasPeeked || this.iterator.hasNext());
/*      */     }
/*      */ 
/*      */     
/*      */     public E next() {
/* 1138 */       if (!this.hasPeeked) {
/* 1139 */         return this.iterator.next();
/*      */       }
/* 1141 */       E result = this.peekedElement;
/* 1142 */       this.hasPeeked = false;
/* 1143 */       this.peekedElement = null;
/* 1144 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1149 */       Preconditions.checkState(!this.hasPeeked, "Can't remove after you've peeked at next");
/* 1150 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public E peek() {
/* 1155 */       if (!this.hasPeeked) {
/* 1156 */         this.peekedElement = this.iterator.next();
/* 1157 */         this.hasPeeked = true;
/*      */       } 
/* 1159 */       return this.peekedElement;
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
/*      */   public static <T> PeekingIterator<T> peekingIterator(Iterator<? extends T> iterator) {
/* 1203 */     if (iterator instanceof PeekingImpl) {
/*      */ 
/*      */ 
/*      */       
/* 1207 */       PeekingImpl<T> peeking = (PeekingImpl)iterator;
/* 1208 */       return peeking;
/*      */     } 
/* 1210 */     return new PeekingImpl<T>(iterator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <T> PeekingIterator<T> peekingIterator(PeekingIterator<T> iterator) {
/* 1221 */     return (PeekingIterator<T>)Preconditions.checkNotNull(iterator);
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Iterators.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */