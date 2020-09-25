/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class PlayerExpChangeEvent
/*    */   extends PlayerEvent
/*    */ {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   private int exp;
/*    */   
/*    */   public PlayerExpChangeEvent(Player player, int expAmount) {
/* 14 */     super(player);
/* 15 */     this.exp = expAmount;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 24 */     return this.exp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAmount(int amount) {
/* 33 */     this.exp = amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 38 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 42 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerExpChangeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */