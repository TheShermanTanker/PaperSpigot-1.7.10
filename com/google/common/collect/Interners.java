/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import com.google.common.base.Equivalences;
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class Interners
/*     */ {
/*     */   public static <E> Interner<E> newStrongInterner() {
/*  45 */     final ConcurrentMap<E, E> map = (new MapMaker()).makeMap();
/*  46 */     return new Interner<E>() {
/*     */         public E intern(E sample) {
/*  48 */           E canonical = map.putIfAbsent(Preconditions.checkNotNull(sample), sample);
/*  49 */           return (canonical == null) ? sample : canonical;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private static class CustomInterner<E>
/*     */     implements Interner<E> {
/*     */     private final CustomConcurrentHashMap<E, Dummy> map;
/*     */     
/*     */     CustomInterner(GenericMapMaker<? super E, Object> mm) {
/*  59 */       this.map = mm.strongValues().keyEquivalence(Equivalences.equals()).makeCustomMap();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public E intern(E sample) {
/*     */       while (true) {
/*  68 */         CustomConcurrentHashMap.ReferenceEntry<E, Dummy> entry = this.map.getEntry(sample);
/*  69 */         if (entry != null) {
/*  70 */           E canonical = entry.getKey();
/*  71 */           if (canonical != null) {
/*  72 */             return canonical;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/*  77 */         Dummy sneaky = this.map.putIfAbsent(sample, Dummy.VALUE);
/*  78 */         if (sneaky == null) {
/*  79 */           return sample;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private enum Dummy
/*     */     {
/*  91 */       VALUE;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.lang.ref.WeakReference")
/*     */   public static <E> Interner<E> newWeakInterner() {
/* 103 */     return new CustomInterner<E>((new MapMaker()).weakKeys());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> Function<E, E> asFunction(Interner<E> interner) {
/* 112 */     return new InternerFunction<E>((Interner<E>)Preconditions.checkNotNull(interner));
/*     */   }
/*     */   
/*     */   private static class InternerFunction<E>
/*     */     implements Function<E, E> {
/*     */     private final Interner<E> interner;
/*     */     
/*     */     public InternerFunction(Interner<E> interner) {
/* 120 */       this.interner = interner;
/*     */     }
/*     */     
/*     */     public E apply(E input) {
/* 124 */       return this.interner.intern(input);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 128 */       return this.interner.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object other) {
/* 132 */       if (other instanceof InternerFunction) {
/* 133 */         InternerFunction<?> that = (InternerFunction)other;
/* 134 */         return this.interner.equals(that.interner);
/*     */       } 
/*     */       
/* 137 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Interners.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */