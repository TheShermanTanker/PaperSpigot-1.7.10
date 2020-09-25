/*     */ package org.bukkit.enchantments;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum EnchantmentTarget
/*     */ {
/*  13 */   ALL
/*     */   {
/*     */     public boolean includes(Material item) {
/*  16 */       return true;
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  23 */   ARMOR
/*     */   {
/*     */     public boolean includes(Material item) {
/*  26 */       return (ARMOR_FEET.includes(item) || ARMOR_LEGS.includes(item) || ARMOR_HEAD.includes(item) || ARMOR_TORSO.includes(item));
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   ARMOR_FEET
/*     */   {
/*     */     public boolean includes(Material item) {
/*  39 */       return (item.equals(Material.LEATHER_BOOTS) || item.equals(Material.CHAINMAIL_BOOTS) || item.equals(Material.IRON_BOOTS) || item.equals(Material.DIAMOND_BOOTS) || item.equals(Material.GOLD_BOOTS));
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   ARMOR_LEGS
/*     */   {
/*     */     public boolean includes(Material item) {
/*  53 */       return (item.equals(Material.LEATHER_LEGGINGS) || item.equals(Material.CHAINMAIL_LEGGINGS) || item.equals(Material.IRON_LEGGINGS) || item.equals(Material.DIAMOND_LEGGINGS) || item.equals(Material.GOLD_LEGGINGS));
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   ARMOR_TORSO
/*     */   {
/*     */     public boolean includes(Material item) {
/*  67 */       return (item.equals(Material.LEATHER_CHESTPLATE) || item.equals(Material.CHAINMAIL_CHESTPLATE) || item.equals(Material.IRON_CHESTPLATE) || item.equals(Material.DIAMOND_CHESTPLATE) || item.equals(Material.GOLD_CHESTPLATE));
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   ARMOR_HEAD
/*     */   {
/*     */     public boolean includes(Material item) {
/*  81 */       return (item.equals(Material.LEATHER_HELMET) || item.equals(Material.CHAINMAIL_HELMET) || item.equals(Material.DIAMOND_HELMET) || item.equals(Material.IRON_HELMET) || item.equals(Material.GOLD_HELMET));
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   WEAPON
/*     */   {
/*     */     public boolean includes(Material item) {
/*  95 */       return (item.equals(Material.WOOD_SWORD) || item.equals(Material.STONE_SWORD) || item.equals(Material.IRON_SWORD) || item.equals(Material.DIAMOND_SWORD) || item.equals(Material.GOLD_SWORD));
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   TOOL
/*     */   {
/*     */     public boolean includes(Material item) {
/* 110 */       return (item.equals(Material.WOOD_SPADE) || item.equals(Material.STONE_SPADE) || item.equals(Material.IRON_SPADE) || item.equals(Material.DIAMOND_SPADE) || item.equals(Material.GOLD_SPADE) || item.equals(Material.WOOD_PICKAXE) || item.equals(Material.STONE_PICKAXE) || item.equals(Material.IRON_PICKAXE) || item.equals(Material.DIAMOND_PICKAXE) || item.equals(Material.GOLD_PICKAXE) || item.equals(Material.WOOD_HOE) || item.equals(Material.STONE_HOE) || item.equals(Material.IRON_HOE) || item.equals(Material.DIAMOND_HOE) || item.equals(Material.GOLD_HOE) || item.equals(Material.WOOD_AXE) || item.equals(Material.STONE_AXE) || item.equals(Material.IRON_AXE) || item.equals(Material.DIAMOND_AXE) || item.equals(Material.GOLD_AXE) || item.equals(Material.SHEARS) || item.equals(Material.FLINT_AND_STEEL));
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   BOW
/*     */   {
/*     */     public boolean includes(Material item) {
/* 141 */       return item.equals(Material.BOW);
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   FISHING_ROD
/*     */   {
/*     */     public boolean includes(Material item) {
/* 151 */       return item.equals(Material.FISHING_ROD);
/*     */     }
/*     */   };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean includes(ItemStack item) {
/* 170 */     return includes(item.getType());
/*     */   }
/*     */   
/*     */   public abstract boolean includes(Material paramMaterial);
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\enchantments\EnchantmentTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */