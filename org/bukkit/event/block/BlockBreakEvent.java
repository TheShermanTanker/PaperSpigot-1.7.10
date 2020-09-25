/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockBreakEvent
/*    */   extends BlockExpEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private final Player player;
/*    */   private boolean cancel;
/*    */   
/*    */   public BlockBreakEvent(Block theBlock, Player player) {
/* 34 */     super(theBlock, 0);
/*    */     
/* 36 */     this.player = player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Player getPlayer() {
/* 45 */     return this.player;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 49 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 53 */     this.cancel = cancel;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockBreakEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */