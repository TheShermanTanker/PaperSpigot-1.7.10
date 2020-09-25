/*     */ package org.apache.logging.log4j.core.config;
/*     */ 
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
/*     */ import org.apache.logging.log4j.core.filter.AbstractFilterable;
/*     */ import org.apache.logging.log4j.core.filter.Filterable;
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
/*     */ public class AppenderControl
/*     */   extends AbstractFilterable
/*     */ {
/*  32 */   private final ThreadLocal<AppenderControl> recursive = new ThreadLocal<AppenderControl>();
/*     */ 
/*     */ 
/*     */   
/*     */   private final Appender appender;
/*     */ 
/*     */   
/*     */   private final Level level;
/*     */ 
/*     */   
/*     */   private final int intLevel;
/*     */ 
/*     */ 
/*     */   
/*     */   public AppenderControl(Appender appender, Level level, Filter filter) {
/*  47 */     super(filter);
/*  48 */     this.appender = appender;
/*  49 */     this.level = level;
/*  50 */     this.intLevel = (level == null) ? Level.ALL.intLevel() : level.intLevel();
/*  51 */     startFilter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Appender getAppender() {
/*  59 */     return this.appender;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void callAppender(LogEvent event) {
/*  67 */     if (getFilter() != null) {
/*  68 */       Filter.Result r = getFilter().filter(event);
/*  69 */       if (r == Filter.Result.DENY) {
/*     */         return;
/*     */       }
/*     */     } 
/*  73 */     if (this.level != null && 
/*  74 */       this.intLevel < event.getLevel().intLevel()) {
/*     */       return;
/*     */     }
/*     */     
/*  78 */     if (this.recursive.get() != null) {
/*  79 */       this.appender.getHandler().error("Recursive call to appender " + this.appender.getName());
/*     */       return;
/*     */     } 
/*     */     try {
/*  83 */       this.recursive.set(this);
/*     */       
/*  85 */       if (!this.appender.isStarted()) {
/*  86 */         this.appender.getHandler().error("Attempted to append to non-started appender " + this.appender.getName());
/*     */         
/*  88 */         if (!this.appender.ignoreExceptions()) {
/*  89 */           throw new AppenderLoggingException("Attempted to append to non-started appender " + this.appender.getName());
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  94 */       if (this.appender instanceof Filterable && ((Filterable)this.appender).isFiltered(event)) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/*  99 */         this.appender.append(event);
/* 100 */       } catch (RuntimeException ex) {
/* 101 */         this.appender.getHandler().error("An exception occurred processing Appender " + this.appender.getName(), ex);
/* 102 */         if (!this.appender.ignoreExceptions()) {
/* 103 */           throw ex;
/*     */         }
/* 105 */       } catch (Exception ex) {
/* 106 */         this.appender.getHandler().error("An exception occurred processing Appender " + this.appender.getName(), ex);
/* 107 */         if (!this.appender.ignoreExceptions()) {
/* 108 */           throw new AppenderLoggingException(ex);
/*     */         }
/*     */       } 
/*     */     } finally {
/* 112 */       this.recursive.set(null);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\AppenderControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */