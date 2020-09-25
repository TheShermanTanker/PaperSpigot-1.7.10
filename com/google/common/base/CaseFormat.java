/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible
/*     */ public enum CaseFormat
/*     */ {
/*  32 */   LOWER_HYPHEN(CharMatcher.is('-'), "-"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   LOWER_UNDERSCORE(CharMatcher.is('_'), "_"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   LOWER_CAMEL(CharMatcher.inRange('A', 'Z'), ""),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   UPPER_CAMEL(CharMatcher.inRange('A', 'Z'), ""),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   UPPER_UNDERSCORE(CharMatcher.is('_'), "_");
/*     */   
/*     */   private final CharMatcher wordBoundary;
/*     */   private final String wordSeparator;
/*     */   
/*     */   CaseFormat(CharMatcher wordBoundary, String wordSeparator) {
/*  58 */     this.wordBoundary = wordBoundary;
/*  59 */     this.wordSeparator = wordSeparator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String to(CaseFormat format, String s) {
/*  68 */     if (format == null) {
/*  69 */       throw new NullPointerException();
/*     */     }
/*  71 */     if (s == null) {
/*  72 */       throw new NullPointerException();
/*     */     }
/*     */     
/*  75 */     if (format == this) {
/*  76 */       return s;
/*     */     }
/*     */ 
/*     */     
/*  80 */     switch (this) {
/*     */       case LOWER_HYPHEN:
/*  82 */         switch (format) {
/*     */           case LOWER_UNDERSCORE:
/*  84 */             return s.replace('-', '_');
/*     */           case UPPER_UNDERSCORE:
/*  86 */             return Ascii.toUpperCase(s.replace('-', '_'));
/*     */         } 
/*     */         break;
/*     */       case LOWER_UNDERSCORE:
/*  90 */         switch (format) {
/*     */           case LOWER_HYPHEN:
/*  92 */             return s.replace('_', '-');
/*     */           case UPPER_UNDERSCORE:
/*  94 */             return Ascii.toUpperCase(s);
/*     */         } 
/*     */         break;
/*     */       case UPPER_UNDERSCORE:
/*  98 */         switch (format) {
/*     */           case LOWER_HYPHEN:
/* 100 */             return Ascii.toLowerCase(s.replace('_', '-'));
/*     */           case LOWER_UNDERSCORE:
/* 102 */             return Ascii.toLowerCase(s);
/*     */         } 
/*     */         
/*     */         break;
/*     */     } 
/*     */     
/* 108 */     StringBuilder out = null;
/* 109 */     int i = 0;
/* 110 */     int j = -1;
/* 111 */     while ((j = this.wordBoundary.indexIn(s, ++j)) != -1) {
/* 112 */       if (i == 0) {
/*     */         
/* 114 */         out = new StringBuilder(s.length() + 4 * this.wordSeparator.length());
/* 115 */         out.append(format.normalizeFirstWord(s.substring(i, j)));
/*     */       } else {
/* 117 */         out.append(format.normalizeWord(s.substring(i, j)));
/*     */       } 
/* 119 */       out.append(format.wordSeparator);
/* 120 */       i = j + this.wordSeparator.length();
/*     */     } 
/* 122 */     if (i == 0) {
/* 123 */       return format.normalizeFirstWord(s);
/*     */     }
/* 125 */     out.append(format.normalizeWord(s.substring(i)));
/* 126 */     return out.toString();
/*     */   }
/*     */   
/*     */   private String normalizeFirstWord(String word) {
/* 130 */     switch (this) {
/*     */       case LOWER_CAMEL:
/* 132 */         return Ascii.toLowerCase(word);
/*     */     } 
/* 134 */     return normalizeWord(word);
/*     */   }
/*     */ 
/*     */   
/*     */   private String normalizeWord(String word) {
/* 139 */     switch (this) {
/*     */       case LOWER_HYPHEN:
/* 141 */         return Ascii.toLowerCase(word);
/*     */       case LOWER_UNDERSCORE:
/* 143 */         return Ascii.toLowerCase(word);
/*     */       case LOWER_CAMEL:
/* 145 */         return firstCharOnlyToUpper(word);
/*     */       case UPPER_CAMEL:
/* 147 */         return firstCharOnlyToUpper(word);
/*     */       case UPPER_UNDERSCORE:
/* 149 */         return Ascii.toUpperCase(word);
/*     */     } 
/* 151 */     throw new RuntimeException("unknown case: " + this);
/*     */   }
/*     */   
/*     */   private static String firstCharOnlyToUpper(String word) {
/* 155 */     int length = word.length();
/* 156 */     if (length == 0) {
/* 157 */       return word;
/*     */     }
/* 159 */     return (new StringBuilder(length)).append(Ascii.toUpperCase(word.charAt(0))).append(Ascii.toLowerCase(word.substring(1))).toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\CaseFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */