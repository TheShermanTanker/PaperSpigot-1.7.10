/*     */ package org.apache.logging.log4j.core.appender.rolling;
/*     */ 
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Locale;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "SizeBasedTriggeringPolicy", category = "Core", printObject = true)
/*     */ public class SizeBasedTriggeringPolicy
/*     */   implements TriggeringPolicy
/*     */ {
/*  40 */   protected static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */ 
/*     */   
/*     */   private static final long KB = 1024L;
/*     */ 
/*     */   
/*     */   private static final long MB = 1048576L;
/*     */ 
/*     */   
/*     */   private static final long GB = 1073741824L;
/*     */ 
/*     */   
/*     */   private static final long MAX_FILE_SIZE = 10485760L;
/*     */ 
/*     */   
/*  55 */   private static final Pattern VALUE_PATTERN = Pattern.compile("([0-9]+([\\.,][0-9]+)?)\\s*(|K|M|G)B?", 2);
/*     */ 
/*     */   
/*     */   private final long maxFileSize;
/*     */ 
/*     */   
/*     */   private RollingFileManager manager;
/*     */ 
/*     */ 
/*     */   
/*     */   protected SizeBasedTriggeringPolicy() {
/*  66 */     this.maxFileSize = 10485760L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SizeBasedTriggeringPolicy(long maxFileSize) {
/*  75 */     this.maxFileSize = maxFileSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(RollingFileManager manager) {
/*  84 */     this.manager = manager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTriggeringEvent(LogEvent event) {
/*  95 */     return (this.manager.getFileSize() > this.maxFileSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 100 */     return "SizeBasedTriggeringPolicy(size=" + this.maxFileSize + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @PluginFactory
/*     */   public static SizeBasedTriggeringPolicy createPolicy(@PluginAttribute("size") String size) {
/* 111 */     long maxSize = (size == null) ? 10485760L : valueOf(size);
/* 112 */     return new SizeBasedTriggeringPolicy(maxSize);
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
/*     */   private static long valueOf(String string) {
/* 124 */     Matcher matcher = VALUE_PATTERN.matcher(string);
/*     */ 
/*     */     
/* 127 */     if (matcher.matches()) {
/*     */       
/*     */       try {
/* 130 */         long value = NumberFormat.getNumberInstance(Locale.getDefault()).parse(matcher.group(1)).longValue();
/*     */ 
/*     */ 
/*     */         
/* 134 */         String units = matcher.group(3);
/*     */         
/* 136 */         if (units.equalsIgnoreCase(""))
/* 137 */           return value; 
/* 138 */         if (units.equalsIgnoreCase("K"))
/* 139 */           return value * 1024L; 
/* 140 */         if (units.equalsIgnoreCase("M"))
/* 141 */           return value * 1048576L; 
/* 142 */         if (units.equalsIgnoreCase("G")) {
/* 143 */           return value * 1073741824L;
/*     */         }
/* 145 */         LOGGER.error("Units not recognized: " + string);
/* 146 */         return 10485760L;
/*     */       }
/* 148 */       catch (ParseException e) {
/* 149 */         LOGGER.error("Unable to parse numeric part: " + string, e);
/* 150 */         return 10485760L;
/*     */       } 
/*     */     }
/* 153 */     LOGGER.error("Unable to parse bytes: " + string);
/* 154 */     return 10485760L;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\SizeBasedTriggeringPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */