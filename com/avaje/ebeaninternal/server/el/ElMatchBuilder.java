/*     */ package com.avaje.ebeaninternal.server.el;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ElMatchBuilder
/*     */ {
/*     */   static class RegularExpr<T>
/*     */     implements ElMatcher<T>
/*     */   {
/*     */     final ElPropertyValue elGetValue;
/*     */     final String value;
/*     */     final Pattern pattern;
/*     */     
/*     */     RegularExpr(ElPropertyValue elGetValue, String value, int options) {
/*  42 */       this.elGetValue = elGetValue;
/*  43 */       this.value = value;
/*  44 */       this.pattern = Pattern.compile(value, options);
/*     */     }
/*     */     
/*     */     public boolean isMatch(T bean) {
/*  48 */       String v = (String)this.elGetValue.elGetValue(bean);
/*  49 */       return this.pattern.matcher(v).matches();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static abstract class BaseString<T>
/*     */     implements ElMatcher<T>
/*     */   {
/*     */     final ElPropertyValue elGetValue;
/*     */     
/*     */     final String value;
/*     */     
/*     */     public BaseString(ElPropertyValue elGetValue, String value) {
/*  62 */       this.elGetValue = elGetValue;
/*  63 */       this.value = value;
/*     */     }
/*     */     
/*     */     public abstract boolean isMatch(T param1T);
/*     */   }
/*     */   
/*     */   static class Ieq<T> extends BaseString<T> {
/*     */     Ieq(ElPropertyValue elGetValue, String value) {
/*  71 */       super(elGetValue, value);
/*     */     }
/*     */     
/*     */     public boolean isMatch(T bean) {
/*  75 */       String v = (String)this.elGetValue.elGetValue(bean);
/*  76 */       return this.value.equalsIgnoreCase(v);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class IStartsWith<T>
/*     */     implements ElMatcher<T>
/*     */   {
/*     */     final ElPropertyValue elGetValue;
/*     */     
/*     */     final CharMatch charMatch;
/*     */     
/*     */     IStartsWith(ElPropertyValue elGetValue, String value) {
/*  89 */       this.elGetValue = elGetValue;
/*  90 */       this.charMatch = new CharMatch(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isMatch(T bean) {
/*  95 */       String v = (String)this.elGetValue.elGetValue(bean);
/*  96 */       return this.charMatch.startsWith(v);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class IEndsWith<T>
/*     */     implements ElMatcher<T>
/*     */   {
/*     */     final ElPropertyValue elGetValue;
/*     */     
/*     */     final CharMatch charMatch;
/*     */     
/*     */     IEndsWith(ElPropertyValue elGetValue, String value) {
/* 109 */       this.elGetValue = elGetValue;
/* 110 */       this.charMatch = new CharMatch(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isMatch(T bean) {
/* 115 */       String v = (String)this.elGetValue.elGetValue(bean);
/* 116 */       return this.charMatch.endsWith(v);
/*     */     }
/*     */   }
/*     */   
/*     */   static class StartsWith<T> extends BaseString<T> {
/*     */     StartsWith(ElPropertyValue elGetValue, String value) {
/* 122 */       super(elGetValue, value);
/*     */     }
/*     */     
/*     */     public boolean isMatch(T bean) {
/* 126 */       String v = (String)this.elGetValue.elGetValue(bean);
/* 127 */       return this.value.startsWith(v);
/*     */     }
/*     */   }
/*     */   
/*     */   static class EndsWith<T> extends BaseString<T> {
/*     */     EndsWith(ElPropertyValue elGetValue, String value) {
/* 133 */       super(elGetValue, value);
/*     */     }
/*     */     
/*     */     public boolean isMatch(T bean) {
/* 137 */       String v = (String)this.elGetValue.elGetValue(bean);
/* 138 */       return this.value.endsWith(v);
/*     */     }
/*     */   }
/*     */   
/*     */   static class IsNull<T>
/*     */     implements ElMatcher<T> {
/*     */     final ElPropertyValue elGetValue;
/*     */     
/*     */     public IsNull(ElPropertyValue elGetValue) {
/* 147 */       this.elGetValue = elGetValue;
/*     */     }
/*     */     
/*     */     public boolean isMatch(T bean) {
/* 151 */       return (null == this.elGetValue.elGetValue(bean));
/*     */     }
/*     */   }
/*     */   
/*     */   static class IsNotNull<T>
/*     */     implements ElMatcher<T> {
/*     */     final ElPropertyValue elGetValue;
/*     */     
/*     */     public IsNotNull(ElPropertyValue elGetValue) {
/* 160 */       this.elGetValue = elGetValue;
/*     */     }
/*     */     
/*     */     public boolean isMatch(T bean) {
/* 164 */       return (null != this.elGetValue.elGetValue(bean));
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract class Base<T>
/*     */     implements ElMatcher<T>
/*     */   {
/*     */     final Object filterValue;
/*     */     final ElComparator<T> comparator;
/*     */     
/*     */     public Base(Object filterValue, ElComparator<T> comparator) {
/* 175 */       this.filterValue = filterValue;
/* 176 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     public abstract boolean isMatch(T param1T);
/*     */   }
/*     */   
/*     */   static class InSet<T>
/*     */     implements ElMatcher<T>
/*     */   {
/*     */     final Set<?> set;
/*     */     final ElPropertyValue elGetValue;
/*     */     
/*     */     public InSet(Set<?> set, ElPropertyValue elGetValue) {
/* 189 */       this.set = new HashSet(set);
/* 190 */       this.elGetValue = elGetValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isMatch(T bean) {
/* 195 */       Object value = this.elGetValue.elGetValue(bean);
/* 196 */       if (value == null) {
/* 197 */         return false;
/*     */       }
/*     */       
/* 200 */       return this.set.contains(value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class Eq<T>
/*     */     extends Base<T>
/*     */   {
/*     */     public Eq(Object filterValue, ElComparator<T> comparator) {
/* 210 */       super(filterValue, comparator);
/*     */     }
/*     */     
/*     */     public boolean isMatch(T value) {
/* 214 */       return (this.comparator.compareValue(this.filterValue, value) == 0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class Ne<T>
/*     */     extends Base<T>
/*     */   {
/*     */     public Ne(Object filterValue, ElComparator<T> comparator) {
/* 224 */       super(filterValue, comparator);
/*     */     }
/*     */     
/*     */     public boolean isMatch(T value) {
/* 228 */       return (this.comparator.compareValue(this.filterValue, value) != 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Between<T>
/*     */     implements ElMatcher<T>
/*     */   {
/*     */     final Object min;
/*     */     
/*     */     final Object max;
/*     */     final ElComparator<T> comparator;
/*     */     
/*     */     Between(Object min, Object max, ElComparator<T> comparator) {
/* 242 */       this.min = min;
/* 243 */       this.max = max;
/* 244 */       this.comparator = comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isMatch(T value) {
/* 249 */       return (this.comparator.compareValue(this.min, value) <= 0 && this.comparator.compareValue(this.max, value) >= 0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class Gt<T>
/*     */     extends Base<T>
/*     */   {
/*     */     Gt(Object filterValue, ElComparator<T> comparator) {
/* 259 */       super(filterValue, comparator);
/*     */     }
/*     */     
/*     */     public boolean isMatch(T value) {
/* 263 */       return (this.comparator.compareValue(this.filterValue, value) == -1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Ge<T>
/*     */     extends Base<T>
/*     */   {
/*     */     Ge(Object filterValue, ElComparator<T> comparator) {
/* 272 */       super(filterValue, comparator);
/*     */     }
/*     */     
/*     */     public boolean isMatch(T value) {
/* 276 */       return (this.comparator.compareValue(this.filterValue, value) >= 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Le<T>
/*     */     extends Base<T>
/*     */   {
/*     */     Le(Object filterValue, ElComparator<T> comparator) {
/* 285 */       super(filterValue, comparator);
/*     */     }
/*     */     
/*     */     public boolean isMatch(T value) {
/* 289 */       return (this.comparator.compareValue(this.filterValue, value) <= 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Lt<T>
/*     */     extends Base<T>
/*     */   {
/*     */     Lt(Object filterValue, ElComparator<T> comparator) {
/* 298 */       super(filterValue, comparator);
/*     */     }
/*     */     
/*     */     public boolean isMatch(T value) {
/* 302 */       return (this.comparator.compareValue(this.filterValue, value) == 1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\el\ElMatchBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */