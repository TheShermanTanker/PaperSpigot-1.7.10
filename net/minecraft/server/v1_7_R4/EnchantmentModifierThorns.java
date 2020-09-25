/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ final class EnchantmentModifierThorns
/*    */   implements EnchantmentModifier {
/*    */   public EntityLiving a;
/*    */   public Entity b;
/*    */   
/*    */   private EnchantmentModifierThorns() {}
/*    */   
/*    */   public void a(Enchantment enchantment, int i) {
/* 11 */     enchantment.b(this.a, this.b, i);
/*    */   }
/*    */   
/*    */   EnchantmentModifierThorns(EmptyClass emptyclass) {
/* 15 */     this();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnchantmentModifierThorns.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */