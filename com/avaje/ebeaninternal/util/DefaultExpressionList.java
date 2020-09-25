/*     */ package com.avaje.ebeaninternal.util;
/*     */ 
/*     */ import com.avaje.ebean.Expression;
/*     */ import com.avaje.ebean.ExpressionFactory;
/*     */ import com.avaje.ebean.ExpressionList;
/*     */ import com.avaje.ebean.FutureIds;
/*     */ import com.avaje.ebean.FutureList;
/*     */ import com.avaje.ebean.FutureRowCount;
/*     */ import com.avaje.ebean.Junction;
/*     */ import com.avaje.ebean.OrderBy;
/*     */ import com.avaje.ebean.PagingList;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.QueryIterator;
/*     */ import com.avaje.ebean.QueryListener;
/*     */ import com.avaje.ebean.QueryResultVisitor;
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*     */ import com.avaje.ebeaninternal.api.SpiExpression;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionList;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultExpressionList<T>
/*     */   implements SpiExpressionList<T>
/*     */ {
/*     */   private static final long serialVersionUID = -6992345500247035947L;
/*  36 */   private final ArrayList<SpiExpression> list = new ArrayList<SpiExpression>();
/*     */   
/*     */   private final Query<T> query;
/*     */   
/*     */   private final ExpressionList<T> parentExprList;
/*     */   
/*     */   private transient ExpressionFactory expr;
/*     */   
/*     */   private final String exprLang;
/*     */   private final String listAndStart;
/*     */   private final String listAndEnd;
/*     */   private final String listAndJoin;
/*     */   
/*     */   public DefaultExpressionList(Query<T> query, ExpressionList<T> parentExprList) {
/*  50 */     this(query, query.getExpressionFactory(), parentExprList);
/*     */   }
/*     */   
/*     */   public DefaultExpressionList(Query<T> query, ExpressionFactory expr, ExpressionList<T> parentExprList) {
/*  54 */     this.query = query;
/*  55 */     this.expr = expr;
/*  56 */     this.exprLang = expr.getLang();
/*  57 */     this.parentExprList = parentExprList;
/*     */     
/*  59 */     if ("ldap".equals(this.exprLang)) {
/*     */       
/*  61 */       this.listAndStart = "(&";
/*  62 */       this.listAndEnd = ")";
/*  63 */       this.listAndJoin = "";
/*     */     } else {
/*     */       
/*  66 */       this.listAndStart = "";
/*  67 */       this.listAndEnd = "";
/*  68 */       this.listAndJoin = " and ";
/*     */     } 
/*     */   }
/*     */   
/*     */   public void trimPath(int prefixTrim) {
/*  73 */     throw new RuntimeException("Only allowed on FilterExpressionList");
/*     */   }
/*     */   
/*     */   public List<SpiExpression> internalList() {
/*  77 */     return this.list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpressionFactory(ExpressionFactory expr) {
/*  87 */     this.expr = expr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultExpressionList<T> copy(Query<T> query) {
/*  97 */     DefaultExpressionList<T> copy = new DefaultExpressionList(query, this.expr, null);
/*  98 */     copy.list.addAll(this.list);
/*  99 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins whereManyJoins) {
/* 107 */     for (int i = 0; i < this.list.size(); i++) {
/* 108 */       ((SpiExpression)this.list.get(i)).containsMany(desc, whereManyJoins);
/*     */     }
/*     */   }
/*     */   
/*     */   public ExpressionList<T> endJunction() {
/* 113 */     return (this.parentExprList == null) ? (ExpressionList<T>)this : this.parentExprList;
/*     */   }
/*     */   
/*     */   public Query<T> query() {
/* 117 */     return this.query;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> where() {
/* 121 */     return this.query.where();
/*     */   }
/*     */   
/*     */   public OrderBy<T> order() {
/* 125 */     return this.query.order();
/*     */   }
/*     */   
/*     */   public OrderBy<T> orderBy() {
/* 129 */     return this.query.order();
/*     */   }
/*     */   
/*     */   public Query<T> order(String orderByClause) {
/* 133 */     return this.query.order(orderByClause);
/*     */   }
/*     */   
/*     */   public Query<T> orderBy(String orderBy) {
/* 137 */     return this.query.order(orderBy);
/*     */   }
/*     */   
/*     */   public Query<T> setOrderBy(String orderBy) {
/* 141 */     return this.query.order(orderBy);
/*     */   }
/*     */   
/*     */   public FutureIds<T> findFutureIds() {
/* 145 */     return this.query.findFutureIds();
/*     */   }
/*     */   
/*     */   public FutureRowCount<T> findFutureRowCount() {
/* 149 */     return this.query.findFutureRowCount();
/*     */   }
/*     */   
/*     */   public FutureList<T> findFutureList() {
/* 153 */     return this.query.findFutureList();
/*     */   }
/*     */   
/*     */   public PagingList<T> findPagingList(int pageSize) {
/* 157 */     return this.query.findPagingList(pageSize);
/*     */   }
/*     */   
/*     */   public int findRowCount() {
/* 161 */     return this.query.findRowCount();
/*     */   }
/*     */   
/*     */   public List<Object> findIds() {
/* 165 */     return this.query.findIds();
/*     */   }
/*     */   
/*     */   public void findVisit(QueryResultVisitor<T> visitor) {
/* 169 */     this.query.findVisit(visitor);
/*     */   }
/*     */   
/*     */   public QueryIterator<T> findIterate() {
/* 173 */     return this.query.findIterate();
/*     */   }
/*     */   
/*     */   public List<T> findList() {
/* 177 */     return this.query.findList();
/*     */   }
/*     */   
/*     */   public Set<T> findSet() {
/* 181 */     return this.query.findSet();
/*     */   }
/*     */   
/*     */   public Map<?, T> findMap() {
/* 185 */     return this.query.findMap();
/*     */   }
/*     */   
/*     */   public <K> Map<K, T> findMap(String keyProperty, Class<K> keyType) {
/* 189 */     return this.query.findMap(keyProperty, keyType);
/*     */   }
/*     */   
/*     */   public T findUnique() {
/* 193 */     return (T)this.query.findUnique();
/*     */   }
/*     */   
/*     */   public ExpressionList<T> filterMany(String prop) {
/* 197 */     return this.query.filterMany(prop);
/*     */   }
/*     */   
/*     */   public Query<T> select(String fetchProperties) {
/* 201 */     return this.query.select(fetchProperties);
/*     */   }
/*     */   
/*     */   public Query<T> join(String assocProperties) {
/* 205 */     return this.query.fetch(assocProperties);
/*     */   }
/*     */   
/*     */   public Query<T> join(String assocProperty, String assocProperties) {
/* 209 */     return this.query.fetch(assocProperty, assocProperties);
/*     */   }
/*     */   
/*     */   public Query<T> setFirstRow(int firstRow) {
/* 213 */     return this.query.setFirstRow(firstRow);
/*     */   }
/*     */   
/*     */   public Query<T> setMaxRows(int maxRows) {
/* 217 */     return this.query.setMaxRows(maxRows);
/*     */   }
/*     */   
/*     */   public Query<T> setBackgroundFetchAfter(int backgroundFetchAfter) {
/* 221 */     return this.query.setBackgroundFetchAfter(backgroundFetchAfter);
/*     */   }
/*     */   
/*     */   public Query<T> setMapKey(String mapKey) {
/* 225 */     return this.query.setMapKey(mapKey);
/*     */   }
/*     */   
/*     */   public Query<T> setListener(QueryListener<T> queryListener) {
/* 229 */     return this.query.setListener(queryListener);
/*     */   }
/*     */   
/*     */   public Query<T> setUseCache(boolean useCache) {
/* 233 */     return this.query.setUseCache(useCache);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> having() {
/* 237 */     return this.query.having();
/*     */   }
/*     */   
/*     */   public ExpressionList<T> add(Expression expr) {
/* 241 */     this.list.add((SpiExpression)expr);
/* 242 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> addAll(ExpressionList<T> exprList) {
/* 246 */     SpiExpressionList<T> spiList = (SpiExpressionList<T>)exprList;
/* 247 */     this.list.addAll(spiList.getUnderlyingList());
/* 248 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public List<SpiExpression> getUnderlyingList() {
/* 252 */     return this.list;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 256 */     return this.list.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public String buildSql(SpiExpressionRequest request) {
/* 261 */     request.append(this.listAndStart);
/* 262 */     for (int i = 0, size = this.list.size(); i < size; i++) {
/* 263 */       SpiExpression expression = this.list.get(i);
/* 264 */       if (i > 0) {
/* 265 */         request.append(this.listAndJoin);
/*     */       }
/* 267 */       expression.addSql(request);
/*     */     } 
/* 269 */     request.append(this.listAndEnd);
/* 270 */     return request.getSql();
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<Object> buildBindValues(SpiExpressionRequest request) {
/* 275 */     for (int i = 0, size = this.list.size(); i < size; i++) {
/* 276 */       SpiExpression expression = this.list.get(i);
/* 277 */       expression.addBindValues(request);
/*     */     } 
/* 279 */     return request.getBindValues();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryAutoFetchHash() {
/* 287 */     int hash = DefaultExpressionList.class.getName().hashCode();
/* 288 */     for (int i = 0, size = this.list.size(); i < size; i++) {
/* 289 */       SpiExpression expression = this.list.get(i);
/* 290 */       hash = hash * 31 + expression.queryAutoFetchHash();
/*     */     } 
/* 292 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 300 */     int hash = DefaultExpressionList.class.getName().hashCode();
/* 301 */     for (int i = 0, size = this.list.size(); i < size; i++) {
/* 302 */       SpiExpression expression = this.list.get(i);
/* 303 */       hash = hash * 31 + expression.queryPlanHash(request);
/*     */     } 
/* 305 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryBindHash() {
/* 312 */     int hash = DefaultExpressionList.class.getName().hashCode();
/* 313 */     for (int i = 0, size = this.list.size(); i < size; i++) {
/* 314 */       SpiExpression expression = this.list.get(i);
/* 315 */       hash = hash * 31 + expression.queryBindHash();
/*     */     } 
/* 317 */     return hash;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> eq(String propertyName, Object value) {
/* 321 */     add(this.expr.eq(propertyName, value));
/* 322 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> ieq(String propertyName, String value) {
/* 326 */     add(this.expr.ieq(propertyName, value));
/* 327 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> ne(String propertyName, Object value) {
/* 331 */     add(this.expr.ne(propertyName, value));
/* 332 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> allEq(Map<String, Object> propertyMap) {
/* 336 */     add(this.expr.allEq(propertyMap));
/* 337 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> and(Expression expOne, Expression expTwo) {
/* 341 */     add(this.expr.and(expOne, expTwo));
/* 342 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> between(String propertyName, Object value1, Object value2) {
/* 346 */     add(this.expr.between(propertyName, value1, value2));
/* 347 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> betweenProperties(String lowProperty, String highProperty, Object value) {
/* 351 */     add(this.expr.betweenProperties(lowProperty, highProperty, value));
/* 352 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public Junction<T> conjunction() {
/* 356 */     Junction<T> conjunction = this.expr.conjunction(this.query, (ExpressionList)this);
/* 357 */     add((Expression)conjunction);
/* 358 */     return conjunction;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> contains(String propertyName, String value) {
/* 362 */     add(this.expr.contains(propertyName, value));
/* 363 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public Junction<T> disjunction() {
/* 367 */     Junction<T> disjunction = this.expr.disjunction(this.query, (ExpressionList)this);
/* 368 */     add((Expression)disjunction);
/* 369 */     return disjunction;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> endsWith(String propertyName, String value) {
/* 373 */     add(this.expr.endsWith(propertyName, value));
/* 374 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> ge(String propertyName, Object value) {
/* 378 */     add(this.expr.ge(propertyName, value));
/* 379 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> gt(String propertyName, Object value) {
/* 383 */     add(this.expr.gt(propertyName, value));
/* 384 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> icontains(String propertyName, String value) {
/* 388 */     add(this.expr.icontains(propertyName, value));
/* 389 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> idIn(List<?> idList) {
/* 393 */     add(this.expr.idIn(idList));
/* 394 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> idEq(Object value) {
/* 398 */     if (this.query != null && this.parentExprList == null) {
/* 399 */       this.query.setId(value);
/*     */     } else {
/* 401 */       add(this.expr.idEq(value));
/*     */     } 
/* 403 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> iendsWith(String propertyName, String value) {
/* 407 */     add(this.expr.iendsWith(propertyName, value));
/* 408 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> ilike(String propertyName, String value) {
/* 412 */     add(this.expr.ilike(propertyName, value));
/* 413 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> in(String propertyName, Query<?> subQuery) {
/* 417 */     add(this.expr.in(propertyName, subQuery));
/* 418 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> in(String propertyName, Collection<?> values) {
/* 422 */     add(this.expr.in(propertyName, values));
/* 423 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> in(String propertyName, Object... values) {
/* 427 */     add(this.expr.in(propertyName, values));
/* 428 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> isNotNull(String propertyName) {
/* 432 */     add(this.expr.isNotNull(propertyName));
/* 433 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> isNull(String propertyName) {
/* 437 */     add(this.expr.isNull(propertyName));
/* 438 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> istartsWith(String propertyName, String value) {
/* 442 */     add(this.expr.istartsWith(propertyName, value));
/* 443 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> le(String propertyName, Object value) {
/* 447 */     add(this.expr.le(propertyName, value));
/* 448 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> exampleLike(Object example) {
/* 452 */     add((Expression)this.expr.exampleLike(example));
/* 453 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> iexampleLike(Object example) {
/* 457 */     add((Expression)this.expr.iexampleLike(example));
/* 458 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> like(String propertyName, String value) {
/* 462 */     add(this.expr.like(propertyName, value));
/* 463 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> lt(String propertyName, Object value) {
/* 467 */     add(this.expr.lt(propertyName, value));
/* 468 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> not(Expression exp) {
/* 472 */     add(this.expr.not(exp));
/* 473 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> or(Expression expOne, Expression expTwo) {
/* 477 */     add(this.expr.or(expOne, expTwo));
/* 478 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> raw(String raw, Object value) {
/* 482 */     add(this.expr.raw(raw, value));
/* 483 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> raw(String raw, Object[] values) {
/* 487 */     add(this.expr.raw(raw, values));
/* 488 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> raw(String raw) {
/* 492 */     add(this.expr.raw(raw));
/* 493 */     return (ExpressionList<T>)this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> startsWith(String propertyName, String value) {
/* 497 */     add(this.expr.startsWith(propertyName, value));
/* 498 */     return (ExpressionList<T>)this;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninterna\\util\DefaultExpressionList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */