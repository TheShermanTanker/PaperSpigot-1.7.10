/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class WorldUnloadEvent
/*    */   extends WorldEvent
/*    */   implements Cancellable
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean isCancelled;
/*    */   
/*    */   public WorldUnloadEvent(World world) {
/* 15 */     super(world);
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 19 */     return this.isCancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 23 */     this.isCancelled = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 28 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 32 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\world\WorldUnloadEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */