/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenTaigaStructure
/*    */   extends WorldGenerator
/*    */ {
/*    */   private Block a;
/*    */   private int b;
/*    */   
/*    */   public WorldGenTaigaStructure(Block paramBlock, int paramInt) {
/* 14 */     super(false);
/* 15 */     this.a = paramBlock;
/* 16 */     this.b = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 21 */     while (paramInt2 > 3) {
/* 22 */       if (!paramWorld.isEmpty(paramInt1, paramInt2 - 1, paramInt3)) {
/* 23 */         Block block = paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3);
/* 24 */         if (block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.STONE) {
/*    */           break;
/*    */         }
/*    */       } 
/* 28 */       paramInt2--;
/*    */     } 
/* 30 */     if (paramInt2 <= 3) {
/* 31 */       return false;
/*    */     }
/*    */     
/* 34 */     int i = this.b;
/* 35 */     byte b = 0;
/* 36 */     while (i >= 0 && b < 3) {
/*    */       
/* 38 */       int j = i + paramRandom.nextInt(2);
/* 39 */       int k = i + paramRandom.nextInt(2);
/* 40 */       int m = i + paramRandom.nextInt(2);
/* 41 */       float f = (j + k + m) * 0.333F + 0.5F;
/* 42 */       for (int n = paramInt1 - j; n <= paramInt1 + j; n++) {
/* 43 */         for (int i1 = paramInt3 - m; i1 <= paramInt3 + m; i1++) {
/* 44 */           for (int i2 = paramInt2 - k; i2 <= paramInt2 + k; i2++) {
/* 45 */             float f1 = (n - paramInt1);
/* 46 */             float f2 = (i1 - paramInt3);
/* 47 */             float f3 = (i2 - paramInt2);
/* 48 */             if (f1 * f1 + f2 * f2 + f3 * f3 <= f * f)
/*    */             {
/* 50 */               paramWorld.setTypeAndData(n, i2, i1, this.a, 0, 4);
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/* 55 */       paramInt1 += -(i + 1) + paramRandom.nextInt(2 + i * 2);
/* 56 */       paramInt3 += -(i + 1) + paramRandom.nextInt(2 + i * 2);
/* 57 */       paramInt2 += 0 - paramRandom.nextInt(2);
/* 58 */       b++;
/*    */     } 
/*    */     
/* 61 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenTaigaStructure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */