package org.bukkit.inventory;

import org.bukkit.entity.Entity;

public interface EntityEquipment {
  ItemStack getItemInHand();
  
  void setItemInHand(ItemStack paramItemStack);
  
  ItemStack getHelmet();
  
  void setHelmet(ItemStack paramItemStack);
  
  ItemStack getChestplate();
  
  void setChestplate(ItemStack paramItemStack);
  
  ItemStack getLeggings();
  
  void setLeggings(ItemStack paramItemStack);
  
  ItemStack getBoots();
  
  void setBoots(ItemStack paramItemStack);
  
  ItemStack[] getArmorContents();
  
  void setArmorContents(ItemStack[] paramArrayOfItemStack);
  
  void clear();
  
  float getItemInHandDropChance();
  
  void setItemInHandDropChance(float paramFloat);
  
  float getHelmetDropChance();
  
  void setHelmetDropChance(float paramFloat);
  
  float getChestplateDropChance();
  
  void setChestplateDropChance(float paramFloat);
  
  float getLeggingsDropChance();
  
  void setLeggingsDropChance(float paramFloat);
  
  float getBootsDropChance();
  
  void setBootsDropChance(float paramFloat);
  
  Entity getHolder();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\EntityEquipment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */