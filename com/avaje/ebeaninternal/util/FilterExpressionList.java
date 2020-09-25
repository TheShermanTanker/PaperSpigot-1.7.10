/*     */ package com.avaje.ebeaninternal.util;
/*     */ 
/*     */ import com.avaje.ebean.ExpressionFactory;
/*     */ import com.avaje.ebean.ExpressionList;
/*     */ import com.avaje.ebean.FutureIds;
/*     */ import com.avaje.ebean.FutureList;
/*     */ import com.avaje.ebean.FutureRowCount;
/*     */ import com.avaje.ebean.OrderBy;
/*     */ import com.avaje.ebean.PagingList;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.QueryListener;
/*     */ import com.avaje.ebeaninternal.server.expression.FilterExprPath;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FilterExpressionList<T>
/*     */   extends DefaultExpressionList<T>
/*     */ {
/*     */   private static final long serialVersionUID = 2226895827150099020L;
/*     */   private final Query<T> rootQuery;
/*     */   private final FilterExprPath pathPrefix;
/*     */   private String notAllowedMessage;
/*     */   
/*     */   public FilterExpressionList(FilterExprPath pathPrefix, ExpressionFactory expr, Query<T> rootQuery) {
/*  48 */     super(null, expr, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  61 */     this.notAllowedMessage = "This method is not allowed on a filter";
/*     */     this.pathPrefix = pathPrefix;
/*     */     this.rootQuery = rootQuery;
/*  64 */   } public void trimPath(int prefixTrim) { this.pathPrefix.trimPath(prefixTrim); } public ExpressionList<T> filterMany(String prop) { return this.rootQuery.filterMany(prop); }
/*     */    public FilterExprPath getPathPrefix() {
/*     */     return this.pathPrefix;
/*     */   } public FutureIds<T> findFutureIds() {
/*  68 */     return this.rootQuery.findFutureIds();
/*     */   }
/*     */   
/*     */   public FutureList<T> findFutureList() {
/*  72 */     return this.rootQuery.findFutureList();
/*     */   }
/*     */   
/*     */   public FutureRowCount<T> findFutureRowCount() {
/*  76 */     return this.rootQuery.findFutureRowCount();
/*     */   }
/*     */   
/*     */   public List<T> findList() {
/*  80 */     return this.rootQuery.findList();
/*     */   }
/*     */   
/*     */   public Map<?, T> findMap() {
/*  84 */     return this.rootQuery.findMap();
/*     */   }
/*     */   
/*     */   public PagingList<T> findPagingList(int pageSize) {
/*  88 */     return this.rootQuery.findPagingList(pageSize);
/*     */   }
/*     */   
/*     */   public int findRowCount() {
/*  92 */     return this.rootQuery.findRowCount();
/*     */   }
/*     */   
/*     */   public Set<T> findSet() {
/*  96 */     return this.rootQuery.findSet();
/*     */   }
/*     */   
/*     */   public T findUnique() {
/* 100 */     return (T)this.rootQuery.findUnique();
/*     */   }
/*     */   
/*     */   public ExpressionList<T> having() {
/* 104 */     throw new PersistenceException(this.notAllowedMessage);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> idEq(Object value) {
/* 108 */     throw new PersistenceException(this.notAllowedMessage);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> idIn(List<?> idValues) {
/* 112 */     throw new PersistenceException(this.notAllowedMessage);
/*     */   }
/*     */   
/*     */   public Query<T> join(String assocProperty, String assocProperties) {
/* 116 */     throw new PersistenceException(this.notAllowedMessage);
/*     */   }
/*     */   
/*     */   public Query<T> join(String assocProperties) {
/* 120 */     throw new PersistenceException(this.notAllowedMessage);
/*     */   }
/*     */   
/*     */   public OrderBy<T> order() {
/* 124 */     return this.rootQuery.order();
/*     */   }
/*     */   
/*     */   public Query<T> order(String orderByClause) {
/* 128 */     return this.rootQuery.order(orderByClause);
/*     */   }
/*     */   
/*     */   public Query<T> orderBy(String orderBy) {
/* 132 */     return this.rootQuery.orderBy(orderBy);
/*     */   }
/*     */   
/*     */   public Query<T> query() {
/* 136 */     return this.rootQuery;
/*     */   }
/*     */   
/*     */   public Query<T> select(String properties) {
/* 140 */     throw new PersistenceException(this.notAllowedMessage);
/*     */   }
/*     */   
/*     */   public Query<T> setBackgroundFetchAfter(int backgroundFetchAfter) {
/* 144 */     return this.rootQuery.setBackgroundFetchAfter(backgroundFetchAfter);
/*     */   }
/*     */   
/*     */   public Query<T> setFirstRow(int firstRow) {
/* 148 */     return this.rootQuery.setFirstRow(firstRow);
/*     */   }
/*     */   
/*     */   public Query<T> setListener(QueryListener<T> queryListener) {
/* 152 */     return this.rootQuery.setListener(queryListener);
/*     */   }
/*     */   
/*     */   public Query<T> setMapKey(String mapKey) {
/* 156 */     return this.rootQuery.setMapKey(mapKey);
/*     */   }
/*     */   
/*     */   public Query<T> setMaxRows(int maxRows) {
/* 160 */     return this.rootQuery.setMaxRows(maxRows);
/*     */   }
/*     */   
/*     */   public Query<T> setUseCache(boolean useCache) {
/* 164 */     return this.rootQuery.setUseCache(useCache);
/*     */   }
/*     */   
/*     */   public ExpressionList<T> where() {
/* 168 */     return this.rootQuery.where();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninterna\\util\FilterExpressionList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */