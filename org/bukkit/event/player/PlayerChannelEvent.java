/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PlayerChannelEvent
/*    */   extends PlayerEvent
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final String channel;
/*    */   
/*    */   public PlayerChannelEvent(Player player, String channel) {
/* 15 */     super(player);
/* 16 */     this.channel = channel;
/*    */   }
/*    */   
/*    */   public final String getChannel() {
/* 20 */     return this.channel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 25 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 29 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerChannelEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */