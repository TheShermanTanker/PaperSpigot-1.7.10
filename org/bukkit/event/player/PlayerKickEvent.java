/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class PlayerKickEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private String leaveMessage;
/*    */   private String kickReason;
/*    */   private Boolean cancel;
/*    */   
/*    */   public PlayerKickEvent(Player playerKicked, String kickReason, String leaveMessage) {
/* 17 */     super(playerKicked);
/* 18 */     this.kickReason = kickReason;
/* 19 */     this.leaveMessage = leaveMessage;
/* 20 */     this.cancel = Boolean.valueOf(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getReason() {
/* 29 */     return this.kickReason;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLeaveMessage() {
/* 38 */     return this.leaveMessage;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 42 */     return this.cancel.booleanValue();
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 46 */     this.cancel = Boolean.valueOf(cancel);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setReason(String kickReason) {
/* 55 */     this.kickReason = kickReason;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLeaveMessage(String leaveMessage) {
/* 64 */     this.leaveMessage = leaveMessage;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 69 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 73 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerKickEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */