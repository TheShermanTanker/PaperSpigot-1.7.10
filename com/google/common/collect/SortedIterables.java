/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ final class SortedIterables
/*     */ {
/*     */   public static boolean hasSameComparator(Comparator<?> comparator, Iterable<?> elements) {
/*     */     Comparator<?> comparator2;
/*  46 */     Preconditions.checkNotNull(comparator);
/*  47 */     Preconditions.checkNotNull(elements);
/*     */     
/*  49 */     if (elements instanceof SortedSet) {
/*  50 */       SortedSet<?> sortedSet = (SortedSet)elements;
/*  51 */       comparator2 = sortedSet.comparator();
/*  52 */       if (comparator2 == null) {
/*  53 */         comparator2 = Ordering.natural();
/*     */       }
/*  55 */     } else if (elements instanceof SortedIterable) {
/*  56 */       comparator2 = ((SortedIterable)elements).comparator();
/*     */     } else {
/*  58 */       comparator2 = null;
/*     */     } 
/*  60 */     return comparator.equals(comparator2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> Collection<E> sortedUnique(Comparator<? super E> comparator, Iterator<E> elements) {
/*  70 */     SortedSet<E> sortedSet = Sets.newTreeSet(comparator);
/*  71 */     Iterators.addAll(sortedSet, elements);
/*  72 */     return sortedSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> Collection<E> sortedUnique(Comparator<? super E> comparator, Iterable<E> elements) {
/*  82 */     if (elements instanceof Multiset) {
/*  83 */       elements = ((Multiset<E>)elements).elementSet();
/*     */     }
/*  85 */     if (elements instanceof Set) {
/*  86 */       if (hasSameComparator(comparator, elements)) {
/*  87 */         return (Set)elements;
/*     */       }
/*  89 */       List<E> list = Lists.newArrayList(elements);
/*  90 */       Collections.sort(list, comparator);
/*  91 */       return list;
/*     */     } 
/*  93 */     E[] array = (E[])Iterables.toArray(elements);
/*  94 */     if (!hasSameComparator(comparator, elements)) {
/*  95 */       Arrays.sort(array, comparator);
/*     */     }
/*  97 */     return uniquifySortedArray(comparator, array);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <E> Collection<E> uniquifySortedArray(Comparator<? super E> comparator, E[] array) {
/* 102 */     if (array.length == 0) {
/* 103 */       return Collections.emptySet();
/*     */     }
/* 105 */     int length = 1;
/* 106 */     for (int i = 1; i < array.length; i++) {
/* 107 */       int cmp = comparator.compare(array[i], array[length - 1]);
/* 108 */       if (cmp != 0) {
/* 109 */         array[length++] = array[i];
/*     */       }
/*     */     } 
/* 112 */     if (length < array.length) {
/* 113 */       array = ObjectArrays.arraysCopyOf(array, length);
/*     */     }
/* 115 */     return Arrays.asList(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> Collection<Multiset.Entry<E>> sortedCounts(Comparator<? super E> comparator, Iterator<E> elements) {
/* 124 */     TreeMultiset<E> multiset = TreeMultiset.create(comparator);
/* 125 */     Iterators.addAll(multiset, elements);
/* 126 */     return multiset.entrySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> Collection<Multiset.Entry<E>> sortedCounts(Comparator<? super E> comparator, Iterable<E> elements) {
/* 135 */     if (elements instanceof Multiset) {
/* 136 */       Multiset<E> multiset1 = (Multiset<E>)elements;
/* 137 */       if (hasSameComparator(comparator, elements)) {
/* 138 */         return multiset1.entrySet();
/*     */       }
/* 140 */       List<Multiset.Entry<E>> entries = Lists.newArrayList(multiset1.entrySet());
/* 141 */       Collections.sort(entries, Ordering.<E>from((Comparator)comparator).onResultOf(new Function<Multiset.Entry<E>, E>()
/*     */             {
/*     */               public E apply(Multiset.Entry<E> entry)
/*     */               {
/* 145 */                 return entry.getElement();
/*     */               }
/*     */             }));
/* 148 */       return entries;
/* 149 */     }  if (elements instanceof Set) {
/*     */       Collection<E> sortedElements;
/* 151 */       if (hasSameComparator(comparator, elements)) {
/* 152 */         sortedElements = (Collection<E>)elements;
/*     */       } else {
/* 154 */         List<E> list = Lists.newArrayList(elements);
/* 155 */         Collections.sort(list, comparator);
/* 156 */         sortedElements = list;
/*     */       } 
/* 158 */       return singletonEntries(sortedElements);
/* 159 */     }  if (hasSameComparator(comparator, elements)) {
/* 160 */       E current = null;
/* 161 */       int currentCount = 0;
/* 162 */       List<Multiset.Entry<E>> sortedEntries = Lists.newArrayList();
/* 163 */       for (E e : elements) {
/* 164 */         if (currentCount > 0) {
/* 165 */           if (comparator.compare(current, e) == 0) {
/* 166 */             currentCount++; continue;
/*     */           } 
/* 168 */           sortedEntries.add(Multisets.immutableEntry(current, currentCount));
/* 169 */           current = e;
/* 170 */           currentCount = 1;
/*     */           continue;
/*     */         } 
/* 173 */         current = e;
/* 174 */         currentCount = 1;
/*     */       } 
/*     */       
/* 177 */       if (currentCount > 0) {
/* 178 */         sortedEntries.add(Multisets.immutableEntry(current, currentCount));
/*     */       }
/* 180 */       return sortedEntries;
/*     */     } 
/* 182 */     TreeMultiset<E> multiset = TreeMultiset.create(comparator);
/* 183 */     Iterables.addAll(multiset, elements);
/* 184 */     return multiset.entrySet();
/*     */   }
/*     */   
/*     */   static <E> Collection<Multiset.Entry<E>> singletonEntries(Collection<E> set) {
/* 188 */     return Collections2.transform(set, new Function<E, Multiset.Entry<E>>()
/*     */         {
/*     */           public Multiset.Entry<E> apply(E elem) {
/* 191 */             return Multisets.immutableEntry(elem, 1);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\SortedIterables.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */