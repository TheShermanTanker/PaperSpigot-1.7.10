/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Slime;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class SlimeSplitEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancel = false;
/*    */   private int count;
/*    */   
/*    */   public SlimeSplitEvent(Slime slime, int count) {
/* 16 */     super((Entity)slime);
/* 17 */     this.count = count;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 21 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 25 */     this.cancel = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public Slime getEntity() {
/* 30 */     return (Slime)this.entity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCount() {
/* 39 */     return this.count;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCount(int count) {
/* 48 */     this.count = count;
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\SlimeSplitEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */