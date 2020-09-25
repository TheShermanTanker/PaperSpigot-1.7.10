/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.enchantments.CraftEnchantment;
/*     */ 
/*     */ public abstract class Enchantment
/*     */ {
/*   8 */   public static final Enchantment[] byId = new Enchantment[256];
/*     */   public static final Enchantment[] c;
/*  10 */   public static final Enchantment PROTECTION_ENVIRONMENTAL = new EnchantmentProtection(0, 10, 0);
/*  11 */   public static final Enchantment PROTECTION_FIRE = new EnchantmentProtection(1, 5, 1);
/*  12 */   public static final Enchantment PROTECTION_FALL = new EnchantmentProtection(2, 5, 2);
/*  13 */   public static final Enchantment PROTECTION_EXPLOSIONS = new EnchantmentProtection(3, 2, 3);
/*  14 */   public static final Enchantment PROTECTION_PROJECTILE = new EnchantmentProtection(4, 5, 4);
/*  15 */   public static final Enchantment OXYGEN = new EnchantmentOxygen(5, 2);
/*  16 */   public static final Enchantment WATER_WORKER = new EnchantmentWaterWorker(6, 2);
/*  17 */   public static final Enchantment THORNS = new EnchantmentThorns(7, 1);
/*  18 */   public static final Enchantment DAMAGE_ALL = new EnchantmentWeaponDamage(16, 10, 0);
/*  19 */   public static final Enchantment DAMAGE_UNDEAD = new EnchantmentWeaponDamage(17, 5, 1);
/*  20 */   public static final Enchantment DAMAGE_ARTHROPODS = new EnchantmentWeaponDamage(18, 5, 2);
/*  21 */   public static final Enchantment KNOCKBACK = new EnchantmentKnockback(19, 5);
/*  22 */   public static final Enchantment FIRE_ASPECT = new EnchantmentFire(20, 2);
/*  23 */   public static final Enchantment LOOT_BONUS_MOBS = new EnchantmentLootBonus(21, 2, EnchantmentSlotType.WEAPON);
/*  24 */   public static final Enchantment DIG_SPEED = new EnchantmentDigging(32, 10);
/*  25 */   public static final Enchantment SILK_TOUCH = new EnchantmentSilkTouch(33, 1);
/*  26 */   public static final Enchantment DURABILITY = new EnchantmentDurability(34, 5);
/*  27 */   public static final Enchantment LOOT_BONUS_BLOCKS = new EnchantmentLootBonus(35, 2, EnchantmentSlotType.DIGGER);
/*  28 */   public static final Enchantment ARROW_DAMAGE = new EnchantmentArrowDamage(48, 10);
/*  29 */   public static final Enchantment ARROW_KNOCKBACK = new EnchantmentArrowKnockback(49, 2);
/*  30 */   public static final Enchantment ARROW_FIRE = new EnchantmentFlameArrows(50, 2);
/*  31 */   public static final Enchantment ARROW_INFINITE = new EnchantmentInfiniteArrows(51, 1);
/*  32 */   public static final Enchantment LUCK = new EnchantmentLootBonus(61, 2, EnchantmentSlotType.FISHING_ROD);
/*  33 */   public static final Enchantment LURE = new EnchantmentLure(62, 2, EnchantmentSlotType.FISHING_ROD);
/*     */   public final int id;
/*     */   private final int weight;
/*     */   public EnchantmentSlotType slot;
/*     */   protected String name;
/*     */   
/*     */   protected Enchantment(int i, int j, EnchantmentSlotType enchantmentslottype) {
/*  40 */     this.id = i;
/*  41 */     this.weight = j;
/*  42 */     this.slot = enchantmentslottype;
/*  43 */     if (byId[i] != null) {
/*  44 */       throw new IllegalArgumentException("Duplicate enchantment id!");
/*     */     }
/*  46 */     byId[i] = this;
/*     */ 
/*     */     
/*  49 */     org.bukkit.enchantments.Enchantment.registerEnchantment((org.bukkit.enchantments.Enchantment)new CraftEnchantment(this));
/*     */   }
/*     */   
/*     */   public int getRandomWeight() {
/*  53 */     return this.weight;
/*     */   }
/*     */   
/*     */   public int getStartLevel() {
/*  57 */     return 1;
/*     */   }
/*     */   
/*     */   public int getMaxLevel() {
/*  61 */     return 1;
/*     */   }
/*     */   
/*     */   public int a(int i) {
/*  65 */     return 1 + i * 10;
/*     */   }
/*     */   
/*     */   public int b(int i) {
/*  69 */     return a(i) + 5;
/*     */   }
/*     */   
/*     */   public int a(int i, DamageSource damagesource) {
/*  73 */     return 0;
/*     */   }
/*     */   
/*     */   public float a(int i, EnumMonsterType enummonstertype) {
/*  77 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public boolean a(Enchantment enchantment) {
/*  81 */     return (this != enchantment);
/*     */   }
/*     */   
/*     */   public Enchantment b(String s) {
/*  85 */     this.name = s;
/*  86 */     return this;
/*     */   }
/*     */   
/*     */   public String a() {
/*  90 */     return "enchantment." + this.name;
/*     */   }
/*     */   
/*     */   public String c(int i) {
/*  94 */     String s = LocaleI18n.get(a());
/*     */     
/*  96 */     return s + " " + LocaleI18n.get("enchantment.level." + i);
/*     */   }
/*     */   
/*     */   public boolean canEnchant(ItemStack itemstack) {
/* 100 */     return this.slot.canEnchant(itemstack.getItem());
/*     */   }
/*     */   
/*     */   public void a(EntityLiving entityliving, Entity entity, int i) {}
/*     */   
/*     */   public void b(EntityLiving entityliving, Entity entity, int i) {}
/*     */   
/*     */   static {
/* 108 */     ArrayList<Enchantment> arraylist = new ArrayList();
/* 109 */     Enchantment[] aenchantment = byId;
/* 110 */     int i = aenchantment.length;
/*     */     
/* 112 */     for (int j = 0; j < i; j++) {
/* 113 */       Enchantment enchantment = aenchantment[j];
/*     */       
/* 115 */       if (enchantment != null) {
/* 116 */         arraylist.add(enchantment);
/*     */       }
/*     */     } 
/*     */     
/* 120 */     c = arraylist.<Enchantment>toArray(new Enchantment[0]);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Enchantment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */