/*     */ package com.avaje.ebeaninternal.server.persist.dml;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.core.PstmtBatch;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.persist.BatchPostExecute;
/*     */ import com.avaje.ebeaninternal.server.persist.BatchedPstmt;
/*     */ import com.avaje.ebeaninternal.server.persist.BatchedPstmtHolder;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.OptimisticLockException;
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
/*     */ public abstract class DmlHandler
/*     */   implements PersistHandler, BindableRequest
/*     */ {
/*  48 */   protected static final Logger logger = Logger.getLogger(DmlHandler.class.getName());
/*     */ 
/*     */   
/*     */   protected final PersistRequestBean<?> persistRequest;
/*     */ 
/*     */   
/*     */   protected final StringBuilder bindLog;
/*     */ 
/*     */   
/*     */   protected final Set<String> loadedProps;
/*     */ 
/*     */   
/*     */   protected final SpiTransaction transaction;
/*     */ 
/*     */   
/*     */   protected final boolean emptyStringToNull;
/*     */ 
/*     */   
/*     */   protected final boolean logLevelSql;
/*     */ 
/*     */   
/*     */   protected DataBind dataBind;
/*     */ 
/*     */   
/*     */   protected String sql;
/*     */ 
/*     */   
/*     */   protected ArrayList<UpdateGenValue> updateGenValues;
/*     */   
/*     */   private Set<String> additionalProps;
/*     */ 
/*     */   
/*     */   protected DmlHandler(PersistRequestBean<?> persistRequest, boolean emptyStringToNull) {
/*  81 */     this.persistRequest = persistRequest;
/*  82 */     this.emptyStringToNull = emptyStringToNull;
/*  83 */     this.loadedProps = persistRequest.getLoadedProperties();
/*  84 */     this.transaction = persistRequest.getTransaction();
/*  85 */     this.logLevelSql = this.transaction.isLogSql();
/*  86 */     if (this.logLevelSql) {
/*  87 */       this.bindLog = new StringBuilder();
/*     */     } else {
/*  89 */       this.bindLog = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistRequestBean<?> getPersistRequest() {
/*  98 */     return this.persistRequest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void bind() throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void execute() throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkRowCount(int rowCount) throws SQLException, OptimisticLockException {
/*     */     try {
/* 116 */       this.persistRequest.checkRowCount(rowCount);
/* 117 */       this.persistRequest.postExecute();
/* 118 */     } catch (OptimisticLockException e) {
/*     */       
/* 120 */       String m = e.getMessage() + " sql[" + this.sql + "] bind[" + this.bindLog + "]";
/* 121 */       this.persistRequest.getTransaction().log("OptimisticLockException:" + m);
/* 122 */       throw new OptimisticLockException(m, null, e.getEntity());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBatch() throws SQLException {
/* 130 */     PstmtBatch pstmtBatch = this.persistRequest.getPstmtBatch();
/* 131 */     if (pstmtBatch != null) {
/* 132 */       pstmtBatch.addBatch(this.dataBind.getPstmt());
/*     */     } else {
/* 134 */       this.dataBind.getPstmt().addBatch();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*     */     try {
/* 143 */       if (this.dataBind != null) {
/* 144 */         this.dataBind.close();
/*     */       }
/* 146 */     } catch (SQLException ex) {
/* 147 */       logger.log(Level.SEVERE, (String)null, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBindLog() {
/* 155 */     return (this.bindLog == null) ? "" : this.bindLog.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIdValue(Object idValue) {
/* 163 */     this.persistRequest.setBoundId(idValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void logBinding() {
/* 170 */     if (this.logLevelSql) {
/* 171 */       this.transaction.logInternal(this.bindLog.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void logSql(String sql) {
/* 179 */     if (this.logLevelSql) {
/* 180 */       this.transaction.logInternal(sql);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIncluded(BeanProperty prop) {
/* 186 */     return (this.loadedProps == null || this.loadedProps.contains(prop.getName()));
/*     */   }
/*     */   
/*     */   public boolean isIncludedWhere(BeanProperty prop) {
/* 190 */     if (prop.isDbEncrypted())
/*     */     {
/*     */ 
/*     */       
/* 194 */       return isIncluded(prop);
/*     */     }
/* 196 */     return (prop.isDbUpdatable() && (this.loadedProps == null || this.loadedProps.contains(prop.getName())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object bind(String propName, Object value, int sqlType) throws SQLException {
/* 203 */     if (this.logLevelSql) {
/* 204 */       this.bindLog.append(propName).append("=");
/* 205 */       this.bindLog.append(value).append(", ");
/*     */     } 
/* 207 */     this.dataBind.setObject(value, sqlType);
/* 208 */     return value;
/*     */   }
/*     */   
/*     */   public Object bindNoLog(Object value, int sqlType, String logPlaceHolder) throws SQLException {
/* 212 */     if (this.logLevelSql) {
/* 213 */       this.bindLog.append(logPlaceHolder).append(" ");
/*     */     }
/* 215 */     this.dataBind.setObject(value, sqlType);
/* 216 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object bind(Object value, BeanProperty prop, String propName, boolean bindNull) throws SQLException {
/* 223 */     return bindInternal(this.logLevelSql, value, prop, propName, bindNull);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object bindNoLog(Object value, BeanProperty prop, String propName, boolean bindNull) throws SQLException {
/* 230 */     return bindInternal(false, value, prop, propName, bindNull);
/*     */   }
/*     */ 
/*     */   
/*     */   private Object bindInternal(boolean log, Object value, BeanProperty prop, String propName, boolean bindNull) throws SQLException {
/* 235 */     if (!bindNull && 
/* 236 */       this.emptyStringToNull && value instanceof String && ((String)value).length() == 0)
/*     */     {
/*     */       
/* 239 */       value = null;
/*     */     }
/*     */ 
/*     */     
/* 243 */     if (!bindNull && value == null) {
/*     */       
/* 245 */       if (log) {
/* 246 */         this.bindLog.append(propName).append("=");
/* 247 */         this.bindLog.append("null, ");
/*     */       } 
/*     */     } else {
/* 250 */       if (log) {
/* 251 */         this.bindLog.append(propName).append("=");
/* 252 */         if (prop.isLob()) {
/* 253 */           this.bindLog.append("[LOB]");
/*     */         } else {
/* 255 */           String sv = String.valueOf(value);
/* 256 */           if (sv.length() > 50) {
/* 257 */             sv = sv.substring(0, 47) + "...";
/*     */           }
/* 259 */           this.bindLog.append(sv);
/*     */         } 
/* 261 */         this.bindLog.append(", ");
/*     */       } 
/*     */       
/* 264 */       prop.bind(this.dataBind, value);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 275 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void bindLogAppend(String comment) {
/* 282 */     if (this.logLevelSql) {
/* 283 */       this.bindLog.append(comment);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void registerAdditionalProperty(String propertyName) {
/* 292 */     if (this.loadedProps != null && !this.loadedProps.contains(propertyName)) {
/* 293 */       if (this.additionalProps == null) {
/* 294 */         this.additionalProps = new HashSet<String>();
/*     */       }
/* 296 */       this.additionalProps.add(propertyName);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setAdditionalProperties() {
/* 305 */     if (this.additionalProps != null) {
/*     */ 
/*     */       
/* 308 */       this.additionalProps.addAll(this.loadedProps);
/* 309 */       this.persistRequest.setLoadedProps(this.additionalProps);
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
/*     */ 
/*     */   
/*     */   public void registerUpdateGenValue(BeanProperty prop, Object bean, Object value) {
/* 323 */     if (this.updateGenValues == null) {
/* 324 */       this.updateGenValues = new ArrayList<UpdateGenValue>();
/*     */     }
/* 326 */     this.updateGenValues.add(new UpdateGenValue(prop, bean, value));
/* 327 */     registerAdditionalProperty(prop.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUpdateGenValues() {
/* 337 */     if (this.updateGenValues != null) {
/* 338 */       for (int i = 0; i < this.updateGenValues.size(); i++) {
/* 339 */         UpdateGenValue updGenVal = this.updateGenValues.get(i);
/* 340 */         updGenVal.setValue();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PreparedStatement getPstmt(SpiTransaction t, String sql, boolean genKeys) throws SQLException {
/* 350 */     Connection conn = t.getInternalConnection();
/* 351 */     if (genKeys) {
/*     */ 
/*     */ 
/*     */       
/* 355 */       int[] columns = { 1 };
/* 356 */       return conn.prepareStatement(sql, columns);
/*     */     } 
/*     */     
/* 359 */     return conn.prepareStatement(sql);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PreparedStatement getPstmt(SpiTransaction t, String sql, PersistRequestBean<?> request, boolean genKeys) throws SQLException {
/* 369 */     BatchedPstmtHolder batch = t.getBatchControl().getPstmtHolder();
/* 370 */     PreparedStatement stmt = batch.getStmt(sql, (BatchPostExecute)request);
/*     */     
/* 372 */     if (stmt != null) {
/* 373 */       return stmt;
/*     */     }
/*     */     
/* 376 */     if (this.logLevelSql) {
/* 377 */       t.logInternal(sql);
/*     */     }
/*     */     
/* 380 */     stmt = getPstmt(t, sql, genKeys);
/*     */     
/* 382 */     PstmtBatch pstmtBatch = request.getPstmtBatch();
/* 383 */     if (pstmtBatch != null) {
/* 384 */       pstmtBatch.setBatchSize(stmt, t.getBatchControl().getBatchSize());
/*     */     }
/*     */     
/* 387 */     BatchedPstmt bs = new BatchedPstmt(stmt, genKeys, sql, request.getPstmtBatch(), true);
/* 388 */     batch.addStmt(bs, (BatchPostExecute)request);
/* 389 */     return stmt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class UpdateGenValue
/*     */   {
/*     */     private final BeanProperty property;
/*     */ 
/*     */     
/*     */     private final Object bean;
/*     */     
/*     */     private final Object value;
/*     */ 
/*     */     
/*     */     private UpdateGenValue(BeanProperty property, Object bean, Object value) {
/* 405 */       this.property = property;
/* 406 */       this.bean = bean;
/* 407 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void setValue() {
/* 415 */       this.property.setValueIntercept(this.bean, this.value);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dml\DmlHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */