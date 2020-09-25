/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Projectile;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class ProjectileHitEvent
/*    */   extends EntityEvent
/*    */ {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   public ProjectileHitEvent(Projectile projectile) {
/* 13 */     super((Entity)projectile);
/*    */   }
/*    */ 
/*    */   
/*    */   public Projectile getEntity() {
/* 18 */     return (Projectile)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 23 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 27 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\ProjectileHitEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */