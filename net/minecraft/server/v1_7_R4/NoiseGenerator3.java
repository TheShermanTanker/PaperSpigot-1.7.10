/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoiseGenerator3
/*    */   extends NoiseGenerator
/*    */ {
/*    */   private NoiseGenerator3Handler[] a;
/*    */   private int b;
/*    */   
/*    */   public NoiseGenerator3(Random paramRandom, int paramInt) {
/* 14 */     this.b = paramInt;
/* 15 */     this.a = new NoiseGenerator3Handler[paramInt];
/* 16 */     for (byte b = 0; b < paramInt; b++) {
/* 17 */       this.a[b] = new NoiseGenerator3Handler(paramRandom);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public double a(double paramDouble1, double paramDouble2) {
/* 23 */     double d1 = 0.0D;
/* 24 */     double d2 = 1.0D;
/*    */     
/* 26 */     for (byte b = 0; b < this.b; b++) {
/* 27 */       d1 += this.a[b].a(paramDouble1 * d2, paramDouble2 * d2) / d2;
/* 28 */       d2 /= 2.0D;
/*    */     } 
/*    */     
/* 31 */     return d1;
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
/*    */   public double[] a(double[] paramArrayOfdouble, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, double paramDouble3, double paramDouble4, double paramDouble5) {
/* 47 */     return a(paramArrayOfdouble, paramDouble1, paramDouble2, paramInt1, paramInt2, paramDouble3, paramDouble4, paramDouble5, 0.5D);
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] a(double[] paramArrayOfdouble, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/* 52 */     if (paramArrayOfdouble == null || paramArrayOfdouble.length < paramInt1 * paramInt2) { paramArrayOfdouble = new double[paramInt1 * paramInt2]; }
/* 53 */     else { for (byte b1 = 0; b1 < paramArrayOfdouble.length; b1++)
/* 54 */         paramArrayOfdouble[b1] = 0.0D;  }
/*    */     
/* 56 */     double d1 = 1.0D;
/* 57 */     double d2 = 1.0D;
/* 58 */     for (byte b = 0; b < this.b; b++) {
/* 59 */       this.a[b].a(paramArrayOfdouble, paramDouble1, paramDouble2, paramInt1, paramInt2, paramDouble3 * d2 * d1, paramDouble4 * d2 * d1, 0.55D / d1);
/* 60 */       d2 *= paramDouble5;
/* 61 */       d1 *= paramDouble6;
/*    */     } 
/*    */     
/* 64 */     return paramArrayOfdouble;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NoiseGenerator3.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */