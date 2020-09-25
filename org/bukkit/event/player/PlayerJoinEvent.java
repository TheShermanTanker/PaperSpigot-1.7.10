/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class PlayerJoinEvent
/*    */   extends PlayerEvent
/*    */ {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   private String joinMessage;
/*    */   
/*    */   public PlayerJoinEvent(Player playerJoined, String joinMessage) {
/* 14 */     super(playerJoined);
/* 15 */     this.joinMessage = joinMessage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getJoinMessage() {
/* 24 */     return this.joinMessage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setJoinMessage(String joinMessage) {
/* 33 */     this.joinMessage = joinMessage;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 38 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 42 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerJoinEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */