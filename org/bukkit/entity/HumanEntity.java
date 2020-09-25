package org.bukkit.entity;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permissible;

public interface HumanEntity extends LivingEntity, AnimalTamer, Permissible, InventoryHolder {
  String getName();
  
  PlayerInventory getInventory();
  
  Inventory getEnderChest();
  
  boolean setWindowProperty(InventoryView.Property paramProperty, int paramInt);
  
  InventoryView getOpenInventory();
  
  InventoryView openInventory(Inventory paramInventory);
  
  InventoryView openWorkbench(Location paramLocation, boolean paramBoolean);
  
  InventoryView openEnchanting(Location paramLocation, boolean paramBoolean);
  
  void openInventory(InventoryView paramInventoryView);
  
  void closeInventory();
  
  ItemStack getItemInHand();
  
  void setItemInHand(ItemStack paramItemStack);
  
  ItemStack getItemOnCursor();
  
  void setItemOnCursor(ItemStack paramItemStack);
  
  boolean isSleeping();
  
  int getSleepTicks();
  
  GameMode getGameMode();
  
  void setGameMode(GameMode paramGameMode);
  
  boolean isBlocking();
  
  int getExpToLevel();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\HumanEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */