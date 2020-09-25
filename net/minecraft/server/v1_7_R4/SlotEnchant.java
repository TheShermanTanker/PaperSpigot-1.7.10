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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class SlotEnchant
/*    */   extends Slot
/*    */ {
/*    */   SlotEnchant(ContainerEnchantTable paramContainerEnchantTable, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3) {
/* 44 */     super(paramIInventory, paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */   public boolean isAllowed(ItemStack paramItemStack) {
/* 47 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\SlotEnchant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */