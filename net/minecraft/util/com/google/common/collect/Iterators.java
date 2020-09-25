/*      */ package net.minecraft.util.com.google.common.collect;
/*      */ 
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.PriorityQueue;
/*      */ import java.util.Queue;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.util.com.google.common.annotations.Beta;
/*      */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*      */ import net.minecraft.util.com.google.common.annotations.GwtIncompatible;
/*      */ import net.minecraft.util.com.google.common.base.Function;
/*      */ import net.minecraft.util.com.google.common.base.Objects;
/*      */ import net.minecraft.util.com.google.common.base.Optional;
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
/*      */ 
/*      */ @GwtCompatible(emulated = true)
/*      */ public final class Iterators
/*      */ {
/*   72 */   static final UnmodifiableListIterator<Object> EMPTY_LIST_ITERATOR = new UnmodifiableListIterator()
/*      */     {
/*      */       public boolean hasNext()
/*      */       {
/*   76 */         return false;
/*      */       }
/*      */       
/*      */       public Object next() {
/*   80 */         throw new NoSuchElementException();
/*      */       }
/*      */       
/*      */       public boolean hasPrevious() {
/*   84 */         return false;
/*      */       }
/*      */       
/*      */       public Object previous() {
/*   88 */         throw new NoSuchElementException();
/*      */       }
/*      */       
/*      */       public int nextIndex() {
/*   92 */         return 0;
/*      */       }
/*      */       
/*      */       public int previousIndex() {
/*   96 */         return -1;
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> UnmodifiableIterator<T> emptyIterator() {
/*  107 */     return emptyListIterator();
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
/*      */   static <T> UnmodifiableListIterator<T> emptyListIterator() {
/*  119 */     return (UnmodifiableListIterator)EMPTY_LIST_ITERATOR;
/*      */   }
/*      */   
/*  122 */   private static final Iterator<Object> EMPTY_MODIFIABLE_ITERATOR = new Iterator()
/*      */     {
/*      */       public boolean hasNext() {
/*  125 */         return false;
/*      */       }
/*      */       
/*      */       public Object next() {
/*  129 */         throw new NoSuchElementException();
/*      */       }
/*      */       
/*      */       public void remove() {
/*  133 */         CollectPreconditions.checkRemove(false);
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
/*  146 */     return (Iterator)EMPTY_MODIFIABLE_ITERATOR;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> UnmodifiableIterator<T> unmodifiableIterator(final Iterator<T> iterator) {
/*  152 */     Preconditions.checkNotNull(iterator);
/*  153 */     if (iterator instanceof UnmodifiableIterator) {
/*  154 */       return (UnmodifiableIterator<T>)iterator;
/*      */     }
/*  156 */     return new UnmodifiableIterator<T>()
/*      */       {
/*      */         public boolean hasNext() {
/*  159 */           return iterator.hasNext();
/*      */         }
/*      */         
/*      */         public T next() {
/*  163 */           return iterator.next();
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
/*  176 */     return (UnmodifiableIterator<T>)Preconditions.checkNotNull(iterator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int size(Iterator<?> iterator) {
/*  185 */     int count = 0;
/*  186 */     while (iterator.hasNext()) {
/*  187 */       iterator.next();
/*  188 */       count++;
/*      */     } 
/*  190 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean contains(Iterator<?> iterator, @Nullable Object element) {
/*  197 */     return any(iterator, Predicates.equalTo(element));
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
/*  211 */     return removeIf(removeFrom, Predicates.in(elementsToRemove));
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
/*  227 */     Preconditions.checkNotNull(predicate);
/*  228 */     boolean modified = false;
/*  229 */     while (removeFrom.hasNext()) {
/*  230 */       if (predicate.apply(removeFrom.next())) {
/*  231 */         removeFrom.remove();
/*  232 */         modified = true;
/*      */       } 
/*      */     } 
/*  235 */     return modified;
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
/*  249 */     return removeIf(removeFrom, Predicates.not(Predicates.in(elementsToRetain)));
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
/*  264 */     while (iterator1.hasNext()) {
/*  265 */       if (!iterator2.hasNext()) {
/*  266 */         return false;
/*      */       }
/*  268 */       Object o1 = iterator1.next();
/*  269 */       Object o2 = iterator2.next();
/*  270 */       if (!Objects.equal(o1, o2)) {
/*  271 */         return false;
/*      */       }
/*      */     } 
/*  274 */     return !iterator2.hasNext();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(Iterator<?> iterator) {
/*  283 */     return Collections2.STANDARD_JOINER.appendTo((new StringBuilder()).append('['), iterator).append(']').toString();
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
/*      */   public static <T> T getOnlyElement(Iterator<T> iterator) {
/*  297 */     T first = iterator.next();
/*  298 */     if (!iterator.hasNext()) {
/*  299 */       return first;
/*      */     }
/*      */     
/*  302 */     StringBuilder sb = new StringBuilder();
/*  303 */     sb.append("expected one element but was: <" + first);
/*  304 */     for (int i = 0; i < 4 && iterator.hasNext(); i++) {
/*  305 */       sb.append(", " + iterator.next());
/*      */     }
/*  307 */     if (iterator.hasNext()) {
/*  308 */       sb.append(", ...");
/*      */     }
/*  310 */     sb.append('>');
/*      */     
/*  312 */     throw new IllegalArgumentException(sb.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static <T> T getOnlyElement(Iterator<? extends T> iterator, @Nullable T defaultValue) {
/*  324 */     return iterator.hasNext() ? getOnlyElement((Iterator)iterator) : defaultValue;
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
/*  339 */     List<T> list = Lists.newArrayList(iterator);
/*  340 */     return Iterables.toArray(list, type);
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
/*  353 */     Preconditions.checkNotNull(addTo);
/*  354 */     Preconditions.checkNotNull(iterator);
/*  355 */     boolean wasModified = false;
/*  356 */     while (iterator.hasNext()) {
/*  357 */       wasModified |= addTo.add(iterator.next());
/*      */     }
/*  359 */     return wasModified;
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
/*  370 */     return size(filter(iterator, Predicates.equalTo(element)));
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
/*  388 */     Preconditions.checkNotNull(iterable);
/*  389 */     return new Iterator<T>() {
/*  390 */         Iterator<T> iterator = Iterators.emptyIterator();
/*      */         
/*      */         Iterator<T> removeFrom;
/*      */         
/*      */         public boolean hasNext() {
/*  395 */           if (!this.iterator.hasNext()) {
/*  396 */             this.iterator = iterable.iterator();
/*      */           }
/*  398 */           return this.iterator.hasNext();
/*      */         }
/*      */         
/*      */         public T next() {
/*  402 */           if (!hasNext()) {
/*  403 */             throw new NoSuchElementException();
/*      */           }
/*  405 */           this.removeFrom = this.iterator;
/*  406 */           return this.iterator.next();
/*      */         }
/*      */         
/*      */         public void remove() {
/*  410 */           CollectPreconditions.checkRemove((this.removeFrom != null));
/*  411 */           this.removeFrom.remove();
/*  412 */           this.removeFrom = null;
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
/*  431 */     return cycle(Lists.newArrayList(elements));
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
/*      */   public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b) {
/*  449 */     return concat(ImmutableList.<Iterator<? extends T>>of(a, b).iterator());
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
/*      */   public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b, Iterator<? extends T> c) {
/*  468 */     return concat(ImmutableList.<Iterator<? extends T>>of(a, b, c).iterator());
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
/*      */   public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b, Iterator<? extends T> c, Iterator<? extends T> d) {
/*  488 */     return concat(ImmutableList.<Iterator<? extends T>>of(a, b, c, d).iterator());
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
/*      */   public static <T> Iterator<T> concat(Iterator<? extends T>... inputs) {
/*  507 */     return concat(ImmutableList.<Iterator<? extends T>>copyOf(inputs).iterator());
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
/*      */   public static <T> Iterator<T> concat(final Iterator<? extends Iterator<? extends T>> inputs) {
/*  526 */     Preconditions.checkNotNull(inputs);
/*  527 */     return new Iterator<T>() {
/*  528 */         Iterator<? extends T> current = Iterators.emptyIterator();
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
/*  542 */           while (!(currentHasNext = ((Iterator)Preconditions.checkNotNull(this.current)).hasNext()) && inputs.hasNext()) {
/*  543 */             this.current = inputs.next();
/*      */           }
/*  545 */           return currentHasNext;
/*      */         }
/*      */         
/*      */         public T next() {
/*  549 */           if (!hasNext()) {
/*  550 */             throw new NoSuchElementException();
/*      */           }
/*  552 */           this.removeFrom = this.current;
/*  553 */           return this.current.next();
/*      */         }
/*      */         
/*      */         public void remove() {
/*  557 */           CollectPreconditions.checkRemove((this.removeFrom != null));
/*  558 */           this.removeFrom.remove();
/*  559 */           this.removeFrom = null;
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
/*  581 */     return partitionImpl(iterator, size, false);
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
/*  602 */     return partitionImpl(iterator, size, true);
/*      */   }
/*      */ 
/*      */   
/*      */   private static <T> UnmodifiableIterator<List<T>> partitionImpl(final Iterator<T> iterator, final int size, final boolean pad) {
/*  607 */     Preconditions.checkNotNull(iterator);
/*  608 */     Preconditions.checkArgument((size > 0));
/*  609 */     return new UnmodifiableIterator<List<T>>()
/*      */       {
/*      */         public boolean hasNext() {
/*  612 */           return iterator.hasNext();
/*      */         }
/*      */         
/*      */         public List<T> next() {
/*  616 */           if (!hasNext()) {
/*  617 */             throw new NoSuchElementException();
/*      */           }
/*  619 */           Object[] array = new Object[size];
/*  620 */           int count = 0;
/*  621 */           for (; count < size && iterator.hasNext(); count++) {
/*  622 */             array[count] = iterator.next();
/*      */           }
/*  624 */           for (int i = count; i < size; i++) {
/*  625 */             array[i] = null;
/*      */           }
/*      */ 
/*      */           
/*  629 */           List<T> list = Collections.unmodifiableList(Arrays.asList((T[])array));
/*      */           
/*  631 */           return (pad || count == size) ? list : list.subList(0, count);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> UnmodifiableIterator<T> filter(final Iterator<T> unfiltered, final Predicate<? super T> predicate) {
/*  641 */     Preconditions.checkNotNull(unfiltered);
/*  642 */     Preconditions.checkNotNull(predicate);
/*  643 */     return new AbstractIterator<T>() {
/*      */         protected T computeNext() {
/*  645 */           while (unfiltered.hasNext()) {
/*  646 */             T element = unfiltered.next();
/*  647 */             if (predicate.apply(element)) {
/*  648 */               return element;
/*      */             }
/*      */           } 
/*  651 */           return endOfData();
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
/*  670 */     return filter((Iterator)unfiltered, Predicates.instanceOf(type));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> boolean any(Iterator<T> iterator, Predicate<? super T> predicate) {
/*  679 */     return (indexOf(iterator, predicate) != -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> boolean all(Iterator<T> iterator, Predicate<? super T> predicate) {
/*  689 */     Preconditions.checkNotNull(predicate);
/*  690 */     while (iterator.hasNext()) {
/*  691 */       T element = iterator.next();
/*  692 */       if (!predicate.apply(element)) {
/*  693 */         return false;
/*      */       }
/*      */     } 
/*  696 */     return true;
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
/*      */   public static <T> T find(Iterator<T> iterator, Predicate<? super T> predicate) {
/*  712 */     return filter(iterator, predicate).next();
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
/*      */   @Nullable
/*      */   public static <T> T find(Iterator<? extends T> iterator, Predicate<? super T> predicate, @Nullable T defaultValue) {
/*  728 */     return getNext(filter(iterator, predicate), defaultValue);
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
/*      */   public static <T> Optional<T> tryFind(Iterator<T> iterator, Predicate<? super T> predicate) {
/*  746 */     UnmodifiableIterator<T> filteredIterator = filter(iterator, predicate);
/*  747 */     return filteredIterator.hasNext() ? Optional.of(filteredIterator.next()) : Optional.absent();
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
/*      */   public static <T> int indexOf(Iterator<T> iterator, Predicate<? super T> predicate) {
/*  770 */     Preconditions.checkNotNull(predicate, "predicate");
/*  771 */     for (int i = 0; iterator.hasNext(); i++) {
/*  772 */       T current = iterator.next();
/*  773 */       if (predicate.apply(current)) {
/*  774 */         return i;
/*      */       }
/*      */     } 
/*  777 */     return -1;
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
/*      */   public static <F, T> Iterator<T> transform(Iterator<F> fromIterator, final Function<? super F, ? extends T> function) {
/*  790 */     Preconditions.checkNotNull(function);
/*  791 */     return new TransformedIterator<F, T>(fromIterator)
/*      */       {
/*      */         T transform(F from) {
/*  794 */           return (T)function.apply(from);
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
/*  810 */     checkNonnegative(position);
/*  811 */     int skipped = advance(iterator, position);
/*  812 */     if (!iterator.hasNext()) {
/*  813 */       throw new IndexOutOfBoundsException("position (" + position + ") must be less than the number of elements that remained (" + skipped + ")");
/*      */     }
/*      */ 
/*      */     
/*  817 */     return iterator.next();
/*      */   }
/*      */   
/*      */   static void checkNonnegative(int position) {
/*  821 */     if (position < 0) {
/*  822 */       throw new IndexOutOfBoundsException("position (" + position + ") must not be negative");
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
/*      */   @Nullable
/*      */   public static <T> T get(Iterator<? extends T> iterator, int position, @Nullable T defaultValue) {
/*  844 */     checkNonnegative(position);
/*  845 */     advance(iterator, position);
/*  846 */     return getNext(iterator, defaultValue);
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
/*      */   @Nullable
/*      */   public static <T> T getNext(Iterator<? extends T> iterator, @Nullable T defaultValue) {
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
/*      */   @Nullable
/*      */   public static <T> T getLast(Iterator<? extends T> iterator, @Nullable T defaultValue) {
/*  888 */     return iterator.hasNext() ? getLast((Iterator)iterator) : defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int advance(Iterator<?> iterator, int numberToAdvance) {
/*  899 */     Preconditions.checkNotNull(iterator);
/*  900 */     Preconditions.checkArgument((numberToAdvance >= 0), "numberToAdvance must be nonnegative");
/*      */     
/*      */     int i;
/*  903 */     for (i = 0; i < numberToAdvance && iterator.hasNext(); i++) {
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
/*      */   @Nullable
/*      */   static <T> T pollNext(Iterator<T> iterator) {
/*  985 */     if (iterator.hasNext()) {
/*  986 */       T result = iterator.next();
/*  987 */       iterator.remove();
/*  988 */       return result;
/*      */     } 
/*  990 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void clear(Iterator<?> iterator) {
/* 1000 */     Preconditions.checkNotNull(iterator);
/* 1001 */     while (iterator.hasNext()) {
/* 1002 */       iterator.next();
/* 1003 */       iterator.remove();
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
/*      */   public static <T> UnmodifiableIterator<T> forArray(T... array) {
/* 1021 */     return forArray(array, 0, array.length, 0);
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
/*      */   static <T> UnmodifiableListIterator<T> forArray(final T[] array, final int offset, int length, int index) {
/* 1033 */     Preconditions.checkArgument((length >= 0));
/* 1034 */     int end = offset + length;
/*      */ 
/*      */     
/* 1037 */     Preconditions.checkPositionIndexes(offset, end, array.length);
/* 1038 */     Preconditions.checkPositionIndex(index, length);
/* 1039 */     if (length == 0) {
/* 1040 */       return emptyListIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1048 */     return new AbstractIndexedListIterator<T>(length, index) {
/*      */         protected T get(int index) {
/* 1050 */           return (T)array[offset + index];
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
/* 1063 */     return new UnmodifiableIterator<T>() {
/*      */         boolean done;
/*      */         
/*      */         public boolean hasNext() {
/* 1067 */           return !this.done;
/*      */         }
/*      */         
/*      */         public T next() {
/* 1071 */           if (this.done) {
/* 1072 */             throw new NoSuchElementException();
/*      */           }
/* 1074 */           this.done = true;
/* 1075 */           return (T)value;
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
/* 1090 */     Preconditions.checkNotNull(enumeration);
/* 1091 */     return new UnmodifiableIterator<T>()
/*      */       {
/*      */         public boolean hasNext() {
/* 1094 */           return enumeration.hasMoreElements();
/*      */         }
/*      */         
/*      */         public T next() {
/* 1098 */           return enumeration.nextElement();
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
/* 1111 */     Preconditions.checkNotNull(iterator);
/* 1112 */     return new Enumeration<T>()
/*      */       {
/*      */         public boolean hasMoreElements() {
/* 1115 */           return iterator.hasNext();
/*      */         }
/*      */         
/*      */         public T nextElement() {
/* 1119 */           return iterator.next();
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
/* 1134 */       this.iterator = (Iterator<? extends E>)Preconditions.checkNotNull(iterator);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1139 */       return (this.hasPeeked || this.iterator.hasNext());
/*      */     }
/*      */ 
/*      */     
/*      */     public E next() {
/* 1144 */       if (!this.hasPeeked) {
/* 1145 */         return this.iterator.next();
/*      */       }
/* 1147 */       E result = this.peekedElement;
/* 1148 */       this.hasPeeked = false;
/* 1149 */       this.peekedElement = null;
/* 1150 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1155 */       Preconditions.checkState(!this.hasPeeked, "Can't remove after you've peeked at next");
/* 1156 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public E peek() {
/* 1161 */       if (!this.hasPeeked) {
/* 1162 */         this.peekedElement = this.iterator.next();
/* 1163 */         this.hasPeeked = true;
/*      */       } 
/* 1165 */       return this.peekedElement;
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
/* 1209 */     if (iterator instanceof PeekingImpl) {
/*      */ 
/*      */ 
/*      */       
/* 1213 */       PeekingImpl<T> peeking = (PeekingImpl)iterator;
/* 1214 */       return peeking;
/*      */     } 
/* 1216 */     return new PeekingImpl<T>(iterator);
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
/* 1227 */     return (PeekingIterator<T>)Preconditions.checkNotNull(iterator);
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
/*      */   public static <T> UnmodifiableIterator<T> mergeSorted(Iterable<? extends Iterator<? extends T>> iterators, Comparator<? super T> comparator) {
/* 1247 */     Preconditions.checkNotNull(iterators, "iterators");
/* 1248 */     Preconditions.checkNotNull(comparator, "comparator");
/*      */     
/* 1250 */     return new MergingIterator<T>(iterators, comparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class MergingIterator<T>
/*      */     extends UnmodifiableIterator<T>
/*      */   {
/*      */     final Queue<PeekingIterator<T>> queue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MergingIterator(Iterable<? extends Iterator<? extends T>> iterators, final Comparator<? super T> itemComparator) {
/* 1269 */       Comparator<PeekingIterator<T>> heapComparator = (Comparator)new Comparator<PeekingIterator<PeekingIterator<T>>>()
/*      */         {
/*      */           public int compare(PeekingIterator<T> o1, PeekingIterator<T> o2)
/*      */           {
/* 1273 */             return itemComparator.compare(o1.peek(), o2.peek());
/*      */           }
/*      */         };
/*      */       
/* 1277 */       this.queue = new PriorityQueue<PeekingIterator<T>>(2, heapComparator);
/*      */       
/* 1279 */       for (Iterator<? extends T> iterator : iterators) {
/* 1280 */         if (iterator.hasNext()) {
/* 1281 */           this.queue.add(Iterators.peekingIterator(iterator));
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1288 */       return !this.queue.isEmpty();
/*      */     }
/*      */ 
/*      */     
/*      */     public T next() {
/* 1293 */       PeekingIterator<T> nextIter = this.queue.remove();
/* 1294 */       T next = nextIter.next();
/* 1295 */       if (nextIter.hasNext()) {
/* 1296 */         this.queue.add(nextIter);
/*      */       }
/* 1298 */       return next;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> ListIterator<T> cast(Iterator<T> iterator) {
/* 1306 */     return (ListIterator<T>)iterator;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\Iterators.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */