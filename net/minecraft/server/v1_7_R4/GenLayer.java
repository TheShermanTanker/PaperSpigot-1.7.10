/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GenLayer
/*     */ {
/*     */   private long c;
/*     */   protected GenLayer a;
/*     */   private long d;
/*     */   protected long b;
/*     */   
/*     */   public static GenLayer[] a(long paramLong, WorldType paramWorldType) {
/*  29 */     boolean bool = false;
/*     */     
/*  31 */     LayerIsland layerIsland = new LayerIsland(1L);
/*  32 */     GenLayerZoomFuzzy genLayerZoomFuzzy = new GenLayerZoomFuzzy(2000L, layerIsland);
/*  33 */     GenLayerIsland genLayerIsland4 = new GenLayerIsland(1L, genLayerZoomFuzzy);
/*  34 */     GenLayerZoom genLayerZoom2 = new GenLayerZoom(2001L, genLayerIsland4);
/*  35 */     GenLayerIsland genLayerIsland3 = new GenLayerIsland(2L, genLayerZoom2);
/*  36 */     genLayerIsland3 = new GenLayerIsland(50L, genLayerIsland3);
/*  37 */     genLayerIsland3 = new GenLayerIsland(70L, genLayerIsland3);
/*  38 */     GenLayerIcePlains genLayerIcePlains = new GenLayerIcePlains(2L, genLayerIsland3);
/*  39 */     GenLayerTopSoil genLayerTopSoil = new GenLayerTopSoil(2L, genLayerIcePlains);
/*  40 */     GenLayerIsland genLayerIsland2 = new GenLayerIsland(3L, genLayerTopSoil);
/*  41 */     GenLayerSpecial genLayerSpecial = new GenLayerSpecial(2L, genLayerIsland2, EnumGenLayerSpecial.COOL_WARM);
/*  42 */     genLayerSpecial = new GenLayerSpecial(2L, genLayerSpecial, EnumGenLayerSpecial.HEAT_ICE);
/*  43 */     genLayerSpecial = new GenLayerSpecial(3L, genLayerSpecial, EnumGenLayerSpecial.PUFFERFISH);
/*  44 */     GenLayerZoom genLayerZoom1 = new GenLayerZoom(2002L, genLayerSpecial);
/*  45 */     genLayerZoom1 = new GenLayerZoom(2003L, genLayerZoom1);
/*  46 */     GenLayerIsland genLayerIsland1 = new GenLayerIsland(4L, genLayerZoom1);
/*  47 */     GenLayerMushroomIsland genLayerMushroomIsland = new GenLayerMushroomIsland(5L, genLayerIsland1);
/*  48 */     GenLayerDeepOcean genLayerDeepOcean = new GenLayerDeepOcean(4L, genLayerMushroomIsland);
/*  49 */     GenLayer genLayer1 = GenLayerZoom.b(1000L, genLayerDeepOcean, 0);
/*     */ 
/*     */ 
/*     */     
/*  53 */     byte b1 = 4;
/*  54 */     if (paramWorldType == WorldType.LARGE_BIOMES) {
/*  55 */       b1 = 6;
/*     */     }
/*  57 */     if (bool) {
/*  58 */       b1 = 4;
/*     */     }
/*     */     
/*  61 */     GenLayer genLayer2 = genLayer1;
/*  62 */     genLayer2 = GenLayerZoom.b(1000L, genLayer2, 0);
/*  63 */     genLayer2 = new GenLayerCleaner(100L, genLayer2);
/*     */     
/*  65 */     GenLayer genLayer3 = genLayer1;
/*  66 */     genLayer3 = new GenLayerBiome(200L, genLayer3, paramWorldType);
/*  67 */     if (!bool) {
/*  68 */       genLayer3 = GenLayerZoom.b(1000L, genLayer3, 2);
/*  69 */       genLayer3 = new GenLayerDesert(1000L, genLayer3);
/*     */     } 
/*  71 */     GenLayer genLayer4 = genLayer2;
/*  72 */     genLayer4 = GenLayerZoom.b(1000L, genLayer4, 2);
/*  73 */     genLayer3 = new GenLayerRegionHills(1000L, genLayer3, genLayer4);
/*     */     
/*  75 */     genLayer2 = GenLayerZoom.b(1000L, genLayer2, 2);
/*  76 */     genLayer2 = GenLayerZoom.b(1000L, genLayer2, b1);
/*  77 */     genLayer2 = new GenLayerRiver(1L, genLayer2);
/*  78 */     genLayer2 = new GenLayerSmooth(1000L, genLayer2);
/*     */     
/*  80 */     genLayer3 = new GenLayerPlains(1001L, genLayer3);
/*  81 */     for (byte b2 = 0; b2 < b1; b2++) {
/*  82 */       genLayer3 = new GenLayerZoom((1000 + b2), genLayer3);
/*  83 */       if (b2 == 0) {
/*  84 */         genLayer3 = new GenLayerIsland(3L, genLayer3);
/*     */       }
/*     */       
/*  87 */       if (b2 == 1) {
/*  88 */         genLayer3 = new GenLayerMushroomShore(1000L, genLayer3);
/*     */       }
/*     */     } 
/*     */     
/*  92 */     genLayer3 = new GenLayerSmooth(1000L, genLayer3);
/*     */     
/*  94 */     genLayer3 = new GenLayerRiverMix(100L, genLayer3, genLayer2);
/*     */     
/*  96 */     GenLayer genLayer5 = genLayer3;
/*  97 */     GenLayerZoomVoronoi genLayerZoomVoronoi = new GenLayerZoomVoronoi(10L, genLayer3);
/*     */     
/*  99 */     genLayer3.a(paramLong);
/* 100 */     genLayerZoomVoronoi.a(paramLong);
/*     */     
/* 102 */     return new GenLayer[] { genLayer3, genLayerZoomVoronoi, genLayer5 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GenLayer(long paramLong) {
/* 108 */     this.b = paramLong;
/* 109 */     this.b *= this.b * 6364136223846793005L + 1442695040888963407L;
/* 110 */     this.b += paramLong;
/* 111 */     this.b *= this.b * 6364136223846793005L + 1442695040888963407L;
/* 112 */     this.b += paramLong;
/* 113 */     this.b *= this.b * 6364136223846793005L + 1442695040888963407L;
/* 114 */     this.b += paramLong;
/*     */   }
/*     */   
/*     */   public void a(long paramLong) {
/* 118 */     this.c = paramLong;
/* 119 */     if (this.a != null) this.a.a(paramLong); 
/* 120 */     this.c *= this.c * 6364136223846793005L + 1442695040888963407L;
/* 121 */     this.c += this.b;
/* 122 */     this.c *= this.c * 6364136223846793005L + 1442695040888963407L;
/* 123 */     this.c += this.b;
/* 124 */     this.c *= this.c * 6364136223846793005L + 1442695040888963407L;
/* 125 */     this.c += this.b;
/*     */   }
/*     */   
/*     */   public void a(long paramLong1, long paramLong2) {
/* 129 */     this.d = this.c;
/* 130 */     this.d *= this.d * 6364136223846793005L + 1442695040888963407L;
/* 131 */     this.d += paramLong1;
/* 132 */     this.d *= this.d * 6364136223846793005L + 1442695040888963407L;
/* 133 */     this.d += paramLong2;
/* 134 */     this.d *= this.d * 6364136223846793005L + 1442695040888963407L;
/* 135 */     this.d += paramLong1;
/* 136 */     this.d *= this.d * 6364136223846793005L + 1442695040888963407L;
/* 137 */     this.d += paramLong2;
/*     */   }
/*     */   
/*     */   protected int a(int paramInt) {
/* 141 */     int i = (int)((this.d >> 24L) % paramInt);
/* 142 */     if (i < 0) i += paramInt; 
/* 143 */     this.d *= this.d * 6364136223846793005L + 1442695040888963407L;
/* 144 */     this.d += this.c;
/* 145 */     return i;
/*     */   }
/*     */   
/*     */   public abstract int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   protected static boolean a(int paramInt1, int paramInt2) {
/* 151 */     if (paramInt1 == paramInt2) {
/* 152 */       return true;
/*     */     }
/* 154 */     if (paramInt1 == BiomeBase.MESA_PLATEAU_F.id || paramInt1 == BiomeBase.MESA_PLATEAU.id) {
/* 155 */       return (paramInt2 == BiomeBase.MESA_PLATEAU_F.id || paramInt2 == BiomeBase.MESA_PLATEAU.id);
/*     */     }
/*     */     
/*     */     try {
/* 159 */       if (BiomeBase.getBiome(paramInt1) != null && BiomeBase.getBiome(paramInt2) != null) {
/* 160 */         return BiomeBase.getBiome(paramInt1).a(BiomeBase.getBiome(paramInt2));
/*     */       }
/* 162 */     } catch (Throwable throwable) {
/* 163 */       CrashReport crashReport = CrashReport.a(throwable, "Comparing biomes");
/* 164 */       CrashReportSystemDetails crashReportSystemDetails = crashReport.a("Biomes being compared");
/*     */       
/* 166 */       crashReportSystemDetails.a("Biome A ID", Integer.valueOf(paramInt1));
/* 167 */       crashReportSystemDetails.a("Biome B ID", Integer.valueOf(paramInt2));
/*     */       
/* 169 */       crashReportSystemDetails.a("Biome A", new CrashReportGenLayer1(paramInt1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 175 */       crashReportSystemDetails.a("Biome B", new CrashReportGenLayer2(paramInt2));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 182 */       throw new ReportedException(crashReport);
/*     */     } 
/*     */     
/* 185 */     return false;
/*     */   }
/*     */   
/*     */   protected static boolean b(int paramInt) {
/* 189 */     return (paramInt == BiomeBase.OCEAN.id || paramInt == BiomeBase.DEEP_OCEAN.id || paramInt == BiomeBase.FROZEN_OCEAN.id);
/*     */   }
/*     */   
/*     */   protected int a(int... paramVarArgs) {
/* 193 */     return paramVarArgs[a(paramVarArgs.length)];
/*     */   }
/*     */   
/*     */   protected int b(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 197 */     if (paramInt2 == paramInt3 && paramInt3 == paramInt4) return paramInt2; 
/* 198 */     if (paramInt1 == paramInt2 && paramInt1 == paramInt3) return paramInt1; 
/* 199 */     if (paramInt1 == paramInt2 && paramInt1 == paramInt4) return paramInt1; 
/* 200 */     if (paramInt1 == paramInt3 && paramInt1 == paramInt4) return paramInt1;
/*     */     
/* 202 */     if (paramInt1 == paramInt2 && paramInt3 != paramInt4) return paramInt1; 
/* 203 */     if (paramInt1 == paramInt3 && paramInt2 != paramInt4) return paramInt1; 
/* 204 */     if (paramInt1 == paramInt4 && paramInt2 != paramInt3) return paramInt1;
/*     */ 
/*     */     
/* 207 */     if (paramInt2 == paramInt3 && paramInt1 != paramInt4) return paramInt2; 
/* 208 */     if (paramInt2 == paramInt4 && paramInt1 != paramInt3) return paramInt2;
/*     */ 
/*     */ 
/*     */     
/* 212 */     if (paramInt3 == paramInt4 && paramInt1 != paramInt2) return paramInt3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     return a(new int[] { paramInt1, paramInt2, paramInt3, paramInt4 });
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenLayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */