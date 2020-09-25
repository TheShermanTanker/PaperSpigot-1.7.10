/*    */ package org.bukkit.craftbukkit.v1_7_R4.block;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.TileEntityMobSpawner;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.CreatureSpawner;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ import org.bukkit.entity.CreatureType;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftCreatureSpawner
/*    */   extends CraftBlockState implements CreatureSpawner {
/*    */   private final TileEntityMobSpawner spawner;
/*    */   
/*    */   public CraftCreatureSpawner(Block block) {
/* 15 */     super(block);
/*    */     
/* 17 */     this.spawner = (TileEntityMobSpawner)((CraftWorld)block.getWorld()).getTileEntityAt(getX(), getY(), getZ());
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public CreatureType getCreatureType() {
/* 22 */     return CreatureType.fromName(this.spawner.getSpawner().getMobName());
/*    */   }
/*    */   
/*    */   public EntityType getSpawnedType() {
/* 26 */     return EntityType.fromName(this.spawner.getSpawner().getMobName());
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public void setCreatureType(CreatureType creatureType) {
/* 31 */     this.spawner.getSpawner().setMobName(creatureType.getName());
/*    */   }
/*    */   
/*    */   public void setSpawnedType(EntityType entityType) {
/* 35 */     if (entityType == null || entityType.getName() == null) {
/* 36 */       throw new IllegalArgumentException("Can't spawn EntityType " + entityType + " from mobspawners!");
/*    */     }
/*    */     
/* 39 */     this.spawner.getSpawner().setMobName(entityType.getName());
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public String getCreatureTypeId() {
/* 44 */     return this.spawner.getSpawner().getMobName();
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public void setCreatureTypeId(String creatureName) {
/* 49 */     setCreatureTypeByName(creatureName);
/*    */   }
/*    */   
/*    */   public String getCreatureTypeName() {
/* 53 */     return this.spawner.getSpawner().getMobName();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCreatureTypeByName(String creatureType) {
/* 58 */     EntityType type = EntityType.fromName(creatureType);
/* 59 */     if (type == null) {
/*    */       return;
/*    */     }
/* 62 */     setSpawnedType(type);
/*    */   }
/*    */   
/*    */   public int getDelay() {
/* 66 */     return (this.spawner.getSpawner()).spawnDelay;
/*    */   }
/*    */   
/*    */   public void setDelay(int delay) {
/* 70 */     (this.spawner.getSpawner()).spawnDelay = delay;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\block\CraftCreatureSpawner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */