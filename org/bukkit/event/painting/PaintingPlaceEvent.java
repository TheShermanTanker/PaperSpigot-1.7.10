/*    */ package org.bukkit.event.painting;
/*    */ 
/*    */ import org.bukkit.Warning;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.entity.Painting;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ @Warning(reason = "This event has been replaced by HangingPlaceEvent")
/*    */ public class PaintingPlaceEvent
/*    */   extends PaintingEvent
/*    */   implements Cancellable
/*    */ {
/* 19 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   private final Player player;
/*    */   private final Block block;
/*    */   private final BlockFace blockFace;
/*    */   
/*    */   public PaintingPlaceEvent(Painting painting, Player player, Block block, BlockFace blockFace) {
/* 26 */     super(painting);
/* 27 */     this.player = player;
/* 28 */     this.block = block;
/* 29 */     this.blockFace = blockFace;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Player getPlayer() {
/* 38 */     return this.player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Block getBlock() {
/* 47 */     return this.block;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockFace getBlockFace() {
/* 57 */     return this.blockFace;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 61 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 65 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 70 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 74 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\painting\PaintingPlaceEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */