/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class PlayerAnimationEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */ 
/*    */   
/*    */   private final PlayerAnimationType animationType;
/*    */ 
/*    */   
/*    */   private boolean isCancelled = false;
/*    */ 
/*    */   
/*    */   public PlayerAnimationEvent(Player player) {
/* 21 */     super(player);
/*    */ 
/*    */     
/* 24 */     this.animationType = PlayerAnimationType.ARM_SWING;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerAnimationType getAnimationType() {
/* 33 */     return this.animationType;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 37 */     return this.isCancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 41 */     this.isCancelled = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 46 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 50 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerAnimationEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */