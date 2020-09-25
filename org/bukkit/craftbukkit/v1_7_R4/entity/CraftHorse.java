/*     */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.server.v1_7_R4.Entity;
/*     */ import net.minecraft.server.v1_7_R4.EntityAgeable;
/*     */ import net.minecraft.server.v1_7_R4.EntityAnimal;
/*     */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*     */ import net.minecraft.server.v1_7_R4.EntityHorse;
/*     */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.entity.AnimalTamer;
/*     */ import org.bukkit.entity.Horse;
/*     */ import org.bukkit.inventory.HorseInventory;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ 
/*     */ public class CraftHorse extends CraftAnimals implements Horse {
/*     */   public CraftHorse(CraftServer server, EntityHorse entity) {
/*  17 */     super(server, (EntityAnimal)entity);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityHorse getHandle() {
/*  22 */     return (EntityHorse)this.entity;
/*     */   }
/*     */   
/*     */   public Horse.Variant getVariant() {
/*  26 */     return Horse.Variant.values()[getHandle().getType()];
/*     */   }
/*     */   
/*     */   public void setVariant(Horse.Variant variant) {
/*  30 */     Validate.notNull(variant, "Variant cannot be null");
/*  31 */     getHandle().setType(variant.ordinal());
/*     */   }
/*     */   
/*     */   public Horse.Color getColor() {
/*  35 */     return Horse.Color.values()[getHandle().getVariant() & 0xFF];
/*     */   }
/*     */   
/*     */   public void setColor(Horse.Color color) {
/*  39 */     Validate.notNull(color, "Color cannot be null");
/*  40 */     getHandle().setVariant(color.ordinal() & 0xFF | getStyle().ordinal() << 8);
/*     */   }
/*     */   
/*     */   public Horse.Style getStyle() {
/*  44 */     return Horse.Style.values()[getHandle().getVariant() >>> 8];
/*     */   }
/*     */   
/*     */   public void setStyle(Horse.Style style) {
/*  48 */     Validate.notNull(style, "Style cannot be null");
/*  49 */     getHandle().setVariant(getColor().ordinal() & 0xFF | style.ordinal() << 8);
/*     */   }
/*     */   
/*     */   public boolean isCarryingChest() {
/*  53 */     return getHandle().hasChest();
/*     */   }
/*     */   
/*     */   public void setCarryingChest(boolean chest) {
/*  57 */     if (chest == isCarryingChest())
/*  58 */       return;  getHandle().setHasChest(chest);
/*  59 */     getHandle().loadChest();
/*     */   }
/*     */   
/*     */   public int getDomestication() {
/*  63 */     return getHandle().getTemper();
/*     */   }
/*     */   
/*     */   public void setDomestication(int value) {
/*  67 */     Validate.isTrue((value >= 0), "Domestication cannot be less than zero");
/*  68 */     Validate.isTrue((value <= getMaxDomestication()), "Domestication cannot be greater than the max domestication");
/*  69 */     getHandle().setTemper(value);
/*     */   }
/*     */   
/*     */   public int getMaxDomestication() {
/*  73 */     return getHandle().getMaxDomestication();
/*     */   }
/*     */   
/*     */   public void setMaxDomestication(int value) {
/*  77 */     Validate.isTrue((value > 0), "Max domestication cannot be zero or less");
/*  78 */     (getHandle()).maxDomestication = value;
/*     */   }
/*     */   
/*     */   public double getJumpStrength() {
/*  82 */     return getHandle().getJumpStrength();
/*     */   }
/*     */   
/*     */   public void setJumpStrength(double strength) {
/*  86 */     Validate.isTrue((strength >= 0.0D), "Jump strength cannot be less than zero");
/*  87 */     getHandle().getAttributeInstance(EntityHorse.attributeJumpStrength).setValue(strength);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTamed() {
/*  92 */     return getHandle().isTame();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTamed(boolean tamed) {
/*  97 */     getHandle().setTame(tamed);
/*     */   }
/*     */ 
/*     */   
/*     */   public AnimalTamer getOwner() {
/* 102 */     if (getOwnerUUID() == null) return null; 
/* 103 */     return (AnimalTamer)getServer().getOfflinePlayer(getOwnerUUID());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOwner(AnimalTamer owner) {
/* 108 */     if (owner != null) {
/* 109 */       setTamed(true);
/* 110 */       getHandle().setPathEntity(null);
/* 111 */       setOwnerUUID(owner.getUniqueId());
/*     */     } else {
/* 113 */       setTamed(false);
/* 114 */       setOwnerUUID((UUID)null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public UUID getOwnerUUID() {
/*     */     try {
/* 120 */       return UUID.fromString(getHandle().getOwnerUUID());
/* 121 */     } catch (IllegalArgumentException ex) {
/* 122 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setOwnerUUID(UUID uuid) {
/* 127 */     if (uuid == null) {
/* 128 */       getHandle().setOwnerUUID("");
/*     */     } else {
/* 130 */       getHandle().setOwnerUUID(uuid.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   public HorseInventory getInventory() {
/* 135 */     return (HorseInventory)new CraftInventoryHorse((IInventory)(getHandle()).inventoryChest);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 140 */     return "CraftHorse{variant=" + getVariant() + ", owner=" + getOwner() + '}';
/*     */   }
/*     */   
/*     */   public EntityType getType() {
/* 144 */     return EntityType.HORSE;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftHorse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */