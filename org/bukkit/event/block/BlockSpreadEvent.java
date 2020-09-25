/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockState;
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
/*    */ 
/*    */ public class BlockSpreadEvent
/*    */   extends BlockFormEvent
/*    */ {
/* 24 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final Block source;
/*    */   
/*    */   public BlockSpreadEvent(Block block, Block source, BlockState newState) {
/* 28 */     super(block, newState);
/* 29 */     this.source = source;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Block getSource() {
/* 38 */     return this.source;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 43 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 47 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockSpreadEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */