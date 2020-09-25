/*      */ package net.minecraft.util.org.apache.commons.lang3.time;
/*      */ 
/*      */ import java.text.ParseException;
/*      */ import java.text.ParsePosition;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.NoSuchElementException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DateUtils
/*      */ {
/*      */   public static final long MILLIS_PER_SECOND = 1000L;
/*      */   public static final long MILLIS_PER_MINUTE = 60000L;
/*      */   public static final long MILLIS_PER_HOUR = 3600000L;
/*      */   public static final long MILLIS_PER_DAY = 86400000L;
/*      */   public static final int SEMI_MONTH = 1001;
/*   75 */   private static final int[][] fields = new int[][] { { 14 }, { 13 }, { 12 }, { 11, 10 }, { 5, 5, 9 }, { 2, 1001 }, { 1 }, { 0 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_WEEK_SUNDAY = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_WEEK_MONDAY = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_WEEK_RELATIVE = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_WEEK_CENTER = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_MONTH_SUNDAY = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_MONTH_MONDAY = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MODIFY_TRUNCATE = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MODIFY_ROUND = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MODIFY_CEILING = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameDay(Date date1, Date date2) {
/*  155 */     if (date1 == null || date2 == null) {
/*  156 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  158 */     Calendar cal1 = Calendar.getInstance();
/*  159 */     cal1.setTime(date1);
/*  160 */     Calendar cal2 = Calendar.getInstance();
/*  161 */     cal2.setTime(date2);
/*  162 */     return isSameDay(cal1, cal2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameDay(Calendar cal1, Calendar cal2) {
/*  179 */     if (cal1 == null || cal2 == null) {
/*  180 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  182 */     return (cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameInstant(Date date1, Date date2) {
/*  200 */     if (date1 == null || date2 == null) {
/*  201 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  203 */     return (date1.getTime() == date2.getTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameInstant(Calendar cal1, Calendar cal2) {
/*  218 */     if (cal1 == null || cal2 == null) {
/*  219 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  221 */     return (cal1.getTime().getTime() == cal2.getTime().getTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameLocalTime(Calendar cal1, Calendar cal2) {
/*  238 */     if (cal1 == null || cal2 == null) {
/*  239 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  241 */     return (cal1.get(14) == cal2.get(14) && cal1.get(13) == cal2.get(13) && cal1.get(12) == cal2.get(12) && cal1.get(11) == cal2.get(11) && cal1.get(6) == cal2.get(6) && cal1.get(1) == cal2.get(1) && cal1.get(0) == cal2.get(0) && cal1.getClass() == cal2.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date parseDate(String str, String... parsePatterns) throws ParseException {
/*  267 */     return parseDate(str, null, parsePatterns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date parseDate(String str, Locale locale, String... parsePatterns) throws ParseException {
/*  290 */     return parseDateWithLeniency(str, locale, parsePatterns, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date parseDateStrictly(String str, String... parsePatterns) throws ParseException {
/*  310 */     return parseDateStrictly(str, null, parsePatterns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date parseDateStrictly(String str, Locale locale, String... parsePatterns) throws ParseException {
/*  332 */     return parseDateWithLeniency(str, null, parsePatterns, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Date parseDateWithLeniency(String str, Locale locale, String[] parsePatterns, boolean lenient) throws ParseException {
/*      */     SimpleDateFormat parser;
/*  354 */     if (str == null || parsePatterns == null) {
/*  355 */       throw new IllegalArgumentException("Date and Patterns must not be null");
/*      */     }
/*      */ 
/*      */     
/*  359 */     if (locale == null) {
/*  360 */       parser = new SimpleDateFormat();
/*      */     } else {
/*  362 */       parser = new SimpleDateFormat("", locale);
/*      */     } 
/*      */     
/*  365 */     parser.setLenient(lenient);
/*  366 */     ParsePosition pos = new ParsePosition(0);
/*  367 */     for (String parsePattern : parsePatterns) {
/*      */       
/*  369 */       String pattern = parsePattern;
/*      */ 
/*      */       
/*  372 */       if (parsePattern.endsWith("ZZ")) {
/*  373 */         pattern = pattern.substring(0, pattern.length() - 1);
/*      */       }
/*      */       
/*  376 */       parser.applyPattern(pattern);
/*  377 */       pos.setIndex(0);
/*      */       
/*  379 */       String str2 = str;
/*      */       
/*  381 */       if (parsePattern.endsWith("ZZ")) {
/*  382 */         str2 = str.replaceAll("([-+][0-9][0-9]):([0-9][0-9])$", "$1$2");
/*      */       }
/*      */       
/*  385 */       Date date = parser.parse(str2, pos);
/*  386 */       if (date != null && pos.getIndex() == str2.length()) {
/*  387 */         return date;
/*      */       }
/*      */     } 
/*  390 */     throw new ParseException("Unable to parse the date: " + str, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addYears(Date date, int amount) {
/*  404 */     return add(date, 1, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addMonths(Date date, int amount) {
/*  418 */     return add(date, 2, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addWeeks(Date date, int amount) {
/*  432 */     return add(date, 3, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addDays(Date date, int amount) {
/*  446 */     return add(date, 5, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addHours(Date date, int amount) {
/*  460 */     return add(date, 11, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addMinutes(Date date, int amount) {
/*  474 */     return add(date, 12, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addSeconds(Date date, int amount) {
/*  488 */     return add(date, 13, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addMilliseconds(Date date, int amount) {
/*  502 */     return add(date, 14, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Date add(Date date, int calendarField, int amount) {
/*  517 */     if (date == null) {
/*  518 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  520 */     Calendar c = Calendar.getInstance();
/*  521 */     c.setTime(date);
/*  522 */     c.add(calendarField, amount);
/*  523 */     return c.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setYears(Date date, int amount) {
/*  538 */     return set(date, 1, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setMonths(Date date, int amount) {
/*  553 */     return set(date, 2, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setDays(Date date, int amount) {
/*  568 */     return set(date, 5, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setHours(Date date, int amount) {
/*  584 */     return set(date, 11, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setMinutes(Date date, int amount) {
/*  599 */     return set(date, 12, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setSeconds(Date date, int amount) {
/*  614 */     return set(date, 13, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setMilliseconds(Date date, int amount) {
/*  629 */     return set(date, 14, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Date set(Date date, int calendarField, int amount) {
/*  646 */     if (date == null) {
/*  647 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*      */     
/*  650 */     Calendar c = Calendar.getInstance();
/*  651 */     c.setLenient(false);
/*  652 */     c.setTime(date);
/*  653 */     c.set(calendarField, amount);
/*  654 */     return c.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Calendar toCalendar(Date date) {
/*  667 */     Calendar c = Calendar.getInstance();
/*  668 */     c.setTime(date);
/*  669 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date round(Date date, int field) {
/*  700 */     if (date == null) {
/*  701 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  703 */     Calendar gval = Calendar.getInstance();
/*  704 */     gval.setTime(date);
/*  705 */     modify(gval, field, 1);
/*  706 */     return gval.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Calendar round(Calendar date, int field) {
/*  737 */     if (date == null) {
/*  738 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  740 */     Calendar rounded = (Calendar)date.clone();
/*  741 */     modify(rounded, field, 1);
/*  742 */     return rounded;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date round(Object date, int field) {
/*  774 */     if (date == null) {
/*  775 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  777 */     if (date instanceof Date)
/*  778 */       return round((Date)date, field); 
/*  779 */     if (date instanceof Calendar) {
/*  780 */       return round((Calendar)date, field).getTime();
/*      */     }
/*  782 */     throw new ClassCastException("Could not round " + date);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date truncate(Date date, int field) {
/*  803 */     if (date == null) {
/*  804 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  806 */     Calendar gval = Calendar.getInstance();
/*  807 */     gval.setTime(date);
/*  808 */     modify(gval, field, 0);
/*  809 */     return gval.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Calendar truncate(Calendar date, int field) {
/*  828 */     if (date == null) {
/*  829 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  831 */     Calendar truncated = (Calendar)date.clone();
/*  832 */     modify(truncated, field, 0);
/*  833 */     return truncated;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date truncate(Object date, int field) {
/*  853 */     if (date == null) {
/*  854 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  856 */     if (date instanceof Date)
/*  857 */       return truncate((Date)date, field); 
/*  858 */     if (date instanceof Calendar) {
/*  859 */       return truncate((Calendar)date, field).getTime();
/*      */     }
/*  861 */     throw new ClassCastException("Could not truncate " + date);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date ceiling(Date date, int field) {
/*  883 */     if (date == null) {
/*  884 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  886 */     Calendar gval = Calendar.getInstance();
/*  887 */     gval.setTime(date);
/*  888 */     modify(gval, field, 2);
/*  889 */     return gval.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Calendar ceiling(Calendar date, int field) {
/*  909 */     if (date == null) {
/*  910 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  912 */     Calendar ceiled = (Calendar)date.clone();
/*  913 */     modify(ceiled, field, 2);
/*  914 */     return ceiled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date ceiling(Object date, int field) {
/*  935 */     if (date == null) {
/*  936 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  938 */     if (date instanceof Date)
/*  939 */       return ceiling((Date)date, field); 
/*  940 */     if (date instanceof Calendar) {
/*  941 */       return ceiling((Calendar)date, field).getTime();
/*      */     }
/*  943 */     throw new ClassCastException("Could not find ceiling of for type: " + date.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void modify(Calendar val, int field, int modType) {
/*  957 */     if (val.get(1) > 280000000) {
/*  958 */       throw new ArithmeticException("Calendar value too large for accurate calculations");
/*      */     }
/*      */     
/*  961 */     if (field == 14) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  971 */     Date date = val.getTime();
/*  972 */     long time = date.getTime();
/*  973 */     boolean done = false;
/*      */ 
/*      */     
/*  976 */     int millisecs = val.get(14);
/*  977 */     if (0 == modType || millisecs < 500) {
/*  978 */       time -= millisecs;
/*      */     }
/*  980 */     if (field == 13) {
/*  981 */       done = true;
/*      */     }
/*      */ 
/*      */     
/*  985 */     int seconds = val.get(13);
/*  986 */     if (!done && (0 == modType || seconds < 30)) {
/*  987 */       time -= seconds * 1000L;
/*      */     }
/*  989 */     if (field == 12) {
/*  990 */       done = true;
/*      */     }
/*      */ 
/*      */     
/*  994 */     int minutes = val.get(12);
/*  995 */     if (!done && (0 == modType || minutes < 30)) {
/*  996 */       time -= minutes * 60000L;
/*      */     }
/*      */ 
/*      */     
/* 1000 */     if (date.getTime() != time) {
/* 1001 */       date.setTime(time);
/* 1002 */       val.setTime(date);
/*      */     } 
/*      */ 
/*      */     
/* 1006 */     boolean roundUp = false;
/* 1007 */     for (int[] aField : fields) {
/* 1008 */       for (int element : aField) {
/* 1009 */         if (element == field) {
/*      */           
/* 1011 */           if (modType == 2 || (modType == 1 && roundUp)) {
/* 1012 */             if (field == 1001) {
/*      */ 
/*      */ 
/*      */               
/* 1016 */               if (val.get(5) == 1) {
/* 1017 */                 val.add(5, 15);
/*      */               } else {
/* 1019 */                 val.add(5, -15);
/* 1020 */                 val.add(2, 1);
/*      */               }
/*      */             
/* 1023 */             } else if (field == 9) {
/*      */ 
/*      */ 
/*      */               
/* 1027 */               if (val.get(11) == 0) {
/* 1028 */                 val.add(11, 12);
/*      */               } else {
/* 1030 */                 val.add(11, -12);
/* 1031 */                 val.add(5, 1);
/*      */               }
/*      */             
/*      */             }
/*      */             else {
/*      */               
/* 1037 */               val.add(aField[0], 1);
/*      */             } 
/*      */           }
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/* 1044 */       int offset = 0;
/* 1045 */       boolean offsetSet = false;
/*      */       
/* 1047 */       switch (field) {
/*      */         case 1001:
/* 1049 */           if (aField[0] == 5) {
/*      */ 
/*      */ 
/*      */             
/* 1053 */             offset = val.get(5) - 1;
/*      */ 
/*      */             
/* 1056 */             if (offset >= 15) {
/* 1057 */               offset -= 15;
/*      */             }
/*      */             
/* 1060 */             roundUp = (offset > 7);
/* 1061 */             offsetSet = true;
/*      */           } 
/*      */           break;
/*      */         case 9:
/* 1065 */           if (aField[0] == 11) {
/*      */ 
/*      */             
/* 1068 */             offset = val.get(11);
/* 1069 */             if (offset >= 12) {
/* 1070 */               offset -= 12;
/*      */             }
/* 1072 */             roundUp = (offset >= 6);
/* 1073 */             offsetSet = true;
/*      */           } 
/*      */           break;
/*      */       } 
/* 1077 */       if (!offsetSet) {
/* 1078 */         int min = val.getActualMinimum(aField[0]);
/* 1079 */         int max = val.getActualMaximum(aField[0]);
/*      */         
/* 1081 */         offset = val.get(aField[0]) - min;
/*      */         
/* 1083 */         roundUp = (offset > (max - min) / 2);
/*      */       } 
/*      */       
/* 1086 */       if (offset != 0) {
/* 1087 */         val.set(aField[0], val.get(aField[0]) - offset);
/*      */       }
/*      */     } 
/* 1090 */     throw new IllegalArgumentException("The field " + field + " is not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Iterator<Calendar> iterator(Date focus, int rangeStyle) {
/* 1120 */     if (focus == null) {
/* 1121 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/* 1123 */     Calendar gval = Calendar.getInstance();
/* 1124 */     gval.setTime(focus);
/* 1125 */     return iterator(gval, rangeStyle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Iterator<Calendar> iterator(Calendar focus, int rangeStyle) {
/* 1153 */     if (focus == null) {
/* 1154 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/* 1156 */     Calendar start = null;
/* 1157 */     Calendar end = null;
/* 1158 */     int startCutoff = 1;
/* 1159 */     int endCutoff = 7;
/* 1160 */     switch (rangeStyle) {
/*      */       
/*      */       case 5:
/*      */       case 6:
/* 1164 */         start = truncate(focus, 2);
/*      */         
/* 1166 */         end = (Calendar)start.clone();
/* 1167 */         end.add(2, 1);
/* 1168 */         end.add(5, -1);
/*      */         
/* 1170 */         if (rangeStyle == 6) {
/* 1171 */           startCutoff = 2;
/* 1172 */           endCutoff = 1;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/* 1180 */         start = truncate(focus, 5);
/* 1181 */         end = truncate(focus, 5);
/* 1182 */         switch (rangeStyle) {
/*      */ 
/*      */ 
/*      */           
/*      */           case 2:
/* 1187 */             startCutoff = 2;
/* 1188 */             endCutoff = 1;
/*      */             break;
/*      */           case 3:
/* 1191 */             startCutoff = focus.get(7);
/* 1192 */             endCutoff = startCutoff - 1;
/*      */             break;
/*      */           case 4:
/* 1195 */             startCutoff = focus.get(7) - 3;
/* 1196 */             endCutoff = focus.get(7) + 3;
/*      */             break;
/*      */         } 
/*      */         break;
/*      */       default:
/* 1201 */         throw new IllegalArgumentException("The range style " + rangeStyle + " is not valid.");
/*      */     } 
/* 1203 */     if (startCutoff < 1) {
/* 1204 */       startCutoff += 7;
/*      */     }
/* 1206 */     if (startCutoff > 7) {
/* 1207 */       startCutoff -= 7;
/*      */     }
/* 1209 */     if (endCutoff < 1) {
/* 1210 */       endCutoff += 7;
/*      */     }
/* 1212 */     if (endCutoff > 7) {
/* 1213 */       endCutoff -= 7;
/*      */     }
/* 1215 */     while (start.get(7) != startCutoff) {
/* 1216 */       start.add(5, -1);
/*      */     }
/* 1218 */     while (end.get(7) != endCutoff) {
/* 1219 */       end.add(5, 1);
/*      */     }
/* 1221 */     return new DateIterator(start, end);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Iterator<?> iterator(Object focus, int rangeStyle) {
/* 1241 */     if (focus == null) {
/* 1242 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/* 1244 */     if (focus instanceof Date)
/* 1245 */       return iterator((Date)focus, rangeStyle); 
/* 1246 */     if (focus instanceof Calendar) {
/* 1247 */       return iterator((Calendar)focus, rangeStyle);
/*      */     }
/* 1249 */     throw new ClassCastException("Could not iterate based on " + focus);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInMilliseconds(Date date, int fragment) {
/* 1287 */     return getFragment(date, fragment, 14);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInSeconds(Date date, int fragment) {
/* 1327 */     return getFragment(date, fragment, 13);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInMinutes(Date date, int fragment) {
/* 1367 */     return getFragment(date, fragment, 12);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInHours(Date date, int fragment) {
/* 1407 */     return getFragment(date, fragment, 11);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInDays(Date date, int fragment) {
/* 1447 */     return getFragment(date, fragment, 6);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInMilliseconds(Calendar calendar, int fragment) {
/* 1487 */     return getFragment(calendar, fragment, 14);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInSeconds(Calendar calendar, int fragment) {
/* 1526 */     return getFragment(calendar, fragment, 13);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInMinutes(Calendar calendar, int fragment) {
/* 1566 */     return getFragment(calendar, fragment, 12);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInHours(Calendar calendar, int fragment) {
/* 1606 */     return getFragment(calendar, fragment, 11);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInDays(Calendar calendar, int fragment) {
/* 1648 */     return getFragment(calendar, fragment, 6);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long getFragment(Date date, int fragment, int unit) {
/* 1663 */     if (date == null) {
/* 1664 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/* 1666 */     Calendar calendar = Calendar.getInstance();
/* 1667 */     calendar.setTime(date);
/* 1668 */     return getFragment(calendar, fragment, unit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long getFragment(Calendar calendar, int fragment, int unit) {
/* 1683 */     if (calendar == null) {
/* 1684 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/* 1686 */     long millisPerUnit = getMillisPerUnit(unit);
/* 1687 */     long result = 0L;
/*      */ 
/*      */     
/* 1690 */     switch (fragment) {
/*      */       case 1:
/* 1692 */         result += calendar.get(6) * 86400000L / millisPerUnit;
/*      */         break;
/*      */       case 2:
/* 1695 */         result += calendar.get(5) * 86400000L / millisPerUnit;
/*      */         break;
/*      */     } 
/*      */     
/* 1699 */     switch (fragment) {
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*      */       case 2:
/*      */       case 5:
/*      */       case 6:
/* 1707 */         result += calendar.get(11) * 3600000L / millisPerUnit;
/*      */       
/*      */       case 11:
/* 1710 */         result += calendar.get(12) * 60000L / millisPerUnit;
/*      */       
/*      */       case 12:
/* 1713 */         result += calendar.get(13) * 1000L / millisPerUnit;
/*      */       
/*      */       case 13:
/* 1716 */         result += (calendar.get(14) * 1) / millisPerUnit;
/*      */ 
/*      */ 
/*      */       
/*      */       case 14:
/* 1721 */         return result;
/*      */     } 
/*      */     throw new IllegalArgumentException("The fragment " + fragment + " is not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean truncatedEquals(Calendar cal1, Calendar cal2, int field) {
/* 1738 */     return (truncatedCompareTo(cal1, cal2, field) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean truncatedEquals(Date date1, Date date2, int field) {
/* 1755 */     return (truncatedCompareTo(date1, date2, field) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int truncatedCompareTo(Calendar cal1, Calendar cal2, int field) {
/* 1773 */     Calendar truncatedCal1 = truncate(cal1, field);
/* 1774 */     Calendar truncatedCal2 = truncate(cal2, field);
/* 1775 */     return truncatedCal1.compareTo(truncatedCal2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int truncatedCompareTo(Date date1, Date date2, int field) {
/* 1793 */     Date truncatedDate1 = truncate(date1, field);
/* 1794 */     Date truncatedDate2 = truncate(date2, field);
/* 1795 */     return truncatedDate1.compareTo(truncatedDate2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long getMillisPerUnit(int unit) {
/* 1808 */     long result = Long.MAX_VALUE;
/* 1809 */     switch (unit) {
/*      */       case 5:
/*      */       case 6:
/* 1812 */         result = 86400000L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1828 */         return result;case 11: result = 3600000L; return result;case 12: result = 60000L; return result;case 13: result = 1000L; return result;case 14: result = 1L; return result;
/*      */     } 
/*      */     throw new IllegalArgumentException("The unit " + unit + " cannot be represented is milleseconds");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class DateIterator
/*      */     implements Iterator<Calendar>
/*      */   {
/*      */     private final Calendar endFinal;
/*      */ 
/*      */     
/*      */     private final Calendar spot;
/*      */ 
/*      */ 
/*      */     
/*      */     DateIterator(Calendar startFinal, Calendar endFinal) {
/* 1847 */       this.endFinal = endFinal;
/* 1848 */       this.spot = startFinal;
/* 1849 */       this.spot.add(5, -1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1859 */       return this.spot.before(this.endFinal);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Calendar next() {
/* 1869 */       if (this.spot.equals(this.endFinal)) {
/* 1870 */         throw new NoSuchElementException();
/*      */       }
/* 1872 */       this.spot.add(5, 1);
/* 1873 */       return (Calendar)this.spot.clone();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1884 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\time\DateUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */