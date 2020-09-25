/*    */ package org.bukkit.enchantments;
/*    */ 
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ public class EnchantmentWrapper
/*    */   extends Enchantment
/*    */ {
/*    */   public EnchantmentWrapper(int id) {
/* 10 */     super(id);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Enchantment getEnchantment() {
/* 19 */     return Enchantment.getById(getId());
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 24 */     return getEnchantment().getMaxLevel();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getStartLevel() {
/* 29 */     return getEnchantment().getStartLevel();
/*    */   }
/*    */ 
/*    */   
/*    */   public EnchantmentTarget getItemTarget() {
/* 34 */     return getEnchantment().getItemTarget();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canEnchantItem(ItemStack item) {
/* 39 */     return getEnchantment().canEnchantItem(item);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 44 */     return getEnchantment().getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean conflictsWith(Enchantment other) {
/* 49 */     return getEnchantment().conflictsWith(other);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\enchantments\EnchantmentWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */