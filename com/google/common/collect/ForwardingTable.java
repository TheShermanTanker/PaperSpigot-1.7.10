/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @GwtCompatible
/*     */ public abstract class ForwardingTable<R, C, V>
/*     */   extends ForwardingObject
/*     */   implements Table<R, C, V>
/*     */ {
/*     */   public Set<Table.Cell<R, C, V>> cellSet() {
/*  46 */     return delegate().cellSet();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  51 */     delegate().clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<R, V> column(C columnKey) {
/*  56 */     return delegate().column(columnKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<C> columnKeySet() {
/*  61 */     return delegate().columnKeySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<C, Map<R, V>> columnMap() {
/*  66 */     return delegate().columnMap();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object rowKey, Object columnKey) {
/*  71 */     return delegate().contains(rowKey, columnKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsColumn(Object columnKey) {
/*  76 */     return delegate().containsColumn(columnKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsRow(Object rowKey) {
/*  81 */     return delegate().containsRow(rowKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/*  86 */     return delegate().containsValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(Object rowKey, Object columnKey) {
/*  91 */     return delegate().get(rowKey, columnKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  96 */     return delegate().isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public V put(R rowKey, C columnKey, V value) {
/* 101 */     return delegate().put(rowKey, columnKey, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
/* 106 */     delegate().putAll(table);
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(Object rowKey, Object columnKey) {
/* 111 */     return delegate().remove(rowKey, columnKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<C, V> row(R rowKey) {
/* 116 */     return delegate().row(rowKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<R> rowKeySet() {
/* 121 */     return delegate().rowKeySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<R, Map<C, V>> rowMap() {
/* 126 */     return delegate().rowMap();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 131 */     return delegate().size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<V> values() {
/* 136 */     return delegate().values();
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 140 */     return (obj == this || delegate().equals(obj));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 144 */     return delegate().hashCode();
/*     */   }
/*     */   
/*     */   protected abstract Table<R, C, V> delegate();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ForwardingTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */