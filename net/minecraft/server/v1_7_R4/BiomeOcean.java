/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeOcean
/*    */   extends BiomeBase
/*    */ {
/*    */   public BiomeOcean(int paramInt) {
/* 11 */     super(paramInt);
/*    */     
/* 13 */     this.at.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumTemperature m() {
/* 18 */     return EnumTemperature.OCEAN;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, Block[] paramArrayOfBlock, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, double paramDouble) {
/* 24 */     super.a(paramWorld, paramRandom, paramArrayOfBlock, paramArrayOfbyte, paramInt1, paramInt2, paramDouble);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeOcean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */