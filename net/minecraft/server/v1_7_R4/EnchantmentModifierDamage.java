/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ final class EnchantmentModifierDamage
/*    */   implements EnchantmentModifier {
/*    */   public float a;
/*    */   public EnumMonsterType b;
/*    */   
/*    */   private EnchantmentModifierDamage() {}
/*    */   
/*    */   public void a(Enchantment enchantment, int i) {
/* 11 */     this.a += enchantment.a(i, this.b);
/*    */   }
/*    */   
/*    */   EnchantmentModifierDamage(EmptyClass emptyclass) {
/* 15 */     this();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnchantmentModifierDamage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */