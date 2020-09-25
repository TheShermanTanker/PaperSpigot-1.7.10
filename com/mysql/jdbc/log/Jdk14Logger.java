/*     */ package com.mysql.jdbc.log;
/*     */ 
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class Jdk14Logger
/*     */   implements Log
/*     */ {
/*  41 */   private static final Level DEBUG = Level.FINE;
/*     */   
/*  43 */   private static final Level ERROR = Level.SEVERE;
/*     */   
/*  45 */   private static final Level FATAL = Level.SEVERE;
/*     */   
/*  47 */   private static final Level INFO = Level.INFO;
/*     */   
/*  49 */   private static final Level TRACE = Level.FINEST;
/*     */   
/*  51 */   private static final Level WARN = Level.WARNING;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   protected Logger jdkLogger = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Jdk14Logger(String name) {
/*  65 */     this.jdkLogger = Logger.getLogger(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDebugEnabled() {
/*  72 */     return this.jdkLogger.isLoggable(Level.FINE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isErrorEnabled() {
/*  79 */     return this.jdkLogger.isLoggable(Level.SEVERE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFatalEnabled() {
/*  86 */     return this.jdkLogger.isLoggable(Level.SEVERE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInfoEnabled() {
/*  93 */     return this.jdkLogger.isLoggable(Level.INFO);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTraceEnabled() {
/* 100 */     return this.jdkLogger.isLoggable(Level.FINEST);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWarnEnabled() {
/* 107 */     return this.jdkLogger.isLoggable(Level.WARNING);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logDebug(Object message) {
/* 117 */     logInternal(DEBUG, message, null);
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
/*     */   public void logDebug(Object message, Throwable exception) {
/* 129 */     logInternal(DEBUG, message, exception);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logError(Object message) {
/* 139 */     logInternal(ERROR, message, null);
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
/*     */   public void logError(Object message, Throwable exception) {
/* 151 */     logInternal(ERROR, message, exception);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logFatal(Object message) {
/* 161 */     logInternal(FATAL, message, null);
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
/*     */   public void logFatal(Object message, Throwable exception) {
/* 173 */     logInternal(FATAL, message, exception);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logInfo(Object message) {
/* 183 */     logInternal(INFO, message, null);
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
/*     */   public void logInfo(Object message, Throwable exception) {
/* 195 */     logInternal(INFO, message, exception);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logTrace(Object message) {
/* 205 */     logInternal(TRACE, message, null);
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
/*     */   public void logTrace(Object message, Throwable exception) {
/* 217 */     logInternal(TRACE, message, exception);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logWarn(Object message) {
/* 227 */     logInternal(WARN, message, null);
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
/*     */   public void logWarn(Object message, Throwable exception) {
/* 239 */     logInternal(WARN, message, exception);
/*     */   }
/*     */   
/*     */   private static final int findCallerStackDepth(StackTraceElement[] stackTrace) {
/* 243 */     int numFrames = stackTrace.length;
/*     */     
/* 245 */     for (int i = 0; i < numFrames; i++) {
/* 246 */       String callerClassName = stackTrace[i].getClassName();
/*     */       
/* 248 */       if (!callerClassName.startsWith("com.mysql.jdbc") || callerClassName.startsWith("com.mysql.jdbc.compliance"))
/*     */       {
/* 250 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 254 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logInternal(Level level, Object msg, Throwable exception) {
/* 263 */     if (this.jdkLogger.isLoggable(level)) {
/* 264 */       String messageAsString = null;
/* 265 */       String callerMethodName = "N/A";
/* 266 */       String callerClassName = "N/A";
/* 267 */       int lineNumber = 0;
/* 268 */       String fileName = "N/A";
/*     */       
/* 270 */       if (msg instanceof com.mysql.jdbc.profiler.ProfilerEvent) {
/* 271 */         messageAsString = LogUtils.expandProfilerEventIfNecessary(msg).toString();
/*     */       } else {
/*     */         
/* 274 */         Throwable locationException = new Throwable();
/* 275 */         StackTraceElement[] locations = locationException.getStackTrace();
/*     */ 
/*     */         
/* 278 */         int frameIdx = findCallerStackDepth(locations);
/*     */         
/* 280 */         if (frameIdx != 0) {
/* 281 */           callerClassName = locations[frameIdx].getClassName();
/* 282 */           callerMethodName = locations[frameIdx].getMethodName();
/* 283 */           lineNumber = locations[frameIdx].getLineNumber();
/* 284 */           fileName = locations[frameIdx].getFileName();
/*     */         } 
/*     */         
/* 287 */         messageAsString = String.valueOf(msg);
/*     */       } 
/*     */       
/* 290 */       if (exception == null) {
/* 291 */         this.jdkLogger.logp(level, callerClassName, callerMethodName, messageAsString);
/*     */       } else {
/*     */         
/* 294 */         this.jdkLogger.logp(level, callerClassName, callerMethodName, messageAsString, exception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\log\Jdk14Logger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */