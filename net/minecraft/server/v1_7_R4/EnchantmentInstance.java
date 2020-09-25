/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class EnchantmentInstance
/*    */   extends WeightedRandomChoice
/*    */ {
/*    */   public final Enchantment enchantment;
/*    */   public final int level;
/*    */   
/*    */   public EnchantmentInstance(Enchantment paramEnchantment, int paramInt) {
/* 10 */     super(paramEnchantment.getRandomWeight());
/* 11 */     this.enchantment = paramEnchantment;
/* 12 */     this.level = paramInt;
/*    */   }
/*    */   
/*    */   public EnchantmentInstance(int paramInt1, int paramInt2) {
/* 16 */     this(Enchantment.byId[paramInt1], paramInt2);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnchantmentInstance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */