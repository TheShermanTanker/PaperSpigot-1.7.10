/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class VehicleBlockCollisionEvent
/*    */   extends VehicleCollisionEvent
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final Block block;
/*    */   
/*    */   public VehicleBlockCollisionEvent(Vehicle vehicle, Block block) {
/* 15 */     super(vehicle);
/* 16 */     this.block = block;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Block getBlock() {
/* 25 */     return this.block;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 30 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 34 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\vehicle\VehicleBlockCollisionEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */