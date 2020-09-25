/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class VehicleExitEvent
/*    */   extends VehicleEvent
/*    */   implements Cancellable
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   private final LivingEntity exited;
/*    */   
/*    */   public VehicleExitEvent(Vehicle vehicle, LivingEntity exited) {
/* 17 */     super(vehicle);
/* 18 */     this.exited = exited;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LivingEntity getExited() {
/* 27 */     return this.exited;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 31 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 35 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 40 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 44 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\vehicle\VehicleExitEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */