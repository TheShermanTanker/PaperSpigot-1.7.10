/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryFurnace;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ 
/*     */ public class ContainerFurnace
/*     */   extends Container
/*     */ {
/*     */   private TileEntityFurnace furnace;
/*     */   private int f;
/*     */   private int g;
/*     */   private int h;
/*  16 */   private CraftInventoryView bukkitEntity = null;
/*     */   private PlayerInventory player;
/*     */   
/*     */   public CraftInventoryView getBukkitView() {
/*  20 */     if (this.bukkitEntity != null) {
/*  21 */       return this.bukkitEntity;
/*     */     }
/*     */     
/*  24 */     CraftInventoryFurnace inventory = new CraftInventoryFurnace(this.furnace);
/*  25 */     this.bukkitEntity = new CraftInventoryView((HumanEntity)this.player.player.getBukkitEntity(), (Inventory)inventory, this);
/*  26 */     return this.bukkitEntity;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerFurnace(PlayerInventory playerinventory, TileEntityFurnace tileentityfurnace) {
/*  31 */     this.furnace = tileentityfurnace;
/*  32 */     a(new Slot(tileentityfurnace, 0, 56, 17));
/*  33 */     a(new Slot(tileentityfurnace, 1, 56, 53));
/*  34 */     a(new SlotFurnaceResult(playerinventory.player, tileentityfurnace, 2, 116, 35));
/*  35 */     this.player = playerinventory;
/*     */     
/*     */     int i;
/*     */     
/*  39 */     for (i = 0; i < 3; i++) {
/*  40 */       for (int j = 0; j < 9; j++) {
/*  41 */         a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  45 */     for (i = 0; i < 9; i++) {
/*  46 */       a(new Slot(playerinventory, i, 8 + i * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */   public void addSlotListener(ICrafting icrafting) {
/*  51 */     super.addSlotListener(icrafting);
/*  52 */     icrafting.setContainerData(this, 0, this.furnace.cookTime);
/*  53 */     icrafting.setContainerData(this, 1, this.furnace.burnTime);
/*  54 */     icrafting.setContainerData(this, 2, this.furnace.ticksForCurrentFuel);
/*     */   }
/*     */   
/*     */   public void b() {
/*  58 */     super.b();
/*     */     
/*  60 */     for (int i = 0; i < this.listeners.size(); i++) {
/*  61 */       ICrafting icrafting = this.listeners.get(i);
/*     */       
/*  63 */       if (this.f != this.furnace.cookTime) {
/*  64 */         icrafting.setContainerData(this, 0, this.furnace.cookTime);
/*     */       }
/*     */       
/*  67 */       if (this.g != this.furnace.burnTime) {
/*  68 */         icrafting.setContainerData(this, 1, this.furnace.burnTime);
/*     */       }
/*     */       
/*  71 */       if (this.h != this.furnace.ticksForCurrentFuel) {
/*  72 */         icrafting.setContainerData(this, 2, this.furnace.ticksForCurrentFuel);
/*     */       }
/*     */     } 
/*     */     
/*  76 */     this.f = this.furnace.cookTime;
/*  77 */     this.g = this.furnace.burnTime;
/*  78 */     this.h = this.furnace.ticksForCurrentFuel;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  82 */     if (!this.checkReachable) return true; 
/*  83 */     return this.furnace.a(entityhuman);
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/*  87 */     ItemStack itemstack = null;
/*  88 */     Slot slot = this.c.get(i);
/*     */     
/*  90 */     if (slot != null && slot.hasItem()) {
/*  91 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/*  93 */       itemstack = itemstack1.cloneItemStack();
/*  94 */       if (i == 2) {
/*  95 */         if (!a(itemstack1, 3, 39, true)) {
/*  96 */           return null;
/*     */         }
/*     */         
/*  99 */         slot.a(itemstack1, itemstack);
/* 100 */       } else if (i != 1 && i != 0) {
/* 101 */         if (RecipesFurnace.getInstance().getResult(itemstack1) != null) {
/* 102 */           if (!a(itemstack1, 0, 1, false)) {
/* 103 */             return null;
/*     */           }
/* 105 */         } else if (TileEntityFurnace.isFuel(itemstack1)) {
/* 106 */           if (!a(itemstack1, 1, 2, false)) {
/* 107 */             return null;
/*     */           }
/* 109 */         } else if (i >= 3 && i < 30) {
/* 110 */           if (!a(itemstack1, 30, 39, false)) {
/* 111 */             return null;
/*     */           }
/* 113 */         } else if (i >= 30 && i < 39 && !a(itemstack1, 3, 30, false)) {
/* 114 */           return null;
/*     */         } 
/* 116 */       } else if (!a(itemstack1, 3, 39, false)) {
/* 117 */         return null;
/*     */       } 
/*     */       
/* 120 */       if (itemstack1.count == 0) {
/* 121 */         slot.set((ItemStack)null);
/*     */       } else {
/* 123 */         slot.f();
/*     */       } 
/*     */       
/* 126 */       if (itemstack1.count == itemstack.count) {
/* 127 */         return null;
/*     */       }
/*     */       
/* 130 */       slot.a(entityhuman, itemstack1);
/*     */     } 
/*     */     
/* 133 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ContainerFurnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */