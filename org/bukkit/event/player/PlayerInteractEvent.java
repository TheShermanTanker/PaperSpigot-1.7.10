/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class PlayerInteractEvent
/*     */   extends PlayerEvent
/*     */   implements Cancellable {
/*  16 */   private static final HandlerList handlers = new HandlerList();
/*     */   protected ItemStack item;
/*     */   protected Action action;
/*     */   protected Block blockClicked;
/*     */   protected BlockFace blockFace;
/*     */   private Event.Result useClickedBlock;
/*     */   private Event.Result useItemInHand;
/*     */   
/*     */   public PlayerInteractEvent(Player who, Action action, ItemStack item, Block clickedBlock, BlockFace clickedFace) {
/*  25 */     super(who);
/*  26 */     this.action = action;
/*  27 */     this.item = item;
/*  28 */     this.blockClicked = clickedBlock;
/*  29 */     this.blockFace = clickedFace;
/*     */     
/*  31 */     this.useItemInHand = Event.Result.DEFAULT;
/*  32 */     this.useClickedBlock = (clickedBlock == null) ? Event.Result.DENY : Event.Result.ALLOW;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action getAction() {
/*  41 */     return this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/*  51 */     return (useInteractedBlock() == Event.Result.DENY);
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
/*     */   public void setCancelled(boolean cancel) {
/*  65 */     setUseInteractedBlock(cancel ? Event.Result.DENY : ((useInteractedBlock() == Event.Result.DENY) ? Event.Result.DEFAULT : useInteractedBlock()));
/*  66 */     setUseItemInHand(cancel ? Event.Result.DENY : ((useItemInHand() == Event.Result.DENY) ? Event.Result.DEFAULT : useItemInHand()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItem() {
/*  75 */     return this.item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  85 */     if (!hasItem()) {
/*  86 */       return Material.AIR;
/*     */     }
/*     */     
/*  89 */     return this.item.getType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasBlock() {
/*  98 */     return (this.blockClicked != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasItem() {
/* 107 */     return (this.item != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlockInHand() {
/* 117 */     if (!hasItem()) {
/* 118 */       return false;
/*     */     }
/*     */     
/* 121 */     return this.item.getType().isBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getClickedBlock() {
/* 130 */     return this.blockClicked;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getBlockFace() {
/* 139 */     return this.blockFace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Event.Result useInteractedBlock() {
/* 150 */     return this.useClickedBlock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUseInteractedBlock(Event.Result useInteractedBlock) {
/* 157 */     this.useClickedBlock = useInteractedBlock;
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
/*     */   public Event.Result useItemInHand() {
/* 169 */     return this.useItemInHand;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUseItemInHand(Event.Result useItemInHand) {
/* 176 */     this.useItemInHand = useItemInHand;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 181 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 185 */     return handlers;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerInteractEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */