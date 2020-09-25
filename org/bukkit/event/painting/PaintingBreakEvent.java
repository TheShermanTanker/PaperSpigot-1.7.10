/*    */ package org.bukkit.event.painting;
/*    */ 
/*    */ import org.bukkit.Warning;
/*    */ import org.bukkit.entity.Painting;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ @Warning(reason = "This event has been replaced by HangingBreakEvent")
/*    */ public class PaintingBreakEvent
/*    */   extends PaintingEvent
/*    */   implements Cancellable
/*    */ {
/* 16 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   private final RemoveCause cause;
/*    */   
/*    */   public PaintingBreakEvent(Painting painting, RemoveCause cause) {
/* 21 */     super(painting);
/* 22 */     this.cause = cause;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RemoveCause getCause() {
/* 31 */     return this.cause;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 35 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 39 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum RemoveCause
/*    */   {
/* 49 */     ENTITY,
/*    */ 
/*    */ 
/*    */     
/* 53 */     FIRE,
/*    */ 
/*    */ 
/*    */     
/* 57 */     OBSTRUCTION,
/*    */ 
/*    */ 
/*    */     
/* 61 */     WATER,
/*    */ 
/*    */ 
/*    */     
/* 65 */     PHYSICS;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 70 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 74 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\painting\PaintingBreakEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */