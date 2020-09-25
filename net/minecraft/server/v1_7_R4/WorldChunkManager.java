/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
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
/*     */ public class WorldChunkManager
/*     */ {
/*     */   private GenLayer c;
/*     */   private GenLayer d;
/*  19 */   private BiomeCache e = new BiomeCache(this);
/*     */   
/*     */   private List f;
/*     */ 
/*     */   
/*     */   protected WorldChunkManager() {
/*  25 */     this.f = new ArrayList();
/*  26 */     this.f.add(BiomeBase.FOREST);
/*  27 */     this.f.add(BiomeBase.PLAINS);
/*  28 */     this.f.add(BiomeBase.TAIGA);
/*  29 */     this.f.add(BiomeBase.TAIGA_HILLS);
/*  30 */     this.f.add(BiomeBase.FOREST_HILLS);
/*  31 */     this.f.add(BiomeBase.JUNGLE);
/*  32 */     this.f.add(BiomeBase.JUNGLE_HILLS);
/*     */   }
/*     */   
/*     */   public WorldChunkManager(long paramLong, WorldType paramWorldType) {
/*  36 */     this();
/*     */     
/*  38 */     GenLayer[] arrayOfGenLayer = GenLayer.a(paramLong, paramWorldType);
/*  39 */     this.c = arrayOfGenLayer[0];
/*  40 */     this.d = arrayOfGenLayer[1];
/*     */   }
/*     */   
/*     */   public WorldChunkManager(World paramWorld) {
/*  44 */     this(paramWorld.getSeed(), paramWorld.getWorldData().getType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List a() {
/*  53 */     return this.f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeBase getBiome(int paramInt1, int paramInt2) {
/*  61 */     return this.e.b(paramInt1, paramInt2);
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
/*     */   public float[] getWetness(float[] paramArrayOffloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  74 */     IntCache.a();
/*  75 */     if (paramArrayOffloat == null || paramArrayOffloat.length < paramInt3 * paramInt4) {
/*  76 */       paramArrayOffloat = new float[paramInt3 * paramInt4];
/*     */     }
/*     */     
/*  79 */     int[] arrayOfInt = this.d.a(paramInt1, paramInt2, paramInt3, paramInt4);
/*  80 */     for (byte b = 0; b < paramInt3 * paramInt4; b++) {
/*     */       try {
/*  82 */         float f = BiomeBase.getBiome(arrayOfInt[b]).h() / 65536.0F;
/*  83 */         if (f > 1.0F) f = 1.0F; 
/*  84 */         paramArrayOffloat[b] = f;
/*  85 */       } catch (Throwable throwable) {
/*  86 */         CrashReport crashReport = CrashReport.a(throwable, "Invalid Biome id");
/*  87 */         CrashReportSystemDetails crashReportSystemDetails = crashReport.a("DownfallBlock");
/*  88 */         crashReportSystemDetails.a("biome id", Integer.valueOf(b));
/*  89 */         crashReportSystemDetails.a("downfalls[] size", Integer.valueOf(paramArrayOffloat.length));
/*  90 */         crashReportSystemDetails.a("x", Integer.valueOf(paramInt1));
/*  91 */         crashReportSystemDetails.a("z", Integer.valueOf(paramInt2));
/*  92 */         crashReportSystemDetails.a("w", Integer.valueOf(paramInt3));
/*  93 */         crashReportSystemDetails.a("h", Integer.valueOf(paramInt4));
/*     */         
/*  95 */         throw new ReportedException(crashReport);
/*     */       } 
/*     */     } 
/*     */     
/*  99 */     return paramArrayOffloat;
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
/* 116 */     IntCache.a();
/* 117 */     if (paramArrayOfBiomeBase == null || paramArrayOfBiomeBase.length < paramInt3 * paramInt4) {
/* 118 */       paramArrayOfBiomeBase = new BiomeBase[paramInt3 * paramInt4];
/*     */     }
/*     */     
/* 121 */     int[] arrayOfInt = this.c.a(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     try {
/* 123 */       for (byte b = 0; b < paramInt3 * paramInt4; b++) {
/* 124 */         paramArrayOfBiomeBase[b] = BiomeBase.getBiome(arrayOfInt[b]);
/*     */       }
/* 126 */     } catch (Throwable throwable) {
/* 127 */       CrashReport crashReport = CrashReport.a(throwable, "Invalid Biome id");
/* 128 */       CrashReportSystemDetails crashReportSystemDetails = crashReport.a("RawBiomeBlock");
/* 129 */       crashReportSystemDetails.a("biomes[] size", Integer.valueOf(paramArrayOfBiomeBase.length));
/* 130 */       crashReportSystemDetails.a("x", Integer.valueOf(paramInt1));
/* 131 */       crashReportSystemDetails.a("z", Integer.valueOf(paramInt2));
/* 132 */       crashReportSystemDetails.a("w", Integer.valueOf(paramInt3));
/* 133 */       crashReportSystemDetails.a("h", Integer.valueOf(paramInt4));
/*     */       
/* 135 */       throw new ReportedException(crashReport);
/*     */     } 
/*     */     
/* 138 */     return paramArrayOfBiomeBase;
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
/*     */   public BiomeBase[] getBiomeBlock(BiomeBase[] paramArrayOfBiomeBase, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 151 */     return a(paramArrayOfBiomeBase, paramInt1, paramInt2, paramInt3, paramInt4, true);
/*     */   }
/*     */   
/*     */   public BiomeBase[] a(BiomeBase[] paramArrayOfBiomeBase, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/* 155 */     IntCache.a();
/* 156 */     if (paramArrayOfBiomeBase == null || paramArrayOfBiomeBase.length < paramInt3 * paramInt4) {
/* 157 */       paramArrayOfBiomeBase = new BiomeBase[paramInt3 * paramInt4];
/*     */     }
/*     */     
/* 160 */     if (paramBoolean && paramInt3 == 16 && paramInt4 == 16 && (paramInt1 & 0xF) == 0 && (paramInt2 & 0xF) == 0) {
/* 161 */       BiomeBase[] arrayOfBiomeBase = this.e.d(paramInt1, paramInt2);
/* 162 */       System.arraycopy(arrayOfBiomeBase, 0, paramArrayOfBiomeBase, 0, paramInt3 * paramInt4);
/* 163 */       return paramArrayOfBiomeBase;
/*     */     } 
/*     */     
/* 166 */     int[] arrayOfInt = this.d.a(paramInt1, paramInt2, paramInt3, paramInt4);
/* 167 */     for (byte b = 0; b < paramInt3 * paramInt4; b++) {
/* 168 */       paramArrayOfBiomeBase[b] = BiomeBase.getBiome(arrayOfInt[b]);
/*     */     }
/*     */     
/* 171 */     return paramArrayOfBiomeBase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(int paramInt1, int paramInt2, int paramInt3, List paramList) {
/* 182 */     IntCache.a();
/* 183 */     int i = paramInt1 - paramInt3 >> 2;
/* 184 */     int j = paramInt2 - paramInt3 >> 2;
/* 185 */     int k = paramInt1 + paramInt3 >> 2;
/* 186 */     int m = paramInt2 + paramInt3 >> 2;
/*     */     
/* 188 */     int n = k - i + 1;
/* 189 */     int i1 = m - j + 1;
/*     */     
/* 191 */     int[] arrayOfInt = this.c.a(i, j, n, i1);
/*     */     try {
/* 193 */       for (byte b = 0; b < n * i1; b++) {
/* 194 */         BiomeBase biomeBase = BiomeBase.getBiome(arrayOfInt[b]);
/* 195 */         if (!paramList.contains(biomeBase)) return false; 
/*     */       } 
/* 197 */     } catch (Throwable throwable) {
/* 198 */       CrashReport crashReport = CrashReport.a(throwable, "Invalid Biome id");
/* 199 */       CrashReportSystemDetails crashReportSystemDetails = crashReport.a("Layer");
/* 200 */       crashReportSystemDetails.a("Layer", this.c.toString());
/* 201 */       crashReportSystemDetails.a("x", Integer.valueOf(paramInt1));
/* 202 */       crashReportSystemDetails.a("z", Integer.valueOf(paramInt2));
/* 203 */       crashReportSystemDetails.a("radius", Integer.valueOf(paramInt3));
/* 204 */       crashReportSystemDetails.a("allowed", paramList);
/*     */       
/* 206 */       throw new ReportedException(crashReport);
/*     */     } 
/*     */     
/* 209 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 275 */     IntCache.a();
/* 276 */     int i = paramInt1 - paramInt3 >> 2;
/* 277 */     int j = paramInt2 - paramInt3 >> 2;
/* 278 */     int k = paramInt1 + paramInt3 >> 2;
/* 279 */     int m = paramInt2 + paramInt3 >> 2;
/*     */     
/* 281 */     int n = k - i + 1;
/* 282 */     int i1 = m - j + 1;
/* 283 */     int[] arrayOfInt = this.c.a(i, j, n, i1);
/* 284 */     ChunkPosition chunkPosition = null;
/* 285 */     byte b1 = 0;
/* 286 */     for (byte b2 = 0; b2 < n * i1; b2++) {
/* 287 */       int i2 = i + b2 % n << 2;
/* 288 */       int i3 = j + b2 / n << 2;
/* 289 */       BiomeBase biomeBase = BiomeBase.getBiome(arrayOfInt[b2]);
/* 290 */       if (paramList.contains(biomeBase) && (
/* 291 */         chunkPosition == null || paramRandom.nextInt(b1 + 1) == 0)) {
/* 292 */         chunkPosition = new ChunkPosition(i2, 0, i3);
/* 293 */         b1++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 298 */     return chunkPosition;
/*     */   }
/*     */   
/*     */   public void b() {
/* 302 */     this.e.a();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldChunkManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */