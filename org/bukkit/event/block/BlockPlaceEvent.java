/*     */ package org.bukkit.event.block;
/*     */ 
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockPlaceEvent
/*     */   extends BlockEvent
/*     */   implements Cancellable
/*     */ {
/*  16 */   private static final HandlerList handlers = new HandlerList();
/*     */   protected boolean cancel;
/*     */   protected boolean canBuild;
/*     */   protected Block placedAgainst;
/*     */   protected BlockState replacedBlockState;
/*     */   protected ItemStack itemInHand;
/*     */   protected Player player;
/*     */   
/*     */   public BlockPlaceEvent(Block placedBlock, BlockState replacedBlockState, Block placedAgainst, ItemStack itemInHand, Player thePlayer, boolean canBuild) {
/*  25 */     super(placedBlock);
/*  26 */     this.placedAgainst = placedAgainst;
/*  27 */     this.itemInHand = itemInHand;
/*  28 */     this.player = thePlayer;
/*  29 */     this.replacedBlockState = replacedBlockState;
/*  30 */     this.canBuild = canBuild;
/*  31 */     this.cancel = false;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/*  35 */     return this.cancel;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/*  39 */     this.cancel = cancel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getPlayer() {
/*  48 */     return this.player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getBlockPlaced() {
/*  58 */     return getBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState getBlockReplacedState() {
/*  68 */     return this.replacedBlockState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getBlockAgainst() {
/*  77 */     return this.placedAgainst;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemInHand() {
/*  87 */     return this.itemInHand;
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
/*     */   public boolean canBuild() {
/* 100 */     return this.canBuild;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBuild(boolean canBuild) {
/* 110 */     this.canBuild = canBuild;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 115 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 119 */     return handlers;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockPlaceEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */