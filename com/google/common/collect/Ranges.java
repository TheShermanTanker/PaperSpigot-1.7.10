/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @Beta
/*     */ public final class Ranges
/*     */ {
/*     */   static <C extends Comparable<?>> Range<C> create(Cut<C> lowerBound, Cut<C> upperBound) {
/*  76 */     return (Range)new Range<Comparable>(lowerBound, upperBound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <C extends Comparable<?>> Range<C> open(C lower, C upper) {
/*  87 */     return create((Cut)Cut.aboveValue((Comparable)lower), (Cut)Cut.belowValue((Comparable)upper));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <C extends Comparable<?>> Range<C> closed(C lower, C upper) {
/*  98 */     return create((Cut)Cut.belowValue((Comparable)lower), (Cut)Cut.aboveValue((Comparable)upper));
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
/*     */   public static <C extends Comparable<?>> Range<C> closedOpen(C lower, C upper) {
/* 110 */     return create((Cut)Cut.belowValue((Comparable)lower), (Cut)Cut.belowValue((Comparable)upper));
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
/*     */   public static <C extends Comparable<?>> Range<C> openClosed(C lower, C upper) {
/* 122 */     return create((Cut)Cut.aboveValue((Comparable)lower), (Cut)Cut.aboveValue((Comparable)upper));
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
/*     */   public static <C extends Comparable<?>> Range<C> range(C lower, BoundType lowerType, C upper, BoundType upperType) {
/* 135 */     Preconditions.checkNotNull(lowerType);
/* 136 */     Preconditions.checkNotNull(upperType);
/*     */     
/* 138 */     Cut<C> lowerBound = (lowerType == BoundType.OPEN) ? (Cut)Cut.<Comparable>aboveValue((Comparable)lower) : (Cut)Cut.<Comparable>belowValue((Comparable)lower);
/*     */ 
/*     */     
/* 141 */     Cut<C> upperBound = (upperType == BoundType.OPEN) ? (Cut)Cut.<Comparable>belowValue((Comparable)upper) : (Cut)Cut.<Comparable>aboveValue((Comparable)upper);
/*     */ 
/*     */     
/* 144 */     return create(lowerBound, upperBound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <C extends Comparable<?>> Range<C> lessThan(C endpoint) {
/* 152 */     return create((Cut)Cut.belowAll(), (Cut)Cut.belowValue((Comparable)endpoint));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <C extends Comparable<?>> Range<C> atMost(C endpoint) {
/* 160 */     return create((Cut)Cut.belowAll(), (Cut)Cut.aboveValue((Comparable)endpoint));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <C extends Comparable<?>> Range<C> upTo(C endpoint, BoundType boundType) {
/* 169 */     switch (boundType) {
/*     */       case OPEN:
/* 171 */         return lessThan(endpoint);
/*     */       case CLOSED:
/* 173 */         return atMost(endpoint);
/*     */     } 
/* 175 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <C extends Comparable<?>> Range<C> greaterThan(C endpoint) {
/* 184 */     return create((Cut)Cut.aboveValue((Comparable)endpoint), (Cut)Cut.aboveAll());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <C extends Comparable<?>> Range<C> atLeast(C endpoint) {
/* 192 */     return create((Cut)Cut.belowValue((Comparable)endpoint), (Cut)Cut.aboveAll());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <C extends Comparable<?>> Range<C> downTo(C endpoint, BoundType boundType) {
/* 201 */     switch (boundType) {
/*     */       case OPEN:
/* 203 */         return greaterThan(endpoint);
/*     */       case CLOSED:
/* 205 */         return atLeast(endpoint);
/*     */     } 
/* 207 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <C extends Comparable<?>> Range<C> all() {
/* 213 */     return create((Cut)Cut.belowAll(), (Cut)Cut.aboveAll());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <C extends Comparable<?>> Range<C> singleton(C value) {
/* 222 */     return closed(value, value);
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
/*     */   public static <C extends Comparable<?>> Range<C> encloseAll(Iterable<C> values) {
/* 237 */     Preconditions.checkNotNull(values);
/* 238 */     if (values instanceof ContiguousSet) {
/* 239 */       return ((ContiguousSet)values).range();
/*     */     }
/* 241 */     Iterator<C> valueIterator = values.iterator();
/* 242 */     Comparable comparable1 = (Comparable)Preconditions.checkNotNull(valueIterator.next());
/* 243 */     Comparable comparable2 = comparable1;
/* 244 */     while (valueIterator.hasNext()) {
/* 245 */       Comparable comparable = (Comparable)Preconditions.checkNotNull(valueIterator.next());
/* 246 */       comparable1 = (Comparable)Ordering.<Comparable>natural().min(comparable1, comparable);
/* 247 */       comparable2 = (Comparable)Ordering.<Comparable>natural().max(comparable2, comparable);
/*     */     } 
/* 249 */     return closed((C)comparable1, (C)comparable2);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Ranges.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */