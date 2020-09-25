/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Horse;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class HorseJumpEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   private float power;
/*    */   
/*    */   public HorseJumpEvent(Horse horse, float power) {
/* 16 */     super((Entity)horse);
/* 17 */     this.power = power;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 21 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 25 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public Horse getEntity() {
/* 30 */     return (Horse)this.entity;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getPower() {
/* 51 */     return this.power;
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
/*    */   public void setPower(float power) {
/* 67 */     this.power = power;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 72 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 76 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\HorseJumpEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */