/*    */ package com.avaje.ebean.common;
/*    */ 
/*    */ import com.avaje.ebean.bean.BeanCollection;
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
/*    */ 
/*    */ 
/*    */ class ModifyListIterator<E>
/*    */   implements ListIterator<E>
/*    */ {
/*    */   private final BeanCollection<E> owner;
/*    */   private final ListIterator<E> it;
/*    */   private E last;
/*    */   
/*    */   ModifyListIterator(BeanCollection<E> owner, ListIterator<E> it) {
/* 46 */     this.owner = owner;
/* 47 */     this.it = it;
/*    */   }
/*    */   
/*    */   public void add(E bean) {
/* 51 */     this.owner.modifyAddition(bean);
/* 52 */     this.last = null;
/* 53 */     this.it.add(bean);
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 57 */     return this.it.hasNext();
/*    */   }
/*    */   
/*    */   public boolean hasPrevious() {
/* 61 */     return this.it.hasPrevious();
/*    */   }
/*    */   
/*    */   public E next() {
/* 65 */     this.last = this.it.next();
/* 66 */     return this.last;
/*    */   }
/*    */   
/*    */   public int nextIndex() {
/* 70 */     return this.it.nextIndex();
/*    */   }
/*    */   
/*    */   public E previous() {
/* 74 */     this.last = this.it.previous();
/* 75 */     return this.last;
/*    */   }
/*    */   
/*    */   public int previousIndex() {
/* 79 */     return this.it.previousIndex();
/*    */   }
/*    */   
/*    */   public void remove() {
/* 83 */     this.owner.modifyRemoval(this.last);
/* 84 */     this.last = null;
/* 85 */     this.it.remove();
/*    */   }
/*    */   
/*    */   public void set(E o) {
/* 89 */     if (this.last != null) {
/*    */ 
/*    */       
/* 92 */       this.owner.modifyRemoval(this.last);
/* 93 */       this.owner.modifyAddition(o);
/*    */     } 
/* 95 */     this.it.set(o);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\common\ModifyListIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */