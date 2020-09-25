/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Item;
/*    */ 
/*    */ public class ItemSpawnEvent
/*    */   extends EntitySpawnEvent
/*    */ {
/*    */   public ItemSpawnEvent(Item spawnee) {
/* 11 */     super((Entity)spawnee);
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public ItemSpawnEvent(Item spawnee, Location loc) {
/* 16 */     this(spawnee);
/*    */   }
/*    */ 
/*    */   
/*    */   public Item getEntity() {
/* 21 */     return (Item)this.entity;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\ItemSpawnEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */