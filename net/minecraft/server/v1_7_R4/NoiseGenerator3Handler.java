/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class NoiseGenerator3Handler {
/*   6 */   private static int[][] e = new int[][] { { 1, 1, 0 }, { -1, 1, 0 }, { 1, -1, 0 }, { -1, -1, 0 }, { 1, 0, 1 }, { -1, 0, 1 }, { 1, 0, -1 }, { -1, 0, -1 }, { 0, 1, 1 }, { 0, -1, 1 }, { 0, 1, -1 }, { 0, -1, -1 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public static final double a = Math.sqrt(3.0D);
/*     */   
/*  35 */   private int[] f = new int[512];
/*     */   public double b;
/*     */   public double c;
/*     */   public double d;
/*     */   
/*     */   public NoiseGenerator3Handler() {
/*  41 */     this(new Random());
/*     */   }
/*     */   
/*     */   public NoiseGenerator3Handler(Random paramRandom) {
/*  45 */     this.b = paramRandom.nextDouble() * 256.0D;
/*  46 */     this.c = paramRandom.nextDouble() * 256.0D;
/*  47 */     this.d = paramRandom.nextDouble() * 256.0D; byte b;
/*  48 */     for (b = 0; b < 'Ā'; b++) {
/*  49 */       this.f[b] = b;
/*     */     }
/*     */     
/*  52 */     for (b = 0; b < 'Ā'; b++) {
/*  53 */       int i = paramRandom.nextInt(256 - b) + b;
/*  54 */       int j = this.f[b];
/*  55 */       this.f[b] = this.f[i];
/*  56 */       this.f[i] = j;
/*     */       
/*  58 */       this.f[b + 256] = this.f[b];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int a(double paramDouble) {
/*  64 */     return (paramDouble > 0.0D) ? (int)paramDouble : ((int)paramDouble - 1);
/*     */   }
/*     */   
/*     */   private static double a(int[] paramArrayOfint, double paramDouble1, double paramDouble2) {
/*  68 */     return paramArrayOfint[0] * paramDouble1 + paramArrayOfint[1] * paramDouble2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double a(double paramDouble1, double paramDouble2) {
/*     */     byte b1, b2;
/*  79 */     double d14, d16, d18, d1 = 0.5D * (a - 1.0D);
/*  80 */     double d2 = (paramDouble1 + paramDouble2) * d1;
/*  81 */     int i = a(paramDouble1 + d2);
/*  82 */     int j = a(paramDouble2 + d2);
/*  83 */     double d3 = (3.0D - a) / 6.0D;
/*  84 */     double d4 = (i + j) * d3;
/*  85 */     double d5 = i - d4;
/*  86 */     double d6 = j - d4;
/*  87 */     double d7 = paramDouble1 - d5;
/*  88 */     double d8 = paramDouble2 - d6;
/*     */ 
/*     */ 
/*     */     
/*  92 */     if (d7 > d8) {
/*  93 */       b1 = 1;
/*  94 */       b2 = 0;
/*     */     } else {
/*     */       
/*  97 */       b1 = 0;
/*  98 */       b2 = 1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 103 */     double d9 = d7 - b1 + d3;
/* 104 */     double d10 = d8 - b2 + d3;
/* 105 */     double d11 = d7 - 1.0D + 2.0D * d3;
/* 106 */     double d12 = d8 - 1.0D + 2.0D * d3;
/*     */     
/* 108 */     int k = i & 0xFF;
/* 109 */     int m = j & 0xFF;
/* 110 */     int n = this.f[k + this.f[m]] % 12;
/* 111 */     int i1 = this.f[k + b1 + this.f[m + b2]] % 12;
/* 112 */     int i2 = this.f[k + 1 + this.f[m + 1]] % 12;
/*     */     
/* 114 */     double d13 = 0.5D - d7 * d7 - d8 * d8;
/* 115 */     if (d13 < 0.0D) { d14 = 0.0D; }
/*     */     else
/* 117 */     { d13 *= d13;
/* 118 */       d14 = d13 * d13 * a(e[n], d7, d8); }
/*     */     
/* 120 */     double d15 = 0.5D - d9 * d9 - d10 * d10;
/* 121 */     if (d15 < 0.0D) { d16 = 0.0D; }
/*     */     else
/* 123 */     { d15 *= d15;
/* 124 */       d16 = d15 * d15 * a(e[i1], d9, d10); }
/*     */     
/* 126 */     double d17 = 0.5D - d11 * d11 - d12 * d12;
/* 127 */     if (d17 < 0.0D) { d18 = 0.0D; }
/*     */     else
/* 129 */     { d17 *= d17;
/* 130 */       d18 = d17 * d17 * a(e[i2], d11, d12); }
/*     */ 
/*     */ 
/*     */     
/* 134 */     return 70.0D * (d14 + d16 + d18);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 258 */   private static final double g = 0.5D * (a - 1.0D);
/* 259 */   private static final double h = (3.0D - a) / 6.0D;
/*     */   
/*     */   public void a(double[] paramArrayOfdouble, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, double paramDouble3, double paramDouble4, double paramDouble5) {
/* 262 */     byte b1 = 0;
/* 263 */     for (byte b2 = 0; b2 < paramInt2; b2++) {
/* 264 */       double d = (paramDouble2 + b2) * paramDouble4 + this.c;
/* 265 */       for (byte b = 0; b < paramInt1; b++) {
/* 266 */         byte b3, b4; double d13, d15, d17, d1 = (paramDouble1 + b) * paramDouble3 + this.b;
/*     */ 
/*     */         
/* 269 */         double d2 = (d1 + d) * g;
/* 270 */         int i = a(d1 + d2);
/* 271 */         int j = a(d + d2);
/* 272 */         double d3 = (i + j) * h;
/* 273 */         double d4 = i - d3;
/* 274 */         double d5 = j - d3;
/* 275 */         double d6 = d1 - d4;
/* 276 */         double d7 = d - d5;
/*     */ 
/*     */ 
/*     */         
/* 280 */         if (d6 > d7) {
/* 281 */           b3 = 1;
/* 282 */           b4 = 0;
/*     */         } else {
/*     */           
/* 285 */           b3 = 0;
/* 286 */           b4 = 1;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 291 */         double d8 = d6 - b3 + h;
/* 292 */         double d9 = d7 - b4 + h;
/* 293 */         double d10 = d6 - 1.0D + 2.0D * h;
/* 294 */         double d11 = d7 - 1.0D + 2.0D * h;
/*     */         
/* 296 */         int k = i & 0xFF;
/* 297 */         int m = j & 0xFF;
/* 298 */         int n = this.f[k + this.f[m]] % 12;
/* 299 */         int i1 = this.f[k + b3 + this.f[m + b4]] % 12;
/* 300 */         int i2 = this.f[k + 1 + this.f[m + 1]] % 12;
/*     */         
/* 302 */         double d12 = 0.5D - d6 * d6 - d7 * d7;
/* 303 */         if (d12 < 0.0D) { d13 = 0.0D; }
/*     */         else
/* 305 */         { d12 *= d12;
/* 306 */           d13 = d12 * d12 * a(e[n], d6, d7); }
/*     */         
/* 308 */         double d14 = 0.5D - d8 * d8 - d9 * d9;
/* 309 */         if (d14 < 0.0D) { d15 = 0.0D; }
/*     */         else
/* 311 */         { d14 *= d14;
/* 312 */           d15 = d14 * d14 * a(e[i1], d8, d9); }
/*     */         
/* 314 */         double d16 = 0.5D - d10 * d10 - d11 * d11;
/* 315 */         if (d16 < 0.0D) { d17 = 0.0D; }
/*     */         else
/* 317 */         { d16 *= d16;
/* 318 */           d17 = d16 * d16 * a(e[i2], d10, d11); }
/*     */ 
/*     */ 
/*     */         
/* 322 */         paramArrayOfdouble[b1++] = paramArrayOfdouble[b1++] + 70.0D * (d13 + d15 + d17) * paramDouble5;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NoiseGenerator3Handler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */