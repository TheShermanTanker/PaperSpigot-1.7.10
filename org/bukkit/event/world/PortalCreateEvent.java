/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class PortalCreateEvent
/*    */   extends WorldEvent
/*    */   implements Cancellable
/*    */ {
/* 15 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancel = false;
/* 17 */   private final ArrayList<Block> blocks = new ArrayList<Block>();
/* 18 */   private CreateReason reason = CreateReason.FIRE;
/*    */   
/*    */   public PortalCreateEvent(Collection<Block> blocks, World world, CreateReason reason) {
/* 21 */     super(world);
/*    */     
/* 23 */     this.blocks.addAll(blocks);
/* 24 */     this.reason = reason;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ArrayList<Block> getBlocks() {
/* 33 */     return this.blocks;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 37 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 41 */     this.cancel = cancel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CreateReason getReason() {
/* 50 */     return this.reason;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 55 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 59 */     return handlers;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum CreateReason
/*    */   {
/* 70 */     FIRE,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 75 */     OBC_DESTINATION;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\world\PortalCreateEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */