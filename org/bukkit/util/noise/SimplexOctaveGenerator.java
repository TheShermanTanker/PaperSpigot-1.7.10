/*     */ package org.bukkit.util.noise;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.World;
/*     */ 
/*     */ 
/*     */ public class SimplexOctaveGenerator
/*     */   extends OctaveGenerator
/*     */ {
/*  10 */   private double wScale = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimplexOctaveGenerator(World world, int octaves) {
/*  19 */     this(new Random(world.getSeed()), octaves);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimplexOctaveGenerator(long seed, int octaves) {
/*  29 */     this(new Random(seed), octaves);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimplexOctaveGenerator(Random rand, int octaves) {
/*  39 */     super(createOctaves(rand, octaves));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setScale(double scale) {
/*  44 */     super.setScale(scale);
/*  45 */     setWScale(scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getWScale() {
/*  54 */     return this.wScale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWScale(double scale) {
/*  63 */     this.wScale = scale;
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
/*     */   public double noise(double x, double y, double z, double w, double frequency, double amplitude) {
/*  79 */     return noise(x, y, z, w, frequency, amplitude, false);
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
/*     */   public double noise(double x, double y, double z, double w, double frequency, double amplitude, boolean normalized) {
/*  96 */     double result = 0.0D;
/*  97 */     double amp = 1.0D;
/*  98 */     double freq = 1.0D;
/*  99 */     double max = 0.0D;
/*     */     
/* 101 */     x *= this.xScale;
/* 102 */     y *= this.yScale;
/* 103 */     z *= this.zScale;
/* 104 */     w *= this.wScale;
/*     */     
/* 106 */     for (NoiseGenerator octave : this.octaves) {
/* 107 */       result += ((SimplexNoiseGenerator)octave).noise(x * freq, y * freq, z * freq, w * freq) * amp;
/* 108 */       max += amp;
/* 109 */       freq *= frequency;
/* 110 */       amp *= amplitude;
/*     */     } 
/*     */     
/* 113 */     if (normalized) {
/* 114 */       result /= max;
/*     */     }
/*     */     
/* 117 */     return result;
/*     */   }
/*     */   
/*     */   private static NoiseGenerator[] createOctaves(Random rand, int octaves) {
/* 121 */     NoiseGenerator[] result = new NoiseGenerator[octaves];
/*     */     
/* 123 */     for (int i = 0; i < octaves; i++) {
/* 124 */       result[i] = new SimplexNoiseGenerator(rand);
/*     */     }
/*     */     
/* 127 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukki\\util\noise\SimplexOctaveGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */