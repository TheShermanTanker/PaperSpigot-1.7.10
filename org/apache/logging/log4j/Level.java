/*     */ package org.apache.logging.log4j;
/*     */ 
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Level
/*     */ {
/*  43 */   OFF(0),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   FATAL(1),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   ERROR(2),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   WARN(3),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   INFO(4),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   DEBUG(5),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   TRACE(6),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   ALL(2147483647);
/*     */   
/*     */   private final int intLevel;
/*     */   
/*     */   Level(int val) {
/*  83 */     this.intLevel = val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Level toLevel(String sArg) {
/*  94 */     return toLevel(sArg, DEBUG);
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
/*     */   public static Level toLevel(String name, Level defaultLevel) {
/* 107 */     if (name == null) {
/* 108 */       return defaultLevel;
/*     */     }
/* 110 */     String cleanLevel = name.toUpperCase(Locale.ENGLISH);
/* 111 */     for (Level level : values()) {
/* 112 */       if (level.name().equals(cleanLevel)) {
/* 113 */         return level;
/*     */       }
/*     */     } 
/* 116 */     return defaultLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAtLeastAsSpecificAs(Level level) {
/* 127 */     return (this.intLevel <= level.intLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAtLeastAsSpecificAs(int level) {
/* 138 */     return (this.intLevel <= level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean lessOrEqual(Level level) {
/* 147 */     return (this.intLevel <= level.intLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean lessOrEqual(int level) {
/* 156 */     return (this.intLevel <= level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int intLevel() {
/* 164 */     return this.intLevel;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\Level.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */