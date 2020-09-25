/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class ChunkProviderHell
/*     */   implements IChunkProvider {
/*     */   private Random i;
/*     */   private NoiseGeneratorOctaves j;
/*     */   private NoiseGeneratorOctaves k;
/*     */   private NoiseGeneratorOctaves l;
/*     */   private NoiseGeneratorOctaves m;
/*     */   private NoiseGeneratorOctaves n;
/*     */   public NoiseGeneratorOctaves a;
/*     */   public NoiseGeneratorOctaves b;
/*     */   private World o;
/*     */   private double[] p;
/*  18 */   public WorldGenNether c = new WorldGenNether();
/*  19 */   private double[] q = new double[256];
/*  20 */   private double[] r = new double[256];
/*  21 */   private double[] s = new double[256];
/*  22 */   private WorldGenBase t = new WorldGenCavesHell();
/*     */   double[] d;
/*     */   double[] e;
/*     */   double[] f;
/*     */   double[] g;
/*     */   double[] h;
/*     */   
/*     */   public ChunkProviderHell(World world, long i) {
/*  30 */     this.o = world;
/*  31 */     this.i = new Random(i);
/*  32 */     this.j = new NoiseGeneratorOctaves(this.i, 16);
/*  33 */     this.k = new NoiseGeneratorOctaves(this.i, 16);
/*  34 */     this.l = new NoiseGeneratorOctaves(this.i, 8);
/*  35 */     this.m = new NoiseGeneratorOctaves(this.i, 4);
/*  36 */     this.n = new NoiseGeneratorOctaves(this.i, 4);
/*  37 */     this.a = new NoiseGeneratorOctaves(this.i, 10);
/*  38 */     this.b = new NoiseGeneratorOctaves(this.i, 16);
/*     */   }
/*     */   
/*     */   public void a(int i, int j, Block[] ablock) {
/*  42 */     byte b0 = 4;
/*  43 */     byte b1 = 32;
/*  44 */     int k = b0 + 1;
/*  45 */     byte b2 = 17;
/*  46 */     int l = b0 + 1;
/*     */     
/*  48 */     this.p = a(this.p, i * b0, 0, j * b0, k, b2, l);
/*     */     
/*  50 */     for (int i1 = 0; i1 < b0; i1++) {
/*  51 */       for (int j1 = 0; j1 < b0; j1++) {
/*  52 */         for (int k1 = 0; k1 < 16; k1++) {
/*  53 */           double d0 = 0.125D;
/*  54 */           double d1 = this.p[((i1 + 0) * l + j1 + 0) * b2 + k1 + 0];
/*  55 */           double d2 = this.p[((i1 + 0) * l + j1 + 1) * b2 + k1 + 0];
/*  56 */           double d3 = this.p[((i1 + 1) * l + j1 + 0) * b2 + k1 + 0];
/*  57 */           double d4 = this.p[((i1 + 1) * l + j1 + 1) * b2 + k1 + 0];
/*  58 */           double d5 = (this.p[((i1 + 0) * l + j1 + 0) * b2 + k1 + 1] - d1) * d0;
/*  59 */           double d6 = (this.p[((i1 + 0) * l + j1 + 1) * b2 + k1 + 1] - d2) * d0;
/*  60 */           double d7 = (this.p[((i1 + 1) * l + j1 + 0) * b2 + k1 + 1] - d3) * d0;
/*  61 */           double d8 = (this.p[((i1 + 1) * l + j1 + 1) * b2 + k1 + 1] - d4) * d0;
/*     */           
/*  63 */           for (int l1 = 0; l1 < 8; l1++) {
/*  64 */             double d9 = 0.25D;
/*  65 */             double d10 = d1;
/*  66 */             double d11 = d2;
/*  67 */             double d12 = (d3 - d1) * d9;
/*  68 */             double d13 = (d4 - d2) * d9;
/*     */             
/*  70 */             for (int i2 = 0; i2 < 4; i2++) {
/*  71 */               int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
/*  72 */               short short1 = 128;
/*  73 */               double d14 = 0.25D;
/*  74 */               double d15 = d10;
/*  75 */               double d16 = (d11 - d10) * d14;
/*     */               
/*  77 */               for (int k2 = 0; k2 < 4; k2++) {
/*  78 */                 Block block = null;
/*     */                 
/*  80 */                 if (k1 * 8 + l1 < b1) {
/*  81 */                   block = Blocks.STATIONARY_LAVA;
/*     */                 }
/*     */                 
/*  84 */                 if (d15 > 0.0D) {
/*  85 */                   block = Blocks.NETHERRACK;
/*     */                 }
/*     */                 
/*  88 */                 ablock[j2] = block;
/*  89 */                 j2 += short1;
/*  90 */                 d15 += d16;
/*     */               } 
/*     */               
/*  93 */               d10 += d12;
/*  94 */               d11 += d13;
/*     */             } 
/*     */             
/*  97 */             d1 += d5;
/*  98 */             d2 += d6;
/*  99 */             d3 += d7;
/* 100 */             d4 += d8;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(int i, int j, Block[] ablock) {
/* 108 */     byte b0 = 64;
/* 109 */     double d0 = 0.03125D;
/*     */     
/* 111 */     this.q = this.m.a(this.q, i * 16, j * 16, 0, 16, 16, 1, d0, d0, 1.0D);
/* 112 */     this.r = this.m.a(this.r, i * 16, 109, j * 16, 16, 1, 16, d0, 1.0D, d0);
/* 113 */     this.s = this.n.a(this.s, i * 16, j * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);
/*     */     
/* 115 */     for (int k = 0; k < 16; k++) {
/* 116 */       for (int l = 0; l < 16; l++) {
/* 117 */         boolean flag = (this.q[k + l * 16] + this.i.nextDouble() * 0.2D > 0.0D);
/* 118 */         boolean flag1 = (this.r[k + l * 16] + this.i.nextDouble() * 0.2D > 0.0D);
/* 119 */         int i1 = (int)(this.s[k + l * 16] / 3.0D + 3.0D + this.i.nextDouble() * 0.25D);
/* 120 */         int j1 = -1;
/* 121 */         Block block = Blocks.NETHERRACK;
/* 122 */         Block block1 = Blocks.NETHERRACK;
/*     */         
/* 124 */         for (int k1 = 127; k1 >= 0; k1--) {
/* 125 */           int l1 = (l * 16 + k) * 128 + k1;
/*     */ 
/*     */           
/* 128 */           if (k1 < 127 - (this.o.paperSpigotConfig.generateFlatBedrock ? 0 : this.i.nextInt(5)) && k1 > (this.o.paperSpigotConfig.generateFlatBedrock ? 0 : this.i.nextInt(5))) {
/*     */ 
/*     */             
/* 131 */             Block block2 = ablock[l1];
/*     */             
/* 133 */             if (block2 != null && block2.getMaterial() != Material.AIR) {
/* 134 */               if (block2 == Blocks.NETHERRACK) {
/* 135 */                 if (j1 == -1) {
/* 136 */                   if (i1 <= 0) {
/* 137 */                     block = null;
/* 138 */                     block1 = Blocks.NETHERRACK;
/* 139 */                   } else if (k1 >= b0 - 4 && k1 <= b0 + 1) {
/* 140 */                     block = Blocks.NETHERRACK;
/* 141 */                     block1 = Blocks.NETHERRACK;
/* 142 */                     if (flag1) {
/* 143 */                       block = Blocks.GRAVEL;
/* 144 */                       block1 = Blocks.NETHERRACK;
/*     */                     } 
/*     */                     
/* 147 */                     if (flag) {
/* 148 */                       block = Blocks.SOUL_SAND;
/* 149 */                       block1 = Blocks.SOUL_SAND;
/*     */                     } 
/*     */                   } 
/*     */                   
/* 153 */                   if (k1 < b0 && (block == null || block.getMaterial() == Material.AIR)) {
/* 154 */                     block = Blocks.STATIONARY_LAVA;
/*     */                   }
/*     */                   
/* 157 */                   j1 = i1;
/* 158 */                   if (k1 >= b0 - 1) {
/* 159 */                     ablock[l1] = block;
/*     */                   } else {
/* 161 */                     ablock[l1] = block1;
/*     */                   } 
/* 163 */                 } else if (j1 > 0) {
/* 164 */                   j1--;
/* 165 */                   ablock[l1] = block1;
/*     */                 } 
/*     */               }
/*     */             } else {
/* 169 */               j1 = -1;
/*     */             } 
/*     */           } else {
/* 172 */             ablock[l1] = Blocks.BEDROCK;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Chunk getChunkAt(int i, int j) {
/* 180 */     return getOrCreateChunk(i, j);
/*     */   }
/*     */   
/*     */   public Chunk getOrCreateChunk(int i, int j) {
/* 184 */     this.i.setSeed(i * 341873128712L + j * 132897987541L);
/* 185 */     Block[] ablock = new Block[32768];
/*     */     
/* 187 */     a(i, j, ablock);
/* 188 */     b(i, j, ablock);
/*     */     
/* 190 */     if (this.o.paperSpigotConfig.generateCaves) this.t.a(this, this.o, i, j, ablock); 
/* 191 */     if (this.o.paperSpigotConfig.generateFortress) this.c.a(this, this.o, i, j, ablock);
/*     */     
/* 193 */     Chunk chunk = new Chunk(this.o, ablock, i, j);
/* 194 */     BiomeBase[] abiomebase = this.o.getWorldChunkManager().getBiomeBlock((BiomeBase[])null, i * 16, j * 16, 16, 16);
/* 195 */     byte[] abyte = chunk.m();
/*     */     
/* 197 */     for (int k = 0; k < abyte.length; k++) {
/* 198 */       abyte[k] = (byte)(abiomebase[k]).id;
/*     */     }
/*     */     
/* 201 */     chunk.n();
/* 202 */     return chunk;
/*     */   }
/*     */   
/*     */   private double[] a(double[] adouble, int i, int j, int k, int l, int i1, int j1) {
/* 206 */     if (adouble == null) {
/* 207 */       adouble = new double[l * i1 * j1];
/*     */     }
/*     */     
/* 210 */     double d0 = 684.412D;
/* 211 */     double d1 = 2053.236D;
/*     */     
/* 213 */     this.g = this.a.a(this.g, i, j, k, l, 1, j1, 1.0D, 0.0D, 1.0D);
/* 214 */     this.h = this.b.a(this.h, i, j, k, l, 1, j1, 100.0D, 0.0D, 100.0D);
/* 215 */     this.d = this.l.a(this.d, i, j, k, l, i1, j1, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
/* 216 */     this.e = this.j.a(this.e, i, j, k, l, i1, j1, d0, d1, d0);
/* 217 */     this.f = this.k.a(this.f, i, j, k, l, i1, j1, d0, d1, d0);
/* 218 */     int k1 = 0;
/* 219 */     int l1 = 0;
/* 220 */     double[] adouble1 = new double[i1];
/*     */     
/*     */     int i2;
/*     */     
/* 224 */     for (i2 = 0; i2 < i1; i2++) {
/* 225 */       adouble1[i2] = Math.cos(i2 * Math.PI * 6.0D / i1) * 2.0D;
/* 226 */       double d2 = i2;
/*     */       
/* 228 */       if (i2 > i1 / 2) {
/* 229 */         d2 = (i1 - 1 - i2);
/*     */       }
/*     */       
/* 232 */       if (d2 < 4.0D) {
/* 233 */         d2 = 4.0D - d2;
/* 234 */         adouble1[i2] = adouble1[i2] - d2 * d2 * d2 * 10.0D;
/*     */       } 
/*     */     } 
/*     */     
/* 238 */     for (i2 = 0; i2 < l; i2++) {
/* 239 */       for (int j2 = 0; j2 < j1; j2++) {
/* 240 */         double d3 = (this.g[l1] + 256.0D) / 512.0D;
/*     */         
/* 242 */         if (d3 > 1.0D) {
/* 243 */           d3 = 1.0D;
/*     */         }
/*     */         
/* 246 */         double d4 = 0.0D;
/* 247 */         double d5 = this.h[l1] / 8000.0D;
/*     */         
/* 249 */         if (d5 < 0.0D) {
/* 250 */           d5 = -d5;
/*     */         }
/*     */         
/* 253 */         d5 = d5 * 3.0D - 3.0D;
/* 254 */         if (d5 < 0.0D) {
/* 255 */           d5 /= 2.0D;
/* 256 */           if (d5 < -1.0D) {
/* 257 */             d5 = -1.0D;
/*     */           }
/*     */           
/* 260 */           d5 /= 1.4D;
/* 261 */           d5 /= 2.0D;
/* 262 */           d3 = 0.0D;
/*     */         } else {
/* 264 */           if (d5 > 1.0D) {
/* 265 */             d5 = 1.0D;
/*     */           }
/*     */           
/* 268 */           d5 /= 6.0D;
/*     */         } 
/*     */         
/* 271 */         d3 += 0.5D;
/* 272 */         d5 = d5 * i1 / 16.0D;
/* 273 */         l1++;
/*     */         
/* 275 */         for (int k2 = 0; k2 < i1; k2++) {
/* 276 */           double d6 = 0.0D;
/* 277 */           double d7 = adouble1[k2];
/* 278 */           double d8 = this.e[k1] / 512.0D;
/* 279 */           double d9 = this.f[k1] / 512.0D;
/* 280 */           double d10 = (this.d[k1] / 10.0D + 1.0D) / 2.0D;
/*     */           
/* 282 */           if (d10 < 0.0D) {
/* 283 */             d6 = d8;
/* 284 */           } else if (d10 > 1.0D) {
/* 285 */             d6 = d9;
/*     */           } else {
/* 287 */             d6 = d8 + (d9 - d8) * d10;
/*     */           } 
/*     */           
/* 290 */           d6 -= d7;
/*     */ 
/*     */           
/* 293 */           if (k2 > i1 - 4) {
/* 294 */             double d11 = ((k2 - i1 - 4) / 3.0F);
/* 295 */             d6 = d6 * (1.0D - d11) + -10.0D * d11;
/*     */           } 
/*     */           
/* 298 */           if (k2 < d4) {
/* 299 */             double d11 = (d4 - k2) / 4.0D;
/* 300 */             if (d11 < 0.0D) {
/* 301 */               d11 = 0.0D;
/*     */             }
/*     */             
/* 304 */             if (d11 > 1.0D) {
/* 305 */               d11 = 1.0D;
/*     */             }
/*     */             
/* 308 */             d6 = d6 * (1.0D - d11) + -10.0D * d11;
/*     */           } 
/*     */           
/* 311 */           adouble[k1] = d6;
/* 312 */           k1++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 317 */     return adouble;
/*     */   }
/*     */   
/*     */   public boolean isChunkLoaded(int i, int j) {
/* 321 */     return true;
/*     */   }
/*     */   
/*     */   public void getChunkAt(IChunkProvider ichunkprovider, int i, int j) {
/* 325 */     BlockFalling.instaFall = true;
/* 326 */     int k = i * 16;
/* 327 */     int l = j * 16;
/*     */     
/* 329 */     if (this.o.paperSpigotConfig.generateFortress) this.c.a(this.o, this.i, i, j);
/*     */ 
/*     */ 
/*     */     
/*     */     int i1;
/*     */ 
/*     */     
/* 336 */     for (i1 = 0; i1 < 8; i1++) {
/* 337 */       int m = k + this.i.nextInt(16) + 8;
/* 338 */       int n = this.i.nextInt(120) + 4;
/* 339 */       int l1 = l + this.i.nextInt(16) + 8;
/* 340 */       (new WorldGenHellLava(Blocks.LAVA, false)).generate(this.o, this.i, m, n, l1);
/*     */     } 
/*     */     
/* 343 */     i1 = this.i.nextInt(this.i.nextInt(10) + 1) + 1;
/*     */     
/*     */     int j1;
/*     */     
/* 347 */     for (j1 = 0; j1 < i1; j1++) {
/* 348 */       int m = k + this.i.nextInt(16) + 8;
/* 349 */       int l1 = this.i.nextInt(120) + 4;
/* 350 */       int i2 = l + this.i.nextInt(16) + 8;
/* 351 */       (new WorldGenFire()).generate(this.o, this.i, m, l1, i2);
/*     */     } 
/*     */     
/* 354 */     i1 = this.i.nextInt(this.i.nextInt(10) + 1);
/*     */     
/* 356 */     for (j1 = 0; j1 < i1; j1++) {
/* 357 */       int m = k + this.i.nextInt(16) + 8;
/* 358 */       int l1 = this.i.nextInt(120) + 4;
/* 359 */       int i2 = l + this.i.nextInt(16) + 8;
/* 360 */       (new WorldGenLightStone1()).generate(this.o, this.i, m, l1, i2);
/*     */     } 
/*     */     
/* 363 */     for (j1 = 0; j1 < 10; j1++) {
/* 364 */       int m = k + this.i.nextInt(16) + 8;
/* 365 */       int l1 = this.i.nextInt(128);
/* 366 */       int i2 = l + this.i.nextInt(16) + 8;
/* 367 */       (new WorldGenLightStone2()).generate(this.o, this.i, m, l1, i2);
/*     */     } 
/*     */     
/* 370 */     if (this.i.nextInt(1) == 0) {
/* 371 */       j1 = k + this.i.nextInt(16) + 8;
/* 372 */       int m = this.i.nextInt(128);
/* 373 */       int l1 = l + this.i.nextInt(16) + 8;
/* 374 */       (new WorldGenFlowers(Blocks.BROWN_MUSHROOM)).generate(this.o, this.i, j1, m, l1);
/*     */     } 
/*     */     
/* 377 */     if (this.i.nextInt(1) == 0) {
/* 378 */       j1 = k + this.i.nextInt(16) + 8;
/* 379 */       int m = this.i.nextInt(128);
/* 380 */       int l1 = l + this.i.nextInt(16) + 8;
/* 381 */       (new WorldGenFlowers(Blocks.RED_MUSHROOM)).generate(this.o, this.i, j1, m, l1);
/*     */     } 
/*     */     
/* 384 */     WorldGenMinable worldgenminable = new WorldGenMinable(Blocks.QUARTZ_ORE, 13, Blocks.NETHERRACK);
/*     */     
/*     */     int k1;
/*     */     
/* 388 */     for (k1 = 0; k1 < 16; k1++) {
/* 389 */       int l1 = k + this.i.nextInt(16);
/* 390 */       int i2 = this.i.nextInt(108) + 10;
/* 391 */       int j2 = l + this.i.nextInt(16);
/* 392 */       worldgenminable.generate(this.o, this.i, l1, i2, j2);
/*     */     } 
/*     */     
/* 395 */     for (k1 = 0; k1 < 16; k1++) {
/* 396 */       int l1 = k + this.i.nextInt(16);
/* 397 */       int i2 = this.i.nextInt(108) + 10;
/* 398 */       int j2 = l + this.i.nextInt(16);
/* 399 */       (new WorldGenHellLava(Blocks.LAVA, true)).generate(this.o, this.i, l1, i2, j2);
/*     */     } 
/*     */     
/* 402 */     BlockFalling.instaFall = false;
/*     */   }
/*     */   
/*     */   public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
/* 406 */     return true;
/*     */   }
/*     */   
/*     */   public void c() {}
/*     */   
/*     */   public boolean unloadChunks() {
/* 412 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canSave() {
/* 416 */     return true;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 420 */     return "HellRandomLevelSource";
/*     */   }
/*     */   
/*     */   public List getMobsFor(EnumCreatureType enumcreaturetype, int i, int j, int k) {
/* 424 */     if (enumcreaturetype == EnumCreatureType.MONSTER) {
/* 425 */       if (this.c.b(i, j, k)) {
/* 426 */         return this.c.b();
/*     */       }
/*     */       
/* 429 */       if (this.c.d(i, j, k) && this.o.getType(i, j - 1, k) == Blocks.NETHER_BRICK) {
/* 430 */         return this.c.b();
/*     */       }
/*     */     } 
/*     */     
/* 434 */     BiomeBase biomebase = this.o.getBiome(i, k);
/*     */     
/* 436 */     return biomebase.getMobs(enumcreaturetype);
/*     */   }
/*     */   
/*     */   public ChunkPosition findNearestMapFeature(World world, String s, int i, int j, int k) {
/* 440 */     return null;
/*     */   }
/*     */   
/*     */   public int getLoadedChunks() {
/* 444 */     return 0;
/*     */   }
/*     */   
/*     */   public void recreateStructures(int i, int j) {
/* 448 */     if (this.o.paperSpigotConfig.generateFortress) this.c.a(this, this.o, i, j, (Block[])null); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChunkProviderHell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */