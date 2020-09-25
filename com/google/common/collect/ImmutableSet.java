/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public abstract class ImmutableSet<E>
/*     */   extends ImmutableCollection<E>
/*     */   implements Set<E>
/*     */ {
/*     */   static final int MAX_TABLE_SIZE = 1073741824;
/*     */   static final int CUTOFF = 536870912;
/*     */   
/*     */   public static <E> ImmutableSet<E> of() {
/*  78 */     return EmptyImmutableSet.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableSet<E> of(E element) {
/*  88 */     return new SingletonImmutableSet<E>(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableSet<E> of(E e1, E e2) {
/*  99 */     return construct(new Object[] { e1, e2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableSet<E> of(E e1, E e2, E e3) {
/* 110 */     return construct(new Object[] { e1, e2, e3 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableSet<E> of(E e1, E e2, E e3, E e4) {
/* 121 */     return construct(new Object[] { e1, e2, e3, e4 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableSet<E> of(E e1, E e2, E e3, E e4, E e5) {
/* 132 */     return construct(new Object[] { e1, e2, e3, e4, e5 });
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
/*     */   public static <E> ImmutableSet<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E... others) {
/* 145 */     int paramCount = 6;
/* 146 */     Object[] elements = new Object[6 + others.length];
/* 147 */     elements[0] = e1;
/* 148 */     elements[1] = e2;
/* 149 */     elements[2] = e3;
/* 150 */     elements[3] = e4;
/* 151 */     elements[4] = e5;
/* 152 */     elements[5] = e6;
/* 153 */     for (int i = 6; i < elements.length; i++) {
/* 154 */       elements[i] = others[i - 6];
/*     */     }
/* 156 */     return construct(elements);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <E> ImmutableSet<E> construct(Object... elements) {
/* 161 */     int tableSize = chooseTableSize(elements.length);
/* 162 */     Object[] table = new Object[tableSize];
/* 163 */     int mask = tableSize - 1;
/* 164 */     ArrayList<Object> uniqueElementsList = null;
/* 165 */     int hashCode = 0;
/* 166 */     for (int i = 0; i < elements.length; i++) {
/* 167 */       Object element = elements[i];
/* 168 */       int hash = element.hashCode();
/* 169 */       for (int j = Hashing.smear(hash);; j++) {
/* 170 */         int index = j & mask;
/* 171 */         Object value = table[index];
/* 172 */         if (value == null) {
/* 173 */           if (uniqueElementsList != null) {
/* 174 */             uniqueElementsList.add(element);
/*     */           }
/*     */           
/* 177 */           table[index] = element;
/* 178 */           hashCode += hash; break;
/*     */         } 
/* 180 */         if (value.equals(element)) {
/* 181 */           if (uniqueElementsList == null) {
/*     */             
/* 183 */             uniqueElementsList = new ArrayList(elements.length);
/* 184 */             for (int k = 0; k < i; k++) {
/* 185 */               Object previous = elements[k];
/* 186 */               uniqueElementsList.add(previous);
/*     */             } 
/*     */           } 
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 193 */     Object[] uniqueElements = (uniqueElementsList == null) ? elements : uniqueElementsList.toArray();
/*     */ 
/*     */     
/* 196 */     if (uniqueElements.length == 1) {
/*     */ 
/*     */       
/* 199 */       E element = (E)uniqueElements[0];
/* 200 */       return new SingletonImmutableSet<E>(element, hashCode);
/* 201 */     }  if (tableSize > 2 * chooseTableSize(uniqueElements.length))
/*     */     {
/*     */       
/* 204 */       return construct(uniqueElements);
/*     */     }
/* 206 */     return new RegularImmutableSet<E>(uniqueElements, hashCode, table, mask);
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
/*     */   static int chooseTableSize(int setSize) {
/* 223 */     if (setSize < 536870912) {
/* 224 */       return Integer.highestOneBit(setSize) << 2;
/*     */     }
/*     */ 
/*     */     
/* 228 */     Preconditions.checkArgument((setSize < 1073741824), "collection too large");
/* 229 */     return 1073741824;
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
/*     */   @Deprecated
/*     */   public static <E> ImmutableSet<E> of(E[] elements) {
/* 245 */     return copyOf(elements);
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
/*     */   public static <E> ImmutableSet<E> copyOf(E[] elements) {
/* 259 */     switch (elements.length) {
/*     */       case 0:
/* 261 */         return of();
/*     */       case 1:
/* 263 */         return of(elements[0]);
/*     */     } 
/* 265 */     return construct((Object[])elements.clone());
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
/*     */   public static <E> ImmutableSet<E> copyOf(Iterable<? extends E> elements) {
/* 287 */     return (elements instanceof Collection) ? copyOf(Collections2.cast(elements)) : copyOf(elements.iterator());
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
/*     */   public static <E> ImmutableSet<E> copyOf(Iterator<? extends E> elements) {
/* 302 */     return copyFromCollection(Lists.newArrayList(elements));
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
/*     */   public static <E> ImmutableSet<E> copyOf(Collection<? extends E> elements) {
/* 337 */     if (elements instanceof ImmutableSet && !(elements instanceof ImmutableSortedSet)) {
/*     */ 
/*     */       
/* 340 */       ImmutableSet<E> set = (ImmutableSet)elements;
/* 341 */       if (!set.isPartialView()) {
/* 342 */         return set;
/*     */       }
/*     */     } 
/* 345 */     return copyFromCollection(elements);
/*     */   }
/*     */   
/*     */   private static <E> ImmutableSet<E> copyFromCollection(Collection<? extends E> collection) {
/*     */     E onlyElement;
/* 350 */     Object[] elements = collection.toArray();
/* 351 */     switch (elements.length) {
/*     */       case 0:
/* 353 */         return of();
/*     */       
/*     */       case 1:
/* 356 */         onlyElement = (E)elements[0];
/* 357 */         return of(onlyElement);
/*     */     } 
/*     */ 
/*     */     
/* 361 */     return construct(elements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isHashCodeFast() {
/* 369 */     return false;
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/* 373 */     if (object == this) {
/* 374 */       return true;
/*     */     }
/* 376 */     if (object instanceof ImmutableSet && isHashCodeFast() && ((ImmutableSet)object).isHashCodeFast() && hashCode() != object.hashCode())
/*     */     {
/*     */ 
/*     */       
/* 380 */       return false;
/*     */     }
/* 382 */     return Sets.equalsImpl(this, object);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 386 */     return Sets.hashCodeImpl(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class ArrayImmutableSet<E>
/*     */     extends ImmutableSet<E>
/*     */   {
/*     */     final transient Object[] elements;
/*     */ 
/*     */     
/*     */     ArrayImmutableSet(Object[] elements) {
/* 398 */       this.elements = elements;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 403 */       return this.elements.length;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 407 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public UnmodifiableIterator<E> iterator() {
/* 416 */       return Iterators.forArray((E[])this.elements);
/*     */     }
/*     */     
/*     */     public Object[] toArray() {
/* 420 */       Object[] array = new Object[size()];
/* 421 */       System.arraycopy(this.elements, 0, array, 0, size());
/* 422 */       return array;
/*     */     }
/*     */     
/*     */     public <T> T[] toArray(T[] array) {
/* 426 */       int size = size();
/* 427 */       if (array.length < size) {
/* 428 */         array = ObjectArrays.newArray(array, size);
/* 429 */       } else if (array.length > size) {
/* 430 */         array[size] = null;
/*     */       } 
/* 432 */       System.arraycopy(this.elements, 0, array, 0, size);
/* 433 */       return array;
/*     */     }
/*     */     
/*     */     public boolean containsAll(Collection<?> targets) {
/* 437 */       if (targets == this) {
/* 438 */         return true;
/*     */       }
/* 440 */       if (!(targets instanceof ArrayImmutableSet)) {
/* 441 */         return super.containsAll(targets);
/*     */       }
/* 443 */       if (targets.size() > size()) {
/* 444 */         return false;
/*     */       }
/* 446 */       for (Object target : ((ArrayImmutableSet)targets).elements) {
/* 447 */         if (!contains(target)) {
/* 448 */           return false;
/*     */         }
/*     */       } 
/* 451 */       return true;
/*     */     }
/*     */     
/*     */     boolean isPartialView() {
/* 455 */       return false;
/*     */     }
/*     */     
/*     */     ImmutableList<E> createAsList() {
/* 459 */       return new ImmutableAsList<E>(this.elements, this);
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract class TransformedImmutableSet<D, E>
/*     */     extends ImmutableSet<E> {
/*     */     final D[] source;
/*     */     final int hashCode;
/*     */     
/*     */     TransformedImmutableSet(D[] source, int hashCode) {
/* 469 */       this.source = source;
/* 470 */       this.hashCode = hashCode;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int size() {
/* 477 */       return this.source.length;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 481 */       return false;
/*     */     }
/*     */     
/*     */     public UnmodifiableIterator<E> iterator() {
/* 485 */       return new AbstractIndexedListIterator<E>(this.source.length) {
/*     */           protected E get(int index) {
/* 487 */             return ImmutableSet.TransformedImmutableSet.this.transform(ImmutableSet.TransformedImmutableSet.this.source[index]);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public Object[] toArray() {
/* 493 */       return toArray(new Object[size()]);
/*     */     }
/*     */     
/*     */     public <T> T[] toArray(T[] array) {
/* 497 */       int size = size();
/* 498 */       if (array.length < size) {
/* 499 */         array = ObjectArrays.newArray(array, size);
/* 500 */       } else if (array.length > size) {
/* 501 */         array[size] = null;
/*     */       } 
/*     */ 
/*     */       
/* 505 */       T[] arrayOfT = array;
/* 506 */       for (int i = 0; i < this.source.length; i++) {
/* 507 */         arrayOfT[i] = (T)transform(this.source[i]);
/*     */       }
/* 509 */       return array;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/* 513 */       return this.hashCode;
/*     */     }
/*     */     
/*     */     boolean isHashCodeFast() {
/* 517 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     abstract E transform(D param1D);
/*     */   }
/*     */   
/*     */   private static class SerializedForm
/*     */     implements Serializable
/*     */   {
/*     */     final Object[] elements;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     SerializedForm(Object[] elements) {
/* 531 */       this.elements = elements;
/*     */     }
/*     */     Object readResolve() {
/* 534 */       return ImmutableSet.copyOf(this.elements);
/*     */     }
/*     */   }
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract UnmodifiableIterator<E> iterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Builder<E>
/*     */     extends ImmutableCollection.Builder<E>
/*     */   {
/* 569 */     final ArrayList<E> contents = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 587 */       this.contents.add((E)Preconditions.checkNotNull(element));
/* 588 */       return this;
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
/*     */     
/*     */     public Builder<E> add(E... elements) {
/* 601 */       this.contents.ensureCapacity(this.contents.size() + elements.length);
/* 602 */       super.add(elements);
/* 603 */       return this;
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
/*     */     
/*     */     public Builder<E> addAll(Iterable<? extends E> elements) {
/* 616 */       if (elements instanceof Collection) {
/* 617 */         Collection<?> collection = (Collection)elements;
/* 618 */         this.contents.ensureCapacity(this.contents.size() + collection.size());
/*     */       } 
/* 620 */       super.addAll(elements);
/* 621 */       return this;
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
/*     */     
/*     */     public Builder<E> addAll(Iterator<? extends E> elements) {
/* 634 */       super.addAll(elements);
/* 635 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ImmutableSet<E> build() {
/* 643 */       return ImmutableSet.copyOf(this.contents);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ImmutableSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */