/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoiseGeneratorOctaves
/*    */   extends NoiseGenerator
/*    */ {
/*    */   private NoiseGeneratorPerlin[] a;
/*    */   private int b;
/*    */   
/*    */   public NoiseGeneratorOctaves(Random paramRandom, int paramInt) {
/* 16 */     this.b = paramInt;
/* 17 */     this.a = new NoiseGeneratorPerlin[paramInt];
/* 18 */     for (byte b = 0; b < paramInt; b++) {
/* 19 */       this.a[b] = new NoiseGeneratorPerlin(paramRandom);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] a(double[] paramArrayOfdouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 49 */     if (paramArrayOfdouble == null) { paramArrayOfdouble = new double[paramInt4 * paramInt5 * paramInt6]; }
/* 50 */     else { for (byte b1 = 0; b1 < paramArrayOfdouble.length; b1++)
/* 51 */         paramArrayOfdouble[b1] = 0.0D;  }
/*    */     
/* 53 */     double d = 1.0D;
/*    */     
/* 55 */     for (byte b = 0; b < this.b; b++) {
/* 56 */       double d1 = paramInt1 * d * paramDouble1;
/* 57 */       double d2 = paramInt2 * d * paramDouble2;
/* 58 */       double d3 = paramInt3 * d * paramDouble3;
/* 59 */       long l1 = MathHelper.d(d1);
/* 60 */       long l2 = MathHelper.d(d3);
/* 61 */       d1 -= l1;
/* 62 */       d3 -= l2;
/* 63 */       l1 %= 16777216L;
/* 64 */       l2 %= 16777216L;
/* 65 */       d1 += l1;
/* 66 */       d3 += l2;
/* 67 */       this.a[b].a(paramArrayOfdouble, d1, d2, d3, paramInt4, paramInt5, paramInt6, paramDouble1 * d, paramDouble2 * d, paramDouble3 * d, d);
/* 68 */       d /= 2.0D;
/*    */     } 
/*    */     
/* 71 */     return paramArrayOfdouble;
/*    */   }
/*    */   
/*    */   public double[] a(double[] paramArrayOfdouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 75 */     return a(paramArrayOfdouble, paramInt1, 10, paramInt2, paramInt3, 1, paramInt4, paramDouble1, 1.0D, paramDouble2);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NoiseGeneratorOctaves.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */