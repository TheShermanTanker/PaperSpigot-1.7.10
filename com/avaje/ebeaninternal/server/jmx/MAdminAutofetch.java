/*     */ package com.avaje.ebeaninternal.server.jmx;
/*     */ 
/*     */ import com.avaje.ebean.AdminAutofetch;
/*     */ import com.avaje.ebean.config.AutofetchMode;
/*     */ import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
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
/*     */ public class MAdminAutofetch
/*     */   implements MAdminAutofetchMBean, AdminAutofetch
/*     */ {
/*  19 */   final Logger logger = Logger.getLogger(MAdminAutofetch.class.getName());
/*     */   
/*     */   final AutoFetchManager autoFetchManager;
/*     */   
/*     */   final String modeOptions;
/*     */   
/*     */   public MAdminAutofetch(AutoFetchManager autoFetchListener) {
/*  26 */     this.autoFetchManager = autoFetchListener;
/*  27 */     this.modeOptions = AutofetchMode.DEFAULT_OFF + ", " + AutofetchMode.DEFAULT_ON + ", " + AutofetchMode.DEFAULT_ONIFEMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isQueryTuning() {
/*  33 */     return this.autoFetchManager.isQueryTuning();
/*     */   }
/*     */   
/*     */   public void setQueryTuning(boolean enable) {
/*  37 */     this.autoFetchManager.setQueryTuning(enable);
/*     */   }
/*     */   
/*     */   public boolean isProfiling() {
/*  41 */     return this.autoFetchManager.isProfiling();
/*     */   }
/*     */   
/*     */   public void setProfiling(boolean enable) {
/*  45 */     this.autoFetchManager.setProfiling(enable);
/*     */   }
/*     */   
/*     */   public String getModeOptions() {
/*  49 */     return this.modeOptions;
/*     */   }
/*     */   
/*     */   public String getMode() {
/*  53 */     return this.autoFetchManager.getMode().name();
/*     */   }
/*     */   
/*     */   public void setMode(String implicitMode) {
/*     */     try {
/*  58 */       AutofetchMode mode = AutofetchMode.valueOf(implicitMode);
/*  59 */       this.autoFetchManager.setMode(mode);
/*  60 */     } catch (Exception e) {
/*  61 */       this.logger.info("Invalid implicit mode attempted " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public String collectUsageViaGC() {
/*  66 */     return this.autoFetchManager.collectUsageViaGC(-1L);
/*     */   }
/*     */   
/*     */   public double getProfilingRate() {
/*  70 */     return this.autoFetchManager.getProfilingRate();
/*     */   }
/*     */   
/*     */   public void setProfilingRate(double rate) {
/*  74 */     this.autoFetchManager.setProfilingRate(rate);
/*     */   }
/*     */   
/*     */   public int getProfilingMin() {
/*  78 */     return this.autoFetchManager.getProfilingMin();
/*     */   }
/*     */   
/*     */   public int getProfilingBase() {
/*  82 */     return this.autoFetchManager.getProfilingBase();
/*     */   }
/*     */   
/*     */   public void setProfilingMin(int profilingMin) {
/*  86 */     this.autoFetchManager.setProfilingMin(profilingMin);
/*     */   }
/*     */   
/*     */   public void setProfilingBase(int profilingMax) {
/*  90 */     this.autoFetchManager.setProfilingBase(profilingMax);
/*     */   }
/*     */   
/*     */   public String updateTunedQueryInfo() {
/*  94 */     return this.autoFetchManager.updateTunedQueryInfo();
/*     */   }
/*     */   
/*     */   public int clearProfilingInfo() {
/*  98 */     return this.autoFetchManager.clearProfilingInfo();
/*     */   }
/*     */   
/*     */   public int clearTunedQueryInfo() {
/* 102 */     return this.autoFetchManager.clearTunedQueryInfo();
/*     */   }
/*     */   
/*     */   public void clearQueryStatistics() {
/* 106 */     this.autoFetchManager.clearQueryStatistics();
/*     */   }
/*     */   
/*     */   public int getTotalProfileSize() {
/* 110 */     return this.autoFetchManager.getTotalProfileSize();
/*     */   }
/*     */   
/*     */   public int getTotalTunedQueryCount() {
/* 114 */     return this.autoFetchManager.getTotalTunedQueryCount();
/*     */   }
/*     */   
/*     */   public int getTotalTunedQuerySize() {
/* 118 */     return this.autoFetchManager.getTotalTunedQuerySize();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\jmx\MAdminAutofetch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */