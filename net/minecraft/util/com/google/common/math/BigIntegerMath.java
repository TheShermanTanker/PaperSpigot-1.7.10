/*     */ package net.minecraft.util.com.google.common.math;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtIncompatible;
/*     */ import net.minecraft.util.com.google.common.annotations.VisibleForTesting;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class BigIntegerMath
/*     */ {
/*     */   @VisibleForTesting
/*     */   static final int SQRT2_PRECOMPUTE_THRESHOLD = 256;
/*     */   
/*     */   public static boolean isPowerOfTwo(BigInteger x) {
/*  56 */     Preconditions.checkNotNull(x);
/*  57 */     return (x.signum() > 0 && x.getLowestSetBit() == x.bitLength() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int log2(BigInteger x, RoundingMode mode) {
/*     */     BigInteger x2;
/*     */     int logX2Floor;
/*  70 */     MathPreconditions.checkPositive("x", (BigInteger)Preconditions.checkNotNull(x));
/*  71 */     int logFloor = x.bitLength() - 1;
/*  72 */     switch (mode) {
/*     */       case UNNECESSARY:
/*  74 */         MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(x));
/*     */       case DOWN:
/*     */       case FLOOR:
/*  77 */         return logFloor;
/*     */       
/*     */       case UP:
/*     */       case CEILING:
/*  81 */         return isPowerOfTwo(x) ? logFloor : (logFloor + 1);
/*     */       
/*     */       case HALF_DOWN:
/*     */       case HALF_UP:
/*     */       case HALF_EVEN:
/*  86 */         if (logFloor < 256) {
/*  87 */           BigInteger halfPower = SQRT2_PRECOMPUTED_BITS.shiftRight(256 - logFloor);
/*     */           
/*  89 */           if (x.compareTo(halfPower) <= 0) {
/*  90 */             return logFloor;
/*     */           }
/*  92 */           return logFloor + 1;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 101 */         x2 = x.pow(2);
/* 102 */         logX2Floor = x2.bitLength() - 1;
/* 103 */         return (logX2Floor < 2 * logFloor + 1) ? logFloor : (logFloor + 1);
/*     */     } 
/*     */     
/* 106 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/* 117 */   static final BigInteger SQRT2_PRECOMPUTED_BITS = new BigInteger("16a09e667f3bcc908b2fb1366ea957d3e3adec17512775099da2f590b0667322a", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("TODO")
/*     */   public static int log10(BigInteger x, RoundingMode mode) {
/*     */     BigInteger x2, halfPowerSquared;
/* 130 */     MathPreconditions.checkPositive("x", x);
/* 131 */     if (fitsInLong(x)) {
/* 132 */       return LongMath.log10(x.longValue(), mode);
/*     */     }
/*     */     
/* 135 */     int approxLog10 = (int)(log2(x, RoundingMode.FLOOR) * LN_2 / LN_10);
/* 136 */     BigInteger approxPow = BigInteger.TEN.pow(approxLog10);
/* 137 */     int approxCmp = approxPow.compareTo(x);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     if (approxCmp > 0) {
/*     */ 
/*     */       
/*     */       do {
/*     */ 
/*     */ 
/*     */         
/* 151 */         approxLog10--;
/* 152 */         approxPow = approxPow.divide(BigInteger.TEN);
/* 153 */         approxCmp = approxPow.compareTo(x);
/* 154 */       } while (approxCmp > 0);
/*     */     } else {
/* 156 */       BigInteger nextPow = BigInteger.TEN.multiply(approxPow);
/* 157 */       int nextCmp = nextPow.compareTo(x);
/* 158 */       while (nextCmp <= 0) {
/* 159 */         approxLog10++;
/* 160 */         approxPow = nextPow;
/* 161 */         approxCmp = nextCmp;
/* 162 */         nextPow = BigInteger.TEN.multiply(approxPow);
/* 163 */         nextCmp = nextPow.compareTo(x);
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     int floorLog = approxLog10;
/* 168 */     BigInteger floorPow = approxPow;
/* 169 */     int floorCmp = approxCmp;
/*     */     
/* 171 */     switch (mode) {
/*     */       case UNNECESSARY:
/* 173 */         MathPreconditions.checkRoundingUnnecessary((floorCmp == 0));
/*     */       
/*     */       case DOWN:
/*     */       case FLOOR:
/* 177 */         return floorLog;
/*     */       
/*     */       case UP:
/*     */       case CEILING:
/* 181 */         return floorPow.equals(x) ? floorLog : (floorLog + 1);
/*     */ 
/*     */       
/*     */       case HALF_DOWN:
/*     */       case HALF_UP:
/*     */       case HALF_EVEN:
/* 187 */         x2 = x.pow(2);
/* 188 */         halfPowerSquared = floorPow.pow(2).multiply(BigInteger.TEN);
/* 189 */         return (x2.compareTo(halfPowerSquared) <= 0) ? floorLog : (floorLog + 1);
/*     */     } 
/* 191 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */   
/* 195 */   private static final double LN_10 = Math.log(10.0D);
/* 196 */   private static final double LN_2 = Math.log(2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("TODO")
/*     */   public static BigInteger sqrt(BigInteger x, RoundingMode mode) {
/*     */     BigInteger halfSquare;
/* 208 */     MathPreconditions.checkNonNegative("x", x);
/* 209 */     if (fitsInLong(x)) {
/* 210 */       return BigInteger.valueOf(LongMath.sqrt(x.longValue(), mode));
/*     */     }
/* 212 */     BigInteger sqrtFloor = sqrtFloor(x);
/* 213 */     switch (mode) {
/*     */       case UNNECESSARY:
/* 215 */         MathPreconditions.checkRoundingUnnecessary(sqrtFloor.pow(2).equals(x));
/*     */       case DOWN:
/*     */       case FLOOR:
/* 218 */         return sqrtFloor;
/*     */       case UP:
/*     */       case CEILING:
/* 221 */         return sqrtFloor.pow(2).equals(x) ? sqrtFloor : sqrtFloor.add(BigInteger.ONE);
/*     */       case HALF_DOWN:
/*     */       case HALF_UP:
/*     */       case HALF_EVEN:
/* 225 */         halfSquare = sqrtFloor.pow(2).add(sqrtFloor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 231 */         return (halfSquare.compareTo(x) >= 0) ? sqrtFloor : sqrtFloor.add(BigInteger.ONE);
/*     */     } 
/* 233 */     throw new AssertionError();
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
/*     */   @GwtIncompatible("TODO")
/*     */   private static BigInteger sqrtFloor(BigInteger x) {
/*     */     BigInteger sqrt0;
/* 259 */     int log2 = log2(x, RoundingMode.FLOOR);
/* 260 */     if (log2 < 1023) {
/* 261 */       sqrt0 = sqrtApproxWithDoubles(x);
/*     */     } else {
/* 263 */       int shift = log2 - 52 & 0xFFFFFFFE;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 268 */       sqrt0 = sqrtApproxWithDoubles(x.shiftRight(shift)).shiftLeft(shift >> 1);
/*     */     } 
/* 270 */     BigInteger sqrt1 = sqrt0.add(x.divide(sqrt0)).shiftRight(1);
/* 271 */     if (sqrt0.equals(sqrt1)) {
/* 272 */       return sqrt0;
/*     */     }
/*     */     while (true) {
/* 275 */       sqrt0 = sqrt1;
/* 276 */       sqrt1 = sqrt0.add(x.divide(sqrt0)).shiftRight(1);
/* 277 */       if (sqrt1.compareTo(sqrt0) >= 0)
/* 278 */         return sqrt0; 
/*     */     } 
/*     */   }
/*     */   @GwtIncompatible("TODO")
/*     */   private static BigInteger sqrtApproxWithDoubles(BigInteger x) {
/* 283 */     return DoubleMath.roundToBigInteger(Math.sqrt(DoubleUtils.bigToDouble(x)), RoundingMode.HALF_EVEN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("TODO")
/*     */   public static BigInteger divide(BigInteger p, BigInteger q, RoundingMode mode) {
/* 295 */     BigDecimal pDec = new BigDecimal(p);
/* 296 */     BigDecimal qDec = new BigDecimal(q);
/* 297 */     return pDec.divide(qDec, 0, mode).toBigIntegerExact();
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
/*     */   public static BigInteger factorial(int n) {
/* 313 */     MathPreconditions.checkNonNegative("n", n);
/*     */ 
/*     */     
/* 316 */     if (n < LongMath.factorials.length) {
/* 317 */       return BigInteger.valueOf(LongMath.factorials[n]);
/*     */     }
/*     */ 
/*     */     
/* 321 */     int approxSize = IntMath.divide(n * IntMath.log2(n, RoundingMode.CEILING), 64, RoundingMode.CEILING);
/* 322 */     ArrayList<BigInteger> bignums = new ArrayList<BigInteger>(approxSize);
/*     */ 
/*     */     
/* 325 */     int startingNumber = LongMath.factorials.length;
/* 326 */     long product = LongMath.factorials[startingNumber - 1];
/*     */     
/* 328 */     int shift = Long.numberOfTrailingZeros(product);
/* 329 */     product >>= shift;
/*     */ 
/*     */     
/* 332 */     int productBits = LongMath.log2(product, RoundingMode.FLOOR) + 1;
/* 333 */     int bits = LongMath.log2(startingNumber, RoundingMode.FLOOR) + 1;
/*     */     
/* 335 */     int nextPowerOfTwo = 1 << bits - 1;
/*     */     
/*     */     long num;
/* 338 */     for (num = startingNumber; num <= n; num++) {
/*     */       
/* 340 */       if ((num & nextPowerOfTwo) != 0L) {
/* 341 */         nextPowerOfTwo <<= 1;
/* 342 */         bits++;
/*     */       } 
/*     */       
/* 345 */       int tz = Long.numberOfTrailingZeros(num);
/* 346 */       long normalizedNum = num >> tz;
/* 347 */       shift += tz;
/*     */       
/* 349 */       int normalizedBits = bits - tz;
/*     */       
/* 351 */       if (normalizedBits + productBits >= 64) {
/* 352 */         bignums.add(BigInteger.valueOf(product));
/* 353 */         product = 1L;
/* 354 */         productBits = 0;
/*     */       } 
/* 356 */       product *= normalizedNum;
/* 357 */       productBits = LongMath.log2(product, RoundingMode.FLOOR) + 1;
/*     */     } 
/*     */     
/* 360 */     if (product > 1L) {
/* 361 */       bignums.add(BigInteger.valueOf(product));
/*     */     }
/*     */     
/* 364 */     return listProduct(bignums).shiftLeft(shift);
/*     */   }
/*     */   
/*     */   static BigInteger listProduct(List<BigInteger> nums) {
/* 368 */     return listProduct(nums, 0, nums.size());
/*     */   }
/*     */   
/*     */   static BigInteger listProduct(List<BigInteger> nums, int start, int end) {
/* 372 */     switch (end - start) {
/*     */       case 0:
/* 374 */         return BigInteger.ONE;
/*     */       case 1:
/* 376 */         return nums.get(start);
/*     */       case 2:
/* 378 */         return ((BigInteger)nums.get(start)).multiply(nums.get(start + 1));
/*     */       case 3:
/* 380 */         return ((BigInteger)nums.get(start)).multiply(nums.get(start + 1)).multiply(nums.get(start + 2));
/*     */     } 
/*     */     
/* 383 */     int m = end + start >>> 1;
/* 384 */     return listProduct(nums, start, m).multiply(listProduct(nums, m, end));
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
/*     */   public static BigInteger binomial(int n, int k) {
/* 397 */     MathPreconditions.checkNonNegative("n", n);
/* 398 */     MathPreconditions.checkNonNegative("k", k);
/* 399 */     Preconditions.checkArgument((k <= n), "k (%s) > n (%s)", new Object[] { Integer.valueOf(k), Integer.valueOf(n) });
/* 400 */     if (k > n >> 1) {
/* 401 */       k = n - k;
/*     */     }
/* 403 */     if (k < LongMath.biggestBinomials.length && n <= LongMath.biggestBinomials[k]) {
/* 404 */       return BigInteger.valueOf(LongMath.binomial(n, k));
/*     */     }
/*     */     
/* 407 */     BigInteger accum = BigInteger.ONE;
/*     */     
/* 409 */     long numeratorAccum = n;
/* 410 */     long denominatorAccum = 1L;
/*     */     
/* 412 */     int bits = LongMath.log2(n, RoundingMode.CEILING);
/*     */     
/* 414 */     int numeratorBits = bits;
/*     */     
/* 416 */     for (int i = 1; i < k; i++) {
/* 417 */       int p = n - i;
/* 418 */       int q = i + 1;
/*     */ 
/*     */ 
/*     */       
/* 422 */       if (numeratorBits + bits >= 63) {
/*     */ 
/*     */         
/* 425 */         accum = accum.multiply(BigInteger.valueOf(numeratorAccum)).divide(BigInteger.valueOf(denominatorAccum));
/*     */ 
/*     */         
/* 428 */         numeratorAccum = p;
/* 429 */         denominatorAccum = q;
/* 430 */         numeratorBits = bits;
/*     */       } else {
/*     */         
/* 433 */         numeratorAccum *= p;
/* 434 */         denominatorAccum *= q;
/* 435 */         numeratorBits += bits;
/*     */       } 
/*     */     } 
/* 438 */     return accum.multiply(BigInteger.valueOf(numeratorAccum)).divide(BigInteger.valueOf(denominatorAccum));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("TODO")
/*     */   static boolean fitsInLong(BigInteger x) {
/* 446 */     return (x.bitLength() <= 63);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\math\BigIntegerMath.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */