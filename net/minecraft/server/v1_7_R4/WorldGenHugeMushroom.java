/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenHugeMushroom
/*     */   extends WorldGenerator
/*     */ {
/*  11 */   private int a = -1;
/*     */   
/*     */   public WorldGenHugeMushroom(int paramInt) {
/*  14 */     super(true);
/*  15 */     this.a = paramInt;
/*     */   }
/*     */   
/*     */   public WorldGenHugeMushroom() {
/*  19 */     super(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*  24 */     int i = paramRandom.nextInt(2);
/*  25 */     if (this.a >= 0) i = this.a;
/*     */     
/*  27 */     int j = paramRandom.nextInt(3) + 4;
/*     */     
/*  29 */     boolean bool = true;
/*  30 */     if (paramInt2 < 1 || paramInt2 + j + 1 >= 256) return false;
/*     */     
/*  32 */     for (int k = paramInt2; k <= paramInt2 + 1 + j; k++) {
/*  33 */       byte b = 3;
/*  34 */       if (k <= paramInt2 + 3) b = 0; 
/*  35 */       for (int i1 = paramInt1 - b; i1 <= paramInt1 + b && bool; i1++) {
/*  36 */         for (int i2 = paramInt3 - b; i2 <= paramInt3 + b && bool; i2++) {
/*  37 */           if (k >= 0 && k < 256) {
/*  38 */             Block block1 = paramWorld.getType(i1, k, i2);
/*  39 */             if (block1.getMaterial() != Material.AIR && block1.getMaterial() != Material.LEAVES) {
/*  40 */               bool = false;
/*     */             }
/*     */           } else {
/*  43 */             bool = false;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  49 */     if (!bool) return false;
/*     */     
/*  51 */     Block block = paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3);
/*  52 */     if (block != Blocks.DIRT && block != Blocks.GRASS && block != Blocks.MYCEL) {
/*  53 */       return false;
/*     */     }
/*     */     
/*  56 */     int m = paramInt2 + j;
/*  57 */     if (i == 1)
/*  58 */       m = paramInt2 + j - 3; 
/*     */     int n;
/*  60 */     for (n = m; n <= paramInt2 + j; n++) {
/*  61 */       byte b = 1;
/*  62 */       if (n < paramInt2 + j) b++; 
/*  63 */       if (i == 0) b = 3; 
/*  64 */       for (int i1 = paramInt1 - b; i1 <= paramInt1 + b; i1++) {
/*  65 */         for (int i2 = paramInt3 - b; i2 <= paramInt3 + b; i2++) {
/*  66 */           byte b1 = 5;
/*  67 */           if (i1 == paramInt1 - b) b1--; 
/*  68 */           if (i1 == paramInt1 + b) b1++; 
/*  69 */           if (i2 == paramInt3 - b) b1 -= 3; 
/*  70 */           if (i2 == paramInt3 + b) b1 += 3;
/*     */           
/*  72 */           if (i == 0 || n < paramInt2 + j) {
/*  73 */             if ((i1 == paramInt1 - b || i1 == paramInt1 + b) && (i2 == paramInt3 - b || i2 == paramInt3 + b))
/*  74 */               continue;  if (i1 == paramInt1 - b - 1 && i2 == paramInt3 - b) b1 = 1; 
/*  75 */             if (i1 == paramInt1 - b && i2 == paramInt3 - b - 1) b1 = 1;
/*     */             
/*  77 */             if (i1 == paramInt1 + b - 1 && i2 == paramInt3 - b) b1 = 3; 
/*  78 */             if (i1 == paramInt1 + b && i2 == paramInt3 - b - 1) b1 = 3;
/*     */             
/*  80 */             if (i1 == paramInt1 - b - 1 && i2 == paramInt3 + b) b1 = 7; 
/*  81 */             if (i1 == paramInt1 - b && i2 == paramInt3 + b - 1) b1 = 7;
/*     */             
/*  83 */             if (i1 == paramInt1 + b - 1 && i2 == paramInt3 + b) b1 = 9; 
/*  84 */             if (i1 == paramInt1 + b && i2 == paramInt3 + b - 1) b1 = 9;
/*     */           
/*     */           } 
/*  87 */           if (b1 == 5 && n < paramInt2 + j) b1 = 0; 
/*  88 */           if (b1 != 0 || paramInt2 >= paramInt2 + j - 1)
/*     */           {
/*  90 */             if (!paramWorld.getType(i1, n, i2).j()) setTypeAndData(paramWorld, i1, n, i2, Block.getById(Block.getId(Blocks.BIG_MUSHROOM_1) + i), b1);  } 
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */     } 
/*  95 */     for (n = 0; n < j; n++) {
/*  96 */       Block block1 = paramWorld.getType(paramInt1, paramInt2 + n, paramInt3);
/*     */       
/*  98 */       if (!block1.j()) setTypeAndData(paramWorld, paramInt1, paramInt2 + n, paramInt3, Block.getById(Block.getId(Blocks.BIG_MUSHROOM_1) + i), 10); 
/*     */     } 
/* 100 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenHugeMushroom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */