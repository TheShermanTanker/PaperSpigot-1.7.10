/*    */ package org.spigotmc.event.player;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.event.player.PlayerEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerSpawnLocationEvent
/*    */   extends PlayerEvent
/*    */ {
/* 13 */   private static final HandlerList handlers = new HandlerList();
/*    */   private Location spawnLocation;
/*    */   
/*    */   public PlayerSpawnLocationEvent(Player who, Location spawnLocation) {
/* 17 */     super(who);
/* 18 */     this.spawnLocation = spawnLocation;
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
/*    */   public Location getSpawnLocation() {
/* 30 */     return this.spawnLocation;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSpawnLocation(Location location) {
/* 39 */     this.spawnLocation = location;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 44 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 48 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\event\player\PlayerSpawnLocationEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */