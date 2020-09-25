/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenCanyon
/*     */   extends WorldGenBase
/*     */ {
/*  11 */   private float[] d = new float[1024];
/*     */   
/*     */   protected void a(long paramLong, int paramInt1, int paramInt2, Block[] paramArrayOfBlock, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt3, int paramInt4, double paramDouble4) {
/*  14 */     Random random = new Random(paramLong);
/*     */     
/*  16 */     double d1 = (paramInt1 * 16 + 8);
/*  17 */     double d2 = (paramInt2 * 16 + 8);
/*     */     
/*  19 */     float f1 = 0.0F;
/*  20 */     float f2 = 0.0F;
/*     */     
/*  22 */     if (paramInt4 <= 0) {
/*  23 */       int i = this.a * 16 - 16;
/*  24 */       paramInt4 = i - random.nextInt(i / 4);
/*     */     } 
/*  26 */     boolean bool = false;
/*     */     
/*  28 */     if (paramInt3 == -1) {
/*  29 */       paramInt3 = paramInt4 / 2;
/*  30 */       bool = true;
/*     */     } 
/*     */     
/*  33 */     float f3 = 1.0F;
/*  34 */     for (byte b = 0; b < 'Ā'; b++) {
/*  35 */       if (b == 0 || random.nextInt(3) == 0) {
/*  36 */         f3 = 1.0F + random.nextFloat() * random.nextFloat() * 1.0F;
/*     */       }
/*  38 */       this.d[b] = f3 * f3;
/*     */     } 
/*     */     
/*  41 */     for (; paramInt3 < paramInt4; paramInt3++) {
/*  42 */       double d3 = 1.5D + (MathHelper.sin(paramInt3 * 3.1415927F / paramInt4) * paramFloat1 * 1.0F);
/*  43 */       double d4 = d3 * paramDouble4;
/*     */       
/*  45 */       d3 *= random.nextFloat() * 0.25D + 0.75D;
/*  46 */       d4 *= random.nextFloat() * 0.25D + 0.75D;
/*     */       
/*  48 */       float f4 = MathHelper.cos(paramFloat3);
/*  49 */       float f5 = MathHelper.sin(paramFloat3);
/*  50 */       paramDouble1 += (MathHelper.cos(paramFloat2) * f4);
/*  51 */       paramDouble2 += f5;
/*  52 */       paramDouble3 += (MathHelper.sin(paramFloat2) * f4);
/*     */       
/*  54 */       paramFloat3 *= 0.7F;
/*     */       
/*  56 */       paramFloat3 += f2 * 0.05F;
/*  57 */       paramFloat2 += f1 * 0.05F;
/*     */       
/*  59 */       f2 *= 0.8F;
/*  60 */       f1 *= 0.5F;
/*  61 */       f2 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
/*  62 */       f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
/*     */       
/*  64 */       if (bool || random.nextInt(4) != 0) {
/*     */ 
/*     */         
/*  67 */         double d5 = paramDouble1 - d1;
/*  68 */         double d6 = paramDouble3 - d2;
/*  69 */         double d7 = (paramInt4 - paramInt3);
/*  70 */         double d8 = (paramFloat1 + 2.0F + 16.0F);
/*  71 */         if (d5 * d5 + d6 * d6 - d7 * d7 > d8 * d8) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  76 */         if (paramDouble1 >= d1 - 16.0D - d3 * 2.0D && paramDouble3 >= d2 - 16.0D - d3 * 2.0D && paramDouble1 <= d1 + 16.0D + d3 * 2.0D && paramDouble3 <= d2 + 16.0D + d3 * 2.0D) {
/*     */ 
/*     */           
/*  79 */           int i = MathHelper.floor(paramDouble1 - d3) - paramInt1 * 16 - 1;
/*  80 */           int j = MathHelper.floor(paramDouble1 + d3) - paramInt1 * 16 + 1;
/*     */           
/*  82 */           int k = MathHelper.floor(paramDouble2 - d4) - 1;
/*  83 */           int m = MathHelper.floor(paramDouble2 + d4) + 1;
/*     */           
/*  85 */           int n = MathHelper.floor(paramDouble3 - d3) - paramInt2 * 16 - 1;
/*  86 */           int i1 = MathHelper.floor(paramDouble3 + d3) - paramInt2 * 16 + 1;
/*     */           
/*  88 */           if (i < 0) i = 0; 
/*  89 */           if (j > 16) j = 16;
/*     */           
/*  91 */           if (k < 1) k = 1; 
/*  92 */           if (m > 248) m = 248;
/*     */           
/*  94 */           if (n < 0) n = 0; 
/*  95 */           if (i1 > 16) i1 = 16;
/*     */           
/*  97 */           boolean bool1 = false; int i2;
/*  98 */           for (i2 = i; !bool1 && i2 < j; i2++) {
/*  99 */             for (int i3 = n; !bool1 && i3 < i1; i3++) {
/* 100 */               for (int i4 = m + 1; !bool1 && i4 >= k - 1; i4--) {
/* 101 */                 int i5 = (i2 * 16 + i3) * 256 + i4;
/* 102 */                 if (i4 >= 0 && i4 < 256) {
/*     */                   
/* 104 */                   Block block = paramArrayOfBlock[i5];
/* 105 */                   if (block == Blocks.WATER || block == Blocks.STATIONARY_WATER) {
/* 106 */                     bool1 = true;
/*     */                   }
/* 108 */                   if (i4 != k - 1 && i2 != i && i2 != j - 1 && i3 != n && i3 != i1 - 1)
/* 109 */                     i4 = k; 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/* 114 */           if (!bool1) {
/*     */             
/* 116 */             for (i2 = i; i2 < j; i2++) {
/* 117 */               double d = ((i2 + paramInt1 * 16) + 0.5D - paramDouble1) / d3;
/* 118 */               for (int i3 = n; i3 < i1; i3++) {
/* 119 */                 double d9 = ((i3 + paramInt2 * 16) + 0.5D - paramDouble3) / d3;
/* 120 */                 int i4 = (i2 * 16 + i3) * 256 + m;
/* 121 */                 boolean bool2 = false;
/* 122 */                 if (d * d + d9 * d9 < 1.0D) {
/* 123 */                   for (int i5 = m - 1; i5 >= k; i5--) {
/* 124 */                     double d10 = (i5 + 0.5D - paramDouble2) / d4;
/* 125 */                     if ((d * d + d9 * d9) * this.d[i5] + d10 * d10 / 6.0D < 1.0D) {
/* 126 */                       Block block = paramArrayOfBlock[i4];
/* 127 */                       if (block == Blocks.GRASS) bool2 = true; 
/* 128 */                       if (block == Blocks.STONE || block == Blocks.DIRT || block == Blocks.GRASS)
/* 129 */                         if (i5 < 10) {
/* 130 */                           paramArrayOfBlock[i4] = Blocks.LAVA;
/*     */                         } else {
/* 132 */                           paramArrayOfBlock[i4] = null;
/* 133 */                           if (bool2 && paramArrayOfBlock[i4 - 1] == Blocks.DIRT) {
/* 134 */                             paramArrayOfBlock[i4 - 1] = (this.c.getBiome(i2 + paramInt1 * 16, i3 + paramInt2 * 16)).ai;
/*     */                           }
/*     */                         }  
/*     */                     } 
/* 138 */                     i4--;
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             } 
/* 143 */             if (bool)
/*     */               break; 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }  } protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Block[] paramArrayOfBlock) {
/* 149 */     if (this.b.nextInt(50) != 0)
/* 150 */       return;  double d1 = (paramInt1 * 16 + this.b.nextInt(16));
/* 151 */     double d2 = (this.b.nextInt(this.b.nextInt(40) + 8) + 20);
/* 152 */     double d3 = (paramInt2 * 16 + this.b.nextInt(16));
/*     */     
/* 154 */     byte b1 = 1;
/*     */     
/* 156 */     for (byte b2 = 0; b2 < b1; b2++) {
/*     */       
/* 158 */       float f1 = this.b.nextFloat() * 3.1415927F * 2.0F;
/* 159 */       float f2 = (this.b.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 160 */       float f3 = (this.b.nextFloat() * 2.0F + this.b.nextFloat()) * 2.0F;
/*     */       
/* 162 */       a(this.b.nextLong(), paramInt3, paramInt4, paramArrayOfBlock, d1, d2, d3, f3, f1, f2, 0, 0, 3.0D);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenCanyon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */