/*     */ package com.google.common.primitives;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class SignedBytes
/*     */ {
/*     */   public static final byte MAX_POWER_OF_TWO = 64;
/*     */   
/*     */   public static byte checkedCast(long value) {
/*  57 */     byte result = (byte)(int)value;
/*  58 */     Preconditions.checkArgument((result == value), "Out of range: %s", new Object[] { Long.valueOf(value) });
/*  59 */     return result;
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
/*     */   public static byte saturatedCast(long value) {
/*  71 */     if (value > 127L) {
/*  72 */       return Byte.MAX_VALUE;
/*     */     }
/*  74 */     if (value < -128L) {
/*  75 */       return Byte.MIN_VALUE;
/*     */     }
/*  77 */     return (byte)(int)value;
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
/*     */   public static int compare(byte a, byte b) {
/*  90 */     return a - b;
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
/*     */   public static byte min(byte... array) {
/* 102 */     Preconditions.checkArgument((array.length > 0));
/* 103 */     byte min = array[0];
/* 104 */     for (int i = 1; i < array.length; i++) {
/* 105 */       if (array[i] < min) {
/* 106 */         min = array[i];
/*     */       }
/*     */     } 
/* 109 */     return min;
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
/*     */   public static byte max(byte... array) {
/* 121 */     Preconditions.checkArgument((array.length > 0));
/* 122 */     byte max = array[0];
/* 123 */     for (int i = 1; i < array.length; i++) {
/* 124 */       if (array[i] > max) {
/* 125 */         max = array[i];
/*     */       }
/*     */     } 
/* 128 */     return max;
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
/*     */   public static String join(String separator, byte... array) {
/* 141 */     Preconditions.checkNotNull(separator);
/* 142 */     if (array.length == 0) {
/* 143 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 147 */     StringBuilder builder = new StringBuilder(array.length * 5);
/* 148 */     builder.append(array[0]);
/* 149 */     for (int i = 1; i < array.length; i++) {
/* 150 */       builder.append(separator).append(array[i]);
/*     */     }
/* 152 */     return builder.toString();
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
/*     */   public static Comparator<byte[]> lexicographicalComparator() {
/* 172 */     return LexicographicalComparator.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum LexicographicalComparator implements Comparator<byte[]> {
/* 176 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int compare(byte[] left, byte[] right) {
/* 180 */       int minLength = Math.min(left.length, right.length);
/* 181 */       for (int i = 0; i < minLength; i++) {
/* 182 */         int result = SignedBytes.compare(left[i], right[i]);
/* 183 */         if (result != 0) {
/* 184 */           return result;
/*     */         }
/*     */       } 
/* 187 */       return left.length - right.length;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\primitives\SignedBytes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */