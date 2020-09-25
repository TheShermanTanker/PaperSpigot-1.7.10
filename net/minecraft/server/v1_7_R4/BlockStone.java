/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockStone
/*    */   extends Block
/*    */ {
/*    */   public BlockStone() {
/* 11 */     super(Material.STONE);
/* 12 */     a(CreativeModeTab.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 17 */     return Item.getItemOf(Blocks.COBBLESTONE);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockStone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */