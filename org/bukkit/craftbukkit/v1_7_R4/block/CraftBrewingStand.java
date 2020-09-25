/*    */ package org.bukkit.craftbukkit.v1_7_R4.block;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.IInventory;
/*    */ import net.minecraft.server.v1_7_R4.TileEntityBrewingStand;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BrewingStand;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryBrewer;
/*    */ import org.bukkit.inventory.BrewerInventory;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CraftBrewingStand extends CraftBlockState implements BrewingStand {
/*    */   public CraftBrewingStand(Block block) {
/* 14 */     super(block);
/*    */     
/* 16 */     this.brewingStand = (TileEntityBrewingStand)((CraftWorld)block.getWorld()).getTileEntityAt(getX(), getY(), getZ());
/*    */   }
/*    */   private final TileEntityBrewingStand brewingStand;
/*    */   public BrewerInventory getInventory() {
/* 20 */     return (BrewerInventory)new CraftInventoryBrewer((IInventory)this.brewingStand);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics) {
/* 25 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 27 */     if (result) {
/* 28 */       this.brewingStand.update();
/*    */     }
/*    */     
/* 31 */     return result;
/*    */   }
/*    */   
/*    */   public int getBrewingTime() {
/* 35 */     return this.brewingStand.brewTime;
/*    */   }
/*    */   
/*    */   public void setBrewingTime(int brewTime) {
/* 39 */     this.brewingStand.brewTime = brewTime;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\block\CraftBrewingStand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */