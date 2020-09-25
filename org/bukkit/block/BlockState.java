package org.bukkit.block;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.Metadatable;

public interface BlockState extends Metadatable {
  Block getBlock();
  
  MaterialData getData();
  
  Material getType();
  
  @Deprecated
  int getTypeId();
  
  byte getLightLevel();
  
  World getWorld();
  
  int getX();
  
  int getY();
  
  int getZ();
  
  Location getLocation();
  
  Location getLocation(Location paramLocation);
  
  Chunk getChunk();
  
  void setData(MaterialData paramMaterialData);
  
  void setType(Material paramMaterial);
  
  @Deprecated
  boolean setTypeId(int paramInt);
  
  boolean update();
  
  boolean update(boolean paramBoolean);
  
  boolean update(boolean paramBoolean1, boolean paramBoolean2);
  
  @Deprecated
  byte getRawData();
  
  @Deprecated
  void setRawData(byte paramByte);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\BlockState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */