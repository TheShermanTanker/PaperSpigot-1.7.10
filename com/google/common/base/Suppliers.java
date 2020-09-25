/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import java.io.Serializable;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ @GwtCompatible
/*     */ public final class Suppliers
/*     */ {
/*     */   public static <F, T> Supplier<T> compose(Function<? super F, T> function, Supplier<F> supplier) {
/*  51 */     Preconditions.checkNotNull(function);
/*  52 */     Preconditions.checkNotNull(supplier);
/*  53 */     return new SupplierComposition<F, T>(function, supplier);
/*     */   }
/*     */   
/*     */   private static class SupplierComposition<F, T> implements Supplier<T>, Serializable {
/*     */     final Function<? super F, T> function;
/*     */     final Supplier<F> supplier;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     SupplierComposition(Function<? super F, T> function, Supplier<F> supplier) {
/*  62 */       this.function = function;
/*  63 */       this.supplier = supplier;
/*     */     }
/*     */     
/*     */     public T get() {
/*  67 */       return this.function.apply(this.supplier.get());
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
/*     */   public static <T> Supplier<T> memoize(Supplier<T> delegate) {
/*  86 */     return (delegate instanceof MemoizingSupplier) ? delegate : new MemoizingSupplier<T>(Preconditions.<Supplier<T>>checkNotNull(delegate));
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static class MemoizingSupplier<T>
/*     */     implements Supplier<T>, Serializable
/*     */   {
/*     */     final Supplier<T> delegate;
/*     */     volatile transient boolean initialized;
/*     */     transient T value;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     MemoizingSupplier(Supplier<T> delegate) {
/* 100 */       this.delegate = delegate;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public T get() {
/* 106 */       if (!this.initialized) {
/* 107 */         synchronized (this) {
/* 108 */           if (!this.initialized) {
/* 109 */             T t = this.delegate.get();
/* 110 */             this.value = t;
/* 111 */             this.initialized = true;
/* 112 */             return t;
/*     */           } 
/*     */         } 
/*     */       }
/* 116 */       return this.value;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Supplier<T> memoizeWithExpiration(Supplier<T> delegate, long duration, TimeUnit unit) {
/* 142 */     return new ExpiringMemoizingSupplier<T>(delegate, duration, unit);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   static class ExpiringMemoizingSupplier<T>
/*     */     implements Supplier<T>, Serializable {
/*     */     final Supplier<T> delegate;
/*     */     final long durationNanos;
/*     */     volatile transient T value;
/*     */     volatile transient long expirationNanos;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     ExpiringMemoizingSupplier(Supplier<T> delegate, long duration, TimeUnit unit) {
/* 155 */       this.delegate = Preconditions.<Supplier<T>>checkNotNull(delegate);
/* 156 */       this.durationNanos = unit.toNanos(duration);
/* 157 */       Preconditions.checkArgument((duration > 0L));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public T get() {
/* 168 */       long nanos = this.expirationNanos;
/* 169 */       long now = Platform.systemNanoTime();
/* 170 */       if (nanos == 0L || now - nanos >= 0L) {
/* 171 */         synchronized (this) {
/* 172 */           if (nanos == this.expirationNanos) {
/* 173 */             T t = this.delegate.get();
/* 174 */             this.value = t;
/* 175 */             nanos = now + this.durationNanos;
/*     */ 
/*     */             
/* 178 */             this.expirationNanos = (nanos == 0L) ? 1L : nanos;
/* 179 */             return t;
/*     */           } 
/*     */         } 
/*     */       }
/* 183 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Supplier<T> ofInstance(@Nullable T instance) {
/* 193 */     return new SupplierOfInstance<T>(instance);
/*     */   }
/*     */   
/*     */   private static class SupplierOfInstance<T> implements Supplier<T>, Serializable {
/*     */     final T instance;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     SupplierOfInstance(@Nullable T instance) {
/* 201 */       this.instance = instance;
/*     */     }
/*     */     
/*     */     public T get() {
/* 205 */       return this.instance;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Supplier<T> synchronizedSupplier(Supplier<T> delegate) {
/* 215 */     return new ThreadSafeSupplier<T>(Preconditions.<Supplier<T>>checkNotNull(delegate));
/*     */   }
/*     */   
/*     */   private static class ThreadSafeSupplier<T> implements Supplier<T>, Serializable {
/*     */     final Supplier<T> delegate;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     ThreadSafeSupplier(Supplier<T> delegate) {
/* 223 */       this.delegate = delegate;
/*     */     }
/*     */     
/*     */     public T get() {
/* 227 */       synchronized (this.delegate) {
/* 228 */         return this.delegate.get();
/*     */       } 
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
/*     */   @Beta
/*     */   public static <T> Function<Supplier<T>, T> supplierFunction() {
/* 243 */     return SupplierFunction.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum SupplierFunction implements Function<Supplier<?>, Object> {
/* 247 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public Object apply(Supplier<?> input) {
/* 251 */       return input.get();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\Suppliers.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */