package org.bukkit.inventory.meta;

import org.bukkit.FireworkEffect;

public interface FireworkEffectMeta extends ItemMeta {
  void setEffect(FireworkEffect paramFireworkEffect);
  
  boolean hasEffect();
  
  FireworkEffect getEffect();
  
  FireworkEffectMeta clone();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\meta\FireworkEffectMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */