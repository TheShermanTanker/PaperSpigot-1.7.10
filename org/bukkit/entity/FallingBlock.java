package org.bukkit.entity;

import org.bukkit.Location;
import org.bukkit.Material;

public interface FallingBlock extends Entity {
  Material getMaterial();
  
  @Deprecated
  int getBlockId();
  
  @Deprecated
  byte getBlockData();
  
  boolean getDropItem();
  
  void setDropItem(boolean paramBoolean);
  
  Location getSourceLoc();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\FallingBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */