/*    */ package com.google.common.cache;
/*    */ 
/*    */ import com.google.common.annotations.Beta;
/*    */ import com.google.common.base.Objects;
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.util.Map;
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
/*    */ @Beta
/*    */ public final class RemovalNotification<K, V>
/*    */   implements Map.Entry<K, V>
/*    */ {
/*    */   @Nullable
/*    */   private final K key;
/*    */   @Nullable
/*    */   private final V value;
/*    */   private final RemovalCause cause;
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   RemovalNotification(@Nullable K key, @Nullable V value, RemovalCause cause) {
/* 46 */     this.key = key;
/* 47 */     this.value = value;
/* 48 */     this.cause = (RemovalCause)Preconditions.checkNotNull(cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RemovalCause getCause() {
/* 55 */     return this.cause;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean wasEvicted() {
/* 63 */     return this.cause.wasEvicted();
/*    */   }
/*    */   @Nullable
/*    */   public K getKey() {
/* 67 */     return this.key;
/*    */   }
/*    */   @Nullable
/*    */   public V getValue() {
/* 71 */     return this.value;
/*    */   }
/*    */   
/*    */   public final V setValue(V value) {
/* 75 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public boolean equals(@Nullable Object object) {
/* 79 */     if (object instanceof Map.Entry) {
/* 80 */       Map.Entry<?, ?> that = (Map.Entry<?, ?>)object;
/* 81 */       return (Objects.equal(getKey(), that.getKey()) && Objects.equal(getValue(), that.getValue()));
/*    */     } 
/*    */     
/* 84 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 88 */     K k = getKey();
/* 89 */     V v = getValue();
/* 90 */     return ((k == null) ? 0 : k.hashCode()) ^ ((v == null) ? 0 : v.hashCode());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 97 */     return (new StringBuilder()).append(getKey()).append("=").append(getValue()).toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\cache\RemovalNotification.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */