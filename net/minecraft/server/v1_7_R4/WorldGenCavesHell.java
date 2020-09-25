/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenCavesHell
/*     */   extends WorldGenBase
/*     */ {
/*     */   protected void a(long paramLong, int paramInt1, int paramInt2, Block[] paramArrayOfBlock, double paramDouble1, double paramDouble2, double paramDouble3) {
/*  13 */     a(paramLong, paramInt1, paramInt2, paramArrayOfBlock, paramDouble1, paramDouble2, paramDouble3, 1.0F + this.b.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
/*     */   }
/*     */   
/*     */   protected void a(long paramLong, int paramInt1, int paramInt2, Block[] paramArrayOfBlock, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt3, int paramInt4, double paramDouble4) {
/*  17 */     double d1 = (paramInt1 * 16 + 8);
/*  18 */     double d2 = (paramInt2 * 16 + 8);
/*     */     
/*  20 */     float f1 = 0.0F;
/*  21 */     float f2 = 0.0F;
/*  22 */     Random random = new Random(paramLong);
/*     */     
/*  24 */     if (paramInt4 <= 0) {
/*  25 */       int j = this.a * 16 - 16;
/*  26 */       paramInt4 = j - random.nextInt(j / 4);
/*     */     } 
/*  28 */     boolean bool1 = false;
/*     */     
/*  30 */     if (paramInt3 == -1) {
/*  31 */       paramInt3 = paramInt4 / 2;
/*  32 */       bool1 = true;
/*     */     } 
/*     */     
/*  35 */     int i = random.nextInt(paramInt4 / 2) + paramInt4 / 4;
/*  36 */     boolean bool2 = (random.nextInt(6) == 0) ? true : false;
/*     */     
/*  38 */     for (; paramInt3 < paramInt4; paramInt3++) {
/*  39 */       double d3 = 1.5D + (MathHelper.sin(paramInt3 * 3.1415927F / paramInt4) * paramFloat1 * 1.0F);
/*  40 */       double d4 = d3 * paramDouble4;
/*     */       
/*  42 */       float f3 = MathHelper.cos(paramFloat3);
/*  43 */       float f4 = MathHelper.sin(paramFloat3);
/*  44 */       paramDouble1 += (MathHelper.cos(paramFloat2) * f3);
/*  45 */       paramDouble2 += f4;
/*  46 */       paramDouble3 += (MathHelper.sin(paramFloat2) * f3);
/*     */       
/*  48 */       if (bool2) {
/*  49 */         paramFloat3 *= 0.92F;
/*     */       } else {
/*  51 */         paramFloat3 *= 0.7F;
/*     */       } 
/*  53 */       paramFloat3 += f2 * 0.1F;
/*  54 */       paramFloat2 += f1 * 0.1F;
/*     */       
/*  56 */       f2 *= 0.9F;
/*  57 */       f1 *= 0.75F;
/*  58 */       f2 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
/*  59 */       f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
/*     */       
/*  61 */       if (!bool1 && paramInt3 == i && paramFloat1 > 1.0F) {
/*  62 */         a(random.nextLong(), paramInt1, paramInt2, paramArrayOfBlock, paramDouble1, paramDouble2, paramDouble3, random.nextFloat() * 0.5F + 0.5F, paramFloat2 - 1.5707964F, paramFloat3 / 3.0F, paramInt3, paramInt4, 1.0D);
/*  63 */         a(random.nextLong(), paramInt1, paramInt2, paramArrayOfBlock, paramDouble1, paramDouble2, paramDouble3, random.nextFloat() * 0.5F + 0.5F, paramFloat2 + 1.5707964F, paramFloat3 / 3.0F, paramInt3, paramInt4, 1.0D);
/*     */         return;
/*     */       } 
/*  66 */       if (bool1 || random.nextInt(4) != 0) {
/*     */ 
/*     */         
/*  69 */         double d5 = paramDouble1 - d1;
/*  70 */         double d6 = paramDouble3 - d2;
/*  71 */         double d7 = (paramInt4 - paramInt3);
/*  72 */         double d8 = (paramFloat1 + 2.0F + 16.0F);
/*  73 */         if (d5 * d5 + d6 * d6 - d7 * d7 > d8 * d8) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  78 */         if (paramDouble1 >= d1 - 16.0D - d3 * 2.0D && paramDouble3 >= d2 - 16.0D - d3 * 2.0D && paramDouble1 <= d1 + 16.0D + d3 * 2.0D && paramDouble3 <= d2 + 16.0D + d3 * 2.0D) {
/*     */           
/*  80 */           int j = MathHelper.floor(paramDouble1 - d3) - paramInt1 * 16 - 1;
/*  81 */           int k = MathHelper.floor(paramDouble1 + d3) - paramInt1 * 16 + 1;
/*     */           
/*  83 */           int m = MathHelper.floor(paramDouble2 - d4) - 1;
/*  84 */           int n = MathHelper.floor(paramDouble2 + d4) + 1;
/*     */           
/*  86 */           int i1 = MathHelper.floor(paramDouble3 - d3) - paramInt2 * 16 - 1;
/*  87 */           int i2 = MathHelper.floor(paramDouble3 + d3) - paramInt2 * 16 + 1;
/*     */           
/*  89 */           if (j < 0) j = 0; 
/*  90 */           if (k > 16) k = 16;
/*     */           
/*  92 */           if (m < 1) m = 1; 
/*  93 */           if (n > 120) n = 120;
/*     */           
/*  95 */           if (i1 < 0) i1 = 0; 
/*  96 */           if (i2 > 16) i2 = 16;
/*     */           
/*  98 */           boolean bool = false; int i3;
/*  99 */           for (i3 = j; !bool && i3 < k; i3++) {
/* 100 */             for (int i4 = i1; !bool && i4 < i2; i4++) {
/* 101 */               for (int i5 = n + 1; !bool && i5 >= m - 1; i5--) {
/* 102 */                 int i6 = (i3 * 16 + i4) * 128 + i5;
/* 103 */                 if (i5 >= 0 && i5 < 128) {
/*     */                   
/* 105 */                   Block block = paramArrayOfBlock[i6];
/* 106 */                   if (block == Blocks.LAVA || block == Blocks.STATIONARY_LAVA) {
/* 107 */                     bool = true;
/*     */                   }
/* 109 */                   if (i5 != m - 1 && i3 != j && i3 != k - 1 && i4 != i1 && i4 != i2 - 1)
/* 110 */                     i5 = m; 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/* 115 */           if (!bool) {
/*     */             
/* 117 */             for (i3 = j; i3 < k; i3++) {
/* 118 */               double d = ((i3 + paramInt1 * 16) + 0.5D - paramDouble1) / d3;
/* 119 */               for (int i4 = i1; i4 < i2; i4++) {
/* 120 */                 double d9 = ((i4 + paramInt2 * 16) + 0.5D - paramDouble3) / d3;
/* 121 */                 int i5 = (i3 * 16 + i4) * 128 + n;
/* 122 */                 for (int i6 = n - 1; i6 >= m; i6--) {
/* 123 */                   double d10 = (i6 + 0.5D - paramDouble2) / d4;
/* 124 */                   if (d10 > -0.7D && d * d + d10 * d10 + d9 * d9 < 1.0D) {
/* 125 */                     Block block = paramArrayOfBlock[i5];
/* 126 */                     if (block == Blocks.NETHERRACK || block == Blocks.DIRT || block == Blocks.GRASS) {
/* 127 */                       paramArrayOfBlock[i5] = null;
/*     */                     }
/*     */                   } 
/* 130 */                   i5--;
/*     */                 } 
/*     */               } 
/*     */             } 
/* 134 */             if (bool1)
/*     */               break; 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }  } protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Block[] paramArrayOfBlock) {
/* 140 */     int i = this.b.nextInt(this.b.nextInt(this.b.nextInt(10) + 1) + 1);
/* 141 */     if (this.b.nextInt(5) != 0) i = 0;
/*     */     
/* 143 */     for (byte b = 0; b < i; b++) {
/* 144 */       double d1 = (paramInt1 * 16 + this.b.nextInt(16));
/* 145 */       double d2 = this.b.nextInt(128);
/* 146 */       double d3 = (paramInt2 * 16 + this.b.nextInt(16));
/*     */       
/* 148 */       int j = 1;
/* 149 */       if (this.b.nextInt(4) == 0) {
/* 150 */         a(this.b.nextLong(), paramInt3, paramInt4, paramArrayOfBlock, d1, d2, d3);
/* 151 */         j += this.b.nextInt(4);
/*     */       } 
/*     */       
/* 154 */       for (byte b1 = 0; b1 < j; b1++) {
/*     */         
/* 156 */         float f1 = this.b.nextFloat() * 3.1415927F * 2.0F;
/* 157 */         float f2 = (this.b.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 158 */         float f3 = this.b.nextFloat() * 2.0F + this.b.nextFloat();
/*     */         
/* 160 */         a(this.b.nextLong(), paramInt3, paramInt4, paramArrayOfBlock, d1, d2, d3, f3 * 2.0F, f1, f2, 0, 0, 0.5D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenCavesHell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */