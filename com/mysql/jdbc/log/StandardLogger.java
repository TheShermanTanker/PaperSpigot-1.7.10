/*     */ package com.mysql.jdbc.log;
/*     */ 
/*     */ import com.mysql.jdbc.Util;
/*     */ import java.util.Date;
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
/*     */ public class StandardLogger
/*     */   implements Log
/*     */ {
/*     */   private static final int FATAL = 0;
/*     */   private static final int ERROR = 1;
/*     */   private static final int WARN = 2;
/*     */   private static final int INFO = 3;
/*     */   private static final int DEBUG = 4;
/*     */   private static final int TRACE = 5;
/*  54 */   public static StringBuffer bufferedLog = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean logLocationInfo = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StandardLogger(String name) {
/*  65 */     this(name, false);
/*     */   }
/*     */   
/*     */   public StandardLogger(String name, boolean logLocationInfo) {
/*  69 */     this.logLocationInfo = logLocationInfo;
/*     */   }
/*     */   
/*     */   public static void saveLogsToBuffer() {
/*  73 */     if (bufferedLog == null) {
/*  74 */       bufferedLog = new StringBuffer();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDebugEnabled() {
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isErrorEnabled() {
/*  89 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFatalEnabled() {
/*  96 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInfoEnabled() {
/* 103 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTraceEnabled() {
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWarnEnabled() {
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logDebug(Object message) {
/* 127 */     logInternal(4, message, null);
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
/* 139 */     logInternal(4, message, exception);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logError(Object message) {
/* 149 */     logInternal(1, message, null);
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
/* 161 */     logInternal(1, message, exception);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logFatal(Object message) {
/* 171 */     logInternal(0, message, null);
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
/* 183 */     logInternal(0, message, exception);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logInfo(Object message) {
/* 193 */     logInternal(3, message, null);
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
/* 205 */     logInternal(3, message, exception);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logTrace(Object message) {
/* 215 */     logInternal(5, message, null);
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
/* 227 */     logInternal(5, message, exception);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logWarn(Object message) {
/* 237 */     logInternal(2, message, null);
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
/* 249 */     logInternal(2, message, exception);
/*     */   }
/*     */   
/*     */   private void logInternal(int level, Object msg, Throwable exception) {
/* 253 */     StringBuffer msgBuf = new StringBuffer();
/* 254 */     msgBuf.append((new Date()).toString());
/* 255 */     msgBuf.append(" ");
/*     */     
/* 257 */     switch (level) {
/*     */       case 0:
/* 259 */         msgBuf.append("FATAL: ");
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 264 */         msgBuf.append("ERROR: ");
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 269 */         msgBuf.append("WARN: ");
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 274 */         msgBuf.append("INFO: ");
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 279 */         msgBuf.append("DEBUG: ");
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 284 */         msgBuf.append("TRACE: ");
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 289 */     if (msg instanceof com.mysql.jdbc.profiler.ProfilerEvent) {
/* 290 */       msgBuf.append(LogUtils.expandProfilerEventIfNecessary(msg));
/*     */     } else {
/*     */       
/* 293 */       if (this.logLocationInfo && level != 5) {
/* 294 */         Throwable locationException = new Throwable();
/* 295 */         msgBuf.append(LogUtils.findCallingClassAndMethod(locationException));
/*     */         
/* 297 */         msgBuf.append(" ");
/*     */       } 
/*     */       
/* 300 */       if (msg != null) {
/* 301 */         msgBuf.append(String.valueOf(msg));
/*     */       }
/*     */     } 
/*     */     
/* 305 */     if (exception != null) {
/* 306 */       msgBuf.append("\n");
/* 307 */       msgBuf.append("\n");
/* 308 */       msgBuf.append("EXCEPTION STACK TRACE:");
/* 309 */       msgBuf.append("\n");
/* 310 */       msgBuf.append("\n");
/* 311 */       msgBuf.append(Util.stackTraceToString(exception));
/*     */     } 
/*     */     
/* 314 */     String messageAsString = msgBuf.toString();
/*     */     
/* 316 */     System.err.println(messageAsString);
/*     */     
/* 318 */     if (bufferedLog != null)
/* 319 */       bufferedLog.append(messageAsString); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\log\StandardLogger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */