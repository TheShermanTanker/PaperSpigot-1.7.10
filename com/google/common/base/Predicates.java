/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
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
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class Predicates
/*     */ {
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <T> Predicate<T> alwaysTrue() {
/*  55 */     return ObjectPredicate.ALWAYS_TRUE.withNarrowedType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <T> Predicate<T> alwaysFalse() {
/*  63 */     return ObjectPredicate.ALWAYS_FALSE.withNarrowedType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <T> Predicate<T> isNull() {
/*  72 */     return ObjectPredicate.IS_NULL.withNarrowedType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <T> Predicate<T> notNull() {
/*  81 */     return ObjectPredicate.NOT_NULL.withNarrowedType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Predicate<T> not(Predicate<T> predicate) {
/*  89 */     return new NotPredicate<T>(predicate);
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
/*     */   public static <T> Predicate<T> and(Iterable<? extends Predicate<? super T>> components) {
/* 103 */     return new AndPredicate<T>(defensiveCopy(components));
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
/*     */   public static <T> Predicate<T> and(Predicate<? super T>... components) {
/* 116 */     return new AndPredicate<T>(defensiveCopy(components));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Predicate<T> and(Predicate<? super T> first, Predicate<? super T> second) {
/* 127 */     return new AndPredicate<T>(asList(Preconditions.<Predicate>checkNotNull(first), Preconditions.<Predicate>checkNotNull(second)));
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
/*     */   public static <T> Predicate<T> or(Iterable<? extends Predicate<? super T>> components) {
/* 142 */     return new OrPredicate<T>(defensiveCopy(components));
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
/*     */   public static <T> Predicate<T> or(Predicate<? super T>... components) {
/* 155 */     return new OrPredicate<T>(defensiveCopy(components));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Predicate<T> or(Predicate<? super T> first, Predicate<? super T> second) {
/* 166 */     return new OrPredicate<T>(asList(Preconditions.<Predicate>checkNotNull(first), Preconditions.<Predicate>checkNotNull(second)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Predicate<T> equalTo(@Nullable T target) {
/* 175 */     return (target == null) ? isNull() : new IsEqualToPredicate<T>(target);
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
/*     */   @GwtIncompatible("Class.isInstance")
/*     */   public static Predicate<Object> instanceOf(Class<?> clazz) {
/* 197 */     return new InstanceOfPredicate(clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("Class.isAssignableFrom")
/*     */   @Beta
/*     */   public static Predicate<Class<?>> assignableFrom(Class<?> clazz) {
/* 210 */     return new AssignableFromPredicate(clazz);
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
/*     */   public static <T> Predicate<T> in(Collection<? extends T> target) {
/* 227 */     return new InPredicate<T>(target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A, B> Predicate<A> compose(Predicate<B> predicate, Function<A, ? extends B> function) {
/* 238 */     return new CompositionPredicate<A, Object>(predicate, function);
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
/*     */   @GwtIncompatible("java.util.regex.Pattern")
/*     */   public static Predicate<CharSequence> containsPattern(String pattern) {
/* 252 */     return new ContainsPatternPredicate(pattern);
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
/*     */   @GwtIncompatible("java.util.regex.Pattern")
/*     */   public static Predicate<CharSequence> contains(Pattern pattern) {
/* 265 */     return new ContainsPatternPredicate(pattern);
/*     */   }
/*     */ 
/*     */   
/*     */   enum ObjectPredicate
/*     */     implements Predicate<Object>
/*     */   {
/* 272 */     ALWAYS_TRUE {
/*     */       public boolean apply(@Nullable Object o) {
/* 274 */         return true;
/*     */       }
/*     */     },
/* 277 */     ALWAYS_FALSE {
/*     */       public boolean apply(@Nullable Object o) {
/* 279 */         return false;
/*     */       }
/*     */     },
/* 282 */     IS_NULL {
/*     */       public boolean apply(@Nullable Object o) {
/* 284 */         return (o == null);
/*     */       }
/*     */     },
/* 287 */     NOT_NULL {
/*     */       public boolean apply(@Nullable Object o) {
/* 289 */         return (o != null);
/*     */       }
/*     */     };
/*     */ 
/*     */     
/*     */     <T> Predicate<T> withNarrowedType() {
/* 295 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class NotPredicate<T> implements Predicate<T>, Serializable {
/*     */     final Predicate<T> predicate;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     NotPredicate(Predicate<T> predicate) {
/* 304 */       this.predicate = Preconditions.<Predicate<T>>checkNotNull(predicate);
/*     */     }
/*     */     
/*     */     public boolean apply(T t) {
/* 308 */       return !this.predicate.apply(t);
/*     */     }
/*     */     public int hashCode() {
/* 311 */       return this.predicate.hashCode() ^ 0xFFFFFFFF;
/*     */     }
/*     */     public boolean equals(@Nullable Object obj) {
/* 314 */       if (obj instanceof NotPredicate) {
/* 315 */         NotPredicate<?> that = (NotPredicate)obj;
/* 316 */         return this.predicate.equals(that.predicate);
/*     */       } 
/* 318 */       return false;
/*     */     }
/*     */     public String toString() {
/* 321 */       return "Not(" + this.predicate.toString() + ")";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 326 */   private static final Joiner COMMA_JOINER = Joiner.on(",");
/*     */   
/*     */   private static class AndPredicate<T> implements Predicate<T>, Serializable {
/*     */     private final List<? extends Predicate<? super T>> components;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     private AndPredicate(List<? extends Predicate<? super T>> components) {
/* 333 */       this.components = components;
/*     */     }
/*     */     
/*     */     public boolean apply(T t) {
/* 337 */       for (Predicate<? super T> predicate : this.components) {
/* 338 */         if (!predicate.apply(t)) {
/* 339 */           return false;
/*     */         }
/*     */       } 
/* 342 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 346 */       return this.components.hashCode() + 306654252;
/*     */     }
/*     */     public boolean equals(@Nullable Object obj) {
/* 349 */       if (obj instanceof AndPredicate) {
/* 350 */         AndPredicate<?> that = (AndPredicate)obj;
/* 351 */         return this.components.equals(that.components);
/*     */       } 
/* 353 */       return false;
/*     */     }
/*     */     public String toString() {
/* 356 */       return "And(" + Predicates.COMMA_JOINER.join(this.components) + ")";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class OrPredicate<T>
/*     */     implements Predicate<T>, Serializable {
/*     */     private final List<? extends Predicate<? super T>> components;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     private OrPredicate(List<? extends Predicate<? super T>> components) {
/* 366 */       this.components = components;
/*     */     }
/*     */     
/*     */     public boolean apply(T t) {
/* 370 */       for (Predicate<? super T> predicate : this.components) {
/* 371 */         if (predicate.apply(t)) {
/* 372 */           return true;
/*     */         }
/*     */       } 
/* 375 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 379 */       return this.components.hashCode() + 87855567;
/*     */     }
/*     */     public boolean equals(@Nullable Object obj) {
/* 382 */       if (obj instanceof OrPredicate) {
/* 383 */         OrPredicate<?> that = (OrPredicate)obj;
/* 384 */         return this.components.equals(that.components);
/*     */       } 
/* 386 */       return false;
/*     */     }
/*     */     public String toString() {
/* 389 */       return "Or(" + Predicates.COMMA_JOINER.join(this.components) + ")";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class IsEqualToPredicate<T>
/*     */     implements Predicate<T>, Serializable
/*     */   {
/*     */     private final T target;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     private IsEqualToPredicate(T target) {
/* 400 */       this.target = target;
/*     */     }
/*     */     
/*     */     public boolean apply(T t) {
/* 404 */       return this.target.equals(t);
/*     */     }
/*     */     public int hashCode() {
/* 407 */       return this.target.hashCode();
/*     */     }
/*     */     public boolean equals(@Nullable Object obj) {
/* 410 */       if (obj instanceof IsEqualToPredicate) {
/* 411 */         IsEqualToPredicate<?> that = (IsEqualToPredicate)obj;
/* 412 */         return this.target.equals(that.target);
/*     */       } 
/* 414 */       return false;
/*     */     }
/*     */     public String toString() {
/* 417 */       return "IsEqualTo(" + this.target + ")";
/*     */     }
/*     */   }
/*     */   
/*     */   @GwtIncompatible("Class.isInstance")
/*     */   private static class InstanceOfPredicate
/*     */     implements Predicate<Object>, Serializable
/*     */   {
/*     */     private final Class<?> clazz;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     private InstanceOfPredicate(Class<?> clazz) {
/* 429 */       this.clazz = Preconditions.<Class<?>>checkNotNull(clazz);
/*     */     }
/*     */     
/*     */     public boolean apply(@Nullable Object o) {
/* 433 */       return this.clazz.isInstance(o);
/*     */     }
/*     */     public int hashCode() {
/* 436 */       return this.clazz.hashCode();
/*     */     }
/*     */     public boolean equals(@Nullable Object obj) {
/* 439 */       if (obj instanceof InstanceOfPredicate) {
/* 440 */         InstanceOfPredicate that = (InstanceOfPredicate)obj;
/* 441 */         return (this.clazz == that.clazz);
/*     */       } 
/* 443 */       return false;
/*     */     }
/*     */     public String toString() {
/* 446 */       return "IsInstanceOf(" + this.clazz.getName() + ")";
/*     */     }
/*     */   }
/*     */   
/*     */   @GwtIncompatible("Class.isAssignableFrom")
/*     */   private static class AssignableFromPredicate
/*     */     implements Predicate<Class<?>>, Serializable
/*     */   {
/*     */     private final Class<?> clazz;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     private AssignableFromPredicate(Class<?> clazz) {
/* 458 */       this.clazz = Preconditions.<Class<?>>checkNotNull(clazz);
/*     */     }
/*     */     
/*     */     public boolean apply(Class<?> input) {
/* 462 */       return this.clazz.isAssignableFrom(input);
/*     */     }
/*     */     public int hashCode() {
/* 465 */       return this.clazz.hashCode();
/*     */     }
/*     */     public boolean equals(@Nullable Object obj) {
/* 468 */       if (obj instanceof AssignableFromPredicate) {
/* 469 */         AssignableFromPredicate that = (AssignableFromPredicate)obj;
/* 470 */         return (this.clazz == that.clazz);
/*     */       } 
/* 472 */       return false;
/*     */     }
/*     */     public String toString() {
/* 475 */       return "IsAssignableFrom(" + this.clazz.getName() + ")";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class InPredicate<T>
/*     */     implements Predicate<T>, Serializable {
/*     */     private final Collection<?> target;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     private InPredicate(Collection<?> target) {
/* 485 */       this.target = Preconditions.<Collection>checkNotNull(target);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean apply(T t) {
/*     */       try {
/* 491 */         return this.target.contains(t);
/* 492 */       } catch (NullPointerException e) {
/* 493 */         return false;
/* 494 */       } catch (ClassCastException e) {
/* 495 */         return false;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 500 */       if (obj instanceof InPredicate) {
/* 501 */         InPredicate<?> that = (InPredicate)obj;
/* 502 */         return this.target.equals(that.target);
/*     */       } 
/* 504 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 508 */       return this.target.hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 512 */       return "In(" + this.target + ")";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class CompositionPredicate<A, B>
/*     */     implements Predicate<A>, Serializable
/*     */   {
/*     */     final Predicate<B> p;
/*     */     final Function<A, ? extends B> f;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     private CompositionPredicate(Predicate<B> p, Function<A, ? extends B> f) {
/* 524 */       this.p = Preconditions.<Predicate<B>>checkNotNull(p);
/* 525 */       this.f = Preconditions.<Function<A, ? extends B>>checkNotNull(f);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean apply(A a) {
/* 530 */       return this.p.apply(this.f.apply(a));
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 534 */       if (obj instanceof CompositionPredicate) {
/* 535 */         CompositionPredicate<?, ?> that = (CompositionPredicate<?, ?>)obj;
/* 536 */         return (this.f.equals(that.f) && this.p.equals(that.p));
/*     */       } 
/* 538 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 542 */       return this.f.hashCode() ^ this.p.hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 546 */       return this.p.toString() + "(" + this.f.toString() + ")";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("Only used by other GWT-incompatible code.")
/*     */   private static class ContainsPatternPredicate
/*     */     implements Predicate<CharSequence>, Serializable
/*     */   {
/*     */     final Pattern pattern;
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */     
/*     */     ContainsPatternPredicate(Pattern pattern) {
/* 562 */       this.pattern = Preconditions.<Pattern>checkNotNull(pattern);
/*     */     }
/*     */     
/*     */     ContainsPatternPredicate(String patternStr) {
/* 566 */       this(Pattern.compile(patternStr));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean apply(CharSequence t) {
/* 571 */       return this.pattern.matcher(t).find();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 578 */       return Objects.hashCode(new Object[] { this.pattern.pattern(), Integer.valueOf(this.pattern.flags()) });
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 582 */       if (obj instanceof ContainsPatternPredicate) {
/* 583 */         ContainsPatternPredicate that = (ContainsPatternPredicate)obj;
/*     */ 
/*     */ 
/*     */         
/* 587 */         return (Objects.equal(this.pattern.pattern(), that.pattern.pattern()) && Objects.equal(Integer.valueOf(this.pattern.flags()), Integer.valueOf(that.pattern.flags())));
/*     */       } 
/*     */       
/* 590 */       return false;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 594 */       return Objects.toStringHelper(this).add("pattern", this.pattern).add("pattern.flags", Integer.toHexString(this.pattern.flags())).toString();
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
/*     */   private static <T> List<Predicate<? super T>> asList(Predicate<? super T> first, Predicate<? super T> second) {
/* 606 */     return Arrays.asList((Predicate<? super T>[])new Predicate[] { first, second });
/*     */   }
/*     */   
/*     */   private static <T> List<T> defensiveCopy(T... array) {
/* 610 */     return defensiveCopy(Arrays.asList(array));
/*     */   }
/*     */   
/*     */   static <T> List<T> defensiveCopy(Iterable<T> iterable) {
/* 614 */     ArrayList<T> list = new ArrayList<T>();
/* 615 */     for (T element : iterable) {
/* 616 */       list.add(Preconditions.checkNotNull(element));
/*     */     }
/* 618 */     return list;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\Predicates.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */