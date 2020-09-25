/*     */ package com.avaje.ebean.config;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AutofetchConfig
/*     */ {
/*   8 */   private AutofetchMode mode = AutofetchMode.DEFAULT_ONIFEMPTY;
/*     */   
/*     */   private boolean queryTuning = false;
/*     */   
/*     */   private boolean queryTuningAddVersion = false;
/*     */   
/*     */   private boolean profiling = false;
/*     */   
/*  16 */   private int profilingMin = 1;
/*     */   
/*  18 */   private int profilingBase = 10;
/*     */   
/*  20 */   private double profilingRate = 0.05D;
/*     */   
/*     */   private boolean useFileLogging = false;
/*     */   
/*     */   private String logDirectory;
/*     */   
/*  26 */   private int profileUpdateFrequency = 60;
/*     */   
/*  28 */   private int garbageCollectionWait = 100;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AutofetchMode getMode() {
/*  38 */     return this.mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMode(AutofetchMode mode) {
/*  46 */     this.mode = mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isQueryTuning() {
/*  53 */     return this.queryTuning;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQueryTuning(boolean queryTuning) {
/*  60 */     this.queryTuning = queryTuning;
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
/*     */   public boolean isQueryTuningAddVersion() {
/*  72 */     return this.queryTuningAddVersion;
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
/*     */   public void setQueryTuningAddVersion(boolean queryTuningAddVersion) {
/*  84 */     this.queryTuningAddVersion = queryTuningAddVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isProfiling() {
/*  91 */     return this.profiling;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProfiling(boolean profiling) {
/* 102 */     this.profiling = profiling;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProfilingMin() {
/* 110 */     return this.profilingMin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProfilingMin(int profilingMin) {
/* 118 */     this.profilingMin = profilingMin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProfilingBase() {
/* 127 */     return this.profilingBase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProfilingBase(int profilingBase) {
/* 134 */     this.profilingBase = profilingBase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getProfilingRate() {
/* 142 */     return this.profilingRate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProfilingRate(double profilingRate) {
/* 150 */     this.profilingRate = profilingRate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseFileLogging() {
/* 158 */     return this.useFileLogging;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUseFileLogging(boolean useFileLogging) {
/* 166 */     this.useFileLogging = useFileLogging;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLogDirectory() {
/* 173 */     return this.logDirectory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLogDirectoryWithEval() {
/* 181 */     return GlobalProperties.evaluateExpressions(this.logDirectory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLogDirectory(String logDirectory) {
/* 188 */     this.logDirectory = logDirectory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProfileUpdateFrequency() {
/* 196 */     return this.profileUpdateFrequency;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProfileUpdateFrequency(int profileUpdateFrequency) {
/* 204 */     this.profileUpdateFrequency = profileUpdateFrequency;
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
/*     */   public int getGarbageCollectionWait() {
/* 219 */     return this.garbageCollectionWait;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGarbageCollectionWait(int garbageCollectionWait) {
/* 227 */     this.garbageCollectionWait = garbageCollectionWait;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadSettings(ConfigPropertyMap p) {
/* 235 */     this.logDirectory = p.get("autofetch.logDirectory", null);
/* 236 */     this.queryTuning = p.getBoolean("autofetch.querytuning", false);
/* 237 */     this.queryTuningAddVersion = p.getBoolean("autofetch.queryTuningAddVersion", false);
/*     */     
/* 239 */     this.profiling = p.getBoolean("autofetch.profiling", false);
/* 240 */     this.mode = p.<AutofetchMode>getEnum(AutofetchMode.class, "autofetch.implicitmode", AutofetchMode.DEFAULT_ONIFEMPTY);
/*     */     
/* 242 */     this.profilingMin = p.getInt("autofetch.profiling.min", 1);
/* 243 */     this.profilingBase = p.getInt("autofetch.profiling.base", 10);
/*     */     
/* 245 */     String rate = p.get("autofetch.profiling.rate", "0.05");
/* 246 */     this.profilingRate = Double.parseDouble(rate);
/*     */     
/* 248 */     this.useFileLogging = p.getBoolean("autofetch.useFileLogging", this.profiling);
/* 249 */     this.profileUpdateFrequency = p.getInt("autofetch.profiling.updatefrequency", 60);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\AutofetchConfig.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */