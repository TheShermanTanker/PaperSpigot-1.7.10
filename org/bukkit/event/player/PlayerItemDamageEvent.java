/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class PlayerItemDamageEvent
/*    */   extends PlayerEvent implements Cancellable {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final ItemStack item;
/*    */   private int damage;
/*    */   private boolean cancelled = false;
/*    */   
/*    */   public PlayerItemDamageEvent(Player player, ItemStack what, int damage) {
/* 16 */     super(player);
/* 17 */     this.item = what;
/* 18 */     this.damage = damage;
/*    */   }
/*    */   
/*    */   public ItemStack getItem() {
/* 22 */     return this.item;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDamage() {
/* 31 */     return this.damage;
/*    */   }
/*    */   
/*    */   public void setDamage(int damage) {
/* 35 */     this.damage = damage;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 39 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 43 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 48 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 52 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerItemDamageEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */