/*      */ package net.minecraft.server.v1_7_R4;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.util.gnu.trove.iterator.TLongShortIterator;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.WeatherType;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.block.BlockState;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.CraftTravelAgent;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.generator.CustomChunkGenerator;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.generator.NetherChunkGenerator;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.generator.NormalChunkGenerator;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.generator.SkyLandsChunkGenerator;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.util.HashTreeSet;
/*      */ import org.bukkit.entity.LightningStrike;
/*      */ import org.bukkit.event.Event;
/*      */ import org.bukkit.event.block.BlockFormEvent;
/*      */ import org.bukkit.event.weather.LightningStrikeEvent;
/*      */ import org.bukkit.event.weather.ThunderChangeEvent;
/*      */ import org.bukkit.event.weather.WeatherChangeEvent;
/*      */ import org.bukkit.generator.ChunkGenerator;
/*      */ 
/*      */ public class WorldServer extends World {
/*   30 */   private static final Logger a = LogManager.getLogger();
/*      */   private final MinecraftServer server;
/*      */   public EntityTracker tracker;
/*      */   private final PlayerChunkMap manager;
/*      */   private HashTreeSet<NextTickListEntry> N;
/*      */   public ChunkProviderServer chunkProviderServer;
/*      */   public boolean savingDisabled;
/*      */   private boolean O;
/*      */   private int emptyTime;
/*      */   private final PortalTravelAgent Q;
/*   40 */   private final SpawnerCreature R = new SpawnerCreature();
/*   41 */   private BlockActionDataList[] S = new BlockActionDataList[] { new BlockActionDataList((BananaAPI)null), new BlockActionDataList((BananaAPI)null) };
/*      */   private int T;
/*   43 */   private static final StructurePieceTreasure[] U = new StructurePieceTreasure[] { new StructurePieceTreasure(Items.STICK, 0, 1, 3, 10), new StructurePieceTreasure(Item.getItemOf(Blocks.WOOD), 0, 1, 3, 10), new StructurePieceTreasure(Item.getItemOf(Blocks.LOG), 0, 1, 3, 10), new StructurePieceTreasure(Items.STONE_AXE, 0, 1, 1, 3), new StructurePieceTreasure(Items.WOOD_AXE, 0, 1, 1, 5), new StructurePieceTreasure(Items.STONE_PICKAXE, 0, 1, 1, 3), new StructurePieceTreasure(Items.WOOD_PICKAXE, 0, 1, 1, 5), new StructurePieceTreasure(Items.APPLE, 0, 2, 3, 5), new StructurePieceTreasure(Items.BREAD, 0, 2, 3, 3), new StructurePieceTreasure(Item.getItemOf(Blocks.LOG2), 0, 1, 3, 10) };
/*   44 */   private List V = new ArrayList();
/*      */   
/*      */   private IntHashMap entitiesById;
/*      */   
/*      */   public final int dimension;
/*      */ 
/*      */   
/*      */   public WorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i, WorldSettings worldsettings, MethodProfiler methodprofiler, World.Environment env, ChunkGenerator gen) {
/*   52 */     super(idatamanager, s, worldsettings, WorldProvider.byDimension(env.getId()), methodprofiler, gen, env);
/*   53 */     this.dimension = i;
/*   54 */     this.pvpMode = minecraftserver.getPvP();
/*      */     
/*   56 */     this.server = minecraftserver;
/*   57 */     this.tracker = new EntityTracker(this);
/*   58 */     this.manager = new PlayerChunkMap(this, this.spigotConfig.viewDistance);
/*   59 */     if (this.entitiesById == null) {
/*   60 */       this.entitiesById = new IntHashMap();
/*      */     }
/*      */     
/*   63 */     if (this.N == null) {
/*   64 */       this.N = new HashTreeSet();
/*      */     }
/*      */     
/*   67 */     this.Q = (PortalTravelAgent)new CraftTravelAgent(this);
/*   68 */     this.scoreboard = new ScoreboardServer(minecraftserver);
/*   69 */     PersistentScoreboard persistentscoreboard = (PersistentScoreboard)this.worldMaps.get(PersistentScoreboard.class, "scoreboard");
/*      */     
/*   71 */     if (persistentscoreboard == null) {
/*   72 */       persistentscoreboard = new PersistentScoreboard();
/*   73 */       this.worldMaps.a("scoreboard", persistentscoreboard);
/*      */     } 
/*      */     
/*   76 */     persistentscoreboard.a(this.scoreboard);
/*   77 */     ((ScoreboardServer)this.scoreboard).a(persistentscoreboard);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public TileEntity getTileEntity(int i, int j, int k) {
/*   83 */     TileEntity result = super.getTileEntity(i, j, k);
/*   84 */     Block type = getType(i, j, k);
/*      */     
/*   86 */     if (type == Blocks.CHEST || type == Blocks.TRAPPED_CHEST) {
/*   87 */       if (!(result instanceof TileEntityChest)) {
/*   88 */         result = fixTileEntity(i, j, k, type, result);
/*      */       }
/*   90 */     } else if (type == Blocks.FURNACE) {
/*   91 */       if (!(result instanceof TileEntityFurnace)) {
/*   92 */         result = fixTileEntity(i, j, k, type, result);
/*      */       }
/*   94 */     } else if (type == Blocks.DROPPER) {
/*   95 */       if (!(result instanceof TileEntityDropper)) {
/*   96 */         result = fixTileEntity(i, j, k, type, result);
/*      */       }
/*   98 */     } else if (type == Blocks.DISPENSER) {
/*   99 */       if (!(result instanceof TileEntityDispenser)) {
/*  100 */         result = fixTileEntity(i, j, k, type, result);
/*      */       }
/*  102 */     } else if (type == Blocks.JUKEBOX) {
/*  103 */       if (!(result instanceof TileEntityRecordPlayer)) {
/*  104 */         result = fixTileEntity(i, j, k, type, result);
/*      */       }
/*  106 */     } else if (type == Blocks.NOTE_BLOCK) {
/*  107 */       if (!(result instanceof TileEntityNote)) {
/*  108 */         result = fixTileEntity(i, j, k, type, result);
/*      */       }
/*  110 */     } else if (type == Blocks.MOB_SPAWNER) {
/*  111 */       if (!(result instanceof TileEntityMobSpawner)) {
/*  112 */         result = fixTileEntity(i, j, k, type, result);
/*      */       }
/*  114 */     } else if (type == Blocks.SIGN_POST || type == Blocks.WALL_SIGN) {
/*  115 */       if (!(result instanceof TileEntitySign)) {
/*  116 */         result = fixTileEntity(i, j, k, type, result);
/*      */       }
/*  118 */     } else if (type == Blocks.ENDER_CHEST) {
/*  119 */       if (!(result instanceof TileEntityEnderChest)) {
/*  120 */         result = fixTileEntity(i, j, k, type, result);
/*      */       }
/*  122 */     } else if (type == Blocks.BREWING_STAND) {
/*  123 */       if (!(result instanceof TileEntityBrewingStand)) {
/*  124 */         result = fixTileEntity(i, j, k, type, result);
/*      */       }
/*  126 */     } else if (type == Blocks.BEACON) {
/*  127 */       if (!(result instanceof TileEntityBeacon)) {
/*  128 */         result = fixTileEntity(i, j, k, type, result);
/*      */       }
/*  130 */     } else if (type == Blocks.HOPPER && 
/*  131 */       !(result instanceof TileEntityHopper)) {
/*  132 */       result = fixTileEntity(i, j, k, type, result);
/*      */     } 
/*      */ 
/*      */     
/*  136 */     return result;
/*      */   }
/*      */   
/*      */   private TileEntity fixTileEntity(int x, int y, int z, Block type, TileEntity found) {
/*  140 */     getServer().getLogger().severe("Block at " + x + "," + y + "," + z + " is " + Material.getMaterial(Block.getId(type)).toString() + " but has " + found + ". " + "Bukkit will attempt to fix this, but there may be additional damage that we cannot recover.");
/*      */ 
/*      */     
/*  143 */     if (type instanceof IContainer) {
/*  144 */       TileEntity replacement = ((IContainer)type).a(this, getData(x, y, z));
/*  145 */       replacement.world = this;
/*  146 */       setTileEntity(x, y, z, replacement);
/*  147 */       return replacement;
/*      */     } 
/*  149 */     getServer().getLogger().severe("Don't know how to fix for this type... Can't do anything! :(");
/*  150 */     return found;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean canSpawn(int x, int z) {
/*  155 */     if (this.generator != null) {
/*  156 */       return this.generator.canSpawn((World)getWorld(), x, z);
/*      */     }
/*  158 */     return this.worldProvider.canSpawn(x, z);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void doTick() {
/*  164 */     super.doTick();
/*  165 */     if (getWorldData().isHardcore() && this.difficulty != EnumDifficulty.HARD) {
/*  166 */       this.difficulty = EnumDifficulty.HARD;
/*      */     }
/*      */     
/*  169 */     this.worldProvider.e.b();
/*  170 */     if (everyoneDeeplySleeping()) {
/*  171 */       if (getGameRules().getBoolean("doDaylightCycle")) {
/*  172 */         long i = this.worldData.getDayTime() + 24000L;
/*      */         
/*  174 */         this.worldData.setDayTime(i - i % 24000L);
/*      */       } 
/*      */       
/*  177 */       d();
/*      */     } 
/*      */     
/*  180 */     this.methodProfiler.a("mobSpawner");
/*      */     
/*  182 */     long time = this.worldData.getTime();
/*  183 */     if (getGameRules().getBoolean("doMobSpawning") && (this.allowMonsters || this.allowAnimals) && this instanceof WorldServer && this.players.size() > 0) {
/*  184 */       this.timings.mobSpawn.startTiming();
/*  185 */       this.R.spawnEntities(this, (this.allowMonsters && this.ticksPerMonsterSpawns != 0L && time % this.ticksPerMonsterSpawns == 0L), (this.allowAnimals && this.ticksPerAnimalSpawns != 0L && time % this.ticksPerAnimalSpawns == 0L), (this.worldData.getTime() % 400L == 0L));
/*  186 */       this.timings.mobSpawn.stopTiming();
/*      */     } 
/*      */ 
/*      */     
/*  190 */     this.timings.doChunkUnload.startTiming();
/*  191 */     this.methodProfiler.c("chunkSource");
/*  192 */     this.chunkProvider.unloadChunks();
/*  193 */     int j = a(1.0F);
/*      */     
/*  195 */     if (j != this.j) {
/*  196 */       this.j = j;
/*      */     }
/*      */     
/*  199 */     this.worldData.setTime(this.worldData.getTime() + 1L);
/*  200 */     if (getGameRules().getBoolean("doDaylightCycle")) {
/*  201 */       this.worldData.setDayTime(this.worldData.getDayTime() + 1L);
/*      */     }
/*      */     
/*  204 */     this.timings.doChunkUnload.stopTiming();
/*  205 */     this.methodProfiler.c("tickPending");
/*  206 */     this.timings.doTickPending.startTiming();
/*  207 */     a(false);
/*  208 */     this.timings.doTickPending.stopTiming();
/*  209 */     this.methodProfiler.c("tickBlocks");
/*  210 */     this.timings.doTickTiles.startTiming();
/*  211 */     g();
/*  212 */     this.timings.doTickTiles.stopTiming();
/*  213 */     this.spigotConfig.antiXrayInstance.flushUpdates(this);
/*  214 */     this.methodProfiler.c("chunkMap");
/*  215 */     this.timings.doChunkMap.startTiming();
/*  216 */     this.manager.flush();
/*  217 */     this.timings.doChunkMap.stopTiming();
/*  218 */     this.methodProfiler.c("village");
/*  219 */     this.timings.doVillages.startTiming();
/*  220 */     this.villages.tick();
/*  221 */     this.siegeManager.a();
/*  222 */     this.timings.doVillages.stopTiming();
/*  223 */     this.methodProfiler.c("portalForcer");
/*  224 */     this.timings.doPortalForcer.startTiming();
/*  225 */     this.Q.a(getTime());
/*  226 */     this.timings.doPortalForcer.stopTiming();
/*  227 */     this.methodProfiler.b();
/*  228 */     this.timings.doSounds.startTiming();
/*  229 */     Z();
/*  230 */     this.timings.doSounds.stopTiming();
/*      */     
/*  232 */     this.timings.doChunkGC.startTiming();
/*  233 */     getWorld().processChunkGC();
/*  234 */     this.timings.doChunkGC.stopTiming();
/*      */   }
/*      */   
/*      */   public BiomeMeta a(EnumCreatureType enumcreaturetype, int i, int j, int k) {
/*  238 */     List list = L().getMobsFor(enumcreaturetype, i, j, k);
/*      */     
/*  240 */     return (list != null && !list.isEmpty()) ? (BiomeMeta)WeightedRandom.a(this.random, list) : null;
/*      */   }
/*      */   
/*      */   public void everyoneSleeping() {
/*  244 */     this.O = !this.players.isEmpty();
/*  245 */     Iterator<EntityHuman> iterator = this.players.iterator();
/*      */     
/*  247 */     while (iterator.hasNext()) {
/*  248 */       EntityHuman entityhuman = iterator.next();
/*      */       
/*  250 */       if (!entityhuman.isSleeping() && !entityhuman.fauxSleeping) {
/*  251 */         this.O = false;
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void d() {
/*  258 */     this.O = false;
/*  259 */     Iterator<EntityHuman> iterator = this.players.iterator();
/*      */     
/*  261 */     while (iterator.hasNext()) {
/*  262 */       EntityHuman entityhuman = iterator.next();
/*      */       
/*  264 */       if (entityhuman.isSleeping()) {
/*  265 */         entityhuman.a(false, false, true);
/*      */       }
/*      */     } 
/*      */     
/*  269 */     Y();
/*      */   }
/*      */ 
/*      */   
/*      */   private void Y() {
/*  274 */     WeatherChangeEvent weather = new WeatherChangeEvent((World)getWorld(), false);
/*  275 */     getServer().getPluginManager().callEvent((Event)weather);
/*      */     
/*  277 */     ThunderChangeEvent thunder = new ThunderChangeEvent((World)getWorld(), false);
/*  278 */     getServer().getPluginManager().callEvent((Event)thunder);
/*  279 */     if (!weather.isCancelled()) {
/*  280 */       this.worldData.setWeatherDuration(0);
/*  281 */       this.worldData.setStorm(false);
/*      */     } 
/*  283 */     if (!thunder.isCancelled()) {
/*  284 */       this.worldData.setThunderDuration(0);
/*  285 */       this.worldData.setThundering(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean everyoneDeeplySleeping() {
/*  291 */     if (this.O && !this.isStatic) {
/*  292 */       EntityHuman entityhuman; Iterator<EntityHuman> iterator = this.players.iterator();
/*      */ 
/*      */       
/*  295 */       boolean foundActualSleepers = false;
/*      */ 
/*      */ 
/*      */       
/*      */       do {
/*  300 */         if (!iterator.hasNext()) {
/*  301 */           return foundActualSleepers;
/*      */         }
/*      */         
/*  304 */         entityhuman = iterator.next();
/*      */         
/*  306 */         if (!entityhuman.isDeeplySleeping())
/*  307 */           continue;  foundActualSleepers = true;
/*      */       }
/*  309 */       while (entityhuman.isDeeplySleeping() || entityhuman.fauxSleeping);
/*      */ 
/*      */       
/*  312 */       return false;
/*      */     } 
/*  314 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void g() {
/*  319 */     super.g();
/*  320 */     int i = 0;
/*  321 */     int j = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  326 */     for (TLongShortIterator iter = this.chunkTickList.iterator(); iter.hasNext(); ) {
/*  327 */       iter.advance();
/*  328 */       long chunkCoord = iter.key();
/*  329 */       int chunkX = World.keyToX(chunkCoord);
/*  330 */       int chunkZ = World.keyToZ(chunkCoord);
/*      */       
/*  332 */       if (!isChunkLoaded(chunkX, chunkZ) || this.chunkProviderServer.unloadQueue.contains(chunkX, chunkZ)) {
/*      */         
/*  334 */         iter.remove();
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  339 */       int k = chunkX * 16;
/*  340 */       int l = chunkZ * 16;
/*      */       
/*  342 */       this.methodProfiler.a("getChunk");
/*  343 */       Chunk chunk = getChunkAt(chunkX, chunkZ);
/*      */ 
/*      */       
/*  346 */       a(k, l, chunk);
/*  347 */       this.methodProfiler.c("tickChunk");
/*  348 */       chunk.b(false);
/*  349 */       this.methodProfiler.c("thunder");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  355 */       if (!this.paperSpigotConfig.disableThunder && this.random.nextInt(100000) == 0 && Q() && P()) {
/*  356 */         this.k = this.k * 3 + 1013904223;
/*  357 */         int i1 = this.k >> 2;
/*  358 */         int m = k + (i1 & 0xF);
/*  359 */         int n = l + (i1 >> 8 & 0xF);
/*  360 */         int l1 = h(m, n);
/*  361 */         if (isRainingAt(m, l1, n)) {
/*  362 */           strikeLightning(new EntityLightning(this, m, l1, n));
/*      */         }
/*      */       } 
/*      */       
/*  366 */       this.methodProfiler.c("iceandsnow");
/*  367 */       if (!this.paperSpigotConfig.disableIceAndSnow && this.random.nextInt(16) == 0) {
/*  368 */         this.k = this.k * 3 + 1013904223;
/*  369 */         int i1 = this.k >> 2;
/*  370 */         int m = i1 & 0xF;
/*  371 */         int n = i1 >> 8 & 0xF;
/*  372 */         int l1 = h(m + k, n + l);
/*  373 */         if (s(m + k, l1 - 1, n + l)) {
/*      */           
/*  375 */           BlockState blockState = getWorld().getBlockAt(m + k, l1 - 1, n + l).getState();
/*  376 */           blockState.setTypeId(Block.getId(Blocks.ICE));
/*      */           
/*  378 */           BlockFormEvent iceBlockForm = new BlockFormEvent(blockState.getBlock(), blockState);
/*  379 */           getServer().getPluginManager().callEvent((Event)iceBlockForm);
/*  380 */           if (!iceBlockForm.isCancelled()) {
/*  381 */             blockState.update(true);
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  386 */         if (Q() && e(m + k, l1, n + l, true)) {
/*      */           
/*  388 */           BlockState blockState = getWorld().getBlockAt(m + k, l1, n + l).getState();
/*  389 */           blockState.setTypeId(Block.getId(Blocks.SNOW));
/*      */           
/*  391 */           BlockFormEvent snow = new BlockFormEvent(blockState.getBlock(), blockState);
/*  392 */           getServer().getPluginManager().callEvent((Event)snow);
/*  393 */           if (!snow.isCancelled()) {
/*  394 */             blockState.update(true);
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  399 */         if (Q()) {
/*  400 */           BiomeBase biomebase = getBiome(m + k, n + l);
/*      */           
/*  402 */           if (biomebase.e()) {
/*  403 */             getType(m + k, l1 - 1, n + l).l(this, m + k, l1 - 1, n + l);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  408 */       this.methodProfiler.c("tickBlocks");
/*  409 */       ChunkSection[] achunksection = chunk.getSections();
/*      */       
/*  411 */       int j1 = achunksection.length;
/*      */       
/*  413 */       for (int k1 = 0; k1 < j1; k1++) {
/*  414 */         ChunkSection chunksection = achunksection[k1];
/*      */         
/*  416 */         if (chunksection != null && chunksection.shouldTick()) {
/*  417 */           for (int i2 = 0; i2 < 3; i2++) {
/*  418 */             this.k = this.k * 3 + 1013904223;
/*  419 */             int j2 = this.k >> 2;
/*  420 */             int k2 = j2 & 0xF;
/*  421 */             int l2 = j2 >> 8 & 0xF;
/*  422 */             int i3 = j2 >> 16 & 0xF;
/*      */             
/*  424 */             j++;
/*  425 */             Block block = chunksection.getTypeId(k2, i3, l2);
/*      */             
/*  427 */             if (block.isTicking()) {
/*  428 */               i++;
/*  429 */               this.growthOdds = (iter.value() < 1) ? this.modifiedOdds : 100.0F;
/*  430 */               block.a(this, k2 + k, i3 + chunksection.getYPosition(), l2 + l, this.random);
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/*  436 */       this.methodProfiler.b();
/*      */     } 
/*      */     
/*  439 */     if (this.spigotConfig.clearChunksOnTick)
/*      */     {
/*  441 */       this.chunkTickList.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean a(int i, int j, int k, Block block) {
/*  447 */     NextTickListEntry nextticklistentry = new NextTickListEntry(i, j, k, block);
/*      */     
/*  449 */     return this.V.contains(nextticklistentry);
/*      */   }
/*      */   
/*      */   public void a(int i, int j, int k, Block block, int l) {
/*  453 */     a(i, j, k, block, l, 0);
/*      */   }
/*      */   
/*      */   public void a(int i, int j, int k, Block block, int l, int i1) {
/*  457 */     NextTickListEntry nextticklistentry = new NextTickListEntry(i, j, k, block);
/*  458 */     byte b0 = 0;
/*      */     
/*  460 */     if (this.d && block.getMaterial() != Material.AIR) {
/*  461 */       if (block.L()) {
/*  462 */         b0 = 8;
/*  463 */         if (b(nextticklistentry.a - b0, nextticklistentry.b - b0, nextticklistentry.c - b0, nextticklistentry.a + b0, nextticklistentry.b + b0, nextticklistentry.c + b0)) {
/*  464 */           Block block1 = getType(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c);
/*      */           
/*  466 */           if (block1.getMaterial() != Material.AIR && block1 == nextticklistentry.a()) {
/*  467 */             block1.a(this, nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, this.random);
/*      */           }
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  474 */       l = 1;
/*      */     } 
/*      */     
/*  477 */     if (b(i - b0, j - b0, k - b0, i + b0, j + b0, k + b0)) {
/*  478 */       if (block.getMaterial() != Material.AIR) {
/*  479 */         nextticklistentry.a(l + this.worldData.getTime());
/*  480 */         nextticklistentry.a(i1);
/*      */       } 
/*      */       
/*  483 */       if (!this.N.contains(nextticklistentry)) {
/*  484 */         this.N.add(nextticklistentry);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void b(int i, int j, int k, Block block, int l, int i1) {
/*  490 */     NextTickListEntry nextticklistentry = new NextTickListEntry(i, j, k, block);
/*      */     
/*  492 */     nextticklistentry.a(i1);
/*  493 */     if (block.getMaterial() != Material.AIR) {
/*  494 */       nextticklistentry.a(l + this.worldData.getTime());
/*      */     }
/*      */     
/*  497 */     if (!this.N.contains(nextticklistentry)) {
/*  498 */       this.N.add(nextticklistentry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tickEntities() {
/*  508 */     i();
/*      */ 
/*      */     
/*  511 */     super.tickEntities();
/*  512 */     this.spigotConfig.currentPrimedTnt = 0;
/*      */   }
/*      */   
/*      */   public void i() {
/*  516 */     this.emptyTime = 0;
/*      */   }
/*      */   
/*      */   public boolean a(boolean flag) {
/*  520 */     int i = this.N.size();
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
/*  536 */     if (i > this.paperSpigotConfig.tickNextTickListCap) {
/*  537 */       i = this.paperSpigotConfig.tickNextTickListCap;
/*      */     }
/*      */ 
/*      */     
/*  541 */     this.methodProfiler.a("cleaning");
/*      */ 
/*      */ 
/*      */     
/*  545 */     for (int j = 0; j < i; j++) {
/*  546 */       NextTickListEntry nextticklistentry = (NextTickListEntry)this.N.first();
/*  547 */       if (!flag && nextticklistentry.d > this.worldData.getTime()) {
/*      */         break;
/*      */       }
/*      */       
/*  551 */       this.N.remove(nextticklistentry);
/*  552 */       this.V.add(nextticklistentry);
/*      */     } 
/*      */ 
/*      */     
/*  556 */     if (this.paperSpigotConfig.tickNextTickListCapIgnoresRedstone) {
/*  557 */       Iterator<NextTickListEntry> iterator1 = this.N.iterator();
/*  558 */       while (iterator1.hasNext()) {
/*  559 */         NextTickListEntry next = iterator1.next();
/*  560 */         if (!flag && next.d > this.worldData.getTime()) {
/*      */           break;
/*      */         }
/*      */         
/*  564 */         if (next.a().isPowerSource() || next.a() instanceof IContainer) {
/*  565 */           iterator1.remove();
/*  566 */           this.V.add(next);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  572 */     this.methodProfiler.b();
/*  573 */     this.methodProfiler.a("ticking");
/*  574 */     Iterator<NextTickListEntry> iterator = this.V.iterator();
/*      */     
/*  576 */     while (iterator.hasNext()) {
/*  577 */       NextTickListEntry nextticklistentry = iterator.next();
/*  578 */       iterator.remove();
/*  579 */       byte b0 = 0;
/*      */       
/*  581 */       if (b(nextticklistentry.a - b0, nextticklistentry.b - b0, nextticklistentry.c - b0, nextticklistentry.a + b0, nextticklistentry.b + b0, nextticklistentry.c + b0)) {
/*  582 */         Block block = getType(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c);
/*      */         
/*  584 */         if (block.getMaterial() != Material.AIR && Block.a(block, nextticklistentry.a()))
/*      */           try {
/*  586 */             block.a(this, nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, this.random);
/*  587 */           } catch (Throwable throwable) {
/*  588 */             byte b; CrashReport crashreport = CrashReport.a(throwable, "Exception while ticking a block");
/*  589 */             CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being ticked");
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/*  594 */               b = getData(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c);
/*  595 */             } catch (Throwable throwable1) {
/*  596 */               b = -1;
/*      */             } 
/*      */             
/*  599 */             CrashReportSystemDetails.a(crashreportsystemdetails, nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, block, b);
/*  600 */             throw new ReportedException(crashreport);
/*      */           }  
/*      */         continue;
/*      */       } 
/*  604 */       a(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, nextticklistentry.a(), 0);
/*      */     } 
/*      */ 
/*      */     
/*  608 */     this.methodProfiler.b();
/*  609 */     this.V.clear();
/*  610 */     return !this.N.isEmpty();
/*      */   }
/*      */ 
/*      */   
/*      */   public List a(Chunk chunk, boolean flag) {
/*  615 */     ArrayList<NextTickListEntry> arraylist = null;
/*  616 */     ChunkCoordIntPair chunkcoordintpair = chunk.l();
/*  617 */     int i = (chunkcoordintpair.x << 4) - 2;
/*  618 */     int j = i + 16 + 2;
/*  619 */     int k = (chunkcoordintpair.z << 4) - 2;
/*  620 */     int l = k + 16 + 2;
/*      */     
/*  622 */     for (int i1 = 0; i1 < 2; i1++) {
/*      */       Iterator<NextTickListEntry> iterator;
/*      */       
/*  625 */       if (i1 == 0) {
/*  626 */         iterator = this.N.iterator();
/*      */       } else {
/*  628 */         iterator = this.V.iterator();
/*  629 */         if (!this.V.isEmpty()) {
/*  630 */           a.debug("toBeTicked = " + this.V.size());
/*      */         }
/*      */       } 
/*      */       
/*  634 */       while (iterator.hasNext()) {
/*  635 */         NextTickListEntry nextticklistentry = iterator.next();
/*      */         
/*  637 */         if (nextticklistentry.a >= i && nextticklistentry.a < j && nextticklistentry.c >= k && nextticklistentry.c < l) {
/*  638 */           if (flag) {
/*  639 */             iterator.remove();
/*      */           }
/*      */           
/*  642 */           if (arraylist == null) {
/*  643 */             arraylist = new ArrayList();
/*      */           }
/*      */           
/*  646 */           arraylist.add(nextticklistentry);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  651 */     return arraylist;
/*      */   }
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
/*      */   protected IChunkProvider j() {
/*      */     NormalChunkGenerator normalChunkGenerator;
/*  669 */     IChunkLoader ichunkloader = this.dataManager.createChunkLoader(this.worldProvider);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  674 */     if (this.generator != null) {
/*  675 */       CustomChunkGenerator customChunkGenerator = new CustomChunkGenerator(this, getSeed(), this.generator);
/*  676 */     } else if (this.worldProvider instanceof WorldProviderHell) {
/*  677 */       NetherChunkGenerator netherChunkGenerator = new NetherChunkGenerator(this, getSeed());
/*  678 */     } else if (this.worldProvider instanceof WorldProviderTheEnd) {
/*  679 */       SkyLandsChunkGenerator skyLandsChunkGenerator = new SkyLandsChunkGenerator(this, getSeed());
/*      */     } else {
/*  681 */       normalChunkGenerator = new NormalChunkGenerator(this, getSeed());
/*      */     } 
/*      */     
/*  684 */     this.chunkProviderServer = new ChunkProviderServer(this, ichunkloader, (IChunkProvider)normalChunkGenerator);
/*      */ 
/*      */     
/*  687 */     return this.chunkProviderServer;
/*      */   }
/*      */   
/*      */   public List getTileEntities(int i, int j, int k, int l, int i1, int j1) {
/*  691 */     ArrayList<TileEntity> arraylist = new ArrayList();
/*      */ 
/*      */     
/*  694 */     for (int chunkX = i >> 4; chunkX <= l - 1 >> 4; chunkX++) {
/*  695 */       for (int chunkZ = k >> 4; chunkZ <= j1 - 1 >> 4; chunkZ++) {
/*  696 */         Chunk chunk = getChunkAt(chunkX, chunkZ);
/*  697 */         if (chunk != null)
/*      */         {
/*      */ 
/*      */           
/*  701 */           for (Object te : chunk.tileEntities.values()) {
/*  702 */             TileEntity tileentity = (TileEntity)te;
/*  703 */             if (tileentity.x >= i && tileentity.y >= j && tileentity.z >= k && tileentity.x < l && tileentity.y < i1 && tileentity.z < j1) {
/*  704 */               arraylist.add(tileentity);
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  711 */     return arraylist;
/*      */   }
/*      */   
/*      */   public boolean a(EntityHuman entityhuman, int i, int j, int k) {
/*  715 */     return !this.server.a(this, i, j, k, entityhuman);
/*      */   }
/*      */   
/*      */   protected void a(WorldSettings worldsettings) {
/*  719 */     if (this.entitiesById == null) {
/*  720 */       this.entitiesById = new IntHashMap();
/*      */     }
/*      */     
/*  723 */     if (this.N == null) {
/*  724 */       this.N = new HashTreeSet();
/*      */     }
/*      */     
/*  727 */     b(worldsettings);
/*  728 */     super.a(worldsettings);
/*      */   }
/*      */   
/*      */   protected void b(WorldSettings worldsettings) {
/*  732 */     if (!this.worldProvider.e()) {
/*  733 */       this.worldData.setSpawn(0, this.worldProvider.getSeaLevel(), 0);
/*      */     } else {
/*  735 */       this.isLoading = true;
/*  736 */       WorldChunkManager worldchunkmanager = this.worldProvider.e;
/*  737 */       List list = worldchunkmanager.a();
/*  738 */       Random random = new Random(getSeed());
/*  739 */       ChunkPosition chunkposition = worldchunkmanager.a(0, 0, 256, list, random);
/*  740 */       int i = 0;
/*  741 */       int j = this.worldProvider.getSeaLevel();
/*  742 */       int k = 0;
/*      */ 
/*      */       
/*  745 */       if (this.generator != null) {
/*  746 */         Random rand = new Random(getSeed());
/*  747 */         Location spawn = this.generator.getFixedSpawnLocation((World)getWorld(), rand);
/*      */         
/*  749 */         if (spawn != null) {
/*  750 */           if (spawn.getWorld() != getWorld()) {
/*  751 */             throw new IllegalStateException("Cannot set spawn point for " + this.worldData.getName() + " to be in another world (" + spawn.getWorld().getName() + ")");
/*      */           }
/*  753 */           this.worldData.setSpawn(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
/*  754 */           this.isLoading = false;
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */       
/*  761 */       if (chunkposition != null) {
/*  762 */         i = chunkposition.x;
/*  763 */         k = chunkposition.z;
/*      */       } else {
/*  765 */         a.warn("Unable to find spawn biome");
/*      */       } 
/*      */       
/*  768 */       int l = 0;
/*      */       
/*  770 */       while (!canSpawn(i, k)) {
/*  771 */         i += random.nextInt(64) - random.nextInt(64);
/*  772 */         k += random.nextInt(64) - random.nextInt(64);
/*  773 */         l++;
/*  774 */         if (l == 1000) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */       
/*  779 */       this.worldData.setSpawn(i, j, k);
/*  780 */       this.isLoading = false;
/*  781 */       if (worldsettings.c()) {
/*  782 */         k();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void k() {
/*  788 */     WorldGenBonusChest worldgenbonuschest = new WorldGenBonusChest(U, 10);
/*      */     
/*  790 */     for (int i = 0; i < 10; i++) {
/*  791 */       int j = this.worldData.c() + this.random.nextInt(6) - this.random.nextInt(6);
/*  792 */       int k = this.worldData.e() + this.random.nextInt(6) - this.random.nextInt(6);
/*  793 */       int l = i(j, k) + 1;
/*      */       
/*  795 */       if (worldgenbonuschest.generate(this, this.random, j, l, k)) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public ChunkCoordinates getDimensionSpawn() {
/*  802 */     return this.worldProvider.h();
/*      */   }
/*      */   
/*      */   public void save(boolean flag, IProgressUpdate iprogressupdate) throws ExceptionWorldConflict {
/*  806 */     if (this.chunkProvider.canSave()) {
/*  807 */       if (iprogressupdate != null) {
/*  808 */         iprogressupdate.a("Saving level");
/*      */       }
/*      */       
/*  811 */       a();
/*  812 */       if (iprogressupdate != null) {
/*  813 */         iprogressupdate.c("Saving chunks");
/*      */       }
/*      */       
/*  816 */       this.chunkProvider.saveChunks(flag, iprogressupdate);
/*      */       
/*  818 */       Collection arraylist = this.chunkProviderServer.a();
/*  819 */       Iterator<Chunk> iterator = arraylist.iterator();
/*      */       
/*  821 */       while (iterator.hasNext()) {
/*  822 */         Chunk chunk = iterator.next();
/*      */         
/*  824 */         if (chunk != null && !this.manager.a(chunk.locX, chunk.locZ)) {
/*  825 */           this.chunkProviderServer.queueUnload(chunk.locX, chunk.locZ);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void flushSave() {
/*  832 */     if (this.chunkProvider.canSave()) {
/*  833 */       this.chunkProvider.c();
/*      */     }
/*      */   }
/*      */   
/*      */   protected void a() throws ExceptionWorldConflict {
/*  838 */     G();
/*  839 */     this.dataManager.saveWorldData(this.worldData, this.server.getPlayerList().t());
/*      */     
/*  841 */     if (!(this instanceof SecondaryWorldServer)) {
/*  842 */       this.worldMaps.a();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void a(Entity entity) {
/*  848 */     super.a(entity);
/*  849 */     this.entitiesById.a(entity.getId(), entity);
/*  850 */     Entity[] aentity = entity.at();
/*      */     
/*  852 */     if (aentity != null) {
/*  853 */       for (int i = 0; i < aentity.length; i++) {
/*  854 */         this.entitiesById.a(aentity[i].getId(), aentity[i]);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void b(Entity entity) {
/*  860 */     super.b(entity);
/*  861 */     this.entitiesById.d(entity.getId());
/*  862 */     Entity[] aentity = entity.at();
/*      */     
/*  864 */     if (aentity != null) {
/*  865 */       for (int i = 0; i < aentity.length; i++) {
/*  866 */         this.entitiesById.d(aentity[i].getId());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public Entity getEntity(int i) {
/*  872 */     return (Entity)this.entitiesById.get(i);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean strikeLightning(Entity entity) {
/*  877 */     LightningStrikeEvent lightning = new LightningStrikeEvent((World)getWorld(), (LightningStrike)entity.getBukkitEntity());
/*  878 */     getServer().getPluginManager().callEvent((Event)lightning);
/*      */     
/*  880 */     if (lightning.isCancelled()) {
/*  881 */       return false;
/*      */     }
/*      */     
/*  884 */     if (super.strikeLightning(entity)) {
/*  885 */       this.server.getPlayerList().sendPacketNearby(entity.locX, entity.locY, entity.locZ, 512.0D, this.dimension, new PacketPlayOutSpawnEntityWeather(entity));
/*      */       
/*  887 */       return true;
/*      */     } 
/*  889 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void broadcastEntityEffect(Entity entity, byte b0) {
/*  894 */     getTracker().sendPacketToEntity(entity, new PacketPlayOutEntityStatus(entity, b0));
/*      */   }
/*      */ 
/*      */   
/*      */   public Explosion createExplosion(Entity entity, double d0, double d1, double d2, float f, boolean flag, boolean flag1) {
/*  899 */     Explosion explosion = super.createExplosion(entity, d0, d1, d2, f, flag, flag1);
/*      */     
/*  901 */     if (explosion.wasCanceled) {
/*  902 */       return explosion;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  913 */     if (!flag1) {
/*  914 */       explosion.blocks.clear();
/*      */     }
/*      */     
/*  917 */     Iterator<EntityHuman> iterator = this.players.iterator();
/*      */     
/*  919 */     while (iterator.hasNext()) {
/*  920 */       EntityHuman entityhuman = iterator.next();
/*      */       
/*  922 */       if (entityhuman.e(d0, d1, d2) < 4096.0D) {
/*  923 */         ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutExplosion(d0, d1, d2, f, explosion.blocks, (Vec3D)explosion.b().get(entityhuman)));
/*      */       }
/*      */     } 
/*      */     
/*  927 */     return explosion;
/*      */   }
/*      */   
/*      */   public void playBlockAction(int i, int j, int k, Block block, int l, int i1) {
/*  931 */     BlockActionData blockactiondata1, blockactiondata = new BlockActionData(i, j, k, block, l, i1);
/*  932 */     Iterator<E> iterator = this.S[this.T].iterator();
/*      */ 
/*      */ 
/*      */     
/*      */     do {
/*  937 */       if (!iterator.hasNext()) {
/*  938 */         this.S[this.T].add((E)blockactiondata);
/*      */         
/*      */         return;
/*      */       } 
/*  942 */       blockactiondata1 = (BlockActionData)iterator.next();
/*  943 */     } while (!blockactiondata1.equals(blockactiondata));
/*      */   }
/*      */ 
/*      */   
/*      */   private void Z() {
/*  948 */     while (!this.S[this.T].isEmpty()) {
/*  949 */       int i = this.T;
/*      */       
/*  951 */       this.T ^= 0x1;
/*  952 */       Iterator<E> iterator = this.S[i].iterator();
/*      */       
/*  954 */       while (iterator.hasNext()) {
/*  955 */         BlockActionData blockactiondata = (BlockActionData)iterator.next();
/*      */         
/*  957 */         if (a(blockactiondata))
/*      */         {
/*  959 */           this.server.getPlayerList().sendPacketNearby(blockactiondata.a(), blockactiondata.b(), blockactiondata.c(), 64.0D, this.dimension, new PacketPlayOutBlockAction(blockactiondata.a(), blockactiondata.b(), blockactiondata.c(), blockactiondata.f(), blockactiondata.d(), blockactiondata.e()));
/*      */         }
/*      */       } 
/*      */       
/*  963 */       this.S[i].clear();
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean a(BlockActionData blockactiondata) {
/*  968 */     Block block = getType(blockactiondata.a(), blockactiondata.b(), blockactiondata.c());
/*      */     
/*  970 */     return (block == blockactiondata.f()) ? block.a(this, blockactiondata.a(), blockactiondata.b(), blockactiondata.c(), blockactiondata.d(), blockactiondata.e()) : false;
/*      */   }
/*      */   
/*      */   public void saveLevel() {
/*  974 */     this.dataManager.a();
/*      */   }
/*      */   
/*      */   protected void o() {
/*  978 */     boolean flag = Q();
/*      */     
/*  980 */     super.o();
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
/* 1001 */     if (flag != Q())
/*      */     {
/* 1003 */       for (int i = 0; i < this.players.size(); i++) {
/* 1004 */         if (((EntityPlayer)this.players.get(i)).world == this) {
/* 1005 */           ((EntityPlayer)this.players.get(i)).setPlayerWeather(!flag ? WeatherType.DOWNFALL : WeatherType.CLEAR, false);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected int p() {
/* 1013 */     return this.server.getPlayerList().s();
/*      */   }
/*      */   
/*      */   public MinecraftServer getMinecraftServer() {
/* 1017 */     return this.server;
/*      */   }
/*      */   
/*      */   public EntityTracker getTracker() {
/* 1021 */     return this.tracker;
/*      */   }
/*      */   
/*      */   public PlayerChunkMap getPlayerChunkMap() {
/* 1025 */     return this.manager;
/*      */   }
/*      */   
/*      */   public PortalTravelAgent getTravelAgent() {
/* 1029 */     return this.Q;
/*      */   }
/*      */   
/*      */   public void a(String s, double d0, double d1, double d2, int i, double d3, double d4, double d5, double d6) {
/* 1033 */     PacketPlayOutWorldParticles packetplayoutworldparticles = new PacketPlayOutWorldParticles(s, (float)d0, (float)d1, (float)d2, (float)d3, (float)d4, (float)d5, (float)d6, i);
/*      */     
/* 1035 */     for (int j = 0; j < this.players.size(); j++) {
/* 1036 */       EntityPlayer entityplayer = this.players.get(j);
/* 1037 */       ChunkCoordinates chunkcoordinates = entityplayer.getChunkCoordinates();
/* 1038 */       double d7 = d0 - chunkcoordinates.x;
/* 1039 */       double d8 = d1 - chunkcoordinates.y;
/* 1040 */       double d9 = d2 - chunkcoordinates.z;
/* 1041 */       double d10 = d7 * d7 + d8 * d8 + d9 * d9;
/*      */       
/* 1043 */       if (d10 <= 256.0D) {
/* 1044 */         entityplayer.playerConnection.sendPacket(packetplayoutworldparticles);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTypeId(int x, int y, int z) {
/* 1051 */     return Block.getId(getType(x, y, z));
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */