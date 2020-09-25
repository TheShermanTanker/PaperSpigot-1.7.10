/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.Page;
/*     */ import com.avaje.ebean.PagingList;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebeaninternal.api.Monitor;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Future;
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
/*     */ public class LimitOffsetPagingQuery<T>
/*     */   implements PagingList<T>
/*     */ {
/*     */   private transient EbeanServer server;
/*     */   private final SpiQuery<T> query;
/*  40 */   private final List<LimitOffsetPage<T>> pages = new ArrayList<LimitOffsetPage<T>>();
/*     */   
/*  42 */   private final Monitor monitor = new Monitor();
/*     */   
/*     */   private final int pageSize;
/*     */   
/*     */   private boolean fetchAhead = true;
/*     */   
/*     */   private Future<Integer> futureRowCount;
/*     */   
/*     */   public LimitOffsetPagingQuery(EbeanServer server, SpiQuery<T> query, int pageSize) {
/*  51 */     this.query = query;
/*  52 */     this.pageSize = pageSize;
/*  53 */     this.server = server;
/*     */   }
/*     */   
/*     */   public EbeanServer getServer() {
/*  57 */     return this.server;
/*     */   }
/*     */   
/*     */   public void setServer(EbeanServer server) {
/*  61 */     this.server = server;
/*     */   }
/*     */   
/*     */   public SpiQuery<T> getSpiQuery() {
/*  65 */     return this.query;
/*     */   }
/*     */   
/*     */   public PagingList<T> setFetchAhead(boolean fetchAhead) {
/*  69 */     this.fetchAhead = fetchAhead;
/*  70 */     return this;
/*     */   }
/*     */   
/*     */   public List<T> getAsList() {
/*  74 */     return new LimitOffsetList<T>(this);
/*     */   }
/*     */   
/*     */   public Future<Integer> getFutureRowCount() {
/*  78 */     synchronized (this.monitor) {
/*  79 */       if (this.futureRowCount == null) {
/*  80 */         this.futureRowCount = (Future<Integer>)this.server.findFutureRowCount((Query)this.query, null);
/*     */       }
/*  82 */       return this.futureRowCount;
/*     */     } 
/*     */   }
/*     */   
/*     */   private LimitOffsetPage<T> internalGetPage(int i) {
/*  87 */     synchronized (this.monitor) {
/*  88 */       int ps = this.pages.size();
/*  89 */       if (ps <= i) {
/*  90 */         for (int j = ps; j <= i; j++) {
/*  91 */           LimitOffsetPage<T> p = new LimitOffsetPage<T>(j, this);
/*  92 */           this.pages.add(p);
/*     */         } 
/*     */       }
/*  95 */       return this.pages.get(i);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void fetchAheadIfRequired(int pageIndex) {
/* 100 */     synchronized (this.monitor) {
/*     */       
/* 102 */       if (this.fetchAhead) {
/*     */         
/* 104 */         LimitOffsetPage<T> nextPage = internalGetPage(pageIndex + 1);
/* 105 */         nextPage.getFutureList();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 111 */     synchronized (this.monitor) {
/* 112 */       this.futureRowCount = null;
/* 113 */       this.pages.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Page<T> getPage(int i) {
/* 118 */     return internalGetPage(i);
/*     */   }
/*     */   
/*     */   protected boolean hasNext(int position) {
/* 122 */     return (position < getTotalRowCount());
/*     */   }
/*     */   
/*     */   protected T get(int rowIndex) {
/* 126 */     int pg = rowIndex / this.pageSize;
/* 127 */     int offset = rowIndex % this.pageSize;
/*     */     
/* 129 */     Page<T> page = getPage(pg);
/* 130 */     return page.getList().get(offset);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTotalPageCount() {
/* 135 */     int rowCount = getTotalRowCount();
/* 136 */     if (rowCount == 0) {
/* 137 */       return 0;
/*     */     }
/* 139 */     return (rowCount - 1) / this.pageSize + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPageSize() {
/* 144 */     return this.pageSize;
/*     */   }
/*     */   
/*     */   public int getTotalRowCount() {
/*     */     try {
/* 149 */       return ((Integer)getFutureRowCount().get()).intValue();
/* 150 */     } catch (Exception e) {
/* 151 */       throw new PersistenceException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\LimitOffsetPagingQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */