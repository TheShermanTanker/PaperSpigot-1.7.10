/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.AbstractQueue;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Queue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ public final class MinMaxPriorityQueue<E>
/*     */   extends AbstractQueue<E>
/*     */ {
/*     */   private final Heap minHeap;
/*     */   private final Heap maxHeap;
/*     */   @VisibleForTesting
/*     */   final int maximumSize;
/*     */   private Object[] queue;
/*     */   private int size;
/*     */   private int modCount;
/*     */   private static final int EVEN_POWERS_OF_TWO = 1431655765;
/*     */   private static final int ODD_POWERS_OF_TWO = -1431655766;
/*     */   private static final int DEFAULT_CAPACITY = 11;
/*     */   
/*     */   public static <E extends Comparable<E>> MinMaxPriorityQueue<E> create() {
/*  96 */     return (new Builder(Ordering.natural())).create();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Comparable<E>> MinMaxPriorityQueue<E> create(Iterable<? extends E> initialContents) {
/* 105 */     return (new Builder(Ordering.natural())).create(initialContents);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <B> Builder<B> orderedBy(Comparator<B> comparator) {
/* 114 */     return new Builder<B>(comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder<Comparable> expectedSize(int expectedSize) {
/* 123 */     return (new Builder<Comparable>(Ordering.natural())).expectedSize(expectedSize);
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
/*     */   public static Builder<Comparable> maximumSize(int maximumSize) {
/* 135 */     return (new Builder<Comparable>(Ordering.natural())).maximumSize(maximumSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   public static final class Builder<B>
/*     */   {
/*     */     private static final int UNSET_EXPECTED_SIZE = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Comparator<B> comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     private int expectedSize = -1;
/* 162 */     private int maximumSize = Integer.MAX_VALUE;
/*     */     
/*     */     private Builder(Comparator<B> comparator) {
/* 165 */       this.comparator = (Comparator<B>)Preconditions.checkNotNull(comparator);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<B> expectedSize(int expectedSize) {
/* 173 */       Preconditions.checkArgument((expectedSize >= 0));
/* 174 */       this.expectedSize = expectedSize;
/* 175 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<B> maximumSize(int maximumSize) {
/* 185 */       Preconditions.checkArgument((maximumSize > 0));
/* 186 */       this.maximumSize = maximumSize;
/* 187 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T extends B> MinMaxPriorityQueue<T> create() {
/* 195 */       return create(Collections.emptySet());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T extends B> MinMaxPriorityQueue<T> create(Iterable<? extends T> initialContents) {
/* 204 */       MinMaxPriorityQueue<T> queue = new MinMaxPriorityQueue<T>(this, MinMaxPriorityQueue.initialQueueSize(this.expectedSize, this.maximumSize, initialContents));
/*     */       
/* 206 */       for (T element : initialContents) {
/* 207 */         queue.offer(element);
/*     */       }
/* 209 */       return queue;
/*     */     }
/*     */ 
/*     */     
/*     */     private <T extends B> Ordering<T> ordering() {
/* 214 */       return Ordering.from(this.comparator);
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
/*     */   private MinMaxPriorityQueue(Builder<? super E> builder, int queueSize) {
/* 226 */     Ordering<E> ordering = builder.ordering();
/* 227 */     this.minHeap = new Heap(ordering);
/* 228 */     this.maxHeap = new Heap(ordering.reverse());
/* 229 */     this.minHeap.otherHeap = this.maxHeap;
/* 230 */     this.maxHeap.otherHeap = this.minHeap;
/*     */     
/* 232 */     this.maximumSize = builder.maximumSize;
/*     */     
/* 234 */     this.queue = new Object[queueSize];
/*     */   }
/*     */   
/*     */   public int size() {
/* 238 */     return this.size;
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
/*     */   public boolean add(E element) {
/* 250 */     offer(element);
/* 251 */     return true;
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends E> newElements) {
/* 255 */     boolean modified = false;
/* 256 */     for (E element : newElements) {
/* 257 */       offer(element);
/* 258 */       modified = true;
/*     */     } 
/* 260 */     return modified;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean offer(E element) {
/* 270 */     Preconditions.checkNotNull(element);
/* 271 */     this.modCount++;
/* 272 */     int insertIndex = this.size++;
/*     */     
/* 274 */     growIfNeeded();
/*     */ 
/*     */ 
/*     */     
/* 278 */     heapForIndex(insertIndex).bubbleUp(insertIndex, element);
/* 279 */     return (this.size <= this.maximumSize || pollLast() != element);
/*     */   }
/*     */   
/*     */   public E poll() {
/* 283 */     return isEmpty() ? null : removeAndGet(0);
/*     */   }
/*     */ 
/*     */   
/*     */   E elementData(int index) {
/* 288 */     return (E)this.queue[index];
/*     */   }
/*     */   
/*     */   public E peek() {
/* 292 */     return isEmpty() ? null : elementData(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getMaxElementIndex() {
/* 299 */     switch (this.size) {
/*     */       case 1:
/* 301 */         return 0;
/*     */       case 2:
/* 303 */         return 1;
/*     */     } 
/*     */ 
/*     */     
/* 307 */     return (this.maxHeap.compareElements(1, 2) <= 0) ? 1 : 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E pollFirst() {
/* 316 */     return poll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E removeFirst() {
/* 325 */     return remove();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E peekFirst() {
/* 333 */     return peek();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E pollLast() {
/* 341 */     return isEmpty() ? null : removeAndGet(getMaxElementIndex());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E removeLast() {
/* 350 */     if (isEmpty()) {
/* 351 */       throw new NoSuchElementException();
/*     */     }
/* 353 */     return removeAndGet(getMaxElementIndex());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E peekLast() {
/* 361 */     return isEmpty() ? null : elementData(getMaxElementIndex());
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
/*     */   @VisibleForTesting
/*     */   MoveDesc<E> removeAt(int index) {
/* 380 */     Preconditions.checkPositionIndex(index, this.size);
/* 381 */     this.modCount++;
/* 382 */     this.size--;
/* 383 */     if (this.size == index) {
/* 384 */       this.queue[this.size] = null;
/* 385 */       return null;
/*     */     } 
/* 387 */     E actualLastElement = elementData(this.size);
/* 388 */     int lastElementAt = heapForIndex(this.size).getCorrectLastElement(actualLastElement);
/*     */     
/* 390 */     E toTrickle = elementData(this.size);
/* 391 */     this.queue[this.size] = null;
/* 392 */     MoveDesc<E> changes = fillHole(index, toTrickle);
/* 393 */     if (lastElementAt < index) {
/*     */       
/* 395 */       if (changes == null)
/*     */       {
/* 397 */         return new MoveDesc<E>(actualLastElement, toTrickle);
/*     */       }
/*     */ 
/*     */       
/* 401 */       return new MoveDesc<E>(actualLastElement, changes.replaced);
/*     */     } 
/*     */ 
/*     */     
/* 405 */     return changes;
/*     */   }
/*     */   
/*     */   private MoveDesc<E> fillHole(int index, E toTrickle) {
/* 409 */     Heap heap = heapForIndex(index);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 417 */     int vacated = heap.fillHoleAt(index);
/*     */     
/* 419 */     int bubbledTo = heap.bubbleUpAlternatingLevels(vacated, toTrickle);
/* 420 */     if (bubbledTo == vacated)
/*     */     {
/*     */ 
/*     */       
/* 424 */       return heap.tryCrossOverAndBubbleUp(index, vacated, toTrickle);
/*     */     }
/* 426 */     return (bubbledTo < index) ? new MoveDesc<E>(toTrickle, elementData(index)) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   static class MoveDesc<E>
/*     */   {
/*     */     final E toTrickle;
/*     */     
/*     */     final E replaced;
/*     */ 
/*     */     
/*     */     MoveDesc(E toTrickle, E replaced) {
/* 438 */       this.toTrickle = toTrickle;
/* 439 */       this.replaced = replaced;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private E removeAndGet(int index) {
/* 447 */     E value = elementData(index);
/* 448 */     removeAt(index);
/* 449 */     return value;
/*     */   }
/*     */   
/*     */   private Heap heapForIndex(int i) {
/* 453 */     return isEvenLevel(i) ? this.minHeap : this.maxHeap;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static boolean isEvenLevel(int index) {
/* 460 */     int oneBased = index + 1;
/* 461 */     Preconditions.checkState((oneBased > 0), "negative index");
/* 462 */     return ((oneBased & 0x55555555) > (oneBased & 0xAAAAAAAA));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   boolean isIntact() {
/* 472 */     for (int i = 1; i < this.size; i++) {
/* 473 */       if (!heapForIndex(i).verifyIndex(i)) {
/* 474 */         return false;
/*     */       }
/*     */     } 
/* 477 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class Heap
/*     */   {
/*     */     final Ordering<E> ordering;
/*     */ 
/*     */     
/*     */     Heap otherHeap;
/*     */ 
/*     */     
/*     */     Heap(Ordering<E> ordering) {
/* 491 */       this.ordering = ordering;
/*     */     }
/*     */     
/*     */     int compareElements(int a, int b) {
/* 495 */       return this.ordering.compare(MinMaxPriorityQueue.this.elementData(a), MinMaxPriorityQueue.this.elementData(b));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     MinMaxPriorityQueue.MoveDesc<E> tryCrossOverAndBubbleUp(int removeIndex, int vacated, E toTrickle) {
/*     */       E parent;
/* 505 */       int crossOver = crossOver(vacated, toTrickle);
/* 506 */       if (crossOver == vacated) {
/* 507 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 515 */       if (crossOver < removeIndex) {
/*     */ 
/*     */         
/* 518 */         parent = MinMaxPriorityQueue.this.elementData(removeIndex);
/*     */       } else {
/* 520 */         parent = MinMaxPriorityQueue.this.elementData(getParentIndex(removeIndex));
/*     */       } 
/*     */       
/* 523 */       if (this.otherHeap.bubbleUpAlternatingLevels(crossOver, toTrickle) < removeIndex)
/*     */       {
/* 525 */         return new MinMaxPriorityQueue.MoveDesc<E>(toTrickle, parent);
/*     */       }
/* 527 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void bubbleUp(int index, E x) {
/*     */       Heap heap;
/* 535 */       int crossOver = crossOverUp(index, x);
/*     */ 
/*     */       
/* 538 */       if (crossOver == index) {
/* 539 */         heap = this;
/*     */       } else {
/* 541 */         index = crossOver;
/* 542 */         heap = this.otherHeap;
/*     */       } 
/* 544 */       heap.bubbleUpAlternatingLevels(index, x);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int bubbleUpAlternatingLevels(int index, E x) {
/* 552 */       while (index > 2) {
/* 553 */         int grandParentIndex = getGrandparentIndex(index);
/* 554 */         E e = MinMaxPriorityQueue.this.elementData(grandParentIndex);
/* 555 */         if (this.ordering.compare(e, x) <= 0) {
/*     */           break;
/*     */         }
/* 558 */         MinMaxPriorityQueue.this.queue[index] = e;
/* 559 */         index = grandParentIndex;
/*     */       } 
/* 561 */       MinMaxPriorityQueue.this.queue[index] = x;
/* 562 */       return index;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int findMin(int index, int len) {
/* 571 */       if (index >= MinMaxPriorityQueue.this.size) {
/* 572 */         return -1;
/*     */       }
/* 574 */       Preconditions.checkState((index > 0));
/* 575 */       int limit = Math.min(index, MinMaxPriorityQueue.this.size - len) + len;
/* 576 */       int minIndex = index;
/* 577 */       for (int i = index + 1; i < limit; i++) {
/* 578 */         if (compareElements(i, minIndex) < 0) {
/* 579 */           minIndex = i;
/*     */         }
/*     */       } 
/* 582 */       return minIndex;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int findMinChild(int index) {
/* 589 */       return findMin(getLeftChildIndex(index), 2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int findMinGrandChild(int index) {
/* 596 */       int leftChildIndex = getLeftChildIndex(index);
/* 597 */       if (leftChildIndex < 0) {
/* 598 */         return -1;
/*     */       }
/* 600 */       return findMin(getLeftChildIndex(leftChildIndex), 4);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int crossOverUp(int index, E x) {
/* 609 */       if (index == 0) {
/* 610 */         MinMaxPriorityQueue.this.queue[0] = x;
/* 611 */         return 0;
/*     */       } 
/* 613 */       int parentIndex = getParentIndex(index);
/* 614 */       E parentElement = MinMaxPriorityQueue.this.elementData(parentIndex);
/* 615 */       if (parentIndex != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 620 */         int grandparentIndex = getParentIndex(parentIndex);
/* 621 */         int uncleIndex = getRightChildIndex(grandparentIndex);
/* 622 */         if (uncleIndex != parentIndex && getLeftChildIndex(uncleIndex) >= MinMaxPriorityQueue.this.size) {
/*     */           
/* 624 */           E uncleElement = MinMaxPriorityQueue.this.elementData(uncleIndex);
/* 625 */           if (this.ordering.compare(uncleElement, parentElement) < 0) {
/* 626 */             parentIndex = uncleIndex;
/* 627 */             parentElement = uncleElement;
/*     */           } 
/*     */         } 
/*     */       } 
/* 631 */       if (this.ordering.compare(parentElement, x) < 0) {
/* 632 */         MinMaxPriorityQueue.this.queue[index] = parentElement;
/* 633 */         MinMaxPriorityQueue.this.queue[parentIndex] = x;
/* 634 */         return parentIndex;
/*     */       } 
/* 636 */       MinMaxPriorityQueue.this.queue[index] = x;
/* 637 */       return index;
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
/*     */     int getCorrectLastElement(E actualLastElement) {
/* 650 */       int parentIndex = getParentIndex(MinMaxPriorityQueue.this.size);
/* 651 */       if (parentIndex != 0) {
/* 652 */         int grandparentIndex = getParentIndex(parentIndex);
/* 653 */         int uncleIndex = getRightChildIndex(grandparentIndex);
/* 654 */         if (uncleIndex != parentIndex && getLeftChildIndex(uncleIndex) >= MinMaxPriorityQueue.this.size) {
/*     */           
/* 656 */           E uncleElement = MinMaxPriorityQueue.this.elementData(uncleIndex);
/* 657 */           if (this.ordering.compare(uncleElement, actualLastElement) < 0) {
/* 658 */             MinMaxPriorityQueue.this.queue[uncleIndex] = actualLastElement;
/* 659 */             MinMaxPriorityQueue.this.queue[MinMaxPriorityQueue.this.size] = uncleElement;
/* 660 */             return uncleIndex;
/*     */           } 
/*     */         } 
/*     */       } 
/* 664 */       return MinMaxPriorityQueue.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int crossOver(int index, E x) {
/* 674 */       int minChildIndex = findMinChild(index);
/*     */ 
/*     */       
/* 677 */       if (minChildIndex > 0 && this.ordering.compare(MinMaxPriorityQueue.this.elementData(minChildIndex), x) < 0) {
/*     */         
/* 679 */         MinMaxPriorityQueue.this.queue[index] = MinMaxPriorityQueue.this.elementData(minChildIndex);
/* 680 */         MinMaxPriorityQueue.this.queue[minChildIndex] = x;
/* 681 */         return minChildIndex;
/*     */       } 
/* 683 */       return crossOverUp(index, x);
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
/*     */     int fillHoleAt(int index) {
/*     */       int minGrandchildIndex;
/* 696 */       while ((minGrandchildIndex = findMinGrandChild(index)) > 0) {
/* 697 */         MinMaxPriorityQueue.this.queue[index] = MinMaxPriorityQueue.this.elementData(minGrandchildIndex);
/* 698 */         index = minGrandchildIndex;
/*     */       } 
/* 700 */       return index;
/*     */     }
/*     */     
/*     */     private boolean verifyIndex(int i) {
/* 704 */       if (getLeftChildIndex(i) < MinMaxPriorityQueue.this.size && compareElements(i, getLeftChildIndex(i)) > 0)
/*     */       {
/* 706 */         return false;
/*     */       }
/* 708 */       if (getRightChildIndex(i) < MinMaxPriorityQueue.this.size && compareElements(i, getRightChildIndex(i)) > 0)
/*     */       {
/* 710 */         return false;
/*     */       }
/* 712 */       if (i > 0 && compareElements(i, getParentIndex(i)) > 0) {
/* 713 */         return false;
/*     */       }
/* 715 */       if (i > 2 && compareElements(getGrandparentIndex(i), i) > 0) {
/* 716 */         return false;
/*     */       }
/* 718 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private int getLeftChildIndex(int i) {
/* 724 */       return i * 2 + 1;
/*     */     }
/*     */     
/*     */     private int getRightChildIndex(int i) {
/* 728 */       return i * 2 + 2;
/*     */     }
/*     */     
/*     */     private int getParentIndex(int i) {
/* 732 */       return (i - 1) / 2;
/*     */     }
/*     */     
/*     */     private int getGrandparentIndex(int i) {
/* 736 */       return getParentIndex(getParentIndex(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class QueueIterator
/*     */     implements Iterator<E>
/*     */   {
/* 747 */     private int cursor = -1;
/* 748 */     private int expectedModCount = MinMaxPriorityQueue.this.modCount;
/*     */     
/*     */     private Queue<E> forgetMeNot;
/*     */     private List<E> skipMe;
/*     */     private E lastFromForgetMeNot;
/*     */     private boolean canRemove;
/*     */     
/*     */     public boolean hasNext() {
/* 756 */       checkModCount();
/* 757 */       return (nextNotInSkipMe(this.cursor + 1) < MinMaxPriorityQueue.this.size() || (this.forgetMeNot != null && !this.forgetMeNot.isEmpty()));
/*     */     }
/*     */ 
/*     */     
/*     */     public E next() {
/* 762 */       checkModCount();
/* 763 */       int tempCursor = nextNotInSkipMe(this.cursor + 1);
/* 764 */       if (tempCursor < MinMaxPriorityQueue.this.size()) {
/* 765 */         this.cursor = tempCursor;
/* 766 */         this.canRemove = true;
/* 767 */         return MinMaxPriorityQueue.this.elementData(this.cursor);
/* 768 */       }  if (this.forgetMeNot != null) {
/* 769 */         this.cursor = MinMaxPriorityQueue.this.size();
/* 770 */         this.lastFromForgetMeNot = this.forgetMeNot.poll();
/* 771 */         if (this.lastFromForgetMeNot != null) {
/* 772 */           this.canRemove = true;
/* 773 */           return this.lastFromForgetMeNot;
/*     */         } 
/*     */       } 
/* 776 */       throw new NoSuchElementException("iterator moved past last element in queue.");
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 781 */       Preconditions.checkState(this.canRemove, "no calls to remove() since the last call to next()");
/*     */       
/* 783 */       checkModCount();
/* 784 */       this.canRemove = false;
/* 785 */       this.expectedModCount++;
/* 786 */       if (this.cursor < MinMaxPriorityQueue.this.size()) {
/* 787 */         MinMaxPriorityQueue.MoveDesc<E> moved = MinMaxPriorityQueue.this.removeAt(this.cursor);
/* 788 */         if (moved != null) {
/* 789 */           if (this.forgetMeNot == null) {
/* 790 */             this.forgetMeNot = new LinkedList<E>();
/* 791 */             this.skipMe = new ArrayList<E>(3);
/*     */           } 
/* 793 */           this.forgetMeNot.add(moved.toTrickle);
/* 794 */           this.skipMe.add(moved.replaced);
/*     */         } 
/* 796 */         this.cursor--;
/*     */       } else {
/* 798 */         Preconditions.checkState(removeExact(this.lastFromForgetMeNot));
/* 799 */         this.lastFromForgetMeNot = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean containsExact(Iterable<E> elements, E target) {
/* 805 */       for (E element : elements) {
/* 806 */         if (element == target) {
/* 807 */           return true;
/*     */         }
/*     */       } 
/* 810 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean removeExact(Object target) {
/* 815 */       for (int i = 0; i < MinMaxPriorityQueue.this.size; i++) {
/* 816 */         if (MinMaxPriorityQueue.this.queue[i] == target) {
/* 817 */           MinMaxPriorityQueue.this.removeAt(i);
/* 818 */           return true;
/*     */         } 
/*     */       } 
/* 821 */       return false;
/*     */     }
/*     */     
/*     */     void checkModCount() {
/* 825 */       if (MinMaxPriorityQueue.this.modCount != this.expectedModCount) {
/* 826 */         throw new ConcurrentModificationException();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int nextNotInSkipMe(int c) {
/* 835 */       if (this.skipMe != null) {
/* 836 */         while (c < MinMaxPriorityQueue.this.size() && containsExact(this.skipMe, MinMaxPriorityQueue.this.elementData(c))) {
/* 837 */           c++;
/*     */         }
/*     */       }
/* 840 */       return c;
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
/*     */     private QueueIterator() {}
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
/*     */   public Iterator<E> iterator() {
/* 867 */     return new QueueIterator();
/*     */   }
/*     */   
/*     */   public void clear() {
/* 871 */     for (int i = 0; i < this.size; i++) {
/* 872 */       this.queue[i] = null;
/*     */     }
/* 874 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public Object[] toArray() {
/* 878 */     Object[] copyTo = new Object[this.size];
/* 879 */     System.arraycopy(this.queue, 0, copyTo, 0, this.size);
/* 880 */     return copyTo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparator<? super E> comparator() {
/* 889 */     return this.minHeap.ordering;
/*     */   }
/*     */   @VisibleForTesting
/*     */   int capacity() {
/* 893 */     return this.queue.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static int initialQueueSize(int configuredExpectedSize, int maximumSize, Iterable<?> initialContents) {
/* 903 */     int result = (configuredExpectedSize == -1) ? 11 : configuredExpectedSize;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 908 */     if (initialContents instanceof Collection) {
/* 909 */       int initialSize = ((Collection)initialContents).size();
/* 910 */       result = Math.max(result, initialSize);
/*     */     } 
/*     */ 
/*     */     
/* 914 */     return capAtMaximumSize(result, maximumSize);
/*     */   }
/*     */   
/*     */   private void growIfNeeded() {
/* 918 */     if (this.size > this.queue.length) {
/* 919 */       int newCapacity = calculateNewCapacity();
/* 920 */       Object[] newQueue = new Object[newCapacity];
/* 921 */       System.arraycopy(this.queue, 0, newQueue, 0, this.queue.length);
/* 922 */       this.queue = newQueue;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int calculateNewCapacity() {
/* 928 */     int oldCapacity = this.queue.length;
/* 929 */     int newCapacity = (oldCapacity < 64) ? ((oldCapacity + 1) * 2) : (oldCapacity / 2 * 3);
/*     */ 
/*     */     
/* 932 */     if (newCapacity < 0) {
/* 933 */       newCapacity = Integer.MAX_VALUE;
/*     */     }
/* 935 */     return capAtMaximumSize(newCapacity, this.maximumSize);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int capAtMaximumSize(int queueSize, int maximumSize) {
/* 940 */     return Math.min(queueSize - 1, maximumSize) + 1;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\MinMaxPriorityQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */