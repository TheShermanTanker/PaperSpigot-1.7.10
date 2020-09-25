/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeBigHills
/*    */   extends BiomeBase
/*    */ {
/* 14 */   private WorldGenerator aC = new WorldGenMinable(Blocks.MONSTER_EGGS, 8);
/* 15 */   private WorldGenTaiga2 aD = new WorldGenTaiga2(false);
/*    */   
/* 17 */   private int aE = 0;
/* 18 */   private int aF = 1;
/* 19 */   private int aG = 2;
/*    */   
/*    */   private int aH;
/*    */   
/*    */   protected BiomeBigHills(int paramInt, boolean paramBoolean) {
/* 24 */     super(paramInt);
/* 25 */     this.aH = this.aE;
/* 26 */     if (paramBoolean) {
/* 27 */       this.ar.x = 3;
/* 28 */       this.aH = this.aF;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenTreeAbstract a(Random paramRandom) {
/* 34 */     if (paramRandom.nextInt(3) > 0) {
/* 35 */       return this.aD;
/*    */     }
/* 37 */     return super.a(paramRandom);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
/* 42 */     super.a(paramWorld, paramRandom, paramInt1, paramInt2);
/*    */ 
/*    */     
/* 45 */     int i = 3 + paramRandom.nextInt(6); int j;
/* 46 */     for (j = 0; j < i; j++) {
/* 47 */       int k = paramInt1 + paramRandom.nextInt(16);
/* 48 */       int m = paramRandom.nextInt(28) + 4;
/* 49 */       int n = paramInt2 + paramRandom.nextInt(16);
/* 50 */       if (paramWorld.getType(k, m, n) == Blocks.STONE) {
/* 51 */         paramWorld.setTypeAndData(k, m, n, Blocks.EMERALD_ORE, 0, 2);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 56 */     for (i = 0; i < 7; i++) {
/* 57 */       j = paramInt1 + paramRandom.nextInt(16);
/* 58 */       int k = paramRandom.nextInt(64);
/* 59 */       int m = paramInt2 + paramRandom.nextInt(16);
/* 60 */       this.aC.generate(paramWorld, paramRandom, j, k, m);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, Block[] paramArrayOfBlock, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, double paramDouble) {
/* 66 */     this.ai = Blocks.GRASS;
/* 67 */     this.aj = 0;
/* 68 */     this.ak = Blocks.DIRT;
/* 69 */     if ((paramDouble < -1.0D || paramDouble > 2.0D) && this.aH == this.aG) {
/* 70 */       this.ai = Blocks.GRAVEL;
/* 71 */       this.ak = Blocks.GRAVEL;
/* 72 */     } else if (paramDouble > 1.0D && this.aH != this.aF) {
/* 73 */       this.ai = Blocks.STONE;
/* 74 */       this.ak = Blocks.STONE;
/*    */     } 
/* 76 */     b(paramWorld, paramRandom, paramArrayOfBlock, paramArrayOfbyte, paramInt1, paramInt2, paramDouble);
/*    */   }
/*    */   
/*    */   private BiomeBigHills b(BiomeBase paramBiomeBase) {
/* 80 */     this.aH = this.aG;
/*    */     
/* 82 */     a(paramBiomeBase.ag, true);
/* 83 */     a(paramBiomeBase.af + " M");
/* 84 */     a(new BiomeTemperature(paramBiomeBase.am, paramBiomeBase.an));
/* 85 */     a(paramBiomeBase.temperature, paramBiomeBase.humidity);
/* 86 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BiomeBase k() {
/* 91 */     return (new BiomeBigHills(this.id + 128, false)).b(this);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeBigHills.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */