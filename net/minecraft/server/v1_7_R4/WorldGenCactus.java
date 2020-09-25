/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenCactus
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 12 */     for (byte b = 0; b < 10; b++) {
/* 13 */       int i = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 14 */       int j = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 15 */       int k = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 16 */       if (paramWorld.isEmpty(i, j, k)) {
/* 17 */         int m = 1 + paramRandom.nextInt(paramRandom.nextInt(3) + 1);
/* 18 */         for (byte b1 = 0; b1 < m; b1++) {
/* 19 */           if (Blocks.CACTUS.j(paramWorld, i, j + b1, k)) {
/* 20 */             paramWorld.setTypeAndData(i, j + b1, k, Blocks.CACTUS, 0, 2);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 26 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenCactus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */