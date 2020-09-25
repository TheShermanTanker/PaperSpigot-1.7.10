/*     */ package org.apache.logging.log4j.core.appender.rolling;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.impl.Log4jLogEvent;
/*     */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
/*     */ import org.apache.logging.log4j.core.pattern.ArrayPatternConverter;
/*     */ import org.apache.logging.log4j.core.pattern.DatePatternConverter;
/*     */ import org.apache.logging.log4j.core.pattern.FormattingInfo;
/*     */ import org.apache.logging.log4j.core.pattern.PatternConverter;
/*     */ import org.apache.logging.log4j.core.pattern.PatternParser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PatternProcessor
/*     */ {
/*     */   private static final String KEY = "FileConverter";
/*     */   private static final char YEAR_CHAR = 'y';
/*     */   private static final char MONTH_CHAR = 'M';
/*  42 */   private static final char[] WEEK_CHARS = new char[] { 'w', 'W' };
/*  43 */   private static final char[] DAY_CHARS = new char[] { 'D', 'd', 'F', 'E' };
/*  44 */   private static final char[] HOUR_CHARS = new char[] { 'H', 'K', 'h', 'k' };
/*     */   
/*     */   private static final char MINUTE_CHAR = 'm';
/*     */   
/*     */   private static final char SECOND_CHAR = 's';
/*     */   private static final char MILLIS_CHAR = 'S';
/*     */   private final ArrayPatternConverter[] patternConverters;
/*     */   private final FormattingInfo[] patternFields;
/*  52 */   private long prevFileTime = 0L;
/*  53 */   private long nextFileTime = 0L;
/*     */   
/*  55 */   private RolloverFrequency frequency = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PatternProcessor(String pattern) {
/*  62 */     PatternParser parser = createPatternParser();
/*  63 */     List<PatternConverter> converters = new ArrayList<PatternConverter>();
/*  64 */     List<FormattingInfo> fields = new ArrayList<FormattingInfo>();
/*  65 */     parser.parse(pattern, converters, fields);
/*  66 */     FormattingInfo[] infoArray = new FormattingInfo[fields.size()];
/*  67 */     this.patternFields = fields.<FormattingInfo>toArray(infoArray);
/*  68 */     ArrayPatternConverter[] converterArray = new ArrayPatternConverter[converters.size()];
/*  69 */     this.patternConverters = converters.<ArrayPatternConverter>toArray(converterArray);
/*     */     
/*  71 */     for (ArrayPatternConverter converter : this.patternConverters) {
/*  72 */       if (converter instanceof DatePatternConverter) {
/*  73 */         DatePatternConverter dateConverter = (DatePatternConverter)converter;
/*  74 */         this.frequency = calculateFrequency(dateConverter.getPattern());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getNextTime(long current, int increment, boolean modulus) {
/*  87 */     this.prevFileTime = this.nextFileTime;
/*     */ 
/*     */     
/*  90 */     if (this.frequency == null) {
/*  91 */       throw new IllegalStateException("Pattern does not contain a date");
/*     */     }
/*  93 */     Calendar currentCal = Calendar.getInstance();
/*  94 */     currentCal.setTimeInMillis(current);
/*  95 */     Calendar cal = Calendar.getInstance();
/*  96 */     cal.set(currentCal.get(1), 0, 1, 0, 0, 0);
/*  97 */     cal.set(14, 0);
/*  98 */     if (this.frequency == RolloverFrequency.ANNUALLY) {
/*  99 */       increment(cal, 1, increment, modulus);
/* 100 */       long l = cal.getTimeInMillis();
/* 101 */       cal.add(1, -1);
/* 102 */       this.nextFileTime = cal.getTimeInMillis();
/* 103 */       return l;
/*     */     } 
/* 105 */     if (this.frequency == RolloverFrequency.MONTHLY) {
/* 106 */       increment(cal, 2, increment, modulus);
/* 107 */       long l = cal.getTimeInMillis();
/* 108 */       cal.add(2, -1);
/* 109 */       this.nextFileTime = cal.getTimeInMillis();
/* 110 */       return l;
/*     */     } 
/* 112 */     if (this.frequency == RolloverFrequency.WEEKLY) {
/* 113 */       increment(cal, 3, increment, modulus);
/* 114 */       long l = cal.getTimeInMillis();
/* 115 */       cal.add(3, -1);
/* 116 */       this.nextFileTime = cal.getTimeInMillis();
/* 117 */       return l;
/*     */     } 
/* 119 */     cal.set(6, currentCal.get(6));
/* 120 */     if (this.frequency == RolloverFrequency.DAILY) {
/* 121 */       increment(cal, 6, increment, modulus);
/* 122 */       long l = cal.getTimeInMillis();
/* 123 */       cal.add(6, -1);
/* 124 */       this.nextFileTime = cal.getTimeInMillis();
/* 125 */       return l;
/*     */     } 
/* 127 */     cal.set(10, currentCal.get(10));
/* 128 */     if (this.frequency == RolloverFrequency.HOURLY) {
/* 129 */       increment(cal, 10, increment, modulus);
/* 130 */       long l = cal.getTimeInMillis();
/* 131 */       cal.add(10, -1);
/* 132 */       this.nextFileTime = cal.getTimeInMillis();
/* 133 */       return l;
/*     */     } 
/* 135 */     cal.set(12, currentCal.get(12));
/* 136 */     if (this.frequency == RolloverFrequency.EVERY_MINUTE) {
/* 137 */       increment(cal, 12, increment, modulus);
/* 138 */       long l = cal.getTimeInMillis();
/* 139 */       cal.add(12, -1);
/* 140 */       this.nextFileTime = cal.getTimeInMillis();
/* 141 */       return l;
/*     */     } 
/* 143 */     cal.set(13, currentCal.get(13));
/* 144 */     if (this.frequency == RolloverFrequency.EVERY_SECOND) {
/* 145 */       increment(cal, 13, increment, modulus);
/* 146 */       long l = cal.getTimeInMillis();
/* 147 */       cal.add(13, -1);
/* 148 */       this.nextFileTime = cal.getTimeInMillis();
/* 149 */       return l;
/*     */     } 
/* 151 */     increment(cal, 14, increment, modulus);
/* 152 */     long nextTime = cal.getTimeInMillis();
/* 153 */     cal.add(14, -1);
/* 154 */     this.nextFileTime = cal.getTimeInMillis();
/* 155 */     return nextTime;
/*     */   }
/*     */   
/*     */   private void increment(Calendar cal, int type, int increment, boolean modulate) {
/* 159 */     int interval = modulate ? (increment - cal.get(type) % increment) : increment;
/* 160 */     cal.add(type, interval);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void formatFileName(StringBuilder buf, Object obj) {
/* 169 */     long time = (this.prevFileTime == 0L) ? System.currentTimeMillis() : this.prevFileTime;
/* 170 */     formatFileName(buf, new Object[] { new Date(time), obj });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void formatFileName(StrSubstitutor subst, StringBuilder buf, Object obj) {
/* 180 */     long time = (this.prevFileTime == 0L) ? System.currentTimeMillis() : this.prevFileTime;
/* 181 */     formatFileName(buf, new Object[] { new Date(time), obj });
/* 182 */     Log4jLogEvent log4jLogEvent = new Log4jLogEvent(time);
/* 183 */     String fileName = subst.replace((LogEvent)log4jLogEvent, buf);
/* 184 */     buf.setLength(0);
/* 185 */     buf.append(fileName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void formatFileName(StringBuilder buf, Object... objects) {
/* 194 */     for (int i = 0; i < this.patternConverters.length; i++) {
/* 195 */       int fieldStart = buf.length();
/* 196 */       this.patternConverters[i].format(buf, objects);
/*     */       
/* 198 */       if (this.patternFields[i] != null) {
/* 199 */         this.patternFields[i].format(fieldStart, buf);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private RolloverFrequency calculateFrequency(String pattern) {
/* 205 */     if (patternContains(pattern, 'S')) {
/* 206 */       return RolloverFrequency.EVERY_MILLISECOND;
/*     */     }
/* 208 */     if (patternContains(pattern, 's')) {
/* 209 */       return RolloverFrequency.EVERY_SECOND;
/*     */     }
/* 211 */     if (patternContains(pattern, 'm')) {
/* 212 */       return RolloverFrequency.EVERY_MINUTE;
/*     */     }
/* 214 */     if (patternContains(pattern, HOUR_CHARS)) {
/* 215 */       return RolloverFrequency.HOURLY;
/*     */     }
/* 217 */     if (patternContains(pattern, DAY_CHARS)) {
/* 218 */       return RolloverFrequency.DAILY;
/*     */     }
/* 220 */     if (patternContains(pattern, WEEK_CHARS)) {
/* 221 */       return RolloverFrequency.WEEKLY;
/*     */     }
/* 223 */     if (patternContains(pattern, 'M')) {
/* 224 */       return RolloverFrequency.MONTHLY;
/*     */     }
/* 226 */     if (patternContains(pattern, 'y')) {
/* 227 */       return RolloverFrequency.ANNUALLY;
/*     */     }
/* 229 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private PatternParser createPatternParser() {
/* 234 */     return new PatternParser(null, "FileConverter", null);
/*     */   }
/*     */   
/*     */   private boolean patternContains(String pattern, char... chars) {
/* 238 */     for (char character : chars) {
/* 239 */       if (patternContains(pattern, character)) {
/* 240 */         return true;
/*     */       }
/*     */     } 
/* 243 */     return false;
/*     */   }
/*     */   
/*     */   private boolean patternContains(String pattern, char character) {
/* 247 */     return (pattern.indexOf(character) >= 0);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\PatternProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */