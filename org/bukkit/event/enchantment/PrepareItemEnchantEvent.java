/*    */ package org.bukkit.event.enchantment;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.event.inventory.InventoryEvent;
/*    */ import org.bukkit.inventory.InventoryView;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ public class PrepareItemEnchantEvent
/*    */   extends InventoryEvent
/*    */   implements Cancellable
/*    */ {
/* 16 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final Block table;
/*    */   private final ItemStack item;
/*    */   private final int[] levelsOffered;
/*    */   private final int bonus;
/*    */   private boolean cancelled;
/*    */   private final Player enchanter;
/*    */   
/*    */   public PrepareItemEnchantEvent(Player enchanter, InventoryView view, Block table, ItemStack item, int[] levelsOffered, int bonus) {
/* 25 */     super(view);
/* 26 */     this.enchanter = enchanter;
/* 27 */     this.table = table;
/* 28 */     this.item = item;
/* 29 */     this.levelsOffered = levelsOffered;
/* 30 */     this.bonus = bonus;
/* 31 */     this.cancelled = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Player getEnchanter() {
/* 40 */     return this.enchanter;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Block getEnchantBlock() {
/* 49 */     return this.table;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getItem() {
/* 58 */     return this.item;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getExpLevelCostsOffered() {
/* 68 */     return this.levelsOffered;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEnchantmentBonus() {
/* 77 */     return this.bonus;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 81 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 85 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 90 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 94 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\enchantment\PrepareItemEnchantEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */