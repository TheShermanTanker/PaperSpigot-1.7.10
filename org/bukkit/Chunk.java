package org.bukkit;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

public interface Chunk {
  int getX();
  
  int getZ();
  
  World getWorld();
  
  Block getBlock(int paramInt1, int paramInt2, int paramInt3);
  
  ChunkSnapshot getChunkSnapshot();
  
  ChunkSnapshot getChunkSnapshot(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
  
  Entity[] getEntities();
  
  BlockState[] getTileEntities();
  
  boolean isLoaded();
  
  boolean load(boolean paramBoolean);
  
  boolean load();
  
  boolean unload(boolean paramBoolean1, boolean paramBoolean2);
  
  boolean unload(boolean paramBoolean);
  
  boolean unload();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Chunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */