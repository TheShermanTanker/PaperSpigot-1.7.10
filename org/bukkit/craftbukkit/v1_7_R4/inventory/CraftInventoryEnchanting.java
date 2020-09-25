/*    */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.ContainerEnchantTableInventory;
/*    */ import net.minecraft.server.v1_7_R4.IInventory;
/*    */ import org.bukkit.inventory.EnchantingInventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftInventoryEnchanting extends CraftInventory implements EnchantingInventory {
/*    */   public CraftInventoryEnchanting(ContainerEnchantTableInventory inventory) {
/* 10 */     super((IInventory)inventory);
/*    */   }
/*    */   
/*    */   public void setItem(ItemStack item) {
/* 14 */     setItem(0, item);
/*    */   }
/*    */   
/*    */   public ItemStack getItem() {
/* 18 */     return getItem(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ContainerEnchantTableInventory getInventory() {
/* 23 */     return (ContainerEnchantTableInventory)this.inventory;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftInventoryEnchanting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */