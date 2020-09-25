/*    */ package net.minecraft.util.com.google.common.cache;
/*    */ 
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import net.minecraft.util.com.google.common.annotations.Beta;
/*    */ import net.minecraft.util.com.google.common.base.Preconditions;
/*    */ import net.minecraft.util.com.google.common.collect.ImmutableMap;
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
/*    */ public abstract class ForwardingLoadingCache<K, V>
/*    */   extends ForwardingCache<K, V>
/*    */   implements LoadingCache<K, V>
/*    */ {
/*    */   public V get(K key) throws ExecutionException {
/* 48 */     return delegate().get(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public V getUnchecked(K key) {
/* 53 */     return delegate().getUnchecked(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
/* 58 */     return delegate().getAll(keys);
/*    */   }
/*    */ 
/*    */   
/*    */   public V apply(K key) {
/* 63 */     return delegate().apply(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public void refresh(K key) {
/* 68 */     delegate().refresh(key);
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract LoadingCache<K, V> delegate();
/*    */ 
/*    */   
/*    */   @Beta
/*    */   public static abstract class SimpleForwardingLoadingCache<K, V>
/*    */     extends ForwardingLoadingCache<K, V>
/*    */   {
/*    */     private final LoadingCache<K, V> delegate;
/*    */ 
/*    */     
/*    */     protected SimpleForwardingLoadingCache(LoadingCache<K, V> delegate) {
/* 83 */       this.delegate = (LoadingCache<K, V>)Preconditions.checkNotNull(delegate);
/*    */     }
/*    */ 
/*    */     
/*    */     protected final LoadingCache<K, V> delegate() {
/* 88 */       return this.delegate;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\cache\ForwardingLoadingCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */