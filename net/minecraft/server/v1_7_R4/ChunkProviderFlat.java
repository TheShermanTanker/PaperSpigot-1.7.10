/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class ChunkProviderFlat
/*     */   implements IChunkProvider {
/*     */   private World a;
/*     */   private Random b;
/*  13 */   private final Block[] c = new Block[256];
/*  14 */   private final byte[] d = new byte[256];
/*     */   private final WorldGenFlatInfo e;
/*  16 */   private final List f = new ArrayList();
/*     */   private final boolean g;
/*     */   private final boolean h;
/*     */   private WorldGenLakes i;
/*     */   private WorldGenLakes j;
/*     */   
/*     */   public ChunkProviderFlat(World world, long i, boolean flag, String s) {
/*  23 */     this.a = world;
/*  24 */     this.b = new Random(i);
/*  25 */     this.e = WorldGenFlatInfo.a(s);
/*  26 */     if (flag) {
/*  27 */       Map map = this.e.b();
/*     */       
/*  29 */       if (world.paperSpigotConfig.generateVillage && map.containsKey("village")) {
/*  30 */         Map<String, String> map1 = (Map)map.get("village");
/*     */         
/*  32 */         if (!map1.containsKey("size")) {
/*  33 */           map1.put("size", "1");
/*     */         }
/*     */         
/*  36 */         this.f.add(new WorldGenVillage(map1));
/*     */       } 
/*     */       
/*  39 */       if (world.paperSpigotConfig.generateTemple && map.containsKey("biome_1")) {
/*  40 */         this.f.add(new WorldGenLargeFeature((Map)map.get("biome_1")));
/*     */       }
/*     */       
/*  43 */       if (world.paperSpigotConfig.generateMineshaft && map.containsKey("mineshaft")) {
/*  44 */         this.f.add(new WorldGenMineshaft((Map)map.get("mineshaft")));
/*     */       }
/*     */       
/*  47 */       if (world.paperSpigotConfig.generateStronghold && map.containsKey("stronghold")) {
/*  48 */         this.f.add(new WorldGenStronghold((Map)map.get("stronghold")));
/*     */       }
/*     */     } 
/*     */     
/*  52 */     this.g = this.e.b().containsKey("decoration");
/*  53 */     if (this.e.b().containsKey("lake")) {
/*  54 */       this.i = new WorldGenLakes(Blocks.STATIONARY_WATER);
/*     */     }
/*     */     
/*  57 */     if (this.e.b().containsKey("lava_lake")) {
/*  58 */       this.j = new WorldGenLakes(Blocks.STATIONARY_LAVA);
/*     */     }
/*     */     
/*  61 */     this.h = (world.paperSpigotConfig.generateDungeon && this.e.b().containsKey("dungeon"));
/*  62 */     Iterator<WorldGenFlatLayerInfo> iterator = this.e.c().iterator();
/*     */     
/*  64 */     while (iterator.hasNext()) {
/*  65 */       WorldGenFlatLayerInfo worldgenflatlayerinfo = iterator.next();
/*     */       
/*  67 */       for (int j = worldgenflatlayerinfo.d(); j < worldgenflatlayerinfo.d() + worldgenflatlayerinfo.a(); j++) {
/*  68 */         this.c[j] = worldgenflatlayerinfo.b();
/*  69 */         this.d[j] = (byte)worldgenflatlayerinfo.c();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Chunk getChunkAt(int i, int j) {
/*  75 */     return getOrCreateChunk(i, j);
/*     */   }
/*     */   
/*     */   public Chunk getOrCreateChunk(int i, int j) {
/*  79 */     Chunk chunk = new Chunk(this.a, i, j);
/*     */ 
/*     */ 
/*     */     
/*  83 */     for (int l = 0; l < this.c.length; l++) {
/*  84 */       Block block = this.c[l];
/*     */       
/*  86 */       if (block != null) {
/*  87 */         int m = l >> 4;
/*  88 */         ChunkSection chunksection = chunk.getSections()[m];
/*     */         
/*  90 */         if (chunksection == null) {
/*  91 */           chunksection = new ChunkSection(l, !this.a.worldProvider.g);
/*  92 */           chunk.getSections()[m] = chunksection;
/*     */         } 
/*     */         
/*  95 */         for (int i1 = 0; i1 < 16; i1++) {
/*  96 */           for (int j1 = 0; j1 < 16; j1++) {
/*  97 */             chunksection.setTypeId(i1, l & 0xF, j1, block);
/*  98 */             chunksection.setData(i1, l & 0xF, j1, this.d[l]);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     chunk.initLighting();
/* 105 */     BiomeBase[] abiomebase = this.a.getWorldChunkManager().getBiomeBlock((BiomeBase[])null, i * 16, j * 16, 16, 16);
/* 106 */     byte[] abyte = chunk.m();
/*     */     
/* 108 */     for (int k = 0; k < abyte.length; k++) {
/* 109 */       abyte[k] = (byte)(abiomebase[k]).id;
/*     */     }
/*     */     
/* 112 */     Iterator<WorldGenBase> iterator = this.f.iterator();
/*     */     
/* 114 */     while (iterator.hasNext()) {
/* 115 */       WorldGenBase worldgenbase = iterator.next();
/*     */       
/* 117 */       worldgenbase.a(this, this.a, i, j, (Block[])null);
/*     */     } 
/*     */     
/* 120 */     chunk.initLighting();
/* 121 */     return chunk;
/*     */   }
/*     */   
/*     */   public boolean isChunkLoaded(int i, int j) {
/* 125 */     return true;
/*     */   }
/*     */   
/*     */   public void getChunkAt(IChunkProvider ichunkprovider, int i, int j) {
/* 129 */     int k = i * 16;
/* 130 */     int l = j * 16;
/* 131 */     BiomeBase biomebase = this.a.getBiome(k + 16, l + 16);
/* 132 */     boolean flag = false;
/*     */     
/* 134 */     this.b.setSeed(this.a.getSeed());
/* 135 */     long i1 = this.b.nextLong() / 2L * 2L + 1L;
/* 136 */     long j1 = this.b.nextLong() / 2L * 2L + 1L;
/*     */     
/* 138 */     this.b.setSeed(i * i1 + j * j1 ^ this.a.getSeed());
/* 139 */     Iterator<StructureGenerator> iterator = this.f.iterator();
/*     */     
/* 141 */     while (iterator.hasNext()) {
/* 142 */       StructureGenerator structuregenerator = iterator.next();
/* 143 */       boolean flag1 = structuregenerator.a(this.a, this.b, i, j);
/*     */       
/* 145 */       if (structuregenerator instanceof WorldGenVillage) {
/* 146 */         flag |= flag1;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     if (this.i != null && !flag && this.b.nextInt(4) == 0) {
/* 155 */       int l1 = k + this.b.nextInt(16) + 8;
/* 156 */       int k1 = this.b.nextInt(256);
/* 157 */       int i2 = l + this.b.nextInt(16) + 8;
/* 158 */       this.i.generate(this.a, this.b, l1, k1, i2);
/*     */     } 
/*     */     
/* 161 */     if (this.j != null && !flag && this.b.nextInt(8) == 0) {
/* 162 */       int l1 = k + this.b.nextInt(16) + 8;
/* 163 */       int k1 = this.b.nextInt(this.b.nextInt(248) + 8);
/* 164 */       int i2 = l + this.b.nextInt(16) + 8;
/* 165 */       if (k1 < 63 || this.b.nextInt(10) == 0) {
/* 166 */         this.j.generate(this.a, this.b, l1, k1, i2);
/*     */       }
/*     */     } 
/*     */     
/* 170 */     if (this.h) {
/* 171 */       for (int l1 = 0; l1 < 8; l1++) {
/* 172 */         int k1 = k + this.b.nextInt(16) + 8;
/* 173 */         int i2 = this.b.nextInt(256);
/* 174 */         int j2 = l + this.b.nextInt(16) + 8;
/*     */         
/* 176 */         (new WorldGenDungeons()).generate(this.a, this.b, k1, i2, j2);
/*     */       } 
/*     */     }
/*     */     
/* 180 */     if (this.g) {
/* 181 */       biomebase.a(this.a, this.b, k, l);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
/* 186 */     return true;
/*     */   }
/*     */   
/*     */   public void c() {}
/*     */   
/*     */   public boolean unloadChunks() {
/* 192 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canSave() {
/* 196 */     return true;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 200 */     return "FlatLevelSource";
/*     */   }
/*     */   
/*     */   public List getMobsFor(EnumCreatureType enumcreaturetype, int i, int j, int k) {
/* 204 */     BiomeBase biomebase = this.a.getBiome(i, k);
/*     */     
/* 206 */     return biomebase.getMobs(enumcreaturetype);
/*     */   }
/*     */   
/*     */   public ChunkPosition findNearestMapFeature(World world, String s, int i, int j, int k) {
/* 210 */     if ("Stronghold".equals(s)) {
/* 211 */       Iterator<StructureGenerator> iterator = this.f.iterator();
/*     */       
/* 213 */       while (iterator.hasNext()) {
/* 214 */         StructureGenerator structuregenerator = iterator.next();
/*     */         
/* 216 */         if (structuregenerator instanceof WorldGenStronghold) {
/* 217 */           return structuregenerator.getNearestGeneratedFeature(world, i, j, k);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 222 */     return null;
/*     */   }
/*     */   
/*     */   public int getLoadedChunks() {
/* 226 */     return 0;
/*     */   }
/*     */   
/*     */   public void recreateStructures(int i, int j) {
/* 230 */     Iterator<StructureGenerator> iterator = this.f.iterator();
/*     */     
/* 232 */     while (iterator.hasNext()) {
/* 233 */       StructureGenerator structuregenerator = iterator.next();
/*     */       
/* 235 */       structuregenerator.a(this, this.a, i, j, (Block[])null);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChunkProviderFlat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */