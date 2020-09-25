/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class GenLayerDesert
/*    */   extends GenLayer
/*    */ {
/*    */   public GenLayerDesert(long paramLong, GenLayer paramGenLayer) {
/*  8 */     super(paramLong);
/*  9 */     this.a = paramGenLayer;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 14 */     int[] arrayOfInt1 = this.a.a(paramInt1 - 1, paramInt2 - 1, paramInt3 + 2, paramInt4 + 2);
/*    */     
/* 16 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/* 17 */     for (byte b = 0; b < paramInt4; b++) {
/* 18 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/* 19 */         a((b1 + paramInt1), (b + paramInt2));
/* 20 */         int i = arrayOfInt1[b1 + 1 + (b + 1) * (paramInt3 + 2)];
/* 21 */         if (!a(arrayOfInt1, arrayOfInt2, b1, b, paramInt3, i, BiomeBase.EXTREME_HILLS.id, BiomeBase.SMALL_MOUNTAINS.id) && 
/* 22 */           !b(arrayOfInt1, arrayOfInt2, b1, b, paramInt3, i, BiomeBase.MESA_PLATEAU_F.id, BiomeBase.MESA.id) && 
/* 23 */           !b(arrayOfInt1, arrayOfInt2, b1, b, paramInt3, i, BiomeBase.MESA_PLATEAU.id, BiomeBase.MESA.id) && 
/* 24 */           !b(arrayOfInt1, arrayOfInt2, b1, b, paramInt3, i, BiomeBase.MEGA_TAIGA.id, BiomeBase.TAIGA.id)) {
/* 25 */           if (i == BiomeBase.DESERT.id) {
/* 26 */             int j = arrayOfInt1[b1 + 1 + (b + 1 - 1) * (paramInt3 + 2)];
/* 27 */             int k = arrayOfInt1[b1 + 1 + 1 + (b + 1) * (paramInt3 + 2)];
/* 28 */             int m = arrayOfInt1[b1 + 1 - 1 + (b + 1) * (paramInt3 + 2)];
/* 29 */             int n = arrayOfInt1[b1 + 1 + (b + 1 + 1) * (paramInt3 + 2)];
/* 30 */             if (j == BiomeBase.ICE_PLAINS.id || k == BiomeBase.ICE_PLAINS.id || m == BiomeBase.ICE_PLAINS.id || n == BiomeBase.ICE_PLAINS.id) {
/* 31 */               arrayOfInt2[b1 + b * paramInt3] = BiomeBase.EXTREME_HILLS_PLUS.id;
/*    */             } else {
/* 33 */               arrayOfInt2[b1 + b * paramInt3] = i;
/*    */             } 
/* 35 */           } else if (i == BiomeBase.SWAMPLAND.id) {
/*    */             
/* 37 */             int j = arrayOfInt1[b1 + 1 + (b + 1 - 1) * (paramInt3 + 2)];
/* 38 */             int k = arrayOfInt1[b1 + 1 + 1 + (b + 1) * (paramInt3 + 2)];
/* 39 */             int m = arrayOfInt1[b1 + 1 - 1 + (b + 1) * (paramInt3 + 2)];
/* 40 */             int n = arrayOfInt1[b1 + 1 + (b + 1 + 1) * (paramInt3 + 2)];
/* 41 */             if (j == BiomeBase.DESERT.id || k == BiomeBase.DESERT.id || m == BiomeBase.DESERT.id || n == BiomeBase.DESERT.id || j == BiomeBase.COLD_TAIGA.id || k == BiomeBase.COLD_TAIGA.id || m == BiomeBase.COLD_TAIGA.id || n == BiomeBase.COLD_TAIGA.id || j == BiomeBase.ICE_PLAINS.id || k == BiomeBase.ICE_PLAINS.id || m == BiomeBase.ICE_PLAINS.id || n == BiomeBase.ICE_PLAINS.id) {
/*    */               
/* 43 */               arrayOfInt2[b1 + b * paramInt3] = BiomeBase.PLAINS.id;
/* 44 */             } else if (j == BiomeBase.JUNGLE.id || n == BiomeBase.JUNGLE.id || k == BiomeBase.JUNGLE.id || m == BiomeBase.JUNGLE.id) {
/* 45 */               arrayOfInt2[b1 + b * paramInt3] = BiomeBase.JUNGLE_EDGE.id;
/*    */             } else {
/* 47 */               arrayOfInt2[b1 + b * paramInt3] = i;
/*    */             } 
/*    */           } else {
/* 50 */             arrayOfInt2[b1 + b * paramInt3] = i;
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/* 55 */     return arrayOfInt2;
/*    */   }
/*    */   
/*    */   private boolean a(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 59 */     if (a(paramInt4, paramInt5)) {
/* 60 */       int i = paramArrayOfint1[paramInt1 + 1 + (paramInt2 + 1 - 1) * (paramInt3 + 2)];
/* 61 */       int j = paramArrayOfint1[paramInt1 + 1 + 1 + (paramInt2 + 1) * (paramInt3 + 2)];
/* 62 */       int k = paramArrayOfint1[paramInt1 + 1 - 1 + (paramInt2 + 1) * (paramInt3 + 2)];
/* 63 */       int m = paramArrayOfint1[paramInt1 + 1 + (paramInt2 + 1 + 1) * (paramInt3 + 2)];
/* 64 */       if (!b(i, paramInt5) || !b(j, paramInt5) || !b(k, paramInt5) || !b(m, paramInt5)) {
/* 65 */         paramArrayOfint2[paramInt1 + paramInt2 * paramInt3] = paramInt6;
/*    */       } else {
/* 67 */         paramArrayOfint2[paramInt1 + paramInt2 * paramInt3] = paramInt4;
/*    */       } 
/* 69 */       return true;
/*    */     } 
/* 71 */     return false;
/*    */   }
/*    */   
/*    */   private boolean b(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 75 */     if (paramInt4 == paramInt5) {
/* 76 */       int i = paramArrayOfint1[paramInt1 + 1 + (paramInt2 + 1 - 1) * (paramInt3 + 2)];
/* 77 */       int j = paramArrayOfint1[paramInt1 + 1 + 1 + (paramInt2 + 1) * (paramInt3 + 2)];
/* 78 */       int k = paramArrayOfint1[paramInt1 + 1 - 1 + (paramInt2 + 1) * (paramInt3 + 2)];
/* 79 */       int m = paramArrayOfint1[paramInt1 + 1 + (paramInt2 + 1 + 1) * (paramInt3 + 2)];
/* 80 */       if (!a(i, paramInt5) || !a(j, paramInt5) || !a(k, paramInt5) || !a(m, paramInt5)) {
/* 81 */         paramArrayOfint2[paramInt1 + paramInt2 * paramInt3] = paramInt6;
/*    */       } else {
/* 83 */         paramArrayOfint2[paramInt1 + paramInt2 * paramInt3] = paramInt4;
/*    */       } 
/* 85 */       return true;
/*    */     } 
/* 87 */     return false;
/*    */   }
/*    */   
/*    */   private boolean b(int paramInt1, int paramInt2) {
/* 91 */     if (a(paramInt1, paramInt2)) {
/* 92 */       return true;
/*    */     }
/* 94 */     if (BiomeBase.getBiome(paramInt1) != null && BiomeBase.getBiome(paramInt2) != null) {
/* 95 */       EnumTemperature enumTemperature1 = BiomeBase.getBiome(paramInt1).m();
/* 96 */       EnumTemperature enumTemperature2 = BiomeBase.getBiome(paramInt2).m();
/* 97 */       return (enumTemperature1 == enumTemperature2 || enumTemperature1 == EnumTemperature.MEDIUM || enumTemperature2 == EnumTemperature.MEDIUM);
/*    */     } 
/* 99 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenLayerDesert.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */