/*     */ package com.avaje.ebean.config;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import java.util.Map;
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
/*     */ public class DataSourceConfig
/*     */ {
/*     */   private String url;
/*     */   private String username;
/*     */   private String password;
/*     */   private String driver;
/*  28 */   private int minConnections = 2;
/*     */   
/*  30 */   private int maxConnections = 20;
/*     */   
/*  32 */   private int isolationLevel = 2;
/*     */   
/*     */   private String heartbeatSql;
/*     */   
/*     */   private boolean captureStackTrace;
/*     */   
/*  38 */   private int maxStackTraceSize = 5;
/*     */   
/*  40 */   private int leakTimeMinutes = 30;
/*     */   
/*  42 */   private int maxInactiveTimeSecs = 900;
/*     */   
/*  44 */   private int pstmtCacheSize = 20;
/*  45 */   private int cstmtCacheSize = 20;
/*     */   
/*  47 */   private int waitTimeoutMillis = 1000;
/*     */ 
/*     */   
/*     */   private String poolListener;
/*     */ 
/*     */   
/*     */   private boolean offline;
/*     */   
/*     */   Map<String, String> customProperties;
/*     */ 
/*     */   
/*     */   public String getUrl() {
/*  59 */     return this.url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUrl(String url) {
/*  66 */     this.url = url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsername() {
/*  73 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUsername(String username) {
/*  80 */     this.username = username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPassword() {
/*  87 */     return this.password;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPassword(String password) {
/*  94 */     this.password = password;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDriver() {
/* 101 */     return this.driver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDriver(String driver) {
/* 108 */     this.driver = driver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIsolationLevel() {
/* 115 */     return this.isolationLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsolationLevel(int isolationLevel) {
/* 122 */     this.isolationLevel = isolationLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinConnections() {
/* 129 */     return this.minConnections;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinConnections(int minConnections) {
/* 136 */     this.minConnections = minConnections;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxConnections() {
/* 143 */     return this.maxConnections;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxConnections(int maxConnections) {
/* 150 */     this.maxConnections = maxConnections;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHeartbeatSql() {
/* 160 */     return this.heartbeatSql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeartbeatSql(String heartbeatSql) {
/* 170 */     this.heartbeatSql = heartbeatSql;
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
/*     */   public boolean isCaptureStackTrace() {
/* 185 */     return this.captureStackTrace;
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
/*     */   public void setCaptureStackTrace(boolean captureStackTrace) {
/* 199 */     this.captureStackTrace = captureStackTrace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStackTraceSize() {
/* 206 */     return this.maxStackTraceSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxStackTraceSize(int maxStackTraceSize) {
/* 213 */     this.maxStackTraceSize = maxStackTraceSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLeakTimeMinutes() {
/* 221 */     return this.leakTimeMinutes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLeakTimeMinutes(int leakTimeMinutes) {
/* 229 */     this.leakTimeMinutes = leakTimeMinutes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPstmtCacheSize() {
/* 236 */     return this.pstmtCacheSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPstmtCacheSize(int pstmtCacheSize) {
/* 243 */     this.pstmtCacheSize = pstmtCacheSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCstmtCacheSize() {
/* 250 */     return this.cstmtCacheSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCstmtCacheSize(int cstmtCacheSize) {
/* 257 */     this.cstmtCacheSize = cstmtCacheSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWaitTimeoutMillis() {
/* 265 */     return this.waitTimeoutMillis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWaitTimeoutMillis(int waitTimeoutMillis) {
/* 273 */     this.waitTimeoutMillis = waitTimeoutMillis;
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
/*     */   public int getMaxInactiveTimeSecs() {
/* 285 */     return this.maxInactiveTimeSecs;
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
/*     */   public void setMaxInactiveTimeSecs(int maxInactiveTimeSecs) {
/* 297 */     this.maxInactiveTimeSecs = maxInactiveTimeSecs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPoolListener() {
/* 304 */     return this.poolListener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPoolListener(String poolListener) {
/* 312 */     this.poolListener = poolListener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOffline() {
/* 323 */     return this.offline;
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
/*     */   public void setOffline(boolean offline) {
/* 338 */     this.offline = offline;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getCustomProperties() {
/* 346 */     return this.customProperties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomProperties(Map<String, String> customProperties) {
/* 354 */     this.customProperties = customProperties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadSettings(String serverName) {
/* 362 */     String prefix = "datasource." + serverName + ".";
/*     */     
/* 364 */     this.username = GlobalProperties.get(prefix + "username", null);
/* 365 */     this.password = GlobalProperties.get(prefix + "password", null);
/*     */ 
/*     */ 
/*     */     
/* 369 */     String v = GlobalProperties.get(prefix + "databaseDriver", null);
/* 370 */     this.driver = GlobalProperties.get(prefix + "driver", v);
/*     */ 
/*     */     
/* 373 */     v = GlobalProperties.get(prefix + "databaseUrl", null);
/* 374 */     this.url = GlobalProperties.get(prefix + "url", v);
/*     */     
/* 376 */     this.captureStackTrace = GlobalProperties.getBoolean(prefix + "captureStackTrace", false);
/* 377 */     this.maxStackTraceSize = GlobalProperties.getInt(prefix + "maxStackTraceSize", 5);
/* 378 */     this.leakTimeMinutes = GlobalProperties.getInt(prefix + "leakTimeMinutes", 30);
/* 379 */     this.maxInactiveTimeSecs = GlobalProperties.getInt(prefix + "maxInactiveTimeSecs", 900);
/*     */     
/* 381 */     this.minConnections = GlobalProperties.getInt(prefix + "minConnections", 0);
/* 382 */     this.maxConnections = GlobalProperties.getInt(prefix + "maxConnections", 20);
/* 383 */     this.pstmtCacheSize = GlobalProperties.getInt(prefix + "pstmtCacheSize", 20);
/* 384 */     this.cstmtCacheSize = GlobalProperties.getInt(prefix + "cstmtCacheSize", 20);
/*     */     
/* 386 */     this.waitTimeoutMillis = GlobalProperties.getInt(prefix + "waitTimeout", 1000);
/*     */     
/* 388 */     this.heartbeatSql = GlobalProperties.get(prefix + "heartbeatSql", null);
/* 389 */     this.poolListener = GlobalProperties.get(prefix + "poolListener", null);
/* 390 */     this.offline = GlobalProperties.getBoolean(prefix + "offline", false);
/*     */     
/* 392 */     String isoLevel = GlobalProperties.get(prefix + "isolationlevel", "READ_COMMITTED");
/* 393 */     this.isolationLevel = TransactionIsolation.getLevel(isoLevel);
/*     */     
/* 395 */     String customProperties = GlobalProperties.get(prefix + "customProperties", null);
/* 396 */     if (customProperties != null && customProperties.length() > 0) {
/* 397 */       Map<String, String> custProps = StringHelper.delimitedToMap(customProperties, ";", "=");
/* 398 */       this.customProperties = custProps;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\DataSourceConfig.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */