/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class EntityExplodeEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/* 15 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancel;
/*    */   private final Location location;
/*    */   private final List<Block> blocks;
/*    */   private float yield;
/*    */   
/*    */   public EntityExplodeEvent(Entity what, Location location, List<Block> blocks, float yield) {
/* 22 */     super(what);
/* 23 */     this.location = location;
/* 24 */     this.blocks = blocks;
/* 25 */     this.yield = yield;
/* 26 */     this.cancel = false;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 30 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 34 */     this.cancel = cancel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Block> blockList() {
/* 44 */     return this.blocks;
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
/*    */   public Location getLocation() {
/* 56 */     return this.location;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getYield() {
/* 65 */     return this.yield;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setYield(float yield) {
/* 74 */     this.yield = yield;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 79 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 83 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityExplodeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */