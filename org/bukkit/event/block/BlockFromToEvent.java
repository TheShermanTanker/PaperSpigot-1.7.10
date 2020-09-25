/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockFromToEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/* 16 */   private static final HandlerList handlers = new HandlerList();
/*    */   protected Block to;
/*    */   protected BlockFace face;
/*    */   protected boolean cancel;
/*    */   
/*    */   public BlockFromToEvent(Block block, BlockFace face) {
/* 22 */     super(block);
/* 23 */     this.face = face;
/* 24 */     this.cancel = false;
/*    */   }
/*    */   
/*    */   public BlockFromToEvent(Block block, Block toBlock) {
/* 28 */     super(block);
/* 29 */     this.to = toBlock;
/* 30 */     this.face = BlockFace.SELF;
/* 31 */     this.cancel = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockFace getFace() {
/* 40 */     return this.face;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Block getToBlock() {
/* 49 */     if (this.to == null) {
/* 50 */       this.to = this.block.getRelative(this.face);
/*    */     }
/* 52 */     return this.to;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 56 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 60 */     this.cancel = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 65 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 69 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockFromToEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */