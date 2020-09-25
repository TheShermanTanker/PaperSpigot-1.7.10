/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.layout.PatternLayout;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "highlight", category = "Converter")
/*     */ @ConverterKeys({"highlight"})
/*     */ public final class HighlightConverter
/*     */   extends LogEventPatternConverter
/*     */ {
/*  75 */   private static final EnumMap<Level, String> DEFAULT_STYLES = new EnumMap<Level, String>(Level.class);
/*     */   
/*  77 */   private static final EnumMap<Level, String> LOGBACK_STYLES = new EnumMap<Level, String>(Level.class);
/*     */   
/*     */   private static final String STYLE_KEY = "STYLE";
/*     */   
/*     */   private static final String STYLE_KEY_DEFAULT = "DEFAULT";
/*     */   
/*     */   private static final String STYLE_KEY_LOGBACK = "LOGBACK";
/*     */   
/*  85 */   private static final Map<String, EnumMap<Level, String>> STYLES = new HashMap<String, EnumMap<Level, String>>();
/*     */   private final EnumMap<Level, String> levelStyles;
/*     */   
/*     */   static {
/*  89 */     DEFAULT_STYLES.put(Level.FATAL, AnsiEscape.createSequence(new String[] { "BRIGHT", "RED" }));
/*  90 */     DEFAULT_STYLES.put(Level.ERROR, AnsiEscape.createSequence(new String[] { "BRIGHT", "RED" }));
/*  91 */     DEFAULT_STYLES.put(Level.WARN, AnsiEscape.createSequence(new String[] { "YELLOW" }));
/*  92 */     DEFAULT_STYLES.put(Level.INFO, AnsiEscape.createSequence(new String[] { "GREEN" }));
/*  93 */     DEFAULT_STYLES.put(Level.DEBUG, AnsiEscape.createSequence(new String[] { "CYAN" }));
/*  94 */     DEFAULT_STYLES.put(Level.TRACE, AnsiEscape.createSequence(new String[] { "BLACK" }));
/*     */     
/*  96 */     LOGBACK_STYLES.put(Level.FATAL, AnsiEscape.createSequence(new String[] { "BLINK", "BRIGHT", "RED" }));
/*  97 */     LOGBACK_STYLES.put(Level.ERROR, AnsiEscape.createSequence(new String[] { "BRIGHT", "RED" }));
/*  98 */     LOGBACK_STYLES.put(Level.WARN, AnsiEscape.createSequence(new String[] { "RED" }));
/*  99 */     LOGBACK_STYLES.put(Level.INFO, AnsiEscape.createSequence(new String[] { "BLUE" }));
/* 100 */     LOGBACK_STYLES.put(Level.DEBUG, AnsiEscape.createSequence((String[])null));
/* 101 */     LOGBACK_STYLES.put(Level.TRACE, AnsiEscape.createSequence((String[])null));
/*     */     
/* 103 */     STYLES.put("DEFAULT", DEFAULT_STYLES);
/* 104 */     STYLES.put("LOGBACK", LOGBACK_STYLES);
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
/*     */ 
/*     */   
/*     */   private final List<PatternFormatter> patternFormatters;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static EnumMap<Level, String> createLevelStyleMap(String[] options) {
/* 132 */     if (options.length < 2) {
/* 133 */       return DEFAULT_STYLES;
/*     */     }
/* 135 */     Map<String, String> styles = AnsiEscape.createMap(options[1], new String[] { "STYLE" });
/* 136 */     EnumMap<Level, String> levelStyles = new EnumMap<Level, String>(DEFAULT_STYLES);
/* 137 */     for (Map.Entry<String, String> entry : styles.entrySet()) {
/* 138 */       String key = ((String)entry.getKey()).toUpperCase(Locale.ENGLISH);
/* 139 */       String value = entry.getValue();
/* 140 */       if ("STYLE".equalsIgnoreCase(key)) {
/* 141 */         EnumMap<Level, String> enumMap = STYLES.get(value.toUpperCase(Locale.ENGLISH));
/* 142 */         if (enumMap == null) {
/* 143 */           LOGGER.error("Unknown level style: " + value + ". Use one of " + Arrays.toString(STYLES.keySet().toArray()));
/*     */           continue;
/*     */         } 
/* 146 */         levelStyles.putAll(enumMap);
/*     */         continue;
/*     */       } 
/* 149 */       Level level = Level.valueOf(key);
/* 150 */       if (level == null) {
/* 151 */         LOGGER.error("Unknown level name: " + key + ". Use one of " + Arrays.toString(DEFAULT_STYLES.keySet().toArray()));
/*     */         continue;
/*     */       } 
/* 154 */       levelStyles.put(level, value);
/*     */     } 
/*     */ 
/*     */     
/* 158 */     return levelStyles;
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
/*     */   public static HighlightConverter newInstance(Configuration config, String[] options) {
/* 170 */     if (options.length < 1) {
/* 171 */       LOGGER.error("Incorrect number of options on style. Expected at least 1, received " + options.length);
/* 172 */       return null;
/*     */     } 
/* 174 */     if (options[0] == null) {
/* 175 */       LOGGER.error("No pattern supplied on style");
/* 176 */       return null;
/*     */     } 
/* 178 */     PatternParser parser = PatternLayout.createPatternParser(config);
/* 179 */     List<PatternFormatter> formatters = parser.parse(options[0]);
/* 180 */     return new HighlightConverter(formatters, createLevelStyleMap(options));
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
/*     */   
/*     */   private HighlightConverter(List<PatternFormatter> patternFormatters, EnumMap<Level, String> levelStyles) {
/* 194 */     super("style", "style");
/* 195 */     this.patternFormatters = patternFormatters;
/* 196 */     this.levelStyles = levelStyles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void format(LogEvent event, StringBuilder toAppendTo) {
/* 204 */     StringBuilder buf = new StringBuilder();
/* 205 */     for (PatternFormatter formatter : this.patternFormatters) {
/* 206 */       formatter.format(event, buf);
/*     */     }
/*     */     
/* 209 */     if (buf.length() > 0) {
/* 210 */       toAppendTo.append(this.levelStyles.get(event.getLevel())).append(buf.toString()).append(AnsiEscape.getDefaultStyle());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handlesThrowable() {
/* 217 */     for (PatternFormatter formatter : this.patternFormatters) {
/* 218 */       if (formatter.handlesThrowable()) {
/* 219 */         return true;
/*     */       }
/*     */     } 
/* 222 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\HighlightConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */