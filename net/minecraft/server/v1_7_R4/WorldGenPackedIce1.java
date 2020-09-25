/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenPackedIce1
/*    */   extends WorldGenerator
/*    */ {
/*    */   private Block a;
/*    */   private int b;
/*    */   
/*    */   public WorldGenPackedIce1(int paramInt) {
/* 14 */     this.a = Blocks.PACKED_ICE;
/* 15 */     this.b = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 20 */     while (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3) && paramInt2 > 2) {
/* 21 */       paramInt2--;
/*    */     }
/* 23 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3) != Blocks.SNOW_BLOCK) {
/* 24 */       return false;
/*    */     }
/* 26 */     int i = paramRandom.nextInt(this.b - 2) + 2;
/* 27 */     byte b = 1;
/* 28 */     for (int j = paramInt1 - i; j <= paramInt1 + i; j++) {
/* 29 */       for (int k = paramInt3 - i; k <= paramInt3 + i; k++) {
/* 30 */         int m = j - paramInt1;
/* 31 */         int n = k - paramInt3;
/* 32 */         if (m * m + n * n <= i * i) {
/* 33 */           for (int i1 = paramInt2 - b; i1 <= paramInt2 + b; i1++) {
/* 34 */             Block block = paramWorld.getType(j, i1, k);
/* 35 */             if (block == Blocks.DIRT || block == Blocks.SNOW_BLOCK || block == Blocks.ICE) {
/* 36 */               paramWorld.setTypeAndData(j, i1, k, this.a, 0, 2);
/*    */             }
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/* 42 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenPackedIce1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */