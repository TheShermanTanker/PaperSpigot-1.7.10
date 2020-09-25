/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import org.bukkit.entity.CreatureType;
/*     */ import org.bukkit.entity.Egg;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.HandlerList;
/*     */ 
/*     */ 
/*     */ public class PlayerEggThrowEvent
/*     */   extends PlayerEvent
/*     */ {
/*  13 */   private static final HandlerList handlers = new HandlerList();
/*     */   private final Egg egg;
/*     */   private boolean hatching;
/*     */   private EntityType hatchType;
/*     */   private byte numHatches;
/*     */   
/*     */   public PlayerEggThrowEvent(Player player, Egg egg, boolean hatching, byte numHatches, EntityType hatchingType) {
/*  20 */     super(player);
/*  21 */     this.egg = egg;
/*  22 */     this.hatching = hatching;
/*  23 */     this.numHatches = numHatches;
/*  24 */     this.hatchType = hatchingType;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public PlayerEggThrowEvent(Player player, Egg egg, boolean hatching, byte numHatches, CreatureType hatchingType) {
/*  29 */     this(player, egg, hatching, numHatches, hatchingType.toEntityType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Egg getEgg() {
/*  38 */     return this.egg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHatching() {
/*  48 */     return this.hatching;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHatching(boolean hatching) {
/*  58 */     this.hatching = hatching;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public CreatureType getHatchType() {
/*  69 */     return CreatureType.fromEntityType(this.hatchType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityType getHatchingType() {
/*  78 */     return this.hatchType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setHatchType(CreatureType hatchType) {
/*  89 */     this.hatchType = hatchType.toEntityType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHatchingType(EntityType hatchType) {
/*  98 */     if (!hatchType.isSpawnable()) throw new IllegalArgumentException("Can't spawn that entity type from an egg!"); 
/*  99 */     this.hatchType = hatchType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getNumHatches() {
/* 114 */     return this.numHatches;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNumHatches(byte numHatches) {
/* 126 */     this.numHatches = numHatches;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 131 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 135 */     return handlers;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerEggThrowEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */