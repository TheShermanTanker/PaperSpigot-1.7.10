package org.bukkit.block;

import org.bukkit.inventory.BrewerInventory;

public interface BrewingStand extends BlockState, ContainerBlock {
  int getBrewingTime();
  
  void setBrewingTime(int paramInt);
  
  BrewerInventory getInventory();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\BrewingStand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */