/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.util.Comparator;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class EmptyImmutableSortedMultiset<E>
/*    */   extends ImmutableSortedMultiset<E>
/*    */ {
/*    */   EmptyImmutableSortedMultiset(Comparator<? super E> comparator) {
/* 30 */     super(comparator);
/*    */   }
/*    */ 
/*    */   
/*    */   public Multiset.Entry<E> firstEntry() {
/* 35 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Multiset.Entry<E> lastEntry() {
/* 40 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int count(@Nullable Object element) {
/* 45 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 50 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   ImmutableSortedSet<E> createElementSet() {
/* 55 */     return ImmutableSortedSet.emptySet(comparator());
/*    */   }
/*    */ 
/*    */   
/*    */   ImmutableSortedSet<E> createDescendingElementSet() {
/* 60 */     return ImmutableSortedSet.emptySet(reverseComparator());
/*    */   }
/*    */ 
/*    */   
/*    */   UnmodifiableIterator<Multiset.Entry<E>> descendingEntryIterator() {
/* 65 */     return Iterators.emptyIterator();
/*    */   }
/*    */ 
/*    */   
/*    */   UnmodifiableIterator<Multiset.Entry<E>> entryIterator() {
/* 70 */     return Iterators.emptyIterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public ImmutableSortedMultiset<E> headMultiset(E upperBound, BoundType boundType) {
/* 75 */     Preconditions.checkNotNull(upperBound);
/* 76 */     Preconditions.checkNotNull(boundType);
/* 77 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public ImmutableSortedMultiset<E> tailMultiset(E lowerBound, BoundType boundType) {
/* 82 */     Preconditions.checkNotNull(lowerBound);
/* 83 */     Preconditions.checkNotNull(boundType);
/* 84 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   int distinctElements() {
/* 89 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   boolean isPartialView() {
/* 94 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\EmptyImmutableSortedMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */