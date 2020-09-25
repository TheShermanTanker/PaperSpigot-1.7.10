/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class GenLayerRiverMix
/*    */   extends GenLayer
/*    */ {
/*    */   private GenLayer c;
/*    */   private GenLayer d;
/*    */   
/*    */   public GenLayerRiverMix(long paramLong, GenLayer paramGenLayer1, GenLayer paramGenLayer2) {
/* 10 */     super(paramLong);
/* 11 */     this.c = paramGenLayer1;
/* 12 */     this.d = paramGenLayer2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(long paramLong) {
/* 17 */     this.c.a(paramLong);
/* 18 */     this.d.a(paramLong);
/* 19 */     super.a(paramLong);
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 24 */     int[] arrayOfInt1 = this.c.a(paramInt1, paramInt2, paramInt3, paramInt4);
/* 25 */     int[] arrayOfInt2 = this.d.a(paramInt1, paramInt2, paramInt3, paramInt4);
/*    */     
/* 27 */     int[] arrayOfInt3 = IntCache.a(paramInt3 * paramInt4);
/* 28 */     for (byte b = 0; b < paramInt3 * paramInt4; b++) {
/* 29 */       if (arrayOfInt1[b] == BiomeBase.OCEAN.id || arrayOfInt1[b] == BiomeBase.DEEP_OCEAN.id)
/* 30 */       { arrayOfInt3[b] = arrayOfInt1[b]; }
/*    */       
/* 32 */       else if (arrayOfInt2[b] == BiomeBase.RIVER.id)
/* 33 */       { if (arrayOfInt1[b] == BiomeBase.ICE_PLAINS.id) { arrayOfInt3[b] = BiomeBase.FROZEN_RIVER.id; }
/* 34 */         else if (arrayOfInt1[b] == BiomeBase.MUSHROOM_ISLAND.id || arrayOfInt1[b] == BiomeBase.MUSHROOM_SHORE.id) { arrayOfInt3[b] = BiomeBase.MUSHROOM_SHORE.id; }
/* 35 */         else { arrayOfInt3[b] = arrayOfInt2[b] & 0xFF; }
/*    */          }
/* 37 */       else { arrayOfInt3[b] = arrayOfInt1[b]; }
/*    */     
/*    */     } 
/*    */ 
/*    */     
/* 42 */     return arrayOfInt3;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenLayerRiverMix.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */