/*     */ package net.minecraft.util.com.google.common.collect;
/*     */ 
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*     */ import net.minecraft.util.com.google.common.base.Objects;
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
/*     */ public abstract class ForwardingMapEntry<K, V>
/*     */   extends ForwardingObject
/*     */   implements Map.Entry<K, V>
/*     */ {
/*     */   public K getKey() {
/*  66 */     return delegate().getKey();
/*     */   }
/*     */ 
/*     */   
/*     */   public V getValue() {
/*  71 */     return delegate().getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public V setValue(V value) {
/*  76 */     return delegate().setValue(value);
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/*  80 */     return delegate().equals(object);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  84 */     return delegate().hashCode();
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
/*     */   protected boolean standardEquals(@Nullable Object object) {
/*  96 */     if (object instanceof Map.Entry) {
/*  97 */       Map.Entry<?, ?> that = (Map.Entry<?, ?>)object;
/*  98 */       return (Objects.equal(getKey(), that.getKey()) && Objects.equal(getValue(), that.getValue()));
/*     */     } 
/*     */     
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int standardHashCode() {
/* 112 */     K k = getKey();
/* 113 */     V v = getValue();
/* 114 */     return ((k == null) ? 0 : k.hashCode()) ^ ((v == null) ? 0 : v.hashCode());
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
/*     */   protected String standardToString() {
/* 126 */     return (new StringBuilder()).append(getKey()).append("=").append(getValue()).toString();
/*     */   }
/*     */   
/*     */   protected abstract Map.Entry<K, V> delegate();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\ForwardingMapEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */