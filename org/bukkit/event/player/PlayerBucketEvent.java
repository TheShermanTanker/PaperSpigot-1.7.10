/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public abstract class PlayerBucketEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private ItemStack itemStack;
/*    */   private boolean cancelled = false;
/*    */   private final Block blockClicked;
/*    */   private final BlockFace blockFace;
/*    */   private final Material bucket;
/*    */   
/*    */   public PlayerBucketEvent(Player who, Block blockClicked, BlockFace blockFace, Material bucket, ItemStack itemInHand) {
/* 21 */     super(who);
/* 22 */     this.blockClicked = blockClicked;
/* 23 */     this.blockFace = blockFace;
/* 24 */     this.itemStack = itemInHand;
/* 25 */     this.bucket = bucket;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Material getBucket() {
/* 34 */     return this.bucket;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getItemStack() {
/* 43 */     return this.itemStack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setItemStack(ItemStack itemStack) {
/* 52 */     this.itemStack = itemStack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Block getBlockClicked() {
/* 61 */     return this.blockClicked;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockFace getBlockFace() {
/* 70 */     return this.blockFace;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 74 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 78 */     this.cancelled = cancel;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerBucketEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */