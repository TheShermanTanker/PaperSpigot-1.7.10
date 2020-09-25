/*    */ package org.bukkit.event.server;
/*    */ 
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.map.MapView;
/*    */ 
/*    */ 
/*    */ public class MapInitializeEvent
/*    */   extends ServerEvent
/*    */ {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final MapView mapView;
/*    */   
/*    */   public MapInitializeEvent(MapView mapView) {
/* 14 */     this.mapView = mapView;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MapView getMap() {
/* 23 */     return this.mapView;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 28 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 32 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\server\MapInitializeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */