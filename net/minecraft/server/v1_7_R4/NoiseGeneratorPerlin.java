/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class NoiseGeneratorPerlin extends NoiseGenerator {
/*   6 */   private int[] d = new int[512];
/*     */   public double a;
/*     */   public double b;
/*     */   public double c;
/*     */   
/*     */   public NoiseGeneratorPerlin() {
/*  12 */     this(new Random());
/*     */   }
/*     */   
/*     */   public NoiseGeneratorPerlin(Random paramRandom) {
/*  16 */     this.a = paramRandom.nextDouble() * 256.0D;
/*  17 */     this.b = paramRandom.nextDouble() * 256.0D;
/*  18 */     this.c = paramRandom.nextDouble() * 256.0D; byte b;
/*  19 */     for (b = 0; b < 'Ā'; b++) {
/*  20 */       this.d[b] = b;
/*     */     }
/*     */     
/*  23 */     for (b = 0; b < 'Ā'; b++) {
/*  24 */       int i = paramRandom.nextInt(256 - b) + b;
/*  25 */       int j = this.d[b];
/*  26 */       this.d[b] = this.d[i];
/*  27 */       this.d[i] = j;
/*     */       
/*  29 */       this.d[b + 256] = this.d[b];
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double b(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  71 */     return paramDouble2 + paramDouble1 * (paramDouble3 - paramDouble2);
/*     */   }
/*     */   
/*  74 */   private static final double[] e = new double[] { 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D };
/*  75 */   private static final double[] f = new double[] { 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D };
/*  76 */   private static final double[] g = new double[] { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D };
/*  77 */   private static final double[] h = new double[] { 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D };
/*  78 */   private static final double[] i = new double[] { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D };
/*     */   
/*     */   public final double a(int paramInt, double paramDouble1, double paramDouble2) {
/*  81 */     int i = paramInt & 0xF;
/*  82 */     return h[i] * paramDouble1 + i[i] * paramDouble2;
/*     */   }
/*     */   
/*     */   public final double a(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3) {
/*  86 */     int i = paramInt & 0xF;
/*  87 */     return e[i] * paramDouble1 + f[i] * paramDouble2 + g[i] * paramDouble3;
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
/*     */   public void a(double[] paramArrayOfdouble, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt1, int paramInt2, int paramInt3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7) {
/* 100 */     if (paramInt2 == 1) {
/* 101 */       int i3 = 0, i4 = 0, i5 = 0, i6 = 0;
/* 102 */       double d6 = 0.0D, d7 = 0.0D;
/* 103 */       byte b3 = 0;
/* 104 */       double d8 = 1.0D / paramDouble7;
/* 105 */       for (byte b4 = 0; b4 < paramInt1; b4++) {
/* 106 */         double d9 = paramDouble1 + b4 * paramDouble4 + this.a;
/* 107 */         int i7 = (int)d9;
/* 108 */         if (d9 < i7) i7--; 
/* 109 */         int i8 = i7 & 0xFF;
/* 110 */         d9 -= i7;
/* 111 */         double d10 = d9 * d9 * d9 * (d9 * (d9 * 6.0D - 15.0D) + 10.0D);
/*     */         
/* 113 */         for (byte b = 0; b < paramInt3; b++) {
/* 114 */           double d11 = paramDouble3 + b * paramDouble6 + this.c;
/* 115 */           int i9 = (int)d11;
/* 116 */           if (d11 < i9) i9--; 
/* 117 */           int i10 = i9 & 0xFF;
/* 118 */           d11 -= i9;
/* 119 */           double d12 = d11 * d11 * d11 * (d11 * (d11 * 6.0D - 15.0D) + 10.0D);
/*     */           
/* 121 */           i3 = this.d[i8] + 0;
/* 122 */           i4 = this.d[i3] + i10;
/* 123 */           i5 = this.d[i8 + 1] + 0;
/* 124 */           i6 = this.d[i5] + i10;
/* 125 */           d6 = b(d10, a(this.d[i4], d9, d11), a(this.d[i6], d9 - 1.0D, 0.0D, d11));
/* 126 */           d7 = b(d10, a(this.d[i4 + 1], d9, 0.0D, d11 - 1.0D), a(this.d[i6 + 1], d9 - 1.0D, 0.0D, d11 - 1.0D));
/*     */           
/* 128 */           double d13 = b(d12, d6, d7);
/*     */           
/* 130 */           paramArrayOfdouble[b3++] = paramArrayOfdouble[b3++] + d13 * d8;
/*     */         } 
/*     */       } 
/*     */       return;
/*     */     } 
/* 135 */     byte b1 = 0;
/* 136 */     double d3 = 1.0D / paramDouble7;
/* 137 */     int i = -1;
/* 138 */     int k = 0, m = 0, n = 0, i1 = 0, j = 0, i2 = 0;
/* 139 */     double d4 = 0.0D, d1 = 0.0D, d5 = 0.0D, d2 = 0.0D;
/*     */     
/* 141 */     for (byte b2 = 0; b2 < paramInt1; b2++) {
/* 142 */       double d6 = paramDouble1 + b2 * paramDouble4 + this.a;
/* 143 */       int i3 = (int)d6;
/* 144 */       if (d6 < i3) i3--; 
/* 145 */       int i4 = i3 & 0xFF;
/* 146 */       d6 -= i3;
/* 147 */       double d7 = d6 * d6 * d6 * (d6 * (d6 * 6.0D - 15.0D) + 10.0D);
/*     */       
/* 149 */       for (byte b = 0; b < paramInt3; b++) {
/* 150 */         double d8 = paramDouble3 + b * paramDouble6 + this.c;
/* 151 */         int i5 = (int)d8;
/* 152 */         if (d8 < i5) i5--; 
/* 153 */         int i6 = i5 & 0xFF;
/* 154 */         d8 -= i5;
/* 155 */         double d9 = d8 * d8 * d8 * (d8 * (d8 * 6.0D - 15.0D) + 10.0D);
/*     */         
/* 157 */         for (byte b3 = 0; b3 < paramInt2; b3++) {
/* 158 */           double d10 = paramDouble2 + b3 * paramDouble5 + this.b;
/* 159 */           int i7 = (int)d10;
/* 160 */           if (d10 < i7) i7--; 
/* 161 */           int i8 = i7 & 0xFF;
/* 162 */           d10 -= i7;
/* 163 */           double d11 = d10 * d10 * d10 * (d10 * (d10 * 6.0D - 15.0D) + 10.0D);
/*     */           
/* 165 */           if (b3 == 0 || i8 != i) {
/* 166 */             i = i8;
/* 167 */             k = this.d[i4] + i8;
/* 168 */             m = this.d[k] + i6;
/* 169 */             n = this.d[k + 1] + i6;
/* 170 */             i1 = this.d[i4 + 1] + i8;
/* 171 */             j = this.d[i1] + i6;
/* 172 */             i2 = this.d[i1 + 1] + i6;
/* 173 */             d4 = b(d7, a(this.d[m], d6, d10, d8), a(this.d[j], d6 - 1.0D, d10, d8));
/* 174 */             d1 = b(d7, a(this.d[n], d6, d10 - 1.0D, d8), a(this.d[i2], d6 - 1.0D, d10 - 1.0D, d8));
/* 175 */             d5 = b(d7, a(this.d[m + 1], d6, d10, d8 - 1.0D), a(this.d[j + 1], d6 - 1.0D, d10, d8 - 1.0D));
/* 176 */             d2 = b(d7, a(this.d[n + 1], d6, d10 - 1.0D, d8 - 1.0D), a(this.d[i2 + 1], d6 - 1.0D, d10 - 1.0D, d8 - 1.0D));
/*     */           } 
/*     */           
/* 179 */           double d12 = b(d11, d4, d1);
/* 180 */           double d13 = b(d11, d5, d2);
/* 181 */           double d14 = b(d9, d12, d13);
/*     */           
/* 183 */           paramArrayOfdouble[b1++] = paramArrayOfdouble[b1++] + d14 * d3;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NoiseGeneratorPerlin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */