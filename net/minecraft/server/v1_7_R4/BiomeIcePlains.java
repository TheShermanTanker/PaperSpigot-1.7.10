/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeIcePlains
/*    */   extends BiomeBase
/*    */ {
/*    */   private boolean aC;
/* 12 */   private WorldGenPackedIce2 aD = new WorldGenPackedIce2();
/* 13 */   private WorldGenPackedIce1 aE = new WorldGenPackedIce1(4);
/*    */   
/*    */   public BiomeIcePlains(int paramInt, boolean paramBoolean) {
/* 16 */     super(paramInt);
/* 17 */     this.aC = paramBoolean;
/*    */     
/* 19 */     if (paramBoolean) {
/* 20 */       this.ai = Blocks.SNOW_BLOCK;
/*    */     }
/* 22 */     this.at.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
/* 28 */     if (this.aC) {
/*    */       byte b;
/* 30 */       for (b = 0; b < 3; b++) {
/* 31 */         int i = paramInt1 + paramRandom.nextInt(16) + 8;
/* 32 */         int j = paramInt2 + paramRandom.nextInt(16) + 8;
/* 33 */         this.aD.generate(paramWorld, paramRandom, i, paramWorld.getHighestBlockYAt(i, j), j);
/*    */       } 
/* 35 */       for (b = 0; b < 2; b++) {
/* 36 */         int i = paramInt1 + paramRandom.nextInt(16) + 8;
/* 37 */         int j = paramInt2 + paramRandom.nextInt(16) + 8;
/* 38 */         this.aE.generate(paramWorld, paramRandom, i, paramWorld.getHighestBlockYAt(i, j), j);
/*    */       } 
/*    */     } 
/*    */     
/* 42 */     super.a(paramWorld, paramRandom, paramInt1, paramInt2);
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenTreeAbstract a(Random paramRandom) {
/* 47 */     return new WorldGenTaiga2(false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected BiomeBase k() {
/* 52 */     BiomeBase biomeBase = (new BiomeIcePlains(this.id + 128, true)).a(13828095, true).a(this.af + " Spikes").c().a(0.0F, 0.5F).a(new BiomeTemperature(this.am + 0.1F, this.an + 0.1F));
/*    */ 
/*    */     
/* 55 */     biomeBase.am = this.am + 0.3F;
/* 56 */     biomeBase.an = this.an + 0.4F;
/*    */     
/* 58 */     return biomeBase;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeIcePlains.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */