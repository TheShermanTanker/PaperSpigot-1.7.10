/*    */ package org.apache.logging.log4j.core.appender.rolling;
/*    */ 
/*    */ import java.lang.management.ManagementFactory;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*    */ import org.apache.logging.log4j.status.StatusLogger;
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
/*    */ @Plugin(name = "OnStartupTriggeringPolicy", category = "Core", printObject = true)
/*    */ public class OnStartupTriggeringPolicy
/*    */   implements TriggeringPolicy
/*    */ {
/* 35 */   private static long JVM_START_TIME = ManagementFactory.getRuntimeMXBean().getStartTime();
/*    */   
/* 37 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean evaluated = false;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private RollingFileManager manager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initialize(RollingFileManager manager) {
/* 57 */     this.manager = manager;
/* 58 */     if (JVM_START_TIME == 0L) {
/* 59 */       this.evaluated = true;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isTriggeringEvent(LogEvent event) {
/* 70 */     if (this.evaluated) {
/* 71 */       return false;
/*    */     }
/* 73 */     this.evaluated = true;
/* 74 */     return (this.manager.getFileTime() < JVM_START_TIME);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 79 */     return "OnStartupTriggeringPolicy";
/*    */   }
/*    */   
/*    */   @PluginFactory
/*    */   public static OnStartupTriggeringPolicy createPolicy() {
/* 84 */     return new OnStartupTriggeringPolicy();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\OnStartupTriggeringPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */