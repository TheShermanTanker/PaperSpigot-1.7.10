/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeBaseSub
/*    */   extends BiomeBase
/*    */ {
/*    */   protected BiomeBase aD;
/*    */   
/*    */   public BiomeBaseSub(int paramInt, BiomeBase paramBiomeBase) {
/* 14 */     super(paramInt);
/* 15 */     this.aD = paramBiomeBase;
/* 16 */     a(paramBiomeBase.ag, true);
/*    */     
/* 18 */     this.af = paramBiomeBase.af + " M";
/*    */     
/* 20 */     this.ai = paramBiomeBase.ai;
/* 21 */     this.ak = paramBiomeBase.ak;
/* 22 */     this.al = paramBiomeBase.al;
/* 23 */     this.am = paramBiomeBase.am;
/* 24 */     this.an = paramBiomeBase.an;
/* 25 */     this.temperature = paramBiomeBase.temperature;
/* 26 */     this.humidity = paramBiomeBase.humidity;
/* 27 */     this.aq = paramBiomeBase.aq;
/* 28 */     this.aw = paramBiomeBase.aw;
/* 29 */     this.ax = paramBiomeBase.ax;
/*    */     
/* 31 */     this.at = new ArrayList(paramBiomeBase.at);
/* 32 */     this.as = new ArrayList(paramBiomeBase.as);
/* 33 */     this.av = new ArrayList(paramBiomeBase.av);
/* 34 */     this.au = new ArrayList(paramBiomeBase.au);
/*    */     
/* 36 */     this.temperature = paramBiomeBase.temperature;
/* 37 */     this.humidity = paramBiomeBase.humidity;
/*    */     
/* 39 */     this.am = paramBiomeBase.am + 0.1F;
/* 40 */     this.an = paramBiomeBase.an + 0.2F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
/* 46 */     this.aD.ar.a(paramWorld, paramRandom, this, paramInt1, paramInt2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, Block[] paramArrayOfBlock, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, double paramDouble) {
/* 51 */     this.aD.a(paramWorld, paramRandom, paramArrayOfBlock, paramArrayOfbyte, paramInt1, paramInt2, paramDouble);
/*    */   }
/*    */ 
/*    */   
/*    */   public float g() {
/* 56 */     return this.aD.g();
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenTreeAbstract a(Random paramRandom) {
/* 61 */     return this.aD.a(paramRandom);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class l() {
/* 76 */     return this.aD.l();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(BiomeBase paramBiomeBase) {
/* 81 */     return this.aD.a(paramBiomeBase);
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumTemperature m() {
/* 86 */     return this.aD.m();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeBaseSub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */