/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
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
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ class RegularImmutableList<E>
/*     */   extends ImmutableList<E>
/*     */ {
/*     */   private final transient int offset;
/*     */   private final transient int size;
/*     */   private final transient Object[] array;
/*     */   
/*     */   RegularImmutableList(Object[] array, int offset, int size) {
/*  39 */     this.offset = offset;
/*  40 */     this.size = size;
/*  41 */     this.array = array;
/*     */   }
/*     */   
/*     */   RegularImmutableList(Object[] array) {
/*  45 */     this(array, 0, array.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  50 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  54 */     return false;
/*     */   }
/*     */   
/*     */   boolean isPartialView() {
/*  58 */     return (this.offset != 0 || this.size != this.array.length);
/*     */   }
/*     */   
/*     */   public boolean contains(@Nullable Object target) {
/*  62 */     return (indexOf(target) != -1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UnmodifiableIterator<E> iterator() {
/*  68 */     return Iterators.forArray((E[])this.array, this.offset, this.size);
/*     */   }
/*     */   
/*     */   public Object[] toArray() {
/*  72 */     Object[] newArray = new Object[size()];
/*  73 */     System.arraycopy(this.array, this.offset, newArray, 0, this.size);
/*  74 */     return newArray;
/*     */   }
/*     */   
/*     */   public <T> T[] toArray(T[] other) {
/*  78 */     if (other.length < this.size) {
/*  79 */       other = ObjectArrays.newArray(other, this.size);
/*  80 */     } else if (other.length > this.size) {
/*  81 */       other[this.size] = null;
/*     */     } 
/*  83 */     System.arraycopy(this.array, this.offset, other, 0, this.size);
/*  84 */     return other;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E get(int index) {
/*  91 */     Preconditions.checkElementIndex(index, this.size);
/*  92 */     return (E)this.array[index + this.offset];
/*     */   }
/*     */   
/*     */   public int indexOf(@Nullable Object target) {
/*  96 */     if (target != null) {
/*  97 */       for (int i = this.offset; i < this.offset + this.size; i++) {
/*  98 */         if (this.array[i].equals(target)) {
/*  99 */           return i - this.offset;
/*     */         }
/*     */       } 
/*     */     }
/* 103 */     return -1;
/*     */   }
/*     */   
/*     */   public int lastIndexOf(@Nullable Object target) {
/* 107 */     if (target != null) {
/* 108 */       for (int i = this.offset + this.size - 1; i >= this.offset; i--) {
/* 109 */         if (this.array[i].equals(target)) {
/* 110 */           return i - this.offset;
/*     */         }
/*     */       } 
/*     */     }
/* 114 */     return -1;
/*     */   }
/*     */   
/*     */   public ImmutableList<E> subList(int fromIndex, int toIndex) {
/* 118 */     Preconditions.checkPositionIndexes(fromIndex, toIndex, this.size);
/* 119 */     return (fromIndex == toIndex) ? ImmutableList.<E>of() : new RegularImmutableList(this.array, this.offset + fromIndex, toIndex - fromIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnmodifiableListIterator<E> listIterator(int start) {
/* 126 */     return new AbstractIndexedListIterator<E>(this.size, start)
/*     */       {
/*     */         protected E get(int index)
/*     */         {
/* 130 */           return (E)RegularImmutableList.this.array[index + RegularImmutableList.this.offset];
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/* 137 */     if (object == this) {
/* 138 */       return true;
/*     */     }
/* 140 */     if (!(object instanceof List)) {
/* 141 */       return false;
/*     */     }
/*     */     
/* 144 */     List<?> that = (List)object;
/* 145 */     if (size() != that.size()) {
/* 146 */       return false;
/*     */     }
/*     */     
/* 149 */     int index = this.offset;
/* 150 */     if (object instanceof RegularImmutableList) {
/* 151 */       RegularImmutableList<?> other = (RegularImmutableList)object;
/* 152 */       for (int i = other.offset; i < other.offset + other.size; i++) {
/* 153 */         if (!this.array[index++].equals(other.array[i])) {
/* 154 */           return false;
/*     */         }
/*     */       } 
/*     */     } else {
/* 158 */       for (Object element : that) {
/* 159 */         if (!this.array[index++].equals(element)) {
/* 160 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 164 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 170 */     int hashCode = 1;
/* 171 */     for (int i = this.offset; i < this.offset + this.size; i++) {
/* 172 */       hashCode = 31 * hashCode + this.array[i].hashCode();
/*     */     }
/* 174 */     return hashCode;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 178 */     StringBuilder sb = Collections2.newStringBuilderForCollection(size()).append('[').append(this.array[this.offset]);
/*     */     
/* 180 */     for (int i = this.offset + 1; i < this.offset + this.size; i++) {
/* 181 */       sb.append(", ").append(this.array[i]);
/*     */     }
/* 183 */     return sb.append(']').toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\RegularImmutableList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */