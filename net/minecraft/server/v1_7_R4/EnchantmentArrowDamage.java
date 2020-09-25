/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class EnchantmentArrowDamage
/*    */   extends Enchantment {
/*    */   public EnchantmentArrowDamage(int paramInt1, int paramInt2) {
/*  6 */     super(paramInt1, paramInt2, EnchantmentSlotType.BOW);
/*  7 */     b("arrowDamage");
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(int paramInt) {
/* 12 */     return 1 + (paramInt - 1) * 10;
/*    */   }
/*    */ 
/*    */   
/*    */   public int b(int paramInt) {
/* 17 */     return a(paramInt) + 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 22 */     return 5;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnchantmentArrowDamage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */