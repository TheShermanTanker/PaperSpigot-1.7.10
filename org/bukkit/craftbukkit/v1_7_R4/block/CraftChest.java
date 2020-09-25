/*    */ package org.bukkit.craftbukkit.v1_7_R4.block;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.IInventory;
/*    */ import net.minecraft.server.v1_7_R4.TileEntityChest;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.Chest;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryDoubleChest;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CraftChest extends CraftBlockState implements Chest {
/*    */   private final CraftWorld world;
/*    */   private final TileEntityChest chest;
/*    */   
/*    */   public CraftChest(Block block) {
/* 18 */     super(block);
/*    */     
/* 20 */     this.world = (CraftWorld)block.getWorld();
/* 21 */     this.chest = (TileEntityChest)this.world.getTileEntityAt(getX(), getY(), getZ());
/*    */   }
/*    */   
/*    */   public Inventory getBlockInventory() {
/* 25 */     return (Inventory)new CraftInventory((IInventory)this.chest);
/*    */   }
/*    */   public Inventory getInventory() {
/*    */     CraftInventoryDoubleChest craftInventoryDoubleChest;
/* 29 */     int id, x = getX();
/* 30 */     int y = getY();
/* 31 */     int z = getZ();
/*    */     
/* 33 */     CraftInventory inventory = new CraftInventory((IInventory)this.chest);
/*    */     
/* 35 */     if (this.world.getBlockTypeIdAt(x, y, z) == Material.CHEST.getId()) {
/* 36 */       id = Material.CHEST.getId();
/* 37 */     } else if (this.world.getBlockTypeIdAt(x, y, z) == Material.TRAPPED_CHEST.getId()) {
/* 38 */       id = Material.TRAPPED_CHEST.getId();
/*    */     } else {
/* 40 */       throw new IllegalStateException("CraftChest is not a chest but is instead " + this.world.getBlockAt(x, y, z));
/*    */     } 
/*    */     
/* 43 */     if (this.world.getBlockTypeIdAt(x - 1, y, z) == id) {
/* 44 */       CraftInventory left = new CraftInventory((IInventory)this.world.getHandle().getTileEntity(x - 1, y, z));
/* 45 */       craftInventoryDoubleChest = new CraftInventoryDoubleChest(left, inventory);
/*    */     } 
/* 47 */     if (this.world.getBlockTypeIdAt(x + 1, y, z) == id) {
/* 48 */       CraftInventory right = new CraftInventory((IInventory)this.world.getHandle().getTileEntity(x + 1, y, z));
/* 49 */       craftInventoryDoubleChest = new CraftInventoryDoubleChest((CraftInventory)craftInventoryDoubleChest, right);
/*    */     } 
/* 51 */     if (this.world.getBlockTypeIdAt(x, y, z - 1) == id) {
/* 52 */       CraftInventory left = new CraftInventory((IInventory)this.world.getHandle().getTileEntity(x, y, z - 1));
/* 53 */       craftInventoryDoubleChest = new CraftInventoryDoubleChest(left, (CraftInventory)craftInventoryDoubleChest);
/*    */     } 
/* 55 */     if (this.world.getBlockTypeIdAt(x, y, z + 1) == id) {
/* 56 */       CraftInventory right = new CraftInventory((IInventory)this.world.getHandle().getTileEntity(x, y, z + 1));
/* 57 */       craftInventoryDoubleChest = new CraftInventoryDoubleChest((CraftInventory)craftInventoryDoubleChest, right);
/*    */     } 
/* 59 */     return (Inventory)craftInventoryDoubleChest;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics) {
/* 64 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 66 */     if (result) {
/* 67 */       this.chest.update();
/*    */     }
/*    */     
/* 70 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\block\CraftChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */