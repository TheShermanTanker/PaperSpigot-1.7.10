/*    */ package net.minecraft.util.com.google.common.collect;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.SortedMap;
/*    */ import java.util.SortedSet;
/*    */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
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
/*    */ @GwtCompatible
/*    */ abstract class AbstractSortedKeySortedSetMultimap<K, V>
/*    */   extends AbstractSortedSetMultimap<K, V>
/*    */ {
/*    */   AbstractSortedKeySortedSetMultimap(SortedMap<K, Collection<V>> map) {
/* 38 */     super(map);
/*    */   }
/*    */ 
/*    */   
/*    */   public SortedMap<K, Collection<V>> asMap() {
/* 43 */     return (SortedMap<K, Collection<V>>)super.asMap();
/*    */   }
/*    */ 
/*    */   
/*    */   SortedMap<K, Collection<V>> backingMap() {
/* 48 */     return (SortedMap<K, Collection<V>>)super.backingMap();
/*    */   }
/*    */ 
/*    */   
/*    */   public SortedSet<K> keySet() {
/* 53 */     return (SortedSet<K>)super.keySet();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\AbstractSortedKeySortedSetMultimap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */