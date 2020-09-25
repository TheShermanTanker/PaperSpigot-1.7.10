package org.bukkit.block;

import org.bukkit.inventory.FurnaceInventory;

public interface Furnace extends BlockState, ContainerBlock {
  short getBurnTime();
  
  void setBurnTime(short paramShort);
  
  short getCookTime();
  
  void setCookTime(short paramShort);
  
  FurnaceInventory getInventory();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\Furnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */