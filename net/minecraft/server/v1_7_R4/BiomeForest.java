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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BiomeForest
/*     */   extends BiomeBase
/*     */ {
/*     */   private int aF;
/*  20 */   protected static final WorldGenForest aC = new WorldGenForest(false, true);
/*  21 */   protected static final WorldGenForest aD = new WorldGenForest(false, false);
/*  22 */   protected static final WorldGenForestTree aE = new WorldGenForestTree(false);
/*     */   
/*     */   public BiomeForest(int paramInt1, int paramInt2) {
/*  25 */     super(paramInt1);
/*  26 */     this.aF = paramInt2;
/*  27 */     this.ar.x = 10;
/*  28 */     this.ar.z = 2;
/*     */     
/*  30 */     if (this.aF == 1) {
/*  31 */       this.ar.x = 6;
/*  32 */       this.ar.y = 100;
/*  33 */       this.ar.z = 1;
/*     */     } 
/*  35 */     a(5159473);
/*  36 */     a(0.7F, 0.8F);
/*     */     
/*  38 */     if (this.aF == 2) {
/*  39 */       this.ah = 353825;
/*  40 */       this.ag = 3175492;
/*  41 */       a(0.6F, 0.6F);
/*     */     } 
/*     */     
/*  44 */     if (this.aF == 0) {
/*  45 */       this.at.add(new BiomeMeta(EntityWolf.class, 5, 4, 4));
/*     */     }
/*     */     
/*  48 */     if (this.aF == 3) {
/*  49 */       this.ar.x = -999;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected BiomeBase a(int paramInt, boolean paramBoolean) {
/*  55 */     if (this.aF == 2) {
/*  56 */       this.ah = 353825;
/*  57 */       this.ag = paramInt;
/*     */       
/*  59 */       if (paramBoolean) {
/*  60 */         this.ah = (this.ah & 0xFEFEFE) >> 1;
/*     */       }
/*  62 */       return this;
/*     */     } 
/*  64 */     return super.a(paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGenTreeAbstract a(Random paramRandom) {
/*  70 */     if (this.aF == 3 && paramRandom.nextInt(3) > 0) {
/*  71 */       return aE;
/*     */     }
/*  73 */     if (this.aF == 2 || paramRandom.nextInt(5) == 0) {
/*  74 */       return aD;
/*     */     }
/*  76 */     return this.az;
/*     */   }
/*     */ 
/*     */   
/*     */   public String a(Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*  81 */     if (this.aF == 1) {
/*  82 */       double d = MathHelper.a((1.0D + ad.a(paramInt1 / 48.0D, paramInt3 / 48.0D)) / 2.0D, 0.0D, 0.9999D);
/*  83 */       int i = (int)(d * BlockFlowers.a.length);
/*  84 */       if (i == 1) {
/*  85 */         i = 0;
/*     */       }
/*  87 */       return BlockFlowers.a[i];
/*     */     } 
/*  89 */     return super.a(paramRandom, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
/*  94 */     if (this.aF == 3) {
/*  95 */       for (byte b1 = 0; b1 < 4; b1++) {
/*  96 */         for (byte b2 = 0; b2 < 4; b2++) {
/*  97 */           int j = paramInt1 + b1 * 4 + 1 + 8 + paramRandom.nextInt(3);
/*  98 */           int k = paramInt2 + b2 * 4 + 1 + 8 + paramRandom.nextInt(3);
/*  99 */           int m = paramWorld.getHighestBlockYAt(j, k);
/*     */           
/* 101 */           if (paramRandom.nextInt(20) == 0) {
/* 102 */             WorldGenHugeMushroom worldGenHugeMushroom = new WorldGenHugeMushroom();
/* 103 */             worldGenHugeMushroom.generate(paramWorld, paramRandom, j, m, k);
/*     */           } else {
/* 105 */             WorldGenTreeAbstract worldGenTreeAbstract = a(paramRandom);
/* 106 */             worldGenTreeAbstract.a(1.0D, 1.0D, 1.0D);
/* 107 */             if (worldGenTreeAbstract.generate(paramWorld, paramRandom, j, m, k)) {
/* 108 */               worldGenTreeAbstract.b(paramWorld, paramRandom, j, m, k);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 114 */     int i = paramRandom.nextInt(5) - 3;
/* 115 */     if (this.aF == 1) {
/* 116 */       i += 2;
/*     */     }
/* 118 */     for (byte b = 0; b < i; b++) {
/* 119 */       int j = paramRandom.nextInt(3);
/* 120 */       if (j == 0) {
/* 121 */         ae.a(1);
/* 122 */       } else if (j == 1) {
/* 123 */         ae.a(4);
/* 124 */       } else if (j == 2) {
/* 125 */         ae.a(5);
/*     */       } 
/*     */       
/* 128 */       for (byte b1 = 0; b1 < 5; b1++) {
/* 129 */         int k = paramInt1 + paramRandom.nextInt(16) + 8;
/* 130 */         int m = paramInt2 + paramRandom.nextInt(16) + 8;
/* 131 */         int n = paramRandom.nextInt(paramWorld.getHighestBlockYAt(k, m) + 32);
/* 132 */         if (ae.generate(paramWorld, paramRandom, k, n, m)) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     super.a(paramWorld, paramRandom, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BiomeBase k() {
/* 154 */     if (this.id == BiomeBase.FOREST.id) {
/* 155 */       BiomeForest biomeForest = new BiomeForest(this.id + 128, 1);
/* 156 */       biomeForest.a(new BiomeTemperature(this.am, this.an + 0.2F));
/* 157 */       biomeForest.a("Flower Forest");
/* 158 */       biomeForest.a(6976549, true);
/* 159 */       biomeForest.a(8233509);
/* 160 */       return biomeForest;
/*     */     } 
/* 162 */     if (this.id == BiomeBase.BIRCH_FOREST.id || this.id == BiomeBase.BIRCH_FOREST_HILLS.id) {
/* 163 */       return new BiomeBaseSubForest(this, this.id + 128, this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     return new BiomeBaseSubForest2(this, this.id + 128, this);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeForest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */