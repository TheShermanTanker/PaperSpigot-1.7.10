/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WorldGenerator
/*    */ {
/*    */   private final boolean a;
/*    */   
/*    */   public WorldGenerator() {
/* 12 */     this.a = false;
/*    */   }
/*    */   
/*    */   public WorldGenerator(boolean paramBoolean) {
/* 16 */     this.a = paramBoolean;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3);
/*    */ 
/*    */   
/*    */   public void a(double paramDouble1, double paramDouble2, double paramDouble3) {}
/*    */ 
/*    */   
/*    */   protected void setType(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock) {
/* 28 */     setTypeAndData(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, 0);
/*    */   }
/*    */   
/*    */   protected void setTypeAndData(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
/* 32 */     if (this.a) {
/* 33 */       paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, paramBlock, paramInt4, 3);
/*    */     } else {
/* 35 */       paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, paramBlock, paramInt4, 2);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */