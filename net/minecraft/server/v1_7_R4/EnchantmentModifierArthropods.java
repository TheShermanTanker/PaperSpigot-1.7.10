/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ final class EnchantmentModifierArthropods
/*    */   implements EnchantmentModifier {
/*    */   public EntityLiving a;
/*    */   public Entity b;
/*    */   
/*    */   private EnchantmentModifierArthropods() {}
/*    */   
/*    */   public void a(Enchantment enchantment, int i) {
/* 11 */     enchantment.a(this.a, this.b, i);
/*    */   }
/*    */   
/*    */   EnchantmentModifierArthropods(EmptyClass emptyclass) {
/* 15 */     this();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnchantmentModifierArthropods.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */