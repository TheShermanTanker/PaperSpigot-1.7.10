/*    */ package org.apache.logging.log4j.core.config;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileConfigurationMonitor
/*    */   implements ConfigurationMonitor
/*    */ {
/*    */   private static final int MASK = 15;
/*    */   private static final int MIN_INTERVAL = 5;
/*    */   private static final int MILLIS_PER_SECOND = 1000;
/*    */   private final File file;
/*    */   private long lastModified;
/*    */   private final List<ConfigurationListener> listeners;
/*    */   private final int interval;
/*    */   private long nextCheck;
/* 44 */   private volatile int counter = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Reconfigurable reconfigurable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FileConfigurationMonitor(Reconfigurable reconfigurable, File file, List<ConfigurationListener> listeners, int interval) {
/* 58 */     this.reconfigurable = reconfigurable;
/* 59 */     this.file = file;
/* 60 */     this.lastModified = file.lastModified();
/* 61 */     this.listeners = listeners;
/* 62 */     this.interval = ((interval < 5) ? 5 : interval) * 1000;
/* 63 */     this.nextCheck = System.currentTimeMillis() + interval;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void checkConfiguration() {
/* 71 */     if ((++this.counter & 0xF) == 0)
/* 72 */       synchronized (this) {
/* 73 */         long current = System.currentTimeMillis();
/* 74 */         if (current >= this.nextCheck) {
/* 75 */           this.nextCheck = current + this.interval;
/* 76 */           if (this.file.lastModified() > this.lastModified) {
/* 77 */             this.lastModified = this.file.lastModified();
/* 78 */             for (ConfigurationListener listener : this.listeners)
/* 79 */               listener.onChange(this.reconfigurable); 
/*    */           } 
/*    */         } 
/*    */       }  
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\FileConfigurationMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */