/*    */ package org.bukkit.event.inventory;
/*    */ 
/*    */ import org.bukkit.entity.HumanEntity;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.inventory.InventoryView;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InventoryCloseEvent
/*    */   extends InventoryEvent
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   public InventoryCloseEvent(InventoryView transaction) {
/* 15 */     super(transaction);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final HumanEntity getPlayer() {
/* 24 */     return this.transaction.getPlayer();
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 29 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 33 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\inventory\InventoryCloseEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */