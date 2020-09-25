/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.chunkio.ChunkIOExecutor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PlayerChunk
/*     */ {
/*     */   private final List b;
/*     */   private final ChunkCoordIntPair location;
/*     */   private short[] dirtyBlocks;
/*     */   private int dirtyCount;
/*     */   private int f;
/*     */   private long g;
/*     */   final PlayerChunkMap playerChunkMap;
/*  21 */   private final HashMap<EntityPlayer, Runnable> players = new HashMap<EntityPlayer, Runnable>();
/*     */   
/*  23 */   private Runnable loadedRunnable = new Runnable() {
/*     */       public void run() {
/*  25 */         PlayerChunk.this.loaded = true;
/*     */       }
/*     */     };
/*     */   private boolean loaded = false;
/*     */   
/*     */   public PlayerChunk(PlayerChunkMap playerchunkmap, int i, int j) {
/*  31 */     this.playerChunkMap = playerchunkmap;
/*  32 */     this.b = new ArrayList();
/*  33 */     this.dirtyBlocks = new short[64];
/*  34 */     this.location = new ChunkCoordIntPair(i, j);
/*  35 */     (playerchunkmap.a()).chunkProviderServer.getChunkAt(i, j, this.loadedRunnable);
/*     */   }
/*     */   
/*     */   public void a(final EntityPlayer entityplayer) {
/*  39 */     if (this.b.contains(entityplayer)) {
/*  40 */       PlayerChunkMap.c().debug("Failed to add player. {} already is in chunk {}, {}", new Object[] { entityplayer, Integer.valueOf(this.location.x), Integer.valueOf(this.location.z) });
/*     */     } else {
/*  42 */       Runnable playerRunnable; if (this.b.isEmpty()) {
/*  43 */         this.g = PlayerChunkMap.a(this.playerChunkMap).getTime();
/*     */       }
/*     */       
/*  46 */       this.b.add(entityplayer);
/*     */ 
/*     */       
/*  49 */       if (this.loaded) {
/*  50 */         playerRunnable = null;
/*  51 */         entityplayer.chunkCoordIntPairQueue.add(this.location);
/*     */       } else {
/*  53 */         playerRunnable = new Runnable() {
/*     */             public void run() {
/*  55 */               entityplayer.chunkCoordIntPairQueue.add(PlayerChunk.this.location);
/*     */             }
/*     */           };
/*  58 */         (this.playerChunkMap.a()).chunkProviderServer.getChunkAt(this.location.x, this.location.z, playerRunnable);
/*     */       } 
/*     */       
/*  61 */       this.players.put(entityplayer, playerRunnable);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(EntityPlayer entityplayer) {
/*  67 */     if (this.b.contains(entityplayer)) {
/*     */       
/*  69 */       if (!this.loaded) {
/*  70 */         ChunkIOExecutor.dropQueuedChunkLoad(this.playerChunkMap.a(), this.location.x, this.location.z, this.players.get(entityplayer));
/*  71 */         this.b.remove(entityplayer);
/*  72 */         this.players.remove(entityplayer);
/*     */         
/*  74 */         if (this.b.isEmpty()) {
/*  75 */           ChunkIOExecutor.dropQueuedChunkLoad(this.playerChunkMap.a(), this.location.x, this.location.z, this.loadedRunnable);
/*  76 */           long i = this.location.x + 2147483647L | this.location.z + 2147483647L << 32L;
/*  77 */           PlayerChunkMap.b(this.playerChunkMap).remove(i);
/*  78 */           PlayerChunkMap.c(this.playerChunkMap).remove(this);
/*     */         } 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  85 */       Chunk chunk = PlayerChunkMap.a(this.playerChunkMap).getChunkAt(this.location.x, this.location.z);
/*     */       
/*  87 */       if (chunk.isReady()) {
/*  88 */         entityplayer.playerConnection.sendPacket(new PacketPlayOutMapChunk(chunk, true, 0, entityplayer.playerConnection.networkManager.getVersion()));
/*     */       }
/*     */       
/*  91 */       this.players.remove(entityplayer);
/*  92 */       this.b.remove(entityplayer);
/*  93 */       entityplayer.chunkCoordIntPairQueue.remove(this.location);
/*  94 */       if (this.b.isEmpty()) {
/*  95 */         long i = this.location.x + 2147483647L | this.location.z + 2147483647L << 32L;
/*     */         
/*  97 */         a(chunk);
/*  98 */         PlayerChunkMap.b(this.playerChunkMap).remove(i);
/*  99 */         PlayerChunkMap.c(this.playerChunkMap).remove(this);
/* 100 */         if (this.dirtyCount > 0) {
/* 101 */           PlayerChunkMap.d(this.playerChunkMap).remove(this);
/*     */         }
/*     */         
/* 104 */         (this.playerChunkMap.a()).chunkProviderServer.queueUnload(this.location.x, this.location.z);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a() {
/* 110 */     a(PlayerChunkMap.a(this.playerChunkMap).getChunkAt(this.location.x, this.location.z));
/*     */   }
/*     */   
/*     */   private void a(Chunk chunk) {
/* 114 */     chunk.s += PlayerChunkMap.a(this.playerChunkMap).getTime() - this.g;
/* 115 */     this.g = PlayerChunkMap.a(this.playerChunkMap).getTime();
/*     */   }
/*     */   
/*     */   public void a(int i, int j, int k) {
/* 119 */     if (this.dirtyCount == 0) {
/* 120 */       PlayerChunkMap.d(this.playerChunkMap).add(this);
/*     */     }
/*     */     
/* 123 */     this.f |= 1 << j >> 4;
/* 124 */     if (this.dirtyCount < 64) {
/* 125 */       short short1 = (short)(i << 12 | k << 8 | j);
/*     */       
/* 127 */       for (int l = 0; l < this.dirtyCount; l++) {
/* 128 */         if (this.dirtyBlocks[l] == short1) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/* 133 */       this.dirtyBlocks[this.dirtyCount++] = short1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendAll(Packet packet) {
/* 138 */     for (int i = 0; i < this.b.size(); i++) {
/* 139 */       EntityPlayer entityplayer = this.b.get(i);
/*     */       
/* 141 */       if (!entityplayer.chunkCoordIntPairQueue.contains(this.location)) {
/* 142 */         entityplayer.playerConnection.sendPacket(packet);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b() {
/* 148 */     if (this.dirtyCount != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 153 */       if (this.dirtyCount == 1) {
/* 154 */         int i = this.location.x * 16 + (this.dirtyBlocks[0] >> 12 & 0xF);
/* 155 */         int j = this.dirtyBlocks[0] & 0xFF;
/* 156 */         int k = this.location.z * 16 + (this.dirtyBlocks[0] >> 8 & 0xF);
/* 157 */         sendAll(new PacketPlayOutBlockChange(i, j, k, PlayerChunkMap.a(this.playerChunkMap)));
/* 158 */         if (PlayerChunkMap.a(this.playerChunkMap).getType(i, j, k).isTileEntity()) {
/* 159 */           sendTileEntity(PlayerChunkMap.a(this.playerChunkMap).getTileEntity(i, j, k));
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 164 */       else if (this.dirtyCount == 64) {
/* 165 */         int i = this.location.x * 16;
/* 166 */         int j = this.location.z * 16;
/*     */ 
/*     */ 
/*     */         
/* 170 */         Chunk chunk = PlayerChunkMap.a(this.playerChunkMap).getChunkAt(this.location.x, this.location.z);
/* 171 */         for (int idx = 0; idx < this.b.size(); idx++) {
/* 172 */           EntityPlayer entityplayer = this.b.get(idx);
/*     */           
/* 174 */           if (!entityplayer.chunkCoordIntPairQueue.contains(this.location)) {
/* 175 */             entityplayer.playerConnection.sendPacket(new PacketPlayOutMapChunk(chunk, (this.f == 65535), this.f, entityplayer.playerConnection.networkManager.getVersion()));
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 182 */         for (int k = 0; k < 16; k++) {
/* 183 */           if ((this.f & 1 << k) != 0) {
/* 184 */             int l = k << 4;
/* 185 */             List<TileEntity> list = PlayerChunkMap.a(this.playerChunkMap).getTileEntities(i, l, j, i + 16, l + 16, j + 16);
/*     */             
/* 187 */             for (int i1 = 0; i1 < list.size(); i1++) {
/* 188 */               sendTileEntity(list.get(i1));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } else {
/* 193 */         sendAll(new PacketPlayOutMultiBlockChange(this.dirtyCount, this.dirtyBlocks, PlayerChunkMap.a(this.playerChunkMap).getChunkAt(this.location.x, this.location.z)));
/*     */         
/* 195 */         for (int i = 0; i < this.dirtyCount; i++) {
/* 196 */           int j = this.location.x * 16 + (this.dirtyBlocks[i] >> 12 & 0xF);
/* 197 */           int k = this.dirtyBlocks[i] & 0xFF;
/* 198 */           int l = this.location.z * 16 + (this.dirtyBlocks[i] >> 8 & 0xF);
/* 199 */           if (PlayerChunkMap.a(this.playerChunkMap).getType(j, k, l).isTileEntity()) {
/* 200 */             sendTileEntity(PlayerChunkMap.a(this.playerChunkMap).getTileEntity(j, k, l));
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 206 */       this.dirtyCount = 0;
/* 207 */       this.f = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendTileEntity(TileEntity tileentity) {
/* 212 */     if (tileentity != null) {
/* 213 */       Packet packet = tileentity.getUpdatePacket();
/*     */       
/* 215 */       if (packet != null) {
/* 216 */         sendAll(packet);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   static ChunkCoordIntPair a(PlayerChunk playerchunk) {
/* 222 */     return playerchunk.location;
/*     */   }
/*     */   
/*     */   static List b(PlayerChunk playerchunk) {
/* 226 */     return playerchunk.b;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PlayerChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */