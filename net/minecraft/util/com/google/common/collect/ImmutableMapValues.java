/*    */ package net.minecraft.util.com.google.common.collect;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*    */ import net.minecraft.util.com.google.common.annotations.GwtIncompatible;
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
/*    */ @GwtCompatible(emulated = true)
/*    */ final class ImmutableMapValues<K, V>
/*    */   extends ImmutableCollection<V>
/*    */ {
/*    */   private final ImmutableMap<K, V> map;
/*    */   
/*    */   ImmutableMapValues(ImmutableMap<K, V> map) {
/* 38 */     this.map = map;
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 43 */     return this.map.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public UnmodifiableIterator<V> iterator() {
/* 48 */     return Maps.valueIterator(this.map.entrySet().iterator());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(@Nullable Object object) {
/* 53 */     return (object != null && Iterators.contains(iterator(), object));
/*    */   }
/*    */ 
/*    */   
/*    */   boolean isPartialView() {
/* 58 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   ImmutableList<V> createAsList() {
/* 63 */     final ImmutableList<Map.Entry<K, V>> entryList = this.map.entrySet().asList();
/* 64 */     return new ImmutableAsList<V>()
/*    */       {
/*    */         public V get(int index) {
/* 67 */           return (V)((Map.Entry)entryList.get(index)).getValue();
/*    */         }
/*    */ 
/*    */         
/*    */         ImmutableCollection<V> delegateCollection() {
/* 72 */           return ImmutableMapValues.this;
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   @GwtIncompatible("serialization")
/*    */   Object writeReplace() {
/* 79 */     return new SerializedForm<V>(this.map);
/*    */   }
/*    */   
/*    */   @GwtIncompatible("serialization")
/*    */   private static class SerializedForm<V> implements Serializable { final ImmutableMap<?, V> map;
/*    */     
/*    */     SerializedForm(ImmutableMap<?, V> map) {
/* 86 */       this.map = map;
/*    */     } private static final long serialVersionUID = 0L;
/*    */     Object readResolve() {
/* 89 */       return this.map.values();
/*    */     } }
/*    */ 
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\ImmutableMapValues.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */