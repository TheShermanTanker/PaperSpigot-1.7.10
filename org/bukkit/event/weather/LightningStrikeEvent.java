/*    */ package org.bukkit.event.weather;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.LightningStrike;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class LightningStrikeEvent
/*    */   extends WeatherEvent
/*    */   implements Cancellable
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean canceled;
/*    */   private final LightningStrike bolt;
/*    */   
/*    */   public LightningStrikeEvent(World world, LightningStrike bolt) {
/* 17 */     super(world);
/* 18 */     this.bolt = bolt;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 22 */     return this.canceled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 26 */     this.canceled = cancel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LightningStrike getLightning() {
/* 35 */     return this.bolt;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 40 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 44 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\weather\LightningStrikeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */