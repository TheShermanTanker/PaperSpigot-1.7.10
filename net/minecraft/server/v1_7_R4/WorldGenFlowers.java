/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class WorldGenFlowers
/*    */   extends WorldGenerator
/*    */ {
/*    */   private Block a;
/*    */   private int b;
/*    */   
/*    */   public WorldGenFlowers(Block paramBlock) {
/* 13 */     this.a = paramBlock;
/*    */   }
/*    */   
/*    */   public void a(Block paramBlock, int paramInt) {
/* 17 */     this.a = paramBlock;
/* 18 */     this.b = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 23 */     for (byte b = 0; b < 64; b++) {
/* 24 */       int i = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 25 */       int j = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 26 */       int k = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 27 */       if (paramWorld.isEmpty(i, j, k) && (!paramWorld.worldProvider.g || j < 255) && 
/* 28 */         this.a.j(paramWorld, i, j, k)) {
/* 29 */         paramWorld.setTypeAndData(i, j, k, this.a, this.b, 2);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 34 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenFlowers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */