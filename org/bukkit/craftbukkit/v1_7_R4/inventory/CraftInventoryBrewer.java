/*    */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.IInventory;
/*    */ import org.bukkit.block.BrewingStand;
/*    */ import org.bukkit.inventory.BrewerInventory;
/*    */ import org.bukkit.inventory.InventoryHolder;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftInventoryBrewer extends CraftInventory implements BrewerInventory {
/*    */   public CraftInventoryBrewer(IInventory inventory) {
/* 11 */     super(inventory);
/*    */   }
/*    */   
/*    */   public ItemStack getIngredient() {
/* 15 */     return getItem(3);
/*    */   }
/*    */   
/*    */   public void setIngredient(ItemStack ingredient) {
/* 19 */     setItem(3, ingredient);
/*    */   }
/*    */ 
/*    */   
/*    */   public BrewingStand getHolder() {
/* 24 */     return (BrewingStand)this.inventory.getOwner();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftInventoryBrewer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */