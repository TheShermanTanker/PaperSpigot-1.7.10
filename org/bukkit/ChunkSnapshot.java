package org.bukkit;

import org.bukkit.block.Biome;

public interface ChunkSnapshot {
  int getX();
  
  int getZ();
  
  String getWorldName();
  
  @Deprecated
  int getBlockTypeId(int paramInt1, int paramInt2, int paramInt3);
  
  @Deprecated
  int getBlockData(int paramInt1, int paramInt2, int paramInt3);
  
  int getBlockSkyLight(int paramInt1, int paramInt2, int paramInt3);
  
  int getBlockEmittedLight(int paramInt1, int paramInt2, int paramInt3);
  
  int getHighestBlockYAt(int paramInt1, int paramInt2);
  
  Biome getBiome(int paramInt1, int paramInt2);
  
  double getRawBiomeTemperature(int paramInt1, int paramInt2);
  
  double getRawBiomeRainfall(int paramInt1, int paramInt2);
  
  long getCaptureFullTime();
  
  boolean isSectionEmpty(int paramInt);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\ChunkSnapshot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */