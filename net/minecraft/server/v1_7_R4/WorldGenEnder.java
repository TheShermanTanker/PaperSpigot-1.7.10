/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenEnder
/*    */   extends WorldGenerator
/*    */ {
/*    */   private Block a;
/*    */   
/*    */   public WorldGenEnder(Block paramBlock) {
/* 15 */     this.a = paramBlock;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 20 */     if (!paramWorld.isEmpty(paramInt1, paramInt2, paramInt3) || paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3) != this.a) {
/* 21 */       return false;
/*    */     }
/* 23 */     int i = paramRandom.nextInt(32) + 6;
/* 24 */     int j = paramRandom.nextInt(4) + 1; int k;
/* 25 */     for (k = paramInt1 - j; k <= paramInt1 + j; k++) {
/* 26 */       for (int m = paramInt3 - j; m <= paramInt3 + j; m++) {
/* 27 */         int n = k - paramInt1;
/* 28 */         int i1 = m - paramInt3;
/* 29 */         if (n * n + i1 * i1 <= j * j + 1 && 
/* 30 */           paramWorld.getType(k, paramInt2 - 1, m) != this.a) return false; 
/*    */       } 
/*    */     } 
/* 33 */     for (k = paramInt2; k < paramInt2 + i && 
/* 34 */       k < 256; k++) {
/* 35 */       for (int m = paramInt1 - j; m <= paramInt1 + j; m++) {
/* 36 */         for (int n = paramInt3 - j; n <= paramInt3 + j; n++) {
/* 37 */           int i1 = m - paramInt1;
/* 38 */           int i2 = n - paramInt3;
/* 39 */           if (i1 * i1 + i2 * i2 <= j * j + 1) {
/* 40 */             paramWorld.setTypeAndData(m, k, n, Blocks.OBSIDIAN, 0, 2);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 46 */     EntityEnderCrystal entityEnderCrystal = new EntityEnderCrystal(paramWorld);
/* 47 */     entityEnderCrystal.setPositionRotation((paramInt1 + 0.5F), (paramInt2 + i), (paramInt3 + 0.5F), paramRandom.nextFloat() * 360.0F, 0.0F);
/* 48 */     paramWorld.addEntity(entityEnderCrystal);
/* 49 */     paramWorld.setTypeAndData(paramInt1, paramInt2 + i, paramInt3, Blocks.BEDROCK, 0, 2);
/*    */     
/* 51 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenEnder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */