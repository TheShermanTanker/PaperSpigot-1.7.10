/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.TreeType;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class StructureGrowEvent
/*    */   extends WorldEvent
/*    */   implements Cancellable
/*    */ {
/* 16 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled = false;
/*    */   private final Location location;
/*    */   private final TreeType species;
/*    */   private final boolean bonemeal;
/*    */   private final Player player;
/*    */   private final List<BlockState> blocks;
/*    */   
/*    */   public StructureGrowEvent(Location location, TreeType species, boolean bonemeal, Player player, List<BlockState> blocks) {
/* 25 */     super(location.getWorld());
/* 26 */     this.location = location;
/* 27 */     this.species = species;
/* 28 */     this.bonemeal = bonemeal;
/* 29 */     this.player = player;
/* 30 */     this.blocks = blocks;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Location getLocation() {
/* 39 */     return this.location;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TreeType getSpecies() {
/* 49 */     return this.species;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFromBonemeal() {
/* 58 */     return this.bonemeal;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Player getPlayer() {
/* 68 */     return this.player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<BlockState> getBlocks() {
/* 77 */     return this.blocks;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 81 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 85 */     this.cancelled = cancel;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 90 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 94 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\world\StructureGrowEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */