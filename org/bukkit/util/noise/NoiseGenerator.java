/*     */ package org.bukkit.util.noise;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class NoiseGenerator
/*     */ {
/*   7 */   protected final int[] perm = new int[512];
/*     */ 
/*     */   
/*     */   protected double offsetX;
/*     */ 
/*     */   
/*     */   protected double offsetY;
/*     */   
/*     */   protected double offsetZ;
/*     */ 
/*     */   
/*     */   public static int floor(double x) {
/*  19 */     return (x >= 0.0D) ? (int)x : ((int)x - 1);
/*     */   }
/*     */   
/*     */   protected static double fade(double x) {
/*  23 */     return x * x * x * (x * (x * 6.0D - 15.0D) + 10.0D);
/*     */   }
/*     */   
/*     */   protected static double lerp(double x, double y, double z) {
/*  27 */     return y + x * (z - y);
/*     */   }
/*     */   
/*     */   protected static double grad(int hash, double x, double y, double z) {
/*  31 */     hash &= 0xF;
/*  32 */     double u = (hash < 8) ? x : y;
/*  33 */     double v = (hash < 4) ? y : ((hash == 12 || hash == 14) ? x : z);
/*  34 */     return (((hash & 0x1) == 0) ? u : -u) + (((hash & 0x2) == 0) ? v : -v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double noise(double x) {
/*  44 */     return noise(x, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double noise(double x, double y) {
/*  55 */     return noise(x, y, 0.0D);
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
/*     */   public abstract double noise(double paramDouble1, double paramDouble2, double paramDouble3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double noise(double x, int octaves, double frequency, double amplitude) {
/*  79 */     return noise(x, 0.0D, 0.0D, octaves, frequency, amplitude);
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
/*     */   public double noise(double x, int octaves, double frequency, double amplitude, boolean normalized) {
/*  94 */     return noise(x, 0.0D, 0.0D, octaves, frequency, amplitude, normalized);
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
/*     */   public double noise(double x, double y, int octaves, double frequency, double amplitude) {
/* 109 */     return noise(x, y, 0.0D, octaves, frequency, amplitude);
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
/*     */   public double noise(double x, double y, int octaves, double frequency, double amplitude, boolean normalized) {
/* 125 */     return noise(x, y, 0.0D, octaves, frequency, amplitude, normalized);
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
/*     */   public double noise(double x, double y, double z, int octaves, double frequency, double amplitude) {
/* 141 */     return noise(x, y, z, octaves, frequency, amplitude, false);
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
/*     */   public double noise(double x, double y, double z, int octaves, double frequency, double amplitude, boolean normalized) {
/* 158 */     double result = 0.0D;
/* 159 */     double amp = 1.0D;
/* 160 */     double freq = 1.0D;
/* 161 */     double max = 0.0D;
/*     */     
/* 163 */     for (int i = 0; i < octaves; i++) {
/* 164 */       result += noise(x * freq, y * freq, z * freq) * amp;
/* 165 */       max += amp;
/* 166 */       freq *= frequency;
/* 167 */       amp *= amplitude;
/*     */     } 
/*     */     
/* 170 */     if (normalized) {
/* 171 */       result /= max;
/*     */     }
/*     */     
/* 174 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukki\\util\noise\NoiseGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */