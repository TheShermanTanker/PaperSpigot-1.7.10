/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum AnsiEscape
/*     */ {
/*  36 */   PREFIX("\033["),
/*     */ 
/*     */ 
/*     */   
/*  40 */   SUFFIX("m"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   SEPARATOR(";"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   NORMAL("0"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   BRIGHT("1"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   DIM("2"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   UNDERLINE("3"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   BLINK("5"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   REVERSE("7"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   HIDDEN("8"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   BLACK("30"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   FG_BLACK("30"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   RED("31"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   FG_RED("31"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   GREEN("32"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   FG_GREEN("32"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   YELLOW("33"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   FG_YELLOW("33"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   BLUE("34"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   FG_BLUE("34"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   MAGENTA("35"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   FG_MAGENTA("35"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   CYAN("36"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   FG_CYAN("36"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   WHITE("37"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   FG_WHITE("37"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   DEFAULT("39"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   FG_DEFAULT("39"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   BG_BLACK("40"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   BG_RED("41"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 185 */   BG_GREEN("42"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   BG_YELLOW("43"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   BG_BLUE("44"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   BG_MAGENTA("45"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 205 */   BG_CYAN("46"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   BG_WHITE("47");
/*     */   
/*     */   private static final String WHITESPACE_REGEX = "\\s*";
/*     */   
/*     */   private final String code;
/*     */   
/*     */   AnsiEscape(String code) {
/* 217 */     this.code = code;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDefaultStyle() {
/* 226 */     return PREFIX.getCode() + SUFFIX.getCode();
/*     */   }
/*     */   
/*     */   private static String toRegexSeparator(String separator) {
/* 230 */     return "\\s*" + separator + "\\s*";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCode() {
/* 239 */     return this.code;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, String> createMap(String values, String[] dontEscapeKeys) {
/* 263 */     return createMap(values.split(toRegexSeparator(",")), dontEscapeKeys);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, String> createMap(String[] values, String[] dontEscapeKeys) {
/* 289 */     String[] sortedIgnoreKeys = (dontEscapeKeys != null) ? (String[])dontEscapeKeys.clone() : new String[0];
/* 290 */     Arrays.sort((Object[])sortedIgnoreKeys);
/* 291 */     Map<String, String> map = new HashMap<String, String>();
/* 292 */     for (String string : values) {
/* 293 */       String[] keyValue = string.split(toRegexSeparator("="));
/* 294 */       if (keyValue.length > 1) {
/* 295 */         String key = keyValue[0].toUpperCase(Locale.ENGLISH);
/* 296 */         String value = keyValue[1];
/* 297 */         boolean escape = (Arrays.binarySearch((Object[])sortedIgnoreKeys, key) < 0);
/* 298 */         map.put(key, escape ? createSequence(value.split("\\s")) : value);
/*     */       } 
/*     */     } 
/* 301 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String createSequence(String... names) {
/* 312 */     if (names == null) {
/* 313 */       return getDefaultStyle();
/*     */     }
/* 315 */     StringBuilder sb = new StringBuilder(PREFIX.getCode());
/* 316 */     boolean first = true;
/* 317 */     for (String name : names) {
/*     */       try {
/* 319 */         AnsiEscape escape = valueOf(name.trim().toUpperCase(Locale.ENGLISH));
/* 320 */         if (!first) {
/* 321 */           sb.append(SEPARATOR.getCode());
/*     */         }
/* 323 */         first = false;
/* 324 */         sb.append(escape.getCode());
/* 325 */       } catch (Exception ex) {}
/*     */     } 
/*     */ 
/*     */     
/* 329 */     sb.append(SUFFIX.getCode());
/* 330 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\AnsiEscape.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */