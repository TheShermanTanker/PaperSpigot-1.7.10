/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemShears
/*    */   extends Item
/*    */ {
/*    */   public ItemShears() {
/* 12 */     e(1);
/* 13 */     setMaxDurability(238);
/* 14 */     a(CreativeModeTab.i);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, World paramWorld, Block paramBlock, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving) {
/* 19 */     if (paramBlock.getMaterial() == Material.LEAVES || paramBlock == Blocks.WEB || paramBlock == Blocks.LONG_GRASS || paramBlock == Blocks.VINE || paramBlock == Blocks.TRIPWIRE) {
/* 20 */       paramItemStack.damage(1, paramEntityLiving);
/* 21 */       return true;
/*    */     } 
/* 23 */     return super.a(paramItemStack, paramWorld, paramBlock, paramInt1, paramInt2, paramInt3, paramEntityLiving);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canDestroySpecialBlock(Block paramBlock) {
/* 28 */     return (paramBlock == Blocks.WEB || paramBlock == Blocks.REDSTONE_WIRE || paramBlock == Blocks.TRIPWIRE);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock) {
/* 33 */     if (paramBlock == Blocks.WEB || paramBlock.getMaterial() == Material.LEAVES) {
/* 34 */       return 15.0F;
/*    */     }
/* 36 */     if (paramBlock == Blocks.WOOL) {
/* 37 */       return 5.0F;
/*    */     }
/* 39 */     return super.getDestroySpeed(paramItemStack, paramBlock);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemShears.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */