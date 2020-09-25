/*     */ package org.bukkit.inventory;
/*     */ 
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InventoryView
/*     */ {
/*     */   public static final int OUTSIDE = -999;
/*     */   
/*     */   public abstract Inventory getTopInventory();
/*     */   
/*     */   public abstract Inventory getBottomInventory();
/*     */   
/*     */   public abstract HumanEntity getPlayer();
/*     */   
/*     */   public abstract InventoryType getType();
/*     */   
/*     */   public enum Property
/*     */   {
/*  24 */     BREW_TIME(0, InventoryType.BREWING),
/*     */ 
/*     */ 
/*     */     
/*  28 */     COOK_TIME(0, InventoryType.FURNACE),
/*     */ 
/*     */ 
/*     */     
/*  32 */     BURN_TIME(1, InventoryType.FURNACE),
/*     */ 
/*     */ 
/*     */     
/*  36 */     TICKS_FOR_CURRENT_FUEL(2, InventoryType.FURNACE),
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  41 */     ENCHANT_BUTTON1(0, InventoryType.ENCHANTING),
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  46 */     ENCHANT_BUTTON2(1, InventoryType.ENCHANTING),
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  51 */     ENCHANT_BUTTON3(2, InventoryType.ENCHANTING);
/*     */     int id;
/*     */     
/*     */     Property(int id, InventoryType appliesTo) {
/*  55 */       this.id = id;
/*  56 */       this.style = appliesTo;
/*     */     }
/*     */     InventoryType style;
/*     */     public InventoryType getType() {
/*  60 */       return this.style;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public int getId() {
/*  69 */       return this.id;
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
/*     */   public void setItem(int slot, ItemStack item) {
/* 112 */     if (slot != -999) {
/* 113 */       if (slot < getTopInventory().getSize()) {
/* 114 */         getTopInventory().setItem(convertSlot(slot), item);
/*     */       } else {
/* 116 */         getBottomInventory().setItem(convertSlot(slot), item);
/*     */       } 
/*     */     } else {
/* 119 */       getPlayer().getWorld().dropItemNaturally(getPlayer().getLocation(), item);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItem(int slot) {
/* 130 */     if (slot == -999) {
/* 131 */       return null;
/*     */     }
/* 133 */     if (slot < getTopInventory().getSize()) {
/* 134 */       return getTopInventory().getItem(convertSlot(slot));
/*     */     }
/* 136 */     return getBottomInventory().getItem(convertSlot(slot));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setCursor(ItemStack item) {
/* 147 */     getPlayer().setItemOnCursor(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ItemStack getCursor() {
/* 157 */     return getPlayer().getItemOnCursor();
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
/*     */   public final int convertSlot(int rawSlot) {
/* 173 */     int numInTop = getTopInventory().getSize();
/* 174 */     if (rawSlot < numInTop) {
/* 175 */       return rawSlot;
/*     */     }
/* 177 */     int slot = rawSlot - numInTop;
/* 178 */     if (getPlayer().getGameMode() == GameMode.CREATIVE && getType() == InventoryType.PLAYER) {
/* 179 */       return slot;
/*     */     }
/* 181 */     if (getType() == InventoryType.CRAFTING) {
/* 182 */       if (slot < 4) return 39 - slot; 
/* 183 */       slot -= 4;
/*     */     } 
/* 185 */     if (slot >= 27) { slot -= 27; }
/* 186 */     else { slot += 9; }
/* 187 */      return slot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void close() {
/* 194 */     getPlayer().closeInventory();
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
/*     */   public final int countSlots() {
/* 207 */     return getTopInventory().getSize() + getBottomInventory().getSize();
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
/*     */   public final boolean setProperty(Property prop, int value) {
/* 220 */     return getPlayer().setWindowProperty(prop, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getTitle() {
/* 229 */     return getTopInventory().getTitle();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\InventoryView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */