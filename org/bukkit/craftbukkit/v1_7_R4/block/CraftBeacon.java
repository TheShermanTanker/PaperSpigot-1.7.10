/*    */ package org.bukkit.craftbukkit.v1_7_R4.block;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.TileEntityBeacon;
/*    */ import org.bukkit.block.Beacon;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryBeacon;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CraftBeacon
/*    */   extends CraftBlockState implements Beacon {
/*    */   private final CraftWorld world;
/*    */   private final TileEntityBeacon beacon;
/*    */   
/*    */   public CraftBeacon(Block block) {
/* 16 */     super(block);
/*    */     
/* 18 */     this.world = (CraftWorld)block.getWorld();
/* 19 */     this.beacon = (TileEntityBeacon)this.world.getTileEntityAt(getX(), getY(), getZ());
/*    */   }
/*    */   
/*    */   public Inventory getInventory() {
/* 23 */     return (Inventory)new CraftInventoryBeacon(this.beacon);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics) {
/* 28 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 30 */     if (result) {
/* 31 */       this.beacon.update();
/*    */     }
/*    */     
/* 34 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\block\CraftBeacon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */