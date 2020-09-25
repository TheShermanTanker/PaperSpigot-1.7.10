/*     */ package org.bukkit.craftbukkit.v1_7_R4.scoreboard;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.server.v1_7_R4.ScoreboardTeam;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.scoreboard.Scoreboard;
/*     */ import org.bukkit.scoreboard.Team;
/*     */ 
/*     */ final class CraftTeam
/*     */   extends CraftScoreboardComponent
/*     */   implements Team {
/*     */   private final ScoreboardTeam team;
/*     */   
/*     */   CraftTeam(CraftScoreboard scoreboard, ScoreboardTeam team) {
/*  18 */     super(scoreboard);
/*  19 */     this.team = team;
/*  20 */     scoreboard.teams.put(team.getName(), this);
/*     */   }
/*     */   
/*     */   public String getName() throws IllegalStateException {
/*  24 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  26 */     return this.team.getName();
/*     */   }
/*     */   
/*     */   public String getDisplayName() throws IllegalStateException {
/*  30 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  32 */     return this.team.getDisplayName();
/*     */   }
/*     */   
/*     */   public void setDisplayName(String displayName) throws IllegalStateException {
/*  36 */     Validate.notNull(displayName, "Display name cannot be null");
/*  37 */     Validate.isTrue((displayName.length() <= 32), "Display name '" + displayName + "' is longer than the limit of 32 characters");
/*  38 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  40 */     this.team.setDisplayName(displayName);
/*     */   }
/*     */   
/*     */   public String getPrefix() throws IllegalStateException {
/*  44 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  46 */     return this.team.getPrefix();
/*     */   }
/*     */   
/*     */   public void setPrefix(String prefix) throws IllegalStateException, IllegalArgumentException {
/*  50 */     Validate.notNull(prefix, "Prefix cannot be null");
/*  51 */     Validate.isTrue((prefix.length() <= 32), "Prefix '" + prefix + "' is longer than the limit of 32 characters");
/*  52 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  54 */     this.team.setPrefix(prefix);
/*     */   }
/*     */   
/*     */   public String getSuffix() throws IllegalStateException {
/*  58 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  60 */     return this.team.getSuffix();
/*     */   }
/*     */   
/*     */   public void setSuffix(String suffix) throws IllegalStateException, IllegalArgumentException {
/*  64 */     Validate.notNull(suffix, "Suffix cannot be null");
/*  65 */     Validate.isTrue((suffix.length() <= 32), "Suffix '" + suffix + "' is longer than the limit of 32 characters");
/*  66 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  68 */     this.team.setSuffix(suffix);
/*     */   }
/*     */   
/*     */   public boolean allowFriendlyFire() throws IllegalStateException {
/*  72 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  74 */     return this.team.allowFriendlyFire();
/*     */   }
/*     */   
/*     */   public void setAllowFriendlyFire(boolean enabled) throws IllegalStateException {
/*  78 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  80 */     this.team.setAllowFriendlyFire(enabled);
/*     */   }
/*     */   
/*     */   public boolean canSeeFriendlyInvisibles() throws IllegalStateException {
/*  84 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  86 */     return this.team.canSeeFriendlyInvisibles();
/*     */   }
/*     */   
/*     */   public void setCanSeeFriendlyInvisibles(boolean enabled) throws IllegalStateException {
/*  90 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  92 */     this.team.setCanSeeFriendlyInvisibles(enabled);
/*     */   }
/*     */   
/*     */   public Set<OfflinePlayer> getPlayers() throws IllegalStateException {
/*  96 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  98 */     ImmutableSet.Builder<OfflinePlayer> players = ImmutableSet.builder();
/*  99 */     for (Object o : this.team.getPlayerNameSet()) {
/* 100 */       players.add(Bukkit.getOfflinePlayer(o.toString()));
/*     */     }
/* 102 */     return (Set<OfflinePlayer>)players.build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getEntries() throws IllegalStateException {
/* 108 */     CraftScoreboard scoreboard = checkState();
/*     */     
/* 110 */     ImmutableSet.Builder<String> entries = ImmutableSet.builder();
/* 111 */     for (Object o : this.team.getPlayerNameSet()) {
/* 112 */       entries.add(o.toString());
/*     */     }
/* 114 */     return (Set<String>)entries.build();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() throws IllegalStateException {
/* 119 */     CraftScoreboard scoreboard = checkState();
/*     */     
/* 121 */     return this.team.getPlayerNameSet().size();
/*     */   }
/*     */   
/*     */   public void addPlayer(OfflinePlayer player) throws IllegalStateException, IllegalArgumentException {
/* 125 */     Validate.notNull(player, "OfflinePlayer cannot be null");
/*     */     
/* 127 */     addEntry(player.getName());
/*     */   }
/*     */   
/*     */   public void addEntry(String entry) throws IllegalStateException, IllegalArgumentException {
/* 131 */     Validate.notNull(entry, "Entry cannot be null");
/* 132 */     CraftScoreboard scoreboard = checkState();
/*     */     
/* 134 */     scoreboard.board.addPlayerToTeam(entry, this.team.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removePlayer(OfflinePlayer player) throws IllegalStateException, IllegalArgumentException {
/* 139 */     Validate.notNull(player, "OfflinePlayer cannot be null");
/*     */     
/* 141 */     return removeEntry(player.getName());
/*     */   }
/*     */   
/*     */   public boolean removeEntry(String entry) throws IllegalStateException, IllegalArgumentException {
/* 145 */     Validate.notNull(entry, "Entry cannot be null");
/* 146 */     CraftScoreboard scoreboard = checkState();
/*     */     
/* 148 */     if (!this.team.getPlayerNameSet().contains(entry)) {
/* 149 */       return false;
/*     */     }
/*     */     
/* 152 */     scoreboard.board.removePlayerFromTeam(entry, this.team);
/*     */     
/* 154 */     return true;
/*     */   }
/*     */   
/*     */   public boolean hasPlayer(OfflinePlayer player) throws IllegalArgumentException, IllegalStateException {
/* 158 */     Validate.notNull(player, "OfflinePlayer cannot be null");
/*     */     
/* 160 */     return hasEntry(player.getName());
/*     */   }
/*     */   
/*     */   public boolean hasEntry(String entry) throws IllegalArgumentException, IllegalStateException {
/* 164 */     Validate.notNull("Entry cannot be null");
/*     */     
/* 166 */     CraftScoreboard scoreboard = checkState();
/*     */     
/* 168 */     return this.team.getPlayerNameSet().contains(entry);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregister() throws IllegalStateException {
/* 174 */     CraftScoreboard scoreboard = checkState();
/*     */     
/* 176 */     scoreboard.board.removeTeam(this.team);
/* 177 */     scoreboard.teams.remove(this.team.getName());
/* 178 */     setUnregistered();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\scoreboard\CraftTeam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */