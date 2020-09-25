/*     */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*     */ 
/*     */ import net.minecraft.server.v1_7_R4.Container;
/*     */ import net.minecraft.server.v1_7_R4.ItemStack;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class CraftInventoryView
/*     */   extends InventoryView
/*     */ {
/*     */   private final Container container;
/*     */   private final CraftHumanEntity player;
/*     */   private final CraftInventory viewing;
/*     */   
/*     */   public CraftInventoryView(HumanEntity player, Inventory viewing, Container container) {
/*  21 */     this.player = (CraftHumanEntity)player;
/*  22 */     this.viewing = (CraftInventory)viewing;
/*  23 */     this.container = container;
/*     */   }
/*     */ 
/*     */   
/*     */   public Inventory getTopInventory() {
/*  28 */     return this.viewing;
/*     */   }
/*     */ 
/*     */   
/*     */   public Inventory getBottomInventory() {
/*  33 */     return (Inventory)this.player.getInventory();
/*     */   }
/*     */ 
/*     */   
/*     */   public HumanEntity getPlayer() {
/*  38 */     return (HumanEntity)this.player;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryType getType() {
/*  43 */     InventoryType type = this.viewing.getType();
/*  44 */     if (type == InventoryType.CRAFTING && this.player.getGameMode() == GameMode.CREATIVE) {
/*  45 */       return InventoryType.CREATIVE;
/*     */     }
/*  47 */     return type;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int slot, ItemStack item) {
/*  52 */     ItemStack stack = CraftItemStack.asNMSCopy(item);
/*  53 */     if (slot != -999) {
/*  54 */       this.container.getSlot(slot).set(stack);
/*     */     } else {
/*  56 */       this.player.getHandle().drop(stack, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(int slot) {
/*  62 */     if (slot == -999) {
/*  63 */       return null;
/*     */     }
/*  65 */     return CraftItemStack.asCraftMirror(this.container.getSlot(slot).getItem());
/*     */   }
/*     */   
/*     */   public boolean isInTop(int rawSlot) {
/*  69 */     return (rawSlot < this.viewing.getSize());
/*     */   }
/*     */   
/*     */   public Container getHandle() {
/*  73 */     return this.container;
/*     */   }
/*     */   
/*     */   public static InventoryType.SlotType getSlotType(InventoryView inventory, int slot) {
/*  77 */     InventoryType.SlotType type = InventoryType.SlotType.CONTAINER;
/*  78 */     if (slot >= 0 && slot < inventory.getTopInventory().getSize()) {
/*  79 */       switch (inventory.getType()) {
/*     */         case FURNACE:
/*  81 */           if (slot == 2) {
/*  82 */             type = InventoryType.SlotType.RESULT; break;
/*  83 */           }  if (slot == 1) {
/*  84 */             type = InventoryType.SlotType.FUEL; break;
/*     */           } 
/*  86 */           type = InventoryType.SlotType.CRAFTING;
/*     */           break;
/*     */         
/*     */         case BREWING:
/*  90 */           if (slot == 3) {
/*  91 */             type = InventoryType.SlotType.FUEL; break;
/*     */           } 
/*  93 */           type = InventoryType.SlotType.CRAFTING;
/*     */           break;
/*     */         
/*     */         case ENCHANTING:
/*  97 */           type = InventoryType.SlotType.CRAFTING;
/*     */           break;
/*     */         case WORKBENCH:
/*     */         case CRAFTING:
/* 101 */           if (slot == 0) {
/* 102 */             type = InventoryType.SlotType.RESULT; break;
/*     */           } 
/* 104 */           type = InventoryType.SlotType.CRAFTING;
/*     */           break;
/*     */         
/*     */         case MERCHANT:
/* 108 */           if (slot == 2) {
/* 109 */             type = InventoryType.SlotType.RESULT; break;
/*     */           } 
/* 111 */           type = InventoryType.SlotType.CRAFTING;
/*     */           break;
/*     */         
/*     */         case BEACON:
/* 115 */           type = InventoryType.SlotType.CRAFTING;
/*     */           break;
/*     */         case ANVIL:
/* 118 */           if (slot == 2) {
/* 119 */             type = InventoryType.SlotType.RESULT; break;
/*     */           } 
/* 121 */           type = InventoryType.SlotType.CRAFTING;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     } else if (slot == -999) {
/* 129 */       type = InventoryType.SlotType.OUTSIDE;
/* 130 */     } else if (inventory.getType() == InventoryType.CRAFTING) {
/* 131 */       if (slot < 9) {
/* 132 */         type = InventoryType.SlotType.ARMOR;
/* 133 */       } else if (slot > 35) {
/* 134 */         type = InventoryType.SlotType.QUICKBAR;
/*     */       } 
/* 136 */     } else if (slot >= inventory.countSlots() - 9) {
/* 137 */       type = InventoryType.SlotType.QUICKBAR;
/*     */     } 
/*     */     
/* 140 */     return type;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftInventoryView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */