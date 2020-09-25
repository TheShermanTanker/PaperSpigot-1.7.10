/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.chunkio.ChunkIOExecutor;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.LongHash;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.LongHashSet;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.LongObjectHashMap;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.world.ChunkLoadEvent;
/*     */ import org.bukkit.event.world.ChunkPopulateEvent;
/*     */ import org.bukkit.event.world.ChunkUnloadEvent;
/*     */ import org.bukkit.generator.BlockPopulator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkProviderServer
/*     */   implements IChunkProvider
/*     */ {
/*  28 */   private static final Logger b = LogManager.getLogger();
/*     */   
/*  30 */   public LongHashSet unloadQueue = new LongHashSet();
/*     */   public Chunk emptyChunk;
/*     */   public IChunkProvider chunkProvider;
/*     */   private IChunkLoader f;
/*     */   public boolean forceChunkLoad = false;
/*  35 */   public LongObjectHashMap<Chunk> chunks = new LongObjectHashMap();
/*     */   
/*     */   public WorldServer world;
/*     */   
/*     */   public ChunkProviderServer(WorldServer worldserver, IChunkLoader ichunkloader, IChunkProvider ichunkprovider) {
/*  40 */     this.emptyChunk = new EmptyChunk(worldserver, 0, 0);
/*  41 */     this.world = worldserver;
/*  42 */     this.f = ichunkloader;
/*  43 */     this.chunkProvider = ichunkprovider;
/*     */   }
/*     */   
/*     */   public boolean isChunkLoaded(int i, int j) {
/*  47 */     return this.chunks.containsKey(LongHash.toLong(i, j));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection a() {
/*  53 */     return this.chunks.values();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void queueUnload(int i, int j) {
/*  59 */     Chunk chunk = (Chunk)this.chunks.get(LongHash.toLong(i, j));
/*  60 */     if (chunk != null && chunk.world.paperSpigotConfig.useAsyncLighting && (chunk.pendingLightUpdates.get() > 0 || chunk.world.getTime() - chunk.lightUpdateTime < 20L)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  65 */     if (chunk != null) {
/*  66 */       for (List<Entity> entities : chunk.entitySlices) {
/*  67 */         for (Entity entity : entities) {
/*  68 */           if (entity.loadChunks) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*  75 */     if (this.world.worldProvider.e()) {
/*  76 */       ChunkCoordinates chunkcoordinates = this.world.getSpawn();
/*  77 */       int k = i * 16 + 8 - chunkcoordinates.x;
/*  78 */       int l = j * 16 + 8 - chunkcoordinates.z;
/*  79 */       short short1 = 128;
/*     */ 
/*     */       
/*  82 */       if (k < -short1 || k > short1 || l < -short1 || l > short1 || !this.world.keepSpawnInMemory) {
/*  83 */         this.unloadQueue.add(i, j);
/*     */         
/*  85 */         Chunk c = (Chunk)this.chunks.get(LongHash.toLong(i, j));
/*  86 */         if (c != null) {
/*  87 */           c.mustSave = true;
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  93 */       this.unloadQueue.add(i, j);
/*     */       
/*  95 */       Chunk c = (Chunk)this.chunks.get(LongHash.toLong(i, j));
/*  96 */       if (c != null) {
/*  97 */         c.mustSave = true;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void b() {
/* 104 */     Iterator<Chunk> iterator = this.chunks.values().iterator();
/*     */     
/* 106 */     while (iterator.hasNext()) {
/* 107 */       Chunk chunk = iterator.next();
/*     */       
/* 109 */       queueUnload(chunk.locX, chunk.locZ);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk getChunkIfLoaded(int x, int z) {
/* 115 */     return (Chunk)this.chunks.get(LongHash.toLong(x, z));
/*     */   }
/*     */   
/*     */   public Chunk getChunkAt(int i, int j) {
/* 119 */     return getChunkAt(i, j, (Runnable)null);
/*     */   }
/*     */   
/*     */   public Chunk getChunkAt(int i, int j, Runnable runnable) {
/* 123 */     this.unloadQueue.remove(i, j);
/* 124 */     Chunk chunk = (Chunk)this.chunks.get(LongHash.toLong(i, j));
/* 125 */     ChunkRegionLoader loader = null;
/*     */     
/* 127 */     if (this.f instanceof ChunkRegionLoader) {
/* 128 */       loader = (ChunkRegionLoader)this.f;
/*     */     }
/*     */ 
/*     */     
/* 132 */     if (chunk == null && loader != null && loader.chunkExists(this.world, i, j)) {
/* 133 */       if (runnable != null) {
/* 134 */         ChunkIOExecutor.queueChunkLoad(this.world, loader, this, i, j, runnable);
/* 135 */         return null;
/*     */       } 
/* 137 */       chunk = ChunkIOExecutor.syncChunkLoad(this.world, loader, this, i, j);
/*     */     }
/* 139 */     else if (chunk == null) {
/* 140 */       chunk = originalGetChunkAt(i, j);
/*     */     } 
/*     */ 
/*     */     
/* 144 */     if (runnable != null) {
/* 145 */       runnable.run();
/*     */     }
/*     */     
/* 148 */     return chunk;
/*     */   }
/*     */   
/*     */   public Chunk originalGetChunkAt(int i, int j) {
/* 152 */     this.unloadQueue.remove(i, j);
/* 153 */     Chunk chunk = (Chunk)this.chunks.get(LongHash.toLong(i, j));
/* 154 */     boolean newChunk = false;
/*     */     
/* 156 */     if (chunk == null) {
/* 157 */       this.world.timings.syncChunkLoadTimer.startTiming();
/* 158 */       chunk = loadChunk(i, j);
/* 159 */       if (chunk == null) {
/* 160 */         if (this.chunkProvider == null) {
/* 161 */           chunk = this.emptyChunk;
/*     */         } else {
/*     */           try {
/* 164 */             chunk = this.chunkProvider.getOrCreateChunk(i, j);
/* 165 */           } catch (Throwable throwable) {
/* 166 */             CrashReport crashreport = CrashReport.a(throwable, "Exception generating new chunk");
/* 167 */             CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Chunk to be generated");
/*     */             
/* 169 */             crashreportsystemdetails.a("Location", String.format("%d,%d", new Object[] { Integer.valueOf(i), Integer.valueOf(j) }));
/* 170 */             crashreportsystemdetails.a("Position hash", Long.valueOf(LongHash.toLong(i, j)));
/* 171 */             crashreportsystemdetails.a("Generator", this.chunkProvider.getName());
/* 172 */             throw new ReportedException(crashreport);
/*     */           } 
/*     */         } 
/* 175 */         newChunk = true;
/*     */       } 
/*     */       
/* 178 */       this.chunks.put(LongHash.toLong(i, j), chunk);
/* 179 */       chunk.addEntities();
/*     */ 
/*     */       
/* 182 */       CraftServer craftServer = this.world.getServer();
/* 183 */       if (craftServer != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 189 */         craftServer.getPluginManager().callEvent((Event)new ChunkLoadEvent(chunk.bukkitChunk, newChunk));
/*     */       }
/*     */ 
/*     */       
/* 193 */       for (int x = -2; x < 3; x++) {
/* 194 */         for (int z = -2; z < 3; z++) {
/* 195 */           if (x != 0 || z != 0) {
/*     */ 
/*     */ 
/*     */             
/* 199 */             Chunk neighbor = getChunkIfLoaded(chunk.locX + x, chunk.locZ + z);
/* 200 */             if (neighbor != null) {
/* 201 */               neighbor.setNeighborLoaded(-x, -z);
/* 202 */               chunk.setNeighborLoaded(x, z);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 207 */       chunk.loadNearby(this, this, i, j);
/* 208 */       this.world.timings.syncChunkLoadTimer.stopTiming();
/*     */     } 
/*     */     
/* 211 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk getOrCreateChunk(int i, int j) {
/* 216 */     Chunk chunk = (Chunk)this.chunks.get(LongHash.toLong(i, j));
/*     */     
/* 218 */     chunk = (chunk == null) ? ((!this.world.isLoading && !this.forceChunkLoad) ? this.emptyChunk : getChunkAt(i, j)) : chunk;
/* 219 */     if (chunk == this.emptyChunk) return chunk; 
/* 220 */     if (i != chunk.locX || j != chunk.locZ) {
/* 221 */       b.error("Chunk (" + chunk.locX + ", " + chunk.locZ + ") stored at  (" + i + ", " + j + ") in world '" + this.world.getWorld().getName() + "'");
/* 222 */       b.error(chunk.getClass().getName());
/* 223 */       Throwable ex = new Throwable();
/* 224 */       ex.fillInStackTrace();
/* 225 */       ex.printStackTrace();
/*     */     } 
/* 227 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk loadChunk(int i, int j) {
/* 232 */     if (this.f == null) {
/* 233 */       return null;
/*     */     }
/*     */     try {
/* 236 */       Chunk chunk = this.f.a(this.world, i, j);
/*     */       
/* 238 */       if (chunk != null) {
/* 239 */         chunk.lastSaved = this.world.getTime();
/* 240 */         if (this.chunkProvider != null) {
/* 241 */           this.world.timings.syncChunkLoadStructuresTimer.startTiming();
/* 242 */           this.chunkProvider.recreateStructures(i, j);
/* 243 */           this.world.timings.syncChunkLoadStructuresTimer.stopTiming();
/*     */         } 
/*     */       } 
/*     */       
/* 247 */       return chunk;
/* 248 */     } catch (Exception exception) {
/* 249 */       b.error("Couldn't load chunk", exception);
/* 250 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveChunkNOP(Chunk chunk) {
/* 256 */     if (this.f != null) {
/*     */       try {
/* 258 */         this.f.b(this.world, chunk);
/* 259 */       } catch (Exception exception) {
/* 260 */         b.error("Couldn't save entities", exception);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveChunk(Chunk chunk) {
/* 266 */     if (this.f != null) {
/*     */       try {
/* 268 */         chunk.lastSaved = this.world.getTime();
/* 269 */         this.f.a(this.world, chunk);
/*     */       }
/* 271 */       catch (Exception ioexception) {
/* 272 */         b.error("Couldn't save chunk", ioexception);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getChunkAt(IChunkProvider ichunkprovider, int i, int j) {
/* 282 */     Chunk chunk = getOrCreateChunk(i, j);
/*     */     
/* 284 */     if (!chunk.done) {
/* 285 */       chunk.p();
/* 286 */       if (this.chunkProvider != null) {
/* 287 */         this.chunkProvider.getChunkAt(ichunkprovider, i, j);
/*     */ 
/*     */         
/* 290 */         BlockSand.instaFall = true;
/* 291 */         Random random = new Random();
/* 292 */         random.setSeed(this.world.getSeed());
/* 293 */         long xRand = random.nextLong() / 2L * 2L + 1L;
/* 294 */         long zRand = random.nextLong() / 2L * 2L + 1L;
/* 295 */         random.setSeed(i * xRand + j * zRand ^ this.world.getSeed());
/*     */         
/* 297 */         CraftWorld craftWorld = this.world.getWorld();
/* 298 */         if (craftWorld != null) {
/* 299 */           this.world.populating = true;
/*     */           try {
/* 301 */             for (BlockPopulator populator : craftWorld.getPopulators()) {
/* 302 */               populator.populate((World)craftWorld, random, chunk.bukkitChunk);
/*     */             }
/*     */           } finally {
/* 305 */             this.world.populating = false;
/*     */           } 
/*     */         } 
/* 308 */         BlockSand.instaFall = false;
/* 309 */         this.world.getServer().getPluginManager().callEvent((Event)new ChunkPopulateEvent(chunk.bukkitChunk));
/*     */ 
/*     */         
/* 312 */         chunk.e();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
/* 318 */     int i = 0;
/*     */     
/* 320 */     Iterator<Chunk> iterator = this.chunks.values().iterator();
/*     */     
/* 322 */     while (iterator.hasNext()) {
/* 323 */       Chunk chunk = iterator.next();
/*     */ 
/*     */       
/* 326 */       if (flag) {
/* 327 */         saveChunkNOP(chunk);
/*     */       }
/*     */       
/* 330 */       if (chunk.a(flag)) {
/* 331 */         saveChunk(chunk);
/* 332 */         chunk.n = false;
/* 333 */         i++;
/* 334 */         if (i == 24 && !flag) {
/* 335 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 340 */     return true;
/*     */   }
/*     */   
/*     */   public void c() {
/* 344 */     if (this.f != null) {
/* 345 */       this.f.b();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean unloadChunks() {
/* 350 */     if (!this.world.savingDisabled) {
/*     */       
/* 352 */       CraftServer craftServer = this.world.getServer();
/* 353 */       for (int i = 0; i < 100 && !this.unloadQueue.isEmpty(); i++) {
/* 354 */         long chunkcoordinates = this.unloadQueue.popFirst();
/* 355 */         Chunk chunk = (Chunk)this.chunks.get(chunkcoordinates);
/* 356 */         if (chunk != null) {
/*     */           
/* 358 */           ChunkUnloadEvent event = new ChunkUnloadEvent(chunk.bukkitChunk);
/* 359 */           craftServer.getPluginManager().callEvent((Event)event);
/* 360 */           if (!event.isCancelled()) {
/* 361 */             if (chunk != null) {
/* 362 */               chunk.removeEntities();
/* 363 */               saveChunk(chunk);
/* 364 */               saveChunkNOP(chunk);
/* 365 */               this.chunks.remove(chunkcoordinates);
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 372 */             for (int x = -2; x < 3; x++) {
/* 373 */               for (int z = -2; z < 3; z++) {
/* 374 */                 if (x != 0 || z != 0) {
/*     */ 
/*     */ 
/*     */                   
/* 378 */                   Chunk neighbor = getChunkIfLoaded(chunk.locX + x, chunk.locZ + z);
/* 379 */                   if (neighbor != null) {
/* 380 */                     neighbor.setNeighborUnloaded(-x, -z);
/* 381 */                     chunk.setNeighborUnloaded(x, z);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 389 */       if (this.f != null) {
/* 390 */         this.f.a();
/*     */       }
/*     */     } 
/*     */     
/* 394 */     return this.chunkProvider.unloadChunks();
/*     */   }
/*     */   
/*     */   public boolean canSave() {
/* 398 */     return !this.world.savingDisabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 403 */     return "ServerChunkCache: " + this.chunks.values().size() + " Drop: " + this.unloadQueue.size();
/*     */   }
/*     */   
/*     */   public List getMobsFor(EnumCreatureType enumcreaturetype, int i, int j, int k) {
/* 407 */     return this.chunkProvider.getMobsFor(enumcreaturetype, i, j, k);
/*     */   }
/*     */   
/*     */   public ChunkPosition findNearestMapFeature(World world, String s, int i, int j, int k) {
/* 411 */     return this.chunkProvider.findNearestMapFeature(world, s, i, j, k);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLoadedChunks() {
/* 416 */     return this.chunks.size();
/*     */   }
/*     */   
/*     */   public void recreateStructures(int i, int j) {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChunkProviderServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */