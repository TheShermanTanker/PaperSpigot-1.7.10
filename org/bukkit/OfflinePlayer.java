package org.bukkit;

import java.util.UUID;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.ServerOperator;

public interface OfflinePlayer extends ServerOperator, AnimalTamer, ConfigurationSerializable {
  boolean isOnline();
  
  String getName();
  
  UUID getUniqueId();
  
  boolean isBanned();
  
  @Deprecated
  void setBanned(boolean paramBoolean);
  
  boolean isWhitelisted();
  
  void setWhitelisted(boolean paramBoolean);
  
  Player getPlayer();
  
  long getFirstPlayed();
  
  long getLastPlayed();
  
  boolean hasPlayedBefore();
  
  Location getBedSpawnLocation();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\OfflinePlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */