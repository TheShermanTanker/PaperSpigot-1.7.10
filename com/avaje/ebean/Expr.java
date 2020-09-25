/*     */ package com.avaje.ebean;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Expr
/*     */ {
/*     */   public static Expression eq(String propertyName, Object value) {
/*  69 */     return Ebean.getExpressionFactory().eq(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression ne(String propertyName, Object value) {
/*  76 */     return Ebean.getExpressionFactory().ne(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression ieq(String propertyName, String value) {
/*  84 */     return Ebean.getExpressionFactory().ieq(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression between(String propertyName, Object value1, Object value2) {
/*  92 */     return Ebean.getExpressionFactory().between(propertyName, value1, value2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression gt(String propertyName, Object value) {
/*  99 */     return Ebean.getExpressionFactory().gt(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression ge(String propertyName, Object value) {
/* 107 */     return Ebean.getExpressionFactory().ge(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression lt(String propertyName, Object value) {
/* 114 */     return Ebean.getExpressionFactory().lt(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression le(String propertyName, Object value) {
/* 121 */     return Ebean.getExpressionFactory().le(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression isNull(String propertyName) {
/* 128 */     return Ebean.getExpressionFactory().isNull(propertyName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression isNotNull(String propertyName) {
/* 135 */     return Ebean.getExpressionFactory().isNotNull(propertyName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ExampleExpression iexampleLike(Object example) {
/* 142 */     return Ebean.getExpressionFactory().iexampleLike(example);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ExampleExpression exampleLike(Object example) {
/* 149 */     return Ebean.getExpressionFactory().exampleLike(example);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ExampleExpression exampleLike(Object example, boolean caseInsensitive, LikeType likeType) {
/* 156 */     return Ebean.getExpressionFactory().exampleLike(example, caseInsensitive, likeType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression like(String propertyName, String value) {
/* 164 */     return Ebean.getExpressionFactory().like(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression ilike(String propertyName, String value) {
/* 173 */     return Ebean.getExpressionFactory().ilike(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression startsWith(String propertyName, String value) {
/* 180 */     return Ebean.getExpressionFactory().startsWith(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression istartsWith(String propertyName, String value) {
/* 188 */     return Ebean.getExpressionFactory().istartsWith(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression endsWith(String propertyName, String value) {
/* 195 */     return Ebean.getExpressionFactory().endsWith(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression iendsWith(String propertyName, String value) {
/* 203 */     return Ebean.getExpressionFactory().iendsWith(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression contains(String propertyName, String value) {
/* 210 */     return Ebean.getExpressionFactory().contains(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression icontains(String propertyName, String value) {
/* 218 */     return Ebean.getExpressionFactory().icontains(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression in(String propertyName, Object[] values) {
/* 225 */     return Ebean.getExpressionFactory().in(propertyName, values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression in(String propertyName, Query<?> subQuery) {
/* 232 */     return Ebean.getExpressionFactory().in(propertyName, subQuery);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression in(String propertyName, Collection<?> values) {
/* 239 */     return Ebean.getExpressionFactory().in(propertyName, values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression idEq(Object value) {
/* 246 */     return Ebean.getExpressionFactory().idEq(value);
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
/*     */   public static Expression allEq(Map<String, Object> propertyMap) {
/* 260 */     return Ebean.getExpressionFactory().allEq(propertyMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression raw(String raw, Object value) {
/* 271 */     return Ebean.getExpressionFactory().raw(raw, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression raw(String raw, Object[] values) {
/* 282 */     return Ebean.getExpressionFactory().raw(raw, values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression raw(String raw) {
/* 289 */     return Ebean.getExpressionFactory().raw(raw);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression and(Expression expOne, Expression expTwo) {
/* 297 */     return Ebean.getExpressionFactory().and(expOne, expTwo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression or(Expression expOne, Expression expTwo) {
/* 305 */     return Ebean.getExpressionFactory().or(expOne, expTwo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Expression not(Expression exp) {
/* 313 */     return Ebean.getExpressionFactory().not(exp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Junction<T> conjunction(Query<T> query) {
/* 321 */     return Ebean.getExpressionFactory().conjunction(query);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Junction<T> disjunction(Query<T> query) {
/* 329 */     return Ebean.getExpressionFactory().disjunction(query);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\Expr.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */