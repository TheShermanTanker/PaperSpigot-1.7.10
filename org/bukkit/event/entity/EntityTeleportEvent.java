/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class EntityTeleportEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/* 13 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancel;
/*    */   private Location from;
/*    */   private Location to;
/*    */   
/*    */   public EntityTeleportEvent(Entity what, Location from, Location to) {
/* 19 */     super(what);
/* 20 */     this.from = from;
/* 21 */     this.to = to;
/* 22 */     this.cancel = false;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 26 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 30 */     this.cancel = cancel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Location getFrom() {
/* 39 */     return this.from;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFrom(Location from) {
/* 48 */     this.from = from;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Location getTo() {
/* 57 */     return this.to;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTo(Location to) {
/* 66 */     this.to = to;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 71 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 75 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityTeleportEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */