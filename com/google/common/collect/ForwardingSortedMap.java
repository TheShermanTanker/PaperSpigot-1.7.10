/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.SortedMap;
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
/*     */ @GwtCompatible
/*     */ public abstract class ForwardingSortedMap<K, V>
/*     */   extends ForwardingMap<K, V>
/*     */   implements SortedMap<K, V>
/*     */ {
/*     */   public Comparator<? super K> comparator() {
/*  66 */     return delegate().comparator();
/*     */   }
/*     */ 
/*     */   
/*     */   public K firstKey() {
/*  71 */     return delegate().firstKey();
/*     */   }
/*     */ 
/*     */   
/*     */   public SortedMap<K, V> headMap(K toKey) {
/*  76 */     return delegate().headMap(toKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public K lastKey() {
/*  81 */     return delegate().lastKey();
/*     */   }
/*     */ 
/*     */   
/*     */   public SortedMap<K, V> subMap(K fromKey, K toKey) {
/*  86 */     return delegate().subMap(fromKey, toKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public SortedMap<K, V> tailMap(K fromKey) {
/*  91 */     return delegate().tailMap(fromKey);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int unsafeCompare(Object k1, Object k2) {
/*  97 */     Comparator<? super K> comparator = comparator();
/*  98 */     if (comparator == null) {
/*  99 */       return ((Comparable<Object>)k1).compareTo(k2);
/*     */     }
/* 101 */     return comparator.compare((K)k1, (K)k2);
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
/*     */   @Beta
/*     */   protected boolean standardContainsKey(@Nullable Object key) {
/*     */     try {
/* 117 */       ForwardingSortedMap<K, V> forwardingSortedMap = this;
/* 118 */       Object ceilingKey = forwardingSortedMap.tailMap((K)key).firstKey();
/* 119 */       return (unsafeCompare(ceilingKey, key) == 0);
/* 120 */     } catch (ClassCastException e) {
/* 121 */       return false;
/* 122 */     } catch (NoSuchElementException e) {
/* 123 */       return false;
/* 124 */     } catch (NullPointerException e) {
/* 125 */       return false;
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
/*     */   @Beta
/*     */   protected V standardRemove(@Nullable Object key) {
/*     */     try {
/* 141 */       ForwardingSortedMap<K, V> forwardingSortedMap = this;
/* 142 */       Iterator<Map.Entry<Object, V>> entryIterator = forwardingSortedMap.tailMap((K)key).entrySet().iterator();
/*     */       
/* 144 */       if (entryIterator.hasNext()) {
/* 145 */         Map.Entry<Object, V> ceilingEntry = entryIterator.next();
/* 146 */         if (unsafeCompare(ceilingEntry.getKey(), key) == 0) {
/* 147 */           V value = ceilingEntry.getValue();
/* 148 */           entryIterator.remove();
/* 149 */           return value;
/*     */         } 
/*     */       } 
/* 152 */     } catch (ClassCastException e) {
/* 153 */       return null;
/* 154 */     } catch (NullPointerException e) {
/* 155 */       return null;
/*     */     } 
/* 157 */     return null;
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
/*     */   protected SortedMap<K, V> standardSubMap(K fromKey, K toKey) {
/* 169 */     return tailMap(fromKey).headMap(toKey);
/*     */   }
/*     */   
/*     */   protected abstract SortedMap<K, V> delegate();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ForwardingSortedMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */