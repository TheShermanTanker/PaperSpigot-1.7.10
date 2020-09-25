/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.entity.EntityUnleashEvent;
/*    */ 
/*    */ public class PlayerUnleashEntityEvent
/*    */   extends EntityUnleashEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private final Player player;
/*    */   private boolean cancelled = false;
/*    */   
/*    */   public PlayerUnleashEntityEvent(Entity entity, Player player) {
/* 16 */     super(entity, EntityUnleashEvent.UnleashReason.PLAYER_UNLEASH);
/* 17 */     this.player = player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Player getPlayer() {
/* 26 */     return this.player;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 30 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 34 */     this.cancelled = cancel;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerUnleashEntityEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */