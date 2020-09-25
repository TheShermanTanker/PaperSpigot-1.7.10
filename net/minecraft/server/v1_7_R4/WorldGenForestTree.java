/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class WorldGenForestTree
/*     */   extends WorldGenTreeAbstract {
/*     */   public WorldGenForestTree(boolean flag) {
/*   8 */     super(flag);
/*     */   }
/*     */   
/*     */   public boolean generate(World world, Random random, int i, int j, int k) {
/*  12 */     int l = random.nextInt(3) + random.nextInt(2) + 6;
/*  13 */     boolean flag = true;
/*     */     
/*  15 */     if (j >= 1 && j + l + 1 <= 256) {
/*     */ 
/*     */ 
/*     */       
/*  19 */       for (int k1 = j; k1 <= j + 1 + l; k1++) {
/*  20 */         byte b0 = 1;
/*     */         
/*  22 */         if (k1 == j) {
/*  23 */           b0 = 0;
/*     */         }
/*     */         
/*  26 */         if (k1 >= j + 1 + l - 2) {
/*  27 */           b0 = 2;
/*     */         }
/*     */         
/*  30 */         for (int i1 = i - b0; i1 <= i + b0 && flag; i1++) {
/*  31 */           for (int j1 = k - b0; j1 <= k + b0 && flag; j1++) {
/*  32 */             if (k1 >= 0 && k1 < 256) {
/*  33 */               Block block = world.getType(i1, k1, j1);
/*     */               
/*  35 */               if (!a(block)) {
/*  36 */                 flag = false;
/*     */               }
/*     */             } else {
/*  39 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  45 */       if (!flag) {
/*  46 */         return false;
/*     */       }
/*  48 */       Block block1 = world.getType(i, j - 1, k);
/*     */       
/*  50 */       if ((block1 == Blocks.GRASS || block1 == Blocks.DIRT) && j < 256 - l - 1) {
/*  51 */         setType(world, i, j - 1, k, Blocks.DIRT);
/*  52 */         setType(world, i + 1, j - 1, k, Blocks.DIRT);
/*  53 */         setType(world, i + 1, j - 1, k + 1, Blocks.DIRT);
/*  54 */         setType(world, i, j - 1, k + 1, Blocks.DIRT);
/*  55 */         int l1 = random.nextInt(4);
/*     */         
/*  57 */         int i1 = l - random.nextInt(4);
/*  58 */         int j1 = 2 - random.nextInt(3);
/*  59 */         int i2 = i;
/*  60 */         int j2 = k;
/*  61 */         int k2 = 0;
/*     */ 
/*     */         
/*     */         int l2;
/*     */         
/*  66 */         for (l2 = 0; l2 < l; l2++) {
/*  67 */           int i3 = j + l2;
/*  68 */           if (l2 >= i1 && j1 > 0) {
/*  69 */             i2 += Direction.a[l1];
/*  70 */             j2 += Direction.b[l1];
/*  71 */             j1--;
/*     */           } 
/*     */           
/*  74 */           Block block2 = world.getType(i2, i3, j2);
/*     */           
/*  76 */           if (block2.getMaterial() == Material.AIR || block2.getMaterial() == Material.LEAVES) {
/*  77 */             setTypeAndData(world, i2, i3, j2, Blocks.LOG2, 1);
/*  78 */             setTypeAndData(world, i2 + 1, i3, j2, Blocks.LOG2, 1);
/*  79 */             setTypeAndData(world, i2, i3, j2 + 1, Blocks.LOG2, 1);
/*  80 */             setTypeAndData(world, i2 + 1, i3, j2 + 1, Blocks.LOG2, 1);
/*  81 */             k2 = i3;
/*     */           } 
/*     */         } 
/*     */         
/*  85 */         for (l2 = -2; l2 <= 0; l2++) {
/*  86 */           for (int i3 = -2; i3 <= 0; i3++) {
/*  87 */             byte b1 = -1;
/*     */             
/*  89 */             a(world, i2 + l2, k2 + b1, j2 + i3);
/*  90 */             a(world, 1 + i2 - l2, k2 + b1, j2 + i3);
/*  91 */             a(world, i2 + l2, k2 + b1, 1 + j2 - i3);
/*  92 */             a(world, 1 + i2 - l2, k2 + b1, 1 + j2 - i3);
/*  93 */             if ((l2 > -2 || i3 > -1) && (l2 != -1 || i3 != -2)) {
/*  94 */               byte b2 = 1;
/*     */               
/*  96 */               a(world, i2 + l2, k2 + b2, j2 + i3);
/*  97 */               a(world, 1 + i2 - l2, k2 + b2, j2 + i3);
/*  98 */               a(world, i2 + l2, k2 + b2, 1 + j2 - i3);
/*  99 */               a(world, 1 + i2 - l2, k2 + b2, 1 + j2 - i3);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 104 */         if (random.nextBoolean()) {
/* 105 */           a(world, i2, k2 + 2, j2);
/* 106 */           a(world, i2 + 1, k2 + 2, j2);
/* 107 */           a(world, i2 + 1, k2 + 2, j2 + 1);
/* 108 */           a(world, i2, k2 + 2, j2 + 1);
/*     */         } 
/*     */         
/* 111 */         for (l2 = -3; l2 <= 4; l2++) {
/* 112 */           for (int i3 = -3; i3 <= 4; i3++) {
/* 113 */             if ((l2 != -3 || i3 != -3) && (l2 != -3 || i3 != 4) && (l2 != 4 || i3 != -3) && (l2 != 4 || i3 != 4) && (Math.abs(l2) < 3 || Math.abs(i3) < 3)) {
/* 114 */               a(world, i2 + l2, k2, j2 + i3);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 119 */         for (l2 = -1; l2 <= 2; l2++) {
/* 120 */           for (int i3 = -1; i3 <= 2; i3++) {
/* 121 */             if ((l2 < 0 || l2 > 1 || i3 < 0 || i3 > 1) && random.nextInt(3) <= 0) {
/* 122 */               int j3 = random.nextInt(3) + 2;
/*     */               
/*     */               int k3;
/*     */               
/* 126 */               for (k3 = 0; k3 < j3; k3++) {
/* 127 */                 Block block = world.getType(i + l2, k2 - k3 - 1, k + i3);
/*     */                 
/* 129 */                 if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES)
/*     */                 {
/* 131 */                   setTypeAndData(world, i + l2, k2 - k3 - 1, k + i3, Blocks.LOG2, 1);
/*     */                 }
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 137 */               for (k3 = -1; k3 <= 1; k3++) {
/* 138 */                 for (int l3 = -1; l3 <= 1; l3++) {
/* 139 */                   a(world, i2 + l2 + k3, k2 - 0, j2 + i3 + l3);
/*     */                 }
/*     */               } 
/*     */               
/* 143 */               for (k3 = -2; k3 <= 2; k3++) {
/* 144 */                 for (int l3 = -2; l3 <= 2; l3++) {
/* 145 */                   if (Math.abs(k3) != 2 || Math.abs(l3) != 2) {
/* 146 */                     a(world, i2 + l2 + k3, k2 - 1, j2 + i3 + l3);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 154 */         return true;
/*     */       } 
/* 156 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 160 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void a(World world, int i, int j, int k) {
/* 165 */     Block block = world.getType(i, j, k);
/*     */     
/* 167 */     if (block.getMaterial() == Material.AIR)
/* 168 */       setTypeAndData(world, i, j, k, Blocks.LEAVES2, 1); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenForestTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */