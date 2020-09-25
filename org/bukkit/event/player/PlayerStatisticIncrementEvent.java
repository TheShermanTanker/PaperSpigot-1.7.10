/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Statistic;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.HandlerList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerStatisticIncrementEvent
/*     */   extends PlayerEvent
/*     */   implements Cancellable
/*     */ {
/*  18 */   private static final HandlerList handlers = new HandlerList();
/*     */   protected final Statistic statistic;
/*     */   private final int initialValue;
/*     */   private final int newValue;
/*     */   private boolean isCancelled = false;
/*     */   private final EntityType entityType;
/*     */   private final Material material;
/*     */   
/*     */   public PlayerStatisticIncrementEvent(Player player, Statistic statistic, int initialValue, int newValue) {
/*  27 */     super(player);
/*  28 */     this.statistic = statistic;
/*  29 */     this.initialValue = initialValue;
/*  30 */     this.newValue = newValue;
/*  31 */     this.entityType = null;
/*  32 */     this.material = null;
/*     */   }
/*     */   
/*     */   public PlayerStatisticIncrementEvent(Player player, Statistic statistic, int initialValue, int newValue, EntityType entityType) {
/*  36 */     super(player);
/*  37 */     this.statistic = statistic;
/*  38 */     this.initialValue = initialValue;
/*  39 */     this.newValue = newValue;
/*  40 */     this.entityType = entityType;
/*  41 */     this.material = null;
/*     */   }
/*     */   
/*     */   public PlayerStatisticIncrementEvent(Player player, Statistic statistic, int initialValue, int newValue, Material material) {
/*  45 */     super(player);
/*  46 */     this.statistic = statistic;
/*  47 */     this.initialValue = initialValue;
/*  48 */     this.newValue = newValue;
/*  49 */     this.entityType = null;
/*  50 */     this.material = material;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Statistic getStatistic() {
/*  59 */     return this.statistic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPreviousValue() {
/*  68 */     return this.initialValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNewValue() {
/*  77 */     return this.newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityType getEntityType() {
/*  87 */     return this.entityType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  97 */     return this.material;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/* 101 */     return this.isCancelled;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/* 105 */     this.isCancelled = cancel;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 110 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 114 */     return handlers;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerStatisticIncrementEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */