/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenMinable
/*    */   extends WorldGenerator
/*    */ {
/*    */   private Block a;
/*    */   private int b;
/*    */   private Block c;
/*    */   
/*    */   public WorldGenMinable(Block paramBlock, int paramInt) {
/* 16 */     this(paramBlock, paramInt, Blocks.STONE);
/*    */   }
/*    */   
/*    */   public WorldGenMinable(Block paramBlock1, int paramInt, Block paramBlock2) {
/* 20 */     this.a = paramBlock1;
/* 21 */     this.b = paramInt;
/* 22 */     this.c = paramBlock2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 28 */     float f = paramRandom.nextFloat() * 3.1415927F;
/*    */     
/* 30 */     double d1 = ((paramInt1 + 8) + MathHelper.sin(f) * this.b / 8.0F);
/* 31 */     double d2 = ((paramInt1 + 8) - MathHelper.sin(f) * this.b / 8.0F);
/* 32 */     double d3 = ((paramInt3 + 8) + MathHelper.cos(f) * this.b / 8.0F);
/* 33 */     double d4 = ((paramInt3 + 8) - MathHelper.cos(f) * this.b / 8.0F);
/*    */     
/* 35 */     double d5 = (paramInt2 + paramRandom.nextInt(3) - 2);
/* 36 */     double d6 = (paramInt2 + paramRandom.nextInt(3) - 2);
/*    */     
/* 38 */     for (byte b = 0; b <= this.b; b++) {
/* 39 */       double d7 = d1 + (d2 - d1) * b / this.b;
/* 40 */       double d8 = d5 + (d6 - d5) * b / this.b;
/* 41 */       double d9 = d3 + (d4 - d3) * b / this.b;
/*    */       
/* 43 */       double d10 = paramRandom.nextDouble() * this.b / 16.0D;
/* 44 */       double d11 = (MathHelper.sin(b * 3.1415927F / this.b) + 1.0F) * d10 + 1.0D;
/* 45 */       double d12 = (MathHelper.sin(b * 3.1415927F / this.b) + 1.0F) * d10 + 1.0D;
/*    */       
/* 47 */       int i = MathHelper.floor(d7 - d11 / 2.0D);
/* 48 */       int j = MathHelper.floor(d8 - d12 / 2.0D);
/* 49 */       int k = MathHelper.floor(d9 - d11 / 2.0D);
/*    */       
/* 51 */       int m = MathHelper.floor(d7 + d11 / 2.0D);
/* 52 */       int n = MathHelper.floor(d8 + d12 / 2.0D);
/* 53 */       int i1 = MathHelper.floor(d9 + d11 / 2.0D);
/*    */       
/* 55 */       for (int i2 = i; i2 <= m; i2++) {
/* 56 */         double d = (i2 + 0.5D - d7) / d11 / 2.0D;
/* 57 */         if (d * d < 1.0D) {
/* 58 */           for (int i3 = j; i3 <= n; i3++) {
/* 59 */             double d13 = (i3 + 0.5D - d8) / d12 / 2.0D;
/* 60 */             if (d * d + d13 * d13 < 1.0D) {
/* 61 */               for (int i4 = k; i4 <= i1; i4++) {
/* 62 */                 double d14 = (i4 + 0.5D - d9) / d11 / 2.0D;
/* 63 */                 if (d * d + d13 * d13 + d14 * d14 < 1.0D && 
/* 64 */                   paramWorld.getType(i2, i3, i4) == this.c) paramWorld.setTypeAndData(i2, i3, i4, this.a, 0, 2);
/*    */               
/*    */               } 
/*    */             }
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 73 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenMinable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */