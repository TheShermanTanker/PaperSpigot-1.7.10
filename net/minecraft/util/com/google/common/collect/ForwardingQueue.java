/*     */ package net.minecraft.util.com.google.common.collect;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Queue;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
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
/*  55 */     return delegate().offer(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public E poll() {
/*  60 */     return delegate().poll();
/*     */   }
/*     */ 
/*     */   
/*     */   public E remove() {
/*  65 */     return delegate().remove();
/*     */   }
/*     */ 
/*     */   
/*     */   public E peek() {
/*  70 */     return delegate().peek();
/*     */   }
/*     */ 
/*     */   
/*     */   public E element() {
/*  75 */     return delegate().element();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean standardOffer(E e) {
/*     */     try {
/*  87 */       return add(e);
/*  88 */     } catch (IllegalStateException caught) {
/*  89 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected E standardPeek() {
/*     */     try {
/* 102 */       return element();
/* 103 */     } catch (NoSuchElementException caught) {
/* 104 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected E standardPoll() {
/*     */     try {
/* 117 */       return remove();
/* 118 */     } catch (NoSuchElementException caught) {
/* 119 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract Queue<E> delegate();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\ForwardingQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */