/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
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
/*     */ @Beta
/*     */ @GwtCompatible
/*     */ public abstract class ContiguousSet<C extends Comparable>
/*     */   extends ImmutableSortedSet<C>
/*     */ {
/*     */   final DiscreteDomain<C> domain;
/*     */   
/*     */   ContiguousSet(DiscreteDomain<C> domain) {
/*  38 */     super(Ordering.natural());
/*  39 */     this.domain = domain;
/*     */   }
/*     */   
/*     */   public ContiguousSet<C> headSet(C toElement) {
/*  43 */     return headSet((C)Preconditions.checkNotNull(toElement), false);
/*     */   }
/*     */   
/*     */   ContiguousSet<C> headSet(C toElement, boolean inclusive) {
/*  47 */     return headSetImpl((C)Preconditions.checkNotNull(toElement), inclusive);
/*     */   }
/*     */   
/*     */   public ContiguousSet<C> subSet(C fromElement, C toElement) {
/*  51 */     Preconditions.checkNotNull(fromElement);
/*  52 */     Preconditions.checkNotNull(toElement);
/*  53 */     Preconditions.checkArgument((comparator().compare(fromElement, toElement) <= 0));
/*  54 */     return subSet(fromElement, true, toElement, false);
/*     */   }
/*     */ 
/*     */   
/*     */   ContiguousSet<C> subSet(C fromElement, boolean fromInclusive, C toElement, boolean toInclusive) {
/*  59 */     Preconditions.checkNotNull(fromElement);
/*  60 */     Preconditions.checkNotNull(toElement);
/*  61 */     Preconditions.checkArgument((comparator().compare(fromElement, toElement) <= 0));
/*  62 */     return subSetImpl(fromElement, fromInclusive, toElement, toInclusive);
/*     */   }
/*     */   
/*     */   public ContiguousSet<C> tailSet(C fromElement) {
/*  66 */     return tailSet((C)Preconditions.checkNotNull(fromElement), true);
/*     */   }
/*     */   
/*     */   ContiguousSet<C> tailSet(C fromElement, boolean inclusive) {
/*  70 */     return tailSetImpl((C)Preconditions.checkNotNull(fromElement), inclusive);
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
/*     */ 
/*     */   
/*     */   public String toString() {
/* 114 */     return range().toString();
/*     */   }
/*     */   
/*     */   abstract ContiguousSet<C> headSetImpl(C paramC, boolean paramBoolean);
/*     */   
/*     */   abstract ContiguousSet<C> subSetImpl(C paramC1, boolean paramBoolean1, C paramC2, boolean paramBoolean2);
/*     */   
/*     */   abstract ContiguousSet<C> tailSetImpl(C paramC, boolean paramBoolean);
/*     */   
/*     */   public abstract ContiguousSet<C> intersection(ContiguousSet<C> paramContiguousSet);
/*     */   
/*     */   public abstract Range<C> range();
/*     */   
/*     */   public abstract Range<C> range(BoundType paramBoundType1, BoundType paramBoundType2);
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ContiguousSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */