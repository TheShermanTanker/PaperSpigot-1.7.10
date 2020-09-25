/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import com.avaje.ebean.config.DataSourceConfig;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
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
/*     */ public class DataSourcePool
/*     */   implements DataSource
/*     */ {
/*  55 */   private static final Logger logger = Logger.getLogger(DataSourcePool.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final DataSourceNotify notify;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final DataSourcePoolListener poolListener;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Properties connectionProps;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String databaseUrl;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String databaseDriver;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String heartbeatsql;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int transactionIsolation;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean autoCommit;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean captureStackTrace;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int maxStackTraceSize;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean dataSourceDownAlertSent;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastTrimTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean dataSourceUp = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean inWarningMode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int minConnections;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int maxConnections;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int warningSize;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int waitTimeoutMillis;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int pstmtCacheSize;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int maxInactiveTimeSecs;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final PooledConnectionQueue queue;
/*     */ 
/*     */ 
/*     */   
/*     */   private long leakTimeMinutes;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataSourcePool(DataSourceNotify notify, String name, DataSourceConfig params) {
/* 175 */     this.notify = notify;
/* 176 */     this.name = name;
/* 177 */     this.poolListener = createPoolListener(params.getPoolListener());
/*     */     
/* 179 */     this.autoCommit = false;
/* 180 */     this.transactionIsolation = params.getIsolationLevel();
/*     */     
/* 182 */     this.maxInactiveTimeSecs = params.getMaxInactiveTimeSecs();
/* 183 */     this.leakTimeMinutes = params.getLeakTimeMinutes();
/* 184 */     this.captureStackTrace = params.isCaptureStackTrace();
/* 185 */     this.maxStackTraceSize = params.getMaxStackTraceSize();
/* 186 */     this.databaseDriver = params.getDriver();
/* 187 */     this.databaseUrl = params.getUrl();
/* 188 */     this.pstmtCacheSize = params.getPstmtCacheSize();
/*     */     
/* 190 */     this.minConnections = params.getMinConnections();
/* 191 */     this.maxConnections = params.getMaxConnections();
/* 192 */     this.waitTimeoutMillis = params.getWaitTimeoutMillis();
/* 193 */     this.heartbeatsql = params.getHeartbeatSql();
/*     */     
/* 195 */     this.queue = new PooledConnectionQueue(this);
/*     */     
/* 197 */     String un = params.getUsername();
/* 198 */     String pw = params.getPassword();
/* 199 */     if (un == null) {
/* 200 */       throw new RuntimeException("DataSource user is null?");
/*     */     }
/* 202 */     if (pw == null) {
/* 203 */       throw new RuntimeException("DataSource password is null?");
/*     */     }
/* 205 */     this.connectionProps = new Properties();
/* 206 */     this.connectionProps.setProperty("user", un);
/* 207 */     this.connectionProps.setProperty("password", pw);
/*     */     
/* 209 */     Map<String, String> customProperties = params.getCustomProperties();
/* 210 */     if (customProperties != null) {
/* 211 */       Set<Map.Entry<String, String>> entrySet = customProperties.entrySet();
/* 212 */       for (Map.Entry<String, String> entry : entrySet) {
/* 213 */         this.connectionProps.setProperty(entry.getKey(), entry.getValue());
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 218 */       initialise();
/* 219 */     } catch (SQLException ex) {
/* 220 */       throw new DataSourceException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DataSourcePoolListener createPoolListener(String cn) {
/* 228 */     if (cn == null) {
/* 229 */       return null;
/*     */     }
/*     */     try {
/* 232 */       return (DataSourcePoolListener)ClassUtil.newInstance(cn, getClass());
/* 233 */     } catch (Exception e) {
/* 234 */       throw new DataSourceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialise() throws SQLException {
/*     */     try {
/* 242 */       ClassUtil.forName(this.databaseDriver, getClass());
/* 243 */     } catch (Throwable e) {
/* 244 */       throw new PersistenceException("Problem loading Database Driver [" + this.databaseDriver + "]: " + e.getMessage(), e);
/*     */     } 
/*     */ 
/*     */     
/* 248 */     String transIsolation = TransactionIsolation.getLevelDescription(this.transactionIsolation);
/* 249 */     StringBuilder sb = new StringBuilder();
/* 250 */     sb.append("DataSourcePool [").append(this.name);
/* 251 */     sb.append("] autoCommit[").append(this.autoCommit);
/* 252 */     sb.append("] transIsolation[").append(transIsolation);
/* 253 */     sb.append("] min[").append(this.minConnections);
/* 254 */     sb.append("] max[").append(this.maxConnections).append("]");
/*     */     
/* 256 */     logger.info(sb.toString());
/*     */     
/* 258 */     this.queue.ensureMinimumConnections();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWrapperFor(Class<?> arg0) throws SQLException {
/* 265 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T unwrap(Class<T> arg0) throws SQLException {
/* 272 */     throw new SQLException("Not Implemented");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 279 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStackTraceSize() {
/* 289 */     return this.maxStackTraceSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDataSourceUp() {
/* 296 */     return this.dataSourceUp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyWarning(String msg) {
/* 304 */     if (!this.inWarningMode) {
/*     */       
/* 306 */       this.inWarningMode = true;
/* 307 */       logger.warning(msg);
/* 308 */       if (this.notify != null) {
/* 309 */         String subject = "DataSourcePool [" + this.name + "] warning";
/* 310 */         this.notify.notifyWarning(subject, msg);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void notifyDataSourceIsDown(SQLException ex) {
/* 317 */     if (!this.dataSourceDownAlertSent) {
/* 318 */       String msg = "FATAL: DataSourcePool [" + this.name + "] is down!!!";
/* 319 */       logger.log(Level.SEVERE, msg, ex);
/* 320 */       if (this.notify != null) {
/* 321 */         this.notify.notifyDataSourceDown(this.name);
/*     */       }
/* 323 */       this.dataSourceDownAlertSent = true;
/*     */     } 
/*     */     
/* 326 */     if (this.dataSourceUp) {
/* 327 */       reset();
/*     */     }
/* 329 */     this.dataSourceUp = false;
/*     */   }
/*     */   
/*     */   private void notifyDataSourceIsUp() {
/* 333 */     if (this.dataSourceDownAlertSent) {
/* 334 */       String msg = "RESOLVED FATAL: DataSourcePool [" + this.name + "] is back up!";
/* 335 */       logger.log(Level.SEVERE, msg);
/* 336 */       if (this.notify != null) {
/* 337 */         this.notify.notifyDataSourceUp(this.name);
/*     */       }
/* 339 */       this.dataSourceDownAlertSent = false;
/*     */     }
/* 341 */     else if (!this.dataSourceUp) {
/* 342 */       logger.log(Level.WARNING, "DataSourcePool [" + this.name + "] is back up!");
/*     */     } 
/*     */     
/* 345 */     if (!this.dataSourceUp) {
/* 346 */       this.dataSourceUp = true;
/* 347 */       reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkDataSource() {
/* 355 */     Connection conn = null;
/*     */     
/*     */     try {
/* 358 */       conn = getConnection();
/* 359 */       testConnection(conn);
/*     */       
/* 361 */       notifyDataSourceIsUp();
/*     */       
/* 363 */       if (System.currentTimeMillis() > this.lastTrimTime + (this.maxInactiveTimeSecs * 1000)) {
/* 364 */         this.queue.trim(this.maxInactiveTimeSecs);
/* 365 */         this.lastTrimTime = System.currentTimeMillis();
/*     */       }
/*     */     
/* 368 */     } catch (SQLException ex) {
/* 369 */       notifyDataSourceIsDown(ex);
/*     */     } finally {
/*     */       try {
/* 372 */         if (conn != null) {
/* 373 */           conn.close();
/*     */         }
/* 375 */       } catch (SQLException ex) {
/* 376 */         logger.log(Level.WARNING, "Can't close connection in checkDataSource!");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection createUnpooledConnection() throws SQLException {
/*     */     try {
/* 396 */       Connection conn = DriverManager.getConnection(this.databaseUrl, this.connectionProps);
/* 397 */       conn.setAutoCommit(this.autoCommit);
/* 398 */       conn.setTransactionIsolation(this.transactionIsolation);
/* 399 */       return conn;
/*     */     }
/* 401 */     catch (SQLException ex) {
/* 402 */       notifyDataSourceIsDown(null);
/* 403 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxSize(int max) {
/* 413 */     this.queue.setMaxSize(max);
/* 414 */     this.maxConnections = max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxSize() {
/* 421 */     return this.maxConnections;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinSize(int min) {
/* 428 */     this.queue.setMinSize(min);
/* 429 */     this.minConnections = min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinSize() {
/* 436 */     return this.minConnections;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWarningSize(int warningSize) {
/* 446 */     this.queue.setWarningSize(warningSize);
/* 447 */     this.warningSize = warningSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWarningSize() {
/* 455 */     return this.warningSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWaitTimeoutMillis() {
/* 464 */     return this.waitTimeoutMillis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxInactiveTimeSecs(int maxInactiveTimeSecs) {
/* 471 */     this.maxInactiveTimeSecs = maxInactiveTimeSecs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxInactiveTimeSecs() {
/* 478 */     return this.maxInactiveTimeSecs;
/*     */   }
/*     */ 
/*     */   
/*     */   private void testConnection(Connection conn) throws SQLException {
/* 483 */     if (this.heartbeatsql == null) {
/*     */       return;
/*     */     }
/* 486 */     Statement stmt = null;
/* 487 */     ResultSet rset = null;
/*     */ 
/*     */     
/*     */     try {
/* 491 */       stmt = conn.createStatement();
/* 492 */       rset = stmt.executeQuery(this.heartbeatsql);
/* 493 */       conn.commit();
/*     */     } finally {
/*     */       
/*     */       try {
/* 497 */         if (rset != null) {
/* 498 */           rset.close();
/*     */         }
/* 500 */       } catch (SQLException e) {
/* 501 */         logger.log(Level.SEVERE, (String)null, e);
/*     */       } 
/*     */       try {
/* 504 */         if (stmt != null) {
/* 505 */           stmt.close();
/*     */         }
/* 507 */       } catch (SQLException e) {
/* 508 */         logger.log(Level.SEVERE, (String)null, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean validateConnection(PooledConnection conn) {
/*     */     try {
/* 519 */       if (this.heartbeatsql == null) {
/* 520 */         logger.info("Can not test connection as heartbeatsql is not set");
/* 521 */         return false;
/*     */       } 
/*     */       
/* 524 */       testConnection((Connection)conn);
/* 525 */       return true;
/*     */     }
/* 527 */     catch (Exception e) {
/* 528 */       String desc = "heartbeatsql test failed on connection[" + conn.getName() + "]";
/* 529 */       logger.warning(desc);
/* 530 */       return false;
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected void returnConnection(PooledConnection pooledConnection) {
/* 549 */     if (this.poolListener != null) {
/* 550 */       this.poolListener.onBeforeReturnConnection((Connection)pooledConnection);
/*     */     }
/* 552 */     this.queue.returnPooledConnection(pooledConnection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBusyConnectionInformation() {
/* 560 */     return this.queue.getBusyConnectionInformation();
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
/*     */   public void dumpBusyConnectionInformation() {
/* 572 */     this.queue.dumpBusyConnectionInformation();
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
/*     */   public void closeBusyConnections(long leakTimeMinutes) {
/* 589 */     this.queue.closeBusyConnections(leakTimeMinutes);
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
/*     */   protected PooledConnection createConnectionForQueue(int connId) throws SQLException {
/*     */     try {
/* 602 */       Connection c = createUnpooledConnection();
/*     */       
/* 604 */       PooledConnection pc = new PooledConnection(this, connId, c);
/* 605 */       pc.resetForUse();
/*     */       
/* 607 */       if (!this.dataSourceUp) {
/* 608 */         notifyDataSourceIsUp();
/*     */       }
/* 610 */       return pc;
/*     */     }
/* 612 */     catch (SQLException ex) {
/* 613 */       notifyDataSourceIsDown(ex);
/* 614 */       throw ex;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 633 */     this.queue.reset(this.leakTimeMinutes);
/* 634 */     this.inWarningMode = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() throws SQLException {
/* 641 */     return (Connection)getPooledConnection();
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
/*     */   public PooledConnection getPooledConnection() throws SQLException {
/* 653 */     PooledConnection c = this.queue.getPooledConnection();
/*     */     
/* 655 */     if (this.captureStackTrace) {
/* 656 */       c.setStackTrace(Thread.currentThread().getStackTrace());
/*     */     }
/*     */     
/* 659 */     if (this.poolListener != null) {
/* 660 */       this.poolListener.onAfterBorrowConnection((Connection)c);
/*     */     }
/* 662 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testAlert() {
/* 671 */     String subject = "Test DataSourcePool [" + this.name + "]";
/* 672 */     String msg = "Just testing if alert message is sent successfully.";
/*     */     
/* 674 */     if (this.notify != null) {
/* 675 */       this.notify.notifyWarning(subject, msg);
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
/*     */   public void shutdown() {
/* 689 */     this.queue.shutdown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAutoCommit() {
/* 698 */     return this.autoCommit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTransactionIsolation() {
/* 708 */     return this.transactionIsolation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCaptureStackTrace() {
/* 719 */     return this.captureStackTrace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCaptureStackTrace(boolean captureStackTrace) {
/* 728 */     this.captureStackTrace = captureStackTrace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection(String username, String password) throws SQLException {
/* 735 */     throw new SQLException("Method not supported");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLoginTimeout() throws SQLException {
/* 742 */     throw new SQLException("Method not supported");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoginTimeout(int seconds) throws SQLException {
/* 749 */     throw new SQLException("Method not supported");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrintWriter getLogWriter() {
/* 756 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLogWriter(PrintWriter writer) throws SQLException {
/* 763 */     throw new SQLException("Method not supported");
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
/*     */   public void setLeakTimeMinutes(long leakTimeMinutes) {
/* 777 */     this.leakTimeMinutes = leakTimeMinutes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLeakTimeMinutes() {
/* 785 */     return this.leakTimeMinutes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPstmtCacheSize() {
/* 792 */     return this.pstmtCacheSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPstmtCacheSize(int pstmtCacheSize) {
/* 799 */     this.pstmtCacheSize = pstmtCacheSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Status getStatus(boolean reset) {
/* 810 */     return this.queue.getStatus(reset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deregisterDriver() {
/*     */     try {
/* 818 */       DriverManager.deregisterDriver(DriverManager.getDriver(this.databaseUrl));
/* 819 */       String msg = "Deregistered the JDBC driver " + this.databaseDriver;
/* 820 */       logger.log(Level.FINE, msg);
/* 821 */     } catch (SQLException e) {
/* 822 */       String msg = "Error trying to deregister the JDBC driver " + this.databaseDriver;
/* 823 */       logger.log(Level.WARNING, msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Status
/*     */   {
/*     */     private final String name;
/*     */     private final int minSize;
/*     */     private final int maxSize;
/*     */     private final int free;
/*     */     private final int busy;
/*     */     private final int waiting;
/*     */     private final int highWaterMark;
/*     */     private final int waitCount;
/*     */     private final int hitCount;
/*     */     
/*     */     protected Status(String name, int minSize, int maxSize, int free, int busy, int waiting, int highWaterMark, int waitCount, int hitCount) {
/* 841 */       this.name = name;
/* 842 */       this.minSize = minSize;
/* 843 */       this.maxSize = maxSize;
/* 844 */       this.free = free;
/* 845 */       this.busy = busy;
/* 846 */       this.waiting = waiting;
/* 847 */       this.highWaterMark = highWaterMark;
/* 848 */       this.waitCount = waitCount;
/* 849 */       this.hitCount = hitCount;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 853 */       return "min:" + this.minSize + " max:" + this.maxSize + " free:" + this.free + " busy:" + this.busy + " waiting:" + this.waiting + " highWaterMark:" + this.highWaterMark + " waitCount:" + this.waitCount + " hitCount:" + this.hitCount;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName() {
/* 861 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMinSize() {
/* 868 */       return this.minSize;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMaxSize() {
/* 875 */       return this.maxSize;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getFree() {
/* 882 */       return this.free;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getBusy() {
/* 889 */       return this.busy;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getWaiting() {
/* 896 */       return this.waiting;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getHighWaterMark() {
/* 903 */       return this.highWaterMark;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getWaitCount() {
/* 910 */       return this.waitCount;
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
/*     */     public int getHitCount() {
/* 922 */       return this.hitCount;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourcePool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */