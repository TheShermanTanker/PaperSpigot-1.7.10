/*    */ package org.spigotmc.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.event.entity.EntityEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityMountEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/* 15 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   private boolean cancelled;
/*    */   private final Entity mount;
/*    */   
/*    */   public EntityMountEvent(Entity what, Entity mount) {
/* 21 */     super(what);
/* 22 */     this.mount = mount;
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity getMount() {
/* 27 */     return this.mount;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 33 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 39 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 45 */     return handlers;
/*    */   }
/*    */ 
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 50 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\event\entity\EntityMountEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */