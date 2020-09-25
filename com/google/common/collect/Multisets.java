/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.primitives.Ints;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
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
/*     */ @GwtCompatible
/*     */ public final class Multisets
/*     */ {
/*     */   public static <E> Multiset<E> unmodifiableMultiset(Multiset<? extends E> multiset) {
/*  68 */     if (multiset instanceof UnmodifiableMultiset || multiset instanceof ImmutableMultiset)
/*     */     {
/*     */ 
/*     */       
/*  72 */       return (Multiset)multiset;
/*     */     }
/*     */     
/*  75 */     return new UnmodifiableMultiset<E>((Multiset<? extends E>)Preconditions.checkNotNull(multiset));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static <E> Multiset<E> unmodifiableMultiset(ImmutableMultiset<E> multiset) {
/*  86 */     return (Multiset<E>)Preconditions.checkNotNull(multiset);
/*     */   }
/*     */   
/*     */   static class UnmodifiableMultiset<E> extends ForwardingMultiset<E> implements Serializable {
/*     */     final Multiset<? extends E> delegate;
/*     */     transient Set<E> elementSet;
/*     */     
/*     */     UnmodifiableMultiset(Multiset<? extends E> delegate) {
/*  94 */       this.delegate = delegate;
/*     */     }
/*     */     transient Set<Multiset.Entry<E>> entrySet;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     protected Multiset<E> delegate() {
/* 100 */       return (Multiset)this.delegate;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Set<E> createElementSet() {
/* 106 */       return Collections.unmodifiableSet(this.delegate.elementSet());
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<E> elementSet() {
/* 111 */       Set<E> es = this.elementSet;
/* 112 */       return (es == null) ? (this.elementSet = createElementSet()) : es;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Multiset.Entry<E>> entrySet() {
/* 119 */       Set<Multiset.Entry<E>> es = this.entrySet;
/* 120 */       return (es == null) ? (this.entrySet = Collections.unmodifiableSet(this.delegate.entrySet())) : es;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Iterator<E> iterator() {
/* 130 */       return Iterators.unmodifiableIterator((Iterator)this.delegate.iterator());
/*     */     }
/*     */     
/*     */     public boolean add(E element) {
/* 134 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int add(E element, int occurences) {
/* 138 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends E> elementsToAdd) {
/* 142 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object element) {
/* 146 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int remove(Object element, int occurrences) {
/* 150 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> elementsToRemove) {
/* 154 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> elementsToRetain) {
/* 158 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 162 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int setCount(E element, int count) {
/* 166 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean setCount(E element, int oldCount, int newCount) {
/* 170 */       throw new UnsupportedOperationException();
/*     */     }
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
/*     */   public static <E> Multiset.Entry<E> immutableEntry(@Nullable E e, int n) {
/* 185 */     return new ImmutableEntry<E>(e, n);
/*     */   }
/*     */   
/*     */   static final class ImmutableEntry<E> extends AbstractEntry<E> implements Serializable { @Nullable
/*     */     final E element;
/*     */     final int count;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     ImmutableEntry(@Nullable E element, int count) {
/* 194 */       this.element = element;
/* 195 */       this.count = count;
/* 196 */       Preconditions.checkArgument((count >= 0));
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public E getElement() {
/* 201 */       return this.element;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getCount() {
/* 206 */       return this.count;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <E> Multiset<E> forSet(Set<E> set) {
/* 230 */     return new SetMultiset<E>(set);
/*     */   }
/*     */   
/*     */   private static class SetMultiset<E> extends ForwardingCollection<E> implements Multiset<E>, Serializable { final Set<E> delegate;
/*     */     transient Set<E> elementSet;
/*     */     transient Set<Multiset.Entry<E>> entrySet;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     SetMultiset(Set<E> set) {
/* 239 */       this.delegate = (Set<E>)Preconditions.checkNotNull(set);
/*     */     }
/*     */     
/*     */     protected Set<E> delegate() {
/* 243 */       return this.delegate;
/*     */     }
/*     */ 
/*     */     
/*     */     public int count(Object element) {
/* 248 */       return this.delegate.contains(element) ? 1 : 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int add(E element, int occurrences) {
/* 253 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int remove(Object element, int occurrences) {
/* 258 */       if (occurrences == 0) {
/* 259 */         return count(element);
/*     */       }
/* 261 */       Preconditions.checkArgument((occurrences > 0));
/* 262 */       return this.delegate.remove(element) ? 1 : 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<E> elementSet() {
/* 269 */       Set<E> es = this.elementSet;
/* 270 */       return (es == null) ? (this.elementSet = new ElementSet()) : es;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Multiset.Entry<E>> entrySet() {
/* 276 */       Set<Multiset.Entry<E>> es = this.entrySet;
/* 277 */       if (es == null) {
/* 278 */         es = this.entrySet = new Multisets.EntrySet<E>() {
/*     */             Multiset<E> multiset() {
/* 280 */               return Multisets.SetMultiset.this;
/*     */             }
/*     */             
/*     */             public Iterator<Multiset.Entry<E>> iterator() {
/* 284 */               return Iterators.transform(Multisets.SetMultiset.this.delegate.iterator(), new Function<E, Multiset.Entry<E>>()
/*     */                   {
/*     */                     public Multiset.Entry<E> apply(E elem) {
/* 287 */                       return Multisets.immutableEntry(elem, 1);
/*     */                     }
/*     */                   });
/*     */             }
/*     */             
/*     */             public int size() {
/* 293 */               return Multisets.SetMultiset.this.delegate.size();
/*     */             }
/*     */           };
/*     */       }
/* 297 */       return es;
/*     */     }
/*     */     
/*     */     public boolean add(E o) {
/* 301 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends E> c) {
/* 305 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int setCount(E element, int count) {
/* 310 */       Multisets.checkNonnegative(count, "count");
/*     */       
/* 312 */       if (count == count(element))
/* 313 */         return count; 
/* 314 */       if (count == 0) {
/* 315 */         remove(element);
/* 316 */         return 1;
/*     */       } 
/* 318 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean setCount(E element, int oldCount, int newCount) {
/* 324 */       return Multisets.setCountImpl(this, element, oldCount, newCount);
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object object) {
/* 328 */       if (object instanceof Multiset) {
/* 329 */         Multiset<?> that = (Multiset)object;
/* 330 */         return (size() == that.size() && this.delegate.equals(that.elementSet()));
/*     */       } 
/* 332 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 336 */       int sum = 0;
/* 337 */       for (E e : this) {
/* 338 */         sum += ((e == null) ? 0 : e.hashCode()) ^ 0x1;
/*     */       }
/* 340 */       return sum;
/*     */     }
/*     */     
/*     */     class ElementSet
/*     */       extends ForwardingSet<E> {
/*     */       protected Set<E> delegate() {
/* 346 */         return Multisets.SetMultiset.this.delegate;
/*     */       }
/*     */       
/*     */       public boolean add(E o) {
/* 350 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public boolean addAll(Collection<? extends E> c) {
/* 354 */         throw new UnsupportedOperationException();
/*     */       }
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int inferDistinctElements(Iterable<?> elements) {
/* 368 */     if (elements instanceof Multiset) {
/* 369 */       return ((Multiset)elements).elementSet().size();
/*     */     }
/* 371 */     return 11;
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
/*     */   public static <E> Multiset<E> intersection(final Multiset<E> multiset1, final Multiset<?> multiset2) {
/* 389 */     Preconditions.checkNotNull(multiset1);
/* 390 */     Preconditions.checkNotNull(multiset2);
/*     */     
/* 392 */     return new AbstractMultiset<E>()
/*     */       {
/*     */         public int count(Object element) {
/* 395 */           int count1 = multiset1.count(element);
/* 396 */           return (count1 == 0) ? 0 : Math.min(count1, multiset2.count(element));
/*     */         }
/*     */ 
/*     */         
/*     */         Set<E> createElementSet() {
/* 401 */           return Sets.intersection(multiset1.elementSet(), multiset2.elementSet());
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         Iterator<Multiset.Entry<E>> entryIterator() {
/* 407 */           final Iterator<Multiset.Entry<E>> iterator1 = multiset1.entrySet().iterator();
/* 408 */           return new AbstractIterator()
/*     */             {
/*     */               protected Multiset.Entry<E> computeNext() {
/* 411 */                 while (iterator1.hasNext()) {
/* 412 */                   Multiset.Entry<E> entry1 = iterator1.next();
/* 413 */                   E element = entry1.getElement();
/* 414 */                   int count = Math.min(entry1.getCount(), multiset2.count(element));
/* 415 */                   if (count > 0) {
/* 416 */                     return Multisets.immutableEntry(element, count);
/*     */                   }
/*     */                 } 
/* 419 */                 return endOfData();
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         int distinctElements() {
/* 426 */           return elementSet().size();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   public static boolean containsOccurrences(Multiset<?> superMultiset, Multiset<?> subMultiset) {
/* 440 */     Preconditions.checkNotNull(superMultiset);
/* 441 */     Preconditions.checkNotNull(subMultiset);
/* 442 */     for (Multiset.Entry<?> entry : subMultiset.entrySet()) {
/* 443 */       int superCount = superMultiset.count(entry.getElement());
/* 444 */       if (superCount < entry.getCount()) {
/* 445 */         return false;
/*     */       }
/*     */     } 
/* 448 */     return true;
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
/*     */   @Beta
/*     */   public static boolean retainOccurrences(Multiset<?> multisetToModify, Multiset<?> multisetToRetain) {
/* 472 */     return retainOccurrencesImpl(multisetToModify, multisetToRetain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E> boolean retainOccurrencesImpl(Multiset<E> multisetToModify, Multiset<?> occurrencesToRetain) {
/* 480 */     Preconditions.checkNotNull(multisetToModify);
/* 481 */     Preconditions.checkNotNull(occurrencesToRetain);
/*     */     
/* 483 */     Iterator<Multiset.Entry<E>> entryIterator = multisetToModify.entrySet().iterator();
/* 484 */     boolean changed = false;
/* 485 */     while (entryIterator.hasNext()) {
/* 486 */       Multiset.Entry<E> entry = entryIterator.next();
/* 487 */       int retainCount = occurrencesToRetain.count(entry.getElement());
/* 488 */       if (retainCount == 0) {
/* 489 */         entryIterator.remove();
/* 490 */         changed = true; continue;
/* 491 */       }  if (retainCount < entry.getCount()) {
/* 492 */         multisetToModify.setCount(entry.getElement(), retainCount);
/* 493 */         changed = true;
/*     */       } 
/*     */     } 
/* 496 */     return changed;
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
/*     */   @Beta
/*     */   public static boolean removeOccurrences(Multiset<?> multisetToModify, Multiset<?> occurrencesToRemove) {
/* 524 */     return removeOccurrencesImpl(multisetToModify, occurrencesToRemove);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E> boolean removeOccurrencesImpl(Multiset<E> multisetToModify, Multiset<?> occurrencesToRemove) {
/* 533 */     Preconditions.checkNotNull(multisetToModify);
/* 534 */     Preconditions.checkNotNull(occurrencesToRemove);
/*     */     
/* 536 */     boolean changed = false;
/* 537 */     Iterator<Multiset.Entry<E>> entryIterator = multisetToModify.entrySet().iterator();
/* 538 */     while (entryIterator.hasNext()) {
/* 539 */       Multiset.Entry<E> entry = entryIterator.next();
/* 540 */       int removeCount = occurrencesToRemove.count(entry.getElement());
/* 541 */       if (removeCount >= entry.getCount()) {
/* 542 */         entryIterator.remove();
/* 543 */         changed = true; continue;
/* 544 */       }  if (removeCount > 0) {
/* 545 */         multisetToModify.remove(entry.getElement(), removeCount);
/* 546 */         changed = true;
/*     */       } 
/*     */     } 
/* 549 */     return changed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class AbstractEntry<E>
/*     */     implements Multiset.Entry<E>
/*     */   {
/*     */     public boolean equals(@Nullable Object object) {
/* 562 */       if (object instanceof Multiset.Entry) {
/* 563 */         Multiset.Entry<?> that = (Multiset.Entry)object;
/* 564 */         return (getCount() == that.getCount() && Objects.equal(getElement(), that.getElement()));
/*     */       } 
/*     */       
/* 567 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 575 */       E e = getElement();
/* 576 */       return ((e == null) ? 0 : e.hashCode()) ^ getCount();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 587 */       String text = String.valueOf(getElement());
/* 588 */       int n = getCount();
/* 589 */       return (n == 1) ? text : (text + " x " + n);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean equalsImpl(Multiset<?> multiset, @Nullable Object object) {
/* 597 */     if (object == multiset) {
/* 598 */       return true;
/*     */     }
/* 600 */     if (object instanceof Multiset) {
/* 601 */       Multiset<?> that = (Multiset)object;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 608 */       if (multiset.size() != that.size() || multiset.entrySet().size() != that.entrySet().size())
/*     */       {
/* 610 */         return false;
/*     */       }
/* 612 */       for (Multiset.Entry<?> entry : that.entrySet()) {
/* 613 */         if (multiset.count(entry.getElement()) != entry.getCount()) {
/* 614 */           return false;
/*     */         }
/*     */       } 
/* 617 */       return true;
/*     */     } 
/* 619 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <E> boolean addAllImpl(Multiset<E> self, Collection<? extends E> elements) {
/* 627 */     if (elements.isEmpty()) {
/* 628 */       return false;
/*     */     }
/* 630 */     if (elements instanceof Multiset) {
/* 631 */       Multiset<? extends E> that = cast(elements);
/* 632 */       for (Multiset.Entry<? extends E> entry : that.entrySet()) {
/* 633 */         self.add(entry.getElement(), entry.getCount());
/*     */       }
/*     */     } else {
/* 636 */       Iterators.addAll(self, elements.iterator());
/*     */     } 
/* 638 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean removeAllImpl(Multiset<?> self, Collection<?> elementsToRemove) {
/* 646 */     Collection<?> collection = (elementsToRemove instanceof Multiset) ? ((Multiset)elementsToRemove).elementSet() : elementsToRemove;
/*     */ 
/*     */     
/* 649 */     return self.elementSet().removeAll(collection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean retainAllImpl(Multiset<?> self, Collection<?> elementsToRetain) {
/* 657 */     Collection<?> collection = (elementsToRetain instanceof Multiset) ? ((Multiset)elementsToRetain).elementSet() : elementsToRetain;
/*     */ 
/*     */     
/* 660 */     return self.elementSet().retainAll(collection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <E> int setCountImpl(Multiset<E> self, E element, int count) {
/* 667 */     checkNonnegative(count, "count");
/*     */     
/* 669 */     int oldCount = self.count(element);
/*     */     
/* 671 */     int delta = count - oldCount;
/* 672 */     if (delta > 0) {
/* 673 */       self.add(element, delta);
/* 674 */     } else if (delta < 0) {
/* 675 */       self.remove(element, -delta);
/*     */     } 
/*     */     
/* 678 */     return oldCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <E> boolean setCountImpl(Multiset<E> self, E element, int oldCount, int newCount) {
/* 686 */     checkNonnegative(oldCount, "oldCount");
/* 687 */     checkNonnegative(newCount, "newCount");
/*     */     
/* 689 */     if (self.count(element) == oldCount) {
/* 690 */       self.setCount(element, newCount);
/* 691 */       return true;
/*     */     } 
/* 693 */     return false;
/*     */   }
/*     */   
/*     */   static abstract class ElementSet<E>
/*     */     extends AbstractSet<E> {
/*     */     abstract Multiset<E> multiset();
/*     */     
/*     */     public void clear() {
/* 701 */       multiset().clear();
/*     */     }
/*     */     
/*     */     public boolean contains(Object o) {
/* 705 */       return multiset().contains(o);
/*     */     }
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 709 */       return multiset().containsAll(c);
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 713 */       return multiset().isEmpty();
/*     */     }
/*     */     
/*     */     public Iterator<E> iterator() {
/* 717 */       return Iterators.transform(multiset().entrySet().iterator(), new Function<Multiset.Entry<E>, E>()
/*     */           {
/*     */             public E apply(Multiset.Entry<E> entry) {
/* 720 */               return entry.getElement();
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 727 */       int count = multiset().count(o);
/* 728 */       if (count > 0) {
/* 729 */         multiset().remove(o, count);
/* 730 */         return true;
/*     */       } 
/* 732 */       return false;
/*     */     }
/*     */     
/*     */     public int size() {
/* 736 */       return multiset().entrySet().size();
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract class EntrySet<E> extends AbstractSet<Multiset.Entry<E>> {
/*     */     abstract Multiset<E> multiset();
/*     */     
/*     */     public boolean contains(@Nullable Object o) {
/* 744 */       if (o instanceof Multiset.Entry) {
/*     */         
/* 746 */         Multiset.Entry<?> entry = (Multiset.Entry)o;
/* 747 */         if (entry.getCount() <= 0) {
/* 748 */           return false;
/*     */         }
/* 750 */         int count = multiset().count(entry.getElement());
/* 751 */         return (count == entry.getCount());
/*     */       } 
/*     */       
/* 754 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 759 */       return (contains(o) && multiset().elementSet().remove(((Multiset.Entry)o).getElement()));
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 764 */       multiset().clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <E> Iterator<E> iteratorImpl(Multiset<E> multiset) {
/* 772 */     return new MultisetIteratorImpl<E>(multiset, multiset.entrySet().iterator());
/*     */   }
/*     */ 
/*     */   
/*     */   static final class MultisetIteratorImpl<E>
/*     */     implements Iterator<E>
/*     */   {
/*     */     private final Multiset<E> multiset;
/*     */     
/*     */     private final Iterator<Multiset.Entry<E>> entryIterator;
/*     */     private Multiset.Entry<E> currentEntry;
/*     */     private int laterCount;
/*     */     private int totalCount;
/*     */     private boolean canRemove;
/*     */     
/*     */     MultisetIteratorImpl(Multiset<E> multiset, Iterator<Multiset.Entry<E>> entryIterator) {
/* 788 */       this.multiset = multiset;
/* 789 */       this.entryIterator = entryIterator;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 794 */       return (this.laterCount > 0 || this.entryIterator.hasNext());
/*     */     }
/*     */ 
/*     */     
/*     */     public E next() {
/* 799 */       if (!hasNext()) {
/* 800 */         throw new NoSuchElementException();
/*     */       }
/* 802 */       if (this.laterCount == 0) {
/* 803 */         this.currentEntry = this.entryIterator.next();
/* 804 */         this.totalCount = this.laterCount = this.currentEntry.getCount();
/*     */       } 
/* 806 */       this.laterCount--;
/* 807 */       this.canRemove = true;
/* 808 */       return this.currentEntry.getElement();
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 813 */       Preconditions.checkState(this.canRemove, "no calls to next() since the last call to remove()");
/*     */       
/* 815 */       if (this.totalCount == 1) {
/* 816 */         this.entryIterator.remove();
/*     */       } else {
/* 818 */         this.multiset.remove(this.currentEntry.getElement());
/*     */       } 
/* 820 */       this.totalCount--;
/* 821 */       this.canRemove = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int sizeImpl(Multiset<?> multiset) {
/* 829 */     long size = 0L;
/* 830 */     for (Multiset.Entry<?> entry : multiset.entrySet()) {
/* 831 */       size += entry.getCount();
/*     */     }
/* 833 */     return Ints.saturatedCast(size);
/*     */   }
/*     */   
/*     */   static void checkNonnegative(int count, String name) {
/* 837 */     Preconditions.checkArgument((count >= 0), "%s cannot be negative: %s", new Object[] { name, Integer.valueOf(count) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> Multiset<T> cast(Iterable<T> iterable) {
/* 844 */     return (Multiset<T>)iterable;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Multisets.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */