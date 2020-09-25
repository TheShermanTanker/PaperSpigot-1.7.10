/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenClay
/*    */   extends WorldGenerator
/*    */ {
/*    */   private Block a;
/*    */   private int b;
/*    */   
/*    */   public WorldGenClay(int paramInt) {
/* 15 */     this.a = Blocks.CLAY;
/* 16 */     this.b = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 21 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3).getMaterial() != Material.WATER) return false; 
/* 22 */     int i = paramRandom.nextInt(this.b - 2) + 2;
/* 23 */     byte b = 1;
/* 24 */     for (int j = paramInt1 - i; j <= paramInt1 + i; j++) {
/* 25 */       for (int k = paramInt3 - i; k <= paramInt3 + i; k++) {
/* 26 */         int m = j - paramInt1;
/* 27 */         int n = k - paramInt3;
/* 28 */         if (m * m + n * n <= i * i) {
/* 29 */           for (int i1 = paramInt2 - b; i1 <= paramInt2 + b; i1++) {
/* 30 */             Block block = paramWorld.getType(j, i1, k);
/* 31 */             if (block == Blocks.DIRT || block == Blocks.CLAY) {
/* 32 */               paramWorld.setTypeAndData(j, i1, k, this.a, 0, 2);
/*    */             }
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/* 38 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenClay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */