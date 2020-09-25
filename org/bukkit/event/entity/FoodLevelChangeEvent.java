/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.HumanEntity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class FoodLevelChangeEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancel = false;
/*    */   private int level;
/*    */   
/*    */   public FoodLevelChangeEvent(HumanEntity what, int level) {
/* 16 */     super((Entity)what);
/* 17 */     this.level = level;
/*    */   }
/*    */ 
/*    */   
/*    */   public HumanEntity getEntity() {
/* 22 */     return (HumanEntity)this.entity;
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
/*    */   public int getFoodLevel() {
/* 34 */     return this.level;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFoodLevel(int level) {
/* 45 */     if (level > 20) { level = 20; }
/* 46 */     else if (level < 0) { level = 0; }
/*    */     
/* 48 */     this.level = level;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 52 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 56 */     this.cancel = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 61 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 65 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\FoodLevelChangeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */