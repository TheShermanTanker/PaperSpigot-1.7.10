/*    */ package org.github.paperspigot.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.event.block.BlockEvent;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ 
/*    */ public class BeaconEffectEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/* 14 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   private PotionEffect effect;
/*    */   private Player player;
/*    */   private boolean primary;
/*    */   
/*    */   public BeaconEffectEvent(Block block, PotionEffect effect, Player player, boolean primary) {
/* 21 */     super(block);
/* 22 */     this.effect = effect;
/* 23 */     this.player = player;
/* 24 */     this.primary = primary;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 29 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 34 */     this.cancelled = cancelled;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PotionEffect getEffect() {
/* 43 */     return this.effect;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setEffect(PotionEffect effect) {
/* 52 */     this.effect = effect;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Player getPlayer() {
/* 61 */     return this.player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPrimary() {
/* 70 */     return this.primary;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 75 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 79 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\github\paperspigot\event\block\BeaconEffectEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */