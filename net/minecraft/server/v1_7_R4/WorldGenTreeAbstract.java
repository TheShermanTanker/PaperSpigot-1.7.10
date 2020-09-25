/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WorldGenTreeAbstract
/*    */   extends WorldGenerator
/*    */ {
/*    */   public WorldGenTreeAbstract(boolean paramBoolean) {
/* 13 */     super(paramBoolean);
/*    */   }
/*    */   
/*    */   protected boolean a(Block paramBlock) {
/* 17 */     return (paramBlock.getMaterial() == Material.AIR || paramBlock.getMaterial() == Material.LEAVES || paramBlock == Blocks.GRASS || paramBlock == Blocks.DIRT || paramBlock == Blocks.LOG || paramBlock == Blocks.LOG2 || paramBlock == Blocks.SAPLING || paramBlock == Blocks.VINE);
/*    */   }
/*    */   
/*    */   public void b(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenTreeAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */