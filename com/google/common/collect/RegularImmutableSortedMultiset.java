/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.primitives.Ints;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
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
/*     */ final class RegularImmutableSortedMultiset<E>
/*     */   extends ImmutableSortedMultiset<E>
/*     */ {
/*     */   final transient ImmutableList<CumulativeCountEntry<E>> entries;
/*     */   
/*     */   private static final class CumulativeCountEntry<E>
/*     */     extends Multisets.AbstractEntry<E>
/*     */   {
/*     */     final E element;
/*     */     final int count;
/*     */     final long cumulativeCount;
/*     */     
/*     */     CumulativeCountEntry(E element, int count, @Nullable CumulativeCountEntry<E> previous) {
/*  42 */       this.element = element;
/*  43 */       this.count = count;
/*  44 */       this.cumulativeCount = count + ((previous == null) ? 0L : previous.cumulativeCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public E getElement() {
/*  49 */       return this.element;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getCount() {
/*  54 */       return this.count;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static <E> RegularImmutableSortedMultiset<E> createFromSorted(Comparator<? super E> comparator, List<? extends Multiset.Entry<E>> entries) {
/*  60 */     List<CumulativeCountEntry<E>> newEntries = Lists.newArrayListWithCapacity(entries.size());
/*  61 */     CumulativeCountEntry<E> previous = null;
/*  62 */     for (Multiset.Entry<E> entry : entries) {
/*  63 */       newEntries.add(previous = new CumulativeCountEntry<E>(entry.getElement(), entry.getCount(), previous));
/*     */     }
/*     */     
/*  66 */     return new RegularImmutableSortedMultiset<E>(comparator, ImmutableList.copyOf(newEntries));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RegularImmutableSortedMultiset(Comparator<? super E> comparator, ImmutableList<CumulativeCountEntry<E>> entries) {
/*  73 */     super(comparator);
/*  74 */     this.entries = entries;
/*  75 */     assert !entries.isEmpty();
/*     */   }
/*     */   
/*     */   ImmutableList<E> elementList() {
/*  79 */     return new TransformedImmutableList<CumulativeCountEntry<E>, E>(this.entries)
/*     */       {
/*     */         E transform(RegularImmutableSortedMultiset.CumulativeCountEntry<E> entry) {
/*  82 */           return entry.getElement();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   ImmutableSortedSet<E> createElementSet() {
/*  89 */     return new RegularImmutableSortedSet<E>(elementList(), comparator());
/*     */   }
/*     */ 
/*     */   
/*     */   ImmutableSortedSet<E> createDescendingElementSet() {
/*  94 */     return new RegularImmutableSortedSet<E>(elementList().reverse(), reverseComparator());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   UnmodifiableIterator<Multiset.Entry<E>> entryIterator() {
/* 100 */     return this.entries.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   UnmodifiableIterator<Multiset.Entry<E>> descendingEntryIterator() {
/* 106 */     return this.entries.reverse().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public CumulativeCountEntry<E> firstEntry() {
/* 111 */     return this.entries.get(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public CumulativeCountEntry<E> lastEntry() {
/* 116 */     return this.entries.get(this.entries.size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 121 */     CumulativeCountEntry<E> firstEntry = firstEntry();
/* 122 */     CumulativeCountEntry<E> lastEntry = lastEntry();
/* 123 */     return Ints.saturatedCast(lastEntry.cumulativeCount - firstEntry.cumulativeCount + firstEntry.count);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int distinctElements() {
/* 129 */     return this.entries.size();
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isPartialView() {
/* 134 */     return this.entries.isPartialView();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int count(@Nullable Object element) {
/* 140 */     if (element == null) {
/* 141 */       return 0;
/*     */     }
/*     */     try {
/* 144 */       int index = SortedLists.binarySearch(elementList(), (E)element, comparator(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.INVERTED_INSERTION_INDEX);
/*     */       
/* 146 */       return (index >= 0) ? ((CumulativeCountEntry)this.entries.get(index)).getCount() : 0;
/* 147 */     } catch (ClassCastException e) {
/* 148 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ImmutableSortedMultiset<E> headMultiset(E upperBound, BoundType boundType) {
/*     */     int index;
/* 155 */     switch (boundType) {
/*     */       case OPEN:
/* 157 */         index = SortedLists.binarySearch(elementList(), (E)Preconditions.checkNotNull(upperBound), comparator(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 167 */         return createSubMultiset(0, index);case CLOSED: index = SortedLists.<E>binarySearch(elementList(), (E)Preconditions.checkNotNull(upperBound), comparator(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER) + 1; return createSubMultiset(0, index);
/*     */     } 
/*     */     throw new AssertionError();
/*     */   }
/*     */   public ImmutableSortedMultiset<E> tailMultiset(E lowerBound, BoundType boundType) {
/*     */     int index;
/* 173 */     switch (boundType) {
/*     */       case OPEN:
/* 175 */         index = SortedLists.<E>binarySearch(elementList(), (E)Preconditions.checkNotNull(lowerBound), comparator(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER) + 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 185 */         return createSubMultiset(index, distinctElements());case CLOSED: index = SortedLists.binarySearch(elementList(), (E)Preconditions.checkNotNull(lowerBound), comparator(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER); return createSubMultiset(index, distinctElements());
/*     */     } 
/*     */     throw new AssertionError();
/*     */   } private ImmutableSortedMultiset<E> createSubMultiset(int newFromIndex, int newToIndex) {
/* 189 */     if (newFromIndex == 0 && newToIndex == this.entries.size())
/* 190 */       return this; 
/* 191 */     if (newFromIndex >= newToIndex) {
/* 192 */       return emptyMultiset(comparator());
/*     */     }
/* 194 */     return new RegularImmutableSortedMultiset(comparator(), this.entries.subList(newFromIndex, newToIndex));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\RegularImmutableSortedMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */