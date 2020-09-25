/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.TravelAgent;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerPortalEvent
/*    */   extends PlayerTeleportEvent
/*    */ {
/* 15 */   private static final HandlerList handlers = new HandlerList();
/*    */   protected boolean useTravelAgent = true;
/*    */   protected TravelAgent travelAgent;
/*    */   
/*    */   public PlayerPortalEvent(Player player, Location from, Location to, TravelAgent pta) {
/* 20 */     super(player, from, to);
/* 21 */     this.travelAgent = pta;
/*    */   }
/*    */   
/*    */   public PlayerPortalEvent(Player player, Location from, Location to, TravelAgent pta, PlayerTeleportEvent.TeleportCause cause) {
/* 25 */     super(player, from, to, cause);
/* 26 */     this.travelAgent = pta;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void useTravelAgent(boolean useTravelAgent) {
/* 42 */     this.useTravelAgent = useTravelAgent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean useTravelAgent() {
/* 58 */     return (this.useTravelAgent && this.travelAgent != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TravelAgent getPortalTravelAgent() {
/* 67 */     return this.travelAgent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPortalTravelAgent(TravelAgent travelAgent) {
/* 76 */     this.travelAgent = travelAgent;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 81 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 85 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerPortalEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */