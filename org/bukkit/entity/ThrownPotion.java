package org.bukkit.entity;

import java.util.Collection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public interface ThrownPotion extends Projectile {
  Collection<PotionEffect> getEffects();
  
  ItemStack getItem();
  
  void setItem(ItemStack paramItemStack);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\ThrownPotion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */