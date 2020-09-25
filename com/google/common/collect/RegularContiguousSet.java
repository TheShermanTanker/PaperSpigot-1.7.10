/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ @GwtCompatible(emulated = true)
/*     */ final class RegularContiguousSet<C extends Comparable>
/*     */   extends ContiguousSet<C>
/*     */ {
/*     */   private final Range<C> range;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   RegularContiguousSet(Range<C> range, DiscreteDomain<C> domain) {
/*  40 */     super(domain);
/*  41 */     this.range = range;
/*     */   }
/*     */ 
/*     */   
/*     */   ContiguousSet<C> headSetImpl(C toElement, boolean inclusive) {
/*  46 */     return this.range.intersection((Range)Ranges.upTo((Comparable<?>)toElement, BoundType.forBoolean(inclusive))).asSet(this.domain);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int indexOf(Object target) {
/*  52 */     return contains(target) ? (int)this.domain.distance(first(), (C)target) : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   ContiguousSet<C> subSetImpl(C fromElement, boolean fromInclusive, C toElement, boolean toInclusive) {
/*  58 */     return this.range.intersection((Range)Ranges.range((Comparable<?>)fromElement, BoundType.forBoolean(fromInclusive), (Comparable<?>)toElement, BoundType.forBoolean(toInclusive))).asSet(this.domain);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   ContiguousSet<C> tailSetImpl(C fromElement, boolean inclusive) {
/*  64 */     return this.range.intersection((Range)Ranges.downTo((Comparable<?>)fromElement, BoundType.forBoolean(inclusive))).asSet(this.domain);
/*     */   }
/*     */ 
/*     */   
/*     */   public UnmodifiableIterator<C> iterator() {
/*  69 */     return new AbstractLinkedIterator<C>((Comparable)first()) {
/*  70 */         final C last = RegularContiguousSet.this.last();
/*     */ 
/*     */         
/*     */         protected C computeNext(C previous) {
/*  74 */           return RegularContiguousSet.equalsOrThrow((Comparable<?>)previous, (Comparable<?>)this.last) ? null : RegularContiguousSet.this.domain.next(previous);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private static boolean equalsOrThrow(Comparable<?> left, @Nullable Comparable<?> right) {
/*  80 */     return (right != null && Range.compareOrThrow(left, right) == 0);
/*     */   }
/*     */   
/*     */   boolean isPartialView() {
/*  84 */     return false;
/*     */   }
/*     */   
/*     */   public C first() {
/*  88 */     return this.range.lowerBound.leastValueAbove(this.domain);
/*     */   }
/*     */   
/*     */   public C last() {
/*  92 */     return this.range.upperBound.greatestValueBelow(this.domain);
/*     */   }
/*     */   
/*     */   public int size() {
/*  96 */     long distance = this.domain.distance(first(), last());
/*  97 */     return (distance >= 2147483647L) ? Integer.MAX_VALUE : ((int)distance + 1);
/*     */   }
/*     */   
/*     */   public boolean contains(Object object) {
/*     */     try {
/* 102 */       return this.range.contains((C)object);
/* 103 */     } catch (ClassCastException e) {
/* 104 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean containsAll(Collection<?> targets) {
/*     */     try {
/* 110 */       return this.range.containsAll((Iterable)targets);
/* 111 */     } catch (ClassCastException e) {
/* 112 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 122 */     return ObjectArrays.toArrayImpl(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] other) {
/* 127 */     return ObjectArrays.toArrayImpl(this, other);
/*     */   }
/*     */   
/*     */   public ContiguousSet<C> intersection(ContiguousSet<C> other) {
/* 131 */     Preconditions.checkNotNull(other);
/* 132 */     Preconditions.checkArgument(this.domain.equals(other.domain));
/* 133 */     if (other.isEmpty()) {
/* 134 */       return other;
/*     */     }
/* 136 */     Comparable<Comparable> comparable1 = (Comparable)Ordering.<Comparable>natural().max(first(), other.first());
/* 137 */     Comparable<Comparable> comparable2 = (Comparable)Ordering.<Comparable>natural().min(last(), other.last());
/* 138 */     return (comparable1.compareTo(comparable2) < 0) ? Ranges.<Comparable<Comparable>>closed(comparable1, comparable2).asSet(this.domain) : new EmptyContiguousSet<C>(this.domain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Range<C> range() {
/* 145 */     return range(BoundType.CLOSED, BoundType.CLOSED);
/*     */   }
/*     */   
/*     */   public Range<C> range(BoundType lowerBoundType, BoundType upperBoundType) {
/* 149 */     return (Range)Ranges.create(this.range.lowerBound.withLowerBoundType(lowerBoundType, this.domain), this.range.upperBound.withUpperBoundType(upperBoundType, this.domain));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/* 154 */     if (object == this)
/* 155 */       return true; 
/* 156 */     if (object instanceof RegularContiguousSet) {
/* 157 */       RegularContiguousSet<?> that = (RegularContiguousSet)object;
/* 158 */       if (this.domain.equals(that.domain)) {
/* 159 */         return (first().equals(that.first()) && last().equals(that.last()));
/*     */       }
/*     */     } 
/*     */     
/* 163 */     return super.equals(object);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 168 */     return Sets.hashCodeImpl(this);
/*     */   }
/*     */   
/*     */   @GwtIncompatible("serialization")
/*     */   private static final class SerializedForm<C extends Comparable> implements Serializable {
/*     */     final Range<C> range;
/*     */     final DiscreteDomain<C> domain;
/*     */     
/*     */     private SerializedForm(Range<C> range, DiscreteDomain<C> domain) {
/* 177 */       this.range = range;
/* 178 */       this.domain = domain;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 182 */       return new RegularContiguousSet<C>(this.range, this.domain);
/*     */     }
/*     */   }
/*     */   
/*     */   @GwtIncompatible("serialization")
/*     */   Object writeReplace() {
/* 188 */     return new SerializedForm<Comparable>(this.range, this.domain);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\RegularContiguousSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */