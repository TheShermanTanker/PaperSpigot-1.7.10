/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.io.Serializable;
/*     */ import java.util.Map;
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
/*     */ @GwtCompatible
/*     */ public final class Functions
/*     */ {
/*     */   public static Function<Object, String> toStringFunction() {
/*  52 */     return ToStringFunction.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum ToStringFunction
/*     */     implements Function<Object, String> {
/*  57 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public String apply(Object o) {
/*  61 */       Preconditions.checkNotNull(o);
/*  62 */       return o.toString();
/*     */     }
/*     */     
/*     */     public String toString() {
/*  66 */       return "toString";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> Function<E, E> identity() {
/*  75 */     return IdentityFunction.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum IdentityFunction
/*     */     implements Function<Object, Object> {
/*  80 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public Object apply(Object o) {
/*  84 */       return o;
/*     */     }
/*     */     
/*     */     public String toString() {
/*  88 */       return "identity";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Function<K, V> forMap(Map<K, V> map) {
/*  97 */     return new FunctionForMapNoDefault<K, V>(map);
/*     */   }
/*     */   
/*     */   private static class FunctionForMapNoDefault<K, V> implements Function<K, V>, Serializable {
/*     */     final Map<K, V> map;
/*     */     
/*     */     FunctionForMapNoDefault(Map<K, V> map) {
/* 104 */       this.map = Preconditions.<Map<K, V>>checkNotNull(map);
/*     */     }
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     public V apply(K key) {
/* 109 */       V result = this.map.get(key);
/* 110 */       Preconditions.checkArgument((result != null || this.map.containsKey(key)), "Key '%s' not present in map", new Object[] { key });
/* 111 */       return result;
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/* 115 */       if (o instanceof FunctionForMapNoDefault) {
/* 116 */         FunctionForMapNoDefault<?, ?> that = (FunctionForMapNoDefault<?, ?>)o;
/* 117 */         return this.map.equals(that.map);
/*     */       } 
/* 119 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 123 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 127 */       return "forMap(" + this.map + ")";
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
/*     */   public static <K, V> Function<K, V> forMap(Map<K, ? extends V> map, @Nullable V defaultValue) {
/* 144 */     return new ForMapWithDefault<K, V>(map, defaultValue);
/*     */   }
/*     */   
/*     */   private static class ForMapWithDefault<K, V> implements Function<K, V>, Serializable { final Map<K, ? extends V> map;
/*     */     final V defaultValue;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     ForMapWithDefault(Map<K, ? extends V> map, @Nullable V defaultValue) {
/* 152 */       this.map = Preconditions.<Map<K, ? extends V>>checkNotNull(map);
/* 153 */       this.defaultValue = defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public V apply(K key) {
/* 158 */       V result = this.map.get(key);
/* 159 */       return (result != null || this.map.containsKey(key)) ? result : this.defaultValue;
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/* 163 */       if (o instanceof ForMapWithDefault) {
/* 164 */         ForMapWithDefault<?, ?> that = (ForMapWithDefault<?, ?>)o;
/* 165 */         return (this.map.equals(that.map) && Objects.equal(this.defaultValue, that.defaultValue));
/*     */       } 
/* 167 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 171 */       return Objects.hashCode(new Object[] { this.map, this.defaultValue });
/*     */     }
/*     */     
/*     */     public String toString() {
/* 175 */       return "forMap(" + this.map + ", defaultValue=" + this.defaultValue + ")";
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A, B, C> Function<A, C> compose(Function<B, C> g, Function<A, ? extends B> f) {
/* 191 */     return new FunctionComposition<A, B, C>(g, f);
/*     */   }
/*     */   
/*     */   private static class FunctionComposition<A, B, C> implements Function<A, C>, Serializable { private final Function<B, C> g;
/*     */     private final Function<A, ? extends B> f;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     public FunctionComposition(Function<B, C> g, Function<A, ? extends B> f) {
/* 199 */       this.g = Preconditions.<Function<B, C>>checkNotNull(g);
/* 200 */       this.f = Preconditions.<Function<A, ? extends B>>checkNotNull(f);
/*     */     }
/*     */ 
/*     */     
/*     */     public C apply(A a) {
/* 205 */       return this.g.apply(this.f.apply(a));
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 209 */       if (obj instanceof FunctionComposition) {
/* 210 */         FunctionComposition<?, ?, ?> that = (FunctionComposition<?, ?, ?>)obj;
/* 211 */         return (this.f.equals(that.f) && this.g.equals(that.g));
/*     */       } 
/* 213 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 217 */       return this.f.hashCode() ^ this.g.hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 221 */       return this.g.toString() + "(" + this.f.toString() + ")";
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Function<T, Boolean> forPredicate(Predicate<T> predicate) {
/* 234 */     return new PredicateFunction<T>(predicate);
/*     */   }
/*     */   
/*     */   private static class PredicateFunction<T> implements Function<T, Boolean>, Serializable {
/*     */     private final Predicate<T> predicate;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     private PredicateFunction(Predicate<T> predicate) {
/* 242 */       this.predicate = Preconditions.<Predicate<T>>checkNotNull(predicate);
/*     */     }
/*     */ 
/*     */     
/*     */     public Boolean apply(T t) {
/* 247 */       return Boolean.valueOf(this.predicate.apply(t));
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 251 */       if (obj instanceof PredicateFunction) {
/* 252 */         PredicateFunction<?> that = (PredicateFunction)obj;
/* 253 */         return this.predicate.equals(that.predicate);
/*     */       } 
/* 255 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 259 */       return this.predicate.hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 263 */       return "forPredicate(" + this.predicate + ")";
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
/*     */   public static <E> Function<Object, E> constant(@Nullable E value) {
/* 276 */     return new ConstantFunction<E>(value);
/*     */   }
/*     */   
/*     */   private static class ConstantFunction<E> implements Function<Object, E>, Serializable { private final E value;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     public ConstantFunction(@Nullable E value) {
/* 283 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public E apply(@Nullable Object from) {
/* 288 */       return this.value;
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 292 */       if (obj instanceof ConstantFunction) {
/* 293 */         ConstantFunction<?> that = (ConstantFunction)obj;
/* 294 */         return Objects.equal(this.value, that.value);
/*     */       } 
/* 296 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 300 */       return (this.value == null) ? 0 : this.value.hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 304 */       return "constant(" + this.value + ")";
/*     */     } }
/*     */ 
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
/*     */   public static <T> Function<Object, T> forSupplier(Supplier<T> supplier) {
/* 318 */     return new SupplierFunction<T>(supplier);
/*     */   }
/*     */   
/*     */   private static class SupplierFunction<T>
/*     */     implements Function<Object, T>, Serializable {
/*     */     private final Supplier<T> supplier;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     private SupplierFunction(Supplier<T> supplier) {
/* 327 */       this.supplier = Preconditions.<Supplier<T>>checkNotNull(supplier);
/*     */     }
/*     */     
/*     */     public T apply(@Nullable Object input) {
/* 331 */       return this.supplier.get();
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 335 */       if (obj instanceof SupplierFunction) {
/* 336 */         SupplierFunction<?> that = (SupplierFunction)obj;
/* 337 */         return this.supplier.equals(that.supplier);
/*     */       } 
/* 339 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 343 */       return this.supplier.hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 347 */       return "forSupplier(" + this.supplier + ")";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\Functions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */