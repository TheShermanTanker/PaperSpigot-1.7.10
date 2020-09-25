/*    */ package net.minecraft.util.com.google.common.cache;
/*    */ 
/*    */ import java.util.concurrent.Executor;
/*    */ import net.minecraft.util.com.google.common.annotations.Beta;
/*    */ import net.minecraft.util.com.google.common.base.Preconditions;
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
/*    */ public final class RemovalListeners
/*    */ {
/*    */   public static <K, V> RemovalListener<K, V> asynchronous(final RemovalListener<K, V> listener, final Executor executor) {
/* 46 */     Preconditions.checkNotNull(listener);
/* 47 */     Preconditions.checkNotNull(executor);
/* 48 */     return new RemovalListener<K, V>()
/*    */       {
/*    */         public void onRemoval(final RemovalNotification<K, V> notification) {
/* 51 */           executor.execute(new Runnable()
/*    */               {
/*    */                 public void run() {
/* 54 */                   listener.onRemoval(notification);
/*    */                 }
/*    */               });
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\cache\RemovalListeners.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */