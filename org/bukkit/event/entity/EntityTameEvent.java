/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.AnimalTamer;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class EntityTameEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   private final AnimalTamer owner;
/*    */   
/*    */   public EntityTameEvent(LivingEntity entity, AnimalTamer owner) {
/* 17 */     super((Entity)entity);
/* 18 */     this.owner = owner;
/*    */   }
/*    */ 
/*    */   
/*    */   public LivingEntity getEntity() {
/* 23 */     return (LivingEntity)this.entity;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 27 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 31 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AnimalTamer getOwner() {
/* 40 */     return this.owner;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 45 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 49 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityTameEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */