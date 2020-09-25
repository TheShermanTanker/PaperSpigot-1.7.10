/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenForest
/*    */   extends WorldGenTreeAbstract
/*    */ {
/*    */   private boolean a;
/*    */   
/*    */   public WorldGenForest(boolean paramBoolean1, boolean paramBoolean2) {
/* 17 */     super(paramBoolean1);
/* 18 */     this.a = paramBoolean2;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 23 */     int i = paramRandom.nextInt(3) + 5;
/* 24 */     if (this.a) {
/* 25 */       i += paramRandom.nextInt(7);
/*    */     }
/*    */     
/* 28 */     boolean bool = true;
/* 29 */     if (paramInt2 < 1 || paramInt2 + i + 1 > 256) return false;
/*    */     
/* 31 */     for (int j = paramInt2; j <= paramInt2 + 1 + i; j++) {
/* 32 */       byte b = 1;
/* 33 */       if (j == paramInt2) b = 0; 
/* 34 */       if (j >= paramInt2 + 1 + i - 2) b = 2; 
/* 35 */       for (int m = paramInt1 - b; m <= paramInt1 + b && bool; m++) {
/* 36 */         for (int n = paramInt3 - b; n <= paramInt3 + b && bool; n++) {
/* 37 */           if (j >= 0 && j < 256) {
/* 38 */             Block block1 = paramWorld.getType(m, j, n);
/* 39 */             if (!a(block1)) bool = false; 
/*    */           } else {
/* 41 */             bool = false;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 47 */     if (!bool) return false;
/*    */     
/* 49 */     Block block = paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3);
/* 50 */     if ((block != Blocks.GRASS && block != Blocks.DIRT && block != Blocks.SOIL) || paramInt2 >= 256 - i - 1) return false;
/*    */     
/* 52 */     setType(paramWorld, paramInt1, paramInt2 - 1, paramInt3, Blocks.DIRT);
/*    */     int k;
/* 54 */     for (k = paramInt2 - 3 + i; k <= paramInt2 + i; k++) {
/* 55 */       int m = k - paramInt2 + i;
/* 56 */       int n = 1 - m / 2;
/* 57 */       for (int i1 = paramInt1 - n; i1 <= paramInt1 + n; i1++) {
/* 58 */         int i2 = i1 - paramInt1;
/* 59 */         for (int i3 = paramInt3 - n; i3 <= paramInt3 + n; i3++) {
/* 60 */           int i4 = i3 - paramInt3;
/* 61 */           if (Math.abs(i2) != n || Math.abs(i4) != n || (paramRandom.nextInt(2) != 0 && m != 0)) {
/* 62 */             Block block1 = paramWorld.getType(i1, k, i3);
/* 63 */             if (block1.getMaterial() == Material.AIR || block1.getMaterial() == Material.LEAVES) setTypeAndData(paramWorld, i1, k, i3, Blocks.LEAVES, 2); 
/*    */           } 
/*    */         } 
/*    */       } 
/* 67 */     }  for (k = 0; k < i; k++) {
/* 68 */       Block block1 = paramWorld.getType(paramInt1, paramInt2 + k, paramInt3);
/* 69 */       if (block1.getMaterial() == Material.AIR || block1.getMaterial() == Material.LEAVES) setTypeAndData(paramWorld, paramInt1, paramInt2 + k, paramInt3, Blocks.LOG, 2); 
/*    */     } 
/* 71 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenForest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */