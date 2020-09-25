/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ public class GenLayerMushroomShore
/*     */   extends GenLayer
/*     */ {
/*     */   public GenLayerMushroomShore(long paramLong, GenLayer paramGenLayer) {
/*   7 */     super(paramLong);
/*   8 */     this.a = paramGenLayer;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  13 */     int[] arrayOfInt1 = this.a.a(paramInt1 - 1, paramInt2 - 1, paramInt3 + 2, paramInt4 + 2);
/*     */     
/*  15 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/*  16 */     for (byte b = 0; b < paramInt4; b++) {
/*  17 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/*  18 */         a((b1 + paramInt1), (b + paramInt2));
/*  19 */         int i = arrayOfInt1[b1 + 1 + (b + 1) * (paramInt3 + 2)];
/*  20 */         BiomeBase biomeBase = BiomeBase.getBiome(i);
/*  21 */         if (i == BiomeBase.MUSHROOM_ISLAND.id) {
/*  22 */           int j = arrayOfInt1[b1 + 1 + (b + 1 - 1) * (paramInt3 + 2)];
/*  23 */           int k = arrayOfInt1[b1 + 1 + 1 + (b + 1) * (paramInt3 + 2)];
/*  24 */           int m = arrayOfInt1[b1 + 1 - 1 + (b + 1) * (paramInt3 + 2)];
/*  25 */           int n = arrayOfInt1[b1 + 1 + (b + 1 + 1) * (paramInt3 + 2)];
/*  26 */           if (j == BiomeBase.OCEAN.id || k == BiomeBase.OCEAN.id || m == BiomeBase.OCEAN.id || n == BiomeBase.OCEAN.id) {
/*  27 */             arrayOfInt2[b1 + b * paramInt3] = BiomeBase.MUSHROOM_SHORE.id;
/*     */           } else {
/*  29 */             arrayOfInt2[b1 + b * paramInt3] = i;
/*     */           } 
/*  31 */         } else if (biomeBase != null && biomeBase.l() == BiomeJungle.class) {
/*  32 */           int j = arrayOfInt1[b1 + 1 + (b + 1 - 1) * (paramInt3 + 2)];
/*  33 */           int k = arrayOfInt1[b1 + 1 + 1 + (b + 1) * (paramInt3 + 2)];
/*  34 */           int m = arrayOfInt1[b1 + 1 - 1 + (b + 1) * (paramInt3 + 2)];
/*  35 */           int n = arrayOfInt1[b1 + 1 + (b + 1 + 1) * (paramInt3 + 2)];
/*  36 */           if (!c(j) || !c(k) || !c(m) || !c(n)) {
/*  37 */             arrayOfInt2[b1 + b * paramInt3] = BiomeBase.JUNGLE_EDGE.id;
/*  38 */           } else if (b(j) || b(k) || b(m) || b(n)) {
/*  39 */             arrayOfInt2[b1 + b * paramInt3] = BiomeBase.BEACH.id;
/*     */           } else {
/*  41 */             arrayOfInt2[b1 + b * paramInt3] = i;
/*     */           } 
/*  43 */         } else if (i == BiomeBase.EXTREME_HILLS.id || i == BiomeBase.EXTREME_HILLS_PLUS.id || i == BiomeBase.SMALL_MOUNTAINS.id) {
/*  44 */           a(arrayOfInt1, arrayOfInt2, b1, b, paramInt3, i, BiomeBase.STONE_BEACH.id);
/*  45 */         } else if (biomeBase != null && biomeBase.j()) {
/*  46 */           a(arrayOfInt1, arrayOfInt2, b1, b, paramInt3, i, BiomeBase.COLD_BEACH.id);
/*  47 */         } else if (i == BiomeBase.MESA.id || i == BiomeBase.MESA_PLATEAU_F.id) {
/*  48 */           int j = arrayOfInt1[b1 + 1 + (b + 1 - 1) * (paramInt3 + 2)];
/*  49 */           int k = arrayOfInt1[b1 + 1 + 1 + (b + 1) * (paramInt3 + 2)];
/*  50 */           int m = arrayOfInt1[b1 + 1 - 1 + (b + 1) * (paramInt3 + 2)];
/*  51 */           int n = arrayOfInt1[b1 + 1 + (b + 1 + 1) * (paramInt3 + 2)];
/*  52 */           if (b(j) || b(k) || b(m) || b(n)) {
/*  53 */             arrayOfInt2[b1 + b * paramInt3] = i;
/*  54 */           } else if (!d(j) || !d(k) || !d(m) || !d(n)) {
/*  55 */             arrayOfInt2[b1 + b * paramInt3] = BiomeBase.DESERT.id;
/*     */           } else {
/*  57 */             arrayOfInt2[b1 + b * paramInt3] = i;
/*     */           } 
/*  59 */         } else if (i != BiomeBase.OCEAN.id && i != BiomeBase.DEEP_OCEAN.id && i != BiomeBase.RIVER.id && i != BiomeBase.SWAMPLAND.id) {
/*  60 */           int j = arrayOfInt1[b1 + 1 + (b + 1 - 1) * (paramInt3 + 2)];
/*  61 */           int k = arrayOfInt1[b1 + 1 + 1 + (b + 1) * (paramInt3 + 2)];
/*  62 */           int m = arrayOfInt1[b1 + 1 - 1 + (b + 1) * (paramInt3 + 2)];
/*  63 */           int n = arrayOfInt1[b1 + 1 + (b + 1 + 1) * (paramInt3 + 2)];
/*  64 */           if (b(j) || b(k) || b(m) || b(n)) {
/*  65 */             arrayOfInt2[b1 + b * paramInt3] = BiomeBase.BEACH.id;
/*     */           } else {
/*  67 */             arrayOfInt2[b1 + b * paramInt3] = i;
/*     */           } 
/*     */         } else {
/*     */           
/*  71 */           arrayOfInt2[b1 + b * paramInt3] = i;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  77 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */   private void a(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  81 */     if (b(paramInt4)) {
/*  82 */       paramArrayOfint2[paramInt1 + paramInt2 * paramInt3] = paramInt4;
/*     */       return;
/*     */     } 
/*  85 */     int i = paramArrayOfint1[paramInt1 + 1 + (paramInt2 + 1 - 1) * (paramInt3 + 2)];
/*  86 */     int j = paramArrayOfint1[paramInt1 + 1 + 1 + (paramInt2 + 1) * (paramInt3 + 2)];
/*  87 */     int k = paramArrayOfint1[paramInt1 + 1 - 1 + (paramInt2 + 1) * (paramInt3 + 2)];
/*  88 */     int m = paramArrayOfint1[paramInt1 + 1 + (paramInt2 + 1 + 1) * (paramInt3 + 2)];
/*  89 */     if (b(i) || b(j) || b(k) || b(m)) {
/*  90 */       paramArrayOfint2[paramInt1 + paramInt2 * paramInt3] = paramInt5;
/*     */     } else {
/*  92 */       paramArrayOfint2[paramInt1 + paramInt2 * paramInt3] = paramInt4;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean c(int paramInt) {
/*  97 */     if (BiomeBase.getBiome(paramInt) != null && BiomeBase.getBiome(paramInt).l() == BiomeJungle.class) {
/*  98 */       return true;
/*     */     }
/*     */     
/* 101 */     return (paramInt == BiomeBase.JUNGLE_EDGE.id || paramInt == BiomeBase.JUNGLE.id || paramInt == BiomeBase.JUNGLE_HILLS.id || paramInt == BiomeBase.FOREST.id || paramInt == BiomeBase.TAIGA.id || b(paramInt));
/*     */   }
/*     */   
/*     */   private boolean d(int paramInt) {
/* 105 */     return (BiomeBase.getBiome(paramInt) != null && BiomeBase.getBiome(paramInt) instanceof BiomeMesa);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenLayerMushroomShore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */