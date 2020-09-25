/*     */ package com.avaje.ebeaninternal.server.lib;
/*     */ 
/*     */ import java.util.Iterator;
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
/*     */ public final class BackgroundThread
/*     */ {
/*  46 */   private static final Logger logger = Logger.getLogger(BackgroundThread.class.getName());
/*     */   
/*  48 */   private static final BackgroundThread me = new BackgroundThread();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private Vector<BackgroundRunnable> list = new Vector<BackgroundRunnable>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private final Object monitor = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Thread thread;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private long sleepTime = 1000L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long count;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long exeTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean stopped;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private Object threadMonitor = new Object();
/*     */ 
/*     */   
/*     */   private BackgroundThread() {
/*  92 */     this.thread = new Thread(new Runner(), "EbeanBackgroundThread");
/*  93 */     this.thread.setDaemon(true);
/*  94 */     this.thread.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void add(int freqInSecs, Runnable runnable) {
/* 101 */     add(new BackgroundRunnable(runnable, freqInSecs));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void add(BackgroundRunnable backgroundRunnable) {
/* 108 */     me.addTask(backgroundRunnable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void shutdown() {
/* 115 */     me.stop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator<BackgroundRunnable> runnables() {
/* 122 */     synchronized (me.monitor) {
/* 123 */       return me.list.iterator();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addTask(BackgroundRunnable backgroundRunnable) {
/* 128 */     synchronized (this.monitor) {
/* 129 */       this.list.add(backgroundRunnable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void stop() {
/* 139 */     this.stopped = true;
/* 140 */     synchronized (this.threadMonitor) {
/*     */       try {
/* 142 */         this.threadMonitor.wait(10000L);
/* 143 */       } catch (InterruptedException e) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class Runner
/*     */     implements Runnable
/*     */   {
/*     */     private Runner() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 157 */       if (ShutdownManager.isStopping()) {
/*     */         return;
/*     */       }
/*     */       
/* 161 */       while (!BackgroundThread.this.stopped) {
/*     */         
/*     */         try {
/* 164 */           long actualSleep = BackgroundThread.this.sleepTime - BackgroundThread.this.exeTime;
/* 165 */           if (actualSleep < 0L) {
/* 166 */             actualSleep = BackgroundThread.this.sleepTime;
/*     */           }
/* 168 */           Thread.sleep(actualSleep);
/* 169 */           synchronized (BackgroundThread.this.monitor) {
/* 170 */             runJobs();
/*     */           }
/*     */         
/* 173 */         } catch (InterruptedException e) {
/* 174 */           BackgroundThread.logger.log(Level.SEVERE, (String)null, e);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 179 */       synchronized (BackgroundThread.this.threadMonitor) {
/* 180 */         BackgroundThread.this.threadMonitor.notifyAll();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void runJobs() {
/* 186 */       long startTime = System.currentTimeMillis();
/*     */ 
/*     */       
/* 189 */       Iterator<BackgroundRunnable> it = BackgroundThread.this.list.iterator();
/* 190 */       while (it.hasNext()) {
/* 191 */         BackgroundRunnable bgr = it.next();
/* 192 */         if (bgr.isActive()) {
/*     */           
/* 194 */           int freqInSecs = bgr.getFreqInSecs();
/*     */           
/* 196 */           if (BackgroundThread.this.count % freqInSecs == 0L) {
/* 197 */             Runnable runable = bgr.getRunnable();
/* 198 */             if (bgr.runNow(startTime)) {
/* 199 */               bgr.runStart();
/* 200 */               if (BackgroundThread.logger.isLoggable(Level.FINER)) {
/* 201 */                 String msg = BackgroundThread.this.count + " BGRunnable running [" + runable.getClass().getName() + "]";
/*     */                 
/* 203 */                 BackgroundThread.logger.finer(msg);
/*     */               } 
/*     */               
/* 206 */               runable.run();
/* 207 */               bgr.runEnd();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 212 */       BackgroundThread.this.exeTime = System.currentTimeMillis() - startTime;
/* 213 */       BackgroundThread.this.count++;
/*     */       
/* 215 */       if (BackgroundThread.this.count == 86400L)
/*     */       {
/* 217 */         BackgroundThread.this.count = 0L;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString() {
/* 223 */     synchronized (this.monitor) {
/* 224 */       StringBuffer sb = new StringBuffer();
/*     */       
/* 226 */       Iterator<BackgroundRunnable> it = runnables();
/* 227 */       while (it.hasNext()) {
/* 228 */         BackgroundRunnable bgr = it.next();
/* 229 */         sb.append(bgr);
/*     */       } 
/*     */       
/* 232 */       return sb.toString();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\BackgroundThread.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */