/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GenLayerIcePlains
/*    */   extends GenLayer
/*    */ {
/*    */   public GenLayerIcePlains(long paramLong, GenLayer paramGenLayer) {
/*  9 */     super(paramLong);
/* 10 */     this.a = paramGenLayer;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 15 */     int i = paramInt1 - 1;
/* 16 */     int j = paramInt2 - 1;
/* 17 */     int k = paramInt3 + 2;
/* 18 */     int m = paramInt4 + 2;
/* 19 */     int[] arrayOfInt1 = this.a.a(i, j, k, m);
/*    */     
/* 21 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/* 22 */     for (byte b = 0; b < paramInt4; b++) {
/* 23 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/* 24 */         int n = arrayOfInt1[b1 + 1 + (b + 1 - 1) * (paramInt3 + 2)];
/* 25 */         int i1 = arrayOfInt1[b1 + 1 + 1 + (b + 1) * (paramInt3 + 2)];
/* 26 */         int i2 = arrayOfInt1[b1 + 1 - 1 + (b + 1) * (paramInt3 + 2)];
/* 27 */         int i3 = arrayOfInt1[b1 + 1 + (b + 1 + 1) * (paramInt3 + 2)];
/*    */         
/* 29 */         int i4 = arrayOfInt1[b1 + 1 + (b + 1) * k];
/* 30 */         arrayOfInt2[b1 + b * paramInt3] = i4;
/* 31 */         a((b1 + paramInt1), (b + paramInt2));
/* 32 */         if (i4 == 0 && n == 0 && i1 == 0 && i2 == 0 && i3 == 0 && a(2) == 0) {
/* 33 */           arrayOfInt2[b1 + b * paramInt3] = 1;
/*    */         }
/*    */       } 
/*    */     } 
/* 37 */     return arrayOfInt2;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenLayerIcePlains.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */