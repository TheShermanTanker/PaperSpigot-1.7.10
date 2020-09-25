/*    */ package org.bukkit.block;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.inventory.DoubleChestInventory;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.InventoryHolder;
/*    */ 
/*    */ 
/*    */ public class DoubleChest
/*    */   implements InventoryHolder
/*    */ {
/*    */   private DoubleChestInventory inventory;
/*    */   
/*    */   public DoubleChest(DoubleChestInventory chest) {
/* 16 */     this.inventory = chest;
/*    */   }
/*    */   
/*    */   public Inventory getInventory() {
/* 20 */     return (Inventory)this.inventory;
/*    */   }
/*    */   
/*    */   public InventoryHolder getLeftSide() {
/* 24 */     return this.inventory.getLeftSide().getHolder();
/*    */   }
/*    */   
/*    */   public InventoryHolder getRightSide() {
/* 28 */     return this.inventory.getRightSide().getHolder();
/*    */   }
/*    */   
/*    */   public Location getLocation() {
/* 32 */     return new Location(getWorld(), getX(), getY(), getZ());
/*    */   }
/*    */   
/*    */   public World getWorld() {
/* 36 */     return ((Chest)getLeftSide()).getWorld();
/*    */   }
/*    */   
/*    */   public double getX() {
/* 40 */     return 0.5D * (((Chest)getLeftSide()).getX() + ((Chest)getRightSide()).getX());
/*    */   }
/*    */   
/*    */   public double getY() {
/* 44 */     return 0.5D * (((Chest)getLeftSide()).getY() + ((Chest)getRightSide()).getY());
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 48 */     return 0.5D * (((Chest)getLeftSide()).getZ() + ((Chest)getRightSide()).getZ());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\DoubleChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */