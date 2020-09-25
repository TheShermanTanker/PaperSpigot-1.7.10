/*    */ package org.bukkit.event.hanging;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.entity.Hanging;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class HangingPlaceEvent
/*    */   extends HangingEvent
/*    */   implements Cancellable
/*    */ {
/* 14 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   private final Player player;
/*    */   private final Block block;
/*    */   private final BlockFace blockFace;
/*    */   
/*    */   public HangingPlaceEvent(Hanging hanging, Player player, Block block, BlockFace blockFace) {
/* 21 */     super(hanging);
/* 22 */     this.player = player;
/* 23 */     this.block = block;
/* 24 */     this.blockFace = blockFace;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Player getPlayer() {
/* 33 */     return this.player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Block getBlock() {
/* 42 */     return this.block;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockFace getBlockFace() {
/* 51 */     return this.blockFace;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 55 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 59 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 64 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 68 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\hanging\HangingPlaceEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */