/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.io.Serializable;
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
/*    */ final class ReverseNaturalOrdering
/*    */   extends Ordering<Comparable>
/*    */   implements Serializable
/*    */ {
/* 30 */   static final ReverseNaturalOrdering INSTANCE = new ReverseNaturalOrdering();
/*    */   
/*    */   public int compare(Comparable left, Comparable<Comparable> right) {
/* 33 */     Preconditions.checkNotNull(left);
/* 34 */     if (left == right) {
/* 35 */       return 0;
/*    */     }
/*    */     
/* 38 */     return right.compareTo(left);
/*    */   }
/*    */   private static final long serialVersionUID = 0L;
/*    */   public <S extends Comparable> Ordering<S> reverse() {
/* 42 */     return Ordering.natural();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <E extends Comparable> E min(E a, E b) {
/* 48 */     return (E)NaturalOrdering.INSTANCE.max(a, b);
/*    */   }
/*    */   
/*    */   public <E extends Comparable> E min(E a, E b, E c, E... rest) {
/* 52 */     return (E)NaturalOrdering.INSTANCE.max(a, b, c, (Object[])rest);
/*    */   }
/*    */   
/*    */   public <E extends Comparable> E min(Iterable<E> iterable) {
/* 56 */     return (E)NaturalOrdering.INSTANCE.max(iterable);
/*    */   }
/*    */   
/*    */   public <E extends Comparable> E max(E a, E b) {
/* 60 */     return (E)NaturalOrdering.INSTANCE.min(a, b);
/*    */   }
/*    */   
/*    */   public <E extends Comparable> E max(E a, E b, E c, E... rest) {
/* 64 */     return (E)NaturalOrdering.INSTANCE.min(a, b, c, (Object[])rest);
/*    */   }
/*    */   
/*    */   public <E extends Comparable> E max(Iterable<E> iterable) {
/* 68 */     return (E)NaturalOrdering.INSTANCE.min(iterable);
/*    */   }
/*    */ 
/*    */   
/*    */   private Object readResolve() {
/* 73 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 77 */     return "Ordering.natural().reverse()";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ReverseNaturalOrdering.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */