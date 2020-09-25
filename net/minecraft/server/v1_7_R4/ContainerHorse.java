/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryHorse;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerHorse
/*     */   extends Container
/*     */ {
/*     */   private IInventory a;
/*     */   private EntityHorse f;
/*     */   CraftInventoryView bukkitEntity;
/*     */   PlayerInventory player;
/*     */   
/*     */   public InventoryView getBukkitView() {
/*  20 */     if (this.bukkitEntity != null) {
/*  21 */       return (InventoryView)this.bukkitEntity;
/*     */     }
/*     */     
/*  24 */     CraftInventoryHorse craftInventoryHorse = new CraftInventoryHorse(this.a);
/*  25 */     return (InventoryView)(this.bukkitEntity = new CraftInventoryView((HumanEntity)this.player.player.getBukkitEntity(), (Inventory)craftInventoryHorse, this));
/*     */   }
/*     */   
/*     */   public ContainerHorse(IInventory iinventory, IInventory iinventory1, EntityHorse entityhorse) {
/*  29 */     this.player = (PlayerInventory)iinventory;
/*     */     
/*  31 */     this.a = iinventory1;
/*  32 */     this.f = entityhorse;
/*  33 */     byte b0 = 3;
/*     */     
/*  35 */     iinventory1.startOpen();
/*  36 */     int i = (b0 - 4) * 18;
/*     */     
/*  38 */     a(new SlotHorseSaddle(this, iinventory1, 0, 8, 18));
/*  39 */     a(new SlotHorseArmor(this, iinventory1, 1, 8, 36, entityhorse));
/*     */ 
/*     */ 
/*     */     
/*  43 */     if (entityhorse.hasChest()) {
/*  44 */       for (int k = 0; k < b0; k++) {
/*  45 */         for (int m = 0; m < 5; m++) {
/*  46 */           a(new Slot(iinventory1, 2 + m + k * 5, 80 + m * 18, 18 + k * 18));
/*     */         }
/*     */       } 
/*     */     }
/*     */     int j;
/*  51 */     for (j = 0; j < 3; j++) {
/*  52 */       for (int k = 0; k < 9; k++) {
/*  53 */         a(new Slot(iinventory, k + j * 9 + 9, 8 + k * 18, 102 + j * 18 + i));
/*     */       }
/*     */     } 
/*     */     
/*  57 */     for (j = 0; j < 9; j++) {
/*  58 */       a(new Slot(iinventory, j, 8 + j * 18, 160 + i));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  63 */     return (this.a.a(entityhuman) && this.f.isAlive() && this.f.e(entityhuman) < 8.0F);
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/*  67 */     ItemStack itemstack = null;
/*  68 */     Slot slot = this.c.get(i);
/*     */     
/*  70 */     if (slot != null && slot.hasItem()) {
/*  71 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/*  73 */       itemstack = itemstack1.cloneItemStack();
/*  74 */       if (i < this.a.getSize()) {
/*  75 */         if (!a(itemstack1, this.a.getSize(), this.c.size(), true)) {
/*  76 */           return null;
/*     */         }
/*  78 */       } else if (getSlot(1).isAllowed(itemstack1) && !getSlot(1).hasItem()) {
/*  79 */         if (!a(itemstack1, 1, 2, false)) {
/*  80 */           return null;
/*     */         }
/*  82 */       } else if (getSlot(0).isAllowed(itemstack1)) {
/*  83 */         if (!a(itemstack1, 0, 1, false)) {
/*  84 */           return null;
/*     */         }
/*  86 */       } else if (this.a.getSize() <= 2 || !a(itemstack1, 2, this.a.getSize(), false)) {
/*  87 */         return null;
/*     */       } 
/*     */       
/*  90 */       if (itemstack1.count == 0) {
/*  91 */         slot.set((ItemStack)null);
/*     */       } else {
/*  93 */         slot.f();
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     return itemstack;
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/* 101 */     super.b(entityhuman);
/* 102 */     this.a.closeContainer();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ContainerHorse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */