/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class EnchantmentLootBonus
/*    */   extends Enchantment {
/*    */   protected EnchantmentLootBonus(int paramInt1, int paramInt2, EnchantmentSlotType paramEnchantmentSlotType) {
/*  6 */     super(paramInt1, paramInt2, paramEnchantmentSlotType);
/*    */     
/*  8 */     if (paramEnchantmentSlotType == EnchantmentSlotType.DIGGER) {
/*  9 */       b("lootBonusDigger");
/* 10 */     } else if (paramEnchantmentSlotType == EnchantmentSlotType.FISHING_ROD) {
/* 11 */       b("lootBonusFishing");
/*    */     } else {
/* 13 */       b("lootBonus");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(int paramInt) {
/* 19 */     return 15 + (paramInt - 1) * 9;
/*    */   }
/*    */ 
/*    */   
/*    */   public int b(int paramInt) {
/* 24 */     return super.a(paramInt) + 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 29 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(Enchantment paramEnchantment) {
/* 34 */     return (super.a(paramEnchantment) && paramEnchantment.id != SILK_TOUCH.id);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnchantmentLootBonus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */