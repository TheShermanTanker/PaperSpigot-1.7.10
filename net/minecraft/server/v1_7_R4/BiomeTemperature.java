/*    */ package net.minecraft.server.v1_7_R4;
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
/*    */ public class BiomeTemperature
/*    */ {
/*    */   public float a;
/*    */   public float b;
/*    */   
/*    */   public BiomeTemperature(float paramFloat1, float paramFloat2) {
/* 37 */     this.a = paramFloat1;
/* 38 */     this.b = paramFloat2;
/*    */   }
/*    */   
/*    */   public BiomeTemperature a() {
/* 42 */     return new BiomeTemperature(this.a * 0.8F, this.b * 0.6F);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeTemperature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */