/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.annotations.Beta;
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import com.google.common.annotations.VisibleForTesting;
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Objects;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.primitives.Ints;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractList;
/*      */ import java.util.AbstractSequentialList;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.RandomAccess;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @GwtCompatible
/*      */ public final class Lists
/*      */ {
/*      */   @GwtCompatible(serializable = true)
/*      */   public static <E> ArrayList<E> newArrayList() {
/*   74 */     return new ArrayList<E>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtCompatible(serializable = true)
/*      */   public static <E> ArrayList<E> newArrayList(E... elements) {
/*   90 */     Preconditions.checkNotNull(elements);
/*      */     
/*   92 */     int capacity = computeArrayListCapacity(elements.length);
/*   93 */     ArrayList<E> list = new ArrayList<E>(capacity);
/*   94 */     Collections.addAll(list, elements);
/*   95 */     return list;
/*      */   }
/*      */   @VisibleForTesting
/*      */   static int computeArrayListCapacity(int arraySize) {
/*   99 */     Preconditions.checkArgument((arraySize >= 0));
/*      */ 
/*      */     
/*  102 */     return Ints.saturatedCast(5L + arraySize + (arraySize / 10));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtCompatible(serializable = true)
/*      */   public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
/*  117 */     Preconditions.checkNotNull(elements);
/*      */     
/*  119 */     return (elements instanceof Collection) ? new ArrayList<E>(Collections2.cast(elements)) : newArrayList(elements.iterator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtCompatible(serializable = true)
/*      */   public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
/*  136 */     Preconditions.checkNotNull(elements);
/*  137 */     ArrayList<E> list = newArrayList();
/*  138 */     while (elements.hasNext()) {
/*  139 */       list.add(elements.next());
/*      */     }
/*  141 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtCompatible(serializable = true)
/*      */   public static <E> ArrayList<E> newArrayListWithCapacity(int initialArraySize) {
/*  167 */     Preconditions.checkArgument((initialArraySize >= 0));
/*  168 */     return new ArrayList<E>(initialArraySize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtCompatible(serializable = true)
/*      */   public static <E> ArrayList<E> newArrayListWithExpectedSize(int estimatedSize) {
/*  189 */     return new ArrayList<E>(computeArrayListCapacity(estimatedSize));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtCompatible(serializable = true)
/*      */   public static <E> LinkedList<E> newLinkedList() {
/*  204 */     return new LinkedList<E>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtCompatible(serializable = true)
/*      */   public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> elements) {
/*  216 */     LinkedList<E> list = newLinkedList();
/*  217 */     for (E element : elements) {
/*  218 */       list.add(element);
/*      */     }
/*  220 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <E> List<E> asList(@Nullable E first, E[] rest) {
/*  240 */     return new OnePlusArrayList<E>(first, rest);
/*      */   }
/*      */   
/*      */   private static class OnePlusArrayList<E>
/*      */     extends AbstractList<E> implements Serializable, RandomAccess {
/*      */     final E first;
/*      */     final E[] rest;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     OnePlusArrayList(@Nullable E first, E[] rest) {
/*  250 */       this.first = first;
/*  251 */       this.rest = (E[])Preconditions.checkNotNull(rest);
/*      */     }
/*      */     public int size() {
/*  254 */       return this.rest.length + 1;
/*      */     }
/*      */     
/*      */     public E get(int index) {
/*  258 */       Preconditions.checkElementIndex(index, size());
/*  259 */       return (index == 0) ? this.first : this.rest[index - 1];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <E> List<E> asList(@Nullable E first, @Nullable E second, E[] rest) {
/*  283 */     return new TwoPlusArrayList<E>(first, second, rest);
/*      */   }
/*      */   
/*      */   private static class TwoPlusArrayList<E>
/*      */     extends AbstractList<E> implements Serializable, RandomAccess {
/*      */     final E first;
/*      */     final E second;
/*      */     final E[] rest;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     TwoPlusArrayList(@Nullable E first, @Nullable E second, E[] rest) {
/*  294 */       this.first = first;
/*  295 */       this.second = second;
/*  296 */       this.rest = (E[])Preconditions.checkNotNull(rest);
/*      */     }
/*      */     public int size() {
/*  299 */       return this.rest.length + 2;
/*      */     }
/*      */     public E get(int index) {
/*  302 */       switch (index) {
/*      */         case 0:
/*  304 */           return this.first;
/*      */         case 1:
/*  306 */           return this.second;
/*      */       } 
/*      */       
/*  309 */       Preconditions.checkElementIndex(index, size());
/*  310 */       return this.rest[index - 2];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <F, T> List<T> transform(List<F> fromList, Function<? super F, ? extends T> function) {
/*  345 */     return (fromList instanceof RandomAccess) ? new TransformingRandomAccessList<F, T>(fromList, function) : new TransformingSequentialList<F, T>(fromList, function);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TransformingSequentialList<F, T>
/*      */     extends AbstractSequentialList<T>
/*      */     implements Serializable
/*      */   {
/*      */     final List<F> fromList;
/*      */     
/*      */     final Function<? super F, ? extends T> function;
/*      */     
/*      */     private static final long serialVersionUID = 0L;
/*      */ 
/*      */     
/*      */     TransformingSequentialList(List<F> fromList, Function<? super F, ? extends T> function) {
/*  362 */       this.fromList = (List<F>)Preconditions.checkNotNull(fromList);
/*  363 */       this.function = (Function<? super F, ? extends T>)Preconditions.checkNotNull(function);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void clear() {
/*  371 */       this.fromList.clear();
/*      */     }
/*      */     public int size() {
/*  374 */       return this.fromList.size();
/*      */     }
/*      */     public ListIterator<T> listIterator(int index) {
/*  377 */       final ListIterator<F> delegate = this.fromList.listIterator(index);
/*  378 */       return new ListIterator<T>()
/*      */         {
/*      */           public void add(T e) {
/*  381 */             throw new UnsupportedOperationException();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean hasNext() {
/*  386 */             return delegate.hasNext();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean hasPrevious() {
/*  391 */             return delegate.hasPrevious();
/*      */           }
/*      */ 
/*      */           
/*      */           public T next() {
/*  396 */             return (T)Lists.TransformingSequentialList.this.function.apply(delegate.next());
/*      */           }
/*      */ 
/*      */           
/*      */           public int nextIndex() {
/*  401 */             return delegate.nextIndex();
/*      */           }
/*      */ 
/*      */           
/*      */           public T previous() {
/*  406 */             return (T)Lists.TransformingSequentialList.this.function.apply(delegate.previous());
/*      */           }
/*      */ 
/*      */           
/*      */           public int previousIndex() {
/*  411 */             return delegate.previousIndex();
/*      */           }
/*      */ 
/*      */           
/*      */           public void remove() {
/*  416 */             delegate.remove();
/*      */           }
/*      */ 
/*      */           
/*      */           public void set(T e) {
/*  421 */             throw new UnsupportedOperationException("not supported");
/*      */           }
/*      */         };
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TransformingRandomAccessList<F, T>
/*      */     extends AbstractList<T>
/*      */     implements RandomAccess, Serializable
/*      */   {
/*      */     final List<F> fromList;
/*      */ 
/*      */     
/*      */     final Function<? super F, ? extends T> function;
/*      */ 
/*      */     
/*      */     private static final long serialVersionUID = 0L;
/*      */ 
/*      */ 
/*      */     
/*      */     TransformingRandomAccessList(List<F> fromList, Function<? super F, ? extends T> function) {
/*  444 */       this.fromList = (List<F>)Preconditions.checkNotNull(fromList);
/*  445 */       this.function = (Function<? super F, ? extends T>)Preconditions.checkNotNull(function);
/*      */     }
/*      */     public void clear() {
/*  448 */       this.fromList.clear();
/*      */     }
/*      */     public T get(int index) {
/*  451 */       return (T)this.function.apply(this.fromList.get(index));
/*      */     }
/*      */     public boolean isEmpty() {
/*  454 */       return this.fromList.isEmpty();
/*      */     }
/*      */     public T remove(int index) {
/*  457 */       return (T)this.function.apply(this.fromList.remove(index));
/*      */     }
/*      */     public int size() {
/*  460 */       return this.fromList.size();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> List<List<T>> partition(List<T> list, int size) {
/*  484 */     Preconditions.checkNotNull(list);
/*  485 */     Preconditions.checkArgument((size > 0));
/*  486 */     return (list instanceof RandomAccess) ? new RandomAccessPartition<T>(list, size) : new Partition<T>(list, size);
/*      */   }
/*      */   
/*      */   private static class Partition<T>
/*      */     extends AbstractList<List<T>>
/*      */   {
/*      */     final List<T> list;
/*      */     final int size;
/*      */     
/*      */     Partition(List<T> list, int size) {
/*  496 */       this.list = list;
/*  497 */       this.size = size;
/*      */     }
/*      */     
/*      */     public List<T> get(int index) {
/*  501 */       int listSize = size();
/*  502 */       Preconditions.checkElementIndex(index, listSize);
/*  503 */       int start = index * this.size;
/*  504 */       int end = Math.min(start + this.size, this.list.size());
/*  505 */       return this.list.subList(start, end);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  510 */       int result = this.list.size() / this.size;
/*  511 */       if (result * this.size != this.list.size()) {
/*  512 */         result++;
/*      */       }
/*  514 */       return result;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/*  518 */       return this.list.isEmpty();
/*      */     }
/*      */   }
/*      */   
/*      */   private static class RandomAccessPartition<T>
/*      */     extends Partition<T> implements RandomAccess {
/*      */     RandomAccessPartition(List<T> list, int size) {
/*  525 */       super(list, size);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static ImmutableList<Character> charactersOf(String string) {
/*  536 */     return new StringAsImmutableList((String)Preconditions.checkNotNull(string));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class StringAsImmutableList
/*      */     extends ImmutableList<Character>
/*      */   {
/*      */     private final String string;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int hash;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(@Nullable Object object) {
/*      */       return (indexOf(object) >= 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int indexOf(@Nullable Object object) {
/*      */       return (object instanceof Character) ? this.string.indexOf(((Character)object).charValue()) : -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     StringAsImmutableList(String string) {
/*  609 */       this.hash = 0; this.string = string;
/*      */     } public int lastIndexOf(@Nullable Object object) { return (object instanceof Character) ? this.string.lastIndexOf(((Character)object).charValue()) : -1; } public UnmodifiableListIterator<Character> listIterator(int index) { return new AbstractIndexedListIterator<Character>(size(), index) {
/*      */           protected Character get(int index) { return Character.valueOf(Lists.StringAsImmutableList.this.string.charAt(index)); }
/*  612 */         }; } public ImmutableList<Character> subList(int fromIndex, int toIndex) { return Lists.charactersOf(this.string.substring(fromIndex, toIndex)); } boolean isPartialView() { return false; } public Character get(int index) { return Character.valueOf(this.string.charAt(index)); } public int hashCode() { int h = this.hash;
/*  613 */       if (h == 0) {
/*  614 */         h = 1;
/*  615 */         for (int i = 0; i < this.string.length(); i++) {
/*  616 */           h = h * 31 + this.string.charAt(i);
/*      */         }
/*  618 */         this.hash = h;
/*      */       } 
/*  620 */       return h; }
/*      */     public int size() { return this.string.length(); } public boolean equals(@Nullable Object obj) { if (!(obj instanceof List))
/*      */         return false; 
/*      */       List<?> list = (List)obj;
/*      */       int n = this.string.length();
/*      */       if (n != list.size())
/*      */         return false; 
/*      */       Iterator<?> iterator = list.iterator();
/*      */       for (int i = 0; i < n; i++) {
/*      */         Object elem = iterator.next();
/*      */         if (!(elem instanceof Character) || ((Character)elem).charValue() != this.string.charAt(i))
/*      */           return false; 
/*      */       } 
/*      */       return true; }
/*      */   } @Beta
/*      */   public static List<Character> charactersOf(CharSequence sequence) {
/*  636 */     return new CharSequenceAsList((CharSequence)Preconditions.checkNotNull(sequence));
/*      */   }
/*      */   
/*      */   private static final class CharSequenceAsList
/*      */     extends AbstractList<Character> {
/*      */     private final CharSequence sequence;
/*      */     
/*      */     CharSequenceAsList(CharSequence sequence) {
/*  644 */       this.sequence = sequence;
/*      */     }
/*      */     
/*      */     public Character get(int index) {
/*  648 */       return Character.valueOf(this.sequence.charAt(index));
/*      */     }
/*      */     
/*      */     public boolean contains(@Nullable Object o) {
/*  652 */       return (indexOf(o) >= 0);
/*      */     }
/*      */     
/*      */     public int indexOf(@Nullable Object o) {
/*  656 */       if (o instanceof Character) {
/*  657 */         char c = ((Character)o).charValue();
/*  658 */         for (int i = 0; i < this.sequence.length(); i++) {
/*  659 */           if (this.sequence.charAt(i) == c) {
/*  660 */             return i;
/*      */           }
/*      */         } 
/*      */       } 
/*  664 */       return -1;
/*      */     }
/*      */     
/*      */     public int lastIndexOf(@Nullable Object o) {
/*  668 */       if (o instanceof Character) {
/*  669 */         char c = ((Character)o).charValue();
/*  670 */         for (int i = this.sequence.length() - 1; i >= 0; i--) {
/*  671 */           if (this.sequence.charAt(i) == c) {
/*  672 */             return i;
/*      */           }
/*      */         } 
/*      */       } 
/*  676 */       return -1;
/*      */     }
/*      */     
/*      */     public int size() {
/*  680 */       return this.sequence.length();
/*      */     }
/*      */     
/*      */     public List<Character> subList(int fromIndex, int toIndex) {
/*  684 */       return Lists.charactersOf(this.sequence.subSequence(fromIndex, toIndex));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  688 */       int hash = 1;
/*  689 */       for (int i = 0; i < this.sequence.length(); i++) {
/*  690 */         hash = hash * 31 + this.sequence.charAt(i);
/*      */       }
/*  692 */       return hash;
/*      */     }
/*      */     
/*      */     public boolean equals(@Nullable Object o) {
/*  696 */       if (!(o instanceof List)) {
/*  697 */         return false;
/*      */       }
/*  699 */       List<?> list = (List)o;
/*  700 */       int n = this.sequence.length();
/*  701 */       if (n != list.size()) {
/*  702 */         return false;
/*      */       }
/*  704 */       Iterator<?> iterator = list.iterator();
/*  705 */       for (int i = 0; i < n; i++) {
/*  706 */         Object elem = iterator.next();
/*  707 */         if (!(elem instanceof Character) || ((Character)elem).charValue() != this.sequence.charAt(i))
/*      */         {
/*  709 */           return false;
/*      */         }
/*      */       } 
/*  712 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> List<T> reverse(List<T> list) {
/*  729 */     if (list instanceof ReverseList)
/*  730 */       return ((ReverseList<T>)list).getForwardList(); 
/*  731 */     if (list instanceof RandomAccess) {
/*  732 */       return new RandomAccessReverseList<T>(list);
/*      */     }
/*  734 */     return new ReverseList<T>(list);
/*      */   }
/*      */   
/*      */   private static class ReverseList<T>
/*      */     extends AbstractList<T> {
/*      */     private final List<T> forwardList;
/*      */     
/*      */     ReverseList(List<T> forwardList) {
/*  742 */       this.forwardList = (List<T>)Preconditions.checkNotNull(forwardList);
/*      */     }
/*      */     
/*      */     List<T> getForwardList() {
/*  746 */       return this.forwardList;
/*      */     }
/*      */     
/*      */     private int reverseIndex(int index) {
/*  750 */       int size = size();
/*  751 */       Preconditions.checkElementIndex(index, size);
/*  752 */       return size - 1 - index;
/*      */     }
/*      */     
/*      */     private int reversePosition(int index) {
/*  756 */       int size = size();
/*  757 */       Preconditions.checkPositionIndex(index, size);
/*  758 */       return size - index;
/*      */     }
/*      */     
/*      */     public void add(int index, @Nullable T element) {
/*  762 */       this.forwardList.add(reversePosition(index), element);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  766 */       this.forwardList.clear();
/*      */     }
/*      */     
/*      */     public T remove(int index) {
/*  770 */       return this.forwardList.remove(reverseIndex(index));
/*      */     }
/*      */     
/*      */     protected void removeRange(int fromIndex, int toIndex) {
/*  774 */       subList(fromIndex, toIndex).clear();
/*      */     }
/*      */     
/*      */     public T set(int index, @Nullable T element) {
/*  778 */       return this.forwardList.set(reverseIndex(index), element);
/*      */     }
/*      */     
/*      */     public T get(int index) {
/*  782 */       return this.forwardList.get(reverseIndex(index));
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/*  786 */       return this.forwardList.isEmpty();
/*      */     }
/*      */     
/*      */     public int size() {
/*  790 */       return this.forwardList.size();
/*      */     }
/*      */     
/*      */     public boolean contains(@Nullable Object o) {
/*  794 */       return this.forwardList.contains(o);
/*      */     }
/*      */     
/*      */     public boolean containsAll(Collection<?> c) {
/*  798 */       return this.forwardList.containsAll(c);
/*      */     }
/*      */     
/*      */     public List<T> subList(int fromIndex, int toIndex) {
/*  802 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size());
/*  803 */       return Lists.reverse(this.forwardList.subList(reversePosition(toIndex), reversePosition(fromIndex)));
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(@Nullable Object o) {
/*  808 */       int index = this.forwardList.lastIndexOf(o);
/*  809 */       return (index >= 0) ? reverseIndex(index) : -1;
/*      */     }
/*      */     
/*      */     public int lastIndexOf(@Nullable Object o) {
/*  813 */       int index = this.forwardList.indexOf(o);
/*  814 */       return (index >= 0) ? reverseIndex(index) : -1;
/*      */     }
/*      */     
/*      */     public Iterator<T> iterator() {
/*  818 */       return listIterator();
/*      */     }
/*      */     
/*      */     public ListIterator<T> listIterator(int index) {
/*  822 */       int start = reversePosition(index);
/*  823 */       final ListIterator<T> forwardIterator = this.forwardList.listIterator(start);
/*  824 */       return new ListIterator<T>()
/*      */         {
/*      */           boolean canRemove;
/*      */           boolean canSet;
/*      */           
/*      */           public void add(T e) {
/*  830 */             forwardIterator.add(e);
/*  831 */             forwardIterator.previous();
/*  832 */             this.canSet = this.canRemove = false;
/*      */           }
/*      */           
/*      */           public boolean hasNext() {
/*  836 */             return forwardIterator.hasPrevious();
/*      */           }
/*      */           
/*      */           public boolean hasPrevious() {
/*  840 */             return forwardIterator.hasNext();
/*      */           }
/*      */           
/*      */           public T next() {
/*  844 */             if (!hasNext()) {
/*  845 */               throw new NoSuchElementException();
/*      */             }
/*  847 */             this.canSet = this.canRemove = true;
/*  848 */             return forwardIterator.previous();
/*      */           }
/*      */           
/*      */           public int nextIndex() {
/*  852 */             return Lists.ReverseList.this.reversePosition(forwardIterator.nextIndex());
/*      */           }
/*      */           
/*      */           public T previous() {
/*  856 */             if (!hasPrevious()) {
/*  857 */               throw new NoSuchElementException();
/*      */             }
/*  859 */             this.canSet = this.canRemove = true;
/*  860 */             return forwardIterator.next();
/*      */           }
/*      */           
/*      */           public int previousIndex() {
/*  864 */             return nextIndex() - 1;
/*      */           }
/*      */           
/*      */           public void remove() {
/*  868 */             Preconditions.checkState(this.canRemove);
/*  869 */             forwardIterator.remove();
/*  870 */             this.canRemove = this.canSet = false;
/*      */           }
/*      */           
/*      */           public void set(T e) {
/*  874 */             Preconditions.checkState(this.canSet);
/*  875 */             forwardIterator.set(e);
/*      */           }
/*      */         };
/*      */     }
/*      */   }
/*      */   
/*      */   private static class RandomAccessReverseList<T>
/*      */     extends ReverseList<T> implements RandomAccess {
/*      */     RandomAccessReverseList(List<T> forwardList) {
/*  884 */       super(forwardList);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int hashCodeImpl(List<?> list) {
/*  892 */     int hashCode = 1;
/*  893 */     for (Object o : list) {
/*  894 */       hashCode = 31 * hashCode + ((o == null) ? 0 : o.hashCode());
/*      */     }
/*  896 */     return hashCode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean equalsImpl(List<?> list, @Nullable Object object) {
/*  903 */     if (object == Preconditions.checkNotNull(list)) {
/*  904 */       return true;
/*      */     }
/*  906 */     if (!(object instanceof List)) {
/*  907 */       return false;
/*      */     }
/*      */     
/*  910 */     List<?> o = (List)object;
/*      */     
/*  912 */     return (list.size() == o.size() && Iterators.elementsEqual(list.iterator(), o.iterator()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <E> boolean addAllImpl(List<E> list, int index, Iterable<? extends E> elements) {
/*  921 */     boolean changed = false;
/*  922 */     ListIterator<E> listIterator = list.listIterator(index);
/*  923 */     for (E e : elements) {
/*  924 */       listIterator.add(e);
/*  925 */       changed = true;
/*      */     } 
/*  927 */     return changed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int indexOfImpl(List<?> list, @Nullable Object element) {
/*  934 */     ListIterator<?> listIterator = list.listIterator();
/*  935 */     while (listIterator.hasNext()) {
/*  936 */       if (Objects.equal(element, listIterator.next())) {
/*  937 */         return listIterator.previousIndex();
/*      */       }
/*      */     } 
/*  940 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int lastIndexOfImpl(List<?> list, @Nullable Object element) {
/*  947 */     ListIterator<?> listIterator = list.listIterator(list.size());
/*  948 */     while (listIterator.hasPrevious()) {
/*  949 */       if (Objects.equal(element, listIterator.previous())) {
/*  950 */         return listIterator.nextIndex();
/*      */       }
/*      */     } 
/*  953 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <E> ListIterator<E> listIteratorImpl(List<E> list, int index) {
/*  960 */     return (new AbstractListWrapper<E>(list)).listIterator(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <E> List<E> subListImpl(List<E> list, int fromIndex, int toIndex) {
/*      */     List<E> wrapper;
/*  969 */     if (list instanceof RandomAccess) {
/*  970 */       wrapper = new RandomAccessListWrapper<E>(list) { private static final long serialVersionUID = 0L;
/*      */           public ListIterator<E> listIterator(int index) {
/*  972 */             return this.backingList.listIterator(index);
/*      */           } }
/*      */         ;
/*      */     }
/*      */     else {
/*      */       
/*  978 */       wrapper = new AbstractListWrapper<E>(list) { private static final long serialVersionUID = 0L;
/*      */           public ListIterator<E> listIterator(int index) {
/*  980 */             return this.backingList.listIterator(index);
/*      */           } }
/*      */         ;
/*      */     } 
/*      */ 
/*      */     
/*  986 */     return wrapper.subList(fromIndex, toIndex);
/*      */   }
/*      */   
/*      */   private static class AbstractListWrapper<E> extends AbstractList<E> {
/*      */     final List<E> backingList;
/*      */     
/*      */     AbstractListWrapper(List<E> backingList) {
/*  993 */       this.backingList = (List<E>)Preconditions.checkNotNull(backingList);
/*      */     }
/*      */     
/*      */     public void add(int index, E element) {
/*  997 */       this.backingList.add(index, element);
/*      */     }
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends E> c) {
/* 1001 */       return this.backingList.addAll(index, c);
/*      */     }
/*      */     
/*      */     public E get(int index) {
/* 1005 */       return this.backingList.get(index);
/*      */     }
/*      */     
/*      */     public E remove(int index) {
/* 1009 */       return this.backingList.remove(index);
/*      */     }
/*      */     
/*      */     public E set(int index, E element) {
/* 1013 */       return this.backingList.set(index, element);
/*      */     }
/*      */     
/*      */     public boolean contains(Object o) {
/* 1017 */       return this.backingList.contains(o);
/*      */     }
/*      */     
/*      */     public int size() {
/* 1021 */       return this.backingList.size();
/*      */     }
/*      */   }
/*      */   
/*      */   private static class RandomAccessListWrapper<E>
/*      */     extends AbstractListWrapper<E> implements RandomAccess {
/*      */     RandomAccessListWrapper(List<E> backingList) {
/* 1028 */       super(backingList);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Lists.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */