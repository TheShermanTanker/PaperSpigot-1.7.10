/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnchantmentDurability
/*    */   extends Enchantment
/*    */ {
/*    */   protected EnchantmentDurability(int paramInt1, int paramInt2) {
/* 11 */     super(paramInt1, paramInt2, EnchantmentSlotType.BREAKABLE);
/*    */     
/* 13 */     b("durability");
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(int paramInt) {
/* 18 */     return 5 + (paramInt - 1) * 8;
/*    */   }
/*    */ 
/*    */   
/*    */   public int b(int paramInt) {
/* 23 */     return super.a(paramInt) + 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 28 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canEnchant(ItemStack paramItemStack) {
/* 33 */     if (paramItemStack.g()) return true; 
/* 34 */     return super.canEnchant(paramItemStack);
/*    */   }
/*    */   
/*    */   public static boolean a(ItemStack paramItemStack, int paramInt, Random paramRandom) {
/* 38 */     if (paramItemStack.getItem() instanceof ItemArmor && paramRandom.nextFloat() < 0.6F) return false; 
/* 39 */     return (paramRandom.nextInt(paramInt + 1) > 0);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnchantmentDurability.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */