package org.bukkit;

public interface TravelAgent {
  TravelAgent setSearchRadius(int paramInt);
  
  int getSearchRadius();
  
  TravelAgent setCreationRadius(int paramInt);
  
  int getCreationRadius();
  
  boolean getCanCreatePortal();
  
  void setCanCreatePortal(boolean paramBoolean);
  
  Location findOrCreate(Location paramLocation);
  
  Location findPortal(Location paramLocation);
  
  boolean createPortal(Location paramLocation);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\TravelAgent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */