/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BiomeTaiga
/*     */   extends BiomeBase
/*     */ {
/*  12 */   private static final WorldGenTaiga1 aC = new WorldGenTaiga1();
/*  13 */   private static final WorldGenTaiga2 aD = new WorldGenTaiga2(false);
/*  14 */   private static final WorldGenMegaTree aE = new WorldGenMegaTree(false, false);
/*  15 */   private static final WorldGenMegaTree aF = new WorldGenMegaTree(false, true);
/*  16 */   private static final WorldGenTaigaStructure aG = new WorldGenTaigaStructure(Blocks.MOSSY_COBBLESTONE, 0);
/*     */ 
/*     */ 
/*     */   
/*     */   private int aH;
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeTaiga(int paramInt1, int paramInt2) {
/*  25 */     super(paramInt1);
/*  26 */     this.aH = paramInt2;
/*     */     
/*  28 */     this.at.add(new BiomeMeta(EntityWolf.class, 8, 4, 4));
/*     */     
/*  30 */     this.ar.x = 10;
/*  31 */     if (paramInt2 == 1 || paramInt2 == 2) {
/*  32 */       this.ar.z = 7;
/*  33 */       this.ar.A = 1;
/*  34 */       this.ar.B = 3;
/*     */     } else {
/*  36 */       this.ar.z = 1;
/*  37 */       this.ar.B = 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGenTreeAbstract a(Random paramRandom) {
/*  44 */     if ((this.aH == 1 || this.aH == 2) && paramRandom.nextInt(3) == 0) {
/*  45 */       if (this.aH == 2 || paramRandom.nextInt(13) == 0) {
/*  46 */         return aF;
/*     */       }
/*  48 */       return aE;
/*     */     } 
/*  50 */     if (paramRandom.nextInt(3) == 0) {
/*  51 */       return aC;
/*     */     }
/*  53 */     return aD;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldGenerator b(Random paramRandom) {
/*  58 */     if (paramRandom.nextInt(5) > 0) {
/*  59 */       return new WorldGenGrass(Blocks.LONG_GRASS, 2);
/*     */     }
/*  61 */     return new WorldGenGrass(Blocks.LONG_GRASS, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
/*  66 */     if (this.aH == 1 || this.aH == 2) {
/*  67 */       int i = paramRandom.nextInt(3);
/*  68 */       for (byte b1 = 0; b1 < i; b1++) {
/*  69 */         int j = paramInt1 + paramRandom.nextInt(16) + 8;
/*  70 */         int k = paramInt2 + paramRandom.nextInt(16) + 8;
/*  71 */         int m = paramWorld.getHighestBlockYAt(j, k);
/*  72 */         aG.generate(paramWorld, paramRandom, j, m, k);
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     ae.a(3);
/*  77 */     for (byte b = 0; b < 7; b++) {
/*  78 */       int i = paramInt1 + paramRandom.nextInt(16) + 8;
/*  79 */       int j = paramInt2 + paramRandom.nextInt(16) + 8;
/*  80 */       int k = paramRandom.nextInt(paramWorld.getHighestBlockYAt(i, j) + 32);
/*  81 */       ae.generate(paramWorld, paramRandom, i, k, j);
/*     */     } 
/*     */     
/*  84 */     super.a(paramWorld, paramRandom, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, Random paramRandom, Block[] paramArrayOfBlock, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, double paramDouble) {
/*  89 */     if (this.aH == 1 || this.aH == 2) {
/*  90 */       this.ai = Blocks.GRASS;
/*  91 */       this.aj = 0;
/*  92 */       this.ak = Blocks.DIRT;
/*  93 */       if (paramDouble > 1.75D) {
/*  94 */         this.ai = Blocks.DIRT;
/*  95 */         this.aj = 1;
/*  96 */       } else if (paramDouble > -0.95D) {
/*  97 */         this.ai = Blocks.DIRT;
/*  98 */         this.aj = 2;
/*     */       } 
/*     */     } 
/* 101 */     b(paramWorld, paramRandom, paramArrayOfBlock, paramArrayOfbyte, paramInt1, paramInt2, paramDouble);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BiomeBase k() {
/* 106 */     if (this.id == BiomeBase.MEGA_TAIGA.id) {
/* 107 */       return (new BiomeTaiga(this.id + 128, 2)).a(5858897, true).a("Mega Spruce Taiga").a(5159473).a(0.25F, 0.8F).a(new BiomeTemperature(this.am, this.an));
/*     */     }
/* 109 */     return super.k();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeTaiga.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */