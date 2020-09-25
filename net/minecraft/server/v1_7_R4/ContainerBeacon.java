/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryBeacon;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ 
/*     */ public class ContainerBeacon
/*     */   extends Container {
/*     */   private TileEntityBeacon a;
/*     */   private final SlotBeacon f;
/*  13 */   private CraftInventoryView bukkitEntity = null; private int g; private int h;
/*     */   private int i;
/*     */   private PlayerInventory player;
/*     */   
/*     */   public ContainerBeacon(PlayerInventory playerinventory, TileEntityBeacon tileentitybeacon) {
/*  18 */     this.player = playerinventory;
/*  19 */     this.a = tileentitybeacon;
/*  20 */     a(this.f = new SlotBeacon(this, tileentitybeacon, 0, 136, 110));
/*  21 */     byte b0 = 36;
/*  22 */     short short1 = 137;
/*     */     
/*     */     int i;
/*     */     
/*  26 */     for (i = 0; i < 3; i++) {
/*  27 */       for (int j = 0; j < 9; j++) {
/*  28 */         a(new Slot(playerinventory, j + i * 9 + 9, b0 + j * 18, short1 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  32 */     for (i = 0; i < 9; i++) {
/*  33 */       a(new Slot(playerinventory, i, b0 + i * 18, 58 + short1));
/*     */     }
/*     */     
/*  36 */     this.g = tileentitybeacon.l();
/*  37 */     this.h = tileentitybeacon.j();
/*  38 */     this.i = tileentitybeacon.k();
/*     */   }
/*     */   
/*     */   public void addSlotListener(ICrafting icrafting) {
/*  42 */     super.addSlotListener(icrafting);
/*  43 */     icrafting.setContainerData(this, 0, this.g);
/*  44 */     icrafting.setContainerData(this, 1, this.h);
/*  45 */     icrafting.setContainerData(this, 2, this.i);
/*     */   }
/*     */   
/*     */   public TileEntityBeacon e() {
/*  49 */     return this.a;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  53 */     if (!this.checkReachable) return true; 
/*  54 */     return this.a.a(entityhuman);
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/*  58 */     ItemStack itemstack = null;
/*  59 */     Slot slot = this.c.get(i);
/*     */     
/*  61 */     if (slot != null && slot.hasItem()) {
/*  62 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/*  64 */       itemstack = itemstack1.cloneItemStack();
/*  65 */       if (i == 0) {
/*  66 */         if (!a(itemstack1, 1, 37, true)) {
/*  67 */           return null;
/*     */         }
/*     */         
/*  70 */         slot.a(itemstack1, itemstack);
/*  71 */       } else if (!this.f.hasItem() && this.f.isAllowed(itemstack1) && itemstack1.count == 1) {
/*  72 */         if (!a(itemstack1, 0, 1, false)) {
/*  73 */           return null;
/*     */         }
/*  75 */       } else if (i >= 1 && i < 28) {
/*  76 */         if (!a(itemstack1, 28, 37, false)) {
/*  77 */           return null;
/*     */         }
/*  79 */       } else if (i >= 28 && i < 37) {
/*  80 */         if (!a(itemstack1, 1, 28, false)) {
/*  81 */           return null;
/*     */         }
/*  83 */       } else if (!a(itemstack1, 1, 37, false)) {
/*  84 */         return null;
/*     */       } 
/*     */       
/*  87 */       if (itemstack1.count == 0) {
/*  88 */         slot.set((ItemStack)null);
/*     */       } else {
/*  90 */         slot.f();
/*     */       } 
/*     */       
/*  93 */       if (itemstack1.count == itemstack.count) {
/*  94 */         return null;
/*     */       }
/*     */       
/*  97 */       slot.a(entityhuman, itemstack1);
/*     */     } 
/*     */     
/* 100 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftInventoryView getBukkitView() {
/* 105 */     if (this.bukkitEntity != null) {
/* 106 */       return this.bukkitEntity;
/*     */     }
/*     */     
/* 109 */     CraftInventoryBeacon craftInventoryBeacon = new CraftInventoryBeacon(this.a);
/* 110 */     this.bukkitEntity = new CraftInventoryView((HumanEntity)this.player.player.getBukkitEntity(), (Inventory)craftInventoryBeacon, this);
/* 111 */     return this.bukkitEntity;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ContainerBeacon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */