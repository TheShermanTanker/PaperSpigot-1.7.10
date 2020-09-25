package org.bukkit.scoreboard;

import java.util.Set;
import org.bukkit.OfflinePlayer;

public interface Scoreboard {
  Objective registerNewObjective(String paramString1, String paramString2) throws IllegalArgumentException;
  
  Objective getObjective(String paramString) throws IllegalArgumentException;
  
  Set<Objective> getObjectivesByCriteria(String paramString) throws IllegalArgumentException;
  
  Set<Objective> getObjectives();
  
  Objective getObjective(DisplaySlot paramDisplaySlot) throws IllegalArgumentException;
  
  @Deprecated
  Set<Score> getScores(OfflinePlayer paramOfflinePlayer) throws IllegalArgumentException;
  
  Set<Score> getScores(String paramString) throws IllegalArgumentException;
  
  @Deprecated
  void resetScores(OfflinePlayer paramOfflinePlayer) throws IllegalArgumentException;
  
  void resetScores(String paramString) throws IllegalArgumentException;
  
  Team getPlayerTeam(OfflinePlayer paramOfflinePlayer) throws IllegalArgumentException;
  
  Team getTeam(String paramString) throws IllegalArgumentException;
  
  Set<Team> getTeams();
  
  Team registerNewTeam(String paramString) throws IllegalArgumentException;
  
  @Deprecated
  Set<OfflinePlayer> getPlayers();
  
  Set<String> getEntries();
  
  void clearSlot(DisplaySlot paramDisplaySlot) throws IllegalArgumentException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\scoreboard\Scoreboard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */