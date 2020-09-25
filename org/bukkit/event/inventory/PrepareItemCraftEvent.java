/*    */ package org.bukkit.event.inventory;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.inventory.CraftingInventory;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.InventoryView;
/*    */ import org.bukkit.inventory.Recipe;
/*    */ 
/*    */ public class PrepareItemCraftEvent extends InventoryEvent {
/*  9 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean repair;
/*    */   private CraftingInventory matrix;
/*    */   
/*    */   public PrepareItemCraftEvent(CraftingInventory what, InventoryView view, boolean isRepair) {
/* 14 */     super(view);
/* 15 */     this.matrix = what;
/* 16 */     this.repair = isRepair;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Recipe getRecipe() {
/* 27 */     return this.matrix.getRecipe();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CraftingInventory getInventory() {
/* 35 */     return this.matrix;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isRepair() {
/* 45 */     return this.repair;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 50 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 54 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\inventory\PrepareItemCraftEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */