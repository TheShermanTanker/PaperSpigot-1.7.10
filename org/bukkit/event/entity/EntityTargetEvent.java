/*     */ package org.bukkit.event.entity;
/*     */ 
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.HandlerList;
/*     */ 
/*     */ public class EntityTargetEvent
/*     */   extends EntityEvent
/*     */   implements Cancellable
/*     */ {
/*  11 */   private static final HandlerList handlers = new HandlerList();
/*     */   private boolean cancel = false;
/*     */   private Entity target;
/*     */   private final TargetReason reason;
/*     */   
/*     */   public EntityTargetEvent(Entity entity, Entity target, TargetReason reason) {
/*  17 */     super(entity);
/*  18 */     this.target = target;
/*  19 */     this.reason = reason;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/*  23 */     return this.cancel;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/*  27 */     this.cancel = cancel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TargetReason getReason() {
/*  36 */     return this.reason;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity getTarget() {
/*  48 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTarget(Entity target) {
/*  64 */     this.target = target;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/*  69 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/*  73 */     return handlers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum TargetReason
/*     */   {
/*  84 */     TARGET_DIED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     CLOSEST_PLAYER,
/*     */ 
/*     */ 
/*     */     
/*  93 */     TARGET_ATTACKED_ENTITY,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     PIG_ZOMBIE_TARGET,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     FORGOT_TARGET,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     TARGET_ATTACKED_OWNER,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     OWNER_ATTACKED_TARGET,
/*     */ 
/*     */ 
/*     */     
/* 119 */     RANDOM_TARGET,
/*     */ 
/*     */ 
/*     */     
/* 123 */     DEFEND_VILLAGE,
/*     */ 
/*     */ 
/*     */     
/* 127 */     TARGET_ATTACKED_NEARBY_ENTITY,
/*     */ 
/*     */ 
/*     */     
/* 131 */     REINFORCEMENT_TARGET,
/*     */ 
/*     */ 
/*     */     
/* 135 */     COLLISION,
/*     */ 
/*     */ 
/*     */     
/* 139 */     CUSTOM;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityTargetEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */