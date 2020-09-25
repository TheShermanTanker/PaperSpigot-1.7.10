/*     */ package com.avaje.ebeaninternal.server.autofetch;
/*     */ 
/*     */ import com.avaje.ebean.bean.CallStack;
/*     */ import com.avaje.ebean.bean.NodeUsageCollector;
/*     */ import com.avaje.ebean.bean.ObjectGraphNode;
/*     */ import com.avaje.ebean.bean.ObjectGraphOrigin;
/*     */ import com.avaje.ebean.config.AutofetchConfig;
/*     */ import com.avaje.ebean.config.AutofetchMode;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultAutoFetchManager
/*     */   implements AutoFetchManager, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6826119882781771722L;
/*  35 */   private final String statisticsMonitor = new String();
/*     */ 
/*     */ 
/*     */   
/*     */   private final String fileName;
/*     */ 
/*     */   
/*  42 */   private Map<String, Statistics> statisticsMap = new ConcurrentHashMap<String, Statistics>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private Map<String, TunedQueryInfo> tunedQueryInfoMap = new ConcurrentHashMap<String, TunedQueryInfo>();
/*     */   
/*  49 */   private transient long defaultGarbageCollectionWait = 100L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient int tunedQueryCount;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private transient double profilingRate = 0.1D;
/*     */   
/*  62 */   private transient int profilingBase = 10;
/*     */   
/*  64 */   private transient int profilingMin = 1;
/*     */ 
/*     */   
/*     */   private transient boolean profiling;
/*     */ 
/*     */   
/*     */   private transient boolean queryTuning;
/*     */ 
/*     */   
/*     */   private transient boolean queryTuningAddVersion;
/*     */ 
/*     */   
/*     */   private transient AutofetchMode mode;
/*     */ 
/*     */   
/*     */   private transient boolean useFileLogging;
/*     */   
/*     */   private transient SpiEbeanServer server;
/*     */   
/*     */   private transient DefaultAutoFetchManagerLogging logging;
/*     */ 
/*     */   
/*     */   public DefaultAutoFetchManager(String fileName) {
/*  87 */     this.fileName = fileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOwner(SpiEbeanServer server, ServerConfig serverConfig) {
/*  94 */     this.server = server;
/*  95 */     this.logging = new DefaultAutoFetchManagerLogging(serverConfig, this);
/*     */     
/*  97 */     AutofetchConfig autofetchConfig = serverConfig.getAutofetchConfig();
/*     */     
/*  99 */     this.useFileLogging = autofetchConfig.isUseFileLogging();
/* 100 */     this.queryTuning = autofetchConfig.isQueryTuning();
/* 101 */     this.queryTuningAddVersion = autofetchConfig.isQueryTuningAddVersion();
/* 102 */     this.profiling = autofetchConfig.isProfiling();
/* 103 */     this.profilingMin = autofetchConfig.getProfilingMin();
/* 104 */     this.profilingBase = autofetchConfig.getProfilingBase();
/*     */     
/* 106 */     setProfilingRate(autofetchConfig.getProfilingRate());
/*     */ 
/*     */     
/* 109 */     this.defaultGarbageCollectionWait = autofetchConfig.getGarbageCollectionWait();
/*     */ 
/*     */ 
/*     */     
/* 113 */     this.mode = autofetchConfig.getMode();
/*     */     
/* 115 */     if (this.profiling || this.queryTuning) {
/*     */       
/* 117 */       String msg = "AutoFetch queryTuning[" + this.queryTuning + "] profiling[" + this.profiling + "] mode[" + this.mode + "]  profiling rate[" + this.profilingRate + "] min[" + this.profilingMin + "] base[" + this.profilingBase + "]";
/*     */ 
/*     */       
/* 120 */       this.logging.logToJavaLogger(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearQueryStatistics() {
/* 127 */     this.server.clearQueryStatistics();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalTunedQueryCount() {
/* 134 */     return this.tunedQueryCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalTunedQuerySize() {
/* 141 */     return this.tunedQueryInfoMap.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalProfileSize() {
/* 148 */     return this.statisticsMap.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int clearTunedQueryInfo() {
/* 154 */     this.tunedQueryCount = 0;
/*     */ 
/*     */     
/* 157 */     int size = this.tunedQueryInfoMap.size();
/* 158 */     this.tunedQueryInfoMap.clear();
/* 159 */     return size;
/*     */   }
/*     */   
/*     */   public int clearProfilingInfo() {
/* 163 */     int size = this.statisticsMap.size();
/* 164 */     this.statisticsMap.clear();
/* 165 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize() {
/* 171 */     File autoFetchFile = new File(this.fileName);
/*     */     
/*     */     try {
/* 174 */       FileOutputStream fout = new FileOutputStream(autoFetchFile);
/*     */       
/* 176 */       ObjectOutputStream oout = new ObjectOutputStream(fout);
/* 177 */       oout.writeObject(this);
/* 178 */       oout.flush();
/* 179 */       oout.close();
/*     */     }
/* 181 */     catch (Exception e) {
/* 182 */       String msg = "Error serializing autofetch file";
/* 183 */       this.logging.logError(Level.SEVERE, msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TunedQueryInfo getTunedQueryInfo(String originKey) {
/* 191 */     return this.tunedQueryInfoMap.get(originKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Statistics getStatistics(String originKey) {
/* 198 */     return this.statisticsMap.get(originKey);
/*     */   }
/*     */   
/*     */   public Iterator<TunedQueryInfo> iterateTunedQueryInfo() {
/* 202 */     return this.tunedQueryInfoMap.values().iterator();
/*     */   }
/*     */   
/*     */   public Iterator<Statistics> iterateStatistics() {
/* 206 */     return this.statisticsMap.values().iterator();
/*     */   }
/*     */   
/*     */   public boolean isProfiling() {
/* 210 */     return this.profiling;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProfiling(boolean profiling) {
/* 219 */     this.profiling = profiling;
/*     */   }
/*     */   
/*     */   public boolean isQueryTuning() {
/* 223 */     return this.queryTuning;
/*     */   }
/*     */   
/*     */   public void setQueryTuning(boolean queryTuning) {
/* 227 */     this.queryTuning = queryTuning;
/*     */   }
/*     */   
/*     */   public double getProfilingRate() {
/* 231 */     return this.profilingRate;
/*     */   }
/*     */   
/*     */   public AutofetchMode getMode() {
/* 235 */     return this.mode;
/*     */   }
/*     */   
/*     */   public void setMode(AutofetchMode mode) {
/* 239 */     this.mode = mode;
/*     */   }
/*     */   
/*     */   public void setProfilingRate(double rate) {
/* 243 */     if (rate < 0.0D) {
/* 244 */       rate = 0.0D;
/* 245 */     } else if (rate > 1.0D) {
/* 246 */       rate = 1.0D;
/*     */     } 
/* 248 */     this.profilingRate = rate;
/*     */   }
/*     */   
/*     */   public int getProfilingBase() {
/* 252 */     return this.profilingBase;
/*     */   }
/*     */   
/*     */   public void setProfilingBase(int profilingBase) {
/* 256 */     this.profilingBase = profilingBase;
/*     */   }
/*     */   
/*     */   public int getProfilingMin() {
/* 260 */     return this.profilingMin;
/*     */   }
/*     */   
/*     */   public void setProfilingMin(int profilingMin) {
/* 264 */     this.profilingMin = profilingMin;
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
/*     */   public void shutdown() {
/* 276 */     if (this.useFileLogging) {
/* 277 */       collectUsageViaGC(-1L);
/* 278 */       serialize();
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
/*     */ 
/*     */   
/*     */   public String collectUsageViaGC(long waitMillis) {
/* 299 */     System.gc();
/*     */     try {
/* 301 */       if (waitMillis < 0L) {
/* 302 */         waitMillis = this.defaultGarbageCollectionWait;
/*     */       }
/* 304 */       Thread.sleep(waitMillis);
/* 305 */     } catch (InterruptedException e) {
/* 306 */       String msg = "Error while sleeping after System.gc() request.";
/* 307 */       this.logging.logError(Level.SEVERE, msg, e);
/* 308 */       return msg;
/*     */     } 
/* 310 */     return updateTunedQueryInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String updateTunedQueryInfo() {
/* 318 */     if (!this.profiling)
/*     */     {
/*     */       
/* 321 */       return "Not profiling";
/*     */     }
/*     */     
/* 324 */     synchronized (this.statisticsMonitor) {
/*     */       
/* 326 */       Counters counters = new Counters();
/*     */       
/* 328 */       Iterator<Statistics> it = this.statisticsMap.values().iterator();
/* 329 */       while (it.hasNext()) {
/* 330 */         Statistics queryPointStatistics = it.next();
/* 331 */         if (!queryPointStatistics.hasUsage()) {
/*     */           
/* 333 */           counters.incrementNoUsage(); continue;
/*     */         } 
/* 335 */         updateTunedQueryFromUsage(counters, queryPointStatistics);
/*     */       } 
/*     */ 
/*     */       
/* 339 */       String summaryInfo = counters.toString();
/*     */       
/* 341 */       if (counters.isInteresting())
/*     */       {
/* 343 */         this.logging.logSummary(summaryInfo);
/*     */       }
/*     */       
/* 346 */       return summaryInfo;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class Counters { int newPlan;
/*     */     int modified;
/*     */     int unchanged;
/*     */     int noUsage;
/*     */     
/*     */     private Counters() {}
/*     */     
/*     */     void incrementNoUsage() {
/* 358 */       this.noUsage++;
/*     */     }
/*     */     void incrementNew() {
/* 361 */       this.newPlan++;
/*     */     }
/*     */     void incrementModified() {
/* 364 */       this.modified++;
/*     */     }
/*     */     void incrementUnchanged() {
/* 367 */       this.unchanged++;
/*     */     }
/*     */     boolean isInteresting() {
/* 370 */       return (this.newPlan > 0 || this.modified > 0);
/*     */     }
/*     */     public String toString() {
/* 373 */       return "new[" + this.newPlan + "] modified[" + this.modified + "] unchanged[" + this.unchanged + "] nousage[" + this.noUsage + "]";
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateTunedQueryFromUsage(Counters counters, Statistics statistics) {
/* 379 */     ObjectGraphOrigin queryPoint = statistics.getOrigin();
/* 380 */     String beanType = queryPoint.getBeanType();
/*     */     
/*     */     try {
/* 383 */       Class<?> beanClass = ClassUtil.forName(beanType, getClass());
/* 384 */       BeanDescriptor<?> beanDescriptor = this.server.getBeanDescriptor(beanClass);
/* 385 */       if (beanDescriptor != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 391 */         OrmQueryDetail newFetchDetail = statistics.buildTunedFetch(beanDescriptor);
/*     */ 
/*     */         
/* 394 */         TunedQueryInfo currentFetch = this.tunedQueryInfoMap.get(queryPoint.getKey());
/*     */         
/* 396 */         if (currentFetch == null) {
/*     */           
/* 398 */           counters.incrementNew();
/*     */           
/* 400 */           currentFetch = statistics.createTunedFetch(newFetchDetail);
/* 401 */           this.logging.logNew(currentFetch);
/* 402 */           this.tunedQueryInfoMap.put(queryPoint.getKey(), currentFetch);
/*     */         }
/* 404 */         else if (!currentFetch.isSame(newFetchDetail)) {
/*     */           
/* 406 */           counters.incrementModified();
/*     */           
/* 408 */           this.logging.logChanged(currentFetch, newFetchDetail);
/* 409 */           currentFetch.setTunedDetail(newFetchDetail);
/*     */         }
/*     */         else {
/*     */           
/* 413 */           counters.incrementUnchanged();
/*     */         } 
/*     */         
/* 416 */         currentFetch.setProfileCount(statistics.getCounter());
/*     */       }
/*     */     
/* 419 */     } catch (ClassNotFoundException e) {
/*     */       
/* 421 */       String msg = e.toString() + " updating autoFetch tuned query for " + beanType + ". It isLikely this bean has been renamed or moved";
/*     */       
/* 423 */       this.logging.logError(Level.INFO, msg, null);
/* 424 */       this.statisticsMap.remove(statistics.getOrigin().getKey());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean useAutoFetch(SpiQuery<?> query) {
/* 433 */     if (query.isLoadBeanCache())
/*     */     {
/*     */       
/* 436 */       return false;
/*     */     }
/*     */     
/* 439 */     Boolean autoFetch = query.isAutofetch();
/* 440 */     if (autoFetch != null)
/*     */     {
/* 442 */       return autoFetch.booleanValue();
/*     */     }
/*     */ 
/*     */     
/* 446 */     switch (this.mode) {
/*     */       case DEFAULT_ON:
/* 448 */         return true;
/*     */       
/*     */       case DEFAULT_OFF:
/* 451 */         return false;
/*     */       
/*     */       case DEFAULT_ONIFEMPTY:
/* 454 */         return query.isDetailEmpty();
/*     */     } 
/*     */     
/* 457 */     throw new PersistenceException("Invalid autoFetchMode " + this.mode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tuneQuery(SpiQuery<?> query) {
/* 467 */     if (!this.queryTuning && !this.profiling) {
/* 468 */       return false;
/*     */     }
/*     */     
/* 471 */     if (!useAutoFetch(query))
/*     */     {
/* 473 */       return false;
/*     */     }
/*     */     
/* 476 */     ObjectGraphNode parentAutoFetchNode = query.getParentNode();
/* 477 */     if (parentAutoFetchNode != null) {
/*     */ 
/*     */       
/* 480 */       query.setAutoFetchManager(this);
/* 481 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 485 */     CallStack stack = this.server.createCallStack();
/* 486 */     ObjectGraphNode origin = query.setOrigin(stack);
/*     */ 
/*     */     
/* 489 */     TunedQueryInfo tunedFetch = this.tunedQueryInfoMap.get(origin.getOriginQueryPoint().getKey());
/*     */ 
/*     */     
/* 492 */     int profileCount = (tunedFetch == null) ? 0 : tunedFetch.getProfileCount();
/*     */     
/* 494 */     if (this.profiling)
/*     */     {
/* 496 */       if (tunedFetch == null) {
/* 497 */         query.setAutoFetchManager(this);
/*     */       }
/* 499 */       else if (profileCount < this.profilingBase) {
/* 500 */         query.setAutoFetchManager(this);
/*     */       }
/* 502 */       else if (tunedFetch.isPercentageProfile(this.profilingRate)) {
/* 503 */         query.setAutoFetchManager(this);
/*     */       } 
/*     */     }
/*     */     
/* 507 */     if (this.queryTuning && 
/* 508 */       tunedFetch != null && profileCount >= this.profilingMin) {
/*     */ 
/*     */       
/* 511 */       if (tunedFetch.autoFetchTune(query))
/*     */       {
/*     */ 
/*     */         
/* 515 */         this.tunedQueryCount++;
/*     */       }
/* 517 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 521 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void collectQueryInfo(ObjectGraphNode node, int beans, int micros) {
/* 531 */     if (node != null) {
/* 532 */       ObjectGraphOrigin origin = node.getOriginQueryPoint();
/* 533 */       if (origin != null) {
/* 534 */         Statistics stats = getQueryPointStats(origin);
/* 535 */         stats.collectQueryInfo(node, beans, micros);
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
/*     */   public void collectNodeUsage(NodeUsageCollector usageCollector) {
/* 549 */     ObjectGraphOrigin origin = usageCollector.getNode().getOriginQueryPoint();
/*     */     
/* 551 */     Statistics stats = getQueryPointStats(origin);
/*     */     
/* 553 */     if (this.logging.isTraceUsageCollection()) {
/* 554 */       System.out.println("... NodeUsageCollector " + usageCollector);
/*     */     }
/*     */     
/* 557 */     stats.collectUsageInfo(usageCollector);
/*     */     
/* 559 */     if (this.logging.isTraceUsageCollection()) {
/* 560 */       System.out.println("stats\n" + stats);
/*     */     }
/*     */   }
/*     */   
/*     */   private Statistics getQueryPointStats(ObjectGraphOrigin originQueryPoint) {
/* 565 */     synchronized (this.statisticsMonitor) {
/* 566 */       Statistics stats = this.statisticsMap.get(originQueryPoint.getKey());
/* 567 */       if (stats == null) {
/* 568 */         stats = new Statistics(originQueryPoint, this.queryTuningAddVersion);
/* 569 */         this.statisticsMap.put(originQueryPoint.getKey(), stats);
/*     */       } 
/* 571 */       return stats;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 576 */     synchronized (this.statisticsMonitor) {
/* 577 */       return this.statisticsMap.values().toString();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\autofetch\DefaultAutoFetchManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */