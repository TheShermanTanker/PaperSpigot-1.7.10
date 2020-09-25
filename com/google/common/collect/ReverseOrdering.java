/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.io.Serializable;
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
/*    */ @GwtCompatible(serializable = true)
/*    */ final class ReverseOrdering<T>
/*    */   extends Ordering<T>
/*    */   implements Serializable
/*    */ {
/*    */   final Ordering<? super T> forwardOrder;
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   ReverseOrdering(Ordering<? super T> forwardOrder) {
/* 33 */     this.forwardOrder = (Ordering<? super T>)Preconditions.checkNotNull(forwardOrder);
/*    */   }
/*    */   
/*    */   public int compare(T a, T b) {
/* 37 */     return this.forwardOrder.compare(b, a);
/*    */   }
/*    */ 
/*    */   
/*    */   public <S extends T> Ordering<S> reverse() {
/* 42 */     return (Ordering)this.forwardOrder;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <E extends T> E min(E a, E b) {
/* 48 */     return this.forwardOrder.max(a, b);
/*    */   }
/*    */   
/*    */   public <E extends T> E min(E a, E b, E c, E... rest) {
/* 52 */     return this.forwardOrder.max(a, b, c, rest);
/*    */   }
/*    */   
/*    */   public <E extends T> E min(Iterable<E> iterable) {
/* 56 */     return this.forwardOrder.max(iterable);
/*    */   }
/*    */   
/*    */   public <E extends T> E max(E a, E b) {
/* 60 */     return this.forwardOrder.min(a, b);
/*    */   }
/*    */   
/*    */   public <E extends T> E max(E a, E b, E c, E... rest) {
/* 64 */     return this.forwardOrder.min(a, b, c, rest);
/*    */   }
/*    */   
/*    */   public <E extends T> E max(Iterable<E> iterable) {
/* 68 */     return this.forwardOrder.min(iterable);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 72 */     return -this.forwardOrder.hashCode();
/*    */   }
/*    */   
/*    */   public boolean equals(@Nullable Object object) {
/* 76 */     if (object == this) {
/* 77 */       return true;
/*    */     }
/* 79 */     if (object instanceof ReverseOrdering) {
/* 80 */       ReverseOrdering<?> that = (ReverseOrdering)object;
/* 81 */       return this.forwardOrder.equals(that.forwardOrder);
/*    */     } 
/* 83 */     return false;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 87 */     return this.forwardOrder + ".reverse()";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ReverseOrdering.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */