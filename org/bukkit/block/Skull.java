package org.bukkit.block;

import org.bukkit.SkullType;

public interface Skull extends BlockState {
  boolean hasOwner();
  
  String getOwner();
  
  boolean setOwner(String paramString);
  
  BlockFace getRotation();
  
  void setRotation(BlockFace paramBlockFace);
  
  SkullType getSkullType();
  
  void setSkullType(SkullType paramSkullType);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\Skull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */