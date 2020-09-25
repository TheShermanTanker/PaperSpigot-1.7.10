/*     */ package net.minecraft.util.com.google.common.collect;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtIncompatible;
/*     */ import net.minecraft.util.com.google.common.base.Equivalence;
/*     */ import net.minecraft.util.com.google.common.base.Function;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.lang.ref.WeakReference")
/*     */   public static <E> Interner<E> newWeakInterner() {
/*  63 */     return new WeakInterner<E>();
/*     */   }
/*     */   
/*     */   private static class WeakInterner<E>
/*     */     implements Interner<E> {
/*  68 */     private final MapMakerInternalMap<E, Dummy> map = (new MapMaker()).weakKeys().keyEquivalence(Equivalence.equals()).makeCustomMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public E intern(E sample) {
/*     */       while (true) {
/*  76 */         MapMakerInternalMap.ReferenceEntry<E, Dummy> entry = this.map.getEntry(sample);
/*  77 */         if (entry != null) {
/*  78 */           E canonical = entry.getKey();
/*  79 */           if (canonical != null) {
/*  80 */             return canonical;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/*  85 */         Dummy sneaky = this.map.putIfAbsent(sample, Dummy.VALUE);
/*  86 */         if (sneaky == null) {
/*  87 */           return sample;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private WeakInterner() {}
/*     */ 
/*     */     
/*     */     private enum Dummy
/*     */     {
/*  99 */       VALUE;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> Function<E, E> asFunction(Interner<E> interner) {
/* 108 */     return new InternerFunction<E>((Interner<E>)Preconditions.checkNotNull(interner));
/*     */   }
/*     */   
/*     */   private static class InternerFunction<E>
/*     */     implements Function<E, E> {
/*     */     private final Interner<E> interner;
/*     */     
/*     */     public InternerFunction(Interner<E> interner) {
/* 116 */       this.interner = interner;
/*     */     }
/*     */     
/*     */     public E apply(E input) {
/* 120 */       return this.interner.intern(input);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 124 */       return this.interner.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object other) {
/* 128 */       if (other instanceof InternerFunction) {
/* 129 */         InternerFunction<?> that = (InternerFunction)other;
/* 130 */         return this.interner.equals(that.interner);
/*     */       } 
/*     */       
/* 133 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\Interners.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */