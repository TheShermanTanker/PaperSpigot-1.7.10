/*     */ package com.avaje.ebeaninternal.server.ldap.expression;
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
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.util.DefaultExpressionList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ abstract class LdJunctionExpression<T>
/*     */   implements Junction<T>, SpiExpression
/*     */ {
/*     */   private static final long serialVersionUID = -7422204102750462677L;
/*     */   private final DefaultExpressionList<T> exprList;
/*     */   private final String joinType;
/*     */   
/*     */   static class Conjunction<T>
/*     */     extends LdJunctionExpression<T> {
/*     */     private static final long serialVersionUID = -645619859900030679L;
/*     */     
/*     */     Conjunction(Query<T> query, ExpressionList<T> parent) {
/*  39 */       super("&", query, parent);
/*     */     }
/*     */     
/*     */     Conjunction(ExpressionFactory exprFactory) {
/*  43 */       super("&", exprFactory);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Disjunction<T>
/*     */     extends LdJunctionExpression<T> {
/*     */     private static final long serialVersionUID = -8464470066692221414L;
/*     */     
/*     */     Disjunction(Query<T> query, ExpressionList<T> parent) {
/*  52 */       super("|", query, parent);
/*     */     }
/*     */     
/*     */     Disjunction(ExpressionFactory exprFactory) {
/*  56 */       super("|", exprFactory);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LdJunctionExpression(String joinType, Query<T> query, ExpressionList<T> parent) {
/*  65 */     this.joinType = joinType;
/*  66 */     this.exprList = new DefaultExpressionList(query, parent);
/*     */   }
/*     */   
/*     */   LdJunctionExpression(String joinType, ExpressionFactory exprFactory) {
/*  70 */     this.joinType = joinType;
/*  71 */     this.exprList = new DefaultExpressionList(null, exprFactory, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
/*  76 */     List<SpiExpression> list = this.exprList.internalList();
/*  77 */     for (int i = 0; i < list.size(); i++) {
/*  78 */       ((SpiExpression)list.get(i)).containsMany(desc, manyWhereJoin);
/*     */     }
/*     */   }
/*     */   
/*     */   public Junction<T> add(Expression item) {
/*  83 */     SpiExpression i = (SpiExpression)item;
/*  84 */     this.exprList.add((Expression)i);
/*  85 */     return this;
/*     */   }
/*     */   
/*     */   public Junction<T> addAll(ExpressionList<T> addList) {
/*  89 */     this.exprList.addAll(addList);
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBindValues(SpiExpressionRequest request) {
/*  95 */     List<SpiExpression> list = this.exprList.internalList();
/*  96 */     for (int i = 0; i < list.size(); i++) {
/*  97 */       SpiExpression item = list.get(i);
/*  98 */       item.addBindValues(request);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSql(SpiExpressionRequest request) {
/* 104 */     List<SpiExpression> list = this.exprList.internalList();
/* 105 */     if (!list.isEmpty()) {
/* 106 */       request.append("(");
/* 107 */       request.append(this.joinType);
/*     */       
/* 109 */       for (int i = 0; i < list.size(); i++) {
/* 110 */         SpiExpression item = list.get(i);
/* 111 */         item.addSql(request);
/*     */       } 
/*     */       
/* 114 */       request.append(") ");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryAutoFetchHash() {
/* 122 */     int hc = LdJunctionExpression.class.getName().hashCode();
/* 123 */     hc = hc * 31 + this.joinType.hashCode();
/* 124 */     List<SpiExpression> list = this.exprList.internalList();
/* 125 */     for (int i = 0; i < list.size(); i++) {
/* 126 */       hc = hc * 31 + ((SpiExpression)list.get(i)).queryAutoFetchHash();
/*     */     }
/*     */     
/* 129 */     return hc;
/*     */   }
/*     */   
/*     */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 133 */     int hc = LdJunctionExpression.class.getName().hashCode();
/* 134 */     hc = hc * 31 + this.joinType.hashCode();
/* 135 */     List<SpiExpression> list = this.exprList.internalList();
/* 136 */     for (int i = 0; i < list.size(); i++) {
/* 137 */       hc = hc * 31 + ((SpiExpression)list.get(i)).queryPlanHash(request);
/*     */     }
/*     */     
/* 140 */     return hc;
/*     */   }
/*     */   
/*     */   public int queryBindHash() {
/* 144 */     int hc = LdJunctionExpression.class.getName().hashCode();
/* 145 */     List<SpiExpression> list = this.exprList.internalList();
/* 146 */     for (int i = 0; i < list.size(); i++) {
/* 147 */       hc = hc * 31 + ((SpiExpression)list.get(i)).queryBindHash();
/*     */     }
/*     */     
/* 150 */     return hc;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> endJunction() {
/* 154 */     return this.exprList.endJunction();
/*     */   }
/*     */   
/*     */   public ExpressionList<T> allEq(Map<String, Object> propertyMap) {
/* 158 */     return this.exprList.allEq(propertyMap);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> and(Expression expOne, Expression expTwo) {
/* 162 */     return this.exprList.and(expOne, expTwo);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> between(String propertyName, Object value1, Object value2) {
/* 166 */     return this.exprList.between(propertyName, value1, value2);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> betweenProperties(String lowProperty, String highProperty, Object value) {
/* 170 */     return this.exprList.betweenProperties(lowProperty, highProperty, value);
/*     */   }
/*     */   
/*     */   public Junction<T> conjunction() {
/* 174 */     return this.exprList.conjunction();
/*     */   }
/*     */   
/*     */   public ExpressionList<T> contains(String propertyName, String value) {
/* 178 */     return this.exprList.contains(propertyName, value);
/*     */   }
/*     */   
/*     */   public Junction<T> disjunction() {
/* 182 */     return this.exprList.disjunction();
/*     */   }
/*     */   
/*     */   public ExpressionList<T> endsWith(String propertyName, String value) {
/* 186 */     return this.exprList.endsWith(propertyName, value);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> eq(String propertyName, Object value) {
/* 190 */     return this.exprList.eq(propertyName, value);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> exampleLike(Object example) {
/* 194 */     return this.exprList.exampleLike(example);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> filterMany(String prop) {
/* 198 */     throw new RuntimeException("filterMany not allowed on Junction expression list");
/*     */   }
/*     */   
/*     */   public FutureIds<T> findFutureIds() {
/* 202 */     return this.exprList.findFutureIds();
/*     */   }
/*     */   
/*     */   public FutureList<T> findFutureList() {
/* 206 */     return this.exprList.findFutureList();
/*     */   }
/*     */   
/*     */   public FutureRowCount<T> findFutureRowCount() {
/* 210 */     return this.exprList.findFutureRowCount();
/*     */   }
/*     */   
/*     */   public List<Object> findIds() {
/* 214 */     return this.exprList.findIds();
/*     */   }
/*     */   
/*     */   public void findVisit(QueryResultVisitor<T> visitor) {
/* 218 */     this.exprList.findVisit(visitor);
/*     */   }
/*     */   
/*     */   public QueryIterator<T> findIterate() {
/* 222 */     return this.exprList.findIterate();
/*     */   }
/*     */   
/*     */   public List<T> findList() {
/* 226 */     return this.exprList.findList();
/*     */   }
/*     */   
/*     */   public Map<?, T> findMap() {
/* 230 */     return this.exprList.findMap();
/*     */   }
/*     */   
/*     */   public <K> Map<K, T> findMap(String keyProperty, Class<K> keyType) {
/* 234 */     return this.exprList.findMap(keyProperty, keyType);
/*     */   }
/*     */   
/*     */   public PagingList<T> findPagingList(int pageSize) {
/* 238 */     return this.exprList.findPagingList(pageSize);
/*     */   }
/*     */   
/*     */   public int findRowCount() {
/* 242 */     return this.exprList.findRowCount();
/*     */   }
/*     */   
/*     */   public Set<T> findSet() {
/* 246 */     return this.exprList.findSet();
/*     */   }
/*     */   
/*     */   public T findUnique() {
/* 250 */     return (T)this.exprList.findUnique();
/*     */   }
/*     */   
/*     */   public ExpressionList<T> ge(String propertyName, Object value) {
/* 254 */     return this.exprList.ge(propertyName, value);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> gt(String propertyName, Object value) {
/* 258 */     return this.exprList.gt(propertyName, value);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> having() {
/* 262 */     throw new RuntimeException("having() not allowed on Junction expression list");
/*     */   }
/*     */   
/*     */   public ExpressionList<T> icontains(String propertyName, String value) {
/* 266 */     return this.exprList.icontains(propertyName, value);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> idEq(Object value) {
/* 270 */     return this.exprList.idEq(value);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> idIn(List<?> idValues) {
/* 274 */     return this.exprList.idIn(idValues);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> iendsWith(String propertyName, String value) {
/* 278 */     return this.exprList.iendsWith(propertyName, value);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> ieq(String propertyName, String value) {
/* 282 */     return this.exprList.ieq(propertyName, value);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> iexampleLike(Object example) {
/* 286 */     return this.exprList.iexampleLike(example);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> ilike(String propertyName, String value) {
/* 290 */     return this.exprList.ilike(propertyName, value);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> in(String propertyName, Collection<?> values) {
/* 294 */     return this.exprList.in(propertyName, values);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> in(String propertyName, Object... values) {
/* 298 */     return this.exprList.in(propertyName, values);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> in(String propertyName, Query<?> subQuery) {
/* 302 */     return this.exprList.in(propertyName, subQuery);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> isNotNull(String propertyName) {
/* 306 */     return this.exprList.isNotNull(propertyName);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> isNull(String propertyName) {
/* 310 */     return this.exprList.isNull(propertyName);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> istartsWith(String propertyName, String value) {
/* 314 */     return this.exprList.istartsWith(propertyName, value);
/*     */   }
/*     */   
/*     */   public Query<T> join(String assocProperty, String assocProperties) {
/* 318 */     return this.exprList.join(assocProperty, assocProperties);
/*     */   }
/*     */   
/*     */   public Query<T> join(String assocProperties) {
/* 322 */     return this.exprList.join(assocProperties);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> le(String propertyName, Object value) {
/* 326 */     return this.exprList.le(propertyName, value);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> like(String propertyName, String value) {
/* 330 */     return this.exprList.like(propertyName, value);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> lt(String propertyName, Object value) {
/* 334 */     return this.exprList.lt(propertyName, value);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> ne(String propertyName, Object value) {
/* 338 */     return this.exprList.ne(propertyName, value);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> not(Expression exp) {
/* 342 */     return this.exprList.not(exp);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> or(Expression expOne, Expression expTwo) {
/* 346 */     return this.exprList.or(expOne, expTwo);
/*     */   }
/*     */   
/*     */   public OrderBy<T> order() {
/* 350 */     return this.exprList.order();
/*     */   }
/*     */   
/*     */   public Query<T> order(String orderByClause) {
/* 354 */     return this.exprList.order(orderByClause);
/*     */   }
/*     */   
/*     */   public OrderBy<T> orderBy() {
/* 358 */     return this.exprList.orderBy();
/*     */   }
/*     */   
/*     */   public Query<T> orderBy(String orderBy) {
/* 362 */     return this.exprList.orderBy(orderBy);
/*     */   }
/*     */   
/*     */   public Query<T> query() {
/* 366 */     return this.exprList.query();
/*     */   }
/*     */   
/*     */   public ExpressionList<T> raw(String raw, Object value) {
/* 370 */     return this.exprList.raw(raw, value);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> raw(String raw, Object[] values) {
/* 374 */     return this.exprList.raw(raw, values);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> raw(String raw) {
/* 378 */     return this.exprList.raw(raw);
/*     */   }
/*     */   
/*     */   public Query<T> select(String properties) {
/* 382 */     return this.exprList.select(properties);
/*     */   }
/*     */   
/*     */   public Query<T> setBackgroundFetchAfter(int backgroundFetchAfter) {
/* 386 */     return this.exprList.setBackgroundFetchAfter(backgroundFetchAfter);
/*     */   }
/*     */   
/*     */   public Query<T> setFirstRow(int firstRow) {
/* 390 */     return this.exprList.setFirstRow(firstRow);
/*     */   }
/*     */   
/*     */   public Query<T> setListener(QueryListener<T> queryListener) {
/* 394 */     return this.exprList.setListener(queryListener);
/*     */   }
/*     */   
/*     */   public Query<T> setMapKey(String mapKey) {
/* 398 */     return this.exprList.setMapKey(mapKey);
/*     */   }
/*     */   
/*     */   public Query<T> setMaxRows(int maxRows) {
/* 402 */     return this.exprList.setMaxRows(maxRows);
/*     */   }
/*     */   
/*     */   public Query<T> setOrderBy(String orderBy) {
/* 406 */     return this.exprList.setOrderBy(orderBy);
/*     */   }
/*     */   
/*     */   public Query<T> setUseCache(boolean useCache) {
/* 410 */     return this.exprList.setUseCache(useCache);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> startsWith(String propertyName, String value) {
/* 414 */     return this.exprList.startsWith(propertyName, value);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> where() {
/* 418 */     return this.exprList.where();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdJunctionExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */