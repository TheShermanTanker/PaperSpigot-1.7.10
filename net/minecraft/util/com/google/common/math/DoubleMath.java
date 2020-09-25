/*     */ package net.minecraft.util.com.google.common.math;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.util.com.google.common.annotations.VisibleForTesting;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.primitives.Booleans;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DoubleMath
/*     */ {
/*     */   private static final double MIN_INT_AS_DOUBLE = -2.147483648E9D;
/*     */   private static final double MAX_INT_AS_DOUBLE = 2.147483647E9D;
/*     */   private static final double MIN_LONG_AS_DOUBLE = -9.223372036854776E18D;
/*     */   private static final double MAX_LONG_AS_DOUBLE_PLUS_ONE = 9.223372036854776E18D;
/*     */   
/*     */   static double roundIntermediate(double x, RoundingMode mode) {
/*     */     double z;
/*  54 */     if (!DoubleUtils.isFinite(x)) {
/*  55 */       throw new ArithmeticException("input is infinite or NaN");
/*     */     }
/*  57 */     switch (mode) {
/*     */       case UNNECESSARY:
/*  59 */         MathPreconditions.checkRoundingUnnecessary(isMathematicalInteger(x));
/*  60 */         return x;
/*     */       
/*     */       case FLOOR:
/*  63 */         if (x >= 0.0D || isMathematicalInteger(x)) {
/*  64 */           return x;
/*     */         }
/*  66 */         return x - 1.0D;
/*     */ 
/*     */       
/*     */       case CEILING:
/*  70 */         if (x <= 0.0D || isMathematicalInteger(x)) {
/*  71 */           return x;
/*     */         }
/*  73 */         return x + 1.0D;
/*     */ 
/*     */       
/*     */       case DOWN:
/*  77 */         return x;
/*     */       
/*     */       case UP:
/*  80 */         if (isMathematicalInteger(x)) {
/*  81 */           return x;
/*     */         }
/*  83 */         return x + Math.copySign(1.0D, x);
/*     */ 
/*     */       
/*     */       case HALF_EVEN:
/*  87 */         return Math.rint(x);
/*     */       
/*     */       case HALF_UP:
/*  90 */         z = Math.rint(x);
/*  91 */         if (Math.abs(x - z) == 0.5D) {
/*  92 */           return x + Math.copySign(0.5D, x);
/*     */         }
/*  94 */         return z;
/*     */ 
/*     */ 
/*     */       
/*     */       case HALF_DOWN:
/*  99 */         z = Math.rint(x);
/* 100 */         if (Math.abs(x - z) == 0.5D) {
/* 101 */           return x;
/*     */         }
/* 103 */         return z;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 108 */     throw new AssertionError();
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
/*     */   public static int roundToInt(double x, RoundingMode mode) {
/* 127 */     double z = roundIntermediate(x, mode);
/* 128 */     MathPreconditions.checkInRange(((z > -2.147483649E9D)) & ((z < 2.147483648E9D)));
/* 129 */     return (int)z;
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
/*     */   public static long roundToLong(double x, RoundingMode mode) {
/* 150 */     double z = roundIntermediate(x, mode);
/* 151 */     MathPreconditions.checkInRange(((-9.223372036854776E18D - z < 1.0D)) & ((z < 9.223372036854776E18D)));
/* 152 */     return (long)z;
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
/*     */   public static BigInteger roundToBigInteger(double x, RoundingMode mode) {
/* 174 */     x = roundIntermediate(x, mode);
/* 175 */     if ((((-9.223372036854776E18D - x < 1.0D) ? 1 : 0) & ((x < 9.223372036854776E18D) ? 1 : 0)) != 0) {
/* 176 */       return BigInteger.valueOf((long)x);
/*     */     }
/* 178 */     int exponent = Math.getExponent(x);
/* 179 */     long significand = DoubleUtils.getSignificand(x);
/* 180 */     BigInteger result = BigInteger.valueOf(significand).shiftLeft(exponent - 52);
/* 181 */     return (x < 0.0D) ? result.negate() : result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPowerOfTwo(double x) {
/* 189 */     return (x > 0.0D && DoubleUtils.isFinite(x) && LongMath.isPowerOfTwo(DoubleUtils.getSignificand(x)));
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
/*     */   public static double log2(double x) {
/* 208 */     return Math.log(x) / LN_2;
/*     */   }
/*     */   
/* 211 */   private static final double LN_2 = Math.log(2.0D);
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static final int MAX_FACTORIAL = 170;
/*     */ 
/*     */ 
/*     */   
/*     */   public static int log2(double x, RoundingMode mode) {
/*     */     boolean increment;
/*     */     int i;
/*     */     double xScaled;
/* 224 */     Preconditions.checkArgument((x > 0.0D && DoubleUtils.isFinite(x)), "x must be positive and finite");
/* 225 */     int exponent = Math.getExponent(x);
/* 226 */     if (!DoubleUtils.isNormal(x)) {
/* 227 */       return log2(x * 4.503599627370496E15D, mode) - 52;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 232 */     switch (mode) {
/*     */       case UNNECESSARY:
/* 234 */         MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(x));
/*     */       
/*     */       case FLOOR:
/* 237 */         increment = false;
/*     */         break;
/*     */       case CEILING:
/* 240 */         increment = !isPowerOfTwo(x);
/*     */         break;
/*     */       case DOWN:
/* 243 */         i = ((exponent < 0) ? 1 : 0) & (!isPowerOfTwo(x) ? 1 : 0);
/*     */         break;
/*     */       case UP:
/* 246 */         i = ((exponent >= 0) ? 1 : 0) & (!isPowerOfTwo(x) ? 1 : 0);
/*     */         break;
/*     */       case HALF_EVEN:
/*     */       case HALF_UP:
/*     */       case HALF_DOWN:
/* 251 */         xScaled = DoubleUtils.scaleNormalize(x);
/*     */ 
/*     */         
/* 254 */         i = (xScaled * xScaled > 2.0D) ? 1 : 0;
/*     */         break;
/*     */       default:
/* 257 */         throw new AssertionError();
/*     */     } 
/* 259 */     return (i != 0) ? (exponent + 1) : exponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isMathematicalInteger(double x) {
/* 269 */     return (DoubleUtils.isFinite(x) && (x == 0.0D || 52 - Long.numberOfTrailingZeros(DoubleUtils.getSignificand(x)) <= Math.getExponent(x)));
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
/*     */   public static double factorial(int n) {
/* 284 */     MathPreconditions.checkNonNegative("n", n);
/* 285 */     if (n > 170) {
/* 286 */       return Double.POSITIVE_INFINITY;
/*     */     }
/*     */ 
/*     */     
/* 290 */     double accum = 1.0D;
/* 291 */     for (int i = 1 + (n & 0xFFFFFFF0); i <= n; i++) {
/* 292 */       accum *= i;
/*     */     }
/* 294 */     return accum * everySixteenthFactorial[n >> 4];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/* 302 */   static final double[] everySixteenthFactorial = new double[] { 1.0D, 2.0922789888E13D, 2.631308369336935E35D, 1.2413915592536073E61D, 1.2688693218588417E89D, 7.156945704626381E118D, 9.916779348709496E149D, 1.974506857221074E182D, 3.856204823625804E215D, 5.5502938327393044E249D, 4.7147236359920616E284D };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean fuzzyEquals(double a, double b, double tolerance) {
/* 341 */     MathPreconditions.checkNonNegative("tolerance", tolerance);
/* 342 */     return (Math.copySign(a - b, 1.0D) <= tolerance || a == b || (Double.isNaN(a) && Double.isNaN(b)));
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
/*     */   public static int fuzzyCompare(double a, double b, double tolerance) {
/* 364 */     if (fuzzyEquals(a, b, tolerance))
/* 365 */       return 0; 
/* 366 */     if (a < b)
/* 367 */       return -1; 
/* 368 */     if (a > b) {
/* 369 */       return 1;
/*     */     }
/* 371 */     return Booleans.compare(Double.isNaN(a), Double.isNaN(b));
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class MeanAccumulator
/*     */   {
/* 377 */     private long count = 0L;
/* 378 */     private double mean = 0.0D;
/*     */     
/*     */     void add(double value) {
/* 381 */       Preconditions.checkArgument(DoubleUtils.isFinite(value));
/* 382 */       this.count++;
/*     */       
/* 384 */       this.mean += (value - this.mean) / this.count;
/*     */     }
/*     */     
/*     */     double mean() {
/* 388 */       Preconditions.checkArgument((this.count > 0L), "Cannot take mean of 0 values");
/* 389 */       return this.mean;
/*     */     }
/*     */ 
/*     */     
/*     */     private MeanAccumulator() {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static double mean(double... values) {
/* 398 */     MeanAccumulator accumulator = new MeanAccumulator();
/* 399 */     for (double value : values) {
/* 400 */       accumulator.add(value);
/*     */     }
/* 402 */     return accumulator.mean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double mean(int... values) {
/* 410 */     MeanAccumulator accumulator = new MeanAccumulator();
/* 411 */     for (int value : values) {
/* 412 */       accumulator.add(value);
/*     */     }
/* 414 */     return accumulator.mean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double mean(long... values) {
/* 423 */     MeanAccumulator accumulator = new MeanAccumulator();
/* 424 */     for (long value : values) {
/* 425 */       accumulator.add(value);
/*     */     }
/* 427 */     return accumulator.mean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double mean(Iterable<? extends Number> values) {
/* 436 */     MeanAccumulator accumulator = new MeanAccumulator();
/* 437 */     for (Number value : values) {
/* 438 */       accumulator.add(value.doubleValue());
/*     */     }
/* 440 */     return accumulator.mean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double mean(Iterator<? extends Number> values) {
/* 449 */     MeanAccumulator accumulator = new MeanAccumulator();
/* 450 */     while (values.hasNext()) {
/* 451 */       accumulator.add(((Number)values.next()).doubleValue());
/*     */     }
/* 453 */     return accumulator.mean();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\math\DoubleMath.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */