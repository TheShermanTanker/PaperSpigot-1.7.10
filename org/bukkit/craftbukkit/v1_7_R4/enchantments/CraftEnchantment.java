/*     */ package org.bukkit.craftbukkit.v1_7_R4.enchantments;
/*     */ 
/*     */ import net.minecraft.server.v1_7_R4.Enchantment;
/*     */ import net.minecraft.server.v1_7_R4.EnchantmentSlotType;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.enchantments.EnchantmentTarget;
/*     */ import org.bukkit.enchantments.EnchantmentWrapper;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class CraftEnchantment extends Enchantment {
/*     */   public CraftEnchantment(Enchantment target) {
/*  13 */     super(target.id);
/*  14 */     this.target = target;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxLevel() {
/*  19 */     return this.target.getMaxLevel();
/*     */   }
/*     */   private final Enchantment target;
/*     */   
/*     */   public int getStartLevel() {
/*  24 */     return this.target.getStartLevel();
/*     */   }
/*     */ 
/*     */   
/*     */   public EnchantmentTarget getItemTarget() {
/*  29 */     switch (this.target.slot) {
/*     */       case ALL:
/*  31 */         return EnchantmentTarget.ALL;
/*     */       case ARMOR:
/*  33 */         return EnchantmentTarget.ARMOR;
/*     */       case ARMOR_FEET:
/*  35 */         return EnchantmentTarget.ARMOR_FEET;
/*     */       case ARMOR_HEAD:
/*  37 */         return EnchantmentTarget.ARMOR_HEAD;
/*     */       case ARMOR_LEGS:
/*  39 */         return EnchantmentTarget.ARMOR_LEGS;
/*     */       case ARMOR_TORSO:
/*  41 */         return EnchantmentTarget.ARMOR_TORSO;
/*     */       case DIGGER:
/*  43 */         return EnchantmentTarget.TOOL;
/*     */       case WEAPON:
/*  45 */         return EnchantmentTarget.WEAPON;
/*     */       case BOW:
/*  47 */         return EnchantmentTarget.BOW;
/*     */       case FISHING_ROD:
/*  49 */         return EnchantmentTarget.FISHING_ROD;
/*     */     } 
/*  51 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canEnchantItem(ItemStack item) {
/*  57 */     return this.target.canEnchant(CraftItemStack.asNMSCopy(item));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  62 */     switch (this.target.id) {
/*     */       case 0:
/*  64 */         return "PROTECTION_ENVIRONMENTAL";
/*     */       case 1:
/*  66 */         return "PROTECTION_FIRE";
/*     */       case 2:
/*  68 */         return "PROTECTION_FALL";
/*     */       case 3:
/*  70 */         return "PROTECTION_EXPLOSIONS";
/*     */       case 4:
/*  72 */         return "PROTECTION_PROJECTILE";
/*     */       case 5:
/*  74 */         return "OXYGEN";
/*     */       case 6:
/*  76 */         return "WATER_WORKER";
/*     */       case 7:
/*  78 */         return "THORNS";
/*     */       case 16:
/*  80 */         return "DAMAGE_ALL";
/*     */       case 17:
/*  82 */         return "DAMAGE_UNDEAD";
/*     */       case 18:
/*  84 */         return "DAMAGE_ARTHROPODS";
/*     */       case 19:
/*  86 */         return "KNOCKBACK";
/*     */       case 20:
/*  88 */         return "FIRE_ASPECT";
/*     */       case 21:
/*  90 */         return "LOOT_BONUS_MOBS";
/*     */       case 32:
/*  92 */         return "DIG_SPEED";
/*     */       case 33:
/*  94 */         return "SILK_TOUCH";
/*     */       case 34:
/*  96 */         return "DURABILITY";
/*     */       case 35:
/*  98 */         return "LOOT_BONUS_BLOCKS";
/*     */       case 48:
/* 100 */         return "ARROW_DAMAGE";
/*     */       case 49:
/* 102 */         return "ARROW_KNOCKBACK";
/*     */       case 50:
/* 104 */         return "ARROW_FIRE";
/*     */       case 51:
/* 106 */         return "ARROW_INFINITE";
/*     */       case 61:
/* 108 */         return "LUCK";
/*     */       case 62:
/* 110 */         return "LURE";
/*     */     } 
/* 112 */     return "UNKNOWN_ENCHANT_" + this.target.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Enchantment getRaw(Enchantment enchantment) {
/* 117 */     if (enchantment instanceof EnchantmentWrapper) {
/* 118 */       enchantment = ((EnchantmentWrapper)enchantment).getEnchantment();
/*     */     }
/*     */     
/* 121 */     if (enchantment instanceof CraftEnchantment) {
/* 122 */       return ((CraftEnchantment)enchantment).target;
/*     */     }
/*     */     
/* 125 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean conflictsWith(Enchantment other) {
/* 130 */     if (other instanceof EnchantmentWrapper) {
/* 131 */       other = ((EnchantmentWrapper)other).getEnchantment();
/*     */     }
/* 133 */     if (!(other instanceof CraftEnchantment)) {
/* 134 */       return false;
/*     */     }
/* 136 */     CraftEnchantment ench = (CraftEnchantment)other;
/* 137 */     return !this.target.a(ench.target);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\enchantments\CraftEnchantment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */