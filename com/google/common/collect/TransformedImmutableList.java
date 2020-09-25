/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
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
/*     */ @GwtCompatible
/*     */ abstract class TransformedImmutableList<D, E>
/*     */   extends ImmutableList<E>
/*     */ {
/*     */   private final transient ImmutableList<D> backingList;
/*     */   
/*     */   private class TransformedView
/*     */     extends TransformedImmutableList<D, E>
/*     */   {
/*     */     TransformedView(ImmutableList<D> backingList) {
/*  37 */       super(backingList);
/*     */     }
/*     */     
/*     */     E transform(D d) {
/*  41 */       return TransformedImmutableList.this.transform(d);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   TransformedImmutableList(ImmutableList<D> backingList) {
/*  48 */     this.backingList = (ImmutableList<D>)Preconditions.checkNotNull(backingList);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(@Nullable Object object) {
/*  54 */     if (object == null) {
/*  55 */       return -1;
/*     */     }
/*  57 */     for (int i = 0; i < size(); i++) {
/*  58 */       if (get(i).equals(object)) {
/*  59 */         return i;
/*     */       }
/*     */     } 
/*  62 */     return -1;
/*     */   }
/*     */   
/*     */   public int lastIndexOf(@Nullable Object object) {
/*  66 */     if (object == null) {
/*  67 */       return -1;
/*     */     }
/*  69 */     for (int i = size() - 1; i >= 0; i--) {
/*  70 */       if (get(i).equals(object)) {
/*  71 */         return i;
/*     */       }
/*     */     } 
/*  74 */     return -1;
/*     */   }
/*     */   
/*     */   public E get(int index) {
/*  78 */     return transform(this.backingList.get(index));
/*     */   }
/*     */   
/*     */   public UnmodifiableListIterator<E> listIterator(int index) {
/*  82 */     return new AbstractIndexedListIterator<E>(size(), index) {
/*     */         protected E get(int index) {
/*  84 */           return (E)TransformedImmutableList.this.get(index);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public int size() {
/*  90 */     return this.backingList.size();
/*     */   }
/*     */   
/*     */   public ImmutableList<E> subList(int fromIndex, int toIndex) {
/*  94 */     return new TransformedView(this.backingList.subList(fromIndex, toIndex));
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object obj) {
/*  98 */     if (obj == this) {
/*  99 */       return true;
/*     */     }
/* 101 */     if (obj instanceof List) {
/* 102 */       List<?> list = (List)obj;
/* 103 */       return (size() == list.size() && Iterators.elementsEqual(iterator(), list.iterator()));
/*     */     } 
/*     */     
/* 106 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 110 */     int hashCode = 1;
/* 111 */     for (E e : this) {
/* 112 */       hashCode = 31 * hashCode + ((e == null) ? 0 : e.hashCode());
/*     */     }
/* 114 */     return hashCode;
/*     */   }
/*     */   
/*     */   public Object[] toArray() {
/* 118 */     return ObjectArrays.toArrayImpl(this);
/*     */   }
/*     */   
/*     */   public <T> T[] toArray(T[] array) {
/* 122 */     return ObjectArrays.toArrayImpl(this, array);
/*     */   }
/*     */   
/*     */   boolean isPartialView() {
/* 126 */     return true;
/*     */   }
/*     */   
/*     */   abstract E transform(D paramD);
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\TransformedImmutableList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */