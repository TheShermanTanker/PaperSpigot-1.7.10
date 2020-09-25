/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class GenLayerMushroomIsland
/*    */   extends GenLayer
/*    */ {
/*    */   public GenLayerMushroomIsland(long paramLong, GenLayer paramGenLayer) {
/*  7 */     super(paramLong);
/*  8 */     this.a = paramGenLayer;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 13 */     int i = paramInt1 - 1;
/* 14 */     int j = paramInt2 - 1;
/* 15 */     int k = paramInt3 + 2;
/* 16 */     int m = paramInt4 + 2;
/* 17 */     int[] arrayOfInt1 = this.a.a(i, j, k, m);
/*    */     
/* 19 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/* 20 */     for (byte b = 0; b < paramInt4; b++) {
/* 21 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/* 22 */         int n = arrayOfInt1[b1 + 0 + (b + 0) * k];
/* 23 */         int i1 = arrayOfInt1[b1 + 2 + (b + 0) * k];
/* 24 */         int i2 = arrayOfInt1[b1 + 0 + (b + 2) * k];
/* 25 */         int i3 = arrayOfInt1[b1 + 2 + (b + 2) * k];
/* 26 */         int i4 = arrayOfInt1[b1 + 1 + (b + 1) * k];
/* 27 */         a((b1 + paramInt1), (b + paramInt2));
/* 28 */         if (i4 == 0 && n == 0 && i1 == 0 && i2 == 0 && i3 == 0 && a(100) == 0) {
/* 29 */           arrayOfInt2[b1 + b * paramInt3] = BiomeBase.MUSHROOM_ISLAND.id;
/*    */         } else {
/* 31 */           arrayOfInt2[b1 + b * paramInt3] = i4;
/*    */         } 
/*    */       } 
/*    */     } 
/* 35 */     return arrayOfInt2;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenLayerMushroomIsland.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */