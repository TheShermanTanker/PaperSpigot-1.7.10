/*    */ package net.minecraft.util.com.google.common.collect;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.NavigableMap;
/*    */ import java.util.NavigableSet;
/*    */ import java.util.Set;
/*    */ import java.util.SortedMap;
/*    */ import java.util.SortedSet;
/*    */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*    */ import net.minecraft.util.com.google.common.base.Function;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible(emulated = true)
/*    */ final class Platform
/*    */ {
/*    */   static <T> T[] newArray(T[] reference, int length) {
/* 48 */     Class<?> type = reference.getClass().getComponentType();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 53 */     T[] result = (T[])Array.newInstance(type, length);
/* 54 */     return result;
/*    */   }
/*    */   
/*    */   static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
/* 58 */     return Collections.newSetFromMap(map);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static MapMaker tryWeakKeys(MapMaker mapMaker) {
/* 68 */     return mapMaker.weakKeys();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static <K, V1, V2> SortedMap<K, V2> mapsTransformEntriesSortedMap(SortedMap<K, V1> fromMap, Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 74 */     return (fromMap instanceof NavigableMap) ? Maps.<K, V1, V2>transformEntries((NavigableMap<K, V1>)fromMap, transformer) : Maps.<K, V1, V2>transformEntriesIgnoreNavigable(fromMap, transformer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static <K, V> SortedMap<K, V> mapsAsMapSortedSet(SortedSet<K> set, Function<? super K, V> function) {
/* 81 */     return (set instanceof NavigableSet) ? Maps.<K, V>asMap((NavigableSet<K>)set, function) : Maps.<K, V>asMapSortedIgnoreNavigable(set, function);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static <E> SortedSet<E> setsFilterSortedSet(SortedSet<E> set, Predicate<? super E> predicate) {
/* 88 */     return (set instanceof NavigableSet) ? Sets.<E>filter((NavigableSet<E>)set, predicate) : Sets.<E>filterSortedIgnoreNavigable(set, predicate);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static <K, V> SortedMap<K, V> mapsFilterSortedMap(SortedMap<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
/* 95 */     return (map instanceof NavigableMap) ? Maps.<K, V>filterEntries((NavigableMap<K, V>)map, predicate) : Maps.<K, V>filterSortedIgnoreNavigable(map, predicate);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\Platform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */