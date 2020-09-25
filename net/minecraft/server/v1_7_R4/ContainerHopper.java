/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
/*    */ import org.bukkit.entity.HumanEntity;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.InventoryView;
/*    */ 
/*    */ public class ContainerHopper
/*    */   extends Container
/*    */ {
/*    */   private final IInventory hopper;
/* 13 */   private CraftInventoryView bukkitEntity = null;
/*    */   private PlayerInventory player;
/*    */   
/*    */   public CraftInventoryView getBukkitView() {
/* 17 */     if (this.bukkitEntity != null) {
/* 18 */       return this.bukkitEntity;
/*    */     }
/*    */     
/* 21 */     CraftInventory inventory = new CraftInventory(this.hopper);
/* 22 */     this.bukkitEntity = new CraftInventoryView((HumanEntity)this.player.player.getBukkitEntity(), (Inventory)inventory, this);
/* 23 */     return this.bukkitEntity;
/*    */   }
/*    */ 
/*    */   
/*    */   public ContainerHopper(PlayerInventory playerinventory, IInventory iinventory) {
/* 28 */     this.hopper = iinventory;
/* 29 */     this.player = playerinventory;
/* 30 */     iinventory.startOpen();
/* 31 */     byte b0 = 51;
/*    */     
/*    */     int i;
/*    */     
/* 35 */     for (i = 0; i < iinventory.getSize(); i++) {
/* 36 */       a(new Slot(iinventory, i, 44 + i * 18, 20));
/*    */     }
/*    */     
/* 39 */     for (i = 0; i < 3; i++) {
/* 40 */       for (int j = 0; j < 9; j++) {
/* 41 */         a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, i * 18 + b0));
/*    */       }
/*    */     } 
/*    */     
/* 45 */     for (i = 0; i < 9; i++) {
/* 46 */       a(new Slot(playerinventory, i, 8 + i * 18, 58 + b0));
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean a(EntityHuman entityhuman) {
/* 51 */     if (!this.checkReachable) return true; 
/* 52 */     return this.hopper.a(entityhuman);
/*    */   }
/*    */   
/*    */   public ItemStack b(EntityHuman entityhuman, int i) {
/* 56 */     ItemStack itemstack = null;
/* 57 */     Slot slot = this.c.get(i);
/*    */     
/* 59 */     if (slot != null && slot.hasItem()) {
/* 60 */       ItemStack itemstack1 = slot.getItem();
/*    */       
/* 62 */       itemstack = itemstack1.cloneItemStack();
/* 63 */       if (i < this.hopper.getSize()) {
/* 64 */         if (!a(itemstack1, this.hopper.getSize(), this.c.size(), true)) {
/* 65 */           return null;
/*    */         }
/* 67 */       } else if (!a(itemstack1, 0, this.hopper.getSize(), false)) {
/* 68 */         return null;
/*    */       } 
/*    */       
/* 71 */       if (itemstack1.count == 0) {
/* 72 */         slot.set((ItemStack)null);
/*    */       } else {
/* 74 */         slot.f();
/*    */       } 
/*    */     } 
/*    */     
/* 78 */     return itemstack;
/*    */   }
/*    */   
/*    */   public void b(EntityHuman entityhuman) {
/* 82 */     super.b(entityhuman);
/* 83 */     this.hopper.closeContainer();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ContainerHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */