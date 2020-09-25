/*    */ package org.bukkit.event.hanging;
/*    */ 
/*    */ import org.bukkit.entity.Hanging;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class HangingBreakEvent
/*    */   extends HangingEvent
/*    */   implements Cancellable
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   private final RemoveCause cause;
/*    */   
/*    */   public HangingBreakEvent(Hanging hanging, RemoveCause cause) {
/* 16 */     super(hanging);
/* 17 */     this.cause = cause;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RemoveCause getCause() {
/* 26 */     return this.cause;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 30 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 34 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum RemoveCause
/*    */   {
/* 44 */     ENTITY,
/*    */ 
/*    */ 
/*    */     
/* 48 */     EXPLOSION,
/*    */ 
/*    */ 
/*    */     
/* 52 */     OBSTRUCTION,
/*    */ 
/*    */ 
/*    */     
/* 56 */     PHYSICS,
/*    */ 
/*    */ 
/*    */     
/* 60 */     DEFAULT;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 65 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 69 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\hanging\HangingBreakEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */