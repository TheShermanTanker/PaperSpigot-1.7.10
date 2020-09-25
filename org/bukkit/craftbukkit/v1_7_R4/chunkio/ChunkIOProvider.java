/*    */ package org.bukkit.craftbukkit.v1_7_R4.chunkio;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import net.minecraft.server.v1_7_R4.Chunk;
/*    */ import net.minecraft.server.v1_7_R4.ChunkRegionLoader;
/*    */ import net.minecraft.server.v1_7_R4.IChunkProvider;
/*    */ import net.minecraft.server.v1_7_R4.NBTTagCompound;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.util.AsynchronousExecutor;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.util.LongHash;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.world.ChunkLoadEvent;
/*    */ 
/*    */ class ChunkIOProvider implements AsynchronousExecutor.CallBackProvider<QueuedChunk, Chunk, Runnable, RuntimeException> {
/* 14 */   private final AtomicInteger threadNumber = new AtomicInteger(1);
/*    */ 
/*    */   
/*    */   public Chunk callStage1(QueuedChunk queuedChunk) throws RuntimeException {
/* 18 */     ChunkRegionLoader loader = queuedChunk.loader;
/* 19 */     Object[] data = loader.loadChunk(queuedChunk.world, queuedChunk.x, queuedChunk.z);
/*    */     
/* 21 */     if (data != null) {
/* 22 */       queuedChunk.compound = (NBTTagCompound)data[1];
/* 23 */       return (Chunk)data[0];
/*    */     } 
/*    */     
/* 26 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void callStage2(QueuedChunk queuedChunk, Chunk chunk) throws RuntimeException {
/* 31 */     if (chunk == null) {
/*    */       
/* 33 */       queuedChunk.provider.originalGetChunkAt(queuedChunk.x, queuedChunk.z);
/*    */       
/*    */       return;
/*    */     } 
/* 37 */     queuedChunk.loader.loadEntities(chunk, queuedChunk.compound.getCompound("Level"), queuedChunk.world);
/* 38 */     chunk.lastSaved = queuedChunk.provider.world.getTime();
/* 39 */     queuedChunk.provider.chunks.put(LongHash.toLong(queuedChunk.x, queuedChunk.z), chunk);
/* 40 */     chunk.addEntities();
/*    */     
/* 42 */     if (queuedChunk.provider.chunkProvider != null) {
/* 43 */       queuedChunk.provider.world.timings.syncChunkLoadStructuresTimer.startTiming();
/* 44 */       queuedChunk.provider.chunkProvider.recreateStructures(queuedChunk.x, queuedChunk.z);
/* 45 */       queuedChunk.provider.world.timings.syncChunkLoadStructuresTimer.stopTiming();
/*    */     } 
/*    */     
/* 48 */     CraftServer craftServer = queuedChunk.provider.world.getServer();
/* 49 */     if (craftServer != null) {
/* 50 */       craftServer.getPluginManager().callEvent((Event)new ChunkLoadEvent(chunk.bukkitChunk, false));
/*    */     }
/*    */ 
/*    */     
/* 54 */     for (int x = -2; x < 3; x++) {
/* 55 */       for (int z = -2; z < 3; z++) {
/* 56 */         if (x != 0 || z != 0) {
/*    */ 
/*    */ 
/*    */           
/* 60 */           Chunk neighbor = queuedChunk.provider.getChunkIfLoaded(chunk.locX + x, chunk.locZ + z);
/* 61 */           if (neighbor != null) {
/* 62 */             neighbor.setNeighborLoaded(-x, -z);
/* 63 */             chunk.setNeighborLoaded(x, z);
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 68 */     chunk.loadNearby((IChunkProvider)queuedChunk.provider, (IChunkProvider)queuedChunk.provider, queuedChunk.x, queuedChunk.z);
/*    */   }
/*    */   
/*    */   public void callStage3(QueuedChunk queuedChunk, Chunk chunk, Runnable runnable) throws RuntimeException {
/* 72 */     runnable.run();
/*    */   }
/*    */   
/*    */   public Thread newThread(Runnable runnable) {
/* 76 */     Thread thread = new Thread(runnable, "Chunk I/O Executor Thread-" + this.threadNumber.getAndIncrement());
/* 77 */     thread.setDaemon(true);
/* 78 */     return thread;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\chunkio\ChunkIOProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */