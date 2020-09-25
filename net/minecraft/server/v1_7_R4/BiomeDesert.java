/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeDesert
/*    */   extends BiomeBase
/*    */ {
/*    */   public BiomeDesert(int paramInt) {
/* 12 */     super(paramInt);
/*    */ 
/*    */     
/* 15 */     this.at.clear();
/* 16 */     this.ai = Blocks.SAND;
/* 17 */     this.ak = Blocks.SAND;
/*    */     
/* 19 */     this.ar.x = -999;
/* 20 */     this.ar.A = 2;
/* 21 */     this.ar.C = 50;
/* 22 */     this.ar.D = 10;
/*    */     
/* 24 */     this.at.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
/* 29 */     super.a(paramWorld, paramRandom, paramInt1, paramInt2);
/*    */     
/* 31 */     if (paramRandom.nextInt(1000) == 0) {
/* 32 */       int i = paramInt1 + paramRandom.nextInt(16) + 8;
/* 33 */       int j = paramInt2 + paramRandom.nextInt(16) + 8;
/* 34 */       WorldGenDesertWell worldGenDesertWell = new WorldGenDesertWell();
/* 35 */       worldGenDesertWell.generate(paramWorld, paramRandom, i, paramWorld.getHighestBlockYAt(i, j) + 1, j);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeDesert.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */