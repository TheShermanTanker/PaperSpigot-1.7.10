/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.Achievement;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class PlayerAchievementAwardedEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final Achievement achievement;
/*    */   private boolean isCancelled = false;
/*    */   
/*    */   public PlayerAchievementAwardedEvent(Player player, Achievement achievement) {
/* 17 */     super(player);
/* 18 */     this.achievement = achievement;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Achievement getAchievement() {
/* 27 */     return this.achievement;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 31 */     return this.isCancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 35 */     this.isCancelled = cancel;
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerAchievementAwardedEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */