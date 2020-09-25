/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GenLayerZoomVoronoi
/*    */   extends GenLayer
/*    */ {
/*    */   public GenLayerZoomVoronoi(long paramLong, GenLayer paramGenLayer) {
/* 11 */     super(paramLong);
/* 12 */     this.a = paramGenLayer;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 17 */     paramInt1 -= 2;
/* 18 */     paramInt2 -= 2;
/* 19 */     int i = paramInt1 >> 2;
/* 20 */     int j = paramInt2 >> 2;
/* 21 */     int k = (paramInt3 >> 2) + 2;
/* 22 */     int m = (paramInt4 >> 2) + 2;
/* 23 */     int[] arrayOfInt1 = this.a.a(i, j, k, m);
/*    */     
/* 25 */     int n = k - 1 << 2;
/* 26 */     int i1 = m - 1 << 2;
/*    */     
/* 28 */     int[] arrayOfInt2 = IntCache.a(n * i1);
/* 29 */     for (byte b1 = 0; b1 < m - 1; b1++) {
/*    */       
/* 31 */       byte b = 0;
/* 32 */       int i2 = arrayOfInt1[b + 0 + (b1 + 0) * k];
/* 33 */       int i3 = arrayOfInt1[b + 0 + (b1 + 1) * k];
/* 34 */       for (; b < k - 1; b++) {
/* 35 */         double d1 = 3.6D;
/* 36 */         a((b + i << 2), (b1 + j << 2));
/* 37 */         double d2 = (a(1024) / 1024.0D - 0.5D) * 3.6D;
/* 38 */         double d3 = (a(1024) / 1024.0D - 0.5D) * 3.6D;
/*    */         
/* 40 */         a((b + i + 1 << 2), (b1 + j << 2));
/* 41 */         double d4 = (a(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
/* 42 */         double d5 = (a(1024) / 1024.0D - 0.5D) * 3.6D;
/*    */         
/* 44 */         a((b + i << 2), (b1 + j + 1 << 2));
/* 45 */         double d6 = (a(1024) / 1024.0D - 0.5D) * 3.6D;
/* 46 */         double d7 = (a(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
/*    */         
/* 48 */         a((b + i + 1 << 2), (b1 + j + 1 << 2));
/* 49 */         double d8 = (a(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
/* 50 */         double d9 = (a(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
/*    */         
/* 52 */         int i4 = arrayOfInt1[b + 1 + (b1 + 0) * k] & 0xFF;
/* 53 */         int i5 = arrayOfInt1[b + 1 + (b1 + 1) * k] & 0xFF;
/*    */         
/* 55 */         for (byte b3 = 0; b3 < 4; b3++) {
/* 56 */           int i6 = ((b1 << 2) + b3) * n + (b << 2);
/* 57 */           for (byte b4 = 0; b4 < 4; b4++) {
/* 58 */             double d10 = (b3 - d3) * (b3 - d3) + (b4 - d2) * (b4 - d2);
/* 59 */             double d11 = (b3 - d5) * (b3 - d5) + (b4 - d4) * (b4 - d4);
/* 60 */             double d12 = (b3 - d7) * (b3 - d7) + (b4 - d6) * (b4 - d6);
/* 61 */             double d13 = (b3 - d9) * (b3 - d9) + (b4 - d8) * (b4 - d8);
/*    */             
/* 63 */             if (d10 < d11 && d10 < d12 && d10 < d13) {
/* 64 */               arrayOfInt2[i6++] = i2;
/* 65 */             } else if (d11 < d10 && d11 < d12 && d11 < d13) {
/* 66 */               arrayOfInt2[i6++] = i4;
/* 67 */             } else if (d12 < d10 && d12 < d11 && d12 < d13) {
/* 68 */               arrayOfInt2[i6++] = i3;
/*    */             } else {
/* 70 */               arrayOfInt2[i6++] = i5;
/*    */             } 
/*    */           } 
/*    */         } 
/*    */         
/* 75 */         i2 = i4;
/* 76 */         i3 = i5;
/*    */       } 
/*    */     } 
/*    */     
/* 80 */     int[] arrayOfInt3 = IntCache.a(paramInt3 * paramInt4);
/* 81 */     for (byte b2 = 0; b2 < paramInt4; b2++) {
/* 82 */       System.arraycopy(arrayOfInt2, (b2 + (paramInt2 & 0x3)) * n + (paramInt1 & 0x3), arrayOfInt3, b2 * paramInt3, paramInt3);
/*    */     }
/* 84 */     return arrayOfInt3;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenLayerZoomVoronoi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */