/*     */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*     */ import net.minecraft.server.v1_7_R4.IInventory;
/*     */ import net.minecraft.server.v1_7_R4.ItemStack;
/*     */ import net.minecraft.server.v1_7_R4.PlayerInventory;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class CraftInventoryPlayer extends CraftInventory implements PlayerInventory, EntityEquipment {
/*     */   public CraftInventoryPlayer(PlayerInventory inventory) {
/*  14 */     super((IInventory)inventory);
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerInventory getInventory() {
/*  19 */     return (PlayerInventory)this.inventory;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/*  24 */     return super.getSize() - 4;
/*     */   }
/*     */   
/*     */   public ItemStack getItemInHand() {
/*  28 */     return CraftItemStack.asCraftMirror(getInventory().getItemInHand());
/*     */   }
/*     */   
/*     */   public void setItemInHand(ItemStack stack) {
/*  32 */     setItem(getHeldItemSlot(), stack);
/*     */   }
/*     */   
/*     */   public int getHeldItemSlot() {
/*  36 */     return (getInventory()).itemInHandIndex;
/*     */   }
/*     */   
/*     */   public void setHeldItemSlot(int slot) {
/*  40 */     Validate.isTrue((slot >= 0 && slot < PlayerInventory.getHotbarSize()), "Slot is not between 0 and 8 inclusive");
/*  41 */     (getInventory()).itemInHandIndex = slot;
/*  42 */     (((CraftPlayer)getHolder()).getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutHeldItemSlot(slot));
/*     */   }
/*     */   
/*     */   public ItemStack getHelmet() {
/*  46 */     return getItem(getSize() + 3);
/*     */   }
/*     */   
/*     */   public ItemStack getChestplate() {
/*  50 */     return getItem(getSize() + 2);
/*     */   }
/*     */   
/*     */   public ItemStack getLeggings() {
/*  54 */     return getItem(getSize() + 1);
/*     */   }
/*     */   
/*     */   public ItemStack getBoots() {
/*  58 */     return getItem(getSize() + 0);
/*     */   }
/*     */   
/*     */   public void setHelmet(ItemStack helmet) {
/*  62 */     setItem(getSize() + 3, helmet);
/*     */   }
/*     */   
/*     */   public void setChestplate(ItemStack chestplate) {
/*  66 */     setItem(getSize() + 2, chestplate);
/*     */   }
/*     */   
/*     */   public void setLeggings(ItemStack leggings) {
/*  70 */     setItem(getSize() + 1, leggings);
/*     */   }
/*     */   
/*     */   public void setBoots(ItemStack boots) {
/*  74 */     setItem(getSize() + 0, boots);
/*     */   }
/*     */   
/*     */   public ItemStack[] getArmorContents() {
/*  78 */     ItemStack[] mcItems = getInventory().getArmorContents();
/*  79 */     ItemStack[] ret = new ItemStack[mcItems.length];
/*     */     
/*  81 */     for (int i = 0; i < mcItems.length; i++) {
/*  82 */       ret[i] = CraftItemStack.asCraftMirror(mcItems[i]);
/*     */     }
/*  84 */     return ret;
/*     */   }
/*     */   
/*     */   public void setArmorContents(ItemStack[] items) {
/*  88 */     int cnt = getSize();
/*     */     
/*  90 */     if (items == null) {
/*  91 */       items = new ItemStack[4];
/*     */     }
/*  93 */     for (ItemStack item : items) {
/*  94 */       if (item == null || item.getTypeId() == 0) {
/*  95 */         clear(cnt++);
/*     */       } else {
/*  97 */         setItem(cnt++, item);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int clear(int id, int data) {
/* 103 */     int count = 0;
/* 104 */     ItemStack[] items = getContents();
/* 105 */     ItemStack[] armor = getArmorContents();
/* 106 */     int armorSlot = getSize();
/*     */     
/* 108 */     for (int i = 0; i < items.length; i++) {
/* 109 */       ItemStack item = items[i];
/* 110 */       if (item != null && (
/* 111 */         id <= -1 || item.getTypeId() == id) && (
/* 112 */         data <= -1 || item.getData().getData() == data)) {
/*     */         
/* 114 */         count += item.getAmount();
/* 115 */         setItem(i, null);
/*     */       } 
/*     */     } 
/* 118 */     for (ItemStack item : armor) {
/* 119 */       if (item != null && (
/* 120 */         id <= -1 || item.getTypeId() == id) && (
/* 121 */         data <= -1 || item.getData().getData() == data)) {
/*     */         
/* 123 */         count += item.getAmount();
/* 124 */         setItem(armorSlot++, null);
/*     */       } 
/* 126 */     }  return count;
/*     */   }
/*     */ 
/*     */   
/*     */   public HumanEntity getHolder() {
/* 131 */     return (HumanEntity)this.inventory.getOwner();
/*     */   }
/*     */   
/*     */   public float getItemInHandDropChance() {
/* 135 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public void setItemInHandDropChance(float chance) {
/* 139 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public float getHelmetDropChance() {
/* 143 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public void setHelmetDropChance(float chance) {
/* 147 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public float getChestplateDropChance() {
/* 151 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public void setChestplateDropChance(float chance) {
/* 155 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public float getLeggingsDropChance() {
/* 159 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public void setLeggingsDropChance(float chance) {
/* 163 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public float getBootsDropChance() {
/* 167 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public void setBootsDropChance(float chance) {
/* 171 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftInventoryPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */