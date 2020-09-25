/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.TravelAgent;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityPortalEvent
/*    */   extends EntityTeleportEvent
/*    */ {
/* 15 */   private static final HandlerList handlers = new HandlerList();
/*    */   protected boolean useTravelAgent = true;
/*    */   protected TravelAgent travelAgent;
/*    */   
/*    */   public EntityPortalEvent(Entity entity, Location from, Location to, TravelAgent pta) {
/* 20 */     super(entity, from, to);
/* 21 */     this.travelAgent = pta;
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
/* 37 */     this.useTravelAgent = useTravelAgent;
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
/* 53 */     return this.useTravelAgent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TravelAgent getPortalTravelAgent() {
/* 62 */     return this.travelAgent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPortalTravelAgent(TravelAgent travelAgent) {
/* 71 */     this.travelAgent = travelAgent;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 76 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 80 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityPortalEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */