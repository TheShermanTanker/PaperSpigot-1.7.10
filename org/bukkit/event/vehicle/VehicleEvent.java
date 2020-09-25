/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public abstract class VehicleEvent
/*    */   extends Event
/*    */ {
/*    */   protected Vehicle vehicle;
/*    */   
/*    */   public VehicleEvent(Vehicle vehicle) {
/* 13 */     this.vehicle = vehicle;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final Vehicle getVehicle() {
/* 22 */     return this.vehicle;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\vehicle\VehicleEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */