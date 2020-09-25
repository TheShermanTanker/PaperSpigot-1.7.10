/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockFormEvent
/*    */   extends BlockGrowEvent
/*    */   implements Cancellable
/*    */ {
/* 25 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   public BlockFormEvent(Block block, BlockState newState) {
/* 28 */     super(block, newState);
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 33 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 37 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockFormEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */