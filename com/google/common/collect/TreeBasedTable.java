/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Supplier;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeMap;
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
/*     */ @GwtCompatible(serializable = true)
/*     */ @Beta
/*     */ public class TreeBasedTable<R, C, V>
/*     */   extends StandardRowSortedTable<R, C, V>
/*     */ {
/*     */   private final Comparator<? super C> columnComparator;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   private static class Factory<C, V>
/*     */     implements Supplier<TreeMap<C, V>>, Serializable
/*     */   {
/*     */     final Comparator<? super C> comparator;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     Factory(Comparator<? super C> comparator) {
/*  84 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     public TreeMap<C, V> get() {
/*  88 */       return new TreeMap<C, V>(this.comparator);
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
/*     */ 
/*     */   
/*     */   public static <R extends Comparable, C extends Comparable, V> TreeBasedTable<R, C, V> create() {
/* 104 */     return new TreeBasedTable<R, C, V>(Ordering.natural(), Ordering.natural());
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
/*     */   public static <R, C, V> TreeBasedTable<R, C, V> create(Comparator<? super R> rowComparator, Comparator<? super C> columnComparator) {
/* 118 */     Preconditions.checkNotNull(rowComparator);
/* 119 */     Preconditions.checkNotNull(columnComparator);
/* 120 */     return new TreeBasedTable<R, C, V>(rowComparator, columnComparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <R, C, V> TreeBasedTable<R, C, V> create(TreeBasedTable<R, C, ? extends V> table) {
/* 129 */     TreeBasedTable<R, C, V> result = new TreeBasedTable<R, C, V>(table.rowComparator(), table.columnComparator());
/*     */ 
/*     */     
/* 132 */     result.putAll(table);
/* 133 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   TreeBasedTable(Comparator<? super R> rowComparator, Comparator<? super C> columnComparator) {
/* 138 */     super(new TreeMap<R, Map<C, V>>(rowComparator), (Supplier)new Factory<C, Object>(columnComparator));
/*     */     
/* 140 */     this.columnComparator = columnComparator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparator<? super R> rowComparator() {
/* 150 */     return rowKeySet().comparator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparator<? super C> columnComparator() {
/* 158 */     return this.columnComparator;
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
/*     */   public SortedMap<C, V> row(R rowKey) {
/* 175 */     return new TreeRow(rowKey);
/*     */   }
/*     */   
/*     */   private class TreeRow extends StandardTable<R, C, V>.Row implements SortedMap<C, V> {
/*     */     @Nullable
/*     */     final C lowerBound;
/*     */     
/*     */     TreeRow(R rowKey) {
/* 183 */       this(rowKey, null, null);
/*     */     } @Nullable
/*     */     final C upperBound; transient SortedMap<C, V> wholeRow;
/*     */     TreeRow(@Nullable R rowKey, @Nullable C lowerBound, C upperBound) {
/* 187 */       super(rowKey);
/* 188 */       this.lowerBound = lowerBound;
/* 189 */       this.upperBound = upperBound;
/* 190 */       Preconditions.checkArgument((lowerBound == null || upperBound == null || compare(lowerBound, upperBound) <= 0));
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator<? super C> comparator() {
/* 195 */       return TreeBasedTable.this.columnComparator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     int compare(Object a, Object b) {
/* 201 */       Comparator<Object> cmp = (Comparator)comparator();
/* 202 */       return cmp.compare(a, b);
/*     */     }
/*     */     
/*     */     boolean rangeContains(@Nullable Object o) {
/* 206 */       return (o != null && (this.lowerBound == null || compare(this.lowerBound, o) <= 0) && (this.upperBound == null || compare(this.upperBound, o) > 0));
/*     */     }
/*     */ 
/*     */     
/*     */     public SortedMap<C, V> subMap(C fromKey, C toKey) {
/* 211 */       Preconditions.checkArgument((rangeContains(Preconditions.checkNotNull(fromKey)) && rangeContains(Preconditions.checkNotNull(toKey))));
/*     */       
/* 213 */       return new TreeRow(this.rowKey, fromKey, toKey);
/*     */     }
/*     */     
/*     */     public SortedMap<C, V> headMap(C toKey) {
/* 217 */       Preconditions.checkArgument(rangeContains(Preconditions.checkNotNull(toKey)));
/* 218 */       return new TreeRow(this.rowKey, this.lowerBound, toKey);
/*     */     }
/*     */     
/*     */     public SortedMap<C, V> tailMap(C fromKey) {
/* 222 */       Preconditions.checkArgument(rangeContains(Preconditions.checkNotNull(fromKey)));
/* 223 */       return new TreeRow(this.rowKey, fromKey, this.upperBound);
/*     */     }
/*     */     
/*     */     public C firstKey() {
/* 227 */       SortedMap<C, V> backing = backingRowMap();
/* 228 */       if (backing == null) {
/* 229 */         throw new NoSuchElementException();
/*     */       }
/* 231 */       return backingRowMap().firstKey();
/*     */     }
/*     */     
/*     */     public C lastKey() {
/* 235 */       SortedMap<C, V> backing = backingRowMap();
/* 236 */       if (backing == null) {
/* 237 */         throw new NoSuchElementException();
/*     */       }
/* 239 */       return backingRowMap().lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SortedMap<C, V> wholeRow() {
/* 249 */       if (this.wholeRow == null || (this.wholeRow.isEmpty() && TreeBasedTable.this.backingMap.containsKey(this.rowKey)))
/*     */       {
/* 251 */         this.wholeRow = (SortedMap<C, V>)TreeBasedTable.this.backingMap.get(this.rowKey);
/*     */       }
/* 253 */       return this.wholeRow;
/*     */     }
/*     */ 
/*     */     
/*     */     SortedMap<C, V> backingRowMap() {
/* 258 */       return (SortedMap<C, V>)super.backingRowMap();
/*     */     }
/*     */ 
/*     */     
/*     */     SortedMap<C, V> computeBackingRowMap() {
/* 263 */       SortedMap<C, V> map = wholeRow();
/* 264 */       if (map != null) {
/* 265 */         if (this.lowerBound != null) {
/* 266 */           map = map.tailMap(this.lowerBound);
/*     */         }
/* 268 */         if (this.upperBound != null) {
/* 269 */           map = map.headMap(this.upperBound);
/*     */         }
/* 271 */         return map;
/*     */       } 
/* 273 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     void maintainEmptyInvariant() {
/* 278 */       if (wholeRow() != null && this.wholeRow.isEmpty()) {
/* 279 */         TreeBasedTable.this.backingMap.remove(this.rowKey);
/* 280 */         this.wholeRow = null;
/* 281 */         this.backingRowMap = null;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object key) {
/* 286 */       return (rangeContains(key) && super.containsKey(key));
/*     */     }
/*     */     
/*     */     public V put(C key, V value) {
/* 290 */       Preconditions.checkArgument(rangeContains(Preconditions.checkNotNull(key)));
/* 291 */       return super.put(key, value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SortedSet<R> rowKeySet() {
/* 298 */     return super.rowKeySet();
/*     */   }
/*     */   
/*     */   public SortedMap<R, Map<C, V>> rowMap() {
/* 302 */     return super.rowMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
/* 309 */     return super.contains(rowKey, columnKey);
/*     */   }
/*     */   
/*     */   public boolean containsColumn(@Nullable Object columnKey) {
/* 313 */     return super.containsColumn(columnKey);
/*     */   }
/*     */   
/*     */   public boolean containsRow(@Nullable Object rowKey) {
/* 317 */     return super.containsRow(rowKey);
/*     */   }
/*     */   
/*     */   public boolean containsValue(@Nullable Object value) {
/* 321 */     return super.containsValue(value);
/*     */   }
/*     */   
/*     */   public V get(@Nullable Object rowKey, @Nullable Object columnKey) {
/* 325 */     return super.get(rowKey, columnKey);
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object obj) {
/* 329 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(@Nullable Object rowKey, @Nullable Object columnKey) {
/* 334 */     return super.remove(rowKey, columnKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Iterator<C> createColumnKeyIterator() {
/* 342 */     return new MergingIterator<C>(Iterables.transform(this.backingMap.values(), new Function<Map<C, V>, Iterator<C>>()
/*     */           {
/*     */             
/*     */             public Iterator<C> apply(Map<C, V> input)
/*     */             {
/* 347 */               return input.keySet().iterator();
/*     */             }
/*     */           }), columnComparator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class MergingIterator<T>
/*     */     extends AbstractIterator<T>
/*     */   {
/*     */     private final Queue<PeekingIterator<T>> queue;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Comparator<? super T> comparator;
/*     */ 
/*     */ 
/*     */     
/* 368 */     private T lastValue = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MergingIterator(Iterable<? extends Iterator<T>> iterators, Comparator<? super T> itemComparator) {
/* 375 */       this.comparator = itemComparator;
/*     */ 
/*     */ 
/*     */       
/* 379 */       Comparator<PeekingIterator<T>> heapComparator = (Comparator)new Comparator<PeekingIterator<PeekingIterator<T>>>()
/*     */         {
/*     */           public int compare(PeekingIterator<T> o1, PeekingIterator<T> o2)
/*     */           {
/* 383 */             return TreeBasedTable.MergingIterator.this.comparator.compare(o1.peek(), o2.peek());
/*     */           }
/*     */         };
/*     */ 
/*     */ 
/*     */       
/* 389 */       this.queue = new PriorityQueue<PeekingIterator<T>>(Math.max(1, Iterables.size(iterators)), heapComparator);
/*     */       
/* 391 */       for (Iterator<T> iterator : iterators) {
/* 392 */         if (iterator.hasNext()) {
/* 393 */           this.queue.add(Iterators.peekingIterator(iterator));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     protected T computeNext() {
/* 399 */       while (!this.queue.isEmpty()) {
/* 400 */         PeekingIterator<T> nextIter = this.queue.poll();
/*     */         
/* 402 */         T next = nextIter.next();
/* 403 */         boolean duplicate = (this.lastValue != null && this.comparator.compare(next, this.lastValue) == 0);
/*     */ 
/*     */ 
/*     */         
/* 407 */         if (nextIter.hasNext()) {
/* 408 */           this.queue.add(nextIter);
/*     */         }
/*     */         
/* 411 */         if (!duplicate) {
/* 412 */           this.lastValue = next;
/* 413 */           return this.lastValue;
/*     */         } 
/*     */       } 
/*     */       
/* 417 */       this.lastValue = null;
/* 418 */       return endOfData();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\TreeBasedTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */