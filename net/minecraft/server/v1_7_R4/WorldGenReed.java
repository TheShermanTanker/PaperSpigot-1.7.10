/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenReed
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 13 */     for (byte b = 0; b < 20; b++) {
/* 14 */       int i = paramInt1 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 15 */       int j = paramInt2;
/* 16 */       int k = paramInt3 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 17 */       if (paramWorld.isEmpty(i, j, k) && (
/* 18 */         paramWorld.getType(i - 1, j - 1, k).getMaterial() == Material.WATER || paramWorld.getType(i + 1, j - 1, k).getMaterial() == Material.WATER || paramWorld.getType(i, j - 1, k - 1).getMaterial() == Material.WATER || paramWorld.getType(i, j - 1, k + 1).getMaterial() == Material.WATER)) {
/*    */ 
/*    */         
/* 21 */         int m = 2 + paramRandom.nextInt(paramRandom.nextInt(3) + 1);
/* 22 */         for (byte b1 = 0; b1 < m; b1++) {
/* 23 */           if (Blocks.SUGAR_CANE_BLOCK.j(paramWorld, i, j + b1, k)) {
/* 24 */             paramWorld.setTypeAndData(i, j + b1, k, Blocks.SUGAR_CANE_BLOCK, 0, 2);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 31 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenReed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */