/*     */ package org.bukkit.craftbukkit.libs.jline.internal;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Log
/*     */ {
/*     */   public enum Level
/*     */   {
/*  36 */     TRACE,
/*  37 */     DEBUG,
/*  38 */     INFO,
/*  39 */     WARN,
/*  40 */     ERROR;
/*     */   }
/*     */ 
/*     */   
/*  44 */   public static final boolean DEBUG = Boolean.getBoolean(Log.class.getName() + ".debug");
/*     */ 
/*     */   
/*  47 */   public static final boolean TRACE = Boolean.getBoolean(Log.class.getName() + ".trace");
/*     */   
/*  49 */   private static PrintStream output = System.err;
/*     */   
/*     */   public static PrintStream getOutput() {
/*  52 */     return output;
/*     */   }
/*     */   
/*     */   public static void setOutput(PrintStream out) {
/*  56 */     assert out != null;
/*  57 */     output = out;
/*     */   }
/*     */   
/*     */   private static void print(Object message) {
/*  61 */     if (message instanceof Throwable) {
/*  62 */       ((Throwable)message).printStackTrace(output);
/*     */     }
/*  64 */     else if (message.getClass().isArray()) {
/*  65 */       Object[] array = (Object[])message;
/*     */       
/*  67 */       for (int i = 0; i < array.length; i++) {
/*  68 */         output.print(array[i]);
/*  69 */         if (i + 1 < array.length) {
/*  70 */           output.print(",");
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/*  75 */       output.print(message);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void log(Level level, Object[] messages) {
/*  81 */     synchronized (output) {
/*  82 */       output.format("[%s] ", new Object[] { level });
/*     */       
/*  84 */       for (Object message : messages) {
/*  85 */         print(message);
/*     */       }
/*     */       
/*  88 */       output.println();
/*  89 */       output.flush();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void trace(Object... messages) {
/*  94 */     if (TRACE) {
/*  95 */       log(Level.TRACE, messages);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void debug(Object... messages) {
/* 100 */     if (TRACE || DEBUG) {
/* 101 */       log(Level.DEBUG, messages);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void warn(Object... messages) {
/* 106 */     log(Level.WARN, messages);
/*     */   }
/*     */   
/*     */   public static void error(Object... messages) {
/* 110 */     log(Level.ERROR, messages);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\internal\Log.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */