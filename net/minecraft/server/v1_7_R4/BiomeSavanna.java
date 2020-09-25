/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeSavanna
/*    */   extends BiomeBase
/*    */ {
/* 12 */   private static final WorldGenAcaciaTree aC = new WorldGenAcaciaTree(false);
/*    */   
/*    */   protected BiomeSavanna(int paramInt) {
/* 15 */     super(paramInt);
/*    */     
/* 17 */     this.at.add(new BiomeMeta(EntityHorse.class, 1, 2, 6));
/*    */     
/* 19 */     this.ar.x = 1;
/* 20 */     this.ar.y = 4;
/* 21 */     this.ar.z = 20;
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenTreeAbstract a(Random paramRandom) {
/* 26 */     if (paramRandom.nextInt(5) > 0) {
/* 27 */       return aC;
/*    */     }
/* 29 */     return this.az;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BiomeBase k() {
/* 34 */     BiomeSavannaSub biomeSavannaSub = new BiomeSavannaSub(this.id + 128, this);
/*    */     
/* 36 */     biomeSavannaSub.temperature = (this.temperature + 1.0F) * 0.5F;
/* 37 */     biomeSavannaSub.am = this.am * 0.5F + 0.3F;
/* 38 */     biomeSavannaSub.an = this.an * 0.5F + 1.2F;
/*    */     
/* 40 */     return biomeSavannaSub;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
/* 46 */     ae.a(2);
/* 47 */     for (byte b = 0; b < 7; b++) {
/* 48 */       int i = paramInt1 + paramRandom.nextInt(16) + 8;
/* 49 */       int j = paramInt2 + paramRandom.nextInt(16) + 8;
/* 50 */       int k = paramRandom.nextInt(paramWorld.getHighestBlockYAt(i, j) + 32);
/* 51 */       ae.generate(paramWorld, paramRandom, i, k, j);
/*    */     } 
/*    */     
/* 54 */     super.a(paramWorld, paramRandom, paramInt1, paramInt2);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeSavanna.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */