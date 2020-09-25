/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class PlayerQuitEvent
/*    */   extends PlayerEvent
/*    */ {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   private String quitMessage;
/*    */   
/*    */   public PlayerQuitEvent(Player who, String quitMessage) {
/* 14 */     super(who);
/* 15 */     this.quitMessage = quitMessage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getQuitMessage() {
/* 24 */     return this.quitMessage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setQuitMessage(String quitMessage) {
/* 33 */     this.quitMessage = quitMessage;
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerQuitEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */