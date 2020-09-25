/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockCanBuildEvent
/*    */   extends BlockEvent
/*    */ {
/* 19 */   private static final HandlerList handlers = new HandlerList();
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean buildable;
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   protected int material;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public BlockCanBuildEvent(Block block, int id, boolean canBuild) {
/* 35 */     super(block);
/* 36 */     this.buildable = canBuild;
/* 37 */     this.material = id;
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
/*    */   public boolean isBuildable() {
/* 49 */     return this.buildable;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setBuildable(boolean cancel) {
/* 59 */     this.buildable = cancel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Material getMaterial() {
/* 68 */     return Material.getMaterial(this.material);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public int getMaterialId() {
/* 79 */     return this.material;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 84 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 88 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockCanBuildEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */