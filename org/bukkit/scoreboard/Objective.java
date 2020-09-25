package org.bukkit.scoreboard;

import org.bukkit.OfflinePlayer;

public interface Objective {
  String getName() throws IllegalStateException;
  
  String getDisplayName() throws IllegalStateException;
  
  void setDisplayName(String paramString) throws IllegalStateException, IllegalArgumentException;
  
  String getCriteria() throws IllegalStateException;
  
  boolean isModifiable() throws IllegalStateException;
  
  Scoreboard getScoreboard();
  
  void unregister() throws IllegalStateException;
  
  void setDisplaySlot(DisplaySlot paramDisplaySlot) throws IllegalStateException;
  
  DisplaySlot getDisplaySlot() throws IllegalStateException;
  
  @Deprecated
  Score getScore(OfflinePlayer paramOfflinePlayer) throws IllegalArgumentException, IllegalStateException;
  
  Score getScore(String paramString) throws IllegalArgumentException, IllegalStateException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\scoreboard\Objective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */