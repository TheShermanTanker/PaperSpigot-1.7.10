/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class EnchantmentFire
/*    */   extends Enchantment {
/*    */   protected EnchantmentFire(int paramInt1, int paramInt2) {
/*  6 */     super(paramInt1, paramInt2, EnchantmentSlotType.WEAPON);
/*    */     
/*  8 */     b("fire");
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(int paramInt) {
/* 13 */     return 10 + 20 * (paramInt - 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int b(int paramInt) {
/* 18 */     return super.a(paramInt) + 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 23 */     return 2;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnchantmentFire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */