/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.BackgroundExecutor;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebeaninternal.api.BeanIdList;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import com.avaje.ebeaninternal.server.type.DataReader;
/*     */ import com.avaje.ebeaninternal.server.type.RsetDataReader;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.FutureTask;
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
/*     */ public class CQueryFetchIds
/*     */ {
/*  56 */   private static final Logger logger = Logger.getLogger(CQueryFetchIds.class.getName());
/*     */ 
/*     */   
/*     */   private final OrmQueryRequest<?> request;
/*     */ 
/*     */   
/*     */   private final BeanDescriptor<?> desc;
/*     */ 
/*     */   
/*     */   private final SpiQuery<?> query;
/*     */ 
/*     */   
/*     */   private final BackgroundExecutor backgroundExecutor;
/*     */ 
/*     */   
/*     */   private final CQueryPredicates predicates;
/*     */ 
/*     */   
/*     */   private final String sql;
/*     */ 
/*     */   
/*     */   private RsetDataReader dataReader;
/*     */ 
/*     */   
/*     */   private PreparedStatement pstmt;
/*     */ 
/*     */   
/*     */   private String bindLog;
/*     */ 
/*     */   
/*     */   private long startNano;
/*     */ 
/*     */   
/*     */   private int executionTimeMicros;
/*     */ 
/*     */   
/*     */   private int rowCount;
/*     */ 
/*     */   
/*     */   private final int maxRows;
/*     */ 
/*     */   
/*     */   private final int bgFetchAfter;
/*     */ 
/*     */ 
/*     */   
/*     */   public CQueryFetchIds(OrmQueryRequest<?> request, CQueryPredicates predicates, String sql, BackgroundExecutor backgroundExecutor) {
/* 103 */     this.backgroundExecutor = backgroundExecutor;
/* 104 */     this.request = request;
/* 105 */     this.query = request.getQuery();
/* 106 */     this.sql = sql;
/* 107 */     this.maxRows = this.query.getMaxRows();
/* 108 */     this.bgFetchAfter = this.query.getBackgroundFetchAfter();
/*     */     
/* 110 */     this.query.setGeneratedSql(sql);
/*     */     
/* 112 */     this.desc = request.getBeanDescriptor();
/* 113 */     this.predicates = predicates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSummary() {
/* 121 */     StringBuilder sb = new StringBuilder();
/* 122 */     sb.append("FindIds exeMicros[").append(this.executionTimeMicros).append("] rows[").append(this.rowCount).append("] type[").append(this.desc.getName()).append("] predicates[").append(this.predicates.getLogWhereSql()).append("] bind[").append(this.bindLog).append("]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBindLog() {
/* 135 */     return this.bindLog;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGeneratedSql() {
/* 142 */     return this.sql;
/*     */   }
/*     */   
/*     */   public SpiOrmQueryRequest<?> getQueryRequest() {
/* 146 */     return (SpiOrmQueryRequest<?>)this.request;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanIdList findIds() throws SQLException {
/* 154 */     boolean useBackgroundToContinueFetch = false;
/*     */     
/* 156 */     this.startNano = System.nanoTime();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 162 */       List<Object> idList = this.query.getIdList();
/* 163 */       if (idList == null) {
/*     */         
/* 165 */         idList = Collections.synchronizedList(new ArrayList());
/* 166 */         this.query.setIdList(idList);
/*     */       } 
/*     */       
/* 169 */       BeanIdList result = new BeanIdList(idList);
/*     */       
/* 171 */       SpiTransaction t = this.request.getTransaction();
/* 172 */       Connection conn = t.getInternalConnection();
/* 173 */       this.pstmt = conn.prepareStatement(this.sql);
/*     */       
/* 175 */       if (this.query.getBufferFetchSizeHint() > 0) {
/* 176 */         this.pstmt.setFetchSize(this.query.getBufferFetchSizeHint());
/*     */       }
/*     */       
/* 179 */       if (this.query.getTimeout() > 0) {
/* 180 */         this.pstmt.setQueryTimeout(this.query.getTimeout());
/*     */       }
/*     */       
/* 183 */       this.bindLog = this.predicates.bind(new DataBind(this.pstmt));
/*     */       
/* 185 */       ResultSet rset = this.pstmt.executeQuery();
/* 186 */       this.dataReader = new RsetDataReader(rset);
/*     */       
/* 188 */       boolean hitMaxRows = false;
/* 189 */       boolean hasMoreRows = false;
/* 190 */       this.rowCount = 0;
/*     */       
/* 192 */       DbReadContext ctx = new DbContext();
/*     */       
/* 194 */       while (rset.next()) {
/* 195 */         Object idValue = this.desc.getIdBinder().read(ctx);
/* 196 */         idList.add(idValue);
/*     */         
/* 198 */         this.dataReader.resetColumnPosition();
/* 199 */         this.rowCount++;
/*     */         
/* 201 */         if (this.maxRows > 0 && this.rowCount == this.maxRows) {
/* 202 */           hitMaxRows = true;
/* 203 */           hasMoreRows = rset.next();
/*     */           break;
/*     */         } 
/* 206 */         if (this.bgFetchAfter > 0 && this.rowCount >= this.bgFetchAfter) {
/* 207 */           useBackgroundToContinueFetch = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 212 */       if (hitMaxRows) {
/* 213 */         result.setHasMore(hasMoreRows);
/*     */       }
/*     */       
/* 216 */       if (useBackgroundToContinueFetch) {
/*     */ 
/*     */         
/* 219 */         this.request.setBackgroundFetching();
/*     */ 
/*     */         
/* 222 */         BackgroundIdFetch bgFetch = new BackgroundIdFetch(t, rset, this.pstmt, ctx, this.desc, result);
/* 223 */         FutureTask<Integer> future = new FutureTask<Integer>(bgFetch);
/* 224 */         this.backgroundExecutor.execute(future);
/*     */ 
/*     */         
/* 227 */         result.setBackgroundFetch(future);
/*     */       } 
/*     */       
/* 230 */       long exeNano = System.nanoTime() - this.startNano;
/* 231 */       this.executionTimeMicros = (int)exeNano / 1000;
/*     */       
/* 233 */       return result;
/*     */     } finally {
/*     */       
/* 236 */       if (!useBackgroundToContinueFetch)
/*     */       {
/*     */         
/* 239 */         close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void close() {
/*     */     try {
/* 253 */       if (this.dataReader != null) {
/* 254 */         this.dataReader.close();
/* 255 */         this.dataReader = null;
/*     */       } 
/* 257 */     } catch (SQLException e) {
/* 258 */       logger.log(Level.SEVERE, (String)null, e);
/*     */     } 
/*     */     try {
/* 261 */       if (this.pstmt != null) {
/* 262 */         this.pstmt.close();
/* 263 */         this.pstmt = null;
/*     */       } 
/* 265 */     } catch (SQLException e) {
/* 266 */       logger.log(Level.SEVERE, (String)null, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   class DbContext
/*     */     implements DbReadContext
/*     */   {
/*     */     public void propagateState(Object e) {
/* 274 */       throw new RuntimeException("Not Called");
/*     */     }
/*     */     
/*     */     public SpiQuery.Mode getQueryMode() {
/* 278 */       return SpiQuery.Mode.NORMAL;
/*     */     }
/*     */     
/*     */     public DataReader getDataReader() {
/* 282 */       return (DataReader)CQueryFetchIds.this.dataReader;
/*     */     }
/*     */     
/*     */     public boolean isVanillaMode() {
/* 286 */       return false;
/*     */     }
/*     */     
/*     */     public Boolean isReadOnly() {
/* 290 */       return Boolean.FALSE;
/*     */     }
/*     */     
/*     */     public boolean isRawSql() {
/* 294 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void register(String path, EntityBeanIntercept ebi) {}
/*     */ 
/*     */     
/*     */     public void register(String path, BeanCollection<?> bc) {}
/*     */ 
/*     */     
/*     */     public BeanPropertyAssocMany<?> getManyProperty() {
/* 305 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public PersistenceContext getPersistenceContext() {
/* 310 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isAutoFetchProfiling() {
/* 314 */       return false;
/*     */     }
/*     */     
/*     */     public void profileBean(EntityBeanIntercept ebi, String prefix) {}
/*     */     
/*     */     public void setCurrentPrefix(String currentPrefix, Map<String, String> pathMap) {}
/*     */     
/*     */     public void setLoadedBean(Object loadedBean, Object id) {}
/*     */     
/*     */     public void setLoadedManyBean(Object loadedBean) {}
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\CQueryFetchIds.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */