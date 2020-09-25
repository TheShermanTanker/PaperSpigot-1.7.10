/*     */ package org.apache.logging.log4j.core.appender.db.jpa;
/*     */ 
/*     */ import java.util.Map;
/*     */ import javax.persistence.Basic;
/*     */ import javax.persistence.Convert;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
/*     */ import javax.persistence.MappedSuperclass;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.ThreadContext;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.db.jpa.converter.ContextMapAttributeConverter;
/*     */ import org.apache.logging.log4j.core.appender.db.jpa.converter.ContextStackAttributeConverter;
/*     */ import org.apache.logging.log4j.core.appender.db.jpa.converter.MarkerAttributeConverter;
/*     */ import org.apache.logging.log4j.core.appender.db.jpa.converter.MessageAttributeConverter;
/*     */ import org.apache.logging.log4j.core.appender.db.jpa.converter.StackTraceElementAttributeConverter;
/*     */ import org.apache.logging.log4j.core.appender.db.jpa.converter.ThrowableAttributeConverter;
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
/*     */ @MappedSuperclass
/*     */ public abstract class BasicLogEventEntity
/*     */   extends AbstractLogEventWrapperEntity
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public BasicLogEventEntity() {}
/*     */   
/*     */   public BasicLogEventEntity(LogEvent wrappedEvent) {
/*  86 */     super(wrappedEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Basic
/*     */   @Enumerated(EnumType.STRING)
/*     */   public Level getLevel() {
/*  98 */     return getWrappedEvent().getLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Basic
/*     */   public String getLoggerName() {
/* 109 */     return getWrappedEvent().getLoggerName();
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
/*     */   @Convert(converter = StackTraceElementAttributeConverter.class)
/*     */   public StackTraceElement getSource() {
/* 122 */     return getWrappedEvent().getSource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Convert(converter = MessageAttributeConverter.class)
/*     */   public Message getMessage() {
/* 134 */     return getWrappedEvent().getMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Convert(converter = MarkerAttributeConverter.class)
/*     */   public Marker getMarker() {
/* 146 */     return getWrappedEvent().getMarker();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Basic
/*     */   public String getThreadName() {
/* 157 */     return getWrappedEvent().getThreadName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Basic
/*     */   public long getMillis() {
/* 168 */     return getWrappedEvent().getMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Convert(converter = ThrowableAttributeConverter.class)
/*     */   public Throwable getThrown() {
/* 180 */     return getWrappedEvent().getThrown();
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
/*     */   @Convert(converter = ContextMapAttributeConverter.class)
/*     */   public Map<String, String> getContextMap() {
/* 193 */     return getWrappedEvent().getContextMap();
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
/*     */   @Convert(converter = ContextStackAttributeConverter.class)
/*     */   public ThreadContext.ContextStack getContextStack() {
/* 206 */     return getWrappedEvent().getContextStack();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Basic
/*     */   public String getFQCN() {
/* 217 */     return getWrappedEvent().getFQCN();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\BasicLogEventEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */