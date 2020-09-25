/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Supplier;
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
/*     */ @GwtCompatible
/*     */ public abstract class ForwardingMap<K, V>
/*     */   extends ForwardingObject
/*     */   implements Map<K, V>
/*     */ {
/*     */   public int size() {
/*  71 */     return delegate().size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  76 */     return delegate().isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(Object object) {
/*  81 */     return delegate().remove(object);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  86 */     delegate().clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/*  91 */     return delegate().containsKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/*  96 */     return delegate().containsValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(Object key) {
/* 101 */     return delegate().get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public V put(K key, V value) {
/* 106 */     return delegate().put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends V> map) {
/* 111 */     delegate().putAll(map);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<K> keySet() {
/* 116 */     return delegate().keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<V> values() {
/* 121 */     return delegate().values();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<K, V>> entrySet() {
/* 126 */     return delegate().entrySet();
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/* 130 */     return (object == this || delegate().equals(object));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 134 */     return delegate().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected void standardPutAll(Map<? extends K, ? extends V> map) {
/* 146 */     Maps.putAllImpl(this, map);
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
/*     */   @Beta
/*     */   protected V standardRemove(@Nullable Object key) {
/* 162 */     Iterator<Map.Entry<K, V>> entryIterator = entrySet().iterator();
/* 163 */     while (entryIterator.hasNext()) {
/* 164 */       Map.Entry<K, V> entry = entryIterator.next();
/* 165 */       if (Objects.equal(entry.getKey(), key)) {
/* 166 */         V value = entry.getValue();
/* 167 */         entryIterator.remove();
/* 168 */         return value;
/*     */       } 
/*     */     } 
/* 171 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected void standardClear() {
/* 182 */     Iterator<Map.Entry<K, V>> entryIterator = entrySet().iterator();
/* 183 */     while (entryIterator.hasNext()) {
/* 184 */       entryIterator.next();
/* 185 */       entryIterator.remove();
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
/*     */   @Deprecated
/*     */   @Beta
/*     */   protected Set<K> standardKeySet() {
/* 201 */     return new StandardKeySet();
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
/*     */   @Beta
/*     */   protected class StandardKeySet
/*     */     extends Maps.KeySet<K, V>
/*     */   {
/*     */     Map<K, V> map() {
/* 221 */       return ForwardingMap.this;
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
/*     */   @Beta
/*     */   protected boolean standardContainsKey(@Nullable Object key) {
/* 234 */     return Maps.containsKeyImpl(this, key);
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
/*     */   @Deprecated
/*     */   @Beta
/*     */   protected Collection<V> standardValues() {
/* 248 */     return new StandardValues();
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
/*     */   @Beta
/*     */   protected class StandardValues
/*     */     extends Maps.Values<K, V>
/*     */   {
/*     */     Map<K, V> map() {
/* 267 */       return ForwardingMap.this;
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
/*     */   @Beta
/*     */   protected boolean standardContainsValue(@Nullable Object value) {
/* 280 */     return Maps.containsValueImpl(this, value);
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
/*     */   @Deprecated
/*     */   @Beta
/*     */   protected Set<Map.Entry<K, V>> standardEntrySet(final Supplier<Iterator<Map.Entry<K, V>>> entryIteratorSupplier) {
/* 302 */     return new StandardEntrySet() {
/*     */         public Iterator<Map.Entry<K, V>> iterator() {
/* 304 */           return (Iterator<Map.Entry<K, V>>)entryIteratorSupplier.get();
/*     */         }
/*     */       };
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
/*     */   @Beta
/*     */   protected abstract class StandardEntrySet
/*     */     extends Maps.EntrySet<K, V>
/*     */   {
/*     */     Map<K, V> map() {
/* 325 */       return ForwardingMap.this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected boolean standardIsEmpty() {
/* 337 */     return !entrySet().iterator().hasNext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected boolean standardEquals(@Nullable Object object) {
/* 348 */     return Maps.equalsImpl(this, object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected int standardHashCode() {
/* 359 */     return Sets.hashCodeImpl(entrySet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected String standardToString() {
/* 370 */     return Maps.toStringImpl(this);
/*     */   }
/*     */   
/*     */   protected abstract Map<K, V> delegate();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ForwardingMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */