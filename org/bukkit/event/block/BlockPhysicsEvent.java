/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class BlockPhysicsEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */ 
/*    */   
/*    */   private final int changed;
/*    */   
/*    */   private boolean cancel = false;
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public BlockPhysicsEvent(Block block, int changed) {
/* 22 */     super(block);
/* 23 */     this.changed = changed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public int getChangedTypeId() {
/* 34 */     return this.changed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Material getChangedType() {
/* 43 */     return Material.getMaterial(this.changed);
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 47 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 51 */     this.cancel = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 56 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 60 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockPhysicsEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */