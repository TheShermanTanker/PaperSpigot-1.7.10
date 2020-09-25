/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class BlockExpEvent
/*    */   extends BlockEvent
/*    */ {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   private int exp;
/*    */   
/*    */   public BlockExpEvent(Block block, int exp) {
/* 14 */     super(block);
/*    */     
/* 16 */     this.exp = exp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getExpToDrop() {
/* 25 */     return this.exp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setExpToDrop(int exp) {
/* 35 */     this.exp = exp;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers() {
/* 39 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 43 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockExpEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */