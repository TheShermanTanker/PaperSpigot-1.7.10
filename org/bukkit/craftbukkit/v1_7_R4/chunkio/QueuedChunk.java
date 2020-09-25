/*    */ package org.bukkit.craftbukkit.v1_7_R4.chunkio;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.ChunkProviderServer;
/*    */ import net.minecraft.server.v1_7_R4.ChunkRegionLoader;
/*    */ import net.minecraft.server.v1_7_R4.NBTTagCompound;
/*    */ import net.minecraft.server.v1_7_R4.World;
/*    */ 
/*    */ class QueuedChunk {
/*    */   final int x;
/*    */   final int z;
/*    */   final ChunkRegionLoader loader;
/*    */   final World world;
/*    */   final ChunkProviderServer provider;
/*    */   NBTTagCompound compound;
/*    */   
/*    */   public QueuedChunk(int x, int z, ChunkRegionLoader loader, World world, ChunkProviderServer provider) {
/* 17 */     this.x = x;
/* 18 */     this.z = z;
/* 19 */     this.loader = loader;
/* 20 */     this.world = world;
/* 21 */     this.provider = provider;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 26 */     return this.x * 31 + this.z * 29 ^ this.world.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object object) {
/* 31 */     if (object instanceof QueuedChunk) {
/* 32 */       QueuedChunk other = (QueuedChunk)object;
/* 33 */       return (this.x == other.x && this.z == other.z && this.world == other.world);
/*    */     } 
/*    */     
/* 36 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\chunkio\QueuedChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */