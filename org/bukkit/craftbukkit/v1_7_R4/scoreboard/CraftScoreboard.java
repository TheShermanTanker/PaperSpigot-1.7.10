/*     */ package org.bukkit.craftbukkit.v1_7_R4.scoreboard;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.server.v1_7_R4.Scoreboard;
/*     */ import net.minecraft.server.v1_7_R4.ScoreboardObjective;
/*     */ import net.minecraft.server.v1_7_R4.ScoreboardTeam;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.scoreboard.DisplaySlot;
/*     */ import org.bukkit.scoreboard.Objective;
/*     */ import org.bukkit.scoreboard.Score;
/*     */ import org.bukkit.scoreboard.Scoreboard;
/*     */ import org.bukkit.scoreboard.Team;
/*     */ 
/*     */ public final class CraftScoreboard
/*     */   implements Scoreboard {
/*     */   final Scoreboard board;
/*  22 */   final Map<String, CraftObjective> objectives = new HashMap<String, CraftObjective>();
/*  23 */   final Map<String, CraftTeam> teams = new HashMap<String, CraftTeam>();
/*     */   
/*     */   CraftScoreboard(Scoreboard board) {
/*  26 */     this.board = board;
/*     */     
/*  28 */     for (ScoreboardObjective objective : board.getObjectives()) {
/*  29 */       new CraftObjective(this, objective);
/*     */     }
/*  31 */     for (ScoreboardTeam team : board.getTeams()) {
/*  32 */       new CraftTeam(this, team);
/*     */     }
/*     */   }
/*     */   
/*     */   public CraftObjective registerNewObjective(String name, String criteria) throws IllegalArgumentException {
/*  37 */     Validate.notNull(name, "Objective name cannot be null");
/*  38 */     Validate.notNull(criteria, "Criteria cannot be null");
/*  39 */     Validate.isTrue((name.length() <= 16), "The name '" + name + "' is longer than the limit of 16 characters");
/*  40 */     Validate.isTrue((this.board.getObjective(name) == null), "An objective of name '" + name + "' already exists");
/*     */     
/*  42 */     CraftCriteria craftCriteria = CraftCriteria.getFromBukkit(criteria);
/*  43 */     ScoreboardObjective objective = this.board.registerObjective(name, craftCriteria.criteria);
/*  44 */     return new CraftObjective(this, objective);
/*     */   }
/*     */   
/*     */   public Objective getObjective(String name) throws IllegalArgumentException {
/*  48 */     Validate.notNull(name, "Name cannot be null");
/*  49 */     return this.objectives.get(name);
/*     */   }
/*     */   
/*     */   public ImmutableSet<Objective> getObjectivesByCriteria(String criteria) throws IllegalArgumentException {
/*  53 */     Validate.notNull(criteria, "Criteria cannot be null");
/*     */     
/*  55 */     ImmutableSet.Builder<Objective> objectives = ImmutableSet.builder();
/*  56 */     for (CraftObjective objective : this.objectives.values()) {
/*  57 */       if (objective.getCriteria().equals(criteria)) {
/*  58 */         objectives.add(objective);
/*     */       }
/*     */     } 
/*  61 */     return objectives.build();
/*     */   }
/*     */   
/*     */   public ImmutableSet<Objective> getObjectives() {
/*  65 */     return ImmutableSet.copyOf(this.objectives.values());
/*     */   }
/*     */   
/*     */   public Objective getObjective(DisplaySlot slot) throws IllegalArgumentException {
/*  69 */     Validate.notNull(slot, "Display slot cannot be null");
/*  70 */     ScoreboardObjective objective = this.board.getObjectiveForSlot(CraftScoreboardTranslations.fromBukkitSlot(slot));
/*  71 */     if (objective == null) {
/*  72 */       return null;
/*     */     }
/*  74 */     return this.objectives.get(objective.getName());
/*     */   }
/*     */   
/*     */   public ImmutableSet<Score> getScores(OfflinePlayer player) throws IllegalArgumentException {
/*  78 */     Validate.notNull(player, "OfflinePlayer cannot be null");
/*     */     
/*  80 */     ImmutableSet.Builder<Score> scores = ImmutableSet.builder();
/*  81 */     for (CraftObjective objective : this.objectives.values()) {
/*  82 */       scores.add(objective.getScore(player));
/*     */     }
/*  84 */     return scores.build();
/*     */   }
/*     */   
/*     */   public ImmutableSet<Score> getScores(String entry) throws IllegalArgumentException {
/*  88 */     Validate.notNull(entry, "Entry cannot be null");
/*     */     
/*  90 */     ImmutableSet.Builder<Score> scores = ImmutableSet.builder();
/*  91 */     for (CraftObjective objective : this.objectives.values()) {
/*  92 */       scores.add(objective.getScore(entry));
/*     */     }
/*  94 */     return scores.build();
/*     */   }
/*     */   
/*     */   public void resetScores(OfflinePlayer player) throws IllegalArgumentException {
/*  98 */     Validate.notNull(player, "OfflinePlayer cannot be null");
/*     */     
/* 100 */     this.board.resetPlayerScores(player.getName());
/*     */   }
/*     */   
/*     */   public void resetScores(String entry) throws IllegalArgumentException {
/* 104 */     Validate.notNull(entry, "Entry cannot be null");
/*     */     
/* 106 */     this.board.resetPlayerScores(entry);
/*     */   }
/*     */   
/*     */   public Team getPlayerTeam(OfflinePlayer player) throws IllegalArgumentException {
/* 110 */     Validate.notNull(player, "OfflinePlayer cannot be null");
/*     */     
/* 112 */     ScoreboardTeam team = this.board.getPlayerTeam(player.getName());
/* 113 */     return (team == null) ? null : this.teams.get(team.getName());
/*     */   }
/*     */   
/*     */   public Team getTeam(String teamName) throws IllegalArgumentException {
/* 117 */     Validate.notNull(teamName, "Team name cannot be null");
/*     */     
/* 119 */     return this.teams.get(teamName);
/*     */   }
/*     */   
/*     */   public ImmutableSet<Team> getTeams() {
/* 123 */     return ImmutableSet.copyOf(this.teams.values());
/*     */   }
/*     */   
/*     */   public Team registerNewTeam(String name) throws IllegalArgumentException {
/* 127 */     Validate.notNull(name, "Team name cannot be null");
/* 128 */     Validate.isTrue((name.length() <= 16), "Team name '" + name + "' is longer than the limit of 16 characters");
/* 129 */     Validate.isTrue((this.board.getTeam(name) == null), "Team name '" + name + "' is already in use");
/*     */     
/* 131 */     return new CraftTeam(this, this.board.createTeam(name));
/*     */   }
/*     */   
/*     */   public ImmutableSet<OfflinePlayer> getPlayers() {
/* 135 */     ImmutableSet.Builder<OfflinePlayer> players = ImmutableSet.builder();
/* 136 */     for (Object playerName : this.board.getPlayers()) {
/* 137 */       players.add(Bukkit.getOfflinePlayer(playerName.toString()));
/*     */     }
/* 139 */     return players.build();
/*     */   }
/*     */   
/*     */   public ImmutableSet<String> getEntries() {
/* 143 */     ImmutableSet.Builder<String> entries = ImmutableSet.builder();
/* 144 */     for (Object entry : this.board.getPlayers()) {
/* 145 */       entries.add(entry.toString());
/*     */     }
/* 147 */     return entries.build();
/*     */   }
/*     */   
/*     */   public void clearSlot(DisplaySlot slot) throws IllegalArgumentException {
/* 151 */     Validate.notNull(slot, "Slot cannot be null");
/* 152 */     this.board.setDisplaySlot(CraftScoreboardTranslations.fromBukkitSlot(slot), null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Scoreboard getHandle() {
/* 157 */     return this.board;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\scoreboard\CraftScoreboard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */