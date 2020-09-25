/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
/*     */ import com.avaje.ebean.meta.MetaQueryStatistic;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import com.avaje.ebeaninternal.server.type.DataReader;
/*     */ import com.avaje.ebeaninternal.server.type.RsetDataReader;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
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
/*     */ public class CQueryPlan
/*     */ {
/*     */   private final boolean autofetchTuned;
/*     */   private final int hash;
/*     */   private final boolean rawSql;
/*     */   private final boolean rowNumberIncluded;
/*     */   private final String sql;
/*     */   private final String logWhereSql;
/*     */   private final SqlTree sqlTree;
/*     */   private final BeanProperty[] encryptedProps;
/*  54 */   private CQueryStats queryStats = new CQueryStats();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CQueryPlan(OrmQueryRequest<?> request, SqlLimitResponse sqlRes, SqlTree sqlTree, boolean rawSql, String logWhereSql, String luceneQueryDescription) {
/*  62 */     this.hash = request.getQueryPlanHash();
/*  63 */     this.autofetchTuned = request.getQuery().isAutofetchTuned();
/*  64 */     if (sqlRes != null) {
/*  65 */       this.sql = sqlRes.getSql();
/*  66 */       this.rowNumberIncluded = sqlRes.isIncludesRowNumberColumn();
/*     */     } else {
/*  68 */       this.sql = luceneQueryDescription;
/*  69 */       this.rowNumberIncluded = false;
/*     */     } 
/*  71 */     this.sqlTree = sqlTree;
/*  72 */     this.rawSql = rawSql;
/*  73 */     this.logWhereSql = logWhereSql;
/*  74 */     this.encryptedProps = sqlTree.getEncryptedProps();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CQueryPlan(String sql, SqlTree sqlTree, boolean rawSql, boolean rowNumberIncluded, String logWhereSql) {
/*  83 */     this.hash = 0;
/*  84 */     this.autofetchTuned = false;
/*  85 */     this.sql = sql;
/*  86 */     this.sqlTree = sqlTree;
/*  87 */     this.rawSql = rawSql;
/*  88 */     this.rowNumberIncluded = rowNumberIncluded;
/*  89 */     this.logWhereSql = logWhereSql;
/*  90 */     this.encryptedProps = sqlTree.getEncryptedProps();
/*     */   }
/*     */   
/*     */   public boolean isLucene() {
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public DataReader createDataReader(ResultSet rset) {
/*  99 */     return (DataReader)new RsetDataReader(rset);
/*     */   }
/*     */   
/*     */   public void bindEncryptedProperties(DataBind dataBind) throws SQLException {
/* 103 */     if (this.encryptedProps != null) {
/* 104 */       for (int i = 0; i < this.encryptedProps.length; i++) {
/* 105 */         String key = this.encryptedProps[i].getEncryptKey().getStringValue();
/* 106 */         dataBind.setString(key);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isAutofetchTuned() {
/* 112 */     return this.autofetchTuned;
/*     */   }
/*     */   
/*     */   public int getHash() {
/* 116 */     return this.hash;
/*     */   }
/*     */   
/*     */   public String getSql() {
/* 120 */     return this.sql;
/*     */   }
/*     */   
/*     */   public SqlTree getSqlTree() {
/* 124 */     return this.sqlTree;
/*     */   }
/*     */   
/*     */   public boolean isRawSql() {
/* 128 */     return this.rawSql;
/*     */   }
/*     */   
/*     */   public boolean isRowNumberIncluded() {
/* 132 */     return this.rowNumberIncluded;
/*     */   }
/*     */   
/*     */   public String getLogWhereSql() {
/* 136 */     return this.logWhereSql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetStatistics() {
/* 143 */     this.queryStats = new CQueryStats();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void executionTime(int loadedBeanCount, int timeMicros) {
/* 151 */     this.queryStats = this.queryStats.add(loadedBeanCount, timeMicros);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CQueryStats getQueryStats() {
/* 158 */     return this.queryStats;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastQueryTime() {
/* 165 */     return this.queryStats.getLastQueryTime();
/*     */   }
/*     */   
/*     */   public MetaQueryStatistic createMetaQueryStatistic(String beanName) {
/* 169 */     return this.queryStats.createMetaQueryStatistic(beanName, this);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\CQueryPlan.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */