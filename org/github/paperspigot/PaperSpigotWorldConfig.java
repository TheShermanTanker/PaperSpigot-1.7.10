/*     */ package org.github.paperspigot;public class PaperSpigotWorldConfig { private final String worldName; private final YamlConfiguration config; private boolean verbose; public boolean allowUndeadHorseLeashing; public double squidMinSpawnHeight; public double squidMaxSpawnHeight; public float playerBlockingDamageMultiplier; public int cactusMaxHeight; public int reedMaxHeight; public boolean invertedDaylightDetectors; public int fishingMinTicks; public int fishingMaxTicks; public float blockBreakExhaustion; public float playerSwimmingExhaustion; public Integer softDespawnDistance; public Integer hardDespawnDistance;
/*     */   public boolean keepSpawnInMemory;
/*     */   public double fallingBlockHeightNerf;
/*     */   public int waterOverLavaFlowSpeed;
/*     */   public boolean removeInvalidMobSpawnerTEs;
/*     */   public boolean removeUnloadedEnderPearls;
/*     */   public boolean removeUnloadedTNTEntities;
/*     */   public boolean removeUnloadedFallingBlocks;
/*     */   public boolean boatsDropBoats;
/*     */   public boolean lessPickyTorches;
/*     */   public boolean disablePlayerCrits;
/*     */   public int tickNextTickListCap;
/*     */   public boolean tickNextTickListCapIgnoresRedstone;
/*     */   
/*     */   public PaperSpigotWorldConfig(String worldName) {
/*  16 */     this.worldName = worldName;
/*  17 */     this.config = PaperSpigotConfig.config;
/*  18 */     init();
/*     */   }
/*     */   public boolean useAsyncLighting; public boolean generateCanyon; public boolean generateCaves; public boolean generateDungeon; public boolean generateFortress; public boolean generateMineshaft; public boolean generateStronghold; public boolean generateTemple; public boolean generateVillage; public boolean generateFlatBedrock; public boolean loadUnloadedEnderPearls; public boolean loadUnloadedTNTEntities; public boolean loadUnloadedFallingBlocks; public boolean fallingBlocksCollideWithSigns; public boolean disableEndCredits; public boolean optimizeExplosions; public boolean fastDrainLava; public boolean fastDrainWater; public int lavaFlowSpeedNormal; public int lavaFlowSpeedNether; public boolean netherVoidTopDamage; public boolean disableExplosionKnockback; public boolean disableThunder; public boolean disableIceAndSnow; public boolean disableMoodSounds; public int mobSpawnerTickRate; public boolean cacheChunkMaps; public int containerUpdateTickRate; public float tntExplosionVolume;
/*     */   
/*     */   public void init() {
/*  23 */     this.verbose = getBoolean("verbose", true);
/*     */     
/*  25 */     log("-------- World Settings For [" + this.worldName + "] --------");
/*  26 */     PaperSpigotConfig.readConfig(PaperSpigotWorldConfig.class, this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void log(String s) {
/*  31 */     if (this.verbose)
/*     */     {
/*  33 */       Bukkit.getLogger().info(s);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void set(String path, Object val) {
/*  39 */     this.config.set("world-settings.default." + path, val);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean getBoolean(String path, boolean def) {
/*  44 */     this.config.addDefault("world-settings.default." + path, Boolean.valueOf(def));
/*  45 */     return this.config.getBoolean("world-settings." + this.worldName + "." + path, this.config.getBoolean("world-settings.default." + path));
/*     */   }
/*     */ 
/*     */   
/*     */   private double getDouble(String path, double def) {
/*  50 */     this.config.addDefault("world-settings.default." + path, Double.valueOf(def));
/*  51 */     return this.config.getDouble("world-settings." + this.worldName + "." + path, this.config.getDouble("world-settings.default." + path));
/*     */   }
/*     */ 
/*     */   
/*     */   private int getInt(String path, int def) {
/*  56 */     this.config.addDefault("world-settings.default." + path, Integer.valueOf(def));
/*  57 */     return this.config.getInt("world-settings." + this.worldName + "." + path, this.config.getInt("world-settings.default." + path));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float getFloat(String path, float def) {
/*  63 */     return (float)getDouble(path, def);
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> List getList(String path, T def) {
/*  68 */     this.config.addDefault("world-settings.default." + path, def);
/*  69 */     return this.config.getList("world-settings." + this.worldName + "." + path, this.config.getList("world-settings.default." + path));
/*     */   }
/*     */ 
/*     */   
/*     */   private String getString(String path, String def) {
/*  74 */     this.config.addDefault("world-settings.default." + path, def);
/*  75 */     return this.config.getString("world-settings." + this.worldName + "." + path, this.config.getString("world-settings.default." + path));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void allowUndeadHorseLeashing() {
/*  81 */     this.allowUndeadHorseLeashing = getBoolean("allow-undead-horse-leashing", true);
/*  82 */     log("Allow undead horse types to be leashed: " + this.allowUndeadHorseLeashing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void squidSpawnHeight() {
/*  89 */     this.squidMinSpawnHeight = getDouble("squid-spawn-height.minimum", 45.0D);
/*  90 */     this.squidMaxSpawnHeight = getDouble("squid-spawn-height.maximum", 63.0D);
/*  91 */     log("Squids will spawn between Y: " + this.squidMinSpawnHeight + " and Y: " + this.squidMaxSpawnHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void playerBlockingDamageMultiplier() {
/*  97 */     this.playerBlockingDamageMultiplier = getFloat("player-blocking-damage-multiplier", 0.5F);
/*  98 */     log("Player blocking damage multiplier set to " + this.playerBlockingDamageMultiplier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void blockGrowthHeight() {
/* 105 */     this.cactusMaxHeight = getInt("max-growth-height.cactus", 3);
/* 106 */     this.reedMaxHeight = getInt("max-growth-height.reeds", 3);
/* 107 */     log("Max height for cactus growth " + this.cactusMaxHeight + ". Max height for reed growth " + this.reedMaxHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void invertedDaylightDetectors() {
/* 113 */     this.invertedDaylightDetectors = getBoolean("inverted-daylight-detectors", false);
/* 114 */     log("Inverted Redstone Lamps: " + this.invertedDaylightDetectors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fishingTickRange() {
/* 121 */     this.fishingMinTicks = getInt("fishing-time-range.MinimumTicks", 100);
/* 122 */     this.fishingMaxTicks = getInt("fishing-time-range.MaximumTicks", 900);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void exhaustionValues() {
/* 129 */     this.blockBreakExhaustion = getFloat("player-exhaustion.block-break", 0.025F);
/* 130 */     this.playerSwimmingExhaustion = getFloat("player-exhaustion.swimming", 0.015F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void despawnDistances() {
/* 137 */     this.softDespawnDistance = Integer.valueOf(getInt("despawn-ranges.soft", 32));
/* 138 */     this.hardDespawnDistance = Integer.valueOf(getInt("despawn-ranges.hard", 128));
/*     */     
/* 140 */     if (this.softDespawnDistance.intValue() > this.hardDespawnDistance.intValue())
/*     */     {
/* 142 */       this.softDespawnDistance = this.hardDespawnDistance;
/*     */     }
/*     */     
/* 145 */     log("Living Entity Despawn Ranges:  Soft: " + this.softDespawnDistance + " Hard: " + this.hardDespawnDistance);
/*     */     
/* 147 */     this.softDespawnDistance = Integer.valueOf(this.softDespawnDistance.intValue() * this.softDespawnDistance.intValue());
/* 148 */     this.hardDespawnDistance = Integer.valueOf(this.hardDespawnDistance.intValue() * this.hardDespawnDistance.intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void keepSpawnInMemory() {
/* 154 */     this.keepSpawnInMemory = getBoolean("keep-spawn-loaded", true);
/* 155 */     log("Keep spawn chunk loaded: " + this.keepSpawnInMemory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fallingBlockheightNerf() {
/* 162 */     this.fallingBlockHeightNerf = getDouble("tnt-entity-height-nerf", 0.0D);
/* 163 */     if (this.fallingBlockHeightNerf != 0.0D) {
/* 164 */       log("TNT/Falling Block Height Limit set to Y: " + this.fallingBlockHeightNerf);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void waterOverLavaFlowSpeed() {
/* 171 */     this.waterOverLavaFlowSpeed = getInt("water-over-lava-flow-speed", 5);
/* 172 */     log("Water over lava flow speed: " + this.waterOverLavaFlowSpeed);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeInvalidMobSpawnerTEs() {
/* 178 */     this.removeInvalidMobSpawnerTEs = getBoolean("remove-invalid-mob-spawner-tile-entities", true);
/* 179 */     log("Remove invalid mob spawner tile entities: " + this.removeInvalidMobSpawnerTEs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeUnloaded() {
/* 187 */     this.removeUnloadedEnderPearls = getBoolean("remove-unloaded.enderpearls", true);
/* 188 */     this.removeUnloadedTNTEntities = getBoolean("remove-unloaded.tnt-entities", true);
/* 189 */     this.removeUnloadedFallingBlocks = getBoolean("remove-unloaded.falling-blocks", true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void mechanicsChanges() {
/* 197 */     this.boatsDropBoats = getBoolean("game-mechanics.boats-drop-boats", false);
/* 198 */     this.lessPickyTorches = getBoolean("game-mechanics.less-picky-torch-placement", false);
/* 199 */     this.disablePlayerCrits = getBoolean("game-mechanics.disable-player-crits", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void tickNextTickListCap() {
/* 206 */     this.tickNextTickListCap = getInt("tick-next-tick-list-cap", 10000);
/* 207 */     this.tickNextTickListCapIgnoresRedstone = getBoolean("tick-next-tick-list-cap-ignores-redstone", false);
/* 208 */     log("WorldServer TickNextTickList cap set at " + this.tickNextTickListCap);
/* 209 */     log("WorldServer TickNextTickList cap always processes redstone: " + this.tickNextTickListCapIgnoresRedstone);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void useAsyncLighting() {
/* 215 */     this.useAsyncLighting = getBoolean("use-async-lighting", false);
/* 216 */     log("World async lighting: " + this.useAsyncLighting);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generatorSettings() {
/* 230 */     this.generateCanyon = getBoolean("generator-settings.canyon", true);
/* 231 */     this.generateCaves = getBoolean("generator-settings.caves", true);
/* 232 */     this.generateDungeon = getBoolean("generator-settings.dungeon", true);
/* 233 */     this.generateFortress = getBoolean("generator-settings.fortress", true);
/* 234 */     this.generateMineshaft = getBoolean("generator-settings.mineshaft", true);
/* 235 */     this.generateStronghold = getBoolean("generator-settings.stronghold", true);
/* 236 */     this.generateTemple = getBoolean("generator-settings.temple", true);
/* 237 */     this.generateVillage = getBoolean("generator-settings.village", true);
/* 238 */     this.generateFlatBedrock = getBoolean("generator-settings.flat-bedrock", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadUnloaded() {
/* 246 */     this.loadUnloadedEnderPearls = getBoolean("load-chunks.enderpearls", false);
/* 247 */     this.loadUnloadedTNTEntities = getBoolean("load-chunks.tnt-entities", false);
/* 248 */     this.loadUnloadedFallingBlocks = getBoolean("load-chunks.falling-blocks", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void fallingBlocksCollideWithSigns() {
/* 254 */     this.fallingBlocksCollideWithSigns = getBoolean("falling-blocks-collide-with-signs", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void disableEndCredits() {
/* 260 */     this.disableEndCredits = getBoolean("game-mechanics.disable-end-credits", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void optimizeExplosions() {
/* 266 */     this.optimizeExplosions = getBoolean("optimize-explosions", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fastDraining() {
/* 273 */     this.fastDrainLava = getBoolean("fast-drain.lava", false);
/* 274 */     this.fastDrainWater = getBoolean("fast-drain.water", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void lavaFlowSpeed() {
/* 281 */     this.lavaFlowSpeedNormal = getInt("lava-flow-speed.normal", 30);
/* 282 */     this.lavaFlowSpeedNether = getInt("lava-flow-speed.nether", 10);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void nethervoidTopDamage() {
/* 288 */     this.netherVoidTopDamage = getBoolean("nether-ceiling-void-damage", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void disableExplosionKnockback() {
/* 294 */     this.disableExplosionKnockback = getBoolean("disable-explosion-knockback", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void disableThunder() {
/* 300 */     this.disableThunder = getBoolean("disable-thunder", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void disableIceAndSnow() {
/* 306 */     this.disableIceAndSnow = getBoolean("disable-ice-and-snow", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void disableMoodSounds() {
/* 312 */     this.disableMoodSounds = getBoolean("disable-mood-sounds", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void mobSpawnerTickRate() {
/* 318 */     this.mobSpawnerTickRate = getInt("mob-spawner-tick-rate", 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void cacheChunkMaps() {
/* 324 */     this.cacheChunkMaps = getBoolean("cache-chunk-maps", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void containerUpdateTickRate() {
/* 330 */     this.containerUpdateTickRate = getInt("container-update-tick-rate", 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void tntExplosionVolume() {
/* 336 */     this.tntExplosionVolume = getFloat("tnt-explosion-volume", 4.0F);
/*     */   } }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\github\paperspigot\PaperSpigotWorldConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */