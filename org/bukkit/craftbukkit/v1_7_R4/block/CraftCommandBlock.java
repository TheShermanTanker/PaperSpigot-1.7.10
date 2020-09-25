/*    */ package org.bukkit.craftbukkit.v1_7_R4.block;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.TileEntityCommand;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.CommandBlock;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ 
/*    */ public class CraftCommandBlock extends CraftBlockState implements CommandBlock {
/*    */   private final TileEntityCommand commandBlock;
/*    */   private String command;
/*    */   private String name;
/*    */   
/*    */   public CraftCommandBlock(Block block) {
/* 14 */     super(block);
/*    */     
/* 16 */     CraftWorld world = (CraftWorld)block.getWorld();
/* 17 */     this.commandBlock = (TileEntityCommand)world.getTileEntityAt(getX(), getY(), getZ());
/* 18 */     this.command = this.commandBlock.getCommandBlock().getCommand();
/* 19 */     this.name = this.commandBlock.getCommandBlock().getName();
/*    */   }
/*    */   
/*    */   public String getCommand() {
/* 23 */     return this.command;
/*    */   }
/*    */   
/*    */   public void setCommand(String command) {
/* 27 */     this.command = (command != null) ? command : "";
/*    */   }
/*    */   
/*    */   public String getName() {
/* 31 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 35 */     this.name = (name != null) ? name : "@";
/*    */   }
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics) {
/* 39 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 41 */     if (result) {
/* 42 */       this.commandBlock.getCommandBlock().setCommand(this.command);
/* 43 */       this.commandBlock.getCommandBlock().setName(this.name);
/*    */     } 
/*    */     
/* 46 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\block\CraftCommandBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */