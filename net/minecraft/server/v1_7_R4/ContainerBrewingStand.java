/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryBrewer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ 
/*     */ public class ContainerBrewingStand
/*     */   extends Container {
/*     */   private TileEntityBrewingStand brewingStand;
/*     */   private final Slot f;
/*     */   private int g;
/*  14 */   private CraftInventoryView bukkitEntity = null;
/*     */   
/*     */   private PlayerInventory player;
/*     */   
/*     */   public ContainerBrewingStand(PlayerInventory playerinventory, TileEntityBrewingStand tileentitybrewingstand) {
/*  19 */     this.player = playerinventory;
/*  20 */     this.brewingStand = tileentitybrewingstand;
/*  21 */     a(new SlotPotionBottle(playerinventory.player, tileentitybrewingstand, 0, 56, 46));
/*  22 */     a(new SlotPotionBottle(playerinventory.player, tileentitybrewingstand, 1, 79, 53));
/*  23 */     a(new SlotPotionBottle(playerinventory.player, tileentitybrewingstand, 2, 102, 46));
/*  24 */     this.f = a(new SlotBrewing(this, tileentitybrewingstand, 3, 79, 17));
/*     */     
/*     */     int i;
/*     */     
/*  28 */     for (i = 0; i < 3; i++) {
/*  29 */       for (int j = 0; j < 9; j++) {
/*  30 */         a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  34 */     for (i = 0; i < 9; i++) {
/*  35 */       a(new Slot(playerinventory, i, 8 + i * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */   public void addSlotListener(ICrafting icrafting) {
/*  40 */     super.addSlotListener(icrafting);
/*  41 */     icrafting.setContainerData(this, 0, this.brewingStand.i());
/*     */   }
/*     */   
/*     */   public void b() {
/*  45 */     super.b();
/*     */     
/*  47 */     for (int i = 0; i < this.listeners.size(); i++) {
/*  48 */       ICrafting icrafting = this.listeners.get(i);
/*     */       
/*  50 */       if (this.g != this.brewingStand.i()) {
/*  51 */         icrafting.setContainerData(this, 0, this.brewingStand.i());
/*     */       }
/*     */     } 
/*     */     
/*  55 */     this.g = this.brewingStand.i();
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  59 */     if (!this.checkReachable) return true; 
/*  60 */     return this.brewingStand.a(entityhuman);
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/*  64 */     ItemStack itemstack = null;
/*  65 */     Slot slot = this.c.get(i);
/*     */     
/*  67 */     if (slot != null && slot.hasItem()) {
/*  68 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/*  70 */       itemstack = itemstack1.cloneItemStack();
/*  71 */       if ((i < 0 || i > 2) && i != 3) {
/*  72 */         if (!this.f.hasItem() && this.f.isAllowed(itemstack1)) {
/*  73 */           if (!a(itemstack1, 3, 4, false)) {
/*  74 */             return null;
/*     */           }
/*  76 */         } else if (SlotPotionBottle.b_(itemstack)) {
/*  77 */           if (!a(itemstack1, 0, 3, false)) {
/*  78 */             return null;
/*     */           }
/*  80 */         } else if (i >= 4 && i < 31) {
/*  81 */           if (!a(itemstack1, 31, 40, false)) {
/*  82 */             return null;
/*     */           }
/*  84 */         } else if (i >= 31 && i < 40) {
/*  85 */           if (!a(itemstack1, 4, 31, false)) {
/*  86 */             return null;
/*     */           }
/*  88 */         } else if (!a(itemstack1, 4, 40, false)) {
/*  89 */           return null;
/*     */         } 
/*     */       } else {
/*  92 */         if (!a(itemstack1, 4, 40, true)) {
/*  93 */           return null;
/*     */         }
/*     */         
/*  96 */         slot.a(itemstack1, itemstack);
/*     */       } 
/*     */       
/*  99 */       if (itemstack1.count == 0) {
/* 100 */         slot.set((ItemStack)null);
/*     */       } else {
/* 102 */         slot.f();
/*     */       } 
/*     */       
/* 105 */       if (itemstack1.count == itemstack.count) {
/* 106 */         return null;
/*     */       }
/*     */       
/* 109 */       slot.a(entityhuman, itemstack1);
/*     */     } 
/*     */     
/* 112 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftInventoryView getBukkitView() {
/* 117 */     if (this.bukkitEntity != null) {
/* 118 */       return this.bukkitEntity;
/*     */     }
/*     */     
/* 121 */     CraftInventoryBrewer inventory = new CraftInventoryBrewer(this.brewingStand);
/* 122 */     this.bukkitEntity = new CraftInventoryView((HumanEntity)this.player.player.getBukkitEntity(), (Inventory)inventory, this);
/* 123 */     return this.bukkitEntity;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ContainerBrewingStand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */