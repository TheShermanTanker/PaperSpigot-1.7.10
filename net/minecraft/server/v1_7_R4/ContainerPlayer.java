/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryCrafting;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ 
/*     */ public class ContainerPlayer extends Container {
/*  10 */   public InventoryCrafting craftInventory = new InventoryCrafting(this, 2, 2);
/*  11 */   public IInventory resultInventory = new InventoryCraftResult();
/*     */   
/*     */   public boolean g;
/*     */   private final EntityHuman h;
/*  15 */   private CraftInventoryView bukkitEntity = null;
/*     */   
/*     */   private PlayerInventory player;
/*     */   
/*     */   public ContainerPlayer(PlayerInventory playerinventory, boolean flag, EntityHuman entityhuman) {
/*  20 */     this.g = flag;
/*  21 */     this.h = entityhuman;
/*  22 */     this.resultInventory = new InventoryCraftResult();
/*  23 */     this.craftInventory = new InventoryCrafting(this, 2, 2, playerinventory.player);
/*  24 */     this.craftInventory.resultInventory = this.resultInventory;
/*  25 */     this.player = playerinventory;
/*  26 */     a(new SlotResult(playerinventory.player, this.craftInventory, this.resultInventory, 0, 144, 36));
/*     */ 
/*     */     
/*     */     int i;
/*     */     
/*  31 */     for (i = 0; i < 2; i++) {
/*  32 */       for (int j = 0; j < 2; j++) {
/*  33 */         a(new Slot(this.craftInventory, j + i * 2, 88 + j * 18, 26 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  37 */     for (i = 0; i < 4; i++) {
/*  38 */       a(new SlotArmor(this, playerinventory, playerinventory.getSize() - 1 - i, 8, 8 + i * 18, i));
/*     */     }
/*     */     
/*  41 */     for (i = 0; i < 3; i++) {
/*  42 */       for (int j = 0; j < 9; j++) {
/*  43 */         a(new Slot(playerinventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  47 */     for (i = 0; i < 9; i++) {
/*  48 */       a(new Slot(playerinventory, i, 8 + i * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(IInventory iinventory) {
/*  56 */     (CraftingManager.getInstance()).lastCraftView = (InventoryView)getBukkitView();
/*  57 */     ItemStack craftResult = CraftingManager.getInstance().craft(this.craftInventory, this.h.world);
/*  58 */     this.resultInventory.setItem(0, craftResult);
/*  59 */     if (this.listeners.size() < 1) {
/*     */       return;
/*     */     }
/*     */     
/*  63 */     EntityPlayer player = this.listeners.get(0);
/*  64 */     player.playerConnection.sendPacket(new PacketPlayOutSetSlot(player.activeContainer.windowId, 0, craftResult));
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/*  69 */     super.b(entityhuman);
/*     */     
/*  71 */     for (int i = 0; i < 4; i++) {
/*  72 */       ItemStack itemstack = this.craftInventory.splitWithoutUpdate(i);
/*     */       
/*  74 */       if (itemstack != null) {
/*  75 */         entityhuman.drop(itemstack, false);
/*     */       }
/*     */     } 
/*     */     
/*  79 */     this.resultInventory.setItem(0, (ItemStack)null);
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  83 */     return true;
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
/*  94 */       if (i == 0) {
/*  95 */         if (!a(itemstack1, 9, 45, true)) {
/*  96 */           return null;
/*     */         }
/*     */         
/*  99 */         slot.a(itemstack1, itemstack);
/* 100 */       } else if (i >= 1 && i < 5) {
/* 101 */         if (!a(itemstack1, 9, 45, false)) {
/* 102 */           return null;
/*     */         }
/* 104 */       } else if (i >= 5 && i < 9) {
/* 105 */         if (!a(itemstack1, 9, 45, false)) {
/* 106 */           return null;
/*     */         }
/* 108 */       } else if (itemstack.getItem() instanceof ItemArmor && !((Slot)this.c.get(5 + ((ItemArmor)itemstack.getItem()).b)).hasItem()) {
/* 109 */         int j = 5 + ((ItemArmor)itemstack.getItem()).b;
/*     */         
/* 111 */         if (!a(itemstack1, j, j + 1, false)) {
/* 112 */           return null;
/*     */         }
/* 114 */       } else if (i >= 9 && i < 36) {
/* 115 */         if (!a(itemstack1, 36, 45, false)) {
/* 116 */           return null;
/*     */         }
/* 118 */       } else if (i >= 36 && i < 45) {
/* 119 */         if (!a(itemstack1, 9, 36, false)) {
/* 120 */           return null;
/*     */         }
/* 122 */       } else if (!a(itemstack1, 9, 45, false)) {
/* 123 */         return null;
/*     */       } 
/*     */       
/* 126 */       if (itemstack1.count == 0) {
/* 127 */         slot.set((ItemStack)null);
/*     */       } else {
/* 129 */         slot.f();
/*     */       } 
/*     */       
/* 132 */       if (itemstack1.count == itemstack.count) {
/* 133 */         return null;
/*     */       }
/*     */       
/* 136 */       slot.a(entityhuman, itemstack1);
/*     */     } 
/*     */     
/* 139 */     return itemstack;
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack itemstack, Slot slot) {
/* 143 */     return (slot.inventory != this.resultInventory && super.a(itemstack, slot));
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftInventoryView getBukkitView() {
/* 148 */     if (this.bukkitEntity != null) {
/* 149 */       return this.bukkitEntity;
/*     */     }
/*     */     
/* 152 */     CraftInventoryCrafting inventory = new CraftInventoryCrafting(this.craftInventory, this.resultInventory);
/* 153 */     this.bukkitEntity = new CraftInventoryView((HumanEntity)this.player.player.getBukkitEntity(), (Inventory)inventory, this);
/* 154 */     return this.bukkitEntity;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ContainerPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */