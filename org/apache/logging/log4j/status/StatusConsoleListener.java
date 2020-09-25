/*     */ package org.apache.logging.log4j.status;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatusConsoleListener
/*     */   implements StatusListener
/*     */ {
/*     */   private static final String STATUS_LEVEL = "org.apache.logging.log4j.StatusLevel";
/*  31 */   private Level level = Level.FATAL;
/*     */   
/*  33 */   private String[] filters = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private final PrintStream stream;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatusConsoleListener() {
/*  43 */     String str = PropertiesUtil.getProperties().getStringProperty("org.apache.logging.log4j.StatusLevel");
/*  44 */     if (str != null) {
/*  45 */       this.level = Level.toLevel(str, Level.FATAL);
/*     */     }
/*  47 */     this.stream = System.out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatusConsoleListener(Level level) {
/*  55 */     this.level = level;
/*  56 */     this.stream = System.out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatusConsoleListener(Level level, PrintStream stream) {
/*  65 */     this.level = level;
/*  66 */     this.stream = stream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLevel(Level level) {
/*  74 */     this.level = level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Level getStatusLevel() {
/*  83 */     return this.level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(StatusData data) {
/*  92 */     if (!filtered(data)) {
/*  93 */       this.stream.println(data.getFormattedStatus());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFilters(String... filters) {
/* 102 */     this.filters = filters;
/*     */   }
/*     */   
/*     */   private boolean filtered(StatusData data) {
/* 106 */     if (this.filters == null) {
/* 107 */       return false;
/*     */     }
/* 109 */     String caller = data.getStackTraceElement().getClassName();
/* 110 */     for (String filter : this.filters) {
/* 111 */       if (caller.startsWith(filter)) {
/* 112 */         return true;
/*     */       }
/*     */     } 
/* 115 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\status\StatusConsoleListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */