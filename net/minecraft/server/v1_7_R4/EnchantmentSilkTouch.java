/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnchantmentSilkTouch
/*    */   extends Enchantment
/*    */ {
/*    */   protected EnchantmentSilkTouch(int paramInt1, int paramInt2) {
/*  9 */     super(paramInt1, paramInt2, EnchantmentSlotType.DIGGER);
/*    */     
/* 11 */     b("untouching");
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(int paramInt) {
/* 16 */     return 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public int b(int paramInt) {
/* 21 */     return super.a(paramInt) + 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 26 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(Enchantment paramEnchantment) {
/* 31 */     return (super.a(paramEnchantment) && paramEnchantment.id != LOOT_BONUS_BLOCKS.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canEnchant(ItemStack paramItemStack) {
/* 36 */     if (paramItemStack.getItem() == Items.SHEARS) return true; 
/* 37 */     return super.canEnchant(paramItemStack);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnchantmentSilkTouch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */