package org.bukkit.entity;

public interface Ageable extends Creature {
  int getAge();
  
  void setAge(int paramInt);
  
  void setAgeLock(boolean paramBoolean);
  
  boolean getAgeLock();
  
  void setBaby();
  
  void setAdult();
  
  boolean isAdult();
  
  boolean canBreed();
  
  void setBreed(boolean paramBoolean);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Ageable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */