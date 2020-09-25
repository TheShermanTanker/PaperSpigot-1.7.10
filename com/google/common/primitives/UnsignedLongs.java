/*     */ package com.google.common.primitives;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.math.BigInteger;
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
/*     */ @Beta
/*     */ @GwtCompatible
/*     */ public final class UnsignedLongs
/*     */ {
/*     */   public static final long MAX_VALUE = -1L;
/*     */   
/*     */   private static long flip(long a) {
/*  59 */     return a ^ Long.MIN_VALUE;
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
/*     */   public static int compare(long a, long b) {
/*  72 */     return Longs.compare(flip(a), flip(b));
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
/*     */   public static long min(long... array) {
/*  84 */     Preconditions.checkArgument((array.length > 0));
/*  85 */     long min = flip(array[0]);
/*  86 */     for (int i = 1; i < array.length; i++) {
/*  87 */       long next = flip(array[i]);
/*  88 */       if (next < min) {
/*  89 */         min = next;
/*     */       }
/*     */     } 
/*  92 */     return flip(min);
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
/*     */   public static long max(long... array) {
/* 104 */     Preconditions.checkArgument((array.length > 0));
/* 105 */     long max = flip(array[0]);
/* 106 */     for (int i = 1; i < array.length; i++) {
/* 107 */       long next = flip(array[i]);
/* 108 */       if (next > max) {
/* 109 */         max = next;
/*     */       }
/*     */     } 
/* 112 */     return flip(max);
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
/*     */   public static String join(String separator, long... array) {
/* 124 */     Preconditions.checkNotNull(separator);
/* 125 */     if (array.length == 0) {
/* 126 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 130 */     StringBuilder builder = new StringBuilder(array.length * 5);
/* 131 */     builder.append(array[0]);
/* 132 */     for (int i = 1; i < array.length; i++) {
/* 133 */       builder.append(separator).append(toString(array[i]));
/*     */     }
/* 135 */     return builder.toString();
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
/*     */   public static Comparator<long[]> lexicographicalComparator() {
/* 153 */     return LexicographicalComparator.INSTANCE;
/*     */   }
/*     */   
/*     */   enum LexicographicalComparator implements Comparator<long[]> {
/* 157 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int compare(long[] left, long[] right) {
/* 161 */       int minLength = Math.min(left.length, right.length);
/* 162 */       for (int i = 0; i < minLength; i++) {
/* 163 */         if (left[i] != right[i]) {
/* 164 */           return UnsignedLongs.compare(left[i], right[i]);
/*     */         }
/*     */       } 
/* 167 */       return left.length - right.length;
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
/*     */   
/*     */   public static long divide(long dividend, long divisor) {
/* 180 */     if (divisor < 0L) {
/* 181 */       if (compare(dividend, divisor) < 0) {
/* 182 */         return 0L;
/*     */       }
/* 184 */       return 1L;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 189 */     if (dividend >= 0L) {
/* 190 */       return dividend / divisor;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     long quotient = (dividend >>> 1L) / divisor << 1L;
/* 200 */     long rem = dividend - quotient * divisor;
/* 201 */     return quotient + ((compare(rem, divisor) >= 0) ? 1L : 0L);
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
/*     */   public static long remainder(long dividend, long divisor) {
/* 213 */     if (divisor < 0L) {
/* 214 */       if (compare(dividend, divisor) < 0) {
/* 215 */         return dividend;
/*     */       }
/* 217 */       return dividend - divisor;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 222 */     if (dividend >= 0L) {
/* 223 */       return dividend % divisor;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     long quotient = (dividend >>> 1L) / divisor << 1L;
/* 233 */     long rem = dividend - quotient * divisor;
/* 234 */     return rem - ((compare(rem, divisor) >= 0) ? divisor : 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long parseUnsignedLong(String s) {
/* 244 */     return parseUnsignedLong(s, 10);
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
/*     */   public static long parseUnsignedLong(String s, int radix) {
/* 257 */     Preconditions.checkNotNull(s);
/* 258 */     if (s.length() == 0) {
/* 259 */       throw new NumberFormatException("empty string");
/*     */     }
/* 261 */     if (radix < 2 || radix > 36) {
/* 262 */       throw new NumberFormatException("illegal radix:" + radix);
/*     */     }
/*     */     
/* 265 */     int max_safe_pos = maxSafeDigits[radix] - 1;
/* 266 */     long value = 0L;
/* 267 */     for (int pos = 0; pos < s.length(); pos++) {
/* 268 */       int digit = Character.digit(s.charAt(pos), radix);
/* 269 */       if (digit == -1) {
/* 270 */         throw new NumberFormatException(s);
/*     */       }
/* 272 */       if (pos > max_safe_pos && overflowInParse(value, digit, radix)) {
/* 273 */         throw new NumberFormatException("Too large for unsigned long: " + s);
/*     */       }
/* 275 */       value = value * radix + digit;
/*     */     } 
/*     */     
/* 278 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean overflowInParse(long current, int digit, int radix) {
/* 288 */     if (current >= 0L) {
/* 289 */       if (current < maxValueDivs[radix]) {
/* 290 */         return false;
/*     */       }
/* 292 */       if (current > maxValueDivs[radix]) {
/* 293 */         return true;
/*     */       }
/*     */       
/* 296 */       return (digit > maxValueMods[radix]);
/*     */     } 
/*     */ 
/*     */     
/* 300 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(long x) {
/* 307 */     return toString(x, 10);
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
/*     */   public static String toString(long x, int radix) {
/* 320 */     Preconditions.checkArgument((radix >= 2 && radix <= 36), "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", new Object[] { Integer.valueOf(radix) });
/*     */     
/* 322 */     if (x == 0L)
/*     */     {
/* 324 */       return "0";
/*     */     }
/* 326 */     char[] buf = new char[64];
/* 327 */     int i = buf.length;
/* 328 */     if (x < 0L) {
/*     */ 
/*     */ 
/*     */       
/* 332 */       long top = x >>> 32L;
/* 333 */       long bot = (x & 0xFFFFFFFFL) + (top % radix << 32L);
/* 334 */       top /= radix;
/* 335 */       while (bot > 0L || top > 0L) {
/* 336 */         buf[--i] = Character.forDigit((int)(bot % radix), radix);
/* 337 */         bot = bot / radix + (top % radix << 32L);
/* 338 */         top /= radix;
/*     */       } 
/*     */     } else {
/*     */       
/* 342 */       while (x > 0L) {
/* 343 */         buf[--i] = Character.forDigit((int)(x % radix), radix);
/* 344 */         x /= radix;
/*     */       } 
/*     */     } 
/*     */     
/* 348 */     return new String(buf, i, buf.length - i);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 353 */   private static final long[] maxValueDivs = new long[37];
/* 354 */   private static final int[] maxValueMods = new int[37];
/* 355 */   private static final int[] maxSafeDigits = new int[37];
/*     */   static {
/* 357 */     BigInteger overflow = new BigInteger("10000000000000000", 16);
/* 358 */     for (int i = 2; i <= 36; i++) {
/* 359 */       maxValueDivs[i] = divide(-1L, i);
/* 360 */       maxValueMods[i] = (int)remainder(-1L, i);
/* 361 */       maxSafeDigits[i] = overflow.toString(i).length() - 1;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\primitives\UnsignedLongs.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */