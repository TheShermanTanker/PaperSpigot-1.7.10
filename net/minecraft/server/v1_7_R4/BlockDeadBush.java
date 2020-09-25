/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockDeadBush
/*    */   extends BlockPlant
/*    */ {
/*    */   protected BlockDeadBush() {
/* 13 */     super(Material.REPLACEABLE_PLANT);
/* 14 */     float f = 0.4F;
/* 15 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean a(Block paramBlock) {
/* 20 */     return (paramBlock == Blocks.SAND || paramBlock == Blocks.HARDENED_CLAY || paramBlock == Blocks.STAINED_HARDENED_CLAY || paramBlock == Blocks.DIRT);
/*    */   }
/*    */ 
/*    */   
/*    */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 25 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, EntityHuman paramEntityHuman, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 30 */     if (!paramWorld.isStatic && paramEntityHuman.bF() != null && paramEntityHuman.bF().getItem() == Items.SHEARS) {
/* 31 */       paramEntityHuman.a(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)], 1);
/*    */ 
/*    */       
/* 34 */       a(paramWorld, paramInt1, paramInt2, paramInt3, new ItemStack(Blocks.DEAD_BUSH, 1, paramInt4));
/*    */     } else {
/* 36 */       super.a(paramWorld, paramEntityHuman, paramInt1, paramInt2, paramInt3, paramInt4);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockDeadBush.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */