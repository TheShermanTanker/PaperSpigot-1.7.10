/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.io.Serializable;
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
/*     */ @Beta
/*     */ @GwtCompatible
/*     */ public abstract class Equivalence<T>
/*     */ {
/*     */   public final boolean equivalent(@Nullable T a, @Nullable T b) {
/*  66 */     if (a == b) {
/*  67 */       return true;
/*     */     }
/*  69 */     if (a == null || b == null) {
/*  70 */       return false;
/*     */     }
/*  72 */     return doEquivalent(a, b);
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
/*     */   protected abstract boolean doEquivalent(T paramT1, T paramT2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int hash(@Nullable T t) {
/* 102 */     if (t == null) {
/* 103 */       return 0;
/*     */     }
/* 105 */     return doHash(t);
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
/*     */   protected abstract int doHash(T paramT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <F> Equivalence<F> onResultOf(Function<F, ? extends T> function) {
/* 141 */     return new FunctionalEquivalence<F, T>(function, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <S extends T> Wrapper<S> wrap(@Nullable S reference) {
/* 152 */     return new Wrapper<S>(this, reference);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   public static final class Wrapper<T>
/*     */     implements Serializable
/*     */   {
/*     */     private final Equivalence<? super T> equivalence;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private final T reference;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Wrapper(Equivalence<? super T> equivalence, @Nullable T reference) {
/* 181 */       this.equivalence = Preconditions.<Equivalence<? super T>>checkNotNull(equivalence);
/* 182 */       this.reference = reference;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public T get() {
/* 187 */       return this.reference;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 196 */       if (obj == this)
/* 197 */         return true; 
/* 198 */       if (obj instanceof Wrapper) {
/* 199 */         Wrapper<?> that = (Wrapper)obj;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 206 */         Equivalence<Object> equivalence = (Equivalence)this.equivalence;
/* 207 */         return (equivalence.equals(that.equivalence) && equivalence.equivalent(this.reference, that.reference));
/*     */       } 
/*     */       
/* 210 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 218 */       return this.equivalence.hash(this.reference);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 226 */       return this.equivalence + ".wrap(" + this.reference + ")";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtCompatible(serializable = true)
/*     */   public final <S extends T> Equivalence<Iterable<S>> pairwise() {
/* 247 */     return new PairwiseEquivalence<S>(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Predicate<T> equivalentTo(@Nullable T target) {
/* 257 */     return new EquivalentToPredicate<T>(this, target);
/*     */   }
/*     */   
/*     */   private static final class EquivalentToPredicate<T>
/*     */     implements Predicate<T>, Serializable
/*     */   {
/*     */     private final Equivalence<T> equivalence;
/*     */     
/*     */     EquivalentToPredicate(Equivalence<T> equivalence, @Nullable T target) {
/* 266 */       this.equivalence = Preconditions.<Equivalence<T>>checkNotNull(equivalence);
/* 267 */       this.target = target;
/*     */     } @Nullable
/*     */     private final T target; private static final long serialVersionUID = 0L;
/*     */     public boolean apply(@Nullable T input) {
/* 271 */       return this.equivalence.equivalent(input, this.target);
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 275 */       if (this == obj) {
/* 276 */         return true;
/*     */       }
/* 278 */       if (obj instanceof EquivalentToPredicate) {
/* 279 */         EquivalentToPredicate<?> that = (EquivalentToPredicate)obj;
/* 280 */         return (this.equivalence.equals(that.equivalence) && Objects.equal(this.target, that.target));
/*     */       } 
/*     */       
/* 283 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 287 */       return Objects.hashCode(new Object[] { this.equivalence, this.target });
/*     */     }
/*     */     
/*     */     public String toString() {
/* 291 */       return this.equivalence + ".equivalentTo(" + this.target + ")";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\Equivalence.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */