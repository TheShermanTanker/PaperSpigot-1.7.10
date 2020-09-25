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
/*     */ @GwtCompatible
/*     */ abstract class AbstractIndexedListIterator<E>
/*     */   extends UnmodifiableListIterator<E>
/*     */ {
/*     */   private final int size;
/*     */   private int position;
/*     */   
/*     */   protected abstract E get(int paramInt);
/*     */   
/*     */   protected AbstractIndexedListIterator(int size) {
/*  54 */     this(size, 0);
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
/*     */   protected AbstractIndexedListIterator(int size, int position) {
/*  69 */     Preconditions.checkPositionIndex(position, size);
/*  70 */     this.size = size;
/*  71 */     this.position = position;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean hasNext() {
/*  76 */     return (this.position < this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public final E next() {
/*  81 */     if (!hasNext()) {
/*  82 */       throw new NoSuchElementException();
/*     */     }
/*  84 */     return get(this.position++);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int nextIndex() {
/*  89 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean hasPrevious() {
/*  94 */     return (this.position > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public final E previous() {
/*  99 */     if (!hasPrevious()) {
/* 100 */       throw new NoSuchElementException();
/*     */     }
/* 102 */     return get(--this.position);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int previousIndex() {
/* 107 */     return this.position - 1;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\AbstractIndexedListIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */