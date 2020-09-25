/*      */ package org.bukkit.craftbukkit.v1_7_R4;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import net.minecraft.server.v1_7_R4.Block;
/*      */ import net.minecraft.server.v1_7_R4.Chunk;
/*      */ import net.minecraft.server.v1_7_R4.ChunkCoordinates;
/*      */ import net.minecraft.server.v1_7_R4.ChunkProviderServer;
/*      */ import net.minecraft.server.v1_7_R4.Entity;
/*      */ import net.minecraft.server.v1_7_R4.EntityArrow;
/*      */ import net.minecraft.server.v1_7_R4.EntityBat;
/*      */ import net.minecraft.server.v1_7_R4.EntityFallingBlock;
/*      */ import net.minecraft.server.v1_7_R4.EntityFireworks;
/*      */ import net.minecraft.server.v1_7_R4.EntityItem;
/*      */ import net.minecraft.server.v1_7_R4.EntityLargeFireball;
/*      */ import net.minecraft.server.v1_7_R4.EntityLeash;
/*      */ import net.minecraft.server.v1_7_R4.EntityLightning;
/*      */ import net.minecraft.server.v1_7_R4.IChunkProvider;
/*      */ import net.minecraft.server.v1_7_R4.PacketPlayOutWorldEvent;
/*      */ import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;
/*      */ import net.minecraft.server.v1_7_R4.World;
/*      */ import net.minecraft.server.v1_7_R4.WorldGenForest;
/*      */ import net.minecraft.server.v1_7_R4.WorldGenForestTree;
/*      */ import net.minecraft.server.v1_7_R4.WorldGenHugeMushroom;
/*      */ import net.minecraft.server.v1_7_R4.WorldGenJungleTree;
/*      */ import net.minecraft.server.v1_7_R4.WorldGenSwampTree;
/*      */ import net.minecraft.server.v1_7_R4.WorldGenTaiga1;
/*      */ import net.minecraft.server.v1_7_R4.WorldGenTaiga2;
/*      */ import net.minecraft.server.v1_7_R4.WorldGenTrees;
/*      */ import net.minecraft.server.v1_7_R4.WorldServer;
/*      */ import org.apache.commons.lang.Validate;
/*      */ import org.bukkit.Chunk;
/*      */ import org.bukkit.Effect;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.TreeType;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.block.Block;
/*      */ import org.bukkit.block.BlockFace;
/*      */ import org.bukkit.block.BlockState;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLightningStrike;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
/*      */ import org.bukkit.entity.Entity;
/*      */ import org.bukkit.entity.EntityType;
/*      */ import org.bukkit.entity.FallingBlock;
/*      */ import org.bukkit.entity.LightningStrike;
/*      */ import org.bukkit.entity.LivingEntity;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*      */ import org.bukkit.event.weather.ThunderChangeEvent;
/*      */ import org.bukkit.event.weather.WeatherChangeEvent;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.material.MaterialData;
/*      */ import org.bukkit.plugin.Plugin;
/*      */ import org.bukkit.util.Vector;
/*      */ 
/*      */ public class CraftWorld implements World {
/*      */   public static final int CUSTOM_DIMENSION_OFFSET = 10;
/*   60 */   private final CraftServer server = (CraftServer)Bukkit.getServer(); private final WorldServer world; private World.Environment environment;
/*      */   private final ChunkGenerator generator;
/*   62 */   private final List<BlockPopulator> populators = new ArrayList<BlockPopulator>();
/*   63 */   private final BlockMetadataStore blockMetadata = new BlockMetadataStore(this);
/*   64 */   private int monsterSpawn = -1;
/*   65 */   private int animalSpawn = -1;
/*   66 */   private int waterAnimalSpawn = -1;
/*   67 */   private int ambientSpawn = -1;
/*   68 */   private int chunkLoadCount = 0;
/*      */   
/*      */   private int chunkGCTickCount;
/*   71 */   private static final Random rand = new Random();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final World.Spigot spigot;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Block getBlockAt(int x, int y, int z) {
/*   85 */     return getChunkAt(x >> 4, z >> 4).getBlock(x & 0xF, y & 0xFF, z & 0xF);
/*      */   }
/*      */   
/*      */   public int getBlockTypeIdAt(int x, int y, int z) {
/*   89 */     return this.world.getTypeId(x, y, z);
/*      */   }
/*      */   
/*      */   public int getHighestBlockYAt(int x, int z) {
/*   93 */     if (!isChunkLoaded(x >> 4, z >> 4)) {
/*   94 */       loadChunk(x >> 4, z >> 4);
/*      */     }
/*      */     
/*   97 */     return this.world.getHighestBlockYAt(x, z);
/*      */   }
/*      */   
/*      */   public Location getSpawnLocation() {
/*  101 */     ChunkCoordinates spawn = this.world.getSpawn();
/*  102 */     return new Location(this, spawn.x, spawn.y, spawn.z);
/*      */   }
/*      */   
/*      */   public boolean setSpawnLocation(int x, int y, int z) {
/*      */     try {
/*  107 */       Location previousLocation = getSpawnLocation();
/*  108 */       this.world.worldData.setSpawn(x, y, z);
/*      */ 
/*      */       
/*  111 */       SpawnChangeEvent event = new SpawnChangeEvent(this, previousLocation);
/*  112 */       this.server.getPluginManager().callEvent((Event)event);
/*      */       
/*  114 */       return true;
/*  115 */     } catch (Exception e) {
/*  116 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void getChunkAtAsync(final int x, final int z, final World.ChunkLoadCallback callback) {
/*  122 */     final ChunkProviderServer cps = this.world.chunkProviderServer;
/*  123 */     cps.getChunkAt(x, z, new Runnable()
/*      */         {
/*      */           public void run() {
/*  126 */             callback.onLoad((cps.getChunkAt(x, z)).bukkitChunk);
/*      */           }
/*      */         });
/*      */   }
/*      */   public void getChunkAtAsync(Block block, World.ChunkLoadCallback callback) {
/*  131 */     getChunkAtAsync(block.getX() >> 4, block.getZ() >> 4, callback);
/*      */   }
/*      */   public void getChunkAtAsync(Location location, World.ChunkLoadCallback callback) {
/*  134 */     getChunkAtAsync(location.getBlockX() >> 4, location.getBlockZ() >> 4, callback);
/*      */   }
/*      */ 
/*      */   
/*      */   public Chunk getChunkAt(int x, int z) {
/*  139 */     return (this.world.chunkProviderServer.getChunkAt(x, z)).bukkitChunk;
/*      */   }
/*      */   
/*      */   public Chunk getChunkAt(Block block) {
/*  143 */     return getChunkAt(block.getX() >> 4, block.getZ() >> 4);
/*      */   }
/*      */   
/*      */   public boolean isChunkLoaded(int x, int z) {
/*  147 */     return this.world.chunkProviderServer.isChunkLoaded(x, z);
/*      */   }
/*      */   
/*      */   public Chunk[] getLoadedChunks() {
/*  151 */     Object[] chunks = this.world.chunkProviderServer.chunks.values().toArray();
/*  152 */     CraftChunk[] arrayOfCraftChunk = new CraftChunk[chunks.length];
/*      */     
/*  154 */     for (int i = 0; i < chunks.length; i++) {
/*  155 */       Chunk chunk = (Chunk)chunks[i];
/*  156 */       arrayOfCraftChunk[i] = (CraftChunk)chunk.bukkitChunk;
/*      */     } 
/*      */     
/*  159 */     return (Chunk[])arrayOfCraftChunk;
/*      */   }
/*      */   
/*      */   public void loadChunk(int x, int z) {
/*  163 */     loadChunk(x, z, true);
/*      */   }
/*      */   
/*      */   public boolean unloadChunk(Chunk chunk) {
/*  167 */     return unloadChunk(chunk.getX(), chunk.getZ());
/*      */   }
/*      */   
/*      */   public boolean unloadChunk(int x, int z) {
/*  171 */     return unloadChunk(x, z, true);
/*      */   }
/*      */   
/*      */   public boolean unloadChunk(int x, int z, boolean save) {
/*  175 */     return unloadChunk(x, z, save, false);
/*      */   }
/*      */   
/*      */   public boolean unloadChunkRequest(int x, int z) {
/*  179 */     return unloadChunkRequest(x, z, true);
/*      */   }
/*      */   
/*      */   public boolean unloadChunkRequest(int x, int z, boolean safe) {
/*  183 */     AsyncCatcher.catchOp("chunk unload");
/*  184 */     if (safe && isChunkInUse(x, z)) {
/*  185 */       return false;
/*      */     }
/*      */     
/*  188 */     this.world.chunkProviderServer.queueUnload(x, z);
/*      */     
/*  190 */     return true;
/*      */   }
/*      */   
/*      */   public boolean unloadChunk(int x, int z, boolean save, boolean safe) {
/*  194 */     AsyncCatcher.catchOp("chunk unload");
/*  195 */     if (safe && isChunkInUse(x, z)) {
/*  196 */       return false;
/*      */     }
/*      */     
/*  199 */     Chunk chunk = this.world.chunkProviderServer.getOrCreateChunk(x, z);
/*  200 */     if (chunk.mustSave) {
/*  201 */       save = true;
/*      */     }
/*      */     
/*  204 */     chunk.removeEntities();
/*      */     
/*  206 */     if (save && !(chunk instanceof net.minecraft.server.v1_7_R4.EmptyChunk)) {
/*  207 */       this.world.chunkProviderServer.saveChunk(chunk);
/*  208 */       this.world.chunkProviderServer.saveChunkNOP(chunk);
/*      */     } 
/*      */     
/*  211 */     this.world.chunkProviderServer.unloadQueue.remove(x, z);
/*  212 */     this.world.chunkProviderServer.chunks.remove(LongHash.toLong(x, z));
/*      */     
/*  214 */     return true;
/*      */   }
/*      */   
/*      */   public boolean regenerateChunk(int x, int z) {
/*  218 */     unloadChunk(x, z, false, false);
/*      */     
/*  220 */     this.world.chunkProviderServer.unloadQueue.remove(x, z);
/*      */     
/*  222 */     Chunk chunk = null;
/*      */     
/*  224 */     if (this.world.chunkProviderServer.chunkProvider == null) {
/*  225 */       chunk = this.world.chunkProviderServer.emptyChunk;
/*      */     } else {
/*  227 */       chunk = this.world.chunkProviderServer.chunkProvider.getOrCreateChunk(x, z);
/*      */     } 
/*      */     
/*  230 */     chunkLoadPostProcess(chunk, x, z);
/*      */     
/*  232 */     refreshChunk(x, z);
/*      */     
/*  234 */     return (chunk != null);
/*      */   }
/*      */   
/*      */   public boolean refreshChunk(int x, int z) {
/*  238 */     if (!isChunkLoaded(x, z)) {
/*  239 */       return false;
/*      */     }
/*      */     
/*  242 */     int px = x << 4;
/*  243 */     int pz = z << 4;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  248 */     int height = getMaxHeight() / 16;
/*  249 */     for (int idx = 0; idx < 64; idx++) {
/*  250 */       this.world.notify(px + idx / height, idx % height * 16, pz);
/*      */     }
/*  252 */     this.world.notify(px + 15, height * 16 - 1, pz + 15);
/*      */     
/*  254 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isChunkInUse(int x, int z) {
/*  258 */     return this.world.getPlayerChunkMap().isChunkInUse(x, z);
/*      */   }
/*      */   
/*      */   public boolean loadChunk(int x, int z, boolean generate) {
/*  262 */     AsyncCatcher.catchOp("chunk load");
/*  263 */     this.chunkLoadCount++;
/*  264 */     if (generate)
/*      */     {
/*  266 */       return (this.world.chunkProviderServer.getChunkAt(x, z) != null);
/*      */     }
/*      */     
/*  269 */     this.world.chunkProviderServer.unloadQueue.remove(x, z);
/*  270 */     Chunk chunk = (Chunk)this.world.chunkProviderServer.chunks.get(LongHash.toLong(x, z));
/*      */     
/*  272 */     if (chunk == null) {
/*  273 */       this.world.timings.syncChunkLoadTimer.startTiming();
/*  274 */       chunk = this.world.chunkProviderServer.loadChunk(x, z);
/*      */       
/*  276 */       chunkLoadPostProcess(chunk, x, z);
/*  277 */       this.world.timings.syncChunkLoadTimer.stopTiming();
/*      */     } 
/*  279 */     return (chunk != null);
/*      */   }
/*      */   
/*      */   private void chunkLoadPostProcess(Chunk chunk, int x, int z) {
/*  283 */     if (chunk != null) {
/*  284 */       this.world.chunkProviderServer.chunks.put(LongHash.toLong(x, z), chunk);
/*      */       
/*  286 */       chunk.addEntities();
/*      */       
/*  288 */       if (!chunk.done && this.world.chunkProviderServer.isChunkLoaded(x + 1, z + 1) && this.world.chunkProviderServer.isChunkLoaded(x, z + 1) && this.world.chunkProviderServer.isChunkLoaded(x + 1, z)) {
/*  289 */         this.world.chunkProviderServer.getChunkAt((IChunkProvider)this.world.chunkProviderServer, x, z);
/*      */       }
/*      */       
/*  292 */       if (this.world.chunkProviderServer.isChunkLoaded(x - 1, z) && !(this.world.chunkProviderServer.getOrCreateChunk(x - 1, z)).done && this.world.chunkProviderServer.isChunkLoaded(x - 1, z + 1) && this.world.chunkProviderServer.isChunkLoaded(x, z + 1) && this.world.chunkProviderServer.isChunkLoaded(x - 1, z)) {
/*  293 */         this.world.chunkProviderServer.getChunkAt((IChunkProvider)this.world.chunkProviderServer, x - 1, z);
/*      */       }
/*      */       
/*  296 */       if (this.world.chunkProviderServer.isChunkLoaded(x, z - 1) && !(this.world.chunkProviderServer.getOrCreateChunk(x, z - 1)).done && this.world.chunkProviderServer.isChunkLoaded(x + 1, z - 1) && this.world.chunkProviderServer.isChunkLoaded(x, z - 1) && this.world.chunkProviderServer.isChunkLoaded(x + 1, z)) {
/*  297 */         this.world.chunkProviderServer.getChunkAt((IChunkProvider)this.world.chunkProviderServer, x, z - 1);
/*      */       }
/*      */       
/*  300 */       if (this.world.chunkProviderServer.isChunkLoaded(x - 1, z - 1) && !(this.world.chunkProviderServer.getOrCreateChunk(x - 1, z - 1)).done && this.world.chunkProviderServer.isChunkLoaded(x - 1, z - 1) && this.world.chunkProviderServer.isChunkLoaded(x, z - 1) && this.world.chunkProviderServer.isChunkLoaded(x - 1, z)) {
/*  301 */         this.world.chunkProviderServer.getChunkAt((IChunkProvider)this.world.chunkProviderServer, x - 1, z - 1);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isChunkLoaded(Chunk chunk) {
/*  307 */     return isChunkLoaded(chunk.getX(), chunk.getZ());
/*      */   }
/*      */   
/*      */   public void loadChunk(Chunk chunk) {
/*  311 */     loadChunk(chunk.getX(), chunk.getZ());
/*  312 */     (((CraftChunk)getChunkAt(chunk.getX(), chunk.getZ())).getHandle()).bukkitChunk = chunk;
/*      */   }
/*      */   
/*      */   public WorldServer getHandle() {
/*  316 */     return this.world;
/*      */   }
/*      */   
/*      */   public Item dropItem(Location loc, ItemStack item) {
/*  320 */     Validate.notNull(item, "Cannot drop a Null item.");
/*  321 */     Validate.isTrue((item.getTypeId() != 0), "Cannot drop AIR.");
/*  322 */     EntityItem entity = new EntityItem((World)this.world, loc.getX(), loc.getY(), loc.getZ(), CraftItemStack.asNMSCopy(item));
/*  323 */     entity.pickupDelay = 10;
/*  324 */     this.world.addEntity((Entity)entity);
/*      */ 
/*      */     
/*  327 */     return (Item)new CraftItem(this.world.getServer(), entity);
/*      */   }
/*      */   
/*      */   public Item dropItemNaturally(Location loc, ItemStack item) {
/*  331 */     double xs = (this.world.random.nextFloat() * 0.7F) + 0.15000000596046448D;
/*  332 */     double ys = (this.world.random.nextFloat() * 0.7F) + 0.15000000596046448D;
/*  333 */     double zs = (this.world.random.nextFloat() * 0.7F) + 0.15000000596046448D;
/*  334 */     loc = loc.clone();
/*  335 */     loc.setX(loc.getX() + xs);
/*  336 */     loc.setY(loc.getY() + ys);
/*  337 */     loc.setZ(loc.getZ() + zs);
/*  338 */     return dropItem(loc, item);
/*      */   }
/*      */   
/*      */   public Arrow spawnArrow(Location loc, Vector velocity, float speed, float spread) {
/*  342 */     Validate.notNull(loc, "Can not spawn arrow with a null location");
/*  343 */     Validate.notNull(velocity, "Can not spawn arrow with a null velocity");
/*      */     
/*  345 */     EntityArrow arrow = new EntityArrow((World)this.world);
/*  346 */     arrow.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
/*  347 */     arrow.shoot(velocity.getX(), velocity.getY(), velocity.getZ(), speed, spread);
/*  348 */     this.world.addEntity((Entity)arrow);
/*  349 */     return (Arrow)arrow.getBukkitEntity();
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public LivingEntity spawnCreature(Location loc, CreatureType creatureType) {
/*  354 */     return spawnCreature(loc, creatureType.toEntityType());
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public LivingEntity spawnCreature(Location loc, EntityType creatureType) {
/*  359 */     Validate.isTrue(creatureType.isAlive(), "EntityType not instance of LivingEntity");
/*  360 */     return (LivingEntity)spawnEntity(loc, creatureType);
/*      */   }
/*      */   
/*      */   public Entity spawnEntity(Location loc, EntityType entityType) {
/*  364 */     return spawn(loc, entityType.getEntityClass());
/*      */   }
/*      */   
/*      */   public LightningStrike strikeLightning(Location loc) {
/*  368 */     EntityLightning lightning = new EntityLightning((World)this.world, loc.getX(), loc.getY(), loc.getZ());
/*  369 */     this.world.strikeLightning((Entity)lightning);
/*  370 */     return (LightningStrike)new CraftLightningStrike(this.server, lightning);
/*      */   }
/*      */   
/*      */   public LightningStrike strikeLightningEffect(Location loc) {
/*  374 */     EntityLightning lightning = new EntityLightning((World)this.world, loc.getX(), loc.getY(), loc.getZ(), true);
/*  375 */     this.world.strikeLightning((Entity)lightning);
/*  376 */     return (LightningStrike)new CraftLightningStrike(this.server, lightning); } public boolean generateTree(Location loc, TreeType type) { WorldGenBigTree worldGenBigTree; WorldGenForest worldGenForest2; WorldGenTaiga2 worldGenTaiga2; WorldGenTaiga1 worldGenTaiga1; WorldGenJungleTree worldGenJungleTree; WorldGenTrees worldGenTrees2; WorldGenGroundBush worldGenGroundBush; WorldGenHugeMushroom worldGenHugeMushroom; WorldGenSwampTree worldGenSwampTree;
/*      */     WorldGenAcaciaTree worldGenAcaciaTree;
/*      */     WorldGenForestTree worldGenForestTree;
/*      */     WorldGenMegaTree worldGenMegaTree;
/*      */     WorldGenForest worldGenForest1;
/*  381 */     switch (type)
/*      */     { case SOUTH:
/*  383 */         worldGenBigTree = new WorldGenBigTree(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  433 */         return worldGenBigTree.generate((World)this.world, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());case WEST: worldGenForest2 = new WorldGenForest(true, false); return worldGenForest2.generate((World)this.world, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());case NORTH: worldGenTaiga2 = new WorldGenTaiga2(true); return worldGenTaiga2.generate((World)this.world, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());case EAST: worldGenTaiga1 = new WorldGenTaiga1(); return worldGenTaiga1.generate((World)this.world, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());case null: worldGenJungleTree = new WorldGenJungleTree(true, 10, 20, 3, 3); return worldGenJungleTree.generate((World)this.world, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());case null: worldGenTrees2 = new WorldGenTrees(true, 4 + rand.nextInt(7), 3, 3, false); return worldGenTrees2.generate((World)this.world, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());case null: worldGenTrees2 = new WorldGenTrees(true, 4 + rand.nextInt(7), 3, 3, true); return worldGenTrees2.generate((World)this.world, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());case null: worldGenGroundBush = new WorldGenGroundBush(3, 0); return worldGenGroundBush.generate((World)this.world, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());case null: worldGenHugeMushroom = new WorldGenHugeMushroom(1); return worldGenHugeMushroom.generate((World)this.world, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());case null: worldGenHugeMushroom = new WorldGenHugeMushroom(0); return worldGenHugeMushroom.generate((World)this.world, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());case null: worldGenSwampTree = new WorldGenSwampTree(); return worldGenSwampTree.generate((World)this.world, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());case null: worldGenAcaciaTree = new WorldGenAcaciaTree(true); return worldGenAcaciaTree.generate((World)this.world, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());case null: worldGenForestTree = new WorldGenForestTree(true); return worldGenForestTree.generate((World)this.world, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());case null: worldGenMegaTree = new WorldGenMegaTree(false, rand.nextBoolean()); return worldGenMegaTree.generate((World)this.world, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());case null: worldGenForest1 = new WorldGenForest(true, true); return worldGenForest1.generate((World)this.world, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()); }  WorldGenTrees worldGenTrees1 = new WorldGenTrees(true); return worldGenTrees1.generate((World)this.world, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()); }
/*      */ 
/*      */   
/*      */   public boolean generateTree(Location loc, TreeType type, BlockChangeDelegate delegate) {
/*  437 */     this.world.captureTreeGeneration = true;
/*  438 */     this.world.captureBlockStates = true;
/*  439 */     boolean grownTree = generateTree(loc, type);
/*  440 */     this.world.captureBlockStates = false;
/*  441 */     this.world.captureTreeGeneration = false;
/*  442 */     if (grownTree) {
/*  443 */       for (BlockState blockstate : this.world.capturedBlockStates) {
/*  444 */         int x = blockstate.getX();
/*  445 */         int y = blockstate.getY();
/*  446 */         int z = blockstate.getZ();
/*  447 */         Block oldBlock = this.world.getType(x, y, z);
/*  448 */         int typeId = blockstate.getTypeId();
/*  449 */         int data = blockstate.getRawData();
/*  450 */         int flag = ((CraftBlockState)blockstate).getFlag();
/*  451 */         delegate.setTypeIdAndData(x, y, z, typeId, data);
/*  452 */         Block newBlock = this.world.getType(x, y, z);
/*  453 */         this.world.notifyAndUpdatePhysics(x, y, z, null, oldBlock, newBlock, flag);
/*      */       } 
/*  455 */       this.world.capturedBlockStates.clear();
/*  456 */       return true;
/*      */     } 
/*  458 */     this.world.capturedBlockStates.clear();
/*  459 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public TileEntity getTileEntityAt(int x, int y, int z) {
/*  464 */     return this.world.getTileEntity(x, y, z);
/*      */   }
/*      */   
/*      */   public String getName() {
/*  468 */     return this.world.worldData.getName();
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public long getId() {
/*  473 */     return this.world.worldData.getSeed();
/*      */   }
/*      */   
/*      */   public UUID getUID() {
/*  477 */     return this.world.getDataManager().getUUID();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  482 */     return "CraftWorld{name=" + getName() + '}';
/*      */   }
/*      */   
/*      */   public long getTime() {
/*  486 */     long time = getFullTime() % 24000L;
/*  487 */     if (time < 0L) time += 24000L; 
/*  488 */     return time;
/*      */   }
/*      */   
/*      */   public void setTime(long time) {
/*  492 */     long margin = (time - getFullTime()) % 24000L;
/*  493 */     if (margin < 0L) margin += 24000L; 
/*  494 */     setFullTime(getFullTime() + margin);
/*      */   }
/*      */   
/*      */   public long getFullTime() {
/*  498 */     return this.world.getDayTime();
/*      */   }
/*      */   
/*      */   public void setFullTime(long time) {
/*  502 */     this.world.setDayTime(time);
/*      */ 
/*      */     
/*  505 */     for (Player p : getPlayers()) {
/*  506 */       CraftPlayer cp = (CraftPlayer)p;
/*  507 */       if ((cp.getHandle()).playerConnection == null)
/*      */         continue; 
/*  509 */       (cp.getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutUpdateTime((cp.getHandle()).world.getTime(), cp.getHandle().getPlayerTime(), (cp.getHandle()).world.getGameRules().getBoolean("doDaylightCycle")));
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean createExplosion(double x, double y, double z, float power) {
/*  514 */     return createExplosion(x, y, z, power, false, true);
/*      */   }
/*      */   
/*      */   public boolean createExplosion(double x, double y, double z, float power, boolean setFire) {
/*  518 */     return createExplosion(x, y, z, power, setFire, true);
/*      */   }
/*      */   
/*      */   public boolean createExplosion(double x, double y, double z, float power, boolean setFire, boolean breakBlocks) {
/*  522 */     return !(this.world.createExplosion(null, x, y, z, power, setFire, breakBlocks)).wasCanceled;
/*      */   }
/*      */   
/*      */   public boolean createExplosion(Location loc, float power) {
/*  526 */     return createExplosion(loc, power, false);
/*      */   }
/*      */   
/*      */   public boolean createExplosion(Location loc, float power, boolean setFire) {
/*  530 */     return createExplosion(loc.getX(), loc.getY(), loc.getZ(), power, setFire);
/*      */   }
/*      */   
/*      */   public World.Environment getEnvironment() {
/*  534 */     return this.environment;
/*      */   }
/*      */   
/*      */   public void setEnvironment(World.Environment env) {
/*  538 */     if (this.environment != env) {
/*  539 */       this.environment = env;
/*  540 */       this.world.worldProvider = WorldProvider.byDimension(this.environment.getId());
/*      */     } 
/*      */   }
/*      */   
/*      */   public Block getBlockAt(Location location) {
/*  545 */     return getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
/*      */   }
/*      */   
/*      */   public int getBlockTypeIdAt(Location location) {
/*  549 */     return getBlockTypeIdAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
/*      */   }
/*      */   
/*      */   public int getHighestBlockYAt(Location location) {
/*  553 */     return getHighestBlockYAt(location.getBlockX(), location.getBlockZ());
/*      */   }
/*      */   
/*      */   public Chunk getChunkAt(Location location) {
/*  557 */     return getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4);
/*      */   }
/*      */   
/*      */   public ChunkGenerator getGenerator() {
/*  561 */     return this.generator;
/*      */   }
/*      */   
/*      */   public List<BlockPopulator> getPopulators() {
/*  565 */     return this.populators;
/*      */   }
/*      */   
/*      */   public Block getHighestBlockAt(int x, int z) {
/*  569 */     return getBlockAt(x, getHighestBlockYAt(x, z), z);
/*      */   }
/*      */   
/*      */   public Block getHighestBlockAt(Location location) {
/*  573 */     return getHighestBlockAt(location.getBlockX(), location.getBlockZ());
/*      */   }
/*      */   
/*      */   public Biome getBiome(int x, int z) {
/*  577 */     return CraftBlock.biomeBaseToBiome(this.world.getBiome(x, z));
/*      */   }
/*      */   
/*      */   public void setBiome(int x, int z, Biome bio) {
/*  581 */     BiomeBase bb = CraftBlock.biomeToBiomeBase(bio);
/*  582 */     if (this.world.isLoaded(x, 0, z)) {
/*  583 */       Chunk chunk = this.world.getChunkAtWorldCoords(x, z);
/*      */       
/*  585 */       if (chunk != null) {
/*  586 */         byte[] biomevals = chunk.m();
/*  587 */         biomevals[(z & 0xF) << 4 | x & 0xF] = (byte)bb.id;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public double getTemperature(int x, int z) {
/*  593 */     return (this.world.getBiome(x, z)).temperature;
/*      */   }
/*      */   
/*      */   public double getHumidity(int x, int z) {
/*  597 */     return (this.world.getBiome(x, z)).humidity;
/*      */   }
/*      */   
/*      */   public List<Entity> getEntities() {
/*  601 */     List<Entity> list = new ArrayList<Entity>();
/*      */     
/*  603 */     for (Object o : this.world.entityList) {
/*  604 */       if (o instanceof Entity) {
/*  605 */         Entity mcEnt = (Entity)o;
/*  606 */         CraftEntity craftEntity = mcEnt.getBukkitEntity();
/*      */ 
/*      */         
/*  609 */         if (craftEntity != null) {
/*  610 */           list.add(craftEntity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  615 */     return list;
/*      */   }
/*      */   
/*      */   public List<LivingEntity> getLivingEntities() {
/*  619 */     List<LivingEntity> list = new ArrayList<LivingEntity>();
/*      */     
/*  621 */     for (Object o : this.world.entityList) {
/*  622 */       if (o instanceof Entity) {
/*  623 */         Entity mcEnt = (Entity)o;
/*  624 */         CraftEntity craftEntity = mcEnt.getBukkitEntity();
/*      */ 
/*      */         
/*  627 */         if (craftEntity != null && craftEntity instanceof LivingEntity) {
/*  628 */           list.add((LivingEntity)craftEntity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  633 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public <T extends Entity> Collection<T> getEntitiesByClass(Class<T>... classes) {
/*  639 */     return (Collection)getEntitiesByClasses((Class<?>[])classes);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> clazz) {
/*  644 */     Collection<T> list = new ArrayList<T>();
/*      */     
/*  646 */     for (Object entity : this.world.entityList) {
/*  647 */       if (entity instanceof Entity) {
/*  648 */         CraftEntity craftEntity = ((Entity)entity).getBukkitEntity();
/*      */         
/*  650 */         if (craftEntity == null) {
/*      */           continue;
/*      */         }
/*      */         
/*  654 */         Class<?> bukkitClass = craftEntity.getClass();
/*      */         
/*  656 */         if (clazz.isAssignableFrom(bukkitClass)) {
/*  657 */           list.add((T)craftEntity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  662 */     return list;
/*      */   }
/*      */   
/*      */   public Collection<Entity> getEntitiesByClasses(Class<?>... classes) {
/*  666 */     Collection<Entity> list = new ArrayList<Entity>();
/*      */     
/*  668 */     for (Object entity : this.world.entityList) {
/*  669 */       if (entity instanceof Entity) {
/*  670 */         CraftEntity craftEntity = ((Entity)entity).getBukkitEntity();
/*      */         
/*  672 */         if (craftEntity == null) {
/*      */           continue;
/*      */         }
/*      */         
/*  676 */         Class<?> bukkitClass = craftEntity.getClass();
/*      */         
/*  678 */         for (Class<?> clazz : classes) {
/*  679 */           if (clazz.isAssignableFrom(bukkitClass)) {
/*  680 */             list.add(craftEntity);
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  687 */     return list;
/*      */   }
/*      */   
/*      */   public List<Player> getPlayers() {
/*  691 */     List<Player> list = new ArrayList<Player>();
/*      */     
/*  693 */     for (Object o : this.world.entityList) {
/*  694 */       if (o instanceof Entity) {
/*  695 */         Entity mcEnt = (Entity)o;
/*  696 */         CraftEntity craftEntity = mcEnt.getBukkitEntity();
/*      */         
/*  698 */         if (craftEntity != null && craftEntity instanceof Player) {
/*  699 */           list.add((Player)craftEntity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  704 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public void save() {
/*  709 */     save(true);
/*      */   }
/*      */ 
/*      */   
/*      */   public void save(boolean forceSave) {
/*  714 */     this.server.checkSaveState();
/*      */     try {
/*  716 */       boolean oldSave = this.world.savingDisabled;
/*      */       
/*  718 */       this.world.savingDisabled = false;
/*  719 */       this.world.save(forceSave, null);
/*      */       
/*  721 */       this.world.savingDisabled = oldSave;
/*  722 */     } catch (ExceptionWorldConflict ex) {
/*  723 */       ex.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isAutoSave() {
/*  728 */     return !this.world.savingDisabled;
/*      */   }
/*      */   
/*      */   public void setAutoSave(boolean value) {
/*  732 */     this.world.savingDisabled = !value;
/*      */   }
/*      */   
/*      */   public void setDifficulty(Difficulty difficulty) {
/*  736 */     (getHandle()).difficulty = EnumDifficulty.getById(difficulty.getValue());
/*      */   }
/*      */   
/*      */   public Difficulty getDifficulty() {
/*  740 */     return Difficulty.getByValue((getHandle()).difficulty.ordinal());
/*      */   }
/*      */   
/*      */   public BlockMetadataStore getBlockMetadata() {
/*  744 */     return this.blockMetadata;
/*      */   }
/*      */   
/*      */   public boolean hasStorm() {
/*  748 */     return this.world.worldData.hasStorm();
/*      */   }
/*      */   
/*      */   public void setStorm(boolean hasStorm) {
/*  752 */     CraftServer server = this.world.getServer();
/*      */     
/*  754 */     WeatherChangeEvent weather = new WeatherChangeEvent(this, hasStorm);
/*  755 */     server.getPluginManager().callEvent((Event)weather);
/*  756 */     if (!weather.isCancelled()) {
/*  757 */       this.world.worldData.setStorm(hasStorm);
/*      */ 
/*      */       
/*  760 */       if (hasStorm) {
/*  761 */         setWeatherDuration(rand.nextInt(12000) + 12000);
/*      */       } else {
/*  763 */         setWeatherDuration(rand.nextInt(168000) + 12000);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getWeatherDuration() {
/*  769 */     return this.world.worldData.getWeatherDuration();
/*      */   }
/*      */   
/*      */   public void setWeatherDuration(int duration) {
/*  773 */     this.world.worldData.setWeatherDuration(duration);
/*      */   }
/*      */   
/*      */   public boolean isThundering() {
/*  777 */     return (hasStorm() && this.world.worldData.isThundering());
/*      */   }
/*      */   
/*      */   public void setThundering(boolean thundering) {
/*  781 */     if (thundering && !hasStorm()) setStorm(true); 
/*  782 */     CraftServer server = this.world.getServer();
/*      */     
/*  784 */     ThunderChangeEvent thunder = new ThunderChangeEvent(this, thundering);
/*  785 */     server.getPluginManager().callEvent((Event)thunder);
/*  786 */     if (!thunder.isCancelled()) {
/*  787 */       this.world.worldData.setThundering(thundering);
/*      */ 
/*      */       
/*  790 */       if (thundering) {
/*  791 */         setThunderDuration(rand.nextInt(12000) + 3600);
/*      */       } else {
/*  793 */         setThunderDuration(rand.nextInt(168000) + 12000);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getThunderDuration() {
/*  799 */     return this.world.worldData.getThunderDuration();
/*      */   }
/*      */   
/*      */   public void setThunderDuration(int duration) {
/*  803 */     this.world.worldData.setThunderDuration(duration);
/*      */   }
/*      */   
/*      */   public long getSeed() {
/*  807 */     return this.world.worldData.getSeed();
/*      */   }
/*      */   
/*      */   public boolean getPVP() {
/*  811 */     return this.world.pvpMode;
/*      */   }
/*      */   
/*      */   public void setPVP(boolean pvp) {
/*  815 */     this.world.pvpMode = pvp;
/*      */   }
/*      */   
/*      */   public void playEffect(Player player, Effect effect, int data) {
/*  819 */     playEffect(player.getLocation(), effect, data, 0);
/*      */   }
/*      */   
/*      */   public void playEffect(Location location, Effect effect, int data) {
/*  823 */     playEffect(location, effect, data, 64);
/*      */   }
/*      */   
/*      */   public <T> void playEffect(Location loc, Effect effect, T data) {
/*  827 */     playEffect(loc, effect, data, 64);
/*      */   }
/*      */   
/*      */   public <T> void playEffect(Location loc, Effect effect, T data, int radius) {
/*  831 */     if (data != null) {
/*  832 */       Validate.isTrue(data.getClass().equals(effect.getData()), "Wrong kind of data for this effect!");
/*      */     } else {
/*  834 */       Validate.isTrue((effect.getData() == null), "Wrong kind of data for this effect!");
/*      */     } 
/*      */     
/*  837 */     if (data != null && data.getClass().equals(MaterialData.class)) {
/*  838 */       MaterialData materialData = (MaterialData)data;
/*  839 */       Validate.isTrue(materialData.getItemType().isBlock(), "Material must be block");
/*  840 */       spigot().playEffect(loc, effect, materialData.getItemType().getId(), materialData.getData(), 0.0F, 0.0F, 0.0F, 1.0F, 1, radius);
/*      */     } else {
/*  842 */       int dataValue = (data == null) ? 0 : CraftEffect.<T>getDataValue(effect, data);
/*  843 */       playEffect(loc, effect, dataValue, radius);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void playEffect(Location location, Effect effect, int data, int radius) {
/*  848 */     spigot().playEffect(location, effect, data, 0, 0.0F, 0.0F, 0.0F, 1.0F, 1, radius);
/*      */   }
/*      */   
/*      */   public <T extends Entity> T spawn(Location location, Class<T> clazz) throws IllegalArgumentException {
/*  852 */     return spawn(location, clazz, CreatureSpawnEvent.SpawnReason.CUSTOM);
/*      */   }
/*      */   
/*      */   public FallingBlock spawnFallingBlock(Location location, Material material, byte data) throws IllegalArgumentException {
/*  856 */     Validate.notNull(location, "Location cannot be null");
/*  857 */     Validate.notNull(material, "Material cannot be null");
/*  858 */     Validate.isTrue(material.isBlock(), "Material must be a block");
/*      */     
/*  860 */     double x = location.getBlockX() + 0.5D;
/*  861 */     double y = location.getBlockY() + 0.5D;
/*  862 */     double z = location.getBlockZ() + 0.5D;
/*      */ 
/*      */     
/*  865 */     location = location.clone();
/*  866 */     EntityFallingBlock entity = new EntityFallingBlock(location, (World)this.world, x, y, z, Block.getById(material.getId()), data);
/*      */     
/*  868 */     entity.ticksLived = 1;
/*      */     
/*  870 */     this.world.addEntity((Entity)entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
/*  871 */     return (FallingBlock)entity.getBukkitEntity();
/*      */   }
/*      */   
/*      */   public FallingBlock spawnFallingBlock(Location location, int blockId, byte blockData) throws IllegalArgumentException {
/*  875 */     return spawnFallingBlock(location, Material.getMaterial(blockId), blockData);
/*      */   }
/*      */   
/*      */   public <T extends Entity> T spawn(Location location, Class<T> clazz, CreatureSpawnEvent.SpawnReason reason) throws IllegalArgumentException {
/*      */     EntityFireworks entityFireworks;
/*  880 */     if (location == null || clazz == null) {
/*  881 */       throw new IllegalArgumentException("Location or entity class cannot be null");
/*      */     }
/*      */     
/*  884 */     Entity entity = null;
/*      */     
/*  886 */     double x = location.getX();
/*  887 */     double y = location.getY();
/*  888 */     double z = location.getZ();
/*  889 */     float pitch = location.getPitch();
/*  890 */     float yaw = location.getYaw();
/*      */ 
/*      */     
/*  893 */     if (Boat.class.isAssignableFrom(clazz)) {
/*  894 */       EntityBoat entityBoat = new EntityBoat((World)this.world, x, y, z);
/*  895 */     } else if (FallingBlock.class.isAssignableFrom(clazz)) {
/*  896 */       x = location.getBlockX();
/*  897 */       y = location.getBlockY();
/*  898 */       z = location.getBlockZ();
/*  899 */       int type = this.world.getTypeId((int)x, (int)y, (int)z);
/*  900 */       int data = this.world.getData((int)x, (int)y, (int)z);
/*      */ 
/*      */       
/*  903 */       location = location.clone();
/*  904 */       EntityFallingBlock entityFallingBlock = new EntityFallingBlock(location, (World)this.world, x + 0.5D, y + 0.5D, z + 0.5D, Block.getById(type), data);
/*      */     }
/*  906 */     else if (Projectile.class.isAssignableFrom(clazz)) {
/*  907 */       if (Snowball.class.isAssignableFrom(clazz)) {
/*  908 */         EntitySnowball entitySnowball = new EntitySnowball((World)this.world, x, y, z);
/*  909 */       } else if (Egg.class.isAssignableFrom(clazz)) {
/*  910 */         EntityEgg entityEgg = new EntityEgg((World)this.world, x, y, z);
/*  911 */       } else if (Arrow.class.isAssignableFrom(clazz)) {
/*  912 */         EntityArrow entityArrow = new EntityArrow((World)this.world);
/*  913 */         entityArrow.setPositionRotation(x, y, z, 0.0F, 0.0F);
/*  914 */       } else if (ThrownExpBottle.class.isAssignableFrom(clazz)) {
/*  915 */         EntityThrownExpBottle entityThrownExpBottle = new EntityThrownExpBottle((World)this.world);
/*  916 */         entityThrownExpBottle.setPositionRotation(x, y, z, 0.0F, 0.0F);
/*  917 */       } else if (EnderPearl.class.isAssignableFrom(clazz)) {
/*  918 */         EntityEnderPearl entityEnderPearl = new EntityEnderPearl((World)this.world);
/*  919 */         entityEnderPearl.setPositionRotation(x, y, z, 0.0F, 0.0F);
/*  920 */       } else if (ThrownPotion.class.isAssignableFrom(clazz)) {
/*  921 */         EntityPotion entityPotion = new EntityPotion((World)this.world, x, y, z, CraftItemStack.asNMSCopy(new ItemStack(Material.POTION, 1)));
/*  922 */       } else if (Fireball.class.isAssignableFrom(clazz)) {
/*  923 */         EntityLargeFireball entityLargeFireball; if (SmallFireball.class.isAssignableFrom(clazz)) {
/*  924 */           EntitySmallFireball entitySmallFireball = new EntitySmallFireball((World)this.world);
/*  925 */         } else if (WitherSkull.class.isAssignableFrom(clazz)) {
/*  926 */           EntityWitherSkull entityWitherSkull = new EntityWitherSkull((World)this.world);
/*      */         } else {
/*  928 */           entityLargeFireball = new EntityLargeFireball((World)this.world);
/*      */         } 
/*  930 */         entityLargeFireball.setPositionRotation(x, y, z, yaw, pitch);
/*  931 */         Vector direction = location.getDirection().multiply(10);
/*  932 */         ((EntityFireball)entityLargeFireball).setDirection(direction.getX(), direction.getY(), direction.getZ());
/*      */       } 
/*  934 */     } else if (Minecart.class.isAssignableFrom(clazz)) {
/*  935 */       if (PoweredMinecart.class.isAssignableFrom(clazz)) {
/*  936 */         EntityMinecartFurnace entityMinecartFurnace = new EntityMinecartFurnace((World)this.world, x, y, z);
/*  937 */       } else if (StorageMinecart.class.isAssignableFrom(clazz)) {
/*  938 */         EntityMinecartChest entityMinecartChest = new EntityMinecartChest((World)this.world, x, y, z);
/*  939 */       } else if (ExplosiveMinecart.class.isAssignableFrom(clazz)) {
/*  940 */         EntityMinecartTNT entityMinecartTNT = new EntityMinecartTNT((World)this.world, x, y, z);
/*  941 */       } else if (HopperMinecart.class.isAssignableFrom(clazz)) {
/*  942 */         EntityMinecartHopper entityMinecartHopper = new EntityMinecartHopper((World)this.world, x, y, z);
/*  943 */       } else if (SpawnerMinecart.class.isAssignableFrom(clazz)) {
/*  944 */         EntityMinecartMobSpawner entityMinecartMobSpawner = new EntityMinecartMobSpawner((World)this.world, x, y, z);
/*      */       } else {
/*  946 */         EntityMinecartRideable entityMinecartRideable = new EntityMinecartRideable((World)this.world, x, y, z);
/*      */       } 
/*  948 */     } else if (EnderSignal.class.isAssignableFrom(clazz)) {
/*  949 */       EntityEnderSignal entityEnderSignal = new EntityEnderSignal((World)this.world, x, y, z);
/*  950 */     } else if (EnderCrystal.class.isAssignableFrom(clazz)) {
/*  951 */       EntityEnderCrystal entityEnderCrystal = new EntityEnderCrystal((World)this.world);
/*  952 */       entityEnderCrystal.setPositionRotation(x, y, z, 0.0F, 0.0F);
/*  953 */     } else if (LivingEntity.class.isAssignableFrom(clazz)) {
/*  954 */       EntityBat entityBat; if (Chicken.class.isAssignableFrom(clazz)) {
/*  955 */         EntityChicken entityChicken = new EntityChicken((World)this.world);
/*  956 */       } else if (Cow.class.isAssignableFrom(clazz)) {
/*  957 */         if (MushroomCow.class.isAssignableFrom(clazz)) {
/*  958 */           EntityMushroomCow entityMushroomCow = new EntityMushroomCow((World)this.world);
/*      */         } else {
/*  960 */           EntityCow entityCow = new EntityCow((World)this.world);
/*      */         } 
/*  962 */       } else if (Golem.class.isAssignableFrom(clazz)) {
/*  963 */         if (Snowman.class.isAssignableFrom(clazz)) {
/*  964 */           EntitySnowman entitySnowman = new EntitySnowman((World)this.world);
/*  965 */         } else if (IronGolem.class.isAssignableFrom(clazz)) {
/*  966 */           EntityIronGolem entityIronGolem = new EntityIronGolem((World)this.world);
/*      */         } 
/*  968 */       } else if (Creeper.class.isAssignableFrom(clazz)) {
/*  969 */         EntityCreeper entityCreeper = new EntityCreeper((World)this.world);
/*  970 */       } else if (Ghast.class.isAssignableFrom(clazz)) {
/*  971 */         EntityGhast entityGhast = new EntityGhast((World)this.world);
/*  972 */       } else if (Pig.class.isAssignableFrom(clazz)) {
/*  973 */         EntityPig entityPig = new EntityPig((World)this.world);
/*  974 */       } else if (!Player.class.isAssignableFrom(clazz)) {
/*      */         
/*  976 */         if (Sheep.class.isAssignableFrom(clazz)) {
/*  977 */           EntitySheep entitySheep = new EntitySheep((World)this.world);
/*  978 */         } else if (Horse.class.isAssignableFrom(clazz)) {
/*  979 */           EntityHorse entityHorse = new EntityHorse((World)this.world);
/*  980 */         } else if (Skeleton.class.isAssignableFrom(clazz)) {
/*  981 */           EntitySkeleton entitySkeleton = new EntitySkeleton((World)this.world);
/*  982 */         } else if (Slime.class.isAssignableFrom(clazz)) {
/*  983 */           if (MagmaCube.class.isAssignableFrom(clazz)) {
/*  984 */             EntityMagmaCube entityMagmaCube = new EntityMagmaCube((World)this.world);
/*      */           } else {
/*  986 */             EntitySlime entitySlime = new EntitySlime((World)this.world);
/*      */           } 
/*  988 */         } else if (Spider.class.isAssignableFrom(clazz)) {
/*  989 */           if (CaveSpider.class.isAssignableFrom(clazz)) {
/*  990 */             EntityCaveSpider entityCaveSpider = new EntityCaveSpider((World)this.world);
/*      */           } else {
/*  992 */             EntitySpider entitySpider = new EntitySpider((World)this.world);
/*      */           } 
/*  994 */         } else if (Squid.class.isAssignableFrom(clazz)) {
/*  995 */           EntitySquid entitySquid = new EntitySquid((World)this.world);
/*  996 */         } else if (Tameable.class.isAssignableFrom(clazz)) {
/*  997 */           if (Wolf.class.isAssignableFrom(clazz)) {
/*  998 */             EntityWolf entityWolf = new EntityWolf((World)this.world);
/*  999 */           } else if (Ocelot.class.isAssignableFrom(clazz)) {
/* 1000 */             EntityOcelot entityOcelot = new EntityOcelot((World)this.world);
/*      */           } 
/* 1002 */         } else if (PigZombie.class.isAssignableFrom(clazz)) {
/* 1003 */           EntityPigZombie entityPigZombie = new EntityPigZombie((World)this.world);
/* 1004 */         } else if (Zombie.class.isAssignableFrom(clazz)) {
/* 1005 */           EntityZombie entityZombie = new EntityZombie((World)this.world);
/* 1006 */         } else if (Giant.class.isAssignableFrom(clazz)) {
/* 1007 */           EntityGiantZombie entityGiantZombie = new EntityGiantZombie((World)this.world);
/* 1008 */         } else if (Silverfish.class.isAssignableFrom(clazz)) {
/* 1009 */           EntitySilverfish entitySilverfish = new EntitySilverfish((World)this.world);
/* 1010 */         } else if (Enderman.class.isAssignableFrom(clazz)) {
/* 1011 */           EntityEnderman entityEnderman = new EntityEnderman((World)this.world);
/* 1012 */         } else if (Blaze.class.isAssignableFrom(clazz)) {
/* 1013 */           EntityBlaze entityBlaze = new EntityBlaze((World)this.world);
/* 1014 */         } else if (Villager.class.isAssignableFrom(clazz)) {
/* 1015 */           EntityVillager entityVillager = new EntityVillager((World)this.world);
/* 1016 */         } else if (Witch.class.isAssignableFrom(clazz)) {
/* 1017 */           EntityWitch entityWitch = new EntityWitch((World)this.world);
/* 1018 */         } else if (Wither.class.isAssignableFrom(clazz)) {
/* 1019 */           EntityWither entityWither = new EntityWither((World)this.world);
/* 1020 */         } else if (ComplexLivingEntity.class.isAssignableFrom(clazz)) {
/* 1021 */           if (EnderDragon.class.isAssignableFrom(clazz)) {
/* 1022 */             EntityEnderDragon entityEnderDragon = new EntityEnderDragon((World)this.world);
/*      */           }
/* 1024 */         } else if (Ambient.class.isAssignableFrom(clazz) && 
/* 1025 */           Bat.class.isAssignableFrom(clazz)) {
/* 1026 */           entityBat = new EntityBat((World)this.world);
/*      */         } 
/*      */       } 
/*      */       
/* 1030 */       if (entityBat != null) {
/* 1031 */         entityBat.setLocation(x, y, z, yaw, pitch);
/*      */       }
/* 1033 */     } else if (Hanging.class.isAssignableFrom(clazz)) {
/* 1034 */       EntityLeash entityLeash; int dir; Block block = getBlockAt(location);
/* 1035 */       BlockFace face = BlockFace.SELF;
/* 1036 */       if (block.getRelative(BlockFace.EAST).getTypeId() == 0) {
/* 1037 */         face = BlockFace.EAST;
/* 1038 */       } else if (block.getRelative(BlockFace.NORTH).getTypeId() == 0) {
/* 1039 */         face = BlockFace.NORTH;
/* 1040 */       } else if (block.getRelative(BlockFace.WEST).getTypeId() == 0) {
/* 1041 */         face = BlockFace.WEST;
/* 1042 */       } else if (block.getRelative(BlockFace.SOUTH).getTypeId() == 0) {
/* 1043 */         face = BlockFace.SOUTH;
/*      */       } 
/*      */       
/* 1046 */       switch (face) {
/*      */         
/*      */         default:
/* 1049 */           dir = 0;
/*      */           break;
/*      */         case WEST:
/* 1052 */           dir = 1;
/*      */           break;
/*      */         case NORTH:
/* 1055 */           dir = 2;
/*      */           break;
/*      */         case EAST:
/* 1058 */           dir = 3;
/*      */           break;
/*      */       } 
/*      */       
/* 1062 */       if (Painting.class.isAssignableFrom(clazz)) {
/* 1063 */         EntityPainting entityPainting = new EntityPainting((World)this.world, (int)x, (int)y, (int)z, dir);
/* 1064 */       } else if (ItemFrame.class.isAssignableFrom(clazz)) {
/* 1065 */         EntityItemFrame entityItemFrame = new EntityItemFrame((World)this.world, (int)x, (int)y, (int)z, dir);
/* 1066 */       } else if (LeashHitch.class.isAssignableFrom(clazz)) {
/* 1067 */         entityLeash = new EntityLeash((World)this.world, (int)x, (int)y, (int)z);
/* 1068 */         ((Entity)entityLeash).attachedToPlayer = true;
/*      */       } 
/*      */       
/* 1071 */       if (entityLeash != null && !((EntityHanging)entityLeash).survives()) {
/* 1072 */         throw new IllegalArgumentException("Cannot spawn hanging entity for " + clazz.getName() + " at " + location);
/*      */       }
/* 1074 */     } else if (TNTPrimed.class.isAssignableFrom(clazz)) {
/*      */       
/* 1076 */       Location loc = new Location(this.world.getWorld(), x, y, z);
/* 1077 */       EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(loc, (World)this.world, x, y, z, null);
/*      */     }
/* 1079 */     else if (ExperienceOrb.class.isAssignableFrom(clazz)) {
/* 1080 */       EntityExperienceOrb entityExperienceOrb = new EntityExperienceOrb((World)this.world, x, y, z, 0);
/* 1081 */     } else if (Weather.class.isAssignableFrom(clazz)) {
/*      */       
/* 1083 */       if (LightningStrike.class.isAssignableFrom(clazz)) {
/* 1084 */         EntityLightning entityLightning = new EntityLightning((World)this.world, x, y, z);
/*      */       }
/*      */     }
/* 1087 */     else if (Firework.class.isAssignableFrom(clazz)) {
/* 1088 */       entityFireworks = new EntityFireworks((World)this.world, x, y, z, null);
/*      */     } 
/*      */     
/* 1091 */     if (entityFireworks != null) {
/*      */       
/* 1093 */       if (entityFireworks instanceof EntityOcelot)
/*      */       {
/* 1095 */         ((EntityOcelot)entityFireworks).spawnBonus = false;
/*      */       }
/*      */       
/* 1098 */       if (entityFireworks instanceof EntityInsentient) {
/* 1099 */         ((EntityInsentient)entityFireworks).prepare((GroupDataEntity)null);
/*      */       }
/*      */       
/* 1102 */       this.world.addEntity((Entity)entityFireworks, reason);
/* 1103 */       return (T)entityFireworks.getBukkitEntity();
/*      */     } 
/*      */     
/* 1106 */     throw new IllegalArgumentException("Cannot spawn an entity for " + clazz.getName());
/*      */   }
/*      */   
/*      */   public ChunkSnapshot getEmptyChunkSnapshot(int x, int z, boolean includeBiome, boolean includeBiomeTempRain) {
/* 1110 */     return CraftChunk.getEmptyChunkSnapshot(x, z, this, includeBiome, includeBiomeTempRain);
/*      */   }
/*      */   
/*      */   public void setSpawnFlags(boolean allowMonsters, boolean allowAnimals) {
/* 1114 */     this.world.setSpawnFlags(allowMonsters, allowAnimals);
/*      */   }
/*      */   
/*      */   public boolean getAllowAnimals() {
/* 1118 */     return this.world.allowAnimals;
/*      */   }
/*      */   
/*      */   public boolean getAllowMonsters() {
/* 1122 */     return this.world.allowMonsters;
/*      */   }
/*      */   
/*      */   public int getMaxHeight() {
/* 1126 */     return this.world.getHeight();
/*      */   }
/*      */   
/*      */   public int getSeaLevel() {
/* 1130 */     return 64;
/*      */   }
/*      */   
/*      */   public boolean getKeepSpawnInMemory() {
/* 1134 */     return this.world.keepSpawnInMemory;
/*      */   }
/*      */   
/*      */   public void setKeepSpawnInMemory(boolean keepLoaded) {
/* 1138 */     this.world.keepSpawnInMemory = keepLoaded;
/*      */     
/* 1140 */     ChunkCoordinates chunkcoordinates = this.world.getSpawn();
/* 1141 */     int chunkCoordX = chunkcoordinates.x >> 4;
/* 1142 */     int chunkCoordZ = chunkcoordinates.z >> 4;
/*      */     
/* 1144 */     for (int x = -12; x <= 12; x++) {
/* 1145 */       for (int z = -12; z <= 12; z++) {
/* 1146 */         if (keepLoaded) {
/* 1147 */           loadChunk(chunkCoordX + x, chunkCoordZ + z);
/*      */         }
/* 1149 */         else if (isChunkLoaded(chunkCoordX + x, chunkCoordZ + z)) {
/* 1150 */           if (getHandle().getChunkAt(chunkCoordX + x, chunkCoordZ + z) instanceof net.minecraft.server.v1_7_R4.EmptyChunk) {
/* 1151 */             unloadChunk(chunkCoordX + x, chunkCoordZ + z, false);
/*      */           } else {
/* 1153 */             unloadChunk(chunkCoordX + x, chunkCoordZ + z);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1163 */     return getUID().hashCode();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/* 1168 */     if (obj == null) {
/* 1169 */       return false;
/*      */     }
/* 1171 */     if (getClass() != obj.getClass()) {
/* 1172 */       return false;
/*      */     }
/*      */     
/* 1175 */     CraftWorld other = (CraftWorld)obj;
/*      */     
/* 1177 */     return (getUID() == other.getUID());
/*      */   }
/*      */   
/*      */   public File getWorldFolder() {
/* 1181 */     return ((WorldNBTStorage)this.world.getDataManager()).getDirectory();
/*      */   }
/*      */   
/*      */   public void sendPluginMessage(Plugin source, String channel, byte[] message) {
/* 1185 */     StandardMessenger.validatePluginMessage(this.server.getMessenger(), source, channel, message);
/*      */     
/* 1187 */     for (Player player : getPlayers()) {
/* 1188 */       player.sendPluginMessage(source, channel, message);
/*      */     }
/*      */   }
/*      */   
/*      */   public Set<String> getListeningPluginChannels() {
/* 1193 */     Set<String> result = new HashSet<String>();
/*      */     
/* 1195 */     for (Player player : getPlayers()) {
/* 1196 */       result.addAll(player.getListeningPluginChannels());
/*      */     }
/*      */     
/* 1199 */     return result;
/*      */   }
/*      */   
/*      */   public WorldType getWorldType() {
/* 1203 */     return WorldType.getByName(this.world.getWorldData().getType().name());
/*      */   }
/*      */   
/*      */   public boolean canGenerateStructures() {
/* 1207 */     return this.world.getWorldData().shouldGenerateMapFeatures();
/*      */   }
/*      */   
/*      */   public long getTicksPerAnimalSpawns() {
/* 1211 */     return this.world.ticksPerAnimalSpawns;
/*      */   }
/*      */   
/*      */   public void setTicksPerAnimalSpawns(int ticksPerAnimalSpawns) {
/* 1215 */     this.world.ticksPerAnimalSpawns = ticksPerAnimalSpawns;
/*      */   }
/*      */   
/*      */   public long getTicksPerMonsterSpawns() {
/* 1219 */     return this.world.ticksPerMonsterSpawns;
/*      */   }
/*      */   
/*      */   public void setTicksPerMonsterSpawns(int ticksPerMonsterSpawns) {
/* 1223 */     this.world.ticksPerMonsterSpawns = ticksPerMonsterSpawns;
/*      */   }
/*      */   
/*      */   public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
/* 1227 */     this.server.getWorldMetadata().setMetadata(this, metadataKey, newMetadataValue);
/*      */   }
/*      */   
/*      */   public List<MetadataValue> getMetadata(String metadataKey) {
/* 1231 */     return this.server.getWorldMetadata().getMetadata(this, metadataKey);
/*      */   }
/*      */   
/*      */   public boolean hasMetadata(String metadataKey) {
/* 1235 */     return this.server.getWorldMetadata().hasMetadata(this, metadataKey);
/*      */   }
/*      */   
/*      */   public void removeMetadata(String metadataKey, Plugin owningPlugin) {
/* 1239 */     this.server.getWorldMetadata().removeMetadata(this, metadataKey, owningPlugin);
/*      */   }
/*      */   
/*      */   public int getMonsterSpawnLimit() {
/* 1243 */     if (this.monsterSpawn < 0) {
/* 1244 */       return this.server.getMonsterSpawnLimit();
/*      */     }
/*      */     
/* 1247 */     return this.monsterSpawn;
/*      */   }
/*      */   
/*      */   public void setMonsterSpawnLimit(int limit) {
/* 1251 */     this.monsterSpawn = limit;
/*      */   }
/*      */   
/*      */   public int getAnimalSpawnLimit() {
/* 1255 */     if (this.animalSpawn < 0) {
/* 1256 */       return this.server.getAnimalSpawnLimit();
/*      */     }
/*      */     
/* 1259 */     return this.animalSpawn;
/*      */   }
/*      */   
/*      */   public void setAnimalSpawnLimit(int limit) {
/* 1263 */     this.animalSpawn = limit;
/*      */   }
/*      */   
/*      */   public int getWaterAnimalSpawnLimit() {
/* 1267 */     if (this.waterAnimalSpawn < 0) {
/* 1268 */       return this.server.getWaterAnimalSpawnLimit();
/*      */     }
/*      */     
/* 1271 */     return this.waterAnimalSpawn;
/*      */   }
/*      */   
/*      */   public void setWaterAnimalSpawnLimit(int limit) {
/* 1275 */     this.waterAnimalSpawn = limit;
/*      */   }
/*      */   
/*      */   public int getAmbientSpawnLimit() {
/* 1279 */     if (this.ambientSpawn < 0) {
/* 1280 */       return this.server.getAmbientSpawnLimit();
/*      */     }
/*      */     
/* 1283 */     return this.ambientSpawn;
/*      */   }
/*      */   
/*      */   public void setAmbientSpawnLimit(int limit) {
/* 1287 */     this.ambientSpawn = limit;
/*      */   }
/*      */ 
/*      */   
/*      */   public void playSound(Location loc, Sound sound, float volume, float pitch) {
/* 1292 */     if (loc == null || sound == null)
/*      */       return; 
/* 1294 */     double x = loc.getX();
/* 1295 */     double y = loc.getY();
/* 1296 */     double z = loc.getZ();
/*      */     
/* 1298 */     getHandle().makeSound(x, y, z, CraftSound.getSound(sound), volume, pitch);
/*      */   }
/*      */   
/*      */   public String getGameRuleValue(String rule) {
/* 1302 */     return getHandle().getGameRules().get(rule);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setGameRuleValue(String rule, String value) {
/* 1307 */     if (rule == null || value == null) return false;
/*      */     
/* 1309 */     if (!isGameRule(rule)) return false;
/*      */     
/* 1311 */     getHandle().getGameRules().set(rule, value);
/* 1312 */     return true;
/*      */   }
/*      */   
/*      */   public String[] getGameRules() {
/* 1316 */     return getHandle().getGameRules().getGameRules();
/*      */   }
/*      */   
/*      */   public boolean isGameRule(String rule) {
/* 1320 */     return getHandle().getGameRules().contains(rule);
/*      */   }
/*      */   
/*      */   public void processChunkGC() {
/* 1324 */     this.chunkGCTickCount++;
/*      */     
/* 1326 */     if (this.chunkLoadCount >= this.server.chunkGCLoadThresh && this.server.chunkGCLoadThresh > 0) {
/* 1327 */       this.chunkLoadCount = 0;
/* 1328 */     } else if (this.chunkGCTickCount >= this.server.chunkGCPeriod && this.server.chunkGCPeriod > 0) {
/* 1329 */       this.chunkGCTickCount = 0;
/*      */     } else {
/*      */       return;
/*      */     } 
/*      */     
/* 1334 */     ChunkProviderServer cps = this.world.chunkProviderServer;
/* 1335 */     for (Chunk chunk : cps.chunks.values()) {
/*      */       
/* 1337 */       if (isChunkInUse(chunk.locX, chunk.locZ)) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/* 1342 */       if (cps.unloadQueue.contains(chunk.locX, chunk.locZ)) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/* 1347 */       cps.queueUnload(chunk.locX, chunk.locZ);
/*      */     } 
/*      */   }
/*      */   
/* 1351 */   public CraftWorld(WorldServer world, ChunkGenerator gen, World.Environment env) { this.spigot = new World.Spigot()
/*      */       {
/*      */         public void playEffect(Location location, Effect effect, int id, int data, float offsetX, float offsetY, float offsetZ, float speed, int particleCount, int radius)
/*      */         {
/*      */           PacketPlayOutWorldParticles packetPlayOutWorldParticles;
/* 1356 */           Validate.notNull(location, "Location cannot be null");
/* 1357 */           Validate.notNull(effect, "Effect cannot be null");
/* 1358 */           Validate.notNull(location.getWorld(), "World cannot be null");
/*      */           
/* 1360 */           if (effect.getType() != Effect.Type.PARTICLE) {
/*      */             
/* 1362 */             int packetData = effect.getId();
/* 1363 */             PacketPlayOutWorldEvent packetPlayOutWorldEvent = new PacketPlayOutWorldEvent(packetData, location.getBlockX(), location.getBlockY(), location.getBlockZ(), id, false);
/*      */           } else {
/*      */             
/* 1366 */             StringBuilder particleFullName = new StringBuilder();
/* 1367 */             particleFullName.append(effect.getName());
/* 1368 */             if (effect.getData() != null && (effect.getData().equals(Material.class) || effect.getData().equals(MaterialData.class)))
/*      */             {
/* 1370 */               particleFullName.append('_').append(id);
/*      */             }
/* 1372 */             if (effect.getData() != null && effect.getData().equals(MaterialData.class))
/*      */             {
/* 1374 */               particleFullName.append('_').append(data);
/*      */             }
/* 1376 */             packetPlayOutWorldParticles = new PacketPlayOutWorldParticles(particleFullName.toString(), (float)location.getX(), (float)location.getY(), (float)location.getZ(), offsetX, offsetY, offsetZ, speed, particleCount);
/*      */           } 
/*      */           
/* 1379 */           radius *= radius;
/* 1380 */           for (Player player : CraftWorld.this.getPlayers()) {
/*      */             
/* 1382 */             if ((((CraftPlayer)player).getHandle()).playerConnection == null) {
/*      */               continue;
/*      */             }
/*      */             
/* 1386 */             if (!location.getWorld().equals(player.getWorld())) {
/*      */               continue;
/*      */             }
/*      */             
/* 1390 */             int distance = (int)player.getLocation().distanceSquared(location);
/* 1391 */             if (distance <= radius)
/*      */             {
/* 1393 */               (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)packetPlayOutWorldParticles);
/*      */             }
/*      */           } 
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public void playEffect(Location location, Effect effect) {
/* 1401 */           CraftWorld.this.playEffect(location, effect, 0);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public LightningStrike strikeLightning(Location loc, boolean isSilent) {
/* 1407 */           EntityLightning lightning = new EntityLightning((World)CraftWorld.this.world, loc.getX(), loc.getY(), loc.getZ(), false, isSilent);
/* 1408 */           CraftWorld.this.world.strikeLightning((Entity)lightning);
/* 1409 */           return (LightningStrike)new CraftLightningStrike(CraftWorld.this.server, lightning);
/*      */         }
/*      */ 
/*      */         
/*      */         public LightningStrike strikeLightningEffect(Location loc, boolean isSilent)
/*      */         {
/* 1415 */           EntityLightning lightning = new EntityLightning((World)CraftWorld.this.world, loc.getX(), loc.getY(), loc.getZ(), true, isSilent);
/* 1416 */           CraftWorld.this.world.strikeLightning((Entity)lightning);
/* 1417 */           return (LightningStrike)new CraftLightningStrike(CraftWorld.this.server, lightning); }
/*      */       };
/*      */     this.world = world;
/*      */     this.generator = gen;
/*      */     this.environment = env;
/*      */     if (this.server.chunkGCPeriod > 0)
/* 1423 */       this.chunkGCTickCount = rand.nextInt(this.server.chunkGCPeriod);  } public World.Spigot spigot() { return this.spigot; }
/*      */ 
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\CraftWorld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */