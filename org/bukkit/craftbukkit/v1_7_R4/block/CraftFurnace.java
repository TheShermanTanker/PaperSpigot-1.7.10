/*    */ package org.bukkit.craftbukkit.v1_7_R4.block;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.TileEntityFurnace;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.Furnace;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryFurnace;
/*    */ import org.bukkit.inventory.FurnaceInventory;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CraftFurnace
/*    */   extends CraftBlockState implements Furnace {
/*    */   public CraftFurnace(Block block) {
/* 14 */     super(block);
/*    */     
/* 16 */     this.furnace = (TileEntityFurnace)((CraftWorld)block.getWorld()).getTileEntityAt(getX(), getY(), getZ());
/*    */   }
/*    */   private final TileEntityFurnace furnace;
/*    */   public FurnaceInventory getInventory() {
/* 20 */     return (FurnaceInventory)new CraftInventoryFurnace(this.furnace);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics) {
/* 25 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 27 */     if (result) {
/* 28 */       this.furnace.update();
/*    */     }
/*    */     
/* 31 */     return result;
/*    */   }
/*    */   
/*    */   public short getBurnTime() {
/* 35 */     return (short)this.furnace.burnTime;
/*    */   }
/*    */   
/*    */   public void setBurnTime(short burnTime) {
/* 39 */     this.furnace.burnTime = burnTime;
/*    */   }
/*    */   
/*    */   public short getCookTime() {
/* 43 */     return (short)this.furnace.cookTime;
/*    */   }
/*    */   
/*    */   public void setCookTime(short cookTime) {
/* 47 */     this.furnace.cookTime = cookTime;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\block\CraftFurnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */