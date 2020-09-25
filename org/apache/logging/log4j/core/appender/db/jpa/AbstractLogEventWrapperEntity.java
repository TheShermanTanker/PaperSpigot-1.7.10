/*     */ package org.apache.logging.log4j.core.appender.db.jpa;
/*     */ 
/*     */ import java.util.Map;
/*     */ import javax.persistence.Inheritance;
/*     */ import javax.persistence.InheritanceType;
/*     */ import javax.persistence.MappedSuperclass;
/*     */ import javax.persistence.Transient;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.ThreadContext;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.message.Message;
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
/*     */ @MappedSuperclass
/*     */ @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
/*     */ public abstract class AbstractLogEventWrapperEntity
/*     */   implements LogEvent
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final LogEvent wrappedEvent;
/*     */   
/*     */   protected AbstractLogEventWrapperEntity() {
/*  81 */     this(new NullLogEvent(null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractLogEventWrapperEntity(LogEvent wrappedEvent) {
/*  91 */     if (wrappedEvent == null) {
/*  92 */       throw new IllegalArgumentException("The wrapped event cannot be null.");
/*     */     }
/*  94 */     this.wrappedEvent = wrappedEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transient
/*     */   protected final LogEvent getWrappedEvent() {
/* 105 */     return this.wrappedEvent;
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
/*     */   public void setLevel(Level level) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoggerName(String loggerName) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSource(StackTraceElement source) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessage(Message message) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMarker(Marker marker) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThreadName(String threadName) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMillis(long millis) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThrown(Throwable throwable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContextMap(Map<String, String> contextMap) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContextStack(ThreadContext.ContextStack contextStack) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFQCN(String fqcn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transient
/*     */   public final boolean isIncludeLocation() {
/* 227 */     return getWrappedEvent().isIncludeLocation();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setIncludeLocation(boolean locationRequired) {
/* 232 */     getWrappedEvent().setIncludeLocation(locationRequired);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transient
/*     */   public final boolean isEndOfBatch() {
/* 244 */     return getWrappedEvent().isEndOfBatch();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setEndOfBatch(boolean endOfBatch) {
/* 249 */     getWrappedEvent().setEndOfBatch(endOfBatch);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class NullLogEvent
/*     */     implements LogEvent
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     private NullLogEvent() {}
/*     */     
/*     */     public Level getLevel() {
/* 261 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getLoggerName() {
/* 266 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public StackTraceElement getSource() {
/* 271 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Message getMessage() {
/* 276 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Marker getMarker() {
/* 281 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getThreadName() {
/* 286 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getMillis() {
/* 291 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/*     */     public Throwable getThrown() {
/* 296 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<String, String> getContextMap() {
/* 301 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ThreadContext.ContextStack getContextStack() {
/* 306 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getFQCN() {
/* 311 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isIncludeLocation() {
/* 316 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setIncludeLocation(boolean locationRequired) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEndOfBatch() {
/* 326 */       return false;
/*     */     }
/*     */     
/*     */     public void setEndOfBatch(boolean endOfBatch) {}
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\AbstractLogEventWrapperEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */