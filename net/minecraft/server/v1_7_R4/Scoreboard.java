/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Scoreboard
/*     */ {
/*  15 */   private final Map objectivesByName = new HashMap<Object, Object>();
/*  16 */   private final Map objectivesByCriteria = new HashMap<Object, Object>();
/*  17 */   private final Map playerScores = new HashMap<Object, Object>();
/*  18 */   private final ScoreboardObjective[] displaySlots = new ScoreboardObjective[3];
/*  19 */   private final Map teamsByName = new HashMap<Object, Object>();
/*  20 */   private final Map teamsByPlayer = new HashMap<Object, Object>();
/*     */   
/*     */   public ScoreboardObjective getObjective(String paramString) {
/*  23 */     return (ScoreboardObjective)this.objectivesByName.get(paramString);
/*     */   }
/*     */   
/*     */   public ScoreboardObjective registerObjective(String paramString, IScoreboardCriteria paramIScoreboardCriteria) {
/*  27 */     ScoreboardObjective scoreboardObjective = getObjective(paramString);
/*  28 */     if (scoreboardObjective != null) throw new IllegalArgumentException("An objective with the name '" + paramString + "' already exists!");
/*     */     
/*  30 */     scoreboardObjective = new ScoreboardObjective(this, paramString, paramIScoreboardCriteria);
/*     */     
/*  32 */     List<ScoreboardObjective> list = (List)this.objectivesByCriteria.get(paramIScoreboardCriteria);
/*     */     
/*  34 */     if (list == null) {
/*  35 */       list = new ArrayList();
/*  36 */       this.objectivesByCriteria.put(paramIScoreboardCriteria, list);
/*     */     } 
/*     */     
/*  39 */     list.add(scoreboardObjective);
/*  40 */     this.objectivesByName.put(paramString, scoreboardObjective);
/*  41 */     handleObjectiveAdded(scoreboardObjective);
/*     */     
/*  43 */     return scoreboardObjective;
/*     */   }
/*     */   
/*     */   public Collection getObjectivesForCriteria(IScoreboardCriteria paramIScoreboardCriteria) {
/*  47 */     Collection<?> collection = (Collection)this.objectivesByCriteria.get(paramIScoreboardCriteria);
/*     */     
/*  49 */     return (collection == null) ? new ArrayList() : new ArrayList(collection);
/*     */   }
/*     */   
/*     */   public ScoreboardScore getPlayerScoreForObjective(String paramString, ScoreboardObjective paramScoreboardObjective) {
/*  53 */     Map<Object, Object> map = (Map)this.playerScores.get(paramString);
/*     */     
/*  55 */     if (map == null) {
/*  56 */       map = new HashMap<Object, Object>();
/*  57 */       this.playerScores.put(paramString, map);
/*     */     } 
/*     */     
/*  60 */     ScoreboardScore scoreboardScore = (ScoreboardScore)map.get(paramScoreboardObjective);
/*     */     
/*  62 */     if (scoreboardScore == null) {
/*  63 */       scoreboardScore = new ScoreboardScore(this, paramScoreboardObjective, paramString);
/*  64 */       map.put(paramScoreboardObjective, scoreboardScore);
/*     */     } 
/*     */     
/*  67 */     return scoreboardScore;
/*     */   }
/*     */   
/*     */   public Collection getScoresForObjective(ScoreboardObjective paramScoreboardObjective) {
/*  71 */     ArrayList<ScoreboardScore> arrayList = new ArrayList();
/*     */     
/*  73 */     for (Map map : this.playerScores.values()) {
/*  74 */       ScoreboardScore scoreboardScore = (ScoreboardScore)map.get(paramScoreboardObjective);
/*  75 */       if (scoreboardScore != null) arrayList.add(scoreboardScore);
/*     */     
/*     */     } 
/*  78 */     Collections.sort(arrayList, ScoreboardScore.a);
/*     */     
/*  80 */     return arrayList;
/*     */   }
/*     */   
/*     */   public Collection getObjectives() {
/*  84 */     return this.objectivesByName.values();
/*     */   }
/*     */   
/*     */   public Collection getPlayers() {
/*  88 */     return this.playerScores.keySet();
/*     */   }
/*     */   
/*     */   public void resetPlayerScores(String paramString) {
/*  92 */     Map map = (Map)this.playerScores.remove(paramString);
/*     */     
/*  94 */     if (map != null) {
/*  95 */       handlePlayerRemoved(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   public Collection getScores() {
/* 100 */     Collection collection = this.playerScores.values();
/* 101 */     ArrayList arrayList = new ArrayList();
/*     */     
/* 103 */     for (Map map : collection) {
/* 104 */       arrayList.addAll(map.values());
/*     */     }
/*     */     
/* 107 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map getPlayerObjectives(String paramString) {
/* 123 */     Map<Object, Object> map = (Map)this.playerScores.get(paramString);
/* 124 */     if (map == null) map = new HashMap<Object, Object>(); 
/* 125 */     return map;
/*     */   }
/*     */   
/*     */   public void unregisterObjective(ScoreboardObjective paramScoreboardObjective) {
/* 129 */     this.objectivesByName.remove(paramScoreboardObjective.getName());
/*     */     
/* 131 */     for (byte b = 0; b < 3; b++) {
/* 132 */       if (getObjectiveForSlot(b) == paramScoreboardObjective) setDisplaySlot(b, null);
/*     */     
/*     */     } 
/* 135 */     List list = (List)this.objectivesByCriteria.get(paramScoreboardObjective.getCriteria());
/* 136 */     if (list != null) list.remove(paramScoreboardObjective);
/*     */     
/* 138 */     for (Map map : this.playerScores.values()) {
/* 139 */       map.remove(paramScoreboardObjective);
/*     */     }
/*     */     
/* 142 */     handleObjectiveRemoved(paramScoreboardObjective);
/*     */   }
/*     */   
/*     */   public void setDisplaySlot(int paramInt, ScoreboardObjective paramScoreboardObjective) {
/* 146 */     this.displaySlots[paramInt] = paramScoreboardObjective;
/*     */   }
/*     */   
/*     */   public ScoreboardObjective getObjectiveForSlot(int paramInt) {
/* 150 */     return this.displaySlots[paramInt];
/*     */   }
/*     */   
/*     */   public ScoreboardTeam getTeam(String paramString) {
/* 154 */     return (ScoreboardTeam)this.teamsByName.get(paramString);
/*     */   }
/*     */   
/*     */   public ScoreboardTeam createTeam(String paramString) {
/* 158 */     ScoreboardTeam scoreboardTeam = getTeam(paramString);
/* 159 */     if (scoreboardTeam != null) throw new IllegalArgumentException("A team with the name '" + paramString + "' already exists!");
/*     */     
/* 161 */     scoreboardTeam = new ScoreboardTeam(this, paramString);
/* 162 */     this.teamsByName.put(paramString, scoreboardTeam);
/* 163 */     handleTeamAdded(scoreboardTeam);
/*     */     
/* 165 */     return scoreboardTeam;
/*     */   }
/*     */   
/*     */   public void removeTeam(ScoreboardTeam paramScoreboardTeam) {
/* 169 */     this.teamsByName.remove(paramScoreboardTeam.getName());
/*     */ 
/*     */ 
/*     */     
/* 173 */     for (String str : paramScoreboardTeam.getPlayerNameSet()) {
/* 174 */       this.teamsByPlayer.remove(str);
/*     */     }
/*     */     
/* 177 */     handleTeamRemoved(paramScoreboardTeam);
/*     */   }
/*     */   
/*     */   public boolean addPlayerToTeam(String paramString1, String paramString2) {
/* 181 */     if (!this.teamsByName.containsKey(paramString2)) return false; 
/* 182 */     ScoreboardTeam scoreboardTeam = getTeam(paramString2);
/*     */     
/* 184 */     if (getPlayerTeam(paramString1) != null) {
/* 185 */       removePlayerFromTeam(paramString1);
/*     */     }
/*     */     
/* 188 */     this.teamsByPlayer.put(paramString1, scoreboardTeam);
/* 189 */     scoreboardTeam.getPlayerNameSet().add(paramString1);
/* 190 */     return true;
/*     */   }
/*     */   
/*     */   public boolean removePlayerFromTeam(String paramString) {
/* 194 */     ScoreboardTeam scoreboardTeam = getPlayerTeam(paramString);
/*     */     
/* 196 */     if (scoreboardTeam != null) {
/* 197 */       removePlayerFromTeam(paramString, scoreboardTeam);
/* 198 */       return true;
/*     */     } 
/* 200 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePlayerFromTeam(String paramString, ScoreboardTeam paramScoreboardTeam) {
/* 205 */     if (getPlayerTeam(paramString) != paramScoreboardTeam) {
/* 206 */       throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + paramScoreboardTeam.getName() + "'.");
/*     */     }
/*     */     
/* 209 */     this.teamsByPlayer.remove(paramString);
/* 210 */     paramScoreboardTeam.getPlayerNameSet().remove(paramString);
/*     */   }
/*     */   
/*     */   public Collection getTeamNames() {
/* 214 */     return this.teamsByName.keySet();
/*     */   }
/*     */   
/*     */   public Collection getTeams() {
/* 218 */     return this.teamsByName.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScoreboardTeam getPlayerTeam(String paramString) {
/* 226 */     return (ScoreboardTeam)this.teamsByPlayer.get(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleObjectiveAdded(ScoreboardObjective paramScoreboardObjective) {}
/*     */ 
/*     */   
/*     */   public void handleObjectiveChanged(ScoreboardObjective paramScoreboardObjective) {}
/*     */ 
/*     */   
/*     */   public void handleObjectiveRemoved(ScoreboardObjective paramScoreboardObjective) {}
/*     */ 
/*     */   
/*     */   public void handleScoreChanged(ScoreboardScore paramScoreboardScore) {}
/*     */ 
/*     */   
/*     */   public void handlePlayerRemoved(String paramString) {}
/*     */ 
/*     */   
/*     */   public void handleTeamAdded(ScoreboardTeam paramScoreboardTeam) {}
/*     */ 
/*     */   
/*     */   public void handleTeamChanged(ScoreboardTeam paramScoreboardTeam) {}
/*     */ 
/*     */   
/*     */   public void handleTeamRemoved(ScoreboardTeam paramScoreboardTeam) {}
/*     */   
/*     */   public static String getSlotName(int paramInt) {
/* 254 */     switch (paramInt) {
/*     */       case 0:
/* 256 */         return "list";
/*     */       case 1:
/* 258 */         return "sidebar";
/*     */       case 2:
/* 260 */         return "belowName";
/*     */     } 
/* 262 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getSlotForName(String paramString) {
/* 267 */     if (paramString.equalsIgnoreCase("list"))
/* 268 */       return 0; 
/* 269 */     if (paramString.equalsIgnoreCase("sidebar"))
/* 270 */       return 1; 
/* 271 */     if (paramString.equalsIgnoreCase("belowName")) {
/* 272 */       return 2;
/*     */     }
/* 274 */     return -1;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Scoreboard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */