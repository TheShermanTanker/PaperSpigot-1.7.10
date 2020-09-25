/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class GenLayerTopSoil
/*    */   extends GenLayer {
/*    */   public GenLayerTopSoil(long paramLong, GenLayer paramGenLayer) {
/*  6 */     super(paramLong);
/*  7 */     this.a = paramGenLayer;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 12 */     int i = paramInt1 - 1;
/* 13 */     int j = paramInt2 - 1;
/* 14 */     int k = paramInt3 + 2;
/* 15 */     int m = paramInt4 + 2;
/* 16 */     int[] arrayOfInt1 = this.a.a(i, j, k, m);
/*    */     
/* 18 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/* 19 */     for (byte b = 0; b < paramInt4; b++) {
/* 20 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/* 21 */         int n = arrayOfInt1[b1 + 1 + (b + 1) * k];
/* 22 */         a((b1 + paramInt1), (b + paramInt2));
/* 23 */         if (n == 0) {
/* 24 */           arrayOfInt2[b1 + b * paramInt3] = 0;
/*    */         } else {
/* 26 */           int i1 = a(6);
/* 27 */           if (i1 == 0) { i1 = 4; }
/* 28 */           else if (i1 <= 1) { i1 = 3; }
/* 29 */           else { i1 = 1; }
/* 30 */            arrayOfInt2[b1 + b * paramInt3] = i1;
/*    */         } 
/*    */       } 
/*    */     } 
/* 34 */     return arrayOfInt2;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenLayerTopSoil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */