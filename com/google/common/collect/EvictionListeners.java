/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.Beta;
/*    */ import java.util.concurrent.Executor;
/*    */ import javax.annotation.Nullable;
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
/*    */ @Deprecated
/*    */ @Beta
/*    */ public final class EvictionListeners
/*    */ {
/*    */   @Deprecated
/*    */   public static <K, V> MapEvictionListener<K, V> asynchronous(final MapEvictionListener<K, V> listener, final Executor executor) {
/* 59 */     return new MapEvictionListener<K, V>()
/*    */       {
/*    */         public void onEviction(@Nullable final K key, @Nullable final V value) {
/* 62 */           executor.execute(new Runnable()
/*    */               {
/*    */                 public void run() {
/* 65 */                   listener.onEviction(key, value);
/*    */                 }
/*    */               });
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\EvictionListeners.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */