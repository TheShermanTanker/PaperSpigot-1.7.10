/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Projectile;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class ProjectileLaunchEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   
/*    */   public ProjectileLaunchEvent(Entity what) {
/* 16 */     super(what);
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 20 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 24 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public Projectile getEntity() {
/* 29 */     return (Projectile)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 34 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 38 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\ProjectileLaunchEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */