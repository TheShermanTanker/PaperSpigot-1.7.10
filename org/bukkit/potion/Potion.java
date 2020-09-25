/*     */ package org.bukkit.potion;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Potion
/*     */ {
/*     */   private boolean extended = false;
/*     */   private boolean splash = false;
/*  18 */   private int level = 1;
/*  19 */   private int name = -1;
/*     */   
/*     */   private PotionType type;
/*     */   
/*     */   private static PotionBrewer brewer;
/*     */   private static final int EXTENDED_BIT = 64;
/*     */   private static final int POTION_BIT = 15;
/*     */   private static final int SPLASH_BIT = 16384;
/*     */   private static final int TIER_BIT = 32;
/*     */   private static final int TIER_SHIFT = 5;
/*     */   private static final int NAME_BIT = 63;
/*     */   
/*     */   public Potion(PotionType type) {
/*  32 */     this.type = type;
/*  33 */     if (type != null) {
/*  34 */       this.name = type.getDamageValue();
/*     */     }
/*  36 */     if (type == null || type == PotionType.WATER) {
/*  37 */       this.level = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Potion(PotionType type, Tier tier) {
/*  47 */     this(type, (tier == Tier.TWO) ? 2 : 1);
/*  48 */     Validate.notNull(type, "Type cannot be null");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Potion(PotionType type, Tier tier, boolean splash) {
/*  57 */     this(type, (tier == Tier.TWO) ? 2 : 1, splash);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Potion(PotionType type, Tier tier, boolean splash, boolean extended) {
/*  67 */     this(type, tier, splash);
/*  68 */     this.extended = extended;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Potion(PotionType type, int level) {
/*  78 */     this(type);
/*  79 */     Validate.notNull(type, "Type cannot be null");
/*  80 */     Validate.isTrue((type != PotionType.WATER), "Water bottles don't have a level!");
/*  81 */     Validate.isTrue((level > 0 && level < 3), "Level must be 1 or 2");
/*  82 */     this.level = level;
/*     */   }
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
/*     */   @Deprecated
/*     */   public Potion(PotionType type, int level, boolean splash) {
/*  96 */     this(type, level);
/*  97 */     this.splash = splash;
/*     */   }
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
/*     */   @Deprecated
/*     */   public Potion(PotionType type, int level, boolean splash, boolean extended) {
/* 112 */     this(type, level, splash);
/* 113 */     this.extended = extended;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Potion(int name) {
/* 122 */     this(PotionType.getByDamageValue(name & 0xF));
/* 123 */     this.name = name & 0x3F;
/* 124 */     if ((name & 0xF) == 0)
/*     */     {
/* 126 */       this.type = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Potion splash() {
/* 136 */     setSplash(true);
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Potion extend() {
/* 146 */     setHasExtendedDuration(true);
/* 147 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(ItemStack to) {
/* 157 */     Validate.notNull(to, "itemstack cannot be null");
/* 158 */     Validate.isTrue((to.getType() == Material.POTION), "given itemstack is not a potion");
/* 159 */     to.setDurability(toDamageValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(LivingEntity to) {
/* 170 */     Validate.notNull(to, "entity cannot be null");
/* 171 */     to.addPotionEffects(getEffects());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 176 */     if (this == obj) {
/* 177 */       return true;
/*     */     }
/* 179 */     if (obj == null || getClass() != obj.getClass()) {
/* 180 */       return false;
/*     */     }
/* 182 */     Potion other = (Potion)obj;
/* 183 */     return (this.extended == other.extended && this.splash == other.splash && this.level == other.level && this.type == other.type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PotionEffect> getEffects() {
/* 195 */     if (this.type == null) return (Collection<PotionEffect>)ImmutableList.of(); 
/* 196 */     return getBrewer().getEffectsFromDamage(toDamageValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLevel() {
/* 205 */     return this.level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Tier getTier() {
/* 215 */     return (this.level == 2) ? Tier.TWO : Tier.ONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PotionType getType() {
/* 224 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasExtendedDuration() {
/* 233 */     return this.extended;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 238 */     int prime = 31;
/* 239 */     int result = 31 + this.level;
/* 240 */     result = 31 * result + (this.extended ? 1231 : 1237);
/* 241 */     result = 31 * result + (this.splash ? 1231 : 1237);
/* 242 */     result = 31 * result + ((this.type == null) ? 0 : this.type.hashCode());
/* 243 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSplash() {
/* 252 */     return this.splash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHasExtendedDuration(boolean isExtended) {
/* 262 */     Validate.isTrue((this.type == null || !this.type.isInstant()), "Instant potions cannot be extended");
/* 263 */     this.extended = isExtended;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSplash(boolean isSplash) {
/* 273 */     this.splash = isSplash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setTier(Tier tier) {
/* 284 */     Validate.notNull(tier, "tier cannot be null");
/* 285 */     this.level = (tier == Tier.TWO) ? 2 : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(PotionType type) {
/* 294 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLevel(int level) {
/* 303 */     Validate.notNull(this.type, "No-effect potions don't have a level.");
/* 304 */     int max = this.type.getMaxLevel();
/* 305 */     Validate.isTrue((level > 0 && level <= max), "Level must be " + ((max == 1) ? "" : "between 1 and ") + max + " for this potion");
/* 306 */     this.level = level;
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
/*     */   public short toDamageValue() {
/*     */     short damage;
/* 319 */     if (this.type == PotionType.WATER)
/* 320 */       return 0; 
/* 321 */     if (this.type == null) {
/*     */       
/* 323 */       damage = (short)((this.name == 0) ? 8192 : this.name);
/*     */     } else {
/* 325 */       damage = (short)(this.level - 1);
/* 326 */       damage = (short)(damage << 5);
/* 327 */       damage = (short)(damage | (short)this.type.getDamageValue());
/*     */     } 
/* 329 */     if (this.splash) {
/* 330 */       damage = (short)(damage | 0x4000);
/*     */     }
/* 332 */     if (this.extended) {
/* 333 */       damage = (short)(damage | 0x40);
/*     */     }
/* 335 */     return damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack toItemStack(int amount) {
/* 346 */     return new ItemStack(Material.POTION, amount, toDamageValue());
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public enum Tier {
/* 351 */     ONE(0),
/* 352 */     TWO(32);
/*     */     
/*     */     private int damageBit;
/*     */     
/*     */     Tier(int bit) {
/* 357 */       this.damageBit = bit;
/*     */     }
/*     */     
/*     */     public int getDamageBit() {
/* 361 */       return this.damageBit;
/*     */     }
/*     */     
/*     */     public static Tier getByDamageBit(int damageBit) {
/* 365 */       for (Tier tier : values()) {
/* 366 */         if (tier.damageBit == damageBit)
/* 367 */           return tier; 
/*     */       } 
/* 369 */       return null;
/*     */     }
/*     */   }
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
/*     */   @Deprecated
/*     */   public static Potion fromDamage(int damage) {
/*     */     Potion potion;
/* 388 */     PotionType type = PotionType.getByDamageValue(damage & 0xF);
/*     */     
/* 390 */     if (type == null || (type == PotionType.WATER && damage != 0)) {
/* 391 */       potion = new Potion(damage & 0x3F);
/*     */     } else {
/* 393 */       int level = (damage & 0x20) >> 5;
/* 394 */       level++;
/* 395 */       potion = new Potion(type, level);
/*     */     } 
/* 397 */     if ((damage & 0x4000) > 0) {
/* 398 */       potion = potion.splash();
/*     */     }
/* 400 */     if ((type == null || !type.isInstant()) && (damage & 0x40) > 0) {
/* 401 */       potion = potion.extend();
/*     */     }
/* 403 */     return potion;
/*     */   }
/*     */   
/*     */   public static Potion fromItemStack(ItemStack item) {
/* 407 */     Validate.notNull(item, "item cannot be null");
/* 408 */     if (item.getType() != Material.POTION)
/* 409 */       throw new IllegalArgumentException("item is not a potion"); 
/* 410 */     return fromDamage(item.getDurability());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PotionBrewer getBrewer() {
/* 419 */     return brewer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setPotionBrewer(PotionBrewer other) {
/* 429 */     if (brewer != null)
/* 430 */       throw new IllegalArgumentException("brewer can only be set internally"); 
/* 431 */     brewer = other;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getNameId() {
/* 440 */     return this.name;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\potion\Potion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */