/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Supplier;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
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
/*     */ @GwtCompatible
/*     */ @Beta
/*     */ public final class Tables
/*     */ {
/*     */   public static <R, C, V> Table.Cell<R, C, V> immutableCell(@Nullable R rowKey, @Nullable C columnKey, @Nullable V value) {
/*  60 */     return new ImmutableCell<R, C, V>(rowKey, columnKey, value);
/*     */   }
/*     */   
/*     */   static final class ImmutableCell<R, C, V>
/*     */     extends AbstractCell<R, C, V> implements Serializable {
/*     */     private final R rowKey;
/*     */     private final C columnKey;
/*     */     private final V value;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     ImmutableCell(@Nullable R rowKey, @Nullable C columnKey, @Nullable V value) {
/*  71 */       this.rowKey = rowKey;
/*  72 */       this.columnKey = columnKey;
/*  73 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public R getRowKey() {
/*  78 */       return this.rowKey;
/*     */     }
/*     */     
/*     */     public C getColumnKey() {
/*  82 */       return this.columnKey;
/*     */     }
/*     */     
/*     */     public V getValue() {
/*  86 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class AbstractCell<R, C, V>
/*     */     implements Table.Cell<R, C, V>
/*     */   {
/*     */     public boolean equals(Object obj) {
/*  97 */       if (obj == this) {
/*  98 */         return true;
/*     */       }
/* 100 */       if (obj instanceof Table.Cell) {
/* 101 */         Table.Cell<?, ?, ?> other = (Table.Cell<?, ?, ?>)obj;
/* 102 */         return (Objects.equal(getRowKey(), other.getRowKey()) && Objects.equal(getColumnKey(), other.getColumnKey()) && Objects.equal(getValue(), other.getValue()));
/*     */       } 
/*     */ 
/*     */       
/* 106 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 110 */       return Objects.hashCode(new Object[] { getRowKey(), getColumnKey(), getValue() });
/*     */     }
/*     */     
/*     */     public String toString() {
/* 114 */       return "(" + getRowKey() + "," + getColumnKey() + ")=" + getValue();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static <R, C, V> Table<C, R, V> transpose(Table<R, C, V> table) {
/* 133 */     return (table instanceof TransposeTable) ? ((TransposeTable)table).original : new TransposeTable<C, R, V>(table);
/*     */   }
/*     */   
/*     */   private static class TransposeTable<C, R, V>
/*     */     implements Table<C, R, V>
/*     */   {
/*     */     final Table<R, C, V> original;
/*     */     
/*     */     TransposeTable(Table<R, C, V> original) {
/* 142 */       this.original = (Table<R, C, V>)Preconditions.checkNotNull(original);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 147 */       this.original.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<C, V> column(R columnKey) {
/* 152 */       return this.original.row(columnKey);
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<R> columnKeySet() {
/* 157 */       return this.original.rowKeySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<R, Map<C, V>> columnMap() {
/* 162 */       return this.original.rowMap();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
/* 168 */       return this.original.contains(columnKey, rowKey);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsColumn(@Nullable Object columnKey) {
/* 173 */       return this.original.containsRow(columnKey);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsRow(@Nullable Object rowKey) {
/* 178 */       return this.original.containsColumn(rowKey);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(@Nullable Object value) {
/* 183 */       return this.original.containsValue(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(@Nullable Object rowKey, @Nullable Object columnKey) {
/* 188 */       return this.original.get(columnKey, rowKey);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 193 */       return this.original.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public V put(C rowKey, R columnKey, V value) {
/* 198 */       return this.original.put(columnKey, rowKey, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Table<? extends C, ? extends R, ? extends V> table) {
/* 203 */       this.original.putAll(Tables.transpose(table));
/*     */     }
/*     */ 
/*     */     
/*     */     public V remove(@Nullable Object rowKey, @Nullable Object columnKey) {
/* 208 */       return this.original.remove(columnKey, rowKey);
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<R, V> row(C rowKey) {
/* 213 */       return this.original.column(rowKey);
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<C> rowKeySet() {
/* 218 */       return this.original.columnKeySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<C, Map<R, V>> rowMap() {
/* 223 */       return this.original.columnMap();
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 228 */       return this.original.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<V> values() {
/* 233 */       return this.original.values();
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 237 */       if (obj == this) {
/* 238 */         return true;
/*     */       }
/* 240 */       if (obj instanceof Table) {
/* 241 */         Table<?, ?, ?> other = (Table<?, ?, ?>)obj;
/* 242 */         return cellSet().equals(other.cellSet());
/*     */       } 
/* 244 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 248 */       return cellSet().hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 252 */       return rowMap().toString();
/*     */     }
/*     */ 
/*     */     
/* 256 */     private static final Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>> TRANSPOSE_CELL = new Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>>()
/*     */       {
/*     */         public Table.Cell<?, ?, ?> apply(Table.Cell<?, ?, ?> cell)
/*     */         {
/* 260 */           return Tables.immutableCell(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*     */     CellSet cellSet;
/*     */ 
/*     */     
/*     */     public Set<Table.Cell<C, R, V>> cellSet() {
/* 269 */       CellSet result = this.cellSet;
/* 270 */       return (result == null) ? (this.cellSet = new CellSet()) : result;
/*     */     }
/*     */     
/*     */     class CellSet
/*     */       extends Collections2.TransformedCollection<Table.Cell<R, C, V>, Table.Cell<C, R, V>>
/*     */       implements Set<Table.Cell<C, R, V>>
/*     */     {
/*     */       CellSet() {
/* 278 */         super(Tables.TransposeTable.this.original.cellSet(), (Function)Tables.TransposeTable.TRANSPOSE_CELL);
/*     */       }
/*     */       
/*     */       public boolean equals(Object obj) {
/* 282 */         if (obj == this) {
/* 283 */           return true;
/*     */         }
/* 285 */         if (!(obj instanceof Set)) {
/* 286 */           return false;
/*     */         }
/* 288 */         Set<?> os = (Set)obj;
/* 289 */         if (os.size() != size()) {
/* 290 */           return false;
/*     */         }
/* 292 */         return containsAll(os);
/*     */       }
/*     */       
/*     */       public int hashCode() {
/* 296 */         return Sets.hashCodeImpl(this);
/*     */       }
/*     */       
/*     */       public boolean contains(Object obj) {
/* 300 */         if (obj instanceof Table.Cell) {
/* 301 */           Table.Cell<?, ?, ?> cell = (Table.Cell<?, ?, ?>)obj;
/* 302 */           return Tables.TransposeTable.this.original.cellSet().contains(Tables.immutableCell(cell.getColumnKey(), cell.getRowKey(), cell.getValue()));
/*     */         } 
/*     */         
/* 305 */         return false;
/*     */       }
/*     */       
/*     */       public boolean remove(Object obj) {
/* 309 */         if (obj instanceof Table.Cell) {
/* 310 */           Table.Cell<?, ?, ?> cell = (Table.Cell<?, ?, ?>)obj;
/* 311 */           return Tables.TransposeTable.this.original.cellSet().remove(Tables.immutableCell(cell.getColumnKey(), cell.getRowKey(), cell.getValue()));
/*     */         } 
/*     */         
/* 314 */         return false;
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <R, C, V> Table<R, C, V> newCustomTable(Map<R, Map<C, V>> backingMap, Supplier<? extends Map<C, V>> factory) {
/* 362 */     Preconditions.checkArgument(backingMap.isEmpty());
/* 363 */     Preconditions.checkNotNull(factory);
/*     */     
/* 365 */     return new StandardTable<R, C, V>(backingMap, factory);
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
/*     */   public static <R, C, V1, V2> Table<R, C, V2> transformValues(Table<R, C, V1> fromTable, Function<? super V1, V2> function) {
/* 396 */     return new TransformedTable<R, C, V1, V2>(fromTable, function);
/*     */   }
/*     */   
/*     */   private static class TransformedTable<R, C, V1, V2>
/*     */     implements Table<R, C, V2> {
/*     */     final Table<R, C, V1> fromTable;
/*     */     final Function<? super V1, V2> function;
/*     */     CellSet cellSet;
/*     */     
/*     */     TransformedTable(Table<R, C, V1> fromTable, Function<? super V1, V2> function) {
/* 406 */       this.fromTable = (Table<R, C, V1>)Preconditions.checkNotNull(fromTable);
/* 407 */       this.function = (Function<? super V1, V2>)Preconditions.checkNotNull(function);
/*     */     }
/*     */     Collection<V2> values; Map<R, Map<C, V2>> rowMap; Map<C, Map<R, V2>> columnMap;
/*     */     public boolean contains(Object rowKey, Object columnKey) {
/* 411 */       return this.fromTable.contains(rowKey, columnKey);
/*     */     }
/*     */     
/*     */     public boolean containsRow(Object rowKey) {
/* 415 */       return this.fromTable.containsRow(rowKey);
/*     */     }
/*     */     
/*     */     public boolean containsColumn(Object columnKey) {
/* 419 */       return this.fromTable.containsColumn(columnKey);
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object value) {
/* 423 */       return values().contains(value);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public V2 get(Object rowKey, Object columnKey) {
/* 429 */       return contains(rowKey, columnKey) ? (V2)this.function.apply(this.fromTable.get(rowKey, columnKey)) : null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 434 */       return this.fromTable.isEmpty();
/*     */     }
/*     */     
/*     */     public int size() {
/* 438 */       return this.fromTable.size();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 442 */       this.fromTable.clear();
/*     */     }
/*     */     
/*     */     public V2 put(R rowKey, C columnKey, V2 value) {
/* 446 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Table<? extends R, ? extends C, ? extends V2> table) {
/* 451 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V2 remove(Object rowKey, Object columnKey) {
/* 455 */       return contains(rowKey, columnKey) ? (V2)this.function.apply(this.fromTable.remove(rowKey, columnKey)) : null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<C, V2> row(R rowKey) {
/* 460 */       return Maps.transformValues(this.fromTable.row(rowKey), this.function);
/*     */     }
/*     */     
/*     */     public Map<R, V2> column(C columnKey) {
/* 464 */       return Maps.transformValues(this.fromTable.column(columnKey), this.function);
/*     */     }
/*     */     
/*     */     Function<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>> cellFunction() {
/* 468 */       return new Function<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>>() {
/*     */           public Table.Cell<R, C, V2> apply(Table.Cell<R, C, V1> cell) {
/* 470 */             return Tables.immutableCell(cell.getRowKey(), cell.getColumnKey(), (V2)Tables.TransformedTable.this.function.apply(cell.getValue()));
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     class CellSet
/*     */       extends Collections2.TransformedCollection<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>>
/*     */       implements Set<Table.Cell<R, C, V2>>
/*     */     {
/*     */       CellSet() {
/* 480 */         super(Tables.TransformedTable.this.fromTable.cellSet(), Tables.TransformedTable.this.cellFunction());
/*     */       }
/*     */       public boolean equals(Object obj) {
/* 483 */         return Sets.equalsImpl(this, obj);
/*     */       }
/*     */       public int hashCode() {
/* 486 */         return Sets.hashCodeImpl(this);
/*     */       }
/*     */       public boolean contains(Object obj) {
/* 489 */         if (obj instanceof Table.Cell) {
/* 490 */           Table.Cell<?, ?, ?> cell = (Table.Cell<?, ?, ?>)obj;
/* 491 */           if (!Objects.equal(cell.getValue(), Tables.TransformedTable.this.get(cell.getRowKey(), cell.getColumnKey())))
/*     */           {
/* 493 */             return false;
/*     */           }
/* 495 */           return (cell.getValue() != null || Tables.TransformedTable.this.fromTable.contains(cell.getRowKey(), cell.getColumnKey()));
/*     */         } 
/*     */         
/* 498 */         return false;
/*     */       }
/*     */       public boolean remove(Object obj) {
/* 501 */         if (contains(obj)) {
/* 502 */           Table.Cell<?, ?, ?> cell = (Table.Cell<?, ?, ?>)obj;
/* 503 */           Tables.TransformedTable.this.fromTable.remove(cell.getRowKey(), cell.getColumnKey());
/* 504 */           return true;
/*     */         } 
/* 506 */         return false;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Table.Cell<R, C, V2>> cellSet() {
/* 513 */       return (this.cellSet == null) ? (this.cellSet = new CellSet()) : this.cellSet;
/*     */     }
/*     */     
/*     */     public Set<R> rowKeySet() {
/* 517 */       return this.fromTable.rowKeySet();
/*     */     }
/*     */     
/*     */     public Set<C> columnKeySet() {
/* 521 */       return this.fromTable.columnKeySet();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Collection<V2> values() {
/* 527 */       return (this.values == null) ? (this.values = Collections2.transform(this.fromTable.values(), this.function)) : this.values;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Map<R, Map<C, V2>> createRowMap() {
/* 533 */       Function<Map<C, V1>, Map<C, V2>> rowFunction = new Function<Map<C, V1>, Map<C, V2>>()
/*     */         {
/*     */           public Map<C, V2> apply(Map<C, V1> row) {
/* 536 */             return Maps.transformValues(row, Tables.TransformedTable.this.function);
/*     */           }
/*     */         };
/* 539 */       return Maps.transformValues(this.fromTable.rowMap(), rowFunction);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<R, Map<C, V2>> rowMap() {
/* 545 */       return (this.rowMap == null) ? (this.rowMap = createRowMap()) : this.rowMap;
/*     */     }
/*     */     
/*     */     Map<C, Map<R, V2>> createColumnMap() {
/* 549 */       Function<Map<R, V1>, Map<R, V2>> columnFunction = new Function<Map<R, V1>, Map<R, V2>>()
/*     */         {
/*     */           public Map<R, V2> apply(Map<R, V1> column) {
/* 552 */             return Maps.transformValues(column, Tables.TransformedTable.this.function);
/*     */           }
/*     */         };
/* 555 */       return Maps.transformValues(this.fromTable.columnMap(), columnFunction);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<C, Map<R, V2>> columnMap() {
/* 561 */       return (this.columnMap == null) ? (this.columnMap = createColumnMap()) : this.columnMap;
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 565 */       if (obj == this) {
/* 566 */         return true;
/*     */       }
/* 568 */       if (obj instanceof Table) {
/* 569 */         Table<?, ?, ?> other = (Table<?, ?, ?>)obj;
/* 570 */         return cellSet().equals(other.cellSet());
/*     */       } 
/* 572 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 576 */       return cellSet().hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 580 */       return rowMap().toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Tables.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */