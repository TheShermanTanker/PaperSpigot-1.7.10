/*     */ package org.apache.logging.log4j.core.impl;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.ThreadContext;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.Property;
/*     */ import org.apache.logging.log4j.message.LoggerNameAwareMessage;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.message.TimestampMessage;
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
/*     */ public class Log4jLogEvent
/*     */   implements LogEvent
/*     */ {
/*     */   private static final long serialVersionUID = -1351367343806656055L;
/*     */   private static final String NOT_AVAIL = "?";
/*     */   private final String fqcnOfLogger;
/*     */   private final Marker marker;
/*     */   private final Level level;
/*     */   private final String name;
/*     */   private final Message message;
/*     */   private final long timestamp;
/*     */   private final ThrowableProxy throwable;
/*     */   private final Map<String, String> mdc;
/*     */   private final ThreadContext.ContextStack ndc;
/*  52 */   private String threadName = null;
/*     */   
/*     */   private StackTraceElement location;
/*     */   
/*     */   private boolean includeLocation;
/*     */   
/*     */   private boolean endOfBatch = false;
/*     */   
/*     */   public Log4jLogEvent(long timestamp) {
/*  61 */     this("", (Marker)null, "", (Level)null, (Message)null, (ThrowableProxy)null, (Map<String, String>)null, (ThreadContext.ContextStack)null, (String)null, (StackTraceElement)null, timestamp);
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
/*     */   public Log4jLogEvent(String loggerName, Marker marker, String fqcn, Level level, Message message, Throwable t) {
/*  75 */     this(loggerName, marker, fqcn, level, message, null, t);
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
/*     */   public Log4jLogEvent(String loggerName, Marker marker, String fqcn, Level level, Message message, List<Property> properties, Throwable t) {
/*  90 */     this(loggerName, marker, fqcn, level, message, t, createMap(properties), (ThreadContext.getDepth() == 0) ? null : ThreadContext.cloneStack(), (String)null, (StackTraceElement)null, System.currentTimeMillis());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Log4jLogEvent(String loggerName, Marker marker, String fqcn, Level level, Message message, Throwable t, Map<String, String> mdc, ThreadContext.ContextStack ndc, String threadName, StackTraceElement location, long timestamp) {
/* 114 */     this(loggerName, marker, fqcn, level, message, (t == null) ? null : new ThrowableProxy(t), mdc, ndc, threadName, location, timestamp);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Log4jLogEvent createEvent(String loggerName, Marker marker, String fqcn, Level level, Message message, ThrowableProxy t, Map<String, String> mdc, ThreadContext.ContextStack ndc, String threadName, StackTraceElement location, long timestamp) {
/* 137 */     return new Log4jLogEvent(loggerName, marker, fqcn, level, message, t, mdc, ndc, threadName, location, timestamp);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Log4jLogEvent(String loggerName, Marker marker, String fqcn, Level level, Message message, ThrowableProxy t, Map<String, String> mdc, ThreadContext.ContextStack ndc, String threadName, StackTraceElement location, long timestamp) {
/* 158 */     this.name = loggerName;
/* 159 */     this.marker = marker;
/* 160 */     this.fqcnOfLogger = fqcn;
/* 161 */     this.level = level;
/* 162 */     this.message = message;
/* 163 */     this.throwable = t;
/* 164 */     this.mdc = mdc;
/* 165 */     this.ndc = ndc;
/* 166 */     this.timestamp = (message instanceof TimestampMessage) ? ((TimestampMessage)message).getTimestamp() : timestamp;
/* 167 */     this.threadName = threadName;
/* 168 */     this.location = location;
/* 169 */     if (message != null && message instanceof LoggerNameAwareMessage) {
/* 170 */       ((LoggerNameAwareMessage)message).setLoggerName(this.name);
/*     */     }
/*     */   }
/*     */   
/*     */   private static Map<String, String> createMap(List<Property> properties) {
/* 175 */     Map<String, String> contextMap = ThreadContext.getImmutableContext();
/* 176 */     if (contextMap == null && (properties == null || properties.size() == 0)) {
/* 177 */       return null;
/*     */     }
/* 179 */     if (properties == null || properties.size() == 0) {
/* 180 */       return contextMap;
/*     */     }
/* 182 */     Map<String, String> map = new HashMap<String, String>(contextMap);
/*     */     
/* 184 */     for (Property prop : properties) {
/* 185 */       if (!map.containsKey(prop.getName())) {
/* 186 */         map.put(prop.getName(), prop.getValue());
/*     */       }
/*     */     } 
/* 189 */     return Collections.unmodifiableMap(map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Level getLevel() {
/* 198 */     return this.level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLoggerName() {
/* 207 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Message getMessage() {
/* 216 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getThreadName() {
/* 225 */     if (this.threadName == null) {
/* 226 */       this.threadName = Thread.currentThread().getName();
/*     */     }
/* 228 */     return this.threadName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMillis() {
/* 237 */     return this.timestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getThrown() {
/* 246 */     return (this.throwable == null) ? null : this.throwable.getThrowable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThrowableProxy getThrownProxy() {
/* 254 */     return this.throwable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Marker getMarker() {
/* 264 */     return this.marker;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFQCN() {
/* 273 */     return this.fqcnOfLogger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getContextMap() {
/* 282 */     return (this.mdc == null) ? ThreadContext.EMPTY_MAP : this.mdc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThreadContext.ContextStack getContextStack() {
/* 291 */     return (this.ndc == null) ? (ThreadContext.ContextStack)ThreadContext.EMPTY_STACK : this.ndc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StackTraceElement getSource() {
/* 301 */     if (this.location != null) {
/* 302 */       return this.location;
/*     */     }
/* 304 */     if (this.fqcnOfLogger == null || !this.includeLocation) {
/* 305 */       return null;
/*     */     }
/* 307 */     this.location = calcLocation(this.fqcnOfLogger);
/* 308 */     return this.location;
/*     */   }
/*     */   
/*     */   public static StackTraceElement calcLocation(String fqcnOfLogger) {
/* 312 */     if (fqcnOfLogger == null) {
/* 313 */       return null;
/*     */     }
/* 315 */     StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
/* 316 */     boolean next = false;
/* 317 */     for (StackTraceElement element : stackTrace) {
/* 318 */       String className = element.getClassName();
/* 319 */       if (next) {
/* 320 */         if (!fqcnOfLogger.equals(className))
/*     */         {
/*     */           
/* 323 */           return element;
/*     */         }
/*     */       }
/* 326 */       else if (fqcnOfLogger.equals(className)) {
/* 327 */         next = true;
/* 328 */       } else if ("?".equals(className)) {
/*     */         break;
/*     */       } 
/*     */     } 
/* 332 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIncludeLocation() {
/* 337 */     return this.includeLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIncludeLocation(boolean includeLocation) {
/* 342 */     this.includeLocation = includeLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEndOfBatch() {
/* 347 */     return this.endOfBatch;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEndOfBatch(boolean endOfBatch) {
/* 352 */     this.endOfBatch = endOfBatch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object writeReplace() {
/* 360 */     return new LogEventProxy(this, this.includeLocation);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Serializable serialize(Log4jLogEvent event, boolean includeLocation) {
/* 365 */     return new LogEventProxy(event, includeLocation);
/*     */   }
/*     */   
/*     */   public static Log4jLogEvent deserialize(Serializable event) {
/* 369 */     if (event == null) {
/* 370 */       throw new NullPointerException("Event cannot be null");
/*     */     }
/* 372 */     if (event instanceof LogEventProxy) {
/* 373 */       LogEventProxy proxy = (LogEventProxy)event;
/* 374 */       Log4jLogEvent result = new Log4jLogEvent(proxy.name, proxy.marker, proxy.fqcnOfLogger, proxy.level, proxy.message, proxy.throwable, proxy.mdc, proxy.ndc, proxy.threadName, proxy.location, proxy.timestamp);
/*     */ 
/*     */ 
/*     */       
/* 378 */       result.setEndOfBatch(proxy.isEndOfBatch);
/* 379 */       result.setIncludeLocation(proxy.isLocationRequired);
/* 380 */       return result;
/*     */     } 
/* 382 */     throw new IllegalArgumentException("Event is not a serialized LogEvent: " + event.toString());
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
/* 386 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 391 */     StringBuilder sb = new StringBuilder();
/* 392 */     String n = this.name.isEmpty() ? "root" : this.name;
/* 393 */     sb.append("Logger=").append(n);
/* 394 */     sb.append(" Level=").append(this.level.name());
/* 395 */     sb.append(" Message=").append(this.message.getFormattedMessage());
/* 396 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LogEventProxy
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7139032940312647146L;
/*     */     
/*     */     private final String fqcnOfLogger;
/*     */     private final Marker marker;
/*     */     private final Level level;
/*     */     private final String name;
/*     */     private final Message message;
/*     */     private final long timestamp;
/*     */     private final ThrowableProxy throwable;
/*     */     private final Map<String, String> mdc;
/*     */     private final ThreadContext.ContextStack ndc;
/*     */     private final String threadName;
/*     */     private final StackTraceElement location;
/*     */     private final boolean isLocationRequired;
/*     */     private final boolean isEndOfBatch;
/*     */     
/*     */     public LogEventProxy(Log4jLogEvent event, boolean includeLocation) {
/* 420 */       this.fqcnOfLogger = event.fqcnOfLogger;
/* 421 */       this.marker = event.marker;
/* 422 */       this.level = event.level;
/* 423 */       this.name = event.name;
/* 424 */       this.message = event.message;
/* 425 */       this.timestamp = event.timestamp;
/* 426 */       this.throwable = event.throwable;
/* 427 */       this.mdc = event.mdc;
/* 428 */       this.ndc = event.ndc;
/* 429 */       this.location = includeLocation ? event.getSource() : null;
/* 430 */       this.threadName = event.getThreadName();
/* 431 */       this.isLocationRequired = includeLocation;
/* 432 */       this.isEndOfBatch = event.endOfBatch;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Object readResolve() {
/* 440 */       Log4jLogEvent result = new Log4jLogEvent(this.name, this.marker, this.fqcnOfLogger, this.level, this.message, this.throwable, this.mdc, this.ndc, this.threadName, this.location, this.timestamp);
/*     */ 
/*     */       
/* 443 */       result.setEndOfBatch(this.isEndOfBatch);
/* 444 */       result.setIncludeLocation(this.isLocationRequired);
/* 445 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\impl\Log4jLogEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */