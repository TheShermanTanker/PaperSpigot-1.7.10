/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldData
/*     */ {
/*     */   private long seed;
/*  14 */   private WorldType type = WorldType.NORMAL;
/*  15 */   private String generatorOptions = "";
/*     */   
/*     */   private int spawnX;
/*     */   
/*     */   private int spawnY;
/*     */   
/*     */   private int spawnZ;
/*     */   private long time;
/*     */   private long dayTime;
/*     */   private long lastPlayed;
/*     */   private long sizeOnDisk;
/*     */   private NBTTagCompound playerData;
/*     */   private int dimension;
/*     */   private String name;
/*     */   private int version;
/*     */   private boolean isRaining;
/*     */   private int rainTicks;
/*     */   private boolean isThundering;
/*     */   private int thunderTicks;
/*     */   private EnumGamemode gameType;
/*     */   private boolean useMapFeatures;
/*     */   private boolean hardcore;
/*     */   private boolean allowCommands;
/*     */   private boolean initialized;
/*  39 */   private GameRules gameRules = new GameRules();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldData(NBTTagCompound paramNBTTagCompound) {
/*  47 */     this.seed = paramNBTTagCompound.getLong("RandomSeed");
/*  48 */     if (paramNBTTagCompound.hasKeyOfType("generatorName", 8)) {
/*  49 */       String str = paramNBTTagCompound.getString("generatorName");
/*  50 */       this.type = WorldType.getType(str);
/*  51 */       if (this.type == null) {
/*  52 */         this.type = WorldType.NORMAL;
/*  53 */       } else if (this.type.f()) {
/*  54 */         int i = 0;
/*  55 */         if (paramNBTTagCompound.hasKeyOfType("generatorVersion", 99)) {
/*  56 */           i = paramNBTTagCompound.getInt("generatorVersion");
/*     */         }
/*  58 */         this.type = this.type.a(i);
/*     */       } 
/*     */       
/*  61 */       if (paramNBTTagCompound.hasKeyOfType("generatorOptions", 8)) this.generatorOptions = paramNBTTagCompound.getString("generatorOptions"); 
/*     */     } 
/*  63 */     this.gameType = EnumGamemode.getById(paramNBTTagCompound.getInt("GameType"));
/*  64 */     if (paramNBTTagCompound.hasKeyOfType("MapFeatures", 99)) {
/*  65 */       this.useMapFeatures = paramNBTTagCompound.getBoolean("MapFeatures");
/*     */     } else {
/*  67 */       this.useMapFeatures = true;
/*     */     } 
/*  69 */     this.spawnX = paramNBTTagCompound.getInt("SpawnX");
/*  70 */     this.spawnY = paramNBTTagCompound.getInt("SpawnY");
/*  71 */     this.spawnZ = paramNBTTagCompound.getInt("SpawnZ");
/*  72 */     this.time = paramNBTTagCompound.getLong("Time");
/*  73 */     if (paramNBTTagCompound.hasKeyOfType("DayTime", 99)) {
/*  74 */       this.dayTime = paramNBTTagCompound.getLong("DayTime");
/*     */     } else {
/*  76 */       this.dayTime = this.time;
/*     */     } 
/*  78 */     this.lastPlayed = paramNBTTagCompound.getLong("LastPlayed");
/*  79 */     this.sizeOnDisk = paramNBTTagCompound.getLong("SizeOnDisk");
/*  80 */     this.name = paramNBTTagCompound.getString("LevelName");
/*  81 */     this.version = paramNBTTagCompound.getInt("version");
/*  82 */     this.rainTicks = paramNBTTagCompound.getInt("rainTime");
/*  83 */     this.isRaining = paramNBTTagCompound.getBoolean("raining");
/*  84 */     this.thunderTicks = paramNBTTagCompound.getInt("thunderTime");
/*  85 */     this.isThundering = paramNBTTagCompound.getBoolean("thundering");
/*  86 */     this.hardcore = paramNBTTagCompound.getBoolean("hardcore");
/*     */     
/*  88 */     if (paramNBTTagCompound.hasKeyOfType("initialized", 99)) {
/*  89 */       this.initialized = paramNBTTagCompound.getBoolean("initialized");
/*     */     } else {
/*  91 */       this.initialized = true;
/*     */     } 
/*     */     
/*  94 */     if (paramNBTTagCompound.hasKeyOfType("allowCommands", 99)) {
/*  95 */       this.allowCommands = paramNBTTagCompound.getBoolean("allowCommands");
/*     */     } else {
/*  97 */       this.allowCommands = (this.gameType == EnumGamemode.CREATIVE);
/*     */     } 
/*     */     
/* 100 */     if (paramNBTTagCompound.hasKeyOfType("Player", 10)) {
/* 101 */       this.playerData = paramNBTTagCompound.getCompound("Player");
/* 102 */       this.dimension = this.playerData.getInt("Dimension");
/*     */     } 
/*     */     
/* 105 */     if (paramNBTTagCompound.hasKeyOfType("GameRules", 10)) {
/* 106 */       this.gameRules.a(paramNBTTagCompound.getCompound("GameRules"));
/*     */     }
/*     */   }
/*     */   
/*     */   public WorldData(WorldSettings paramWorldSettings, String paramString) {
/* 111 */     this.seed = paramWorldSettings.d();
/* 112 */     this.gameType = paramWorldSettings.e();
/* 113 */     this.useMapFeatures = paramWorldSettings.g();
/* 114 */     this.name = paramString;
/* 115 */     this.hardcore = paramWorldSettings.f();
/* 116 */     this.type = paramWorldSettings.h();
/* 117 */     this.generatorOptions = paramWorldSettings.j();
/* 118 */     this.allowCommands = paramWorldSettings.i();
/* 119 */     this.initialized = false;
/*     */   }
/*     */   
/*     */   public WorldData(WorldData paramWorldData) {
/* 123 */     this.seed = paramWorldData.seed;
/* 124 */     this.type = paramWorldData.type;
/* 125 */     this.generatorOptions = paramWorldData.generatorOptions;
/* 126 */     this.gameType = paramWorldData.gameType;
/* 127 */     this.useMapFeatures = paramWorldData.useMapFeatures;
/* 128 */     this.spawnX = paramWorldData.spawnX;
/* 129 */     this.spawnY = paramWorldData.spawnY;
/* 130 */     this.spawnZ = paramWorldData.spawnZ;
/* 131 */     this.time = paramWorldData.time;
/* 132 */     this.dayTime = paramWorldData.dayTime;
/* 133 */     this.lastPlayed = paramWorldData.lastPlayed;
/* 134 */     this.sizeOnDisk = paramWorldData.sizeOnDisk;
/* 135 */     this.playerData = paramWorldData.playerData;
/* 136 */     this.dimension = paramWorldData.dimension;
/* 137 */     this.name = paramWorldData.name;
/* 138 */     this.version = paramWorldData.version;
/* 139 */     this.rainTicks = paramWorldData.rainTicks;
/* 140 */     this.isRaining = paramWorldData.isRaining;
/* 141 */     this.thunderTicks = paramWorldData.thunderTicks;
/* 142 */     this.isThundering = paramWorldData.isThundering;
/* 143 */     this.hardcore = paramWorldData.hardcore;
/* 144 */     this.allowCommands = paramWorldData.allowCommands;
/* 145 */     this.initialized = paramWorldData.initialized;
/* 146 */     this.gameRules = paramWorldData.gameRules;
/*     */   }
/*     */   
/*     */   public NBTTagCompound a() {
/* 150 */     NBTTagCompound nBTTagCompound = new NBTTagCompound();
/*     */     
/* 152 */     a(nBTTagCompound, this.playerData);
/*     */     
/* 154 */     return nBTTagCompound;
/*     */   }
/*     */   
/*     */   public NBTTagCompound a(NBTTagCompound paramNBTTagCompound) {
/* 158 */     NBTTagCompound nBTTagCompound = new NBTTagCompound();
/* 159 */     a(nBTTagCompound, paramNBTTagCompound);
/*     */     
/* 161 */     return nBTTagCompound;
/*     */   }
/*     */   
/*     */   private void a(NBTTagCompound paramNBTTagCompound1, NBTTagCompound paramNBTTagCompound2) {
/* 165 */     paramNBTTagCompound1.setLong("RandomSeed", this.seed);
/* 166 */     paramNBTTagCompound1.setString("generatorName", this.type.name());
/* 167 */     paramNBTTagCompound1.setInt("generatorVersion", this.type.getVersion());
/* 168 */     paramNBTTagCompound1.setString("generatorOptions", this.generatorOptions);
/* 169 */     paramNBTTagCompound1.setInt("GameType", this.gameType.getId());
/* 170 */     paramNBTTagCompound1.setBoolean("MapFeatures", this.useMapFeatures);
/* 171 */     paramNBTTagCompound1.setInt("SpawnX", this.spawnX);
/* 172 */     paramNBTTagCompound1.setInt("SpawnY", this.spawnY);
/* 173 */     paramNBTTagCompound1.setInt("SpawnZ", this.spawnZ);
/* 174 */     paramNBTTagCompound1.setLong("Time", this.time);
/* 175 */     paramNBTTagCompound1.setLong("DayTime", this.dayTime);
/* 176 */     paramNBTTagCompound1.setLong("SizeOnDisk", this.sizeOnDisk);
/* 177 */     paramNBTTagCompound1.setLong("LastPlayed", MinecraftServer.ar());
/* 178 */     paramNBTTagCompound1.setString("LevelName", this.name);
/* 179 */     paramNBTTagCompound1.setInt("version", this.version);
/* 180 */     paramNBTTagCompound1.setInt("rainTime", this.rainTicks);
/* 181 */     paramNBTTagCompound1.setBoolean("raining", this.isRaining);
/* 182 */     paramNBTTagCompound1.setInt("thunderTime", this.thunderTicks);
/* 183 */     paramNBTTagCompound1.setBoolean("thundering", this.isThundering);
/* 184 */     paramNBTTagCompound1.setBoolean("hardcore", this.hardcore);
/* 185 */     paramNBTTagCompound1.setBoolean("allowCommands", this.allowCommands);
/* 186 */     paramNBTTagCompound1.setBoolean("initialized", this.initialized);
/* 187 */     paramNBTTagCompound1.set("GameRules", this.gameRules.a());
/*     */     
/* 189 */     if (paramNBTTagCompound2 != null) {
/* 190 */       paramNBTTagCompound1.set("Player", paramNBTTagCompound2);
/*     */     }
/*     */   }
/*     */   
/*     */   public long getSeed() {
/* 195 */     return this.seed;
/*     */   }
/*     */   
/*     */   public int c() {
/* 199 */     return this.spawnX;
/*     */   }
/*     */   
/*     */   public int d() {
/* 203 */     return this.spawnY;
/*     */   }
/*     */   
/*     */   public int e() {
/* 207 */     return this.spawnZ;
/*     */   }
/*     */   
/*     */   public long getTime() {
/* 211 */     return this.time;
/*     */   }
/*     */   
/*     */   public long getDayTime() {
/* 215 */     return this.dayTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound i() {
/* 223 */     return this.playerData;
/*     */   }
/*     */   
/*     */   public int j() {
/* 227 */     return this.dimension;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTime(long paramLong) {
/* 247 */     this.time = paramLong;
/*     */   }
/*     */   
/*     */   public void setDayTime(long paramLong) {
/* 251 */     this.dayTime = paramLong;
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
/*     */ 
/*     */   
/*     */   public void setSpawn(int paramInt1, int paramInt2, int paramInt3) {
/* 267 */     this.spawnX = paramInt1;
/* 268 */     this.spawnY = paramInt2;
/* 269 */     this.spawnZ = paramInt3;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 273 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String paramString) {
/* 277 */     this.name = paramString;
/*     */   }
/*     */   
/*     */   public int l() {
/* 281 */     return this.version;
/*     */   }
/*     */   
/*     */   public void e(int paramInt) {
/* 285 */     this.version = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThundering() {
/* 293 */     return this.isThundering;
/*     */   }
/*     */   
/*     */   public void setThundering(boolean paramBoolean) {
/* 297 */     this.isThundering = paramBoolean;
/*     */   }
/*     */   
/*     */   public int getThunderDuration() {
/* 301 */     return this.thunderTicks;
/*     */   }
/*     */   
/*     */   public void setThunderDuration(int paramInt) {
/* 305 */     this.thunderTicks = paramInt;
/*     */   }
/*     */   
/*     */   public boolean hasStorm() {
/* 309 */     return this.isRaining;
/*     */   }
/*     */   
/*     */   public void setStorm(boolean paramBoolean) {
/* 313 */     this.isRaining = paramBoolean;
/*     */   }
/*     */   
/*     */   public int getWeatherDuration() {
/* 317 */     return this.rainTicks;
/*     */   }
/*     */   
/*     */   public void setWeatherDuration(int paramInt) {
/* 321 */     this.rainTicks = paramInt;
/*     */   }
/*     */   
/*     */   public EnumGamemode getGameType() {
/* 325 */     return this.gameType;
/*     */   }
/*     */   
/*     */   public boolean shouldGenerateMapFeatures() {
/* 329 */     return this.useMapFeatures;
/*     */   }
/*     */   
/*     */   public void setGameType(EnumGamemode paramEnumGamemode) {
/* 333 */     this.gameType = paramEnumGamemode;
/*     */   }
/*     */   
/*     */   public boolean isHardcore() {
/* 337 */     return this.hardcore;
/*     */   }
/*     */   
/*     */   public WorldType getType() {
/* 341 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(WorldType paramWorldType) {
/* 345 */     this.type = paramWorldType;
/*     */   }
/*     */   
/*     */   public String getGeneratorOptions() {
/* 349 */     return this.generatorOptions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean allowCommands() {
/* 357 */     return this.allowCommands;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInitialized() {
/* 365 */     return this.initialized;
/*     */   }
/*     */   
/*     */   public void d(boolean paramBoolean) {
/* 369 */     this.initialized = paramBoolean;
/*     */   }
/*     */   
/*     */   public GameRules getGameRules() {
/* 373 */     return this.gameRules;
/*     */   }
/*     */   
/*     */   public void a(CrashReportSystemDetails paramCrashReportSystemDetails) {
/* 377 */     paramCrashReportSystemDetails.a("Level seed", new CrashReportLevelSeed(this));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 384 */     paramCrashReportSystemDetails.a("Level generator", new CrashReportLevelGenerator(this));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 391 */     paramCrashReportSystemDetails.a("Level generator options", new CrashReportLevelGeneratorOptions(this));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 398 */     paramCrashReportSystemDetails.a("Level spawn location", new CrashReportLevelSpawnLocation(this));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 405 */     paramCrashReportSystemDetails.a("Level time", new CrashReportLevelTime(this));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 412 */     paramCrashReportSystemDetails.a("Level dimension", new CrashReportLevelDimension(this));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 419 */     paramCrashReportSystemDetails.a("Level storage version", new CrashReportLevelStorageVersion(this));
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
/* 441 */     paramCrashReportSystemDetails.a("Level weather", new CrashReportLevelWeather(this));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 448 */     paramCrashReportSystemDetails.a("Level game mode", new CrashReportLevelGameMode(this));
/*     */   }
/*     */   
/*     */   protected WorldData() {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */