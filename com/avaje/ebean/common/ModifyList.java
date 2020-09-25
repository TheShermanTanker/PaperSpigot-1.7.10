/*     */ package com.avaje.ebean.common;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ModifyList<E>
/*     */   extends ModifyCollection<E>
/*     */   implements List<E>
/*     */ {
/*     */   private final List<E> list;
/*     */   
/*     */   ModifyList(BeanCollection<E> owner, List<E> list) {
/*  52 */     super(owner, list);
/*  53 */     this.list = list;
/*     */   }
/*     */   
/*     */   public void add(int index, E element) {
/*  57 */     this.list.add(index, element);
/*  58 */     this.owner.modifyAddition(element);
/*     */   }
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends E> co) {
/*  62 */     if (this.list.addAll(index, co)) {
/*  63 */       Iterator<? extends E> it = co.iterator();
/*  64 */       while (it.hasNext()) {
/*  65 */         E o = it.next();
/*  66 */         this.owner.modifyAddition(o);
/*     */       } 
/*  68 */       return true;
/*     */     } 
/*  70 */     return false;
/*     */   }
/*     */   
/*     */   public E get(int index) {
/*  74 */     return this.list.get(index);
/*     */   }
/*     */   
/*     */   public int indexOf(Object o) {
/*  78 */     return this.list.indexOf(o);
/*     */   }
/*     */   
/*     */   public int lastIndexOf(Object o) {
/*  82 */     return this.list.lastIndexOf(o);
/*     */   }
/*     */   
/*     */   public ListIterator<E> listIterator() {
/*  86 */     return new ModifyListIterator<E>(this.owner, this.list.listIterator());
/*     */   }
/*     */   
/*     */   public ListIterator<E> listIterator(int index) {
/*  90 */     return new ModifyListIterator<E>(this.owner, this.list.listIterator(index));
/*     */   }
/*     */   
/*     */   public E remove(int index) {
/*  94 */     E o = this.list.remove(index);
/*  95 */     this.owner.modifyRemoval(o);
/*  96 */     return o;
/*     */   }
/*     */   
/*     */   public E set(int index, E element) {
/* 100 */     E o = this.list.set(index, element);
/* 101 */     this.owner.modifyAddition(element);
/* 102 */     this.owner.modifyRemoval(o);
/* 103 */     return o;
/*     */   }
/*     */   
/*     */   public List<E> subList(int fromIndex, int toIndex) {
/* 107 */     return new ModifyList(this.owner, this.list.subList(fromIndex, toIndex));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\common\ModifyList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */