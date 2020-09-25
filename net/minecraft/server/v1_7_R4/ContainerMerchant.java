/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryMerchant;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ 
/*     */ public class ContainerMerchant
/*     */   extends Container {
/*     */   private IMerchant merchant;
/*  12 */   private CraftInventoryView bukkitEntity = null; private InventoryMerchant f;
/*     */   private final World g;
/*     */   private PlayerInventory player;
/*     */   
/*     */   public CraftInventoryView getBukkitView() {
/*  17 */     if (this.bukkitEntity == null) {
/*  18 */       this.bukkitEntity = new CraftInventoryView((HumanEntity)this.player.player.getBukkitEntity(), (Inventory)new CraftInventoryMerchant(getMerchantInventory()), this);
/*     */     }
/*  20 */     return this.bukkitEntity;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerMerchant(PlayerInventory playerinventory, IMerchant imerchant, World world) {
/*  26 */     this.merchant = imerchant;
/*  27 */     this.g = world;
/*  28 */     this.f = new InventoryMerchant(playerinventory.player, imerchant);
/*  29 */     a(new Slot(this.f, 0, 36, 53));
/*  30 */     a(new Slot(this.f, 1, 62, 53));
/*  31 */     a(new SlotMerchantResult(playerinventory.player, imerchant, this.f, 2, 120, 53));
/*  32 */     this.player = playerinventory;
/*     */     
/*     */     int i;
/*     */     
/*  36 */     for (i = 0; i < 3; i++) {
/*  37 */       for (int j = 0; j < 9; j++) {
/*  38 */         a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  42 */     for (i = 0; i < 9; i++) {
/*  43 */       a(new Slot(playerinventory, i, 8 + i * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */   public InventoryMerchant getMerchantInventory() {
/*  48 */     return this.f;
/*     */   }
/*     */   
/*     */   public void addSlotListener(ICrafting icrafting) {
/*  52 */     super.addSlotListener(icrafting);
/*     */   }
/*     */   
/*     */   public void b() {
/*  56 */     super.b();
/*     */   }
/*     */   
/*     */   public void a(IInventory iinventory) {
/*  60 */     this.f.h();
/*  61 */     super.a(iinventory);
/*     */   }
/*     */   
/*     */   public void e(int i) {
/*  65 */     this.f.c(i);
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  69 */     return (this.merchant.b() == entityhuman);
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/*  73 */     ItemStack itemstack = null;
/*  74 */     Slot slot = this.c.get(i);
/*     */     
/*  76 */     if (slot != null && slot.hasItem()) {
/*  77 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/*  79 */       itemstack = itemstack1.cloneItemStack();
/*  80 */       if (i == 2) {
/*  81 */         if (!a(itemstack1, 3, 39, true)) {
/*  82 */           return null;
/*     */         }
/*     */         
/*  85 */         slot.a(itemstack1, itemstack);
/*  86 */       } else if (i != 0 && i != 1) {
/*  87 */         if (i >= 3 && i < 30) {
/*  88 */           if (!a(itemstack1, 30, 39, false)) {
/*  89 */             return null;
/*     */           }
/*  91 */         } else if (i >= 30 && i < 39 && !a(itemstack1, 3, 30, false)) {
/*  92 */           return null;
/*     */         } 
/*  94 */       } else if (!a(itemstack1, 3, 39, false)) {
/*  95 */         return null;
/*     */       } 
/*     */       
/*  98 */       if (itemstack1.count == 0) {
/*  99 */         slot.set((ItemStack)null);
/*     */       } else {
/* 101 */         slot.f();
/*     */       } 
/*     */       
/* 104 */       if (itemstack1.count == itemstack.count) {
/* 105 */         return null;
/*     */       }
/*     */       
/* 108 */       slot.a(entityhuman, itemstack1);
/*     */     } 
/*     */     
/* 111 */     return itemstack;
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/* 115 */     super.b(entityhuman);
/* 116 */     this.merchant.a_((EntityHuman)null);
/* 117 */     super.b(entityhuman);
/* 118 */     if (!this.g.isStatic) {
/* 119 */       ItemStack itemstack = this.f.splitWithoutUpdate(0);
/*     */       
/* 121 */       if (itemstack != null) {
/* 122 */         entityhuman.drop(itemstack, false);
/*     */       }
/*     */       
/* 125 */       itemstack = this.f.splitWithoutUpdate(1);
/* 126 */       if (itemstack != null)
/* 127 */         entityhuman.drop(itemstack, false); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ContainerMerchant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */