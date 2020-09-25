/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ @Beta
/*     */ public final class ArrayTable<R, C, V>
/*     */   implements Table<R, C, V>, Serializable
/*     */ {
/*     */   private final ImmutableList<R> rowList;
/*     */   private final ImmutableList<C> columnList;
/*     */   private final ImmutableMap<R, Integer> rowKeyToIndex;
/*     */   private final ImmutableMap<C, Integer> columnKeyToIndex;
/*     */   private final V[][] array;
/*     */   private transient CellSet cellSet;
/*     */   private transient ColumnMap columnMap;
/*     */   private transient RowMap rowMap;
/*     */   private transient Collection<V> values;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   public static <R, C, V> ArrayTable<R, C, V> create(Iterable<? extends R> rowKeys, Iterable<? extends C> columnKeys) {
/*  94 */     return new ArrayTable<R, C, V>(rowKeys, columnKeys);
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
/*     */   public static <R, C, V> ArrayTable<R, C, V> create(Table<R, C, V> table) {
/* 126 */     return new ArrayTable<R, C, V>(table);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <R, C, V> ArrayTable<R, C, V> create(ArrayTable<R, C, V> table) {
/* 135 */     return new ArrayTable<R, C, V>(table);
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
/*     */   private ArrayTable(Iterable<? extends R> rowKeys, Iterable<? extends C> columnKeys) {
/* 148 */     this.rowList = ImmutableList.copyOf(rowKeys);
/* 149 */     this.columnList = ImmutableList.copyOf(columnKeys);
/* 150 */     Preconditions.checkArgument(!this.rowList.isEmpty());
/* 151 */     Preconditions.checkArgument(!this.columnList.isEmpty());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     ImmutableMap.Builder<R, Integer> rowBuilder = ImmutableMap.builder();
/* 159 */     for (int i = 0; i < this.rowList.size(); i++) {
/* 160 */       rowBuilder.put(this.rowList.get(i), Integer.valueOf(i));
/*     */     }
/* 162 */     this.rowKeyToIndex = rowBuilder.build();
/*     */     
/* 164 */     ImmutableMap.Builder<C, Integer> columnBuilder = ImmutableMap.builder();
/* 165 */     for (int j = 0; j < this.columnList.size(); j++) {
/* 166 */       columnBuilder.put(this.columnList.get(j), Integer.valueOf(j));
/*     */     }
/* 168 */     this.columnKeyToIndex = columnBuilder.build();
/*     */ 
/*     */     
/* 171 */     V[][] tmpArray = (V[][])new Object[this.rowList.size()][this.columnList.size()];
/*     */     
/* 173 */     this.array = tmpArray;
/*     */   }
/*     */   
/*     */   private ArrayTable(Table<R, C, V> table) {
/* 177 */     this(table.rowKeySet(), table.columnKeySet());
/* 178 */     putAll(table);
/*     */   }
/*     */   
/*     */   private ArrayTable(ArrayTable<R, C, V> table) {
/* 182 */     this.rowList = table.rowList;
/* 183 */     this.columnList = table.columnList;
/* 184 */     this.rowKeyToIndex = table.rowKeyToIndex;
/* 185 */     this.columnKeyToIndex = table.columnKeyToIndex;
/*     */     
/* 187 */     V[][] copy = (V[][])new Object[this.rowList.size()][this.columnList.size()];
/* 188 */     this.array = copy;
/* 189 */     for (int i = 0; i < this.rowList.size(); i++) {
/* 190 */       System.arraycopy(table.array[i], 0, copy[i], 0, (table.array[i]).length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableList<R> rowKeyList() {
/* 199 */     return this.rowList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableList<C> columnKeyList() {
/* 207 */     return this.columnList;
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
/*     */   public V at(int rowIndex, int columnIndex) {
/* 225 */     return this.array[rowIndex][columnIndex];
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
/*     */   public V set(int rowIndex, int columnIndex, @Nullable V value) {
/* 244 */     V oldValue = this.array[rowIndex][columnIndex];
/* 245 */     this.array[rowIndex][columnIndex] = value;
/* 246 */     return oldValue;
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
/*     */   public V[][] toArray(Class<V> valueClass) {
/* 262 */     V[][] copy = (V[][])Array.newInstance(valueClass, new int[] { this.rowList.size(), this.columnList.size() });
/*     */     
/* 264 */     for (int i = 0; i < this.rowList.size(); i++) {
/* 265 */       System.arraycopy(this.array[i], 0, copy[i], 0, (this.array[i]).length);
/*     */     }
/* 267 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void clear() {
/* 278 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void eraseAll() {
/* 286 */     for (V[] row : this.array) {
/* 287 */       Arrays.fill((Object[])row, (Object)null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
/* 297 */     return (containsRow(rowKey) && containsColumn(columnKey));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsColumn(@Nullable Object columnKey) {
/* 306 */     return this.columnKeyToIndex.containsKey(columnKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsRow(@Nullable Object rowKey) {
/* 315 */     return this.rowKeyToIndex.containsKey(rowKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(@Nullable Object value) {
/* 320 */     for (V[] row : this.array) {
/* 321 */       for (V element : row) {
/* 322 */         if (Objects.equal(value, element)) {
/* 323 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 327 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(@Nullable Object rowKey, @Nullable Object columnKey) {
/* 332 */     Integer rowIndex = this.rowKeyToIndex.get(rowKey);
/* 333 */     Integer columnIndex = this.columnKeyToIndex.get(columnKey);
/* 334 */     return getIndexed(rowIndex, columnIndex);
/*     */   }
/*     */   
/*     */   private V getIndexed(Integer rowIndex, Integer columnIndex) {
/* 338 */     return (rowIndex == null || columnIndex == null) ? null : this.array[rowIndex.intValue()][columnIndex.intValue()];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 347 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V put(R rowKey, C columnKey, @Nullable V value) {
/* 358 */     Preconditions.checkNotNull(rowKey);
/* 359 */     Preconditions.checkNotNull(columnKey);
/* 360 */     Integer rowIndex = this.rowKeyToIndex.get(rowKey);
/* 361 */     Preconditions.checkArgument((rowIndex != null), "Row %s not in %s", new Object[] { rowKey, this.rowList });
/* 362 */     Integer columnIndex = this.columnKeyToIndex.get(columnKey);
/* 363 */     Preconditions.checkArgument((columnIndex != null), "Column %s not in %s", new Object[] { columnKey, this.columnList });
/*     */     
/* 365 */     return set(rowIndex.intValue(), columnIndex.intValue(), value);
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
/*     */   public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
/* 386 */     for (Table.Cell<? extends R, ? extends C, ? extends V> cell : table.cellSet()) {
/* 387 */       put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public V remove(Object rowKey, Object columnKey) {
/* 399 */     throw new UnsupportedOperationException();
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
/*     */   public V erase(@Nullable Object rowKey, @Nullable Object columnKey) {
/* 416 */     Integer rowIndex = this.rowKeyToIndex.get(rowKey);
/* 417 */     Integer columnIndex = this.columnKeyToIndex.get(columnKey);
/* 418 */     if (rowIndex == null || columnIndex == null) {
/* 419 */       return null;
/*     */     }
/* 421 */     return set(rowIndex.intValue(), columnIndex.intValue(), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 428 */     return this.rowList.size() * this.columnList.size();
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object obj) {
/* 432 */     if (obj instanceof Table) {
/* 433 */       Table<?, ?, ?> other = (Table<?, ?, ?>)obj;
/* 434 */       return cellSet().equals(other.cellSet());
/*     */     } 
/* 436 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 440 */     return cellSet().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 447 */     return rowMap().toString();
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
/*     */   public Set<Table.Cell<R, C, V>> cellSet() {
/* 467 */     CellSet set = this.cellSet;
/* 468 */     return (set == null) ? (this.cellSet = new CellSet()) : set;
/*     */   }
/*     */   
/*     */   private class CellSet extends AbstractSet<Table.Cell<R, C, V>> { private CellSet() {}
/*     */     
/*     */     public Iterator<Table.Cell<R, C, V>> iterator() {
/* 474 */       return new AbstractIndexedListIterator<Table.Cell<R, C, V>>(size()) {
/*     */           protected Table.Cell<R, C, V> get(final int index) {
/* 476 */             return new Tables.AbstractCell<R, C, V>() {
/* 477 */                 final int rowIndex = index / ArrayTable.this.columnList.size();
/* 478 */                 final int columnIndex = index % ArrayTable.this.columnList.size();
/*     */                 
/*     */                 public R getRowKey() {
/* 481 */                   return (R)ArrayTable.this.rowList.get(this.rowIndex);
/*     */                 }
/*     */                 
/*     */                 public C getColumnKey() {
/* 485 */                   return (C)ArrayTable.this.columnList.get(this.columnIndex);
/*     */                 }
/*     */                 
/*     */                 public V getValue() {
/* 489 */                   return (V)ArrayTable.this.array[this.rowIndex][this.columnIndex];
/*     */                 }
/*     */               };
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 497 */       return ArrayTable.this.size();
/*     */     }
/*     */     
/*     */     public boolean contains(Object obj) {
/* 501 */       if (obj instanceof Table.Cell) {
/* 502 */         Table.Cell<?, ?, ?> cell = (Table.Cell<?, ?, ?>)obj;
/* 503 */         Integer rowIndex = (Integer)ArrayTable.this.rowKeyToIndex.get(cell.getRowKey());
/* 504 */         Integer columnIndex = (Integer)ArrayTable.this.columnKeyToIndex.get(cell.getColumnKey());
/* 505 */         return (rowIndex != null && columnIndex != null && Objects.equal(ArrayTable.this.array[rowIndex.intValue()][columnIndex.intValue()], cell.getValue()));
/*     */       } 
/*     */ 
/*     */       
/* 509 */       return false;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<R, V> column(C columnKey) {
/* 527 */     Preconditions.checkNotNull(columnKey);
/* 528 */     Integer columnIndex = this.columnKeyToIndex.get(columnKey);
/* 529 */     return (columnIndex == null) ? ImmutableMap.<R, V>of() : new Column(columnIndex.intValue());
/*     */   }
/*     */   
/*     */   private class Column extends AbstractMap<R, V> {
/*     */     final int columnIndex;
/*     */     ArrayTable<R, C, V>.ColumnEntrySet entrySet;
/*     */     
/*     */     Column(int columnIndex) {
/* 537 */       this.columnIndex = columnIndex;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<R, V>> entrySet() {
/* 543 */       ArrayTable<R, C, V>.ColumnEntrySet set = this.entrySet;
/* 544 */       return (set == null) ? (this.entrySet = new ArrayTable.ColumnEntrySet(this.columnIndex)) : set;
/*     */     }
/*     */     
/*     */     public V get(Object rowKey) {
/* 548 */       Integer rowIndex = (Integer)ArrayTable.this.rowKeyToIndex.get(rowKey);
/* 549 */       return ArrayTable.this.getIndexed(rowIndex, Integer.valueOf(this.columnIndex));
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object rowKey) {
/* 553 */       return ArrayTable.this.rowKeyToIndex.containsKey(rowKey);
/*     */     }
/*     */     
/*     */     public V put(R rowKey, V value) {
/* 557 */       Preconditions.checkNotNull(rowKey);
/* 558 */       Integer rowIndex = (Integer)ArrayTable.this.rowKeyToIndex.get(rowKey);
/* 559 */       Preconditions.checkArgument((rowIndex != null), "Row %s not in %s", new Object[] { rowKey, ArrayTable.access$200(this.this$0) });
/* 560 */       return (V)ArrayTable.this.set(rowIndex.intValue(), this.columnIndex, value);
/*     */     }
/*     */     
/*     */     public Set<R> keySet() {
/* 564 */       return ArrayTable.this.rowKeySet();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ColumnEntrySet extends AbstractSet<Map.Entry<R, V>> {
/*     */     final int columnIndex;
/*     */     
/*     */     ColumnEntrySet(int columnIndex) {
/* 572 */       this.columnIndex = columnIndex;
/*     */     }
/*     */     
/*     */     public Iterator<Map.Entry<R, V>> iterator() {
/* 576 */       return new AbstractIndexedListIterator<Map.Entry<R, V>>(size()) {
/*     */           protected Map.Entry<R, V> get(final int rowIndex) {
/* 578 */             return new AbstractMapEntry<R, V>() {
/*     */                 public R getKey() {
/* 580 */                   return (R)ArrayTable.this.rowList.get(rowIndex);
/*     */                 }
/*     */                 public V getValue() {
/* 583 */                   return (V)ArrayTable.this.array[rowIndex][ArrayTable.ColumnEntrySet.this.columnIndex];
/*     */                 }
/*     */                 public V setValue(V value) {
/* 586 */                   return (V)ArrayTable.this.set(rowIndex, ArrayTable.ColumnEntrySet.this.columnIndex, value);
/*     */                 }
/*     */               };
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 594 */       return ArrayTable.this.rowList.size();
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
/*     */   public ImmutableSet<C> columnKeySet() {
/* 606 */     return this.columnKeyToIndex.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<C, Map<R, V>> columnMap() {
/* 613 */     ColumnMap map = this.columnMap;
/* 614 */     return (map == null) ? (this.columnMap = new ColumnMap()) : map;
/*     */   }
/*     */   
/*     */   private class ColumnMap extends AbstractMap<C, Map<R, V>> {
/*     */     transient ArrayTable<R, C, V>.ColumnMapEntrySet entrySet;
/*     */     
/*     */     public Set<Map.Entry<C, Map<R, V>>> entrySet() {
/* 621 */       ArrayTable<R, C, V>.ColumnMapEntrySet set = this.entrySet;
/* 622 */       return (set == null) ? (this.entrySet = new ArrayTable.ColumnMapEntrySet()) : set;
/*     */     }
/*     */     private ColumnMap() {}
/*     */     public Map<R, V> get(Object columnKey) {
/* 626 */       Integer columnIndex = (Integer)ArrayTable.this.columnKeyToIndex.get(columnKey);
/* 627 */       return (columnIndex == null) ? null : new ArrayTable.Column(columnIndex.intValue());
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object columnKey) {
/* 631 */       return ArrayTable.this.containsColumn(columnKey);
/*     */     }
/*     */     
/*     */     public Set<C> keySet() {
/* 635 */       return ArrayTable.this.columnKeySet();
/*     */     }
/*     */     
/*     */     public Map<R, V> remove(Object columnKey) {
/* 639 */       throw new UnsupportedOperationException();
/*     */     } }
/*     */   
/*     */   private class ColumnMapEntrySet extends AbstractSet<Map.Entry<C, Map<R, V>>> { private ColumnMapEntrySet() {}
/*     */     
/*     */     public Iterator<Map.Entry<C, Map<R, V>>> iterator() {
/* 645 */       return new AbstractIndexedListIterator<Map.Entry<C, Map<R, V>>>(size()) {
/*     */           protected Map.Entry<C, Map<R, V>> get(int index) {
/* 647 */             return Maps.immutableEntry((C)ArrayTable.this.columnList.get(index), new ArrayTable.Column(index));
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 654 */       return ArrayTable.this.columnList.size();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<C, V> row(R rowKey) {
/* 673 */     Preconditions.checkNotNull(rowKey);
/* 674 */     Integer rowIndex = this.rowKeyToIndex.get(rowKey);
/* 675 */     return (rowIndex == null) ? ImmutableMap.<C, V>of() : new Row(rowIndex.intValue());
/*     */   }
/*     */   
/*     */   private class Row extends AbstractMap<C, V> { final int rowIndex;
/*     */     ArrayTable<R, C, V>.RowEntrySet entrySet;
/*     */     
/*     */     Row(int rowIndex) {
/* 682 */       this.rowIndex = rowIndex;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<C, V>> entrySet() {
/* 688 */       ArrayTable<R, C, V>.RowEntrySet set = this.entrySet;
/* 689 */       return (set == null) ? (this.entrySet = new ArrayTable.RowEntrySet(this.rowIndex)) : set;
/*     */     }
/*     */     
/*     */     public V get(Object columnKey) {
/* 693 */       Integer columnIndex = (Integer)ArrayTable.this.columnKeyToIndex.get(columnKey);
/* 694 */       return ArrayTable.this.getIndexed(Integer.valueOf(this.rowIndex), columnIndex);
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object columnKey) {
/* 698 */       return ArrayTable.this.containsColumn(columnKey);
/*     */     }
/*     */     
/*     */     public V put(C columnKey, V value) {
/* 702 */       Preconditions.checkNotNull(columnKey);
/* 703 */       Integer columnIndex = (Integer)ArrayTable.this.columnKeyToIndex.get(columnKey);
/* 704 */       Preconditions.checkArgument((columnIndex != null), "Column %s not in %s", new Object[] { columnKey, ArrayTable.access$100(this.this$0) });
/*     */       
/* 706 */       return (V)ArrayTable.this.set(this.rowIndex, columnIndex.intValue(), value);
/*     */     }
/*     */     
/*     */     public Set<C> keySet() {
/* 710 */       return ArrayTable.this.columnKeySet();
/*     */     } }
/*     */ 
/*     */   
/*     */   private class RowEntrySet extends AbstractSet<Map.Entry<C, V>> {
/*     */     final int rowIndex;
/*     */     
/*     */     RowEntrySet(int rowIndex) {
/* 718 */       this.rowIndex = rowIndex;
/*     */     }
/*     */     
/*     */     public Iterator<Map.Entry<C, V>> iterator() {
/* 722 */       return new AbstractIndexedListIterator<Map.Entry<C, V>>(size()) {
/*     */           protected Map.Entry<C, V> get(final int columnIndex) {
/* 724 */             return new AbstractMapEntry<C, V>() {
/*     */                 public C getKey() {
/* 726 */                   return (C)ArrayTable.this.columnList.get(columnIndex);
/*     */                 }
/*     */                 public V getValue() {
/* 729 */                   return (V)ArrayTable.this.array[ArrayTable.RowEntrySet.this.rowIndex][columnIndex];
/*     */                 }
/*     */                 public V setValue(V value) {
/* 732 */                   return (V)ArrayTable.this.set(ArrayTable.RowEntrySet.this.rowIndex, columnIndex, value);
/*     */                 }
/*     */               };
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 740 */       return ArrayTable.this.columnList.size();
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
/*     */   public ImmutableSet<R> rowKeySet() {
/* 752 */     return this.rowKeyToIndex.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<R, Map<C, V>> rowMap() {
/* 759 */     RowMap map = this.rowMap;
/* 760 */     return (map == null) ? (this.rowMap = new RowMap()) : map;
/*     */   }
/*     */   private class RowMap extends AbstractMap<R, Map<C, V>> { transient ArrayTable<R, C, V>.RowMapEntrySet entrySet;
/*     */     
/*     */     private RowMap() {}
/*     */     
/*     */     public Set<Map.Entry<R, Map<C, V>>> entrySet() {
/* 767 */       ArrayTable<R, C, V>.RowMapEntrySet set = this.entrySet;
/* 768 */       return (set == null) ? (this.entrySet = new ArrayTable.RowMapEntrySet()) : set;
/*     */     }
/*     */     
/*     */     public Map<C, V> get(Object rowKey) {
/* 772 */       Integer rowIndex = (Integer)ArrayTable.this.rowKeyToIndex.get(rowKey);
/* 773 */       return (rowIndex == null) ? null : new ArrayTable.Row(rowIndex.intValue());
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object rowKey) {
/* 777 */       return ArrayTable.this.containsRow(rowKey);
/*     */     }
/*     */     
/*     */     public Set<R> keySet() {
/* 781 */       return ArrayTable.this.rowKeySet();
/*     */     }
/*     */     
/*     */     public Map<C, V> remove(Object rowKey) {
/* 785 */       throw new UnsupportedOperationException();
/*     */     } }
/*     */   
/*     */   private class RowMapEntrySet extends AbstractSet<Map.Entry<R, Map<C, V>>> { private RowMapEntrySet() {}
/*     */     
/*     */     public Iterator<Map.Entry<R, Map<C, V>>> iterator() {
/* 791 */       return new AbstractIndexedListIterator<Map.Entry<R, Map<C, V>>>(size()) {
/*     */           protected Map.Entry<R, Map<C, V>> get(int index) {
/* 793 */             return Maps.immutableEntry((R)ArrayTable.this.rowList.get(index), new ArrayTable.Row(index));
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 800 */       return ArrayTable.this.rowList.size();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<V> values() {
/* 817 */     Collection<V> v = this.values;
/* 818 */     return (v == null) ? (this.values = new Values()) : v;
/*     */   }
/*     */   private class Values extends AbstractCollection<V> { private Values() {}
/*     */     
/*     */     public Iterator<V> iterator() {
/* 823 */       return new AbstractIndexedListIterator<V>(size()) {
/*     */           protected V get(int index) {
/* 825 */             int rowIndex = index / ArrayTable.this.columnList.size();
/* 826 */             int columnIndex = index % ArrayTable.this.columnList.size();
/* 827 */             return (V)ArrayTable.this.array[rowIndex][columnIndex];
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 833 */       return ArrayTable.this.size();
/*     */     }
/*     */     
/*     */     public boolean contains(Object value) {
/* 837 */       return ArrayTable.this.containsValue(value);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ArrayTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */