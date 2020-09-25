/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
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
/*     */ public class ChunkProviderTheEnd
/*     */   implements IChunkProvider
/*     */ {
/*     */   private Random i;
/*     */   private NoiseGeneratorOctaves j;
/*     */   private NoiseGeneratorOctaves k;
/*     */   private NoiseGeneratorOctaves l;
/*     */   public NoiseGeneratorOctaves a;
/*     */   public NoiseGeneratorOctaves b;
/*     */   private World m;
/*     */   private double[] n;
/*     */   private BiomeBase[] o;
/*     */   double[] c;
/*     */   double[] d;
/*     */   double[] e;
/*     */   double[] f;
/*     */   double[] g;
/*     */   int[][] h;
/*     */   
/*     */   public ChunkProviderTheEnd(World paramWorld, long paramLong) {
/* 269 */     this.h = new int[32][32]; this.m = paramWorld; this.i = new Random(paramLong); this.j = new NoiseGeneratorOctaves(this.i, 16); this.k = new NoiseGeneratorOctaves(this.i, 16); this.l = new NoiseGeneratorOctaves(this.i, 8); this.a = new NoiseGeneratorOctaves(this.i, 10); this.b = new NoiseGeneratorOctaves(this.i, 16);
/*     */   } public void a(int paramInt1, int paramInt2, Block[] paramArrayOfBlock, BiomeBase[] paramArrayOfBiomeBase) { byte b1 = 2; int i = b1 + 1; byte b2 = 33; int j = b1 + 1; this.n = a(this.n, paramInt1 * b1, 0, paramInt2 * b1, i, b2, j); for (byte b3 = 0; b3 < b1; b3++) {
/*     */       for (byte b = 0; b < b1; b++) {
/*     */         for (byte b4 = 0; b4 < 32; b4++) {
/*     */           double d1 = 0.25D; double d2 = this.n[((b3 + 0) * j + b + 0) * b2 + b4 + 0]; double d3 = this.n[((b3 + 0) * j + b + 1) * b2 + b4 + 0]; double d4 = this.n[((b3 + 1) * j + b + 0) * b2 + b4 + 0]; double d5 = this.n[((b3 + 1) * j + b + 1) * b2 + b4 + 0]; double d6 = (this.n[((b3 + 0) * j + b + 0) * b2 + b4 + 1] - d2) * d1; double d7 = (this.n[((b3 + 0) * j + b + 1) * b2 + b4 + 1] - d3) * d1; double d8 = (this.n[((b3 + 1) * j + b + 0) * b2 + b4 + 1] - d4) * d1; double d9 = (this.n[((b3 + 1) * j + b + 1) * b2 + b4 + 1] - d5) * d1; for (byte b5 = 0; b5 < 4; b5++) {
/*     */             double d10 = 0.125D; double d11 = d2; double d12 = d3; double d13 = (d4 - d2) * d10; double d14 = (d5 - d3) * d10; for (byte b6 = 0; b6 < 8; b6++) {
/*     */               int k = b6 + b3 * 8 << 11 | 0 + b * 8 << 7 | b4 * 4 + b5; char c = ''; double d15 = 0.125D; double d16 = d11; double d17 = (d12 - d11) * d15; for (byte b7 = 0; b7 < 8; b7++) {
/*     */                 Block block = null; if (d16 > 0.0D)
/*     */                   block = Blocks.WHITESTONE;  paramArrayOfBlock[k] = block; k += c; d16 += d17;
/*     */               }  d11 += d13; d12 += d14;
/*     */             } 
/*     */             d2 += d6;
/*     */             d3 += d7;
/*     */             d4 += d8;
/*     */             d5 += d9;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }  } public void b(int paramInt1, int paramInt2, Block[] paramArrayOfBlock, BiomeBase[] paramArrayOfBiomeBase) { for (byte b = 0; b < 16; b++) {
/*     */       for (byte b1 = 0; b1 < 16; b1++) {
/*     */         boolean bool = true;
/*     */         byte b2 = -1;
/*     */         Block block1 = Blocks.WHITESTONE;
/*     */         Block block2 = Blocks.WHITESTONE;
/*     */         for (byte b3 = 127; b3 >= 0; b3--) {
/*     */           int i = (b1 * 16 + b) * 128 + b3;
/*     */           Block block = paramArrayOfBlock[i];
/*     */           if (block == null || block.getMaterial() == Material.AIR) {
/*     */             b2 = -1;
/*     */           } else if (block == Blocks.STONE) {
/*     */             if (b2 == -1) {
/*     */               if (bool) {
/*     */                 block1 = null;
/*     */                 block2 = Blocks.WHITESTONE;
/*     */               } 
/*     */               b2 = bool;
/*     */               if (b3 >= 0) {
/*     */                 paramArrayOfBlock[i] = block1;
/*     */               } else {
/*     */                 paramArrayOfBlock[i] = block2;
/*     */               } 
/*     */             } else if (b2 > 0) {
/*     */               b2--;
/*     */               paramArrayOfBlock[i] = block2;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }  }
/*     */   public Chunk getChunkAt(int paramInt1, int paramInt2) { return getOrCreateChunk(paramInt1, paramInt2); }
/* 319 */   public void getChunkAt(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2) { BlockFalling.instaFall = true;
/* 320 */     int i = paramInt1 * 16;
/* 321 */     int j = paramInt2 * 16;
/*     */     
/* 323 */     BiomeBase biomeBase = this.m.getBiome(i + 16, j + 16);
/* 324 */     biomeBase.a(this.m, this.m.random, i, j);
/*     */     
/* 326 */     BlockFalling.instaFall = false; } public Chunk getOrCreateChunk(int paramInt1, int paramInt2) { this.i.setSeed(paramInt1 * 341873128712L + paramInt2 * 132897987541L); Block[] arrayOfBlock = new Block[32768]; this.o = this.m.getWorldChunkManager().getBiomeBlock(this.o, paramInt1 * 16, paramInt2 * 16, 16, 16); a(paramInt1, paramInt2, arrayOfBlock, this.o); b(paramInt1, paramInt2, arrayOfBlock, this.o); Chunk chunk = new Chunk(this.m, arrayOfBlock, paramInt1, paramInt2); byte[] arrayOfByte = chunk.m();
/*     */     for (byte b = 0; b < arrayOfByte.length; b++)
/*     */       arrayOfByte[b] = (byte)(this.o[b]).id; 
/*     */     chunk.initLighting();
/*     */     return chunk; }
/* 331 */   public boolean saveChunks(boolean paramBoolean, IProgressUpdate paramIProgressUpdate) { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unloadChunks() {
/* 340 */     return false;
/*     */   }
/*     */   private double[] a(double[] paramArrayOfdouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) { if (paramArrayOfdouble == null) paramArrayOfdouble = new double[paramInt4 * paramInt5 * paramInt6];  double d1 = 684.412D; double d2 = 684.412D; this.f = this.a.a(this.f, paramInt1, paramInt3, paramInt4, paramInt6, 1.121D, 1.121D, 0.5D); this.g = this.b.a(this.g, paramInt1, paramInt3, paramInt4, paramInt6, 200.0D, 200.0D, 0.5D); d1 *= 2.0D; this.c = this.l.a(this.c, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1 / 80.0D, d2 / 160.0D, d1 / 80.0D); this.d = this.j.a(this.d, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1); this.e = this.k.a(this.e, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1); byte b1 = 0; byte b2 = 0; for (byte b3 = 0; b3 < paramInt4; b3++) { for (byte b = 0; b < paramInt6; b++) { double d3 = (this.f[b2] + 256.0D) / 512.0D; if (d3 > 1.0D) d3 = 1.0D;  double d4 = this.g[b2] / 8000.0D; if (d4 < 0.0D) d4 = -d4 * 0.3D;  d4 = d4 * 3.0D - 2.0D; float f1 = (b3 + paramInt1 - 0) / 1.0F; float f2 = (b + paramInt3 - 0) / 1.0F; float f3 = 100.0F - MathHelper.c(f1 * f1 + f2 * f2) * 8.0F; if (f3 > 80.0F) f3 = 80.0F;  if (f3 < -100.0F) f3 = -100.0F;  if (d4 > 1.0D) d4 = 1.0D;  d4 /= 8.0D; d4 = 0.0D; if (d3 < 0.0D) d3 = 0.0D;  d3 += 0.5D; d4 = d4 * paramInt5 / 16.0D; b2++; double d5 = paramInt5 / 2.0D; for (byte b4 = 0; b4 < paramInt5; b4++) { double d6 = 0.0D; double d7 = (b4 - d5) * 8.0D / d3; if (d7 < 0.0D)
/*     */             d7 *= -1.0D;  double d8 = this.d[b1] / 512.0D; double d9 = this.e[b1] / 512.0D; double d10 = (this.c[b1] / 10.0D + 1.0D) / 2.0D; if (d10 < 0.0D) { d6 = d8; } else if (d10 > 1.0D) { d6 = d9; } else { d6 = d8 + (d9 - d8) * d10; }  d6 -= 8.0D; d6 += f3; byte b5 = 2; if (b4 > paramInt5 / 2 - b5) { double d = ((b4 - paramInt5 / 2 - b5) / 64.0F); if (d < 0.0D)
/*     */               d = 0.0D;  if (d > 1.0D)
/* 345 */               d = 1.0D;  d6 = d6 * (1.0D - d) + -3000.0D * d; }  b5 = 8; if (b4 < b5) { double d = ((b5 - b4) / (b5 - 1.0F)); d6 = d6 * (1.0D - d) + -30.0D * d; }  paramArrayOfdouble[b1] = d6; b1++; }  }  }  return paramArrayOfdouble; } public boolean isChunkLoaded(int paramInt1, int paramInt2) { return true; } public void c() {} public boolean canSave() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 351 */     return "RandomLevelSource";
/*     */   }
/*     */ 
/*     */   
/*     */   public List getMobsFor(EnumCreatureType paramEnumCreatureType, int paramInt1, int paramInt2, int paramInt3) {
/* 356 */     BiomeBase biomeBase = this.m.getBiome(paramInt1, paramInt3);
/* 357 */     return biomeBase.getMobs(paramEnumCreatureType);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkPosition findNearestMapFeature(World paramWorld, String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 362 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLoadedChunks() {
/* 367 */     return 0;
/*     */   }
/*     */   
/*     */   public void recreateStructures(int paramInt1, int paramInt2) {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChunkProviderTheEnd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */