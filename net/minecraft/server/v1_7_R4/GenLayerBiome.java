/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class GenLayerBiome
/*    */   extends GenLayer
/*    */ {
/*  7 */   private BiomeBase[] c = new BiomeBase[] { BiomeBase.DESERT, BiomeBase.DESERT, BiomeBase.DESERT, BiomeBase.SAVANNA, BiomeBase.SAVANNA, BiomeBase.PLAINS };
/*    */ 
/*    */ 
/*    */   
/* 11 */   private BiomeBase[] d = new BiomeBase[] { BiomeBase.FOREST, BiomeBase.ROOFED_FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.PLAINS, BiomeBase.BIRCH_FOREST, BiomeBase.SWAMPLAND };
/*    */ 
/*    */ 
/*    */   
/* 15 */   private BiomeBase[] e = new BiomeBase[] { BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.TAIGA, BiomeBase.PLAINS };
/*    */ 
/*    */ 
/*    */   
/* 19 */   private BiomeBase[] f = new BiomeBase[] { BiomeBase.ICE_PLAINS, BiomeBase.ICE_PLAINS, BiomeBase.ICE_PLAINS, BiomeBase.COLD_TAIGA };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GenLayerBiome(long paramLong, GenLayer paramGenLayer, WorldType paramWorldType) {
/* 25 */     super(paramLong);
/* 26 */     this.a = paramGenLayer;
/*    */     
/* 28 */     if (paramWorldType == WorldType.NORMAL_1_1) {
/* 29 */       this.c = new BiomeBase[] { BiomeBase.DESERT, BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.SWAMPLAND, BiomeBase.PLAINS, BiomeBase.TAIGA };
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 37 */     int[] arrayOfInt1 = this.a.a(paramInt1, paramInt2, paramInt3, paramInt4);
/*    */     
/* 39 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/* 40 */     for (byte b = 0; b < paramInt4; b++) {
/* 41 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/* 42 */         a((b1 + paramInt1), (b + paramInt2));
/* 43 */         int i = arrayOfInt1[b1 + b * paramInt3];
/* 44 */         int j = (i & 0xF00) >> 8;
/* 45 */         i &= 0xFFFFF0FF;
/* 46 */         if (b(i)) {
/* 47 */           arrayOfInt2[b1 + b * paramInt3] = i;
/* 48 */         } else if (i == BiomeBase.MUSHROOM_ISLAND.id) {
/* 49 */           arrayOfInt2[b1 + b * paramInt3] = i;
/* 50 */         } else if (i == 1) {
/* 51 */           if (j > 0) {
/* 52 */             if (a(3) == 0) {
/* 53 */               arrayOfInt2[b1 + b * paramInt3] = BiomeBase.MESA_PLATEAU.id;
/*    */             } else {
/* 55 */               arrayOfInt2[b1 + b * paramInt3] = BiomeBase.MESA_PLATEAU_F.id;
/*    */             } 
/*    */           } else {
/* 58 */             arrayOfInt2[b1 + b * paramInt3] = (this.c[a(this.c.length)]).id;
/*    */           } 
/* 60 */         } else if (i == 2) {
/* 61 */           if (j > 0) {
/* 62 */             arrayOfInt2[b1 + b * paramInt3] = BiomeBase.JUNGLE.id;
/*    */           } else {
/* 64 */             arrayOfInt2[b1 + b * paramInt3] = (this.d[a(this.d.length)]).id;
/*    */           } 
/* 66 */         } else if (i == 3) {
/* 67 */           if (j > 0) {
/* 68 */             arrayOfInt2[b1 + b * paramInt3] = BiomeBase.MEGA_TAIGA.id;
/*    */           } else {
/* 70 */             arrayOfInt2[b1 + b * paramInt3] = (this.e[a(this.e.length)]).id;
/*    */           } 
/* 72 */         } else if (i == 4) {
/* 73 */           arrayOfInt2[b1 + b * paramInt3] = (this.f[a(this.f.length)]).id;
/*    */         } else {
/* 75 */           arrayOfInt2[b1 + b * paramInt3] = BiomeBase.MUSHROOM_ISLAND.id;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 80 */     return arrayOfInt2;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenLayerBiome.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */