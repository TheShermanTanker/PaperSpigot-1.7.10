/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.block.CreatureSpawner;
/*    */ import org.bukkit.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpawnerSpawnEvent
/*    */   extends EntitySpawnEvent
/*    */ {
/*    */   private final CreatureSpawner spawner;
/*    */   
/*    */   public SpawnerSpawnEvent(Entity spawnee, CreatureSpawner spawner) {
/* 15 */     super(spawnee);
/* 16 */     this.spawner = spawner;
/*    */   }
/*    */   
/*    */   public CreatureSpawner getSpawner() {
/* 20 */     return this.spawner;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\SpawnerSpawnEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */