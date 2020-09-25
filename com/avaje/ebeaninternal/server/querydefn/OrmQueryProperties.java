/*     */ package com.avaje.ebeaninternal.server.querydefn;
/*     */ 
/*     */ import com.avaje.ebean.ExpressionFactory;
/*     */ import com.avaje.ebean.ExpressionList;
/*     */ import com.avaje.ebean.FetchConfig;
/*     */ import com.avaje.ebean.OrderBy;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionFactory;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionList;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.expression.FilterExprPath;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import com.avaje.ebeaninternal.server.query.SplitName;
/*     */ import com.avaje.ebeaninternal.util.FilterExpressionList;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OrmQueryProperties
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8785582703966455658L;
/*     */   private String parentPath;
/*     */   private String path;
/*     */   private String properties;
/*     */   private String trimmedProperties;
/*  43 */   private int queryFetchBatch = -1;
/*     */ 
/*     */   
/*     */   private boolean queryFetchAll;
/*     */ 
/*     */   
/*  49 */   private int lazyFetchBatch = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FetchConfig fetchConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean cache;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean readOnly;
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<String> included;
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<String> includedBeanJoin;
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<String> secondaryQueryJoins;
/*     */ 
/*     */ 
/*     */   
/*     */   private List<OrmQueryProperties> secondaryChildren;
/*     */ 
/*     */ 
/*     */   
/*     */   private OrderBy orderBy;
/*     */ 
/*     */ 
/*     */   
/*     */   private SpiExpressionList filterMany;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OrmQueryProperties(String path) {
/*  94 */     this.path = path;
/*  95 */     this.parentPath = SplitName.parent(path);
/*     */   }
/*     */   
/*     */   public OrmQueryProperties() {
/*  99 */     this(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OrmQueryProperties(String path, String properties) {
/* 106 */     this(path);
/* 107 */     setProperties(properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSecJoinOrderProperty(OrderBy.Property orderProp) {
/* 115 */     if (this.orderBy == null) {
/* 116 */       this.orderBy = new OrderBy();
/*     */     }
/* 118 */     this.orderBy.add(orderProp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFetchConfig(FetchConfig fetchConfig) {
/* 125 */     if (fetchConfig != null) {
/* 126 */       this.fetchConfig = fetchConfig;
/* 127 */       this.lazyFetchBatch = fetchConfig.getLazyBatchSize();
/* 128 */       this.queryFetchBatch = fetchConfig.getQueryBatchSize();
/* 129 */       this.queryFetchAll = fetchConfig.isQueryAll();
/*     */     } 
/*     */   }
/*     */   
/*     */   public FetchConfig getFetchConfig() {
/* 134 */     return this.fetchConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperties(String properties) {
/* 144 */     this.properties = properties;
/* 145 */     this.trimmedProperties = properties;
/* 146 */     parseProperties();
/*     */     
/* 148 */     if (!isAllProperties()) {
/* 149 */       Set<String> parsed = parseIncluded(this.trimmedProperties);
/* 150 */       if (parsed.contains("*")) {
/* 151 */         this.included = null;
/*     */       } else {
/* 153 */         this.included = parsed;
/*     */       } 
/*     */     } else {
/* 156 */       this.included = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isAllProperties() {
/* 161 */     return (this.trimmedProperties == null || this.trimmedProperties.length() == 0 || "*".equals(this.trimmedProperties));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> SpiExpressionList<T> filterMany(Query<T> rootQuery) {
/* 170 */     if (this.filterMany == null) {
/* 171 */       FilterExprPath exprPath = new FilterExprPath(this.path);
/* 172 */       SpiExpressionFactory queryEf = (SpiExpressionFactory)rootQuery.getExpressionFactory();
/* 173 */       ExpressionFactory filterEf = queryEf.createExpressionFactory(exprPath);
/* 174 */       this.filterMany = (SpiExpressionList)new FilterExpressionList(exprPath, filterEf, rootQuery);
/*     */       
/* 176 */       this.queryFetchAll = true;
/* 177 */       this.queryFetchBatch = 100;
/* 178 */       this.lazyFetchBatch = 100;
/*     */     } 
/* 180 */     return this.filterMany;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiExpressionList<?> getFilterManyTrimPath(int trimPath) {
/* 187 */     if (this.filterMany == null) {
/* 188 */       return null;
/*     */     }
/* 190 */     this.filterMany.trimPath(trimPath);
/* 191 */     return this.filterMany;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiExpressionList<?> getFilterMany() {
/* 198 */     return this.filterMany;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFilterMany(SpiExpressionList<?> filterMany) {
/* 205 */     this.filterMany = filterMany;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultProperties(String properties, Set<String> included) {
/* 212 */     this.properties = properties;
/* 213 */     this.trimmedProperties = properties;
/* 214 */     this.included = included;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTunedProperties(OrmQueryProperties tunedProperties) {
/* 221 */     this.properties = tunedProperties.properties;
/* 222 */     this.trimmedProperties = tunedProperties.trimmedProperties;
/* 223 */     this.included = tunedProperties.included;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configureBeanQuery(SpiQuery<?> query) {
/* 232 */     if (this.trimmedProperties != null && this.trimmedProperties.length() > 0) {
/* 233 */       query.select(this.trimmedProperties);
/* 234 */       if (this.filterMany != null) {
/* 235 */         query.setFilterMany(this.path, (ExpressionList)this.filterMany);
/*     */       }
/*     */     } 
/*     */     
/* 239 */     if (this.secondaryChildren != null) {
/* 240 */       int trimPath = this.path.length() + 1;
/* 241 */       for (int i = 0; i < this.secondaryChildren.size(); i++) {
/* 242 */         OrmQueryProperties p = this.secondaryChildren.get(i);
/* 243 */         String path = p.getPath();
/* 244 */         path = path.substring(trimPath);
/* 245 */         query.fetch(path, p.getProperties(), p.getFetchConfig());
/* 246 */         query.setFilterMany(path, (ExpressionList)p.getFilterManyTrimPath(trimPath));
/*     */       } 
/*     */     } 
/*     */     
/* 250 */     if (this.orderBy != null) {
/* 251 */       List<OrderBy.Property> orderByProps = this.orderBy.getProperties();
/* 252 */       for (int i = 0; i < orderByProps.size(); i++) {
/* 253 */         ((OrderBy.Property)orderByProps.get(i)).trim(this.path);
/*     */       }
/* 255 */       query.setOrder(this.orderBy);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configureManyQuery(SpiQuery<?> query) {
/* 265 */     if (this.trimmedProperties != null && this.trimmedProperties.length() > 0) {
/* 266 */       query.fetch(query.getLazyLoadManyPath(), this.trimmedProperties);
/*     */     }
/* 268 */     if (this.filterMany != null) {
/* 269 */       query.setFilterMany(this.path, (ExpressionList)this.filterMany);
/*     */     }
/* 271 */     if (this.secondaryChildren != null) {
/*     */       
/* 273 */       int trimlen = this.path.length() - query.getLazyLoadManyPath().length();
/*     */       
/* 275 */       for (int i = 0; i < this.secondaryChildren.size(); i++) {
/* 276 */         OrmQueryProperties p = this.secondaryChildren.get(i);
/* 277 */         String path = p.getPath();
/* 278 */         path = path.substring(trimlen);
/* 279 */         query.fetch(path, p.getProperties(), p.getFetchConfig());
/* 280 */         query.setFilterMany(path, (ExpressionList)p.getFilterManyTrimPath(trimlen));
/*     */       } 
/*     */     } 
/*     */     
/* 284 */     if (this.orderBy != null) {
/* 285 */       query.setOrder(this.orderBy);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OrmQueryProperties copy() {
/* 293 */     OrmQueryProperties copy = new OrmQueryProperties();
/* 294 */     copy.parentPath = this.parentPath;
/* 295 */     copy.path = this.path;
/* 296 */     copy.properties = this.properties;
/* 297 */     copy.cache = this.cache;
/* 298 */     copy.readOnly = this.readOnly;
/* 299 */     copy.queryFetchAll = this.queryFetchAll;
/* 300 */     copy.queryFetchBatch = this.queryFetchBatch;
/* 301 */     copy.lazyFetchBatch = this.lazyFetchBatch;
/* 302 */     copy.filterMany = this.filterMany;
/* 303 */     if (this.included != null) {
/* 304 */       copy.included = new HashSet<String>(this.included);
/*     */     }
/* 306 */     if (this.includedBeanJoin != null) {
/* 307 */       copy.includedBeanJoin = new HashSet<String>(this.includedBeanJoin);
/*     */     }
/* 309 */     return copy;
/*     */   }
/*     */   
/*     */   public boolean hasSelectClause() {
/* 313 */     if ("*".equals(this.trimmedProperties))
/*     */     {
/* 315 */       return true;
/*     */     }
/*     */     
/* 318 */     return (this.included != null);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 322 */     String s = "";
/* 323 */     if (this.path != null) {
/* 324 */       s = s + this.path + " ";
/*     */     }
/* 326 */     if (this.properties != null) {
/* 327 */       s = s + "(" + this.properties + ") ";
/* 328 */     } else if (this.included != null) {
/* 329 */       s = s + "(" + this.included + ") ";
/*     */     } 
/* 331 */     return s;
/*     */   }
/*     */   
/*     */   public boolean isChild(OrmQueryProperties possibleChild) {
/* 335 */     return possibleChild.getPath().startsWith(this.path + ".");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(OrmQueryProperties child) {
/* 342 */     if (this.secondaryChildren == null) {
/* 343 */       this.secondaryChildren = new ArrayList<OrmQueryProperties>();
/*     */     }
/* 345 */     this.secondaryChildren.add(child);
/*     */   }
/*     */ 
/*     */   
/*     */   public int autofetchPlanHash() {
/* 350 */     int hc = (this.path != null) ? this.path.hashCode() : 1;
/* 351 */     if (this.properties != null) {
/* 352 */       hc = hc * 31 + this.properties.hashCode();
/*     */     } else {
/* 354 */       hc = hc * 31 + ((this.included != null) ? this.included.hashCode() : 1);
/*     */     } 
/*     */     
/* 357 */     return hc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 366 */     int hc = (this.path != null) ? this.path.hashCode() : 1;
/* 367 */     if (this.properties != null) {
/* 368 */       hc = hc * 31 + this.properties.hashCode();
/*     */     } else {
/* 370 */       hc = hc * 31 + ((this.included != null) ? this.included.hashCode() : 1);
/*     */     } 
/* 372 */     hc = hc * 31 + ((this.filterMany != null) ? this.filterMany.queryPlanHash(request) : 1);
/*     */     
/* 374 */     return hc;
/*     */   }
/*     */   
/*     */   public String getProperties() {
/* 378 */     return this.properties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasProperties() {
/* 385 */     return (this.properties != null || this.included != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIncludedBeanJoin(String propertyName) {
/* 396 */     if (this.includedBeanJoin == null) {
/* 397 */       return false;
/*     */     }
/* 399 */     return this.includedBeanJoin.contains(propertyName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void includeBeanJoin(String propertyName) {
/* 407 */     if (this.includedBeanJoin == null) {
/* 408 */       this.includedBeanJoin = new HashSet<String>();
/*     */     }
/* 410 */     this.includedBeanJoin.add(propertyName);
/*     */   }
/*     */   
/*     */   public boolean allProperties() {
/* 414 */     return (this.included == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<String> getSelectProperties() {
/* 425 */     if (this.secondaryQueryJoins == null) {
/* 426 */       return this.included.iterator();
/*     */     }
/*     */     
/* 429 */     LinkedHashSet<String> temp = new LinkedHashSet<String>(this.secondaryQueryJoins.size() + this.included.size());
/* 430 */     temp.addAll(this.included);
/* 431 */     temp.addAll(this.secondaryQueryJoins);
/* 432 */     return temp.iterator();
/*     */   }
/*     */   
/*     */   public void addSecondaryQueryJoin(String property) {
/* 436 */     if (this.secondaryQueryJoins == null) {
/* 437 */       this.secondaryQueryJoins = new HashSet<String>(4);
/*     */     }
/* 439 */     this.secondaryQueryJoins.add(property);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getAllIncludedProperties() {
/* 449 */     if (this.included == null) {
/* 450 */       return null;
/*     */     }
/*     */     
/* 453 */     if (this.includedBeanJoin == null && this.secondaryQueryJoins == null) {
/* 454 */       return new LinkedHashSet<String>(this.included);
/*     */     }
/*     */     
/* 457 */     LinkedHashSet<String> s = new LinkedHashSet<String>(2 * (this.included.size() + 5));
/* 458 */     if (this.included != null) {
/* 459 */       s.addAll(this.included);
/*     */     }
/* 461 */     if (this.includedBeanJoin != null) {
/* 462 */       s.addAll(this.includedBeanJoin);
/*     */     }
/* 464 */     if (this.secondaryQueryJoins != null) {
/* 465 */       s.addAll(this.secondaryQueryJoins);
/*     */     }
/* 467 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIncluded(String propName) {
/* 472 */     if (this.includedBeanJoin != null && this.includedBeanJoin.contains(propName)) {
/* 473 */       return false;
/*     */     }
/* 475 */     if (this.included == null)
/*     */     {
/* 477 */       return true;
/*     */     }
/* 479 */     return this.included.contains(propName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OrmQueryProperties setQueryFetchBatch(int queryFetchBatch) {
/* 490 */     this.queryFetchBatch = queryFetchBatch;
/* 491 */     return this;
/*     */   }
/*     */   
/*     */   public OrmQueryProperties setQueryFetchAll(boolean queryFetchAll) {
/* 495 */     this.queryFetchAll = queryFetchAll;
/* 496 */     return this;
/*     */   }
/*     */   
/*     */   public OrmQueryProperties setQueryFetch(int batch, boolean queryFetchAll) {
/* 500 */     this.queryFetchBatch = batch;
/* 501 */     this.queryFetchAll = queryFetchAll;
/* 502 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OrmQueryProperties setLazyFetchBatch(int lazyFetchBatch) {
/* 509 */     this.lazyFetchBatch = lazyFetchBatch;
/* 510 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isFetchJoin() {
/* 514 */     return (!isQueryFetch() && !isLazyFetch());
/*     */   }
/*     */   
/*     */   public boolean isQueryFetch() {
/* 518 */     return (this.queryFetchBatch > -1);
/*     */   }
/*     */   
/*     */   public int getQueryFetchBatch() {
/* 522 */     return this.queryFetchBatch;
/*     */   }
/*     */   
/*     */   public boolean isQueryFetchAll() {
/* 526 */     return this.queryFetchAll;
/*     */   }
/*     */   
/*     */   public boolean isLazyFetch() {
/* 530 */     return (this.lazyFetchBatch > -1);
/*     */   }
/*     */   
/*     */   public int getLazyFetchBatch() {
/* 534 */     return this.lazyFetchBatch;
/*     */   }
/*     */   
/*     */   public boolean isReadOnly() {
/* 538 */     return this.readOnly;
/*     */   }
/*     */   
/*     */   public boolean isCache() {
/* 542 */     return this.cache;
/*     */   }
/*     */   
/*     */   public String getParentPath() {
/* 546 */     return this.parentPath;
/*     */   }
/*     */   
/*     */   public String getPath() {
/* 550 */     return this.path;
/*     */   }
/*     */   
/*     */   private void parseProperties() {
/* 554 */     if (this.trimmedProperties == null) {
/*     */       return;
/*     */     }
/* 557 */     int pos = this.trimmedProperties.indexOf("+readonly");
/* 558 */     if (pos > -1) {
/* 559 */       this.trimmedProperties = StringHelper.replaceString(this.trimmedProperties, "+readonly", "");
/* 560 */       this.readOnly = true;
/*     */     } 
/* 562 */     pos = this.trimmedProperties.indexOf("+cache");
/* 563 */     if (pos > -1) {
/* 564 */       this.trimmedProperties = StringHelper.replaceString(this.trimmedProperties, "+cache", "");
/* 565 */       this.cache = true;
/*     */     } 
/* 567 */     pos = this.trimmedProperties.indexOf("+query");
/* 568 */     if (pos > -1) {
/* 569 */       this.queryFetchBatch = parseBatchHint(pos, "+query");
/*     */     }
/* 571 */     pos = this.trimmedProperties.indexOf("+lazy");
/* 572 */     if (pos > -1) {
/* 573 */       this.lazyFetchBatch = parseBatchHint(pos, "+lazy");
/*     */     }
/*     */     
/* 576 */     this.trimmedProperties = this.trimmedProperties.trim();
/* 577 */     while (this.trimmedProperties.startsWith(",")) {
/* 578 */       this.trimmedProperties = this.trimmedProperties.substring(1).trim();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private int parseBatchHint(int pos, String option) {
/* 584 */     int startPos = pos + option.length();
/*     */     
/* 586 */     int endPos = findEndPos(startPos, this.trimmedProperties);
/* 587 */     if (endPos == -1) {
/* 588 */       this.trimmedProperties = StringHelper.replaceString(this.trimmedProperties, option, "");
/* 589 */       return 0;
/*     */     } 
/*     */ 
/*     */     
/* 593 */     String batchParam = this.trimmedProperties.substring(startPos + 1, endPos);
/*     */     
/* 595 */     if (endPos + 1 >= this.trimmedProperties.length()) {
/* 596 */       this.trimmedProperties = this.trimmedProperties.substring(0, pos);
/*     */     } else {
/* 598 */       this.trimmedProperties = this.trimmedProperties.substring(0, pos) + this.trimmedProperties.substring(endPos + 1);
/*     */     } 
/* 600 */     return Integer.parseInt(batchParam);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int findEndPos(int pos, String props) {
/* 606 */     if (pos < props.length() && 
/* 607 */       props.charAt(pos) == '(') {
/* 608 */       int endPara = props.indexOf(')', pos + 1);
/* 609 */       if (endPara == -1) {
/* 610 */         String m = "Error could not find ')' in " + props + " after position " + pos;
/* 611 */         throw new RuntimeException(m);
/*     */       } 
/* 613 */       return endPara;
/*     */     } 
/*     */     
/* 616 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Set<String> parseIncluded(String rawList) {
/* 624 */     String[] res = rawList.split(",");
/*     */     
/* 626 */     LinkedHashSet<String> set = new LinkedHashSet<String>(res.length + 3);
/*     */     
/* 628 */     String temp = null;
/* 629 */     for (int i = 0; i < res.length; i++) {
/* 630 */       temp = res[i].trim();
/* 631 */       if (temp.length() > 0) {
/* 632 */         set.add(temp);
/*     */       }
/*     */     } 
/*     */     
/* 636 */     if (set.isEmpty()) {
/* 637 */       return null;
/*     */     }
/*     */     
/* 640 */     return Collections.unmodifiableSet(set);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\querydefn\OrmQueryProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */