/*    */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.logging.ConsoleHandler;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.LogRecord;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class ForwardLogHandler
/*    */   extends ConsoleHandler {
/* 13 */   private Map<String, Logger> cachedLoggers = new ConcurrentHashMap<String, Logger>();
/*    */   
/*    */   private Logger getLogger(String name) {
/* 16 */     Logger logger = this.cachedLoggers.get(name);
/* 17 */     if (logger == null) {
/* 18 */       logger = LogManager.getLogger(name);
/* 19 */       this.cachedLoggers.put(name, logger);
/*    */     } 
/*    */     
/* 22 */     return logger;
/*    */   }
/*    */ 
/*    */   
/*    */   public void publish(LogRecord record) {
/* 27 */     Logger logger = getLogger(record.getLoggerName());
/* 28 */     Throwable exception = record.getThrown();
/* 29 */     Level level = record.getLevel();
/* 30 */     String message = getFormatter().formatMessage(record);
/*    */     
/* 32 */     if (level == Level.SEVERE) {
/* 33 */       logger.error(message, exception);
/* 34 */     } else if (level == Level.WARNING) {
/* 35 */       logger.warn(message, exception);
/* 36 */     } else if (level == Level.INFO) {
/* 37 */       logger.info(message, exception);
/* 38 */     } else if (level == Level.CONFIG) {
/* 39 */       logger.debug(message, exception);
/*    */     } else {
/* 41 */       logger.trace(message, exception);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void flush() {}
/*    */   
/*    */   public void close() throws SecurityException {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\ForwardLogHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */