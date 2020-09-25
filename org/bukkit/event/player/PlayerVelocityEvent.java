/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class PlayerVelocityEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancel = false;
/*    */   private Vector velocity;
/*    */   
/*    */   public PlayerVelocityEvent(Player player, Vector velocity) {
/* 17 */     super(player);
/* 18 */     this.velocity = velocity;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 22 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 26 */     this.cancel = cancel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Vector getVelocity() {
/* 35 */     return this.velocity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setVelocity(Vector velocity) {
/* 44 */     this.velocity = velocity;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 49 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 53 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerVelocityEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */