package org.bukkit.inventory;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public interface ItemFactory {
  ItemMeta getItemMeta(Material paramMaterial);
  
  boolean isApplicable(ItemMeta paramItemMeta, ItemStack paramItemStack) throws IllegalArgumentException;
  
  boolean isApplicable(ItemMeta paramItemMeta, Material paramMaterial) throws IllegalArgumentException;
  
  boolean equals(ItemMeta paramItemMeta1, ItemMeta paramItemMeta2) throws IllegalArgumentException;
  
  ItemMeta asMetaFor(ItemMeta paramItemMeta, ItemStack paramItemStack) throws IllegalArgumentException;
  
  ItemMeta asMetaFor(ItemMeta paramItemMeta, Material paramMaterial) throws IllegalArgumentException;
  
  Color getDefaultLeatherColor();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\ItemFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */