/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityCombustEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/* 13 */   private static final HandlerList handlers = new HandlerList();
/*    */   private int duration;
/*    */   private boolean cancel;
/*    */   
/*    */   public EntityCombustEvent(Entity combustee, int duration) {
/* 18 */     super(combustee);
/* 19 */     this.duration = duration;
/* 20 */     this.cancel = false;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 24 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 28 */     this.cancel = cancel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDuration() {
/* 36 */     return this.duration;
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
/*    */   public void setDuration(int duration) {
/* 48 */     this.duration = duration;
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityCombustEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */