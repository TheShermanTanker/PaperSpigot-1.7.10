/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenDeadBush
/*    */   extends WorldGenerator
/*    */ {
/*    */   private Block a;
/*    */   
/*    */   public WorldGenDeadBush(Block paramBlock) {
/* 14 */     this.a = paramBlock;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*    */     Block block;
/* 20 */     while (((block = paramWorld.getType(paramInt1, paramInt2, paramInt3)).getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) && paramInt2 > 0) {
/* 21 */       paramInt2--;
/*    */     }
/* 23 */     for (byte b = 0; b < 4; b++) {
/* 24 */       int i = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 25 */       int j = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 26 */       int k = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 27 */       if (paramWorld.isEmpty(i, j, k) && 
/* 28 */         this.a.j(paramWorld, i, j, k)) {
/* 29 */         paramWorld.setTypeAndData(i, j, k, this.a, 0, 2);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 34 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenDeadBush.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */