package org.bukkit.inventory;

import org.bukkit.block.BrewingStand;

public interface BrewerInventory extends Inventory {
  ItemStack getIngredient();
  
  void setIngredient(ItemStack paramItemStack);
  
  BrewingStand getHolder();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\BrewerInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */