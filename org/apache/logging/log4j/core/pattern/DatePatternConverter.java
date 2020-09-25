/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "DatePatternConverter", category = "Converter")
/*     */ @ConverterKeys({"d", "date"})
/*     */ public final class DatePatternConverter
/*     */   extends LogEventPatternConverter
/*     */   implements ArrayPatternConverter
/*     */ {
/*     */   private static final String ABSOLUTE_FORMAT = "ABSOLUTE";
/*     */   private static final String COMPACT_FORMAT = "COMPACT";
/*     */   private static final String ABSOLUTE_TIME_PATTERN = "HH:mm:ss,SSS";
/*     */   private static final String DATE_AND_TIME_FORMAT = "DATE";
/*     */   private static final String DATE_AND_TIME_PATTERN = "dd MMM yyyy HH:mm:ss,SSS";
/*     */   private static final String ISO8601_FORMAT = "ISO8601";
/*     */   private static final String ISO8601_BASIC_FORMAT = "ISO8601_BASIC";
/*     */   private static final String ISO8601_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";
/*     */   private static final String ISO8601_BASIC_PATTERN = "yyyyMMdd HHmmss,SSS";
/*     */   private static final String COMPACT_PATTERN = "yyyyMMddHHmmssSSS";
/*     */   private String cachedDate;
/*     */   private long lastTimestamp;
/*     */   private final SimpleDateFormat simpleFormat;
/*     */   
/*     */   private DatePatternConverter(String[] options) {
/*  99 */     super("Date", "date");
/*     */     
/*     */     String patternOption, pattern;
/*     */     SimpleDateFormat simpleDateFormat;
/* 103 */     if (options == null || options.length == 0) {
/*     */ 
/*     */       
/* 106 */       patternOption = null;
/*     */     } else {
/* 108 */       patternOption = options[0];
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 113 */     if (patternOption == null || patternOption.equalsIgnoreCase("ISO8601")) {
/* 114 */       pattern = "yyyy-MM-dd HH:mm:ss,SSS";
/* 115 */     } else if (patternOption.equalsIgnoreCase("ISO8601_BASIC")) {
/* 116 */       pattern = "yyyyMMdd HHmmss,SSS";
/* 117 */     } else if (patternOption.equalsIgnoreCase("ABSOLUTE")) {
/* 118 */       pattern = "HH:mm:ss,SSS";
/* 119 */     } else if (patternOption.equalsIgnoreCase("DATE")) {
/* 120 */       pattern = "dd MMM yyyy HH:mm:ss,SSS";
/* 121 */     } else if (patternOption.equalsIgnoreCase("COMPACT")) {
/* 122 */       pattern = "yyyyMMddHHmmssSSS";
/*     */     } else {
/* 124 */       pattern = patternOption;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 130 */       simpleDateFormat = new SimpleDateFormat(pattern);
/* 131 */     } catch (IllegalArgumentException e) {
/* 132 */       LOGGER.warn("Could not instantiate SimpleDateFormat with pattern " + patternOption, e);
/*     */ 
/*     */       
/* 135 */       simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
/*     */     } 
/*     */ 
/*     */     
/* 139 */     if (options != null && options.length > 1) {
/* 140 */       TimeZone tz = TimeZone.getTimeZone(options[1]);
/* 141 */       simpleDateFormat.setTimeZone(tz);
/*     */     } 
/* 143 */     this.simpleFormat = simpleDateFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DatePatternConverter newInstance(String[] options) {
/* 153 */     return new DatePatternConverter(options);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void format(LogEvent event, StringBuilder output) {
/* 161 */     long timestamp = event.getMillis();
/*     */     
/* 163 */     synchronized (this) {
/* 164 */       if (timestamp != this.lastTimestamp) {
/* 165 */         this.lastTimestamp = timestamp;
/* 166 */         this.cachedDate = this.simpleFormat.format(Long.valueOf(timestamp));
/*     */       } 
/*     */     } 
/* 169 */     output.append(this.cachedDate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void format(StringBuilder toAppendTo, Object... objects) {
/* 174 */     for (Object obj : objects) {
/* 175 */       if (obj instanceof Date) {
/* 176 */         format(obj, toAppendTo);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void format(Object obj, StringBuilder output) {
/* 187 */     if (obj instanceof Date) {
/* 188 */       format((Date)obj, output);
/*     */     }
/*     */     
/* 191 */     super.format(obj, output);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void format(Date date, StringBuilder toAppendTo) {
/* 201 */     synchronized (this) {
/* 202 */       toAppendTo.append(this.simpleFormat.format(Long.valueOf(date.getTime())));
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getPattern() {
/* 207 */     return this.simpleFormat.toPattern();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\DatePatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */