package org.bukkit.inventory.meta;

import java.util.List;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public interface PotionMeta extends ItemMeta {
  boolean hasCustomEffects();
  
  List<PotionEffect> getCustomEffects();
  
  boolean addCustomEffect(PotionEffect paramPotionEffect, boolean paramBoolean);
  
  boolean removeCustomEffect(PotionEffectType paramPotionEffectType);
  
  boolean hasCustomEffect(PotionEffectType paramPotionEffectType);
  
  boolean setMainEffect(PotionEffectType paramPotionEffectType);
  
  boolean clearCustomEffects();
  
  PotionMeta clone();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\meta\PotionMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */