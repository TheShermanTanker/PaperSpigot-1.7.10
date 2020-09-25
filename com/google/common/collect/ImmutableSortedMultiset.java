/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtIncompatible("hasn't been tested yet")
/*     */ abstract class ImmutableSortedMultiset<E>
/*     */   extends ImmutableSortedMultisetFauxverideShim<E>
/*     */   implements SortedMultiset<E>
/*     */ {
/*  79 */   private static final Comparator<Comparable> NATURAL_ORDER = Ordering.natural();
/*     */   
/*  81 */   private static final ImmutableSortedMultiset<Comparable> NATURAL_EMPTY_MULTISET = new EmptyImmutableSortedMultiset<Comparable>(NATURAL_ORDER);
/*     */   
/*     */   private final transient Comparator<? super E> comparator;
/*     */   private transient Comparator<? super E> reverseComparator;
/*     */   private transient ImmutableSortedSet<E> elementSet;
/*     */   transient ImmutableSortedMultiset<E> descendingMultiset;
/*     */   
/*     */   public static <E> ImmutableSortedMultiset<E> of() {
/*  89 */     return (ImmutableSortedMultiset)NATURAL_EMPTY_MULTISET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E element) {
/*  96 */     return RegularImmutableSortedMultiset.createFromSorted((Comparator)NATURAL_ORDER, ImmutableList.of(Multisets.immutableEntry((E)Preconditions.checkNotNull(element), 1)));
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
/*     */   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e1, E e2) {
/* 108 */     return copyOf(Ordering.natural(), Arrays.asList((E[])new Comparable[] { (Comparable)e1, (Comparable)e2 }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e1, E e2, E e3) {
/* 119 */     return copyOf(Ordering.natural(), Arrays.asList((E[])new Comparable[] { (Comparable)e1, (Comparable)e2, (Comparable)e3 }));
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
/*     */   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e1, E e2, E e3, E e4) {
/* 131 */     return copyOf(Ordering.natural(), Arrays.asList((E[])new Comparable[] { (Comparable)e1, (Comparable)e2, (Comparable)e3, (Comparable)e4 }));
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
/*     */   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e1, E e2, E e3, E e4, E e5) {
/* 143 */     return copyOf(Ordering.natural(), Arrays.asList((E[])new Comparable[] { (Comparable)e1, (Comparable)e2, (Comparable)e3, (Comparable)e4, (Comparable)e5 }));
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
/*     */   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E... remaining) {
/* 161 */     int size = remaining.length + 6;
/* 162 */     List<E> all = new ArrayList<E>(size);
/* 163 */     Collections.addAll(all, (E[])new Comparable[] { (Comparable)e1, (Comparable)e2, (Comparable)e3, (Comparable)e4, (Comparable)e5, (Comparable)e6 });
/* 164 */     Collections.addAll(all, remaining);
/* 165 */     return copyOf(Ordering.natural(), all);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> copyOf(E[] elements) {
/* 175 */     return copyOf(Ordering.natural(), Arrays.asList(elements));
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
/*     */   public static <E> ImmutableSortedMultiset<E> copyOf(Iterable<? extends E> elements) {
/* 204 */     Ordering<E> naturalOrder = Ordering.natural();
/* 205 */     return copyOf(naturalOrder, elements);
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
/*     */   public static <E> ImmutableSortedMultiset<E> copyOf(Iterator<? extends E> elements) {
/* 222 */     Ordering<E> naturalOrder = Ordering.natural();
/* 223 */     return copyOfInternal(naturalOrder, elements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableSortedMultiset<E> copyOf(Comparator<? super E> comparator, Iterator<? extends E> elements) {
/* 234 */     Preconditions.checkNotNull(comparator);
/* 235 */     return copyOfInternal(comparator, elements);
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
/*     */   public static <E> ImmutableSortedMultiset<E> copyOf(Comparator<? super E> comparator, Iterable<? extends E> elements) {
/* 250 */     Preconditions.checkNotNull(comparator);
/* 251 */     return copyOfInternal(comparator, elements);
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
/*     */   public static <E> ImmutableSortedMultiset<E> copyOfSorted(SortedMultiset<E> sortedMultiset) {
/*     */     Comparator<Comparable> comparator1;
/* 270 */     Comparator<? super E> comparator = sortedMultiset.comparator();
/* 271 */     if (comparator == null) {
/* 272 */       comparator1 = NATURAL_ORDER;
/*     */     }
/* 274 */     return copyOfInternal((Comparator)comparator1, sortedMultiset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E> ImmutableSortedMultiset<E> copyOfInternal(Comparator<? super E> comparator, Iterable<? extends E> iterable) {
/* 280 */     if (SortedIterables.hasSameComparator(comparator, iterable) && iterable instanceof ImmutableSortedMultiset) {
/*     */       
/* 282 */       ImmutableSortedMultiset<E> multiset = (ImmutableSortedMultiset)iterable;
/* 283 */       if (!multiset.isPartialView()) {
/* 284 */         return (ImmutableSortedMultiset)iterable;
/*     */       }
/*     */     } 
/* 287 */     ImmutableList<Multiset.Entry<E>> entries = ImmutableList.copyOf(SortedIterables.sortedCounts(comparator, (Iterable)iterable));
/*     */     
/* 289 */     if (entries.isEmpty()) {
/* 290 */       return emptyMultiset(comparator);
/*     */     }
/* 292 */     verifyEntries(entries);
/* 293 */     return RegularImmutableSortedMultiset.createFromSorted(comparator, entries);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E> ImmutableSortedMultiset<E> copyOfInternal(Comparator<? super E> comparator, Iterator<? extends E> iterator) {
/* 299 */     ImmutableList<Multiset.Entry<E>> entries = ImmutableList.copyOf(SortedIterables.sortedCounts(comparator, (Iterator)iterator));
/*     */     
/* 301 */     if (entries.isEmpty()) {
/* 302 */       return emptyMultiset(comparator);
/*     */     }
/* 304 */     verifyEntries(entries);
/* 305 */     return RegularImmutableSortedMultiset.createFromSorted(comparator, entries);
/*     */   }
/*     */   
/*     */   private static <E> void verifyEntries(Collection<Multiset.Entry<E>> entries) {
/* 309 */     for (Multiset.Entry<E> entry : entries) {
/* 310 */       Preconditions.checkNotNull(entry.getElement());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static <E> ImmutableSortedMultiset<E> emptyMultiset(Comparator<? super E> comparator) {
/* 316 */     if (NATURAL_ORDER.equals(comparator)) {
/* 317 */       return (ImmutableSortedMultiset)NATURAL_EMPTY_MULTISET;
/*     */     }
/* 319 */     return new EmptyImmutableSortedMultiset<E>(comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   ImmutableSortedMultiset(Comparator<? super E> comparator) {
/* 325 */     this.comparator = (Comparator<? super E>)Preconditions.checkNotNull(comparator);
/*     */   }
/*     */ 
/*     */   
/*     */   public Comparator<? super E> comparator() {
/* 330 */     return this.comparator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Comparator<Object> unsafeComparator() {
/* 338 */     return (Comparator)this.comparator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Comparator<? super E> reverseComparator() {
/* 344 */     Comparator<? super E> result = this.reverseComparator;
/* 345 */     if (result == null) {
/* 346 */       return this.reverseComparator = Ordering.from(this.comparator).reverse();
/*     */     }
/* 348 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSortedSet<E> elementSet() {
/* 355 */     ImmutableSortedSet<E> result = this.elementSet;
/* 356 */     if (result == null) {
/* 357 */       return this.elementSet = createElementSet();
/*     */     }
/* 359 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSortedMultiset<E> descendingMultiset() {
/* 370 */     ImmutableSortedMultiset<E> result = this.descendingMultiset;
/* 371 */     if (result == null) {
/* 372 */       return this.descendingMultiset = new DescendingImmutableSortedMultiset<E>(this);
/*     */     }
/* 374 */     return result;
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
/*     */   public final Multiset.Entry<E> pollFirstEntry() {
/* 386 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Multiset.Entry<E> pollLastEntry() {
/* 396 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSortedMultiset<E> subMultiset(E lowerBound, BoundType lowerBoundType, E upperBound, BoundType upperBoundType) {
/* 405 */     return tailMultiset(lowerBound, lowerBoundType).headMultiset(upperBound, upperBoundType);
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
/*     */   public static <E> Builder<E> orderedBy(Comparator<E> comparator) {
/* 420 */     return new Builder<E>(comparator);
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
/*     */   public static <E extends Comparable<E>> Builder<E> reverseOrder() {
/* 432 */     return new Builder<E>(Ordering.<Comparable>natural().reverse());
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
/*     */   public static <E extends Comparable<E>> Builder<E> naturalOrder() {
/* 446 */     return new Builder<E>(Ordering.natural());
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
/*     */   public static class Builder<E>
/*     */     extends ImmutableMultiset.Builder<E>
/*     */   {
/*     */     private final Comparator<? super E> comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder(Comparator<? super E> comparator) {
/* 474 */       super(TreeMultiset.create(comparator));
/* 475 */       this.comparator = (Comparator<? super E>)Preconditions.checkNotNull(comparator);
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
/*     */     public Builder<E> add(E element) {
/* 487 */       super.add(element);
/* 488 */       return this;
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
/*     */     public Builder<E> addCopies(E element, int occurrences) {
/* 504 */       super.addCopies(element, occurrences);
/* 505 */       return this;
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
/*     */     public Builder<E> setCount(E element, int count) {
/* 520 */       super.setCount(element, count);
/* 521 */       return this;
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
/*     */     public Builder<E> add(E... elements) {
/* 533 */       super.add(elements);
/* 534 */       return this;
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
/*     */     public Builder<E> addAll(Iterable<? extends E> elements) {
/* 546 */       super.addAll(elements);
/* 547 */       return this;
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
/*     */     public Builder<E> addAll(Iterator<? extends E> elements) {
/* 559 */       super.addAll(elements);
/* 560 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ImmutableSortedMultiset<E> build() {
/* 569 */       return ImmutableSortedMultiset.copyOf(this.comparator, this.contents);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class SerializedForm implements Serializable {
/*     */     Comparator comparator;
/*     */     Object[] elements;
/*     */     int[] counts;
/*     */     
/*     */     SerializedForm(SortedMultiset<?> multiset) {
/* 579 */       this.comparator = multiset.comparator();
/* 580 */       int n = multiset.entrySet().size();
/* 581 */       this.elements = new Object[n];
/* 582 */       this.counts = new int[n];
/* 583 */       int i = 0;
/* 584 */       for (Multiset.Entry<?> entry : multiset.entrySet()) {
/* 585 */         this.elements[i] = entry.getElement();
/* 586 */         this.counts[i] = entry.getCount();
/* 587 */         i++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     Object readResolve() {
/* 593 */       int n = this.elements.length;
/* 594 */       ImmutableSortedMultiset.Builder<Object> builder = ImmutableSortedMultiset.orderedBy(this.comparator);
/* 595 */       for (int i = 0; i < n; i++) {
/* 596 */         builder.addCopies(this.elements[i], this.counts[i]);
/*     */       }
/* 598 */       return builder.build();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   Object writeReplace() {
/* 604 */     return new SerializedForm(this);
/*     */   }
/*     */   
/*     */   abstract ImmutableSortedSet<E> createElementSet();
/*     */   
/*     */   abstract ImmutableSortedSet<E> createDescendingElementSet();
/*     */   
/*     */   abstract UnmodifiableIterator<Multiset.Entry<E>> descendingEntryIterator();
/*     */   
/*     */   public abstract ImmutableSortedMultiset<E> headMultiset(E paramE, BoundType paramBoundType);
/*     */   
/*     */   public abstract ImmutableSortedMultiset<E> tailMultiset(E paramE, BoundType paramBoundType);
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ImmutableSortedMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */