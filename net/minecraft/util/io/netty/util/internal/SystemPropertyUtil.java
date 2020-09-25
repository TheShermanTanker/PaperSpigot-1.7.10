/*     */ package net.minecraft.util.io.netty.util.internal;
/*     */ 
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SystemPropertyUtil
/*     */ {
/*     */   private static boolean initializedLogger;
/*  36 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SystemPropertyUtil.class); private static boolean loggedException; private static final Pattern INTEGER_PATTERN;
/*  37 */   static { initializedLogger = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     INTEGER_PATTERN = Pattern.compile("-?[0-9]+"); }
/*     */ 
/*     */   
/*     */   public static boolean contains(String key) {
/*     */     return (get(key) != null);
/*     */   }
/*     */   
/*     */   public static String get(String key) {
/*     */     return get(key, null);
/*     */   }
/*     */   
/*     */   public static int getInt(String key, int def) {
/* 139 */     String value = get(key);
/* 140 */     if (value == null) {
/* 141 */       return def;
/*     */     }
/*     */     
/* 144 */     value = value.trim().toLowerCase();
/* 145 */     if (INTEGER_PATTERN.matcher(value).matches()) {
/*     */       try {
/* 147 */         return Integer.parseInt(value);
/* 148 */       } catch (Exception e) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 153 */     log("Unable to parse the integer system property '" + key + "':" + value + " - " + "using the default value: " + def);
/*     */ 
/*     */ 
/*     */     
/* 157 */     return def;
/*     */   } public static String get(String key, String def) { if (key == null)
/*     */       throw new NullPointerException("key");  if (key.isEmpty())
/*     */       throw new IllegalArgumentException("key must not be empty.");  String value = null; try {
/*     */       value = System.getProperty(key);
/*     */     } catch (Exception e) {
/*     */       if (!loggedException) {
/*     */         log("Unable to retrieve a system property '" + key + "'; default values will be used.", e);
/*     */         loggedException = true;
/*     */       } 
/*     */     } 
/*     */     if (value == null)
/*     */       return def; 
/* 170 */     return value; } public static long getLong(String key, long def) { String value = get(key);
/* 171 */     if (value == null) {
/* 172 */       return def;
/*     */     }
/*     */     
/* 175 */     value = value.trim().toLowerCase();
/* 176 */     if (INTEGER_PATTERN.matcher(value).matches()) {
/*     */       try {
/* 178 */         return Long.parseLong(value);
/* 179 */       } catch (Exception e) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 184 */     log("Unable to parse the long integer system property '" + key + "':" + value + " - " + "using the default value: " + def);
/*     */ 
/*     */ 
/*     */     
/* 188 */     return def; }
/*     */   public static boolean getBoolean(String key, boolean def) { String value = get(key); if (value == null) return def;  value = value.trim().toLowerCase(); if (value.isEmpty())
/*     */       return true;  if ("true".equals(value) || "yes".equals(value) || "1".equals(value))
/*     */       return true;  if ("false".equals(value) || "no".equals(value) || "0".equals(value))
/* 192 */       return false;  log("Unable to parse the boolean system property '" + key + "':" + value + " - " + "using the default value: " + def); return def; } private static void log(String msg) { if (initializedLogger) {
/* 193 */       logger.warn(msg);
/*     */     } else {
/*     */       
/* 196 */       Logger.getLogger(SystemPropertyUtil.class.getName()).log(Level.WARNING, msg);
/*     */     }  }
/*     */ 
/*     */   
/*     */   private static void log(String msg, Exception e) {
/* 201 */     if (initializedLogger) {
/* 202 */       logger.warn(msg, e);
/*     */     } else {
/*     */       
/* 205 */       Logger.getLogger(SystemPropertyUtil.class.getName()).log(Level.WARNING, msg, e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\internal\SystemPropertyUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */