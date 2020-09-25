/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
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
/*     */ final class ImmutableSortedAsList<E>
/*     */   extends ImmutableList<E>
/*     */   implements SortedIterable<E>
/*     */ {
/*     */   private final transient ImmutableSortedSet<E> backingSet;
/*     */   private final transient ImmutableList<E> backingList;
/*     */   
/*     */   ImmutableSortedAsList(ImmutableSortedSet<E> backingSet, ImmutableList<E> backingList) {
/*  36 */     this.backingSet = backingSet;
/*  37 */     this.backingList = backingList;
/*     */   }
/*     */   
/*     */   public Comparator<? super E> comparator() {
/*  41 */     return this.backingSet.comparator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(@Nullable Object target) {
/*  48 */     return (this.backingSet.indexOf(target) >= 0);
/*     */   }
/*     */   
/*     */   public int indexOf(@Nullable Object target) {
/*  52 */     return this.backingSet.indexOf(target);
/*     */   }
/*     */   
/*     */   public int lastIndexOf(@Nullable Object target) {
/*  56 */     return this.backingSet.indexOf(target);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableList<E> subList(int fromIndex, int toIndex) {
/*  62 */     Preconditions.checkPositionIndexes(fromIndex, toIndex, size());
/*  63 */     return (fromIndex == toIndex) ? ImmutableList.<E>of() : (new RegularImmutableSortedSet<E>(this.backingList.subList(fromIndex, toIndex), this.backingSet.comparator())).asList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object writeReplace() {
/*  71 */     return new ImmutableAsList.SerializedForm(this.backingSet);
/*     */   }
/*     */   
/*     */   public UnmodifiableIterator<E> iterator() {
/*  75 */     return this.backingList.iterator();
/*     */   }
/*     */   
/*     */   public E get(int index) {
/*  79 */     return this.backingList.get(index);
/*     */   }
/*     */   
/*     */   public UnmodifiableListIterator<E> listIterator() {
/*  83 */     return this.backingList.listIterator();
/*     */   }
/*     */   
/*     */   public UnmodifiableListIterator<E> listIterator(int index) {
/*  87 */     return this.backingList.listIterator(index);
/*     */   }
/*     */   
/*     */   public int size() {
/*  91 */     return this.backingList.size();
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object obj) {
/*  95 */     return this.backingList.equals(obj);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  99 */     return this.backingList.hashCode();
/*     */   }
/*     */   
/*     */   boolean isPartialView() {
/* 103 */     return this.backingList.isPartialView();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ImmutableSortedAsList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */