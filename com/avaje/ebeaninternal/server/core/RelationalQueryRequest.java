/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.SqlQuery;
/*     */ import com.avaje.ebean.SqlRow;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiSqlQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public final class RelationalQueryRequest
/*     */ {
/*     */   private final SpiSqlQuery query;
/*     */   private final RelationalQueryEngine queryEngine;
/*     */   private final SpiEbeanServer ebeanServer;
/*     */   private SpiTransaction trans;
/*     */   private boolean createdTransaction;
/*     */   private SpiQuery.Type queryType;
/*     */   
/*     */   public RelationalQueryRequest(SpiEbeanServer server, RelationalQueryEngine engine, SqlQuery q, Transaction t) {
/*  56 */     this.ebeanServer = server;
/*  57 */     this.queryEngine = engine;
/*  58 */     this.query = (SpiSqlQuery)q;
/*  59 */     this.trans = (SpiTransaction)t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rollbackTransIfRequired() {
/*  66 */     if (this.createdTransaction) {
/*  67 */       this.trans.rollback();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initTransIfRequired() {
/*  75 */     if (this.trans == null) {
/*  76 */       this.trans = this.ebeanServer.getCurrentServerTransaction();
/*  77 */       if (this.trans == null || !this.trans.isActive()) {
/*     */         
/*  79 */         this.trans = this.ebeanServer.createServerTransaction(false, -1);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  84 */         this.createdTransaction = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endTransIfRequired() {
/*  93 */     if (this.createdTransaction)
/*     */     {
/*  95 */       this.trans.rollback();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<SqlRow> findList() {
/* 101 */     this.queryType = SpiQuery.Type.LIST;
/* 102 */     return (List<SqlRow>)this.queryEngine.findMany(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<SqlRow> findSet() {
/* 107 */     this.queryType = SpiQuery.Type.SET;
/* 108 */     return (Set<SqlRow>)this.queryEngine.findMany(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<?, SqlRow> findMap() {
/* 113 */     this.queryType = SpiQuery.Type.MAP;
/* 114 */     return (Map<?, SqlRow>)this.queryEngine.findMany(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiSqlQuery getQuery() {
/* 121 */     return this.query;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiQuery.Type getQueryType() {
/* 128 */     return this.queryType;
/*     */   }
/*     */   
/*     */   public EbeanServer getEbeanServer() {
/* 132 */     return (EbeanServer)this.ebeanServer;
/*     */   }
/*     */   
/*     */   public SpiTransaction getTransaction() {
/* 136 */     return this.trans;
/*     */   }
/*     */   
/*     */   public boolean isLogSql() {
/* 140 */     return this.trans.isLogSql();
/*     */   }
/*     */   
/*     */   public boolean isLogSummary() {
/* 144 */     return this.trans.isLogSummary();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\RelationalQueryRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */