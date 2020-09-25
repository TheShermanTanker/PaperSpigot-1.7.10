/*    */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*    */ 
/*    */ import java.util.ListIterator;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class InventoryIterator
/*    */   implements ListIterator<ItemStack> {
/*    */   private final Inventory inventory;
/*    */   private int nextIndex;
/*    */   private Boolean lastDirection;
/*    */   
/*    */   InventoryIterator(Inventory craftInventory) {
/* 14 */     this.inventory = craftInventory;
/* 15 */     this.nextIndex = 0;
/*    */   }
/*    */   
/*    */   InventoryIterator(Inventory craftInventory, int index) {
/* 19 */     this.inventory = craftInventory;
/* 20 */     this.nextIndex = index;
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 24 */     return (this.nextIndex < this.inventory.getSize());
/*    */   }
/*    */   
/*    */   public ItemStack next() {
/* 28 */     this.lastDirection = Boolean.valueOf(true);
/* 29 */     return this.inventory.getItem(this.nextIndex++);
/*    */   }
/*    */   
/*    */   public int nextIndex() {
/* 33 */     return this.nextIndex;
/*    */   }
/*    */   
/*    */   public boolean hasPrevious() {
/* 37 */     return (this.nextIndex > 0);
/*    */   }
/*    */   
/*    */   public ItemStack previous() {
/* 41 */     this.lastDirection = Boolean.valueOf(false);
/* 42 */     return this.inventory.getItem(--this.nextIndex);
/*    */   }
/*    */   
/*    */   public int previousIndex() {
/* 46 */     return this.nextIndex - 1;
/*    */   }
/*    */   
/*    */   public void set(ItemStack item) {
/* 50 */     if (this.lastDirection == null) {
/* 51 */       throw new IllegalStateException("No current item!");
/*    */     }
/* 53 */     int i = this.lastDirection.booleanValue() ? (this.nextIndex - 1) : this.nextIndex;
/* 54 */     this.inventory.setItem(i, item);
/*    */   }
/*    */   
/*    */   public void add(ItemStack item) {
/* 58 */     throw new UnsupportedOperationException("Can't change the size of an inventory!");
/*    */   }
/*    */   
/*    */   public void remove() {
/* 62 */     throw new UnsupportedOperationException("Can't change the size of an inventory!");
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\InventoryIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */