/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Creeper;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.LightningStrike;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class CreeperPowerEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/* 14 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean canceled;
/*    */   private final PowerCause cause;
/*    */   private LightningStrike bolt;
/*    */   
/*    */   public CreeperPowerEvent(Creeper creeper, LightningStrike bolt, PowerCause cause) {
/* 20 */     this(creeper, cause);
/* 21 */     this.bolt = bolt;
/*    */   }
/*    */   
/*    */   public CreeperPowerEvent(Creeper creeper, PowerCause cause) {
/* 25 */     super((Entity)creeper);
/* 26 */     this.cause = cause;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 30 */     return this.canceled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 34 */     this.canceled = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public Creeper getEntity() {
/* 39 */     return (Creeper)this.entity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LightningStrike getLightning() {
/* 48 */     return this.bolt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PowerCause getCause() {
/* 57 */     return this.cause;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 62 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 66 */     return handlers;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum PowerCause
/*    */   {
/* 79 */     LIGHTNING,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 85 */     SET_ON,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 91 */     SET_OFF;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\CreeperPowerEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */