/*     */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_7_R4.NBTBase;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagCompound;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagInt;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.inventory.meta.Repairable;
/*     */ 
/*     */ @DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
/*     */ class CraftMetaLeatherArmor
/*     */   extends CraftMetaItem
/*     */   implements LeatherArmorMeta
/*     */ {
/*  20 */   static final CraftMetaItem.ItemMetaKey COLOR = new CraftMetaItem.ItemMetaKey("color");
/*     */   
/*  22 */   private Color color = CraftItemFactory.DEFAULT_LEATHER_COLOR;
/*     */   
/*     */   CraftMetaLeatherArmor(CraftMetaItem meta) {
/*  25 */     super(meta);
/*  26 */     if (!(meta instanceof CraftMetaLeatherArmor)) {
/*     */       return;
/*     */     }
/*     */     
/*  30 */     CraftMetaLeatherArmor armorMeta = (CraftMetaLeatherArmor)meta;
/*  31 */     this.color = armorMeta.color;
/*     */   }
/*     */   
/*     */   CraftMetaLeatherArmor(NBTTagCompound tag) {
/*  35 */     super(tag);
/*  36 */     if (tag.hasKey(DISPLAY.NBT)) {
/*  37 */       NBTTagCompound display = tag.getCompound(DISPLAY.NBT);
/*  38 */       if (display.hasKey(COLOR.NBT)) {
/*  39 */         this.color = Color.fromRGB(display.getInt(COLOR.NBT));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   CraftMetaLeatherArmor(Map<String, Object> map) {
/*  45 */     super(map);
/*  46 */     setColor(CraftMetaItem.SerializableMeta.<Color>getObject(Color.class, map, COLOR.BUKKIT, true));
/*     */   }
/*     */ 
/*     */   
/*     */   void applyToItem(NBTTagCompound itemTag) {
/*  51 */     super.applyToItem(itemTag);
/*     */     
/*  53 */     if (hasColor()) {
/*  54 */       setDisplayTag(itemTag, COLOR.NBT, (NBTBase)new NBTTagInt(this.color.asRGB()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isEmpty() {
/*  60 */     return (super.isEmpty() && isLeatherArmorEmpty());
/*     */   }
/*     */   
/*     */   boolean isLeatherArmorEmpty() {
/*  64 */     return !hasColor();
/*     */   }
/*     */ 
/*     */   
/*     */   boolean applicableTo(Material type) {
/*  69 */     switch (type) {
/*     */       case LEATHER_HELMET:
/*     */       case LEATHER_CHESTPLATE:
/*     */       case LEATHER_LEGGINGS:
/*     */       case LEATHER_BOOTS:
/*  74 */         return true;
/*     */     } 
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CraftMetaLeatherArmor clone() {
/*  82 */     return (CraftMetaLeatherArmor)super.clone();
/*     */   }
/*     */   
/*     */   public Color getColor() {
/*  86 */     return this.color;
/*     */   }
/*     */   
/*     */   public void setColor(Color color) {
/*  90 */     this.color = (color == null) ? CraftItemFactory.DEFAULT_LEATHER_COLOR : color;
/*     */   }
/*     */   
/*     */   boolean hasColor() {
/*  94 */     return !CraftItemFactory.DEFAULT_LEATHER_COLOR.equals(this.color);
/*     */   }
/*     */ 
/*     */   
/*     */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder) {
/*  99 */     super.serialize(builder);
/*     */     
/* 101 */     if (hasColor()) {
/* 102 */       builder.put(COLOR.BUKKIT, this.color);
/*     */     }
/*     */     
/* 105 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean equalsCommon(CraftMetaItem meta) {
/* 110 */     if (!super.equalsCommon(meta)) {
/* 111 */       return false;
/*     */     }
/* 113 */     if (meta instanceof CraftMetaLeatherArmor) {
/* 114 */       CraftMetaLeatherArmor that = (CraftMetaLeatherArmor)meta;
/*     */       
/* 116 */       return this.color.equals(that.color);
/*     */     } 
/* 118 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean notUncommon(CraftMetaItem meta) {
/* 123 */     return (super.notUncommon(meta) && (meta instanceof CraftMetaLeatherArmor || isLeatherArmorEmpty()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int applyHash() {
/* 129 */     int original = super.applyHash(), hash = original;
/* 130 */     if (hasColor()) {
/* 131 */       hash ^= this.color.hashCode();
/*     */     }
/* 133 */     return (original != hash) ? (CraftMetaSkull.class.hashCode() ^ hash) : hash;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftMetaLeatherArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */