/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ChunkRegionLoader
/*     */   implements IChunkLoader, IAsyncChunkSaver {
/*  18 */   private LinkedHashMap<ChunkCoordIntPair, PendingChunkToSave> pendingSaves = new LinkedHashMap<ChunkCoordIntPair, PendingChunkToSave>();
/*  19 */   private static final Logger a = LogManager.getLogger();
/*  20 */   private List b = new ArrayList();
/*  21 */   private Set c = new HashSet();
/*  22 */   private Object d = new Object();
/*     */   private final File e;
/*     */   
/*     */   public ChunkRegionLoader(File file1) {
/*  26 */     this.e = file1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean chunkExists(World world, int i, int j) {
/*  31 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
/*     */     
/*  33 */     synchronized (this.d) {
/*     */       
/*  35 */       if (this.pendingSaves.containsKey(chunkcoordintpair)) {
/*  36 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  41 */     return RegionFileCache.a(this.e, i, j).chunkExists(i & 0x1F, j & 0x1F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk a(World world, int i, int j) {
/*  47 */     world.timings.syncChunkLoadDataTimer.startTiming();
/*  48 */     Object[] data = loadChunk(world, i, j);
/*  49 */     world.timings.syncChunkLoadDataTimer.stopTiming();
/*  50 */     if (data != null) {
/*  51 */       Chunk chunk = (Chunk)data[0];
/*  52 */       NBTTagCompound nbttagcompound = (NBTTagCompound)data[1];
/*  53 */       loadEntities(chunk, nbttagcompound.getCompound("Level"), world);
/*  54 */       return chunk;
/*     */     } 
/*     */     
/*  57 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] loadChunk(World world, int i, int j) {
/*  62 */     NBTTagCompound nbttagcompound = null;
/*  63 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
/*  64 */     Object object = this.d;
/*     */     
/*  66 */     synchronized (this.d) {
/*     */       
/*  68 */       PendingChunkToSave pendingchunktosave = this.pendingSaves.get(chunkcoordintpair);
/*  69 */       if (pendingchunktosave != null) {
/*  70 */         nbttagcompound = pendingchunktosave.b;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  75 */     if (nbttagcompound == null) {
/*  76 */       DataInputStream datainputstream = RegionFileCache.c(this.e, i, j);
/*     */       
/*  78 */       if (datainputstream == null) {
/*  79 */         return null;
/*     */       }
/*     */       
/*  82 */       nbttagcompound = NBTCompressedStreamTools.a(datainputstream);
/*     */     } 
/*     */     
/*  85 */     return a(world, i, j, nbttagcompound);
/*     */   }
/*     */   
/*     */   protected Object[] a(World world, int i, int j, NBTTagCompound nbttagcompound) {
/*  89 */     if (!nbttagcompound.hasKeyOfType("Level", 10)) {
/*  90 */       a.error("Chunk file at " + i + "," + j + " is missing level data, skipping");
/*  91 */       return null;
/*  92 */     }  if (!nbttagcompound.getCompound("Level").hasKeyOfType("Sections", 9)) {
/*  93 */       a.error("Chunk file at " + i + "," + j + " is missing block data, skipping");
/*  94 */       return null;
/*     */     } 
/*  96 */     Chunk chunk = a(world, nbttagcompound.getCompound("Level"));
/*     */     
/*  98 */     if (!chunk.a(i, j)) {
/*  99 */       a.error("Chunk file at " + i + "," + j + " is in the wrong location; relocating. (Expected " + i + ", " + j + ", got " + chunk.locX + ", " + chunk.locZ + ")");
/* 100 */       nbttagcompound.getCompound("Level").setInt("xPos", i);
/* 101 */       nbttagcompound.getCompound("Level").setInt("zPos", j);
/*     */ 
/*     */       
/* 104 */       NBTTagList tileEntities = nbttagcompound.getCompound("Level").getList("TileEntities", 10);
/* 105 */       if (tileEntities != null) {
/* 106 */         for (int te = 0; te < tileEntities.size(); te++) {
/* 107 */           NBTTagCompound tileEntity = tileEntities.get(te);
/* 108 */           int x = tileEntity.getInt("x") - chunk.locX * 16;
/* 109 */           int z = tileEntity.getInt("z") - chunk.locZ * 16;
/* 110 */           tileEntity.setInt("x", i * 16 + x);
/* 111 */           tileEntity.setInt("z", j * 16 + z);
/*     */         } 
/*     */       }
/*     */       
/* 115 */       chunk = a(world, nbttagcompound.getCompound("Level"));
/*     */     } 
/*     */ 
/*     */     
/* 119 */     Object[] data = new Object[2];
/* 120 */     data[0] = chunk;
/* 121 */     data[1] = nbttagcompound;
/* 122 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(World world, Chunk chunk) {
/*     */     try {
/* 130 */       world.G();
/* 131 */     } catch (ExceptionWorldConflict ex) {
/* 132 */       ex.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 137 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 138 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */       
/* 140 */       nbttagcompound.set("Level", nbttagcompound1);
/* 141 */       a(chunk, world, nbttagcompound1);
/* 142 */       a(chunk.l(), nbttagcompound);
/* 143 */     } catch (Exception exception) {
/* 144 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void a(ChunkCoordIntPair chunkcoordintpair, NBTTagCompound nbttagcompound) {
/* 149 */     Object object = this.d;
/*     */     
/* 151 */     synchronized (this.d) {
/*     */       
/* 153 */       if (this.pendingSaves.put(chunkcoordintpair, new PendingChunkToSave(chunkcoordintpair, nbttagcompound)) != null) {
/*     */         return;
/*     */       }
/*     */       
/* 157 */       FileIOThread.a.a(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean c() {
/* 162 */     PendingChunkToSave pendingchunktosave = null;
/* 163 */     Object object = this.d;
/*     */     
/* 165 */     synchronized (this.d) {
/*     */       
/* 167 */       if (this.pendingSaves.isEmpty()) {
/* 168 */         return false;
/*     */       }
/*     */       
/* 171 */       pendingchunktosave = this.pendingSaves.values().iterator().next();
/* 172 */       this.pendingSaves.remove(pendingchunktosave.a);
/*     */     } 
/*     */ 
/*     */     
/* 176 */     if (pendingchunktosave != null) {
/*     */       try {
/* 178 */         a(pendingchunktosave);
/* 179 */       } catch (Exception exception) {
/* 180 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 184 */     return true;
/*     */   }
/*     */   
/*     */   public void a(PendingChunkToSave pendingchunktosave) throws IOException {
/* 188 */     DataOutputStream dataoutputstream = RegionFileCache.d(this.e, pendingchunktosave.a.x, pendingchunktosave.a.z);
/*     */     
/* 190 */     NBTCompressedStreamTools.a(pendingchunktosave.b, dataoutputstream);
/* 191 */     dataoutputstream.close();
/*     */   }
/*     */   
/*     */   public void b(World world, Chunk chunk) {}
/*     */   
/*     */   public void a() {}
/*     */   
/*     */   public void b() {
/* 199 */     while (c());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void a(Chunk chunk, World world, NBTTagCompound nbttagcompound) {
/* 205 */     nbttagcompound.setByte("V", (byte)1);
/* 206 */     nbttagcompound.setInt("xPos", chunk.locX);
/* 207 */     nbttagcompound.setInt("zPos", chunk.locZ);
/* 208 */     nbttagcompound.setLong("LastUpdate", world.getTime());
/* 209 */     nbttagcompound.setIntArray("HeightMap", chunk.heightMap);
/* 210 */     nbttagcompound.setBoolean("TerrainPopulated", chunk.done);
/* 211 */     nbttagcompound.setBoolean("LightPopulated", chunk.lit);
/* 212 */     nbttagcompound.setLong("InhabitedTime", chunk.s);
/* 213 */     ChunkSection[] achunksection = chunk.getSections();
/* 214 */     NBTTagList nbttaglist = new NBTTagList();
/* 215 */     boolean flag = !world.worldProvider.g;
/* 216 */     ChunkSection[] achunksection1 = achunksection;
/* 217 */     int i = achunksection.length;
/*     */ 
/*     */ 
/*     */     
/* 221 */     for (int j = 0; j < i; j++) {
/* 222 */       ChunkSection chunksection = achunksection1[j];
/*     */       
/* 224 */       if (chunksection != null) {
/* 225 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 226 */         nbttagcompound1.setByte("Y", (byte)(chunksection.getYPosition() >> 4 & 0xFF));
/* 227 */         nbttagcompound1.setByteArray("Blocks", chunksection.getIdArray());
/* 228 */         if (chunksection.getExtendedIdArray() != null) {
/* 229 */           nbttagcompound1.setByteArray("Add", (chunksection.getExtendedIdArray()).a);
/*     */         }
/*     */         
/* 232 */         nbttagcompound1.setByteArray("Data", (chunksection.getDataArray()).a);
/* 233 */         nbttagcompound1.setByteArray("BlockLight", (chunksection.getEmittedLightArray()).a);
/* 234 */         if (flag) {
/* 235 */           nbttagcompound1.setByteArray("SkyLight", (chunksection.getSkyLightArray()).a);
/*     */         } else {
/* 237 */           nbttagcompound1.setByteArray("SkyLight", new byte[(chunksection.getEmittedLightArray()).a.length]);
/*     */         } 
/*     */         
/* 240 */         nbttaglist.add(nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     nbttagcompound.set("Sections", nbttaglist);
/* 245 */     nbttagcompound.setByteArray("Biomes", chunk.m());
/* 246 */     chunk.o = false;
/* 247 */     NBTTagList nbttaglist1 = new NBTTagList();
/*     */ 
/*     */ 
/*     */     
/* 251 */     for (i = 0; i < chunk.entitySlices.length; i++) {
/* 252 */       Iterator<?> iterator1 = chunk.entitySlices[i].iterator();
/*     */       
/* 254 */       while (iterator1.hasNext()) {
/* 255 */         Entity entity = (Entity)iterator1.next();
/*     */         
/* 257 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 258 */         if (entity.d(nbttagcompound1)) {
/* 259 */           chunk.o = true;
/* 260 */           nbttaglist1.add(nbttagcompound1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 265 */     nbttagcompound.set("Entities", nbttaglist1);
/* 266 */     NBTTagList nbttaglist2 = new NBTTagList();
/*     */     
/* 268 */     Iterator<TileEntity> iterator = chunk.tileEntities.values().iterator();
/*     */     
/* 270 */     while (iterator.hasNext()) {
/* 271 */       TileEntity tileentity = iterator.next();
/*     */       
/* 273 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 274 */       tileentity.b(nbttagcompound1);
/* 275 */       nbttaglist2.add(nbttagcompound1);
/*     */     } 
/*     */     
/* 278 */     nbttagcompound.set("TileEntities", nbttaglist2);
/* 279 */     List list = world.a(chunk, false);
/*     */     
/* 281 */     if (list != null) {
/* 282 */       long k = world.getTime();
/* 283 */       NBTTagList nbttaglist3 = new NBTTagList();
/* 284 */       Iterator<NextTickListEntry> iterator1 = list.iterator();
/*     */       
/* 286 */       while (iterator1.hasNext()) {
/* 287 */         NextTickListEntry nextticklistentry = iterator1.next();
/* 288 */         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/*     */         
/* 290 */         nbttagcompound2.setInt("i", Block.getId(nextticklistentry.a()));
/* 291 */         nbttagcompound2.setInt("x", nextticklistentry.a);
/* 292 */         nbttagcompound2.setInt("y", nextticklistentry.b);
/* 293 */         nbttagcompound2.setInt("z", nextticklistentry.c);
/* 294 */         nbttagcompound2.setInt("t", (int)(nextticklistentry.d - k));
/* 295 */         nbttagcompound2.setInt("p", nextticklistentry.e);
/* 296 */         nbttaglist3.add(nbttagcompound2);
/*     */       } 
/*     */       
/* 299 */       nbttagcompound.set("TileTicks", nbttaglist3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Chunk a(World world, NBTTagCompound nbttagcompound) {
/* 304 */     int i = nbttagcompound.getInt("xPos");
/* 305 */     int j = nbttagcompound.getInt("zPos");
/* 306 */     Chunk chunk = new Chunk(world, i, j);
/*     */     
/* 308 */     chunk.heightMap = nbttagcompound.getIntArray("HeightMap");
/* 309 */     chunk.done = nbttagcompound.getBoolean("TerrainPopulated");
/* 310 */     chunk.lit = nbttagcompound.getBoolean("LightPopulated");
/* 311 */     chunk.s = nbttagcompound.getLong("InhabitedTime");
/* 312 */     NBTTagList nbttaglist = nbttagcompound.getList("Sections", 10);
/* 313 */     byte b0 = 16;
/* 314 */     ChunkSection[] achunksection = new ChunkSection[b0];
/* 315 */     boolean flag = !world.worldProvider.g;
/*     */     
/* 317 */     for (int k = 0; k < nbttaglist.size(); k++) {
/* 318 */       NBTTagCompound nbttagcompound1 = nbttaglist.get(k);
/* 319 */       byte b1 = nbttagcompound1.getByte("Y");
/* 320 */       ChunkSection chunksection = new ChunkSection(b1 << 4, flag);
/*     */       
/* 322 */       chunksection.setIdArray(nbttagcompound1.getByteArray("Blocks"));
/* 323 */       if (nbttagcompound1.hasKeyOfType("Add", 7)) {
/* 324 */         chunksection.setExtendedIdArray(new NibbleArray(nbttagcompound1.getByteArray("Add"), 4));
/*     */       }
/*     */       
/* 327 */       chunksection.setDataArray(new NibbleArray(nbttagcompound1.getByteArray("Data"), 4));
/* 328 */       chunksection.setEmittedLightArray(new NibbleArray(nbttagcompound1.getByteArray("BlockLight"), 4));
/* 329 */       if (flag) {
/* 330 */         chunksection.setSkyLightArray(new NibbleArray(nbttagcompound1.getByteArray("SkyLight"), 4));
/*     */       }
/*     */       
/* 333 */       chunksection.recalcBlockCounts();
/* 334 */       achunksection[b1] = chunksection;
/*     */     } 
/*     */     
/* 337 */     chunk.a(achunksection);
/* 338 */     if (nbttagcompound.hasKeyOfType("Biomes", 7)) {
/* 339 */       chunk.a(nbttagcompound.getByteArray("Biomes"));
/*     */     }
/*     */ 
/*     */     
/* 343 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadEntities(Chunk chunk, NBTTagCompound nbttagcompound, World world) {
/* 348 */     world.timings.syncChunkLoadEntitiesTimer.startTiming();
/* 349 */     NBTTagList nbttaglist1 = nbttagcompound.getList("Entities", 10);
/*     */     
/* 351 */     if (nbttaglist1 != null) {
/* 352 */       for (int l = 0; l < nbttaglist1.size(); l++) {
/* 353 */         NBTTagCompound nbttagcompound2 = nbttaglist1.get(l);
/* 354 */         Entity entity = EntityTypes.a(nbttagcompound2, world);
/*     */         
/* 356 */         chunk.o = true;
/* 357 */         if (entity != null) {
/* 358 */           chunk.a(entity);
/* 359 */           Entity entity1 = entity;
/*     */           
/* 361 */           for (NBTTagCompound nbttagcompound3 = nbttagcompound2; nbttagcompound3.hasKeyOfType("Riding", 10); nbttagcompound3 = nbttagcompound3.getCompound("Riding")) {
/* 362 */             Entity entity2 = EntityTypes.a(nbttagcompound3.getCompound("Riding"), world);
/*     */             
/* 364 */             if (entity2 != null) {
/* 365 */               chunk.a(entity2);
/* 366 */               entity1.mount(entity2);
/*     */             } 
/*     */             
/* 369 */             entity1 = entity2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 374 */     world.timings.syncChunkLoadEntitiesTimer.stopTiming();
/* 375 */     world.timings.syncChunkLoadTileEntitiesTimer.startTiming();
/* 376 */     NBTTagList nbttaglist2 = nbttagcompound.getList("TileEntities", 10);
/*     */     
/* 378 */     if (nbttaglist2 != null) {
/* 379 */       for (int i1 = 0; i1 < nbttaglist2.size(); i1++) {
/* 380 */         NBTTagCompound nbttagcompound4 = nbttaglist2.get(i1);
/* 381 */         TileEntity tileentity = TileEntity.c(nbttagcompound4);
/*     */         
/* 383 */         if (tileentity != null) {
/* 384 */           chunk.a(tileentity);
/*     */         }
/*     */       } 
/*     */     }
/* 388 */     world.timings.syncChunkLoadTileEntitiesTimer.stopTiming();
/* 389 */     world.timings.syncChunkLoadTileTicksTimer.startTiming();
/*     */     
/* 391 */     if (nbttagcompound.hasKeyOfType("TileTicks", 9)) {
/* 392 */       NBTTagList nbttaglist3 = nbttagcompound.getList("TileTicks", 10);
/*     */       
/* 394 */       if (nbttaglist3 != null) {
/* 395 */         for (int j1 = 0; j1 < nbttaglist3.size(); j1++) {
/* 396 */           NBTTagCompound nbttagcompound5 = nbttaglist3.get(j1);
/*     */           
/* 398 */           world.b(nbttagcompound5.getInt("x"), nbttagcompound5.getInt("y"), nbttagcompound5.getInt("z"), Block.getById(nbttagcompound5.getInt("i")), nbttagcompound5.getInt("t"), nbttagcompound5.getInt("p"));
/*     */         } 
/*     */       }
/*     */     } 
/* 402 */     world.timings.syncChunkLoadTileTicksTimer.stopTiming();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChunkRegionLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */