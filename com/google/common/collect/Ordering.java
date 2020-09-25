/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ @GwtCompatible
/*     */ public abstract class Ordering<T>
/*     */   implements Comparator<T>
/*     */ {
/*     */   static final int LEFT_IS_GREATER = 1;
/*     */   static final int RIGHT_IS_GREATER = -1;
/*     */   
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <C extends Comparable> Ordering<C> natural() {
/*  82 */     return NaturalOrdering.INSTANCE;
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
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <T> Ordering<T> from(Comparator<T> comparator) {
/*  95 */     return (comparator instanceof Ordering) ? (Ordering<T>)comparator : new ComparatorOrdering<T>(comparator);
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
/* 107 */     return (Ordering<T>)Preconditions.checkNotNull(ordering);
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
/* 133 */     return new ExplicitOrdering<T>(valuesInOrder);
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
/* 162 */     return explicit(Lists.asList(leastValue, remainingValuesInOrder));
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
/* 177 */       super("Cannot compare value: " + value);
/* 178 */       this.value = value;
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
/*     */   public static Ordering<Object> arbitrary() {
/* 201 */     return ArbitraryOrderingHolder.ARBITRARY_ORDERING;
/*     */   }
/*     */   
/*     */   private static class ArbitraryOrderingHolder {
/* 205 */     static final Ordering<Object> ARBITRARY_ORDERING = new Ordering.ArbitraryOrdering(); }
/*     */   
/*     */   @VisibleForTesting
/*     */   static class ArbitraryOrdering extends Ordering<Object> {
/* 209 */     private Map<Object, Integer> uids = Platform.tryWeakKeys(new MapMaker()).makeComputingMap(new Function<Object, Integer>()
/*     */         {
/*     */ 
/*     */           
/* 213 */           final AtomicInteger counter = new AtomicInteger(0);
/*     */           
/*     */           public Integer apply(Object from) {
/* 216 */             return Integer.valueOf(this.counter.getAndIncrement());
/*     */           }
/*     */         });
/*     */     
/*     */     public int compare(Object left, Object right) {
/* 221 */       if (left == right) {
/* 222 */         return 0;
/*     */       }
/* 224 */       int leftCode = identityHashCode(left);
/* 225 */       int rightCode = identityHashCode(right);
/* 226 */       if (leftCode != rightCode) {
/* 227 */         return (leftCode < rightCode) ? -1 : 1;
/*     */       }
/*     */ 
/*     */       
/* 231 */       int result = ((Integer)this.uids.get(left)).compareTo(this.uids.get(right));
/* 232 */       if (result == 0) {
/* 233 */         throw new AssertionError();
/*     */       }
/* 235 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 239 */       return "Ordering.arbitrary()";
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
/* 251 */       return System.identityHashCode(object);
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
/*     */   @GwtCompatible(serializable = true)
/*     */   public static Ordering<Object> usingToString() {
/* 264 */     return UsingToStringOrdering.INSTANCE;
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
/* 285 */     return new CompoundOrdering<T>(comparators);
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
/*     */   @GwtCompatible(serializable = true)
/*     */   public <U extends T> Ordering<U> compound(Comparator<? super U> secondaryComparator) {
/* 310 */     return new CompoundOrdering<U>(this, (Comparator<? super U>)Preconditions.checkNotNull(secondaryComparator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtCompatible(serializable = true)
/*     */   public <S extends T> Ordering<S> reverse() {
/* 321 */     return new ReverseOrdering<S>(this);
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
/* 335 */     return new ByFunctionOrdering<F, T>(function, this);
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
/* 364 */     return new LexicographicalOrdering<S>(this);
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
/* 375 */     return new NullsFirstOrdering<S>(this);
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
/* 386 */     return new NullsLastOrdering<S>(this);
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
/*     */   @Beta
/*     */   public <E extends T> List<E> leastOf(Iterable<E> iterable, int k) {
/*     */     E[] resultArray;
/* 410 */     Preconditions.checkArgument((k >= 0), "%d is negative", new Object[] { Integer.valueOf(k) });
/*     */ 
/*     */ 
/*     */     
/* 414 */     E[] values = (E[])Iterables.toArray(iterable);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 419 */     if (values.length <= k) {
/* 420 */       Arrays.sort(values, this);
/* 421 */       resultArray = values;
/*     */     } else {
/* 423 */       quicksortLeastK(values, 0, values.length - 1, k);
/*     */ 
/*     */ 
/*     */       
/* 427 */       E[] tmp = (E[])new Object[k];
/* 428 */       resultArray = tmp;
/* 429 */       System.arraycopy(values, 0, resultArray, 0, k);
/*     */     } 
/*     */     
/* 432 */     return Collections.unmodifiableList(Arrays.asList(resultArray));
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
/*     */   @Beta
/*     */   public <E extends T> List<E> greatestOf(Iterable<E> iterable, int k) {
/* 453 */     return reverse().leastOf(iterable, k);
/*     */   }
/*     */ 
/*     */   
/*     */   private <E extends T> void quicksortLeastK(E[] values, int left, int right, int k) {
/* 458 */     if (right > left) {
/* 459 */       int pivotIndex = left + right >>> 1;
/* 460 */       int pivotNewIndex = partition(values, left, right, pivotIndex);
/* 461 */       quicksortLeastK(values, left, pivotNewIndex - 1, k);
/* 462 */       if (pivotNewIndex < k) {
/* 463 */         quicksortLeastK(values, pivotNewIndex + 1, right, k);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private <E extends T> int partition(E[] values, int left, int right, int pivotIndex) {
/* 470 */     E pivotValue = values[pivotIndex];
/*     */     
/* 472 */     values[pivotIndex] = values[right];
/* 473 */     values[right] = pivotValue;
/*     */     
/* 475 */     int storeIndex = left;
/* 476 */     for (int i = left; i < right; i++) {
/* 477 */       if (compare((T)values[i], (T)pivotValue) < 0) {
/* 478 */         ObjectArrays.swap((Object[])values, storeIndex, i);
/* 479 */         storeIndex++;
/*     */       } 
/*     */     } 
/* 482 */     ObjectArrays.swap((Object[])values, right, storeIndex);
/* 483 */     return storeIndex;
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
/* 495 */     return Collections.binarySearch(sortedList, key, this);
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
/*     */   public <E extends T> List<E> sortedCopy(Iterable<E> iterable) {
/* 512 */     List<E> list = Lists.newArrayList(iterable);
/* 513 */     Collections.sort(list, this);
/* 514 */     return list;
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
/*     */   public <E extends T> ImmutableList<E> immutableSortedCopy(Iterable<E> iterable) {
/* 534 */     return ImmutableList.copyOf(sortedCopy(iterable));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOrdered(Iterable<? extends T> iterable) {
/* 544 */     Iterator<? extends T> it = iterable.iterator();
/* 545 */     if (it.hasNext()) {
/* 546 */       T prev = it.next();
/* 547 */       while (it.hasNext()) {
/* 548 */         T next = it.next();
/* 549 */         if (compare(prev, next) > 0) {
/* 550 */           return false;
/*     */         }
/* 552 */         prev = next;
/*     */       } 
/*     */     } 
/* 555 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStrictlyOrdered(Iterable<? extends T> iterable) {
/* 565 */     Iterator<? extends T> it = iterable.iterator();
/* 566 */     if (it.hasNext()) {
/* 567 */       T prev = it.next();
/* 568 */       while (it.hasNext()) {
/* 569 */         T next = it.next();
/* 570 */         if (compare(prev, next) >= 0) {
/* 571 */           return false;
/*     */         }
/* 573 */         prev = next;
/*     */       } 
/*     */     } 
/* 576 */     return true;
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
/* 589 */     Iterator<E> iterator = iterable.iterator();
/*     */ 
/*     */     
/* 592 */     E maxSoFar = iterator.next();
/*     */     
/* 594 */     while (iterator.hasNext()) {
/* 595 */       maxSoFar = max(maxSoFar, iterator.next());
/*     */     }
/*     */     
/* 598 */     return maxSoFar;
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
/* 614 */     E maxSoFar = max(max(a, b), c);
/*     */     
/* 616 */     for (E r : rest) {
/* 617 */       maxSoFar = max(maxSoFar, r);
/*     */     }
/*     */     
/* 620 */     return maxSoFar;
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
/* 637 */     return (compare((T)a, (T)b) >= 0) ? a : b;
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
/* 650 */     Iterator<E> iterator = iterable.iterator();
/*     */ 
/*     */     
/* 653 */     E minSoFar = iterator.next();
/*     */     
/* 655 */     while (iterator.hasNext()) {
/* 656 */       minSoFar = min(minSoFar, iterator.next());
/*     */     }
/*     */     
/* 659 */     return minSoFar;
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
/* 675 */     E minSoFar = min(min(a, b), c);
/*     */     
/* 677 */     for (E r : rest) {
/* 678 */       minSoFar = min(minSoFar, r);
/*     */     }
/*     */     
/* 681 */     return minSoFar;
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
/* 698 */     return (compare((T)a, (T)b) <= 0) ? a : b;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Ordering.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */