/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class GenLayerIsland
/*    */   extends GenLayer {
/*    */   public GenLayerIsland(long paramLong, GenLayer paramGenLayer) {
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
/* 21 */         int n = arrayOfInt1[b1 + 0 + (b + 0) * k];
/* 22 */         int i1 = arrayOfInt1[b1 + 2 + (b + 0) * k];
/* 23 */         int i2 = arrayOfInt1[b1 + 0 + (b + 2) * k];
/* 24 */         int i3 = arrayOfInt1[b1 + 2 + (b + 2) * k];
/* 25 */         int i4 = arrayOfInt1[b1 + 1 + (b + 1) * k];
/* 26 */         a((b1 + paramInt1), (b + paramInt2));
/* 27 */         if (i4 == 0 && (n != 0 || i1 != 0 || i2 != 0 || i3 != 0))
/* 28 */         { byte b2 = 1;
/* 29 */           int i5 = 1;
/* 30 */           if (n != 0 && a(b2++) == 0) i5 = n; 
/* 31 */           if (i1 != 0 && a(b2++) == 0) i5 = i1; 
/* 32 */           if (i2 != 0 && a(b2++) == 0) i5 = i2; 
/* 33 */           if (i3 != 0 && a(b2++) == 0) i5 = i3; 
/* 34 */           if (a(3) == 0)
/* 35 */           { arrayOfInt2[b1 + b * paramInt3] = i5; }
/*    */           
/* 37 */           else if (i5 == 4) { arrayOfInt2[b1 + b * paramInt3] = 4; }
/* 38 */           else { arrayOfInt2[b1 + b * paramInt3] = 0; }
/*    */            }
/* 40 */         else if (i4 > 0 && (n == 0 || i1 == 0 || i2 == 0 || i3 == 0))
/* 41 */         { if (a(5) == 0)
/* 42 */           { if (i4 == 4) { arrayOfInt2[b1 + b * paramInt3] = 4; }
/* 43 */             else { arrayOfInt2[b1 + b * paramInt3] = 0; }  }
/* 44 */           else { arrayOfInt2[b1 + b * paramInt3] = i4; }
/*    */            }
/* 46 */         else { arrayOfInt2[b1 + b * paramInt3] = i4; }
/*    */       
/*    */       } 
/*    */     } 
/* 50 */     return arrayOfInt2;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenLayerIsland.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */