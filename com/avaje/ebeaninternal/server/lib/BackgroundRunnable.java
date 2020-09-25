/*     */ package com.avaje.ebeaninternal.server.lib;
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
/*     */ public class BackgroundRunnable
/*     */ {
/*     */   Runnable runnable;
/*     */   int freqInSecs;
/*  39 */   int runCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   long totalRunTime = 0L;
/*     */ 
/*     */ 
/*     */   
/*     */   long startTimeTemp;
/*     */ 
/*     */   
/*     */   long startAfter;
/*     */ 
/*     */   
/*     */   boolean isActive = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public BackgroundRunnable(Runnable runnable, int freqInSecs) {
/*  59 */     this(runnable, freqInSecs, System.currentTimeMillis() + (1000 * (freqInSecs + 10)));
/*     */   }
/*     */   
/*     */   public BackgroundRunnable(Runnable runnable, int freqInSecs, long startAfter) {
/*  63 */     this.runnable = runnable;
/*  64 */     this.freqInSecs = freqInSecs;
/*  65 */     this.startAfter = startAfter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean runNow(long now) {
/*  75 */     return (now > this.startAfter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/*  82 */     return this.isActive;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActive(boolean isActive) {
/*  90 */     this.isActive = isActive;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runStart() {
/*  97 */     this.startTimeTemp = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runEnd() {
/* 104 */     this.runCount++;
/* 105 */     long exeTime = System.currentTimeMillis() - this.startTimeTemp;
/* 106 */     this.totalRunTime += exeTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRunCount() {
/* 113 */     return this.runCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getAverageRunTime() {
/* 120 */     if (this.runCount == 0) {
/* 121 */       return 0L;
/*     */     }
/* 123 */     return this.totalRunTime / this.runCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFreqInSecs() {
/* 130 */     return this.freqInSecs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFreqInSecs(int freqInSecs) {
/* 137 */     this.freqInSecs = freqInSecs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Runnable getRunnable() {
/* 144 */     return this.runnable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRunnable(Runnable runnable) {
/* 151 */     this.runnable = runnable;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 155 */     StringBuffer sb = new StringBuffer();
/* 156 */     sb.append("[");
/* 157 */     sb.append(this.runnable.getClass().getName());
/* 158 */     sb.append(" freq:").append(this.freqInSecs);
/* 159 */     sb.append(" count:").append(getRunCount());
/* 160 */     sb.append(" avgTime:").append(getAverageRunTime());
/* 161 */     sb.append("]");
/* 162 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\BackgroundRunnable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */