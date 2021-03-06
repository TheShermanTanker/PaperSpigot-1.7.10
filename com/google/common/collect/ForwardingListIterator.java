/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Iterator;
/*    */ import java.util.ListIterator;
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
/*    */ 
/*    */ @GwtCompatible
/*    */ public abstract class ForwardingListIterator<E>
/*    */   extends ForwardingIterator<E>
/*    */   implements ListIterator<E>
/*    */ {
/*    */   public void add(E element) {
/* 43 */     delegate().add(element);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasPrevious() {
/* 48 */     return delegate().hasPrevious();
/*    */   }
/*    */ 
/*    */   
/*    */   public int nextIndex() {
/* 53 */     return delegate().nextIndex();
/*    */   }
/*    */ 
/*    */   
/*    */   public E previous() {
/* 58 */     return delegate().previous();
/*    */   }
/*    */ 
/*    */   
/*    */   public int previousIndex() {
/* 63 */     return delegate().previousIndex();
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(E element) {
/* 68 */     delegate().set(element);
/*    */   }
/*    */   
/*    */   protected abstract ListIterator<E> delegate();
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ForwardingListIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */