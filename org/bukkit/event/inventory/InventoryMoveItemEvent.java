/*     */ package org.bukkit.event.inventory;
/*     */ 
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventoryMoveItemEvent
/*     */   extends Event
/*     */   implements Cancellable
/*     */ {
/*  27 */   private static final HandlerList handlers = new HandlerList();
/*     */   private boolean cancelled;
/*     */   private final Inventory sourceInventory;
/*     */   private final Inventory destinationInventory;
/*     */   private ItemStack itemStack;
/*     */   private final boolean didSourceInitiate;
/*     */   
/*     */   public InventoryMoveItemEvent(Inventory sourceInventory, ItemStack itemStack, Inventory destinationInventory, boolean didSourceInitiate) {
/*  35 */     Validate.notNull(itemStack, "ItemStack cannot be null");
/*  36 */     this.sourceInventory = sourceInventory;
/*  37 */     this.itemStack = itemStack;
/*  38 */     this.destinationInventory = destinationInventory;
/*  39 */     this.didSourceInitiate = didSourceInitiate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Inventory getSource() {
/*  48 */     return this.sourceInventory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItem() {
/*  58 */     return this.itemStack.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItem(ItemStack itemStack) {
/*  69 */     Validate.notNull(itemStack, "ItemStack cannot be null.  Cancel the event if you want nothing to be transferred.");
/*  70 */     this.itemStack = itemStack.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Inventory getDestination() {
/*  79 */     return this.destinationInventory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Inventory getInitiator() {
/*  89 */     return this.didSourceInitiate ? this.sourceInventory : this.destinationInventory;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/*  93 */     return this.cancelled;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/*  97 */     this.cancelled = cancel;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 102 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 106 */     return handlers;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\inventory\InventoryMoveItemEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */