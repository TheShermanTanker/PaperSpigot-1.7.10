/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class WorldChunkManagerHell extends WorldChunkManager {
/*     */   private BiomeBase c;
/*     */   private float d;
/*     */   
/*     */   public WorldChunkManagerHell(BiomeBase paramBiomeBase, float paramFloat) {
/*  12 */     this.c = paramBiomeBase;
/*  13 */     this.d = paramFloat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeBase getBiome(int paramInt1, int paramInt2) {
/*  23 */     return this.c;
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
/*     */   public BiomeBase[] getBiomes(BiomeBase[] paramArrayOfBiomeBase, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  40 */     if (paramArrayOfBiomeBase == null || paramArrayOfBiomeBase.length < paramInt3 * paramInt4) {
/*  41 */       paramArrayOfBiomeBase = new BiomeBase[paramInt3 * paramInt4];
/*     */     }
/*     */     
/*  44 */     Arrays.fill((Object[])paramArrayOfBiomeBase, 0, paramInt3 * paramInt4, this.c);
/*     */     
/*  46 */     return paramArrayOfBiomeBase;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getWetness(float[] paramArrayOffloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  51 */     if (paramArrayOffloat == null || paramArrayOffloat.length < paramInt3 * paramInt4) {
/*  52 */       paramArrayOffloat = new float[paramInt3 * paramInt4];
/*     */     }
/*  54 */     Arrays.fill(paramArrayOffloat, 0, paramInt3 * paramInt4, this.d);
/*     */     
/*  56 */     return paramArrayOffloat;
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
/*     */   public BiomeBase[] getBiomeBlock(BiomeBase[] paramArrayOfBiomeBase, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  80 */     if (paramArrayOfBiomeBase == null || paramArrayOfBiomeBase.length < paramInt3 * paramInt4) {
/*  81 */       paramArrayOfBiomeBase = new BiomeBase[paramInt3 * paramInt4];
/*     */     }
/*     */     
/*  84 */     Arrays.fill((Object[])paramArrayOfBiomeBase, 0, paramInt3 * paramInt4, this.c);
/*     */     
/*  86 */     return paramArrayOfBiomeBase;
/*     */   }
/*     */ 
/*     */   
/*     */   public BiomeBase[] a(BiomeBase[] paramArrayOfBiomeBase, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/*  91 */     return getBiomeBlock(paramArrayOfBiomeBase, paramInt1, paramInt2, paramInt3, paramInt4);
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
/*     */   public ChunkPosition a(int paramInt1, int paramInt2, int paramInt3, List paramList, Random paramRandom) {
/* 106 */     if (paramList.contains(this.c)) {
/* 107 */       return new ChunkPosition(paramInt1 - paramInt3 + paramRandom.nextInt(paramInt3 * 2 + 1), 0, paramInt2 - paramInt3 + paramRandom.nextInt(paramInt3 * 2 + 1));
/*     */     }
/*     */     
/* 110 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(int paramInt1, int paramInt2, int paramInt3, List paramList) {
/* 120 */     return paramList.contains(this.c);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldChunkManagerHell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */