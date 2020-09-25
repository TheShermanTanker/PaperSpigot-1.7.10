/*    */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*    */ import net.minecraft.server.v1_7_R4.IInventory;
/*    */ import net.minecraft.server.v1_7_R4.TileEntityBeacon;
/*    */ import org.bukkit.inventory.BeaconInventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftInventoryBeacon extends CraftInventory implements BeaconInventory {
/*    */   public CraftInventoryBeacon(TileEntityBeacon beacon) {
/*  9 */     super((IInventory)beacon);
/*    */   }
/*    */   
/*    */   public void setItem(ItemStack item) {
/* 13 */     setItem(0, item);
/*    */   }
/*    */   
/*    */   public ItemStack getItem() {
/* 17 */     return getItem(0);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftInventoryBeacon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */