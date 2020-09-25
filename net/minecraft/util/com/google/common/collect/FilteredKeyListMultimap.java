/*    */ package net.minecraft.util.com.google.common.collect;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*    */ import net.minecraft.util.com.google.common.base.Predicate;
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
/*    */ final class FilteredKeyListMultimap<K, V>
/*    */   extends FilteredKeyMultimap<K, V>
/*    */   implements ListMultimap<K, V>
/*    */ {
/*    */   FilteredKeyListMultimap(ListMultimap<K, V> unfiltered, Predicate<? super K> keyPredicate) {
/* 35 */     super(unfiltered, keyPredicate);
/*    */   }
/*    */ 
/*    */   
/*    */   public ListMultimap<K, V> unfiltered() {
/* 40 */     return (ListMultimap<K, V>)super.unfiltered();
/*    */   }
/*    */ 
/*    */   
/*    */   public List<V> get(K key) {
/* 45 */     return (List<V>)super.get(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<V> removeAll(@Nullable Object key) {
/* 50 */     return (List<V>)super.removeAll(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<V> replaceValues(K key, Iterable<? extends V> values) {
/* 55 */     return (List<V>)super.replaceValues(key, values);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\FilteredKeyListMultimap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */