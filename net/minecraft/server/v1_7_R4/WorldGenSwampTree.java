/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenSwampTree
/*     */   extends WorldGenTreeAbstract
/*     */ {
/*     */   public WorldGenSwampTree() {
/*  12 */     super(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*  17 */     int i = paramRandom.nextInt(4) + 5;
/*  18 */     while (paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3).getMaterial() == Material.WATER) {
/*  19 */       paramInt2--;
/*     */     }
/*  21 */     boolean bool = true;
/*  22 */     if (paramInt2 < 1 || paramInt2 + i + 1 > 256) return false;
/*     */     
/*  24 */     for (int j = paramInt2; j <= paramInt2 + 1 + i; j++) {
/*  25 */       byte b = 1;
/*  26 */       if (j == paramInt2) b = 0; 
/*  27 */       if (j >= paramInt2 + 1 + i - 2) b = 3; 
/*  28 */       for (int m = paramInt1 - b; m <= paramInt1 + b && bool; m++) {
/*  29 */         for (int n = paramInt3 - b; n <= paramInt3 + b && bool; n++) {
/*  30 */           if (j >= 0 && j < 256) {
/*  31 */             Block block1 = paramWorld.getType(m, j, n);
/*  32 */             if (block1.getMaterial() != Material.AIR && block1.getMaterial() != Material.LEAVES) {
/*  33 */               if (block1 == Blocks.STATIONARY_WATER || block1 == Blocks.WATER) {
/*  34 */                 if (j > paramInt2) bool = false; 
/*     */               } else {
/*  36 */                 bool = false;
/*     */               } 
/*     */             }
/*     */           } else {
/*  40 */             bool = false;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  46 */     if (!bool) return false;
/*     */     
/*  48 */     Block block = paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3);
/*  49 */     if ((block != Blocks.GRASS && block != Blocks.DIRT) || paramInt2 >= 256 - i - 1) {
/*  50 */       return false;
/*     */     }
/*  52 */     setType(paramWorld, paramInt1, paramInt2 - 1, paramInt3, Blocks.DIRT);
/*     */     int k;
/*  54 */     for (k = paramInt2 - 3 + i; k <= paramInt2 + i; k++) {
/*  55 */       int m = k - paramInt2 + i;
/*  56 */       int n = 2 - m / 2;
/*  57 */       for (int i1 = paramInt1 - n; i1 <= paramInt1 + n; i1++) {
/*  58 */         int i2 = i1 - paramInt1;
/*  59 */         for (int i3 = paramInt3 - n; i3 <= paramInt3 + n; i3++) {
/*  60 */           int i4 = i3 - paramInt3;
/*  61 */           if ((Math.abs(i2) != n || Math.abs(i4) != n || (paramRandom.nextInt(2) != 0 && m != 0)) && 
/*  62 */             !paramWorld.getType(i1, k, i3).j()) setType(paramWorld, i1, k, i3, Blocks.LEAVES);
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*  67 */     for (k = 0; k < i; k++) {
/*  68 */       Block block1 = paramWorld.getType(paramInt1, paramInt2 + k, paramInt3);
/*  69 */       if (block1.getMaterial() == Material.AIR || block1.getMaterial() == Material.LEAVES || block1 == Blocks.WATER || block1 == Blocks.STATIONARY_WATER) {
/*  70 */         setType(paramWorld, paramInt1, paramInt2 + k, paramInt3, Blocks.LOG);
/*     */       }
/*     */     } 
/*  73 */     for (k = paramInt2 - 3 + i; k <= paramInt2 + i; k++) {
/*  74 */       int m = k - paramInt2 + i;
/*  75 */       int n = 2 - m / 2;
/*  76 */       for (int i1 = paramInt1 - n; i1 <= paramInt1 + n; i1++) {
/*  77 */         for (int i2 = paramInt3 - n; i2 <= paramInt3 + n; i2++) {
/*  78 */           if (paramWorld.getType(i1, k, i2).getMaterial() == Material.LEAVES) {
/*  79 */             if (paramRandom.nextInt(4) == 0 && paramWorld.getType(i1 - 1, k, i2).getMaterial() == Material.AIR) {
/*  80 */               a(paramWorld, i1 - 1, k, i2, 8);
/*     */             }
/*  82 */             if (paramRandom.nextInt(4) == 0 && paramWorld.getType(i1 + 1, k, i2).getMaterial() == Material.AIR) {
/*  83 */               a(paramWorld, i1 + 1, k, i2, 2);
/*     */             }
/*  85 */             if (paramRandom.nextInt(4) == 0 && paramWorld.getType(i1, k, i2 - 1).getMaterial() == Material.AIR) {
/*  86 */               a(paramWorld, i1, k, i2 - 1, 1);
/*     */             }
/*  88 */             if (paramRandom.nextInt(4) == 0 && paramWorld.getType(i1, k, i2 + 1).getMaterial() == Material.AIR) {
/*  89 */               a(paramWorld, i1, k, i2 + 1, 4);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  95 */     return true;
/*     */   }
/*     */   
/*     */   private void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  99 */     setTypeAndData(paramWorld, paramInt1, paramInt2, paramInt3, Blocks.VINE, paramInt4);
/* 100 */     byte b = 4;
/* 101 */     while (paramWorld.getType(paramInt1, --paramInt2, paramInt3).getMaterial() == Material.AIR && b > 0) {
/* 102 */       setTypeAndData(paramWorld, paramInt1, paramInt2, paramInt3, Blocks.VINE, paramInt4);
/* 103 */       b--;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenSwampTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */