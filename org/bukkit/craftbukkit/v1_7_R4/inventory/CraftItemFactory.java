/*     */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerialization;
/*     */ import org.bukkit.inventory.ItemFactory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public final class CraftItemFactory
/*     */   implements ItemFactory
/*     */ {
/*  16 */   static final Color DEFAULT_LEATHER_COLOR = Color.fromRGB(10511680);
/*     */ 
/*     */   
/*     */   static final Collection<String> KNOWN_NBT_ATTRIBUTE_NAMES;
/*     */   
/*  21 */   private static final CraftItemFactory instance = new CraftItemFactory(); static {
/*  22 */     ConfigurationSerialization.registerClass(CraftMetaItem.SerializableMeta.class);
/*  23 */     KNOWN_NBT_ATTRIBUTE_NAMES = (Collection<String>)ImmutableSet.builder().add("generic.attackDamage").add("generic.followRange").add("generic.knockbackResistance").add("generic.maxHealth").add("generic.movementSpeed").add("horse.jumpStrength").add("zombie.spawnReinforcements").build();
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
/*     */   public boolean isApplicable(ItemMeta meta, ItemStack itemstack) {
/*  38 */     if (itemstack == null) {
/*  39 */       return false;
/*     */     }
/*  41 */     return isApplicable(meta, itemstack.getType());
/*     */   }
/*     */   
/*     */   public boolean isApplicable(ItemMeta meta, Material type) {
/*  45 */     if (type == null || meta == null) {
/*  46 */       return false;
/*     */     }
/*  48 */     if (!(meta instanceof CraftMetaItem)) {
/*  49 */       throw new IllegalArgumentException("Meta of " + meta.getClass().toString() + " not created by " + CraftItemFactory.class.getName());
/*     */     }
/*     */     
/*  52 */     return ((CraftMetaItem)meta).applicableTo(type);
/*     */   }
/*     */   
/*     */   public ItemMeta getItemMeta(Material material) {
/*  56 */     Validate.notNull(material, "Material cannot be null");
/*  57 */     return getItemMeta(material, null);
/*     */   }
/*     */   
/*     */   private ItemMeta getItemMeta(Material material, CraftMetaItem meta) {
/*  61 */     switch (material) {
/*     */       case AIR:
/*  63 */         return null;
/*     */       case WRITTEN_BOOK:
/*     */       case BOOK_AND_QUILL:
/*  66 */         return (meta instanceof CraftMetaBook) ? meta : new CraftMetaBook(meta);
/*     */       case SKULL_ITEM:
/*  68 */         return (meta instanceof CraftMetaSkull) ? meta : new CraftMetaSkull(meta);
/*     */       case LEATHER_HELMET:
/*     */       case LEATHER_CHESTPLATE:
/*     */       case LEATHER_LEGGINGS:
/*     */       case LEATHER_BOOTS:
/*  73 */         return (meta instanceof CraftMetaLeatherArmor) ? meta : new CraftMetaLeatherArmor(meta);
/*     */       case POTION:
/*  75 */         return (meta instanceof CraftMetaPotion) ? meta : new CraftMetaPotion(meta);
/*     */       case MAP:
/*  77 */         return (meta instanceof CraftMetaMap) ? meta : new CraftMetaMap(meta);
/*     */       case FIREWORK:
/*  79 */         return (meta instanceof CraftMetaFirework) ? meta : new CraftMetaFirework(meta);
/*     */       case FIREWORK_CHARGE:
/*  81 */         return (meta instanceof CraftMetaCharge) ? meta : new CraftMetaCharge(meta);
/*     */       case ENCHANTED_BOOK:
/*  83 */         return (meta instanceof CraftMetaEnchantedBook) ? meta : new CraftMetaEnchantedBook(meta);
/*     */     } 
/*  85 */     return new CraftMetaItem(meta);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(ItemMeta meta1, ItemMeta meta2) {
/*  90 */     if (meta1 == meta2) {
/*  91 */       return true;
/*     */     }
/*  93 */     if (meta1 != null && !(meta1 instanceof CraftMetaItem)) {
/*  94 */       throw new IllegalArgumentException("First meta of " + meta1.getClass().getName() + " does not belong to " + CraftItemFactory.class.getName());
/*     */     }
/*  96 */     if (meta2 != null && !(meta2 instanceof CraftMetaItem)) {
/*  97 */       throw new IllegalArgumentException("Second meta " + meta2.getClass().getName() + " does not belong to " + CraftItemFactory.class.getName());
/*     */     }
/*  99 */     if (meta1 == null) {
/* 100 */       return ((CraftMetaItem)meta2).isEmpty();
/*     */     }
/* 102 */     if (meta2 == null) {
/* 103 */       return ((CraftMetaItem)meta1).isEmpty();
/*     */     }
/*     */     
/* 106 */     return equals((CraftMetaItem)meta1, (CraftMetaItem)meta2);
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
/*     */   boolean equals(CraftMetaItem meta1, CraftMetaItem meta2) {
/* 119 */     return (meta1.equalsCommon(meta2) && meta1.notUncommon(meta2) && meta2.notUncommon(meta1));
/*     */   }
/*     */   
/*     */   public static CraftItemFactory instance() {
/* 123 */     return instance;
/*     */   }
/*     */   
/*     */   public ItemMeta asMetaFor(ItemMeta meta, ItemStack stack) {
/* 127 */     Validate.notNull(stack, "Stack cannot be null");
/* 128 */     return asMetaFor(meta, stack.getType());
/*     */   }
/*     */   
/*     */   public ItemMeta asMetaFor(ItemMeta meta, Material material) {
/* 132 */     Validate.notNull(material, "Material cannot be null");
/* 133 */     if (!(meta instanceof CraftMetaItem)) {
/* 134 */       throw new IllegalArgumentException("Meta of " + ((meta != null) ? meta.getClass().toString() : "null") + " not created by " + CraftItemFactory.class.getName());
/*     */     }
/* 136 */     return getItemMeta(material, (CraftMetaItem)meta);
/*     */   }
/*     */   
/*     */   public Color getDefaultLeatherColor() {
/* 140 */     return DEFAULT_LEATHER_COLOR;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftItemFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */