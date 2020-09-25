/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionList;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployParser;
/*     */ import com.avaje.ebeaninternal.server.persist.Binder;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import com.avaje.ebeaninternal.server.util.BindParamsParser;
/*     */ import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class CQueryPredicates
/*     */ {
/*  36 */   private static final Logger logger = Logger.getLogger(CQueryPredicates.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   private final Binder binder;
/*     */ 
/*     */ 
/*     */   
/*     */   private final OrmQueryRequest<?> request;
/*     */ 
/*     */ 
/*     */   
/*     */   private final SpiQuery<?> query;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Object idValue;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean rawSql;
/*     */ 
/*     */ 
/*     */   
/*     */   private final BindParams bindParams;
/*     */ 
/*     */ 
/*     */   
/*     */   private BindParams.OrderedList havingNamedParams;
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<Object> filterManyExprBindValues;
/*     */ 
/*     */ 
/*     */   
/*     */   private String filterManyExprSql;
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<Object> whereExprBindValues;
/*     */ 
/*     */ 
/*     */   
/*     */   private String whereExprSql;
/*     */ 
/*     */ 
/*     */   
/*     */   private String whereRawSql;
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<Object> havingExprBindValues;
/*     */ 
/*     */ 
/*     */   
/*     */   private String havingExprSql;
/*     */ 
/*     */ 
/*     */   
/*     */   private String havingRawSql;
/*     */ 
/*     */ 
/*     */   
/*     */   private String dbHaving;
/*     */ 
/*     */ 
/*     */   
/*     */   private String dbWhere;
/*     */ 
/*     */ 
/*     */   
/*     */   private String dbFilterMany;
/*     */ 
/*     */ 
/*     */   
/*     */   private String logicalOrderBy;
/*     */ 
/*     */ 
/*     */   
/*     */   private String dbOrderBy;
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<String> predicateIncludes;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CQueryPredicates(Binder binder, OrmQueryRequest<?> request) {
/* 126 */     this.binder = binder;
/* 127 */     this.request = request;
/* 128 */     this.query = request.getQuery();
/* 129 */     this.bindParams = this.query.getBindParams();
/* 130 */     this.idValue = this.query.getId();
/*     */   }
/*     */ 
/*     */   
/*     */   public String bind(DataBind dataBind) throws SQLException {
/* 135 */     StringBuilder bindLog = new StringBuilder();
/*     */     
/* 137 */     if (this.idValue != null) {
/*     */       
/* 139 */       this.request.getBeanDescriptor().bindId(dataBind, this.idValue);
/* 140 */       bindLog.append(this.idValue);
/*     */     } 
/*     */     
/* 143 */     if (this.bindParams != null)
/*     */     {
/* 145 */       this.binder.bind(this.bindParams, dataBind, bindLog);
/*     */     }
/*     */     
/* 148 */     if (this.whereExprBindValues != null)
/*     */     {
/* 150 */       for (int i = 0; i < this.whereExprBindValues.size(); i++) {
/* 151 */         Object bindValue = this.whereExprBindValues.get(i);
/* 152 */         this.binder.bindObject(dataBind, bindValue);
/* 153 */         if (i > 0 || this.idValue != null) {
/* 154 */           bindLog.append(", ");
/*     */         }
/* 156 */         bindLog.append(bindValue);
/*     */       } 
/*     */     }
/*     */     
/* 160 */     if (this.filterManyExprBindValues != null)
/*     */     {
/* 162 */       for (int i = 0; i < this.filterManyExprBindValues.size(); i++) {
/* 163 */         Object bindValue = this.filterManyExprBindValues.get(i);
/* 164 */         this.binder.bindObject(dataBind, bindValue);
/* 165 */         if (i > 0 || this.idValue != null) {
/* 166 */           bindLog.append(", ");
/*     */         }
/* 168 */         bindLog.append(bindValue);
/*     */       } 
/*     */     }
/*     */     
/* 172 */     if (this.havingNamedParams != null) {
/*     */       
/* 174 */       bindLog.append(" havingNamed ");
/* 175 */       this.binder.bind(this.havingNamedParams.list(), dataBind, bindLog);
/*     */     } 
/*     */     
/* 178 */     if (this.havingExprBindValues != null) {
/*     */       
/* 180 */       bindLog.append(" having ");
/* 181 */       for (int i = 0; i < this.havingExprBindValues.size(); i++) {
/* 182 */         Object bindValue = this.havingExprBindValues.get(i);
/* 183 */         this.binder.bindObject(dataBind, bindValue);
/* 184 */         if (i > 0) {
/* 185 */           bindLog.append(", ");
/*     */         }
/* 187 */         bindLog.append(bindValue);
/*     */       } 
/*     */     } 
/*     */     
/* 191 */     return bindLog.toString();
/*     */   }
/*     */   
/*     */   private void buildBindHavingRawSql(boolean buildSql, boolean parseRaw, DeployParser deployParser) {
/* 195 */     if (buildSql || this.bindParams != null) {
/*     */       
/* 197 */       this.havingRawSql = this.query.getAdditionalHaving();
/* 198 */       if (parseRaw) {
/* 199 */         this.havingRawSql = deployParser.parse(this.havingRawSql);
/*     */       }
/* 201 */       if (this.havingRawSql != null && this.bindParams != null) {
/*     */         
/* 203 */         this.havingNamedParams = BindParamsParser.parseNamedParams(this.bindParams, this.havingRawSql);
/* 204 */         this.havingRawSql = this.havingNamedParams.getPreparedSql();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildBindWhereRawSql(boolean buildSql, boolean parseRaw, DeployParser parser) {
/* 213 */     if (buildSql || this.bindParams != null) {
/* 214 */       this.whereRawSql = buildWhereRawSql();
/* 215 */       boolean hasRaw = !"".equals(this.whereRawSql);
/* 216 */       if (hasRaw && parseRaw) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 222 */         parser.setEncrypted(true);
/* 223 */         this.whereRawSql = parser.parse(this.whereRawSql);
/* 224 */         parser.setEncrypted(false);
/*     */       } 
/*     */       
/* 227 */       if (this.bindParams != null) {
/* 228 */         if (hasRaw) {
/* 229 */           this.whereRawSql = BindParamsParser.parse(this.bindParams, this.whereRawSql, this.request.getBeanDescriptor());
/*     */         }
/* 231 */         else if (this.query.isRawSql() && !buildSql) {
/*     */ 
/*     */ 
/*     */           
/* 235 */           String s = this.query.getRawSql().getSql().getPreWhere();
/* 236 */           if (this.bindParams.requiresNamedParamsPrepare()) {
/* 237 */             BindParamsParser.parse(this.bindParams, s);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String buildWhereRawSql() {
/* 247 */     String whereRaw = this.query.getRawWhereClause();
/* 248 */     if (whereRaw == null) {
/* 249 */       whereRaw = "";
/*     */     }
/*     */     
/* 252 */     String additionalWhere = this.query.getAdditionalWhere();
/* 253 */     if (additionalWhere != null) {
/* 254 */       whereRaw = whereRaw + additionalWhere;
/*     */     }
/* 256 */     return whereRaw;
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepare(boolean buildSql) {
/* 261 */     DeployParser deployParser = this.request.createDeployParser();
/*     */     
/* 263 */     prepare(buildSql, true, deployParser);
/*     */   }
/*     */   
/*     */   public void prepareRawSql(DeployParser deployParser) {
/* 267 */     prepare(true, false, deployParser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void prepare(boolean buildSql, boolean parseRaw, DeployParser deployParser) {
/* 275 */     buildBindWhereRawSql(buildSql, parseRaw, deployParser);
/* 276 */     buildBindHavingRawSql(buildSql, parseRaw, deployParser);
/*     */     
/* 278 */     SpiExpressionList<?> whereExp = this.query.getWhereExpressions();
/* 279 */     if (whereExp != null) {
/* 280 */       DefaultExpressionRequest whereReq = new DefaultExpressionRequest((SpiOrmQueryRequest)this.request, deployParser);
/* 281 */       this.whereExprBindValues = whereExp.buildBindValues((SpiExpressionRequest)whereReq);
/* 282 */       if (buildSql) {
/* 283 */         this.whereExprSql = whereExp.buildSql((SpiExpressionRequest)whereReq);
/*     */       }
/*     */     } 
/*     */     
/* 287 */     BeanPropertyAssocMany<?> manyProperty = this.request.getManyProperty();
/* 288 */     if (manyProperty != null) {
/* 289 */       OrmQueryProperties chunk = this.query.getDetail().getChunk(manyProperty.getName(), false);
/* 290 */       SpiExpressionList<?> filterMany = chunk.getFilterMany();
/* 291 */       if (filterMany != null) {
/* 292 */         DefaultExpressionRequest filterReq = new DefaultExpressionRequest((SpiOrmQueryRequest)this.request, deployParser);
/* 293 */         this.filterManyExprBindValues = filterMany.buildBindValues((SpiExpressionRequest)filterReq);
/* 294 */         if (buildSql) {
/* 295 */           this.filterManyExprSql = filterMany.buildSql((SpiExpressionRequest)filterReq);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 301 */     SpiExpressionList<?> havingExpr = this.query.getHavingExpressions();
/* 302 */     if (havingExpr != null) {
/* 303 */       DefaultExpressionRequest havingReq = new DefaultExpressionRequest((SpiOrmQueryRequest)this.request, deployParser);
/* 304 */       this.havingExprBindValues = havingExpr.buildBindValues((SpiExpressionRequest)havingReq);
/* 305 */       if (buildSql) {
/* 306 */         this.havingExprSql = havingExpr.buildSql((SpiExpressionRequest)havingReq);
/*     */       }
/*     */     } 
/*     */     
/* 310 */     if (buildSql) {
/* 311 */       parsePropertiesToDbColumns(deployParser);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parsePropertiesToDbColumns(DeployParser deployParser) {
/* 322 */     this.dbWhere = deriveWhere(deployParser);
/* 323 */     this.dbFilterMany = deriveFilterMany(deployParser);
/* 324 */     this.dbHaving = deriveHaving(deployParser);
/*     */ 
/*     */     
/* 327 */     this.logicalOrderBy = deriveOrderByWithMany(this.request.getManyProperty());
/* 328 */     if (this.logicalOrderBy != null) {
/* 329 */       this.dbOrderBy = deployParser.parse(this.logicalOrderBy);
/*     */     }
/*     */     
/* 332 */     this.predicateIncludes = deployParser.getIncludes();
/*     */   }
/*     */   
/*     */   private String deriveFilterMany(DeployParser deployParser) {
/* 336 */     if (isEmpty(this.filterManyExprSql)) {
/* 337 */       return null;
/*     */     }
/* 339 */     return deployParser.parse(this.filterManyExprSql);
/*     */   }
/*     */ 
/*     */   
/*     */   private String deriveWhere(DeployParser deployParser) {
/* 344 */     return parse(this.whereRawSql, this.whereExprSql, deployParser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseTableAlias(SqlTreeAlias alias) {
/* 351 */     if (this.dbWhere != null)
/*     */     {
/* 353 */       this.dbWhere = alias.parseWhere(this.dbWhere);
/*     */     }
/* 355 */     if (this.dbFilterMany != null)
/*     */     {
/* 357 */       this.dbFilterMany = alias.parse(this.dbFilterMany);
/*     */     }
/* 359 */     if (this.dbHaving != null) {
/* 360 */       this.dbHaving = alias.parseWhere(this.dbHaving);
/*     */     }
/* 362 */     if (this.dbOrderBy != null) {
/* 363 */       this.dbOrderBy = alias.parse(this.dbOrderBy);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isEmpty(String s) {
/* 368 */     return (s == null || s.length() == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private String parse(String raw, String expr, DeployParser deployParser) {
/* 373 */     StringBuilder sb = new StringBuilder();
/* 374 */     if (!isEmpty(raw)) {
/* 375 */       sb.append(raw);
/*     */     }
/* 377 */     if (!isEmpty(expr)) {
/* 378 */       if (sb.length() > 0) {
/* 379 */         sb.append(" and ");
/*     */       }
/* 381 */       sb.append(deployParser.parse(expr));
/*     */     } 
/* 383 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private String deriveHaving(DeployParser deployParser) {
/* 387 */     return parse(this.havingRawSql, this.havingExprSql, deployParser);
/*     */   }
/*     */ 
/*     */   
/*     */   private String parseOrderBy() {
/* 392 */     return CQueryOrderBy.parse(this.request.getBeanDescriptor(), this.query);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String deriveOrderByWithMany(BeanPropertyAssocMany<?> manyProp) {
/* 401 */     if (manyProp == null) {
/* 402 */       return parseOrderBy();
/*     */     }
/*     */     
/* 405 */     String orderBy = parseOrderBy();
/*     */     
/* 407 */     BeanDescriptor<?> desc = this.request.getBeanDescriptor();
/* 408 */     String orderById = desc.getDefaultOrderBy();
/*     */     
/* 410 */     if (orderBy == null) {
/* 411 */       orderBy = orderById;
/*     */     }
/*     */ 
/*     */     
/* 415 */     String manyOrderBy = manyProp.getFetchOrderBy();
/* 416 */     if (manyOrderBy != null) {
/* 417 */       orderBy = orderBy + ", " + CQueryBuilder.prefixOrderByFields(manyProp.getName(), manyOrderBy);
/*     */     }
/*     */     
/* 420 */     if (this.request.isFindById())
/*     */     {
/* 422 */       return orderBy;
/*     */     }
/*     */     
/* 425 */     if (orderBy.startsWith(orderById)) {
/* 426 */       return orderBy;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 433 */     int manyPos = orderBy.indexOf(manyProp.getName());
/* 434 */     int idPos = orderBy.indexOf(" " + orderById);
/*     */     
/* 436 */     if (manyPos == -1) {
/*     */       
/* 438 */       if (idPos == -1)
/*     */       {
/*     */         
/* 441 */         return orderBy + ", " + orderById;
/*     */       }
/*     */       
/* 444 */       return orderBy;
/*     */     } 
/*     */     
/* 447 */     if (idPos <= -1 || idPos >= manyPos) {
/*     */ 
/*     */ 
/*     */       
/* 451 */       if (idPos > manyPos) {
/*     */         
/* 453 */         String msg = "A Query on [" + desc + "] includes a join to a 'many' association [" + manyProp.getName();
/* 454 */         msg = msg + "] with an incorrect orderBy [" + orderBy + "]. The id property [" + orderById + "]";
/* 455 */         msg = msg + " must come before the many property [" + manyProp.getName() + "] in the orderBy.";
/* 456 */         msg = msg + " Ebean has automatically modified the orderBy clause to do this.";
/*     */         
/* 458 */         logger.log(Level.WARNING, msg);
/*     */       } 
/*     */ 
/*     */       
/* 462 */       orderBy = orderBy.substring(0, manyPos) + orderById + ", " + orderBy.substring(manyPos);
/*     */     } 
/*     */     
/* 465 */     return orderBy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Object> getWhereExprBindValues() {
/* 472 */     return this.whereExprBindValues;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDbHaving() {
/* 479 */     return this.dbHaving;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDbWhere() {
/* 486 */     return this.dbWhere;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDbFilterMany() {
/* 493 */     return this.dbFilterMany;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDbOrderBy() {
/* 500 */     return this.dbOrderBy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getPredicateIncludes() {
/* 507 */     return this.predicateIncludes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWhereRawSql() {
/* 514 */     return this.whereRawSql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWhereExpressionSql() {
/* 521 */     return this.whereExprSql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHavingRawSql() {
/* 528 */     return this.havingRawSql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHavingExpressionSql() {
/* 535 */     return this.havingExprSql;
/*     */   }
/*     */   
/*     */   public String getLogWhereSql() {
/* 539 */     if (this.rawSql) {
/* 540 */       return "";
/*     */     }
/* 542 */     if (this.dbWhere == null && this.dbFilterMany == null) {
/* 543 */       return "";
/*     */     }
/* 545 */     StringBuilder sb = new StringBuilder();
/* 546 */     if (this.dbWhere != null) {
/* 547 */       sb.append(this.dbWhere);
/*     */     }
/* 549 */     if (this.dbFilterMany != null) {
/* 550 */       if (sb.length() > 0) {
/* 551 */         sb.append(" and ");
/*     */       }
/* 553 */       sb.append(this.dbFilterMany);
/*     */     } 
/* 555 */     String logPred = sb.toString();
/* 556 */     if (logPred.length() > 400) {
/* 557 */       logPred = logPred.substring(0, 400) + " ...";
/*     */     }
/* 559 */     return logPred;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\CQueryPredicates.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */