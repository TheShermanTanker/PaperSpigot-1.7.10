/*    */ package net.minecraft.util.com.google.common.collect;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
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
/*    */ final class FilteredEntrySetMultimap<K, V>
/*    */   extends FilteredEntryMultimap<K, V>
/*    */   implements FilteredSetMultimap<K, V>
/*    */ {
/*    */   FilteredEntrySetMultimap(SetMultimap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> predicate) {
/* 35 */     super(unfiltered, predicate);
/*    */   }
/*    */ 
/*    */   
/*    */   public SetMultimap<K, V> unfiltered() {
/* 40 */     return (SetMultimap<K, V>)this.unfiltered;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<V> get(K key) {
/* 45 */     return (Set<V>)super.get(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<V> removeAll(Object key) {
/* 50 */     return (Set<V>)super.removeAll(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<V> replaceValues(K key, Iterable<? extends V> values) {
/* 55 */     return (Set<V>)super.replaceValues(key, values);
/*    */   }
/*    */ 
/*    */   
/*    */   Set<Map.Entry<K, V>> createEntries() {
/* 60 */     return Sets.filter(unfiltered().entries(), entryPredicate());
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<Map.Entry<K, V>> entries() {
/* 65 */     return (Set<Map.Entry<K, V>>)super.entries();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\FilteredEntrySetMultimap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */