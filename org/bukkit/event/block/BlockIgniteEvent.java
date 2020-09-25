/*     */ package org.bukkit.event.block;
/*     */ 
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.HandlerList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockIgniteEvent
/*     */   extends BlockEvent
/*     */   implements Cancellable
/*     */ {
/*  16 */   private static final HandlerList handlers = new HandlerList();
/*     */   
/*     */   private final IgniteCause cause;
/*     */   
/*     */   private final Entity ignitingEntity;
/*     */   
/*     */   private final Block ignitingBlock;
/*     */   
/*     */   private boolean cancel;
/*     */   
/*     */   @Deprecated
/*     */   public BlockIgniteEvent(Block theBlock, IgniteCause cause, Player thePlayer) {
/*  28 */     this(theBlock, cause, (Entity)thePlayer);
/*     */   }
/*     */   
/*     */   public BlockIgniteEvent(Block theBlock, IgniteCause cause, Entity ignitingEntity) {
/*  32 */     this(theBlock, cause, ignitingEntity, null);
/*     */   }
/*     */   
/*     */   public BlockIgniteEvent(Block theBlock, IgniteCause cause, Block ignitingBlock) {
/*  36 */     this(theBlock, cause, null, ignitingBlock);
/*     */   }
/*     */   
/*     */   public BlockIgniteEvent(Block theBlock, IgniteCause cause, Entity ignitingEntity, Block ignitingBlock) {
/*  40 */     super(theBlock);
/*  41 */     this.cause = cause;
/*  42 */     this.ignitingEntity = ignitingEntity;
/*  43 */     this.ignitingBlock = ignitingBlock;
/*  44 */     this.cancel = false;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/*  48 */     return this.cancel;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/*  52 */     this.cancel = cancel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IgniteCause getCause() {
/*  61 */     return this.cause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getPlayer() {
/*  70 */     if (this.ignitingEntity instanceof Player) {
/*  71 */       return (Player)this.ignitingEntity;
/*     */     }
/*     */     
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity getIgnitingEntity() {
/*  83 */     return this.ignitingEntity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getIgnitingBlock() {
/*  92 */     return this.ignitingBlock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum IgniteCause
/*     */   {
/* 103 */     LAVA,
/*     */ 
/*     */ 
/*     */     
/* 107 */     FLINT_AND_STEEL,
/*     */ 
/*     */ 
/*     */     
/* 111 */     SPREAD,
/*     */ 
/*     */ 
/*     */     
/* 115 */     LIGHTNING,
/*     */ 
/*     */ 
/*     */     
/* 119 */     FIREBALL,
/*     */ 
/*     */ 
/*     */     
/* 123 */     ENDER_CRYSTAL,
/*     */ 
/*     */ 
/*     */     
/* 127 */     EXPLOSION;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 132 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 136 */     return handlers;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockIgniteEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */