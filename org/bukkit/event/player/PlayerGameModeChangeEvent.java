/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.GameMode;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class PlayerGameModeChangeEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   private final GameMode newGameMode;
/*    */   
/*    */   public PlayerGameModeChangeEvent(Player player, GameMode newGameMode) {
/* 17 */     super(player);
/* 18 */     this.newGameMode = newGameMode;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 22 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 26 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GameMode getNewGameMode() {
/* 35 */     return this.newGameMode;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 40 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 44 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerGameModeChangeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */