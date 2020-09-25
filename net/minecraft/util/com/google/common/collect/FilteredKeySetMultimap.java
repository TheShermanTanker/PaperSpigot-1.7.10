/*    */ package net.minecraft.util.com.google.common.collect;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ @GwtCompatible
/*    */ final class FilteredKeySetMultimap<K, V>
/*    */   extends FilteredKeyMultimap<K, V>
/*    */   implements FilteredSetMultimap<K, V>
/*    */ {
/*    */   FilteredKeySetMultimap(SetMultimap<K, V> unfiltered, Predicate<? super K> keyPredicate) {
/* 37 */     super(unfiltered, keyPredicate);
/*    */   }
/*    */ 
/*    */   
/*    */   public SetMultimap<K, V> unfiltered() {
/* 42 */     return (SetMultimap<K, V>)this.unfiltered;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<V> get(K key) {
/* 47 */     return (Set<V>)super.get(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<V> removeAll(Object key) {
/* 52 */     return (Set<V>)super.removeAll(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<V> replaceValues(K key, Iterable<? extends V> values) {
/* 57 */     return (Set<V>)super.replaceValues(key, values);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<Map.Entry<K, V>> entries() {
/* 62 */     return (Set<Map.Entry<K, V>>)super.entries();
/*    */   }
/*    */ 
/*    */   
/*    */   Set<Map.Entry<K, V>> createEntries() {
/* 67 */     return new EntrySet();
/*    */   }
/*    */   
/*    */   class EntrySet
/*    */     extends FilteredKeyMultimap<K, V>.Entries implements Set<Map.Entry<K, V>> {
/*    */     public int hashCode() {
/* 73 */       return Sets.hashCodeImpl(this);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean equals(@Nullable Object o) {
/* 78 */       return Sets.equalsImpl(this, o);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\FilteredKeySetMultimap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */