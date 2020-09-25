/*     */ package org.bukkit.craftbukkit.v1_7_R4.scoreboard;
/*     */ 
/*     */ import net.minecraft.server.v1_7_R4.Scoreboard;
/*     */ import net.minecraft.server.v1_7_R4.ScoreboardObjective;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.scoreboard.DisplaySlot;
/*     */ import org.bukkit.scoreboard.Objective;
/*     */ import org.bukkit.scoreboard.Score;
/*     */ import org.bukkit.scoreboard.Scoreboard;
/*     */ 
/*     */ final class CraftObjective extends CraftScoreboardComponent implements Objective {
/*     */   private final ScoreboardObjective objective;
/*     */   private final CraftCriteria criteria;
/*     */   
/*     */   CraftObjective(CraftScoreboard scoreboard, ScoreboardObjective objective) {
/*  17 */     super(scoreboard);
/*  18 */     this.objective = objective;
/*  19 */     this.criteria = CraftCriteria.getFromNMS(objective);
/*     */     
/*  21 */     scoreboard.objectives.put(objective.getName(), this);
/*     */   }
/*     */   
/*     */   ScoreboardObjective getHandle() {
/*  25 */     return this.objective;
/*     */   }
/*     */   
/*     */   public String getName() throws IllegalStateException {
/*  29 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  31 */     return this.objective.getName();
/*     */   }
/*     */   
/*     */   public String getDisplayName() throws IllegalStateException {
/*  35 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  37 */     return this.objective.getDisplayName();
/*     */   }
/*     */   
/*     */   public void setDisplayName(String displayName) throws IllegalStateException, IllegalArgumentException {
/*  41 */     Validate.notNull(displayName, "Display name cannot be null");
/*  42 */     Validate.isTrue((displayName.length() <= 32), "Display name '" + displayName + "' is longer than the limit of 32 characters");
/*  43 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  45 */     this.objective.setDisplayName(displayName);
/*     */   }
/*     */   
/*     */   public String getCriteria() throws IllegalStateException {
/*  49 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  51 */     return this.criteria.bukkitName;
/*     */   }
/*     */   
/*     */   public boolean isModifiable() throws IllegalStateException {
/*  55 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  57 */     return !this.criteria.criteria.isReadOnly();
/*     */   }
/*     */   
/*     */   public void setDisplaySlot(DisplaySlot slot) throws IllegalStateException {
/*  61 */     CraftScoreboard scoreboard = checkState();
/*  62 */     Scoreboard board = scoreboard.board;
/*  63 */     ScoreboardObjective objective = this.objective;
/*     */     
/*  65 */     for (int i = 0; i < 3; i++) {
/*  66 */       if (board.getObjectiveForSlot(i) == objective) {
/*  67 */         board.setDisplaySlot(i, null);
/*     */       }
/*     */     } 
/*  70 */     if (slot != null) {
/*  71 */       int slotNumber = CraftScoreboardTranslations.fromBukkitSlot(slot);
/*  72 */       board.setDisplaySlot(slotNumber, getHandle());
/*     */     } 
/*     */   }
/*     */   
/*     */   public DisplaySlot getDisplaySlot() throws IllegalStateException {
/*  77 */     CraftScoreboard scoreboard = checkState();
/*  78 */     Scoreboard board = scoreboard.board;
/*  79 */     ScoreboardObjective objective = this.objective;
/*     */     
/*  81 */     for (int i = 0; i < 3; i++) {
/*  82 */       if (board.getObjectiveForSlot(i) == objective) {
/*  83 */         return CraftScoreboardTranslations.toBukkitSlot(i);
/*     */       }
/*     */     } 
/*  86 */     return null;
/*     */   }
/*     */   
/*     */   public Score getScore(OfflinePlayer player) throws IllegalArgumentException, IllegalStateException {
/*  90 */     Validate.notNull(player, "Player cannot be null");
/*  91 */     CraftScoreboard scoreboard = checkState();
/*     */     
/*  93 */     return new CraftScore(this, player.getName());
/*     */   }
/*     */   
/*     */   public Score getScore(String entry) throws IllegalArgumentException, IllegalStateException {
/*  97 */     Validate.notNull(entry, "Entry cannot be null");
/*  98 */     if (entry.length() > 16) throw new IllegalArgumentException("Entry cannot be longer than 16 characters!"); 
/*  99 */     CraftScoreboard scoreboard = checkState();
/*     */     
/* 101 */     return new CraftScore(this, entry);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregister() throws IllegalStateException {
/* 106 */     CraftScoreboard scoreboard = checkState();
/*     */     
/* 108 */     scoreboard.objectives.remove(getName());
/* 109 */     scoreboard.board.unregisterObjective(this.objective);
/* 110 */     setUnregistered();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\scoreboard\CraftObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */