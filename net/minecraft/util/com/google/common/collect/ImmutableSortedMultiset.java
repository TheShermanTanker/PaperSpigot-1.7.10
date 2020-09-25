/*     */ package net.minecraft.util.com.google.common.collect;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NavigableSet;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtIncompatible;
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
/*     */ @Beta
/*     */ @GwtIncompatible("hasn't been tested yet")
/*     */ public abstract class ImmutableSortedMultiset<E>
/*     */   extends ImmutableSortedMultisetFauxverideShim<E>
/*     */   implements SortedMultiset<E>
/*     */ {
/*  86 */   private static final Comparator<Comparable> NATURAL_ORDER = Ordering.natural();
/*     */   
/*  88 */   private static final ImmutableSortedMultiset<Comparable> NATURAL_EMPTY_MULTISET = new EmptyImmutableSortedMultiset<Comparable>(NATURAL_ORDER);
/*     */ 
/*     */   
/*     */   transient ImmutableSortedMultiset<E> descendingMultiset;
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableSortedMultiset<E> of() {
/*  96 */     return (ImmutableSortedMultiset)NATURAL_EMPTY_MULTISET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E element) {
/* 103 */     RegularImmutableSortedSet<E> elementSet = (RegularImmutableSortedSet<E>)ImmutableSortedSet.<E>of(element);
/*     */     
/* 105 */     int[] counts = { 1 };
/* 106 */     long[] cumulativeCounts = { 0L, 1L };
/* 107 */     return new RegularImmutableSortedMultiset<E>(elementSet, counts, cumulativeCounts, 0, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e1, E e2) {
/* 118 */     return copyOf(Ordering.natural(), Arrays.asList((E[])new Comparable[] { (Comparable)e1, (Comparable)e2 }));
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
/* 129 */     return copyOf(Ordering.natural(), Arrays.asList((E[])new Comparable[] { (Comparable)e1, (Comparable)e2, (Comparable)e3 }));
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
/* 141 */     return copyOf(Ordering.natural(), Arrays.asList((E[])new Comparable[] { (Comparable)e1, (Comparable)e2, (Comparable)e3, (Comparable)e4 }));
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
/* 153 */     return copyOf(Ordering.natural(), Arrays.asList((E[])new Comparable[] { (Comparable)e1, (Comparable)e2, (Comparable)e3, (Comparable)e4, (Comparable)e5 }));
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
/*     */   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E... remaining) {
/* 165 */     int size = remaining.length + 6;
/* 166 */     List<E> all = Lists.newArrayListWithCapacity(size);
/* 167 */     Collections.addAll(all, (E[])new Comparable[] { (Comparable)e1, (Comparable)e2, (Comparable)e3, (Comparable)e4, (Comparable)e5, (Comparable)e6 });
/* 168 */     Collections.addAll(all, remaining);
/* 169 */     return copyOf(Ordering.natural(), all);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> copyOf(E[] elements) {
/* 179 */     return copyOf(Ordering.natural(), Arrays.asList(elements));
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
/* 208 */     Ordering<E> naturalOrder = Ordering.natural();
/* 209 */     return copyOf(naturalOrder, elements);
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
/* 226 */     Ordering<E> naturalOrder = Ordering.natural();
/* 227 */     return copyOf(naturalOrder, elements);
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
/* 238 */     Preconditions.checkNotNull(comparator);
/* 239 */     return (new Builder<E>(comparator)).addAll(elements).build();
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
/* 254 */     if (elements instanceof ImmutableSortedMultiset) {
/*     */       
/* 256 */       ImmutableSortedMultiset<E> multiset = (ImmutableSortedMultiset)elements;
/* 257 */       if (comparator.equals(multiset.comparator())) {
/* 258 */         if (multiset.isPartialView()) {
/* 259 */           return copyOfSortedEntries(comparator, multiset.entrySet().asList());
/*     */         }
/* 261 */         return multiset;
/*     */       } 
/*     */     } 
/*     */     
/* 265 */     elements = Lists.newArrayList(elements);
/* 266 */     TreeMultiset<E> sortedCopy = TreeMultiset.create((Comparator<? super E>)Preconditions.checkNotNull(comparator));
/* 267 */     Iterables.addAll(sortedCopy, elements);
/* 268 */     return copyOfSortedEntries(comparator, sortedCopy.entrySet());
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
/* 286 */     return copyOfSortedEntries(sortedMultiset.comparator(), Lists.newArrayList(sortedMultiset.entrySet()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E> ImmutableSortedMultiset<E> copyOfSortedEntries(Comparator<? super E> comparator, Collection<Multiset.Entry<E>> entries) {
/* 292 */     if (entries.isEmpty()) {
/* 293 */       return emptyMultiset(comparator);
/*     */     }
/* 295 */     ImmutableList.Builder<E> elementsBuilder = new ImmutableList.Builder<E>(entries.size());
/* 296 */     int[] counts = new int[entries.size()];
/* 297 */     long[] cumulativeCounts = new long[entries.size() + 1];
/* 298 */     int i = 0;
/* 299 */     for (Multiset.Entry<E> entry : entries) {
/* 300 */       elementsBuilder.add(entry.getElement());
/* 301 */       counts[i] = entry.getCount();
/* 302 */       cumulativeCounts[i + 1] = cumulativeCounts[i] + counts[i];
/* 303 */       i++;
/*     */     } 
/* 305 */     return new RegularImmutableSortedMultiset<E>(new RegularImmutableSortedSet<E>(elementsBuilder.build(), comparator), counts, cumulativeCounts, 0, entries.size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <E> ImmutableSortedMultiset<E> emptyMultiset(Comparator<? super E> comparator) {
/* 312 */     if (NATURAL_ORDER.equals(comparator)) {
/* 313 */       return (ImmutableSortedMultiset)NATURAL_EMPTY_MULTISET;
/*     */     }
/* 315 */     return new EmptyImmutableSortedMultiset<E>(comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Comparator<? super E> comparator() {
/* 322 */     return elementSet().comparator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSortedMultiset<E> descendingMultiset() {
/* 332 */     ImmutableSortedMultiset<E> result = this.descendingMultiset;
/* 333 */     if (result == null) {
/* 334 */       return this.descendingMultiset = new DescendingImmutableSortedMultiset<E>(this);
/*     */     }
/* 336 */     return result;
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
/*     */   @Deprecated
/*     */   public final Multiset.Entry<E> pollFirstEntry() {
/* 350 */     throw new UnsupportedOperationException();
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
/*     */   @Deprecated
/*     */   public final Multiset.Entry<E> pollLastEntry() {
/* 364 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSortedMultiset<E> subMultiset(E lowerBound, BoundType lowerBoundType, E upperBound, BoundType upperBoundType) {
/* 373 */     Preconditions.checkArgument((comparator().compare(lowerBound, upperBound) <= 0), "Expected lowerBound <= upperBound but %s > %s", new Object[] { lowerBound, upperBound });
/*     */     
/* 375 */     return tailMultiset(lowerBound, lowerBoundType).headMultiset(upperBound, upperBoundType);
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
/* 390 */     return new Builder<E>(comparator);
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
/* 402 */     return new Builder<E>(Ordering.<Comparable>natural().reverse());
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
/* 416 */     return new Builder<E>(Ordering.natural());
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
/*     */     
/*     */     public Builder(Comparator<? super E> comparator) {
/* 446 */       super(TreeMultiset.create(comparator));
/* 447 */       this.comparator = (Comparator<? super E>)Preconditions.checkNotNull(comparator);
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
/* 459 */       super.add(element);
/* 460 */       return this;
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
/* 476 */       super.addCopies(element, occurrences);
/* 477 */       return this;
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
/* 492 */       super.setCount(element, count);
/* 493 */       return this;
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
/* 505 */       super.add(elements);
/* 506 */       return this;
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
/* 518 */       super.addAll(elements);
/* 519 */       return this;
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
/* 531 */       super.addAll(elements);
/* 532 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ImmutableSortedMultiset<E> build() {
/* 541 */       return ImmutableSortedMultiset.copyOfSorted((SortedMultiset<E>)this.contents);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class SerializedForm<E>
/*     */     implements Serializable {
/*     */     Comparator<? super E> comparator;
/*     */     E[] elements;
/*     */     int[] counts;
/*     */     
/*     */     SerializedForm(SortedMultiset<E> multiset) {
/* 552 */       this.comparator = multiset.comparator();
/* 553 */       int n = multiset.entrySet().size();
/* 554 */       this.elements = (E[])new Object[n];
/* 555 */       this.counts = new int[n];
/* 556 */       int i = 0;
/* 557 */       for (Multiset.Entry<E> entry : multiset.entrySet()) {
/* 558 */         this.elements[i] = entry.getElement();
/* 559 */         this.counts[i] = entry.getCount();
/* 560 */         i++;
/*     */       } 
/*     */     }
/*     */     
/*     */     Object readResolve() {
/* 565 */       int n = this.elements.length;
/* 566 */       ImmutableSortedMultiset.Builder<E> builder = new ImmutableSortedMultiset.Builder<E>(this.comparator);
/* 567 */       for (int i = 0; i < n; i++) {
/* 568 */         builder.addCopies(this.elements[i], this.counts[i]);
/*     */       }
/* 570 */       return builder.build();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   Object writeReplace() {
/* 576 */     return new SerializedForm<E>(this);
/*     */   }
/*     */   
/*     */   public abstract ImmutableSortedSet<E> elementSet();
/*     */   
/*     */   public abstract ImmutableSortedMultiset<E> headMultiset(E paramE, BoundType paramBoundType);
/*     */   
/*     */   public abstract ImmutableSortedMultiset<E> tailMultiset(E paramE, BoundType paramBoundType);
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\ImmutableSortedMultiset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */