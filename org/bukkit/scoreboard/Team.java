package org.bukkit.scoreboard;

import java.util.Set;
import org.bukkit.OfflinePlayer;

public interface Team {
  String getName() throws IllegalStateException;
  
  String getDisplayName() throws IllegalStateException;
  
  void setDisplayName(String paramString) throws IllegalStateException, IllegalArgumentException;
  
  String getPrefix() throws IllegalStateException;
  
  void setPrefix(String paramString) throws IllegalStateException, IllegalArgumentException;
  
  String getSuffix() throws IllegalStateException;
  
  void setSuffix(String paramString) throws IllegalStateException, IllegalArgumentException;
  
  boolean allowFriendlyFire() throws IllegalStateException;
  
  void setAllowFriendlyFire(boolean paramBoolean) throws IllegalStateException;
  
  boolean canSeeFriendlyInvisibles() throws IllegalStateException;
  
  void setCanSeeFriendlyInvisibles(boolean paramBoolean) throws IllegalStateException;
  
  Set<OfflinePlayer> getPlayers() throws IllegalStateException;
  
  Set<String> getEntries() throws IllegalStateException;
  
  int getSize() throws IllegalStateException;
  
  Scoreboard getScoreboard();
  
  void addPlayer(OfflinePlayer paramOfflinePlayer) throws IllegalStateException, IllegalArgumentException;
  
  void addEntry(String paramString) throws IllegalStateException, IllegalArgumentException;
  
  boolean removePlayer(OfflinePlayer paramOfflinePlayer) throws IllegalStateException, IllegalArgumentException;
  
  boolean removeEntry(String paramString) throws IllegalStateException, IllegalArgumentException;
  
  void unregister() throws IllegalStateException;
  
  boolean hasPlayer(OfflinePlayer paramOfflinePlayer) throws IllegalArgumentException, IllegalStateException;
  
  boolean hasEntry(String paramString) throws IllegalArgumentException, IllegalStateException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\scoreboard\Team.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */