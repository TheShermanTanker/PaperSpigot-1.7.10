/*     */ package org.bukkit.enchantments;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.command.defaults.EnchantCommand;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Enchantment
/*     */ {
/*  16 */   public static final Enchantment PROTECTION_ENVIRONMENTAL = new EnchantmentWrapper(0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  21 */   public static final Enchantment PROTECTION_FIRE = new EnchantmentWrapper(1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  26 */   public static final Enchantment PROTECTION_FALL = new EnchantmentWrapper(2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   public static final Enchantment PROTECTION_EXPLOSIONS = new EnchantmentWrapper(3);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   public static final Enchantment PROTECTION_PROJECTILE = new EnchantmentWrapper(4);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   public static final Enchantment OXYGEN = new EnchantmentWrapper(5);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   public static final Enchantment WATER_WORKER = new EnchantmentWrapper(6);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   public static final Enchantment THORNS = new EnchantmentWrapper(7);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static final Enchantment DAMAGE_ALL = new EnchantmentWrapper(16);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public static final Enchantment DAMAGE_UNDEAD = new EnchantmentWrapper(17);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public static final Enchantment DAMAGE_ARTHROPODS = new EnchantmentWrapper(18);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public static final Enchantment KNOCKBACK = new EnchantmentWrapper(19);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static final Enchantment FIRE_ASPECT = new EnchantmentWrapper(20);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static final Enchantment LOOT_BONUS_MOBS = new EnchantmentWrapper(21);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public static final Enchantment DIG_SPEED = new EnchantmentWrapper(32);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static final Enchantment SILK_TOUCH = new EnchantmentWrapper(33);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public static final Enchantment DURABILITY = new EnchantmentWrapper(34);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public static final Enchantment LOOT_BONUS_BLOCKS = new EnchantmentWrapper(35);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public static final Enchantment ARROW_DAMAGE = new EnchantmentWrapper(48);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   public static final Enchantment ARROW_KNOCKBACK = new EnchantmentWrapper(49);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public static final Enchantment ARROW_FIRE = new EnchantmentWrapper(50);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public static final Enchantment ARROW_INFINITE = new EnchantmentWrapper(51);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public static final Enchantment LUCK = new EnchantmentWrapper(61);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public static final Enchantment LURE = new EnchantmentWrapper(62);
/*     */   
/* 134 */   private static final Map<Integer, Enchantment> byId = new HashMap<Integer, Enchantment>();
/* 135 */   private static final Map<String, Enchantment> byName = new HashMap<String, Enchantment>();
/*     */   private static boolean acceptingNew = true;
/*     */   private final int id;
/*     */   
/*     */   public Enchantment(int id) {
/* 140 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getId() {
/* 151 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getMaxLevel();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getStartLevel();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract EnchantmentTarget getItemTarget();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean conflictsWith(Enchantment paramEnchantment);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean canEnchantItem(ItemStack paramItemStack);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 204 */     if (obj == null) {
/* 205 */       return false;
/*     */     }
/* 207 */     if (!(obj instanceof Enchantment)) {
/* 208 */       return false;
/*     */     }
/* 210 */     Enchantment other = (Enchantment)obj;
/* 211 */     if (this.id != other.id) {
/* 212 */       return false;
/*     */     }
/* 214 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 219 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 224 */     return "Enchantment[" + this.id + ", " + getName() + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerEnchantment(Enchantment enchantment) {
/* 235 */     if (byId.containsKey(Integer.valueOf(enchantment.id)) || byName.containsKey(enchantment.getName()))
/* 236 */       throw new IllegalArgumentException("Cannot set already-set enchantment"); 
/* 237 */     if (!isAcceptingRegistrations()) {
/* 238 */       throw new IllegalStateException("No longer accepting new enchantments (can only be done by the server implementation)");
/*     */     }
/*     */     
/* 241 */     byId.put(Integer.valueOf(enchantment.id), enchantment);
/* 242 */     byName.put(enchantment.getName(), enchantment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAcceptingRegistrations() {
/* 251 */     return acceptingNew;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void stopAcceptingRegistrations() {
/* 258 */     acceptingNew = false;
/* 259 */     EnchantCommand.buildEnchantments();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Enchantment getById(int id) {
/* 271 */     return byId.get(Integer.valueOf(id));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Enchantment getByName(String name) {
/* 281 */     return byName.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Enchantment[] values() {
/* 290 */     return (Enchantment[])byId.values().toArray((Object[])new Enchantment[byId.size()]);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\enchantments\Enchantment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */