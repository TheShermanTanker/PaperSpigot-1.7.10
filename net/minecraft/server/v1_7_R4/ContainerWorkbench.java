/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryCrafting;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ 
/*     */ public class ContainerWorkbench
/*     */   extends Container {
/*     */   public InventoryCrafting craftInventory;
/*     */   public IInventory resultInventory;
/*     */   private World g;
/*     */   private int h;
/*     */   private int i;
/*     */   private int j;
/*  17 */   private CraftInventoryView bukkitEntity = null;
/*     */   
/*     */   private PlayerInventory player;
/*     */ 
/*     */   
/*     */   public ContainerWorkbench(PlayerInventory playerinventory, World world, int i, int j, int k) {
/*  23 */     this.resultInventory = new InventoryCraftResult();
/*  24 */     this.craftInventory = new InventoryCrafting(this, 3, 3, playerinventory.player);
/*  25 */     this.craftInventory.resultInventory = this.resultInventory;
/*  26 */     this.player = playerinventory;
/*     */     
/*  28 */     this.g = world;
/*  29 */     this.h = i;
/*  30 */     this.i = j;
/*  31 */     this.j = k;
/*  32 */     a(new SlotResult(playerinventory.player, this.craftInventory, this.resultInventory, 0, 124, 35));
/*     */ 
/*     */     
/*     */     int l;
/*     */     
/*  37 */     for (l = 0; l < 3; l++) {
/*  38 */       for (int i1 = 0; i1 < 3; i1++) {
/*  39 */         a(new Slot(this.craftInventory, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
/*     */       }
/*     */     } 
/*     */     
/*  43 */     for (l = 0; l < 3; l++) {
/*  44 */       for (int i1 = 0; i1 < 9; i1++) {
/*  45 */         a(new Slot(playerinventory, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
/*     */       }
/*     */     } 
/*     */     
/*  49 */     for (l = 0; l < 9; l++) {
/*  50 */       a(new Slot(playerinventory, l, 8 + l * 18, 142));
/*     */     }
/*     */     
/*  53 */     a(this.craftInventory);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(IInventory iinventory) {
/*  58 */     (CraftingManager.getInstance()).lastCraftView = (InventoryView)getBukkitView();
/*  59 */     ItemStack craftResult = CraftingManager.getInstance().craft(this.craftInventory, this.g);
/*  60 */     this.resultInventory.setItem(0, craftResult);
/*  61 */     if (this.listeners.size() < 1) {
/*     */       return;
/*     */     }
/*     */     
/*  65 */     EntityPlayer player = this.listeners.get(0);
/*  66 */     player.playerConnection.sendPacket(new PacketPlayOutSetSlot(player.activeContainer.windowId, 0, craftResult));
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/*  71 */     super.b(entityhuman);
/*  72 */     if (!this.g.isStatic) {
/*  73 */       for (int i = 0; i < 9; i++) {
/*  74 */         ItemStack itemstack = this.craftInventory.splitWithoutUpdate(i);
/*     */         
/*  76 */         if (itemstack != null) {
/*  77 */           entityhuman.drop(itemstack, false);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  84 */     if (!this.checkReachable) return true; 
/*  85 */     return (this.g.getType(this.h, this.i, this.j) != Blocks.WORKBENCH) ? false : ((entityhuman.e(this.h + 0.5D, this.i + 0.5D, this.j + 0.5D) <= 64.0D));
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/*  89 */     ItemStack itemstack = null;
/*  90 */     Slot slot = this.c.get(i);
/*     */     
/*  92 */     if (slot != null && slot.hasItem()) {
/*  93 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/*  95 */       itemstack = itemstack1.cloneItemStack();
/*  96 */       if (i == 0) {
/*  97 */         if (!a(itemstack1, 10, 46, true)) {
/*  98 */           return null;
/*     */         }
/*     */         
/* 101 */         slot.a(itemstack1, itemstack);
/* 102 */       } else if (i >= 10 && i < 37) {
/* 103 */         if (!a(itemstack1, 37, 46, false)) {
/* 104 */           return null;
/*     */         }
/* 106 */       } else if (i >= 37 && i < 46) {
/* 107 */         if (!a(itemstack1, 10, 37, false)) {
/* 108 */           return null;
/*     */         }
/* 110 */       } else if (!a(itemstack1, 10, 46, false)) {
/* 111 */         return null;
/*     */       } 
/*     */       
/* 114 */       if (itemstack1.count == 0) {
/* 115 */         slot.set((ItemStack)null);
/*     */       } else {
/* 117 */         slot.f();
/*     */       } 
/*     */       
/* 120 */       if (itemstack1.count == itemstack.count) {
/* 121 */         return null;
/*     */       }
/*     */       
/* 124 */       slot.a(entityhuman, itemstack1);
/*     */     } 
/*     */     
/* 127 */     return itemstack;
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack itemstack, Slot slot) {
/* 131 */     return (slot.inventory != this.resultInventory && super.a(itemstack, slot));
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftInventoryView getBukkitView() {
/* 136 */     if (this.bukkitEntity != null) {
/* 137 */       return this.bukkitEntity;
/*     */     }
/*     */     
/* 140 */     CraftInventoryCrafting inventory = new CraftInventoryCrafting(this.craftInventory, this.resultInventory);
/* 141 */     this.bukkitEntity = new CraftInventoryView((HumanEntity)this.player.player.getBukkitEntity(), (Inventory)inventory, this);
/* 142 */     return this.bukkitEntity;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ContainerWorkbench.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */