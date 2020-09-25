/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.meta.MetaQueryStatistic;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CQueryStats
/*    */ {
/*    */   private final int count;
/*    */   private final int totalLoadedBeanCount;
/*    */   private final int totalTimeMicros;
/*    */   private final long startCollecting;
/*    */   private final long lastQueryTime;
/*    */   
/*    */   public CQueryStats() {
/* 22 */     this.count = 0;
/* 23 */     this.totalLoadedBeanCount = 0;
/* 24 */     this.totalTimeMicros = 0;
/* 25 */     this.startCollecting = System.currentTimeMillis();
/* 26 */     this.lastQueryTime = 0L;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CQueryStats(CQueryStats previous, int loadedBeanCount, int timeMicros) {
/* 33 */     previous.count++;
/* 34 */     previous.totalLoadedBeanCount += loadedBeanCount;
/* 35 */     previous.totalTimeMicros += timeMicros;
/* 36 */     this.startCollecting = previous.startCollecting;
/* 37 */     this.lastQueryTime = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public CQueryStats add(int loadedBeanCount, int timeMicros) {
/* 41 */     return new CQueryStats(this, loadedBeanCount, timeMicros);
/*    */   }
/*    */   
/*    */   public int getCount() {
/* 45 */     return this.count;
/*    */   }
/*    */   
/*    */   public int getAverageTimeMicros() {
/* 49 */     if (this.count == 0) {
/* 50 */       return 0;
/*    */     }
/* 52 */     return this.totalTimeMicros / this.count;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTotalLoadedBeanCount() {
/* 57 */     return this.totalLoadedBeanCount;
/*    */   }
/*    */   
/*    */   public int getTotalTimeMicros() {
/* 61 */     return this.totalTimeMicros;
/*    */   }
/*    */   
/*    */   public long getStartCollecting() {
/* 65 */     return this.startCollecting;
/*    */   }
/*    */   
/*    */   public long getLastQueryTime() {
/* 69 */     return this.lastQueryTime;
/*    */   }
/*    */   
/*    */   public MetaQueryStatistic createMetaQueryStatistic(String beanName, CQueryPlan qp) {
/* 73 */     return new MetaQueryStatistic(qp.isAutofetchTuned(), beanName, qp.getHash(), qp.getSql(), this.count, this.totalLoadedBeanCount, this.totalTimeMicros, this.startCollecting, this.lastQueryTime);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\CQueryStats.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */