/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.DyeColor;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Sheep;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class SheepDyeWoolEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancel;
/*    */   private DyeColor color;
/*    */   
/*    */   public SheepDyeWoolEvent(Sheep sheep, DyeColor color) {
/* 17 */     super((Entity)sheep);
/* 18 */     this.cancel = false;
/* 19 */     this.color = color;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 23 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 27 */     this.cancel = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public Sheep getEntity() {
/* 32 */     return (Sheep)this.entity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DyeColor getColor() {
/* 41 */     return this.color;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setColor(DyeColor color) {
/* 50 */     this.color = color;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 55 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 59 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\SheepDyeWoolEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */