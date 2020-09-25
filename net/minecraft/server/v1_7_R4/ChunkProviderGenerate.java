/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class ChunkProviderGenerate
/*     */   implements IChunkProvider {
/*     */   private Random i;
/*     */   private NoiseGeneratorOctaves j;
/*     */   private NoiseGeneratorOctaves k;
/*     */   private NoiseGeneratorOctaves l;
/*     */   private NoiseGenerator3 m;
/*     */   public NoiseGeneratorOctaves a;
/*     */   public NoiseGeneratorOctaves b;
/*     */   public NoiseGeneratorOctaves c;
/*     */   private World n;
/*     */   private final boolean o;
/*     */   private WorldType p;
/*     */   private final double[] q;
/*     */   private final float[] r;
/*  21 */   private double[] s = new double[256];
/*  22 */   private WorldGenBase t = new WorldGenCaves();
/*  23 */   private WorldGenStronghold u = new WorldGenStronghold();
/*  24 */   private WorldGenVillage v = new WorldGenVillage();
/*  25 */   private WorldGenMineshaft w = new WorldGenMineshaft();
/*  26 */   private WorldGenLargeFeature x = new WorldGenLargeFeature();
/*  27 */   private WorldGenBase y = new WorldGenCanyon();
/*     */   private BiomeBase[] z;
/*     */   double[] d;
/*     */   double[] e;
/*     */   double[] f;
/*     */   double[] g;
/*  33 */   int[][] h = new int[32][32];
/*     */   
/*     */   public ChunkProviderGenerate(World world, long i, boolean flag) {
/*  36 */     this.n = world;
/*  37 */     this.o = flag;
/*  38 */     this.p = world.getWorldData().getType();
/*  39 */     this.i = new Random(i);
/*  40 */     this.j = new NoiseGeneratorOctaves(this.i, 16);
/*  41 */     this.k = new NoiseGeneratorOctaves(this.i, 16);
/*  42 */     this.l = new NoiseGeneratorOctaves(this.i, 8);
/*  43 */     this.m = new NoiseGenerator3(this.i, 4);
/*  44 */     this.a = new NoiseGeneratorOctaves(this.i, 10);
/*  45 */     this.b = new NoiseGeneratorOctaves(this.i, 16);
/*  46 */     this.c = new NoiseGeneratorOctaves(this.i, 8);
/*  47 */     this.q = new double[825];
/*  48 */     this.r = new float[25];
/*     */     
/*  50 */     for (int j = -2; j <= 2; j++) {
/*  51 */       for (int k = -2; k <= 2; k++) {
/*  52 */         float f = 10.0F / MathHelper.c((j * j + k * k) + 0.2F);
/*     */         
/*  54 */         this.r[j + 2 + (k + 2) * 5] = f;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(int i, int j, Block[] ablock) {
/*  60 */     byte b0 = 63;
/*     */     
/*  62 */     this.z = this.n.getWorldChunkManager().getBiomes(this.z, i * 4 - 2, j * 4 - 2, 10, 10);
/*  63 */     a(i * 4, 0, j * 4);
/*     */     
/*  65 */     for (int k = 0; k < 4; k++) {
/*  66 */       int l = k * 5;
/*  67 */       int i1 = (k + 1) * 5;
/*     */       
/*  69 */       for (int j1 = 0; j1 < 4; j1++) {
/*  70 */         int k1 = (l + j1) * 33;
/*  71 */         int l1 = (l + j1 + 1) * 33;
/*  72 */         int i2 = (i1 + j1) * 33;
/*  73 */         int j2 = (i1 + j1 + 1) * 33;
/*     */         
/*  75 */         for (int k2 = 0; k2 < 32; k2++) {
/*  76 */           double d0 = 0.125D;
/*  77 */           double d1 = this.q[k1 + k2];
/*  78 */           double d2 = this.q[l1 + k2];
/*  79 */           double d3 = this.q[i2 + k2];
/*  80 */           double d4 = this.q[j2 + k2];
/*  81 */           double d5 = (this.q[k1 + k2 + 1] - d1) * d0;
/*  82 */           double d6 = (this.q[l1 + k2 + 1] - d2) * d0;
/*  83 */           double d7 = (this.q[i2 + k2 + 1] - d3) * d0;
/*  84 */           double d8 = (this.q[j2 + k2 + 1] - d4) * d0;
/*     */           
/*  86 */           for (int l2 = 0; l2 < 8; l2++) {
/*  87 */             double d9 = 0.25D;
/*  88 */             double d10 = d1;
/*  89 */             double d11 = d2;
/*  90 */             double d12 = (d3 - d1) * d9;
/*  91 */             double d13 = (d4 - d2) * d9;
/*     */             
/*  93 */             for (int i3 = 0; i3 < 4; i3++) {
/*  94 */               int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
/*  95 */               short short1 = 256;
/*     */               
/*  97 */               j3 -= short1;
/*  98 */               double d14 = 0.25D;
/*  99 */               double d15 = (d11 - d10) * d14;
/* 100 */               double d16 = d10 - d15;
/*     */               
/* 102 */               for (int k3 = 0; k3 < 4; k3++) {
/* 103 */                 if ((d16 += d15) > 0.0D) {
/* 104 */                   ablock[j3 += short1] = Blocks.STONE;
/* 105 */                 } else if (k2 * 8 + l2 < b0) {
/* 106 */                   ablock[j3 += short1] = Blocks.STATIONARY_WATER;
/*     */                 } else {
/* 108 */                   ablock[j3 += short1] = null;
/*     */                 } 
/*     */               } 
/*     */               
/* 112 */               d10 += d12;
/* 113 */               d11 += d13;
/*     */             } 
/*     */             
/* 116 */             d1 += d5;
/* 117 */             d2 += d6;
/* 118 */             d3 += d7;
/* 119 */             d4 += d8;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(int i, int j, Block[] ablock, byte[] abyte, BiomeBase[] abiomebase) {
/* 127 */     double d0 = 0.03125D;
/*     */     
/* 129 */     this.s = this.m.a(this.s, (i * 16), (j * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);
/*     */     
/* 131 */     for (int k = 0; k < 16; k++) {
/* 132 */       for (int l = 0; l < 16; l++) {
/* 133 */         BiomeBase biomebase = abiomebase[l + k * 16];
/*     */         
/* 135 */         biomebase.a(this.n, this.i, ablock, abyte, i * 16 + k, j * 16 + l, this.s[l + k * 16]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Chunk getChunkAt(int i, int j) {
/* 141 */     return getOrCreateChunk(i, j);
/*     */   }
/*     */   
/*     */   public Chunk getOrCreateChunk(int i, int j) {
/* 145 */     this.i.setSeed(i * 341873128712L + j * 132897987541L);
/* 146 */     Block[] ablock = new Block[65536];
/* 147 */     byte[] abyte = new byte[65536];
/*     */     
/* 149 */     a(i, j, ablock);
/* 150 */     this.z = this.n.getWorldChunkManager().getBiomeBlock(this.z, i * 16, j * 16, 16, 16);
/* 151 */     a(i, j, ablock, abyte, this.z);
/*     */     
/* 153 */     if (this.n.paperSpigotConfig.generateCaves) this.t.a(this, this.n, i, j, ablock); 
/* 154 */     if (this.n.paperSpigotConfig.generateCanyon) this.y.a(this, this.n, i, j, ablock);
/*     */     
/* 156 */     if (this.o) {
/*     */       
/* 158 */       if (this.n.paperSpigotConfig.generateMineshaft) this.w.a(this, this.n, i, j, ablock); 
/* 159 */       if (this.n.paperSpigotConfig.generateVillage) this.v.a(this, this.n, i, j, ablock); 
/* 160 */       if (this.n.paperSpigotConfig.generateStronghold) this.u.a(this, this.n, i, j, ablock); 
/* 161 */       if (this.n.paperSpigotConfig.generateTemple) this.x.a(this, this.n, i, j, ablock);
/*     */     
/*     */     } 
/*     */     
/* 165 */     Chunk chunk = new Chunk(this.n, ablock, abyte, i, j);
/* 166 */     byte[] abyte1 = chunk.m();
/*     */     
/* 168 */     for (int k = 0; k < abyte1.length; k++) {
/* 169 */       abyte1[k] = (byte)(this.z[k]).id;
/*     */     }
/*     */     
/* 172 */     chunk.initLighting();
/* 173 */     return chunk;
/*     */   }
/*     */   
/*     */   private void a(int i, int j, int k) {
/* 177 */     double d0 = 684.412D;
/* 178 */     double d1 = 684.412D;
/* 179 */     double d2 = 512.0D;
/* 180 */     double d3 = 512.0D;
/*     */     
/* 182 */     this.g = this.b.a(this.g, i, k, 5, 5, 200.0D, 200.0D, 0.5D);
/* 183 */     this.d = this.l.a(this.d, i, j, k, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
/* 184 */     this.e = this.j.a(this.e, i, j, k, 5, 33, 5, 684.412D, 684.412D, 684.412D);
/* 185 */     this.f = this.k.a(this.f, i, j, k, 5, 33, 5, 684.412D, 684.412D, 684.412D);
/* 186 */     boolean flag = false;
/* 187 */     boolean flag1 = false;
/* 188 */     int l = 0;
/* 189 */     int i1 = 0;
/* 190 */     double d4 = 8.5D;
/*     */     
/* 192 */     for (int j1 = 0; j1 < 5; j1++) {
/* 193 */       for (int k1 = 0; k1 < 5; k1++) {
/* 194 */         float f = 0.0F;
/* 195 */         float f1 = 0.0F;
/* 196 */         float f2 = 0.0F;
/* 197 */         byte b0 = 2;
/* 198 */         BiomeBase biomebase = this.z[j1 + 2 + (k1 + 2) * 10];
/*     */         
/* 200 */         for (int l1 = -b0; l1 <= b0; l1++) {
/* 201 */           for (int i2 = -b0; i2 <= b0; i2++) {
/* 202 */             BiomeBase biomebase1 = this.z[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
/* 203 */             float f3 = biomebase1.am;
/* 204 */             float f4 = biomebase1.an;
/*     */             
/* 206 */             if (this.p == WorldType.AMPLIFIED && f3 > 0.0F) {
/* 207 */               f3 = 1.0F + f3 * 2.0F;
/* 208 */               f4 = 1.0F + f4 * 4.0F;
/*     */             } 
/*     */             
/* 211 */             float f5 = this.r[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);
/*     */             
/* 213 */             if (biomebase1.am > biomebase.am) {
/* 214 */               f5 /= 2.0F;
/*     */             }
/*     */             
/* 217 */             f += f4 * f5;
/* 218 */             f1 += f3 * f5;
/* 219 */             f2 += f5;
/*     */           } 
/*     */         } 
/*     */         
/* 223 */         f /= f2;
/* 224 */         f1 /= f2;
/* 225 */         f = f * 0.9F + 0.1F;
/* 226 */         f1 = (f1 * 4.0F - 1.0F) / 8.0F;
/* 227 */         double d5 = this.g[i1] / 8000.0D;
/*     */         
/* 229 */         if (d5 < 0.0D) {
/* 230 */           d5 = -d5 * 0.3D;
/*     */         }
/*     */         
/* 233 */         d5 = d5 * 3.0D - 2.0D;
/* 234 */         if (d5 < 0.0D) {
/* 235 */           d5 /= 2.0D;
/* 236 */           if (d5 < -1.0D) {
/* 237 */             d5 = -1.0D;
/*     */           }
/*     */           
/* 240 */           d5 /= 1.4D;
/* 241 */           d5 /= 2.0D;
/*     */         } else {
/* 243 */           if (d5 > 1.0D) {
/* 244 */             d5 = 1.0D;
/*     */           }
/*     */           
/* 247 */           d5 /= 8.0D;
/*     */         } 
/*     */         
/* 250 */         i1++;
/* 251 */         double d6 = f1;
/* 252 */         double d7 = f;
/*     */         
/* 254 */         d6 += d5 * 0.2D;
/* 255 */         d6 = d6 * 8.5D / 8.0D;
/* 256 */         double d8 = 8.5D + d6 * 4.0D;
/*     */         
/* 258 */         for (int j2 = 0; j2 < 33; j2++) {
/* 259 */           double d9 = (j2 - d8) * 12.0D * 128.0D / 256.0D / d7;
/*     */           
/* 261 */           if (d9 < 0.0D) {
/* 262 */             d9 *= 4.0D;
/*     */           }
/*     */           
/* 265 */           double d10 = this.e[l] / 512.0D;
/* 266 */           double d11 = this.f[l] / 512.0D;
/* 267 */           double d12 = (this.d[l] / 10.0D + 1.0D) / 2.0D;
/* 268 */           double d13 = MathHelper.b(d10, d11, d12) - d9;
/*     */           
/* 270 */           if (j2 > 29) {
/* 271 */             double d14 = ((j2 - 29) / 3.0F);
/*     */             
/* 273 */             d13 = d13 * (1.0D - d14) + -10.0D * d14;
/*     */           } 
/*     */           
/* 276 */           this.q[l] = d13;
/* 277 */           l++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isChunkLoaded(int i, int j) {
/* 284 */     return true;
/*     */   }
/*     */   
/*     */   public void getChunkAt(IChunkProvider ichunkprovider, int i, int j) {
/* 288 */     BlockFalling.instaFall = true;
/* 289 */     int k = i * 16;
/* 290 */     int l = j * 16;
/* 291 */     BiomeBase biomebase = this.n.getBiome(k + 16, l + 16);
/*     */     
/* 293 */     this.i.setSeed(this.n.getSeed());
/* 294 */     long i1 = this.i.nextLong() / 2L * 2L + 1L;
/* 295 */     long j1 = this.i.nextLong() / 2L * 2L + 1L;
/*     */     
/* 297 */     this.i.setSeed(i * i1 + j * j1 ^ this.n.getSeed());
/* 298 */     boolean flag = false;
/*     */     
/* 300 */     if (this.o) {
/*     */       
/* 302 */       if (this.n.paperSpigotConfig.generateMineshaft) this.w.a(this.n, this.i, i, j); 
/* 303 */       if (this.n.paperSpigotConfig.generateVillage) flag = this.v.a(this.n, this.i, i, j); 
/* 304 */       if (this.n.paperSpigotConfig.generateStronghold) this.u.a(this.n, this.i, i, j); 
/* 305 */       if (this.n.paperSpigotConfig.generateTemple) this.x.a(this.n, this.i, i, j);
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     if (biomebase != BiomeBase.DESERT && biomebase != BiomeBase.DESERT_HILLS && !flag && this.i.nextInt(4) == 0) {
/* 314 */       int m = k + this.i.nextInt(16) + 8;
/* 315 */       int l1 = this.i.nextInt(256);
/* 316 */       int i2 = l + this.i.nextInt(16) + 8;
/* 317 */       (new WorldGenLakes(Blocks.STATIONARY_WATER)).generate(this.n, this.i, m, l1, i2);
/*     */     } 
/*     */     
/* 320 */     if (!flag && this.i.nextInt(8) == 0) {
/* 321 */       int m = k + this.i.nextInt(16) + 8;
/* 322 */       int l1 = this.i.nextInt(this.i.nextInt(248) + 8);
/* 323 */       int i2 = l + this.i.nextInt(16) + 8;
/* 324 */       if (l1 < 63 || this.i.nextInt(10) == 0) {
/* 325 */         (new WorldGenLakes(Blocks.STATIONARY_LAVA)).generate(this.n, this.i, m, l1, i2);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 330 */     if (this.n.paperSpigotConfig.generateDungeon) {
/* 331 */       for (int m = 0; m < 8; m++) {
/* 332 */         int l1 = k + this.i.nextInt(16) + 8;
/* 333 */         int i2 = this.i.nextInt(256);
/* 334 */         int j2 = l + this.i.nextInt(16) + 8;
/*     */         
/* 336 */         (new WorldGenDungeons()).generate(this.n, this.i, l1, i2, j2);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 341 */     biomebase.a(this.n, this.i, k, l);
/* 342 */     SpawnerCreature.a(this.n, biomebase, k + 8, l + 8, 16, 16, this.i);
/* 343 */     k += 8;
/* 344 */     l += 8;
/*     */     
/* 346 */     for (int k1 = 0; k1 < 16; k1++) {
/* 347 */       for (int l1 = 0; l1 < 16; l1++) {
/* 348 */         int i2 = this.n.h(k + k1, l + l1);
/* 349 */         if (this.n.r(k1 + k, i2 - 1, l1 + l)) {
/* 350 */           this.n.setTypeAndData(k1 + k, i2 - 1, l1 + l, Blocks.ICE, 0, 2);
/*     */         }
/*     */         
/* 353 */         if (this.n.e(k1 + k, i2, l1 + l, true)) {
/* 354 */           this.n.setTypeAndData(k1 + k, i2, l1 + l, Blocks.SNOW, 0, 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 359 */     BlockFalling.instaFall = false;
/*     */   }
/*     */   
/*     */   public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
/* 363 */     return true;
/*     */   }
/*     */   
/*     */   public void c() {}
/*     */   
/*     */   public boolean unloadChunks() {
/* 369 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canSave() {
/* 373 */     return true;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 377 */     return "RandomLevelSource";
/*     */   }
/*     */   
/*     */   public List getMobsFor(EnumCreatureType enumcreaturetype, int i, int j, int k) {
/* 381 */     BiomeBase biomebase = this.n.getBiome(i, k);
/*     */     
/* 383 */     return (enumcreaturetype == EnumCreatureType.MONSTER && this.x.a(i, j, k)) ? this.x.b() : biomebase.getMobs(enumcreaturetype);
/*     */   }
/*     */   
/*     */   public ChunkPosition findNearestMapFeature(World world, String s, int i, int j, int k) {
/* 387 */     return ("Stronghold".equals(s) && this.u != null) ? this.u.getNearestGeneratedFeature(world, i, j, k) : null;
/*     */   }
/*     */   
/*     */   public int getLoadedChunks() {
/* 391 */     return 0;
/*     */   }
/*     */   
/*     */   public void recreateStructures(int i, int j) {
/* 395 */     if (this.o) {
/*     */       
/* 397 */       if (this.n.paperSpigotConfig.generateMineshaft) this.w.a(this, this.n, i, j, (Block[])null); 
/* 398 */       if (this.n.paperSpigotConfig.generateVillage) this.v.a(this, this.n, i, j, (Block[])null); 
/* 399 */       if (this.n.paperSpigotConfig.generateStronghold) this.u.a(this, this.n, i, j, (Block[])null); 
/* 400 */       if (this.n.paperSpigotConfig.generateTemple) this.x.a(this, this.n, i, j, (Block[])null); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChunkProviderGenerate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */