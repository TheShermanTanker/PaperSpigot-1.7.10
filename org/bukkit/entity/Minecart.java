package org.bukkit.entity;

import org.bukkit.util.Vector;

public interface Minecart extends Vehicle {
  @Deprecated
  void setDamage(int paramInt);
  
  void setDamage(double paramDouble);
  
  @Deprecated
  int getDamage();
  
  double getDamage();
  
  double getMaxSpeed();
  
  void setMaxSpeed(double paramDouble);
  
  boolean isSlowWhenEmpty();
  
  void setSlowWhenEmpty(boolean paramBoolean);
  
  Vector getFlyingVelocityMod();
  
  void setFlyingVelocityMod(Vector paramVector);
  
  Vector getDerailedVelocityMod();
  
  void setDerailedVelocityMod(Vector paramVector);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Minecart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */