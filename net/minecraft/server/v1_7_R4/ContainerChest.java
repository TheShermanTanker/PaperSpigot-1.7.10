/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryDoubleChest;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryPlayer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ 
/*     */ public class ContainerChest extends Container {
/*     */   public IInventory container;
/*  13 */   private CraftInventoryView bukkitEntity = null; private int f; private PlayerInventory player;
/*     */   
/*     */   public CraftInventoryView getBukkitView() {
/*     */     CraftInventory inventory;
/*  17 */     if (this.bukkitEntity != null) {
/*  18 */       return this.bukkitEntity;
/*     */     }
/*     */ 
/*     */     
/*  22 */     if (this.container instanceof PlayerInventory) {
/*  23 */       CraftInventoryPlayer craftInventoryPlayer = new CraftInventoryPlayer((PlayerInventory)this.container);
/*  24 */     } else if (this.container instanceof InventoryLargeChest) {
/*  25 */       CraftInventoryDoubleChest craftInventoryDoubleChest = new CraftInventoryDoubleChest((InventoryLargeChest)this.container);
/*     */     } else {
/*  27 */       inventory = new CraftInventory(this.container);
/*     */     } 
/*     */     
/*  30 */     this.bukkitEntity = new CraftInventoryView((HumanEntity)this.player.player.getBukkitEntity(), (Inventory)inventory, this);
/*  31 */     return this.bukkitEntity;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerChest(IInventory iinventory, IInventory iinventory1) {
/*  36 */     this.container = iinventory1;
/*  37 */     this.f = iinventory1.getSize() / 9;
/*  38 */     iinventory1.startOpen();
/*  39 */     int i = (this.f - 4) * 18;
/*     */ 
/*     */     
/*  42 */     this.player = (PlayerInventory)iinventory;
/*     */ 
/*     */     
/*     */     int j;
/*     */ 
/*     */     
/*  48 */     for (j = 0; j < this.f; j++) {
/*  49 */       for (int k = 0; k < 9; k++) {
/*  50 */         a(new Slot(iinventory1, k + j * 9, 8 + k * 18, 18 + j * 18));
/*     */       }
/*     */     } 
/*     */     
/*  54 */     for (j = 0; j < 3; j++) {
/*  55 */       for (int k = 0; k < 9; k++) {
/*  56 */         a(new Slot(iinventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
/*     */       }
/*     */     } 
/*     */     
/*  60 */     for (j = 0; j < 9; j++) {
/*  61 */       a(new Slot(iinventory, j, 8 + j * 18, 161 + i));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  66 */     if (!this.checkReachable) return true; 
/*  67 */     return this.container.a(entityhuman);
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/*  71 */     ItemStack itemstack = null;
/*  72 */     Slot slot = this.c.get(i);
/*     */     
/*  74 */     if (slot != null && slot.hasItem()) {
/*  75 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/*  77 */       itemstack = itemstack1.cloneItemStack();
/*  78 */       if (i < this.f * 9) {
/*  79 */         if (!a(itemstack1, this.f * 9, this.c.size(), true)) {
/*  80 */           return null;
/*     */         }
/*  82 */       } else if (!a(itemstack1, 0, this.f * 9, false)) {
/*  83 */         return null;
/*     */       } 
/*     */       
/*  86 */       if (itemstack1.count == 0) {
/*  87 */         slot.set((ItemStack)null);
/*     */       } else {
/*  89 */         slot.f();
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     return itemstack;
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/*  97 */     super.b(entityhuman);
/*  98 */     this.container.closeContainer();
/*     */   }
/*     */   
/*     */   public IInventory e() {
/* 102 */     return this.container;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ContainerChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */