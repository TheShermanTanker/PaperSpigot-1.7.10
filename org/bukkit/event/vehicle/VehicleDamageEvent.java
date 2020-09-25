/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.util.NumberConversions;
/*    */ 
/*    */ public class VehicleDamageEvent
/*    */   extends VehicleEvent
/*    */   implements Cancellable
/*    */ {
/* 13 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final Entity attacker;
/*    */   private double damage;
/*    */   private boolean cancelled;
/*    */   
/*    */   @Deprecated
/*    */   public VehicleDamageEvent(Vehicle vehicle, Entity attacker, int damage) {
/* 20 */     this(vehicle, attacker, damage);
/*    */   }
/*    */   
/*    */   public VehicleDamageEvent(Vehicle vehicle, Entity attacker, double damage) {
/* 24 */     super(vehicle);
/* 25 */     this.attacker = attacker;
/* 26 */     this.damage = damage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Entity getAttacker() {
/* 35 */     return this.attacker;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getDamage() {
/* 44 */     return this.damage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public int getDamage() {
/* 54 */     return NumberConversions.ceil(getDamage());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDamage(double damage) {
/* 63 */     this.damage = damage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public void setDamage(int damage) {
/* 73 */     setDamage(damage);
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 77 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 81 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 86 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 90 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\vehicle\VehicleDamageEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */