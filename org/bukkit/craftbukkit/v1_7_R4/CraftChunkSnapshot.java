/*    */ package org.bukkit.craftbukkit.v1_7_R4;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.BiomeBase;
/*    */ import org.bukkit.ChunkSnapshot;
/*    */ import org.bukkit.block.Biome;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.block.CraftBlock;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CraftChunkSnapshot
/*    */   implements ChunkSnapshot
/*    */ {
/*    */   private final int x;
/*    */   private final int z;
/*    */   private final String worldname;
/*    */   private final short[][] blockids;
/*    */   private final byte[][] blockdata;
/*    */   private final byte[][] skylight;
/*    */   private final byte[][] emitlight;
/*    */   private final boolean[] empty;
/*    */   private final int[] hmap;
/*    */   private final long captureFulltime;
/*    */   private final BiomeBase[] biome;
/*    */   private final double[] biomeTemp;
/*    */   private final double[] biomeRain;
/*    */   
/*    */   CraftChunkSnapshot(int x, int z, String wname, long wtime, short[][] sectionBlockIDs, byte[][] sectionBlockData, byte[][] sectionSkyLights, byte[][] sectionEmitLights, boolean[] sectionEmpty, int[] hmap, BiomeBase[] biome, double[] biomeTemp, double[] biomeRain) {
/* 28 */     this.x = x;
/* 29 */     this.z = z;
/* 30 */     this.worldname = wname;
/* 31 */     this.captureFulltime = wtime;
/* 32 */     this.blockids = sectionBlockIDs;
/* 33 */     this.blockdata = sectionBlockData;
/* 34 */     this.skylight = sectionSkyLights;
/* 35 */     this.emitlight = sectionEmitLights;
/* 36 */     this.empty = sectionEmpty;
/* 37 */     this.hmap = hmap;
/* 38 */     this.biome = biome;
/* 39 */     this.biomeTemp = biomeTemp;
/* 40 */     this.biomeRain = biomeRain;
/*    */   }
/*    */   
/*    */   public int getX() {
/* 44 */     return this.x;
/*    */   }
/*    */   
/*    */   public int getZ() {
/* 48 */     return this.z;
/*    */   }
/*    */   
/*    */   public String getWorldName() {
/* 52 */     return this.worldname;
/*    */   }
/*    */   
/*    */   public final int getBlockTypeId(int x, int y, int z) {
/* 56 */     return this.blockids[y >> 4][(y & 0xF) << 8 | z << 4 | x];
/*    */   }
/*    */   
/*    */   public final int getBlockData(int x, int y, int z) {
/* 60 */     int off = (y & 0xF) << 7 | z << 3 | x >> 1;
/* 61 */     return this.blockdata[y >> 4][off] >> (x & 0x1) << 2 & 0xF;
/*    */   }
/*    */   
/*    */   public final int getBlockSkyLight(int x, int y, int z) {
/* 65 */     int off = (y & 0xF) << 7 | z << 3 | x >> 1;
/* 66 */     return this.skylight[y >> 4][off] >> (x & 0x1) << 2 & 0xF;
/*    */   }
/*    */   
/*    */   public final int getBlockEmittedLight(int x, int y, int z) {
/* 70 */     int off = (y & 0xF) << 7 | z << 3 | x >> 1;
/* 71 */     return this.emitlight[y >> 4][off] >> (x & 0x1) << 2 & 0xF;
/*    */   }
/*    */   
/*    */   public final int getHighestBlockYAt(int x, int z) {
/* 75 */     return this.hmap[z << 4 | x];
/*    */   }
/*    */   
/*    */   public final Biome getBiome(int x, int z) {
/* 79 */     return CraftBlock.biomeBaseToBiome(this.biome[z << 4 | x]);
/*    */   }
/*    */   
/*    */   public final double getRawBiomeTemperature(int x, int z) {
/* 83 */     return this.biomeTemp[z << 4 | x];
/*    */   }
/*    */   
/*    */   public final double getRawBiomeRainfall(int x, int z) {
/* 87 */     return this.biomeRain[z << 4 | x];
/*    */   }
/*    */   
/*    */   public final long getCaptureFullTime() {
/* 91 */     return this.captureFulltime;
/*    */   }
/*    */   
/*    */   public final boolean isSectionEmpty(int sy) {
/* 95 */     return this.empty[sy];
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\CraftChunkSnapshot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */