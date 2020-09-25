/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @GwtCompatible
/*     */ public final class Equivalences
/*     */ {
/*     */   public static Equivalence<Object> equals() {
/*  49 */     return Equals.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Equivalence<Object> identity() {
/*  58 */     return Identity.INSTANCE;
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
/*     */   @Deprecated
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <T> Equivalence<Iterable<T>> pairwise(Equivalence<? super T> elementEquivalence) {
/*  79 */     return new PairwiseEquivalence<T>(elementEquivalence);
/*     */   }
/*     */   
/*     */   private static final class Equals
/*     */     extends Equivalence<Object> implements Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*  85 */     static final Equals INSTANCE = new Equals();
/*     */     
/*     */     protected boolean doEquivalent(Object a, Object b) {
/*  88 */       return a.equals(b);
/*     */     }
/*     */     public int doHash(Object o) {
/*  91 */       return o.hashCode();
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  95 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class Identity
/*     */     extends Equivalence<Object>
/*     */     implements Serializable
/*     */   {
/* 103 */     static final Identity INSTANCE = new Identity(); private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected boolean doEquivalent(Object a, Object b) {
/* 106 */       return false;
/*     */     }
/*     */     
/*     */     protected int doHash(Object o) {
/* 110 */       return System.identityHashCode(o);
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 114 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\Equivalences.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */