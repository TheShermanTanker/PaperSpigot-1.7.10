/*     */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*     */ 
/*     */ import net.minecraft.server.v1_7_R4.EntityInsentient;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.inventory.EntityEquipment;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class CraftEntityEquipment
/*     */   implements EntityEquipment
/*     */ {
/*     */   private static final int WEAPON_SLOT = 0;
/*     */   private static final int HELMET_SLOT = 4;
/*     */   private static final int CHEST_SLOT = 3;
/*     */   private static final int LEG_SLOT = 2;
/*     */   private static final int BOOT_SLOT = 1;
/*     */   private static final int INVENTORY_SLOTS = 5;
/*     */   private final CraftLivingEntity entity;
/*     */   
/*     */   public CraftEntityEquipment(CraftLivingEntity entity) {
/*  21 */     this.entity = entity;
/*     */   }
/*     */   
/*     */   public ItemStack getItemInHand() {
/*  25 */     return getEquipment(0);
/*     */   }
/*     */   
/*     */   public void setItemInHand(ItemStack stack) {
/*  29 */     setEquipment(0, stack);
/*     */   }
/*     */   
/*     */   public ItemStack getHelmet() {
/*  33 */     return getEquipment(4);
/*     */   }
/*     */   
/*     */   public void setHelmet(ItemStack helmet) {
/*  37 */     setEquipment(4, helmet);
/*     */   }
/*     */   
/*     */   public ItemStack getChestplate() {
/*  41 */     return getEquipment(3);
/*     */   }
/*     */   
/*     */   public void setChestplate(ItemStack chestplate) {
/*  45 */     setEquipment(3, chestplate);
/*     */   }
/*     */   
/*     */   public ItemStack getLeggings() {
/*  49 */     return getEquipment(2);
/*     */   }
/*     */   
/*     */   public void setLeggings(ItemStack leggings) {
/*  53 */     setEquipment(2, leggings);
/*     */   }
/*     */   
/*     */   public ItemStack getBoots() {
/*  57 */     return getEquipment(1);
/*     */   }
/*     */   
/*     */   public void setBoots(ItemStack boots) {
/*  61 */     setEquipment(1, boots);
/*     */   }
/*     */   
/*     */   public ItemStack[] getArmorContents() {
/*  65 */     ItemStack[] armor = new ItemStack[4];
/*  66 */     for (int slot = 1; slot < 5; slot++) {
/*  67 */       armor[slot - 1] = getEquipment(slot);
/*     */     }
/*  69 */     return armor;
/*     */   }
/*     */   
/*     */   public void setArmorContents(ItemStack[] items) {
/*  73 */     for (int slot = 1; slot < 5; slot++) {
/*  74 */       ItemStack equipment = (items != null && slot <= items.length) ? items[slot - 1] : null;
/*  75 */       setEquipment(slot, equipment);
/*     */     } 
/*     */   }
/*     */   
/*     */   private ItemStack getEquipment(int slot) {
/*  80 */     return CraftItemStack.asBukkitCopy(this.entity.getHandle().getEquipment(slot));
/*     */   }
/*     */   
/*     */   private void setEquipment(int slot, ItemStack stack) {
/*  84 */     this.entity.getHandle().setEquipment(slot, CraftItemStack.asNMSCopy(stack));
/*     */   }
/*     */   
/*     */   public void clear() {
/*  88 */     for (int i = 0; i < 5; i++) {
/*  89 */       setEquipment(i, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public Entity getHolder() {
/*  94 */     return (Entity)this.entity;
/*     */   }
/*     */   
/*     */   public float getItemInHandDropChance() {
/*  98 */     return getDropChance(0);
/*     */   }
/*     */   
/*     */   public void setItemInHandDropChance(float chance) {
/* 102 */     setDropChance(0, chance);
/*     */   }
/*     */   
/*     */   public float getHelmetDropChance() {
/* 106 */     return getDropChance(4);
/*     */   }
/*     */   
/*     */   public void setHelmetDropChance(float chance) {
/* 110 */     setDropChance(4, chance);
/*     */   }
/*     */   
/*     */   public float getChestplateDropChance() {
/* 114 */     return getDropChance(3);
/*     */   }
/*     */   
/*     */   public void setChestplateDropChance(float chance) {
/* 118 */     setDropChance(3, chance);
/*     */   }
/*     */   
/*     */   public float getLeggingsDropChance() {
/* 122 */     return getDropChance(2);
/*     */   }
/*     */   
/*     */   public void setLeggingsDropChance(float chance) {
/* 126 */     setDropChance(2, chance);
/*     */   }
/*     */   
/*     */   public float getBootsDropChance() {
/* 130 */     return getDropChance(1);
/*     */   }
/*     */   
/*     */   public void setBootsDropChance(float chance) {
/* 134 */     setDropChance(1, chance);
/*     */   }
/*     */   
/*     */   private void setDropChance(int slot, float chance) {
/* 138 */     ((EntityInsentient)this.entity.getHandle()).dropChances[slot] = chance - 0.1F;
/*     */   }
/*     */   
/*     */   private float getDropChance(int slot) {
/* 142 */     return ((EntityInsentient)this.entity.getHandle()).dropChances[slot] + 0.1F;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftEntityEquipment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */