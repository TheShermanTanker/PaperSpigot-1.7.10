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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeSavannaSub
/*    */   extends BiomeBaseSub
/*    */ {
/*    */   public BiomeSavannaSub(int paramInt, BiomeBase paramBiomeBase) {
/* 60 */     super(paramInt, paramBiomeBase);
/*    */     
/* 62 */     this.ar.x = 2;
/* 63 */     this.ar.y = 2;
/* 64 */     this.ar.z = 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, Block[] paramArrayOfBlock, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, double paramDouble) {
/* 69 */     this.ai = Blocks.GRASS;
/* 70 */     this.aj = 0;
/* 71 */     this.ak = Blocks.DIRT;
/* 72 */     if (paramDouble > 1.75D) {
/* 73 */       this.ai = Blocks.STONE;
/* 74 */       this.ak = Blocks.STONE;
/* 75 */     } else if (paramDouble > -0.5D) {
/* 76 */       this.ai = Blocks.DIRT;
/* 77 */       this.aj = 1;
/*    */     } 
/* 79 */     b(paramWorld, paramRandom, paramArrayOfBlock, paramArrayOfbyte, paramInt1, paramInt2, paramDouble);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
/* 84 */     this.ar.a(paramWorld, paramRandom, this, paramInt1, paramInt2);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeSavannaSub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */