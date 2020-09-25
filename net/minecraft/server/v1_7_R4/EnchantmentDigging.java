/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnchantmentDigging
/*    */   extends Enchantment
/*    */ {
/*    */   protected EnchantmentDigging(int paramInt1, int paramInt2) {
/*  9 */     super(paramInt1, paramInt2, EnchantmentSlotType.DIGGER);
/*    */     
/* 11 */     b("digging");
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(int paramInt) {
/* 16 */     return 1 + 10 * (paramInt - 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int b(int paramInt) {
/* 21 */     return super.a(paramInt) + 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 26 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canEnchant(ItemStack paramItemStack) {
/* 31 */     if (paramItemStack.getItem() == Items.SHEARS) return true; 
/* 32 */     return super.canEnchant(paramItemStack);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnchantmentDigging.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */