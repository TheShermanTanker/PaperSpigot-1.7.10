/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.ErrorHandler;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.filter.AbstractFilterable;
/*     */ import org.apache.logging.log4j.core.helpers.Integers;
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
/*     */ public abstract class AbstractAppender
/*     */   extends AbstractFilterable
/*     */   implements Appender
/*     */ {
/*     */   private final boolean ignoreExceptions;
/*  37 */   private ErrorHandler handler = new DefaultErrorHandler(this);
/*     */ 
/*     */   
/*     */   private final Layout<? extends Serializable> layout;
/*     */ 
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private boolean started = false;
/*     */ 
/*     */   
/*     */   public static int parseInt(String s, int defaultValue) {
/*     */     try {
/*  50 */       return Integers.parseInt(s, defaultValue);
/*  51 */     } catch (NumberFormatException e) {
/*  52 */       LOGGER.error("Could not parse \"{}\" as an integer,  using default value {}: {}", new Object[] { s, Integer.valueOf(defaultValue), e });
/*  53 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
/*  64 */     this(name, filter, layout, true);
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
/*     */   protected AbstractAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions) {
/*  77 */     super(filter);
/*  78 */     this.name = name;
/*  79 */     this.layout = layout;
/*  80 */     this.ignoreExceptions = ignoreExceptions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void error(String msg) {
/*  88 */     this.handler.error(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void error(String msg, LogEvent event, Throwable t) {
/*  98 */     this.handler.error(msg, event, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void error(String msg, Throwable t) {
/* 107 */     this.handler.error(msg, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ErrorHandler getHandler() {
/* 116 */     return this.handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Layout<? extends Serializable> getLayout() {
/* 125 */     return this.layout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 134 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean ignoreExceptions() {
/* 145 */     return this.ignoreExceptions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStarted() {
/* 154 */     return this.started;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHandler(ErrorHandler handler) {
/* 163 */     if (handler == null) {
/* 164 */       LOGGER.error("The handler cannot be set to null");
/*     */     }
/* 166 */     if (isStarted()) {
/* 167 */       LOGGER.error("The handler cannot be changed once the appender is started");
/*     */       return;
/*     */     } 
/* 170 */     this.handler = handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 178 */     startFilter();
/* 179 */     this.started = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/* 187 */     this.started = false;
/* 188 */     stopFilter();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 193 */     return this.name;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\AbstractAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */