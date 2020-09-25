/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.Condition;
/*     */ import java.util.concurrent.locks.ReentrantLock;
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
/*     */ public class PooledConnectionQueue
/*     */ {
/*  37 */   private static final Logger logger = Logger.getLogger(PooledConnectionQueue.class.getName());
/*     */   
/*  39 */   private static final TimeUnit MILLIS_TIME_UNIT = TimeUnit.MILLISECONDS;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */ 
/*     */   
/*     */   private final DataSourcePool pool;
/*     */ 
/*     */ 
/*     */   
/*     */   private final FreeConnectionBuffer freeList;
/*     */ 
/*     */ 
/*     */   
/*     */   private final BusyConnectionBuffer busyList;
/*     */ 
/*     */ 
/*     */   
/*     */   private final ReentrantLock lock;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Condition notEmpty;
/*     */ 
/*     */ 
/*     */   
/*     */   private int connectionId;
/*     */ 
/*     */ 
/*     */   
/*     */   private long waitTimeoutMillis;
/*     */ 
/*     */ 
/*     */   
/*     */   private long leakTimeMinutes;
/*     */ 
/*     */ 
/*     */   
/*     */   private int warningSize;
/*     */ 
/*     */   
/*     */   private int maxSize;
/*     */ 
/*     */   
/*     */   private int minSize;
/*     */ 
/*     */   
/*     */   private int waitingThreads;
/*     */ 
/*     */   
/*     */   private int waitCount;
/*     */ 
/*     */   
/*     */   private int hitCount;
/*     */ 
/*     */   
/*     */   private int highWaterMark;
/*     */ 
/*     */   
/*     */   private long lastResetTime;
/*     */ 
/*     */   
/*     */   private boolean doingShutdown;
/*     */ 
/*     */ 
/*     */   
/*     */   public PooledConnectionQueue(DataSourcePool pool) {
/* 108 */     this.pool = pool;
/* 109 */     this.name = pool.getName();
/* 110 */     this.minSize = pool.getMinSize();
/* 111 */     this.maxSize = pool.getMaxSize();
/*     */     
/* 113 */     this.warningSize = pool.getWarningSize();
/* 114 */     this.waitTimeoutMillis = pool.getWaitTimeoutMillis();
/* 115 */     this.leakTimeMinutes = pool.getLeakTimeMinutes();
/*     */     
/* 117 */     this.busyList = new BusyConnectionBuffer(50, 20);
/* 118 */     this.freeList = new FreeConnectionBuffer(this.maxSize);
/*     */     
/* 120 */     this.lock = new ReentrantLock(true);
/* 121 */     this.notEmpty = this.lock.newCondition();
/*     */   }
/*     */   
/*     */   private DataSourcePool.Status createStatus() {
/* 125 */     return new DataSourcePool.Status(this.name, this.minSize, this.maxSize, this.freeList.size(), this.busyList.size(), this.waitingThreads, this.highWaterMark, this.waitCount, this.hitCount);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 129 */     ReentrantLock lock = this.lock;
/* 130 */     lock.lock();
/*     */     try {
/* 132 */       return createStatus().toString();
/*     */     } finally {
/* 134 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public DataSourcePool.Status getStatus(boolean reset) {
/* 139 */     ReentrantLock lock = this.lock;
/* 140 */     lock.lock();
/*     */     try {
/* 142 */       DataSourcePool.Status s = createStatus();
/* 143 */       if (reset) {
/* 144 */         this.highWaterMark = this.busyList.size();
/* 145 */         this.hitCount = 0;
/* 146 */         this.waitCount = 0;
/*     */       } 
/* 148 */       return s;
/*     */     } finally {
/* 150 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMinSize(int minSize) {
/* 155 */     ReentrantLock lock = this.lock;
/* 156 */     lock.lock();
/*     */     try {
/* 158 */       if (minSize > this.maxSize) {
/* 159 */         throw new IllegalArgumentException("minSize " + minSize + " > maxSize " + this.maxSize);
/*     */       }
/* 161 */       this.minSize = minSize;
/*     */     } finally {
/* 163 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMaxSize(int maxSize) {
/* 168 */     ReentrantLock lock = this.lock;
/* 169 */     lock.lock();
/*     */     try {
/* 171 */       if (maxSize < this.minSize) {
/* 172 */         throw new IllegalArgumentException("maxSize " + maxSize + " < minSize " + this.minSize);
/*     */       }
/* 174 */       this.freeList.setCapacity(maxSize);
/* 175 */       this.maxSize = maxSize;
/*     */     } finally {
/* 177 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setWarningSize(int warningSize) {
/* 182 */     ReentrantLock lock = this.lock;
/* 183 */     lock.lock();
/*     */     try {
/* 185 */       if (warningSize > this.maxSize) {
/* 186 */         throw new IllegalArgumentException("warningSize " + warningSize + " > maxSize " + this.maxSize);
/*     */       }
/* 188 */       this.warningSize = warningSize;
/*     */     } finally {
/* 190 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private int totalConnections() {
/* 195 */     return this.freeList.size() + this.busyList.size();
/*     */   }
/*     */   
/*     */   public void ensureMinimumConnections() throws SQLException {
/* 199 */     ReentrantLock lock = this.lock;
/* 200 */     lock.lock();
/*     */     try {
/* 202 */       int add = this.minSize - totalConnections();
/* 203 */       if (add > 0) {
/* 204 */         for (int i = 0; i < add; i++) {
/* 205 */           PooledConnection c = this.pool.createConnectionForQueue(this.connectionId++);
/* 206 */           this.freeList.add(c);
/*     */         } 
/* 208 */         this.notEmpty.signal();
/*     */       } 
/*     */     } finally {
/*     */       
/* 212 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void returnPooledConnection(PooledConnection c) {
/* 221 */     ReentrantLock lock = this.lock;
/* 222 */     lock.lock();
/*     */     try {
/* 224 */       if (!this.busyList.remove(c)) {
/* 225 */         logger.log(Level.SEVERE, "Connection [" + c + "] not found in BusyList? ");
/*     */       }
/* 227 */       if (c.getCreationTime() <= this.lastResetTime) {
/* 228 */         c.closeConnectionFully(false);
/*     */       } else {
/* 230 */         this.freeList.add(c);
/* 231 */         this.notEmpty.signal();
/*     */       } 
/*     */     } finally {
/* 234 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private PooledConnection extractFromFreeList() {
/* 239 */     PooledConnection c = this.freeList.remove();
/* 240 */     registerBusyConnection(c);
/* 241 */     return c;
/*     */   }
/*     */ 
/*     */   
/*     */   public PooledConnection getPooledConnection() throws SQLException {
/*     */     try {
/* 247 */       PooledConnection pc = _getPooledConnection();
/* 248 */       pc.resetForUse();
/* 249 */       return pc;
/*     */     }
/* 251 */     catch (InterruptedException e) {
/* 252 */       String msg = "Interrupted getting connection from pool " + e;
/* 253 */       throw new SQLException(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int registerBusyConnection(PooledConnection c) {
/* 261 */     int busySize = this.busyList.add(c);
/* 262 */     if (busySize > this.highWaterMark) {
/* 263 */       this.highWaterMark = busySize;
/*     */     }
/* 265 */     return busySize;
/*     */   }
/*     */   
/*     */   private PooledConnection _getPooledConnection() throws InterruptedException, SQLException {
/* 269 */     ReentrantLock lock = this.lock;
/* 270 */     lock.lockInterruptibly();
/*     */     try {
/* 272 */       if (this.doingShutdown) {
/* 273 */         throw new SQLException("Trying to access the Connection Pool when it is shutting down");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 278 */       this.hitCount++;
/*     */ 
/*     */       
/* 281 */       if (this.waitingThreads == 0)
/*     */       {
/* 283 */         int freeSize = this.freeList.size();
/* 284 */         if (freeSize > 0)
/*     */         {
/* 286 */           return extractFromFreeList();
/*     */         }
/*     */         
/* 289 */         if (this.busyList.size() < this.maxSize)
/*     */         {
/* 291 */           PooledConnection c = this.pool.createConnectionForQueue(this.connectionId++);
/* 292 */           int busySize = registerBusyConnection(c);
/*     */           
/* 294 */           String msg = "DataSourcePool [" + this.name + "] grow; id[" + c.getName() + "] busy[" + busySize + "] max[" + this.maxSize + "]";
/* 295 */           logger.info(msg);
/*     */           
/* 297 */           checkForWarningSize();
/* 298 */           return c;
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 313 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PooledConnection _getPooledConnectionWaitLoop() throws SQLException, InterruptedException {
/* 322 */     long nanos = MILLIS_TIME_UNIT.toNanos(this.waitTimeoutMillis);
/*     */     
/*     */     while (true) {
/* 325 */       if (nanos <= 0L) {
/* 326 */         String msg = "Unsuccessfully waited [" + this.waitTimeoutMillis + "] millis for a connection to be returned." + " No connections are free. You need to Increase the max connections of [" + this.maxSize + "]" + " or look for a connection pool leak using datasource.xxx.capturestacktrace=true";
/*     */ 
/*     */         
/* 329 */         if (this.pool.isCaptureStackTrace()) {
/* 330 */           dumpBusyConnectionInformation();
/*     */         }
/*     */         
/* 333 */         throw new SQLException(msg);
/*     */       } 
/*     */       
/*     */       try {
/* 337 */         nanos = this.notEmpty.awaitNanos(nanos);
/* 338 */         if (!this.freeList.isEmpty())
/*     */         {
/* 340 */           return extractFromFreeList();
/*     */         }
/* 342 */       } catch (InterruptedException ie) {
/* 343 */         this.notEmpty.signal();
/* 344 */         throw ie;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void shutdown() {
/* 350 */     ReentrantLock lock = this.lock;
/* 351 */     lock.lock();
/*     */     try {
/* 353 */       this.doingShutdown = true;
/* 354 */       DataSourcePool.Status status = createStatus();
/* 355 */       logger.info("DataSourcePool [" + this.name + "] shutdown: " + status);
/*     */       
/* 357 */       closeFreeConnections(true);
/*     */       
/* 359 */       if (!this.busyList.isEmpty()) {
/* 360 */         String msg = "A potential connection leak was detected.  Busy connections: " + this.busyList.size();
/* 361 */         logger.warning(msg);
/*     */         
/* 363 */         dumpBusyConnectionInformation();
/* 364 */         closeBusyConnections(0L);
/*     */       } 
/*     */     } finally {
/* 367 */       lock.unlock();
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
/*     */   public void reset(long leakTimeMinutes) {
/* 379 */     ReentrantLock lock = this.lock;
/* 380 */     lock.lock();
/*     */     try {
/* 382 */       DataSourcePool.Status status = createStatus();
/* 383 */       logger.info("Reseting DataSourcePool [" + this.name + "] " + status);
/* 384 */       this.lastResetTime = System.currentTimeMillis();
/*     */       
/* 386 */       closeFreeConnections(false);
/* 387 */       closeBusyConnections(leakTimeMinutes);
/*     */       
/* 389 */       String busyMsg = "Busy Connections:\r\n" + getBusyConnectionInformation();
/* 390 */       logger.info(busyMsg);
/*     */     } finally {
/*     */       
/* 393 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void trim(int maxInactiveTimeSecs) throws SQLException {
/* 398 */     ReentrantLock lock = this.lock;
/* 399 */     lock.lock();
/*     */     try {
/* 401 */       trimInactiveConnections(maxInactiveTimeSecs);
/* 402 */       ensureMinimumConnections();
/*     */     } finally {
/*     */       
/* 405 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int trimInactiveConnections(int maxInactiveTimeSecs) {
/* 414 */     int maxTrim = this.freeList.size() - this.minSize;
/* 415 */     if (maxTrim <= 0) {
/* 416 */       return 0;
/*     */     }
/*     */     
/* 419 */     int trimedCount = 0;
/* 420 */     long usedSince = System.currentTimeMillis() - (maxInactiveTimeSecs * 1000);
/*     */ 
/*     */     
/* 423 */     List<PooledConnection> freeListCopy = this.freeList.getShallowCopy();
/*     */     
/* 425 */     Iterator<PooledConnection> it = freeListCopy.iterator();
/* 426 */     while (it.hasNext()) {
/* 427 */       PooledConnection pc = it.next();
/* 428 */       if (pc.getLastUsedTime() < usedSince) {
/*     */         
/* 430 */         trimedCount++;
/* 431 */         it.remove();
/* 432 */         pc.closeConnectionFully(true);
/* 433 */         if (trimedCount >= maxTrim) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 439 */     if (trimedCount > 0) {
/*     */ 
/*     */       
/* 442 */       this.freeList.setShallowCopy(freeListCopy);
/*     */       
/* 444 */       String msg = "DataSourcePool [" + this.name + "] trimmed [" + trimedCount + "] inactive connections. New size[" + totalConnections() + "]";
/* 445 */       logger.info(msg);
/*     */     } 
/* 447 */     return trimedCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeFreeConnections(boolean logErrors) {
/* 454 */     ReentrantLock lock = this.lock;
/* 455 */     lock.lock();
/*     */     try {
/* 457 */       while (!this.freeList.isEmpty()) {
/* 458 */         PooledConnection c = this.freeList.remove();
/* 459 */         logger.info("PSTMT Statistics: " + c.getStatistics());
/* 460 */         c.closeConnectionFully(logErrors);
/*     */       } 
/*     */     } finally {
/* 463 */       lock.unlock();
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
/*     */   public void closeBusyConnections(long leakTimeMinutes) {
/* 481 */     ReentrantLock lock = this.lock;
/* 482 */     lock.lock();
/*     */     
/*     */     try {
/* 485 */       long olderThanTime = System.currentTimeMillis() - leakTimeMinutes * 60000L;
/*     */       
/* 487 */       List<PooledConnection> copy = this.busyList.getShallowCopy();
/* 488 */       for (int i = 0; i < copy.size(); i++) {
/* 489 */         PooledConnection pc = copy.get(i);
/* 490 */         if (!pc.isLongRunning() && pc.getLastUsedTime() <= olderThanTime) {
/*     */ 
/*     */ 
/*     */           
/* 494 */           this.busyList.remove(pc);
/* 495 */           closeBusyConnection(pc);
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 500 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void closeBusyConnection(PooledConnection pc) {
/*     */     try {
/* 506 */       String methodLine = pc.getCreatedByMethod();
/*     */       
/* 508 */       Date luDate = new Date();
/* 509 */       luDate.setTime(pc.getLastUsedTime());
/*     */       
/* 511 */       String msg = "DataSourcePool closing leaked connection?  name[" + pc.getName() + "] lastUsed[" + luDate + "] createdBy[" + methodLine + "] lastStmt[" + pc.getLastStatement() + "]";
/*     */ 
/*     */ 
/*     */       
/* 515 */       logger.warning(msg);
/* 516 */       logStackElement(pc, "Possible Leaked Connection: ");
/*     */       
/* 518 */       System.out.println("CLOSING BUSY CONNECTION ??? " + pc);
/* 519 */       pc.close();
/*     */     }
/* 521 */     catch (SQLException ex) {
/*     */       
/* 523 */       logger.log(Level.SEVERE, (String)null, ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void logStackElement(PooledConnection pc, String prefix) {
/* 528 */     StackTraceElement[] stackTrace = pc.getStackTrace();
/* 529 */     if (stackTrace != null) {
/* 530 */       String s = Arrays.toString((Object[])stackTrace);
/* 531 */       String msg = prefix + " name[" + pc.getName() + "] stackTrace: " + s;
/* 532 */       logger.warning(msg);
/*     */ 
/*     */       
/* 535 */       System.err.println(msg);
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
/*     */   private void checkForWarningSize() {
/* 552 */     int availableGrowth = this.maxSize - totalConnections();
/*     */     
/* 554 */     if (availableGrowth < this.warningSize) {
/*     */       
/* 556 */       closeBusyConnections(this.leakTimeMinutes);
/*     */       
/* 558 */       String msg = "DataSourcePool [" + this.name + "] is [" + availableGrowth + "] connections from its maximum size.";
/* 559 */       this.pool.notifyWarning(msg);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getBusyConnectionInformation() {
/* 564 */     return getBusyConnectionInformation(false);
/*     */   }
/*     */   
/*     */   public void dumpBusyConnectionInformation() {
/* 568 */     getBusyConnectionInformation(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getBusyConnectionInformation(boolean toLogger) {
/* 576 */     ReentrantLock lock = this.lock;
/* 577 */     lock.lock();
/*     */     
/*     */     try {
/* 580 */       if (toLogger) {
/* 581 */         logger.info("Dumping busy connections: (Use datasource.xxx.capturestacktrace=true  ... to get stackTraces)");
/*     */       }
/*     */       
/* 584 */       StringBuilder sb = new StringBuilder();
/*     */       
/* 586 */       List<PooledConnection> copy = this.busyList.getShallowCopy();
/* 587 */       for (int i = 0; i < copy.size(); i++) {
/* 588 */         PooledConnection pc = copy.get(i);
/* 589 */         if (toLogger) {
/* 590 */           logger.info(pc.getDescription());
/* 591 */           logStackElement(pc, "Busy Connection: ");
/*     */         } else {
/*     */           
/* 594 */           sb.append(pc.getDescription()).append("\r\n");
/*     */         } 
/*     */       } 
/*     */       
/* 598 */       return sb.toString();
/*     */     } finally {
/*     */       
/* 601 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\PooledConnectionQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */