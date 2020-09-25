/*     */ package org.bukkit.util.noise;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class OctaveGenerator
/*     */ {
/*     */   protected final NoiseGenerator[] octaves;
/*   8 */   protected double xScale = 1.0D;
/*   9 */   protected double yScale = 1.0D;
/*  10 */   protected double zScale = 1.0D;
/*     */   
/*     */   protected OctaveGenerator(NoiseGenerator[] octaves) {
/*  13 */     this.octaves = octaves;
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
/*     */   public void setScale(double scale) {
/*  25 */     setXScale(scale);
/*  26 */     setYScale(scale);
/*  27 */     setZScale(scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getXScale() {
/*  36 */     return this.xScale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXScale(double scale) {
/*  45 */     this.xScale = scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYScale() {
/*  54 */     return this.yScale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setYScale(double scale) {
/*  63 */     this.yScale = scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZScale() {
/*  72 */     return this.zScale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZScale(double scale) {
/*  81 */     this.zScale = scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NoiseGenerator[] getOctaves() {
/*  90 */     return (NoiseGenerator[])this.octaves.clone();
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
/*     */   public double noise(double x, double frequency, double amplitude) {
/* 103 */     return noise(x, 0.0D, 0.0D, frequency, amplitude);
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
/*     */   public double noise(double x, double frequency, double amplitude, boolean normalized) {
/* 117 */     return noise(x, 0.0D, 0.0D, frequency, amplitude, normalized);
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
/*     */   public double noise(double x, double y, double frequency, double amplitude) {
/* 131 */     return noise(x, y, 0.0D, frequency, amplitude);
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
/*     */   public double noise(double x, double y, double frequency, double amplitude, boolean normalized) {
/* 146 */     return noise(x, y, 0.0D, frequency, amplitude, normalized);
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
/*     */   public double noise(double x, double y, double z, double frequency, double amplitude) {
/* 161 */     return noise(x, y, z, frequency, amplitude, false);
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
/*     */   public double noise(double x, double y, double z, double frequency, double amplitude, boolean normalized) {
/* 177 */     double result = 0.0D;
/* 178 */     double amp = 1.0D;
/* 179 */     double freq = 1.0D;
/* 180 */     double max = 0.0D;
/*     */     
/* 182 */     x *= this.xScale;
/* 183 */     y *= this.yScale;
/* 184 */     z *= this.zScale;
/*     */     
/* 186 */     for (NoiseGenerator octave : this.octaves) {
/* 187 */       result += octave.noise(x * freq, y * freq, z * freq) * amp;
/* 188 */       max += amp;
/* 189 */       freq *= frequency;
/* 190 */       amp *= amplitude;
/*     */     } 
/*     */     
/* 193 */     if (normalized) {
/* 194 */       result /= max;
/*     */     }
/*     */     
/* 197 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukki\\util\noise\OctaveGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */