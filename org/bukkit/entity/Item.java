package org.bukkit.entity;

import org.bukkit.inventory.ItemStack;

public interface Item extends Entity {
  ItemStack getItemStack();
  
  void setItemStack(ItemStack paramItemStack);
  
  int getPickupDelay();
  
  void setPickupDelay(int paramInt);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Item.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */