/*      */ package net.minecraft.util.org.apache.commons.lang3;
/*      */ 
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.nio.charset.Charset;
/*      */ import java.text.Normalizer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StringUtils
/*      */ {
/*      */   public static final String SPACE = " ";
/*      */   public static final String EMPTY = "";
/*      */   public static final String LF = "\n";
/*      */   public static final String CR = "\r";
/*      */   public static final int INDEX_NOT_FOUND = -1;
/*      */   private static final int PAD_LIMIT = 8192;
/*  182 */   private static final Pattern WHITESPACE_PATTERN = Pattern.compile("(?: |\\u00A0|\\s|[\\s&&[^ ]])\\s*");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(CharSequence cs) {
/*  218 */     return (cs == null || cs.length() == 0);
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
/*      */   public static boolean isNotEmpty(CharSequence cs) {
/*  237 */     return !isEmpty(cs);
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
/*      */   public static boolean isAnyEmpty(CharSequence... css) {
/*  258 */     if (ArrayUtils.isEmpty((Object[])css)) {
/*  259 */       return true;
/*      */     }
/*  261 */     for (CharSequence cs : css) {
/*  262 */       if (isEmpty(cs)) {
/*  263 */         return true;
/*      */       }
/*      */     } 
/*  266 */     return false;
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
/*      */   public static boolean isNoneEmpty(CharSequence... css) {
/*  287 */     return !isAnyEmpty(css);
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
/*      */   public static boolean isBlank(CharSequence cs) {
/*      */     int strLen;
/*  307 */     if (cs == null || (strLen = cs.length()) == 0) {
/*  308 */       return true;
/*      */     }
/*  310 */     for (int i = 0; i < strLen; i++) {
/*  311 */       if (!Character.isWhitespace(cs.charAt(i))) {
/*  312 */         return false;
/*      */       }
/*      */     } 
/*  315 */     return true;
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
/*      */   public static boolean isNotBlank(CharSequence cs) {
/*  336 */     return !isBlank(cs);
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
/*      */   public static boolean isAnyBlank(CharSequence... css) {
/*  358 */     if (ArrayUtils.isEmpty((Object[])css)) {
/*  359 */       return true;
/*      */     }
/*  361 */     for (CharSequence cs : css) {
/*  362 */       if (isBlank(cs)) {
/*  363 */         return true;
/*      */       }
/*      */     } 
/*  366 */     return false;
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
/*      */   public static boolean isNoneBlank(CharSequence... css) {
/*  388 */     return !isAnyBlank(css);
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
/*      */   public static String trim(String str) {
/*  417 */     return (str == null) ? null : str.trim();
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
/*      */   public static String trimToNull(String str) {
/*  443 */     String ts = trim(str);
/*  444 */     return isEmpty(ts) ? null : ts;
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
/*      */   public static String trimToEmpty(String str) {
/*  469 */     return (str == null) ? "" : str.trim();
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
/*      */   public static String strip(String str) {
/*  497 */     return strip(str, null);
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
/*      */   public static String stripToNull(String str) {
/*  524 */     if (str == null) {
/*  525 */       return null;
/*      */     }
/*  527 */     str = strip(str, null);
/*  528 */     return str.isEmpty() ? null : str;
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
/*      */   public static String stripToEmpty(String str) {
/*  554 */     return (str == null) ? "" : strip(str, null);
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
/*      */   public static String strip(String str, String stripChars) {
/*  584 */     if (isEmpty(str)) {
/*  585 */       return str;
/*      */     }
/*  587 */     str = stripStart(str, stripChars);
/*  588 */     return stripEnd(str, stripChars);
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
/*      */   public static String stripStart(String str, String stripChars) {
/*      */     int strLen;
/*  617 */     if (str == null || (strLen = str.length()) == 0) {
/*  618 */       return str;
/*      */     }
/*  620 */     int start = 0;
/*  621 */     if (stripChars == null) {
/*  622 */       while (start != strLen && Character.isWhitespace(str.charAt(start)))
/*  623 */         start++; 
/*      */     } else {
/*  625 */       if (stripChars.isEmpty()) {
/*  626 */         return str;
/*      */       }
/*  628 */       while (start != strLen && stripChars.indexOf(str.charAt(start)) != -1) {
/*  629 */         start++;
/*      */       }
/*      */     } 
/*  632 */     return str.substring(start);
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
/*      */   public static String stripEnd(String str, String stripChars) {
/*      */     int end;
/*  662 */     if (str == null || (end = str.length()) == 0) {
/*  663 */       return str;
/*      */     }
/*      */     
/*  666 */     if (stripChars == null) {
/*  667 */       while (end != 0 && Character.isWhitespace(str.charAt(end - 1)))
/*  668 */         end--; 
/*      */     } else {
/*  670 */       if (stripChars.isEmpty()) {
/*  671 */         return str;
/*      */       }
/*  673 */       while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != -1) {
/*  674 */         end--;
/*      */       }
/*      */     } 
/*  677 */     return str.substring(0, end);
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
/*      */   public static String[] stripAll(String... strs) {
/*  702 */     return stripAll(strs, null);
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
/*      */   public static String[] stripAll(String[] strs, String stripChars) {
/*      */     int strsLen;
/*  732 */     if (strs == null || (strsLen = strs.length) == 0) {
/*  733 */       return strs;
/*      */     }
/*  735 */     String[] newArr = new String[strsLen];
/*  736 */     for (int i = 0; i < strsLen; i++) {
/*  737 */       newArr[i] = strip(strs[i], stripChars);
/*      */     }
/*  739 */     return newArr;
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
/*      */   public static String stripAccents(String input) {
/*  761 */     if (input == null) {
/*  762 */       return null;
/*      */     }
/*  764 */     Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
/*  765 */     String decomposed = Normalizer.normalize(input, Normalizer.Form.NFD);
/*      */     
/*  767 */     return pattern.matcher(decomposed).replaceAll("");
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
/*      */   public static boolean equals(CharSequence cs1, CharSequence cs2) {
/*  794 */     if (cs1 == cs2) {
/*  795 */       return true;
/*      */     }
/*  797 */     if (cs1 == null || cs2 == null) {
/*  798 */       return false;
/*      */     }
/*  800 */     if (cs1 instanceof String && cs2 instanceof String) {
/*  801 */       return cs1.equals(cs2);
/*      */     }
/*  803 */     return CharSequenceUtils.regionMatches(cs1, false, 0, cs2, 0, Math.max(cs1.length(), cs2.length()));
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
/*      */   public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
/*  828 */     if (str1 == null || str2 == null)
/*  829 */       return (str1 == str2); 
/*  830 */     if (str1 == str2)
/*  831 */       return true; 
/*  832 */     if (str1.length() != str2.length()) {
/*  833 */       return false;
/*      */     }
/*  835 */     return CharSequenceUtils.regionMatches(str1, true, 0, str2, 0, str1.length());
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
/*      */   public static int indexOf(CharSequence seq, int searchChar) {
/*  862 */     if (isEmpty(seq)) {
/*  863 */       return -1;
/*      */     }
/*  865 */     return CharSequenceUtils.indexOf(seq, searchChar, 0);
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
/*      */   public static int indexOf(CharSequence seq, int searchChar, int startPos) {
/*  895 */     if (isEmpty(seq)) {
/*  896 */       return -1;
/*      */     }
/*  898 */     return CharSequenceUtils.indexOf(seq, searchChar, startPos);
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
/*      */   public static int indexOf(CharSequence seq, CharSequence searchSeq) {
/*  926 */     if (seq == null || searchSeq == null) {
/*  927 */       return -1;
/*      */     }
/*  929 */     return CharSequenceUtils.indexOf(seq, searchSeq, 0);
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
/*      */   public static int indexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
/*  966 */     if (seq == null || searchSeq == null) {
/*  967 */       return -1;
/*      */     }
/*  969 */     return CharSequenceUtils.indexOf(seq, searchSeq, startPos);
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
/*      */   public static int ordinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
/* 1007 */     return ordinalIndexOf(str, searchStr, ordinal, false);
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
/*      */   private static int ordinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal, boolean lastIndex) {
/* 1025 */     if (str == null || searchStr == null || ordinal <= 0) {
/* 1026 */       return -1;
/*      */     }
/* 1028 */     if (searchStr.length() == 0) {
/* 1029 */       return lastIndex ? str.length() : 0;
/*      */     }
/* 1031 */     int found = 0;
/* 1032 */     int index = lastIndex ? str.length() : -1;
/*      */     while (true) {
/* 1034 */       if (lastIndex) {
/* 1035 */         index = CharSequenceUtils.lastIndexOf(str, searchStr, index - 1);
/*      */       } else {
/* 1037 */         index = CharSequenceUtils.indexOf(str, searchStr, index + 1);
/*      */       } 
/* 1039 */       if (index < 0) {
/* 1040 */         return index;
/*      */       }
/* 1042 */       found++;
/* 1043 */       if (found >= ordinal) {
/* 1044 */         return index;
/*      */       }
/*      */     } 
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
/*      */   public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
/* 1073 */     return indexOfIgnoreCase(str, searchStr, 0);
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
/*      */   public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
/* 1109 */     if (str == null || searchStr == null) {
/* 1110 */       return -1;
/*      */     }
/* 1112 */     if (startPos < 0) {
/* 1113 */       startPos = 0;
/*      */     }
/* 1115 */     int endLimit = str.length() - searchStr.length() + 1;
/* 1116 */     if (startPos > endLimit) {
/* 1117 */       return -1;
/*      */     }
/* 1119 */     if (searchStr.length() == 0) {
/* 1120 */       return startPos;
/*      */     }
/* 1122 */     for (int i = startPos; i < endLimit; i++) {
/* 1123 */       if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, searchStr.length())) {
/* 1124 */         return i;
/*      */       }
/*      */     } 
/* 1127 */     return -1;
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
/*      */   public static int lastIndexOf(CharSequence seq, int searchChar) {
/* 1153 */     if (isEmpty(seq)) {
/* 1154 */       return -1;
/*      */     }
/* 1156 */     return CharSequenceUtils.lastIndexOf(seq, searchChar, seq.length());
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
/*      */   public static int lastIndexOf(CharSequence seq, int searchChar, int startPos) {
/* 1191 */     if (isEmpty(seq)) {
/* 1192 */       return -1;
/*      */     }
/* 1194 */     return CharSequenceUtils.lastIndexOf(seq, searchChar, startPos);
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
/*      */   public static int lastIndexOf(CharSequence seq, CharSequence searchSeq) {
/* 1221 */     if (seq == null || searchSeq == null) {
/* 1222 */       return -1;
/*      */     }
/* 1224 */     return CharSequenceUtils.lastIndexOf(seq, searchSeq, seq.length());
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
/*      */   public static int lastOrdinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
/* 1262 */     return ordinalIndexOf(str, searchStr, ordinal, true);
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
/*      */   public static int lastIndexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
/* 1302 */     if (seq == null || searchSeq == null) {
/* 1303 */       return -1;
/*      */     }
/* 1305 */     return CharSequenceUtils.lastIndexOf(seq, searchSeq, startPos);
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
/*      */   public static int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
/* 1332 */     if (str == null || searchStr == null) {
/* 1333 */       return -1;
/*      */     }
/* 1335 */     return lastIndexOfIgnoreCase(str, searchStr, str.length());
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
/*      */   public static int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
/* 1371 */     if (str == null || searchStr == null) {
/* 1372 */       return -1;
/*      */     }
/* 1374 */     if (startPos > str.length() - searchStr.length()) {
/* 1375 */       startPos = str.length() - searchStr.length();
/*      */     }
/* 1377 */     if (startPos < 0) {
/* 1378 */       return -1;
/*      */     }
/* 1380 */     if (searchStr.length() == 0) {
/* 1381 */       return startPos;
/*      */     }
/*      */     
/* 1384 */     for (int i = startPos; i >= 0; i--) {
/* 1385 */       if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, searchStr.length())) {
/* 1386 */         return i;
/*      */       }
/*      */     } 
/* 1389 */     return -1;
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
/*      */   public static boolean contains(CharSequence seq, int searchChar) {
/* 1415 */     if (isEmpty(seq)) {
/* 1416 */       return false;
/*      */     }
/* 1418 */     return (CharSequenceUtils.indexOf(seq, searchChar, 0) >= 0);
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
/*      */   public static boolean contains(CharSequence seq, CharSequence searchSeq) {
/* 1444 */     if (seq == null || searchSeq == null) {
/* 1445 */       return false;
/*      */     }
/* 1447 */     return (CharSequenceUtils.indexOf(seq, searchSeq, 0) >= 0);
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
/*      */   public static boolean containsIgnoreCase(CharSequence str, CharSequence searchStr) {
/* 1475 */     if (str == null || searchStr == null) {
/* 1476 */       return false;
/*      */     }
/* 1478 */     int len = searchStr.length();
/* 1479 */     int max = str.length() - len;
/* 1480 */     for (int i = 0; i <= max; i++) {
/* 1481 */       if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, len)) {
/* 1482 */         return true;
/*      */       }
/*      */     } 
/* 1485 */     return false;
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
/*      */   public static boolean containsWhitespace(CharSequence seq) {
/* 1498 */     if (isEmpty(seq)) {
/* 1499 */       return false;
/*      */     }
/* 1501 */     int strLen = seq.length();
/* 1502 */     for (int i = 0; i < strLen; i++) {
/* 1503 */       if (Character.isWhitespace(seq.charAt(i))) {
/* 1504 */         return true;
/*      */       }
/*      */     } 
/* 1507 */     return false;
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
/*      */   public static int indexOfAny(CharSequence cs, char... searchChars) {
/* 1536 */     if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
/* 1537 */       return -1;
/*      */     }
/* 1539 */     int csLen = cs.length();
/* 1540 */     int csLast = csLen - 1;
/* 1541 */     int searchLen = searchChars.length;
/* 1542 */     int searchLast = searchLen - 1;
/* 1543 */     for (int i = 0; i < csLen; i++) {
/* 1544 */       char ch = cs.charAt(i);
/* 1545 */       for (int j = 0; j < searchLen; j++) {
/* 1546 */         if (searchChars[j] == ch) {
/* 1547 */           if (i < csLast && j < searchLast && Character.isHighSurrogate(ch)) {
/*      */             
/* 1549 */             if (searchChars[j + 1] == cs.charAt(i + 1)) {
/* 1550 */               return i;
/*      */             }
/*      */           } else {
/* 1553 */             return i;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 1558 */     return -1;
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
/*      */   public static int indexOfAny(CharSequence cs, String searchChars) {
/* 1585 */     if (isEmpty(cs) || isEmpty(searchChars)) {
/* 1586 */       return -1;
/*      */     }
/* 1588 */     return indexOfAny(cs, searchChars.toCharArray());
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
/*      */   public static boolean containsAny(CharSequence cs, char... searchChars) {
/* 1618 */     if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
/* 1619 */       return false;
/*      */     }
/* 1621 */     int csLength = cs.length();
/* 1622 */     int searchLength = searchChars.length;
/* 1623 */     int csLast = csLength - 1;
/* 1624 */     int searchLast = searchLength - 1;
/* 1625 */     for (int i = 0; i < csLength; i++) {
/* 1626 */       char ch = cs.charAt(i);
/* 1627 */       for (int j = 0; j < searchLength; j++) {
/* 1628 */         if (searchChars[j] == ch) {
/* 1629 */           if (Character.isHighSurrogate(ch)) {
/* 1630 */             if (j == searchLast)
/*      */             {
/* 1632 */               return true;
/*      */             }
/* 1634 */             if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
/* 1635 */               return true;
/*      */             }
/*      */           } else {
/*      */             
/* 1639 */             return true;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 1644 */     return false;
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
/*      */   public static boolean containsAny(CharSequence cs, CharSequence searchChars) {
/* 1676 */     if (searchChars == null) {
/* 1677 */       return false;
/*      */     }
/* 1679 */     return containsAny(cs, CharSequenceUtils.toCharArray(searchChars));
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
/*      */   public static int indexOfAnyBut(CharSequence cs, char... searchChars) {
/* 1709 */     if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
/* 1710 */       return -1;
/*      */     }
/* 1712 */     int csLen = cs.length();
/* 1713 */     int csLast = csLen - 1;
/* 1714 */     int searchLen = searchChars.length;
/* 1715 */     int searchLast = searchLen - 1;
/*      */     
/* 1717 */     for (int i = 0; i < csLen; i++) {
/* 1718 */       char ch = cs.charAt(i);
/* 1719 */       int j = 0; while (true) { if (j < searchLen) {
/* 1720 */           if (searchChars[j] == ch && (
/* 1721 */             i >= csLast || j >= searchLast || !Character.isHighSurrogate(ch) || 
/* 1722 */             searchChars[j + 1] == cs.charAt(i + 1))) {
/*      */             break;
/*      */           }
/*      */           
/*      */           j++;
/*      */           
/*      */           continue;
/*      */         } 
/* 1730 */         return i; }
/*      */     
/* 1732 */     }  return -1;
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
/*      */   public static int indexOfAnyBut(CharSequence seq, CharSequence searchChars) {
/* 1759 */     if (isEmpty(seq) || isEmpty(searchChars)) {
/* 1760 */       return -1;
/*      */     }
/* 1762 */     int strLen = seq.length();
/* 1763 */     for (int i = 0; i < strLen; i++) {
/* 1764 */       char ch = seq.charAt(i);
/* 1765 */       boolean chFound = (CharSequenceUtils.indexOf(searchChars, ch, 0) >= 0);
/* 1766 */       if (i + 1 < strLen && Character.isHighSurrogate(ch)) {
/* 1767 */         char ch2 = seq.charAt(i + 1);
/* 1768 */         if (chFound && CharSequenceUtils.indexOf(searchChars, ch2, 0) < 0) {
/* 1769 */           return i;
/*      */         }
/*      */       }
/* 1772 */       else if (!chFound) {
/* 1773 */         return i;
/*      */       } 
/*      */     } 
/*      */     
/* 1777 */     return -1;
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
/*      */   public static boolean containsOnly(CharSequence cs, char... valid) {
/* 1806 */     if (valid == null || cs == null) {
/* 1807 */       return false;
/*      */     }
/* 1809 */     if (cs.length() == 0) {
/* 1810 */       return true;
/*      */     }
/* 1812 */     if (valid.length == 0) {
/* 1813 */       return false;
/*      */     }
/* 1815 */     return (indexOfAnyBut(cs, valid) == -1);
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
/*      */   public static boolean containsOnly(CharSequence cs, String validChars) {
/* 1842 */     if (cs == null || validChars == null) {
/* 1843 */       return false;
/*      */     }
/* 1845 */     return containsOnly(cs, validChars.toCharArray());
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
/*      */   public static boolean containsNone(CharSequence cs, char... searchChars) {
/* 1874 */     if (cs == null || searchChars == null) {
/* 1875 */       return true;
/*      */     }
/* 1877 */     int csLen = cs.length();
/* 1878 */     int csLast = csLen - 1;
/* 1879 */     int searchLen = searchChars.length;
/* 1880 */     int searchLast = searchLen - 1;
/* 1881 */     for (int i = 0; i < csLen; i++) {
/* 1882 */       char ch = cs.charAt(i);
/* 1883 */       for (int j = 0; j < searchLen; j++) {
/* 1884 */         if (searchChars[j] == ch) {
/* 1885 */           if (Character.isHighSurrogate(ch)) {
/* 1886 */             if (j == searchLast)
/*      */             {
/* 1888 */               return false;
/*      */             }
/* 1890 */             if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
/* 1891 */               return false;
/*      */             }
/*      */           } else {
/*      */             
/* 1895 */             return false;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 1900 */     return true;
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
/*      */   public static boolean containsNone(CharSequence cs, String invalidChars) {
/* 1927 */     if (cs == null || invalidChars == null) {
/* 1928 */       return true;
/*      */     }
/* 1930 */     return containsNone(cs, invalidChars.toCharArray());
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
/*      */   public static int indexOfAny(CharSequence str, CharSequence... searchStrs) {
/* 1963 */     if (str == null || searchStrs == null) {
/* 1964 */       return -1;
/*      */     }
/* 1966 */     int sz = searchStrs.length;
/*      */ 
/*      */     
/* 1969 */     int ret = Integer.MAX_VALUE;
/*      */     
/* 1971 */     int tmp = 0;
/* 1972 */     for (int i = 0; i < sz; i++) {
/* 1973 */       CharSequence search = searchStrs[i];
/* 1974 */       if (search != null) {
/*      */ 
/*      */         
/* 1977 */         tmp = CharSequenceUtils.indexOf(str, search, 0);
/* 1978 */         if (tmp != -1)
/*      */         {
/*      */ 
/*      */           
/* 1982 */           if (tmp < ret)
/* 1983 */             ret = tmp; 
/*      */         }
/*      */       } 
/*      */     } 
/* 1987 */     return (ret == Integer.MAX_VALUE) ? -1 : ret;
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
/*      */   public static int lastIndexOfAny(CharSequence str, CharSequence... searchStrs) {
/* 2017 */     if (str == null || searchStrs == null) {
/* 2018 */       return -1;
/*      */     }
/* 2020 */     int sz = searchStrs.length;
/* 2021 */     int ret = -1;
/* 2022 */     int tmp = 0;
/* 2023 */     for (int i = 0; i < sz; i++) {
/* 2024 */       CharSequence search = searchStrs[i];
/* 2025 */       if (search != null) {
/*      */ 
/*      */         
/* 2028 */         tmp = CharSequenceUtils.lastIndexOf(str, search, str.length());
/* 2029 */         if (tmp > ret)
/* 2030 */           ret = tmp; 
/*      */       } 
/*      */     } 
/* 2033 */     return ret;
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
/*      */   public static String substring(String str, int start) {
/* 2063 */     if (str == null) {
/* 2064 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 2068 */     if (start < 0) {
/* 2069 */       start = str.length() + start;
/*      */     }
/*      */     
/* 2072 */     if (start < 0) {
/* 2073 */       start = 0;
/*      */     }
/* 2075 */     if (start > str.length()) {
/* 2076 */       return "";
/*      */     }
/*      */     
/* 2079 */     return str.substring(start);
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
/*      */   public static String substring(String str, int start, int end) {
/* 2118 */     if (str == null) {
/* 2119 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 2123 */     if (end < 0) {
/* 2124 */       end = str.length() + end;
/*      */     }
/* 2126 */     if (start < 0) {
/* 2127 */       start = str.length() + start;
/*      */     }
/*      */ 
/*      */     
/* 2131 */     if (end > str.length()) {
/* 2132 */       end = str.length();
/*      */     }
/*      */ 
/*      */     
/* 2136 */     if (start > end) {
/* 2137 */       return "";
/*      */     }
/*      */     
/* 2140 */     if (start < 0) {
/* 2141 */       start = 0;
/*      */     }
/* 2143 */     if (end < 0) {
/* 2144 */       end = 0;
/*      */     }
/*      */     
/* 2147 */     return str.substring(start, end);
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
/*      */   public static String left(String str, int len) {
/* 2173 */     if (str == null) {
/* 2174 */       return null;
/*      */     }
/* 2176 */     if (len < 0) {
/* 2177 */       return "";
/*      */     }
/* 2179 */     if (str.length() <= len) {
/* 2180 */       return str;
/*      */     }
/* 2182 */     return str.substring(0, len);
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
/*      */   public static String right(String str, int len) {
/* 2206 */     if (str == null) {
/* 2207 */       return null;
/*      */     }
/* 2209 */     if (len < 0) {
/* 2210 */       return "";
/*      */     }
/* 2212 */     if (str.length() <= len) {
/* 2213 */       return str;
/*      */     }
/* 2215 */     return str.substring(str.length() - len);
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
/*      */   public static String mid(String str, int pos, int len) {
/* 2244 */     if (str == null) {
/* 2245 */       return null;
/*      */     }
/* 2247 */     if (len < 0 || pos > str.length()) {
/* 2248 */       return "";
/*      */     }
/* 2250 */     if (pos < 0) {
/* 2251 */       pos = 0;
/*      */     }
/* 2253 */     if (str.length() <= pos + len) {
/* 2254 */       return str.substring(pos);
/*      */     }
/* 2256 */     return str.substring(pos, pos + len);
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
/*      */   public static String substringBefore(String str, String separator) {
/* 2289 */     if (isEmpty(str) || separator == null) {
/* 2290 */       return str;
/*      */     }
/* 2292 */     if (separator.isEmpty()) {
/* 2293 */       return "";
/*      */     }
/* 2295 */     int pos = str.indexOf(separator);
/* 2296 */     if (pos == -1) {
/* 2297 */       return str;
/*      */     }
/* 2299 */     return str.substring(0, pos);
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
/*      */   public static String substringAfter(String str, String separator) {
/* 2331 */     if (isEmpty(str)) {
/* 2332 */       return str;
/*      */     }
/* 2334 */     if (separator == null) {
/* 2335 */       return "";
/*      */     }
/* 2337 */     int pos = str.indexOf(separator);
/* 2338 */     if (pos == -1) {
/* 2339 */       return "";
/*      */     }
/* 2341 */     return str.substring(pos + separator.length());
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
/*      */   public static String substringBeforeLast(String str, String separator) {
/* 2372 */     if (isEmpty(str) || isEmpty(separator)) {
/* 2373 */       return str;
/*      */     }
/* 2375 */     int pos = str.lastIndexOf(separator);
/* 2376 */     if (pos == -1) {
/* 2377 */       return str;
/*      */     }
/* 2379 */     return str.substring(0, pos);
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
/*      */   public static String substringAfterLast(String str, String separator) {
/* 2412 */     if (isEmpty(str)) {
/* 2413 */       return str;
/*      */     }
/* 2415 */     if (isEmpty(separator)) {
/* 2416 */       return "";
/*      */     }
/* 2418 */     int pos = str.lastIndexOf(separator);
/* 2419 */     if (pos == -1 || pos == str.length() - separator.length()) {
/* 2420 */       return "";
/*      */     }
/* 2422 */     return str.substring(pos + separator.length());
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
/*      */   public static String substringBetween(String str, String tag) {
/* 2449 */     return substringBetween(str, tag, tag);
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
/*      */   public static String substringBetween(String str, String open, String close) {
/* 2480 */     if (str == null || open == null || close == null) {
/* 2481 */       return null;
/*      */     }
/* 2483 */     int start = str.indexOf(open);
/* 2484 */     if (start != -1) {
/* 2485 */       int end = str.indexOf(close, start + open.length());
/* 2486 */       if (end != -1) {
/* 2487 */         return str.substring(start + open.length(), end);
/*      */       }
/*      */     } 
/* 2490 */     return null;
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
/*      */   public static String[] substringsBetween(String str, String open, String close) {
/* 2516 */     if (str == null || isEmpty(open) || isEmpty(close)) {
/* 2517 */       return null;
/*      */     }
/* 2519 */     int strLen = str.length();
/* 2520 */     if (strLen == 0) {
/* 2521 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/* 2523 */     int closeLen = close.length();
/* 2524 */     int openLen = open.length();
/* 2525 */     List<String> list = new ArrayList<String>();
/* 2526 */     int pos = 0;
/* 2527 */     while (pos < strLen - closeLen) {
/* 2528 */       int start = str.indexOf(open, pos);
/* 2529 */       if (start < 0) {
/*      */         break;
/*      */       }
/* 2532 */       start += openLen;
/* 2533 */       int end = str.indexOf(close, start);
/* 2534 */       if (end < 0) {
/*      */         break;
/*      */       }
/* 2537 */       list.add(str.substring(start, end));
/* 2538 */       pos = end + closeLen;
/*      */     } 
/* 2540 */     if (list.isEmpty()) {
/* 2541 */       return null;
/*      */     }
/* 2543 */     return list.<String>toArray(new String[list.size()]);
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
/*      */   public static String[] split(String str) {
/* 2574 */     return split(str, null, -1);
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
/*      */   public static String[] split(String str, char separatorChar) {
/* 2602 */     return splitWorker(str, separatorChar, false);
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
/*      */   public static String[] split(String str, String separatorChars) {
/* 2631 */     return splitWorker(str, separatorChars, -1, false);
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
/*      */   public static String[] split(String str, String separatorChars, int max) {
/* 2665 */     return splitWorker(str, separatorChars, max, false);
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
/*      */   public static String[] splitByWholeSeparator(String str, String separator) {
/* 2692 */     return splitByWholeSeparatorWorker(str, separator, -1, false);
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
/*      */   public static String[] splitByWholeSeparator(String str, String separator, int max) {
/* 2723 */     return splitByWholeSeparatorWorker(str, separator, max, false);
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
/*      */   public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator) {
/* 2752 */     return splitByWholeSeparatorWorker(str, separator, -1, true);
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
/*      */   public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max) {
/* 2785 */     return splitByWholeSeparatorWorker(str, separator, max, true);
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
/*      */   private static String[] splitByWholeSeparatorWorker(String str, String separator, int max, boolean preserveAllTokens) {
/* 2804 */     if (str == null) {
/* 2805 */       return null;
/*      */     }
/*      */     
/* 2808 */     int len = str.length();
/*      */     
/* 2810 */     if (len == 0) {
/* 2811 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/*      */     
/* 2814 */     if (separator == null || "".equals(separator))
/*      */     {
/* 2816 */       return splitWorker(str, null, max, preserveAllTokens);
/*      */     }
/*      */     
/* 2819 */     int separatorLength = separator.length();
/*      */     
/* 2821 */     ArrayList<String> substrings = new ArrayList<String>();
/* 2822 */     int numberOfSubstrings = 0;
/* 2823 */     int beg = 0;
/* 2824 */     int end = 0;
/* 2825 */     while (end < len) {
/* 2826 */       end = str.indexOf(separator, beg);
/*      */       
/* 2828 */       if (end > -1) {
/* 2829 */         if (end > beg) {
/* 2830 */           numberOfSubstrings++;
/*      */           
/* 2832 */           if (numberOfSubstrings == max) {
/* 2833 */             end = len;
/* 2834 */             substrings.add(str.substring(beg));
/*      */             
/*      */             continue;
/*      */           } 
/* 2838 */           substrings.add(str.substring(beg, end));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2843 */           beg = end + separatorLength;
/*      */           
/*      */           continue;
/*      */         } 
/* 2847 */         if (preserveAllTokens) {
/* 2848 */           numberOfSubstrings++;
/* 2849 */           if (numberOfSubstrings == max) {
/* 2850 */             end = len;
/* 2851 */             substrings.add(str.substring(beg));
/*      */           } else {
/* 2853 */             substrings.add("");
/*      */           } 
/*      */         } 
/* 2856 */         beg = end + separatorLength;
/*      */         
/*      */         continue;
/*      */       } 
/* 2860 */       substrings.add(str.substring(beg));
/* 2861 */       end = len;
/*      */     } 
/*      */ 
/*      */     
/* 2865 */     return substrings.<String>toArray(new String[substrings.size()]);
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
/*      */   public static String[] splitPreserveAllTokens(String str) {
/* 2894 */     return splitWorker(str, null, -1, true);
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
/*      */   public static String[] splitPreserveAllTokens(String str, char separatorChar) {
/* 2930 */     return splitWorker(str, separatorChar, true);
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
/*      */   private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens) {
/* 2948 */     if (str == null) {
/* 2949 */       return null;
/*      */     }
/* 2951 */     int len = str.length();
/* 2952 */     if (len == 0) {
/* 2953 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/* 2955 */     List<String> list = new ArrayList<String>();
/* 2956 */     int i = 0, start = 0;
/* 2957 */     boolean match = false;
/* 2958 */     boolean lastMatch = false;
/* 2959 */     while (i < len) {
/* 2960 */       if (str.charAt(i) == separatorChar) {
/* 2961 */         if (match || preserveAllTokens) {
/* 2962 */           list.add(str.substring(start, i));
/* 2963 */           match = false;
/* 2964 */           lastMatch = true;
/*      */         } 
/* 2966 */         start = ++i;
/*      */         continue;
/*      */       } 
/* 2969 */       lastMatch = false;
/* 2970 */       match = true;
/* 2971 */       i++;
/*      */     } 
/* 2973 */     if (match || (preserveAllTokens && lastMatch)) {
/* 2974 */       list.add(str.substring(start, i));
/*      */     }
/* 2976 */     return list.<String>toArray(new String[list.size()]);
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
/*      */   public static String[] splitPreserveAllTokens(String str, String separatorChars) {
/* 3013 */     return splitWorker(str, separatorChars, -1, true);
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
/*      */   public static String[] splitPreserveAllTokens(String str, String separatorChars, int max) {
/* 3053 */     return splitWorker(str, separatorChars, max, true);
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
/*      */   private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
/* 3075 */     if (str == null) {
/* 3076 */       return null;
/*      */     }
/* 3078 */     int len = str.length();
/* 3079 */     if (len == 0) {
/* 3080 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/* 3082 */     List<String> list = new ArrayList<String>();
/* 3083 */     int sizePlus1 = 1;
/* 3084 */     int i = 0, start = 0;
/* 3085 */     boolean match = false;
/* 3086 */     boolean lastMatch = false;
/* 3087 */     if (separatorChars == null) {
/*      */       
/* 3089 */       while (i < len) {
/* 3090 */         if (Character.isWhitespace(str.charAt(i))) {
/* 3091 */           if (match || preserveAllTokens) {
/* 3092 */             lastMatch = true;
/* 3093 */             if (sizePlus1++ == max) {
/* 3094 */               i = len;
/* 3095 */               lastMatch = false;
/*      */             } 
/* 3097 */             list.add(str.substring(start, i));
/* 3098 */             match = false;
/*      */           } 
/* 3100 */           start = ++i;
/*      */           continue;
/*      */         } 
/* 3103 */         lastMatch = false;
/* 3104 */         match = true;
/* 3105 */         i++;
/*      */       } 
/* 3107 */     } else if (separatorChars.length() == 1) {
/*      */       
/* 3109 */       char sep = separatorChars.charAt(0);
/* 3110 */       while (i < len) {
/* 3111 */         if (str.charAt(i) == sep) {
/* 3112 */           if (match || preserveAllTokens) {
/* 3113 */             lastMatch = true;
/* 3114 */             if (sizePlus1++ == max) {
/* 3115 */               i = len;
/* 3116 */               lastMatch = false;
/*      */             } 
/* 3118 */             list.add(str.substring(start, i));
/* 3119 */             match = false;
/*      */           } 
/* 3121 */           start = ++i;
/*      */           continue;
/*      */         } 
/* 3124 */         lastMatch = false;
/* 3125 */         match = true;
/* 3126 */         i++;
/*      */       } 
/*      */     } else {
/*      */       
/* 3130 */       while (i < len) {
/* 3131 */         if (separatorChars.indexOf(str.charAt(i)) >= 0) {
/* 3132 */           if (match || preserveAllTokens) {
/* 3133 */             lastMatch = true;
/* 3134 */             if (sizePlus1++ == max) {
/* 3135 */               i = len;
/* 3136 */               lastMatch = false;
/*      */             } 
/* 3138 */             list.add(str.substring(start, i));
/* 3139 */             match = false;
/*      */           } 
/* 3141 */           start = ++i;
/*      */           continue;
/*      */         } 
/* 3144 */         lastMatch = false;
/* 3145 */         match = true;
/* 3146 */         i++;
/*      */       } 
/*      */     } 
/* 3149 */     if (match || (preserveAllTokens && lastMatch)) {
/* 3150 */       list.add(str.substring(start, i));
/*      */     }
/* 3152 */     return list.<String>toArray(new String[list.size()]);
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
/*      */   public static String[] splitByCharacterType(String str) {
/* 3175 */     return splitByCharacterType(str, false);
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
/*      */   public static String[] splitByCharacterTypeCamelCase(String str) {
/* 3203 */     return splitByCharacterType(str, true);
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
/*      */   private static String[] splitByCharacterType(String str, boolean camelCase) {
/* 3221 */     if (str == null) {
/* 3222 */       return null;
/*      */     }
/* 3224 */     if (str.isEmpty()) {
/* 3225 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/* 3227 */     char[] c = str.toCharArray();
/* 3228 */     List<String> list = new ArrayList<String>();
/* 3229 */     int tokenStart = 0;
/* 3230 */     int currentType = Character.getType(c[tokenStart]);
/* 3231 */     for (int pos = tokenStart + 1; pos < c.length; pos++) {
/* 3232 */       int type = Character.getType(c[pos]);
/* 3233 */       if (type != currentType) {
/*      */ 
/*      */         
/* 3236 */         if (camelCase && type == 2 && currentType == 1) {
/* 3237 */           int newTokenStart = pos - 1;
/* 3238 */           if (newTokenStart != tokenStart) {
/* 3239 */             list.add(new String(c, tokenStart, newTokenStart - tokenStart));
/* 3240 */             tokenStart = newTokenStart;
/*      */           } 
/*      */         } else {
/* 3243 */           list.add(new String(c, tokenStart, pos - tokenStart));
/* 3244 */           tokenStart = pos;
/*      */         } 
/* 3246 */         currentType = type;
/*      */       } 
/* 3248 */     }  list.add(new String(c, tokenStart, c.length - tokenStart));
/* 3249 */     return list.<String>toArray(new String[list.size()]);
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
/*      */   public static <T> String join(T... elements) {
/* 3277 */     return join((Object[])elements, (String)null);
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
/*      */   public static String join(Object[] array, char separator) {
/* 3303 */     if (array == null) {
/* 3304 */       return null;
/*      */     }
/* 3306 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(long[] array, char separator) {
/* 3335 */     if (array == null) {
/* 3336 */       return null;
/*      */     }
/* 3338 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(int[] array, char separator) {
/* 3367 */     if (array == null) {
/* 3368 */       return null;
/*      */     }
/* 3370 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(short[] array, char separator) {
/* 3399 */     if (array == null) {
/* 3400 */       return null;
/*      */     }
/* 3402 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(byte[] array, char separator) {
/* 3431 */     if (array == null) {
/* 3432 */       return null;
/*      */     }
/* 3434 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(char[] array, char separator) {
/* 3463 */     if (array == null) {
/* 3464 */       return null;
/*      */     }
/* 3466 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(float[] array, char separator) {
/* 3495 */     if (array == null) {
/* 3496 */       return null;
/*      */     }
/* 3498 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(double[] array, char separator) {
/* 3527 */     if (array == null) {
/* 3528 */       return null;
/*      */     }
/* 3530 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(Object[] array, char separator, int startIndex, int endIndex) {
/* 3561 */     if (array == null) {
/* 3562 */       return null;
/*      */     }
/* 3564 */     int noOfItems = endIndex - startIndex;
/* 3565 */     if (noOfItems <= 0) {
/* 3566 */       return "";
/*      */     }
/* 3568 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/* 3569 */     for (int i = startIndex; i < endIndex; i++) {
/* 3570 */       if (i > startIndex) {
/* 3571 */         buf.append(separator);
/*      */       }
/* 3573 */       if (array[i] != null) {
/* 3574 */         buf.append(array[i]);
/*      */       }
/*      */     } 
/* 3577 */     return buf.toString();
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
/*      */   public static String join(long[] array, char separator, int startIndex, int endIndex) {
/* 3612 */     if (array == null) {
/* 3613 */       return null;
/*      */     }
/* 3615 */     int noOfItems = endIndex - startIndex;
/* 3616 */     if (noOfItems <= 0) {
/* 3617 */       return "";
/*      */     }
/* 3619 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/* 3620 */     for (int i = startIndex; i < endIndex; i++) {
/* 3621 */       if (i > startIndex) {
/* 3622 */         buf.append(separator);
/*      */       }
/* 3624 */       buf.append(array[i]);
/*      */     } 
/* 3626 */     return buf.toString();
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
/*      */   public static String join(int[] array, char separator, int startIndex, int endIndex) {
/* 3661 */     if (array == null) {
/* 3662 */       return null;
/*      */     }
/* 3664 */     int noOfItems = endIndex - startIndex;
/* 3665 */     if (noOfItems <= 0) {
/* 3666 */       return "";
/*      */     }
/* 3668 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/* 3669 */     for (int i = startIndex; i < endIndex; i++) {
/* 3670 */       if (i > startIndex) {
/* 3671 */         buf.append(separator);
/*      */       }
/* 3673 */       buf.append(array[i]);
/*      */     } 
/* 3675 */     return buf.toString();
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
/*      */   public static String join(byte[] array, char separator, int startIndex, int endIndex) {
/* 3710 */     if (array == null) {
/* 3711 */       return null;
/*      */     }
/* 3713 */     int noOfItems = endIndex - startIndex;
/* 3714 */     if (noOfItems <= 0) {
/* 3715 */       return "";
/*      */     }
/* 3717 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/* 3718 */     for (int i = startIndex; i < endIndex; i++) {
/* 3719 */       if (i > startIndex) {
/* 3720 */         buf.append(separator);
/*      */       }
/* 3722 */       buf.append(array[i]);
/*      */     } 
/* 3724 */     return buf.toString();
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
/*      */   public static String join(short[] array, char separator, int startIndex, int endIndex) {
/* 3759 */     if (array == null) {
/* 3760 */       return null;
/*      */     }
/* 3762 */     int noOfItems = endIndex - startIndex;
/* 3763 */     if (noOfItems <= 0) {
/* 3764 */       return "";
/*      */     }
/* 3766 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/* 3767 */     for (int i = startIndex; i < endIndex; i++) {
/* 3768 */       if (i > startIndex) {
/* 3769 */         buf.append(separator);
/*      */       }
/* 3771 */       buf.append(array[i]);
/*      */     } 
/* 3773 */     return buf.toString();
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
/*      */   public static String join(char[] array, char separator, int startIndex, int endIndex) {
/* 3808 */     if (array == null) {
/* 3809 */       return null;
/*      */     }
/* 3811 */     int noOfItems = endIndex - startIndex;
/* 3812 */     if (noOfItems <= 0) {
/* 3813 */       return "";
/*      */     }
/* 3815 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/* 3816 */     for (int i = startIndex; i < endIndex; i++) {
/* 3817 */       if (i > startIndex) {
/* 3818 */         buf.append(separator);
/*      */       }
/* 3820 */       buf.append(array[i]);
/*      */     } 
/* 3822 */     return buf.toString();
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
/*      */   public static String join(double[] array, char separator, int startIndex, int endIndex) {
/* 3857 */     if (array == null) {
/* 3858 */       return null;
/*      */     }
/* 3860 */     int noOfItems = endIndex - startIndex;
/* 3861 */     if (noOfItems <= 0) {
/* 3862 */       return "";
/*      */     }
/* 3864 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/* 3865 */     for (int i = startIndex; i < endIndex; i++) {
/* 3866 */       if (i > startIndex) {
/* 3867 */         buf.append(separator);
/*      */       }
/* 3869 */       buf.append(array[i]);
/*      */     } 
/* 3871 */     return buf.toString();
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
/*      */   public static String join(float[] array, char separator, int startIndex, int endIndex) {
/* 3906 */     if (array == null) {
/* 3907 */       return null;
/*      */     }
/* 3909 */     int noOfItems = endIndex - startIndex;
/* 3910 */     if (noOfItems <= 0) {
/* 3911 */       return "";
/*      */     }
/* 3913 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/* 3914 */     for (int i = startIndex; i < endIndex; i++) {
/* 3915 */       if (i > startIndex) {
/* 3916 */         buf.append(separator);
/*      */       }
/* 3918 */       buf.append(array[i]);
/*      */     } 
/* 3920 */     return buf.toString();
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
/*      */   public static String join(Object[] array, String separator) {
/* 3948 */     if (array == null) {
/* 3949 */       return null;
/*      */     }
/* 3951 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(Object[] array, String separator, int startIndex, int endIndex) {
/* 3990 */     if (array == null) {
/* 3991 */       return null;
/*      */     }
/* 3993 */     if (separator == null) {
/* 3994 */       separator = "";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3999 */     int noOfItems = endIndex - startIndex;
/* 4000 */     if (noOfItems <= 0) {
/* 4001 */       return "";
/*      */     }
/*      */     
/* 4004 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/*      */     
/* 4006 */     for (int i = startIndex; i < endIndex; i++) {
/* 4007 */       if (i > startIndex) {
/* 4008 */         buf.append(separator);
/*      */       }
/* 4010 */       if (array[i] != null) {
/* 4011 */         buf.append(array[i]);
/*      */       }
/*      */     } 
/* 4014 */     return buf.toString();
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
/*      */   public static String join(Iterator<?> iterator, char separator) {
/* 4034 */     if (iterator == null) {
/* 4035 */       return null;
/*      */     }
/* 4037 */     if (!iterator.hasNext()) {
/* 4038 */       return "";
/*      */     }
/* 4040 */     Object first = iterator.next();
/* 4041 */     if (!iterator.hasNext()) {
/* 4042 */       return ObjectUtils.toString(first);
/*      */     }
/*      */ 
/*      */     
/* 4046 */     StringBuilder buf = new StringBuilder(256);
/* 4047 */     if (first != null) {
/* 4048 */       buf.append(first);
/*      */     }
/*      */     
/* 4051 */     while (iterator.hasNext()) {
/* 4052 */       buf.append(separator);
/* 4053 */       Object obj = iterator.next();
/* 4054 */       if (obj != null) {
/* 4055 */         buf.append(obj);
/*      */       }
/*      */     } 
/*      */     
/* 4059 */     return buf.toString();
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
/*      */   public static String join(Iterator<?> iterator, String separator) {
/* 4078 */     if (iterator == null) {
/* 4079 */       return null;
/*      */     }
/* 4081 */     if (!iterator.hasNext()) {
/* 4082 */       return "";
/*      */     }
/* 4084 */     Object first = iterator.next();
/* 4085 */     if (!iterator.hasNext()) {
/* 4086 */       return ObjectUtils.toString(first);
/*      */     }
/*      */ 
/*      */     
/* 4090 */     StringBuilder buf = new StringBuilder(256);
/* 4091 */     if (first != null) {
/* 4092 */       buf.append(first);
/*      */     }
/*      */     
/* 4095 */     while (iterator.hasNext()) {
/* 4096 */       if (separator != null) {
/* 4097 */         buf.append(separator);
/*      */       }
/* 4099 */       Object obj = iterator.next();
/* 4100 */       if (obj != null) {
/* 4101 */         buf.append(obj);
/*      */       }
/*      */     } 
/* 4104 */     return buf.toString();
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
/*      */   public static String join(Iterable<?> iterable, char separator) {
/* 4122 */     if (iterable == null) {
/* 4123 */       return null;
/*      */     }
/* 4125 */     return join(iterable.iterator(), separator);
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
/*      */   public static String join(Iterable<?> iterable, String separator) {
/* 4143 */     if (iterable == null) {
/* 4144 */       return null;
/*      */     }
/* 4146 */     return join(iterable.iterator(), separator);
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
/*      */   public static String deleteWhitespace(String str) {
/* 4166 */     if (isEmpty(str)) {
/* 4167 */       return str;
/*      */     }
/* 4169 */     int sz = str.length();
/* 4170 */     char[] chs = new char[sz];
/* 4171 */     int count = 0;
/* 4172 */     for (int i = 0; i < sz; i++) {
/* 4173 */       if (!Character.isWhitespace(str.charAt(i))) {
/* 4174 */         chs[count++] = str.charAt(i);
/*      */       }
/*      */     } 
/* 4177 */     if (count == sz) {
/* 4178 */       return str;
/*      */     }
/* 4180 */     return new String(chs, 0, count);
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
/*      */   public static String removeStart(String str, String remove) {
/* 4210 */     if (isEmpty(str) || isEmpty(remove)) {
/* 4211 */       return str;
/*      */     }
/* 4213 */     if (str.startsWith(remove)) {
/* 4214 */       return str.substring(remove.length());
/*      */     }
/* 4216 */     return str;
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
/*      */   public static String removeStartIgnoreCase(String str, String remove) {
/* 4245 */     if (isEmpty(str) || isEmpty(remove)) {
/* 4246 */       return str;
/*      */     }
/* 4248 */     if (startsWithIgnoreCase(str, remove)) {
/* 4249 */       return str.substring(remove.length());
/*      */     }
/* 4251 */     return str;
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
/*      */   public static String removeEnd(String str, String remove) {
/* 4279 */     if (isEmpty(str) || isEmpty(remove)) {
/* 4280 */       return str;
/*      */     }
/* 4282 */     if (str.endsWith(remove)) {
/* 4283 */       return str.substring(0, str.length() - remove.length());
/*      */     }
/* 4285 */     return str;
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
/*      */   public static String removeEndIgnoreCase(String str, String remove) {
/* 4315 */     if (isEmpty(str) || isEmpty(remove)) {
/* 4316 */       return str;
/*      */     }
/* 4318 */     if (endsWithIgnoreCase(str, remove)) {
/* 4319 */       return str.substring(0, str.length() - remove.length());
/*      */     }
/* 4321 */     return str;
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
/*      */   public static String remove(String str, String remove) {
/* 4348 */     if (isEmpty(str) || isEmpty(remove)) {
/* 4349 */       return str;
/*      */     }
/* 4351 */     return replace(str, remove, "", -1);
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
/*      */   public static String remove(String str, char remove) {
/* 4374 */     if (isEmpty(str) || str.indexOf(remove) == -1) {
/* 4375 */       return str;
/*      */     }
/* 4377 */     char[] chars = str.toCharArray();
/* 4378 */     int pos = 0;
/* 4379 */     for (int i = 0; i < chars.length; i++) {
/* 4380 */       if (chars[i] != remove) {
/* 4381 */         chars[pos++] = chars[i];
/*      */       }
/*      */     } 
/* 4384 */     return new String(chars, 0, pos);
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
/*      */   public static String replaceOnce(String text, String searchString, String replacement) {
/* 4413 */     return replace(text, searchString, replacement, 1);
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
/*      */   public static String replacePattern(String source, String regex, String replacement) {
/* 4437 */     return Pattern.compile(regex, 32).matcher(source).replaceAll(replacement);
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
/*      */   public static String removePattern(String source, String regex) {
/* 4453 */     return replacePattern(source, regex, "");
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
/*      */   public static String replace(String text, String searchString, String replacement) {
/* 4480 */     return replace(text, searchString, replacement, -1);
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
/*      */   public static String replace(String text, String searchString, String replacement, int max) {
/* 4512 */     if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
/* 4513 */       return text;
/*      */     }
/* 4515 */     int start = 0;
/* 4516 */     int end = text.indexOf(searchString, start);
/* 4517 */     if (end == -1) {
/* 4518 */       return text;
/*      */     }
/* 4520 */     int replLength = searchString.length();
/* 4521 */     int increase = replacement.length() - replLength;
/* 4522 */     increase = (increase < 0) ? 0 : increase;
/* 4523 */     increase *= (max < 0) ? 16 : ((max > 64) ? 64 : max);
/* 4524 */     StringBuilder buf = new StringBuilder(text.length() + increase);
/* 4525 */     while (end != -1) {
/* 4526 */       buf.append(text.substring(start, end)).append(replacement);
/* 4527 */       start = end + replLength;
/* 4528 */       if (--max == 0) {
/*      */         break;
/*      */       }
/* 4531 */       end = text.indexOf(searchString, start);
/*      */     } 
/* 4533 */     buf.append(text.substring(start));
/* 4534 */     return buf.toString();
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
/*      */   
/*      */   public static String replaceEach(String text, String[] searchList, String[] replacementList) {
/* 4577 */     return replaceEach(text, searchList, replacementList, false, 0);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String replaceEachRepeatedly(String text, String[] searchList, String[] replacementList) {
/* 4627 */     int timeToLive = (searchList == null) ? 0 : searchList.length;
/* 4628 */     return replaceEach(text, searchList, replacementList, true, timeToLive);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String replaceEach(String text, String[] searchList, String[] replacementList, boolean repeat, int timeToLive) {
/* 4685 */     if (text == null || text.isEmpty() || searchList == null || searchList.length == 0 || replacementList == null || replacementList.length == 0)
/*      */     {
/* 4687 */       return text;
/*      */     }
/*      */ 
/*      */     
/* 4691 */     if (timeToLive < 0) {
/* 4692 */       throw new IllegalStateException("Aborting to protect against StackOverflowError - output of one loop is the input of another");
/*      */     }
/*      */ 
/*      */     
/* 4696 */     int searchLength = searchList.length;
/* 4697 */     int replacementLength = replacementList.length;
/*      */ 
/*      */     
/* 4700 */     if (searchLength != replacementLength) {
/* 4701 */       throw new IllegalArgumentException("Search and Replace array lengths don't match: " + searchLength + " vs " + replacementLength);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4708 */     boolean[] noMoreMatchesForReplIndex = new boolean[searchLength];
/*      */ 
/*      */     
/* 4711 */     int textIndex = -1;
/* 4712 */     int replaceIndex = -1;
/* 4713 */     int tempIndex = -1;
/*      */ 
/*      */ 
/*      */     
/* 4717 */     for (int i = 0; i < searchLength; i++) {
/* 4718 */       if (!noMoreMatchesForReplIndex[i] && searchList[i] != null && !searchList[i].isEmpty() && replacementList[i] != null) {
/*      */ 
/*      */ 
/*      */         
/* 4722 */         tempIndex = text.indexOf(searchList[i]);
/*      */ 
/*      */         
/* 4725 */         if (tempIndex == -1) {
/* 4726 */           noMoreMatchesForReplIndex[i] = true;
/*      */         }
/* 4728 */         else if (textIndex == -1 || tempIndex < textIndex) {
/* 4729 */           textIndex = tempIndex;
/* 4730 */           replaceIndex = i;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4737 */     if (textIndex == -1) {
/* 4738 */       return text;
/*      */     }
/*      */     
/* 4741 */     int start = 0;
/*      */ 
/*      */     
/* 4744 */     int increase = 0;
/*      */ 
/*      */     
/* 4747 */     for (int j = 0; j < searchList.length; j++) {
/* 4748 */       if (searchList[j] != null && replacementList[j] != null) {
/*      */ 
/*      */         
/* 4751 */         int greater = replacementList[j].length() - searchList[j].length();
/* 4752 */         if (greater > 0) {
/* 4753 */           increase += 3 * greater;
/*      */         }
/*      */       } 
/*      */     } 
/* 4757 */     increase = Math.min(increase, text.length() / 5);
/*      */     
/* 4759 */     StringBuilder buf = new StringBuilder(text.length() + increase);
/*      */     
/* 4761 */     while (textIndex != -1) {
/*      */       int m;
/* 4763 */       for (m = start; m < textIndex; m++) {
/* 4764 */         buf.append(text.charAt(m));
/*      */       }
/* 4766 */       buf.append(replacementList[replaceIndex]);
/*      */       
/* 4768 */       start = textIndex + searchList[replaceIndex].length();
/*      */       
/* 4770 */       textIndex = -1;
/* 4771 */       replaceIndex = -1;
/* 4772 */       tempIndex = -1;
/*      */ 
/*      */       
/* 4775 */       for (m = 0; m < searchLength; m++) {
/* 4776 */         if (!noMoreMatchesForReplIndex[m] && searchList[m] != null && !searchList[m].isEmpty() && replacementList[m] != null) {
/*      */ 
/*      */ 
/*      */           
/* 4780 */           tempIndex = text.indexOf(searchList[m], start);
/*      */ 
/*      */           
/* 4783 */           if (tempIndex == -1) {
/* 4784 */             noMoreMatchesForReplIndex[m] = true;
/*      */           }
/* 4786 */           else if (textIndex == -1 || tempIndex < textIndex) {
/* 4787 */             textIndex = tempIndex;
/* 4788 */             replaceIndex = m;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4795 */     int textLength = text.length();
/* 4796 */     for (int k = start; k < textLength; k++) {
/* 4797 */       buf.append(text.charAt(k));
/*      */     }
/* 4799 */     String result = buf.toString();
/* 4800 */     if (!repeat) {
/* 4801 */       return result;
/*      */     }
/*      */     
/* 4804 */     return replaceEach(result, searchList, replacementList, repeat, timeToLive - 1);
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
/*      */   public static String replaceChars(String str, char searchChar, char replaceChar) {
/* 4830 */     if (str == null) {
/* 4831 */       return null;
/*      */     }
/* 4833 */     return str.replace(searchChar, replaceChar);
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
/*      */   public static String replaceChars(String str, String searchChars, String replaceChars) {
/* 4873 */     if (isEmpty(str) || isEmpty(searchChars)) {
/* 4874 */       return str;
/*      */     }
/* 4876 */     if (replaceChars == null) {
/* 4877 */       replaceChars = "";
/*      */     }
/* 4879 */     boolean modified = false;
/* 4880 */     int replaceCharsLength = replaceChars.length();
/* 4881 */     int strLength = str.length();
/* 4882 */     StringBuilder buf = new StringBuilder(strLength);
/* 4883 */     for (int i = 0; i < strLength; i++) {
/* 4884 */       char ch = str.charAt(i);
/* 4885 */       int index = searchChars.indexOf(ch);
/* 4886 */       if (index >= 0) {
/* 4887 */         modified = true;
/* 4888 */         if (index < replaceCharsLength) {
/* 4889 */           buf.append(replaceChars.charAt(index));
/*      */         }
/*      */       } else {
/* 4892 */         buf.append(ch);
/*      */       } 
/*      */     } 
/* 4895 */     if (modified) {
/* 4896 */       return buf.toString();
/*      */     }
/* 4898 */     return str;
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
/*      */   public static String overlay(String str, String overlay, int start, int end) {
/* 4933 */     if (str == null) {
/* 4934 */       return null;
/*      */     }
/* 4936 */     if (overlay == null) {
/* 4937 */       overlay = "";
/*      */     }
/* 4939 */     int len = str.length();
/* 4940 */     if (start < 0) {
/* 4941 */       start = 0;
/*      */     }
/* 4943 */     if (start > len) {
/* 4944 */       start = len;
/*      */     }
/* 4946 */     if (end < 0) {
/* 4947 */       end = 0;
/*      */     }
/* 4949 */     if (end > len) {
/* 4950 */       end = len;
/*      */     }
/* 4952 */     if (start > end) {
/* 4953 */       int temp = start;
/* 4954 */       start = end;
/* 4955 */       end = temp;
/*      */     } 
/* 4957 */     return (new StringBuilder(len + start - end + overlay.length() + 1)).append(str.substring(0, start)).append(overlay).append(str.substring(end)).toString();
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
/*      */   public static String chomp(String str) {
/* 4992 */     if (isEmpty(str)) {
/* 4993 */       return str;
/*      */     }
/*      */     
/* 4996 */     if (str.length() == 1) {
/* 4997 */       char ch = str.charAt(0);
/* 4998 */       if (ch == '\r' || ch == '\n') {
/* 4999 */         return "";
/*      */       }
/* 5001 */       return str;
/*      */     } 
/*      */     
/* 5004 */     int lastIdx = str.length() - 1;
/* 5005 */     char last = str.charAt(lastIdx);
/*      */     
/* 5007 */     if (last == '\n') {
/* 5008 */       if (str.charAt(lastIdx - 1) == '\r') {
/* 5009 */         lastIdx--;
/*      */       }
/* 5011 */     } else if (last != '\r') {
/* 5012 */       lastIdx++;
/*      */     } 
/* 5014 */     return str.substring(0, lastIdx);
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
/*      */   @Deprecated
/*      */   public static String chomp(String str, String separator) {
/* 5046 */     return removeEnd(str, separator);
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
/*      */   public static String chop(String str) {
/* 5075 */     if (str == null) {
/* 5076 */       return null;
/*      */     }
/* 5078 */     int strLen = str.length();
/* 5079 */     if (strLen < 2) {
/* 5080 */       return "";
/*      */     }
/* 5082 */     int lastIdx = strLen - 1;
/* 5083 */     String ret = str.substring(0, lastIdx);
/* 5084 */     char last = str.charAt(lastIdx);
/* 5085 */     if (last == '\n' && ret.charAt(lastIdx - 1) == '\r') {
/* 5086 */       return ret.substring(0, lastIdx - 1);
/*      */     }
/* 5088 */     return ret;
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
/*      */   public static String repeat(String str, int repeat) {
/*      */     char ch0, ch1, output2[];
/*      */     int i;
/* 5117 */     if (str == null) {
/* 5118 */       return null;
/*      */     }
/* 5120 */     if (repeat <= 0) {
/* 5121 */       return "";
/*      */     }
/* 5123 */     int inputLength = str.length();
/* 5124 */     if (repeat == 1 || inputLength == 0) {
/* 5125 */       return str;
/*      */     }
/* 5127 */     if (inputLength == 1 && repeat <= 8192) {
/* 5128 */       return repeat(str.charAt(0), repeat);
/*      */     }
/*      */     
/* 5131 */     int outputLength = inputLength * repeat;
/* 5132 */     switch (inputLength) {
/*      */       case 1:
/* 5134 */         return repeat(str.charAt(0), repeat);
/*      */       case 2:
/* 5136 */         ch0 = str.charAt(0);
/* 5137 */         ch1 = str.charAt(1);
/* 5138 */         output2 = new char[outputLength];
/* 5139 */         for (i = repeat * 2 - 2; i >= 0; i--, i--) {
/* 5140 */           output2[i] = ch0;
/* 5141 */           output2[i + 1] = ch1;
/*      */         } 
/* 5143 */         return new String(output2);
/*      */     } 
/* 5145 */     StringBuilder buf = new StringBuilder(outputLength);
/* 5146 */     for (int j = 0; j < repeat; j++) {
/* 5147 */       buf.append(str);
/*      */     }
/* 5149 */     return buf.toString();
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
/*      */   public static String repeat(String str, String separator, int repeat) {
/* 5174 */     if (str == null || separator == null) {
/* 5175 */       return repeat(str, repeat);
/*      */     }
/*      */     
/* 5178 */     String result = repeat(str + separator, repeat);
/* 5179 */     return removeEnd(result, separator);
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
/*      */   public static String repeat(char ch, int repeat) {
/* 5205 */     char[] buf = new char[repeat];
/* 5206 */     for (int i = repeat - 1; i >= 0; i--) {
/* 5207 */       buf[i] = ch;
/*      */     }
/* 5209 */     return new String(buf);
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
/*      */   public static String rightPad(String str, int size) {
/* 5232 */     return rightPad(str, size, ' ');
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
/*      */   public static String rightPad(String str, int size, char padChar) {
/* 5257 */     if (str == null) {
/* 5258 */       return null;
/*      */     }
/* 5260 */     int pads = size - str.length();
/* 5261 */     if (pads <= 0) {
/* 5262 */       return str;
/*      */     }
/* 5264 */     if (pads > 8192) {
/* 5265 */       return rightPad(str, size, String.valueOf(padChar));
/*      */     }
/* 5267 */     return str.concat(repeat(padChar, pads));
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
/*      */   public static String rightPad(String str, int size, String padStr) {
/* 5294 */     if (str == null) {
/* 5295 */       return null;
/*      */     }
/* 5297 */     if (isEmpty(padStr)) {
/* 5298 */       padStr = " ";
/*      */     }
/* 5300 */     int padLen = padStr.length();
/* 5301 */     int strLen = str.length();
/* 5302 */     int pads = size - strLen;
/* 5303 */     if (pads <= 0) {
/* 5304 */       return str;
/*      */     }
/* 5306 */     if (padLen == 1 && pads <= 8192) {
/* 5307 */       return rightPad(str, size, padStr.charAt(0));
/*      */     }
/*      */     
/* 5310 */     if (pads == padLen)
/* 5311 */       return str.concat(padStr); 
/* 5312 */     if (pads < padLen) {
/* 5313 */       return str.concat(padStr.substring(0, pads));
/*      */     }
/* 5315 */     char[] padding = new char[pads];
/* 5316 */     char[] padChars = padStr.toCharArray();
/* 5317 */     for (int i = 0; i < pads; i++) {
/* 5318 */       padding[i] = padChars[i % padLen];
/*      */     }
/* 5320 */     return str.concat(new String(padding));
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
/*      */   public static String leftPad(String str, int size) {
/* 5344 */     return leftPad(str, size, ' ');
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
/*      */   public static String leftPad(String str, int size, char padChar) {
/* 5369 */     if (str == null) {
/* 5370 */       return null;
/*      */     }
/* 5372 */     int pads = size - str.length();
/* 5373 */     if (pads <= 0) {
/* 5374 */       return str;
/*      */     }
/* 5376 */     if (pads > 8192) {
/* 5377 */       return leftPad(str, size, String.valueOf(padChar));
/*      */     }
/* 5379 */     return repeat(padChar, pads).concat(str);
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
/*      */   public static String leftPad(String str, int size, String padStr) {
/* 5406 */     if (str == null) {
/* 5407 */       return null;
/*      */     }
/* 5409 */     if (isEmpty(padStr)) {
/* 5410 */       padStr = " ";
/*      */     }
/* 5412 */     int padLen = padStr.length();
/* 5413 */     int strLen = str.length();
/* 5414 */     int pads = size - strLen;
/* 5415 */     if (pads <= 0) {
/* 5416 */       return str;
/*      */     }
/* 5418 */     if (padLen == 1 && pads <= 8192) {
/* 5419 */       return leftPad(str, size, padStr.charAt(0));
/*      */     }
/*      */     
/* 5422 */     if (pads == padLen)
/* 5423 */       return padStr.concat(str); 
/* 5424 */     if (pads < padLen) {
/* 5425 */       return padStr.substring(0, pads).concat(str);
/*      */     }
/* 5427 */     char[] padding = new char[pads];
/* 5428 */     char[] padChars = padStr.toCharArray();
/* 5429 */     for (int i = 0; i < pads; i++) {
/* 5430 */       padding[i] = padChars[i % padLen];
/*      */     }
/* 5432 */     return (new String(padding)).concat(str);
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
/*      */   public static int length(CharSequence cs) {
/* 5448 */     return (cs == null) ? 0 : cs.length();
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
/*      */   public static String center(String str, int size) {
/* 5477 */     return center(str, size, ' ');
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
/*      */   public static String center(String str, int size, char padChar) {
/* 5505 */     if (str == null || size <= 0) {
/* 5506 */       return str;
/*      */     }
/* 5508 */     int strLen = str.length();
/* 5509 */     int pads = size - strLen;
/* 5510 */     if (pads <= 0) {
/* 5511 */       return str;
/*      */     }
/* 5513 */     str = leftPad(str, strLen + pads / 2, padChar);
/* 5514 */     str = rightPad(str, size, padChar);
/* 5515 */     return str;
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
/*      */   public static String center(String str, int size, String padStr) {
/* 5545 */     if (str == null || size <= 0) {
/* 5546 */       return str;
/*      */     }
/* 5548 */     if (isEmpty(padStr)) {
/* 5549 */       padStr = " ";
/*      */     }
/* 5551 */     int strLen = str.length();
/* 5552 */     int pads = size - strLen;
/* 5553 */     if (pads <= 0) {
/* 5554 */       return str;
/*      */     }
/* 5556 */     str = leftPad(str, strLen + pads / 2, padStr);
/* 5557 */     str = rightPad(str, size, padStr);
/* 5558 */     return str;
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
/*      */   public static String upperCase(String str) {
/* 5583 */     if (str == null) {
/* 5584 */       return null;
/*      */     }
/* 5586 */     return str.toUpperCase();
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
/*      */   public static String upperCase(String str, Locale locale) {
/* 5606 */     if (str == null) {
/* 5607 */       return null;
/*      */     }
/* 5609 */     return str.toUpperCase(locale);
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
/*      */   public static String lowerCase(String str) {
/* 5632 */     if (str == null) {
/* 5633 */       return null;
/*      */     }
/* 5635 */     return str.toLowerCase();
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
/*      */   public static String lowerCase(String str, Locale locale) {
/* 5655 */     if (str == null) {
/* 5656 */       return null;
/*      */     }
/* 5658 */     return str.toLowerCase(locale);
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
/*      */   public static String capitalize(String str) {
/*      */     int strLen;
/* 5683 */     if (str == null || (strLen = str.length()) == 0) {
/* 5684 */       return str;
/*      */     }
/*      */     
/* 5687 */     char firstChar = str.charAt(0);
/* 5688 */     if (Character.isTitleCase(firstChar))
/*      */     {
/* 5690 */       return str;
/*      */     }
/*      */     
/* 5693 */     return (new StringBuilder(strLen)).append(Character.toTitleCase(firstChar)).append(str.substring(1)).toString();
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
/*      */   public static String uncapitalize(String str) {
/*      */     int strLen;
/* 5721 */     if (str == null || (strLen = str.length()) == 0) {
/* 5722 */       return str;
/*      */     }
/*      */     
/* 5725 */     char firstChar = str.charAt(0);
/* 5726 */     if (Character.isLowerCase(firstChar))
/*      */     {
/* 5728 */       return str;
/*      */     }
/*      */     
/* 5731 */     return (new StringBuilder(strLen)).append(Character.toLowerCase(firstChar)).append(str.substring(1)).toString();
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
/*      */   public static String swapCase(String str) {
/* 5765 */     if (isEmpty(str)) {
/* 5766 */       return str;
/*      */     }
/*      */     
/* 5769 */     char[] buffer = str.toCharArray();
/*      */     
/* 5771 */     for (int i = 0; i < buffer.length; i++) {
/* 5772 */       char ch = buffer[i];
/* 5773 */       if (Character.isUpperCase(ch)) {
/* 5774 */         buffer[i] = Character.toLowerCase(ch);
/* 5775 */       } else if (Character.isTitleCase(ch)) {
/* 5776 */         buffer[i] = Character.toLowerCase(ch);
/* 5777 */       } else if (Character.isLowerCase(ch)) {
/* 5778 */         buffer[i] = Character.toUpperCase(ch);
/*      */       } 
/*      */     } 
/* 5781 */     return new String(buffer);
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
/*      */   public static int countMatches(CharSequence str, CharSequence sub) {
/* 5807 */     if (isEmpty(str) || isEmpty(sub)) {
/* 5808 */       return 0;
/*      */     }
/* 5810 */     int count = 0;
/* 5811 */     int idx = 0;
/* 5812 */     while ((idx = CharSequenceUtils.indexOf(str, sub, idx)) != -1) {
/* 5813 */       count++;
/* 5814 */       idx += sub.length();
/*      */     } 
/* 5816 */     return count;
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
/*      */   public static boolean isAlpha(CharSequence cs) {
/* 5842 */     if (isEmpty(cs)) {
/* 5843 */       return false;
/*      */     }
/* 5845 */     int sz = cs.length();
/* 5846 */     for (int i = 0; i < sz; i++) {
/* 5847 */       if (!Character.isLetter(cs.charAt(i))) {
/* 5848 */         return false;
/*      */       }
/*      */     } 
/* 5851 */     return true;
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
/*      */   public static boolean isAlphaSpace(CharSequence cs) {
/* 5877 */     if (cs == null) {
/* 5878 */       return false;
/*      */     }
/* 5880 */     int sz = cs.length();
/* 5881 */     for (int i = 0; i < sz; i++) {
/* 5882 */       if (!Character.isLetter(cs.charAt(i)) && cs.charAt(i) != ' ') {
/* 5883 */         return false;
/*      */       }
/*      */     } 
/* 5886 */     return true;
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
/*      */   public static boolean isAlphanumeric(CharSequence cs) {
/* 5912 */     if (isEmpty(cs)) {
/* 5913 */       return false;
/*      */     }
/* 5915 */     int sz = cs.length();
/* 5916 */     for (int i = 0; i < sz; i++) {
/* 5917 */       if (!Character.isLetterOrDigit(cs.charAt(i))) {
/* 5918 */         return false;
/*      */       }
/*      */     } 
/* 5921 */     return true;
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
/*      */   public static boolean isAlphanumericSpace(CharSequence cs) {
/* 5947 */     if (cs == null) {
/* 5948 */       return false;
/*      */     }
/* 5950 */     int sz = cs.length();
/* 5951 */     for (int i = 0; i < sz; i++) {
/* 5952 */       if (!Character.isLetterOrDigit(cs.charAt(i)) && cs.charAt(i) != ' ') {
/* 5953 */         return false;
/*      */       }
/*      */     } 
/* 5956 */     return true;
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
/*      */   public static boolean isAsciiPrintable(CharSequence cs) {
/* 5986 */     if (cs == null) {
/* 5987 */       return false;
/*      */     }
/* 5989 */     int sz = cs.length();
/* 5990 */     for (int i = 0; i < sz; i++) {
/* 5991 */       if (!CharUtils.isAsciiPrintable(cs.charAt(i))) {
/* 5992 */         return false;
/*      */       }
/*      */     } 
/* 5995 */     return true;
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
/*      */   public static boolean isNumeric(CharSequence cs) {
/* 6029 */     if (isEmpty(cs)) {
/* 6030 */       return false;
/*      */     }
/* 6032 */     int sz = cs.length();
/* 6033 */     for (int i = 0; i < sz; i++) {
/* 6034 */       if (!Character.isDigit(cs.charAt(i))) {
/* 6035 */         return false;
/*      */       }
/*      */     } 
/* 6038 */     return true;
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
/*      */   public static boolean isNumericSpace(CharSequence cs) {
/* 6066 */     if (cs == null) {
/* 6067 */       return false;
/*      */     }
/* 6069 */     int sz = cs.length();
/* 6070 */     for (int i = 0; i < sz; i++) {
/* 6071 */       if (!Character.isDigit(cs.charAt(i)) && cs.charAt(i) != ' ') {
/* 6072 */         return false;
/*      */       }
/*      */     } 
/* 6075 */     return true;
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
/*      */   public static boolean isWhitespace(CharSequence cs) {
/* 6099 */     if (cs == null) {
/* 6100 */       return false;
/*      */     }
/* 6102 */     int sz = cs.length();
/* 6103 */     for (int i = 0; i < sz; i++) {
/* 6104 */       if (!Character.isWhitespace(cs.charAt(i))) {
/* 6105 */         return false;
/*      */       }
/*      */     } 
/* 6108 */     return true;
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
/*      */   public static boolean isAllLowerCase(CharSequence cs) {
/* 6131 */     if (cs == null || isEmpty(cs)) {
/* 6132 */       return false;
/*      */     }
/* 6134 */     int sz = cs.length();
/* 6135 */     for (int i = 0; i < sz; i++) {
/* 6136 */       if (!Character.isLowerCase(cs.charAt(i))) {
/* 6137 */         return false;
/*      */       }
/*      */     } 
/* 6140 */     return true;
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
/*      */   public static boolean isAllUpperCase(CharSequence cs) {
/* 6163 */     if (cs == null || isEmpty(cs)) {
/* 6164 */       return false;
/*      */     }
/* 6166 */     int sz = cs.length();
/* 6167 */     for (int i = 0; i < sz; i++) {
/* 6168 */       if (!Character.isUpperCase(cs.charAt(i))) {
/* 6169 */         return false;
/*      */       }
/*      */     } 
/* 6172 */     return true;
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
/*      */   public static String defaultString(String str) {
/* 6194 */     return (str == null) ? "" : str;
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
/*      */   public static String defaultString(String str, String defaultStr) {
/* 6215 */     return (str == null) ? defaultStr : str;
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
/*      */   public static <T extends CharSequence> T defaultIfBlank(T str, T defaultStr) {
/* 6237 */     return isBlank((CharSequence)str) ? defaultStr : str;
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
/*      */   public static <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
/* 6259 */     return isEmpty((CharSequence)str) ? defaultStr : str;
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
/*      */   public static String reverse(String str) {
/* 6279 */     if (str == null) {
/* 6280 */       return null;
/*      */     }
/* 6282 */     return (new StringBuilder(str)).reverse().toString();
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
/*      */   public static String reverseDelimited(String str, char separatorChar) {
/* 6305 */     if (str == null) {
/* 6306 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 6310 */     String[] strs = split(str, separatorChar);
/* 6311 */     ArrayUtils.reverse((Object[])strs);
/* 6312 */     return join((Object[])strs, separatorChar);
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
/*      */   public static String abbreviate(String str, int maxWidth) {
/* 6350 */     return abbreviate(str, 0, maxWidth);
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
/*      */   public static String abbreviate(String str, int offset, int maxWidth) {
/* 6389 */     if (str == null) {
/* 6390 */       return null;
/*      */     }
/* 6392 */     if (maxWidth < 4) {
/* 6393 */       throw new IllegalArgumentException("Minimum abbreviation width is 4");
/*      */     }
/* 6395 */     if (str.length() <= maxWidth) {
/* 6396 */       return str;
/*      */     }
/* 6398 */     if (offset > str.length()) {
/* 6399 */       offset = str.length();
/*      */     }
/* 6401 */     if (str.length() - offset < maxWidth - 3) {
/* 6402 */       offset = str.length() - maxWidth - 3;
/*      */     }
/* 6404 */     String abrevMarker = "...";
/* 6405 */     if (offset <= 4) {
/* 6406 */       return str.substring(0, maxWidth - 3) + "...";
/*      */     }
/* 6408 */     if (maxWidth < 7) {
/* 6409 */       throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
/*      */     }
/* 6411 */     if (offset + maxWidth - 3 < str.length()) {
/* 6412 */       return "..." + abbreviate(str.substring(offset), maxWidth - 3);
/*      */     }
/* 6414 */     return "..." + str.substring(str.length() - maxWidth - 3);
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
/*      */   public static String abbreviateMiddle(String str, String middle, int length) {
/* 6447 */     if (isEmpty(str) || isEmpty(middle)) {
/* 6448 */       return str;
/*      */     }
/*      */     
/* 6451 */     if (length >= str.length() || length < middle.length() + 2) {
/* 6452 */       return str;
/*      */     }
/*      */     
/* 6455 */     int targetSting = length - middle.length();
/* 6456 */     int startOffset = targetSting / 2 + targetSting % 2;
/* 6457 */     int endOffset = str.length() - targetSting / 2;
/*      */     
/* 6459 */     StringBuilder builder = new StringBuilder(length);
/* 6460 */     builder.append(str.substring(0, startOffset));
/* 6461 */     builder.append(middle);
/* 6462 */     builder.append(str.substring(endOffset));
/*      */     
/* 6464 */     return builder.toString();
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
/*      */   public static String difference(String str1, String str2) {
/* 6498 */     if (str1 == null) {
/* 6499 */       return str2;
/*      */     }
/* 6501 */     if (str2 == null) {
/* 6502 */       return str1;
/*      */     }
/* 6504 */     int at = indexOfDifference(str1, str2);
/* 6505 */     if (at == -1) {
/* 6506 */       return "";
/*      */     }
/* 6508 */     return str2.substring(at);
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
/*      */   public static int indexOfDifference(CharSequence cs1, CharSequence cs2) {
/* 6537 */     if (cs1 == cs2) {
/* 6538 */       return -1;
/*      */     }
/* 6540 */     if (cs1 == null || cs2 == null) {
/* 6541 */       return 0;
/*      */     }
/*      */     int i;
/* 6544 */     for (i = 0; i < cs1.length() && i < cs2.length() && 
/* 6545 */       cs1.charAt(i) == cs2.charAt(i); i++);
/*      */ 
/*      */ 
/*      */     
/* 6549 */     if (i < cs2.length() || i < cs1.length()) {
/* 6550 */       return i;
/*      */     }
/* 6552 */     return -1;
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
/*      */   public static int indexOfDifference(CharSequence... css) {
/* 6588 */     if (css == null || css.length <= 1) {
/* 6589 */       return -1;
/*      */     }
/* 6591 */     boolean anyStringNull = false;
/* 6592 */     boolean allStringsNull = true;
/* 6593 */     int arrayLen = css.length;
/* 6594 */     int shortestStrLen = Integer.MAX_VALUE;
/* 6595 */     int longestStrLen = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6600 */     for (int i = 0; i < arrayLen; i++) {
/* 6601 */       if (css[i] == null) {
/* 6602 */         anyStringNull = true;
/* 6603 */         shortestStrLen = 0;
/*      */       } else {
/* 6605 */         allStringsNull = false;
/* 6606 */         shortestStrLen = Math.min(css[i].length(), shortestStrLen);
/* 6607 */         longestStrLen = Math.max(css[i].length(), longestStrLen);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 6612 */     if (allStringsNull || (longestStrLen == 0 && !anyStringNull)) {
/* 6613 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 6617 */     if (shortestStrLen == 0) {
/* 6618 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 6622 */     int firstDiff = -1;
/* 6623 */     for (int stringPos = 0; stringPos < shortestStrLen; stringPos++) {
/* 6624 */       char comparisonChar = css[0].charAt(stringPos);
/* 6625 */       for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++) {
/* 6626 */         if (css[arrayPos].charAt(stringPos) != comparisonChar) {
/* 6627 */           firstDiff = stringPos;
/*      */           break;
/*      */         } 
/*      */       } 
/* 6631 */       if (firstDiff != -1) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 6636 */     if (firstDiff == -1 && shortestStrLen != longestStrLen)
/*      */     {
/*      */ 
/*      */       
/* 6640 */       return shortestStrLen;
/*      */     }
/* 6642 */     return firstDiff;
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
/*      */   public static String getCommonPrefix(String... strs) {
/* 6679 */     if (strs == null || strs.length == 0) {
/* 6680 */       return "";
/*      */     }
/* 6682 */     int smallestIndexOfDiff = indexOfDifference((CharSequence[])strs);
/* 6683 */     if (smallestIndexOfDiff == -1) {
/*      */       
/* 6685 */       if (strs[0] == null) {
/* 6686 */         return "";
/*      */       }
/* 6688 */       return strs[0];
/* 6689 */     }  if (smallestIndexOfDiff == 0)
/*      */     {
/* 6691 */       return "";
/*      */     }
/*      */     
/* 6694 */     return strs[0].substring(0, smallestIndexOfDiff);
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
/*      */   
/*      */   public static int getLevenshteinDistance(CharSequence s, CharSequence t) {
/* 6737 */     if (s == null || t == null) {
/* 6738 */       throw new IllegalArgumentException("Strings must not be null");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6758 */     int n = s.length();
/* 6759 */     int m = t.length();
/*      */     
/* 6761 */     if (n == 0)
/* 6762 */       return m; 
/* 6763 */     if (m == 0) {
/* 6764 */       return n;
/*      */     }
/*      */     
/* 6767 */     if (n > m) {
/*      */       
/* 6769 */       CharSequence tmp = s;
/* 6770 */       s = t;
/* 6771 */       t = tmp;
/* 6772 */       n = m;
/* 6773 */       m = t.length();
/*      */     } 
/*      */     
/* 6776 */     int[] p = new int[n + 1];
/* 6777 */     int[] d = new int[n + 1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int i;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6788 */     for (i = 0; i <= n; i++) {
/* 6789 */       p[i] = i;
/*      */     }
/*      */     
/* 6792 */     for (int j = 1; j <= m; j++) {
/* 6793 */       char t_j = t.charAt(j - 1);
/* 6794 */       d[0] = j;
/*      */       
/* 6796 */       for (i = 1; i <= n; i++) {
/* 6797 */         int cost = (s.charAt(i - 1) == t_j) ? 0 : 1;
/*      */         
/* 6799 */         d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
/*      */       } 
/*      */ 
/*      */       
/* 6803 */       int[] _d = p;
/* 6804 */       p = d;
/* 6805 */       d = _d;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6810 */     return p[n];
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
/*      */   public static int getLevenshteinDistance(CharSequence s, CharSequence t, int threshold) {
/* 6846 */     if (s == null || t == null) {
/* 6847 */       throw new IllegalArgumentException("Strings must not be null");
/*      */     }
/* 6849 */     if (threshold < 0) {
/* 6850 */       throw new IllegalArgumentException("Threshold must not be negative");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6897 */     int n = s.length();
/* 6898 */     int m = t.length();
/*      */ 
/*      */     
/* 6901 */     if (n == 0)
/* 6902 */       return (m <= threshold) ? m : -1; 
/* 6903 */     if (m == 0) {
/* 6904 */       return (n <= threshold) ? n : -1;
/*      */     }
/*      */     
/* 6907 */     if (n > m) {
/*      */       
/* 6909 */       CharSequence tmp = s;
/* 6910 */       s = t;
/* 6911 */       t = tmp;
/* 6912 */       n = m;
/* 6913 */       m = t.length();
/*      */     } 
/*      */     
/* 6916 */     int[] p = new int[n + 1];
/* 6917 */     int[] d = new int[n + 1];
/*      */ 
/*      */ 
/*      */     
/* 6921 */     int boundary = Math.min(n, threshold) + 1;
/* 6922 */     for (int i = 0; i < boundary; i++) {
/* 6923 */       p[i] = i;
/*      */     }
/*      */ 
/*      */     
/* 6927 */     Arrays.fill(p, boundary, p.length, 2147483647);
/* 6928 */     Arrays.fill(d, 2147483647);
/*      */ 
/*      */     
/* 6931 */     for (int j = 1; j <= m; j++) {
/* 6932 */       char t_j = t.charAt(j - 1);
/* 6933 */       d[0] = j;
/*      */ 
/*      */       
/* 6936 */       int min = Math.max(1, j - threshold);
/* 6937 */       int max = Math.min(n, j + threshold);
/*      */ 
/*      */       
/* 6940 */       if (min > max) {
/* 6941 */         return -1;
/*      */       }
/*      */ 
/*      */       
/* 6945 */       if (min > 1) {
/* 6946 */         d[min - 1] = Integer.MAX_VALUE;
/*      */       }
/*      */ 
/*      */       
/* 6950 */       for (int k = min; k <= max; k++) {
/* 6951 */         if (s.charAt(k - 1) == t_j) {
/*      */           
/* 6953 */           d[k] = p[k - 1];
/*      */         } else {
/*      */           
/* 6956 */           d[k] = 1 + Math.min(Math.min(d[k - 1], p[k]), p[k - 1]);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 6961 */       int[] _d = p;
/* 6962 */       p = d;
/* 6963 */       d = _d;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6968 */     if (p[n] <= threshold) {
/* 6969 */       return p[n];
/*      */     }
/* 6971 */     return -1;
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
/*      */   public static boolean startsWith(CharSequence str, CharSequence prefix) {
/* 7000 */     return startsWith(str, prefix, false);
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
/*      */   public static boolean startsWithIgnoreCase(CharSequence str, CharSequence prefix) {
/* 7026 */     return startsWith(str, prefix, true);
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
/*      */   private static boolean startsWith(CharSequence str, CharSequence prefix, boolean ignoreCase) {
/* 7041 */     if (str == null || prefix == null) {
/* 7042 */       return (str == null && prefix == null);
/*      */     }
/* 7044 */     if (prefix.length() > str.length()) {
/* 7045 */       return false;
/*      */     }
/* 7047 */     return CharSequenceUtils.regionMatches(str, ignoreCase, 0, prefix, 0, prefix.length());
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
/*      */   public static boolean startsWithAny(CharSequence string, CharSequence... searchStrings) {
/* 7070 */     if (isEmpty(string) || ArrayUtils.isEmpty((Object[])searchStrings)) {
/* 7071 */       return false;
/*      */     }
/* 7073 */     for (CharSequence searchString : searchStrings) {
/* 7074 */       if (startsWith(string, searchString)) {
/* 7075 */         return true;
/*      */       }
/*      */     } 
/* 7078 */     return false;
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
/*      */   public static boolean endsWith(CharSequence str, CharSequence suffix) {
/* 7108 */     return endsWith(str, suffix, false);
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
/*      */   public static boolean endsWithIgnoreCase(CharSequence str, CharSequence suffix) {
/* 7135 */     return endsWith(str, suffix, true);
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
/*      */   private static boolean endsWith(CharSequence str, CharSequence suffix, boolean ignoreCase) {
/* 7150 */     if (str == null || suffix == null) {
/* 7151 */       return (str == null && suffix == null);
/*      */     }
/* 7153 */     if (suffix.length() > str.length()) {
/* 7154 */       return false;
/*      */     }
/* 7156 */     int strOffset = str.length() - suffix.length();
/* 7157 */     return CharSequenceUtils.regionMatches(str, ignoreCase, strOffset, suffix, 0, suffix.length());
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static String normalizeSpace(String str) {
/* 7202 */     if (str == null) {
/* 7203 */       return null;
/*      */     }
/* 7205 */     return WHITESPACE_PATTERN.matcher(trim(str)).replaceAll(" ");
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
/*      */   public static boolean endsWithAny(CharSequence string, CharSequence... searchStrings) {
/* 7227 */     if (isEmpty(string) || ArrayUtils.isEmpty((Object[])searchStrings)) {
/* 7228 */       return false;
/*      */     }
/* 7230 */     for (CharSequence searchString : searchStrings) {
/* 7231 */       if (endsWith(string, searchString)) {
/* 7232 */         return true;
/*      */       }
/*      */     } 
/* 7235 */     return false;
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
/*      */   private static String appendIfMissing(String str, CharSequence suffix, boolean ignoreCase, CharSequence... suffixes) {
/* 7250 */     if (str == null || isEmpty(suffix) || endsWith(str, suffix, ignoreCase)) {
/* 7251 */       return str;
/*      */     }
/* 7253 */     if (suffixes != null && suffixes.length > 0) {
/* 7254 */       for (CharSequence s : suffixes) {
/* 7255 */         if (endsWith(str, s, ignoreCase)) {
/* 7256 */           return str;
/*      */         }
/*      */       } 
/*      */     }
/* 7260 */     return str + suffix.toString();
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
/*      */   public static String appendIfMissing(String str, CharSequence suffix, CharSequence... suffixes) {
/* 7298 */     return appendIfMissing(str, suffix, false, suffixes);
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
/*      */   public static String appendIfMissingIgnoreCase(String str, CharSequence suffix, CharSequence... suffixes) {
/* 7336 */     return appendIfMissing(str, suffix, true, suffixes);
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
/*      */   private static String prependIfMissing(String str, CharSequence prefix, boolean ignoreCase, CharSequence... prefixes) {
/* 7351 */     if (str == null || isEmpty(prefix) || startsWith(str, prefix, ignoreCase)) {
/* 7352 */       return str;
/*      */     }
/* 7354 */     if (prefixes != null && prefixes.length > 0) {
/* 7355 */       for (CharSequence p : prefixes) {
/* 7356 */         if (startsWith(str, p, ignoreCase)) {
/* 7357 */           return str;
/*      */         }
/*      */       } 
/*      */     }
/* 7361 */     return prefix.toString() + str;
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
/*      */   public static String prependIfMissing(String str, CharSequence prefix, CharSequence... prefixes) {
/* 7399 */     return prependIfMissing(str, prefix, false, prefixes);
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
/*      */   public static String prependIfMissingIgnoreCase(String str, CharSequence prefix, CharSequence... prefixes) {
/* 7437 */     return prependIfMissing(str, prefix, true, prefixes);
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
/*      */   @Deprecated
/*      */   public static String toString(byte[] bytes, String charsetName) throws UnsupportedEncodingException {
/* 7457 */     return (charsetName != null) ? new String(bytes, charsetName) : new String(bytes, Charset.defaultCharset());
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
/*      */   public static String toEncodedString(byte[] bytes, Charset charset) throws UnsupportedEncodingException {
/* 7475 */     return new String(bytes, (charset != null) ? charset : Charset.defaultCharset());
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\StringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */