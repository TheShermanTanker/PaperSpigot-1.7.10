/*     */ package net.minecraft.util.com.google.common.collect;
/*     */ 
/*     */ import java.util.NoSuchElementException;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
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
/*     */ 
/*     */ 
/*     */ @GwtCompatible
/*     */ public abstract class AbstractIterator<T>
/*     */   extends UnmodifiableIterator<T>
/*     */ {
/*  65 */   private State state = State.NOT_READY;
/*     */   private T next;
/*     */   
/*     */   protected abstract T computeNext();
/*     */   
/*     */   private enum State
/*     */   {
/*  72 */     READY,
/*     */ 
/*     */     
/*  75 */     NOT_READY,
/*     */ 
/*     */     
/*  78 */     DONE,
/*     */ 
/*     */     
/*  81 */     FAILED;
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
/* 124 */     this.state = State.DONE;
/* 125 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean hasNext() {
/* 130 */     Preconditions.checkState((this.state != State.FAILED));
/* 131 */     switch (this.state) {
/*     */       case DONE:
/* 133 */         return false;
/*     */       case READY:
/* 135 */         return true;
/*     */     } 
/*     */     
/* 138 */     return tryToComputeNext();
/*     */   }
/*     */   
/*     */   private boolean tryToComputeNext() {
/* 142 */     this.state = State.FAILED;
/* 143 */     this.next = computeNext();
/* 144 */     if (this.state != State.DONE) {
/* 145 */       this.state = State.READY;
/* 146 */       return true;
/*     */     } 
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public final T next() {
/* 153 */     if (!hasNext()) {
/* 154 */       throw new NoSuchElementException();
/*     */     }
/* 156 */     this.state = State.NOT_READY;
/* 157 */     T result = this.next;
/* 158 */     this.next = null;
/* 159 */     return result;
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
/* 170 */     if (!hasNext()) {
/* 171 */       throw new NoSuchElementException();
/*     */     }
/* 173 */     return this.next;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\AbstractIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */