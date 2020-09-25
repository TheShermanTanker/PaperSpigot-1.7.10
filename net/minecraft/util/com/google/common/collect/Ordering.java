/*     */ package net.minecraft.util.com.google.common.collect;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*     */ import net.minecraft.util.com.google.common.annotations.VisibleForTesting;
/*     */ import net.minecraft.util.com.google.common.base.Function;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible
/*     */ public abstract class Ordering<T>
/*     */   implements Comparator<T>
/*     */ {
/*     */   static final int LEFT_IS_GREATER = 1;
/*     */   static final int RIGHT_IS_GREATER = -1;
/*     */   
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <C extends Comparable> Ordering<C> natural() {
/* 106 */     return NaturalOrdering.INSTANCE;
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
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <T> Ordering<T> from(Comparator<T> comparator) {
/* 123 */     return (comparator instanceof Ordering) ? (Ordering<T>)comparator : new ComparatorOrdering<T>(comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <T> Ordering<T> from(Ordering<T> ordering) {
/* 135 */     return (Ordering<T>)Preconditions.checkNotNull(ordering);
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
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <T> Ordering<T> explicit(List<T> valuesInOrder) {
/* 161 */     return new ExplicitOrdering<T>(valuesInOrder);
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
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <T> Ordering<T> explicit(T leastValue, T... remainingValuesInOrder) {
/* 190 */     return explicit(Lists.asList(leastValue, remainingValuesInOrder));
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
/*     */   @GwtCompatible(serializable = true)
/*     */   public static Ordering<Object> allEqual() {
/* 222 */     return AllEqualOrdering.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtCompatible(serializable = true)
/*     */   public static Ordering<Object> usingToString() {
/* 234 */     return UsingToStringOrdering.INSTANCE;
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
/*     */   public static Ordering<Object> arbitrary() {
/* 254 */     return ArbitraryOrderingHolder.ARBITRARY_ORDERING;
/*     */   }
/*     */   
/*     */   private static class ArbitraryOrderingHolder {
/* 258 */     static final Ordering<Object> ARBITRARY_ORDERING = new Ordering.ArbitraryOrdering(); }
/*     */   
/*     */   @VisibleForTesting
/*     */   static class ArbitraryOrdering extends Ordering<Object> {
/* 262 */     private Map<Object, Integer> uids = Platform.tryWeakKeys(new MapMaker()).makeComputingMap(new Function<Object, Integer>()
/*     */         {
/*     */ 
/*     */           
/* 266 */           final AtomicInteger counter = new AtomicInteger(0);
/*     */           
/*     */           public Integer apply(Object from) {
/* 269 */             return Integer.valueOf(this.counter.getAndIncrement());
/*     */           }
/*     */         });
/*     */     
/*     */     public int compare(Object left, Object right) {
/* 274 */       if (left == right)
/* 275 */         return 0; 
/* 276 */       if (left == null)
/* 277 */         return -1; 
/* 278 */       if (right == null) {
/* 279 */         return 1;
/*     */       }
/* 281 */       int leftCode = identityHashCode(left);
/* 282 */       int rightCode = identityHashCode(right);
/* 283 */       if (leftCode != rightCode) {
/* 284 */         return (leftCode < rightCode) ? -1 : 1;
/*     */       }
/*     */ 
/*     */       
/* 288 */       int result = ((Integer)this.uids.get(left)).compareTo(this.uids.get(right));
/* 289 */       if (result == 0) {
/* 290 */         throw new AssertionError();
/*     */       }
/* 292 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 296 */       return "Ordering.arbitrary()";
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
/*     */     int identityHashCode(Object object) {
/* 308 */       return System.identityHashCode(object);
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
/*     */   @GwtCompatible(serializable = true)
/*     */   public <S extends T> Ordering<S> reverse() {
/* 330 */     return new ReverseOrdering<S>(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtCompatible(serializable = true)
/*     */   public <S extends T> Ordering<S> nullsFirst() {
/* 341 */     return new NullsFirstOrdering<S>(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtCompatible(serializable = true)
/*     */   public <S extends T> Ordering<S> nullsLast() {
/* 352 */     return new NullsLastOrdering<S>(this);
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
/*     */   @GwtCompatible(serializable = true)
/*     */   public <F> Ordering<F> onResultOf(Function<F, ? extends T> function) {
/* 366 */     return new ByFunctionOrdering<F, T>(function, this);
/*     */   }
/*     */   
/*     */   <T2 extends T> Ordering<Map.Entry<T2, ?>> onKeys() {
/* 370 */     return onResultOf(Maps.keyFunction());
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
/*     */   @GwtCompatible(serializable = true)
/*     */   public <U extends T> Ordering<U> compound(Comparator<? super U> secondaryComparator) {
/* 387 */     return new CompoundOrdering<U>(this, (Comparator<? super U>)Preconditions.checkNotNull(secondaryComparator));
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
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <T> Ordering<T> compound(Iterable<? extends Comparator<? super T>> comparators) {
/* 408 */     return new CompoundOrdering<T>(comparators);
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
/*     */   @GwtCompatible(serializable = true)
/*     */   public <S extends T> Ordering<Iterable<S>> lexicographical() {
/* 437 */     return new LexicographicalOrdering<S>(this);
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
/*     */   public abstract int compare(@Nullable T paramT1, @Nullable T paramT2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <E extends T> E min(Iterator<E> iterator) {
/* 460 */     E minSoFar = iterator.next();
/*     */     
/* 462 */     while (iterator.hasNext()) {
/* 463 */       minSoFar = min(minSoFar, iterator.next());
/*     */     }
/*     */     
/* 466 */     return minSoFar;
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
/*     */   public <E extends T> E min(Iterable<E> iterable) {
/* 479 */     return min(iterable.iterator());
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
/*     */   public <E extends T> E min(@Nullable E a, @Nullable E b) {
/* 496 */     return (compare((T)a, (T)b) <= 0) ? a : b;
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
/*     */   public <E extends T> E min(@Nullable E a, @Nullable E b, @Nullable E c, E... rest) {
/* 512 */     E minSoFar = min(min(a, b), c);
/*     */     
/* 514 */     for (E r : rest) {
/* 515 */       minSoFar = min(minSoFar, r);
/*     */     }
/*     */     
/* 518 */     return minSoFar;
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
/*     */   public <E extends T> E max(Iterator<E> iterator) {
/* 536 */     E maxSoFar = iterator.next();
/*     */     
/* 538 */     while (iterator.hasNext()) {
/* 539 */       maxSoFar = max(maxSoFar, iterator.next());
/*     */     }
/*     */     
/* 542 */     return maxSoFar;
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
/*     */   public <E extends T> E max(Iterable<E> iterable) {
/* 555 */     return max(iterable.iterator());
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
/*     */   public <E extends T> E max(@Nullable E a, @Nullable E b) {
/* 572 */     return (compare((T)a, (T)b) >= 0) ? a : b;
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
/*     */   public <E extends T> E max(@Nullable E a, @Nullable E b, @Nullable E c, E... rest) {
/* 588 */     E maxSoFar = max(max(a, b), c);
/*     */     
/* 590 */     for (E r : rest) {
/* 591 */       maxSoFar = max(maxSoFar, r);
/*     */     }
/*     */     
/* 594 */     return maxSoFar;
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
/*     */   public <E extends T> List<E> leastOf(Iterable<E> iterable, int k) {
/* 612 */     if (iterable instanceof Collection) {
/* 613 */       Collection<E> collection = (Collection<E>)iterable;
/* 614 */       if (collection.size() <= 2L * k) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 620 */         E[] array = (E[])collection.toArray();
/* 621 */         Arrays.sort(array, this);
/* 622 */         if (array.length > k) {
/* 623 */           array = ObjectArrays.arraysCopyOf(array, k);
/*     */         }
/* 625 */         return Collections.unmodifiableList(Arrays.asList(array));
/*     */       } 
/*     */     } 
/* 628 */     return leastOf(iterable.iterator(), k);
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
/*     */   public <E extends T> List<E> leastOf(Iterator<E> elements, int k) {
/* 646 */     Preconditions.checkNotNull(elements);
/* 647 */     CollectPreconditions.checkNonnegative(k, "k");
/*     */     
/* 649 */     if (k == 0 || !elements.hasNext())
/* 650 */       return ImmutableList.of(); 
/* 651 */     if (k >= 1073741823) {
/*     */       
/* 653 */       ArrayList<E> list = Lists.newArrayList(elements);
/* 654 */       Collections.sort(list, this);
/* 655 */       if (list.size() > k) {
/* 656 */         list.subList(k, list.size()).clear();
/*     */       }
/* 658 */       list.trimToSize();
/* 659 */       return Collections.unmodifiableList(list);
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
/*     */ 
/*     */     
/* 676 */     int bufferCap = k * 2;
/*     */     
/* 678 */     E[] buffer = (E[])new Object[bufferCap];
/* 679 */     E threshold = elements.next();
/* 680 */     buffer[0] = threshold;
/* 681 */     int bufferSize = 1;
/*     */ 
/*     */ 
/*     */     
/* 685 */     while (bufferSize < k && elements.hasNext()) {
/* 686 */       E e = elements.next();
/* 687 */       buffer[bufferSize++] = e;
/* 688 */       threshold = max(threshold, e);
/*     */     } 
/*     */     
/* 691 */     while (elements.hasNext()) {
/* 692 */       E e = elements.next();
/* 693 */       if (compare((T)e, (T)threshold) >= 0) {
/*     */         continue;
/*     */       }
/*     */       
/* 697 */       buffer[bufferSize++] = e;
/* 698 */       if (bufferSize == bufferCap) {
/*     */ 
/*     */         
/* 701 */         int left = 0;
/* 702 */         int right = bufferCap - 1;
/*     */         
/* 704 */         int minThresholdPosition = 0;
/*     */ 
/*     */ 
/*     */         
/* 708 */         while (left < right) {
/* 709 */           int pivotIndex = left + right + 1 >>> 1;
/* 710 */           int pivotNewIndex = partition(buffer, left, right, pivotIndex);
/* 711 */           if (pivotNewIndex > k) {
/* 712 */             right = pivotNewIndex - 1; continue;
/* 713 */           }  if (pivotNewIndex < k) {
/* 714 */             left = Math.max(pivotNewIndex, left + 1);
/* 715 */             minThresholdPosition = pivotNewIndex;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 720 */         bufferSize = k;
/*     */         
/* 722 */         threshold = buffer[minThresholdPosition];
/* 723 */         for (int i = minThresholdPosition + 1; i < bufferSize; i++) {
/* 724 */           threshold = max(threshold, buffer[i]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 729 */     Arrays.sort(buffer, 0, bufferSize, this);
/*     */     
/* 731 */     bufferSize = Math.min(bufferSize, k);
/* 732 */     return Collections.unmodifiableList(Arrays.asList(ObjectArrays.arraysCopyOf(buffer, bufferSize)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <E extends T> int partition(E[] values, int left, int right, int pivotIndex) {
/* 739 */     E pivotValue = values[pivotIndex];
/*     */     
/* 741 */     values[pivotIndex] = values[right];
/* 742 */     values[right] = pivotValue;
/*     */     
/* 744 */     int storeIndex = left;
/* 745 */     for (int i = left; i < right; i++) {
/* 746 */       if (compare((T)values[i], (T)pivotValue) < 0) {
/* 747 */         ObjectArrays.swap((Object[])values, storeIndex, i);
/* 748 */         storeIndex++;
/*     */       } 
/*     */     } 
/* 751 */     ObjectArrays.swap((Object[])values, right, storeIndex);
/* 752 */     return storeIndex;
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
/*     */   public <E extends T> List<E> greatestOf(Iterable<E> iterable, int k) {
/* 772 */     return reverse().leastOf(iterable, k);
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
/*     */   public <E extends T> List<E> greatestOf(Iterator<E> iterator, int k) {
/* 790 */     return reverse().leastOf(iterator, k);
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
/*     */   public <E extends T> List<E> sortedCopy(Iterable<E> iterable) {
/* 815 */     E[] array = (E[])Iterables.toArray(iterable);
/* 816 */     Arrays.sort(array, this);
/* 817 */     return Lists.newArrayList(Arrays.asList(array));
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
/*     */   public <E extends T> ImmutableList<E> immutableSortedCopy(Iterable<E> iterable) {
/* 845 */     E[] elements = (E[])Iterables.toArray(iterable);
/* 846 */     for (E e : elements) {
/* 847 */       Preconditions.checkNotNull(e);
/*     */     }
/* 849 */     Arrays.sort(elements, this);
/* 850 */     return ImmutableList.asImmutableList((Object[])elements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOrdered(Iterable<? extends T> iterable) {
/* 860 */     Iterator<? extends T> it = iterable.iterator();
/* 861 */     if (it.hasNext()) {
/* 862 */       T prev = it.next();
/* 863 */       while (it.hasNext()) {
/* 864 */         T next = it.next();
/* 865 */         if (compare(prev, next) > 0) {
/* 866 */           return false;
/*     */         }
/* 868 */         prev = next;
/*     */       } 
/*     */     } 
/* 871 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStrictlyOrdered(Iterable<? extends T> iterable) {
/* 881 */     Iterator<? extends T> it = iterable.iterator();
/* 882 */     if (it.hasNext()) {
/* 883 */       T prev = it.next();
/* 884 */       while (it.hasNext()) {
/* 885 */         T next = it.next();
/* 886 */         if (compare(prev, next) >= 0) {
/* 887 */           return false;
/*     */         }
/* 889 */         prev = next;
/*     */       } 
/*     */     } 
/* 892 */     return true;
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
/*     */   public int binarySearch(List<? extends T> sortedList, @Nullable T key) {
/* 904 */     return Collections.binarySearch(sortedList, key, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static class IncomparableValueException
/*     */     extends ClassCastException
/*     */   {
/*     */     final Object value;
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */     
/*     */     IncomparableValueException(Object value) {
/* 919 */       super("Cannot compare value: " + value);
/* 920 */       this.value = value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\Ordering.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */