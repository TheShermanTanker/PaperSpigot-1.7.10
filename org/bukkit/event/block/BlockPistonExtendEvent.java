/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockPistonExtendEvent
/*    */   extends BlockPistonEvent
/*    */ {
/* 15 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final int length;
/*    */   private List<Block> blocks;
/*    */   
/*    */   public BlockPistonExtendEvent(Block block, int length, BlockFace direction) {
/* 20 */     super(block, direction);
/*    */     
/* 22 */     this.length = length;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLength() {
/* 31 */     return this.length;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Block> getBlocks() {
/* 41 */     if (this.blocks == null) {
/* 42 */       ArrayList<Block> tmp = new ArrayList<Block>();
/* 43 */       for (int i = 0; i < getLength(); i++) {
/* 44 */         tmp.add(this.block.getRelative(getDirection(), i + 1));
/*    */       }
/* 46 */       this.blocks = Collections.unmodifiableList(tmp);
/*    */     } 
/* 48 */     return this.blocks;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 53 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 57 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockPistonExtendEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */