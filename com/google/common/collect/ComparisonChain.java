/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.primitives.Booleans;
/*     */ import com.google.common.primitives.Ints;
/*     */ import com.google.common.primitives.Longs;
/*     */ import java.util.Comparator;
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
/*     */ @GwtCompatible
/*     */ public abstract class ComparisonChain
/*     */ {
/*     */   private ComparisonChain() {}
/*     */   
/*     */   public static ComparisonChain start() {
/*  61 */     return ACTIVE;
/*     */   }
/*     */   
/*  64 */   private static final ComparisonChain ACTIVE = new ComparisonChain()
/*     */     {
/*     */       public ComparisonChain compare(Comparable<Comparable> left, Comparable right)
/*     */       {
/*  68 */         return classify(left.compareTo(right));
/*     */       }
/*     */       
/*     */       public <T> ComparisonChain compare(@Nullable T left, @Nullable T right, Comparator<T> comparator) {
/*  72 */         return classify(comparator.compare(left, right));
/*     */       }
/*     */       public ComparisonChain compare(int left, int right) {
/*  75 */         return classify(Ints.compare(left, right));
/*     */       }
/*     */       public ComparisonChain compare(long left, long right) {
/*  78 */         return classify(Longs.compare(left, right));
/*     */       }
/*     */       public ComparisonChain compare(float left, float right) {
/*  81 */         return classify(Float.compare(left, right));
/*     */       }
/*     */       public ComparisonChain compare(double left, double right) {
/*  84 */         return classify(Double.compare(left, right));
/*     */       }
/*     */       public ComparisonChain compare(boolean left, boolean right) {
/*  87 */         return classify(Booleans.compare(left, right));
/*     */       }
/*     */       ComparisonChain classify(int result) {
/*  90 */         return (result < 0) ? ComparisonChain.LESS : ((result > 0) ? ComparisonChain.GREATER : ComparisonChain.ACTIVE);
/*     */       }
/*     */       public int result() {
/*  93 */         return 0;
/*     */       }
/*     */     };
/*     */   
/*  97 */   private static final ComparisonChain LESS = new InactiveComparisonChain(-1);
/*     */   
/*  99 */   private static final ComparisonChain GREATER = new InactiveComparisonChain(1); public abstract ComparisonChain compare(Comparable<?> paramComparable1, Comparable<?> paramComparable2); public abstract <T> ComparisonChain compare(@Nullable T paramT1, @Nullable T paramT2, Comparator<T> paramComparator); public abstract ComparisonChain compare(int paramInt1, int paramInt2); public abstract ComparisonChain compare(long paramLong1, long paramLong2);
/*     */   public abstract ComparisonChain compare(float paramFloat1, float paramFloat2);
/*     */   public abstract ComparisonChain compare(double paramDouble1, double paramDouble2);
/*     */   public abstract ComparisonChain compare(boolean paramBoolean1, boolean paramBoolean2);
/*     */   public abstract int result();
/*     */   private static final class InactiveComparisonChain extends ComparisonChain { InactiveComparisonChain(int result) {
/* 105 */       this.result = result;
/*     */     }
/*     */     final int result;
/*     */     public ComparisonChain compare(@Nullable Comparable left, @Nullable Comparable right) {
/* 109 */       return this;
/*     */     }
/*     */     
/*     */     public <T> ComparisonChain compare(@Nullable T left, @Nullable T right, @Nullable Comparator<T> comparator) {
/* 113 */       return this;
/*     */     }
/*     */     public ComparisonChain compare(int left, int right) {
/* 116 */       return this;
/*     */     }
/*     */     public ComparisonChain compare(long left, long right) {
/* 119 */       return this;
/*     */     }
/*     */     public ComparisonChain compare(float left, float right) {
/* 122 */       return this;
/*     */     }
/*     */     public ComparisonChain compare(double left, double right) {
/* 125 */       return this;
/*     */     }
/*     */     public ComparisonChain compare(boolean left, boolean right) {
/* 128 */       return this;
/*     */     }
/*     */     public int result() {
/* 131 */       return this.result;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ComparisonChain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */