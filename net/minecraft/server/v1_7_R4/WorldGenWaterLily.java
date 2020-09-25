/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenWaterLily
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 14 */     for (byte b = 0; b < 10; b++) {
/* 15 */       int i = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 16 */       int j = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 17 */       int k = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 18 */       if (paramWorld.isEmpty(i, j, k) && 
/* 19 */         Blocks.WATER_LILY.canPlace(paramWorld, i, j, k)) {
/* 20 */         paramWorld.setTypeAndData(i, j, k, Blocks.WATER_LILY, 0, 2);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 25 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenWaterLily.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */