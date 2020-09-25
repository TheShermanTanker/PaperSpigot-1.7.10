/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenLakes
/*     */   extends WorldGenerator
/*     */ {
/*     */   private Block a;
/*     */   
/*     */   public WorldGenLakes(Block paramBlock) {
/*  15 */     this.a = paramBlock;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*  20 */     paramInt1 -= 8;
/*  21 */     paramInt3 -= 8;
/*  22 */     while (paramInt2 > 5 && paramWorld.isEmpty(paramInt1, paramInt2, paramInt3))
/*  23 */       paramInt2--; 
/*  24 */     if (paramInt2 <= 4) {
/*  25 */       return false;
/*     */     }
/*     */     
/*  28 */     paramInt2 -= 4;
/*     */     
/*  30 */     boolean[] arrayOfBoolean = new boolean[2048];
/*     */     
/*  32 */     int i = paramRandom.nextInt(4) + 4; byte b;
/*  33 */     for (b = 0; b < i; b++) {
/*  34 */       double d1 = paramRandom.nextDouble() * 6.0D + 3.0D;
/*  35 */       double d2 = paramRandom.nextDouble() * 4.0D + 2.0D;
/*  36 */       double d3 = paramRandom.nextDouble() * 6.0D + 3.0D;
/*     */       
/*  38 */       double d4 = paramRandom.nextDouble() * (16.0D - d1 - 2.0D) + 1.0D + d1 / 2.0D;
/*  39 */       double d5 = paramRandom.nextDouble() * (8.0D - d2 - 4.0D) + 2.0D + d2 / 2.0D;
/*  40 */       double d6 = paramRandom.nextDouble() * (16.0D - d3 - 2.0D) + 1.0D + d3 / 2.0D;
/*     */       
/*  42 */       for (byte b1 = 1; b1 < 15; b1++) {
/*  43 */         for (byte b2 = 1; b2 < 15; b2++) {
/*  44 */           for (byte b3 = 1; b3 < 7; b3++) {
/*  45 */             double d7 = (b1 - d4) / d1 / 2.0D;
/*  46 */             double d8 = (b3 - d5) / d2 / 2.0D;
/*  47 */             double d9 = (b2 - d6) / d3 / 2.0D;
/*  48 */             double d10 = d7 * d7 + d8 * d8 + d9 * d9;
/*  49 */             if (d10 < 1.0D) arrayOfBoolean[(b1 * 16 + b2) * 8 + b3] = true;
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  55 */     for (b = 0; b < 16; b++) {
/*  56 */       for (byte b1 = 0; b1 < 16; b1++) {
/*  57 */         for (byte b2 = 0; b2 < 8; b2++) {
/*  58 */           boolean bool = (!arrayOfBoolean[(b * 16 + b1) * 8 + b2] && ((b < 15 && arrayOfBoolean[((b + 1) * 16 + b1) * 8 + b2]) || (b > 0 && arrayOfBoolean[((b - 1) * 16 + b1) * 8 + b2]) || (b1 < 15 && arrayOfBoolean[(b * 16 + b1 + 1) * 8 + b2]) || (b1 > 0 && arrayOfBoolean[(b * 16 + b1 - 1) * 8 + b2]) || (b2 < 7 && arrayOfBoolean[(b * 16 + b1) * 8 + b2 + 1]) || (b2 > 0 && arrayOfBoolean[(b * 16 + b1) * 8 + b2 - 1]))) ? true : false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  65 */           if (bool) {
/*  66 */             Material material = paramWorld.getType(paramInt1 + b, paramInt2 + b2, paramInt3 + b1).getMaterial();
/*  67 */             if (b2 >= 4 && material.isLiquid()) return false; 
/*  68 */             if (b2 < 4 && !material.isBuildable() && paramWorld.getType(paramInt1 + b, paramInt2 + b2, paramInt3 + b1) != this.a) return false;
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     for (b = 0; b < 16; b++) {
/*  76 */       for (byte b1 = 0; b1 < 16; b1++) {
/*  77 */         for (byte b2 = 0; b2 < 8; b2++) {
/*  78 */           if (arrayOfBoolean[(b * 16 + b1) * 8 + b2]) {
/*  79 */             paramWorld.setTypeAndData(paramInt1 + b, paramInt2 + b2, paramInt3 + b1, (b2 >= 4) ? Blocks.AIR : this.a, 0, 2);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     for (b = 0; b < 16; b++) {
/*  86 */       for (byte b1 = 0; b1 < 16; b1++) {
/*  87 */         for (byte b2 = 4; b2 < 8; b2++) {
/*  88 */           if (arrayOfBoolean[(b * 16 + b1) * 8 + b2] && 
/*  89 */             paramWorld.getType(paramInt1 + b, paramInt2 + b2 - 1, paramInt3 + b1) == Blocks.DIRT && paramWorld.b(EnumSkyBlock.SKY, paramInt1 + b, paramInt2 + b2, paramInt3 + b1) > 0) {
/*  90 */             BiomeBase biomeBase = paramWorld.getBiome(paramInt1 + b, paramInt3 + b1);
/*  91 */             if (biomeBase.ai == Blocks.MYCEL) { paramWorld.setTypeAndData(paramInt1 + b, paramInt2 + b2 - 1, paramInt3 + b1, Blocks.MYCEL, 0, 2); }
/*  92 */             else { paramWorld.setTypeAndData(paramInt1 + b, paramInt2 + b2 - 1, paramInt3 + b1, Blocks.GRASS, 0, 2); }
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  99 */     if (this.a.getMaterial() == Material.LAVA) {
/* 100 */       for (b = 0; b < 16; b++) {
/* 101 */         for (byte b1 = 0; b1 < 16; b1++) {
/* 102 */           for (byte b2 = 0; b2 < 8; b2++) {
/* 103 */             boolean bool = (!arrayOfBoolean[(b * 16 + b1) * 8 + b2] && ((b < 15 && arrayOfBoolean[((b + 1) * 16 + b1) * 8 + b2]) || (b > 0 && arrayOfBoolean[((b - 1) * 16 + b1) * 8 + b2]) || (b1 < 15 && arrayOfBoolean[(b * 16 + b1 + 1) * 8 + b2]) || (b1 > 0 && arrayOfBoolean[(b * 16 + b1 - 1) * 8 + b2]) || (b2 < 7 && arrayOfBoolean[(b * 16 + b1) * 8 + b2 + 1]) || (b2 > 0 && arrayOfBoolean[(b * 16 + b1) * 8 + b2 - 1]))) ? true : false;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 108 */             if (bool && (
/* 109 */               b2 < 4 || paramRandom.nextInt(2) != 0) && paramWorld.getType(paramInt1 + b, paramInt2 + b2, paramInt3 + b1).getMaterial().isBuildable()) {
/* 110 */               paramWorld.setTypeAndData(paramInt1 + b, paramInt2 + b2, paramInt3 + b1, Blocks.STONE, 0, 2);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 118 */     if (this.a.getMaterial() == Material.WATER) {
/* 119 */       for (b = 0; b < 16; b++) {
/* 120 */         for (byte b1 = 0; b1 < 16; b1++) {
/* 121 */           byte b2 = 4;
/* 122 */           if (paramWorld.r(paramInt1 + b, paramInt2 + b2, paramInt3 + b1)) paramWorld.setTypeAndData(paramInt1 + b, paramInt2 + b2, paramInt3 + b1, Blocks.ICE, 0, 2);
/*     */         
/*     */         } 
/*     */       } 
/*     */     }
/* 127 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenLakes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */