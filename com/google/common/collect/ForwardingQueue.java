/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.util.Collection;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Queue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible
/*     */ public abstract class ForwardingQueue<E>
/*     */   extends ForwardingCollection<E>
/*     */   implements Queue<E>
/*     */ {
/*     */   public boolean offer(E o) {
/*  56 */     return delegate().offer(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public E poll() {
/*  61 */     return delegate().poll();
/*     */   }
/*     */ 
/*     */   
/*     */   public E remove() {
/*  66 */     return delegate().remove();
/*     */   }
/*     */ 
/*     */   
/*     */   public E peek() {
/*  71 */     return delegate().peek();
/*     */   }
/*     */ 
/*     */   
/*     */   public E element() {
/*  76 */     return delegate().element();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected boolean standardOffer(E e) {
/*     */     try {
/*  88 */       return add(e);
/*  89 */     } catch (IllegalStateException caught) {
/*  90 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected E standardPeek() {
/*     */     try {
/* 103 */       return element();
/* 104 */     } catch (NoSuchElementException caught) {
/* 105 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected E standardPoll() {
/*     */     try {
/* 118 */       return remove();
/* 119 */     } catch (NoSuchElementException caught) {
/* 120 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract Queue<E> delegate();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ForwardingQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */