/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class EnchantmentLure
/*    */   extends Enchantment {
/*    */   protected EnchantmentLure(int paramInt1, int paramInt2, EnchantmentSlotType paramEnchantmentSlotType) {
/*  6 */     super(paramInt1, paramInt2, paramEnchantmentSlotType);
/*    */     
/*  8 */     b("fishingSpeed");
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(int paramInt) {
/* 13 */     return 15 + (paramInt - 1) * 9;
/*    */   }
/*    */ 
/*    */   
/*    */   public int b(int paramInt) {
/* 18 */     return super.a(paramInt) + 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 23 */     return 3;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnchantmentLure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */