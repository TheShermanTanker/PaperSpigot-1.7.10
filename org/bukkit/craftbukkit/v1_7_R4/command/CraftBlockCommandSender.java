/*    */ package org.bukkit.craftbukkit.v1_7_R4.command;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.ICommandListener;
/*    */ import net.minecraft.server.v1_7_R4.TileEntityCommandListener;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.command.BlockCommandSender;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CraftBlockCommandSender
/*    */   extends ServerCommandSender
/*    */   implements BlockCommandSender
/*    */ {
/*    */   private final TileEntityCommandListener commandBlock;
/*    */   
/*    */   public CraftBlockCommandSender(TileEntityCommandListener commandBlockListenerAbstract) {
/* 17 */     this.commandBlock = commandBlockListenerAbstract;
/*    */   }
/*    */   
/*    */   public Block getBlock() {
/* 21 */     return this.commandBlock.getWorld().getWorld().getBlockAt((this.commandBlock.getChunkCoordinates()).x, (this.commandBlock.getChunkCoordinates()).y, (this.commandBlock.getChunkCoordinates()).z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendMessage(String message) {}
/*    */ 
/*    */   
/*    */   public void sendMessage(String[] messages) {}
/*    */   
/*    */   public String getName() {
/* 31 */     return this.commandBlock.getName();
/*    */   }
/*    */   
/*    */   public boolean isOp() {
/* 35 */     return true;
/*    */   }
/*    */   
/*    */   public void setOp(boolean value) {
/* 39 */     throw new UnsupportedOperationException("Cannot change operator status of a block");
/*    */   }
/*    */   
/*    */   public ICommandListener getTileEntity() {
/* 43 */     return (ICommandListener)this.commandBlock;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\command\CraftBlockCommandSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */