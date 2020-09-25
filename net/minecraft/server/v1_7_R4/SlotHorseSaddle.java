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
/*    */ class SlotHorseSaddle
/*    */   extends Slot
/*    */ {
/*    */   SlotHorseSaddle(ContainerHorse paramContainerHorse, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3) {
/* 21 */     super(paramIInventory, paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */   public boolean isAllowed(ItemStack paramItemStack) {
/* 24 */     return (super.isAllowed(paramItemStack) && paramItemStack.getItem() == Items.SADDLE && !hasItem());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\SlotHorseSaddle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */