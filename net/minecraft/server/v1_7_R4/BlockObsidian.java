/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockObsidian
/*    */   extends BlockStone
/*    */ {
/*    */   public int a(Random paramRandom) {
/* 11 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 16 */     return Item.getItemOf(Blocks.OBSIDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public MaterialMapColor f(int paramInt) {
/* 21 */     return MaterialMapColor.J;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockObsidian.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */