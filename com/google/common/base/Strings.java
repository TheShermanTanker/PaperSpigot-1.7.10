/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class Strings
/*     */ {
/*     */   public static String nullToEmpty(@Nullable String string) {
/*  46 */     return (string == null) ? "" : string;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static String emptyToNull(@Nullable String string) {
/*  57 */     return isNullOrEmpty(string) ? null : string;
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
/*     */   public static boolean isNullOrEmpty(@Nullable String string) {
/*  74 */     return (string == null || string.length() == 0);
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
/*     */   public static String padStart(String string, int minLength, char padChar) {
/*  97 */     Preconditions.checkNotNull(string);
/*  98 */     if (string.length() >= minLength) {
/*  99 */       return string;
/*     */     }
/* 101 */     StringBuilder sb = new StringBuilder(minLength);
/* 102 */     for (int i = string.length(); i < minLength; i++) {
/* 103 */       sb.append(padChar);
/*     */     }
/* 105 */     sb.append(string);
/* 106 */     return sb.toString();
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
/*     */   public static String padEnd(String string, int minLength, char padChar) {
/* 129 */     Preconditions.checkNotNull(string);
/* 130 */     if (string.length() >= minLength) {
/* 131 */       return string;
/*     */     }
/* 133 */     StringBuilder sb = new StringBuilder(minLength);
/* 134 */     sb.append(string);
/* 135 */     for (int i = string.length(); i < minLength; i++) {
/* 136 */       sb.append(padChar);
/*     */     }
/* 138 */     return sb.toString();
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
/*     */   public static String repeat(String string, int count) {
/* 153 */     Preconditions.checkNotNull(string);
/* 154 */     Preconditions.checkArgument((count >= 0), "invalid count: %s", new Object[] { Integer.valueOf(count) });
/*     */ 
/*     */ 
/*     */     
/* 158 */     StringBuilder builder = new StringBuilder(string.length() * count);
/* 159 */     for (int i = 0; i < count; i++) {
/* 160 */       builder.append(string);
/*     */     }
/* 162 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\Strings.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */