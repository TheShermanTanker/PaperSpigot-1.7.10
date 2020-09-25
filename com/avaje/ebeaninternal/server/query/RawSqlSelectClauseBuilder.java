/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimitRequest;
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimiter;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.DRawSqlSelect;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployParser;
/*     */ import com.avaje.ebeaninternal.server.persist.Binder;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryLimitRequest;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RawSqlSelectClauseBuilder
/*     */ {
/*  47 */   private static final Logger logger = Logger.getLogger(RawSqlSelectClauseBuilder.class.getName());
/*     */   
/*     */   private final Binder binder;
/*     */   
/*     */   private final SqlLimiter dbQueryLimiter;
/*     */   
/*     */   private final DatabasePlatform dbPlatform;
/*     */   
/*     */   public RawSqlSelectClauseBuilder(DatabasePlatform dbPlatform, Binder binder) {
/*  56 */     this.binder = binder;
/*  57 */     this.dbQueryLimiter = dbPlatform.getSqlLimiter();
/*  58 */     this.dbPlatform = dbPlatform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> CQuery<T> build(OrmQueryRequest<T> request) throws PersistenceException {
/*  66 */     SpiQuery<T> query = request.getQuery();
/*  67 */     BeanDescriptor<T> desc = request.getBeanDescriptor();
/*     */     
/*  69 */     DeployNamedQuery namedQuery = desc.getNamedQuery(query.getName());
/*  70 */     DRawSqlSelect sqlSelect = namedQuery.getSqlSelect();
/*     */ 
/*     */ 
/*     */     
/*  74 */     DeployParser parser = sqlSelect.createDeployPropertyParser();
/*     */     
/*  76 */     CQueryPredicates predicates = new CQueryPredicates(this.binder, request);
/*     */     
/*  78 */     predicates.prepareRawSql(parser);
/*     */     
/*  80 */     SqlTreeAlias alias = new SqlTreeAlias(sqlSelect.getTableAlias());
/*  81 */     predicates.parseTableAlias(alias);
/*     */     
/*  83 */     String sql = null;
/*     */     
/*     */     try {
/*  86 */       boolean includeRowNumColumn = false;
/*  87 */       String orderBy = sqlSelect.getOrderBy(predicates);
/*     */ 
/*     */       
/*  90 */       sql = sqlSelect.buildSql(orderBy, predicates, request);
/*  91 */       if (query.hasMaxRowsOrFirstRow() && this.dbQueryLimiter != null) {
/*     */         
/*  93 */         SqlLimitResponse limitSql = this.dbQueryLimiter.limit((SqlLimitRequest)new OrmQueryLimitRequest(sql, orderBy, query, this.dbPlatform));
/*  94 */         includeRowNumColumn = limitSql.isIncludesRowNumberColumn();
/*     */         
/*  96 */         sql = limitSql.getSql();
/*     */       }
/*     */       else {
/*     */         
/* 100 */         sql = "select " + sql;
/*     */       } 
/*     */       
/* 103 */       SqlTree sqlTree = sqlSelect.getSqlTree();
/*     */       
/* 105 */       CQueryPlan queryPlan = new CQueryPlan(sql, sqlTree, true, includeRowNumColumn, "");
/* 106 */       CQuery<T> compiledQuery = new CQuery<T>(request, predicates, queryPlan);
/*     */       
/* 108 */       return compiledQuery;
/*     */     }
/* 110 */     catch (Exception e) {
/*     */       
/* 112 */       String msg = "Error with " + desc.getFullName() + " query:\r" + sql;
/* 113 */       logger.log(Level.SEVERE, msg);
/* 114 */       throw new PersistenceException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\RawSqlSelectClauseBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */