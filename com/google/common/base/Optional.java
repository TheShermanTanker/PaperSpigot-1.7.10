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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ @GwtCompatible
/*     */ public abstract class Optional<T>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   public static <T> Optional<T> absent() {
/*  75 */     return Absent.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Optional<T> of(T reference) {
/*  82 */     return new Present<T>(Preconditions.checkNotNull(reference));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Optional<T> fromNullable(@Nullable T nullableReference) {
/*  90 */     return (nullableReference == null) ? absent() : new Present<T>(nullableReference);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Optional() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isPresent();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract T get();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract T or(T paramT);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Optional<T> or(Optional<? extends T> paramOptional);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public abstract T or(Supplier<? extends T> paramSupplier);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public abstract T orNull();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean equals(@Nullable Object paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int hashCode();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String toString();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class Present<T>
/*     */     extends Optional<T>
/*     */   {
/*     */     private final T reference;
/*     */ 
/*     */ 
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Present(T reference) {
/* 162 */       this.reference = reference;
/*     */     }
/*     */     
/*     */     public boolean isPresent() {
/* 166 */       return true;
/*     */     }
/*     */     
/*     */     public T get() {
/* 170 */       return this.reference;
/*     */     }
/*     */     
/*     */     public T or(T defaultValue) {
/* 174 */       Preconditions.checkNotNull(defaultValue, "use orNull() instead of or(null)");
/* 175 */       return this.reference;
/*     */     }
/*     */     
/*     */     public Optional<T> or(Optional<? extends T> secondChoice) {
/* 179 */       Preconditions.checkNotNull(secondChoice);
/* 180 */       return this;
/*     */     }
/*     */     
/*     */     public T or(Supplier<? extends T> supplier) {
/* 184 */       Preconditions.checkNotNull(supplier);
/* 185 */       return this.reference;
/*     */     }
/*     */     
/*     */     public T orNull() {
/* 189 */       return this.reference;
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object object) {
/* 193 */       if (object instanceof Present) {
/* 194 */         Present<?> other = (Present)object;
/* 195 */         return this.reference.equals(other.reference);
/*     */       } 
/* 197 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 201 */       return 1502476572 + this.reference.hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 205 */       return "Optional.of(" + this.reference + ")";
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class Absent
/*     */     extends Optional<Object>
/*     */   {
/* 212 */     private static final Absent INSTANCE = new Absent();
/*     */     
/*     */     public boolean isPresent() {
/* 215 */       return false;
/*     */     }
/*     */     private static final long serialVersionUID = 0L;
/*     */     public Object get() {
/* 219 */       throw new IllegalStateException("value is absent");
/*     */     }
/*     */     
/*     */     public Object or(Object defaultValue) {
/* 223 */       return Preconditions.checkNotNull(defaultValue, "use orNull() instead of or(null)");
/*     */     }
/*     */ 
/*     */     
/*     */     public Optional<Object> or(Optional<?> secondChoice) {
/* 228 */       return (Optional<Object>)Preconditions.<Optional<?>>checkNotNull(secondChoice);
/*     */     }
/*     */     @Nullable
/*     */     public Object or(Supplier<?> supplier) {
/* 232 */       return supplier.get();
/*     */     }
/*     */     @Nullable
/*     */     public Object orNull() {
/* 236 */       return null;
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object object) {
/* 240 */       return (object == this);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 244 */       return 1502476572;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 248 */       return "Optional.absent()";
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 252 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\Optional.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */