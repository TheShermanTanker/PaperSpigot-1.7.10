/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenAcaciaTree
/*     */   extends WorldGenTreeAbstract
/*     */ {
/*     */   public WorldGenAcaciaTree(boolean paramBoolean) {
/*  16 */     super(paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*  21 */     int i = paramRandom.nextInt(3) + paramRandom.nextInt(3) + 5;
/*     */     
/*  23 */     boolean bool = true;
/*  24 */     if (paramInt2 < 1 || paramInt2 + i + 1 > 256) return false;
/*     */     
/*  26 */     for (int j = paramInt2; j <= paramInt2 + 1 + i; j++) {
/*  27 */       byte b = 1;
/*  28 */       if (j == paramInt2) b = 0; 
/*  29 */       if (j >= paramInt2 + 1 + i - 2) b = 2; 
/*  30 */       for (int i5 = paramInt1 - b; i5 <= paramInt1 + b && bool; i5++) {
/*  31 */         for (int i6 = paramInt3 - b; i6 <= paramInt3 + b && bool; i6++) {
/*  32 */           if (j >= 0 && j < 256) {
/*  33 */             Block block1 = paramWorld.getType(i5, j, i6);
/*  34 */             if (!a(block1)) bool = false; 
/*     */           } else {
/*  36 */             bool = false;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  42 */     if (!bool) return false;
/*     */     
/*  44 */     Block block = paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3);
/*  45 */     if ((block != Blocks.GRASS && block != Blocks.DIRT) || paramInt2 >= 256 - i - 1) return false;
/*     */     
/*  47 */     setType(paramWorld, paramInt1, paramInt2 - 1, paramInt3, Blocks.DIRT);
/*     */     
/*  49 */     int k = paramRandom.nextInt(4);
/*  50 */     int m = i - paramRandom.nextInt(4) - 1;
/*  51 */     int n = 3 - paramRandom.nextInt(3);
/*     */     
/*  53 */     int i1 = paramInt1, i2 = paramInt3;
/*  54 */     int i3 = 0; int i4;
/*  55 */     for (i4 = 0; i4 < i; i4++) {
/*  56 */       int i5 = paramInt2 + i4;
/*  57 */       if (i4 >= m && n > 0) {
/*  58 */         i1 += Direction.a[k];
/*  59 */         i2 += Direction.b[k];
/*  60 */         n--;
/*     */       } 
/*  62 */       Block block1 = paramWorld.getType(i1, i5, i2);
/*  63 */       if (block1.getMaterial() == Material.AIR || block1.getMaterial() == Material.LEAVES) {
/*  64 */         setTypeAndData(paramWorld, i1, i5, i2, Blocks.LOG2, 0);
/*  65 */         i3 = i5;
/*     */       } 
/*     */     } 
/*     */     
/*  69 */     for (i4 = -1; i4 <= 1; i4++) {
/*  70 */       for (byte b = -1; b <= 1; b++) {
/*  71 */         a(paramWorld, i1 + i4, i3 + 1, i2 + b);
/*     */       }
/*     */     } 
/*  74 */     a(paramWorld, i1 + 2, i3 + 1, i2);
/*  75 */     a(paramWorld, i1 - 2, i3 + 1, i2);
/*  76 */     a(paramWorld, i1, i3 + 1, i2 + 2);
/*  77 */     a(paramWorld, i1, i3 + 1, i2 - 2);
/*  78 */     for (i4 = -3; i4 <= 3; i4++) {
/*  79 */       for (byte b = -3; b <= 3; b++) {
/*  80 */         if (Math.abs(i4) != 3 || Math.abs(b) != 3)
/*     */         {
/*     */           
/*  83 */           a(paramWorld, i1 + i4, i3, i2 + b);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  89 */     i1 = paramInt1;
/*  90 */     i2 = paramInt3;
/*  91 */     i4 = paramRandom.nextInt(4);
/*  92 */     if (i4 != k) {
/*  93 */       int i5 = m - paramRandom.nextInt(2) - 1;
/*  94 */       int i6 = 1 + paramRandom.nextInt(3);
/*     */       
/*  96 */       i3 = 0; int i7;
/*  97 */       for (i7 = i5; i7 < i && i6 > 0; i7++, i6--) {
/*  98 */         if (i7 >= 1) {
/*     */ 
/*     */           
/* 101 */           int i8 = paramInt2 + i7;
/* 102 */           i1 += Direction.a[i4];
/* 103 */           i2 += Direction.b[i4];
/* 104 */           Block block1 = paramWorld.getType(i1, i8, i2);
/* 105 */           if (block1.getMaterial() == Material.AIR || block1.getMaterial() == Material.LEAVES) {
/* 106 */             setTypeAndData(paramWorld, i1, i8, i2, Blocks.LOG2, 0);
/* 107 */             i3 = i8;
/*     */           } 
/*     */         } 
/* 110 */       }  if (i3 > 0) {
/* 111 */         for (i7 = -1; i7 <= 1; i7++) {
/* 112 */           for (byte b = -1; b <= 1; b++) {
/* 113 */             a(paramWorld, i1 + i7, i3 + 1, i2 + b);
/*     */           }
/*     */         } 
/* 116 */         for (i7 = -2; i7 <= 2; i7++) {
/* 117 */           for (byte b = -2; b <= 2; b++) {
/* 118 */             if (Math.abs(i7) != 2 || Math.abs(b) != 2)
/*     */             {
/*     */               
/* 121 */               a(paramWorld, i1 + i7, i3, i2 + b);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 127 */     return true;
/*     */   }
/*     */   
/*     */   private void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 131 */     Block block = paramWorld.getType(paramInt1, paramInt2, paramInt3);
/* 132 */     if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) setTypeAndData(paramWorld, paramInt1, paramInt2, paramInt3, Blocks.LEAVES2, 0); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenAcaciaTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */