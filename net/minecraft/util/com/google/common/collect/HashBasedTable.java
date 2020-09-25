/*     */ package net.minecraft.util.com.google.common.collect;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*     */ import net.minecraft.util.com.google.common.base.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  61 */       this.expectedSize = expectedSize;
/*     */     }
/*     */     
/*     */     public Map<C, V> get() {
/*  65 */       return Maps.newHashMapWithExpectedSize(this.expectedSize);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <R, C, V> HashBasedTable<R, C, V> create() {
/*  74 */     return new HashBasedTable<R, C, V>(new HashMap<R, Map<C, V>>(), new Factory<C, V>(0));
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
/*  89 */     CollectPreconditions.checkNonnegative(expectedCellsPerRow, "expectedCellsPerRow");
/*  90 */     Map<R, Map<C, V>> backingMap = Maps.newHashMapWithExpectedSize(expectedRows);
/*     */     
/*  92 */     return new HashBasedTable<R, C, V>(backingMap, new Factory<C, V>(expectedCellsPerRow));
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
/* 106 */     HashBasedTable<R, C, V> result = create();
/* 107 */     result.putAll(table);
/* 108 */     return result;
/*     */   }
/*     */   
/*     */   HashBasedTable(Map<R, Map<C, V>> backingMap, Factory<C, V> factory) {
/* 112 */     super(backingMap, factory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
/* 119 */     return super.contains(rowKey, columnKey);
/*     */   }
/*     */   
/*     */   public boolean containsColumn(@Nullable Object columnKey) {
/* 123 */     return super.containsColumn(columnKey);
/*     */   }
/*     */   
/*     */   public boolean containsRow(@Nullable Object rowKey) {
/* 127 */     return super.containsRow(rowKey);
/*     */   }
/*     */   
/*     */   public boolean containsValue(@Nullable Object value) {
/* 131 */     return super.containsValue(value);
/*     */   }
/*     */   
/*     */   public V get(@Nullable Object rowKey, @Nullable Object columnKey) {
/* 135 */     return super.get(rowKey, columnKey);
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object obj) {
/* 139 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(@Nullable Object rowKey, @Nullable Object columnKey) {
/* 144 */     return super.remove(rowKey, columnKey);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\HashBasedTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */