/*    */ package org.bukkit.craftbukkit.v1_7_R4.chunkio;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.Chunk;
/*    */ import net.minecraft.server.v1_7_R4.ChunkProviderServer;
/*    */ import net.minecraft.server.v1_7_R4.ChunkRegionLoader;
/*    */ import net.minecraft.server.v1_7_R4.World;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.util.AsynchronousExecutor;
/*    */ 
/*    */ public class ChunkIOExecutor
/*    */ {
/*    */   static final int BASE_THREADS = 1;
/*    */   static final int PLAYERS_PER_THREAD = 50;
/* 13 */   private static final AsynchronousExecutor<QueuedChunk, Chunk, Runnable, RuntimeException> instance = new AsynchronousExecutor(new ChunkIOProvider(), 1);
/*    */   
/*    */   public static Chunk syncChunkLoad(World world, ChunkRegionLoader loader, ChunkProviderServer provider, int x, int z) {
/* 16 */     return (Chunk)instance.getSkipQueue(new QueuedChunk(x, z, loader, world, provider));
/*    */   }
/*    */   
/*    */   public static void queueChunkLoad(World world, ChunkRegionLoader loader, ChunkProviderServer provider, int x, int z, Runnable runnable) {
/* 20 */     instance.add(new QueuedChunk(x, z, loader, world, provider), runnable);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void dropQueuedChunkLoad(World world, int x, int z, Runnable runnable) {
/* 25 */     instance.drop(new QueuedChunk(x, z, null, world, null), runnable);
/*    */   }
/*    */   
/*    */   public static void adjustPoolSize(int players) {
/* 29 */     int size = Math.max(1, (int)Math.ceil((players / 50)));
/* 30 */     instance.setActiveThreads(size);
/*    */   }
/*    */   
/*    */   public static void tick() {
/* 34 */     instance.finishActive();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\chunkio\ChunkIOExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */