/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ final class NaturalOrdering
/*    */   extends Ordering<Comparable>
/*    */   implements Serializable
/*    */ {
/* 32 */   static final NaturalOrdering INSTANCE = new NaturalOrdering();
/*    */   
/*    */   public int compare(Comparable<Comparable> left, Comparable right) {
/* 35 */     Preconditions.checkNotNull(right);
/* 36 */     if (left == right) {
/* 37 */       return 0;
/*    */     }
/*    */     
/* 40 */     return left.compareTo(right);
/*    */   }
/*    */   private static final long serialVersionUID = 0L;
/*    */   public <S extends Comparable> Ordering<S> reverse() {
/* 44 */     return ReverseNaturalOrdering.INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int binarySearch(List<? extends Comparable> sortedList, Comparable key) {
/* 50 */     return Collections.binarySearch((List)sortedList, key);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <E extends Comparable> List<E> sortedCopy(Iterable<E> iterable) {
/* 56 */     List<E> list = Lists.newArrayList(iterable);
/* 57 */     Collections.sort(list);
/* 58 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   private Object readResolve() {
/* 63 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 67 */     return "Ordering.natural()";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\NaturalOrdering.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */