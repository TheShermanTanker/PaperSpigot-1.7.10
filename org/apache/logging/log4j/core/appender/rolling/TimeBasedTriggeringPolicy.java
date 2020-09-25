/*    */ package org.apache.logging.log4j.core.appender.rolling;
/*    */ 
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*    */ import org.apache.logging.log4j.core.helpers.Integers;
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
/*    */ @Plugin(name = "TimeBasedTriggeringPolicy", category = "Core", printObject = true)
/*    */ public final class TimeBasedTriggeringPolicy
/*    */   implements TriggeringPolicy
/*    */ {
/*    */   private long nextRollover;
/*    */   private final int interval;
/*    */   private final boolean modulate;
/*    */   private RollingFileManager manager;
/*    */   
/*    */   private TimeBasedTriggeringPolicy(int interval, boolean modulate) {
/* 38 */     this.interval = interval;
/* 39 */     this.modulate = modulate;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initialize(RollingFileManager manager) {
/* 48 */     this.manager = manager;
/* 49 */     this.nextRollover = manager.getPatternProcessor().getNextTime(manager.getFileTime(), this.interval, this.modulate);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isTriggeringEvent(LogEvent event) {
/* 59 */     if (this.manager.getFileSize() == 0L) {
/* 60 */       return false;
/*    */     }
/* 62 */     long now = System.currentTimeMillis();
/* 63 */     if (now > this.nextRollover) {
/* 64 */       this.nextRollover = this.manager.getPatternProcessor().getNextTime(now, this.interval, this.modulate);
/* 65 */       return true;
/*    */     } 
/* 67 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 72 */     return "TimeBasedTriggeringPolicy";
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
/*    */   @PluginFactory
/*    */   public static TimeBasedTriggeringPolicy createPolicy(@PluginAttribute("interval") String interval, @PluginAttribute("modulate") String modulate) {
/* 85 */     int increment = Integers.parseInt(interval, 1);
/* 86 */     boolean mod = Boolean.parseBoolean(modulate);
/* 87 */     return new TimeBasedTriggeringPolicy(increment, mod);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\TimeBasedTriggeringPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */