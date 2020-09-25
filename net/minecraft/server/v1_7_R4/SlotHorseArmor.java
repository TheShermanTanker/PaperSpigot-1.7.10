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
/*    */ class SlotHorseArmor
/*    */   extends Slot
/*    */ {
/*    */   SlotHorseArmor(ContainerHorse paramContainerHorse, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3, EntityHorse paramEntityHorse) {
/* 27 */     super(paramIInventory, paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */   public boolean isAllowed(ItemStack paramItemStack) {
/* 30 */     return (super.isAllowed(paramItemStack) && this.a.cB() && EntityHorse.a(paramItemStack.getItem()));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\SlotHorseArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */