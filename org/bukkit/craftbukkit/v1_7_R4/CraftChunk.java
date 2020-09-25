/*     */ package org.bukkit.craftbukkit.v1_7_R4;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.server.v1_7_R4.BiomeBase;
/*     */ import net.minecraft.server.v1_7_R4.Chunk;
/*     */ import net.minecraft.server.v1_7_R4.ChunkPosition;
/*     */ import net.minecraft.server.v1_7_R4.ChunkSection;
/*     */ import net.minecraft.server.v1_7_R4.Entity;
/*     */ import net.minecraft.server.v1_7_R4.WorldChunkManager;
/*     */ import net.minecraft.server.v1_7_R4.WorldServer;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.ChunkSnapshot;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.block.CraftBlock;
/*     */ import org.bukkit.entity.Entity;
/*     */ 
/*     */ public class CraftChunk
/*     */   implements Chunk {
/*     */   private WeakReference<Chunk> weakChunk;
/*     */   private final WorldServer worldServer;
/*     */   private final int x;
/*     */   private final int z;
/*  26 */   private static final byte[] emptyData = new byte[2048];
/*  27 */   private static final short[] emptyBlockIDs = new short[4096];
/*  28 */   private static final byte[] emptySkyLight = new byte[2048];
/*     */   
/*     */   public CraftChunk(Chunk chunk) {
/*  31 */     if (!(chunk instanceof net.minecraft.server.v1_7_R4.EmptyChunk)) {
/*  32 */       this.weakChunk = new WeakReference<Chunk>(chunk);
/*     */     }
/*     */     
/*  35 */     this.worldServer = (WorldServer)(getHandle()).world;
/*  36 */     this.x = (getHandle()).locX;
/*  37 */     this.z = (getHandle()).locZ;
/*     */   }
/*     */   
/*     */   public World getWorld() {
/*  41 */     return this.worldServer.getWorld();
/*     */   }
/*     */   
/*     */   public CraftWorld getCraftWorld() {
/*  45 */     return (CraftWorld)getWorld();
/*     */   }
/*     */   
/*     */   public Chunk getHandle() {
/*  49 */     Chunk c = this.weakChunk.get();
/*     */     
/*  51 */     if (c == null) {
/*  52 */       c = this.worldServer.getChunkAt(this.x, this.z);
/*     */       
/*  54 */       if (!(c instanceof net.minecraft.server.v1_7_R4.EmptyChunk)) {
/*  55 */         this.weakChunk = new WeakReference<Chunk>(c);
/*     */       }
/*     */     } 
/*     */     
/*  59 */     return c;
/*     */   }
/*     */   
/*     */   void breakLink() {
/*  63 */     this.weakChunk.clear();
/*     */   }
/*     */   
/*     */   public int getX() {
/*  67 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getZ() {
/*  71 */     return this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  76 */     return "CraftChunk{x=" + getX() + "z=" + getZ() + '}';
/*     */   }
/*     */   
/*     */   public Block getBlock(int x, int y, int z) {
/*  80 */     return (Block)new CraftBlock(this, getX() << 4 | x & 0xF, y & 0xFF, getZ() << 4 | z & 0xF);
/*     */   }
/*     */   
/*     */   public Entity[] getEntities() {
/*  84 */     int count = 0, index = 0;
/*  85 */     Chunk chunk = getHandle();
/*     */     
/*  87 */     for (int i = 0; i < 16; i++) {
/*  88 */       count += chunk.entitySlices[i].size();
/*     */     }
/*     */     
/*  91 */     Entity[] entities = new Entity[count];
/*     */     
/*  93 */     for (int j = 0; j < 16; j++) {
/*  94 */       for (Object obj : chunk.entitySlices[j].toArray()) {
/*  95 */         if (obj instanceof Entity)
/*     */         {
/*     */ 
/*     */           
/*  99 */           entities[index++] = (Entity)((Entity)obj).getBukkitEntity();
/*     */         }
/*     */       } 
/*     */     } 
/* 103 */     return entities;
/*     */   }
/*     */   
/*     */   public BlockState[] getTileEntities() {
/* 107 */     int index = 0;
/* 108 */     Chunk chunk = getHandle();
/* 109 */     BlockState[] entities = new BlockState[chunk.tileEntities.size()];
/*     */     
/* 111 */     for (Object obj : chunk.tileEntities.keySet().toArray()) {
/* 112 */       if (obj instanceof ChunkPosition) {
/*     */ 
/*     */ 
/*     */         
/* 116 */         ChunkPosition position = (ChunkPosition)obj;
/* 117 */         entities[index++] = this.worldServer.getWorld().getBlockAt(position.x + (chunk.locX << 4), position.y, position.z + (chunk.locZ << 4)).getState();
/*     */       } 
/* 119 */     }  return entities;
/*     */   }
/*     */   
/*     */   public boolean isLoaded() {
/* 123 */     return getWorld().isChunkLoaded(this);
/*     */   }
/*     */   
/*     */   public boolean load() {
/* 127 */     return getWorld().loadChunk(getX(), getZ(), true);
/*     */   }
/*     */   
/*     */   public boolean load(boolean generate) {
/* 131 */     return getWorld().loadChunk(getX(), getZ(), generate);
/*     */   }
/*     */   
/*     */   public boolean unload() {
/* 135 */     return getWorld().unloadChunk(getX(), getZ());
/*     */   }
/*     */   
/*     */   public boolean unload(boolean save) {
/* 139 */     return getWorld().unloadChunk(getX(), getZ(), save);
/*     */   }
/*     */   
/*     */   public boolean unload(boolean save, boolean safe) {
/* 143 */     return getWorld().unloadChunk(getX(), getZ(), save, safe);
/*     */   }
/*     */   
/*     */   public ChunkSnapshot getChunkSnapshot() {
/* 147 */     return getChunkSnapshot(true, false, false);
/*     */   }
/*     */   
/*     */   public ChunkSnapshot getChunkSnapshot(boolean includeMaxBlockY, boolean includeBiome, boolean includeBiomeTempRain) {
/* 151 */     Chunk chunk = getHandle();
/*     */     
/* 153 */     ChunkSection[] cs = chunk.getSections();
/* 154 */     short[][] sectionBlockIDs = new short[cs.length][];
/* 155 */     byte[][] sectionBlockData = new byte[cs.length][];
/* 156 */     byte[][] sectionSkyLights = new byte[cs.length][];
/* 157 */     byte[][] sectionEmitLights = new byte[cs.length][];
/* 158 */     boolean[] sectionEmpty = new boolean[cs.length];
/*     */     
/* 160 */     for (int i = 0; i < cs.length; i++) {
/* 161 */       if (cs[i] == null) {
/* 162 */         sectionBlockIDs[i] = emptyBlockIDs;
/* 163 */         sectionBlockData[i] = emptyData;
/* 164 */         sectionSkyLights[i] = emptySkyLight;
/* 165 */         sectionEmitLights[i] = emptyData;
/* 166 */         sectionEmpty[i] = true;
/*     */       } else {
/* 168 */         short[] blockids = new short[4096];
/* 169 */         byte[] baseids = cs[i].getIdArray();
/*     */ 
/*     */         
/* 172 */         for (int j = 0; j < 4096; j++) {
/* 173 */           blockids[j] = (short)(baseids[j] & 0xFF);
/*     */         }
/*     */         
/* 176 */         if (cs[i].getExtendedIdArray() != null) {
/* 177 */           byte[] extids = (cs[i].getExtendedIdArray()).a;
/*     */           
/* 179 */           for (int k = 0; k < 2048; k++) {
/* 180 */             short b = (short)(extids[k] & 0xFF);
/*     */             
/* 182 */             if (b != 0) {
/*     */ 
/*     */ 
/*     */               
/* 186 */               blockids[k << 1] = (short)(blockids[k << 1] | (b & 0xF) << 8);
/* 187 */               blockids[(k << 1) + 1] = (short)(blockids[(k << 1) + 1] | (b & 0xF0) << 4);
/*     */             } 
/*     */           } 
/*     */         } 
/* 191 */         sectionBlockIDs[i] = blockids;
/*     */ 
/*     */         
/* 194 */         sectionBlockData[i] = new byte[2048];
/* 195 */         System.arraycopy((cs[i].getDataArray()).a, 0, sectionBlockData[i], 0, 2048);
/* 196 */         if (cs[i].getSkyLightArray() == null) {
/* 197 */           sectionSkyLights[i] = emptyData;
/*     */         } else {
/* 199 */           sectionSkyLights[i] = new byte[2048];
/* 200 */           System.arraycopy((cs[i].getSkyLightArray()).a, 0, sectionSkyLights[i], 0, 2048);
/*     */         } 
/* 202 */         sectionEmitLights[i] = new byte[2048];
/* 203 */         System.arraycopy((cs[i].getEmittedLightArray()).a, 0, sectionEmitLights[i], 0, 2048);
/*     */       } 
/*     */     } 
/*     */     
/* 207 */     int[] hmap = null;
/*     */     
/* 209 */     if (includeMaxBlockY) {
/* 210 */       hmap = new int[256];
/* 211 */       System.arraycopy(chunk.heightMap, 0, hmap, 0, 256);
/*     */     } 
/*     */     
/* 214 */     BiomeBase[] biome = null;
/* 215 */     double[] biomeTemp = null;
/* 216 */     double[] biomeRain = null;
/*     */     
/* 218 */     if (includeBiome || includeBiomeTempRain) {
/* 219 */       WorldChunkManager wcm = chunk.world.getWorldChunkManager();
/*     */       
/* 221 */       if (includeBiome) {
/* 222 */         biome = new BiomeBase[256];
/* 223 */         for (int j = 0; j < 256; j++) {
/* 224 */           biome[j] = chunk.getBiome(j & 0xF, j >> 4, wcm);
/*     */         }
/*     */       } 
/*     */       
/* 228 */       if (includeBiomeTempRain) {
/* 229 */         biomeTemp = new double[256];
/* 230 */         biomeRain = new double[256];
/* 231 */         float[] dat = getTemperatures(wcm, getX() << 4, getZ() << 4);
/*     */         int j;
/* 233 */         for (j = 0; j < 256; j++) {
/* 234 */           biomeTemp[j] = dat[j];
/*     */         }
/*     */         
/* 237 */         dat = wcm.getWetness(null, getX() << 4, getZ() << 4, 16, 16);
/*     */         
/* 239 */         for (j = 0; j < 256; j++) {
/* 240 */           biomeRain[j] = dat[j];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 245 */     World world = getWorld();
/* 246 */     return new CraftChunkSnapshot(getX(), getZ(), world.getName(), world.getFullTime(), sectionBlockIDs, sectionBlockData, sectionSkyLights, sectionEmitLights, sectionEmpty, hmap, biome, biomeTemp, biomeRain);
/*     */   }
/*     */   
/*     */   public static ChunkSnapshot getEmptyChunkSnapshot(int x, int z, CraftWorld world, boolean includeBiome, boolean includeBiomeTempRain) {
/* 250 */     BiomeBase[] biome = null;
/* 251 */     double[] biomeTemp = null;
/* 252 */     double[] biomeRain = null;
/*     */     
/* 254 */     if (includeBiome || includeBiomeTempRain) {
/* 255 */       WorldChunkManager wcm = world.getHandle().getWorldChunkManager();
/*     */       
/* 257 */       if (includeBiome) {
/* 258 */         biome = new BiomeBase[256];
/* 259 */         for (int j = 0; j < 256; j++) {
/* 260 */           biome[j] = world.getHandle().getBiome((x << 4) + (j & 0xF), (z << 4) + (j >> 4));
/*     */         }
/*     */       } 
/*     */       
/* 264 */       if (includeBiomeTempRain) {
/* 265 */         biomeTemp = new double[256];
/* 266 */         biomeRain = new double[256];
/* 267 */         float[] dat = getTemperatures(wcm, x << 4, z << 4);
/*     */         int j;
/* 269 */         for (j = 0; j < 256; j++) {
/* 270 */           biomeTemp[j] = dat[j];
/*     */         }
/*     */         
/* 273 */         dat = wcm.getWetness(null, x << 4, z << 4, 16, 16);
/*     */         
/* 275 */         for (j = 0; j < 256; j++) {
/* 276 */           biomeRain[j] = dat[j];
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 282 */     int hSection = world.getMaxHeight() >> 4;
/* 283 */     short[][] blockIDs = new short[hSection][];
/* 284 */     byte[][] skyLight = new byte[hSection][];
/* 285 */     byte[][] emitLight = new byte[hSection][];
/* 286 */     byte[][] blockData = new byte[hSection][];
/* 287 */     boolean[] empty = new boolean[hSection];
/*     */     
/* 289 */     for (int i = 0; i < hSection; i++) {
/* 290 */       blockIDs[i] = emptyBlockIDs;
/* 291 */       skyLight[i] = emptySkyLight;
/* 292 */       emitLight[i] = emptyData;
/* 293 */       blockData[i] = emptyData;
/* 294 */       empty[i] = true;
/*     */     } 
/*     */     
/* 297 */     return new CraftChunkSnapshot(x, z, world.getName(), world.getFullTime(), blockIDs, blockData, skyLight, emitLight, empty, new int[256], biome, biomeTemp, biomeRain);
/*     */   }
/*     */   
/*     */   private static float[] getTemperatures(WorldChunkManager chunkmanager, int chunkX, int chunkZ) {
/* 301 */     BiomeBase[] biomes = chunkmanager.getBiomes(null, chunkX, chunkZ, 16, 16);
/* 302 */     float[] temps = new float[biomes.length];
/*     */     
/* 304 */     for (int i = 0; i < biomes.length; i++) {
/* 305 */       float temp = (biomes[i]).temperature;
/*     */       
/* 307 */       if (temp > 1.0F) {
/* 308 */         temp = 1.0F;
/*     */       }
/*     */       
/* 311 */       temps[i] = temp;
/*     */     } 
/*     */     
/* 314 */     return temps;
/*     */   }
/*     */   
/*     */   static {
/* 318 */     Arrays.fill(emptySkyLight, (byte)-1);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\CraftChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */