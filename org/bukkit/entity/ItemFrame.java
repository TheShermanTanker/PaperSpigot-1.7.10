package org.bukkit.entity;

import org.bukkit.Rotation;
import org.bukkit.inventory.ItemStack;

public interface ItemFrame extends Hanging {
  ItemStack getItem();
  
  void setItem(ItemStack paramItemStack);
  
  Rotation getRotation();
  
  void setRotation(Rotation paramRotation) throws IllegalArgumentException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\ItemFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */