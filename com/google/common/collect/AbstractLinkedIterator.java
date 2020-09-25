/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.Beta;
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.NoSuchElementException;
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
/*    */ @Beta
/*    */ @GwtCompatible
/*    */ public abstract class AbstractLinkedIterator<T>
/*    */   extends UnmodifiableIterator<T>
/*    */ {
/*    */   private T nextOrNull;
/*    */   
/*    */   protected AbstractLinkedIterator(@Nullable T firstOrNull) {
/* 46 */     this.nextOrNull = firstOrNull;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract T computeNext(T paramT);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final boolean hasNext() {
/* 59 */     return (this.nextOrNull != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public final T next() {
/* 64 */     if (!hasNext()) {
/* 65 */       throw new NoSuchElementException();
/*    */     }
/*    */     try {
/* 68 */       return this.nextOrNull;
/*    */     } finally {
/* 70 */       this.nextOrNull = computeNext(this.nextOrNull);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\AbstractLinkedIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */