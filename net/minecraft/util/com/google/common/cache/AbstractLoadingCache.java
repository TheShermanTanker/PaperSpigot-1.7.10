/*    */ package net.minecraft.util.com.google.common.cache;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import net.minecraft.util.com.google.common.annotations.Beta;
/*    */ import net.minecraft.util.com.google.common.collect.ImmutableMap;
/*    */ import net.minecraft.util.com.google.common.collect.Maps;
/*    */ import net.minecraft.util.com.google.common.util.concurrent.UncheckedExecutionException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Beta
/*    */ public abstract class AbstractLoadingCache<K, V>
/*    */   extends AbstractCache<K, V>
/*    */   implements LoadingCache<K, V>
/*    */ {
/*    */   public V getUnchecked(K key) {
/*    */     try {
/* 53 */       return get(key);
/* 54 */     } catch (ExecutionException e) {
/* 55 */       throw new UncheckedExecutionException(e.getCause());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
/* 61 */     Map<K, V> result = Maps.newLinkedHashMap();
/* 62 */     for (K key : keys) {
/* 63 */       if (!result.containsKey(key)) {
/* 64 */         result.put(key, get(key));
/*    */       }
/*    */     } 
/* 67 */     return ImmutableMap.copyOf(result);
/*    */   }
/*    */ 
/*    */   
/*    */   public final V apply(K key) {
/* 72 */     return getUnchecked(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public void refresh(K key) {
/* 77 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\cache\AbstractLoadingCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */