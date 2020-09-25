/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class GenLayerRiver
/*    */   extends GenLayer
/*    */ {
/*    */   public GenLayerRiver(long paramLong, GenLayer paramGenLayer) {
/*  8 */     super(paramLong);
/*  9 */     this.a = paramGenLayer;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 14 */     int i = paramInt1 - 1;
/* 15 */     int j = paramInt2 - 1;
/* 16 */     int k = paramInt3 + 2;
/* 17 */     int m = paramInt4 + 2;
/* 18 */     int[] arrayOfInt1 = this.a.a(i, j, k, m);
/*    */     
/* 20 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/* 21 */     for (byte b = 0; b < paramInt4; b++) {
/* 22 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/* 23 */         int n = c(arrayOfInt1[b1 + 0 + (b + 1) * k]);
/* 24 */         int i1 = c(arrayOfInt1[b1 + 2 + (b + 1) * k]);
/* 25 */         int i2 = c(arrayOfInt1[b1 + 1 + (b + 0) * k]);
/* 26 */         int i3 = c(arrayOfInt1[b1 + 1 + (b + 2) * k]);
/* 27 */         int i4 = c(arrayOfInt1[b1 + 1 + (b + 1) * k]);
/* 28 */         if (i4 != n || i4 != i2 || i4 != i1 || i4 != i3) {
/* 29 */           arrayOfInt2[b1 + b * paramInt3] = BiomeBase.RIVER.id;
/*    */         } else {
/* 31 */           arrayOfInt2[b1 + b * paramInt3] = -1;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 36 */     return arrayOfInt2;
/*    */   }
/*    */   
/*    */   private int c(int paramInt) {
/* 40 */     if (paramInt >= 2) {
/* 41 */       return 2 + (paramInt & 0x1);
/*    */     }
/* 43 */     return paramInt;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenLayerRiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */