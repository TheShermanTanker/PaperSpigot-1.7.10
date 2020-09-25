/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public abstract class AbstractIterator<T>
/*     */   extends UnmodifiableIterator<T>
/*     */ {
/*  63 */   private State state = State.NOT_READY;
/*     */   private T next;
/*     */   
/*     */   protected abstract T computeNext();
/*     */   
/*     */   private enum State
/*     */   {
/*  70 */     READY,
/*     */ 
/*     */     
/*  73 */     NOT_READY,
/*     */ 
/*     */     
/*  76 */     DONE,
/*     */ 
/*     */     
/*  79 */     FAILED;
/*     */   }
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
/*     */   protected final T endOfData() {
/* 122 */     this.state = State.DONE;
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean hasNext() {
/* 128 */     Preconditions.checkState((this.state != State.FAILED));
/* 129 */     switch (this.state) {
/*     */       case DONE:
/* 131 */         return false;
/*     */       case READY:
/* 133 */         return true;
/*     */     } 
/*     */     
/* 136 */     return tryToComputeNext();
/*     */   }
/*     */   
/*     */   private boolean tryToComputeNext() {
/* 140 */     this.state = State.FAILED;
/* 141 */     this.next = computeNext();
/* 142 */     if (this.state != State.DONE) {
/* 143 */       this.state = State.READY;
/* 144 */       return true;
/*     */     } 
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public final T next() {
/* 151 */     if (!hasNext()) {
/* 152 */       throw new NoSuchElementException();
/*     */     }
/* 154 */     this.state = State.NOT_READY;
/* 155 */     return this.next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T peek() {
/* 166 */     if (!hasNext()) {
/* 167 */       throw new NoSuchElementException();
/*     */     }
/* 169 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\AbstractIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */