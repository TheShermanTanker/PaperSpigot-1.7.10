/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import com.avaje.ebean.config.DataSourceConfig;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import com.avaje.ebeaninternal.server.lib.BackgroundRunnable;
/*     */ import com.avaje.ebeaninternal.server.lib.BackgroundThread;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class DataSourceManager
/*     */   implements DataSourceNotify
/*     */ {
/*  40 */   private static final Logger logger = Logger.getLogger(DataSourceManager.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final DataSourceAlertListener alertlistener;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private final Hashtable<String, DataSourcePool> dsMap = new Hashtable<String, DataSourcePool>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private final Object monitor = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final BackgroundRunnable dbChecker;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int dbUpFreqInSecs;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int dbDownFreqInSecs;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shuttingDown;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean deregisterDriver;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataSourceManager() {
/*  84 */     this.alertlistener = createAlertListener();
/*     */ 
/*     */     
/*  87 */     this.dbUpFreqInSecs = GlobalProperties.getInt("datasource.heartbeatfreq", 30);
/*  88 */     this.dbDownFreqInSecs = GlobalProperties.getInt("datasource.deadbeatfreq", 10);
/*  89 */     this.dbChecker = new BackgroundRunnable(new Checker(), this.dbUpFreqInSecs);
/*  90 */     this.deregisterDriver = GlobalProperties.getBoolean("datasource.deregisterDriver", true);
/*     */     
/*     */     try {
/*  93 */       BackgroundThread.add(this.dbChecker);
/*     */     }
/*  95 */     catch (Exception e) {
/*  96 */       logger.log(Level.SEVERE, (String)null, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private DataSourceAlertListener createAlertListener() throws DataSourceException {
/* 102 */     String alertCN = GlobalProperties.get("datasource.alert.class", null);
/* 103 */     if (alertCN == null) {
/* 104 */       return new SimpleAlerter();
/*     */     }
/*     */     
/*     */     try {
/* 108 */       return (DataSourceAlertListener)ClassUtil.newInstance(alertCN, getClass());
/*     */     }
/* 110 */     catch (Exception ex) {
/* 111 */       throw new DataSourceException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyDataSourceUp(String dataSourceName) {
/* 121 */     this.dbChecker.setFreqInSecs(this.dbUpFreqInSecs);
/*     */     
/* 123 */     if (this.alertlistener != null) {
/* 124 */       this.alertlistener.dataSourceUp(dataSourceName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyDataSourceDown(String dataSourceName) {
/* 133 */     this.dbChecker.setFreqInSecs(this.dbDownFreqInSecs);
/*     */     
/* 135 */     if (this.alertlistener != null) {
/* 136 */       this.alertlistener.dataSourceDown(dataSourceName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyWarning(String subject, String msg) {
/* 144 */     if (this.alertlistener != null) {
/* 145 */       this.alertlistener.warning(subject, msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/* 153 */     synchronized (this.monitor) {
/* 154 */       return this.shuttingDown;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 163 */     synchronized (this.monitor) {
/*     */       
/* 165 */       this.shuttingDown = true;
/*     */       
/* 167 */       Collection<DataSourcePool> values = this.dsMap.values();
/* 168 */       for (DataSourcePool ds : values) {
/*     */         try {
/* 170 */           ds.shutdown();
/* 171 */         } catch (DataSourceException e) {
/*     */           
/* 173 */           logger.log(Level.SEVERE, (String)null, e);
/*     */         } 
/*     */       } 
/* 176 */       if (this.deregisterDriver) {
/* 177 */         for (DataSourcePool ds : values) {
/* 178 */           ds.deregisterDriver();
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<DataSourcePool> getPools() {
/* 188 */     synchronized (this.monitor) {
/*     */       
/* 190 */       ArrayList<DataSourcePool> list = new ArrayList<DataSourcePool>();
/* 191 */       list.addAll(this.dsMap.values());
/* 192 */       return list;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataSourcePool getDataSource(String name) {
/* 200 */     return getDataSource(name, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DataSourcePool getDataSource(String name, DataSourceConfig dsConfig) {
/* 206 */     if (name == null) {
/* 207 */       throw new IllegalArgumentException("name not defined");
/*     */     }
/*     */     
/* 210 */     synchronized (this.monitor) {
/* 211 */       DataSourcePool pool = this.dsMap.get(name);
/* 212 */       if (pool == null) {
/* 213 */         if (dsConfig == null) {
/* 214 */           dsConfig = new DataSourceConfig();
/* 215 */           dsConfig.loadSettings(name);
/*     */         } 
/* 217 */         pool = new DataSourcePool(this, name, dsConfig);
/* 218 */         this.dsMap.put(name, pool);
/*     */       } 
/* 220 */       return pool;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkDataSource() {
/* 230 */     synchronized (this.monitor) {
/* 231 */       if (!isShuttingDown()) {
/* 232 */         Iterator<DataSourcePool> it = this.dsMap.values().iterator();
/* 233 */         while (it.hasNext()) {
/* 234 */           DataSourcePool ds = it.next();
/* 235 */           ds.checkDataSource();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private final class Checker
/*     */     implements Runnable
/*     */   {
/*     */     private Checker() {}
/*     */     
/*     */     public void run() {
/* 247 */       DataSourceManager.this.checkDataSource();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourceManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */