/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class EntityChangeBlockEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/* 14 */   private static final HandlerList handlers = new HandlerList();
/*    */ 
/*    */   
/*    */   private final Block block;
/*    */ 
/*    */   
/*    */   private boolean cancel;
/*    */ 
/*    */   
/*    */   private final Material to;
/*    */   
/*    */   private final byte data;
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public EntityChangeBlockEvent(LivingEntity what, Block block, Material to) {
/* 30 */     this((Entity)what, block, to, (byte)0);
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
/*    */   @Deprecated
/*    */   public EntityChangeBlockEvent(Entity what, Block block, Material to, byte data) {
/* 43 */     super(what);
/* 44 */     this.block = block;
/* 45 */     this.cancel = false;
/* 46 */     this.to = to;
/* 47 */     this.data = data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Block getBlock() {
/* 56 */     return this.block;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 60 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 64 */     this.cancel = cancel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Material getTo() {
/* 73 */     return this.to;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public byte getData() {
/* 84 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 89 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 93 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityChangeBlockEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */