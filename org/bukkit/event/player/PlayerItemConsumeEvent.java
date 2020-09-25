/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerItemConsumeEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable
/*    */ {
/* 20 */   private static final HandlerList handlers = new HandlerList();
/*    */ 
/*    */   
/*    */   private boolean isCancelled = false;
/*    */   
/*    */   private ItemStack item;
/*    */ 
/*    */   
/*    */   public PlayerItemConsumeEvent(Player player, ItemStack item) {
/* 29 */     super(player);
/*    */     
/* 31 */     this.item = item;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getItem() {
/* 42 */     return this.item.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setItem(ItemStack item) {
/* 51 */     if (item == null) {
/* 52 */       this.item = new ItemStack(Material.AIR);
/*    */     } else {
/* 54 */       this.item = item;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 59 */     return this.isCancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 63 */     this.isCancelled = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 68 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 72 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerItemConsumeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */