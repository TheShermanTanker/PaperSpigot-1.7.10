/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MathHelper
/*     */ {
/*  18 */   private static float[] a = new float[65536]; static {
/*  19 */     for (byte b = 0; b < 65536; b++) {
/*  20 */       a[b] = (float)Math.sin(b * Math.PI * 2.0D / 65536.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final float sin(float paramFloat) {
/*  25 */     return a[(int)(paramFloat * 10430.378F) & 0xFFFF];
/*     */   }
/*     */   
/*     */   public static final float cos(float paramFloat) {
/*  29 */     return a[(int)(paramFloat * 10430.378F + 16384.0F) & 0xFFFF];
/*     */   }
/*     */   
/*     */   public static final float c(float paramFloat) {
/*  33 */     return (float)Math.sqrt(paramFloat);
/*     */   }
/*     */   
/*     */   public static final float sqrt(double paramDouble) {
/*  37 */     return (float)Math.sqrt(paramDouble);
/*     */   }
/*     */   
/*     */   public static int d(float paramFloat) {
/*  41 */     int i = (int)paramFloat;
/*  42 */     return (paramFloat < i) ? (i - 1) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int floor(double paramDouble) {
/*  50 */     int i = (int)paramDouble;
/*  51 */     return (paramDouble < i) ? (i - 1) : i;
/*     */   }
/*     */   
/*     */   public static long d(double paramDouble) {
/*  55 */     long l = (long)paramDouble;
/*  56 */     return (paramDouble < l) ? (l - 1L) : l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float abs(float paramFloat) {
/*  64 */     return (paramFloat >= 0.0F) ? paramFloat : -paramFloat;
/*     */   }
/*     */   
/*     */   public static int a(int paramInt) {
/*  68 */     return (paramInt >= 0) ? paramInt : -paramInt;
/*     */   }
/*     */   
/*     */   public static int f(float paramFloat) {
/*  72 */     int i = (int)paramFloat;
/*  73 */     return (paramFloat > i) ? (i + 1) : i;
/*     */   }
/*     */   
/*     */   public static int f(double paramDouble) {
/*  77 */     int i = (int)paramDouble;
/*  78 */     return (paramDouble > i) ? (i + 1) : i;
/*     */   }
/*     */   
/*     */   public static int a(int paramInt1, int paramInt2, int paramInt3) {
/*  82 */     if (paramInt1 < paramInt2) {
/*  83 */       return paramInt2;
/*     */     }
/*  85 */     if (paramInt1 > paramInt3) {
/*  86 */       return paramInt3;
/*     */     }
/*  88 */     return paramInt1;
/*     */   }
/*     */   
/*     */   public static float a(float paramFloat1, float paramFloat2, float paramFloat3) {
/*  92 */     if (paramFloat1 < paramFloat2) {
/*  93 */       return paramFloat2;
/*     */     }
/*  95 */     if (paramFloat1 > paramFloat3) {
/*  96 */       return paramFloat3;
/*     */     }
/*  98 */     return paramFloat1;
/*     */   }
/*     */   
/*     */   public static double a(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 102 */     if (paramDouble1 < paramDouble2) {
/* 103 */       return paramDouble2;
/*     */     }
/* 105 */     if (paramDouble1 > paramDouble3) {
/* 106 */       return paramDouble3;
/*     */     }
/* 108 */     return paramDouble1;
/*     */   }
/*     */   
/*     */   public static double b(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 112 */     if (paramDouble3 < 0.0D) {
/* 113 */       return paramDouble1;
/*     */     }
/* 115 */     if (paramDouble3 > 1.0D) {
/* 116 */       return paramDouble2;
/*     */     }
/* 118 */     return paramDouble1 + (paramDouble2 - paramDouble1) * paramDouble3;
/*     */   }
/*     */   
/*     */   public static double a(double paramDouble1, double paramDouble2) {
/* 122 */     if (paramDouble1 < 0.0D) paramDouble1 = -paramDouble1; 
/* 123 */     if (paramDouble2 < 0.0D) paramDouble2 = -paramDouble2; 
/* 124 */     return (paramDouble1 > paramDouble2) ? paramDouble1 : paramDouble2;
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
/*     */   public static int nextInt(Random paramRandom, int paramInt1, int paramInt2) {
/* 137 */     if (paramInt1 >= paramInt2) {
/* 138 */       return paramInt1;
/*     */     }
/* 140 */     return paramRandom.nextInt(paramInt2 - paramInt1 + 1) + paramInt1;
/*     */   }
/*     */   
/*     */   public static float a(Random paramRandom, float paramFloat1, float paramFloat2) {
/* 144 */     if (paramFloat1 >= paramFloat2) return paramFloat1; 
/* 145 */     return paramRandom.nextFloat() * (paramFloat2 - paramFloat1) + paramFloat1;
/*     */   }
/*     */   
/*     */   public static double a(Random paramRandom, double paramDouble1, double paramDouble2) {
/* 149 */     if (paramDouble1 >= paramDouble2) return paramDouble1; 
/* 150 */     return paramRandom.nextDouble() * (paramDouble2 - paramDouble1) + paramDouble1;
/*     */   }
/*     */   
/*     */   public static double a(long[] paramArrayOflong) {
/* 154 */     long l = 0L;
/*     */     
/* 156 */     for (long l1 : paramArrayOflong) {
/* 157 */       l += l1;
/*     */     }
/*     */     
/* 160 */     return l / paramArrayOflong.length;
/*     */   }
/*     */   
/*     */   public static float g(float paramFloat) {
/* 164 */     paramFloat %= 360.0F;
/* 165 */     if (paramFloat >= 180.0F) paramFloat -= 360.0F; 
/* 166 */     if (paramFloat < -180.0F) paramFloat += 360.0F; 
/* 167 */     return paramFloat;
/*     */   }
/*     */   
/*     */   public static double g(double paramDouble) {
/* 171 */     paramDouble %= 360.0D;
/* 172 */     if (paramDouble >= 180.0D) paramDouble -= 360.0D; 
/* 173 */     if (paramDouble < -180.0D) paramDouble += 360.0D; 
/* 174 */     return paramDouble;
/*     */   }
/*     */   
/*     */   public static int a(String paramString, int paramInt) {
/* 178 */     int i = paramInt;
/*     */     
/*     */     try {
/* 181 */       i = Integer.parseInt(paramString);
/* 182 */     } catch (Throwable throwable) {}
/*     */ 
/*     */     
/* 185 */     return i;
/*     */   }
/*     */   
/*     */   public static int a(String paramString, int paramInt1, int paramInt2) {
/* 189 */     int i = paramInt1;
/*     */     
/*     */     try {
/* 192 */       i = Integer.parseInt(paramString);
/* 193 */     } catch (Throwable throwable) {}
/*     */ 
/*     */     
/* 196 */     if (i < paramInt2) i = paramInt2; 
/* 197 */     return i;
/*     */   }
/*     */   
/*     */   public static double a(String paramString, double paramDouble) {
/* 201 */     double d = paramDouble;
/*     */     
/*     */     try {
/* 204 */       d = Double.parseDouble(paramString);
/* 205 */     } catch (Throwable throwable) {}
/*     */ 
/*     */     
/* 208 */     return d;
/*     */   }
/*     */   
/*     */   public static double a(String paramString, double paramDouble1, double paramDouble2) {
/* 212 */     double d = paramDouble1;
/*     */     
/*     */     try {
/* 215 */       d = Double.parseDouble(paramString);
/* 216 */     } catch (Throwable throwable) {}
/*     */ 
/*     */     
/* 219 */     if (d < paramDouble2) d = paramDouble2; 
/* 220 */     return d;
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
/* 240 */   private static final int[] b = new int[] { 0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9 };
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MathHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */