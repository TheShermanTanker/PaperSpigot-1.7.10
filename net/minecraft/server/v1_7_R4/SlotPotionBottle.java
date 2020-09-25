/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SlotPotionBottle
/*     */   extends Slot
/*     */ {
/*     */   private EntityHuman a;
/*     */   
/*     */   public SlotPotionBottle(EntityHuman paramEntityHuman, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3) {
/* 125 */     super(paramIInventory, paramInt1, paramInt2, paramInt3);
/* 126 */     this.a = paramEntityHuman;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAllowed(ItemStack paramItemStack) {
/* 131 */     return b_(paramItemStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxStackSize() {
/* 136 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(EntityHuman paramEntityHuman, ItemStack paramItemStack) {
/* 141 */     if (paramItemStack.getItem() == Items.POTION && paramItemStack.getData() > 0) this.a.a(AchievementList.B, 1); 
/* 142 */     super.a(paramEntityHuman, paramItemStack);
/*     */   }
/*     */   
/*     */   public static boolean b_(ItemStack paramItemStack) {
/* 146 */     return (paramItemStack != null && (paramItemStack.getItem() == Items.POTION || paramItemStack.getItem() == Items.GLASS_BOTTLE));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\SlotPotionBottle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */