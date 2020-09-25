package org.bukkit.entity;

public interface Tameable {
  boolean isTamed();
  
  void setTamed(boolean paramBoolean);
  
  AnimalTamer getOwner();
  
  void setOwner(AnimalTamer paramAnimalTamer);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Tameable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */