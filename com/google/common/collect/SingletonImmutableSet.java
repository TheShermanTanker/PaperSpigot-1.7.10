/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ final class SingletonImmutableSet<E>
/*     */   extends ImmutableSet<E>
/*     */ {
/*     */   final transient E element;
/*     */   private transient Integer cachedHashCode;
/*     */   
/*     */   SingletonImmutableSet(E element) {
/*  44 */     this.element = (E)Preconditions.checkNotNull(element);
/*     */   }
/*     */ 
/*     */   
/*     */   SingletonImmutableSet(E element, int hashCode) {
/*  49 */     this.element = element;
/*  50 */     this.cachedHashCode = Integer.valueOf(hashCode);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  55 */     return 1;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  59 */     return false;
/*     */   }
/*     */   
/*     */   public boolean contains(Object target) {
/*  63 */     return this.element.equals(target);
/*     */   }
/*     */   
/*     */   public UnmodifiableIterator<E> iterator() {
/*  67 */     return Iterators.singletonIterator(this.element);
/*     */   }
/*     */   
/*     */   boolean isPartialView() {
/*  71 */     return false;
/*     */   }
/*     */   
/*     */   public Object[] toArray() {
/*  75 */     return new Object[] { this.element };
/*     */   }
/*     */   
/*     */   public <T> T[] toArray(T[] array) {
/*  79 */     if (array.length == 0) {
/*  80 */       array = ObjectArrays.newArray(array, 1);
/*  81 */     } else if (array.length > 1) {
/*  82 */       array[1] = null;
/*     */     } 
/*     */     
/*  85 */     T[] arrayOfT = array;
/*  86 */     arrayOfT[0] = (T)this.element;
/*  87 */     return array;
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/*  91 */     if (object == this) {
/*  92 */       return true;
/*     */     }
/*  94 */     if (object instanceof Set) {
/*  95 */       Set<?> that = (Set)object;
/*  96 */       return (that.size() == 1 && this.element.equals(that.iterator().next()));
/*     */     } 
/*  98 */     return false;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/* 102 */     Integer code = this.cachedHashCode;
/* 103 */     if (code == null) {
/* 104 */       return (this.cachedHashCode = Integer.valueOf(this.element.hashCode())).intValue();
/*     */     }
/* 106 */     return code.intValue();
/*     */   }
/*     */   
/*     */   boolean isHashCodeFast() {
/* 110 */     return false;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 114 */     String elementToString = this.element.toString();
/* 115 */     return (new StringBuilder(elementToString.length() + 2)).append('[').append(elementToString).append(']').toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\SingletonImmutableSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */