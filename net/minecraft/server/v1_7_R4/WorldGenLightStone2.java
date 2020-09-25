/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenLightStone2
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 13 */     if (!paramWorld.isEmpty(paramInt1, paramInt2, paramInt3)) return false; 
/* 14 */     if (paramWorld.getType(paramInt1, paramInt2 + 1, paramInt3) != Blocks.NETHERRACK) return false; 
/* 15 */     paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, Blocks.GLOWSTONE, 0, 2);
/*    */     
/* 17 */     for (byte b = 0; b < 'ל'; b++) {
/* 18 */       int i = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 19 */       int j = paramInt2 - paramRandom.nextInt(12);
/* 20 */       int k = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 21 */       if (paramWorld.getType(i, j, k).getMaterial() == Material.AIR) {
/*    */         
/* 23 */         byte b1 = 0;
/* 24 */         for (byte b2 = 0; b2 < 6; b2++) {
/* 25 */           Block block = null;
/* 26 */           if (b2 == 0) block = paramWorld.getType(i - 1, j, k); 
/* 27 */           if (b2 == 1) block = paramWorld.getType(i + 1, j, k); 
/* 28 */           if (b2 == 2) block = paramWorld.getType(i, j - 1, k); 
/* 29 */           if (b2 == 3) block = paramWorld.getType(i, j + 1, k); 
/* 30 */           if (b2 == 4) block = paramWorld.getType(i, j, k - 1); 
/* 31 */           if (b2 == 5) block = paramWorld.getType(i, j, k + 1);
/*    */           
/* 33 */           if (block == Blocks.GLOWSTONE) b1++;
/*    */         
/*    */         } 
/* 36 */         if (b1 == 1) paramWorld.setTypeAndData(i, j, k, Blocks.GLOWSTONE, 0, 2); 
/*    */       } 
/*    */     } 
/* 39 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenLightStone2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */