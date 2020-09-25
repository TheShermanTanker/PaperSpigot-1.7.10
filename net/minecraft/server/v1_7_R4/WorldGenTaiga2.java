/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenTaiga2
/*    */   extends WorldGenTreeAbstract
/*    */ {
/*    */   public WorldGenTaiga2(boolean paramBoolean) {
/* 12 */     super(paramBoolean);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 19 */     int i = paramRandom.nextInt(4) + 6;
/* 20 */     int j = 1 + paramRandom.nextInt(2);
/* 21 */     int k = i - j;
/* 22 */     int m = 2 + paramRandom.nextInt(2);
/*    */     
/* 24 */     boolean bool1 = true;
/*    */     
/* 26 */     if (paramInt2 < 1 || paramInt2 + i + 1 > 256) {
/* 27 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 31 */     for (int n = paramInt2; n <= paramInt2 + 1 + i && bool1; n++) {
/*    */       
/* 33 */       int i4 = 1;
/* 34 */       if (n - paramInt2 < j) {
/* 35 */         i4 = 0;
/*    */       } else {
/* 37 */         i4 = m;
/*    */       } 
/* 39 */       for (int i5 = paramInt1 - i4; i5 <= paramInt1 + i4 && bool1; i5++) {
/* 40 */         for (int i6 = paramInt3 - i4; i6 <= paramInt3 + i4 && bool1; i6++) {
/* 41 */           if (n >= 0 && n < 256) {
/* 42 */             Block block1 = paramWorld.getType(i5, n, i6);
/* 43 */             if (block1.getMaterial() != Material.AIR && block1.getMaterial() != Material.LEAVES) bool1 = false; 
/*    */           } else {
/* 45 */             bool1 = false;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 51 */     if (!bool1) return false;
/*    */ 
/*    */     
/* 54 */     Block block = paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3);
/* 55 */     if ((block != Blocks.GRASS && block != Blocks.DIRT && block != Blocks.SOIL) || paramInt2 >= 256 - i - 1) {
/* 56 */       return false;
/*    */     }
/* 58 */     setType(paramWorld, paramInt1, paramInt2 - 1, paramInt3, Blocks.DIRT);
/*    */ 
/*    */     
/* 61 */     int i1 = paramRandom.nextInt(2);
/* 62 */     int i2 = 1;
/* 63 */     boolean bool2 = false; int i3;
/* 64 */     for (i3 = 0; i3 <= k; i3++) {
/*    */       
/* 66 */       int i4 = paramInt2 + i - i3;
/*    */       
/* 68 */       for (int i5 = paramInt1 - i1; i5 <= paramInt1 + i1; i5++) {
/* 69 */         int i6 = i5 - paramInt1;
/* 70 */         for (int i7 = paramInt3 - i1; i7 <= paramInt3 + i1; i7++) {
/* 71 */           int i8 = i7 - paramInt3;
/* 72 */           if ((Math.abs(i6) != i1 || Math.abs(i8) != i1 || i1 <= 0) && 
/* 73 */             !paramWorld.getType(i5, i4, i7).j()) {
/* 74 */             setTypeAndData(paramWorld, i5, i4, i7, Blocks.LEAVES, 1);
/*    */           }
/*    */         } 
/*    */       } 
/* 78 */       if (i1 >= i2) {
/* 79 */         i1 = bool2;
/* 80 */         bool2 = true;
/* 81 */         i2++;
/* 82 */         if (i2 > m) {
/* 83 */           i2 = m;
/*    */         }
/*    */       } else {
/* 86 */         i1++;
/*    */       } 
/*    */     } 
/* 89 */     i3 = paramRandom.nextInt(3);
/* 90 */     for (byte b = 0; b < i - i3; b++) {
/* 91 */       Block block1 = paramWorld.getType(paramInt1, paramInt2 + b, paramInt3);
/* 92 */       if (block1.getMaterial() == Material.AIR || block1.getMaterial() == Material.LEAVES) setTypeAndData(paramWorld, paramInt1, paramInt2 + b, paramInt3, Blocks.LOG, 1); 
/*    */     } 
/* 94 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenTaiga2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */