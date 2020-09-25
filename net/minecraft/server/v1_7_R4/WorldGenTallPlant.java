/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenTallPlant
/*    */   extends WorldGenerator
/*    */ {
/*    */   private int a;
/*    */   
/*    */   public void a(int paramInt) {
/* 15 */     this.a = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 20 */     boolean bool = false;
/*    */     
/* 22 */     for (byte b = 0; b < 64; b++) {
/* 23 */       int i = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 24 */       int j = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 25 */       int k = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 26 */       if (paramWorld.isEmpty(i, j, k) && (!paramWorld.worldProvider.g || j < 254) && 
/* 27 */         Blocks.DOUBLE_PLANT.canPlace(paramWorld, i, j, k)) {
/* 28 */         Blocks.DOUBLE_PLANT.c(paramWorld, i, j, k, this.a, 2);
/* 29 */         bool = true;
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 34 */     return bool;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenTallPlant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */