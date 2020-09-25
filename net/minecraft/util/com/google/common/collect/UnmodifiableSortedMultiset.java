/*     */ package net.minecraft.util.com.google.common.collect;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.NavigableSet;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible(emulated = true)
/*     */ final class UnmodifiableSortedMultiset<E>
/*     */   extends Multisets.UnmodifiableMultiset<E>
/*     */   implements SortedMultiset<E>
/*     */ {
/*     */   private transient UnmodifiableSortedMultiset<E> descendingMultiset;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   UnmodifiableSortedMultiset(SortedMultiset<E> delegate) {
/*  36 */     super(delegate);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SortedMultiset<E> delegate() {
/*  41 */     return (SortedMultiset<E>)super.delegate();
/*     */   }
/*     */ 
/*     */   
/*     */   public Comparator<? super E> comparator() {
/*  46 */     return delegate().comparator();
/*     */   }
/*     */ 
/*     */   
/*     */   NavigableSet<E> createElementSet() {
/*  51 */     return Sets.unmodifiableNavigableSet(delegate().elementSet());
/*     */   }
/*     */ 
/*     */   
/*     */   public NavigableSet<E> elementSet() {
/*  56 */     return (NavigableSet<E>)super.elementSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SortedMultiset<E> descendingMultiset() {
/*  63 */     UnmodifiableSortedMultiset<E> result = this.descendingMultiset;
/*  64 */     if (result == null) {
/*  65 */       result = new UnmodifiableSortedMultiset(delegate().descendingMultiset());
/*     */       
/*  67 */       result.descendingMultiset = this;
/*  68 */       return this.descendingMultiset = result;
/*     */     } 
/*  70 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Multiset.Entry<E> firstEntry() {
/*  75 */     return delegate().firstEntry();
/*     */   }
/*     */ 
/*     */   
/*     */   public Multiset.Entry<E> lastEntry() {
/*  80 */     return delegate().lastEntry();
/*     */   }
/*     */ 
/*     */   
/*     */   public Multiset.Entry<E> pollFirstEntry() {
/*  85 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Multiset.Entry<E> pollLastEntry() {
/*  90 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public SortedMultiset<E> headMultiset(E upperBound, BoundType boundType) {
/*  95 */     return Multisets.unmodifiableSortedMultiset(delegate().headMultiset(upperBound, boundType));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SortedMultiset<E> subMultiset(E lowerBound, BoundType lowerBoundType, E upperBound, BoundType upperBoundType) {
/* 103 */     return Multisets.unmodifiableSortedMultiset(delegate().subMultiset(lowerBound, lowerBoundType, upperBound, upperBoundType));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SortedMultiset<E> tailMultiset(E lowerBound, BoundType boundType) {
/* 109 */     return Multisets.unmodifiableSortedMultiset(delegate().tailMultiset(lowerBound, boundType));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\UnmodifiableSortedMultiset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */