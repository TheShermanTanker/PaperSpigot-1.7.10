/*     */ package org.bukkit.craftbukkit.v1_7_R4.generator;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.v1_7_R4.BiomeBase;
/*     */ import net.minecraft.server.v1_7_R4.Chunk;
/*     */ import net.minecraft.server.v1_7_R4.ChunkPosition;
/*     */ import net.minecraft.server.v1_7_R4.ChunkSection;
/*     */ import net.minecraft.server.v1_7_R4.EnumCreatureType;
/*     */ import net.minecraft.server.v1_7_R4.IChunkProvider;
/*     */ import net.minecraft.server.v1_7_R4.IProgressUpdate;
/*     */ import net.minecraft.server.v1_7_R4.World;
/*     */ import net.minecraft.server.v1_7_R4.WorldGenStronghold;
/*     */ import net.minecraft.server.v1_7_R4.WorldServer;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Biome;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.block.CraftBlock;
/*     */ import org.bukkit.generator.BlockPopulator;
/*     */ import org.bukkit.generator.ChunkGenerator;
/*     */ 
/*     */ public class CustomChunkGenerator
/*     */   extends InternalChunkGenerator {
/*     */   private final ChunkGenerator generator;
/*     */   private final WorldServer world;
/*     */   private final Random random;
/*  26 */   private final WorldGenStronghold strongholdGen = new WorldGenStronghold();
/*     */   
/*     */   private static class CustomBiomeGrid implements ChunkGenerator.BiomeGrid {
/*     */     BiomeBase[] biome;
/*     */     
/*     */     public Biome getBiome(int x, int z) {
/*  32 */       return CraftBlock.biomeBaseToBiome(this.biome[z << 4 | x]);
/*     */     }
/*     */     private CustomBiomeGrid() {}
/*     */     public void setBiome(int x, int z, Biome bio) {
/*  36 */       this.biome[z << 4 | x] = CraftBlock.biomeToBiomeBase(bio);
/*     */     }
/*     */   }
/*     */   
/*     */   public CustomChunkGenerator(World world, long seed, ChunkGenerator generator) {
/*  41 */     this.world = (WorldServer)world;
/*  42 */     this.generator = generator;
/*     */     
/*  44 */     this.random = new Random(seed);
/*     */   }
/*     */   
/*     */   public boolean isChunkLoaded(int x, int z) {
/*  48 */     return true;
/*     */   }
/*     */   public Chunk getOrCreateChunk(int x, int z) {
/*     */     Chunk chunk;
/*  52 */     this.random.setSeed(x * 341873128712L + z * 132897987541L);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     CustomBiomeGrid biomegrid = new CustomBiomeGrid();
/*  58 */     biomegrid.biome = new BiomeBase[256];
/*  59 */     this.world.getWorldChunkManager().getBiomeBlock(biomegrid.biome, x << 4, z << 4, 16, 16);
/*     */ 
/*     */     
/*  62 */     short[][] xbtypes = this.generator.generateExtBlockSections((World)this.world.getWorld(), this.random, x, z, biomegrid);
/*  63 */     if (xbtypes != null) {
/*  64 */       chunk = new Chunk((World)this.world, x, z);
/*     */       
/*  66 */       ChunkSection[] csect = chunk.getSections();
/*  67 */       int scnt = Math.min(csect.length, xbtypes.length);
/*     */ 
/*     */       
/*  70 */       for (int sec = 0; sec < scnt; sec++) {
/*  71 */         if (xbtypes[sec] != null) {
/*     */ 
/*     */           
/*  74 */           byte[] secBlkID = new byte[4096];
/*  75 */           byte[] secExtBlkID = null;
/*  76 */           short[] bdata = xbtypes[sec];
/*     */           
/*  78 */           for (int k = 0, j = 0; k < bdata.length; k += 2, j++) {
/*  79 */             short b1 = bdata[k];
/*  80 */             short b2 = bdata[k + 1];
/*  81 */             byte extb = (byte)(b1 >> 8 | b2 >> 4 & 0xF0);
/*     */             
/*  83 */             secBlkID[k] = (byte)b1;
/*  84 */             secBlkID[k + 1] = (byte)b2;
/*     */             
/*  86 */             if (extb != 0) {
/*  87 */               if (secExtBlkID == null) {
/*  88 */                 secExtBlkID = new byte[2048];
/*     */               }
/*  90 */               secExtBlkID[j] = extb;
/*     */             } 
/*     */           } 
/*     */           
/*  94 */           csect[sec] = new ChunkSection(sec << 4, true, secBlkID, secExtBlkID);
/*     */         } 
/*     */       } 
/*     */     } else {
/*  98 */       byte[][] btypes = this.generator.generateBlockSections((World)this.world.getWorld(), this.random, x, z, biomegrid);
/*     */       
/* 100 */       if (btypes != null) {
/* 101 */         chunk = new Chunk((World)this.world, x, z);
/*     */         
/* 103 */         ChunkSection[] csect = chunk.getSections();
/* 104 */         int scnt = Math.min(csect.length, btypes.length);
/*     */         
/* 106 */         for (int sec = 0; sec < scnt; sec++) {
/* 107 */           if (btypes[sec] != null)
/*     */           {
/*     */             
/* 110 */             csect[sec] = new ChunkSection(sec << 4, true, btypes[sec], null);
/*     */           }
/*     */         } 
/*     */       } else {
/*     */         
/* 115 */         byte[] types = this.generator.generate((World)this.world.getWorld(), this.random, x, z);
/* 116 */         int ydim = types.length / 256;
/* 117 */         int scnt = ydim / 16;
/*     */         
/* 119 */         chunk = new Chunk((World)this.world, x, z);
/*     */         
/* 121 */         ChunkSection[] csect = chunk.getSections();
/*     */         
/* 123 */         scnt = Math.min(scnt, csect.length);
/*     */         
/* 125 */         for (int sec = 0; sec < scnt; sec++) {
/* 126 */           ChunkSection cs = null;
/* 127 */           byte[] csbytes = null;
/*     */           
/* 129 */           for (int cy = 0; cy < 16; cy++) {
/* 130 */             int cyoff = cy | sec << 4;
/*     */             
/* 132 */             for (int cx = 0; cx < 16; cx++) {
/* 133 */               int cxyoff = cx * ydim * 16 + cyoff;
/*     */               
/* 135 */               for (int cz = 0; cz < 16; cz++) {
/* 136 */                 byte blk = types[cxyoff + cz * ydim];
/*     */                 
/* 138 */                 if (blk != 0) {
/* 139 */                   if (cs == null) {
/* 140 */                     cs = csect[sec] = new ChunkSection(sec << 4, true);
/* 141 */                     csbytes = cs.getIdArray();
/*     */                   } 
/* 143 */                   csbytes[cy << 8 | cz << 4 | cx] = blk;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 149 */           if (cs != null) {
/* 150 */             cs.recalcBlockCounts();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     byte[] biomeIndex = chunk.m();
/* 157 */     for (int i = 0; i < biomeIndex.length; i++) {
/* 158 */       biomeIndex[i] = (byte)((biomegrid.biome[i]).id & 0xFF);
/*     */     }
/*     */     
/* 161 */     chunk.initLighting();
/*     */     
/* 163 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getChunkAt(IChunkProvider icp, int i, int i1) {}
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean bln, IProgressUpdate ipu) {
/* 171 */     return true;
/*     */   }
/*     */   
/*     */   public boolean unloadChunks() {
/* 175 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canSave() {
/* 179 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generate(World world, Random random, int x, int z) {
/* 184 */     return this.generator.generate(world, random, x, z);
/*     */   }
/*     */   
/*     */   public byte[][] generateBlockSections(World world, Random random, int x, int z, ChunkGenerator.BiomeGrid biomes) {
/* 188 */     return this.generator.generateBlockSections(world, random, x, z, biomes);
/*     */   }
/*     */   
/*     */   public short[][] generateExtBlockSections(World world, Random random, int x, int z, ChunkGenerator.BiomeGrid biomes) {
/* 192 */     return this.generator.generateExtBlockSections(world, random, x, z, biomes);
/*     */   }
/*     */   
/*     */   public Chunk getChunkAt(int x, int z) {
/* 196 */     return getOrCreateChunk(x, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSpawn(World world, int x, int z) {
/* 201 */     return this.generator.canSpawn(world, x, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BlockPopulator> getDefaultPopulators(World world) {
/* 206 */     return this.generator.getDefaultPopulators(world);
/*     */   }
/*     */   
/*     */   public List<?> getMobsFor(EnumCreatureType type, int x, int y, int z) {
/* 210 */     BiomeBase biomebase = this.world.getBiome(x, z);
/*     */     
/* 212 */     return (biomebase == null) ? null : biomebase.getMobs(type);
/*     */   }
/*     */   
/*     */   public ChunkPosition findNearestMapFeature(World world, String type, int x, int y, int z) {
/* 216 */     return ("Stronghold".equals(type) && this.strongholdGen != null) ? this.strongholdGen.getNearestGeneratedFeature(world, x, y, z) : null;
/*     */   }
/*     */   
/*     */   public void recreateStructures(int i, int j) {}
/*     */   
/*     */   public int getLoadedChunks() {
/* 222 */     return 0;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 226 */     return "CustomChunkGenerator";
/*     */   }
/*     */   
/*     */   public void c() {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\generator\CustomChunkGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */