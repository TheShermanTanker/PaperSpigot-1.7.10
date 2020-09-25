/*      */ package net.minecraft.server.v1_7_R4;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.ExecutorService;
/*      */ import java.util.concurrent.Executors;
/*      */ import net.minecraft.util.com.google.common.util.concurrent.ThreadFactoryBuilder;
/*      */ import net.minecraft.util.gnu.trove.map.hash.TLongShortHashMap;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.block.BlockState;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.block.CraftBlockState;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.Cancellable;
/*      */ import org.bukkit.event.Event;
/*      */ import org.bukkit.event.block.BlockCanBuildEvent;
/*      */ import org.bukkit.event.block.BlockPhysicsEvent;
/*      */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*      */ import org.bukkit.event.entity.ItemSpawnEvent;
/*      */ import org.bukkit.event.entity.ProjectileLaunchEvent;
/*      */ import org.bukkit.event.weather.ThunderChangeEvent;
/*      */ import org.bukkit.event.weather.WeatherChangeEvent;
/*      */ import org.bukkit.generator.ChunkGenerator;
/*      */ import org.github.paperspigot.PaperSpigotWorldConfig;
/*      */ import org.spigotmc.ActivationRange;
/*      */ import org.spigotmc.AsyncCatcher;
/*      */ import org.spigotmc.SpigotWorldConfig;
/*      */ import org.spigotmc.WorldTileEntityList;
/*      */ 
/*      */ public abstract class World implements IBlockAccess {
/*   44 */   public List entityList = new ArrayList()
/*      */     {
/*      */       
/*      */       public Object remove(int index)
/*      */       {
/*   49 */         guard();
/*   50 */         return super.remove(index);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean remove(Object o) {
/*   56 */         guard();
/*   57 */         return super.remove(o);
/*      */       }
/*      */ 
/*      */       
/*      */       private void guard() {
/*   62 */         if (World.this.guardEntityList)
/*      */         {
/*   64 */           throw new ConcurrentModificationException(); } 
/*      */       }
/*      */     };
/*      */   
/*      */   public boolean d;
/*   69 */   protected List f = new ArrayList();
/*   70 */   public Set tileEntityList = (Set)new WorldTileEntityList(this);
/*   71 */   private List a = new ArrayList();
/*   72 */   private List b = new ArrayList();
/*   73 */   public List players = new ArrayList();
/*   74 */   public List i = new ArrayList();
/*   75 */   private long c = 16777215L;
/*      */   public int j;
/*   77 */   protected int k = (new Random()).nextInt();
/*   78 */   protected final int l = 1013904223;
/*      */   protected float m;
/*      */   protected float n;
/*      */   protected float o;
/*      */   protected float p;
/*      */   public int q;
/*      */   public EnumDifficulty difficulty;
/*   85 */   public Random random = new Random();
/*      */   public WorldProvider worldProvider;
/*   87 */   protected List u = new ArrayList();
/*      */   public IChunkProvider chunkProvider;
/*      */   protected final IDataManager dataManager;
/*      */   public WorldData worldData;
/*      */   public boolean isLoading;
/*      */   public PersistentCollection worldMaps;
/*      */   public final PersistentVillage villages;
/*   94 */   protected final VillageSiege siegeManager = new VillageSiege(this);
/*      */   public final MethodProfiler methodProfiler;
/*   96 */   private final Calendar J = Calendar.getInstance();
/*   97 */   public Scoreboard scoreboard = new Scoreboard();
/*      */   
/*      */   public boolean isStatic;
/*      */   
/*      */   private int K;
/*      */   
/*      */   public boolean allowMonsters;
/*      */   public boolean allowAnimals;
/*      */   public boolean captureBlockStates = false;
/*      */   public boolean captureTreeGeneration = false;
/*  107 */   public ArrayList<BlockState> capturedBlockStates = new ArrayList<BlockState>();
/*      */   
/*      */   public long ticksPerAnimalSpawns;
/*      */   
/*      */   public long ticksPerMonsterSpawns;
/*      */   
/*      */   public boolean populating;
/*      */   private int tickPosition;
/*      */   private ArrayList L;
/*      */   private boolean M;
/*      */   int[] I;
/*      */   private boolean guardEntityList;
/*      */   protected final TLongShortHashMap chunkTickList;
/*  120 */   protected float growthOdds = 100.0F;
/*  121 */   protected float modifiedOdds = 100.0F;
/*      */   private final byte chunkTickRadius;
/*      */   public static boolean haveWeSilencedAPhysicsCrash;
/*      */   public static String blockLocation;
/*  125 */   public List<TileEntity> triggerHoppersList = new ArrayList<TileEntity>();
/*  126 */   public ExecutorService lightingExecutor = Executors.newSingleThreadExecutor((new ThreadFactoryBuilder()).setNameFormat("PaperSpigot - Lighting Thread").build());
/*  127 */   public final Map<Explosion.CacheKey, Float> explosionDensityCache = new HashMap<Explosion.CacheKey, Float>(); private final CraftWorld world;
/*      */   public boolean pvpMode;
/*      */   
/*      */   public static long chunkToKey(int x, int z) {
/*  131 */     long k = (x & 0xFFFF0000L) << 16L | (x & 0xFFFFL) << 0L;
/*  132 */     k |= (z & 0xFFFF0000L) << 32L | (z & 0xFFFFL) << 16L;
/*  133 */     return k;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int keyToX(long k) {
/*  138 */     return (int)(k >> 16L & 0xFFFFFFFFFFFF0000L | k & 0xFFFFL);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int keyToZ(long k) {
/*  143 */     return (int)(k >> 32L & 0xFFFF0000L | k >> 16L & 0xFFFFL);
/*      */   }
/*      */ 
/*      */   
/*      */   private void initializeHoppers() {
/*  148 */     if (this.spigotConfig.altHopperTicking) {
/*  149 */       for (TileEntity o : this.triggerHoppersList) {
/*  150 */         o.scheduleTicks();
/*  151 */         if (o instanceof TileEntityHopper) {
/*  152 */           ((TileEntityHopper)o).convertToScheduling();
/*  153 */           ((TileEntityHopper)o).scheduleHopperTick();
/*      */         } 
/*      */       } 
/*      */     }
/*  157 */     this.triggerHoppersList.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateChestAndHoppers(int a, int b, int c) {
/*  163 */     Block block = getType(a, b, c);
/*  164 */     if (block instanceof BlockChest) {
/*  165 */       TileEntity tile = getTileEntity(a, b, c);
/*  166 */       if (tile instanceof TileEntityChest) {
/*  167 */         tile.scheduleTicks();
/*      */       }
/*  169 */       for (int i = 2; i < 6; i++) {
/*      */         
/*  171 */         if (getType(a + Facing.b[i], b, c + Facing.d[i]) == block) {
/*  172 */           tile = getTileEntity(a + Facing.b[i], b, c + Facing.d[i]);
/*  173 */           if (tile instanceof TileEntityChest) {
/*  174 */             tile.scheduleTicks();
/*      */           }
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public BiomeBase getBiome(int i, int j) {
/*  184 */     if (isLoaded(i, 0, j)) {
/*  185 */       Chunk chunk = getChunkAtWorldCoords(i, j);
/*      */       
/*      */       try {
/*  188 */         return chunk.getBiome(i & 0xF, j & 0xF, this.worldProvider.e);
/*  189 */       } catch (Throwable throwable) {
/*  190 */         CrashReport crashreport = CrashReport.a(throwable, "Getting biome");
/*  191 */         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Coordinates of biome request");
/*      */         
/*  193 */         crashreportsystemdetails.a("Location", new CrashReportWorldLocation(this, i, j));
/*  194 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */     } 
/*  197 */     return this.worldProvider.e.getBiome(i, j);
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldChunkManager getWorldChunkManager() {
/*  202 */     return this.worldProvider.e;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean keepSpawnInMemory = true;
/*      */   
/*      */   public ChunkGenerator generator;
/*      */   
/*      */   public final SpigotWorldConfig spigotConfig;
/*      */   
/*      */   public final PaperSpigotWorldConfig paperSpigotConfig;
/*      */   public final SpigotTimings.WorldTimingsHandler timings;
/*      */   
/*      */   public CraftWorld getWorld() {
/*  216 */     return this.world;
/*      */   }
/*      */   
/*      */   public CraftServer getServer() {
/*  220 */     return (CraftServer)Bukkit.getServer();
/*      */   }
/*      */   
/*      */   public Chunk getChunkIfLoaded(int x, int z) {
/*  224 */     return ((ChunkProviderServer)this.chunkProvider).getChunkIfLoaded(x, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public World(IDataManager idatamanager, String s, WorldSettings worldsettings, WorldProvider worldprovider, MethodProfiler methodprofiler, ChunkGenerator gen, org.bukkit.World.Environment env) {
/*  229 */     this.spigotConfig = new SpigotWorldConfig(s);
/*  230 */     this.paperSpigotConfig = new PaperSpigotWorldConfig(s);
/*  231 */     this.generator = gen;
/*  232 */     this.world = new CraftWorld((WorldServer)this, gen, env);
/*  233 */     this.ticksPerAnimalSpawns = getServer().getTicksPerAnimalSpawns();
/*  234 */     this.ticksPerMonsterSpawns = getServer().getTicksPerMonsterSpawns();
/*      */     
/*  236 */     this.keepSpawnInMemory = this.paperSpigotConfig.keepSpawnInMemory;
/*      */     
/*  238 */     this.chunkTickRadius = (byte)((getServer().getViewDistance() < 7) ? getServer().getViewDistance() : 7);
/*  239 */     this.chunkTickList = new TLongShortHashMap(this.spigotConfig.chunksPerTick * 5, 0.7F, Long.MIN_VALUE, -32768);
/*  240 */     this.chunkTickList.setAutoCompactionFactor(0.0F);
/*      */ 
/*      */     
/*  243 */     this.K = this.random.nextInt(12000);
/*  244 */     this.allowMonsters = true;
/*  245 */     this.allowAnimals = true;
/*  246 */     this.L = new ArrayList();
/*  247 */     this.I = new int[32768];
/*  248 */     this.dataManager = idatamanager;
/*  249 */     this.methodProfiler = methodprofiler;
/*  250 */     this.worldMaps = new PersistentCollection(idatamanager);
/*  251 */     this.worldData = idatamanager.getWorldData();
/*  252 */     if (worldprovider != null) {
/*  253 */       this.worldProvider = worldprovider;
/*  254 */     } else if (this.worldData != null && this.worldData.j() != 0) {
/*  255 */       this.worldProvider = WorldProvider.byDimension(this.worldData.j());
/*      */     } else {
/*  257 */       this.worldProvider = WorldProvider.byDimension(0);
/*      */     } 
/*      */     
/*  260 */     if (this.worldData == null) {
/*  261 */       this.worldData = new WorldData(worldsettings, s);
/*      */     } else {
/*  263 */       this.worldData.setName(s);
/*      */     } 
/*      */     
/*  266 */     this.worldProvider.a(this);
/*  267 */     this.chunkProvider = j();
/*  268 */     this.timings = new SpigotTimings.WorldTimingsHandler(this);
/*  269 */     if (!this.worldData.isInitialized()) {
/*      */       try {
/*  271 */         a(worldsettings);
/*  272 */       } catch (Throwable throwable) {
/*  273 */         CrashReport crashreport = CrashReport.a(throwable, "Exception initializing level");
/*      */         
/*      */         try {
/*  276 */           a(crashreport);
/*  277 */         } catch (Throwable throwable1) {}
/*      */ 
/*      */ 
/*      */         
/*  281 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/*  284 */       this.worldData.d(true);
/*      */     } 
/*      */     
/*  287 */     PersistentVillage persistentvillage = (PersistentVillage)this.worldMaps.get(PersistentVillage.class, "villages");
/*      */     
/*  289 */     if (persistentvillage == null) {
/*  290 */       this.villages = new PersistentVillage(this);
/*  291 */       this.worldMaps.a("villages", this.villages);
/*      */     } else {
/*  293 */       this.villages = persistentvillage;
/*  294 */       this.villages.a(this);
/*      */     } 
/*      */     
/*  297 */     B();
/*  298 */     a();
/*      */     
/*  300 */     getServer().addWorld((org.bukkit.World)this.world);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void a(WorldSettings worldsettings) {
/*  306 */     this.worldData.d(true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Block b(int i, int j) {
/*      */     int k;
/*  312 */     for (k = 63; !isEmpty(i, k + 1, j); k++);
/*      */ 
/*      */ 
/*      */     
/*  316 */     return getType(i, k, j);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Block getType(int i, int j, int k) {
/*  322 */     return getType(i, j, k, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Block getType(int i, int j, int k, boolean useCaptured) {
/*  327 */     if (this.captureTreeGeneration && useCaptured) {
/*      */       
/*  329 */       Iterator<BlockState> it = this.capturedBlockStates.iterator();
/*  330 */       while (it.hasNext()) {
/*  331 */         BlockState previous = it.next();
/*  332 */         if (previous.getX() == i && previous.getY() == j && previous.getZ() == k) {
/*  333 */           return CraftMagicNumbers.getBlock(previous.getTypeId());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  338 */     if (i >= -30000000 && k >= -30000000 && i < 30000000 && k < 30000000 && j >= 0 && j < 256) {
/*  339 */       Chunk chunk = null;
/*      */       
/*      */       try {
/*  342 */         chunk = getChunkAt(i >> 4, k >> 4);
/*  343 */         return chunk.getType(i & 0xF, j, k & 0xF);
/*  344 */       } catch (Throwable throwable) {
/*  345 */         CrashReport crashreport = CrashReport.a(throwable, "Exception getting block type in world");
/*  346 */         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Requested block coordinates");
/*      */         
/*  348 */         crashreportsystemdetails.a("Found chunk", Boolean.valueOf((chunk == null)));
/*  349 */         crashreportsystemdetails.a("Location", CrashReportSystemDetails.a(i, j, k));
/*  350 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */     } 
/*  353 */     return Blocks.AIR;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty(int i, int j, int k) {
/*  358 */     return (getType(i, j, k).getMaterial() == Material.AIR);
/*      */   }
/*      */   
/*      */   public boolean isLoaded(int i, int j, int k) {
/*  362 */     return (j >= 0 && j < 256) ? isChunkLoaded(i >> 4, k >> 4) : false;
/*      */   }
/*      */   
/*      */   public boolean areChunksLoaded(int i, int j, int k, int l) {
/*  366 */     return b(i - l, j - l, k - l, i + l, j + l, k + l);
/*      */   }
/*      */   
/*      */   public boolean b(int i, int j, int k, int l, int i1, int j1) {
/*  370 */     if (i1 >= 0 && j < 256) {
/*  371 */       i >>= 4;
/*  372 */       k >>= 4;
/*  373 */       l >>= 4;
/*  374 */       j1 >>= 4;
/*      */       
/*  376 */       for (int k1 = i; k1 <= l; k1++) {
/*  377 */         for (int l1 = k; l1 <= j1; l1++) {
/*  378 */           if (!isChunkLoaded(k1, l1)) {
/*  379 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  384 */       return true;
/*      */     } 
/*  386 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isChunkLoaded(int i, int j) {
/*  391 */     return this.chunkProvider.isChunkLoaded(i, j);
/*      */   }
/*      */   
/*      */   public Chunk getChunkAtWorldCoords(int i, int j) {
/*  395 */     return getChunkAt(i >> 4, j >> 4);
/*      */   }
/*      */   
/*      */   public Chunk getChunkAt(int i, int j) {
/*  399 */     return this.chunkProvider.getOrCreateChunk(i, j);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setTypeAndData(int i, int j, int k, Block block, int l, int i1) {
/*  404 */     if (this.captureTreeGeneration) {
/*  405 */       CraftBlockState craftBlockState; BlockState blockstate = null;
/*  406 */       Iterator<BlockState> it = this.capturedBlockStates.iterator();
/*  407 */       while (it.hasNext()) {
/*  408 */         BlockState previous = it.next();
/*  409 */         if (previous.getX() == i && previous.getY() == j && previous.getZ() == k) {
/*  410 */           blockstate = previous;
/*  411 */           it.remove();
/*      */           break;
/*      */         } 
/*      */       } 
/*  415 */       if (blockstate == null) {
/*  416 */         craftBlockState = CraftBlockState.getBlockState(this, i, j, k, i1);
/*      */       }
/*  418 */       craftBlockState.setTypeId(CraftMagicNumbers.getId(block));
/*  419 */       craftBlockState.setRawData((byte)l);
/*  420 */       this.capturedBlockStates.add(craftBlockState);
/*  421 */       return true;
/*      */     } 
/*  423 */     if (i >= -30000000 && k >= -30000000 && i < 30000000 && k < 30000000) {
/*  424 */       CraftBlockState craftBlockState; if (j < 0)
/*  425 */         return false; 
/*  426 */       if (j >= 256) {
/*  427 */         return false;
/*      */       }
/*  429 */       Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*  430 */       Block block1 = null;
/*      */       
/*  432 */       if ((i1 & 0x1) != 0) {
/*  433 */         block1 = chunk.getType(i & 0xF, j, k & 0xF);
/*      */       }
/*      */ 
/*      */       
/*  437 */       BlockState blockstate = null;
/*  438 */       if (this.captureBlockStates) {
/*  439 */         craftBlockState = CraftBlockState.getBlockState(this, i, j, k, i1);
/*  440 */         this.capturedBlockStates.add(craftBlockState);
/*      */       } 
/*      */ 
/*      */       
/*  444 */       boolean flag = chunk.a(i & 0xF, j, k & 0xF, block, l);
/*      */ 
/*      */       
/*  447 */       if (!flag && this.captureBlockStates) {
/*  448 */         this.capturedBlockStates.remove(craftBlockState);
/*      */       }
/*      */ 
/*      */       
/*  452 */       this.methodProfiler.a("checkLight");
/*  453 */       t(i, j, k);
/*  454 */       this.methodProfiler.b();
/*      */       
/*  456 */       if (flag && !this.captureBlockStates)
/*      */       {
/*  458 */         notifyAndUpdatePhysics(i, j, k, chunk, block1, block, i1);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  465 */       if (this.spigotConfig.altHopperTicking && block1 != null && block1.r() && !block.r()) {
/*  466 */         updateChestAndHoppers(i, j - 1, k);
/*      */       }
/*      */ 
/*      */       
/*  470 */       return flag;
/*      */     } 
/*      */     
/*  473 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void notifyAndUpdatePhysics(int i, int j, int k, Chunk chunk, Block oldBlock, Block newBlock, int flag) {
/*  481 */     if ((flag & 0x2) != 0 && (chunk == null || chunk.isReady())) {
/*  482 */       notify(i, j, k);
/*      */     }
/*      */     
/*  485 */     if ((flag & 0x1) != 0) {
/*  486 */       update(i, j, k, oldBlock);
/*  487 */       if (newBlock.isComplexRedstone()) {
/*  488 */         updateAdjacentComparators(i, j, k, newBlock);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getData(int i, int j, int k) {
/*  496 */     if (this.captureTreeGeneration) {
/*  497 */       Iterator<BlockState> it = this.capturedBlockStates.iterator();
/*  498 */       while (it.hasNext()) {
/*  499 */         BlockState previous = it.next();
/*  500 */         if (previous.getX() == i && previous.getY() == j && previous.getZ() == k) {
/*  501 */           return previous.getRawData();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  506 */     if (i >= -30000000 && k >= -30000000 && i < 30000000 && k < 30000000) {
/*  507 */       if (j < 0)
/*  508 */         return 0; 
/*  509 */       if (j >= 256) {
/*  510 */         return 0;
/*      */       }
/*  512 */       Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*      */       
/*  514 */       i &= 0xF;
/*  515 */       k &= 0xF;
/*  516 */       return chunk.getData(i, j, k);
/*      */     } 
/*      */     
/*  519 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setData(int i, int j, int k, int l, int i1) {
/*  525 */     if (this.captureTreeGeneration) {
/*  526 */       CraftBlockState craftBlockState; BlockState blockstate = null;
/*  527 */       Iterator<BlockState> it = this.capturedBlockStates.iterator();
/*  528 */       while (it.hasNext()) {
/*  529 */         BlockState previous = it.next();
/*  530 */         if (previous.getX() == i && previous.getY() == j && previous.getZ() == k) {
/*  531 */           blockstate = previous;
/*  532 */           it.remove();
/*      */           break;
/*      */         } 
/*      */       } 
/*  536 */       if (blockstate == null) {
/*  537 */         craftBlockState = CraftBlockState.getBlockState(this, i, j, k, i1);
/*      */       }
/*  539 */       craftBlockState.setRawData((byte)l);
/*  540 */       this.capturedBlockStates.add(craftBlockState);
/*  541 */       return true;
/*      */     } 
/*      */     
/*  544 */     if (i >= -30000000 && k >= -30000000 && i < 30000000 && k < 30000000) {
/*  545 */       if (j < 0)
/*  546 */         return false; 
/*  547 */       if (j >= 256) {
/*  548 */         return false;
/*      */       }
/*  550 */       Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*  551 */       int j1 = i & 0xF;
/*  552 */       int k1 = k & 0xF;
/*  553 */       boolean flag = chunk.a(j1, j, k1, l);
/*      */       
/*  555 */       if (flag) {
/*  556 */         Block block = chunk.getType(j1, j, k1);
/*      */         
/*  558 */         if ((i1 & 0x2) != 0 && (!this.isStatic || (i1 & 0x4) == 0) && chunk.isReady()) {
/*  559 */           notify(i, j, k);
/*      */         }
/*      */         
/*  562 */         if (!this.isStatic && (i1 & 0x1) != 0) {
/*  563 */           update(i, j, k, block);
/*  564 */           if (block.isComplexRedstone()) {
/*  565 */             updateAdjacentComparators(i, j, k, block);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  570 */       return flag;
/*      */     } 
/*      */     
/*  573 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setAir(int i, int j, int k) {
/*  578 */     return setTypeAndData(i, j, k, Blocks.AIR, 0, 3);
/*      */   }
/*      */   
/*      */   public boolean setAir(int i, int j, int k, boolean flag) {
/*  582 */     Block block = getType(i, j, k);
/*      */     
/*  584 */     if (block.getMaterial() == Material.AIR) {
/*  585 */       return false;
/*      */     }
/*  587 */     int l = getData(i, j, k);
/*      */     
/*  589 */     triggerEffect(2001, i, j, k, Block.getId(block) + (l << 12));
/*  590 */     if (flag) {
/*  591 */       block.b(this, i, j, k, l, 0);
/*      */     }
/*      */     
/*  594 */     return setTypeAndData(i, j, k, Blocks.AIR, 0, 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setTypeUpdate(int i, int j, int k, Block block) {
/*  599 */     return setTypeAndData(i, j, k, block, 0, 3);
/*      */   }
/*      */   
/*      */   public void notify(int i, int j, int k) {
/*  603 */     for (int l = 0; l < this.u.size(); l++) {
/*  604 */       ((IWorldAccess)this.u.get(l)).a(i, j, k);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void update(int i, int j, int k, Block block) {
/*  610 */     if (this.populating) {
/*      */       return;
/*      */     }
/*      */     
/*  614 */     applyPhysics(i, j, k, block);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void b(int i, int j, int k, int l) {
/*  620 */     if (k > l) {
/*  621 */       int i1 = l;
/*  622 */       l = k;
/*  623 */       k = i1;
/*      */     } 
/*      */     
/*  626 */     if (!this.worldProvider.g) {
/*  627 */       for (int i1 = k; i1 <= l; i1++) {
/*  628 */         updateLight(EnumSkyBlock.SKY, i, i1, j);
/*      */       }
/*      */     }
/*      */     
/*  632 */     c(i, k, j, i, l, j);
/*      */   }
/*      */   
/*      */   public void c(int i, int j, int k, int l, int i1, int j1) {
/*  636 */     for (int k1 = 0; k1 < this.u.size(); k1++) {
/*  637 */       ((IWorldAccess)this.u.get(k1)).a(i, j, k, l, i1, j1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void applyPhysics(int i, int j, int k, Block block) {
/*  642 */     e(i - 1, j, k, block);
/*  643 */     e(i + 1, j, k, block);
/*  644 */     e(i, j - 1, k, block);
/*  645 */     e(i, j + 1, k, block);
/*  646 */     e(i, j, k - 1, block);
/*  647 */     e(i, j, k + 1, block);
/*  648 */     this.spigotConfig.antiXrayInstance.updateNearbyBlocks(this, i, j, k);
/*      */   }
/*      */   
/*      */   public void b(int i, int j, int k, Block block, int l) {
/*  652 */     if (l != 4) {
/*  653 */       e(i - 1, j, k, block);
/*      */     }
/*      */     
/*  656 */     if (l != 5) {
/*  657 */       e(i + 1, j, k, block);
/*      */     }
/*      */     
/*  660 */     if (l != 0) {
/*  661 */       e(i, j - 1, k, block);
/*      */     }
/*      */     
/*  664 */     if (l != 1) {
/*  665 */       e(i, j + 1, k, block);
/*      */     }
/*      */     
/*  668 */     if (l != 2) {
/*  669 */       e(i, j, k - 1, block);
/*      */     }
/*      */     
/*  672 */     if (l != 3) {
/*  673 */       e(i, j, k + 1, block);
/*      */     }
/*      */   }
/*      */   
/*      */   public void e(int i, int j, int k, Block block) {
/*  678 */     if (!this.isStatic) {
/*  679 */       Block block1 = getType(i, j, k);
/*      */ 
/*      */       
/*      */       try {
/*  683 */         CraftWorld world = ((WorldServer)this).getWorld();
/*  684 */         if (world != null) {
/*  685 */           BlockPhysicsEvent event = new BlockPhysicsEvent(world.getBlockAt(i, j, k), CraftMagicNumbers.getId(block));
/*  686 */           getServer().getPluginManager().callEvent((Event)event);
/*      */           
/*  688 */           if (event.isCancelled()) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  694 */         block1.doPhysics(this, i, j, k, block);
/*  695 */       } catch (StackOverflowError stackoverflowerror) {
/*  696 */         haveWeSilencedAPhysicsCrash = true;
/*  697 */         blockLocation = i + ", " + j + ", " + k;
/*  698 */       } catch (Throwable throwable) {
/*  699 */         byte b; CrashReport crashreport = CrashReport.a(throwable, "Exception while updating neighbours");
/*  700 */         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being updated");
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  705 */           b = getData(i, j, k);
/*  706 */         } catch (Throwable throwable1) {
/*  707 */           b = -1;
/*      */         } 
/*      */         
/*  710 */         crashreportsystemdetails.a("Source block type", new CrashReportSourceBlockType(this, block));
/*  711 */         CrashReportSystemDetails.a(crashreportsystemdetails, i, j, k, block1, b);
/*  712 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean a(int i, int j, int k, Block block) {
/*  718 */     return false;
/*      */   }
/*      */   
/*      */   public boolean i(int i, int j, int k) {
/*  722 */     return getChunkAt(i >> 4, k >> 4).d(i & 0xF, j, k & 0xF);
/*      */   }
/*      */   
/*      */   public int j(int i, int j, int k) {
/*  726 */     if (j < 0) {
/*  727 */       return 0;
/*      */     }
/*  729 */     if (j >= 256) {
/*  730 */       j = 255;
/*      */     }
/*      */     
/*  733 */     return getChunkAt(i >> 4, k >> 4).b(i & 0xF, j, k & 0xF, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLightLevel(int i, int j, int k) {
/*  738 */     return b(i, j, k, true);
/*      */   }
/*      */   
/*      */   public int b(int i, int j, int k, boolean flag) {
/*  742 */     if (i >= -30000000 && k >= -30000000 && i < 30000000 && k < 30000000) {
/*  743 */       if (flag && getType(i, j, k).n()) {
/*  744 */         int l = b(i, j + 1, k, false);
/*  745 */         int i1 = b(i + 1, j, k, false);
/*  746 */         int j1 = b(i - 1, j, k, false);
/*  747 */         int k1 = b(i, j, k + 1, false);
/*  748 */         int l1 = b(i, j, k - 1, false);
/*      */         
/*  750 */         if (i1 > l) {
/*  751 */           l = i1;
/*      */         }
/*      */         
/*  754 */         if (j1 > l) {
/*  755 */           l = j1;
/*      */         }
/*      */         
/*  758 */         if (k1 > l) {
/*  759 */           l = k1;
/*      */         }
/*      */         
/*  762 */         if (l1 > l) {
/*  763 */           l = l1;
/*      */         }
/*      */         
/*  766 */         return l;
/*  767 */       }  if (j < 0) {
/*  768 */         return 0;
/*      */       }
/*  770 */       if (j >= 256) {
/*  771 */         j = 255;
/*      */       }
/*      */       
/*  774 */       Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*      */       
/*  776 */       i &= 0xF;
/*  777 */       k &= 0xF;
/*  778 */       return chunk.b(i, j, k, this.j);
/*      */     } 
/*      */     
/*  781 */     return 15;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getHighestBlockYAt(int i, int j) {
/*  786 */     if (i >= -30000000 && j >= -30000000 && i < 30000000 && j < 30000000) {
/*  787 */       if (!isChunkLoaded(i >> 4, j >> 4)) {
/*  788 */         return 0;
/*      */       }
/*  790 */       Chunk chunk = getChunkAt(i >> 4, j >> 4);
/*      */       
/*  792 */       return chunk.b(i & 0xF, j & 0xF);
/*      */     } 
/*      */     
/*  795 */     return 64;
/*      */   }
/*      */ 
/*      */   
/*      */   public int g(int i, int j) {
/*  800 */     if (i >= -30000000 && j >= -30000000 && i < 30000000 && j < 30000000) {
/*  801 */       if (!isChunkLoaded(i >> 4, j >> 4)) {
/*  802 */         return 0;
/*      */       }
/*  804 */       Chunk chunk = getChunkAt(i >> 4, j >> 4);
/*      */       
/*  806 */       return chunk.r;
/*      */     } 
/*      */     
/*  809 */     return 64;
/*      */   }
/*      */ 
/*      */   
/*      */   public int b(EnumSkyBlock enumskyblock, int i, int j, int k) {
/*  814 */     if (j < 0) {
/*  815 */       j = 0;
/*      */     }
/*      */     
/*  818 */     if (j >= 256) {
/*  819 */       j = 255;
/*      */     }
/*      */     
/*  822 */     if (i >= -30000000 && k >= -30000000 && i < 30000000 && k < 30000000) {
/*  823 */       int l = i >> 4;
/*  824 */       int i1 = k >> 4;
/*      */       
/*  826 */       if (!isChunkLoaded(l, i1)) {
/*  827 */         return enumskyblock.c;
/*      */       }
/*  829 */       Chunk chunk = getChunkAt(l, i1);
/*      */       
/*  831 */       return chunk.getBrightness(enumskyblock, i & 0xF, j, k & 0xF);
/*      */     } 
/*      */     
/*  834 */     return enumskyblock.c;
/*      */   }
/*      */ 
/*      */   
/*      */   public void b(EnumSkyBlock enumskyblock, int i, int j, int k, int l) {
/*  839 */     if (i >= -30000000 && k >= -30000000 && i < 30000000 && k < 30000000 && 
/*  840 */       j >= 0 && 
/*  841 */       j < 256 && 
/*  842 */       isChunkLoaded(i >> 4, k >> 4)) {
/*  843 */       Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*      */       
/*  845 */       chunk.a(enumskyblock, i & 0xF, j, k & 0xF, l);
/*      */       
/*  847 */       for (int i1 = 0; i1 < this.u.size(); i1++) {
/*  848 */         ((IWorldAccess)this.u.get(i1)).b(i, j, k);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void m(int i, int j, int k) {
/*  857 */     for (int l = 0; l < this.u.size(); l++) {
/*  858 */       ((IWorldAccess)this.u.get(l)).b(i, j, k);
/*      */     }
/*      */   }
/*      */   
/*      */   public float n(int i, int j, int k) {
/*  863 */     return this.worldProvider.h[getLightLevel(i, j, k)];
/*      */   }
/*      */   
/*      */   public boolean w() {
/*  867 */     return (this.j < 4);
/*      */   }
/*      */   
/*      */   public MovingObjectPosition a(Vec3D vec3d, Vec3D vec3d1) {
/*  871 */     return rayTrace(vec3d, vec3d1, false, false, false);
/*      */   }
/*      */   
/*      */   public MovingObjectPosition rayTrace(Vec3D vec3d, Vec3D vec3d1, boolean flag) {
/*  875 */     return rayTrace(vec3d, vec3d1, flag, false, false);
/*      */   }
/*      */   
/*      */   public MovingObjectPosition rayTrace(Vec3D vec3d, Vec3D vec3d1, boolean flag, boolean flag1, boolean flag2) {
/*  879 */     if (!Double.isNaN(vec3d.a) && !Double.isNaN(vec3d.b) && !Double.isNaN(vec3d.c)) {
/*  880 */       if (!Double.isNaN(vec3d1.a) && !Double.isNaN(vec3d1.b) && !Double.isNaN(vec3d1.c)) {
/*  881 */         int i = MathHelper.floor(vec3d1.a);
/*  882 */         int j = MathHelper.floor(vec3d1.b);
/*  883 */         int k = MathHelper.floor(vec3d1.c);
/*  884 */         int l = MathHelper.floor(vec3d.a);
/*  885 */         int i1 = MathHelper.floor(vec3d.b);
/*  886 */         int j1 = MathHelper.floor(vec3d.c);
/*  887 */         Block block = getType(l, i1, j1);
/*  888 */         int k1 = getData(l, i1, j1);
/*      */         
/*  890 */         if ((!flag1 || block.a(this, l, i1, j1) != null) && block.a(k1, flag)) {
/*  891 */           MovingObjectPosition movingobjectposition = block.a(this, l, i1, j1, vec3d, vec3d1);
/*      */           
/*  893 */           if (movingobjectposition != null) {
/*  894 */             return movingobjectposition;
/*      */           }
/*      */         } 
/*      */         
/*  898 */         MovingObjectPosition movingobjectposition1 = null;
/*      */         
/*  900 */         k1 = 200;
/*      */         
/*  902 */         while (k1-- >= 0) {
/*  903 */           byte b0; if (Double.isNaN(vec3d.a) || Double.isNaN(vec3d.b) || Double.isNaN(vec3d.c)) {
/*  904 */             return null;
/*      */           }
/*      */           
/*  907 */           if (l == i && i1 == j && j1 == k) {
/*  908 */             return flag2 ? movingobjectposition1 : null;
/*      */           }
/*      */           
/*  911 */           boolean flag3 = true;
/*  912 */           boolean flag4 = true;
/*  913 */           boolean flag5 = true;
/*  914 */           double d0 = 999.0D;
/*  915 */           double d1 = 999.0D;
/*  916 */           double d2 = 999.0D;
/*      */           
/*  918 */           if (i > l) {
/*  919 */             d0 = l + 1.0D;
/*  920 */           } else if (i < l) {
/*  921 */             d0 = l + 0.0D;
/*      */           } else {
/*  923 */             flag3 = false;
/*      */           } 
/*      */           
/*  926 */           if (j > i1) {
/*  927 */             d1 = i1 + 1.0D;
/*  928 */           } else if (j < i1) {
/*  929 */             d1 = i1 + 0.0D;
/*      */           } else {
/*  931 */             flag4 = false;
/*      */           } 
/*      */           
/*  934 */           if (k > j1) {
/*  935 */             d2 = j1 + 1.0D;
/*  936 */           } else if (k < j1) {
/*  937 */             d2 = j1 + 0.0D;
/*      */           } else {
/*  939 */             flag5 = false;
/*      */           } 
/*      */           
/*  942 */           double d3 = 999.0D;
/*  943 */           double d4 = 999.0D;
/*  944 */           double d5 = 999.0D;
/*  945 */           double d6 = vec3d1.a - vec3d.a;
/*  946 */           double d7 = vec3d1.b - vec3d.b;
/*  947 */           double d8 = vec3d1.c - vec3d.c;
/*      */           
/*  949 */           if (flag3) {
/*  950 */             d3 = (d0 - vec3d.a) / d6;
/*      */           }
/*      */           
/*  953 */           if (flag4) {
/*  954 */             d4 = (d1 - vec3d.b) / d7;
/*      */           }
/*      */           
/*  957 */           if (flag5) {
/*  958 */             d5 = (d2 - vec3d.c) / d8;
/*      */           }
/*      */           
/*  961 */           boolean flag6 = false;
/*      */ 
/*      */           
/*  964 */           if (d3 < d4 && d3 < d5) {
/*  965 */             if (i > l) {
/*  966 */               b0 = 4;
/*      */             } else {
/*  968 */               b0 = 5;
/*      */             } 
/*      */             
/*  971 */             vec3d.a = d0;
/*  972 */             vec3d.b += d7 * d3;
/*  973 */             vec3d.c += d8 * d3;
/*  974 */           } else if (d4 < d5) {
/*  975 */             if (j > i1) {
/*  976 */               b0 = 0;
/*      */             } else {
/*  978 */               b0 = 1;
/*      */             } 
/*      */             
/*  981 */             vec3d.a += d6 * d4;
/*  982 */             vec3d.b = d1;
/*  983 */             vec3d.c += d8 * d4;
/*      */           } else {
/*  985 */             if (k > j1) {
/*  986 */               b0 = 2;
/*      */             } else {
/*  988 */               b0 = 3;
/*      */             } 
/*      */             
/*  991 */             vec3d.a += d6 * d5;
/*  992 */             vec3d.b += d7 * d5;
/*  993 */             vec3d.c = d2;
/*      */           } 
/*      */           
/*  996 */           Vec3D vec3d2 = Vec3D.a(vec3d.a, vec3d.b, vec3d.c);
/*      */           
/*  998 */           l = (int)(vec3d2.a = MathHelper.floor(vec3d.a));
/*  999 */           if (b0 == 5) {
/* 1000 */             l--;
/* 1001 */             vec3d2.a++;
/*      */           } 
/*      */           
/* 1004 */           i1 = (int)(vec3d2.b = MathHelper.floor(vec3d.b));
/* 1005 */           if (b0 == 1) {
/* 1006 */             i1--;
/* 1007 */             vec3d2.b++;
/*      */           } 
/*      */           
/* 1010 */           j1 = (int)(vec3d2.c = MathHelper.floor(vec3d.c));
/* 1011 */           if (b0 == 3) {
/* 1012 */             j1--;
/* 1013 */             vec3d2.c++;
/*      */           } 
/*      */           
/* 1016 */           Block block1 = getType(l, i1, j1);
/* 1017 */           int l1 = getData(l, i1, j1);
/*      */           
/* 1019 */           if (!flag1 || block1.a(this, l, i1, j1) != null) {
/* 1020 */             if (block1.a(l1, flag)) {
/* 1021 */               MovingObjectPosition movingobjectposition2 = block1.a(this, l, i1, j1, vec3d, vec3d1);
/*      */               
/* 1023 */               if (movingobjectposition2 != null)
/* 1024 */                 return movingobjectposition2; 
/*      */               continue;
/*      */             } 
/* 1027 */             movingobjectposition1 = new MovingObjectPosition(l, i1, j1, b0, vec3d, false);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1032 */         return flag2 ? movingobjectposition1 : null;
/*      */       } 
/* 1034 */       return null;
/*      */     } 
/*      */     
/* 1037 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void makeSound(Entity entity, String s, float f, float f1) {
/* 1042 */     for (int i = 0; i < this.u.size(); i++) {
/* 1043 */       ((IWorldAccess)this.u.get(i)).a(s, entity.locX, entity.locY - entity.height, entity.locZ, f, f1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(EntityHuman entityhuman, String s, float f, float f1) {
/* 1048 */     for (int i = 0; i < this.u.size(); i++) {
/* 1049 */       ((IWorldAccess)this.u.get(i)).a(entityhuman, s, entityhuman.locX, entityhuman.locY - entityhuman.height, entityhuman.locZ, f, f1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void makeSound(double d0, double d1, double d2, String s, float f, float f1) {
/* 1054 */     for (int i = 0; i < this.u.size(); i++) {
/* 1055 */       ((IWorldAccess)this.u.get(i)).a(s, d0, d1, d2, f, f1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(double d0, double d1, double d2, String s, float f, float f1, boolean flag) {}
/*      */   
/*      */   public void a(String s, int i, int j, int k) {
/* 1062 */     for (int l = 0; l < this.u.size(); l++) {
/* 1063 */       ((IWorldAccess)this.u.get(l)).a(s, i, j, k);
/*      */     }
/*      */   }
/*      */   
/*      */   public void addParticle(String s, double d0, double d1, double d2, double d3, double d4, double d5) {
/* 1068 */     for (int i = 0; i < this.u.size(); i++) {
/* 1069 */       ((IWorldAccess)this.u.get(i)).a(s, d0, d1, d2, d3, d4, d5);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean strikeLightning(Entity entity) {
/* 1074 */     this.i.add(entity);
/* 1075 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addEntity(Entity entity) {
/* 1080 */     return addEntity(entity, CreatureSpawnEvent.SpawnReason.DEFAULT);
/*      */   }
/*      */   public boolean addEntity(Entity entity, CreatureSpawnEvent.SpawnReason spawnReason) {
/*      */     ProjectileLaunchEvent projectileLaunchEvent;
/* 1084 */     AsyncCatcher.catchOp("entity add");
/* 1085 */     if (entity == null) return false;
/*      */ 
/*      */     
/* 1088 */     int i = MathHelper.floor(entity.locX / 16.0D);
/* 1089 */     int j = MathHelper.floor(entity.locZ / 16.0D);
/* 1090 */     boolean flag = entity.attachedToPlayer;
/*      */     
/* 1092 */     if (entity instanceof EntityHuman) {
/* 1093 */       flag = true;
/*      */     }
/*      */ 
/*      */     
/* 1097 */     Cancellable event = null;
/* 1098 */     if (entity instanceof EntityLiving && !(entity instanceof EntityPlayer)) {
/* 1099 */       boolean isAnimal = (entity instanceof EntityAnimal || entity instanceof EntityWaterAnimal || entity instanceof EntityGolem);
/* 1100 */       boolean isMonster = (entity instanceof EntityMonster || entity instanceof EntityGhast || entity instanceof EntitySlime);
/*      */       
/* 1102 */       if (spawnReason != CreatureSpawnEvent.SpawnReason.CUSTOM && ((
/* 1103 */         isAnimal && !this.allowAnimals) || (isMonster && !this.allowMonsters))) {
/* 1104 */         entity.dead = true;
/* 1105 */         return false;
/*      */       } 
/*      */ 
/*      */       
/* 1109 */       CreatureSpawnEvent creatureSpawnEvent = CraftEventFactory.callCreatureSpawnEvent((EntityLiving)entity, spawnReason);
/* 1110 */     } else if (entity instanceof EntityItem) {
/* 1111 */       ItemSpawnEvent itemSpawnEvent = CraftEventFactory.callItemSpawnEvent((EntityItem)entity);
/* 1112 */     } else if (entity.getBukkitEntity() instanceof org.bukkit.entity.Projectile) {
/*      */       
/* 1114 */       projectileLaunchEvent = CraftEventFactory.callProjectileLaunchEvent(entity);
/*      */     
/*      */     }
/* 1117 */     else if (entity instanceof EntityExperienceOrb) {
/* 1118 */       EntityExperienceOrb xp = (EntityExperienceOrb)entity;
/* 1119 */       double radius = this.spigotConfig.expMerge;
/* 1120 */       if (radius > 0.0D) {
/* 1121 */         List<Entity> entities = getEntities(entity, entity.boundingBox.grow(radius, radius, radius));
/* 1122 */         for (Entity e : entities) {
/* 1123 */           if (e instanceof EntityExperienceOrb) {
/* 1124 */             EntityExperienceOrb loopItem = (EntityExperienceOrb)e;
/* 1125 */             if (!loopItem.dead) {
/* 1126 */               xp.value += loopItem.value;
/* 1127 */               loopItem.die();
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1134 */     if (projectileLaunchEvent != null && (projectileLaunchEvent.isCancelled() || entity.dead)) {
/* 1135 */       entity.dead = true;
/* 1136 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1140 */     if (!flag && !isChunkLoaded(i, j)) {
/* 1141 */       entity.dead = true;
/* 1142 */       return false;
/*      */     } 
/* 1144 */     if (entity instanceof EntityHuman) {
/* 1145 */       EntityHuman entityhuman = (EntityHuman)entity;
/*      */       
/* 1147 */       this.players.add(entityhuman);
/* 1148 */       everyoneSleeping();
/* 1149 */       b(entity);
/*      */     } 
/*      */     
/* 1152 */     getChunkAt(i, j).a(entity);
/* 1153 */     this.entityList.add(entity);
/* 1154 */     a(entity);
/* 1155 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void a(Entity entity) {
/* 1160 */     for (int i = 0; i < this.u.size(); i++) {
/* 1161 */       ((IWorldAccess)this.u.get(i)).a(entity);
/*      */     }
/*      */     
/* 1164 */     entity.valid = true;
/*      */   }
/*      */   
/*      */   protected void b(Entity entity) {
/* 1168 */     for (int i = 0; i < this.u.size(); i++) {
/* 1169 */       ((IWorldAccess)this.u.get(i)).b(entity);
/*      */     }
/*      */     
/* 1172 */     entity.valid = false;
/*      */   }
/*      */   
/*      */   public void kill(Entity entity) {
/* 1176 */     if (entity.passenger != null) {
/* 1177 */       entity.passenger.mount((Entity)null);
/*      */     }
/*      */     
/* 1180 */     if (entity.vehicle != null) {
/* 1181 */       entity.mount((Entity)null);
/*      */     }
/*      */     
/* 1184 */     entity.die();
/* 1185 */     if (entity instanceof EntityHuman) {
/* 1186 */       this.players.remove(entity);
/*      */       
/* 1188 */       for (Object o : this.worldMaps.c) {
/*      */         
/* 1190 */         if (o instanceof WorldMap) {
/*      */           
/* 1192 */           WorldMap map = (WorldMap)o;
/* 1193 */           map.i.remove(entity);
/* 1194 */           for (Iterator<WorldMapHumanTracker> iter = map.f.iterator(); iter.hasNext();) {
/*      */             
/* 1196 */             if (((WorldMapHumanTracker)iter.next()).trackee == entity)
/*      */             {
/* 1198 */               iter.remove();
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1204 */       everyoneSleeping();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void removeEntity(Entity entity) {
/* 1209 */     AsyncCatcher.catchOp("entity remove");
/* 1210 */     entity.die();
/* 1211 */     if (entity instanceof EntityHuman) {
/* 1212 */       this.players.remove(entity);
/* 1213 */       everyoneSleeping();
/*      */     } 
/*      */     
/* 1216 */     if (!this.guardEntityList) {
/* 1217 */       int i = entity.ah;
/* 1218 */       int j = entity.aj;
/* 1219 */       if (entity.ag && isChunkLoaded(i, j)) {
/* 1220 */         getChunkAt(i, j).b(entity);
/*      */       }
/*      */       
/* 1223 */       int index = this.entityList.indexOf(entity);
/* 1224 */       if (index != -1) {
/* 1225 */         if (index <= this.tickPosition) {
/* 1226 */           this.tickPosition--;
/*      */         }
/* 1228 */         this.entityList.remove(index);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1234 */     b(entity);
/*      */   }
/*      */   
/*      */   public void addIWorldAccess(IWorldAccess iworldaccess) {
/* 1238 */     this.u.add(iworldaccess);
/*      */   }
/*      */   
/*      */   public List getCubes(Entity entity, AxisAlignedBB axisalignedbb) {
/* 1242 */     this.L.clear();
/* 1243 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1244 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1245 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1246 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1247 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1248 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */ 
/*      */     
/* 1251 */     int ystart = (k - 1 < 0) ? 0 : (k - 1);
/* 1252 */     for (int chunkx = i >> 4; chunkx <= j - 1 >> 4; chunkx++) {
/*      */       
/* 1254 */       int cx = chunkx << 4;
/* 1255 */       int chunkz = i1 >> 4; while (true) { if (chunkz <= j1 - 1 >> 4) {
/*      */           
/* 1257 */           Chunk chunk = getChunkIfLoaded(chunkx, chunkz);
/* 1258 */           if (chunk == null)
/*      */           {
/*      */             
/* 1261 */             if (entity.loadChunks) {
/* 1262 */               chunk = entity.world.chunkProvider.getChunkAt(chunkx, chunkz);
/*      */             } else {
/* 1264 */               entity.inUnloadedChunk = true;
/*      */               
/*      */               chunkz++;
/*      */             } 
/*      */           }
/* 1269 */           int cz = chunkz << 4;
/*      */           
/* 1271 */           int xstart = (i < cx) ? cx : i;
/* 1272 */           int xend = (j < cx + 16) ? j : (cx + 16);
/* 1273 */           int zstart = (i1 < cz) ? cz : i1;
/* 1274 */           int zend = (j1 < cz + 16) ? j1 : (cz + 16);
/*      */           
/* 1276 */           for (int x = xstart; x < xend; x++) {
/*      */             
/* 1278 */             for (int z = zstart; z < zend; z++) {
/*      */               
/* 1280 */               for (int y = ystart; y < l; y++) {
/*      */                 
/* 1282 */                 Block block = chunk.getType(x - cx, y, z - cz);
/* 1283 */                 if (block != null)
/*      */                 {
/*      */                   
/* 1286 */                   if (entity.world.paperSpigotConfig.fallingBlocksCollideWithSigns && (entity instanceof EntityTNTPrimed || entity instanceof EntityFallingBlock) && (block instanceof BlockSign || block instanceof BlockFenceGate || block instanceof BlockTorch || block instanceof BlockButtonAbstract || block instanceof BlockLever || block instanceof BlockTripwireHook || block instanceof BlockTripwire)) {
/*      */                     
/* 1288 */                     AxisAlignedBB aabb = AxisAlignedBB.a(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D);
/* 1289 */                     if (axisalignedbb.b(aabb)) this.L.add(aabb); 
/*      */                   } else {
/* 1291 */                     block.a(this, x, y, z, axisalignedbb, this.L, entity);
/*      */                   }  } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } else {
/*      */           break;
/*      */         } 
/*      */         chunkz++; }
/*      */     
/*      */     } 
/* 1302 */     if (entity instanceof EntityItem) return this.L;
/*      */     
/* 1304 */     double d0 = 0.25D;
/* 1305 */     List<Entity> list = getEntities(entity, axisalignedbb.grow(d0, d0, d0));
/*      */     
/* 1307 */     for (int j2 = 0; j2 < list.size(); j2++) {
/* 1308 */       AxisAlignedBB axisalignedbb1 = ((Entity)list.get(j2)).J();
/*      */       
/* 1310 */       if (axisalignedbb1 != null && axisalignedbb1.b(axisalignedbb)) {
/* 1311 */         this.L.add(axisalignedbb1);
/*      */       }
/*      */       
/* 1314 */       axisalignedbb1 = entity.h(list.get(j2));
/* 1315 */       if (axisalignedbb1 != null && axisalignedbb1.b(axisalignedbb)) {
/* 1316 */         this.L.add(axisalignedbb1);
/*      */       }
/*      */     } 
/*      */     
/* 1320 */     return this.L;
/*      */   }
/*      */   
/*      */   public List a(AxisAlignedBB axisalignedbb) {
/* 1324 */     this.L.clear();
/* 1325 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1326 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1327 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1328 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1329 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1330 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */     
/* 1332 */     for (int k1 = i; k1 < j; k1++) {
/* 1333 */       for (int l1 = i1; l1 < j1; l1++) {
/* 1334 */         if (isLoaded(k1, 64, l1)) {
/* 1335 */           for (int i2 = k - 1; i2 < l; i2++) {
/*      */             Block block;
/*      */             
/* 1338 */             if (k1 >= -30000000 && k1 < 30000000 && l1 >= -30000000 && l1 < 30000000) {
/* 1339 */               block = getType(k1, i2, l1);
/*      */             } else {
/* 1341 */               block = Blocks.BEDROCK;
/*      */             } 
/*      */             
/* 1344 */             block.a(this, k1, i2, l1, axisalignedbb, this.L, (Entity)null);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1350 */     return this.L;
/*      */   }
/*      */   
/*      */   public int a(float f) {
/* 1354 */     float f1 = c(f);
/* 1355 */     float f2 = 1.0F - MathHelper.cos(f1 * 3.1415927F * 2.0F) * 2.0F + 0.5F;
/*      */     
/* 1357 */     if (f2 < 0.0F) {
/* 1358 */       f2 = 0.0F;
/*      */     }
/*      */     
/* 1361 */     if (f2 > 1.0F) {
/* 1362 */       f2 = 1.0F;
/*      */     }
/*      */     
/* 1365 */     f2 = 1.0F - f2;
/* 1366 */     f2 = (float)(f2 * (1.0D - (j(f) * 5.0F) / 16.0D));
/* 1367 */     f2 = (float)(f2 * (1.0D - (h(f) * 5.0F) / 16.0D));
/* 1368 */     f2 = 1.0F - f2;
/* 1369 */     return (int)(f2 * 11.0F);
/*      */   }
/*      */   
/*      */   public float c(float f) {
/* 1373 */     return this.worldProvider.a(this.worldData.getDayTime(), f);
/*      */   }
/*      */   
/*      */   public float y() {
/* 1377 */     return WorldProvider.a[this.worldProvider.a(this.worldData.getDayTime())];
/*      */   }
/*      */   
/*      */   public float d(float f) {
/* 1381 */     float f1 = c(f);
/*      */     
/* 1383 */     return f1 * 3.1415927F * 2.0F;
/*      */   }
/*      */   
/*      */   public int h(int i, int j) {
/* 1387 */     return getChunkAtWorldCoords(i, j).d(i & 0xF, j & 0xF);
/*      */   }
/*      */   
/*      */   public int i(int i, int j) {
/* 1391 */     Chunk chunk = getChunkAtWorldCoords(i, j);
/* 1392 */     int k = chunk.h() + 15;
/*      */     
/* 1394 */     i &= 0xF;
/*      */     
/* 1396 */     for (j &= 0xF; k > 0; k--) {
/* 1397 */       Block block = chunk.getType(i, k, j);
/*      */       
/* 1399 */       if (block.getMaterial().isSolid() && block.getMaterial() != Material.LEAVES) {
/* 1400 */         return k + 1;
/*      */       }
/*      */     } 
/*      */     
/* 1404 */     return -1;
/*      */   }
/*      */   
/*      */   public void a(int i, int j, int k, Block block, int l) {}
/*      */   
/*      */   public void a(int i, int j, int k, Block block, int l, int i1) {}
/*      */   
/*      */   public void b(int i, int j, int k, Block block, int l, int i1) {}
/*      */   
/*      */   public void tickEntities() {
/* 1414 */     this.methodProfiler.a("entities");
/* 1415 */     this.methodProfiler.a("global");
/*      */ 
/*      */ 
/*      */     
/*      */     int i;
/*      */ 
/*      */     
/* 1422 */     for (i = 0; i < this.i.size(); i++) {
/* 1423 */       Entity entity = this.i.get(i);
/*      */       
/* 1425 */       if (entity != null) {
/*      */ 
/*      */         
/*      */         try {
/*      */ 
/*      */           
/* 1431 */           entity.ticksLived++;
/* 1432 */           entity.h();
/* 1433 */         } catch (Throwable throwable) {
/* 1434 */           CrashReport crashreport = CrashReport.a(throwable, "Ticking entity");
/* 1435 */           CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being ticked");
/* 1436 */           if (entity == null) {
/* 1437 */             crashreportsystemdetails.a("Entity", "~~NULL~~");
/*      */           } else {
/* 1439 */             entity.a(crashreportsystemdetails);
/*      */           } 
/*      */           
/* 1442 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */         
/* 1445 */         if (entity.dead) {
/* 1446 */           this.i.remove(i--);
/*      */         }
/*      */       } 
/*      */     } 
/* 1450 */     this.methodProfiler.c("remove");
/* 1451 */     this.entityList.removeAll(this.f);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1456 */     for (i = 0; i < this.f.size(); i++) {
/* 1457 */       Entity entity = this.f.get(i);
/* 1458 */       int j = entity.ah;
/* 1459 */       int k = entity.aj;
/* 1460 */       if (entity.ag && isChunkLoaded(j, k)) {
/* 1461 */         getChunkAt(j, k).b(entity);
/*      */       }
/*      */     } 
/*      */     
/* 1465 */     for (i = 0; i < this.f.size(); i++) {
/* 1466 */       b(this.f.get(i));
/*      */     }
/*      */     
/* 1469 */     this.f.clear();
/* 1470 */     this.methodProfiler.c("regular");
/*      */     
/* 1472 */     ActivationRange.activateEntities(this);
/* 1473 */     this.timings.entityTick.startTiming();
/* 1474 */     this.guardEntityList = true;
/*      */     
/* 1476 */     for (this.tickPosition = 0; this.tickPosition < this.entityList.size(); this.tickPosition++) {
/* 1477 */       Entity entity = this.entityList.get(this.tickPosition);
/* 1478 */       if (entity.vehicle != null) {
/* 1479 */         if (!entity.vehicle.dead && entity.vehicle.passenger == entity) {
/*      */           continue;
/*      */         }
/*      */         
/* 1483 */         entity.vehicle.passenger = null;
/* 1484 */         entity.vehicle = null;
/*      */       } 
/*      */       
/* 1487 */       this.methodProfiler.a("tick");
/* 1488 */       if (!entity.dead) {
/*      */         try {
/* 1490 */           SpigotTimings.tickEntityTimer.startTiming();
/* 1491 */           playerJoinedWorld(entity);
/* 1492 */           SpigotTimings.tickEntityTimer.stopTiming();
/* 1493 */         } catch (Throwable throwable1) {
/*      */           
/* 1495 */           SpigotTimings.tickEntityTimer.stopTiming();
/* 1496 */           System.err.println("Entity threw exception at " + entity.world.getWorld().getName() + ":" + entity.locX + "," + entity.locY + "," + entity.locZ);
/* 1497 */           throwable1.printStackTrace();
/* 1498 */           entity.dead = true;
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1510 */       this.methodProfiler.b();
/* 1511 */       this.methodProfiler.a("remove");
/* 1512 */       if (entity.dead) {
/* 1513 */         int j = entity.ah;
/* 1514 */         int k = entity.aj;
/* 1515 */         if (entity.ag && isChunkLoaded(j, k)) {
/* 1516 */           getChunkAt(j, k).b(entity);
/*      */         }
/*      */         
/* 1519 */         this.guardEntityList = false;
/* 1520 */         this.entityList.remove(this.tickPosition--);
/* 1521 */         this.guardEntityList = true;
/* 1522 */         b(entity);
/*      */       } 
/*      */       
/* 1525 */       this.methodProfiler.b(); continue;
/*      */     } 
/* 1527 */     this.guardEntityList = false;
/*      */     
/* 1529 */     this.timings.entityTick.stopTiming();
/* 1530 */     this.methodProfiler.c("blockEntities");
/* 1531 */     this.timings.tileEntityTick.startTiming();
/* 1532 */     this.M = true;
/*      */     
/* 1534 */     if (!this.b.isEmpty()) {
/* 1535 */       this.tileEntityList.removeAll(this.b);
/* 1536 */       this.b.clear();
/*      */     } 
/*      */ 
/*      */     
/* 1540 */     initializeHoppers();
/* 1541 */     Iterator<TileEntity> iterator = this.tileEntityList.iterator();
/*      */     
/* 1543 */     while (iterator.hasNext()) {
/* 1544 */       TileEntity tileentity = iterator.next();
/*      */       
/* 1546 */       if (tileentity == null) {
/* 1547 */         getServer().getLogger().severe("Spigot has detected a null entity and has removed it, preventing a crash");
/* 1548 */         iterator.remove();
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1553 */       if (!tileentity.r() && tileentity.o() && isLoaded(tileentity.x, tileentity.y, tileentity.z)) {
/*      */         try {
/* 1555 */           tileentity.tickTimer.startTiming();
/* 1556 */           tileentity.h();
/* 1557 */           tileentity.tickTimer.stopTiming();
/* 1558 */         } catch (Throwable throwable2) {
/*      */           
/* 1560 */           tileentity.tickTimer.stopTiming();
/* 1561 */           System.err.println("TileEntity threw exception at " + tileentity.world.getWorld().getName() + ":" + tileentity.x + "," + tileentity.y + "," + tileentity.z);
/* 1562 */           throwable2.printStackTrace();
/* 1563 */           iterator.remove();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1575 */       if (tileentity.r()) {
/* 1576 */         iterator.remove();
/* 1577 */         if (isChunkLoaded(tileentity.x >> 4, tileentity.z >> 4)) {
/* 1578 */           Chunk chunk = getChunkAt(tileentity.x >> 4, tileentity.z >> 4);
/*      */           
/* 1580 */           if (chunk != null) {
/* 1581 */             chunk.f(tileentity.x & 0xF, tileentity.y, tileentity.z & 0xF);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1587 */     this.timings.tileEntityTick.stopTiming();
/* 1588 */     this.timings.tileEntityPending.startTiming();
/* 1589 */     this.M = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1597 */     this.methodProfiler.c("pendingBlockEntities");
/* 1598 */     if (!this.a.isEmpty()) {
/* 1599 */       for (int l = 0; l < this.a.size(); l++) {
/* 1600 */         TileEntity tileentity1 = this.a.get(l);
/*      */         
/* 1602 */         if (!tileentity1.r()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1609 */           if (isChunkLoaded(tileentity1.x >> 4, tileentity1.z >> 4)) {
/* 1610 */             Chunk chunk1 = getChunkAt(tileentity1.x >> 4, tileentity1.z >> 4);
/*      */             
/* 1612 */             if (chunk1 != null) {
/* 1613 */               chunk1.a(tileentity1.x & 0xF, tileentity1.y, tileentity1.z & 0xF, tileentity1);
/*      */               
/* 1615 */               if (!this.tileEntityList.contains(tileentity1)) {
/* 1616 */                 this.tileEntityList.add(tileentity1);
/*      */               }
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/* 1622 */           notify(tileentity1.x, tileentity1.y, tileentity1.z);
/*      */         } 
/*      */       } 
/*      */       
/* 1626 */       this.a.clear();
/*      */     } 
/*      */     
/* 1629 */     this.timings.tileEntityPending.stopTiming();
/* 1630 */     this.methodProfiler.b();
/* 1631 */     this.methodProfiler.b();
/*      */   }
/*      */   
/*      */   public void a(Collection collection) {
/* 1635 */     if (this.M) {
/* 1636 */       this.a.addAll(collection);
/*      */     } else {
/* 1638 */       this.tileEntityList.addAll(collection);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void playerJoinedWorld(Entity entity) {
/* 1643 */     entityJoinedWorld(entity, true);
/*      */   }
/*      */   
/*      */   public void entityJoinedWorld(Entity entity, boolean flag) {
/* 1647 */     int i = MathHelper.floor(entity.locX);
/* 1648 */     int j = MathHelper.floor(entity.locZ);
/* 1649 */     byte b0 = 32;
/*      */ 
/*      */     
/* 1652 */     if (!ActivationRange.checkIfActive(entity)) {
/* 1653 */       entity.ticksLived++;
/* 1654 */       entity.inactiveTick();
/*      */       
/* 1656 */       if (!isChunkLoaded(i, j) && ((entity instanceof EntityEnderPearl && this.paperSpigotConfig.removeUnloadedEnderPearls) || (entity instanceof EntityFallingBlock && this.paperSpigotConfig.removeUnloadedFallingBlocks) || (entity instanceof EntityTNTPrimed && this.paperSpigotConfig.removeUnloadedTNTEntities))) {
/*      */ 
/*      */         
/* 1659 */         entity.inUnloadedChunk = true;
/* 1660 */         entity.die();
/*      */       } 
/*      */     } else {
/*      */       
/* 1664 */       entity.tickTimer.startTiming();
/*      */       
/* 1666 */       entity.S = entity.locX;
/* 1667 */       entity.T = entity.locY;
/* 1668 */       entity.U = entity.locZ;
/* 1669 */       entity.lastYaw = entity.yaw;
/* 1670 */       entity.lastPitch = entity.pitch;
/* 1671 */       if (flag && entity.ag) {
/* 1672 */         entity.ticksLived++;
/* 1673 */         if (entity.vehicle != null) {
/* 1674 */           entity.ab();
/*      */         } else {
/* 1676 */           entity.h();
/*      */         } 
/*      */       } 
/*      */       
/* 1680 */       this.methodProfiler.a("chunkCheck");
/* 1681 */       if (Double.isNaN(entity.locX) || Double.isInfinite(entity.locX)) {
/* 1682 */         entity.locX = entity.S;
/*      */       }
/*      */       
/* 1685 */       if (Double.isNaN(entity.locY) || Double.isInfinite(entity.locY)) {
/* 1686 */         entity.locY = entity.T;
/*      */       }
/*      */       
/* 1689 */       if (Double.isNaN(entity.locZ) || Double.isInfinite(entity.locZ)) {
/* 1690 */         entity.locZ = entity.U;
/*      */       }
/*      */       
/* 1693 */       if (Double.isNaN(entity.pitch) || Double.isInfinite(entity.pitch)) {
/* 1694 */         entity.pitch = entity.lastPitch;
/*      */       }
/*      */       
/* 1697 */       if (Double.isNaN(entity.yaw) || Double.isInfinite(entity.yaw)) {
/* 1698 */         entity.yaw = entity.lastYaw;
/*      */       }
/*      */       
/* 1701 */       int k = MathHelper.floor(entity.locX / 16.0D);
/* 1702 */       int l = MathHelper.floor(entity.locY / 16.0D);
/* 1703 */       int i1 = MathHelper.floor(entity.locZ / 16.0D);
/*      */       
/* 1705 */       if (!entity.ag || entity.ah != k || entity.ai != l || entity.aj != i1) {
/* 1706 */         if (entity.loadChunks) entity.loadChunks(); 
/* 1707 */         if (entity.ag && isChunkLoaded(entity.ah, entity.aj)) {
/* 1708 */           getChunkAt(entity.ah, entity.aj).a(entity, entity.ai);
/*      */         }
/*      */         
/* 1711 */         if (isChunkLoaded(k, i1)) {
/* 1712 */           entity.ag = true;
/* 1713 */           getChunkAt(k, i1).a(entity);
/*      */         } else {
/* 1715 */           entity.ag = false;
/*      */         } 
/*      */       } 
/*      */       
/* 1719 */       this.methodProfiler.b();
/* 1720 */       if (flag && entity.ag && entity.passenger != null) {
/* 1721 */         if (!entity.passenger.dead && entity.passenger.vehicle == entity) {
/* 1722 */           playerJoinedWorld(entity.passenger);
/*      */         } else {
/* 1724 */           entity.passenger.vehicle = null;
/* 1725 */           entity.passenger = null;
/*      */         } 
/*      */       }
/* 1728 */       entity.tickTimer.stopTiming();
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean b(AxisAlignedBB axisalignedbb) {
/* 1733 */     return a(axisalignedbb, (Entity)null);
/*      */   }
/*      */   
/*      */   public boolean a(AxisAlignedBB axisalignedbb, Entity entity) {
/* 1737 */     List<Entity> list = getEntities((Entity)null, axisalignedbb);
/*      */     
/* 1739 */     for (int i = 0; i < list.size(); i++) {
/* 1740 */       Entity entity1 = list.get(i);
/*      */       
/* 1742 */       if (!(entity instanceof EntityPlayer) || !(entity1 instanceof EntityPlayer) || (
/* 1743 */         (EntityPlayer)entity).getBukkitEntity().canSee((Player)((EntityPlayer)entity1).getBukkitEntity()))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1749 */         if (!entity1.dead && entity1.k && entity1 != entity) {
/* 1750 */           return false;
/*      */         }
/*      */       }
/*      */     } 
/* 1754 */     return true;
/*      */   }
/*      */   
/*      */   public boolean c(AxisAlignedBB axisalignedbb) {
/* 1758 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1759 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1760 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1761 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1762 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1763 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */     
/* 1765 */     if (axisalignedbb.a < 0.0D) {
/* 1766 */       i--;
/*      */     }
/*      */     
/* 1769 */     if (axisalignedbb.b < 0.0D) {
/* 1770 */       k--;
/*      */     }
/*      */     
/* 1773 */     if (axisalignedbb.c < 0.0D) {
/* 1774 */       i1--;
/*      */     }
/*      */     
/* 1777 */     for (int k1 = i; k1 < j; k1++) {
/* 1778 */       for (int l1 = k; l1 < l; l1++) {
/* 1779 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1780 */           Block block = getType(k1, l1, i2);
/*      */           
/* 1782 */           if (block.getMaterial() != Material.AIR) {
/* 1783 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1789 */     return false;
/*      */   }
/*      */   
/*      */   public boolean containsLiquid(AxisAlignedBB axisalignedbb) {
/* 1793 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1794 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1795 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1796 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1797 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1798 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */     
/* 1800 */     if (axisalignedbb.a < 0.0D) {
/* 1801 */       i--;
/*      */     }
/*      */     
/* 1804 */     if (axisalignedbb.b < 0.0D) {
/* 1805 */       k--;
/*      */     }
/*      */     
/* 1808 */     if (axisalignedbb.c < 0.0D) {
/* 1809 */       i1--;
/*      */     }
/*      */     
/* 1812 */     for (int k1 = i; k1 < j; k1++) {
/* 1813 */       for (int l1 = k; l1 < l; l1++) {
/* 1814 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1815 */           Block block = getType(k1, l1, i2);
/*      */           
/* 1817 */           if (block.getMaterial().isLiquid()) {
/* 1818 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1824 */     return false;
/*      */   }
/*      */   
/*      */   public boolean e(AxisAlignedBB axisalignedbb) {
/* 1828 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1829 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1830 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1831 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1832 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1833 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */     
/* 1835 */     if (b(i, k, i1, j, l, j1)) {
/* 1836 */       for (int k1 = i; k1 < j; k1++) {
/* 1837 */         for (int l1 = k; l1 < l; l1++) {
/* 1838 */           for (int i2 = i1; i2 < j1; i2++) {
/* 1839 */             Block block = getType(k1, l1, i2);
/*      */             
/* 1841 */             if (block == Blocks.FIRE || block == Blocks.LAVA || block == Blocks.STATIONARY_LAVA) {
/* 1842 */               return true;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1849 */     return false;
/*      */   }
/*      */   
/*      */   public boolean a(AxisAlignedBB axisalignedbb, Material material, Entity entity) {
/* 1853 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1854 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1855 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1856 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1857 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1858 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */     
/* 1860 */     if (!b(i, k, i1, j, l, j1)) {
/* 1861 */       return false;
/*      */     }
/* 1863 */     boolean flag = false;
/* 1864 */     Vec3D vec3d = Vec3D.a(0.0D, 0.0D, 0.0D);
/*      */     
/* 1866 */     for (int k1 = i; k1 < j; k1++) {
/* 1867 */       for (int l1 = k; l1 < l; l1++) {
/* 1868 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1869 */           Block block = getType(k1, l1, i2);
/*      */           
/* 1871 */           if (block.getMaterial() == material) {
/* 1872 */             double d0 = ((l1 + 1) - BlockFluids.b(getData(k1, l1, i2)));
/*      */             
/* 1874 */             if (l >= d0) {
/* 1875 */               flag = true;
/* 1876 */               block.a(this, k1, l1, i2, entity, vec3d);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1883 */     if (vec3d.b() > 0.0D && entity.aC()) {
/* 1884 */       vec3d = vec3d.a();
/* 1885 */       double d1 = 0.014D;
/*      */       
/* 1887 */       entity.motX += vec3d.a * d1;
/* 1888 */       entity.motY += vec3d.b * d1;
/* 1889 */       entity.motZ += vec3d.c * d1;
/*      */     } 
/*      */     
/* 1892 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean a(AxisAlignedBB axisalignedbb, Material material) {
/* 1897 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1898 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1899 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1900 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1901 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1902 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */     
/* 1904 */     for (int k1 = i; k1 < j; k1++) {
/* 1905 */       for (int l1 = k; l1 < l; l1++) {
/* 1906 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1907 */           if (getType(k1, l1, i2).getMaterial() == material) {
/* 1908 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1914 */     return false;
/*      */   }
/*      */   
/*      */   public boolean b(AxisAlignedBB axisalignedbb, Material material) {
/* 1918 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1919 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1920 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1921 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1922 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1923 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */     
/* 1925 */     for (int k1 = i; k1 < j; k1++) {
/* 1926 */       for (int l1 = k; l1 < l; l1++) {
/* 1927 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1928 */           Block block = getType(k1, l1, i2);
/*      */           
/* 1930 */           if (block.getMaterial() == material) {
/* 1931 */             int j2 = getData(k1, l1, i2);
/* 1932 */             double d0 = (l1 + 1);
/*      */             
/* 1934 */             if (j2 < 8) {
/* 1935 */               d0 = (l1 + 1) - j2 / 8.0D;
/*      */             }
/*      */             
/* 1938 */             if (d0 >= axisalignedbb.b) {
/* 1939 */               return true;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1946 */     return false;
/*      */   }
/*      */   
/*      */   public Explosion explode(Entity entity, double d0, double d1, double d2, float f, boolean flag) {
/* 1950 */     return createExplosion(entity, d0, d1, d2, f, false, flag);
/*      */   }
/*      */   
/*      */   public Explosion createExplosion(Entity entity, double d0, double d1, double d2, float f, boolean flag, boolean flag1) {
/* 1954 */     Explosion explosion = new Explosion(this, entity, d0, d1, d2, f);
/*      */     
/* 1956 */     explosion.a = flag;
/* 1957 */     explosion.b = flag1;
/* 1958 */     explosion.a();
/* 1959 */     explosion.a(true);
/* 1960 */     return explosion;
/*      */   }
/*      */   
/*      */   public float a(Vec3D vec3d, AxisAlignedBB axisalignedbb) {
/* 1964 */     double d0 = 1.0D / ((axisalignedbb.d - axisalignedbb.a) * 2.0D + 1.0D);
/* 1965 */     double d1 = 1.0D / ((axisalignedbb.e - axisalignedbb.b) * 2.0D + 1.0D);
/* 1966 */     double d2 = 1.0D / ((axisalignedbb.f - axisalignedbb.c) * 2.0D + 1.0D);
/*      */ 
/*      */ 
/*      */     
/* 1970 */     double xOffset = (1.0D - Math.floor(1.0D / d0) * d0) / 2.0D;
/* 1971 */     double zOffset = (1.0D - Math.floor(1.0D / d2) * d2) / 2.0D;
/*      */ 
/*      */     
/* 1974 */     if (d0 >= 0.0D && d1 >= 0.0D && d2 >= 0.0D) {
/* 1975 */       int i = 0;
/* 1976 */       int j = 0;
/*      */       
/* 1978 */       Vec3D vec3d2 = Vec3D.a(0.0D, 0.0D, 0.0D); float f;
/* 1979 */       for (f = 0.0F; f <= 1.0F; f = (float)(f + d0)) {
/* 1980 */         float f1; for (f1 = 0.0F; f1 <= 1.0F; f1 = (float)(f1 + d1)) {
/* 1981 */           float f2; for (f2 = 0.0F; f2 <= 1.0F; f2 = (float)(f2 + d2)) {
/* 1982 */             double d3 = axisalignedbb.a + (axisalignedbb.d - axisalignedbb.a) * f;
/* 1983 */             double d4 = axisalignedbb.b + (axisalignedbb.e - axisalignedbb.b) * f1;
/* 1984 */             double d5 = axisalignedbb.c + (axisalignedbb.f - axisalignedbb.c) * f2;
/*      */             
/* 1986 */             if (a(vec3d2.b(xOffset + d3, d4, zOffset + d5), vec3d) == null) {
/* 1987 */               i++;
/*      */             }
/*      */             
/* 1990 */             j++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1995 */       return i / j;
/*      */     } 
/* 1997 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean douseFire(EntityHuman entityhuman, int i, int j, int k, int l) {
/* 2002 */     if (l == 0) {
/* 2003 */       j--;
/*      */     }
/*      */     
/* 2006 */     if (l == 1) {
/* 2007 */       j++;
/*      */     }
/*      */     
/* 2010 */     if (l == 2) {
/* 2011 */       k--;
/*      */     }
/*      */     
/* 2014 */     if (l == 3) {
/* 2015 */       k++;
/*      */     }
/*      */     
/* 2018 */     if (l == 4) {
/* 2019 */       i--;
/*      */     }
/*      */     
/* 2022 */     if (l == 5) {
/* 2023 */       i++;
/*      */     }
/*      */     
/* 2026 */     if (getType(i, j, k) == Blocks.FIRE) {
/* 2027 */       a(entityhuman, 1004, i, j, k, 0);
/* 2028 */       setAir(i, j, k);
/* 2029 */       return true;
/*      */     } 
/* 2031 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public TileEntity getTileEntity(int i, int j, int k) {
/* 2036 */     if (j >= 0 && j < 256) {
/* 2037 */       TileEntity tileentity = null;
/*      */ 
/*      */ 
/*      */       
/* 2041 */       if (this.M) {
/* 2042 */         for (int l = 0; l < this.a.size(); l++) {
/* 2043 */           TileEntity tileentity1 = this.a.get(l);
/* 2044 */           if (!tileentity1.r() && tileentity1.x == i && tileentity1.y == j && tileentity1.z == k) {
/* 2045 */             tileentity = tileentity1;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/* 2051 */       if (tileentity == null) {
/* 2052 */         Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*      */         
/* 2054 */         if (chunk != null) {
/* 2055 */           tileentity = chunk.e(i & 0xF, j, k & 0xF);
/*      */         }
/*      */       } 
/*      */       
/* 2059 */       if (tileentity == null) {
/* 2060 */         for (int l = 0; l < this.a.size(); l++) {
/* 2061 */           TileEntity tileentity1 = this.a.get(l);
/* 2062 */           if (!tileentity1.r() && tileentity1.x == i && tileentity1.y == j && tileentity1.z == k) {
/* 2063 */             tileentity = tileentity1;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/* 2069 */       return tileentity;
/*      */     } 
/* 2071 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTileEntity(int i, int j, int k, TileEntity tileentity) {
/* 2076 */     if (tileentity != null && !tileentity.r()) {
/* 2077 */       if (this.M) {
/* 2078 */         tileentity.x = i;
/* 2079 */         tileentity.y = j;
/* 2080 */         tileentity.z = k;
/* 2081 */         Iterator<TileEntity> iterator = this.a.iterator();
/*      */         
/* 2083 */         while (iterator.hasNext()) {
/* 2084 */           TileEntity tileentity1 = iterator.next();
/*      */           
/* 2086 */           if (tileentity1.x == i && tileentity1.y == j && tileentity1.z == k) {
/* 2087 */             tileentity1.s();
/* 2088 */             iterator.remove();
/*      */           } 
/*      */         } 
/*      */         
/* 2092 */         tileentity.a(this);
/* 2093 */         this.a.add(tileentity);
/*      */       } else {
/* 2095 */         this.tileEntityList.add(tileentity);
/* 2096 */         Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*      */         
/* 2098 */         if (chunk != null) {
/* 2099 */           chunk.a(i & 0xF, j, k & 0xF, tileentity);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void p(int i, int j, int k) {
/* 2106 */     TileEntity tileentity = getTileEntity(i, j, k);
/*      */     
/* 2108 */     if (tileentity != null && this.M) {
/* 2109 */       tileentity.s();
/* 2110 */       this.a.remove(tileentity);
/*      */     } else {
/* 2112 */       if (tileentity != null) {
/* 2113 */         this.a.remove(tileentity);
/* 2114 */         this.tileEntityList.remove(tileentity);
/*      */       } 
/*      */       
/* 2117 */       Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*      */       
/* 2119 */       if (chunk != null) {
/* 2120 */         chunk.f(i & 0xF, j, k & 0xF);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void a(TileEntity tileentity) {
/* 2126 */     this.b.add(tileentity);
/*      */   }
/*      */   
/*      */   public boolean q(int i, int j, int k) {
/* 2130 */     AxisAlignedBB axisalignedbb = getType(i, j, k).a(this, i, j, k);
/*      */     
/* 2132 */     return (axisalignedbb != null && axisalignedbb.a() >= 1.0D);
/*      */   }
/*      */   
/*      */   public static boolean a(IBlockAccess iblockaccess, int i, int j, int k) {
/* 2136 */     Block block = iblockaccess.getType(i, j, k);
/* 2137 */     int l = iblockaccess.getData(i, j, k);
/*      */     
/* 2139 */     return (block.getMaterial().k() && block.d()) ? true : ((block instanceof BlockStairs) ? (((l & 0x4) == 4)) : ((block instanceof BlockStepAbstract) ? (((l & 0x8) == 8)) : ((block instanceof BlockHopper) ? true : ((block instanceof BlockSnow) ? (((l & 0x7) == 7)) : false))));
/*      */   }
/*      */   
/*      */   public boolean c(int i, int j, int k, boolean flag) {
/* 2143 */     if (i >= -30000000 && k >= -30000000 && i < 30000000 && k < 30000000) {
/* 2144 */       Chunk chunk = this.chunkProvider.getOrCreateChunk(i >> 4, k >> 4);
/*      */       
/* 2146 */       if (chunk != null && !chunk.isEmpty()) {
/* 2147 */         Block block = getType(i, j, k);
/*      */         
/* 2149 */         return (block.getMaterial().k() && block.d());
/*      */       } 
/* 2151 */       return flag;
/*      */     } 
/*      */     
/* 2154 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public void B() {
/* 2159 */     int i = a(1.0F);
/*      */     
/* 2161 */     if (i != this.j) {
/* 2162 */       this.j = i;
/*      */     }
/*      */   }
/*      */   
/*      */   public void setSpawnFlags(boolean flag, boolean flag1) {
/* 2167 */     this.allowMonsters = flag;
/* 2168 */     this.allowAnimals = flag1;
/*      */   }
/*      */   
/*      */   public void doTick() {
/* 2172 */     o();
/*      */   }
/*      */   
/*      */   private void a() {
/* 2176 */     if (this.worldData.hasStorm()) {
/* 2177 */       this.n = 1.0F;
/* 2178 */       if (this.worldData.isThundering()) {
/* 2179 */         this.p = 1.0F;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void o() {
/* 2185 */     if (!this.worldProvider.g && 
/* 2186 */       !this.isStatic) {
/* 2187 */       int i = this.worldData.getThunderDuration();
/*      */       
/* 2189 */       if (i <= 0) {
/* 2190 */         if (this.worldData.isThundering()) {
/* 2191 */           this.worldData.setThunderDuration(this.random.nextInt(12000) + 3600);
/*      */         } else {
/* 2193 */           this.worldData.setThunderDuration(this.random.nextInt(168000) + 12000);
/*      */         } 
/*      */       } else {
/* 2196 */         i--;
/* 2197 */         this.worldData.setThunderDuration(i);
/* 2198 */         if (i <= 0) {
/*      */           
/* 2200 */           ThunderChangeEvent thunder = new ThunderChangeEvent((org.bukkit.World)getWorld(), !this.worldData.isThundering());
/* 2201 */           getServer().getPluginManager().callEvent((Event)thunder);
/* 2202 */           if (!thunder.isCancelled()) {
/* 2203 */             this.worldData.setThundering(!this.worldData.isThundering());
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2209 */       this.o = this.p;
/* 2210 */       if (this.worldData.isThundering()) {
/* 2211 */         this.p = (float)(this.p + 0.01D);
/*      */       } else {
/* 2213 */         this.p = (float)(this.p - 0.01D);
/*      */       } 
/*      */       
/* 2216 */       this.p = MathHelper.a(this.p, 0.0F, 1.0F);
/* 2217 */       int j = this.worldData.getWeatherDuration();
/*      */       
/* 2219 */       if (j <= 0) {
/* 2220 */         if (this.worldData.hasStorm()) {
/* 2221 */           this.worldData.setWeatherDuration(this.random.nextInt(12000) + 12000);
/*      */         } else {
/* 2223 */           this.worldData.setWeatherDuration(this.random.nextInt(168000) + 12000);
/*      */         } 
/*      */       } else {
/* 2226 */         j--;
/* 2227 */         this.worldData.setWeatherDuration(j);
/* 2228 */         if (j <= 0) {
/*      */           
/* 2230 */           WeatherChangeEvent weather = new WeatherChangeEvent((org.bukkit.World)getWorld(), !this.worldData.hasStorm());
/* 2231 */           getServer().getPluginManager().callEvent((Event)weather);
/*      */           
/* 2233 */           if (!weather.isCancelled()) {
/* 2234 */             this.worldData.setStorm(!this.worldData.hasStorm());
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2240 */       this.m = this.n;
/* 2241 */       if (this.worldData.hasStorm()) {
/* 2242 */         this.n = (float)(this.n + 0.01D);
/*      */       } else {
/* 2244 */         this.n = (float)(this.n - 0.01D);
/*      */       } 
/*      */       
/* 2247 */       this.n = MathHelper.a(this.n, 0.0F, 1.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void C() {
/* 2254 */     this.methodProfiler.a("buildList");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2263 */     int optimalChunks = this.spigotConfig.chunksPerTick;
/*      */     
/* 2265 */     if (optimalChunks <= 0 || this.players.isEmpty()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 2270 */     int chunksPerPlayer = Math.min(200, Math.max(1, (int)((optimalChunks - this.players.size()) / this.players.size() + 0.5D)));
/* 2271 */     int randRange = 3 + chunksPerPlayer / 30;
/*      */     
/* 2273 */     randRange = (randRange > this.chunkTickRadius) ? this.chunkTickRadius : randRange;
/*      */     
/* 2275 */     this.growthOdds = this.modifiedOdds = Math.max(35.0F, Math.min(100.0F, (chunksPerPlayer + 1) * 100.0F / 15.0F));
/*      */     int i;
/* 2277 */     for (i = 0; i < this.players.size(); i++) {
/* 2278 */       EntityHuman entityhuman = this.players.get(i);
/* 2279 */       int j = MathHelper.floor(entityhuman.locX / 16.0D);
/* 2280 */       int k = MathHelper.floor(entityhuman.locZ / 16.0D);
/* 2281 */       int l = p();
/*      */ 
/*      */       
/* 2284 */       long key = chunkToKey(j, k);
/* 2285 */       int existingPlayers = Math.max(0, this.chunkTickList.get(key));
/* 2286 */       this.chunkTickList.put(key, (short)(existingPlayers + 1));
/*      */ 
/*      */       
/* 2289 */       for (int chunk = 0; chunk < chunksPerPlayer; chunk++) {
/*      */         
/* 2291 */         int dx = (this.random.nextBoolean() ? 1 : -1) * this.random.nextInt(randRange);
/* 2292 */         int dz = (this.random.nextBoolean() ? 1 : -1) * this.random.nextInt(randRange);
/* 2293 */         long hash = chunkToKey(dx + j, dz + k);
/* 2294 */         if (!this.chunkTickList.contains(hash) && isChunkLoaded(dx + j, dz + k))
/*      */         {
/* 2296 */           this.chunkTickList.put(hash, (short)-1);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2302 */     this.methodProfiler.b();
/* 2303 */     if (this.K > 0) {
/* 2304 */       this.K--;
/*      */     }
/*      */     
/* 2307 */     this.methodProfiler.a("playerCheckLight");
/* 2308 */     if (this.spigotConfig.randomLightUpdates && !this.players.isEmpty()) {
/* 2309 */       i = this.random.nextInt(this.players.size());
/* 2310 */       EntityHuman entityhuman = this.players.get(i);
/* 2311 */       int j = MathHelper.floor(entityhuman.locX) + this.random.nextInt(11) - 5;
/* 2312 */       int k = MathHelper.floor(entityhuman.locY) + this.random.nextInt(11) - 5;
/* 2313 */       int l = MathHelper.floor(entityhuman.locZ) + this.random.nextInt(11) - 5;
/* 2314 */       t(j, k, l);
/*      */     } 
/*      */     
/* 2317 */     this.methodProfiler.b();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void a(int i, int j, Chunk chunk) {
/* 2323 */     this.methodProfiler.c("moodSound");
/* 2324 */     if (!this.paperSpigotConfig.disableMoodSounds && this.K == 0 && !this.isStatic) {
/* 2325 */       this.k = this.k * 3 + 1013904223;
/* 2326 */       int k = this.k >> 2;
/* 2327 */       int l = k & 0xF;
/* 2328 */       int i1 = k >> 8 & 0xF;
/* 2329 */       int j1 = k >> 16 & 0xFF;
/* 2330 */       Block block = chunk.getType(l, j1, i1);
/*      */       
/* 2332 */       l += i;
/* 2333 */       i1 += j;
/* 2334 */       if (block.getMaterial() == Material.AIR && j(l, j1, i1) <= this.random.nextInt(8) && b(EnumSkyBlock.SKY, l, j1, i1) <= 0) {
/* 2335 */         EntityHuman entityhuman = findNearbyPlayer(l + 0.5D, j1 + 0.5D, i1 + 0.5D, 8.0D);
/*      */         
/* 2337 */         if (entityhuman != null && entityhuman.e(l + 0.5D, j1 + 0.5D, i1 + 0.5D) > 4.0D) {
/* 2338 */           makeSound(l + 0.5D, j1 + 0.5D, i1 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.random.nextFloat() * 0.2F);
/* 2339 */           this.K = this.random.nextInt(12000) + 6000;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2344 */     this.methodProfiler.c("checkLight");
/* 2345 */     chunk.o();
/*      */   }
/*      */   
/*      */   protected void g() {
/* 2349 */     C();
/*      */   }
/*      */   
/*      */   public boolean r(int i, int j, int k) {
/* 2353 */     return d(i, j, k, false);
/*      */   }
/*      */   
/*      */   public boolean s(int i, int j, int k) {
/* 2357 */     return d(i, j, k, true);
/*      */   }
/*      */   
/*      */   public boolean d(int i, int j, int k, boolean flag) {
/* 2361 */     BiomeBase biomebase = getBiome(i, k);
/* 2362 */     float f = biomebase.a(i, j, k);
/*      */     
/* 2364 */     if (f > 0.15F) {
/* 2365 */       return false;
/*      */     }
/* 2367 */     if (j >= 0 && j < 256 && b(EnumSkyBlock.BLOCK, i, j, k) < 10) {
/* 2368 */       Block block = getType(i, j, k);
/*      */       
/* 2370 */       if ((block == Blocks.STATIONARY_WATER || block == Blocks.WATER) && getData(i, j, k) == 0) {
/* 2371 */         if (!flag) {
/* 2372 */           return true;
/*      */         }
/*      */         
/* 2375 */         boolean flag1 = true;
/*      */         
/* 2377 */         if (flag1 && getType(i - 1, j, k).getMaterial() != Material.WATER) {
/* 2378 */           flag1 = false;
/*      */         }
/*      */         
/* 2381 */         if (flag1 && getType(i + 1, j, k).getMaterial() != Material.WATER) {
/* 2382 */           flag1 = false;
/*      */         }
/*      */         
/* 2385 */         if (flag1 && getType(i, j, k - 1).getMaterial() != Material.WATER) {
/* 2386 */           flag1 = false;
/*      */         }
/*      */         
/* 2389 */         if (flag1 && getType(i, j, k + 1).getMaterial() != Material.WATER) {
/* 2390 */           flag1 = false;
/*      */         }
/*      */         
/* 2393 */         if (!flag1) {
/* 2394 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2399 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean e(int i, int j, int k, boolean flag) {
/* 2404 */     BiomeBase biomebase = getBiome(i, k);
/* 2405 */     float f = biomebase.a(i, j, k);
/*      */     
/* 2407 */     if (f > 0.15F)
/* 2408 */       return false; 
/* 2409 */     if (!flag) {
/* 2410 */       return true;
/*      */     }
/* 2412 */     if (j >= 0 && j < 256 && b(EnumSkyBlock.BLOCK, i, j, k) < 10) {
/* 2413 */       Block block = getType(i, j, k);
/*      */       
/* 2415 */       if (block.getMaterial() == Material.AIR && Blocks.SNOW.canPlace(this, i, j, k)) {
/* 2416 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 2420 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean t(int i, int j, int k) {
/* 2425 */     boolean flag = false;
/*      */     
/* 2427 */     if (!this.worldProvider.g) {
/* 2428 */       flag |= updateLight(EnumSkyBlock.SKY, i, j, k);
/*      */     }
/*      */     
/* 2431 */     flag |= updateLight(EnumSkyBlock.BLOCK, i, j, k);
/* 2432 */     return flag;
/*      */   }
/*      */   
/*      */   private int a(int i, int j, int k, EnumSkyBlock enumskyblock) {
/* 2436 */     if (enumskyblock == EnumSkyBlock.SKY && i(i, j, k)) {
/* 2437 */       return 15;
/*      */     }
/* 2439 */     Block block = getType(i, j, k);
/* 2440 */     int l = (enumskyblock == EnumSkyBlock.SKY) ? 0 : block.m();
/* 2441 */     int i1 = block.k();
/*      */     
/* 2443 */     if (i1 >= 15 && block.m() > 0) {
/* 2444 */       i1 = 1;
/*      */     }
/*      */     
/* 2447 */     if (i1 < 1) {
/* 2448 */       i1 = 1;
/*      */     }
/*      */     
/* 2451 */     if (i1 >= 15)
/* 2452 */       return 0; 
/* 2453 */     if (l >= 14) {
/* 2454 */       return l;
/*      */     }
/* 2456 */     for (int j1 = 0; j1 < 6; j1++) {
/* 2457 */       int k1 = i + Facing.b[j1];
/* 2458 */       int l1 = j + Facing.c[j1];
/* 2459 */       int i2 = k + Facing.d[j1];
/* 2460 */       int j2 = b(enumskyblock, k1, l1, i2) - i1;
/*      */       
/* 2462 */       if (j2 > l) {
/* 2463 */         l = j2;
/*      */       }
/*      */       
/* 2466 */       if (l >= 14) {
/* 2467 */         return l;
/*      */       }
/*      */     } 
/*      */     
/* 2471 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean c(EnumSkyBlock enumskyblock, int i, int j, int k, Chunk chunk, List<Chunk> neighbors) {
/* 2479 */     if (chunk == null)
/*      */     {
/* 2481 */       return false;
/*      */     }
/* 2483 */     int l = 0;
/* 2484 */     int i1 = 0;
/*      */     
/* 2486 */     this.methodProfiler.a("getBrightness");
/* 2487 */     int j1 = b(enumskyblock, i, j, k);
/* 2488 */     int k1 = a(i, j, k, enumskyblock);
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
/* 2499 */     if (k1 > j1) {
/* 2500 */       this.I[i1++] = 133152;
/* 2501 */     } else if (k1 < j1) {
/* 2502 */       this.I[i1++] = 0x20820 | j1 << 18;
/*      */       
/* 2504 */       while (l < i1) {
/* 2505 */         int l1 = this.I[l++];
/* 2506 */         int i2 = (l1 & 0x3F) - 32 + i;
/* 2507 */         int j2 = (l1 >> 6 & 0x3F) - 32 + j;
/* 2508 */         int k2 = (l1 >> 12 & 0x3F) - 32 + k;
/* 2509 */         int l2 = l1 >> 18 & 0xF;
/* 2510 */         int i3 = b(enumskyblock, i2, j2, k2);
/* 2511 */         if (i3 == l2) {
/* 2512 */           b(enumskyblock, i2, j2, k2, 0);
/* 2513 */           if (l2 > 0) {
/* 2514 */             int j3 = MathHelper.a(i2 - i);
/* 2515 */             int l3 = MathHelper.a(j2 - j);
/* 2516 */             int k3 = MathHelper.a(k2 - k);
/* 2517 */             if (j3 + l3 + k3 < 17) {
/* 2518 */               for (int i4 = 0; i4 < 6; i4++) {
/* 2519 */                 int j4 = i2 + Facing.b[i4];
/* 2520 */                 int k4 = j2 + Facing.c[i4];
/* 2521 */                 int l4 = k2 + Facing.d[i4];
/* 2522 */                 int i5 = Math.max(1, getType(j4, k4, l4).k());
/*      */                 
/* 2524 */                 i3 = b(enumskyblock, j4, k4, l4);
/* 2525 */                 if (i3 == l2 - i5 && i1 < this.I.length) {
/* 2526 */                   this.I[i1++] = j4 - i + 32 | k4 - j + 32 << 6 | l4 - k + 32 << 12 | l2 - i5 << 18;
/*      */                 }
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2534 */       l = 0;
/*      */     } 
/*      */     
/* 2537 */     this.methodProfiler.b();
/* 2538 */     this.methodProfiler.a("checkedPosition < toCheckCount");
/*      */     
/* 2540 */     while (l < i1) {
/* 2541 */       int l1 = this.I[l++];
/* 2542 */       int i2 = (l1 & 0x3F) - 32 + i;
/* 2543 */       int j2 = (l1 >> 6 & 0x3F) - 32 + j;
/* 2544 */       int k2 = (l1 >> 12 & 0x3F) - 32 + k;
/* 2545 */       int l2 = b(enumskyblock, i2, j2, k2);
/* 2546 */       int i3 = a(i2, j2, k2, enumskyblock);
/* 2547 */       if (i3 != l2) {
/* 2548 */         b(enumskyblock, i2, j2, k2, i3);
/* 2549 */         if (i3 > l2) {
/* 2550 */           int j3 = Math.abs(i2 - i);
/* 2551 */           int l3 = Math.abs(j2 - j);
/* 2552 */           int k3 = Math.abs(k2 - k);
/* 2553 */           boolean flag = (i1 < this.I.length - 6);
/*      */           
/* 2555 */           if (j3 + l3 + k3 < 17 && flag) {
/* 2556 */             if (b(enumskyblock, i2 - 1, j2, k2) < i3) {
/* 2557 */               this.I[i1++] = i2 - 1 - i + 32 + (j2 - j + 32 << 6) + (k2 - k + 32 << 12);
/*      */             }
/*      */             
/* 2560 */             if (b(enumskyblock, i2 + 1, j2, k2) < i3) {
/* 2561 */               this.I[i1++] = i2 + 1 - i + 32 + (j2 - j + 32 << 6) + (k2 - k + 32 << 12);
/*      */             }
/*      */             
/* 2564 */             if (b(enumskyblock, i2, j2 - 1, k2) < i3) {
/* 2565 */               this.I[i1++] = i2 - i + 32 + (j2 - 1 - j + 32 << 6) + (k2 - k + 32 << 12);
/*      */             }
/*      */             
/* 2568 */             if (b(enumskyblock, i2, j2 + 1, k2) < i3) {
/* 2569 */               this.I[i1++] = i2 - i + 32 + (j2 + 1 - j + 32 << 6) + (k2 - k + 32 << 12);
/*      */             }
/*      */             
/* 2572 */             if (b(enumskyblock, i2, j2, k2 - 1) < i3) {
/* 2573 */               this.I[i1++] = i2 - i + 32 + (j2 - j + 32 << 6) + (k2 - 1 - k + 32 << 12);
/*      */             }
/*      */             
/* 2576 */             if (b(enumskyblock, i2, j2, k2 + 1) < i3) {
/* 2577 */               this.I[i1++] = i2 - i + 32 + (j2 - j + 32 << 6) + (k2 + 1 - k + 32 << 12);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2585 */     if (chunk.world.paperSpigotConfig.useAsyncLighting) {
/* 2586 */       chunk.pendingLightUpdates.decrementAndGet();
/* 2587 */       if (neighbors != null) {
/* 2588 */         for (Chunk neighbor : neighbors) {
/* 2589 */           neighbor.pendingLightUpdates.decrementAndGet();
/*      */         }
/*      */       }
/*      */     } 
/*      */     
/* 2594 */     this.methodProfiler.b();
/* 2595 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean updateLight(final EnumSkyBlock enumskyblock, final int x, final int y, final int z) {
/* 2603 */     final Chunk chunk = getChunkIfLoaded(x >> 4, z >> 4);
/* 2604 */     if (chunk == null || !chunk.areNeighborsLoaded(1)) {
/* 2605 */       return false;
/*      */     }
/*      */     
/* 2608 */     if (!chunk.world.paperSpigotConfig.useAsyncLighting) {
/* 2609 */       return c(enumskyblock, x, y, z, chunk, (List<Chunk>)null);
/*      */     }
/*      */     
/* 2612 */     chunk.pendingLightUpdates.incrementAndGet();
/* 2613 */     chunk.lightUpdateTime = chunk.world.getTime();
/*      */     
/* 2615 */     final List<Chunk> neighbors = new ArrayList<Chunk>();
/* 2616 */     for (int cx = (x >> 4) - 1; cx <= (x >> 4) + 1; cx++) {
/* 2617 */       for (int cz = (z >> 4) - 1; cz <= (z >> 4) + 1; cz++) {
/* 2618 */         if (cx != x >> 4 && cz != z >> 4) {
/* 2619 */           Chunk neighbor = getChunkIfLoaded(cx, cz);
/* 2620 */           if (neighbor != null) {
/* 2621 */             neighbor.pendingLightUpdates.incrementAndGet();
/* 2622 */             neighbor.lightUpdateTime = chunk.world.getTime();
/* 2623 */             neighbors.add(neighbor);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2629 */     if (!Bukkit.isPrimaryThread()) {
/* 2630 */       return c(enumskyblock, x, y, z, chunk, neighbors);
/*      */     }
/*      */     
/* 2633 */     this.lightingExecutor.submit(new Runnable()
/*      */         {
/*      */           public void run() {
/* 2636 */             World.this.c(enumskyblock, x, y, z, chunk, neighbors);
/*      */           }
/*      */         });
/* 2639 */     return true;
/*      */   }
/*      */   
/*      */   public boolean a(boolean flag) {
/* 2643 */     return false;
/*      */   }
/*      */   
/*      */   public List a(Chunk chunk, boolean flag) {
/* 2647 */     return null;
/*      */   }
/*      */   
/*      */   public List getEntities(Entity entity, AxisAlignedBB axisalignedbb) {
/* 2651 */     return getEntities(entity, axisalignedbb, (IEntitySelector)null);
/*      */   }
/*      */   
/*      */   public List getEntities(Entity entity, AxisAlignedBB axisalignedbb, IEntitySelector ientityselector) {
/* 2655 */     ArrayList arraylist = new ArrayList();
/* 2656 */     int i = MathHelper.floor((axisalignedbb.a - 2.0D) / 16.0D);
/* 2657 */     int j = MathHelper.floor((axisalignedbb.d + 2.0D) / 16.0D);
/* 2658 */     int k = MathHelper.floor((axisalignedbb.c - 2.0D) / 16.0D);
/* 2659 */     int l = MathHelper.floor((axisalignedbb.f + 2.0D) / 16.0D);
/*      */     
/* 2661 */     for (int i1 = i; i1 <= j; i1++) {
/* 2662 */       for (int j1 = k; j1 <= l; j1++) {
/* 2663 */         if (isChunkLoaded(i1, j1)) {
/* 2664 */           getChunkAt(i1, j1).a(entity, axisalignedbb, arraylist, ientityselector);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2669 */     return arraylist;
/*      */   }
/*      */   
/*      */   public List a(Class oclass, AxisAlignedBB axisalignedbb) {
/* 2673 */     return a(oclass, axisalignedbb, (IEntitySelector)null);
/*      */   }
/*      */   
/*      */   public List a(Class oclass, AxisAlignedBB axisalignedbb, IEntitySelector ientityselector) {
/* 2677 */     int i = MathHelper.floor((axisalignedbb.a - 2.0D) / 16.0D);
/* 2678 */     int j = MathHelper.floor((axisalignedbb.d + 2.0D) / 16.0D);
/* 2679 */     int k = MathHelper.floor((axisalignedbb.c - 2.0D) / 16.0D);
/* 2680 */     int l = MathHelper.floor((axisalignedbb.f + 2.0D) / 16.0D);
/* 2681 */     ArrayList arraylist = new ArrayList();
/*      */     
/* 2683 */     for (int i1 = i; i1 <= j; i1++) {
/* 2684 */       for (int j1 = k; j1 <= l; j1++) {
/* 2685 */         if (isChunkLoaded(i1, j1)) {
/* 2686 */           getChunkAt(i1, j1).a(oclass, axisalignedbb, arraylist, ientityselector);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2691 */     return arraylist;
/*      */   }
/*      */   
/*      */   public Entity a(Class oclass, AxisAlignedBB axisalignedbb, Entity entity) {
/* 2695 */     List<Entity> list = a(oclass, axisalignedbb);
/* 2696 */     Entity entity1 = null;
/* 2697 */     double d0 = Double.MAX_VALUE;
/*      */     
/* 2699 */     for (int i = 0; i < list.size(); i++) {
/* 2700 */       Entity entity2 = list.get(i);
/*      */       
/* 2702 */       if (entity2 != entity) {
/* 2703 */         double d1 = entity.f(entity2);
/*      */         
/* 2705 */         if (d1 <= d0) {
/* 2706 */           entity1 = entity2;
/* 2707 */           d0 = d1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2712 */     return entity1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void b(int i, int j, int k, TileEntity tileentity) {
/* 2718 */     if (isLoaded(i, j, k)) {
/* 2719 */       getChunkAtWorldCoords(i, k).e();
/*      */     }
/*      */   }
/*      */   
/*      */   public int a(Class oclass) {
/* 2724 */     int i = 0;
/*      */     
/* 2726 */     for (int j = 0; j < this.entityList.size(); j++) {
/* 2727 */       Entity entity = this.entityList.get(j);
/*      */ 
/*      */       
/* 2730 */       if (entity instanceof EntityInsentient) {
/* 2731 */         EntityInsentient entityinsentient = (EntityInsentient)entity;
/* 2732 */         if (entityinsentient.isTypeNotPersistent() && entityinsentient.isPersistent()) {
/*      */           continue;
/*      */         }
/*      */       } 
/*      */       
/* 2737 */       if (oclass.isAssignableFrom(entity.getClass()))
/*      */       {
/*      */         
/* 2740 */         i++;
/*      */       }
/*      */       continue;
/*      */     } 
/* 2744 */     return i;
/*      */   }
/*      */   
/*      */   public void a(List<Entity> list) {
/* 2748 */     AsyncCatcher.catchOp("entity world add");
/*      */ 
/*      */     
/* 2751 */     Entity entity = null;
/*      */     
/* 2753 */     for (int i = 0; i < list.size(); i++) {
/* 2754 */       entity = list.get(i);
/* 2755 */       if (entity != null) {
/*      */ 
/*      */         
/* 2758 */         this.entityList.add(entity);
/*      */         
/* 2760 */         a(list.get(i));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   public void b(List list) {
/* 2765 */     this.f.addAll(list);
/*      */   }
/*      */   
/*      */   public boolean mayPlace(Block block, int i, int j, int k, boolean flag, int l, Entity entity, ItemStack itemstack) {
/* 2769 */     Block block1 = getType(i, j, k);
/* 2770 */     AxisAlignedBB axisalignedbb = flag ? null : block.a(this, i, j, k);
/*      */ 
/*      */     
/* 2773 */     boolean defaultReturn = (axisalignedbb != null && !a(axisalignedbb, entity)) ? false : ((block1.getMaterial() == Material.ORIENTABLE && block == Blocks.ANVIL) ? true : ((block1.getMaterial().isReplaceable() && block.canPlace(this, i, j, k, l, itemstack))));
/*      */ 
/*      */     
/* 2776 */     BlockCanBuildEvent event = new BlockCanBuildEvent(getWorld().getBlockAt(i, j, k), CraftMagicNumbers.getId(block), defaultReturn);
/* 2777 */     getServer().getPluginManager().callEvent((Event)event);
/*      */     
/* 2779 */     return event.isBuildable();
/*      */   }
/*      */ 
/*      */   
/*      */   public PathEntity findPath(Entity entity, Entity entity1, float f, boolean flag, boolean flag1, boolean flag2, boolean flag3) {
/* 2784 */     this.methodProfiler.a("pathfind");
/* 2785 */     int i = MathHelper.floor(entity.locX);
/* 2786 */     int j = MathHelper.floor(entity.locY + 1.0D);
/* 2787 */     int k = MathHelper.floor(entity.locZ);
/* 2788 */     int l = (int)(f + 16.0F);
/* 2789 */     int i1 = i - l;
/* 2790 */     int j1 = j - l;
/* 2791 */     int k1 = k - l;
/* 2792 */     int l1 = i + l;
/* 2793 */     int i2 = j + l;
/* 2794 */     int j2 = k + l;
/* 2795 */     ChunkCache chunkcache = new ChunkCache(this, i1, j1, k1, l1, i2, j2, 0);
/* 2796 */     PathEntity pathentity = (new Pathfinder(chunkcache, flag, flag1, flag2, flag3)).a(entity, entity1, f);
/*      */     
/* 2798 */     this.methodProfiler.b();
/* 2799 */     return pathentity;
/*      */   }
/*      */   
/*      */   public PathEntity a(Entity entity, int i, int j, int k, float f, boolean flag, boolean flag1, boolean flag2, boolean flag3) {
/* 2803 */     this.methodProfiler.a("pathfind");
/* 2804 */     int l = MathHelper.floor(entity.locX);
/* 2805 */     int i1 = MathHelper.floor(entity.locY);
/* 2806 */     int j1 = MathHelper.floor(entity.locZ);
/* 2807 */     int k1 = (int)(f + 8.0F);
/* 2808 */     int l1 = l - k1;
/* 2809 */     int i2 = i1 - k1;
/* 2810 */     int j2 = j1 - k1;
/* 2811 */     int k2 = l + k1;
/* 2812 */     int l2 = i1 + k1;
/* 2813 */     int i3 = j1 + k1;
/* 2814 */     ChunkCache chunkcache = new ChunkCache(this, l1, i2, j2, k2, l2, i3, 0);
/* 2815 */     PathEntity pathentity = (new Pathfinder(chunkcache, flag, flag1, flag2, flag3)).a(entity, i, j, k, f);
/*      */     
/* 2817 */     this.methodProfiler.b();
/* 2818 */     return pathentity;
/*      */   }
/*      */   
/*      */   public int getBlockPower(int i, int j, int k, int l) {
/* 2822 */     return getType(i, j, k).c(this, i, j, k, l);
/*      */   }
/*      */   
/*      */   public int getBlockPower(int i, int j, int k) {
/* 2826 */     byte b0 = 0;
/* 2827 */     int l = Math.max(b0, getBlockPower(i, j - 1, k, 0));
/*      */     
/* 2829 */     if (l >= 15) {
/* 2830 */       return l;
/*      */     }
/* 2832 */     l = Math.max(l, getBlockPower(i, j + 1, k, 1));
/* 2833 */     if (l >= 15) {
/* 2834 */       return l;
/*      */     }
/* 2836 */     l = Math.max(l, getBlockPower(i, j, k - 1, 2));
/* 2837 */     if (l >= 15) {
/* 2838 */       return l;
/*      */     }
/* 2840 */     l = Math.max(l, getBlockPower(i, j, k + 1, 3));
/* 2841 */     if (l >= 15) {
/* 2842 */       return l;
/*      */     }
/* 2844 */     l = Math.max(l, getBlockPower(i - 1, j, k, 4));
/* 2845 */     if (l >= 15) {
/* 2846 */       return l;
/*      */     }
/* 2848 */     l = Math.max(l, getBlockPower(i + 1, j, k, 5));
/* 2849 */     return (l >= 15) ? l : l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockFacePowered(int i, int j, int k, int l) {
/* 2858 */     return (getBlockFacePower(i, j, k, l) > 0);
/*      */   }
/*      */   
/*      */   public int getBlockFacePower(int i, int j, int k, int l) {
/* 2862 */     return getType(i, j, k).r() ? getBlockPower(i, j, k) : getType(i, j, k).b(this, i, j, k, l);
/*      */   }
/*      */   
/*      */   public boolean isBlockIndirectlyPowered(int i, int j, int k) {
/* 2866 */     return (getBlockFacePower(i, j - 1, k, 0) > 0) ? true : ((getBlockFacePower(i, j + 1, k, 1) > 0) ? true : ((getBlockFacePower(i, j, k - 1, 2) > 0) ? true : ((getBlockFacePower(i, j, k + 1, 3) > 0) ? true : ((getBlockFacePower(i - 1, j, k, 4) > 0) ? true : ((getBlockFacePower(i + 1, j, k, 5) > 0))))));
/*      */   }
/*      */   
/*      */   public int getHighestNeighborSignal(int i, int j, int k) {
/* 2870 */     int l = 0;
/*      */     
/* 2872 */     for (int i1 = 0; i1 < 6; i1++) {
/* 2873 */       int j1 = getBlockFacePower(i + Facing.b[i1], j + Facing.c[i1], k + Facing.d[i1], i1);
/*      */       
/* 2875 */       if (j1 >= 15) {
/* 2876 */         return 15;
/*      */       }
/*      */       
/* 2879 */       if (j1 > l) {
/* 2880 */         l = j1;
/*      */       }
/*      */     } 
/*      */     
/* 2884 */     return l;
/*      */   }
/*      */   
/*      */   public EntityHuman findNearbyPlayer(Entity entity, double d0) {
/* 2888 */     return findNearbyPlayer(entity.locX, entity.locY, entity.locZ, d0);
/*      */   }
/*      */   
/*      */   public EntityHuman findNearbyPlayer(double d0, double d1, double d2, double d3) {
/* 2892 */     double d4 = -1.0D;
/* 2893 */     EntityHuman entityhuman = null;
/*      */     
/* 2895 */     for (int i = 0; i < this.players.size(); i++) {
/* 2896 */       EntityHuman entityhuman1 = this.players.get(i);
/*      */       
/* 2898 */       if (entityhuman1 != null && !entityhuman1.dead) {
/*      */ 
/*      */ 
/*      */         
/* 2902 */         double d5 = entityhuman1.e(d0, d1, d2);
/*      */         
/* 2904 */         if ((d3 < 0.0D || d5 < d3 * d3) && (d4 == -1.0D || d5 < d4)) {
/* 2905 */           d4 = d5;
/* 2906 */           entityhuman = entityhuman1;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2910 */     return entityhuman;
/*      */   }
/*      */   
/*      */   public EntityHuman findNearbyVulnerablePlayer(Entity entity, double d0) {
/* 2914 */     return findNearbyVulnerablePlayer(entity.locX, entity.locY, entity.locZ, d0);
/*      */   }
/*      */   
/*      */   public EntityHuman findNearbyVulnerablePlayer(double d0, double d1, double d2, double d3) {
/* 2918 */     double d4 = -1.0D;
/* 2919 */     EntityHuman entityhuman = null;
/*      */     
/* 2921 */     for (int i = 0; i < this.players.size(); i++) {
/* 2922 */       EntityHuman entityhuman1 = this.players.get(i);
/*      */       
/* 2924 */       if (entityhuman1 != null && !entityhuman1.dead)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 2929 */         if (!entityhuman1.abilities.isInvulnerable && entityhuman1.isAlive()) {
/* 2930 */           double d5 = entityhuman1.e(d0, d1, d2);
/* 2931 */           double d6 = d3;
/*      */           
/* 2933 */           if (entityhuman1.isSneaking()) {
/* 2934 */             d6 = d3 * 0.800000011920929D;
/*      */           }
/*      */           
/* 2937 */           if (entityhuman1.isInvisible()) {
/* 2938 */             float f = entityhuman1.bE();
/*      */             
/* 2940 */             if (f < 0.1F) {
/* 2941 */               f = 0.1F;
/*      */             }
/*      */             
/* 2944 */             d6 *= (0.7F * f);
/*      */           } 
/*      */           
/* 2947 */           if ((d3 < 0.0D || d5 < d6 * d6) && (d4 == -1.0D || d5 < d4)) {
/* 2948 */             d4 = d5;
/* 2949 */             entityhuman = entityhuman1;
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 2954 */     return entityhuman;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityHuman findNearbyPlayerWhoAffectsSpawning(Entity entity, double radius) {
/* 2959 */     return findNearbyPlayerWhoAffectsSpawning(entity.locX, entity.locY, entity.locZ, radius);
/*      */   }
/*      */   
/*      */   public EntityHuman findNearbyPlayerWhoAffectsSpawning(double x, double y, double z, double radius) {
/* 2963 */     double nearestRadius = -1.0D;
/* 2964 */     EntityHuman entityHuman = null;
/*      */     
/* 2966 */     for (int i = 0; i < this.players.size(); i++) {
/* 2967 */       EntityHuman nearestPlayer = this.players.get(i);
/*      */       
/* 2969 */       if (nearestPlayer != null && !nearestPlayer.dead && nearestPlayer.affectsSpawning) {
/*      */ 
/*      */ 
/*      */         
/* 2973 */         double distance = nearestPlayer.e(x, y, z);
/*      */         
/* 2975 */         if ((radius < 0.0D || distance < radius * radius) && (nearestRadius == -1.0D || distance < nearestRadius)) {
/* 2976 */           nearestRadius = distance;
/* 2977 */           entityHuman = nearestPlayer;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2981 */     return entityHuman;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityHuman a(String s) {
/* 2986 */     for (int i = 0; i < this.players.size(); i++) {
/* 2987 */       EntityHuman entityhuman = this.players.get(i);
/*      */       
/* 2989 */       if (s.equals(entityhuman.getName())) {
/* 2990 */         return entityhuman;
/*      */       }
/*      */     } 
/*      */     
/* 2994 */     return null;
/*      */   }
/*      */   
/*      */   public EntityHuman a(UUID uuid) {
/* 2998 */     for (int i = 0; i < this.players.size(); i++) {
/* 2999 */       EntityHuman entityhuman = this.players.get(i);
/*      */       
/* 3001 */       if (uuid.equals(entityhuman.getUniqueID())) {
/* 3002 */         return entityhuman;
/*      */       }
/*      */     } 
/*      */     
/* 3006 */     return null;
/*      */   }
/*      */   
/*      */   public void G() throws ExceptionWorldConflict {
/* 3010 */     this.dataManager.checkSession();
/*      */   }
/*      */   
/*      */   public long getSeed() {
/* 3014 */     return this.worldData.getSeed();
/*      */   }
/*      */   
/*      */   public long getTime() {
/* 3018 */     return this.worldData.getTime();
/*      */   }
/*      */   
/*      */   public long getDayTime() {
/* 3022 */     return this.worldData.getDayTime();
/*      */   }
/*      */   
/*      */   public void setDayTime(long i) {
/* 3026 */     this.worldData.setDayTime(i);
/*      */   }
/*      */   
/*      */   public ChunkCoordinates getSpawn() {
/* 3030 */     return new ChunkCoordinates(this.worldData.c(), this.worldData.d(), this.worldData.e());
/*      */   }
/*      */   
/*      */   public void x(int i, int j, int k) {
/* 3034 */     this.worldData.setSpawn(i, j, k);
/*      */   }
/*      */   
/*      */   public boolean a(EntityHuman entityhuman, int i, int j, int k) {
/* 3038 */     return true;
/*      */   }
/*      */   
/*      */   public void broadcastEntityEffect(Entity entity, byte b0) {}
/*      */   
/*      */   public IChunkProvider L() {
/* 3044 */     return this.chunkProvider;
/*      */   }
/*      */   
/*      */   public void playBlockAction(int i, int j, int k, Block block, int l, int i1) {
/* 3048 */     block.a(this, i, j, k, l, i1);
/*      */   }
/*      */   
/*      */   public IDataManager getDataManager() {
/* 3052 */     return this.dataManager;
/*      */   }
/*      */   
/*      */   public WorldData getWorldData() {
/* 3056 */     return this.worldData;
/*      */   }
/*      */   
/*      */   public GameRules getGameRules() {
/* 3060 */     return this.worldData.getGameRules();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void everyoneSleeping() {}
/*      */ 
/*      */   
/*      */   public void checkSleepStatus() {
/* 3069 */     if (!this.isStatic) {
/* 3070 */       everyoneSleeping();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public float h(float f) {
/* 3076 */     return (this.o + (this.p - this.o) * f) * j(f);
/*      */   }
/*      */   
/*      */   public float j(float f) {
/* 3080 */     return this.m + (this.n - this.m) * f;
/*      */   }
/*      */   
/*      */   public boolean P() {
/* 3084 */     return (h(1.0F) > 0.9D);
/*      */   }
/*      */   
/*      */   public boolean Q() {
/* 3088 */     return (j(1.0F) > 0.2D);
/*      */   }
/*      */   
/*      */   public boolean isRainingAt(int i, int j, int k) {
/* 3092 */     if (!Q())
/* 3093 */       return false; 
/* 3094 */     if (!i(i, j, k))
/* 3095 */       return false; 
/* 3096 */     if (h(i, k) > j) {
/* 3097 */       return false;
/*      */     }
/* 3099 */     BiomeBase biomebase = getBiome(i, k);
/*      */     
/* 3101 */     return biomebase.d() ? false : (e(i, j, k, false) ? false : biomebase.e());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean z(int i, int j, int k) {
/* 3106 */     BiomeBase biomebase = getBiome(i, k);
/*      */     
/* 3108 */     return biomebase.f();
/*      */   }
/*      */   
/*      */   public void a(String s, PersistentBase persistentbase) {
/* 3112 */     this.worldMaps.a(s, persistentbase);
/*      */   }
/*      */   
/*      */   public PersistentBase a(Class oclass, String s) {
/* 3116 */     return this.worldMaps.get(oclass, s);
/*      */   }
/*      */   
/*      */   public int b(String s) {
/* 3120 */     return this.worldMaps.a(s);
/*      */   }
/*      */   
/*      */   public void b(int i, int j, int k, int l, int i1) {
/* 3124 */     for (int j1 = 0; j1 < this.u.size(); j1++) {
/* 3125 */       ((IWorldAccess)this.u.get(j1)).a(i, j, k, l, i1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void triggerEffect(int i, int j, int k, int l, int i1) {
/* 3130 */     a((EntityHuman)null, i, j, k, l, i1);
/*      */   }
/*      */   
/*      */   public void a(EntityHuman entityhuman, int i, int j, int k, int l, int i1) {
/*      */     try {
/* 3135 */       for (int j1 = 0; j1 < this.u.size(); j1++) {
/* 3136 */         ((IWorldAccess)this.u.get(j1)).a(entityhuman, i, j, k, l, i1);
/*      */       }
/* 3138 */     } catch (Throwable throwable) {
/* 3139 */       CrashReport crashreport = CrashReport.a(throwable, "Playing level event");
/* 3140 */       CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Level event being played");
/*      */       
/* 3142 */       crashreportsystemdetails.a("Block coordinates", CrashReportSystemDetails.a(j, k, l));
/* 3143 */       crashreportsystemdetails.a("Event source", entityhuman);
/* 3144 */       crashreportsystemdetails.a("Event type", Integer.valueOf(i));
/* 3145 */       crashreportsystemdetails.a("Event data", Integer.valueOf(i1));
/* 3146 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getHeight() {
/* 3151 */     return 256;
/*      */   }
/*      */   
/*      */   public int S() {
/* 3155 */     return this.worldProvider.g ? 128 : 256;
/*      */   }
/*      */   
/*      */   public Random A(int i, int j, int k) {
/* 3159 */     long l = i * 341873128712L + j * 132897987541L + getWorldData().getSeed() + k;
/*      */     
/* 3161 */     this.random.setSeed(l);
/* 3162 */     return this.random;
/*      */   }
/*      */   
/*      */   public ChunkPosition b(String s, int i, int j, int k) {
/* 3166 */     return L().findNearestMapFeature(this, s, i, j, k);
/*      */   }
/*      */   
/*      */   public CrashReportSystemDetails a(CrashReport crashreport) {
/* 3170 */     CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Affected level", 1);
/*      */     
/* 3172 */     crashreportsystemdetails.a("Level name", (this.worldData == null) ? "????" : this.worldData.getName());
/* 3173 */     crashreportsystemdetails.a("All players", new CrashReportPlayers(this));
/* 3174 */     crashreportsystemdetails.a("Chunk stats", new CrashReportChunkStats(this));
/*      */     
/*      */     try {
/* 3177 */       this.worldData.a(crashreportsystemdetails);
/* 3178 */     } catch (Throwable throwable) {
/* 3179 */       crashreportsystemdetails.a("Level Data Unobtainable", throwable);
/*      */     } 
/*      */     
/* 3182 */     return crashreportsystemdetails;
/*      */   }
/*      */   
/*      */   public void d(int i, int j, int k, int l, int i1) {
/* 3186 */     for (int j1 = 0; j1 < this.u.size(); j1++) {
/* 3187 */       IWorldAccess iworldaccess = this.u.get(j1);
/*      */       
/* 3189 */       iworldaccess.b(i, j, k, l, i1);
/*      */     } 
/*      */   }
/*      */   
/*      */   public Calendar V() {
/* 3194 */     if (getTime() % 600L == 0L) {
/* 3195 */       this.J.setTimeInMillis(MinecraftServer.ar());
/*      */     }
/*      */     
/* 3198 */     return this.J;
/*      */   }
/*      */   
/*      */   public Scoreboard getScoreboard() {
/* 3202 */     return this.scoreboard;
/*      */   }
/*      */   
/*      */   public void updateAdjacentComparators(int i, int j, int k, Block block) {
/* 3206 */     for (int l = 0; l < 4; l++) {
/* 3207 */       int i1 = i + Direction.a[l];
/* 3208 */       int j1 = k + Direction.b[l];
/* 3209 */       Block block1 = getType(i1, j, j1);
/*      */       
/* 3211 */       if (Blocks.REDSTONE_COMPARATOR_OFF.e(block1)) {
/* 3212 */         block1.doPhysics(this, i1, j, j1, block);
/* 3213 */       } else if (block1.r()) {
/* 3214 */         i1 += Direction.a[l];
/* 3215 */         j1 += Direction.b[l];
/* 3216 */         Block block2 = getType(i1, j, j1);
/*      */         
/* 3218 */         if (Blocks.REDSTONE_COMPARATOR_OFF.e(block2)) {
/* 3219 */           block2.doPhysics(this, i1, j, j1, block);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public float b(double d0, double d1, double d2) {
/* 3226 */     return B(MathHelper.floor(d0), MathHelper.floor(d1), MathHelper.floor(d2));
/*      */   }
/*      */   
/*      */   public float B(int i, int j, int k) {
/* 3230 */     float f = 0.0F;
/* 3231 */     boolean flag = (this.difficulty == EnumDifficulty.HARD);
/*      */     
/* 3233 */     if (isLoaded(i, j, k)) {
/* 3234 */       float f1 = y();
/*      */       
/* 3236 */       f += MathHelper.a((float)(getChunkAtWorldCoords(i, k)).s / 3600000.0F, 0.0F, 1.0F) * (flag ? 1.0F : 0.75F);
/* 3237 */       f += f1 * 0.25F;
/*      */     } 
/*      */     
/* 3240 */     if (this.difficulty == EnumDifficulty.EASY || this.difficulty == EnumDifficulty.PEACEFUL) {
/* 3241 */       f *= this.difficulty.a() / 2.0F;
/*      */     }
/*      */     
/* 3244 */     return MathHelper.a(f, 0.0F, flag ? 1.5F : 1.0F);
/*      */   }
/*      */   
/*      */   public void X() {
/* 3248 */     Iterator<IWorldAccess> iterator = this.u.iterator();
/*      */     
/* 3250 */     while (iterator.hasNext()) {
/* 3251 */       IWorldAccess iworldaccess = iterator.next();
/*      */       
/* 3253 */       iworldaccess.b();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected abstract IChunkProvider j();
/*      */   
/*      */   protected abstract int p();
/*      */   
/*      */   public abstract Entity getEntity(int paramInt);
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\World.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */