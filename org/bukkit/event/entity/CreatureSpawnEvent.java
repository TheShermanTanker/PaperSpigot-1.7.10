/*     */ package org.bukkit.event.entity;
/*     */ 
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.CreatureType;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreatureSpawnEvent
/*     */   extends EntitySpawnEvent
/*     */ {
/*     */   private final SpawnReason spawnReason;
/*     */   
/*     */   public CreatureSpawnEvent(LivingEntity spawnee, SpawnReason spawnReason) {
/*  17 */     super((Entity)spawnee);
/*  18 */     this.spawnReason = spawnReason;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public CreatureSpawnEvent(Entity spawnee, CreatureType type, Location loc, SpawnReason reason) {
/*  23 */     super(spawnee);
/*  24 */     this.spawnReason = reason;
/*     */   }
/*     */ 
/*     */   
/*     */   public LivingEntity getEntity() {
/*  29 */     return (LivingEntity)this.entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public CreatureType getCreatureType() {
/*  41 */     return CreatureType.fromEntityType(getEntityType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnReason getSpawnReason() {
/*  51 */     return this.spawnReason;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum SpawnReason
/*     */   {
/*  62 */     NATURAL,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     JOCKEY,
/*     */ 
/*     */ 
/*     */     
/*  71 */     CHUNK_GEN,
/*     */ 
/*     */ 
/*     */     
/*  75 */     SPAWNER,
/*     */ 
/*     */ 
/*     */     
/*  79 */     EGG,
/*     */ 
/*     */ 
/*     */     
/*  83 */     SPAWNER_EGG,
/*     */ 
/*     */ 
/*     */     
/*  87 */     LIGHTNING,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     BED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     BUILD_SNOWMAN,
/*     */ 
/*     */ 
/*     */     
/* 102 */     BUILD_IRONGOLEM,
/*     */ 
/*     */ 
/*     */     
/* 106 */     BUILD_WITHER,
/*     */ 
/*     */ 
/*     */     
/* 110 */     VILLAGE_DEFENSE,
/*     */ 
/*     */ 
/*     */     
/* 114 */     VILLAGE_INVASION,
/*     */ 
/*     */ 
/*     */     
/* 118 */     BREEDING,
/*     */ 
/*     */ 
/*     */     
/* 122 */     SLIME_SPLIT,
/*     */ 
/*     */ 
/*     */     
/* 126 */     REINFORCEMENTS,
/*     */ 
/*     */ 
/*     */     
/* 130 */     NETHER_PORTAL,
/*     */ 
/*     */ 
/*     */     
/* 134 */     DISPENSE_EGG,
/*     */ 
/*     */ 
/*     */     
/* 138 */     INFECTION,
/*     */ 
/*     */ 
/*     */     
/* 142 */     CURED,
/*     */ 
/*     */ 
/*     */     
/* 146 */     OCELOT_BABY,
/*     */ 
/*     */ 
/*     */     
/* 150 */     SILVERFISH_BLOCK,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     MOUNT,
/*     */ 
/*     */ 
/*     */     
/* 159 */     CUSTOM,
/*     */ 
/*     */ 
/*     */     
/* 163 */     DEFAULT;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\CreatureSpawnEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */