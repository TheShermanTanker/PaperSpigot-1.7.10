/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.QueryIterator;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.event.BeanFinder;
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.BeanIdList;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryEngine;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
/*     */ import java.util.Collection;
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
/*     */ public class DefaultOrmQueryEngine
/*     */   implements OrmQueryEngine
/*     */ {
/*     */   private final CQueryEngine queryEngine;
/*     */   
/*     */   public DefaultOrmQueryEngine(BeanDescriptorManager descMgr, CQueryEngine queryEngine) {
/*  50 */     this.queryEngine = queryEngine;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> int findRowCount(OrmQueryRequest<T> request) {
/*  55 */     return this.queryEngine.findRowCount(request);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> BeanIdList findIds(OrmQueryRequest<T> request) {
/*  60 */     return this.queryEngine.findIds(request);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> QueryIterator<T> findIterate(OrmQueryRequest<T> request) {
/*  68 */     SpiTransaction t = request.getTransaction();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     t.flushBatch();
/*     */     
/*  75 */     return this.queryEngine.findIterate(request);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> BeanCollection<T> findMany(OrmQueryRequest<T> request) {
/*  80 */     SpiQuery<T> query = request.getQuery();
/*     */     
/*  82 */     BeanCollection<T> result = null;
/*     */     
/*  84 */     SpiTransaction t = request.getTransaction();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     t.flushBatch();
/*     */     
/*  91 */     BeanFinder<T> finder = request.getBeanFinder();
/*  92 */     if (finder != null) {
/*     */       
/*  94 */       result = finder.findMany((BeanQueryRequest)request);
/*     */     } else {
/*  96 */       result = this.queryEngine.findMany(request);
/*     */     } 
/*     */     
/*  99 */     if (query.isLoadBeanCache()) {
/*     */       
/* 101 */       BeanDescriptor<T> descriptor = request.getBeanDescriptor();
/* 102 */       Collection<T> c = result.getActualDetails();
/* 103 */       for (T bean : c) {
/* 104 */         descriptor.cachePutBeanData(bean);
/*     */       }
/*     */     } 
/*     */     
/* 108 */     if (!result.isEmpty() && query.isUseQueryCache())
/*     */     {
/* 110 */       request.putToQueryCache(result);
/*     */     }
/*     */     
/* 113 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T findId(OrmQueryRequest<T> request) {
/* 122 */     T result = null;
/*     */     
/* 124 */     SpiTransaction t = request.getTransaction();
/*     */     
/* 126 */     if (t.isBatchFlushOnQuery())
/*     */     {
/*     */ 
/*     */       
/* 130 */       t.flushBatch();
/*     */     }
/*     */     
/* 133 */     BeanFinder<T> finder = request.getBeanFinder();
/* 134 */     if (finder != null) {
/* 135 */       result = (T)finder.find((BeanQueryRequest)request);
/*     */     } else {
/* 137 */       result = this.queryEngine.find(request);
/*     */     } 
/*     */     
/* 140 */     if (result != null && request.isUseBeanCache()) {
/* 141 */       request.getBeanDescriptor().cachePutBeanData(result);
/*     */     }
/*     */     
/* 144 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\DefaultOrmQueryEngine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */