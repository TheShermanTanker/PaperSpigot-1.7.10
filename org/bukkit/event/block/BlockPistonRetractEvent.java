/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class BlockPistonRetractEvent
/*    */   extends BlockPistonEvent
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   public BlockPistonRetractEvent(Block block, BlockFace direction) {
/* 14 */     super(block, direction);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Location getRetractLocation() {
/* 24 */     return getBlock().getRelative(getDirection(), 2).getLocation();
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 29 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 33 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockPistonRetractEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */