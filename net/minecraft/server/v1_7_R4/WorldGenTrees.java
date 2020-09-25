/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenTrees
/*     */   extends WorldGenTreeAbstract
/*     */ {
/*     */   private final int a;
/*     */   private final boolean b;
/*     */   private final int c;
/*     */   private final int d;
/*     */   
/*     */   public WorldGenTrees(boolean paramBoolean) {
/*  17 */     this(paramBoolean, 4, 0, 0, false);
/*     */   }
/*     */   
/*     */   public WorldGenTrees(boolean paramBoolean1, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean2) {
/*  21 */     super(paramBoolean1);
/*  22 */     this.a = paramInt1;
/*  23 */     this.c = paramInt2;
/*  24 */     this.d = paramInt3;
/*  25 */     this.b = paramBoolean2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*  30 */     int i = paramRandom.nextInt(3) + this.a;
/*     */     
/*  32 */     boolean bool = true;
/*  33 */     if (paramInt2 < 1 || paramInt2 + i + 1 > 256) return false;
/*     */     
/*  35 */     for (int j = paramInt2; j <= paramInt2 + 1 + i; j++) {
/*  36 */       byte b = 1;
/*  37 */       if (j == paramInt2) b = 0; 
/*  38 */       if (j >= paramInt2 + 1 + i - 2) b = 2; 
/*  39 */       for (int m = paramInt1 - b; m <= paramInt1 + b && bool; m++) {
/*  40 */         for (int n = paramInt3 - b; n <= paramInt3 + b && bool; n++) {
/*  41 */           if (j >= 0 && j < 256) {
/*  42 */             Block block1 = paramWorld.getType(m, j, n);
/*  43 */             if (!a(block1)) bool = false; 
/*     */           } else {
/*  45 */             bool = false;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  51 */     if (!bool) return false;
/*     */     
/*  53 */     Block block = paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3);
/*  54 */     if ((block != Blocks.GRASS && block != Blocks.DIRT && block != Blocks.SOIL) || paramInt2 >= 256 - i - 1) return false;
/*     */     
/*  56 */     setType(paramWorld, paramInt1, paramInt2 - 1, paramInt3, Blocks.DIRT);
/*     */     
/*  58 */     byte b1 = 3;
/*  59 */     byte b2 = 0; int k;
/*  60 */     for (k = paramInt2 - b1 + i; k <= paramInt2 + i; k++) {
/*  61 */       int m = k - paramInt2 + i;
/*  62 */       int n = b2 + 1 - m / 2;
/*  63 */       for (int i1 = paramInt1 - n; i1 <= paramInt1 + n; i1++) {
/*  64 */         int i2 = i1 - paramInt1;
/*  65 */         for (int i3 = paramInt3 - n; i3 <= paramInt3 + n; i3++) {
/*  66 */           int i4 = i3 - paramInt3;
/*  67 */           if (Math.abs(i2) != n || Math.abs(i4) != n || (paramRandom.nextInt(2) != 0 && m != 0)) {
/*  68 */             Block block1 = paramWorld.getType(i1, k, i3);
/*  69 */             if (block1.getMaterial() == Material.AIR || block1.getMaterial() == Material.LEAVES) setTypeAndData(paramWorld, i1, k, i3, Blocks.LEAVES, this.d); 
/*     */           } 
/*     */         } 
/*     */       } 
/*  73 */     }  for (k = 0; k < i; k++) {
/*  74 */       Block block1 = paramWorld.getType(paramInt1, paramInt2 + k, paramInt3);
/*  75 */       if (block1.getMaterial() == Material.AIR || block1.getMaterial() == Material.LEAVES) {
/*  76 */         setTypeAndData(paramWorld, paramInt1, paramInt2 + k, paramInt3, Blocks.LOG, this.c);
/*  77 */         if (this.b && k > 0) {
/*  78 */           if (paramRandom.nextInt(3) > 0 && paramWorld.isEmpty(paramInt1 - 1, paramInt2 + k, paramInt3)) {
/*  79 */             setTypeAndData(paramWorld, paramInt1 - 1, paramInt2 + k, paramInt3, Blocks.VINE, 8);
/*     */           }
/*  81 */           if (paramRandom.nextInt(3) > 0 && paramWorld.isEmpty(paramInt1 + 1, paramInt2 + k, paramInt3)) {
/*  82 */             setTypeAndData(paramWorld, paramInt1 + 1, paramInt2 + k, paramInt3, Blocks.VINE, 2);
/*     */           }
/*  84 */           if (paramRandom.nextInt(3) > 0 && paramWorld.isEmpty(paramInt1, paramInt2 + k, paramInt3 - 1)) {
/*  85 */             setTypeAndData(paramWorld, paramInt1, paramInt2 + k, paramInt3 - 1, Blocks.VINE, 1);
/*     */           }
/*  87 */           if (paramRandom.nextInt(3) > 0 && paramWorld.isEmpty(paramInt1, paramInt2 + k, paramInt3 + 1)) {
/*  88 */             setTypeAndData(paramWorld, paramInt1, paramInt2 + k, paramInt3 + 1, Blocks.VINE, 4);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     if (this.b) {
/*  95 */       for (k = paramInt2 - 3 + i; k <= paramInt2 + i; k++) {
/*  96 */         int m = k - paramInt2 + i;
/*  97 */         int n = 2 - m / 2;
/*  98 */         for (int i1 = paramInt1 - n; i1 <= paramInt1 + n; i1++) {
/*  99 */           for (int i2 = paramInt3 - n; i2 <= paramInt3 + n; i2++) {
/* 100 */             if (paramWorld.getType(i1, k, i2).getMaterial() == Material.LEAVES) {
/* 101 */               if (paramRandom.nextInt(4) == 0 && paramWorld.getType(i1 - 1, k, i2).getMaterial() == Material.AIR) {
/* 102 */                 a(paramWorld, i1 - 1, k, i2, 8);
/*     */               }
/* 104 */               if (paramRandom.nextInt(4) == 0 && paramWorld.getType(i1 + 1, k, i2).getMaterial() == Material.AIR) {
/* 105 */                 a(paramWorld, i1 + 1, k, i2, 2);
/*     */               }
/* 107 */               if (paramRandom.nextInt(4) == 0 && paramWorld.getType(i1, k, i2 - 1).getMaterial() == Material.AIR) {
/* 108 */                 a(paramWorld, i1, k, i2 - 1, 1);
/*     */               }
/* 110 */               if (paramRandom.nextInt(4) == 0 && paramWorld.getType(i1, k, i2 + 1).getMaterial() == Material.AIR) {
/* 111 */                 a(paramWorld, i1, k, i2 + 1, 4);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 119 */       if (paramRandom.nextInt(5) == 0 && i > 5) {
/* 120 */         for (k = 0; k < 2; k++) {
/* 121 */           for (byte b = 0; b < 4; b++) {
/* 122 */             if (paramRandom.nextInt(4 - k) == 0) {
/* 123 */               int m = paramRandom.nextInt(3);
/* 124 */               setTypeAndData(paramWorld, paramInt1 + Direction.a[Direction.f[b]], paramInt2 + i - 5 + k, paramInt3 + Direction.b[Direction.f[b]], Blocks.COCOA, m << 2 | b);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 130 */     return true;
/*     */   }
/*     */   
/*     */   private void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 134 */     setTypeAndData(paramWorld, paramInt1, paramInt2, paramInt3, Blocks.VINE, paramInt4);
/* 135 */     byte b = 4;
/* 136 */     while (paramWorld.getType(paramInt1, --paramInt2, paramInt3).getMaterial() == Material.AIR && b > 0) {
/* 137 */       setTypeAndData(paramWorld, paramInt1, paramInt2, paramInt3, Blocks.VINE, paramInt4);
/* 138 */       b--;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenTrees.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */