/*    */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.IInventory;
/*    */ import net.minecraft.server.v1_7_R4.InventoryLargeChest;
/*    */ import org.bukkit.block.DoubleChest;
/*    */ import org.bukkit.inventory.DoubleChestInventory;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.InventoryHolder;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftInventoryDoubleChest extends CraftInventory implements DoubleChestInventory {
/*    */   private final CraftInventory left;
/*    */   
/*    */   public CraftInventoryDoubleChest(CraftInventory left, CraftInventory right) {
/* 15 */     super((IInventory)new InventoryLargeChest("Large chest", left.getInventory(), right.getInventory()));
/* 16 */     this.left = left;
/* 17 */     this.right = right;
/*    */   }
/*    */   private final CraftInventory right;
/*    */   public CraftInventoryDoubleChest(InventoryLargeChest largeChest) {
/* 21 */     super((IInventory)largeChest);
/* 22 */     if (largeChest.left instanceof InventoryLargeChest) {
/* 23 */       this.left = new CraftInventoryDoubleChest((InventoryLargeChest)largeChest.left);
/*    */     } else {
/* 25 */       this.left = new CraftInventory(largeChest.left);
/*    */     } 
/* 27 */     if (largeChest.right instanceof InventoryLargeChest) {
/* 28 */       this.right = new CraftInventoryDoubleChest((InventoryLargeChest)largeChest.right);
/*    */     } else {
/* 30 */       this.right = new CraftInventory(largeChest.right);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Inventory getLeftSide() {
/* 35 */     return this.left;
/*    */   }
/*    */   
/*    */   public Inventory getRightSide() {
/* 39 */     return this.right;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setContents(ItemStack[] items) {
/* 44 */     if ((getInventory().getContents()).length < items.length) {
/* 45 */       throw new IllegalArgumentException("Invalid inventory size; expected " + (getInventory().getContents()).length + " or less");
/*    */     }
/* 47 */     ItemStack[] leftItems = new ItemStack[this.left.getSize()], rightItems = new ItemStack[this.right.getSize()];
/* 48 */     System.arraycopy(items, 0, leftItems, 0, Math.min(this.left.getSize(), items.length));
/* 49 */     this.left.setContents(leftItems);
/* 50 */     if (items.length >= this.left.getSize()) {
/* 51 */       System.arraycopy(items, this.left.getSize(), rightItems, 0, Math.min(this.right.getSize(), items.length - this.left.getSize()));
/* 52 */       this.right.setContents(rightItems);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public DoubleChest getHolder() {
/* 58 */     return new DoubleChest(this);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftInventoryDoubleChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */