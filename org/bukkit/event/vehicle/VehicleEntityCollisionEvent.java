/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class VehicleEntityCollisionEvent
/*    */   extends VehicleCollisionEvent
/*    */   implements Cancellable
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final Entity entity;
/*    */   private boolean cancelled = false;
/*    */   private boolean cancelledPickup = false;
/*    */   private boolean cancelledCollision = false;
/*    */   
/*    */   public VehicleEntityCollisionEvent(Vehicle vehicle, Entity entity) {
/* 19 */     super(vehicle);
/* 20 */     this.entity = entity;
/*    */   }
/*    */   
/*    */   public Entity getEntity() {
/* 24 */     return this.entity;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 28 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 32 */     this.cancelled = cancel;
/*    */   }
/*    */   
/*    */   public boolean isPickupCancelled() {
/* 36 */     return this.cancelledPickup;
/*    */   }
/*    */   
/*    */   public void setPickupCancelled(boolean cancel) {
/* 40 */     this.cancelledPickup = cancel;
/*    */   }
/*    */   
/*    */   public boolean isCollisionCancelled() {
/* 44 */     return this.cancelledCollision;
/*    */   }
/*    */   
/*    */   public void setCollisionCancelled(boolean cancel) {
/* 48 */     this.cancelledCollision = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 53 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 57 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\vehicle\VehicleEntityCollisionEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */