/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import org.apache.logging.log4j.Level;
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
/*     */ public enum Severity
/*     */ {
/*  38 */   EMERG(0),
/*     */   
/*  40 */   ALERT(1),
/*     */   
/*  42 */   CRITICAL(2),
/*     */   
/*  44 */   ERROR(3),
/*     */   
/*  46 */   WARNING(4),
/*     */   
/*  48 */   NOTICE(5),
/*     */   
/*  50 */   INFO(6),
/*     */   
/*  52 */   DEBUG(7);
/*     */   
/*     */   private final int code;
/*     */   
/*     */   Severity(int code) {
/*  57 */     this.code = code;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCode() {
/*  65 */     return this.code;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEqual(String name) {
/*  74 */     return name().equalsIgnoreCase(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Severity getSeverity(Level level) {
/*  83 */     switch (level) {
/*     */       case ALL:
/*  85 */         return DEBUG;
/*     */       case TRACE:
/*  87 */         return DEBUG;
/*     */       case DEBUG:
/*  89 */         return DEBUG;
/*     */       case INFO:
/*  91 */         return INFO;
/*     */       case WARN:
/*  93 */         return WARNING;
/*     */       case ERROR:
/*  95 */         return ERROR;
/*     */       case FATAL:
/*  97 */         return ALERT;
/*     */       case OFF:
/*  99 */         return EMERG;
/*     */     } 
/* 101 */     return DEBUG;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\Severity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */