/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenGrass
/*    */   extends WorldGenerator
/*    */ {
/*    */   private Block a;
/*    */   private int b;
/*    */   
/*    */   public WorldGenGrass(Block paramBlock, int paramInt) {
/* 15 */     this.a = paramBlock;
/* 16 */     this.b = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*    */     Block block;
/* 23 */     while (((block = paramWorld.getType(paramInt1, paramInt2, paramInt3)).getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) && paramInt2 > 0) {
/* 24 */       paramInt2--;
/*    */     }
/* 26 */     for (byte b = 0; b < ''; b++) {
/* 27 */       int i = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 28 */       int j = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 29 */       int k = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 30 */       if (paramWorld.isEmpty(i, j, k) && 
/* 31 */         this.a.j(paramWorld, i, j, k)) {
/* 32 */         paramWorld.setTypeAndData(i, j, k, this.a, this.b, 2);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenGrass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */