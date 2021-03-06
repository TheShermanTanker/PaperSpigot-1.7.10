/*    */ package org.bukkit.event.inventory;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.entity.HumanEntity;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.InventoryView;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InventoryEvent
/*    */   extends Event
/*    */ {
/* 16 */   private static final HandlerList handlers = new HandlerList();
/*    */   protected InventoryView transaction;
/*    */   
/*    */   public InventoryEvent(InventoryView transaction) {
/* 20 */     this.transaction = transaction;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Inventory getInventory() {
/* 29 */     return this.transaction.getTopInventory();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<HumanEntity> getViewers() {
/* 39 */     return this.transaction.getTopInventory().getViewers();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InventoryView getView() {
/* 48 */     return this.transaction;
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\inventory\InventoryEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */