/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.RandomAccess;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public abstract class ImmutableList<E>
/*     */   extends ImmutableCollection<E>
/*     */   implements List<E>, RandomAccess
/*     */ {
/*     */   public static <E> ImmutableList<E> of() {
/*  68 */     return EmptyImmutableList.INSTANCE;
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
/*     */   public static <E> ImmutableList<E> of(E element) {
/*  80 */     return new SingletonImmutableList<E>(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableList<E> of(E e1, E e2) {
/*  89 */     return construct(new Object[] { e1, e2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableList<E> of(E e1, E e2, E e3) {
/*  98 */     return construct(new Object[] { e1, e2, e3 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4) {
/* 107 */     return construct(new Object[] { e1, e2, e3, e4 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5) {
/* 116 */     return construct(new Object[] { e1, e2, e3, e4, e5 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6) {
/* 125 */     return construct(new Object[] { e1, e2, e3, e4, e5, e6 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
/* 135 */     return construct(new Object[] { e1, e2, e3, e4, e5, e6, e7 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
/* 145 */     return construct(new Object[] { e1, e2, e3, e4, e5, e6, e7, e8 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
/* 155 */     return construct(new Object[] { e1, e2, e3, e4, e5, e6, e7, e8, e9 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
/* 165 */     return construct(new Object[] { e1, e2, e3, e4, e5, e6, e7, e8, e9, e10 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11) {
/* 175 */     return construct(new Object[] { e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11 });
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
/*     */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12, E... others) {
/* 190 */     Object[] array = new Object[12 + others.length];
/* 191 */     array[0] = e1;
/* 192 */     array[1] = e2;
/* 193 */     array[2] = e3;
/* 194 */     array[3] = e4;
/* 195 */     array[4] = e5;
/* 196 */     array[5] = e6;
/* 197 */     array[6] = e7;
/* 198 */     array[7] = e8;
/* 199 */     array[8] = e9;
/* 200 */     array[9] = e10;
/* 201 */     array[10] = e11;
/* 202 */     array[11] = e12;
/* 203 */     System.arraycopy(others, 0, array, 12, others.length);
/* 204 */     return construct(array);
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
/*     */   @Deprecated
/*     */   public static <E> ImmutableList<E> of(E[] elements) {
/* 217 */     return copyOf(elements);
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
/*     */   public static <E> ImmutableList<E> copyOf(Iterable<? extends E> elements) {
/* 229 */     Preconditions.checkNotNull(elements);
/* 230 */     return (elements instanceof Collection) ? copyOf(Collections2.cast(elements)) : copyOf(elements.iterator());
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
/*     */   public static <E> ImmutableList<E> copyOf(Collection<? extends E> elements) {
/* 255 */     if (elements instanceof ImmutableCollection) {
/*     */       
/* 257 */       ImmutableList<E> list = ((ImmutableCollection)elements).asList();
/* 258 */       return list.isPartialView() ? copyFromCollection(list) : list;
/*     */     } 
/* 260 */     return copyFromCollection(elements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableList<E> copyOf(Iterator<? extends E> elements) {
/* 269 */     return copyFromCollection(Lists.newArrayList(elements));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableList<E> copyOf(E[] elements) {
/* 279 */     switch (elements.length) {
/*     */       case 0:
/* 281 */         return of();
/*     */       case 1:
/* 283 */         return new SingletonImmutableList<E>(elements[0]);
/*     */     } 
/* 285 */     return construct((Object[])elements.clone());
/*     */   }
/*     */ 
/*     */   
/*     */   private static <E> ImmutableList<E> copyFromCollection(Collection<? extends E> collection) {
/*     */     ImmutableList<E> list;
/* 291 */     Object[] elements = collection.toArray();
/* 292 */     switch (elements.length) {
/*     */       case 0:
/* 294 */         return of();
/*     */       
/*     */       case 1:
/* 297 */         list = new SingletonImmutableList<E>((E)elements[0]);
/* 298 */         return list;
/*     */     } 
/*     */ 
/*     */     
/* 302 */     return construct(elements);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E> ImmutableList<E> construct(Object... elements) {
/* 308 */     for (int i = 0; i < elements.length; i++) {
/* 309 */       checkElementNotNull(elements[i], i);
/*     */     }
/* 311 */     return new RegularImmutableList<E>(elements);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object checkElementNotNull(Object element, int index) {
/* 317 */     if (element == null) {
/* 318 */       throw new NullPointerException("at index " + index);
/*     */     }
/* 320 */     return element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnmodifiableIterator<E> iterator() {
/* 328 */     return listIterator();
/*     */   }
/*     */   
/*     */   public UnmodifiableListIterator<E> listIterator() {
/* 332 */     return listIterator(0);
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
/*     */   public final boolean addAll(int index, Collection<? extends E> newElements) {
/* 363 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final E set(int index, E element) {
/* 373 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(int index, E element) {
/* 383 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final E remove(int index) {
/* 393 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableList<E> asList() {
/* 402 */     return this;
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
/*     */   public ImmutableList<E> reverse() {
/* 414 */     return new ReverseImmutableList<E>(this);
/*     */   }
/*     */   
/*     */   private static class ReverseImmutableList<E> extends ImmutableList<E> {
/*     */     private final transient ImmutableList<E> forwardList;
/*     */     private final transient int size;
/*     */     
/*     */     ReverseImmutableList(ImmutableList<E> backingList) {
/* 422 */       this.forwardList = backingList;
/* 423 */       this.size = backingList.size();
/*     */     }
/*     */     
/*     */     private int reverseIndex(int index) {
/* 427 */       return this.size - 1 - index;
/*     */     }
/*     */     
/*     */     private int reversePosition(int index) {
/* 431 */       return this.size - index;
/*     */     }
/*     */     
/*     */     public ImmutableList<E> reverse() {
/* 435 */       return this.forwardList;
/*     */     }
/*     */     
/*     */     public boolean contains(@Nullable Object object) {
/* 439 */       return this.forwardList.contains(object);
/*     */     }
/*     */     
/*     */     public boolean containsAll(Collection<?> targets) {
/* 443 */       return this.forwardList.containsAll(targets);
/*     */     }
/*     */     
/*     */     public int indexOf(@Nullable Object object) {
/* 447 */       int index = this.forwardList.lastIndexOf(object);
/* 448 */       return (index >= 0) ? reverseIndex(index) : -1;
/*     */     }
/*     */     
/*     */     public int lastIndexOf(@Nullable Object object) {
/* 452 */       int index = this.forwardList.indexOf(object);
/* 453 */       return (index >= 0) ? reverseIndex(index) : -1;
/*     */     }
/*     */     
/*     */     public ImmutableList<E> subList(int fromIndex, int toIndex) {
/* 457 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, this.size);
/* 458 */       return this.forwardList.subList(reversePosition(toIndex), reversePosition(fromIndex)).reverse();
/*     */     }
/*     */ 
/*     */     
/*     */     public E get(int index) {
/* 463 */       Preconditions.checkElementIndex(index, this.size);
/* 464 */       return this.forwardList.get(reverseIndex(index));
/*     */     }
/*     */     
/*     */     public UnmodifiableListIterator<E> listIterator(int index) {
/* 468 */       Preconditions.checkPositionIndex(index, this.size);
/* 469 */       final UnmodifiableListIterator<E> forward = this.forwardList.listIterator(reversePosition(index));
/*     */       
/* 471 */       return new UnmodifiableListIterator<E>() {
/*     */           public boolean hasNext() {
/* 473 */             return forward.hasPrevious();
/*     */           }
/*     */           
/*     */           public boolean hasPrevious() {
/* 477 */             return forward.hasNext();
/*     */           }
/*     */           
/*     */           public E next() {
/* 481 */             return forward.previous();
/*     */           }
/*     */           
/*     */           public int nextIndex() {
/* 485 */             return ImmutableList.ReverseImmutableList.this.reverseIndex(forward.previousIndex());
/*     */           }
/*     */           
/*     */           public E previous() {
/* 489 */             return forward.next();
/*     */           }
/*     */           
/*     */           public int previousIndex() {
/* 493 */             return ImmutableList.ReverseImmutableList.this.reverseIndex(forward.nextIndex());
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 499 */       return this.size;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 503 */       return this.forwardList.isEmpty();
/*     */     }
/*     */     
/*     */     boolean isPartialView() {
/* 507 */       return this.forwardList.isPartialView();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 512 */     return Lists.equalsImpl(this, obj);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 516 */     return Lists.hashCodeImpl(this);
/*     */   }
/*     */   
/*     */   private static class SerializedForm
/*     */     implements Serializable
/*     */   {
/*     */     final Object[] elements;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     SerializedForm(Object[] elements) {
/* 526 */       this.elements = elements;
/*     */     }
/*     */     Object readResolve() {
/* 529 */       return ImmutableList.copyOf(this.elements);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
/* 536 */     throw new InvalidObjectException("Use SerializedForm");
/*     */   }
/*     */   
/*     */   Object writeReplace() {
/* 540 */     return new SerializedForm(toArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> Builder<E> builder() {
/* 548 */     return new Builder<E>();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract UnmodifiableListIterator<E> listIterator(int paramInt);
/*     */ 
/*     */   
/*     */   public abstract int indexOf(@Nullable Object paramObject);
/*     */ 
/*     */   
/*     */   public abstract int lastIndexOf(@Nullable Object paramObject);
/*     */ 
/*     */   
/*     */   public abstract ImmutableList<E> subList(int paramInt1, int paramInt2);
/*     */ 
/*     */   
/*     */   public static final class Builder<E>
/*     */     extends ImmutableCollection.Builder<E>
/*     */   {
/* 568 */     private final ArrayList<E> contents = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<E> add(E element) {
/* 584 */       this.contents.add((E)Preconditions.checkNotNull(element));
/* 585 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<E> addAll(Iterable<? extends E> elements) {
/* 597 */       if (elements instanceof Collection) {
/* 598 */         Collection<?> collection = (Collection)elements;
/* 599 */         this.contents.ensureCapacity(this.contents.size() + collection.size());
/*     */       } 
/* 601 */       super.addAll(elements);
/* 602 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<E> add(E... elements) {
/* 614 */       this.contents.ensureCapacity(this.contents.size() + elements.length);
/* 615 */       super.add(elements);
/* 616 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<E> addAll(Iterator<? extends E> elements) {
/* 628 */       super.addAll(elements);
/* 629 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ImmutableList<E> build() {
/* 637 */       return ImmutableList.copyOf(this.contents);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ImmutableList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */