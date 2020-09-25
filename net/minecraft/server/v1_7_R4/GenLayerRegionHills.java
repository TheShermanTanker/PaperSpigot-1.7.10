/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GenLayerRegionHills
/*     */   extends GenLayer {
/*   8 */   private static final Logger c = LogManager.getLogger();
/*     */   private GenLayer d;
/*     */   
/*     */   public GenLayerRegionHills(long paramLong, GenLayer paramGenLayer1, GenLayer paramGenLayer2) {
/*  12 */     super(paramLong);
/*  13 */     this.a = paramGenLayer1;
/*  14 */     this.d = paramGenLayer2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  19 */     int[] arrayOfInt1 = this.a.a(paramInt1 - 1, paramInt2 - 1, paramInt3 + 2, paramInt4 + 2);
/*  20 */     int[] arrayOfInt2 = this.d.a(paramInt1 - 1, paramInt2 - 1, paramInt3 + 2, paramInt4 + 2);
/*     */     
/*  22 */     int[] arrayOfInt3 = IntCache.a(paramInt3 * paramInt4);
/*  23 */     for (byte b = 0; b < paramInt4; b++) {
/*  24 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/*  25 */         a((b1 + paramInt1), (b + paramInt2));
/*  26 */         int i = arrayOfInt1[b1 + 1 + (b + 1) * (paramInt3 + 2)];
/*  27 */         int j = arrayOfInt2[b1 + 1 + (b + 1) * (paramInt3 + 2)];
/*  28 */         boolean bool = ((j - 2) % 29 == 0) ? true : false;
/*  29 */         if (i > 255) {
/*  30 */           c.debug("old! " + i);
/*     */         }
/*  32 */         if (i != 0 && j >= 2 && (j - 2) % 29 == 1 && i < 128) {
/*  33 */           if (BiomeBase.getBiome(i + 128) != null) {
/*  34 */             arrayOfInt3[b1 + b * paramInt3] = i + 128;
/*     */           } else {
/*  36 */             arrayOfInt3[b1 + b * paramInt3] = i;
/*     */           } 
/*  38 */         } else if (a(3) == 0 || bool) {
/*  39 */           int k = i;
/*  40 */           if (i == BiomeBase.DESERT.id) {
/*  41 */             k = BiomeBase.DESERT_HILLS.id;
/*  42 */           } else if (i == BiomeBase.FOREST.id) {
/*  43 */             k = BiomeBase.FOREST_HILLS.id;
/*  44 */           } else if (i == BiomeBase.BIRCH_FOREST.id) {
/*  45 */             k = BiomeBase.BIRCH_FOREST_HILLS.id;
/*  46 */           } else if (i == BiomeBase.ROOFED_FOREST.id) {
/*  47 */             k = BiomeBase.PLAINS.id;
/*  48 */           } else if (i == BiomeBase.TAIGA.id) {
/*  49 */             k = BiomeBase.TAIGA_HILLS.id;
/*  50 */           } else if (i == BiomeBase.MEGA_TAIGA.id) {
/*  51 */             k = BiomeBase.MEGA_TAIGA_HILLS.id;
/*  52 */           } else if (i == BiomeBase.COLD_TAIGA.id) {
/*  53 */             k = BiomeBase.COLD_TAIGA_HILLS.id;
/*  54 */           } else if (i == BiomeBase.PLAINS.id) {
/*  55 */             if (a(3) == 0) {
/*  56 */               k = BiomeBase.FOREST_HILLS.id;
/*     */             } else {
/*  58 */               k = BiomeBase.FOREST.id;
/*     */             } 
/*  60 */           } else if (i == BiomeBase.ICE_PLAINS.id) {
/*  61 */             k = BiomeBase.ICE_MOUNTAINS.id;
/*  62 */           } else if (i == BiomeBase.JUNGLE.id) {
/*  63 */             k = BiomeBase.JUNGLE_HILLS.id;
/*  64 */           } else if (i == BiomeBase.OCEAN.id) {
/*  65 */             k = BiomeBase.DEEP_OCEAN.id;
/*  66 */           } else if (i == BiomeBase.EXTREME_HILLS.id) {
/*  67 */             k = BiomeBase.EXTREME_HILLS_PLUS.id;
/*  68 */           } else if (i == BiomeBase.SAVANNA.id) {
/*  69 */             k = BiomeBase.SAVANNA_PLATEAU.id;
/*  70 */           } else if (a(i, BiomeBase.MESA_PLATEAU_F.id)) {
/*  71 */             k = BiomeBase.MESA.id;
/*  72 */           } else if (i == BiomeBase.DEEP_OCEAN.id && 
/*  73 */             a(3) == 0) {
/*  74 */             int m = a(2);
/*  75 */             if (m == 0) {
/*  76 */               k = BiomeBase.PLAINS.id;
/*     */             } else {
/*  78 */               k = BiomeBase.FOREST.id;
/*     */             } 
/*     */           } 
/*     */           
/*  82 */           if (bool && k != i) {
/*  83 */             if (BiomeBase.getBiome(k + 128) != null) {
/*  84 */               k += 128;
/*     */             } else {
/*  86 */               k = i;
/*     */             } 
/*     */           }
/*  89 */           if (k == i) {
/*  90 */             arrayOfInt3[b1 + b * paramInt3] = i;
/*     */           } else {
/*  92 */             int m = arrayOfInt1[b1 + 1 + (b + 1 - 1) * (paramInt3 + 2)];
/*  93 */             int n = arrayOfInt1[b1 + 1 + 1 + (b + 1) * (paramInt3 + 2)];
/*  94 */             int i1 = arrayOfInt1[b1 + 1 - 1 + (b + 1) * (paramInt3 + 2)];
/*  95 */             int i2 = arrayOfInt1[b1 + 1 + (b + 1 + 1) * (paramInt3 + 2)];
/*  96 */             byte b2 = 0;
/*  97 */             if (a(m, i)) {
/*  98 */               b2++;
/*     */             }
/* 100 */             if (a(n, i)) {
/* 101 */               b2++;
/*     */             }
/* 103 */             if (a(i1, i)) {
/* 104 */               b2++;
/*     */             }
/* 106 */             if (a(i2, i)) {
/* 107 */               b2++;
/*     */             }
/* 109 */             if (b2 >= 3) {
/* 110 */               arrayOfInt3[b1 + b * paramInt3] = k;
/*     */             } else {
/* 112 */               arrayOfInt3[b1 + b * paramInt3] = i;
/*     */             } 
/*     */           } 
/*     */         } else {
/* 116 */           arrayOfInt3[b1 + b * paramInt3] = i;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     return arrayOfInt3;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenLayerRegionHills.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */