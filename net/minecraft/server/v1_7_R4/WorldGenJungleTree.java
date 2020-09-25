/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenJungleTree
/*     */   extends WorldGenMegaTreeAbstract
/*     */ {
/*     */   public WorldGenJungleTree(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  13 */     super(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*  19 */     int i = a(paramRandom);
/*  20 */     if (!a(paramWorld, paramRandom, paramInt1, paramInt2, paramInt3, i)) return false;
/*     */     
/*  22 */     c(paramWorld, paramInt1, paramInt3, paramInt2 + i, 2, paramRandom);
/*     */     
/*  24 */     int j = paramInt2 + i - 2 - paramRandom.nextInt(4);
/*  25 */     while (j > paramInt2 + i / 2) {
/*  26 */       float f = paramRandom.nextFloat() * 3.1415927F * 2.0F;
/*  27 */       int k = paramInt1 + (int)(0.5F + MathHelper.cos(f) * 4.0F);
/*  28 */       int m = paramInt3 + (int)(0.5F + MathHelper.sin(f) * 4.0F);
/*     */       int n;
/*  30 */       for (n = 0; n < 5; n++) {
/*  31 */         k = paramInt1 + (int)(1.5F + MathHelper.cos(f) * n);
/*  32 */         m = paramInt3 + (int)(1.5F + MathHelper.sin(f) * n);
/*  33 */         setTypeAndData(paramWorld, k, j - 3 + n / 2, m, Blocks.LOG, this.b);
/*     */       } 
/*  35 */       n = 1 + paramRandom.nextInt(2);
/*  36 */       int i1 = j;
/*  37 */       for (int i2 = i1 - n; i2 <= i1; i2++) {
/*  38 */         int i3 = i2 - i1;
/*  39 */         b(paramWorld, k, i2, m, 1 - i3, paramRandom);
/*     */       } 
/*     */       
/*  42 */       j -= 2 + paramRandom.nextInt(4);
/*     */     } 
/*     */     
/*  45 */     for (byte b = 0; b < i; b++) {
/*  46 */       Block block = paramWorld.getType(paramInt1, paramInt2 + b, paramInt3);
/*  47 */       if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) {
/*  48 */         setTypeAndData(paramWorld, paramInt1, paramInt2 + b, paramInt3, Blocks.LOG, this.b);
/*  49 */         if (b > 0) {
/*  50 */           if (paramRandom.nextInt(3) > 0 && paramWorld.isEmpty(paramInt1 - 1, paramInt2 + b, paramInt3)) {
/*  51 */             setTypeAndData(paramWorld, paramInt1 - 1, paramInt2 + b, paramInt3, Blocks.VINE, 8);
/*     */           }
/*  53 */           if (paramRandom.nextInt(3) > 0 && paramWorld.isEmpty(paramInt1, paramInt2 + b, paramInt3 - 1)) {
/*  54 */             setTypeAndData(paramWorld, paramInt1, paramInt2 + b, paramInt3 - 1, Blocks.VINE, 1);
/*     */           }
/*     */         } 
/*     */       } 
/*  58 */       if (b < i - 1) {
/*  59 */         block = paramWorld.getType(paramInt1 + 1, paramInt2 + b, paramInt3);
/*  60 */         if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) {
/*  61 */           setTypeAndData(paramWorld, paramInt1 + 1, paramInt2 + b, paramInt3, Blocks.LOG, this.b);
/*  62 */           if (b > 0) {
/*  63 */             if (paramRandom.nextInt(3) > 0 && paramWorld.isEmpty(paramInt1 + 2, paramInt2 + b, paramInt3)) {
/*  64 */               setTypeAndData(paramWorld, paramInt1 + 2, paramInt2 + b, paramInt3, Blocks.VINE, 2);
/*     */             }
/*  66 */             if (paramRandom.nextInt(3) > 0 && paramWorld.isEmpty(paramInt1 + 1, paramInt2 + b, paramInt3 - 1)) {
/*  67 */               setTypeAndData(paramWorld, paramInt1 + 1, paramInt2 + b, paramInt3 - 1, Blocks.VINE, 1);
/*     */             }
/*     */           } 
/*     */         } 
/*  71 */         block = paramWorld.getType(paramInt1 + 1, paramInt2 + b, paramInt3 + 1);
/*  72 */         if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) {
/*  73 */           setTypeAndData(paramWorld, paramInt1 + 1, paramInt2 + b, paramInt3 + 1, Blocks.LOG, this.b);
/*  74 */           if (b > 0) {
/*  75 */             if (paramRandom.nextInt(3) > 0 && paramWorld.isEmpty(paramInt1 + 2, paramInt2 + b, paramInt3 + 1)) {
/*  76 */               setTypeAndData(paramWorld, paramInt1 + 2, paramInt2 + b, paramInt3 + 1, Blocks.VINE, 2);
/*     */             }
/*  78 */             if (paramRandom.nextInt(3) > 0 && paramWorld.isEmpty(paramInt1 + 1, paramInt2 + b, paramInt3 + 2)) {
/*  79 */               setTypeAndData(paramWorld, paramInt1 + 1, paramInt2 + b, paramInt3 + 2, Blocks.VINE, 4);
/*     */             }
/*     */           } 
/*     */         } 
/*  83 */         block = paramWorld.getType(paramInt1, paramInt2 + b, paramInt3 + 1);
/*  84 */         if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) {
/*  85 */           setTypeAndData(paramWorld, paramInt1, paramInt2 + b, paramInt3 + 1, Blocks.LOG, this.b);
/*  86 */           if (b > 0) {
/*  87 */             if (paramRandom.nextInt(3) > 0 && paramWorld.isEmpty(paramInt1 - 1, paramInt2 + b, paramInt3 + 1)) {
/*  88 */               setTypeAndData(paramWorld, paramInt1 - 1, paramInt2 + b, paramInt3 + 1, Blocks.VINE, 8);
/*     */             }
/*  90 */             if (paramRandom.nextInt(3) > 0 && paramWorld.isEmpty(paramInt1, paramInt2 + b, paramInt3 + 2)) {
/*  91 */               setTypeAndData(paramWorld, paramInt1, paramInt2 + b, paramInt3 + 2, Blocks.VINE, 4);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     return true;
/*     */   }
/*     */   
/*     */   private void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Random paramRandom) {
/* 102 */     byte b = 2;
/* 103 */     for (int i = paramInt3 - b; i <= paramInt3; i++) {
/* 104 */       int j = i - paramInt3;
/* 105 */       a(paramWorld, paramInt1, i, paramInt2, paramInt4 + 1 - j, paramRandom);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenJungleTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */