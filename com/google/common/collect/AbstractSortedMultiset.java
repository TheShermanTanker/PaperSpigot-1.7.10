/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
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
/*     */ @GwtCompatible
/*     */ abstract class AbstractSortedMultiset<E>
/*     */   extends AbstractMultiset<E>
/*     */   implements SortedMultiset<E>
/*     */ {
/*     */   final Comparator<? super E> comparator;
/*     */   private transient SortedMultiset<E> descendingMultiset;
/*     */   
/*     */   AbstractSortedMultiset(Comparator<? super E> comparator) {
/*  39 */     this.comparator = (Comparator<? super E>)Preconditions.checkNotNull(comparator);
/*     */   }
/*     */ 
/*     */   
/*     */   public SortedSet<E> elementSet() {
/*  44 */     return (SortedSet<E>)super.elementSet();
/*     */   }
/*     */ 
/*     */   
/*     */   SortedSet<E> createElementSet() {
/*  49 */     return new SortedMultisets.ElementSet<E>()
/*     */       {
/*     */         SortedMultiset<E> multiset() {
/*  52 */           return AbstractSortedMultiset.this;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public Comparator<? super E> comparator() {
/*  59 */     return this.comparator;
/*     */   }
/*     */ 
/*     */   
/*     */   public Multiset.Entry<E> firstEntry() {
/*  64 */     Iterator<Multiset.Entry<E>> entryIterator = entryIterator();
/*  65 */     return entryIterator.hasNext() ? entryIterator.next() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Multiset.Entry<E> lastEntry() {
/*  70 */     Iterator<Multiset.Entry<E>> entryIterator = descendingEntryIterator();
/*  71 */     return entryIterator.hasNext() ? entryIterator.next() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Multiset.Entry<E> pollFirstEntry() {
/*  76 */     Iterator<Multiset.Entry<E>> entryIterator = entryIterator();
/*  77 */     if (entryIterator.hasNext()) {
/*  78 */       Multiset.Entry<E> result = entryIterator.next();
/*  79 */       result = Multisets.immutableEntry(result.getElement(), result.getCount());
/*  80 */       entryIterator.remove();
/*  81 */       return result;
/*     */     } 
/*  83 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Multiset.Entry<E> pollLastEntry() {
/*  88 */     Iterator<Multiset.Entry<E>> entryIterator = descendingEntryIterator();
/*  89 */     if (entryIterator.hasNext()) {
/*  90 */       Multiset.Entry<E> result = entryIterator.next();
/*  91 */       result = Multisets.immutableEntry(result.getElement(), result.getCount());
/*  92 */       entryIterator.remove();
/*  93 */       return result;
/*     */     } 
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SortedMultiset<E> subMultiset(E fromElement, BoundType fromBoundType, E toElement, BoundType toBoundType) {
/* 101 */     return tailMultiset(fromElement, fromBoundType).headMultiset(toElement, toBoundType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Iterator<E> descendingIterator() {
/* 107 */     return Multisets.iteratorImpl(descendingMultiset());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SortedMultiset<E> descendingMultiset() {
/* 114 */     SortedMultiset<E> result = this.descendingMultiset;
/* 115 */     return (result == null) ? (this.descendingMultiset = createDescendingMultiset()) : result;
/*     */   }
/*     */   
/*     */   SortedMultiset<E> createDescendingMultiset() {
/* 119 */     return new SortedMultisets.DescendingMultiset<E>()
/*     */       {
/*     */         SortedMultiset<E> forwardMultiset() {
/* 122 */           return AbstractSortedMultiset.this;
/*     */         }
/*     */ 
/*     */         
/*     */         Iterator<Multiset.Entry<E>> entryIterator() {
/* 127 */           return AbstractSortedMultiset.this.descendingEntryIterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public Iterator<E> iterator() {
/* 132 */           return AbstractSortedMultiset.this.descendingIterator();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   abstract Iterator<Multiset.Entry<E>> descendingEntryIterator();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\AbstractSortedMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */