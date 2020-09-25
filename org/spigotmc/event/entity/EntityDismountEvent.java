/*    */ package org.spigotmc.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.event.entity.EntityEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityDismountEvent
/*    */   extends EntityEvent
/*    */ {
/* 14 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   private boolean cancelled;
/*    */   private final Entity dismounted;
/*    */   
/*    */   public EntityDismountEvent(Entity what, Entity dismounted) {
/* 20 */     super(what);
/* 21 */     this.dismounted = dismounted;
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity getDismounted() {
/* 26 */     return this.dismounted;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 32 */     return handlers;
/*    */   }
/*    */ 
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 37 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\event\entity\EntityDismountEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */