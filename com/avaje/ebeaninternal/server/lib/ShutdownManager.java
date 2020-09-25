/*     */ package com.avaje.ebeaninternal.server.lib;
/*     */ 
/*     */ import com.avaje.ebean.common.BootupEbeanManager;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import com.avaje.ebeaninternal.server.lib.sql.DataSourceGlobalManager;
/*     */ import com.avaje.ebeaninternal.server.lib.thread.ThreadPoolManager;
/*     */ import java.sql.Driver;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ShutdownManager
/*     */ {
/*  42 */   private static final Logger logger = Logger.getLogger(BackgroundThread.class.getName());
/*     */   
/*  44 */   static final Vector<Runnable> runnables = new Vector<Runnable>();
/*     */   
/*     */   static boolean stopping;
/*     */   
/*     */   static BootupEbeanManager serverFactory;
/*     */   
/*  50 */   static final ShutdownHook shutdownHook = new ShutdownHook();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  56 */     register();
/*  57 */   } static boolean whyShutdown = GlobalProperties.getBoolean("debug.shutdown.why", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerServerFactory(BootupEbeanManager factory) {
/*  67 */     serverFactory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void touch() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isStopping() {
/*  80 */     synchronized (runnables) {
/*  81 */       return stopping;
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
/*     */   private static void deregister() {
/*  93 */     synchronized (runnables) {
/*     */       try {
/*  95 */         Runtime.getRuntime().removeShutdownHook(shutdownHook);
/*  96 */       } catch (IllegalStateException ex) {
/*  97 */         if (!ex.getMessage().equals("Shutdown in progress")) {
/*  98 */           throw ex;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void register() {
/* 108 */     synchronized (runnables) {
/*     */       try {
/* 110 */         Runtime.getRuntime().addShutdownHook(shutdownHook);
/* 111 */       } catch (IllegalStateException ex) {
/* 112 */         if (!ex.getMessage().equals("Shutdown in progress")) {
/* 113 */           throw ex;
/*     */         }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void shutdown() {
/* 132 */     synchronized (runnables) {
/* 133 */       if (stopping) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 138 */       if (whyShutdown) {
/*     */         try {
/* 140 */           throw new RuntimeException("debug.shutdown.why=true ...");
/* 141 */         } catch (Throwable throwable) {
/* 142 */           logger.log(Level.WARNING, "Stacktrace showing why shutdown was fired", throwable);
/*     */         } 
/*     */       }
/*     */       
/* 146 */       stopping = true;
/*     */ 
/*     */       
/* 149 */       deregister();
/*     */ 
/*     */       
/* 152 */       BackgroundThread.shutdown();
/*     */       
/* 154 */       String shutdownRunner = GlobalProperties.get("system.shutdown.runnable", null);
/* 155 */       if (shutdownRunner != null) {
/*     */         try {
/* 157 */           Runnable r = (Runnable)ClassUtil.newInstance(shutdownRunner);
/* 158 */           r.run();
/* 159 */         } catch (Exception exception) {
/* 160 */           logger.log(Level.SEVERE, (String)null, exception);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 166 */       Enumeration<Runnable> e = runnables.elements();
/* 167 */       while (e.hasMoreElements()) {
/*     */         try {
/* 169 */           Runnable r = e.nextElement();
/* 170 */           r.run();
/* 171 */         } catch (Exception ex) {
/* 172 */           logger.log(Level.SEVERE, (String)null, ex);
/* 173 */           ex.printStackTrace();
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 180 */         if (serverFactory != null) {
/* 181 */           serverFactory.shutdown();
/*     */         }
/*     */         
/* 184 */         ThreadPoolManager.shutdown();
/*     */         
/* 186 */         DataSourceGlobalManager.shutdown();
/*     */         
/* 188 */         boolean dereg = GlobalProperties.getBoolean("datasource.deregisterAllDrivers", false);
/* 189 */         if (dereg) {
/* 190 */           deregisterAllJdbcDrivers();
/*     */         }
/*     */       }
/* 193 */       catch (Exception ex) {
/* 194 */         String msg = "Shutdown Exception: " + ex.getMessage();
/* 195 */         System.err.println(msg);
/* 196 */         ex.printStackTrace();
/*     */         try {
/* 198 */           logger.log(Level.SEVERE, (String)null, ex);
/* 199 */         } catch (Exception exc) {
/* 200 */           String ms = "Error Logging error to the Log. It may be shutting down.";
/* 201 */           System.err.println(ms);
/* 202 */           exc.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void deregisterAllJdbcDrivers() {
/* 210 */     Enumeration<Driver> drivers = DriverManager.getDrivers();
/* 211 */     while (drivers.hasMoreElements()) {
/* 212 */       Driver driver = drivers.nextElement();
/*     */       try {
/* 214 */         DriverManager.deregisterDriver(driver);
/* 215 */         logger.log(Level.INFO, String.format("Deregistering jdbc driver: %s", new Object[] { driver }));
/* 216 */       } catch (SQLException e) {
/* 217 */         logger.log(Level.SEVERE, String.format("Error deregistering driver %s", new Object[] { driver }), e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(Runnable runnable) {
/* 228 */     synchronized (runnables) {
/* 229 */       runnables.add(runnable);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\ShutdownManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */