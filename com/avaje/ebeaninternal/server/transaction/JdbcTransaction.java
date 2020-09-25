/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import com.avaje.ebean.LogLevel;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebeaninternal.api.DerivedRelationshipData;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.api.TransactionEvent;
/*     */ import com.avaje.ebeaninternal.server.persist.BatchControl;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ import javax.persistence.RollbackException;
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
/*     */ public class JdbcTransaction
/*     */   implements SpiTransaction
/*     */ {
/*  49 */   private static final Logger logger = Logger.getLogger(JdbcTransaction.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String illegalStateMessage = "Transaction is Inactive";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final TransactionManager manager;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final String id;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean explicit;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean autoCommit;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final TransactionManager.OnQueryOnly onQueryOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean active;
/*     */ 
/*     */ 
/*     */   
/*     */   Connection connection;
/*     */ 
/*     */ 
/*     */   
/*     */   BatchControl batchControl;
/*     */ 
/*     */ 
/*     */   
/*     */   TransactionEvent event;
/*     */ 
/*     */ 
/*     */   
/*     */   PersistenceContext persistenceContext;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean persistCascade = true;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean queryOnly = true;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean localReadOnly;
/*     */ 
/*     */ 
/*     */   
/*     */   LogLevel logLevel;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean batchMode;
/*     */ 
/*     */ 
/*     */   
/* 124 */   int batchSize = -1;
/*     */ 
/*     */   
/*     */   boolean batchFlushOnQuery = true;
/*     */ 
/*     */   
/*     */   Boolean batchGetGeneratedKeys;
/*     */ 
/*     */   
/*     */   Boolean batchFlushOnMixed;
/*     */   
/* 135 */   int depth = 0;
/*     */   
/* 137 */   HashSet<Object> persistingBeans = new HashSet();
/*     */   
/*     */   HashSet<Integer> deletingBeansHash;
/*     */   
/*     */   TransactionLogBuffer logBuffer;
/*     */   
/*     */   HashMap<Integer, List<DerivedRelationshipData>> derivedRelMap;
/* 144 */   private final Map<String, Object> userObjects = new ConcurrentHashMap<String, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdbcTransaction(String id, boolean explicit, LogLevel logLevel, Connection connection, TransactionManager manager) {
/*     */     try {
/* 151 */       this.active = true;
/* 152 */       this.id = id;
/* 153 */       this.explicit = explicit;
/* 154 */       this.logLevel = logLevel;
/* 155 */       this.manager = manager;
/* 156 */       this.connection = connection;
/* 157 */       this.autoCommit = connection.getAutoCommit();
/* 158 */       if (this.autoCommit) {
/* 159 */         connection.setAutoCommit(false);
/*     */       }
/* 161 */       this.onQueryOnly = (manager == null) ? TransactionManager.OnQueryOnly.ROLLBACK : manager.getOnQueryOnly();
/* 162 */       this.persistenceContext = new DefaultPersistenceContext();
/*     */       
/* 164 */       this.logBuffer = new TransactionLogBuffer(50, id);
/*     */     }
/* 166 */     catch (Exception e) {
/* 167 */       throw new PersistenceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 172 */     return "Trans[" + this.id + "]";
/*     */   }
/*     */   
/*     */   public List<DerivedRelationshipData> getDerivedRelationship(Object bean) {
/* 176 */     if (this.derivedRelMap == null) {
/* 177 */       return null;
/*     */     }
/* 179 */     Integer key = Integer.valueOf(System.identityHashCode(bean));
/* 180 */     return this.derivedRelMap.get(key);
/*     */   }
/*     */   
/*     */   public void registerDerivedRelationship(DerivedRelationshipData derivedRelationship) {
/* 184 */     if (this.derivedRelMap == null) {
/* 185 */       this.derivedRelMap = new HashMap<Integer, List<DerivedRelationshipData>>();
/*     */     }
/* 187 */     Integer key = Integer.valueOf(System.identityHashCode(derivedRelationship.getAssocBean()));
/*     */     
/* 189 */     List<DerivedRelationshipData> list = this.derivedRelMap.get(key);
/* 190 */     if (list == null) {
/* 191 */       list = new ArrayList<DerivedRelationshipData>();
/* 192 */       this.derivedRelMap.put(key, list);
/*     */     } 
/* 194 */     list.add(derivedRelationship);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerDeleteBean(Integer persistingBean) {
/* 204 */     if (this.deletingBeansHash == null) {
/* 205 */       this.deletingBeansHash = new HashSet<Integer>();
/*     */     }
/* 207 */     this.deletingBeansHash.add(persistingBean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterDeleteBean(Integer persistedBean) {
/* 214 */     if (this.deletingBeansHash != null) {
/* 215 */       this.deletingBeansHash.remove(persistedBean);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRegisteredDeleteBean(Integer persistingBean) {
/* 223 */     if (this.deletingBeansHash == null) {
/* 224 */       return false;
/*     */     }
/* 226 */     return this.deletingBeansHash.contains(persistingBean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterBean(Object bean) {
/* 234 */     this.persistingBeans.remove(bean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRegisteredBean(Object bean) {
/* 242 */     return !this.persistingBeans.add(bean);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int depth(int diff) {
/* 263 */     this.depth += diff;
/* 264 */     return this.depth;
/*     */   }
/*     */   
/*     */   public boolean isReadOnly() {
/* 268 */     if (!isActive()) {
/* 269 */       throw new IllegalStateException("Transaction is Inactive");
/*     */     }
/*     */     try {
/* 272 */       return this.connection.isReadOnly();
/* 273 */     } catch (SQLException e) {
/* 274 */       throw new PersistenceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setReadOnly(boolean readOnly) {
/* 279 */     if (!isActive()) {
/* 280 */       throw new IllegalStateException("Transaction is Inactive");
/*     */     }
/*     */     try {
/* 283 */       this.localReadOnly = readOnly;
/* 284 */       this.connection.setReadOnly(readOnly);
/* 285 */     } catch (SQLException e) {
/* 286 */       throw new PersistenceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setBatchMode(boolean batchMode) {
/* 291 */     if (!isActive()) {
/* 292 */       throw new IllegalStateException("Transaction is Inactive");
/*     */     }
/* 294 */     this.batchMode = batchMode;
/*     */   }
/*     */   
/*     */   public void setBatchGetGeneratedKeys(boolean getGeneratedKeys) {
/* 298 */     this.batchGetGeneratedKeys = Boolean.valueOf(getGeneratedKeys);
/* 299 */     if (this.batchControl != null) {
/* 300 */       this.batchControl.setGetGeneratedKeys(Boolean.valueOf(getGeneratedKeys));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setBatchFlushOnMixed(boolean batchFlushOnMixed) {
/* 305 */     this.batchFlushOnMixed = Boolean.valueOf(batchFlushOnMixed);
/* 306 */     if (this.batchControl != null) {
/* 307 */       this.batchControl.setBatchFlushOnMixed(batchFlushOnMixed);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBatchSize() {
/* 318 */     return this.batchSize;
/*     */   }
/*     */   
/*     */   public void setBatchSize(int batchSize) {
/* 322 */     this.batchSize = batchSize;
/* 323 */     if (this.batchControl != null) {
/* 324 */       this.batchControl.setBatchSize(batchSize);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isBatchFlushOnQuery() {
/* 329 */     return this.batchFlushOnQuery;
/*     */   }
/*     */   
/*     */   public void setBatchFlushOnQuery(boolean batchFlushOnQuery) {
/* 333 */     this.batchFlushOnQuery = batchFlushOnQuery;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBatchThisRequest() {
/* 341 */     if (!this.explicit && this.depth <= 0)
/*     */     {
/*     */       
/* 344 */       return false;
/*     */     }
/* 346 */     return this.batchMode;
/*     */   }
/*     */   
/*     */   public BatchControl getBatchControl() {
/* 350 */     return this.batchControl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBatchControl(BatchControl batchControl) {
/* 358 */     this.queryOnly = false;
/* 359 */     this.batchControl = batchControl;
/*     */     
/* 361 */     if (this.batchGetGeneratedKeys != null) {
/* 362 */       batchControl.setGetGeneratedKeys(this.batchGetGeneratedKeys);
/*     */     }
/* 364 */     if (this.batchSize != -1) {
/* 365 */       batchControl.setBatchSize(this.batchSize);
/*     */     }
/* 367 */     if (this.batchFlushOnMixed != null) {
/* 368 */       batchControl.setBatchFlushOnMixed(this.batchFlushOnMixed.booleanValue());
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
/*     */   public void flushBatch() {
/* 380 */     if (!isActive()) {
/* 381 */       throw new IllegalStateException("Transaction is Inactive");
/*     */     }
/* 383 */     if (this.batchControl != null) {
/* 384 */       this.batchControl.flush();
/*     */     }
/*     */   }
/*     */   
/*     */   public void batchFlush() {
/* 389 */     flushBatch();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistenceContext getPersistenceContext() {
/* 396 */     return this.persistenceContext;
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
/*     */   public void setPersistenceContext(PersistenceContext context) {
/* 408 */     if (!isActive()) {
/* 409 */       throw new IllegalStateException("Transaction is Inactive");
/*     */     }
/* 411 */     this.persistenceContext = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransactionEvent getEvent() {
/* 418 */     this.queryOnly = false;
/* 419 */     if (this.event == null) {
/* 420 */       this.event = new TransactionEvent();
/*     */     }
/* 422 */     return this.event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoggingOn(boolean loggingOn) {
/* 429 */     if (loggingOn) {
/* 430 */       this.logLevel = LogLevel.SQL;
/*     */     } else {
/* 432 */       this.logLevel = LogLevel.NONE;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExplicit() {
/* 440 */     return this.explicit;
/*     */   }
/*     */   
/*     */   public boolean isLogSql() {
/* 444 */     return (this.logLevel.ordinal() >= LogLevel.SQL.ordinal());
/*     */   }
/*     */   
/*     */   public boolean isLogSummary() {
/* 448 */     return (this.logLevel.ordinal() >= LogLevel.SUMMARY.ordinal());
/*     */   }
/*     */   
/*     */   public LogLevel getLogLevel() {
/* 452 */     return this.logLevel;
/*     */   }
/*     */   
/*     */   public void setLogLevel(LogLevel logLevel) {
/* 456 */     this.logLevel = logLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(String msg) {
/* 463 */     if (isLogSummary()) {
/* 464 */       logInternal(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logInternal(String msg) {
/* 473 */     if (this.manager != null && 
/* 474 */       this.logBuffer.add(msg)) {
/*     */       
/* 476 */       this.manager.log(this.logBuffer);
/* 477 */       this.logBuffer = this.logBuffer.newBuffer();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 486 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getInternalConnection() {
/* 493 */     if (!isActive()) {
/* 494 */       throw new IllegalStateException("Transaction is Inactive");
/*     */     }
/* 496 */     return this.connection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() {
/* 503 */     this.queryOnly = false;
/* 504 */     return getInternalConnection();
/*     */   }
/*     */   
/*     */   protected void deactivate() {
/*     */     try {
/* 509 */       if (this.localReadOnly)
/*     */       {
/* 511 */         this.connection.setReadOnly(false);
/*     */       }
/* 513 */     } catch (SQLException e) {
/* 514 */       logger.log(Level.SEVERE, "Error setting to readOnly?", e);
/*     */     } 
/*     */     try {
/* 517 */       if (this.autoCommit)
/*     */       {
/* 519 */         this.connection.setAutoCommit(true);
/*     */       }
/* 521 */     } catch (SQLException e) {
/* 522 */       logger.log(Level.SEVERE, "Error setting to readOnly?", e);
/*     */     } 
/*     */     try {
/* 525 */       this.connection.close();
/* 526 */     } catch (Exception ex) {
/*     */ 
/*     */       
/* 529 */       logger.log(Level.SEVERE, "Error closing connection", ex);
/*     */     } 
/* 531 */     this.connection = null;
/* 532 */     this.active = false;
/*     */   }
/*     */   
/*     */   public TransactionLogBuffer getLogBuffer() {
/* 536 */     return this.logBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyCommit() {
/* 543 */     if (this.manager == null) {
/*     */       return;
/*     */     }
/* 546 */     if (this.queryOnly) {
/* 547 */       this.manager.notifyOfQueryOnly(true, this, null);
/*     */     } else {
/* 549 */       this.manager.notifyOfCommit(this);
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
/*     */   private void commitQueryOnly() {
/*     */     try {
/* 562 */       switch (this.onQueryOnly) {
/*     */         case ROLLBACK:
/* 564 */           this.connection.rollback();
/*     */         
/*     */         case COMMIT:
/* 567 */           this.connection.commit();
/*     */ 
/*     */         
/*     */         case CLOSE_ON_READCOMMITTED:
/*     */           return;
/*     */       } 
/*     */       
/* 574 */       this.connection.rollback();
/*     */     }
/* 576 */     catch (SQLException e) {
/* 577 */       String m = "Error when ending a query only transaction via " + this.onQueryOnly;
/* 578 */       logger.log(Level.SEVERE, m, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void commit() throws RollbackException {
/* 586 */     if (!isActive()) {
/* 587 */       throw new IllegalStateException("Transaction is Inactive");
/*     */     }
/*     */     try {
/* 590 */       if (this.queryOnly) {
/*     */         
/* 592 */         commitQueryOnly();
/*     */       } else {
/*     */         
/* 595 */         if (this.batchControl != null && !this.batchControl.isEmpty()) {
/* 596 */           this.batchControl.flush();
/*     */         }
/* 598 */         this.connection.commit();
/*     */       } 
/*     */       
/* 601 */       deactivate();
/* 602 */       notifyCommit();
/*     */     }
/* 604 */     catch (Exception e) {
/* 605 */       throw new RollbackException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyRollback(Throwable cause) {
/* 613 */     if (this.manager == null) {
/*     */       return;
/*     */     }
/* 616 */     if (this.queryOnly) {
/* 617 */       this.manager.notifyOfQueryOnly(false, this, cause);
/*     */     } else {
/* 619 */       this.manager.notifyOfRollback(this, cause);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rollback() throws PersistenceException {
/* 627 */     rollback(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rollback(Throwable cause) throws PersistenceException {
/* 635 */     if (!isActive()) {
/* 636 */       throw new IllegalStateException("Transaction is Inactive");
/*     */     }
/*     */     try {
/* 639 */       this.connection.rollback();
/*     */ 
/*     */       
/* 642 */       deactivate();
/* 643 */       notifyRollback(cause);
/*     */     }
/* 645 */     catch (Exception ex) {
/* 646 */       throw new PersistenceException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void end() throws PersistenceException {
/* 654 */     if (isActive()) {
/* 655 */       rollback();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 663 */     return this.active;
/*     */   }
/*     */   
/*     */   public boolean isPersistCascade() {
/* 667 */     return this.persistCascade;
/*     */   }
/*     */   
/*     */   public void setPersistCascade(boolean persistCascade) {
/* 671 */     this.persistCascade = persistCascade;
/*     */   }
/*     */   
/*     */   public void addModification(String tableName, boolean inserts, boolean updates, boolean deletes) {
/* 675 */     getEvent().add(tableName, inserts, updates, deletes);
/*     */   }
/*     */   
/*     */   public void putUserObject(String name, Object value) {
/* 679 */     this.userObjects.put(name, value);
/*     */   }
/*     */   
/*     */   public Object getUserObject(String name) {
/* 683 */     return this.userObjects.get(name);
/*     */   }
/*     */   
/*     */   public final TransactionManager getTransactionManger() {
/* 687 */     return this.manager;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\JdbcTransaction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */