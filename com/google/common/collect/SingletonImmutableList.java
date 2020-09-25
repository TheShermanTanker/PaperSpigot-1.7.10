/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
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
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ final class SingletonImmutableList<E>
/*     */   extends ImmutableList<E>
/*     */ {
/*     */   final transient E element;
/*     */   
/*     */   SingletonImmutableList(E element) {
/*  41 */     this.element = (E)Preconditions.checkNotNull(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public E get(int index) {
/*  46 */     Preconditions.checkElementIndex(index, 1);
/*  47 */     return this.element;
/*     */   }
/*     */   
/*     */   public int indexOf(@Nullable Object object) {
/*  51 */     return this.element.equals(object) ? 0 : -1;
/*     */   }
/*     */   
/*     */   public UnmodifiableIterator<E> iterator() {
/*  55 */     return Iterators.singletonIterator(this.element);
/*     */   }
/*     */   
/*     */   public int lastIndexOf(@Nullable Object object) {
/*  59 */     return this.element.equals(object) ? 0 : -1;
/*     */   }
/*     */   
/*     */   public UnmodifiableListIterator<E> listIterator(final int start) {
/*  63 */     Preconditions.checkPositionIndex(start, 1);
/*  64 */     return new UnmodifiableListIterator<E>()
/*     */       {
/*  66 */         boolean hasNext = (start == 0);
/*     */         
/*     */         public boolean hasNext() {
/*  69 */           return this.hasNext;
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/*  73 */           return !this.hasNext;
/*     */         }
/*     */         
/*     */         public E next() {
/*  77 */           if (!this.hasNext) {
/*  78 */             throw new NoSuchElementException();
/*     */           }
/*  80 */           this.hasNext = false;
/*  81 */           return SingletonImmutableList.this.element;
/*     */         }
/*     */         
/*     */         public int nextIndex() {
/*  85 */           return this.hasNext ? 0 : 1;
/*     */         }
/*     */         
/*     */         public E previous() {
/*  89 */           if (this.hasNext) {
/*  90 */             throw new NoSuchElementException();
/*     */           }
/*  92 */           this.hasNext = true;
/*  93 */           return SingletonImmutableList.this.element;
/*     */         }
/*     */         
/*     */         public int previousIndex() {
/*  97 */           return this.hasNext ? -1 : 0;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 104 */     return 1;
/*     */   }
/*     */   
/*     */   public ImmutableList<E> subList(int fromIndex, int toIndex) {
/* 108 */     Preconditions.checkPositionIndexes(fromIndex, toIndex, 1);
/* 109 */     return (fromIndex == toIndex) ? ImmutableList.<E>of() : this;
/*     */   }
/*     */   
/*     */   public ImmutableList<E> reverse() {
/* 113 */     return this;
/*     */   }
/*     */   
/*     */   public boolean contains(@Nullable Object object) {
/* 117 */     return this.element.equals(object);
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 121 */     if (object == this) {
/* 122 */       return true;
/*     */     }
/* 124 */     if (object instanceof List) {
/* 125 */       List<?> that = (List)object;
/* 126 */       return (that.size() == 1 && this.element.equals(that.get(0)));
/*     */     } 
/* 128 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 134 */     return 31 + this.element.hashCode();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 138 */     String elementToString = this.element.toString();
/* 139 */     return (new StringBuilder(elementToString.length() + 2)).append('[').append(elementToString).append(']').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 147 */     return false;
/*     */   }
/*     */   
/*     */   boolean isPartialView() {
/* 151 */     return false;
/*     */   }
/*     */   
/*     */   public Object[] toArray() {
/* 155 */     return new Object[] { this.element };
/*     */   }
/*     */   
/*     */   public <T> T[] toArray(T[] array) {
/* 159 */     if (array.length == 0) {
/* 160 */       array = ObjectArrays.newArray(array, 1);
/* 161 */     } else if (array.length > 1) {
/* 162 */       array[1] = null;
/*     */     } 
/*     */     
/* 165 */     T[] arrayOfT = array;
/* 166 */     arrayOfT[0] = (T)this.element;
/* 167 */     return array;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\SingletonImmutableList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */