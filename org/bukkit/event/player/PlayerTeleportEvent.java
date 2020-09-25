/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class PlayerTeleportEvent
/*    */   extends PlayerMoveEvent
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/* 12 */   private TeleportCause cause = TeleportCause.UNKNOWN;
/*    */   
/*    */   public PlayerTeleportEvent(Player player, Location from, Location to) {
/* 15 */     super(player, from, to);
/*    */   }
/*    */   
/*    */   public PlayerTeleportEvent(Player player, Location from, Location to, TeleportCause cause) {
/* 19 */     this(player, from, to);
/*    */     
/* 21 */     this.cause = cause;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TeleportCause getCause() {
/* 30 */     return this.cause;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum TeleportCause
/*    */   {
/* 38 */     ENDER_PEARL,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 43 */     COMMAND,
/*    */ 
/*    */ 
/*    */     
/* 47 */     PLUGIN,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 52 */     NETHER_PORTAL,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 57 */     END_PORTAL,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 62 */     UNKNOWN;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 67 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 71 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerTeleportEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */