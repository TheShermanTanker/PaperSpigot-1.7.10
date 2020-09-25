/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockGravel
/*    */   extends BlockFalling
/*    */ {
/*    */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 11 */     if (paramInt2 > 3) paramInt2 = 3; 
/* 12 */     if (paramRandom.nextInt(10 - paramInt2 * 3) == 0) return Items.FLINT; 
/* 13 */     return Item.getItemOf(this);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockGravel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */