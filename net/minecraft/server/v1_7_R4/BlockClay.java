/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class BlockClay
/*    */   extends Block
/*    */ {
/*    */   public BlockClay() {
/* 10 */     super(Material.CLAY);
/* 11 */     a(CreativeModeTab.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 16 */     return Items.CLAY_BALL;
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(Random paramRandom) {
/* 21 */     return 4;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockClay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */