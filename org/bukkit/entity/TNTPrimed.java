package org.bukkit.entity;

import org.bukkit.Location;

public interface TNTPrimed extends Explosive {
  void setFuseTicks(int paramInt);
  
  int getFuseTicks();
  
  Entity getSource();
  
  Location getSourceLoc();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\TNTPrimed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */