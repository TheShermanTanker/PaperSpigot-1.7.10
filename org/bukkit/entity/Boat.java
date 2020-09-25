package org.bukkit.entity;

public interface Boat extends Vehicle {
  double getMaxSpeed();
  
  void setMaxSpeed(double paramDouble);
  
  double getOccupiedDeceleration();
  
  void setOccupiedDeceleration(double paramDouble);
  
  double getUnoccupiedDeceleration();
  
  void setUnoccupiedDeceleration(double paramDouble);
  
  boolean getWorkOnLand();
  
  void setWorkOnLand(boolean paramBoolean);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Boat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */