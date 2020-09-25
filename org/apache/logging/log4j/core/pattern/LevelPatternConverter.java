/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.util.EnumMap;
/*     */ import java.util.Locale;
/*     */ import org.apache.logging.log4j.Level;
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
/*     */ @Plugin(name = "LevelPatternConverter", category = "Converter")
/*     */ @ConverterKeys({"p", "level"})
/*     */ public final class LevelPatternConverter
/*     */   extends LogEventPatternConverter
/*     */ {
/*     */   private static final String OPTION_LENGTH = "length";
/*     */   private static final String OPTION_LOWER = "lowerCase";
/*  38 */   private static final LevelPatternConverter INSTANCE = new LevelPatternConverter(null);
/*     */ 
/*     */   
/*     */   private final EnumMap<Level, String> levelMap;
/*     */ 
/*     */ 
/*     */   
/*     */   private LevelPatternConverter(EnumMap<Level, String> map) {
/*  46 */     super("Level", "level");
/*  47 */     this.levelMap = map;
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
/*     */   public static LevelPatternConverter newInstance(String[] options) {
/*  59 */     if (options == null || options.length == 0) {
/*  60 */       return INSTANCE;
/*     */     }
/*  62 */     EnumMap<Level, String> levelMap = new EnumMap<Level, String>(Level.class);
/*  63 */     int length = Integer.MAX_VALUE;
/*  64 */     boolean lowerCase = false;
/*  65 */     String[] definitions = options[0].split(",");
/*  66 */     for (String def : definitions) {
/*  67 */       String[] pair = def.split("=");
/*  68 */       if (pair == null || pair.length != 2) {
/*  69 */         LOGGER.error("Invalid option {}", new Object[] { def });
/*     */       } else {
/*     */         
/*  72 */         String key = pair[0].trim();
/*  73 */         String value = pair[1].trim();
/*  74 */         if ("length".equalsIgnoreCase(key)) {
/*  75 */           length = Integer.parseInt(value);
/*  76 */         } else if ("lowerCase".equalsIgnoreCase(key)) {
/*  77 */           lowerCase = Boolean.parseBoolean(value);
/*     */         } else {
/*  79 */           Level level = Level.toLevel(key, null);
/*  80 */           if (level == null) {
/*  81 */             LOGGER.error("Invalid Level {}", new Object[] { key });
/*     */           } else {
/*  83 */             levelMap.put(level, value);
/*     */           } 
/*     */         } 
/*     */       } 
/*  87 */     }  if (levelMap.size() == 0 && length == Integer.MAX_VALUE && !lowerCase) {
/*  88 */       return INSTANCE;
/*     */     }
/*  90 */     for (Level level : Level.values()) {
/*  91 */       if (!levelMap.containsKey(level)) {
/*  92 */         String left = left(level, length);
/*  93 */         levelMap.put(level, lowerCase ? left.toLowerCase(Locale.US) : left);
/*     */       } 
/*     */     } 
/*  96 */     return new LevelPatternConverter(levelMap);
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
/*     */   private static String left(Level level, int length) {
/* 110 */     String string = level.toString();
/* 111 */     if (length >= string.length()) {
/* 112 */       return string;
/*     */     }
/* 114 */     return string.substring(0, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void format(LogEvent event, StringBuilder output) {
/* 122 */     output.append((this.levelMap == null) ? event.getLevel().toString() : this.levelMap.get(event.getLevel()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStyleClass(Object e) {
/* 130 */     if (e instanceof LogEvent) {
/* 131 */       Level level = ((LogEvent)e).getLevel();
/*     */       
/* 133 */       switch (level) {
/*     */         case TRACE:
/* 135 */           return "level trace";
/*     */         
/*     */         case DEBUG:
/* 138 */           return "level debug";
/*     */         
/*     */         case INFO:
/* 141 */           return "level info";
/*     */         
/*     */         case WARN:
/* 144 */           return "level warn";
/*     */         
/*     */         case ERROR:
/* 147 */           return "level error";
/*     */         
/*     */         case FATAL:
/* 150 */           return "level fatal";
/*     */       } 
/*     */       
/* 153 */       return "level " + ((LogEvent)e).getLevel().toString();
/*     */     } 
/*     */ 
/*     */     
/* 157 */     return "level";
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\LevelPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */