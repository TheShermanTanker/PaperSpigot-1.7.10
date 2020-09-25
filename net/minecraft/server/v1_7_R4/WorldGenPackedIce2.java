/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class WorldGenPackedIce2
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World world, Random random, int i, int j, int k) {
/* 10 */     while (world.isEmpty(i, j, k) && j > 2) {
/* 11 */       j--;
/*    */     }
/*    */     
/* 14 */     if (world.getType(i, j, k) != Blocks.SNOW_BLOCK) {
/* 15 */       return false;
/*    */     }
/* 17 */     j += random.nextInt(4);
/* 18 */     int l = random.nextInt(4) + 7;
/* 19 */     int i1 = l / 4 + random.nextInt(2);
/*    */     
/* 21 */     if (i1 > 1 && random.nextInt(60) == 0) {
/* 22 */       j += 10 + random.nextInt(30);
/*    */     }
/*    */ 
/*    */     
/*    */     int j1;
/*    */ 
/*    */     
/* 29 */     for (j1 = 0; j1 < l; j1++) {
/* 30 */       float f = (1.0F - j1 / l) * i1;
/*    */       
/* 32 */       int k1 = MathHelper.f(f);
/*    */       
/* 34 */       for (int l1 = -k1; l1 <= k1; l1++) {
/* 35 */         float f1 = MathHelper.a(l1) - 0.25F;
/*    */         
/* 37 */         for (int i2 = -k1; i2 <= k1; i2++) {
/* 38 */           float f2 = MathHelper.a(i2) - 0.25F;
/*    */           
/* 40 */           if (((l1 == 0 && i2 == 0) || f1 * f1 + f2 * f2 <= f * f) && ((l1 != -k1 && l1 != k1 && i2 != -k1 && i2 != k1) || random.nextFloat() <= 0.75F)) {
/* 41 */             Block block = world.getType(i + l1, j + j1, k + i2);
/*    */             
/* 43 */             if (block.getMaterial() == Material.AIR || block == Blocks.DIRT || block == Blocks.SNOW_BLOCK || block == Blocks.ICE) {
/* 44 */               world.setTypeUpdate(i + l1, j + j1, k + i2, Blocks.PACKED_ICE);
/*    */             }
/*    */             
/* 47 */             if (j1 != 0 && k1 > 1) {
/* 48 */               block = world.getType(i + l1, j - j1, k + i2);
/* 49 */               if (block.getMaterial() == Material.AIR || block == Blocks.DIRT || block == Blocks.SNOW_BLOCK || block == Blocks.ICE) {
/* 50 */                 world.setTypeUpdate(i + l1, j - j1, k + i2, Blocks.PACKED_ICE);
/*    */               }
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 58 */     j1 = i1 - 1;
/* 59 */     if (j1 < 0) {
/* 60 */       j1 = 0;
/* 61 */     } else if (j1 > 1) {
/* 62 */       j1 = 1;
/*    */     } 
/*    */     
/* 65 */     for (int j2 = -j1; j2 <= j1; j2++) {
/* 66 */       int k1 = -j1;
/*    */       
/* 68 */       while (k1 <= j1) {
/* 69 */         int l1 = j - 1;
/* 70 */         int k2 = 50;
/*    */         
/* 72 */         if (Math.abs(j2) == 1 && Math.abs(k1) == 1) {
/* 73 */           k2 = random.nextInt(5);
/*    */         }
/*    */ 
/*    */         
/* 77 */         while (l1 > 50) {
/* 78 */           Block block1 = world.getType(i + j2, l1, k + k1);
/*    */           
/* 80 */           if (block1.getMaterial() == Material.AIR || block1 == Blocks.DIRT || block1 == Blocks.SNOW_BLOCK || block1 == Blocks.ICE || block1 == Blocks.PACKED_ICE) {
/* 81 */             world.setTypeUpdate(i + j2, l1, k + k1, Blocks.PACKED_ICE);
/* 82 */             l1--;
/* 83 */             k2--;
/* 84 */             if (k2 <= 0) {
/* 85 */               l1 -= random.nextInt(5) + 1;
/* 86 */               k2 = random.nextInt(5);
/*    */             } 
/*    */           } 
/*    */         } 
/*    */ 
/*    */         
/* 92 */         k1++;
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 98 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenPackedIce2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */