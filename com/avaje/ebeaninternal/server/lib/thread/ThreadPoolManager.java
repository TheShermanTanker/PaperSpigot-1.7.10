/*     */ package com.avaje.ebeaninternal.server.lib.thread;
/*     */ 
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.server.lib.BackgroundThread;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadPoolManager
/*     */   implements Runnable
/*     */ {
/*     */   private static final class Single
/*     */   {
/*  32 */     private static final ThreadPoolManager me = new ThreadPoolManager();
/*     */   }
/*     */   
/*  35 */   private static int debugLevel = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isShuttingDown = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private ConcurrentHashMap<String, ThreadPool> threadPoolCache = new ConcurrentHashMap<String, ThreadPool>();
/*     */ 
/*     */ 
/*     */   
/*     */   private long defaultIdleTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ThreadPoolManager() {
/*  55 */     initialise();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initialise() {
/*  60 */     debugLevel = GlobalProperties.getInt("threadpool.debugLevel", 0);
/*     */     
/*  62 */     this.defaultIdleTime = (1000 * GlobalProperties.getInt("threadpool.idletime", 60));
/*     */     
/*  64 */     int freqIsSecs = GlobalProperties.getInt("threadpool.sleeptime", 30);
/*     */     
/*  66 */     BackgroundThread.add(freqIsSecs, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDebugLevel(int level) {
/*  73 */     debugLevel = level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDebugLevel() {
/*  80 */     return debugLevel;
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
/*     */   public void run() {
/*  94 */     if (!this.isShuttingDown) {
/*  95 */       maintainPoolSize();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ThreadPool getThreadPool(String poolName) {
/* 103 */     return Single.me.getPool(poolName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ThreadPool getPool(String poolName) {
/* 111 */     synchronized (this) {
/* 112 */       ThreadPool threadPool = this.threadPoolCache.get(poolName);
/* 113 */       if (threadPool == null) {
/* 114 */         threadPool = createThreadPool(poolName);
/* 115 */         this.threadPoolCache.put(poolName, threadPool);
/*     */       } 
/* 117 */       return threadPool;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator<ThreadPool> pools() {
/* 128 */     return Single.me.threadPoolCache.values().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void maintainPoolSize() {
/* 137 */     if (this.isShuttingDown) {
/*     */       return;
/*     */     }
/* 140 */     synchronized (this) {
/*     */       
/* 142 */       Iterator<ThreadPool> e = pools();
/* 143 */       while (e.hasNext()) {
/* 144 */         ThreadPool pool = e.next();
/* 145 */         pool.maintainPoolSize();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void shutdown() {
/* 155 */     Single.me.shutdownPools();
/*     */   }
/*     */   
/*     */   private void shutdownPools() {
/* 159 */     synchronized (this) {
/* 160 */       this.isShuttingDown = true;
/* 161 */       Iterator<ThreadPool> i = pools();
/* 162 */       while (i.hasNext()) {
/* 163 */         ThreadPool pool = i.next();
/* 164 */         pool.shutdown();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ThreadPool createThreadPool(String poolName) {
/* 171 */     int min = GlobalProperties.getInt("threadpool." + poolName + ".min", 0);
/* 172 */     int max = GlobalProperties.getInt("threadpool." + poolName + ".max", 100);
/*     */     
/* 174 */     long idle = (1000 * GlobalProperties.getInt("threadpool." + poolName + ".idletime", -1));
/* 175 */     if (idle < 0L) {
/* 176 */       idle = this.defaultIdleTime;
/*     */     }
/*     */     
/* 179 */     boolean isDaemon = true;
/* 180 */     Integer priority = null;
/* 181 */     String threadPriority = GlobalProperties.get("threadpool." + poolName + ".priority", null);
/* 182 */     if (threadPriority != null) {
/* 183 */       priority = new Integer(threadPriority);
/*     */     }
/*     */     
/* 186 */     ThreadPool newPool = new ThreadPool(poolName, isDaemon, priority);
/* 187 */     newPool.setMaxSize(max);
/* 188 */     newPool.setMinSize(min);
/* 189 */     newPool.setMaxIdleTime(idle);
/*     */     
/* 191 */     return newPool;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\thread\ThreadPoolManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */