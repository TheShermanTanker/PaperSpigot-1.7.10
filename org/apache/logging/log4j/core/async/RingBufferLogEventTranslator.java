/*    */ package org.apache.logging.log4j.core.async;
/*    */ 
/*    */ import com.lmax.disruptor.EventTranslator;
/*    */ import java.util.Map;
/*    */ import org.apache.logging.log4j.Level;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.ThreadContext;
/*    */ import org.apache.logging.log4j.message.Message;
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
/*    */ public class RingBufferLogEventTranslator
/*    */   implements EventTranslator<RingBufferLogEvent>
/*    */ {
/*    */   private AsyncLogger asyncLogger;
/*    */   private String loggerName;
/*    */   private Marker marker;
/*    */   private String fqcn;
/*    */   private Level level;
/*    */   private Message message;
/*    */   private Throwable thrown;
/*    */   private Map<String, String> contextMap;
/*    */   private ThreadContext.ContextStack contextStack;
/*    */   private String threadName;
/*    */   private StackTraceElement location;
/*    */   private long currentTimeMillis;
/*    */   
/*    */   public void translateTo(RingBufferLogEvent event, long sequence) {
/* 53 */     event.setValues(this.asyncLogger, this.loggerName, this.marker, this.fqcn, this.level, this.message, this.thrown, this.contextMap, this.contextStack, this.threadName, this.location, this.currentTimeMillis);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setValues(AsyncLogger asyncLogger, String loggerName, Marker marker, String fqcn, Level level, Message message, Throwable thrown, Map<String, String> contextMap, ThreadContext.ContextStack contextStack, String threadName, StackTraceElement location, long currentTimeMillis) {
/* 63 */     this.asyncLogger = asyncLogger;
/* 64 */     this.loggerName = loggerName;
/* 65 */     this.marker = marker;
/* 66 */     this.fqcn = fqcn;
/* 67 */     this.level = level;
/* 68 */     this.message = message;
/* 69 */     this.thrown = thrown;
/* 70 */     this.contextMap = contextMap;
/* 71 */     this.contextStack = contextStack;
/* 72 */     this.threadName = threadName;
/* 73 */     this.location = location;
/* 74 */     this.currentTimeMillis = currentTimeMillis;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\async\RingBufferLogEventTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */