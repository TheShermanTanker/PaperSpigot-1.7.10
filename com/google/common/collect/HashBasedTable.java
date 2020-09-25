/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Supplier;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
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
/*     */ @GwtCompatible(serializable = true)
/*     */ @Beta
/*     */ public class HashBasedTable<R, C, V>
/*     */   extends StandardTable<R, C, V>
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   private static class Factory<C, V>
/*     */     implements Supplier<Map<C, V>>, Serializable
/*     */   {
/*     */     final int expectedSize;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     Factory(int expectedSize) {
/*  59 */       this.expectedSize = expectedSize;
/*     */     }
/*     */     
/*     */     public Map<C, V> get() {
/*  63 */       return Maps.newHashMapWithExpectedSize(this.expectedSize);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <R, C, V> HashBasedTable<R, C, V> create() {
/*  72 */     return new HashBasedTable<R, C, V>(new HashMap<R, Map<C, V>>(), new Factory<C, V>(0));
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
/*     */   public static <R, C, V> HashBasedTable<R, C, V> create(int expectedRows, int expectedCellsPerRow) {
/*  87 */     Preconditions.checkArgument((expectedCellsPerRow >= 0));
/*  88 */     Map<R, Map<C, V>> backingMap = Maps.newHashMapWithExpectedSize(expectedRows);
/*     */     
/*  90 */     return new HashBasedTable<R, C, V>(backingMap, new Factory<C, V>(expectedCellsPerRow));
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
/*     */   public static <R, C, V> HashBasedTable<R, C, V> create(Table<? extends R, ? extends C, ? extends V> table) {
/* 104 */     HashBasedTable<R, C, V> result = create();
/* 105 */     result.putAll(table);
/* 106 */     return result;
/*     */   }
/*     */   
/*     */   HashBasedTable(Map<R, Map<C, V>> backingMap, Factory<C, V> factory) {
/* 110 */     super(backingMap, factory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
/* 117 */     return super.contains(rowKey, columnKey);
/*     */   }
/*     */   
/*     */   public boolean containsColumn(@Nullable Object columnKey) {
/* 121 */     return super.containsColumn(columnKey);
/*     */   }
/*     */   
/*     */   public boolean containsRow(@Nullable Object rowKey) {
/* 125 */     return super.containsRow(rowKey);
/*     */   }
/*     */   
/*     */   public boolean containsValue(@Nullable Object value) {
/* 129 */     return super.containsValue(value);
/*     */   }
/*     */   
/*     */   public V get(@Nullable Object rowKey, @Nullable Object columnKey) {
/* 133 */     return super.get(rowKey, columnKey);
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object obj) {
/* 137 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(@Nullable Object rowKey, @Nullable Object columnKey) {
/* 142 */     return super.remove(rowKey, columnKey);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\HashBasedTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */