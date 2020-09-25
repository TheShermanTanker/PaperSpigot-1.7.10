/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.PortalType;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class EntityCreatePortalEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable {
/* 14 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final List<BlockState> blocks;
/*    */   private boolean cancelled = false;
/* 17 */   private PortalType type = PortalType.CUSTOM;
/*    */   
/*    */   public EntityCreatePortalEvent(LivingEntity what, List<BlockState> blocks, PortalType type) {
/* 20 */     super((Entity)what);
/*    */     
/* 22 */     this.blocks = blocks;
/* 23 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public LivingEntity getEntity() {
/* 28 */     return (LivingEntity)this.entity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<BlockState> getBlocks() {
/* 37 */     return this.blocks;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 41 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 45 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PortalType getPortalType() {
/* 54 */     return this.type;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 59 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 63 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityCreatePortalEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */