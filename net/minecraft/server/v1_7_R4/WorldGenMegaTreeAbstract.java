/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public abstract class WorldGenMegaTreeAbstract
/*     */   extends WorldGenTreeAbstract {
/*     */   protected final int a;
/*     */   protected final int b;
/*     */   protected final int c;
/*     */   protected int d;
/*     */   
/*     */   public WorldGenMegaTreeAbstract(boolean flag, int i, int j, int k, int l) {
/*  13 */     super(flag);
/*  14 */     this.a = i;
/*  15 */     this.d = j;
/*  16 */     this.b = k;
/*  17 */     this.c = l;
/*     */   }
/*     */   
/*     */   protected int a(Random random) {
/*  21 */     int i = random.nextInt(3) + this.a;
/*     */     
/*  23 */     if (this.d > 1) {
/*  24 */       i += random.nextInt(this.d);
/*     */     }
/*     */     
/*  27 */     return i;
/*     */   }
/*     */   
/*     */   private boolean b(World world, Random random, int i, int j, int k, int l) {
/*  31 */     boolean flag = true;
/*     */     
/*  33 */     if (j >= 1 && j + l + 1 <= 256) {
/*  34 */       for (int i1 = j; i1 <= j + 1 + l; i1++) {
/*  35 */         byte b0 = 2;
/*     */         
/*  37 */         if (i1 == j) {
/*  38 */           b0 = 1;
/*     */         }
/*     */         
/*  41 */         if (i1 >= j + 1 + l - 2) {
/*  42 */           b0 = 2;
/*     */         }
/*     */         
/*  45 */         for (int j1 = i - b0; j1 <= i + b0 && flag; j1++) {
/*  46 */           for (int k1 = k - b0; k1 <= k + b0 && flag; k1++) {
/*  47 */             if (i1 >= 0 && i1 < 256) {
/*  48 */               Block block = world.getType(j1, i1, k1);
/*     */ 
/*     */               
/*  51 */               if (block != Blocks.SAPLING && !a(block)) {
/*  52 */                 flag = false;
/*     */               }
/*     */             } else {
/*  55 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  61 */       return flag;
/*     */     } 
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean c(World world, Random random, int i, int j, int k) {
/*  68 */     Block block = world.getType(i, j - 1, k);
/*     */     
/*  70 */     if ((block == Blocks.GRASS || block == Blocks.DIRT) && j >= 2) {
/*  71 */       world.setTypeAndData(i, j - 1, k, Blocks.DIRT, 0, 2);
/*  72 */       world.setTypeAndData(i + 1, j - 1, k, Blocks.DIRT, 0, 2);
/*  73 */       world.setTypeAndData(i, j - 1, k + 1, Blocks.DIRT, 0, 2);
/*  74 */       world.setTypeAndData(i + 1, j - 1, k + 1, Blocks.DIRT, 0, 2);
/*  75 */       return true;
/*     */     } 
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean a(World world, Random random, int i, int j, int k, int l) {
/*  82 */     return (b(world, random, i, j, k, l) && c(world, random, i, j, k));
/*     */   }
/*     */   
/*     */   protected void a(World world, int i, int j, int k, int l, Random random) {
/*  86 */     int i1 = l * l;
/*     */     
/*  88 */     for (int j1 = i - l; j1 <= i + l + 1; j1++) {
/*  89 */       int k1 = j1 - i;
/*     */       
/*  91 */       for (int l1 = k - l; l1 <= k + l + 1; l1++) {
/*  92 */         int i2 = l1 - k;
/*  93 */         int j2 = k1 - 1;
/*  94 */         int k2 = i2 - 1;
/*     */         
/*  96 */         if (k1 * k1 + i2 * i2 <= i1 || j2 * j2 + k2 * k2 <= i1 || k1 * k1 + k2 * k2 <= i1 || j2 * j2 + i2 * i2 <= i1) {
/*  97 */           Block block = world.getType(j1, j, l1);
/*     */           
/*  99 */           if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) {
/* 100 */             setTypeAndData(world, j1, j, l1, Blocks.LEAVES, this.c);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void b(World world, int i, int j, int k, int l, Random random) {
/* 108 */     int i1 = l * l;
/*     */     
/* 110 */     for (int j1 = i - l; j1 <= i + l; j1++) {
/* 111 */       int k1 = j1 - i;
/*     */       
/* 113 */       for (int l1 = k - l; l1 <= k + l; l1++) {
/* 114 */         int i2 = l1 - k;
/*     */         
/* 116 */         if (k1 * k1 + i2 * i2 <= i1) {
/* 117 */           Block block = world.getType(j1, j, l1);
/*     */           
/* 119 */           if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES)
/* 120 */             setTypeAndData(world, j1, j, l1, Blocks.LEAVES, this.c); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenMegaTreeAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */