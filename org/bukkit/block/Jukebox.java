package org.bukkit.block;

import org.bukkit.Material;

public interface Jukebox extends BlockState {
  Material getPlaying();
  
  void setPlaying(Material paramMaterial);
  
  boolean isPlaying();
  
  boolean eject();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\Jukebox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */