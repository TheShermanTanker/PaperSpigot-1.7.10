/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class GenLayerSmooth extends GenLayer {
/*    */   public GenLayerSmooth(long paramLong, GenLayer paramGenLayer) {
/*  5 */     super(paramLong);
/*  6 */     this.a = paramGenLayer;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 11 */     int i = paramInt1 - 1;
/* 12 */     int j = paramInt2 - 1;
/* 13 */     int k = paramInt3 + 2;
/* 14 */     int m = paramInt4 + 2;
/* 15 */     int[] arrayOfInt1 = this.a.a(i, j, k, m);
/*    */     
/* 17 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/* 18 */     for (byte b = 0; b < paramInt4; b++) {
/* 19 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/* 20 */         int n = arrayOfInt1[b1 + 0 + (b + 1) * k];
/* 21 */         int i1 = arrayOfInt1[b1 + 2 + (b + 1) * k];
/* 22 */         int i2 = arrayOfInt1[b1 + 1 + (b + 0) * k];
/* 23 */         int i3 = arrayOfInt1[b1 + 1 + (b + 2) * k];
/* 24 */         int i4 = arrayOfInt1[b1 + 1 + (b + 1) * k];
/* 25 */         if (n == i1 && i2 == i3) {
/* 26 */           a((b1 + paramInt1), (b + paramInt2));
/* 27 */           if (a(2) == 0) { i4 = n; }
/* 28 */           else { i4 = i2; }
/*    */         
/*    */         } else {
/* 31 */           if (n == i1) i4 = n; 
/* 32 */           if (i2 == i3) i4 = i2; 
/*    */         } 
/* 34 */         arrayOfInt2[b1 + b * paramInt3] = i4;
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 39 */     return arrayOfInt2;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenLayerSmooth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */