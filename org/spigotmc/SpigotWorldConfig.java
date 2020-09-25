/*     */ package org.spigotmc;
/*     */ public class SpigotWorldConfig { private final String worldName;
/*     */   private final YamlConfiguration config;
/*     */   private boolean verbose;
/*     */   public int chunksPerTick;
/*     */   public boolean clearChunksOnTick;
/*     */   public int cactusModifier;
/*     */   public int caneModifier;
/*     */   public int melonModifier;
/*     */   public int mushroomModifier;
/*     */   public int pumpkinModifier;
/*     */   public int saplingModifier;
/*     */   public int wheatModifier;
/*     */   public double itemMerge;
/*     */   public double expMerge;
/*     */   public int viewDistance;
/*     */   public byte mobSpawnRange;
/*     */   public int animalActivationRange;
/*     */   public int monsterActivationRange;
/*     */   public int miscActivationRange;
/*     */   public int playerTrackingRange;
/*     */   public int animalTrackingRange;
/*     */   public int monsterTrackingRange;
/*     */   public int miscTrackingRange;
/*     */   public int otherTrackingRange;
/*     */   public boolean altHopperTicking;
/*     */   public int hopperTransfer;
/*     */   public int hopperCheck;
/*     */   public int hopperAmount;
/*     */   public boolean randomLightUpdates;
/*     */   public boolean saveStructureInfo;
/*     */   public int itemDespawnRate;
/*     */   public int arrowDespawnRate;
/*     */   public boolean antiXray;
/*     */   public int engineMode;
/*     */   public List<Integer> hiddenBlocks;
/*     */   public List<Integer> replaceBlocks;
/*     */   public AntiXray antiXrayInstance;
/*     */   public boolean zombieAggressiveTowardsVillager;
/*     */   public boolean nerfSpawnerMobs;
/*     */   public boolean enableZombiePigmenPortalSpawns;
/*     */   public int maxBulkChunk;
/*     */   public int maxCollisionsPerEntity;
/*     */   public int dragonDeathSoundRadius;
/*     */   public int witherSpawnSoundRadius;
/*     */   public int villageSeed;
/*     */   public int largeFeatureSeed;
/*     */   public float walkExhaustion;
/*     */   public float sprintExhaustion;
/*     */   public float combatExhaustion;
/*     */   public float regenExhaustion;
/*     */   public int currentPrimedTnt;
/*     */   public int maxTntTicksPerTick;
/*     */   public int hangingTickFrequency;
/*     */   
/*     */   public void init() {
/*     */     this.verbose = getBoolean("verbose", true);
/*     */     log("-------- World Settings For [" + this.worldName + "] --------");
/*     */     SpigotConfig.readConfig(SpigotWorldConfig.class, this);
/*     */   }
/*     */   
/*     */   private void log(String s) {
/*     */     if (this.verbose)
/*     */       Bukkit.getLogger().info(s); 
/*     */   }
/*     */   
/*     */   private void set(String path, Object val) {
/*     */     this.config.set("world-settings.default." + path, val);
/*     */   }
/*     */   
/*     */   private boolean getBoolean(String path, boolean def) {
/*     */     this.config.addDefault("world-settings.default." + path, Boolean.valueOf(def));
/*     */     return this.config.getBoolean("world-settings." + this.worldName + "." + path, this.config.getBoolean("world-settings.default." + path));
/*     */   }
/*     */   
/*     */   private double getDouble(String path, double def) {
/*     */     this.config.addDefault("world-settings.default." + path, Double.valueOf(def));
/*     */     return this.config.getDouble("world-settings." + this.worldName + "." + path, this.config.getDouble("world-settings.default." + path));
/*     */   }
/*     */   
/*     */   private int getInt(String path, int def) {
/*     */     this.config.addDefault("world-settings.default." + path, Integer.valueOf(def));
/*     */     return this.config.getInt("world-settings." + this.worldName + "." + path, this.config.getInt("world-settings.default." + path));
/*     */   }
/*     */   
/*     */   private <T> List getList(String path, T def) {
/*     */     this.config.addDefault("world-settings.default." + path, def);
/*     */     return this.config.getList("world-settings." + this.worldName + "." + path, this.config.getList("world-settings.default." + path));
/*     */   }
/*     */   
/*     */   private String getString(String path, String def) {
/*     */     this.config.addDefault("world-settings.default." + path, def);
/*     */     return this.config.getString("world-settings." + this.worldName + "." + path, this.config.getString("world-settings.default." + path));
/*     */   }
/*     */   
/*     */   private void chunksPerTick() {
/*     */     this.chunksPerTick = getInt("chunks-per-tick", 650);
/*     */     log("Chunks to Grow per Tick: " + this.chunksPerTick);
/*     */     this.clearChunksOnTick = getBoolean("clear-tick-list", false);
/*     */     log("Clear tick list: " + this.clearChunksOnTick);
/*     */   }
/*     */   
/*     */   private int getAndValidateGrowth(String crop) {
/*     */     int modifier = getInt("growth." + crop.toLowerCase() + "-modifier", 100);
/*     */     if (modifier == 0) {
/*     */       log("Cannot set " + crop + " growth to zero, defaulting to 100");
/*     */       modifier = 100;
/*     */     } 
/*     */     log(crop + " Growth Modifier: " + modifier + "%");
/*     */     return modifier;
/*     */   }
/*     */   
/*     */   private void growthModifiers() {
/*     */     this.cactusModifier = getAndValidateGrowth("Cactus");
/*     */     this.caneModifier = getAndValidateGrowth("Cane");
/*     */     this.melonModifier = getAndValidateGrowth("Melon");
/*     */     this.mushroomModifier = getAndValidateGrowth("Mushroom");
/*     */     this.pumpkinModifier = getAndValidateGrowth("Pumpkin");
/*     */     this.saplingModifier = getAndValidateGrowth("Sapling");
/*     */     this.wheatModifier = getAndValidateGrowth("Wheat");
/*     */   }
/*     */   
/*     */   private void itemMerge() {
/*     */     this.itemMerge = getDouble("merge-radius.item", 2.5D);
/*     */     log("Item Merge Radius: " + this.itemMerge);
/*     */   }
/*     */   
/*     */   private void expMerge() {
/*     */     this.expMerge = getDouble("merge-radius.exp", 3.0D);
/*     */     log("Experience Merge Radius: " + this.expMerge);
/*     */   }
/*     */   
/*     */   private void viewDistance() {
/*     */     this.viewDistance = getInt("view-distance", Bukkit.getViewDistance());
/*     */     log("View Distance: " + this.viewDistance);
/*     */   }
/*     */   
/*     */   private void mobSpawnRange() {
/*     */     this.mobSpawnRange = (byte)getInt("mob-spawn-range", 4);
/*     */     log("Mob Spawn Range: " + this.mobSpawnRange);
/*     */   }
/*     */   
/* 143 */   public SpigotWorldConfig(String worldName) { this.animalActivationRange = 32;
/* 144 */     this.monsterActivationRange = 32;
/* 145 */     this.miscActivationRange = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     this.playerTrackingRange = 48;
/* 155 */     this.animalTrackingRange = 48;
/* 156 */     this.monsterTrackingRange = 48;
/* 157 */     this.miscTrackingRange = 32;
/* 158 */     this.otherTrackingRange = 64;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 342 */     this.currentPrimedTnt = 0; this.worldName = worldName; this.config = SpigotConfig.config; init(); }
/*     */   private void activationRange() { this.animalActivationRange = getInt("entity-activation-range.animals", this.animalActivationRange); this.monsterActivationRange = getInt("entity-activation-range.monsters", this.monsterActivationRange);
/*     */     this.miscActivationRange = getInt("entity-activation-range.misc", this.miscActivationRange);
/* 345 */     log("Entity Activation Range: An " + this.animalActivationRange + " / Mo " + this.monsterActivationRange + " / Mi " + this.miscActivationRange); } private void maxTntPerTick() { if (SpigotConfig.version < 7)
/*     */     {
/* 347 */       set("max-tnt-per-tick", Integer.valueOf(100));
/*     */     }
/* 349 */     this.maxTntTicksPerTick = getInt("max-tnt-per-tick", 100);
/* 350 */     log("Max TNT Explosions: " + this.maxTntTicksPerTick); } private void trackingRange() { this.playerTrackingRange = getInt("entity-tracking-range.players", this.playerTrackingRange);
/*     */     this.animalTrackingRange = getInt("entity-tracking-range.animals", this.animalTrackingRange);
/*     */     this.monsterTrackingRange = getInt("entity-tracking-range.monsters", this.monsterTrackingRange);
/*     */     this.miscTrackingRange = getInt("entity-tracking-range.misc", this.miscTrackingRange);
/*     */     this.otherTrackingRange = getInt("entity-tracking-range.other", this.otherTrackingRange);
/*     */     log("Entity Tracking Range: Pl " + this.playerTrackingRange + " / An " + this.animalTrackingRange + " / Mo " + this.monsterTrackingRange + " / Mi " + this.miscTrackingRange + " / Other " + this.otherTrackingRange); }
/* 356 */   private void hangingTickFrequency() { this.hangingTickFrequency = getInt("hanging-tick-frequency", 100); }
/*     */ 
/*     */   
/*     */   private void hoppers() {
/*     */     boolean prev = this.altHopperTicking;
/*     */     this.altHopperTicking = getBoolean("hopper-alt-ticking", false);
/*     */     if (prev != this.altHopperTicking) {
/*     */       World world = (World)Bukkit.getWorld(this.worldName);
/*     */       if (world != null)
/*     */         if (this.altHopperTicking) {
/*     */           for (Object o : world.tileEntityList) {
/*     */             if (o instanceof TileEntityHopper)
/*     */               ((TileEntityHopper)o).convertToScheduling(); 
/*     */           } 
/*     */         } else {
/*     */           for (Object o : world.tileEntityList) {
/*     */             if (o instanceof TileEntityHopper)
/*     */               ((TileEntityHopper)o).convertToPolling(); 
/*     */           } 
/*     */         }  
/*     */     } 
/*     */     this.hopperTransfer = getInt("ticks-per.hopper-transfer", 8);
/*     */     this.hopperCheck = getInt("ticks-per.hopper-check", this.hopperTransfer);
/*     */     this.hopperAmount = getInt("hopper-amount", 1);
/*     */     log("Alternative Hopper Ticking: " + this.altHopperTicking);
/*     */     log("Hopper Transfer: " + this.hopperTransfer + " Hopper Check: " + this.hopperCheck + " Hopper Amount: " + this.hopperAmount);
/*     */   }
/*     */   
/*     */   private void lightUpdates() {
/*     */     this.randomLightUpdates = getBoolean("random-light-updates", false);
/*     */     log("Random Lighting Updates: " + this.randomLightUpdates);
/*     */   }
/*     */   
/*     */   private void structureInfo() {
/*     */     this.saveStructureInfo = getBoolean("save-structure-info", true);
/*     */     log("Structure Info Saving: " + this.saveStructureInfo);
/*     */     if (!this.saveStructureInfo) {
/*     */       log("*** WARNING *** You have selected to NOT save structure info. This may cause structures such as fortresses to not spawn mobs when updating to 1.7!");
/*     */       log("*** WARNING *** Please use this option with caution, SpigotMC is not responsible for any issues this option may cause in the future!");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void itemDespawnRate() {
/*     */     this.itemDespawnRate = getInt("item-despawn-rate", 6000);
/*     */     log("Item Despawn Rate: " + this.itemDespawnRate);
/*     */   }
/*     */   
/*     */   private void arrowDespawnRate() {
/*     */     this.arrowDespawnRate = getInt("arrow-despawn-rate", 1200);
/*     */     log("Arrow Despawn Rate: " + this.arrowDespawnRate);
/*     */   }
/*     */   
/*     */   private void antiXray() {
/*     */     this.antiXray = getBoolean("anti-xray.enabled", true);
/*     */     log("Anti X-Ray: " + this.antiXray);
/*     */     this.engineMode = getInt("anti-xray.engine-mode", 1);
/*     */     log("\tEngine Mode: " + this.engineMode);
/*     */     if (SpigotConfig.version < 5)
/*     */       set("anti-xray.blocks", null); 
/*     */     this.hiddenBlocks = getList("anti-xray.hide-blocks", Arrays.asList(new Integer[] { 
/*     */             Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(21), Integer.valueOf(48), Integer.valueOf(49), Integer.valueOf(54), Integer.valueOf(56), Integer.valueOf(73), Integer.valueOf(74), 
/*     */             Integer.valueOf(82), Integer.valueOf(129), Integer.valueOf(130) }));
/*     */     log("\tHidden Blocks: " + this.hiddenBlocks);
/*     */     this.replaceBlocks = getList("anti-xray.replace-blocks", Arrays.asList(new Integer[] { Integer.valueOf(1), Integer.valueOf(5) }));
/*     */     log("\tReplace Blocks: " + this.replaceBlocks);
/*     */     this.antiXrayInstance = new AntiXray(this);
/*     */   }
/*     */   
/*     */   private void zombieAggressiveTowardsVillager() {
/*     */     this.zombieAggressiveTowardsVillager = getBoolean("zombie-aggressive-towards-villager", true);
/*     */     log("Zombie Aggressive Towards Villager: " + this.zombieAggressiveTowardsVillager);
/*     */   }
/*     */   
/*     */   private void nerfSpawnerMobs() {
/*     */     this.nerfSpawnerMobs = getBoolean("nerf-spawner-mobs", false);
/*     */     log("Nerfing mobs spawned from spawners: " + this.nerfSpawnerMobs);
/*     */   }
/*     */   
/*     */   private void enableZombiePigmenPortalSpawns() {
/*     */     this.enableZombiePigmenPortalSpawns = getBoolean("enable-zombie-pigmen-portal-spawns", true);
/*     */     log("Allow Zombie Pigmen to spawn from portal blocks: " + this.enableZombiePigmenPortalSpawns);
/*     */   }
/*     */   
/*     */   private void bulkChunkCount() {
/*     */     this.maxBulkChunk = getInt("max-bulk-chunks", 5);
/*     */     log("Sending up to " + this.maxBulkChunk + " chunks per packet");
/*     */   }
/*     */   
/*     */   private void maxEntityCollision() {
/*     */     this.maxCollisionsPerEntity = getInt("max-entity-collisions", 8);
/*     */     log("Max Entity Collisions: " + this.maxCollisionsPerEntity);
/*     */   }
/*     */   
/*     */   private void keepDragonDeathPerWorld() {
/*     */     this.dragonDeathSoundRadius = getInt("dragon-death-sound-radius", 0);
/*     */   }
/*     */   
/*     */   private void witherSpawnSoundRadius() {
/*     */     this.witherSpawnSoundRadius = getInt("wither-spawn-sound-radius", 0);
/*     */   }
/*     */   
/*     */   private void initWorldGenSeeds() {
/*     */     this.villageSeed = getInt("seed-village", 10387312);
/*     */     this.largeFeatureSeed = getInt("seed-feature", 14357617);
/*     */     log("Custom Map Seeds:  Village: " + this.villageSeed + " Feature: " + this.largeFeatureSeed);
/*     */   }
/*     */   
/*     */   private void initHunger() {
/*     */     this.walkExhaustion = (float)getDouble("hunger.walk-exhaustion", 0.2D);
/*     */     this.sprintExhaustion = (float)getDouble("hunger.sprint-exhaustion", 0.8D);
/*     */     this.combatExhaustion = (float)getDouble("hunger.combat-exhaustion", 0.3D);
/*     */     this.regenExhaustion = (float)getDouble("hunger.regen-exhaustion", 3.0D);
/*     */   } }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\SpigotWorldConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */