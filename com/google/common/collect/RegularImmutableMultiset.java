/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
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
/*    */ @GwtCompatible(serializable = true)
/*    */ class RegularImmutableMultiset<E>
/*    */   extends ImmutableMultiset<E>
/*    */ {
/*    */   private final transient ImmutableMap<E, Integer> map;
/*    */   private final transient int size;
/*    */   
/*    */   RegularImmutableMultiset(ImmutableMap<E, Integer> map, int size) {
/* 40 */     this.map = map;
/* 41 */     this.size = size;
/*    */   }
/*    */ 
/*    */   
/*    */   boolean isPartialView() {
/* 46 */     return this.map.isPartialView();
/*    */   }
/*    */ 
/*    */   
/*    */   public int count(@Nullable Object element) {
/* 51 */     Integer value = this.map.get(element);
/* 52 */     return (value == null) ? 0 : value.intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 57 */     return this.size;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(@Nullable Object element) {
/* 62 */     return this.map.containsKey(element);
/*    */   }
/*    */ 
/*    */   
/*    */   public ImmutableSet<E> elementSet() {
/* 67 */     return this.map.keySet();
/*    */   }
/*    */ 
/*    */   
/*    */   UnmodifiableIterator<Multiset.Entry<E>> entryIterator() {
/* 72 */     final Iterator<Map.Entry<E, Integer>> mapIterator = this.map.entrySet().iterator();
/*    */     
/* 74 */     return (UnmodifiableIterator)new UnmodifiableIterator<Multiset.Entry<Multiset.Entry<E>>>()
/*    */       {
/*    */         public boolean hasNext() {
/* 77 */           return mapIterator.hasNext();
/*    */         }
/*    */ 
/*    */         
/*    */         public Multiset.Entry<E> next() {
/* 82 */           Map.Entry<E, Integer> mapEntry = mapIterator.next();
/* 83 */           return Multisets.immutableEntry(mapEntry.getKey(), ((Integer)mapEntry.getValue()).intValue());
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 90 */     return this.map.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   int distinctElements() {
/* 95 */     return this.map.size();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\RegularImmutableMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */