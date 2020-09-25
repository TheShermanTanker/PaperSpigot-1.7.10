/*     */ package org.apache.logging.log4j.core.async;
/*     */ 
/*     */ import com.lmax.disruptor.EventFactory;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.ThreadContext;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.Property;
/*     */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.message.SimpleMessage;
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
/*     */ public class RingBufferLogEvent
/*     */   implements LogEvent
/*     */ {
/*     */   private static final long serialVersionUID = 8462119088943934758L;
/*     */   
/*     */   private static class Factory
/*     */     implements EventFactory<RingBufferLogEvent>
/*     */   {
/*     */     private Factory() {}
/*     */     
/*     */     public RingBufferLogEvent newInstance() {
/*  47 */       return new RingBufferLogEvent();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  52 */   public static final Factory FACTORY = new Factory();
/*     */   
/*     */   private AsyncLogger asyncLogger;
/*     */   
/*     */   private String loggerName;
/*     */   
/*     */   private Marker marker;
/*     */   
/*     */   private String fqcn;
/*     */   
/*     */   private Level level;
/*     */   
/*     */   private Message message;
/*     */   private Throwable thrown;
/*     */   private Map<String, String> contextMap;
/*     */   private ThreadContext.ContextStack contextStack;
/*     */   private String threadName;
/*     */   private StackTraceElement location;
/*     */   private long currentTimeMillis;
/*     */   private boolean endOfBatch;
/*     */   private boolean includeLocation;
/*     */   
/*     */   public void setValues(AsyncLogger asyncLogger, String loggerName, Marker marker, String fqcn, Level level, Message data, Throwable t, Map<String, String> map, ThreadContext.ContextStack contextStack, String threadName, StackTraceElement location, long currentTimeMillis) {
/*  75 */     this.asyncLogger = asyncLogger;
/*  76 */     this.loggerName = loggerName;
/*  77 */     this.marker = marker;
/*  78 */     this.fqcn = fqcn;
/*  79 */     this.level = level;
/*  80 */     this.message = data;
/*  81 */     this.thrown = t;
/*  82 */     this.contextMap = map;
/*  83 */     this.contextStack = contextStack;
/*  84 */     this.threadName = threadName;
/*  85 */     this.location = location;
/*  86 */     this.currentTimeMillis = currentTimeMillis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(boolean endOfBatch) {
/*  97 */     this.endOfBatch = endOfBatch;
/*  98 */     this.asyncLogger.actualAsyncLog(this);
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
/*     */   public boolean isEndOfBatch() {
/* 110 */     return this.endOfBatch;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEndOfBatch(boolean endOfBatch) {
/* 115 */     this.endOfBatch = endOfBatch;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIncludeLocation() {
/* 120 */     return this.includeLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIncludeLocation(boolean includeLocation) {
/* 125 */     this.includeLocation = includeLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLoggerName() {
/* 130 */     return this.loggerName;
/*     */   }
/*     */ 
/*     */   
/*     */   public Marker getMarker() {
/* 135 */     return this.marker;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFQCN() {
/* 140 */     return this.fqcn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Level getLevel() {
/* 145 */     return this.level;
/*     */   }
/*     */ 
/*     */   
/*     */   public Message getMessage() {
/* 150 */     if (this.message == null) {
/* 151 */       this.message = (Message)new SimpleMessage("");
/*     */     }
/* 153 */     return this.message;
/*     */   }
/*     */ 
/*     */   
/*     */   public Throwable getThrown() {
/* 158 */     return this.thrown;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, String> getContextMap() {
/* 163 */     return this.contextMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public ThreadContext.ContextStack getContextStack() {
/* 168 */     return this.contextStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getThreadName() {
/* 173 */     return this.threadName;
/*     */   }
/*     */ 
/*     */   
/*     */   public StackTraceElement getSource() {
/* 178 */     return this.location;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getMillis() {
/* 183 */     return this.currentTimeMillis;
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
/*     */   public void mergePropertiesIntoContextMap(Map<Property, Boolean> properties, StrSubstitutor strSubstitutor) {
/* 197 */     if (properties == null) {
/*     */       return;
/*     */     }
/*     */     
/* 201 */     Map<String, String> map = (this.contextMap == null) ? new HashMap<String, String>() : new HashMap<String, String>(this.contextMap);
/*     */ 
/*     */     
/* 204 */     for (Map.Entry<Property, Boolean> entry : properties.entrySet()) {
/* 205 */       Property prop = entry.getKey();
/* 206 */       if (map.containsKey(prop.getName())) {
/*     */         continue;
/*     */       }
/* 209 */       String value = ((Boolean)entry.getValue()).booleanValue() ? strSubstitutor.replace(prop.getValue()) : prop.getValue();
/*     */       
/* 211 */       map.put(prop.getName(), value);
/*     */     } 
/* 213 */     this.contextMap = map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 221 */     setValues(null, null, null, null, null, null, null, null, null, null, null, 0L);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\async\RingBufferLogEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */