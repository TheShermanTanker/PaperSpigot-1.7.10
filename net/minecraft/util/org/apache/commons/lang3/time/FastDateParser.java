/*     */ package net.minecraft.util.org.apache.commons.lang3.time;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormatSymbols;
/*     */ import java.text.ParseException;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TimeZone;
/*     */ import java.util.TreeMap;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FastDateParser
/*     */   implements DateParser, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  76 */   static final Locale JAPANESE_IMPERIAL = new Locale("ja", "JP", "JP");
/*     */ 
/*     */   
/*     */   private final String pattern;
/*     */ 
/*     */   
/*     */   private final TimeZone timeZone;
/*     */ 
/*     */   
/*     */   private final Locale locale;
/*     */ 
/*     */   
/*     */   private transient Pattern parsePattern;
/*     */ 
/*     */   
/*     */   private transient Strategy[] strategies;
/*     */   
/*     */   private transient int thisYear;
/*     */   
/*     */   private transient String currentFormatField;
/*     */   
/*     */   private transient Strategy nextStrategy;
/*     */ 
/*     */   
/*     */   protected FastDateParser(String pattern, TimeZone timeZone, Locale locale) {
/* 101 */     this.pattern = pattern;
/* 102 */     this.timeZone = timeZone;
/* 103 */     this.locale = locale;
/* 104 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init() {
/* 112 */     Calendar definingCalendar = Calendar.getInstance(this.timeZone, this.locale);
/* 113 */     this.thisYear = definingCalendar.get(1);
/*     */     
/* 115 */     StringBuilder regex = new StringBuilder();
/* 116 */     List<Strategy> collector = new ArrayList<Strategy>();
/*     */     
/* 118 */     Matcher patternMatcher = formatPattern.matcher(this.pattern);
/* 119 */     if (!patternMatcher.lookingAt()) {
/* 120 */       throw new IllegalArgumentException("Illegal pattern character '" + this.pattern.charAt(patternMatcher.regionStart()) + "'");
/*     */     }
/*     */ 
/*     */     
/* 124 */     this.currentFormatField = patternMatcher.group();
/* 125 */     Strategy currentStrategy = getStrategy(this.currentFormatField, definingCalendar);
/*     */     while (true) {
/* 127 */       patternMatcher.region(patternMatcher.end(), patternMatcher.regionEnd());
/* 128 */       if (!patternMatcher.lookingAt()) {
/* 129 */         this.nextStrategy = null;
/*     */         break;
/*     */       } 
/* 132 */       String nextFormatField = patternMatcher.group();
/* 133 */       this.nextStrategy = getStrategy(nextFormatField, definingCalendar);
/* 134 */       if (currentStrategy.addRegex(this, regex)) {
/* 135 */         collector.add(currentStrategy);
/*     */       }
/* 137 */       this.currentFormatField = nextFormatField;
/* 138 */       currentStrategy = this.nextStrategy;
/*     */     } 
/* 140 */     if (patternMatcher.regionStart() != patternMatcher.regionEnd()) {
/* 141 */       throw new IllegalArgumentException("Failed to parse \"" + this.pattern + "\" ; gave up at index " + patternMatcher.regionStart());
/*     */     }
/* 143 */     if (currentStrategy.addRegex(this, regex)) {
/* 144 */       collector.add(currentStrategy);
/*     */     }
/* 146 */     this.currentFormatField = null;
/* 147 */     this.strategies = collector.<Strategy>toArray(new Strategy[collector.size()]);
/* 148 */     this.parsePattern = Pattern.compile(regex.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPattern() {
/* 158 */     return this.pattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeZone getTimeZone() {
/* 166 */     return this.timeZone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Locale getLocale() {
/* 174 */     return this.locale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Pattern getParsePattern() {
/* 183 */     return this.parsePattern;
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
/*     */   public boolean equals(Object obj) {
/* 196 */     if (!(obj instanceof FastDateParser)) {
/* 197 */       return false;
/*     */     }
/* 199 */     FastDateParser other = (FastDateParser)obj;
/* 200 */     return (this.pattern.equals(other.pattern) && this.timeZone.equals(other.timeZone) && this.locale.equals(other.locale));
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
/*     */   public int hashCode() {
/* 212 */     return this.pattern.hashCode() + 13 * (this.timeZone.hashCode() + 13 * this.locale.hashCode());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 222 */     return "FastDateParser[" + this.pattern + "," + this.locale + "," + this.timeZone.getID() + "]";
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
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 236 */     in.defaultReadObject();
/* 237 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object parseObject(String source) throws ParseException {
/* 245 */     return parse(source);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date parse(String source) throws ParseException {
/* 253 */     Date date = parse(source, new ParsePosition(0));
/* 254 */     if (date == null) {
/*     */       
/* 256 */       if (this.locale.equals(JAPANESE_IMPERIAL)) {
/* 257 */         throw new ParseException("(The " + this.locale + " locale does not support dates before 1868 AD)\n" + "Unparseable date: \"" + source + "\" does not match " + this.parsePattern.pattern(), 0);
/*     */       }
/*     */ 
/*     */       
/* 261 */       throw new ParseException("Unparseable date: \"" + source + "\" does not match " + this.parsePattern.pattern(), 0);
/*     */     } 
/* 263 */     return date;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object parseObject(String source, ParsePosition pos) {
/* 271 */     return parse(source, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date parse(String source, ParsePosition pos) {
/* 279 */     int offset = pos.getIndex();
/* 280 */     Matcher matcher = this.parsePattern.matcher(source.substring(offset));
/* 281 */     if (!matcher.lookingAt()) {
/* 282 */       return null;
/*     */     }
/*     */     
/* 285 */     Calendar cal = Calendar.getInstance(this.timeZone, this.locale);
/* 286 */     cal.clear();
/*     */     
/* 288 */     for (int i = 0; i < this.strategies.length; ) {
/* 289 */       Strategy strategy = this.strategies[i++];
/* 290 */       strategy.setCalendar(this, cal, matcher.group(i));
/*     */     } 
/* 292 */     pos.setIndex(offset + matcher.end());
/* 293 */     return cal.getTime();
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
/*     */   private static StringBuilder escapeRegex(StringBuilder regex, String value, boolean unquote) {
/* 307 */     regex.append("\\Q");
/* 308 */     for (int i = 0; i < value.length(); i++) {
/* 309 */       char c = value.charAt(i);
/* 310 */       switch (c) {
/*     */         case '\'':
/* 312 */           if (unquote) {
/* 313 */             if (++i == value.length()) {
/* 314 */               return regex;
/*     */             }
/* 316 */             c = value.charAt(i);
/*     */           } 
/*     */           break;
/*     */         case '\\':
/* 320 */           if (++i == value.length()) {
/*     */             break;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 330 */           regex.append(c);
/* 331 */           c = value.charAt(i);
/* 332 */           if (c == 'E') {
/* 333 */             regex.append("E\\\\E\\");
/* 334 */             c = 'Q';
/*     */           } 
/*     */           break;
/*     */       } 
/* 338 */       regex.append(c);
/*     */     } 
/* 340 */     regex.append("\\E");
/* 341 */     return regex;
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
/*     */   private static Map<String, Integer> getDisplayNames(int field, Calendar definingCalendar, Locale locale) {
/* 353 */     return definingCalendar.getDisplayNames(field, 0, locale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int adjustYear(int twoDigitYear) {
/* 362 */     int trial = twoDigitYear + this.thisYear - this.thisYear % 100;
/* 363 */     if (trial < this.thisYear + 20) {
/* 364 */       return trial;
/*     */     }
/* 366 */     return trial - 100;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isNextNumber() {
/* 374 */     return (this.nextStrategy != null && this.nextStrategy.isNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getFieldWidth() {
/* 382 */     return this.currentFormatField.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class Strategy
/*     */   {
/*     */     private Strategy() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isNumber() {
/* 396 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setCalendar(FastDateParser parser, Calendar cal, String value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract boolean addRegex(FastDateParser param1FastDateParser, StringBuilder param1StringBuilder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 424 */   private static final Pattern formatPattern = Pattern.compile("D+|E+|F+|G+|H+|K+|M+|S+|W+|Z+|a+|d+|h+|k+|m+|s+|w+|y+|z+|''|'[^']++(''[^']*+)*+'|[^'A-Za-z]++");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Strategy getStrategy(String formatField, Calendar definingCalendar) {
/* 434 */     switch (formatField.charAt(0))
/*     */     { case '\'':
/* 436 */         if (formatField.length() > 2) {
/* 437 */           formatField = formatField.substring(1, formatField.length() - 1);
/*     */         }
/*     */       
/*     */       default:
/* 441 */         return new CopyQuotedStrategy(formatField);
/*     */       case 'D':
/* 443 */         return DAY_OF_YEAR_STRATEGY;
/*     */       case 'E':
/* 445 */         return getLocaleSpecificStrategy(7, definingCalendar);
/*     */       case 'F':
/* 447 */         return DAY_OF_WEEK_IN_MONTH_STRATEGY;
/*     */       case 'G':
/* 449 */         return getLocaleSpecificStrategy(0, definingCalendar);
/*     */       case 'H':
/* 451 */         return MODULO_HOUR_OF_DAY_STRATEGY;
/*     */       case 'K':
/* 453 */         return HOUR_STRATEGY;
/*     */       case 'M':
/* 455 */         return (formatField.length() >= 3) ? getLocaleSpecificStrategy(2, definingCalendar) : NUMBER_MONTH_STRATEGY;
/*     */       case 'S':
/* 457 */         return MILLISECOND_STRATEGY;
/*     */       case 'W':
/* 459 */         return WEEK_OF_MONTH_STRATEGY;
/*     */       case 'a':
/* 461 */         return getLocaleSpecificStrategy(9, definingCalendar);
/*     */       case 'd':
/* 463 */         return DAY_OF_MONTH_STRATEGY;
/*     */       case 'h':
/* 465 */         return MODULO_HOUR_STRATEGY;
/*     */       case 'k':
/* 467 */         return HOUR_OF_DAY_STRATEGY;
/*     */       case 'm':
/* 469 */         return MINUTE_STRATEGY;
/*     */       case 's':
/* 471 */         return SECOND_STRATEGY;
/*     */       case 'w':
/* 473 */         return WEEK_OF_YEAR_STRATEGY;
/*     */       case 'y':
/* 475 */         return (formatField.length() > 2) ? LITERAL_YEAR_STRATEGY : ABBREVIATED_YEAR_STRATEGY;
/*     */       case 'Z':
/*     */       case 'z':
/* 478 */         break; }  return getLocaleSpecificStrategy(15, definingCalendar);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 483 */   private static ConcurrentMap<Locale, Strategy>[] caches = (ConcurrentMap<Locale, Strategy>[])new ConcurrentMap[17];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ConcurrentMap<Locale, Strategy> getCache(int field) {
/* 491 */     synchronized (caches) {
/* 492 */       if (caches[field] == null) {
/* 493 */         caches[field] = new ConcurrentHashMap<Locale, Strategy>(3);
/*     */       }
/* 495 */       return caches[field];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Strategy getLocaleSpecificStrategy(int field, Calendar definingCalendar) {
/* 506 */     ConcurrentMap<Locale, Strategy> cache = getCache(field);
/* 507 */     Strategy strategy = cache.get(this.locale);
/* 508 */     if (strategy == null) {
/* 509 */       strategy = (field == 15) ? new TimeZoneStrategy(this.locale) : new TextStrategy(field, definingCalendar, this.locale);
/*     */ 
/*     */       
/* 512 */       Strategy inCache = cache.putIfAbsent(this.locale, strategy);
/* 513 */       if (inCache != null) {
/* 514 */         return inCache;
/*     */       }
/*     */     } 
/* 517 */     return strategy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CopyQuotedStrategy
/*     */     extends Strategy
/*     */   {
/*     */     private final String formatField;
/*     */ 
/*     */ 
/*     */     
/*     */     CopyQuotedStrategy(String formatField) {
/* 531 */       this.formatField = formatField;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isNumber() {
/* 539 */       char c = this.formatField.charAt(0);
/* 540 */       if (c == '\'') {
/* 541 */         c = this.formatField.charAt(1);
/*     */       }
/* 543 */       return Character.isDigit(c);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean addRegex(FastDateParser parser, StringBuilder regex) {
/* 551 */       FastDateParser.escapeRegex(regex, this.formatField, true);
/* 552 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class TextStrategy
/*     */     extends Strategy
/*     */   {
/*     */     private final int field;
/*     */ 
/*     */     
/*     */     private final Map<String, Integer> keyValues;
/*     */ 
/*     */ 
/*     */     
/*     */     TextStrategy(int field, Calendar definingCalendar, Locale locale) {
/* 570 */       this.field = field;
/* 571 */       this.keyValues = FastDateParser.getDisplayNames(field, definingCalendar, locale);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean addRegex(FastDateParser parser, StringBuilder regex) {
/* 579 */       regex.append('(');
/* 580 */       for (String textKeyValue : this.keyValues.keySet()) {
/* 581 */         FastDateParser.escapeRegex(regex, textKeyValue, false).append('|');
/*     */       }
/* 583 */       regex.setCharAt(regex.length() - 1, ')');
/* 584 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setCalendar(FastDateParser parser, Calendar cal, String value) {
/* 592 */       Integer iVal = this.keyValues.get(value);
/* 593 */       if (iVal == null) {
/* 594 */         StringBuilder sb = new StringBuilder(value);
/* 595 */         sb.append(" not in (");
/* 596 */         for (String textKeyValue : this.keyValues.keySet()) {
/* 597 */           sb.append(textKeyValue).append(' ');
/*     */         }
/* 599 */         sb.setCharAt(sb.length() - 1, ')');
/* 600 */         throw new IllegalArgumentException(sb.toString());
/*     */       } 
/* 602 */       cal.set(this.field, iVal.intValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class NumberStrategy
/*     */     extends Strategy
/*     */   {
/*     */     private final int field;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     NumberStrategy(int field) {
/* 618 */       this.field = field;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isNumber() {
/* 626 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean addRegex(FastDateParser parser, StringBuilder regex) {
/* 634 */       if (parser.isNextNumber()) {
/* 635 */         regex.append("(\\p{IsNd}{").append(parser.getFieldWidth()).append("}+)");
/*     */       } else {
/*     */         
/* 638 */         regex.append("(\\p{IsNd}++)");
/*     */       } 
/* 640 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setCalendar(FastDateParser parser, Calendar cal, String value) {
/* 648 */       cal.set(this.field, modify(Integer.parseInt(value)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int modify(int iValue) {
/* 657 */       return iValue;
/*     */     }
/*     */   }
/*     */   
/* 661 */   private static final Strategy ABBREVIATED_YEAR_STRATEGY = new NumberStrategy(1)
/*     */     {
/*     */ 
/*     */       
/*     */       void setCalendar(FastDateParser parser, Calendar cal, String value)
/*     */       {
/* 667 */         int iValue = Integer.parseInt(value);
/* 668 */         if (iValue < 100) {
/* 669 */           iValue = parser.adjustYear(iValue);
/*     */         }
/* 671 */         cal.set(1, iValue);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private static class TimeZoneStrategy
/*     */     extends Strategy
/*     */   {
/*     */     private final String validTimeZoneChars;
/*     */     
/* 681 */     private final SortedMap<String, TimeZone> tzNames = new TreeMap<String, TimeZone>(String.CASE_INSENSITIVE_ORDER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int ID = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int LONG_STD = 1;
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int SHORT_STD = 2;
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int LONG_DST = 3;
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int SHORT_DST = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     TimeZoneStrategy(Locale locale) {
/* 709 */       String[][] zones = DateFormatSymbols.getInstance(locale).getZoneStrings();
/* 710 */       for (String[] zone : zones) {
/* 711 */         if (!zone[0].startsWith("GMT")) {
/*     */ 
/*     */           
/* 714 */           TimeZone tz = TimeZone.getTimeZone(zone[0]);
/* 715 */           if (!this.tzNames.containsKey(zone[1])) {
/* 716 */             this.tzNames.put(zone[1], tz);
/*     */           }
/* 718 */           if (!this.tzNames.containsKey(zone[2])) {
/* 719 */             this.tzNames.put(zone[2], tz);
/*     */           }
/* 721 */           if (tz.useDaylightTime()) {
/* 722 */             if (!this.tzNames.containsKey(zone[3])) {
/* 723 */               this.tzNames.put(zone[3], tz);
/*     */             }
/* 725 */             if (!this.tzNames.containsKey(zone[4])) {
/* 726 */               this.tzNames.put(zone[4], tz);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 731 */       StringBuilder sb = new StringBuilder();
/* 732 */       sb.append("(GMT[+\\-]\\d{0,1}\\d{2}|[+\\-]\\d{2}:?\\d{2}|");
/* 733 */       for (String id : this.tzNames.keySet()) {
/* 734 */         FastDateParser.escapeRegex(sb, id, false).append('|');
/*     */       }
/* 736 */       sb.setCharAt(sb.length() - 1, ')');
/* 737 */       this.validTimeZoneChars = sb.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean addRegex(FastDateParser parser, StringBuilder regex) {
/* 745 */       regex.append(this.validTimeZoneChars);
/* 746 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setCalendar(FastDateParser parser, Calendar cal, String value) {
/*     */       TimeZone tz;
/* 755 */       if (value.charAt(0) == '+' || value.charAt(0) == '-') {
/* 756 */         tz = TimeZone.getTimeZone("GMT" + value);
/*     */       }
/* 758 */       else if (value.startsWith("GMT")) {
/* 759 */         tz = TimeZone.getTimeZone(value);
/*     */       } else {
/*     */         
/* 762 */         tz = this.tzNames.get(value);
/* 763 */         if (tz == null) {
/* 764 */           throw new IllegalArgumentException(value + " is not a supported timezone name");
/*     */         }
/*     */       } 
/* 767 */       cal.setTimeZone(tz);
/*     */     }
/*     */   }
/*     */   
/* 771 */   private static final Strategy NUMBER_MONTH_STRATEGY = new NumberStrategy(2)
/*     */     {
/*     */       int modify(int iValue) {
/* 774 */         return iValue - 1;
/*     */       }
/*     */     };
/* 777 */   private static final Strategy LITERAL_YEAR_STRATEGY = new NumberStrategy(1);
/* 778 */   private static final Strategy WEEK_OF_YEAR_STRATEGY = new NumberStrategy(3);
/* 779 */   private static final Strategy WEEK_OF_MONTH_STRATEGY = new NumberStrategy(4);
/* 780 */   private static final Strategy DAY_OF_YEAR_STRATEGY = new NumberStrategy(6);
/* 781 */   private static final Strategy DAY_OF_MONTH_STRATEGY = new NumberStrategy(5);
/* 782 */   private static final Strategy DAY_OF_WEEK_IN_MONTH_STRATEGY = new NumberStrategy(8);
/* 783 */   private static final Strategy HOUR_OF_DAY_STRATEGY = new NumberStrategy(11);
/* 784 */   private static final Strategy MODULO_HOUR_OF_DAY_STRATEGY = new NumberStrategy(11)
/*     */     {
/*     */       int modify(int iValue) {
/* 787 */         return iValue % 24;
/*     */       }
/*     */     };
/* 790 */   private static final Strategy MODULO_HOUR_STRATEGY = new NumberStrategy(10)
/*     */     {
/*     */       int modify(int iValue) {
/* 793 */         return iValue % 12;
/*     */       }
/*     */     };
/* 796 */   private static final Strategy HOUR_STRATEGY = new NumberStrategy(10);
/* 797 */   private static final Strategy MINUTE_STRATEGY = new NumberStrategy(12);
/* 798 */   private static final Strategy SECOND_STRATEGY = new NumberStrategy(13);
/* 799 */   private static final Strategy MILLISECOND_STRATEGY = new NumberStrategy(14);
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\time\FastDateParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */