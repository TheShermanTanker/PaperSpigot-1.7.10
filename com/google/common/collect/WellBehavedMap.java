/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible
/*    */ final class WellBehavedMap<K, V>
/*    */   extends ForwardingMap<K, V>
/*    */ {
/*    */   private final Map<K, V> delegate;
/*    */   private Set<Map.Entry<K, V>> entrySet;
/*    */   
/*    */   private WellBehavedMap(Map<K, V> delegate) {
/* 39 */     this.delegate = delegate;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static <K, V> WellBehavedMap<K, V> wrap(Map<K, V> delegate) {
/* 49 */     return new WellBehavedMap<K, V>(delegate);
/*    */   }
/*    */   
/*    */   protected Map<K, V> delegate() {
/* 53 */     return this.delegate;
/*    */   }
/*    */   
/*    */   public Set<Map.Entry<K, V>> entrySet() {
/* 57 */     Set<Map.Entry<K, V>> es = this.entrySet;
/* 58 */     if (es != null) {
/* 59 */       return es;
/*    */     }
/* 61 */     return this.entrySet = Sets.transform(this.delegate.keySet(), new KeyToEntryConverter<Object, V>(this));
/*    */   }
/*    */   
/*    */   private static class KeyToEntryConverter<K, V>
/*    */     extends Sets.InvertibleFunction<K, Map.Entry<K, V>>
/*    */   {
/*    */     final Map<K, V> map;
/*    */     
/*    */     KeyToEntryConverter(Map<K, V> map) {
/* 70 */       this.map = map;
/*    */     }
/*    */     
/*    */     public Map.Entry<K, V> apply(final K key) {
/* 74 */       return new AbstractMapEntry<K, V>() {
/*    */           public K getKey() {
/* 76 */             return (K)key;
/*    */           }
/*    */           public V getValue() {
/* 79 */             return (V)WellBehavedMap.KeyToEntryConverter.this.map.get(key);
/*    */           }
/*    */           public V setValue(V value) {
/* 82 */             return WellBehavedMap.KeyToEntryConverter.this.map.put(key, value);
/*    */           }
/*    */         };
/*    */     }
/*    */     
/*    */     public K invert(Map.Entry<K, V> entry) {
/* 88 */       return entry.getKey();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\WellBehavedMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */