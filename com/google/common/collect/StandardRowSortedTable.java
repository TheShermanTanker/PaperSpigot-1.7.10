/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Supplier;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
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
/*     */ @GwtCompatible
/*     */ class StandardRowSortedTable<R, C, V>
/*     */   extends StandardTable<R, C, V>
/*     */   implements RowSortedTable<R, C, V>
/*     */ {
/*     */   private transient SortedSet<R> rowKeySet;
/*     */   private transient RowSortedMap rowMap;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   StandardRowSortedTable(SortedMap<R, Map<C, V>> backingMap, Supplier<? extends Map<C, V>> factory) {
/*  59 */     super(backingMap, factory);
/*     */   }
/*     */   
/*     */   private SortedMap<R, Map<C, V>> sortedBackingMap() {
/*  63 */     return (SortedMap<R, Map<C, V>>)this.backingMap;
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
/*     */   public SortedSet<R> rowKeySet() {
/*  75 */     SortedSet<R> result = this.rowKeySet;
/*  76 */     return (result == null) ? (this.rowKeySet = new RowKeySortedSet()) : result;
/*     */   }
/*     */   
/*     */   private class RowKeySortedSet
/*     */     extends StandardTable<R, C, V>.RowKeySet implements SortedSet<R> {
/*     */     public Comparator<? super R> comparator() {
/*  82 */       return StandardRowSortedTable.this.sortedBackingMap().comparator();
/*     */     }
/*     */     private RowKeySortedSet() {}
/*     */     
/*     */     public R first() {
/*  87 */       return (R)StandardRowSortedTable.this.sortedBackingMap().firstKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public R last() {
/*  92 */       return (R)StandardRowSortedTable.this.sortedBackingMap().lastKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public SortedSet<R> headSet(R toElement) {
/*  97 */       Preconditions.checkNotNull(toElement);
/*  98 */       return (new StandardRowSortedTable<R, Object, Object>(StandardRowSortedTable.this.sortedBackingMap().headMap(toElement), StandardRowSortedTable.this.factory)).rowKeySet();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public SortedSet<R> subSet(R fromElement, R toElement) {
/* 104 */       Preconditions.checkNotNull(fromElement);
/* 105 */       Preconditions.checkNotNull(toElement);
/* 106 */       return (new StandardRowSortedTable<R, Object, Object>(StandardRowSortedTable.this.sortedBackingMap().subMap(fromElement, toElement), StandardRowSortedTable.this.factory)).rowKeySet();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SortedSet<R> tailSet(R fromElement) {
/* 113 */       Preconditions.checkNotNull(fromElement);
/* 114 */       return (new StandardRowSortedTable<R, Object, Object>(StandardRowSortedTable.this.sortedBackingMap().tailMap(fromElement), StandardRowSortedTable.this.factory)).rowKeySet();
/*     */     }
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
/*     */   public SortedMap<R, Map<C, V>> rowMap() {
/* 128 */     RowSortedMap result = this.rowMap;
/* 129 */     return (result == null) ? (this.rowMap = new RowSortedMap()) : result;
/*     */   }
/*     */   
/*     */   private class RowSortedMap extends StandardTable<R, C, V>.RowMap implements SortedMap<R, Map<C, V>> { private RowSortedMap() {}
/*     */     
/*     */     public Comparator<? super R> comparator() {
/* 135 */       return StandardRowSortedTable.this.sortedBackingMap().comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public R firstKey() {
/* 140 */       return (R)StandardRowSortedTable.this.sortedBackingMap().firstKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public R lastKey() {
/* 145 */       return (R)StandardRowSortedTable.this.sortedBackingMap().lastKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public SortedMap<R, Map<C, V>> headMap(R toKey) {
/* 150 */       Preconditions.checkNotNull(toKey);
/* 151 */       return (new StandardRowSortedTable<R, C, V>(StandardRowSortedTable.this.sortedBackingMap().headMap(toKey), StandardRowSortedTable.this.factory)).rowMap();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public SortedMap<R, Map<C, V>> subMap(R fromKey, R toKey) {
/* 157 */       Preconditions.checkNotNull(fromKey);
/* 158 */       Preconditions.checkNotNull(toKey);
/* 159 */       return (new StandardRowSortedTable<R, C, V>(StandardRowSortedTable.this.sortedBackingMap().subMap(fromKey, toKey), StandardRowSortedTable.this.factory)).rowMap();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public SortedMap<R, Map<C, V>> tailMap(R fromKey) {
/* 165 */       Preconditions.checkNotNull(fromKey);
/* 166 */       return (new StandardRowSortedTable<R, C, V>(StandardRowSortedTable.this.sortedBackingMap().tailMap(fromKey), StandardRowSortedTable.this.factory)).rowMap();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\StandardRowSortedTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */