/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockDamageEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/* 15 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final Player player;
/*    */   private boolean instaBreak;
/*    */   private boolean cancel;
/*    */   private final ItemStack itemstack;
/*    */   
/*    */   public BlockDamageEvent(Player player, Block block, ItemStack itemInHand, boolean instaBreak) {
/* 22 */     super(block);
/* 23 */     this.instaBreak = instaBreak;
/* 24 */     this.player = player;
/* 25 */     this.itemstack = itemInHand;
/* 26 */     this.cancel = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Player getPlayer() {
/* 35 */     return this.player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getInstaBreak() {
/* 45 */     return this.instaBreak;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setInstaBreak(boolean bool) {
/* 55 */     this.instaBreak = bool;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getItemInHand() {
/* 64 */     return this.itemstack;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 68 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 72 */     this.cancel = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 77 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 81 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockDamageEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */