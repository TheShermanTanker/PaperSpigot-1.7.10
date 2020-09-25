/*     */ package com.avaje.ebeaninternal.server.ldap.expression;
/*     */ 
/*     */ import com.avaje.ebean.ExampleExpression;
/*     */ import com.avaje.ebean.Expression;
/*     */ import com.avaje.ebean.ExpressionFactory;
/*     */ import com.avaje.ebean.ExpressionList;
/*     */ import com.avaje.ebean.Junction;
/*     */ import com.avaje.ebean.LikeType;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebeaninternal.server.ldap.LdapPersistenceException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class LdapExpressionFactory
/*     */   implements ExpressionFactory
/*     */ {
/*     */   public String getLang() {
/*  41 */     return "ldap";
/*     */   }
/*     */   
/*     */   public ExpressionFactory createExpressionFactory(String path) {
/*  45 */     return new LdapExpressionFactory();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Expression allEq(Map<String, Object> propertyMap) {
/*  51 */     Junction conjunction = new LdJunctionExpression.Conjunction(this);
/*     */     
/*  53 */     Iterator<Map.Entry<String, Object>> it = propertyMap.entrySet().iterator();
/*  54 */     while (it.hasNext()) {
/*  55 */       Map.Entry<String, Object> entry = it.next();
/*  56 */       conjunction.add(eq(entry.getKey(), entry.getValue()));
/*     */     } 
/*  58 */     return (Expression)conjunction;
/*     */   }
/*     */   
/*     */   public Expression and(Expression expOne, Expression expTwo) {
/*  62 */     return (Expression)new LdLogicExpression.And(expOne, expTwo);
/*     */   }
/*     */   
/*     */   public Expression between(String propertyName, Object value1, Object value2) {
/*  66 */     Expression e1 = gt(propertyName, value1);
/*  67 */     Expression e2 = lt(propertyName, value2);
/*  68 */     return and(e1, e2);
/*     */   }
/*     */   
/*     */   public Expression betweenProperties(String lowProperty, String highProperty, Object value) {
/*  72 */     throw new RuntimeException("Not Implemented");
/*     */   }
/*     */   
/*     */   public Expression contains(String propertyName, String value) {
/*  76 */     if (!value.endsWith("*")) {
/*  77 */       value = "*" + value + "*";
/*     */     }
/*  79 */     return (Expression)new LdSimpleExpression(propertyName, LdSimpleExpression.Op.EQ, value);
/*     */   }
/*     */   
/*     */   public <T> Junction<T> conjunction(Query<T> query) {
/*  83 */     return new LdJunctionExpression.Conjunction<T>(query, query.where());
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Junction<T> disjunction(Query<T> query) {
/*  88 */     return new LdJunctionExpression.Disjunction<T>(query, query.where());
/*     */   }
/*     */   
/*     */   public <T> Junction<T> conjunction(Query<T> query, ExpressionList<T> parent) {
/*  92 */     return new LdJunctionExpression.Conjunction<T>(query, parent);
/*     */   }
/*     */   
/*     */   public <T> Junction<T> disjunction(Query<T> query, ExpressionList<T> parent) {
/*  96 */     return new LdJunctionExpression.Disjunction<T>(query, parent);
/*     */   }
/*     */   
/*     */   public Expression endsWith(String propertyName, String value) {
/* 100 */     if (!value.startsWith("*")) {
/* 101 */       value = "*" + value;
/*     */     }
/* 103 */     return (Expression)new LdLikeExpression(propertyName, value);
/*     */   }
/*     */   
/*     */   public Expression eq(String propertyName, Object value) {
/* 107 */     return (Expression)new LdSimpleExpression(propertyName, LdSimpleExpression.Op.EQ, value);
/*     */   }
/*     */   
/*     */   public Expression lucene(String propertyName, String value) {
/* 111 */     throw new RuntimeException("Not Implemented");
/*     */   }
/*     */   
/*     */   public Expression lucene(String value) {
/* 115 */     throw new RuntimeException("Not Implemented");
/*     */   }
/*     */   
/*     */   public ExampleExpression exampleLike(Object example, boolean caseInsensitive, LikeType likeType) {
/* 119 */     throw new RuntimeException("Not Implemented");
/*     */   }
/*     */   
/*     */   public ExampleExpression exampleLike(Object example) {
/* 123 */     throw new RuntimeException("Not Implemented");
/*     */   }
/*     */   
/*     */   public Expression ge(String propertyName, Object value) {
/* 127 */     return (Expression)new LdSimpleExpression(propertyName, LdSimpleExpression.Op.GT_EQ, value);
/*     */   }
/*     */   
/*     */   public Expression gt(String propertyName, Object value) {
/* 131 */     return (Expression)new LdSimpleExpression(propertyName, LdSimpleExpression.Op.GT, value);
/*     */   }
/*     */   
/*     */   public Expression icontains(String propertyName, String value) {
/* 135 */     if (!value.endsWith("*")) {
/* 136 */       value = "*" + value + "*";
/*     */     }
/* 138 */     return (Expression)new LdLikeExpression(propertyName, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Expression idEq(Object value) {
/* 143 */     return null;
/*     */   }
/*     */   
/*     */   public Expression idIn(List<?> idList) {
/* 147 */     throw new RuntimeException("Not Implemented");
/*     */   }
/*     */   
/*     */   public Expression iendsWith(String propertyName, String value) {
/* 151 */     if (!value.startsWith("*")) {
/* 152 */       value = "*" + value;
/*     */     }
/* 154 */     return (Expression)new LdLikeExpression(propertyName, value);
/*     */   }
/*     */   
/*     */   public Expression ieq(String propertyName, String value) {
/* 158 */     return (Expression)new LdSimpleExpression(propertyName, LdSimpleExpression.Op.EQ, value);
/*     */   }
/*     */   
/*     */   public ExampleExpression iexampleLike(Object example) {
/* 162 */     throw new RuntimeException("Not Implemented");
/*     */   }
/*     */   
/*     */   public Expression ilike(String propertyName, String value) {
/* 166 */     return (Expression)new LdLikeExpression(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Expression in(String propertyName, Collection<?> values) {
/* 172 */     if (values == null || values.isEmpty()) {
/* 173 */       throw new LdapPersistenceException("collection can't be empty for Ldap");
/*     */     }
/*     */     
/* 176 */     Junction disjunction = new LdJunctionExpression.Disjunction(this);
/* 177 */     for (Object v : values) {
/* 178 */       disjunction.add(eq(propertyName, v));
/*     */     }
/*     */     
/* 181 */     return (Expression)disjunction;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Expression in(String propertyName, Object[] values) {
/* 187 */     if (values == null || values.length == 0) {
/* 188 */       throw new LdapPersistenceException("values can't be empty for Ldap");
/*     */     }
/*     */     
/* 191 */     Junction disjunction = new LdJunctionExpression.Disjunction(this);
/* 192 */     for (Object v : values) {
/* 193 */       disjunction.add(eq(propertyName, v));
/*     */     }
/*     */     
/* 196 */     return (Expression)disjunction;
/*     */   }
/*     */   
/*     */   public Expression in(String propertyName, Query<?> subQuery) {
/* 200 */     throw new RuntimeException("Not Implemented");
/*     */   }
/*     */   
/*     */   public Expression isNotNull(String propertyName) {
/* 204 */     return (Expression)new LdPresentExpression(propertyName);
/*     */   }
/*     */   
/*     */   public Expression isNull(String propertyName) {
/* 208 */     LdPresentExpression exp = new LdPresentExpression(propertyName);
/* 209 */     return (Expression)new LdNotExpression((Expression)exp);
/*     */   }
/*     */   
/*     */   public Expression istartsWith(String propertyName, String value) {
/* 213 */     if (!value.endsWith("*")) {
/* 214 */       value = value + "*";
/*     */     }
/* 216 */     return (Expression)new LdLikeExpression(propertyName, value);
/*     */   }
/*     */   
/*     */   public Expression le(String propertyName, Object value) {
/* 220 */     return (Expression)new LdSimpleExpression(propertyName, LdSimpleExpression.Op.LT_EQ, value);
/*     */   }
/*     */   
/*     */   public Expression like(String propertyName, String value) {
/* 224 */     return (Expression)new LdLikeExpression(propertyName, value);
/*     */   }
/*     */   
/*     */   public Expression lt(String propertyName, Object value) {
/* 228 */     return (Expression)new LdSimpleExpression(propertyName, LdSimpleExpression.Op.LT, value);
/*     */   }
/*     */   
/*     */   public Expression ne(String propertyName, Object value) {
/* 232 */     return (Expression)new LdSimpleExpression(propertyName, LdSimpleExpression.Op.NOT_EQ, value);
/*     */   }
/*     */   
/*     */   public Expression not(Expression exp) {
/* 236 */     return (Expression)new LdNotExpression(exp);
/*     */   }
/*     */   
/*     */   public Expression or(Expression expOne, Expression expTwo) {
/* 240 */     return (Expression)new LdLogicExpression.Or(expOne, expTwo);
/*     */   }
/*     */   
/*     */   public Expression raw(String raw, Object value) {
/* 244 */     if (value != null) {
/* 245 */       return (Expression)new LdRawExpression(raw, new Object[] { value });
/*     */     }
/*     */     
/* 248 */     return (Expression)new LdRawExpression(raw, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Expression raw(String raw, Object[] values) {
/* 253 */     return (Expression)new LdRawExpression(raw, values);
/*     */   }
/*     */   
/*     */   public Expression raw(String raw) {
/* 257 */     return (Expression)new LdRawExpression(raw, null);
/*     */   }
/*     */   
/*     */   public Expression startsWith(String propertyName, String value) {
/* 261 */     if (!value.endsWith("*")) {
/* 262 */       value = value + "*";
/*     */     }
/* 264 */     return (Expression)new LdLikeExpression(propertyName, value);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdapExpressionFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */