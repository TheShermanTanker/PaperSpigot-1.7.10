/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerChunkMap
/*     */ {
/*  18 */   private static final Logger a = LogManager.getLogger();
/*     */   private final WorldServer world;
/*  20 */   private final List managedPlayers = new ArrayList();
/*  21 */   private final LongHashMap d = new LongHashMap();
/*  22 */   private final Queue e = new ConcurrentLinkedQueue();
/*  23 */   private final Queue f = new ConcurrentLinkedQueue();
/*     */   private int g;
/*     */   private long h;
/*  26 */   private final int[][] i = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
/*     */   private boolean wasNotEmpty;
/*     */   
/*     */   public PlayerChunkMap(WorldServer worldserver, int viewDistance) {
/*  30 */     this.world = worldserver;
/*  31 */     a(viewDistance);
/*     */   }
/*     */   
/*     */   public WorldServer a() {
/*  35 */     return this.world;
/*     */   }
/*     */   
/*     */   public void flush() {
/*  39 */     long i = this.world.getTime();
/*     */ 
/*     */ 
/*     */     
/*  43 */     if (i - this.h > 8000L) {
/*  44 */       this.h = i;
/*     */ 
/*     */       
/*  47 */       Iterator<PlayerChunk> iterator = this.f.iterator();
/*  48 */       while (iterator.hasNext()) {
/*  49 */         PlayerChunk playerchunk = iterator.next();
/*  50 */         playerchunk.b();
/*  51 */         playerchunk.a();
/*     */       } 
/*     */     } else {
/*  54 */       Iterator<PlayerChunk> iterator = this.e.iterator();
/*     */       
/*  56 */       while (iterator.hasNext()) {
/*  57 */         PlayerChunk playerchunk = iterator.next();
/*  58 */         playerchunk.b();
/*  59 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  65 */     if (this.managedPlayers.isEmpty()) {
/*  66 */       if (!this.wasNotEmpty)
/*  67 */         return;  WorldProvider worldprovider = this.world.worldProvider;
/*     */       
/*  69 */       if (!worldprovider.e()) {
/*  70 */         this.world.chunkProviderServer.b();
/*     */       }
/*     */       
/*  73 */       this.wasNotEmpty = false;
/*     */     } else {
/*  75 */       this.wasNotEmpty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(int i, int j) {
/*  81 */     long k = i + 2147483647L | j + 2147483647L << 32L;
/*     */     
/*  83 */     return (this.d.getEntry(k) != null);
/*     */   }
/*     */   
/*     */   private PlayerChunk a(int i, int j, boolean flag) {
/*  87 */     long k = i + 2147483647L | j + 2147483647L << 32L;
/*  88 */     PlayerChunk playerchunk = (PlayerChunk)this.d.getEntry(k);
/*     */     
/*  90 */     if (playerchunk == null && flag) {
/*  91 */       playerchunk = new PlayerChunk(this, i, j);
/*  92 */       this.d.put(k, playerchunk);
/*  93 */       this.f.add(playerchunk);
/*     */     } 
/*     */     
/*  96 */     return playerchunk;
/*     */   }
/*     */   
/*     */   public final boolean isChunkInUse(int x, int z) {
/* 100 */     PlayerChunk pi = a(x, z, false);
/* 101 */     if (pi != null) {
/* 102 */       return (PlayerChunk.b(pi).size() > 0);
/*     */     }
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void flagDirty(int i, int j, int k) {
/* 109 */     int l = i >> 4;
/* 110 */     int i1 = k >> 4;
/* 111 */     PlayerChunk playerchunk = a(l, i1, false);
/*     */     
/* 113 */     if (playerchunk != null) {
/* 114 */       playerchunk.a(i & 0xF, j, k & 0xF);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addPlayer(EntityPlayer entityplayer) {
/* 119 */     int i = (int)entityplayer.locX >> 4;
/* 120 */     int j = (int)entityplayer.locZ >> 4;
/*     */     
/* 122 */     entityplayer.d = entityplayer.locX;
/* 123 */     entityplayer.e = entityplayer.locZ;
/*     */ 
/*     */     
/* 126 */     List<ChunkCoordIntPair> chunkList = new LinkedList<ChunkCoordIntPair>();
/*     */     
/* 128 */     for (int k = i - entityplayer.viewDistance; k <= i + entityplayer.viewDistance; k++) {
/* 129 */       for (int l = j - entityplayer.viewDistance; l <= j + entityplayer.viewDistance; l++)
/*     */       {
/* 131 */         chunkList.add(new ChunkCoordIntPair(k, l));
/*     */       }
/*     */     } 
/*     */     
/* 135 */     Collections.sort(chunkList, new ChunkCoordComparator(entityplayer));
/* 136 */     for (ChunkCoordIntPair pair : chunkList) {
/* 137 */       a(pair.x, pair.z, true).a(entityplayer);
/*     */     }
/*     */ 
/*     */     
/* 141 */     this.managedPlayers.add(entityplayer);
/* 142 */     b(entityplayer);
/*     */   }
/*     */   
/*     */   public void b(EntityPlayer entityplayer) {
/* 146 */     ArrayList arraylist = new ArrayList(entityplayer.chunkCoordIntPairQueue);
/* 147 */     int i = 0;
/* 148 */     int j = entityplayer.viewDistance;
/* 149 */     int k = (int)entityplayer.locX >> 4;
/* 150 */     int l = (int)entityplayer.locZ >> 4;
/* 151 */     int i1 = 0;
/* 152 */     int j1 = 0;
/* 153 */     ChunkCoordIntPair chunkcoordintpair = PlayerChunk.a(a(k, l, true));
/*     */     
/* 155 */     entityplayer.chunkCoordIntPairQueue.clear();
/* 156 */     if (arraylist.contains(chunkcoordintpair)) {
/* 157 */       entityplayer.chunkCoordIntPairQueue.add(chunkcoordintpair);
/*     */     }
/*     */     
/*     */     int k1;
/*     */     
/* 162 */     for (k1 = 1; k1 <= j * 2; k1++) {
/* 163 */       for (int l1 = 0; l1 < 2; l1++) {
/* 164 */         int[] aint = this.i[i++ % 4];
/*     */         
/* 166 */         for (int i2 = 0; i2 < k1; i2++) {
/* 167 */           i1 += aint[0];
/* 168 */           j1 += aint[1];
/* 169 */           chunkcoordintpair = PlayerChunk.a(a(k + i1, l + j1, true));
/* 170 */           if (arraylist.contains(chunkcoordintpair)) {
/* 171 */             entityplayer.chunkCoordIntPairQueue.add(chunkcoordintpair);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     i %= 4;
/*     */     
/* 179 */     for (k1 = 0; k1 < j * 2; k1++) {
/* 180 */       i1 += this.i[i][0];
/* 181 */       j1 += this.i[i][1];
/* 182 */       chunkcoordintpair = PlayerChunk.a(a(k + i1, l + j1, true));
/* 183 */       if (arraylist.contains(chunkcoordintpair)) {
/* 184 */         entityplayer.chunkCoordIntPairQueue.add(chunkcoordintpair);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removePlayer(EntityPlayer entityplayer) {
/* 190 */     int i = (int)entityplayer.d >> 4;
/* 191 */     int j = (int)entityplayer.e >> 4;
/*     */ 
/*     */     
/* 194 */     for (int k = i - entityplayer.viewDistance; k <= i + entityplayer.viewDistance; k++) {
/* 195 */       for (int l = j - entityplayer.viewDistance; l <= j + entityplayer.viewDistance; l++) {
/*     */         
/* 197 */         PlayerChunk playerchunk = a(k, l, false);
/*     */         
/* 199 */         if (playerchunk != null) {
/* 200 */           playerchunk.b(entityplayer);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 205 */     this.managedPlayers.remove(entityplayer);
/*     */   }
/*     */   
/*     */   private boolean a(int i, int j, int k, int l, int i1) {
/* 209 */     int j1 = i - k;
/* 210 */     int k1 = j - l;
/*     */     
/* 212 */     return (j1 >= -i1 && j1 <= i1) ? ((k1 >= -i1 && k1 <= i1)) : false;
/*     */   }
/*     */   
/*     */   public void movePlayer(EntityPlayer entityplayer) {
/* 216 */     int i = (int)entityplayer.locX >> 4;
/* 217 */     int j = (int)entityplayer.locZ >> 4;
/* 218 */     double d0 = entityplayer.d - entityplayer.locX;
/* 219 */     double d1 = entityplayer.e - entityplayer.locZ;
/* 220 */     double d2 = d0 * d0 + d1 * d1;
/*     */     
/* 222 */     if (d2 >= 64.0D) {
/* 223 */       int k = (int)entityplayer.d >> 4;
/* 224 */       int l = (int)entityplayer.e >> 4;
/* 225 */       int i1 = entityplayer.viewDistance;
/* 226 */       int j1 = i - k;
/* 227 */       int k1 = j - l;
/* 228 */       List<ChunkCoordIntPair> chunksToLoad = new LinkedList<ChunkCoordIntPair>();
/*     */       
/* 230 */       if (j1 != 0 || k1 != 0) {
/* 231 */         for (int l1 = i - i1; l1 <= i + i1; l1++) {
/* 232 */           for (int i2 = j - i1; i2 <= j + i1; i2++) {
/* 233 */             if (!a(l1, i2, k, l, i1)) {
/* 234 */               chunksToLoad.add(new ChunkCoordIntPair(l1, i2));
/*     */             }
/*     */             
/* 237 */             if (!a(l1 - j1, i2 - k1, i, j, i1)) {
/* 238 */               PlayerChunk playerchunk = a(l1 - j1, i2 - k1, false);
/*     */               
/* 240 */               if (playerchunk != null) {
/* 241 */                 playerchunk.b(entityplayer);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 247 */         b(entityplayer);
/* 248 */         entityplayer.d = entityplayer.locX;
/* 249 */         entityplayer.e = entityplayer.locZ;
/*     */ 
/*     */         
/* 252 */         Collections.sort(chunksToLoad, new ChunkCoordComparator(entityplayer));
/* 253 */         for (ChunkCoordIntPair pair : chunksToLoad) {
/* 254 */           a(pair.x, pair.z, true).a(entityplayer);
/*     */         }
/*     */         
/* 257 */         if (j1 > 1 || j1 < -1 || k1 > 1 || k1 < -1) {
/* 258 */           Collections.sort(entityplayer.chunkCoordIntPairQueue, new ChunkCoordComparator(entityplayer));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(EntityPlayer entityplayer, int i, int j) {
/* 266 */     PlayerChunk playerchunk = a(i, j, false);
/*     */     
/* 268 */     return (playerchunk != null && PlayerChunk.b(playerchunk).contains(entityplayer) && !entityplayer.chunkCoordIntPairQueue.contains(PlayerChunk.a(playerchunk)));
/*     */   }
/*     */   
/*     */   public void a(int i) {
/* 272 */     i = MathHelper.a(i, 3, 20);
/* 273 */     if (i != this.g) {
/* 274 */       int j = i - this.g;
/* 275 */       Iterator<EntityPlayer> iterator = this.managedPlayers.iterator();
/*     */       
/* 277 */       while (iterator.hasNext()) {
/* 278 */         EntityPlayer entityplayer = iterator.next();
/* 279 */         int k = (int)entityplayer.locX >> 4;
/* 280 */         int l = (int)entityplayer.locZ >> 4;
/*     */ 
/*     */ 
/*     */         
/* 284 */         if (j > 0) {
/* 285 */           for (int m = k - i; m <= k + i; m++) {
/* 286 */             for (int j1 = l - i; j1 <= l + i; j1++) {
/* 287 */               PlayerChunk playerchunk = a(m, j1, true);
/*     */               
/* 289 */               if (!PlayerChunk.b(playerchunk).contains(entityplayer))
/* 290 */                 playerchunk.a(entityplayer); 
/*     */             } 
/*     */           } 
/*     */           continue;
/*     */         } 
/* 295 */         for (int i1 = k - this.g; i1 <= k + this.g; i1++) {
/* 296 */           for (int j1 = l - this.g; j1 <= l + this.g; j1++) {
/* 297 */             if (!a(i1, j1, k, l, i)) {
/* 298 */               a(i1, j1, true).b(entityplayer);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 305 */       this.g = i;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateViewDistance(EntityPlayer player, int viewDistance) {
/* 311 */     viewDistance = MathHelper.a(viewDistance, 3, 20);
/* 312 */     if (viewDistance != player.viewDistance) {
/* 313 */       int cx = (int)player.locX >> 4;
/* 314 */       int cz = (int)player.locZ >> 4;
/*     */       
/* 316 */       if (viewDistance - player.viewDistance > 0) {
/* 317 */         for (int x = cx - viewDistance; x <= cx + viewDistance; x++) {
/* 318 */           for (int z = cz - viewDistance; z <= cz + viewDistance; z++) {
/* 319 */             PlayerChunk playerChunk = a(x, z, true);
/* 320 */             if (!PlayerChunk.b(playerChunk).contains(player)) {
/* 321 */               playerChunk.a(player);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } else {
/* 326 */         for (int x = cx - player.viewDistance; x <= cx + player.viewDistance; x++) {
/* 327 */           for (int z = cz - player.viewDistance; z <= cz + player.viewDistance; z++) {
/* 328 */             if (!a(x, z, cx, cz, viewDistance)) {
/* 329 */               a(x, z, true).b(player);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 335 */       player.viewDistance = viewDistance;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getFurthestViewableBlock(int i) {
/* 341 */     return i * 16 - 16;
/*     */   }
/*     */   
/*     */   static Logger c() {
/* 345 */     return a;
/*     */   }
/*     */   
/*     */   static WorldServer a(PlayerChunkMap playerchunkmap) {
/* 349 */     return playerchunkmap.world;
/*     */   }
/*     */   
/*     */   static LongHashMap b(PlayerChunkMap playerchunkmap) {
/* 353 */     return playerchunkmap.d;
/*     */   }
/*     */   
/*     */   static Queue c(PlayerChunkMap playermanager) {
/* 357 */     return playermanager.f;
/*     */   }
/*     */   
/*     */   static Queue d(PlayerChunkMap playermanager) {
/* 361 */     return playermanager.e;
/*     */   }
/*     */   
/*     */   private static class ChunkCoordComparator
/*     */     implements Comparator<ChunkCoordIntPair> {
/*     */     private int x;
/*     */     private int z;
/*     */     
/*     */     public ChunkCoordComparator(EntityPlayer entityplayer) {
/* 370 */       this.x = (int)entityplayer.locX >> 4;
/* 371 */       this.z = (int)entityplayer.locZ >> 4;
/*     */     }
/*     */     
/*     */     public int compare(ChunkCoordIntPair a, ChunkCoordIntPair b) {
/* 375 */       if (a.equals(b)) {
/* 376 */         return 0;
/*     */       }
/*     */ 
/*     */       
/* 380 */       int ax = a.x - this.x;
/* 381 */       int az = a.z - this.z;
/* 382 */       int bx = b.x - this.x;
/* 383 */       int bz = b.z - this.z;
/*     */       
/* 385 */       int result = (ax - bx) * (ax + bx) + (az - bz) * (az + bz);
/* 386 */       if (result != 0) {
/* 387 */         return result;
/*     */       }
/*     */       
/* 390 */       if (ax < 0) {
/* 391 */         if (bx < 0) {
/* 392 */           return bz - az;
/*     */         }
/* 394 */         return -1;
/*     */       } 
/*     */       
/* 397 */       if (bx < 0) {
/* 398 */         return 1;
/*     */       }
/* 400 */       return az - bz;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PlayerChunkMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */