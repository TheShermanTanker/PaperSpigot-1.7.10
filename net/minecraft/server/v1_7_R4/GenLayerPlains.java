/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class GenLayerPlains
/*    */   extends GenLayer
/*    */ {
/*    */   public GenLayerPlains(long paramLong, GenLayer paramGenLayer) {
/*  7 */     super(paramLong);
/*  8 */     this.a = paramGenLayer;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 13 */     int[] arrayOfInt1 = this.a.a(paramInt1 - 1, paramInt2 - 1, paramInt3 + 2, paramInt4 + 2);
/*    */     
/* 15 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/* 16 */     for (byte b = 0; b < paramInt4; b++) {
/* 17 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/* 18 */         a((b1 + paramInt1), (b + paramInt2));
/* 19 */         int i = arrayOfInt1[b1 + 1 + (b + 1) * (paramInt3 + 2)];
/* 20 */         if (a(57) == 0) {
/* 21 */           if (i == BiomeBase.PLAINS.id) {
/* 22 */             arrayOfInt2[b1 + b * paramInt3] = BiomeBase.PLAINS.id + 128;
/*    */           } else {
/* 24 */             arrayOfInt2[b1 + b * paramInt3] = i;
/*    */           } 
/*    */         } else {
/* 27 */           arrayOfInt2[b1 + b * paramInt3] = i;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 32 */     return arrayOfInt2;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenLayerPlains.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */