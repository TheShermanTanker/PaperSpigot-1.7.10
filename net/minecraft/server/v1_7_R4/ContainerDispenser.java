/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
/*    */ import org.bukkit.entity.HumanEntity;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.InventoryView;
/*    */ 
/*    */ public class ContainerDispenser
/*    */   extends Container {
/*    */   public TileEntityDispenser items;
/* 12 */   private CraftInventoryView bukkitEntity = null;
/*    */   
/*    */   private PlayerInventory player;
/*    */   
/*    */   public ContainerDispenser(IInventory iinventory, TileEntityDispenser tileentitydispenser) {
/* 17 */     this.items = tileentitydispenser;
/*    */ 
/*    */     
/* 20 */     this.player = (PlayerInventory)iinventory;
/*    */ 
/*    */     
/*    */     int i;
/*    */ 
/*    */     
/* 26 */     for (i = 0; i < 3; i++) {
/* 27 */       for (int j = 0; j < 3; j++) {
/* 28 */         a(new Slot(tileentitydispenser, j + i * 3, 62 + j * 18, 17 + i * 18));
/*    */       }
/*    */     } 
/*    */     
/* 32 */     for (i = 0; i < 3; i++) {
/* 33 */       for (int j = 0; j < 9; j++) {
/* 34 */         a(new Slot(iinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/*    */     
/* 38 */     for (i = 0; i < 9; i++) {
/* 39 */       a(new Slot(iinventory, i, 8 + i * 18, 142));
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean a(EntityHuman entityhuman) {
/* 44 */     if (!this.checkReachable) return true; 
/* 45 */     return this.items.a(entityhuman);
/*    */   }
/*    */   
/*    */   public ItemStack b(EntityHuman entityhuman, int i) {
/* 49 */     ItemStack itemstack = null;
/* 50 */     Slot slot = this.c.get(i);
/*    */     
/* 52 */     if (slot != null && slot.hasItem()) {
/* 53 */       ItemStack itemstack1 = slot.getItem();
/*    */       
/* 55 */       itemstack = itemstack1.cloneItemStack();
/* 56 */       if (i < 9) {
/* 57 */         if (!a(itemstack1, 9, 45, true)) {
/* 58 */           return null;
/*    */         }
/* 60 */       } else if (!a(itemstack1, 0, 9, false)) {
/* 61 */         return null;
/*    */       } 
/*    */       
/* 64 */       if (itemstack1.count == 0) {
/* 65 */         slot.set((ItemStack)null);
/*    */       } else {
/* 67 */         slot.f();
/*    */       } 
/*    */       
/* 70 */       if (itemstack1.count == itemstack.count) {
/* 71 */         return null;
/*    */       }
/*    */       
/* 74 */       slot.a(entityhuman, itemstack1);
/*    */     } 
/*    */     
/* 77 */     return itemstack;
/*    */   }
/*    */ 
/*    */   
/*    */   public CraftInventoryView getBukkitView() {
/* 82 */     if (this.bukkitEntity != null) {
/* 83 */       return this.bukkitEntity;
/*    */     }
/*    */     
/* 86 */     CraftInventory inventory = new CraftInventory(this.items);
/* 87 */     this.bukkitEntity = new CraftInventoryView((HumanEntity)this.player.player.getBukkitEntity(), (Inventory)inventory, this);
/* 88 */     return this.bukkitEntity;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ContainerDispenser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */