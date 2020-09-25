/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import java.util.concurrent.locks.LockSupport;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CachedClock
/*    */   implements Clock
/*    */ {
/*    */   private static final int UPDATE_THRESHOLD = 1023;
/* 31 */   private static CachedClock instance = new CachedClock();
/* 32 */   private volatile long millis = System.currentTimeMillis();
/* 33 */   private volatile short count = 0;
/* 34 */   private final Thread updater = new Thread("Clock Updater Thread")
/*    */     {
/*    */       public void run() {
/*    */         while (true) {
/* 38 */           long time = System.currentTimeMillis();
/* 39 */           CachedClock.this.millis = time;
/*    */ 
/*    */           
/* 42 */           LockSupport.parkNanos(1000000L);
/*    */         } 
/*    */       }
/*    */     };
/*    */   
/*    */   private CachedClock() {
/* 48 */     this.updater.setDaemon(true);
/* 49 */     this.updater.start();
/*    */   }
/*    */   
/*    */   public static CachedClock instance() {
/* 53 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long currentTimeMillis() {
/* 69 */     if (((this.count = (short)(this.count + 1)) & 0x3FF) == 1023) {
/* 70 */       this.millis = System.currentTimeMillis();
/*    */     }
/* 72 */     return this.millis;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\CachedClock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */