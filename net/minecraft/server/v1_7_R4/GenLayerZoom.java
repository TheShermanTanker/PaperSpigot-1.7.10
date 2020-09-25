/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GenLayerZoom
/*    */   extends GenLayer
/*    */ {
/*    */   public GenLayerZoom(long paramLong, GenLayer paramGenLayer) {
/* 10 */     super(paramLong);
/* 11 */     this.a = paramGenLayer;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 16 */     int i = paramInt1 >> 1;
/* 17 */     int j = paramInt2 >> 1;
/* 18 */     int k = (paramInt3 >> 1) + 2;
/* 19 */     int m = (paramInt4 >> 1) + 2;
/* 20 */     int[] arrayOfInt1 = this.a.a(i, j, k, m);
/*    */     
/* 22 */     int n = k - 1 << 1;
/* 23 */     int i1 = m - 1 << 1;
/*    */     
/* 25 */     int[] arrayOfInt2 = IntCache.a(n * i1);
/*    */     
/* 27 */     for (byte b1 = 0; b1 < m - 1; b1++) {
/* 28 */       int i2 = (b1 << 1) * n;
/*    */       
/* 30 */       byte b = 0;
/* 31 */       int i3 = arrayOfInt1[b + 0 + (b1 + 0) * k];
/* 32 */       int i4 = arrayOfInt1[b + 0 + (b1 + 1) * k];
/* 33 */       for (; b < k - 1; b++) {
/* 34 */         a((b + i << 1), (b1 + j << 1));
/*    */         
/* 36 */         int i5 = arrayOfInt1[b + 1 + (b1 + 0) * k];
/* 37 */         int i6 = arrayOfInt1[b + 1 + (b1 + 1) * k];
/*    */         
/* 39 */         arrayOfInt2[i2] = i3;
/* 40 */         arrayOfInt2[i2++ + n] = a(new int[] { i3, i4 });
/* 41 */         arrayOfInt2[i2] = a(new int[] { i3, i5 });
/* 42 */         arrayOfInt2[i2++ + n] = b(i3, i5, i4, i6);
/*    */         
/* 44 */         i3 = i5;
/* 45 */         i4 = i6;
/*    */       } 
/*    */     } 
/*    */     
/* 49 */     int[] arrayOfInt3 = IntCache.a(paramInt3 * paramInt4);
/* 50 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/* 51 */       System.arraycopy(arrayOfInt2, (b2 + (paramInt2 & 0x1)) * n + (paramInt1 & 0x1), arrayOfInt3, b2 * paramInt3, paramInt3);
/*    */     }
/* 53 */     return arrayOfInt3;
/*    */   }
/*    */   
/*    */   public static GenLayer b(long paramLong, GenLayer paramGenLayer, int paramInt) {
/* 57 */     GenLayer genLayer = paramGenLayer;
/* 58 */     for (byte b = 0; b < paramInt; b++) {
/* 59 */       genLayer = new GenLayerZoom(paramLong + b, genLayer);
/*    */     }
/* 61 */     return genLayer;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenLayerZoom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */