/*    */ package org.apache.logging.log4j;
/*    */ 
/*    */ import org.apache.logging.log4j.message.Message;
/*    */ import org.apache.logging.log4j.message.StructuredDataMessage;
/*    */ import org.apache.logging.log4j.spi.AbstractLogger;
/*    */ import org.apache.logging.log4j.spi.AbstractLoggerWrapper;
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
/*    */ public final class EventLogger
/*    */ {
/*    */   private static final String NAME = "EventLogger";
/* 33 */   public static final Marker EVENT_MARKER = MarkerManager.getMarker("EVENT");
/*    */   
/* 35 */   private static final String FQCN = EventLogger.class.getName();
/*    */   
/*    */   private static AbstractLoggerWrapper loggerWrapper;
/*    */   
/*    */   static {
/* 40 */     Logger eventLogger = LogManager.getLogger("EventLogger");
/* 41 */     if (!(eventLogger instanceof AbstractLogger)) {
/* 42 */       throw new LoggingException("Logger returned must be based on AbstractLogger");
/*    */     }
/* 44 */     loggerWrapper = new AbstractLoggerWrapper((AbstractLogger)eventLogger, "EventLogger", null);
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
/*    */   public static void logEvent(StructuredDataMessage msg) {
/* 56 */     loggerWrapper.log(EVENT_MARKER, FQCN, Level.OFF, (Message)msg, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void logEvent(StructuredDataMessage msg, Level level) {
/* 65 */     loggerWrapper.log(EVENT_MARKER, FQCN, level, (Message)msg, null);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\EventLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */