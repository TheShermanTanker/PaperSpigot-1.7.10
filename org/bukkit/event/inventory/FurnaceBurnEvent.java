/*    */ package org.bukkit.event.inventory;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.event.block.BlockEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class FurnaceBurnEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/* 13 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final ItemStack fuel;
/*    */   private int burnTime;
/*    */   private boolean cancelled;
/*    */   private boolean burning;
/*    */   
/*    */   public FurnaceBurnEvent(Block furnace, ItemStack fuel, int burnTime) {
/* 20 */     super(furnace);
/* 21 */     this.fuel = fuel;
/* 22 */     this.burnTime = burnTime;
/* 23 */     this.cancelled = false;
/* 24 */     this.burning = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Block getFurnace() {
/* 35 */     return getBlock();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getFuel() {
/* 44 */     return this.fuel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getBurnTime() {
/* 53 */     return this.burnTime;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setBurnTime(int burnTime) {
/* 62 */     this.burnTime = burnTime;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isBurning() {
/* 71 */     return this.burning;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setBurning(boolean burning) {
/* 80 */     this.burning = burning;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 84 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 88 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 93 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 97 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\inventory\FurnaceBurnEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */