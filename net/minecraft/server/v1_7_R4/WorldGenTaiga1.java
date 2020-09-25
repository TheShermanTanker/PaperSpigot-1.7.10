/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenTaiga1
/*    */   extends WorldGenTreeAbstract
/*    */ {
/*    */   public WorldGenTaiga1() {
/* 12 */     super(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 19 */     int i = paramRandom.nextInt(5) + 7;
/* 20 */     int j = i - paramRandom.nextInt(2) - 3;
/* 21 */     int k = i - j;
/* 22 */     int m = 1 + paramRandom.nextInt(k + 1);
/*    */     
/* 24 */     boolean bool = true;
/*    */     
/* 26 */     if (paramInt2 < 1 || paramInt2 + i + 1 > 256) {
/* 27 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 31 */     for (int n = paramInt2; n <= paramInt2 + 1 + i && bool; n++) {
/*    */       
/* 33 */       int i2 = 1;
/* 34 */       if (n - paramInt2 < j) {
/* 35 */         i2 = 0;
/*    */       } else {
/* 37 */         i2 = m;
/*    */       } 
/* 39 */       for (int i3 = paramInt1 - i2; i3 <= paramInt1 + i2 && bool; i3++) {
/* 40 */         for (int i4 = paramInt3 - i2; i4 <= paramInt3 + i2 && bool; i4++) {
/* 41 */           if (n >= 0 && n < 256) {
/* 42 */             Block block1 = paramWorld.getType(i3, n, i4);
/* 43 */             if (!a(block1)) bool = false; 
/*    */           } else {
/* 45 */             bool = false;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 51 */     if (!bool) return false;
/*    */ 
/*    */     
/* 54 */     Block block = paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3);
/* 55 */     if ((block != Blocks.GRASS && block != Blocks.DIRT) || paramInt2 >= 256 - i - 1) {
/* 56 */       return false;
/*    */     }
/* 58 */     setType(paramWorld, paramInt1, paramInt2 - 1, paramInt3, Blocks.DIRT);
/*    */ 
/*    */     
/* 61 */     byte b = 0; int i1;
/* 62 */     for (i1 = paramInt2 + i; i1 >= paramInt2 + j; i1--) {
/*    */       
/* 64 */       for (int i2 = paramInt1 - b; i2 <= paramInt1 + b; i2++) {
/* 65 */         int i3 = i2 - paramInt1;
/* 66 */         for (int i4 = paramInt3 - b; i4 <= paramInt3 + b; i4++) {
/* 67 */           int i5 = i4 - paramInt3;
/* 68 */           if ((Math.abs(i3) != b || Math.abs(i5) != b || b <= 0) && 
/* 69 */             !paramWorld.getType(i2, i1, i4).j()) {
/* 70 */             setTypeAndData(paramWorld, i2, i1, i4, Blocks.LEAVES, 1);
/*    */           }
/*    */         } 
/*    */       } 
/* 74 */       if (b >= 1 && i1 == paramInt2 + j + 1) {
/* 75 */         b--;
/* 76 */       } else if (b < m) {
/* 77 */         b++;
/*    */       } 
/*    */     } 
/* 80 */     for (i1 = 0; i1 < i - 1; i1++) {
/* 81 */       Block block1 = paramWorld.getType(paramInt1, paramInt2 + i1, paramInt3);
/* 82 */       if (block1.getMaterial() == Material.AIR || block1.getMaterial() == Material.LEAVES) setTypeAndData(paramWorld, paramInt1, paramInt2 + i1, paramInt3, Blocks.LOG, 1); 
/*    */     } 
/* 84 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenTaiga1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */