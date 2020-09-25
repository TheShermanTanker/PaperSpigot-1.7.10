/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import com.avaje.ebean.BackgroundExecutor;
/*     */ import com.avaje.ebean.LogLevel;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.TxIsolation;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebean.event.TransactionEventListener;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.api.TransactionEvent;
/*     */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*     */ import com.avaje.ebeaninternal.server.cluster.ClusterManager;
/*     */ import com.avaje.ebeaninternal.server.core.BootupClasses;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ import javax.sql.DataSource;
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
/*     */ public class TransactionManager
/*     */ {
/*     */   private final BeanDescriptorManager beanDescriptorManager;
/*     */   private LogLevel logLevel;
/*     */   private final TransactionLogManager transLogger;
/*     */   private final String prefix;
/*     */   private final String externalTransPrefix;
/*     */   private final DataSource dataSource;
/*  55 */   private static final Logger logger = Logger.getLogger(TransactionManager.class.getName());
/*     */   private final OnQueryOnly onQueryOnly;
/*     */   private final boolean defaultBatchMode;
/*     */   private final BackgroundExecutor backgroundExecutor;
/*     */   private final ClusterManager clusterManager;
/*     */   private final int commitDebugLevel;
/*     */   private final String serverName;
/*     */   
/*     */   public enum OnQueryOnly
/*     */   {
/*  65 */     ROLLBACK,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     CLOSE_ON_READCOMMITTED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     COMMIT;
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
/* 121 */   private AtomicLong transactionCounter = new AtomicLong(1000L);
/*     */ 
/*     */   
/*     */   private int clusterDebugLevel;
/*     */ 
/*     */   
/*     */   private final BulkEventListenerMap bulkEventListenerMap;
/*     */ 
/*     */   
/*     */   private TransactionEventListener[] transactionEventListeners;
/*     */ 
/*     */ 
/*     */   
/*     */   public TransactionManager(ClusterManager clusterManager, BackgroundExecutor backgroundExecutor, ServerConfig config, BeanDescriptorManager descMgr, BootupClasses bootupClasses) {
/* 135 */     this.beanDescriptorManager = descMgr;
/* 136 */     this.clusterManager = clusterManager;
/* 137 */     this.serverName = config.getName();
/*     */     
/* 139 */     this.logLevel = config.getLoggingLevel();
/* 140 */     this.transLogger = new TransactionLogManager(config);
/* 141 */     this.backgroundExecutor = backgroundExecutor;
/* 142 */     this.dataSource = config.getDataSource();
/* 143 */     this.bulkEventListenerMap = new BulkEventListenerMap(config.getBulkTableEventListeners());
/*     */     
/* 145 */     List<TransactionEventListener> transactionEventListeners = bootupClasses.getTransactionEventListeners();
/* 146 */     this.transactionEventListeners = transactionEventListeners.<TransactionEventListener>toArray(new TransactionEventListener[transactionEventListeners.size()]);
/*     */ 
/*     */     
/* 149 */     this.commitDebugLevel = GlobalProperties.getInt("ebean.commit.debuglevel", 0);
/* 150 */     this.clusterDebugLevel = GlobalProperties.getInt("ebean.cluster.debuglevel", 0);
/*     */     
/* 152 */     this.defaultBatchMode = config.isPersistBatching();
/*     */     
/* 154 */     this.prefix = GlobalProperties.get("transaction.prefix", "");
/* 155 */     this.externalTransPrefix = GlobalProperties.get("transaction.prefix", "e");
/*     */     
/* 157 */     String value = GlobalProperties.get("transaction.onqueryonly", "ROLLBACK").toUpperCase().trim();
/* 158 */     this.onQueryOnly = getOnQueryOnly(value, this.dataSource);
/*     */   }
/*     */   
/*     */   public void shutdown() {
/* 162 */     this.transLogger.shutdown();
/*     */   }
/*     */   
/*     */   public BeanDescriptorManager getBeanDescriptorManager() {
/* 166 */     return this.beanDescriptorManager;
/*     */   }
/*     */   
/*     */   public BulkEventListenerMap getBulkEventListenerMap() {
/* 170 */     return this.bulkEventListenerMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LogLevel getTransactionLogLevel() {
/* 177 */     return this.logLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransactionLogLevel(LogLevel logLevel) {
/* 184 */     this.logLevel = logLevel;
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
/*     */   private OnQueryOnly getOnQueryOnly(String onQueryOnly, DataSource ds) {
/* 202 */     if (onQueryOnly.equals("COMMIT")) {
/* 203 */       return OnQueryOnly.COMMIT;
/*     */     }
/* 205 */     if (onQueryOnly.startsWith("CLOSE")) {
/* 206 */       if (!isReadCommitedIsolation(ds)) {
/* 207 */         String m = "transaction.queryonlyclose is true but the transaction Isolation Level is not READ_COMMITTED";
/* 208 */         throw new PersistenceException(m);
/*     */       } 
/* 210 */       return OnQueryOnly.CLOSE_ON_READCOMMITTED;
/*     */     } 
/*     */ 
/*     */     
/* 214 */     return OnQueryOnly.ROLLBACK;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isReadCommitedIsolation(DataSource ds) {
/* 222 */     Connection c = null;
/*     */     try {
/* 224 */       c = ds.getConnection();
/*     */       
/* 226 */       int isolationLevel = c.getTransactionIsolation();
/* 227 */       return (isolationLevel == 2);
/*     */     }
/* 229 */     catch (SQLException ex) {
/* 230 */       String m = "Errored trying to determine the default Isolation Level";
/* 231 */       throw new PersistenceException(m, ex);
/*     */     } finally {
/*     */       
/*     */       try {
/* 235 */         if (c != null) {
/* 236 */           c.close();
/*     */         }
/* 238 */       } catch (SQLException ex) {
/* 239 */         logger.log(Level.SEVERE, "closing connection", ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getServerName() {
/* 245 */     return this.serverName;
/*     */   }
/*     */   
/*     */   public DataSource getDataSource() {
/* 249 */     return this.dataSource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getClusterDebugLevel() {
/* 256 */     return this.clusterDebugLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClusterDebugLevel(int clusterDebugLevel) {
/* 263 */     this.clusterDebugLevel = clusterDebugLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OnQueryOnly getOnQueryOnly() {
/* 270 */     return this.onQueryOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransactionLogManager getLogger() {
/* 277 */     return this.transLogger;
/*     */   }
/*     */   
/*     */   public void log(TransactionLogBuffer logBuffer) {
/* 281 */     if (!logBuffer.isEmpty()) {
/* 282 */       this.transLogger.log(logBuffer);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiTransaction wrapExternalConnection(Connection c) {
/* 291 */     return wrapExternalConnection(this.externalTransPrefix + c.hashCode(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiTransaction wrapExternalConnection(String id, Connection c) {
/* 299 */     ExternalJdbcTransaction t = new ExternalJdbcTransaction(id, true, this.logLevel, c, this);
/*     */ 
/*     */ 
/*     */     
/* 303 */     if (this.defaultBatchMode) {
/* 304 */       t.setBatchMode(true);
/*     */     }
/*     */     
/* 307 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiTransaction createTransaction(boolean explicit, int isolationLevel) {
/* 314 */     Connection c = null;
/*     */     try {
/* 316 */       c = this.dataSource.getConnection();
/* 317 */       long id = this.transactionCounter.incrementAndGet();
/*     */       
/* 319 */       JdbcTransaction t = new JdbcTransaction(this.prefix + id, explicit, this.logLevel, c, this);
/*     */ 
/*     */ 
/*     */       
/* 323 */       if (this.defaultBatchMode) {
/* 324 */         t.setBatchMode(true);
/*     */       }
/* 326 */       if (isolationLevel > -1) {
/* 327 */         c.setTransactionIsolation(isolationLevel);
/*     */       }
/*     */       
/* 330 */       if (this.commitDebugLevel >= 3) {
/* 331 */         String msg = "Transaction [" + t.getId() + "] begin";
/* 332 */         if (isolationLevel > -1) {
/* 333 */           TxIsolation txi = TxIsolation.fromLevel(isolationLevel);
/* 334 */           msg = msg + " isolationLevel[" + txi + "]";
/*     */         } 
/* 336 */         logger.info(msg);
/*     */       } 
/*     */       
/* 339 */       return t;
/*     */     }
/* 341 */     catch (SQLException ex) {
/*     */       
/*     */       try {
/* 344 */         if (c != null) {
/* 345 */           c.close();
/*     */         }
/* 347 */       } catch (SQLException e) {
/* 348 */         logger.log(Level.SEVERE, "Error closing failed connection", e);
/*     */       } 
/* 350 */       throw new PersistenceException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SpiTransaction createQueryTransaction() {
/* 355 */     Connection c = null;
/*     */     try {
/* 357 */       c = this.dataSource.getConnection();
/* 358 */       long id = this.transactionCounter.incrementAndGet();
/*     */       
/* 360 */       JdbcTransaction t = new JdbcTransaction(this.prefix + id, false, this.logLevel, c, this);
/*     */ 
/*     */ 
/*     */       
/* 364 */       if (this.defaultBatchMode) {
/* 365 */         t.setBatchMode(true);
/*     */       }
/*     */       
/* 368 */       if (this.commitDebugLevel >= 3) {
/* 369 */         logger.info("Transaction [" + t.getId() + "] begin - queryOnly");
/*     */       }
/*     */       
/* 372 */       return t;
/*     */     }
/* 374 */     catch (PersistenceException ex) {
/*     */       
/*     */       try {
/* 377 */         if (c != null) {
/* 378 */           c.close();
/*     */         }
/* 380 */       } catch (SQLException e) {
/* 381 */         logger.log(Level.SEVERE, "Error closing failed connection", e);
/*     */       } 
/* 383 */       throw ex;
/*     */     }
/* 385 */     catch (SQLException ex) {
/*     */       
/* 387 */       throw new PersistenceException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOfRollback(SpiTransaction transaction, Throwable cause) {
/*     */     try {
/* 397 */       for (TransactionEventListener listener : this.transactionEventListeners) {
/* 398 */         listener.postTransactionRollback((Transaction)transaction, cause);
/*     */       }
/*     */       
/* 401 */       if (transaction.isLogSummary() || this.commitDebugLevel >= 1) {
/* 402 */         String msg = "Rollback";
/* 403 */         if (cause != null) {
/* 404 */           msg = msg + " error: " + formatThrowable(cause);
/*     */         }
/* 406 */         if (transaction.isLogSummary()) {
/* 407 */           transaction.logInternal(msg);
/*     */         }
/*     */         
/* 410 */         if (this.commitDebugLevel >= 1) {
/* 411 */           logger.info("Transaction [" + transaction.getId() + "] " + msg);
/*     */         }
/*     */       } 
/*     */       
/* 415 */       log(transaction.getLogBuffer());
/* 416 */     } catch (Exception ex) {
/* 417 */       String m = "Potentially Transaction Log incomplete due to error:";
/* 418 */       logger.log(Level.SEVERE, m, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOfQueryOnly(boolean onCommit, SpiTransaction transaction, Throwable cause) {
/*     */     try {
/* 428 */       if (this.commitDebugLevel >= 2) {
/*     */         String msg;
/* 430 */         if (onCommit) {
/* 431 */           msg = "Commit queryOnly";
/*     */         } else {
/*     */           
/* 434 */           msg = "Rollback queryOnly";
/* 435 */           if (cause != null) {
/* 436 */             msg = msg + " error: " + formatThrowable(cause);
/*     */           }
/*     */         } 
/* 439 */         if (transaction.isLogSummary()) {
/* 440 */           transaction.logInternal(msg);
/*     */         }
/* 442 */         logger.info("Transaction [" + transaction.getId() + "] " + msg);
/*     */       } 
/*     */       
/* 445 */       log(transaction.getLogBuffer());
/*     */     }
/* 447 */     catch (Exception ex) {
/* 448 */       String m = "Potentially Transaction Log incomplete due to error:";
/* 449 */       logger.log(Level.SEVERE, m, ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String formatThrowable(Throwable e) {
/* 454 */     if (e == null) {
/* 455 */       return "";
/*     */     }
/* 457 */     StringBuilder sb = new StringBuilder();
/* 458 */     formatThrowable(e, sb);
/* 459 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private void formatThrowable(Throwable e, StringBuilder sb) {
/* 464 */     sb.append(e.toString());
/* 465 */     StackTraceElement[] stackTrace = e.getStackTrace();
/* 466 */     if (stackTrace.length > 0) {
/* 467 */       sb.append(" stack0: ");
/* 468 */       sb.append(stackTrace[0]);
/*     */     } 
/* 470 */     Throwable cause = e.getCause();
/* 471 */     if (cause != null) {
/* 472 */       sb.append(" cause: ");
/* 473 */       formatThrowable(cause, sb);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOfCommit(SpiTransaction transaction) {
/*     */     try {
/* 484 */       log(transaction.getLogBuffer());
/*     */       
/* 486 */       PostCommitProcessing postCommit = new PostCommitProcessing(this.clusterManager, this, transaction, transaction.getEvent());
/*     */ 
/*     */       
/* 489 */       postCommit.notifyLocalCacheIndex();
/* 490 */       postCommit.notifyCluster();
/*     */ 
/*     */       
/* 493 */       this.backgroundExecutor.execute(postCommit.notifyPersistListeners());
/*     */       
/* 495 */       for (TransactionEventListener listener : this.transactionEventListeners) {
/* 496 */         listener.postTransactionCommit((Transaction)transaction);
/*     */       }
/*     */       
/* 499 */       if (this.commitDebugLevel >= 1) {
/* 500 */         logger.info("Transaction [" + transaction.getId() + "] commit");
/*     */       }
/* 502 */     } catch (Exception ex) {
/* 503 */       String m = "NotifyOfCommit failed. Cache/Lucene potentially not notified.";
/* 504 */       logger.log(Level.SEVERE, m, ex);
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
/*     */ 
/*     */   
/*     */   public void externalModification(TransactionEventTable tableEvents) {
/* 520 */     TransactionEvent event = new TransactionEvent();
/* 521 */     event.add(tableEvents);
/*     */     
/* 523 */     PostCommitProcessing postCommit = new PostCommitProcessing(this.clusterManager, this, null, event);
/*     */ 
/*     */     
/* 526 */     postCommit.notifyLocalCacheIndex();
/*     */     
/* 528 */     this.backgroundExecutor.execute(postCommit.notifyPersistListeners());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remoteTransactionEvent(RemoteTransactionEvent remoteEvent) {
/* 537 */     if (this.clusterDebugLevel > 0 || logger.isLoggable(Level.FINE)) {
/* 538 */       logger.info("Cluster Received: " + remoteEvent.toString());
/*     */     }
/*     */     
/* 541 */     List<TransactionEventTable.TableIUD> tableIUDList = remoteEvent.getTableIUDList();
/* 542 */     if (tableIUDList != null) {
/* 543 */       for (int i = 0; i < tableIUDList.size(); i++) {
/* 544 */         TransactionEventTable.TableIUD tableIUD = tableIUDList.get(i);
/* 545 */         this.beanDescriptorManager.cacheNotify(tableIUD);
/*     */       } 
/*     */     }
/*     */     
/* 549 */     List<BeanPersistIds> beanPersistList = remoteEvent.getBeanPersistList();
/* 550 */     if (beanPersistList != null)
/* 551 */       for (int i = 0; i < beanPersistList.size(); i++) {
/* 552 */         BeanPersistIds beanPersist = beanPersistList.get(i);
/* 553 */         beanPersist.notifyCacheAndListener();
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\TransactionManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */