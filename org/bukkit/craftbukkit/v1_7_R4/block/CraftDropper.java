/*    */ package org.bukkit.craftbukkit.v1_7_R4.block;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.BlockDropper;
/*    */ import net.minecraft.server.v1_7_R4.Blocks;
/*    */ import net.minecraft.server.v1_7_R4.IInventory;
/*    */ import net.minecraft.server.v1_7_R4.TileEntityDropper;
/*    */ import net.minecraft.server.v1_7_R4.World;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.Dropper;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CraftDropper extends CraftBlockState implements Dropper {
/*    */   private final CraftWorld world;
/*    */   
/*    */   public CraftDropper(Block block) {
/* 19 */     super(block);
/*    */     
/* 21 */     this.world = (CraftWorld)block.getWorld();
/* 22 */     this.dropper = (TileEntityDropper)this.world.getTileEntityAt(getX(), getY(), getZ());
/*    */   }
/*    */   private final TileEntityDropper dropper;
/*    */   public Inventory getInventory() {
/* 26 */     return (Inventory)new CraftInventory((IInventory)this.dropper);
/*    */   }
/*    */   
/*    */   public void drop() {
/* 30 */     Block block = getBlock();
/*    */     
/* 32 */     if (block.getType() == Material.DROPPER) {
/* 33 */       BlockDropper drop = (BlockDropper)Blocks.DROPPER;
/*    */       
/* 35 */       drop.dispense((World)this.world.getHandle(), getX(), getY(), getZ());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics) {
/* 41 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 43 */     if (result) {
/* 44 */       this.dropper.update();
/*    */     }
/*    */     
/* 47 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\block\CraftDropper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */