/*     */ package com.avaje.ebeaninternal.server.querydefn;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.SqlFutureList;
/*     */ import com.avaje.ebean.SqlQuery;
/*     */ import com.avaje.ebean.SqlQueryListener;
/*     */ import com.avaje.ebean.SqlRow;
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.SpiSqlQuery;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultRelationalQuery
/*     */   implements SpiSqlQuery
/*     */ {
/*     */   private static final long serialVersionUID = -1098305779779591068L;
/*     */   private transient EbeanServer server;
/*     */   private transient SqlQueryListener queryListener;
/*     */   private String query;
/*     */   private int firstRow;
/*     */   private int maxRows;
/*     */   private int timeout;
/*     */   private boolean futureFetch;
/*     */   private boolean cancelled;
/*     */   private transient PreparedStatement pstmt;
/*     */   private int backgroundFetchAfter;
/*     */   private int bufferFetchSizeHint;
/*     */   private String mapKey;
/*  61 */   private BindParams bindParams = new BindParams();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultRelationalQuery(EbeanServer server, String query) {
/*  67 */     this.server = server;
/*  68 */     this.query = query;
/*     */   }
/*     */   
/*     */   public DefaultRelationalQuery setQuery(String query) {
/*  72 */     this.query = query;
/*  73 */     return this;
/*     */   }
/*     */   
/*     */   public List<SqlRow> findList() {
/*  77 */     return this.server.findList((SqlQuery)this, null);
/*     */   }
/*     */   
/*     */   public Set<SqlRow> findSet() {
/*  81 */     return this.server.findSet((SqlQuery)this, null);
/*     */   }
/*     */   
/*     */   public Map<?, SqlRow> findMap() {
/*  85 */     return this.server.findMap((SqlQuery)this, null);
/*     */   }
/*     */   
/*     */   public SqlRow findUnique() {
/*  89 */     return this.server.findUnique((SqlQuery)this, null);
/*     */   }
/*     */   
/*     */   public SqlFutureList findFutureList() {
/*  93 */     return this.server.findFutureList((SqlQuery)this, null);
/*     */   }
/*     */   
/*     */   public DefaultRelationalQuery setParameter(int position, Object value) {
/*  97 */     this.bindParams.setParameter(position, value);
/*  98 */     return this;
/*     */   }
/*     */   
/*     */   public DefaultRelationalQuery setParameter(String paramName, Object value) {
/* 102 */     this.bindParams.setParameter(paramName, value);
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SqlQueryListener getListener() {
/* 110 */     return this.queryListener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultRelationalQuery setListener(SqlQueryListener queryListener) {
/* 122 */     this.queryListener = queryListener;
/* 123 */     return this;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 127 */     return "SqlQuery [" + this.query + "]";
/*     */   }
/*     */   
/*     */   public int getFirstRow() {
/* 131 */     return this.firstRow;
/*     */   }
/*     */   
/*     */   public DefaultRelationalQuery setFirstRow(int firstRow) {
/* 135 */     this.firstRow = firstRow;
/* 136 */     return this;
/*     */   }
/*     */   
/*     */   public int getMaxRows() {
/* 140 */     return this.maxRows;
/*     */   }
/*     */   
/*     */   public DefaultRelationalQuery setMaxRows(int maxRows) {
/* 144 */     this.maxRows = maxRows;
/* 145 */     return this;
/*     */   }
/*     */   
/*     */   public String getMapKey() {
/* 149 */     return this.mapKey;
/*     */   }
/*     */   
/*     */   public DefaultRelationalQuery setMapKey(String mapKey) {
/* 153 */     this.mapKey = mapKey;
/* 154 */     return this;
/*     */   }
/*     */   
/*     */   public int getBackgroundFetchAfter() {
/* 158 */     return this.backgroundFetchAfter;
/*     */   }
/*     */   
/*     */   public DefaultRelationalQuery setBackgroundFetchAfter(int backgroundFetchAfter) {
/* 162 */     this.backgroundFetchAfter = backgroundFetchAfter;
/* 163 */     return this;
/*     */   }
/*     */   
/*     */   public int getTimeout() {
/* 167 */     return this.timeout;
/*     */   }
/*     */   
/*     */   public DefaultRelationalQuery setTimeout(int secs) {
/* 171 */     this.timeout = secs;
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   public BindParams getBindParams() {
/* 176 */     return this.bindParams;
/*     */   }
/*     */   
/*     */   public DefaultRelationalQuery setBufferFetchSizeHint(int bufferFetchSizeHint) {
/* 180 */     this.bufferFetchSizeHint = bufferFetchSizeHint;
/* 181 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBufferFetchSizeHint() {
/* 186 */     return this.bufferFetchSizeHint;
/*     */   }
/*     */   
/*     */   public String getQuery() {
/* 190 */     return this.query;
/*     */   }
/*     */   
/*     */   public boolean isFutureFetch() {
/* 194 */     return this.futureFetch;
/*     */   }
/*     */   
/*     */   public void setFutureFetch(boolean futureFetch) {
/* 198 */     this.futureFetch = futureFetch;
/*     */   }
/*     */   
/*     */   public void setPreparedStatement(PreparedStatement pstmt) {
/* 202 */     synchronized (this) {
/* 203 */       this.pstmt = pstmt;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cancel() {
/* 208 */     synchronized (this) {
/* 209 */       this.cancelled = true;
/* 210 */       if (this.pstmt != null) {
/*     */         try {
/* 212 */           this.pstmt.cancel();
/* 213 */         } catch (SQLException e) {
/* 214 */           String msg = "Error cancelling query";
/* 215 */           throw new PersistenceException(msg, e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/* 222 */     synchronized (this) {
/* 223 */       return this.cancelled;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\querydefn\DefaultRelationalQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */