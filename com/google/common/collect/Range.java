/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.SortedSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @Beta
/*     */ public final class Range<C extends Comparable>
/*     */   implements Predicate<C>, Serializable
/*     */ {
/*     */   final Cut<C> lowerBound;
/*     */   final Cut<C> upperBound;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   Range(Cut<C> lowerBound, Cut<C> upperBound) {
/* 118 */     if (lowerBound.compareTo(upperBound) > 0) {
/* 119 */       throw new IllegalArgumentException("Invalid range: " + toString(lowerBound, upperBound));
/*     */     }
/*     */     
/* 122 */     this.lowerBound = lowerBound;
/* 123 */     this.upperBound = upperBound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasLowerBound() {
/* 130 */     return (this.lowerBound != Cut.belowAll());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public C lowerEndpoint() {
/* 140 */     return this.lowerBound.endpoint();
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
/*     */   public BoundType lowerBoundType() {
/* 152 */     return this.lowerBound.typeAsLowerBound();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasUpperBound() {
/* 159 */     return (this.upperBound != Cut.aboveAll());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public C upperEndpoint() {
/* 169 */     return this.upperBound.endpoint();
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
/*     */   public BoundType upperBoundType() {
/* 181 */     return this.upperBound.typeAsUpperBound();
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
/*     */   public boolean isEmpty() {
/* 194 */     return this.lowerBound.equals(this.upperBound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(C value) {
/* 203 */     Preconditions.checkNotNull(value);
/*     */     
/* 205 */     return (this.lowerBound.isLessThan(value) && !this.upperBound.isLessThan(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean apply(C input) {
/* 214 */     return contains(input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsAll(Iterable<? extends C> values) {
/* 222 */     if (Iterables.isEmpty(values)) {
/* 223 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 227 */     if (values instanceof SortedSet) {
/* 228 */       SortedSet<? extends C> set = cast(values);
/* 229 */       Comparator<?> comparator = set.comparator();
/* 230 */       if (Ordering.<Comparable>natural().equals(comparator) || comparator == null) {
/* 231 */         return (contains(set.first()) && contains(set.last()));
/*     */       }
/*     */     } 
/*     */     
/* 235 */     for (Comparable comparable : values) {
/* 236 */       if (!contains((C)comparable)) {
/* 237 */         return false;
/*     */       }
/*     */     } 
/* 240 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean encloses(Range<C> other) {
/* 282 */     return (this.lowerBound.compareTo(other.lowerBound) <= 0 && this.upperBound.compareTo(other.upperBound) >= 0);
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
/*     */   
/*     */   public Range<C> intersection(Range<C> other) {
/* 314 */     Cut<C> newLower = (Cut<C>)Ordering.<Comparable>natural().max(this.lowerBound, other.lowerBound);
/* 315 */     Cut<C> newUpper = (Cut<C>)Ordering.<Comparable>natural().min(this.upperBound, other.upperBound);
/* 316 */     return (Range)Ranges.create(newLower, newUpper);
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
/*     */   public boolean isConnected(Range<C> other) {
/* 345 */     return (this.lowerBound.compareTo(other.upperBound) <= 0 && other.lowerBound.compareTo(this.upperBound) <= 0);
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
/*     */   public Range<C> span(Range<C> other) {
/* 370 */     Cut<C> newLower = (Cut<C>)Ordering.<Comparable>natural().min(this.lowerBound, other.lowerBound);
/* 371 */     Cut<C> newUpper = (Cut<C>)Ordering.<Comparable>natural().max(this.upperBound, other.upperBound);
/* 372 */     return (Range)Ranges.create(newLower, newUpper);
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
/*     */   @GwtCompatible(serializable = false)
/*     */   public ContiguousSet<C> asSet(DiscreteDomain<C> domain) {
/* 398 */     Preconditions.checkNotNull(domain);
/* 399 */     Range<C> effectiveRange = this;
/*     */     try {
/* 401 */       if (!hasLowerBound()) {
/* 402 */         effectiveRange = effectiveRange.intersection((Range)Ranges.atLeast((Comparable<?>)domain.minValue()));
/*     */       }
/*     */       
/* 405 */       if (!hasUpperBound()) {
/* 406 */         effectiveRange = effectiveRange.intersection((Range)Ranges.atMost((Comparable<?>)domain.maxValue()));
/*     */       }
/*     */     }
/* 409 */     catch (NoSuchElementException e) {
/* 410 */       throw new IllegalArgumentException(e);
/*     */     } 
/*     */ 
/*     */     
/* 414 */     boolean empty = (effectiveRange.isEmpty() || compareOrThrow((Comparable)this.lowerBound.leastValueAbove(domain), (Comparable)this.upperBound.greatestValueBelow(domain)) > 0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 419 */     return empty ? new EmptyContiguousSet<C>(domain) : new RegularContiguousSet<C>(effectiveRange, domain);
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
/*     */   public Range<C> canonical(DiscreteDomain<C> domain) {
/* 450 */     Preconditions.checkNotNull(domain);
/* 451 */     Cut<C> lower = this.lowerBound.canonical(domain);
/* 452 */     Cut<C> upper = this.upperBound.canonical(domain);
/* 453 */     return (lower == this.lowerBound && upper == this.upperBound) ? this : (Range)Ranges.<Comparable<?>>create(lower, upper);
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
/*     */   public boolean equals(@Nullable Object object) {
/* 467 */     if (object instanceof Range) {
/* 468 */       Range<?> other = (Range)object;
/* 469 */       return (this.lowerBound.equals(other.lowerBound) && this.upperBound.equals(other.upperBound));
/*     */     } 
/*     */     
/* 472 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 477 */     return this.lowerBound.hashCode() * 31 + this.upperBound.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 485 */     return toString(this.lowerBound, this.upperBound);
/*     */   }
/*     */   
/*     */   private static String toString(Cut<?> lowerBound, Cut<?> upperBound) {
/* 489 */     StringBuilder sb = new StringBuilder(16);
/* 490 */     lowerBound.describeAsLowerBound(sb);
/* 491 */     sb.append('‥');
/* 492 */     upperBound.describeAsUpperBound(sb);
/* 493 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> SortedSet<T> cast(Iterable<T> iterable) {
/* 500 */     return (SortedSet<T>)iterable;
/*     */   }
/*     */ 
/*     */   
/*     */   static int compareOrThrow(Comparable<Comparable> left, Comparable right) {
/* 505 */     return left.compareTo(right);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Range.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */