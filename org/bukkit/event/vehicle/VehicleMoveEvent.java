/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class VehicleMoveEvent
/*    */   extends VehicleEvent
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final Location from;
/*    */   private final Location to;
/*    */   
/*    */   public VehicleMoveEvent(Vehicle vehicle, Location from, Location to) {
/* 16 */     super(vehicle);
/*    */     
/* 18 */     this.from = from;
/* 19 */     this.to = to;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Location getFrom() {
/* 28 */     return this.from;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Location getTo() {
/* 37 */     return this.to;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 43 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 47 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\vehicle\VehicleMoveEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */