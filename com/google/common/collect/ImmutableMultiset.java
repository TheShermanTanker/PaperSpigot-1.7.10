/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.primitives.Ints;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ @GwtCompatible(serializable = true)
/*     */ public abstract class ImmutableMultiset<E>
/*     */   extends ImmutableCollection<E>
/*     */   implements Multiset<E>
/*     */ {
/*     */   private transient ImmutableSet<Multiset.Entry<E>> entrySet;
/*     */   
/*     */   public static <E> ImmutableMultiset<E> of() {
/*  58 */     return EmptyImmutableMultiset.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableMultiset<E> of(E element) {
/*  69 */     return copyOfInternal((E[])new Object[] { element });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableMultiset<E> of(E e1, E e2) {
/*  80 */     return copyOfInternal((E[])new Object[] { e1, e2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableMultiset<E> of(E e1, E e2, E e3) {
/*  91 */     return copyOfInternal((E[])new Object[] { e1, e2, e3 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableMultiset<E> of(E e1, E e2, E e3, E e4) {
/* 102 */     return copyOfInternal((E[])new Object[] { e1, e2, e3, e4 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableMultiset<E> of(E e1, E e2, E e3, E e4, E e5) {
/* 113 */     return copyOfInternal((E[])new Object[] { e1, e2, e3, e4, e5 });
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
/*     */   public static <E> ImmutableMultiset<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E... others) {
/* 125 */     int size = others.length + 6;
/* 126 */     List<E> all = new ArrayList<E>(size);
/* 127 */     Collections.addAll(all, (E[])new Object[] { e1, e2, e3, e4, e5, e6 });
/* 128 */     Collections.addAll(all, others);
/* 129 */     return copyOf(all);
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
/*     */   @Deprecated
/*     */   public static <E> ImmutableMultiset<E> of(E[] elements) {
/* 146 */     return copyOf(Arrays.asList(elements));
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
/*     */   public static <E> ImmutableMultiset<E> copyOf(E[] elements) {
/* 160 */     return copyOf(Arrays.asList(elements));
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
/*     */   public static <E> ImmutableMultiset<E> copyOf(Iterable<? extends E> elements) {
/* 182 */     if (elements instanceof ImmutableMultiset) {
/*     */       
/* 184 */       ImmutableMultiset<E> result = (ImmutableMultiset)elements;
/* 185 */       if (!result.isPartialView()) {
/* 186 */         return result;
/*     */       }
/*     */     } 
/*     */     
/* 190 */     Multiset<? extends E> multiset = (elements instanceof Multiset) ? Multisets.<E>cast(elements) : LinkedHashMultiset.<E>create(elements);
/*     */ 
/*     */ 
/*     */     
/* 194 */     return copyOfInternal(multiset);
/*     */   }
/*     */   
/*     */   private static <E> ImmutableMultiset<E> copyOfInternal(E... elements) {
/* 198 */     return copyOf(Arrays.asList(elements));
/*     */   }
/*     */ 
/*     */   
/*     */   private static <E> ImmutableMultiset<E> copyOfInternal(Multiset<? extends E> multiset) {
/* 203 */     long size = 0L;
/* 204 */     ImmutableMap.Builder<E, Integer> builder = ImmutableMap.builder();
/*     */     
/* 206 */     for (Multiset.Entry<? extends E> entry : multiset.entrySet()) {
/* 207 */       int count = entry.getCount();
/* 208 */       if (count > 0) {
/*     */ 
/*     */         
/* 211 */         builder.put(entry.getElement(), Integer.valueOf(count));
/* 212 */         size += count;
/*     */       } 
/*     */     } 
/*     */     
/* 216 */     if (size == 0L) {
/* 217 */       return of();
/*     */     }
/* 219 */     return new RegularImmutableMultiset<E>(builder.build(), Ints.saturatedCast(size));
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
/*     */   public static <E> ImmutableMultiset<E> copyOf(Iterator<? extends E> elements) {
/* 235 */     Multiset<E> multiset = LinkedHashMultiset.create();
/* 236 */     Iterators.addAll(multiset, elements);
/* 237 */     return copyOfInternal(multiset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UnmodifiableIterator<E> iterator() {
/* 243 */     final Iterator<Multiset.Entry<E>> entryIterator = entryIterator();
/*     */     
/* 245 */     return new UnmodifiableIterator<E>()
/*     */       {
/*     */         int remaining;
/*     */         E element;
/*     */         
/*     */         public boolean hasNext() {
/* 251 */           return (this.remaining > 0 || entryIterator.hasNext());
/*     */         }
/*     */ 
/*     */         
/*     */         public E next() {
/* 256 */           if (this.remaining <= 0) {
/* 257 */             Multiset.Entry<E> entry = entryIterator.next();
/* 258 */             this.element = entry.getElement();
/* 259 */             this.remaining = entry.getCount();
/*     */           } 
/* 261 */           this.remaining--;
/* 262 */           return this.element;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(@Nullable Object object) {
/* 269 */     return (count(object) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(Collection<?> targets) {
/* 274 */     return elementSet().containsAll(targets);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int add(E element, int occurrences) {
/* 284 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int remove(Object element, int occurrences) {
/* 294 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int setCount(E element, int count) {
/* 304 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean setCount(E element, int oldCount, int newCount) {
/* 314 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/* 318 */     if (object == this) {
/* 319 */       return true;
/*     */     }
/* 321 */     if (object instanceof Multiset) {
/* 322 */       Multiset<?> that = (Multiset)object;
/* 323 */       if (size() != that.size()) {
/* 324 */         return false;
/*     */       }
/* 326 */       for (Multiset.Entry<?> entry : that.entrySet()) {
/* 327 */         if (count(entry.getElement()) != entry.getCount()) {
/* 328 */           return false;
/*     */         }
/*     */       } 
/* 331 */       return true;
/*     */     } 
/* 333 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 337 */     return Sets.hashCodeImpl(entrySet());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 341 */     return entrySet().toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Multiset.Entry<E>> entrySet() {
/* 348 */     ImmutableSet<Multiset.Entry<E>> es = this.entrySet;
/* 349 */     return (es == null) ? (this.entrySet = createEntrySet()) : es;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ImmutableSet<Multiset.Entry<E>> createEntrySet() {
/* 357 */     return new EntrySet<E>(this);
/*     */   }
/*     */   
/*     */   static class EntrySet<E> extends ImmutableSet<Multiset.Entry<E>> {
/*     */     final transient ImmutableMultiset<E> multiset;
/*     */     
/*     */     public EntrySet(ImmutableMultiset<E> multiset) {
/* 364 */       this.multiset = multiset;
/*     */     }
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     public UnmodifiableIterator<Multiset.Entry<E>> iterator() {
/* 369 */       return this.multiset.entryIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 374 */       return this.multiset.distinctElements();
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isPartialView() {
/* 379 */       return this.multiset.isPartialView();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 384 */       if (o instanceof Multiset.Entry) {
/* 385 */         Multiset.Entry<?> entry = (Multiset.Entry)o;
/* 386 */         if (entry.getCount() <= 0) {
/* 387 */           return false;
/*     */         }
/* 389 */         int count = this.multiset.count(entry.getElement());
/* 390 */         return (count == entry.getCount());
/*     */       } 
/* 392 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 401 */       Object[] newArray = new Object[size()];
/* 402 */       return toArray(newArray);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] other) {
/* 411 */       int size = size();
/* 412 */       if (other.length < size) {
/* 413 */         other = ObjectArrays.newArray(other, size);
/* 414 */       } else if (other.length > size) {
/* 415 */         other[size] = null;
/*     */       } 
/*     */ 
/*     */       
/* 419 */       T[] arrayOfT = other;
/* 420 */       int index = 0;
/* 421 */       for (Multiset.Entry<?> element : this) {
/* 422 */         arrayOfT[index++] = (T)element;
/*     */       }
/* 424 */       return other;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 429 */       return this.multiset.hashCode();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Object writeReplace() {
/* 435 */       return new EntrySetSerializedForm<E>(this.multiset);
/*     */     }
/*     */     
/*     */     static class EntrySetSerializedForm<E> implements Serializable {
/*     */       final ImmutableMultiset<E> multiset;
/*     */       
/*     */       EntrySetSerializedForm(ImmutableMultiset<E> multiset) {
/* 442 */         this.multiset = multiset;
/*     */       }
/*     */       
/*     */       Object readResolve() {
/* 446 */         return this.multiset.entrySet();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SerializedForm
/*     */     implements Serializable {
/*     */     final Object[] elements;
/*     */     final int[] counts;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     SerializedForm(Multiset<?> multiset) {
/* 458 */       int distinct = multiset.entrySet().size();
/* 459 */       this.elements = new Object[distinct];
/* 460 */       this.counts = new int[distinct];
/* 461 */       int i = 0;
/* 462 */       for (Multiset.Entry<?> entry : multiset.entrySet()) {
/* 463 */         this.elements[i] = entry.getElement();
/* 464 */         this.counts[i] = entry.getCount();
/* 465 */         i++;
/*     */       } 
/*     */     }
/*     */     
/*     */     Object readResolve() {
/* 470 */       LinkedHashMultiset<Object> multiset = LinkedHashMultiset.create(this.elements.length);
/*     */       
/* 472 */       for (int i = 0; i < this.elements.length; i++) {
/* 473 */         multiset.add(this.elements[i], this.counts[i]);
/*     */       }
/* 475 */       return ImmutableMultiset.copyOf(multiset);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object writeReplace() {
/* 484 */     return new SerializedForm(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> Builder<E> builder() {
/* 492 */     return new Builder<E>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract UnmodifiableIterator<Multiset.Entry<E>> entryIterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract int distinctElements();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Builder<E>
/*     */     extends ImmutableCollection.Builder<E>
/*     */   {
/*     */     final Multiset<E> contents;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder() {
/* 521 */       this(LinkedHashMultiset.create());
/*     */     }
/*     */     
/*     */     Builder(Multiset<E> contents) {
/* 525 */       this.contents = contents;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<E> add(E element) {
/* 536 */       this.contents.add((E)Preconditions.checkNotNull(element));
/* 537 */       return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<E> addCopies(E element, int occurrences) {
/* 554 */       this.contents.add((E)Preconditions.checkNotNull(element), occurrences);
/* 555 */       return this;
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
/*     */     
/*     */     public Builder<E> setCount(E element, int count) {
/* 569 */       this.contents.setCount((E)Preconditions.checkNotNull(element), count);
/* 570 */       return this;
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
/* 582 */       super.add(elements);
/* 583 */       return this;
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
/* 596 */       if (elements instanceof Multiset) {
/* 597 */         Multiset<? extends E> multiset = Multisets.cast(elements);
/* 598 */         for (Multiset.Entry<? extends E> entry : multiset.entrySet()) {
/* 599 */           addCopies(entry.getElement(), entry.getCount());
/*     */         }
/*     */       } else {
/* 602 */         super.addAll(elements);
/*     */       } 
/* 604 */       return this;
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
/* 616 */       super.addAll(elements);
/* 617 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ImmutableMultiset<E> build() {
/* 625 */       return ImmutableMultiset.copyOf(this.contents);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ImmutableMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */