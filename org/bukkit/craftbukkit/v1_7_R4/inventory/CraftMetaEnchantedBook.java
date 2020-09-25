/*     */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagCompound;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.inventory.meta.EnchantmentStorageMeta;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.Repairable;
/*     */ 
/*     */ @DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
/*     */ class CraftMetaEnchantedBook
/*     */   extends CraftMetaItem
/*     */   implements EnchantmentStorageMeta
/*     */ {
/*  19 */   static final CraftMetaItem.ItemMetaKey STORED_ENCHANTMENTS = new CraftMetaItem.ItemMetaKey("StoredEnchantments", "stored-enchants");
/*     */   
/*     */   private Map<Enchantment, Integer> enchantments;
/*     */   
/*     */   CraftMetaEnchantedBook(CraftMetaItem meta) {
/*  24 */     super(meta);
/*     */     
/*  26 */     if (!(meta instanceof CraftMetaEnchantedBook)) {
/*     */       return;
/*     */     }
/*     */     
/*  30 */     CraftMetaEnchantedBook that = (CraftMetaEnchantedBook)meta;
/*     */     
/*  32 */     if (that.hasEnchants()) {
/*  33 */       this.enchantments = new HashMap<Enchantment, Integer>(that.enchantments);
/*     */     }
/*     */   }
/*     */   
/*     */   CraftMetaEnchantedBook(NBTTagCompound tag) {
/*  38 */     super(tag);
/*     */     
/*  40 */     if (!tag.hasKey(STORED_ENCHANTMENTS.NBT)) {
/*     */       return;
/*     */     }
/*     */     
/*  44 */     this.enchantments = buildEnchantments(tag, STORED_ENCHANTMENTS);
/*     */   }
/*     */   
/*     */   CraftMetaEnchantedBook(Map<String, Object> map) {
/*  48 */     super(map);
/*     */     
/*  50 */     this.enchantments = buildEnchantments(map, STORED_ENCHANTMENTS);
/*     */   }
/*     */ 
/*     */   
/*     */   void applyToItem(NBTTagCompound itemTag) {
/*  55 */     super.applyToItem(itemTag);
/*     */     
/*  57 */     applyEnchantments(this.enchantments, itemTag, STORED_ENCHANTMENTS);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean applicableTo(Material type) {
/*  62 */     switch (type) {
/*     */       case ENCHANTED_BOOK:
/*  64 */         return true;
/*     */     } 
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isEmpty() {
/*  72 */     return (super.isEmpty() && isEnchantedEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   boolean equalsCommon(CraftMetaItem meta) {
/*  77 */     if (!super.equalsCommon(meta)) {
/*  78 */       return false;
/*     */     }
/*  80 */     if (meta instanceof CraftMetaEnchantedBook) {
/*  81 */       CraftMetaEnchantedBook that = (CraftMetaEnchantedBook)meta;
/*     */       
/*  83 */       return hasStoredEnchants() ? ((that.hasStoredEnchants() && this.enchantments.equals(that.enchantments))) : (!that.hasStoredEnchants());
/*     */     } 
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean notUncommon(CraftMetaItem meta) {
/*  90 */     return (super.notUncommon(meta) && (meta instanceof CraftMetaEnchantedBook || isEnchantedEmpty()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int applyHash() {
/*  96 */     int original = super.applyHash(), hash = original;
/*     */     
/*  98 */     if (hasStoredEnchants()) {
/*  99 */       hash = 61 * hash + this.enchantments.hashCode();
/*     */     }
/*     */     
/* 102 */     return (original != hash) ? (CraftMetaEnchantedBook.class.hashCode() ^ hash) : hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftMetaEnchantedBook clone() {
/* 107 */     CraftMetaEnchantedBook meta = (CraftMetaEnchantedBook)super.clone();
/*     */     
/* 109 */     if (this.enchantments != null) {
/* 110 */       meta.enchantments = new HashMap<Enchantment, Integer>(this.enchantments);
/*     */     }
/*     */     
/* 113 */     return meta;
/*     */   }
/*     */ 
/*     */   
/*     */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder) {
/* 118 */     super.serialize(builder);
/*     */     
/* 120 */     serializeEnchantments(this.enchantments, builder, STORED_ENCHANTMENTS);
/*     */     
/* 122 */     return builder;
/*     */   }
/*     */   
/*     */   boolean isEnchantedEmpty() {
/* 126 */     return !hasStoredEnchants();
/*     */   }
/*     */   
/*     */   public boolean hasStoredEnchant(Enchantment ench) {
/* 130 */     return (hasStoredEnchants() && this.enchantments.containsKey(ench));
/*     */   }
/*     */   
/*     */   public int getStoredEnchantLevel(Enchantment ench) {
/* 134 */     Integer level = hasStoredEnchants() ? this.enchantments.get(ench) : null;
/* 135 */     if (level == null) {
/* 136 */       return 0;
/*     */     }
/* 138 */     return level.intValue();
/*     */   }
/*     */   
/*     */   public Map<Enchantment, Integer> getStoredEnchants() {
/* 142 */     return hasStoredEnchants() ? (Map<Enchantment, Integer>)ImmutableMap.copyOf(this.enchantments) : (Map<Enchantment, Integer>)ImmutableMap.of();
/*     */   }
/*     */   
/*     */   public boolean addStoredEnchant(Enchantment ench, int level, boolean ignoreRestrictions) {
/* 146 */     if (this.enchantments == null) {
/* 147 */       this.enchantments = new HashMap<Enchantment, Integer>(4);
/*     */     }
/*     */     
/* 150 */     if (ignoreRestrictions || (level >= ench.getStartLevel() && level <= ench.getMaxLevel())) {
/* 151 */       Integer old = this.enchantments.put(ench, Integer.valueOf(level));
/* 152 */       return (old == null || old.intValue() != level);
/*     */     } 
/* 154 */     return false;
/*     */   }
/*     */   
/*     */   public boolean removeStoredEnchant(Enchantment ench) {
/* 158 */     return (hasStoredEnchants() && this.enchantments.remove(ench) != null);
/*     */   }
/*     */   
/*     */   public boolean hasStoredEnchants() {
/* 162 */     return (this.enchantments != null && !this.enchantments.isEmpty());
/*     */   }
/*     */   
/*     */   public boolean hasConflictingStoredEnchant(Enchantment ench) {
/* 166 */     return checkConflictingEnchants(this.enchantments, ench);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftMetaEnchantedBook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */