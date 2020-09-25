package org.bukkit.entity;

import org.bukkit.DyeColor;

public interface Wolf extends Animals, Tameable {
  boolean isAngry();
  
  void setAngry(boolean paramBoolean);
  
  boolean isSitting();
  
  void setSitting(boolean paramBoolean);
  
  DyeColor getCollarColor();
  
  void setCollarColor(DyeColor paramDyeColor);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Wolf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */