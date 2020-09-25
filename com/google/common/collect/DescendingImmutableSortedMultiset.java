/*    */ package com.google.common.collect;
/*    */ 
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
/*    */ final class DescendingImmutableSortedMultiset<E>
/*    */   extends ImmutableSortedMultiset<E>
/*    */ {
/*    */   private final transient ImmutableSortedMultiset<E> forward;
/*    */   
/*    */   DescendingImmutableSortedMultiset(ImmutableSortedMultiset<E> forward) {
/* 28 */     super(forward.reverseComparator());
/* 29 */     this.forward = forward;
/*    */   }
/*    */ 
/*    */   
/*    */   public int count(@Nullable Object element) {
/* 34 */     return this.forward.count(element);
/*    */   }
/*    */ 
/*    */   
/*    */   public Multiset.Entry<E> firstEntry() {
/* 39 */     return this.forward.lastEntry();
/*    */   }
/*    */ 
/*    */   
/*    */   public Multiset.Entry<E> lastEntry() {
/* 44 */     return this.forward.firstEntry();
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 49 */     return this.forward.size();
/*    */   }
/*    */ 
/*    */   
/*    */   ImmutableSortedSet<E> createElementSet() {
/* 54 */     return this.forward.createDescendingElementSet();
/*    */   }
/*    */ 
/*    */   
/*    */   ImmutableSortedSet<E> createDescendingElementSet() {
/* 59 */     return this.forward.elementSet();
/*    */   }
/*    */ 
/*    */   
/*    */   UnmodifiableIterator<Multiset.Entry<E>> descendingEntryIterator() {
/* 64 */     return this.forward.entryIterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public ImmutableSortedMultiset<E> descendingMultiset() {
/* 69 */     return this.forward;
/*    */   }
/*    */ 
/*    */   
/*    */   public ImmutableSortedMultiset<E> headMultiset(E upperBound, BoundType boundType) {
/* 74 */     return this.forward.tailMultiset(upperBound, boundType).descendingMultiset();
/*    */   }
/*    */ 
/*    */   
/*    */   public ImmutableSortedMultiset<E> tailMultiset(E lowerBound, BoundType boundType) {
/* 79 */     return this.forward.headMultiset(lowerBound, boundType).descendingMultiset();
/*    */   }
/*    */ 
/*    */   
/*    */   UnmodifiableIterator<Multiset.Entry<E>> entryIterator() {
/* 84 */     return this.forward.descendingEntryIterator();
/*    */   }
/*    */ 
/*    */   
/*    */   int distinctElements() {
/* 89 */     return this.forward.distinctElements();
/*    */   }
/*    */ 
/*    */   
/*    */   boolean isPartialView() {
/* 94 */     return this.forward.isPartialView();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\DescendingImmutableSortedMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */