/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenFire
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 12 */     for (byte b = 0; b < 64; b++) {
/* 13 */       int i = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 14 */       int j = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 15 */       int k = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 16 */       if (paramWorld.isEmpty(i, j, k) && 
/* 17 */         paramWorld.getType(i, j - 1, k) == Blocks.NETHERRACK) {
/* 18 */         paramWorld.setTypeAndData(i, j, k, Blocks.FIRE, 0, 2);
/*    */       }
/*    */     } 
/* 21 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenFire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */