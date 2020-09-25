/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenVines
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 14 */     int i = paramInt1;
/* 15 */     int j = paramInt3;
/*    */     
/* 17 */     while (paramInt2 < 128) {
/* 18 */       if (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3)) {
/* 19 */         for (byte b = 2; b <= 5; b++) {
/* 20 */           if (Blocks.VINE.canPlace(paramWorld, paramInt1, paramInt2, paramInt3, b)) {
/* 21 */             paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, Blocks.VINE, 1 << Direction.e[Facing.OPPOSITE_FACING[b]], 2);
/*    */             break;
/*    */           } 
/*    */         } 
/*    */       } else {
/* 26 */         paramInt1 = i + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 27 */         paramInt3 = j + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/*    */       } 
/* 29 */       paramInt2++;
/*    */     } 
/*    */     
/* 32 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenVines.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */