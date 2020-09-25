/*    */ package org.bukkit.craftbukkit.v1_7_R4.block;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.BlockDispenser;
/*    */ import net.minecraft.server.v1_7_R4.Blocks;
/*    */ import net.minecraft.server.v1_7_R4.IInventory;
/*    */ import net.minecraft.server.v1_7_R4.TileEntityDispenser;
/*    */ import net.minecraft.server.v1_7_R4.World;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.Dispenser;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.projectiles.CraftBlockProjectileSource;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.projectiles.BlockProjectileSource;
/*    */ 
/*    */ public class CraftDispenser extends CraftBlockState implements Dispenser {
/*    */   private final CraftWorld world;
/*    */   
/*    */   public CraftDispenser(Block block) {
/* 21 */     super(block);
/*    */     
/* 23 */     this.world = (CraftWorld)block.getWorld();
/* 24 */     this.dispenser = (TileEntityDispenser)this.world.getTileEntityAt(getX(), getY(), getZ());
/*    */   }
/*    */   private final TileEntityDispenser dispenser;
/*    */   public Inventory getInventory() {
/* 28 */     return (Inventory)new CraftInventory((IInventory)this.dispenser);
/*    */   }
/*    */   
/*    */   public BlockProjectileSource getBlockProjectileSource() {
/* 32 */     Block block = getBlock();
/*    */     
/* 34 */     if (block.getType() != Material.DISPENSER) {
/* 35 */       return null;
/*    */     }
/*    */     
/* 38 */     return (BlockProjectileSource)new CraftBlockProjectileSource(this.dispenser);
/*    */   }
/*    */   
/*    */   public boolean dispense() {
/* 42 */     Block block = getBlock();
/*    */     
/* 44 */     if (block.getType() == Material.DISPENSER) {
/* 45 */       BlockDispenser dispense = (BlockDispenser)Blocks.DISPENSER;
/*    */       
/* 47 */       dispense.dispense((World)this.world.getHandle(), getX(), getY(), getZ());
/* 48 */       return true;
/*    */     } 
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics) {
/* 56 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 58 */     if (result) {
/* 59 */       this.dispenser.update();
/*    */     }
/*    */     
/* 62 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\block\CraftDispenser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */