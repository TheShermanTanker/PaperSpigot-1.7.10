/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenMegaTree
/*     */   extends WorldGenMegaTreeAbstract
/*     */ {
/*     */   private boolean e;
/*     */   
/*     */   public WorldGenMegaTree(boolean paramBoolean1, boolean paramBoolean2) {
/*  15 */     super(paramBoolean1, 13, 15, 1, 1);
/*  16 */     this.e = paramBoolean2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*  22 */     int i = a(paramRandom);
/*  23 */     if (!a(paramWorld, paramRandom, paramInt1, paramInt2, paramInt3, i)) return false;
/*     */     
/*  25 */     c(paramWorld, paramInt1, paramInt3, paramInt2 + i, 0, paramRandom);
/*     */     
/*  27 */     for (byte b = 0; b < i; b++) {
/*  28 */       Block block = paramWorld.getType(paramInt1, paramInt2 + b, paramInt3);
/*  29 */       if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) {
/*  30 */         setTypeAndData(paramWorld, paramInt1, paramInt2 + b, paramInt3, Blocks.LOG, this.b);
/*     */       }
/*  32 */       if (b < i - 1) {
/*  33 */         block = paramWorld.getType(paramInt1 + 1, paramInt2 + b, paramInt3);
/*  34 */         if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) {
/*  35 */           setTypeAndData(paramWorld, paramInt1 + 1, paramInt2 + b, paramInt3, Blocks.LOG, this.b);
/*     */         }
/*  37 */         block = paramWorld.getType(paramInt1 + 1, paramInt2 + b, paramInt3 + 1);
/*  38 */         if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) {
/*  39 */           setTypeAndData(paramWorld, paramInt1 + 1, paramInt2 + b, paramInt3 + 1, Blocks.LOG, this.b);
/*     */         }
/*  41 */         block = paramWorld.getType(paramInt1, paramInt2 + b, paramInt3 + 1);
/*  42 */         if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) {
/*  43 */           setTypeAndData(paramWorld, paramInt1, paramInt2 + b, paramInt3 + 1, Blocks.LOG, this.b);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  48 */     return true;
/*     */   }
/*     */   
/*     */   private void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Random paramRandom) {
/*  52 */     int i = paramRandom.nextInt(5);
/*  53 */     if (this.e) {
/*  54 */       i += this.a;
/*     */     } else {
/*  56 */       i += 3;
/*     */     } 
/*  58 */     int j = 0;
/*  59 */     for (int k = paramInt3 - i; k <= paramInt3; k++) {
/*  60 */       int m = paramInt3 - k;
/*  61 */       int n = paramInt4 + MathHelper.d(m / i * 3.5F);
/*  62 */       a(paramWorld, paramInt1, k, paramInt2, n + ((m > 0 && n == j && (k & 0x1) == 0) ? 1 : 0), paramRandom);
/*  63 */       j = n;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*  69 */     c(paramWorld, paramRandom, paramInt1 - 1, paramInt2, paramInt3 - 1);
/*  70 */     c(paramWorld, paramRandom, paramInt1 + 2, paramInt2, paramInt3 - 1);
/*  71 */     c(paramWorld, paramRandom, paramInt1 - 1, paramInt2, paramInt3 + 2);
/*  72 */     c(paramWorld, paramRandom, paramInt1 + 2, paramInt2, paramInt3 + 2);
/*     */     
/*  74 */     for (byte b = 0; b < 5; b++) {
/*  75 */       int i = paramRandom.nextInt(64);
/*  76 */       int j = i % 8;
/*  77 */       int k = i / 8;
/*  78 */       if (j == 0 || j == 7 || k == 0 || k == 7) {
/*  79 */         c(paramWorld, paramRandom, paramInt1 - 3 + j, paramInt2, paramInt3 - 3 + k);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void c(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*  86 */     for (byte b = -2; b <= 2; b++) {
/*  87 */       for (byte b1 = -2; b1 <= 2; b1++) {
/*  88 */         if (Math.abs(b) != 2 || Math.abs(b1) != 2) {
/*  89 */           a(paramWorld, paramInt1 + b, paramInt2, paramInt3 + b1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  97 */     for (int i = paramInt2 + 2; i >= paramInt2 - 3; i--) {
/*  98 */       Block block = paramWorld.getType(paramInt1, i, paramInt3);
/*  99 */       if (block == Blocks.GRASS || block == Blocks.DIRT) {
/* 100 */         setTypeAndData(paramWorld, paramInt1, i, paramInt3, Blocks.DIRT, 2); break;
/*     */       } 
/* 102 */       if (block.getMaterial() != Material.AIR && i < paramInt2)
/*     */         break; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenMegaTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */