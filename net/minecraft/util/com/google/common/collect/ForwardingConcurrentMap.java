/*    */ package net.minecraft.util.com.google.common.collect;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentMap;
/*    */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible
/*    */ public abstract class ForwardingConcurrentMap<K, V>
/*    */   extends ForwardingMap<K, V>
/*    */   implements ConcurrentMap<K, V>
/*    */ {
/*    */   public V putIfAbsent(K key, V value) {
/* 43 */     return delegate().putIfAbsent(key, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean remove(Object key, Object value) {
/* 48 */     return delegate().remove(key, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public V replace(K key, V value) {
/* 53 */     return delegate().replace(key, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean replace(K key, V oldValue, V newValue) {
/* 58 */     return delegate().replace(key, oldValue, newValue);
/*    */   }
/*    */   
/*    */   protected abstract ConcurrentMap<K, V> delegate();
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\ForwardingConcurrentMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */