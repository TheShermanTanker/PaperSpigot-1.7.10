/*    */ package com.google.common.cache;
/*    */ 
/*    */ import com.google.common.annotations.Beta;
/*    */ import com.google.common.base.Function;
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.google.common.base.Supplier;
/*    */ import java.io.Serializable;
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
/*    */ public abstract class CacheLoader<K, V>
/*    */ {
/*    */   public static <K, V> CacheLoader<K, V> from(Function<K, V> function) {
/* 40 */     return new FunctionToCacheLoader<K, V>(function);
/*    */   }
/*    */   
/*    */   private static final class FunctionToCacheLoader<K, V> extends CacheLoader<K, V> implements Serializable {
/*    */     private final Function<K, V> computingFunction;
/*    */     private static final long serialVersionUID = 0L;
/*    */     
/*    */     public FunctionToCacheLoader(Function<K, V> computingFunction) {
/* 48 */       this.computingFunction = (Function<K, V>)Preconditions.checkNotNull(computingFunction);
/*    */     }
/*    */ 
/*    */     
/*    */     public V load(K key) {
/* 53 */       return (V)this.computingFunction.apply(key);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <V> CacheLoader<Object, V> from(Supplier<V> supplier) {
/* 64 */     return new SupplierToCacheLoader<V>(supplier);
/*    */   }
/*    */   
/*    */   public abstract V load(K paramK) throws Exception;
/*    */   
/*    */   private static final class SupplierToCacheLoader<V> extends CacheLoader<Object, V> implements Serializable { private final Supplier<V> computingSupplier;
/*    */     
/*    */     public SupplierToCacheLoader(Supplier<V> computingSupplier) {
/* 72 */       this.computingSupplier = (Supplier<V>)Preconditions.checkNotNull(computingSupplier);
/*    */     }
/*    */     private static final long serialVersionUID = 0L;
/*    */     
/*    */     public V load(Object key) {
/* 77 */       return (V)this.computingSupplier.get();
/*    */     } }
/*    */ 
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\cache\CacheLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */