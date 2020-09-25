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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenLargeFeatureStart
/*     */   extends StructureStart
/*     */ {
/*     */   public WorldGenLargeFeatureStart() {}
/*     */   
/*     */   public WorldGenLargeFeatureStart(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
/*  88 */     super(paramInt1, paramInt2);
/*  89 */     BiomeBase biomeBase = paramWorld.getBiome(paramInt1 * 16 + 8, paramInt2 * 16 + 8);
/*  90 */     if (biomeBase == BiomeBase.JUNGLE || biomeBase == BiomeBase.JUNGLE_HILLS) {
/*  91 */       WorldGenJungleTemple worldGenJungleTemple = new WorldGenJungleTemple(paramRandom, paramInt1 * 16, paramInt2 * 16);
/*  92 */       this.a.add(worldGenJungleTemple);
/*  93 */     } else if (biomeBase == BiomeBase.SWAMPLAND) {
/*  94 */       WorldGenWitchHut worldGenWitchHut = new WorldGenWitchHut(paramRandom, paramInt1 * 16, paramInt2 * 16);
/*  95 */       this.a.add(worldGenWitchHut);
/*     */     } else {
/*  97 */       WorldGenPyramidPiece worldGenPyramidPiece = new WorldGenPyramidPiece(paramRandom, paramInt1 * 16, paramInt2 * 16);
/*  98 */       this.a.add(worldGenPyramidPiece);
/*     */     } 
/*     */     
/* 101 */     c();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenLargeFeatureStart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */