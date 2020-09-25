/*    */ package org.bukkit.event.inventory;
/*    */ 
/*    */ import org.bukkit.entity.HumanEntity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.inventory.InventoryView;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class InventoryInteractEvent
/*    */   extends InventoryEvent
/*    */   implements Cancellable
/*    */ {
/* 14 */   private Event.Result result = Event.Result.DEFAULT;
/*    */   
/*    */   public InventoryInteractEvent(InventoryView transaction) {
/* 17 */     super(transaction);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HumanEntity getWhoClicked() {
/* 26 */     return getView().getPlayer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setResult(Event.Result newResult) {
/* 37 */     this.result = newResult;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Event.Result getResult() {
/* 48 */     return this.result;
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
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 62 */     return (getResult() == Event.Result.DENY);
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
/*    */   
/*    */   public void setCancelled(boolean toCancel) {
/* 75 */     setResult(toCancel ? Event.Result.DENY : Event.Result.ALLOW);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\inventory\InventoryInteractEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */