/*     */ package org.bukkit.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.material.MaterialData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemStack
/*     */   implements Cloneable, ConfigurationSerializable
/*     */ {
/*  20 */   private int type = 0;
/*  21 */   private int amount = 0;
/*  22 */   private MaterialData data = null;
/*  23 */   private short durability = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ItemMeta meta;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ItemStack(int type) {
/*  37 */     this(type, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack(Material type) {
/*  46 */     this(type, 1);
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
/*     */   public ItemStack(int type, int amount) {
/*  58 */     this(type, amount, (short)0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack(Material type, int amount) {
/*  68 */     this(type.getId(), amount);
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
/*     */   @Deprecated
/*     */   public ItemStack(int type, int amount, short damage) {
/*  81 */     this.type = type;
/*  82 */     this.amount = amount;
/*  83 */     this.durability = damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack(Material type, int amount, short damage) {
/*  94 */     this(type.getId(), amount, damage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ItemStack(int type, int amount, short damage, Byte data) {
/* 102 */     this.type = type;
/* 103 */     this.amount = amount;
/* 104 */     this.durability = damage;
/* 105 */     if (data != null) {
/* 106 */       createData(data.byteValue());
/* 107 */       this.durability = (short)data.byteValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ItemStack(Material type, int amount, short damage, Byte data) {
/* 116 */     this(type.getId(), amount, damage, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack(ItemStack stack) throws IllegalArgumentException {
/* 127 */     Validate.notNull(stack, "Cannot copy null stack");
/* 128 */     this.type = stack.getTypeId();
/* 129 */     this.amount = stack.getAmount();
/* 130 */     this.durability = stack.getDurability();
/* 131 */     this.data = stack.getData();
/* 132 */     if (stack.hasItemMeta()) {
/* 133 */       setItemMeta0(stack.getItemMeta(), getType0());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Material getType() {
/* 144 */     return getType0(getTypeId());
/*     */   }
/*     */   
/*     */   private Material getType0() {
/* 148 */     return getType0(this.type);
/*     */   }
/*     */   
/*     */   private static Material getType0(int id) {
/* 152 */     Material material = Material.getMaterial(id);
/* 153 */     return (material == null) ? Material.AIR : material;
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
/*     */   public void setType(Material type) {
/* 165 */     Validate.notNull(type, "Material cannot be null");
/* 166 */     setTypeId(type.getId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getTypeId() {
/* 177 */     return this.type;
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
/*     */   @Deprecated
/*     */   public void setTypeId(int type) {
/* 190 */     this.type = type;
/* 191 */     if (this.meta != null) {
/* 192 */       this.meta = Bukkit.getItemFactory().asMetaFor(this.meta, getType0());
/*     */     }
/* 194 */     createData((byte)0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAmount() {
/* 203 */     return this.amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAmount(int amount) {
/* 212 */     this.amount = amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MaterialData getData() {
/* 221 */     Material mat = getType();
/* 222 */     if (this.data == null && mat != null && mat.getData() != null) {
/* 223 */       this.data = mat.getNewData((byte)getDurability());
/*     */     }
/*     */     
/* 226 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(MaterialData data) {
/* 235 */     Material mat = getType();
/*     */     
/* 237 */     if (data == null || mat == null || mat.getData() == null) {
/* 238 */       this.data = data;
/*     */     }
/* 240 */     else if (data.getClass() == mat.getData() || data.getClass() == MaterialData.class) {
/* 241 */       this.data = data;
/*     */     } else {
/* 243 */       throw new IllegalArgumentException("Provided data is not of type " + mat.getData().getName() + ", found " + data.getClass().getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDurability(short durability) {
/* 254 */     this.durability = durability;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getDurability() {
/* 263 */     return this.durability;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStackSize() {
/* 274 */     Material material = getType();
/* 275 */     if (material != null) {
/* 276 */       return material.getMaxStackSize();
/*     */     }
/* 278 */     return -1;
/*     */   }
/*     */   
/*     */   private void createData(byte data) {
/* 282 */     Material mat = Material.getMaterial(this.type);
/*     */     
/* 284 */     if (mat == null) {
/* 285 */       this.data = new MaterialData(this.type, data);
/*     */     } else {
/* 287 */       this.data = mat.getNewData(data);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 294 */     StringBuilder toString = (new StringBuilder("ItemStack{")).append(getType().name()).append(" x ").append(getAmount());
/* 295 */     if (hasItemMeta()) {
/* 296 */       toString.append(", ").append(getItemMeta());
/*     */     }
/* 298 */     return toString.append('}').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 304 */     if (this == obj) {
/* 305 */       return true;
/*     */     }
/* 307 */     if (!(obj instanceof ItemStack)) {
/* 308 */       return false;
/*     */     }
/*     */     
/* 311 */     ItemStack stack = (ItemStack)obj;
/* 312 */     return (getAmount() == stack.getAmount() && isSimilar(stack));
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
/*     */   public boolean isSimilar(ItemStack stack) {
/* 324 */     if (stack == null) {
/* 325 */       return false;
/*     */     }
/* 327 */     if (stack == this) {
/* 328 */       return true;
/*     */     }
/* 330 */     return (getTypeId() == stack.getTypeId() && getDurability() == stack.getDurability() && hasItemMeta() == stack.hasItemMeta() && (!hasItemMeta() || Bukkit.getItemFactory().equals(getItemMeta(), stack.getItemMeta())));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack clone() {
/*     */     try {
/* 336 */       ItemStack itemStack = (ItemStack)super.clone();
/*     */       
/* 338 */       if (this.meta != null) {
/* 339 */         itemStack.meta = this.meta.clone();
/*     */       }
/*     */       
/* 342 */       if (this.data != null) {
/* 343 */         itemStack.data = this.data.clone();
/*     */       }
/*     */       
/* 346 */       return itemStack;
/* 347 */     } catch (CloneNotSupportedException e) {
/* 348 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/* 355 */     int hash = 1;
/*     */     
/* 357 */     hash = hash * 31 + getTypeId();
/* 358 */     hash = hash * 31 + getAmount();
/* 359 */     hash = hash * 31 + (getDurability() & 0xFFFF);
/* 360 */     hash = hash * 31 + (hasItemMeta() ? ((this.meta == null) ? getItemMeta().hashCode() : this.meta.hashCode()) : 0);
/*     */     
/* 362 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsEnchantment(Enchantment ench) {
/* 372 */     return (this.meta == null) ? false : this.meta.hasEnchant(ench);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnchantmentLevel(Enchantment ench) {
/* 382 */     return (this.meta == null) ? 0 : this.meta.getEnchantLevel(ench);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Enchantment, Integer> getEnchantments() {
/* 391 */     return (this.meta == null) ? (Map<Enchantment, Integer>)ImmutableMap.of() : this.meta.getEnchants();
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
/*     */ 
/*     */   
/*     */   public void addEnchantments(Map<Enchantment, Integer> enchantments) {
/* 409 */     Validate.notNull(enchantments, "Enchantments cannot be null");
/* 410 */     for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
/* 411 */       addEnchantment(entry.getKey(), ((Integer)entry.getValue()).intValue());
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
/*     */   public void addEnchantment(Enchantment ench, int level) {
/* 428 */     Validate.notNull(ench, "Enchantment cannot be null");
/* 429 */     if (level < ench.getStartLevel() || level > ench.getMaxLevel())
/* 430 */       throw new IllegalArgumentException("Enchantment level is either too low or too high (given " + level + ", bounds are " + ench.getStartLevel() + " to " + ench.getMaxLevel() + ")"); 
/* 431 */     if (!ench.canEnchantItem(this)) {
/* 432 */       throw new IllegalArgumentException("Specified enchantment cannot be applied to this itemstack");
/*     */     }
/*     */     
/* 435 */     addUnsafeEnchantment(ench, level);
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
/*     */   public void addUnsafeEnchantments(Map<Enchantment, Integer> enchantments) {
/* 449 */     for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
/* 450 */       addUnsafeEnchantment(entry.getKey(), ((Integer)entry.getValue()).intValue());
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
/*     */   public void addUnsafeEnchantment(Enchantment ench, int level) {
/* 467 */     ((this.meta == null) ? (this.meta = Bukkit.getItemFactory().getItemMeta(getType0())) : this.meta).addEnchant(ench, level, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int removeEnchantment(Enchantment ench) {
/* 478 */     int level = getEnchantmentLevel(ench);
/* 479 */     if (level == 0 || this.meta == null) {
/* 480 */       return level;
/*     */     }
/* 482 */     this.meta.removeEnchant(ench);
/* 483 */     return level;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Object> serialize() {
/* 488 */     Map<String, Object> result = new LinkedHashMap<String, Object>();
/*     */     
/* 490 */     result.put("type", getType().name());
/*     */     
/* 492 */     if (getDurability() != 0) {
/* 493 */       result.put("damage", Short.valueOf(getDurability()));
/*     */     }
/*     */     
/* 496 */     if (getAmount() != 1) {
/* 497 */       result.put("amount", Integer.valueOf(getAmount()));
/*     */     }
/*     */     
/* 500 */     ItemMeta meta = getItemMeta();
/* 501 */     if (!Bukkit.getItemFactory().equals(meta, null)) {
/* 502 */       result.put("meta", meta);
/*     */     }
/*     */     
/* 505 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack deserialize(Map<String, Object> args) {
/* 516 */     Material type = Material.getMaterial((String)args.get("type"));
/* 517 */     short damage = 0;
/* 518 */     int amount = 1;
/*     */     
/* 520 */     if (args.containsKey("damage")) {
/* 521 */       damage = ((Number)args.get("damage")).shortValue();
/*     */     }
/*     */     
/* 524 */     if (args.containsKey("amount")) {
/* 525 */       amount = ((Integer)args.get("amount")).intValue();
/*     */     }
/*     */     
/* 528 */     ItemStack result = new ItemStack(type, amount, damage);
/*     */     
/* 530 */     if (args.containsKey("enchantments")) {
/* 531 */       Object raw = args.get("enchantments");
/*     */       
/* 533 */       if (raw instanceof Map) {
/* 534 */         Map<?, ?> map = (Map<?, ?>)raw;
/*     */         
/* 536 */         for (Map.Entry<?, ?> entry : map.entrySet()) {
/* 537 */           Enchantment enchantment = Enchantment.getByName(entry.getKey().toString());
/*     */           
/* 539 */           if (enchantment != null && entry.getValue() instanceof Integer) {
/* 540 */             result.addUnsafeEnchantment(enchantment, ((Integer)entry.getValue()).intValue());
/*     */           }
/*     */         } 
/*     */       } 
/* 544 */     } else if (args.containsKey("meta")) {
/* 545 */       Object raw = args.get("meta");
/* 546 */       if (raw instanceof ItemMeta) {
/* 547 */         result.setItemMeta((ItemMeta)raw);
/*     */       }
/*     */     } 
/*     */     
/* 551 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemMeta getItemMeta() {
/* 560 */     return (this.meta == null) ? Bukkit.getItemFactory().getItemMeta(getType0()) : this.meta.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasItemMeta() {
/* 569 */     return !Bukkit.getItemFactory().equals(this.meta, null);
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
/*     */   public boolean setItemMeta(ItemMeta itemMeta) {
/* 582 */     return setItemMeta0(itemMeta, getType0());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean setItemMeta0(ItemMeta itemMeta, Material material) {
/* 589 */     if (itemMeta == null) {
/* 590 */       this.meta = null;
/* 591 */       return true;
/*     */     } 
/* 593 */     if (!Bukkit.getItemFactory().isApplicable(itemMeta, material)) {
/* 594 */       return false;
/*     */     }
/* 596 */     this.meta = Bukkit.getItemFactory().asMetaFor(itemMeta, material);
/* 597 */     if (this.meta == itemMeta) {
/* 598 */       this.meta = itemMeta.clone();
/*     */     }
/*     */     
/* 601 */     return true;
/*     */   }
/*     */   
/*     */   protected ItemStack() {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\ItemStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */