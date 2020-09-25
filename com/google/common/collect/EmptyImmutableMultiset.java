/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
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
/*    */ @GwtCompatible(serializable = true)
/*    */ final class EmptyImmutableMultiset
/*    */   extends ImmutableMultiset<Object>
/*    */ {
/* 31 */   static final EmptyImmutableMultiset INSTANCE = new EmptyImmutableMultiset();
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   public int count(@Nullable Object element) {
/* 35 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public ImmutableSet<Object> elementSet() {
/* 40 */     return ImmutableSet.of();
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 45 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   UnmodifiableIterator<Multiset.Entry<Object>> entryIterator() {
/* 50 */     return Iterators.emptyIterator();
/*    */   }
/*    */ 
/*    */   
/*    */   int distinctElements() {
/* 55 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   boolean isPartialView() {
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   ImmutableSet<Multiset.Entry<Object>> createEntrySet() {
/* 65 */     return ImmutableSet.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\EmptyImmutableMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */