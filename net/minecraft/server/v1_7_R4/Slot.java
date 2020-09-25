/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class Slot
/*    */ {
/*    */   public final int index;
/*    */   public final IInventory inventory;
/*    */   public int rawSlotIndex;
/*    */   public int h;
/*    */   public int i;
/*    */   
/*    */   public Slot(IInventory iinventory, int i, int j, int k) {
/* 12 */     this.inventory = iinventory;
/* 13 */     this.index = i;
/* 14 */     this.h = j;
/* 15 */     this.i = k;
/*    */   }
/*    */   
/*    */   public void a(ItemStack itemstack, ItemStack itemstack1) {
/* 19 */     if (itemstack != null && itemstack1 != null && 
/* 20 */       itemstack.getItem() == itemstack1.getItem()) {
/* 21 */       int i = itemstack1.count - itemstack.count;
/*    */       
/* 23 */       if (i > 0) {
/* 24 */         a(itemstack, i);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void a(ItemStack itemstack, int i) {}
/*    */   
/*    */   protected void b(ItemStack itemstack) {}
/*    */   
/*    */   public void a(EntityHuman entityhuman, ItemStack itemstack) {
/* 35 */     f();
/*    */   }
/*    */   
/*    */   public boolean isAllowed(ItemStack itemstack) {
/* 39 */     return true;
/*    */   }
/*    */   
/*    */   public ItemStack getItem() {
/* 43 */     return this.inventory.getItem(this.index);
/*    */   }
/*    */   
/*    */   public boolean hasItem() {
/* 47 */     return (getItem() != null);
/*    */   }
/*    */   
/*    */   public void set(ItemStack itemstack) {
/* 51 */     this.inventory.setItem(this.index, itemstack);
/* 52 */     f();
/*    */   }
/*    */   
/*    */   public void f() {
/* 56 */     this.inventory.update();
/*    */   }
/*    */   
/*    */   public int getMaxStackSize() {
/* 60 */     return this.inventory.getMaxStackSize();
/*    */   }
/*    */   
/*    */   public ItemStack a(int i) {
/* 64 */     return this.inventory.splitStack(this.index, i);
/*    */   }
/*    */   
/*    */   public boolean a(IInventory iinventory, int i) {
/* 68 */     return (iinventory == this.inventory && i == this.index);
/*    */   }
/*    */   
/*    */   public boolean isAllowed(EntityHuman entityhuman) {
/* 72 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Slot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */