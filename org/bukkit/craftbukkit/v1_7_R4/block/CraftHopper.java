/*    */ package org.bukkit.craftbukkit.v1_7_R4.block;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.IInventory;
/*    */ import net.minecraft.server.v1_7_R4.TileEntityHopper;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.Hopper;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CraftHopper
/*    */   extends CraftBlockState implements Hopper {
/*    */   public CraftHopper(Block block) {
/* 14 */     super(block);
/*    */     
/* 16 */     this.hopper = (TileEntityHopper)((CraftWorld)block.getWorld()).getTileEntityAt(getX(), getY(), getZ());
/*    */   }
/*    */   private final TileEntityHopper hopper;
/*    */   public Inventory getInventory() {
/* 20 */     return (Inventory)new CraftInventory((IInventory)this.hopper);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics) {
/* 25 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 27 */     if (result) {
/* 28 */       this.hopper.update();
/*    */     }
/*    */     
/* 31 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\block\CraftHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */