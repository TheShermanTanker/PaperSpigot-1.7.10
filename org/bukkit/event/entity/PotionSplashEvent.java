/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.entity.Projectile;
/*    */ import org.bukkit.entity.ThrownPotion;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class PotionSplashEvent
/*    */   extends ProjectileHitEvent
/*    */   implements Cancellable {
/* 17 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   private final Map<LivingEntity, Double> affectedEntities;
/*    */   
/*    */   public PotionSplashEvent(ThrownPotion potion, Map<LivingEntity, Double> affectedEntities) {
/* 22 */     super((Projectile)potion);
/*    */     
/* 24 */     this.affectedEntities = affectedEntities;
/*    */   }
/*    */ 
/*    */   
/*    */   public ThrownPotion getEntity() {
/* 29 */     return (ThrownPotion)this.entity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ThrownPotion getPotion() {
/* 38 */     return getEntity();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<LivingEntity> getAffectedEntities() {
/* 47 */     return new ArrayList<LivingEntity>(this.affectedEntities.keySet());
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
/*    */   public double getIntensity(LivingEntity entity) {
/* 59 */     Double intensity = this.affectedEntities.get(entity);
/* 60 */     return (intensity != null) ? intensity.doubleValue() : 0.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setIntensity(LivingEntity entity, double intensity) {
/* 70 */     Validate.notNull(entity, "You must specify a valid entity.");
/* 71 */     if (intensity <= 0.0D) {
/* 72 */       this.affectedEntities.remove(entity);
/*    */     } else {
/* 74 */       this.affectedEntities.put(entity, Double.valueOf(Math.min(intensity, 1.0D)));
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 79 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 83 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 88 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 92 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\PotionSplashEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */