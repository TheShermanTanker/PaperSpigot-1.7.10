/*     */ package com.avaje.ebeaninternal.server.lib.thread;
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
/*     */ public class Work
/*     */ {
/*     */   private Runnable runnable;
/*     */   private long exitQueueTime;
/*     */   private long enterQueueTime;
/*     */   private long startTime;
/*     */   
/*     */   public Work(Runnable runnable) {
/*  34 */     this.runnable = runnable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Runnable getRunnable() {
/*  41 */     return this.runnable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getStartTime() {
/*  48 */     return this.startTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStartTime(long startTime) {
/*  55 */     this.startTime = startTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getEnterQueueTime() {
/*  62 */     return this.enterQueueTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnterQueueTime(long enterQueueTime) {
/*  69 */     this.enterQueueTime = enterQueueTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getExitQueueTime() {
/*  76 */     return this.exitQueueTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExitQueueTime(long exitQueueTime) {
/*  83 */     this.exitQueueTime = exitQueueTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  90 */     return getDescription();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("Work[");
/* 100 */     if (this.runnable != null) {
/* 101 */       sb.append(this.runnable.toString());
/*     */     }
/* 103 */     sb.append("]");
/* 104 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\thread\Work.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */