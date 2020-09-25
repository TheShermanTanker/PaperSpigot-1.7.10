/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenDesertWell
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 12 */     while (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3) && paramInt2 > 2) {
/* 13 */       paramInt2--;
/*    */     }
/* 15 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3) != Blocks.SAND) {
/* 16 */       return false;
/*    */     }
/*    */     
/*    */     byte b;
/* 20 */     for (b = -2; b <= 2; b++) {
/* 21 */       for (byte b1 = -2; b1 <= 2; b1++) {
/* 22 */         if (paramWorld.isEmpty(paramInt1 + b, paramInt2 - 1, paramInt3 + b1) && paramWorld.isEmpty(paramInt1 + b, paramInt2 - 2, paramInt3 + b1)) {
/* 23 */           return false;
/*    */         }
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 29 */     for (b = -1; b <= 0; b++) {
/* 30 */       for (byte b1 = -2; b1 <= 2; b1++) {
/* 31 */         for (byte b2 = -2; b2 <= 2; b2++) {
/* 32 */           paramWorld.setTypeAndData(paramInt1 + b1, paramInt2 + b, paramInt3 + b2, Blocks.SANDSTONE, 0, 2);
/*    */         }
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 38 */     paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, Blocks.WATER, 0, 2);
/* 39 */     paramWorld.setTypeAndData(paramInt1 - 1, paramInt2, paramInt3, Blocks.WATER, 0, 2);
/* 40 */     paramWorld.setTypeAndData(paramInt1 + 1, paramInt2, paramInt3, Blocks.WATER, 0, 2);
/* 41 */     paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3 - 1, Blocks.WATER, 0, 2);
/* 42 */     paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3 + 1, Blocks.WATER, 0, 2);
/*    */ 
/*    */     
/* 45 */     for (b = -2; b <= 2; b++) {
/* 46 */       for (byte b1 = -2; b1 <= 2; b1++) {
/* 47 */         if (b == -2 || b == 2 || b1 == -2 || b1 == 2) {
/* 48 */           paramWorld.setTypeAndData(paramInt1 + b, paramInt2 + 1, paramInt3 + b1, Blocks.SANDSTONE, 0, 2);
/*    */         }
/*    */       } 
/*    */     } 
/* 52 */     paramWorld.setTypeAndData(paramInt1 + 2, paramInt2 + 1, paramInt3, Blocks.STEP, 1, 2);
/* 53 */     paramWorld.setTypeAndData(paramInt1 - 2, paramInt2 + 1, paramInt3, Blocks.STEP, 1, 2);
/* 54 */     paramWorld.setTypeAndData(paramInt1, paramInt2 + 1, paramInt3 + 2, Blocks.STEP, 1, 2);
/* 55 */     paramWorld.setTypeAndData(paramInt1, paramInt2 + 1, paramInt3 - 2, Blocks.STEP, 1, 2);
/*    */ 
/*    */     
/* 58 */     for (b = -1; b <= 1; b++) {
/* 59 */       for (byte b1 = -1; b1 <= 1; b1++) {
/* 60 */         if (b == 0 && b1 == 0) {
/* 61 */           paramWorld.setTypeAndData(paramInt1 + b, paramInt2 + 4, paramInt3 + b1, Blocks.SANDSTONE, 0, 2);
/*    */         } else {
/* 63 */           paramWorld.setTypeAndData(paramInt1 + b, paramInt2 + 4, paramInt3 + b1, Blocks.STEP, 1, 2);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 69 */     for (b = 1; b <= 3; b++) {
/* 70 */       paramWorld.setTypeAndData(paramInt1 - 1, paramInt2 + b, paramInt3 - 1, Blocks.SANDSTONE, 0, 2);
/* 71 */       paramWorld.setTypeAndData(paramInt1 - 1, paramInt2 + b, paramInt3 + 1, Blocks.SANDSTONE, 0, 2);
/* 72 */       paramWorld.setTypeAndData(paramInt1 + 1, paramInt2 + b, paramInt3 - 1, Blocks.SANDSTONE, 0, 2);
/* 73 */       paramWorld.setTypeAndData(paramInt1 + 1, paramInt2 + b, paramInt3 + 1, Blocks.SANDSTONE, 0, 2);
/*    */     } 
/*    */     
/* 76 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenDesertWell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */