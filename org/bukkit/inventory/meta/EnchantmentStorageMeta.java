package org.bukkit.inventory.meta;

import java.util.Map;
import org.bukkit.enchantments.Enchantment;

public interface EnchantmentStorageMeta extends ItemMeta {
  boolean hasStoredEnchants();
  
  boolean hasStoredEnchant(Enchantment paramEnchantment);
  
  int getStoredEnchantLevel(Enchantment paramEnchantment);
  
  Map<Enchantment, Integer> getStoredEnchants();
  
  boolean addStoredEnchant(Enchantment paramEnchantment, int paramInt, boolean paramBoolean);
  
  boolean removeStoredEnchant(Enchantment paramEnchantment) throws IllegalArgumentException;
  
  boolean hasConflictingStoredEnchant(Enchantment paramEnchantment);
  
  EnchantmentStorageMeta clone();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\meta\EnchantmentStorageMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */