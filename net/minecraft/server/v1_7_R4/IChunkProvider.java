package net.minecraft.server.v1_7_R4;

import java.util.List;

public interface IChunkProvider {
  boolean isChunkLoaded(int paramInt1, int paramInt2);
  
  Chunk getOrCreateChunk(int paramInt1, int paramInt2);
  
  Chunk getChunkAt(int paramInt1, int paramInt2);
  
  void getChunkAt(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2);
  
  boolean saveChunks(boolean paramBoolean, IProgressUpdate paramIProgressUpdate);
  
  boolean unloadChunks();
  
  boolean canSave();
  
  String getName();
  
  List getMobsFor(EnumCreatureType paramEnumCreatureType, int paramInt1, int paramInt2, int paramInt3);
  
  ChunkPosition findNearestMapFeature(World paramWorld, String paramString, int paramInt1, int paramInt2, int paramInt3);
  
  int getLoadedChunks();
  
  void recreateStructures(int paramInt1, int paramInt2);
  
  void c();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\IChunkProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */