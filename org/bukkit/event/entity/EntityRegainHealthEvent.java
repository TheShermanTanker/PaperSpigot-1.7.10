/*     */ package org.bukkit.event.entity;
/*     */ 
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.util.NumberConversions;
/*     */ 
/*     */ public class EntityRegainHealthEvent
/*     */   extends EntityEvent
/*     */   implements Cancellable
/*     */ {
/*  12 */   private static final HandlerList handlers = new HandlerList();
/*     */   private boolean cancelled;
/*     */   private double amount;
/*     */   private final RegainReason regainReason;
/*     */   
/*     */   @Deprecated
/*     */   public EntityRegainHealthEvent(Entity entity, int amount, RegainReason regainReason) {
/*  19 */     this(entity, amount, regainReason);
/*     */   }
/*     */   
/*     */   public EntityRegainHealthEvent(Entity entity, double amount, RegainReason regainReason) {
/*  23 */     super(entity);
/*  24 */     this.amount = amount;
/*  25 */     this.regainReason = regainReason;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAmount() {
/*  34 */     return this.amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getAmount() {
/*  44 */     return NumberConversions.ceil(getAmount());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAmount(double amount) {
/*  53 */     this.amount = amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setAmount(int amount) {
/*  63 */     setAmount(amount);
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/*  67 */     return this.cancelled;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/*  71 */     this.cancelled = cancel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegainReason getRegainReason() {
/*  81 */     return this.regainReason;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/*  86 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/*  90 */     return handlers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum RegainReason
/*     */   {
/* 102 */     REGEN,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     SATIATED,
/*     */ 
/*     */ 
/*     */     
/* 111 */     EATING,
/*     */ 
/*     */ 
/*     */     
/* 115 */     ENDER_CRYSTAL,
/*     */ 
/*     */ 
/*     */     
/* 119 */     MAGIC,
/*     */ 
/*     */ 
/*     */     
/* 123 */     MAGIC_REGEN,
/*     */ 
/*     */ 
/*     */     
/* 127 */     WITHER_SPAWN,
/*     */ 
/*     */ 
/*     */     
/* 131 */     WITHER,
/*     */ 
/*     */ 
/*     */     
/* 135 */     CUSTOM;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityRegainHealthEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */