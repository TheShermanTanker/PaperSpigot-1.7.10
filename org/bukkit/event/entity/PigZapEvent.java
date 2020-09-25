/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.LightningStrike;
/*    */ import org.bukkit.entity.Pig;
/*    */ import org.bukkit.entity.PigZombie;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class PigZapEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable {
/* 13 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean canceled;
/*    */   private final PigZombie pigzombie;
/*    */   private final LightningStrike bolt;
/*    */   
/*    */   public PigZapEvent(Pig pig, LightningStrike bolt, PigZombie pigzombie) {
/* 19 */     super((Entity)pig);
/* 20 */     this.bolt = bolt;
/* 21 */     this.pigzombie = pigzombie;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 25 */     return this.canceled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 29 */     this.canceled = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public Pig getEntity() {
/* 34 */     return (Pig)this.entity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LightningStrike getLightning() {
/* 43 */     return this.bolt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PigZombie getPigZombie() {
/* 53 */     return this.pigzombie;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 58 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 62 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\PigZapEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */