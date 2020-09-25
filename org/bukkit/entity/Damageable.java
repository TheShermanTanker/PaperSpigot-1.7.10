package org.bukkit.entity;

public interface Damageable extends Entity {
  void damage(double paramDouble);
  
  @Deprecated
  void damage(int paramInt);
  
  void damage(double paramDouble, Entity paramEntity);
  
  @Deprecated
  void damage(int paramInt, Entity paramEntity);
  
  double getHealth();
  
  @Deprecated
  int getHealth();
  
  void setHealth(double paramDouble);
  
  @Deprecated
  void setHealth(int paramInt);
  
  double getMaxHealth();
  
  @Deprecated
  int getMaxHealth();
  
  void setMaxHealth(double paramDouble);
  
  @Deprecated
  void setMaxHealth(int paramInt);
  
  void resetMaxHealth();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Damageable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */