/*    */ package org.github.paperspigot.event.player;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.event.player.PlayerMoveEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerMicroMoveEvent
/*    */   extends PlayerMoveEvent
/*    */ {
/* 18 */   private static final HandlerList handlerList = new HandlerList();
/*    */   
/*    */   public PlayerMicroMoveEvent(Player player, Location from, Location to) {
/* 21 */     super(player, from, to);
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 26 */     return getHandlerList();
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 30 */     return handlerList;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\github\paperspigot\event\player\PlayerMicroMoveEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */